package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.domain.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class A2fController {

    private final UsuarioService usuarioService;

    @PatchMapping("/configurar-a2f")
    public ResponseEntity<String> geraQrCode(@AuthenticationPrincipal Usuario usuarioLogado) {
        var url = usuarioService.gerarUrl(usuarioLogado);
        return ResponseEntity.ok(url);
    }
}
