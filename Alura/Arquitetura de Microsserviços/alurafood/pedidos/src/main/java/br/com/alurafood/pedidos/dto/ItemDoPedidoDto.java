package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.ItemDoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDoPedidoDto {

    private Long id;
    private Integer quantidade;
    private String descricao;

    public ItemDoPedidoDto(ItemDoPedido itemDoPedido) {
        this(itemDoPedido.getId(), itemDoPedido.getQuantidade(), itemDoPedido.getDescricao());
    }
}
