package med.voll.api.validacoes.agendamento;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Medico;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.infra.exception.ValidationException;
import med.voll.api.repository.MedicoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorMedicoAtivo implements ValidadorStrategy {

    private final MedicoRepository medicoRepository;

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO) {
        Medico medico = medicoRepository.findById(dadosDTO.idMedico())
                .orElseThrow(() -> new ResourceNotFoundException(dadosDTO.idMedico()));

        Boolean medicoAtivo = medicoRepository.findAtivoById(dadosDTO.idMedico());
        if (!medicoAtivo){
            throw new ValidationException("Consulta não pode ser agendada com Medico inativo!");
        }
    }
}
