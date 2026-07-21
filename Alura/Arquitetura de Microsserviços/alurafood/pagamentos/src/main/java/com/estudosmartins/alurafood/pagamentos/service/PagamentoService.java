package com.estudosmartins.alurafood.pagamentos.service;

import com.estudosmartins.alurafood.pagamentos.dto.PagamentoCreateRequestDTO;
import com.estudosmartins.alurafood.pagamentos.dto.PagamentoResponseDTO;
import com.estudosmartins.alurafood.pagamentos.dto.PagamentoUpdateRequestDTO;
import com.estudosmartins.alurafood.pagamentos.infra.client.PedidoClient;
import com.estudosmartins.alurafood.pagamentos.infra.exception.ResourceNotFoundException;
import com.estudosmartins.alurafood.pagamentos.model.Pagamento;
import com.estudosmartins.alurafood.pagamentos.model.enums.StatusPagamento;
import com.estudosmartins.alurafood.pagamentos.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository repository;
    private final PedidoClient pedidoClient;

    public Page<PagamentoResponseDTO> findAllPagamentos(Pageable pageable) {
        return repository.findAll(pageable).map(PagamentoResponseDTO::new);
    }

    public PagamentoResponseDTO findById(Long id) {
        return repository.findById(id).map(PagamentoResponseDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public PagamentoResponseDTO createPagamento(PagamentoCreateRequestDTO pagamentoRequestDTO) {
        Pagamento pagamento = Pagamento.builder()
                .valor(pagamentoRequestDTO.valor())
                .numero(pagamentoRequestDTO.numero())
                .nome(pagamentoRequestDTO.nome())
                .expiracao(pagamentoRequestDTO.expiracao())
                .codigo(pagamentoRequestDTO.codigo())
                .status(StatusPagamento.CRIADO)
                .pedidoId(pagamentoRequestDTO.pedidoId())
                .formaDePagamentoId(pagamentoRequestDTO.formaDePagamentoId())
                .build();
        repository.save(pagamento);
        return new PagamentoResponseDTO(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO updatePagamento(Long id, PagamentoUpdateRequestDTO pagamentoUpdateRequestDTO) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        repository.save(pagamento);
        return new PagamentoResponseDTO(pagamento);
    }

    @Transactional
    public void deletePagamento(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        pagamento.setStatus(StatusPagamento.CANCELADO);
        repository.save(pagamento);
    }

    @Transactional
    public void confirmarPagamento(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        pagamento.setStatus(StatusPagamento.CONFIRMADO);
        repository.save(pagamento);
        pedidoClient.atualizarPagamento(pagamento.getPedidoId());
    }
}
