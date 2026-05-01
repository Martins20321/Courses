package med.voll.api.repository;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.Medico;
import med.voll.api.domain.enums.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.ativo = true and m.especialidade = :especialidade and
            m.id NOT IN(
                SELECT c.medico.id FROM Consulta c
                WHERE c.data = :data
                )
            order by rand()
            LIMIT 1;
            """)
    Medico FindRandomly(Especialidade especialidade, @NotNull @Future LocalDateTime data);
}
