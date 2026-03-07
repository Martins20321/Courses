package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizarTutorDTO;
import br.com.alura.adopet.api.dto.CadastrarTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public void cadastrarTutor(CadastrarTutorDTO dto){
       boolean tutorJaCadastrado = repository.existsByTelefoneOrEmail(dto.telefone(), dto.email());

        if (tutorJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        }
        else {
            repository.save(new Tutor(dto));
        }
    }

    public void atualizarTutor(AtualizarTutorDTO dto){
        Tutor tutor = repository.getReferenceById(dto.id());
        tutor.atualizarInfo(dto);
    }

}
