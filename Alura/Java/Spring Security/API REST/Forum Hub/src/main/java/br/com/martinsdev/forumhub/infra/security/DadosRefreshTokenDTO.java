package br.com.martinsdev.forumhub.infra.security;

import jakarta.validation.constraints.NotBlank;

public record DadosRefreshTokenDTO(@NotBlank String refreshToken) {
}
