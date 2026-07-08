package com.estudosmartins.alurafood.pagamentos.dto;

import com.estudosmartins.alurafood.pagamentos.model.Pagamento;
import com.estudosmartins.alurafood.pagamentos.model.enums.StatusPagamento;

import java.math.BigDecimal;

public record PagamentoResponseDTO(Long id,
                                   BigDecimal valor,
                                   String nome,
                                   String numero,
                                   String expiracao,
                                   StatusPagamento status,
                                   Long pedidoId,
                                   Long formaDePagamentoId) {
    public PagamentoResponseDTO(Pagamento pagamento) {
        this(pagamento.getId(), pagamento.getValor(), pagamento.getNome(), pagamento.getNumero(),
                pagamento.getExpiracao(), pagamento.getStatus(), pagamento.getPedidoId(), pagamento.getFormaDePagamentoId());
    }
}
