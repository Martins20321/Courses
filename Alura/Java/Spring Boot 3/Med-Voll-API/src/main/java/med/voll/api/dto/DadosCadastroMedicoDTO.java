package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.enums.Especialidade;

public record DadosCadastroMedicoDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email(message = "Formato do email é inválido")
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}", message = "Formato do CRM é inválido")
        String crm,
        @NotNull
        Especialidade especialidade,

        @NotNull @Valid DadosEnderecoDTO endereco) {
}
