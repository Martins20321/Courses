package br.com.martinsdev.forumhub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuarioDTO(@NotBlank String email,
                                      @NotBlank String senha,
                                      @NotBlank String nomeCompleto,
                                      @NotBlank String nickName,
                                      @NotBlank String headLine,
                                      @NotBlank String biografia) {
}
