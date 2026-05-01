package med.voll.api.service;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Consulta;
import med.voll.api.domain.Medico;
import med.voll.api.domain.Paciente;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.dto.DadosCancelamentoConsultaDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AgendaConsultasService {

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsultaDTO agendamentoConsultaDTO) {
        Consulta consulta = new Consulta(agendamentoConsultaDTO);

        Medico medico = escolherMedico(agendamentoConsultaDTO);
        Paciente paciente = pacienteRepository.findById(agendamentoConsultaDTO.idPaciente())
                .orElseThrow(() -> new ResourceNotFoundException(agendamentoConsultaDTO.idPaciente()));

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        consulta = repository.save(consulta);
        return new ConsultaDTO(consulta);
    }


    public void cancelamento(DadosCancelamentoConsultaDTO dadosDTO) {
        Consulta consulta = repository.findById(dadosDTO.idConsulta())
                .orElseThrow(() -> new ResourceNotFoundException(dadosDTO.idConsulta()));

        consulta.setCancelar(dadosDTO.motivo());

        String motivo = dadosDTO.motivo();
        Instant momentoCancelamento = dadosDTO.momento();

        if (motivo == null) {
            throw new RuntimeException("O motivo do cancelamento é obrigatório!");
        }
        if (momentoCancelamento.isBefore(momentoCancelamento.minusSeconds(86400))) {
            throw new RuntimeException("Uma consulta somente poderá ser cancelada com antecedência mínima de 24 horas");
        }

        repository.delete(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsultaDTO agendamentoConsultaDTO) {
        if (agendamentoConsultaDTO.idMedico() != null) {
            return medicoRepository.findById(agendamentoConsultaDTO.idMedico())
                    .orElseThrow(() -> new ResourceNotFoundException(agendamentoConsultaDTO.idMedico()));
        }
        if (agendamentoConsultaDTO.especialidade() == null){
            throw new RuntimeException("Especialidade é obrigatória quando o médico não for escolhido");
        }

        return medicoRepository.FindRandomly(agendamentoConsultaDTO.especialidade(), agendamentoConsultaDTO.data());
    }
}
