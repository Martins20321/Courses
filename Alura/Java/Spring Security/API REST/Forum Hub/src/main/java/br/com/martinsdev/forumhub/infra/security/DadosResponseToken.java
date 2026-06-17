package br.com.martinsdev.forumhub.infra.security;

public record DadosResponseToken(String token,
                            String refreshToken) {
}
