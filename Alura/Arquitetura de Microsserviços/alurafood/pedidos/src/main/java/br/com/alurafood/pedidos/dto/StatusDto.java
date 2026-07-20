package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private StatusPedido status;
}
