package br.com.martinsdev.forumhub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAlteracaoSenha(@NotBlank String senhaAtual,
                                  @NotBlank String senhaNova,
                                  @NotBlank String senhaConfirmada) {
}
