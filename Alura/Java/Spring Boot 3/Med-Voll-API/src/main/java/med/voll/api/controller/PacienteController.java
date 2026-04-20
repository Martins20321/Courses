package med.voll.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Paciente;
import med.voll.api.dto.DadosAtualizacaoPacienteDTO;
import med.voll.api.dto.DadosCadastroPacienteDTO;
import med.voll.api.dto.DadosDetalhamentoPacienteDTO;
import med.voll.api.dto.DadosListagemPacienteDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.repository.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPacienteDTO> cadastrar(@RequestBody @Valid DadosCadastroPacienteDTO dadosDTO) {
        Paciente paciente = new Paciente(dadosDTO);
        repository.save(paciente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPacienteDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacienteDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = repository.findAllByAtivoTrue(pageable).map(DadosListagemPacienteDTO::new);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPacienteDTO> listarPorId(@PathVariable Long id) {
        Paciente paciente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return ResponseEntity.ok().body(new DadosDetalhamentoPacienteDTO(paciente));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoPacienteDTO> atualizar(@RequestBody @Valid DadosAtualizacaoPacienteDTO dadosDTO, @PathVariable Long id) {
        Paciente paciente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        paciente.atualizarInformacoes(dadosDTO);
        return ResponseEntity.ok().body(new DadosDetalhamentoPacienteDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Paciente pacieteTemp = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        repository.delete(pacieteTemp);
        return ResponseEntity.noContent().build();
    }
}
