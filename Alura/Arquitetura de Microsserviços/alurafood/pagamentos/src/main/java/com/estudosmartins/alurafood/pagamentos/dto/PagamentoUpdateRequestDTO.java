package com.estudosmartins.alurafood.pagamentos.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record PagamentoUpdateRequestDTO(@DecimalMin(value = "0.0", inclusive = false) BigDecimal valor,
                                        String name) {
}
