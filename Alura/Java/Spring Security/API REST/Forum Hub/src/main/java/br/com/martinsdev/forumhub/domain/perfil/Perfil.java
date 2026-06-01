package br.com.martinsdev.forumhub.domain.perfil;

import br.com.martinsdev.forumhub.domain.perfil.enums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "perfis")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Perfil implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario tipo;

    @Override
    public String getAuthority() {
        return "ROLE_" + tipo;
    }
}
