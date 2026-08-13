package com.estudosmartins.alurafood.pagamentos.service;

import com.estudosmartins.alurafood.pagamentos.dto.*;
import com.estudosmartins.alurafood.pagamentos.event.PagamentoConcluidoEvent;
import com.estudosmartins.alurafood.pagamentos.infra.client.PedidoClient;
import com.estudosmartins.alurafood.pagamentos.infra.exception.ResourceNotFoundException;
import com.estudosmartins.alurafood.pagamentos.model.Pagamento;
import com.estudosmartins.alurafood.pagamentos.model.enums.StatusPagamento;
import com.estudosmartins.alurafood.pagamentos.repository.PagamentoRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository repository;
    private final PedidoClient pedidoClient;
    private final RabbitTemplate rabbitTemplate;

    public Page<PagamentoResponseDTO> findAllPagamentos(Pageable pageable) {
        return repository.findAll(pageable).map(PagamentoResponseDTO::new);
    }

    public PagamentoDetailsResponseDTO findById(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        List<ItemDoPedidoDTO> itensPedido = pedidoClient.buscarItensPedido(pagamento.getPedidoId());
        return new PagamentoDetailsResponseDTO(pagamento, itensPedido);
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

        //Routing key vai ser ignorada devido ao modelo de fanout
        rabbitTemplate.convertAndSend("pagamentos.ex","", new PagamentoConcluidoEvent(pagamento));
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
    @CircuitBreaker(name = "atualizarPedido", fallbackMethod = "fallbackConfirmarPagamento")
    public void confirmarPagamento(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        pagamento.setStatus(StatusPagamento.CONFIRMADO);
        repository.save(pagamento);
        pedidoClient.atualizarPagamento(pagamento.getPedidoId());
    }

    //Resposta alternativa ao usuário quando o Circuite Breaker estiver com estado ABERTO
    public void fallbackConfirmarPagamento(Long id, Exception e) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        pagamento.setStatus(StatusPagamento.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(pagamento);
    }
}
