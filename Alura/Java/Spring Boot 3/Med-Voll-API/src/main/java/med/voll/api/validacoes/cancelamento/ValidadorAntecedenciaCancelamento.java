package med.voll.api.validacoes.cancelamento;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.Consulta;
import med.voll.api.dto.DadosCancelamentoConsultaDTO;
import med.voll.api.infra.exception.ResourceNotFoundException;
import med.voll.api.infra.exception.ValidationException;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorAntecedenciaCancelamento implements ValidadorCancelamentoStrategy {

    private final ConsultaRepository consultaRepository;

    @Override
    public void validarCancelamento(DadosCancelamentoConsultaDTO dadosDTO) {
        Consulta consulta = consultaRepository.findById(dadosDTO.idConsulta())
                .orElseThrow(() -> new ResourceNotFoundException(dadosDTO.idConsulta()));
        LocalDateTime horaConsulta = consulta.getData();
        LocalDateTime horaAtual = LocalDateTime.now();
        var diferencaHoras = Duration.between(horaConsulta, horaAtual).toHours();

        if (diferencaHoras < 24){
            throw new ValidationException("A consulta somente pode ser cancelada com 24 horas de antecedência");
        }
    }
}
