package com.estudosmartins.alurafood.pagamentos.controller;

import com.estudosmartins.alurafood.pagamentos.dto.PagamentoCreateRequestDTO;
import com.estudosmartins.alurafood.pagamentos.dto.PagamentoResponseDTO;
import com.estudosmartins.alurafood.pagamentos.dto.PagamentoUpdateRequestDTO;
import com.estudosmartins.alurafood.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService service;

    @GetMapping
    public ResponseEntity<Page<PagamentoResponseDTO>> findAllPagamentos(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.findAllPagamentos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> createPagamento(@RequestBody @Valid PagamentoCreateRequestDTO pagamentoRequestDTO) {
        PagamentoResponseDTO pagamento = service.createPagamento(pagamentoRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pagamento.id()).toUri();
        return ResponseEntity.created(uri).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> updatePagamento(@PathVariable Long id, @RequestBody @Valid PagamentoUpdateRequestDTO pagamentoUpdateRequestDTO) {
        return ResponseEntity.ok(service.updatePagamento(id, pagamentoUpdateRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        service.deletePagamento(id);
        return ResponseEntity.noContent().build();
    }
}
