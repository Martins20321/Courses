package br.com.martinsdev.forumhub.domain.resposta;

import br.com.martinsdev.forumhub.domain.topico.Topico;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private LocalDateTime dataCriacao;
    private Boolean solucao;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Resposta(DadosCadastroResposta dados, Topico topico, Usuario autor) {
        this.mensagem = dados.mensagem();
        this.autor = autor;
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
