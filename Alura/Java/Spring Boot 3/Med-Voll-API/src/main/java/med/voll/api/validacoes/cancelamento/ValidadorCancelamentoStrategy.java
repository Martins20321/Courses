package med.voll.api.validacoes.cancelamento;

import med.voll.api.dto.DadosCancelamentoConsultaDTO;

public interface ValidadorCancelamentoStrategy {

    void validarCancelamento(DadosCancelamentoConsultaDTO dadosDTO);
}
