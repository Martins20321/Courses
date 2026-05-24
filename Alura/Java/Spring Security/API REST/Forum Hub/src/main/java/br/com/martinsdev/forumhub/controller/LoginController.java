package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.authentication.dto.DadosLoginDTO;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.infra.security.DadosTokenJWT;
import br.com.martinsdev.forumhub.infra.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosLoginDTO dadosDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosDTO.email(), dadosDTO.password());
        var authentication = authenticationManager.authenticate(authenticationToken); //Processo de autenticação
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        //Próximo passo: fazer o bloqueio de outra requisições
        return ResponseEntity.ok().body(new DadosTokenJWT(tokenJWT));
    }
}
