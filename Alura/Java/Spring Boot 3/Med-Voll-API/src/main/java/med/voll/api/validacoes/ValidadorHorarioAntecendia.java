package med.voll.api.validacoes;

import med.voll.api.dto.DadosAgendamentoConsultaDTO;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecendia implements ValidadorStrategy {

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO) {
        LocalDateTime horarioConsulta = dadosDTO.data();
        LocalDateTime momentoAtual = LocalDateTime.now();
        var diferencaMinutos = Duration.between(horarioConsulta, momentoAtual).toMinutes();

        if (diferencaMinutos < 30) {
            throw new RuntimeException("Consulta deve ser agendada com antecedência mínima de trinta minutos");
        }
    }
}
