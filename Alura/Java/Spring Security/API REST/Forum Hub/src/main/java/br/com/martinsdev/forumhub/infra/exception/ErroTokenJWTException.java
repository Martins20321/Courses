package br.com.martinsdev.forumhub.infra.exception;

public class ErroTokenJWTException extends RuntimeException {
    public ErroTokenJWTException(String message) {
        super(message);
    }
}
