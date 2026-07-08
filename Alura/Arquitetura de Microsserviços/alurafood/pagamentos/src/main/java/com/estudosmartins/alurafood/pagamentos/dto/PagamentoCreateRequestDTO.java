package com.estudosmartins.alurafood.pagamentos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PagamentoCreateRequestDTO(@DecimalMin(value = "0.0", inclusive = false) BigDecimal valor,
                                  @NotBlank String nome,
                                  @NotBlank String numero,
                                  @NotBlank String expiracao,
                                  @NotBlank @Size(min = 3, max = 3) String codigo,
                                  @NotNull Long pedidoId,
                                  @NotNull Long formaDePagamentoId) {
}
