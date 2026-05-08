package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.dto.DadosCancelamentoConsultaDTO;
import med.voll.api.dto.DadosDetalhamentoConsultaDTO;
import med.voll.api.service.AgendaConsultasService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private final AgendaConsultasService consultasService;

    @PostMapping("/agendar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsultaDTO> agendar(@RequestBody @Valid DadosAgendamentoConsultaDTO dadosDTO) {
        DadosDetalhamentoConsultaDTO consultaDTO = consultasService.agendar(dadosDTO);
        return ResponseEntity.ok().body(consultaDTO);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cancelamento(@RequestBody @Valid DadosCancelamentoConsultaDTO dadosDTO) {
        consultasService.cancelar(dadosDTO);
        return ResponseEntity.noContent().build();
    }
}
