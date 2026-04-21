package med.voll.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.voll.api.dto.DadosAutenticacaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager manager;

    @PostMapping
    public ResponseEntity<Void> efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dadosDTO) {
        var token = new UsernamePasswordAuthenticationToken(dadosDTO.login(), dadosDTO.senha());
        var authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
