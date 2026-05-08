package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.Medico;

public record DadosAtualizacaoMedicoDTO(
        String nome,
        String telefone,
        DadosEnderecoDTO endereco) {
}
