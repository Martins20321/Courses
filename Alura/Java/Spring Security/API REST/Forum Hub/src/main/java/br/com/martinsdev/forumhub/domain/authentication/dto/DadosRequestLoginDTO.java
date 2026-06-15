package br.com.martinsdev.forumhub.domain.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosRequestLoginDTO(@NotBlank String email,
                                   @NotBlank String password) {
}
