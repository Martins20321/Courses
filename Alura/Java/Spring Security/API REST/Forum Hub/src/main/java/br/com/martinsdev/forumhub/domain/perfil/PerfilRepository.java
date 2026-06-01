package br.com.martinsdev.forumhub.domain.perfil;

import br.com.martinsdev.forumhub.domain.perfil.enums.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByTipo(PerfilUsuario tipoPerfil);
}
