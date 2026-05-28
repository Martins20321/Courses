package br.com.martinsdev.forumhub.domain.resposta;

import java.time.LocalDateTime;

public record DadosListagemResposta(
        Long id,
        String mensagem,
        String autor,
        LocalDateTime dataCriacao,
        Boolean solucao
) {
    public DadosListagemResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getAutor().getNickName(), resposta.getDataCriacao(), resposta.ehSolucao());
    }
}
