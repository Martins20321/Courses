package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.dto.DadosDetalhamentoConsultaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsultaDTO> agendar(@RequestBody @Valid DadosAgendamentoConsultaDTO dadosDTO) {
        System.out.println(dadosDTO);
        return ResponseEntity.ok().body(new DadosDetalhamentoConsultaDTO(null, null, null, null));

    }
}
