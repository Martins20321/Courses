package med.voll.api.service;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Consulta;
import med.voll.api.domain.Medico;
import med.voll.api.domain.Paciente;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendaConsultasService {

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsultaDTO agendamentoConsultaDTO) {
        
        Medico medico = medicoRepository.findById(agendamentoConsultaDTO.idMedico())
                .orElseThrow(() -> new ResourceNotFoundException(agendamentoConsultaDTO.idMedico()));
        Paciente paciente = pacienteRepository.findById(agendamentoConsultaDTO.idPaciente())
                .orElseThrow(() -> new ResourceNotFoundException(agendamentoConsultaDTO.idPaciente()));
        Consulta consulta = new Consulta(null, medico, paciente, agendamentoConsultaDTO.data());
    }
}
