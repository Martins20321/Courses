package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Medico;
import med.voll.api.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.dto.DadosCadastroMedicoDTO;
import med.voll.api.dto.DadosDetalhamentoMedicoDTO;
import med.voll.api.dto.DadosListagemMedicoDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.repository.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    private final MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedicoDTO> cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dadosDTO) {
        Medico medico = new Medico(dadosDTO);
        repository.save(medico);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedicoDTO(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicoDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicoDTO::new);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedicoDTO> listarPorId(@PathVariable Long id){
        Medico medico = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return ResponseEntity.ok().body(new DadosDetalhamentoMedicoDTO(medico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedicoDTO> atualizar(@RequestBody @Valid DadosAtualizacaoMedicoDTO dadosDTO, @PathVariable Long id) {
        Medico medico = repository.findById(dadosDTO.id()).orElseThrow(() -> new ResourceNotFoundException(id));
        medico.atualizarInformacoes(dadosDTO);

        return ResponseEntity.ok().body(new DadosDetalhamentoMedicoDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Medico medicoTemp = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        repository.delete(medicoTemp);
        return ResponseEntity.noContent().build();
    }
}