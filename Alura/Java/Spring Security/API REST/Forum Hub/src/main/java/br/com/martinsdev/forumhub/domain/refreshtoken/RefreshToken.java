package br.com.martinsdev.forumhub.domain.refreshtoken;

import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean utilizado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
}
