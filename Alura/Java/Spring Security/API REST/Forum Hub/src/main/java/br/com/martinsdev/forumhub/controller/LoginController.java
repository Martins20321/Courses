package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.authentication.dto.DadosLoginDTO;
import br.com.martinsdev.forumhub.domain.refreshtoken.RefreshToken;
import br.com.martinsdev.forumhub.domain.refreshtoken.RefreshTokenRepository;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.domain.usuario.UsuarioRepository;
import br.com.martinsdev.forumhub.infra.exception.RefreshTokenException;
import br.com.martinsdev.forumhub.infra.exception.RefreshTokenNotFoundException;
import br.com.martinsdev.forumhub.infra.exception.ResourceNotFoundException;
import br.com.martinsdev.forumhub.infra.security.DadosRefreshTokenDTO;
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
    private final UsuarioRepository repository;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosLoginDTO dadosDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosDTO.email(), dadosDTO.password());
        var authentication = authenticationManager.authenticate(authenticationToken); //Processo de autenticação
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        var refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok().body(new DadosTokenJWT(tokenJWT, refreshToken.toString()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<DadosTokenJWT> atualizarToken(@RequestBody @Valid DadosRefreshTokenDTO dados){
        tokenService.getSubject(dados.refreshToken());
        RefreshToken refreshToken = refreshTokenRepository.findByToken(dados.refreshToken())
                .orElseThrow(() -> new RefreshTokenNotFoundException(dados.refreshToken()));

        if (refreshToken.isUtilizado()){
            throw new RefreshTokenException("Este token já foi utilizado!");
        }

        refreshToken.setUtilizado(true);
        refreshTokenRepository.save(refreshToken);

        Usuario usuario = refreshToken.getUsuario();

        var tokenJWT = tokenService.gerarToken(usuario);
        var tokenAtualizado = tokenService.gerarRefreshToken(usuario);

        return ResponseEntity.ok().body(new DadosTokenJWT(tokenJWT, tokenAtualizado.toString()));
    }
}
