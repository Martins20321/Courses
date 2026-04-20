package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroPacienteDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email(message = "Formato do email é inválido")
        String email,

        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}", message = "Formato do CPF é inválido")
        String cpf,

        @NotNull @Valid DadosEnderecoDTO endereco) {
}