package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.PetService;
import br.com.alura.adopet.api.service.ValidacaoException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository repository;

    @Autowired
    private PetService service;

    @GetMapping
    public ResponseEntity<List<PetDTO>> listarTodosDisponiveis() {
        try {
            List<PetDTO> pets = service.listarTodosPets();
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            throw new ValidacaoException(e.getMessage());
        }
    }

}
