package med.voll.api.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Paciente;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.repository.PacienteRepository;

@RequiredArgsConstructor
public class ValidadorPacienteAtivo implements ValidadorStrategy {

    private final PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO) {

        Paciente paciente = pacienteRepository.findById(dadosDTO.idPaciente())
                .orElseThrow(() -> new ResourceNotFoundException(dadosDTO.idPaciente()));

        boolean pacienteInativo = pacienteRepository.findAtivoById(dadosDTO.idPaciente());
        if (pacienteInativo) {
            throw new RuntimeException("Consulta não pode ser agendada com Paciente inativo!");
        }
    }
}
