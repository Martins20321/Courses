package med.voll.api.validacoes;

import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorStrategy {

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO) {
        LocalDateTime horarioConsulta = dadosDTO.data();
        LocalDateTime momentoAtual = LocalDateTime.now();
        var diferencaMinutos = Duration.between(momentoAtual, horarioConsulta).toMinutes();

        if (diferencaMinutos < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecedência mínima de trinta minutos");
        }
    }
}
