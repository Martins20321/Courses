package med.voll.api.validacoes.cancelamento;

import med.voll.api.domain.MotivoCancelamento;
import med.voll.api.dto.DadosCancelamentoConsultaDTO;
import med.voll.api.infra.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMotivoCancelamentoObrigatorio implements ValidadorCancelamentoStrategy {

    @Override
    public void validarCancelamento(DadosCancelamentoConsultaDTO dadosDTO) {
        MotivoCancelamento motivo = dadosDTO.motivo();

        if (motivo == null){
            throw new ValidationException("É obrigatório informar o motivo do cancelamento!");
        }
    }
}
