package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.authentication.github.LoginGitHubService;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.domain.usuario.UsuarioRepository;
import br.com.martinsdev.forumhub.domain.usuario.UsuarioService;
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
@RequestMapping("/login/github")
@RequiredArgsConstructor
public class LoginGitHubController {

    private final LoginGitHubService loginGitHubService;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<Void> redirecionarGitHub() {
        var url = loginGitHubService.gerarUrl();
        var headers = new HttpHeaders();
        headers.setLocation(URI.create(url));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/autorizado")
    public ResponseEntity<DadosResponseToken> autenticarUsuarioOAuth(@RequestParam String code) {
        var dadosGitHubUsuario = loginGitHubService.obterDadosUsuario(code);

        var emailExistente = usuarioService.existePorEmail(dadosGitHubUsuario.email());

        UserDetails usuario;
        if (emailExistente) {
            usuario = usuarioService.loadUserByUsername(dadosGitHubUsuario.email());
        } else {
            usuario = usuarioService.cadastrarViaGitHub(dadosGitHubUsuario);
        }

        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var tokenAcesso = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        var refresthToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosResponseToken(tokenAcesso, refresthToken));
    }
}
