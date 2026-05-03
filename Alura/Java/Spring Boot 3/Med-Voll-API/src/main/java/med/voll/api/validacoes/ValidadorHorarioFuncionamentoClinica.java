package med.voll.api.validacoes;

import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorStrategy{

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO){
        LocalDateTime dataConsulta = dadosDTO.data();
        boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        boolean depoisDoEncerramentoDaClinica = dataConsulta.getHour() < 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica){
            throw new RuntimeException("Consulta fora do horário de funcionamento da cliínica");
        }
    }
}
