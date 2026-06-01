package br.com.martinsdev.forumhub.domain.usuario;

import br.com.martinsdev.forumhub.domain.perfil.Perfil;
import br.com.martinsdev.forumhub.domain.perfil.enums.PerfilUsuario;
import br.com.martinsdev.forumhub.infra.exception.RegraDeNegocioException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String nickName;
    private String biografia;
    private String headLine;
    private boolean verificado;
    private String tokenIdentificador;
    private LocalDateTime tokenExpiracao;
    private boolean ativo = true;

    //Trabalhando com múltiplos perfis
    private List<Perfil> perfis = new ArrayList<>();

    public Usuario(DadosCadastroUsuarioDTO dadosDTO, String encode) {
        this.nomeCompleto = dadosDTO.nomeCompleto();
        this.email = dadosDTO.email();
        this.senha = encode;
        this.nickName = dadosDTO.nickName();
        this.biografia = dadosDTO.biografia();
        this.headLine = dadosDTO.headLine();
        this.verificado = false;
        this.tokenIdentificador = UUID.randomUUID().toString();
        this.tokenExpiracao = LocalDateTime.now().plusMinutes(30);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //Relacionado a permissões
        return perfis;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void verificar() {
        if (tokenExpiracao.isBefore(LocalDateTime.now())){
            throw new RegraDeNegocioException("O link de verificação expirou!");
        }
        this.verificado = true;
        this.tokenIdentificador = null;
        this.tokenExpiracao = null;
    }

    public void atualizarDados(DadosAtualizacaoUsuario dadosDTO) {
        this.nickName = dadosDTO.nickName();
        this.headLine = dadosDTO.headLine();
        this.biografia = dadosDTO.biografia();
    }
}
