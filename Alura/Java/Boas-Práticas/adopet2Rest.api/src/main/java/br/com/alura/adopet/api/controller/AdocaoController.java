package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AprovacaoAbrigoDTO;
import br.com.alura.adopet.api.dto.ReprovacaoAbrigoDTO;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.service.AdocaoService;
import br.com.alura.adopet.api.service.ValidacaoException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid SolicitacaoAdocaoDTO adocaoDTO) {
        try {
            adocaoService.solicitar(adocaoDTO);
            return ResponseEntity.ok().body("Adoção solicitada com sucesso!");
        }
        catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAbrigoDTO adocaoDTO) {
        try{
            adocaoService.aprovar(adocaoDTO);
            return ResponseEntity.ok().build();
        }
        catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovacaoAbrigoDTO adocaoDTO) {
        adocaoService.reprovar(adocaoDTO);
        return ResponseEntity.ok().build();
    }

}
