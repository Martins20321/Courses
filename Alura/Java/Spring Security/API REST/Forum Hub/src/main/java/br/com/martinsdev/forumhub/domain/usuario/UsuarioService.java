package br.com.martinsdev.forumhub.domain.usuario;

import br.com.martinsdev.forumhub.domain.perfil.PerfilRepository;
import br.com.martinsdev.forumhub.domain.perfil.enums.PerfilUsuario;
import br.com.martinsdev.forumhub.infra.email.EmailService;
import br.com.martinsdev.forumhub.infra.exception.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PerfilRepository perfilRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmailIgnoreCaseAndVerificadoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }

    @Transactional
    public Usuario cadastrar(DadosCadastroUsuarioDTO dadosDTO) {
        boolean existisByEmail = repository.existsUsuarioByEmail(dadosDTO.email());

        if (existisByEmail){
            throw new RegraDeNegocioException("Já existe uma conta cadastrada com esse email!");
        }

        //Definindo perfil padrão para um usuário
        var perfil = perfilRepository.findByTipo(PerfilUsuario.ESTUDANTE)
                .orElseThrow(() -> new RegraDeNegocioException("Não foi possível encontrar o perfil informado!"));
        Usuario usuario = new Usuario(dadosDTO, passwordEncoder.encode(dadosDTO.senha()), perfil);

        emailService.verificaoEmail(usuario);

        repository.save(usuario);
        return usuario;
    }

    @Transactional
    public Usuario atualizarPerfil(Usuario usuarioLogado, DadosAtualizacaoUsuario dadosDTO){
        Usuario usuario = repository.findByEmailIgnoreCaseAndVerificadoTrue(usuarioLogado.toString())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados!"));

        usuario.atualizarDados(dadosDTO);
        repository.save(usuario);
        return usuario;
    }

    @Transactional
    public void alterarSenha(Usuario usuarioLogado, DadosAlteracaoSenha dadosDTO) {
        if (!passwordEncoder.matches(dadosDTO.senhaAtual(), usuarioLogado.getPassword())){
            throw new RegraDeNegocioException("Senha digitada não confere com a atual");
        }

        if (!dadosDTO.senhaNova().equals(dadosDTO.senhaConfirmada())){
            throw new RegraDeNegocioException("Por gentileza, confira a senha de confirmação!");
        }

        usuarioLogado.setSenha(passwordEncoder.encode(dadosDTO.senhaAtual()));
    }

    @Transactional
    public void desativarPerfil(Usuario usuarioLogado) {
        Usuario usuario = repository.findByEmailIgnoreCaseAndVerificadoTrue(usuarioLogado.toString())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        usuario.setAtivo(false);
        repository.save(usuario);
    }

    @Transactional
    public void verificarEmail(String codigo) {
        Usuario usuario = repository.findByTokenIdentificador(codigo).
                orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));

        usuario.verificar();
    }
}
