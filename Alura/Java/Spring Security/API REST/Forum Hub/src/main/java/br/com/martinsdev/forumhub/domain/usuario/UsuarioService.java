package br.com.martinsdev.forumhub.domain.usuario;

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

        Usuario usuario = new Usuario(dadosDTO, passwordEncoder.encode(dadosDTO.senha()));
        repository.save(usuario);
        return usuario;
    }
}
