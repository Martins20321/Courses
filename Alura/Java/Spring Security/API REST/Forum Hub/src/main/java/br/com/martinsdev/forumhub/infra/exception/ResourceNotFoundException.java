package br.com.martinsdev.forumhub.infra.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id) {
        super("Recurso não encontrado! Id: " + id);
    }
}
