package com.estudosmartins.alurafood.pagamentos.repository;

import com.estudosmartins.alurafood.pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
