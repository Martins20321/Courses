package br.com.martinsdev.forumhub.controller;

import br.com.martinsdev.forumhub.domain.usuario.DadosCadastroUsuarioDTO;
import br.com.martinsdev.forumhub.domain.usuario.DadosListagemUsuario;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/registrar")
    public ResponseEntity<DadosListagemUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuarioDTO dadosDTO){
        Usuario usuario = service.cadastrar(dadosDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nomeUsuario}").buildAndExpand(usuario.getNickName()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemUsuario(usuario));
    }
}
