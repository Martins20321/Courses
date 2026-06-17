package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.authentication.dto.DadosRequestLoginDTO;
import br.com.martinsdev.forumhub.domain.refreshtoken.RefreshTokenService;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.infra.security.DadosRefreshTokenDTO;
import br.com.martinsdev.forumhub.infra.security.DadosResponseToken;
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
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<DadosResponseToken> efetuarLogin(@RequestBody @Valid DadosRequestLoginDTO dadosDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosDTO.email(), dadosDTO.password());
        var authentication = authenticationManager.authenticate(authenticationToken); //Processo de autenticação
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        var refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok().body(new DadosResponseToken(tokenJWT, refreshToken.toString()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<DadosResponseToken> atualizarToken(@RequestBody @Valid DadosRefreshTokenDTO refreshTokenDTO){
        return ResponseEntity.ok().body(refreshTokenService.atualizarToken(refreshTokenDTO));
    }
}
