package com.estudosmartins.alurafood.pagamentos.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Resource not found by id: " + id);
    }
}
