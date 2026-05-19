package med.voll.web_application.domain.medico;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    public Medico(DadosCadastroMedico dados) {
        atualizarDados(dados);
    }

    public void atualizarDados(DadosCadastroMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
    }

}
