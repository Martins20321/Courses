package com.estudosmartins.alurafood.pagamentos.infra.client;

import com.estudosmartins.alurafood.pagamentos.dto.ItemDoPedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient("pedidos-ms")
public interface PedidoClient {

    @PutMapping("/v1/pedidos/{id}/pago")
    void atualizarPagamento(@PathVariable Long id);

    @GetMapping("/v1/pedidos/{id}/itens-pedido")
    List<ItemDoPedidoDTO> buscarItensPedido(@PathVariable Long id);
}
