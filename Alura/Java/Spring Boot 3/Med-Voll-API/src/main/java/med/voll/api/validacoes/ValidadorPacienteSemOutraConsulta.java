package med.voll.api.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.PacienteRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ValidadorPacienteSemOutraConsulta implements ValidadorStrategy{

    private final ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO) {
        LocalDateTime primeiroHorario = dadosDTO.data().withHour(7);
        LocalDateTime ultimoHorario = dadosDTO.data().withHour(18);
        boolean pacientePossuiOutraConsultaNoDia = consultaRepository
                .existsByPacienteIdAndDataBetween(dadosDTO.idPaciente(), primeiroHorario, ultimoHorario);

        if (pacientePossuiOutraConsultaNoDia){
            throw new RuntimeException("Paciente já possui outra consulta agendada neste dia");
        }
    }
}
