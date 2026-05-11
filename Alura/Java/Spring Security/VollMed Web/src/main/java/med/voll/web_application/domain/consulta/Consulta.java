package med.voll.web_application.domain.consulta;

import jakarta.persistence.*;
import lombok.*;
import med.voll.web_application.domain.medico.Medico;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medico medico;

    private LocalDateTime data;

    public void modificarDados(Medico medico, DadosAgendamentoConsulta dados) {
        this.medico = medico;
        this.paciente = dados.paciente();
        this.data = dados.data();
    }

}
