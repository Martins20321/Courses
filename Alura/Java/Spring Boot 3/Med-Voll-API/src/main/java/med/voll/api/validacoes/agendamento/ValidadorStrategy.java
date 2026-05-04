package med.voll.api.validacoes.agendamento;

import med.voll.api.dto.DadosAgendamentoConsultaDTO;

public interface ValidadorStrategy {

    void validar(DadosAgendamentoConsultaDTO dadosDTO);
}
