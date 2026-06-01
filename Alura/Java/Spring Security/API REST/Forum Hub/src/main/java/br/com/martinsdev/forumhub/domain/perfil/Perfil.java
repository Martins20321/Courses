package br.com.martinsdev.forumhub.domain.perfil;

import br.com.martinsdev.forumhub.domain.perfil.enums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfis")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfilUsuario;
}
