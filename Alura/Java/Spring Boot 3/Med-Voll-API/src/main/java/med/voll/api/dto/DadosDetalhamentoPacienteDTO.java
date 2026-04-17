package med.voll.api.dto;

import med.voll.api.domain.Paciente;
import med.voll.api.domain.Endereco;

public record DadosDetalhamentoPacienteDTO(Long id, String nome, String email, String cpf, String telefone, Endereco endereco) {

    public DadosDetalhamentoPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
    }
}
