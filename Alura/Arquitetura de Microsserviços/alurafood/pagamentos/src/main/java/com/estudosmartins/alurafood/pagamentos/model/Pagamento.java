package com.estudosmartins.alurafood.pagamentos.model;

import com.estudosmartins.alurafood.pagamentos.model.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_pagamentos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String expiracao;
    @Column(nullable = false)
    private String codigo;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    @Column(nullable = false)
    private Long pedidoId;
    @Column(nullable = false)
    private Long formaDePagamentoId;
}
