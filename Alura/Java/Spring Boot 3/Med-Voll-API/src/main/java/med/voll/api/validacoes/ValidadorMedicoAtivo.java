package med.voll.api.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Medico;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.repository.MedicoRepository;

@RequiredArgsConstructor
public class ValidadorMedicoAtivo implements ValidadorStrategy{

    private final MedicoRepository medicoRepository;

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO) {
        Medico medico = medicoRepository.findById(dadosDTO.idMedico())
                .orElseThrow(() -> new ResourceNotFoundException(dadosDTO.idMedico()));

        boolean medicoInativo = medicoRepository.FyndAtivoById(dadosDTO.idMedico());

        if (medicoInativo){
            throw new RuntimeException("Consulta não pode ser agendada com Medico inativo!");
        }
    }
}
