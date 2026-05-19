package br.com.martinsdev.forumhub.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> tratarErro400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    //@ExceptionHandler(AccessDeniedException.class)
    //public ResponseEntity<String> tratarErro403(AccessDeniedException ex) {
      //  return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    //}

    //@ExceptionHandler(AuthenticationException.class)
    //public ResponseEntity<String> tratarErro401(AuthenticationException ex) {
      //  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    //}


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}