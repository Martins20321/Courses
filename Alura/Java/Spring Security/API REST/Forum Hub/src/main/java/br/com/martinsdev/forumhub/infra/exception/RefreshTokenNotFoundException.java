package br.com.martinsdev.forumhub.infra.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String token) {
        super("Não foi encontrado este refresh token: " + token);
    }
}
