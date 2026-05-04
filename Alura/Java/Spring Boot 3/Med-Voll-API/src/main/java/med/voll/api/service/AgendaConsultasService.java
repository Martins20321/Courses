package med.voll.api.service;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Consulta;
import med.voll.api.domain.Medico;
import med.voll.api.domain.Paciente;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.dto.DadosCancelamentoConsultaDTO;
import med.voll.api.dto.DadosDetalhamentoConsultaDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.infra.exception.ValidationException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.validacoes.cancelamento.ValidadorCancelamentoStrategy;
import med.voll.api.validacoes.agendamento.ValidadorStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaConsultasService {

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    //Procura todas as classes que implementam essa interface
    private final List<ValidadorStrategy> validadores;
    private final List<ValidadorCancelamentoStrategy> validadoresCancelamento;

    public DadosDetalhamentoConsultaDTO agendar(DadosAgendamentoConsultaDTO agendamentoConsultaDTO) {

        validadores.forEach(validadorStrategy -> validadorStrategy.validar(agendamentoConsultaDTO));

        Paciente paciente = pacienteRepository.findById(agendamentoConsultaDTO.idPaciente())
                .orElseThrow(() -> new ResourceNotFoundException(agendamentoConsultaDTO.idPaciente()));

        Medico medico = escolherMedico(agendamentoConsultaDTO);
        if (medico == null) {
            throw new ValidationException("Não Existe médico disponível nesta data!");
        }

        Consulta consulta = new Consulta(agendamentoConsultaDTO);
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        consulta = repository.save(consulta);
        return new DadosDetalhamentoConsultaDTO(consulta);
    }

    public void cancelar(DadosCancelamentoConsultaDTO dadosDTO) {

        validadoresCancelamento.forEach(validadorCancelamento -> validadorCancelamento.validarCancelamento(dadosDTO));

       Consulta consulta = repository.findById(dadosDTO.idConsulta())
                       .orElseThrow(() -> new ResourceNotFoundException(dadosDTO.idConsulta()));

        repository.delete(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsultaDTO agendamentoConsultaDTO) {
        if (agendamentoConsultaDTO.idMedico() != null) {
            return medicoRepository.findById(agendamentoConsultaDTO.idMedico())
                    .orElseThrow(() -> new ResourceNotFoundException(agendamentoConsultaDTO.idMedico()));
        }
        if (agendamentoConsultaDTO.especialidade() == null) {
            throw new RuntimeException("Especialidade é obrigatória quando o médico não for escolhido");
        }

        return medicoRepository.findRandomly(agendamentoConsultaDTO.especialidade(), agendamentoConsultaDTO.data());
    }
}
