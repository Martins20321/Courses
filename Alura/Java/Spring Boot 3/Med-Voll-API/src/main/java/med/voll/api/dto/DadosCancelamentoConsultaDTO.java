package med.voll.api.dto;

import jakarta.validation.constraints.NotBlank;
import med.voll.api.domain.MotivoCancelamento;

public record DadosCancelamentoConsultaDTO(Long idConsulta,
                                           @NotBlank MotivoCancelamento motivo) {
}
