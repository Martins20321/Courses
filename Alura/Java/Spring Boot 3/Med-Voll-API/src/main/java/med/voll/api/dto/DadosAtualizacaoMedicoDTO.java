package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.Medico;

public record DadosAtualizacaoMedicoDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEnderecoDTO endereco) {
}
