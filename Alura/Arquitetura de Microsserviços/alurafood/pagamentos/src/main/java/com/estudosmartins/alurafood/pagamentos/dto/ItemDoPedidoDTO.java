package com.estudosmartins.alurafood.pagamentos.dto;


public record ItemDoPedidoDTO(Long id,
                              Integer quantidade,
                              String descricao) {

    public ItemDoPedidoDTO(ItemDoPedidoDTO itemDoPedido) {
        this(itemDoPedido.id(), itemDoPedido.quantidade(), itemDoPedido.descricao());
    }
}
