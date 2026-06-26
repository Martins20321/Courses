package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.usuario.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/registrar")
    public ResponseEntity<DadosListagemUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuarioDTO dadosDTO) {
        DadosListagemUsuario usuario = service.cadastrar(dadosDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nomeUsuario}").buildAndExpand(usuario.nickName()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping("/verificar-conta")
    public ResponseEntity<String> verificarEmail(@RequestParam String codigo) {
        service.verificarEmail(codigo);
        return ResponseEntity.ok().body("Conta verificada com sucesso!");
    }

    @PutMapping("/editar-perfil")
    public ResponseEntity<DadosListagemUsuario> atualizarUsuario(@AuthenticationPrincipal Usuario usuarioLogado, @RequestBody DadosAtualizacaoUsuario dadosDTO) {
        Usuario usuario = service.atualizarPerfil(usuarioLogado, dadosDTO);
        return ResponseEntity.ok().body(new DadosListagemUsuario(usuario));
    }

    @PatchMapping("/editar-senha")
    public ResponseEntity<Void> alterarSenha(@AuthenticationPrincipal Usuario usuarioLogado, @RequestBody @Valid DadosAlteracaoSenha dadosDTO) {
        service.alterarSenha(usuarioLogado, dadosDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/desativar")
    public ResponseEntity<Void> deletarUsuario(@AuthenticationPrincipal Usuario usuarioLogado) {
        service.desativarPerfil(usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}
