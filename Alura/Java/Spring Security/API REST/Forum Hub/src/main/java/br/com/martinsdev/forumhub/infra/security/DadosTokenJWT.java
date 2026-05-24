package br.com.martinsdev.forumhub.infra.security;

public record DadosTokenJWT(String token,
                            String refreshToken) {
}
