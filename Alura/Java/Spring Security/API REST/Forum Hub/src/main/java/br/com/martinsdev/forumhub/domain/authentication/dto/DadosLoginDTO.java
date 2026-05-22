package br.com.martinsdev.forumhub.domain.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosLoginDTO(@NotBlank String email,
                            @NotBlank String password) {
}
