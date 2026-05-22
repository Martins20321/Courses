package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.authentication.dto.DadosLoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/auth/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<Authentication> efetuarLogin(@RequestBody @Valid DadosLoginDTO dadosDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosDTO.email(), dadosDTO.password());
        var authentication = authenticationManager.authenticate(authenticationToken); //Processo de autenticação
        return ResponseEntity.ok().body(authentication);
    }
}
