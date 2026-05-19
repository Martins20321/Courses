package br.com.martinsdev.forumhub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        @NotNull Long id,
        String titulo,
        String mensagem,
        Long cursoId
) {
}
