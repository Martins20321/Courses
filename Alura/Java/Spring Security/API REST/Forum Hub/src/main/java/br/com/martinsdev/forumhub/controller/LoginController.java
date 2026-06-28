package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.authentication.DadosA2fDTO;
import br.com.martinsdev.forumhub.domain.authentication.dto.DadosRequestLoginDTO;
import br.com.martinsdev.forumhub.domain.refreshtoken.RefreshTokenService;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.domain.usuario.UsuarioService;
import br.com.martinsdev.forumhub.infra.exception.RegraDeNegocioException;
import br.com.martinsdev.forumhub.infra.security.DadosRefreshTokenDTO;
import br.com.martinsdev.forumhub.infra.security.DadosResponseToken;
import br.com.martinsdev.forumhub.infra.security.TokenService;
import br.com.martinsdev.forumhub.infra.security.totp.TotpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final UsuarioService usuarioService;
    private final TotpService totpService;

    @PostMapping("/login")
    public ResponseEntity<DadosResponseToken> efetuarLogin(@RequestBody @Valid DadosRequestLoginDTO dadosDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosDTO.email(), dadosDTO.password());
        var authentication = authenticationManager.authenticate(authenticationToken); //Processo de autenticação

        var usuario = (Usuario) authentication.getPrincipal();
        if (usuario.isA2fAtiva()) {
            return ResponseEntity.ok(new DadosResponseToken(null, null, true)); //Front recebe e redirecionada para /verificar-a2f
        }

        var tokenJWT = tokenService.gerarToken(usuario);
        var refreshToken = tokenService.gerarRefreshToken(usuario);

        return ResponseEntity.ok().body(new DadosResponseToken(tokenJWT, refreshToken.toString(), false));
    }

    @PostMapping("/verificar-a2f")
    public ResponseEntity<DadosResponseToken> verificarSegundoFator(@Valid @RequestBody DadosA2fDTO dadosA2fDTO) {
        var usuario = usuarioService.loadUserByUsername(dadosA2fDTO.email());
        var codigoValido = totpService.validarCodigo(dadosA2fDTO.code(), (Usuario) usuario);

        if (!codigoValido) {
            throw new BadCredentialsException("Código Inválido!");
        }

        var tokenJWT = tokenService.gerarToken((Usuario) usuario);
        var refreshToken = tokenService.gerarRefreshToken((Usuario) usuario);
        return ResponseEntity.ok().body(new DadosResponseToken(tokenJWT, refreshToken.toString(), false));
    }

    @PostMapping("/refresh")
    public ResponseEntity<DadosResponseToken> atualizarToken(@RequestBody @Valid DadosRefreshTokenDTO refreshTokenDTO) {
        return ResponseEntity.ok().body(refreshTokenService.atualizarToken(refreshTokenDTO));
    }
}
