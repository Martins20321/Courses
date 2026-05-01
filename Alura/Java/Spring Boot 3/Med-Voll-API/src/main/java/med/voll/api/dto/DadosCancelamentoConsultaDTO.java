package med.voll.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record DadosCancelamentoConsultaDTO(Long idConsulta,
                                           @NotBlank String motivo,
                                           Instant momento) {
}
