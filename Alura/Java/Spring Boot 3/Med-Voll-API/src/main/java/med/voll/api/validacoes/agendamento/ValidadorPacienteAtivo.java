package med.voll.api.validacoes.agendamento;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Paciente;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.infra.exception.ValidationException;
import med.voll.api.repository.PacienteRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorPacienteAtivo implements ValidadorStrategy {

    private final PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO) {

        Paciente paciente = pacienteRepository.findById(dadosDTO.idPaciente())
                .orElseThrow(() -> new ResourceNotFoundException(dadosDTO.idPaciente()));

        Boolean pacienteAtivo = pacienteRepository.findAtivoById(dadosDTO.idPaciente());
        if (!pacienteAtivo) {
            throw new ValidationException("Consulta não pode ser agendada com Paciente inativo!");
        }
    }
}
