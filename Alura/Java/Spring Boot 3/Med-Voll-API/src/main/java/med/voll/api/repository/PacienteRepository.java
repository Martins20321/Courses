package med.voll.api.repository;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT p FROM Paciente p WHERE p.id = :idPaciente AND p.ativo = "true";
            """)
    boolean findAtivoById(@NotNull Long idPaciente);
}
