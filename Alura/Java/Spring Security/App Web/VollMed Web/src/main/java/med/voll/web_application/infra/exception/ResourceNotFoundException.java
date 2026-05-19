package med.voll.web_application.infra.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id) {
        super("Resource not found! Id: " + id);
    }
}
