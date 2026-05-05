package med.voll.api.domain;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.dto.DadosAgendamentoConsultaDTO;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;

    private String motivo_cancelamento;

    @Transient
    private String cancelar;

    public Consulta(DadosAgendamentoConsultaDTO agendamentoConsultaDTO) {
        this.data = agendamentoConsultaDTO.data();
    }
}
