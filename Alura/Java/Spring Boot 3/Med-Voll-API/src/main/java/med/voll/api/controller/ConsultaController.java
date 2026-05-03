package med.voll.api.controller;

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
public class ConsultaController {

    private final AgendaConsultasService consultasService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsultaDTO> agendar(@RequestBody @Valid DadosAgendamentoConsultaDTO dadosDTO) {
        DadosDetalhamentoConsultaDTO consultaDTO = consultasService.agendar(dadosDTO);
        return ResponseEntity.ok().body(consultaDTO);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<String> cancelamento(@RequestBody @Valid DadosCancelamentoConsultaDTO dadosDTO) {
        consultasService.cancelamento(dadosDTO);
        return ResponseEntity.noContent().build();
    }
}
