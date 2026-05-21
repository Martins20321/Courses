package br.com.martinsdev.forumhub.domain.resposta;

import br.com.martinsdev.forumhub.domain.topico.Topico;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Resposta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private String autor;
    private LocalDateTime dataCriacao;
    private Boolean solucao;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Resposta(DadosCadastroResposta dados, Topico topico) {
        this.mensagem = dados.mensagem();
        this.autor = dados.autor();
        this.dataCriacao = LocalDateTime.now();
        this.solucao = false;
        this.topico = topico;
    }

    public Boolean ehSolucao() {
        return solucao;
    }

    public Resposta atualizarInformacoes(DadosAtualizacaoResposta dados) {
        this.mensagem = dados.mensagem();
        return this;
    }

    public Resposta marcarComoSolucao() {
        this.solucao = true;
        return this;
    }
}
