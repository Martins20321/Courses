package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;
    @Mock
    private Tutor tutor;

    @Captor
    private ArgumentCaptor<Tutor> tutorArgumentCaptor;

    private CadastroTutorDto cadastroTutorDto;
    private AtualizacaoTutorDto  atualizacaoTutorDto;

    @Test
    @DisplayName("Deve ter sucesso ao realizar um cadastro de um tutor")
    void verificacaoSucessoAoCadastrar() {

        //ARRANGE
        this.cadastroTutorDto = new CadastroTutorDto("Pedro", "(61) 99123-4567", "pedro@email.com");

        //ACT
        service.cadastrar(cadastroTutorDto);

        //ASSERTIVE
        then(repository).should().save(tutorArgumentCaptor.capture());
        Tutor tutorSalvo = tutorArgumentCaptor.getValue();

        Assertions.assertEquals(cadastroTutorDto.email(), tutorSalvo.getEmail());
    }

    @Test
    @DisplayName("Deve ter sucesso ao atualizar um tutor")
    void verificacaoDeSucessoAoAtualizar(){

        //ARRANGE
        this.atualizacaoTutorDto = new AtualizacaoTutorDto(10l, "João marcos", "(84) 99231-0987", "joao.marcos@email.com");
        given(repository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);

        //ACT
        service.atualizar(atualizacaoTutorDto);

        //ASSERT
        then(tutor).should().atualizarDados(atualizacaoTutorDto);
    }
}