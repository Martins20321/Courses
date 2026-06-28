package br.com.martinsdev.forumhub.domain.authentication;

import jakarta.validation.constraints.NotBlank;

public record DadosA2fDTO(@NotBlank String tokenTemp,
                          @NotBlank String code) {
}
