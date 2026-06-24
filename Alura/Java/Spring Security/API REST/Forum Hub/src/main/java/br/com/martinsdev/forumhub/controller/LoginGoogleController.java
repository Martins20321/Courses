package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.authentication.google.LoginGoogleService;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.domain.usuario.UsuarioService;
import br.com.martinsdev.forumhub.infra.exception.RegraDeNegocioException;
import br.com.martinsdev.forumhub.infra.security.DadosResponseToken;
import br.com.martinsdev.forumhub.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/login/google")
@RequiredArgsConstructor
public class LoginGoogleController {

    private final LoginGoogleService loginGoogleService;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<Void> redirecionarGoogle() {
        var url = loginGoogleService.gerarUrlAutorizacao();

        var headers = new HttpHeaders();
        headers.setLocation(URI.create(url));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    //URL de callback
    @GetMapping("/autorizado")
    public ResponseEntity<DadosResponseToken> autenticarUsuarioOAuth(@RequestParam String code) {
        var email = loginGoogleService.obterEmail(code);

        boolean emailExistente = usuarioService.existePorEmail(email);

        UserDetails usuario;
        if (emailExistente) {
            usuario = usuarioService.loadUserByUsername(email);
        }
        else {
            usuario = usuarioService.cadastrarViaGoogle(email);
        }
        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var tokenAcesso = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        var refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosResponseToken(tokenAcesso, refreshToken));
    }
}
