package com.estudosmartins.alurafood.pagamentos.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("pedidos-ms")
public interface PedidoClient {

    @PutMapping("/v1/pedidos/{id}/pago")
    void atualizarPagamento(@PathVariable Long id);
}
