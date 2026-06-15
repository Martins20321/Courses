package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.authentication.github.LoginGitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<Void> redirecionarGitHub() {
        var url = loginGitHubService.gerarUrl();
        var headers = new HttpHeaders();
        headers.setLocation(URI.create(url));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    //Pegando o acess token
    @GetMapping("/autorizado")
    public ResponseEntity<String> obterTokenAcesso(@RequestParam String code) {
        var token = loginGitHubService.obterTokenAcesso(code);
        return ResponseEntity.ok(token);
    }
}
