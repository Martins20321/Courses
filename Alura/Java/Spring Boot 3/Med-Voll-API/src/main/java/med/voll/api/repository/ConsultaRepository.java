package med.voll.api.repository;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByPacienteIdAndDataBetween(@NotNull Long aLong, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndData(Long aLong, @NotNull @Future LocalDateTime data);
}
