package com.estudosmartins.alurafood.pagamentos.dto;

import com.estudosmartins.alurafood.pagamentos.model.Pagamento;
import com.estudosmartins.alurafood.pagamentos.model.enums.StatusPagamento;

import java.math.BigDecimal;
import java.util.List;

public record PagamentoDetailsResponseDTO(Long id,
                                          BigDecimal valor,
                                          String nome,
                                          String numero,
                                          String expiracao,
                                          StatusPagamento status,
                                          Long pedidoId,
                                          Long formaDePagamentoId,
                                          List<ItemDoPedidoDTO> itens) {

    public PagamentoDetailsResponseDTO(Pagamento pagamento, List<ItemDoPedidoDTO> itensPedido) {
        this(pagamento.getId(), pagamento.getValor(), pagamento.getNome(), pagamento.getNumero(),
                pagamento.getExpiracao(), pagamento.getStatus(), pagamento.getPedidoId(), pagamento.getFormaDePagamentoId(), itensPedido);
    }
}
