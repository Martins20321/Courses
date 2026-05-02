package med.voll.api.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;
import med.voll.api.repository.ConsultaRepository;

@RequiredArgsConstructor
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorStrategy{

    private final ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsultaDTO dadosDTO) {
        boolean medicoPossuiConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dadosDTO.idMedico(), dadosDTO.data());
        if (medicoPossuiConsultaNoMesmoHorario){
            throw new RuntimeException("Médico já possui outra consulta agendada neste mesmo horário");
        }

    }
}
