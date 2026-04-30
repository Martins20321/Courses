package med.voll.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsultaDTO(Long idMedico,
                                          @NotNull @JsonAlias({"produto_id", "id_produto"}) Long idPaciente,
                                          @NotNull @Future LocalDateTime data) {
}
