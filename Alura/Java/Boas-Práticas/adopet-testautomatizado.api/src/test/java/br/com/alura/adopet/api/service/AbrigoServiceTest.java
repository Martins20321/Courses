package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private Abrigo abrigo;
    @Mock
    private AbrigoRepository repository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private CadastroAbrigoDto cadastroAbrigoDto;

    @Test
    void verificacaoDeListarTodosOsAbrigos() {

        //ARRANGE

        //ACT
        service.listar();

        //ASSERT
        then(repository).should().findAll();
    }

    @Test
    void verificacaoDeSucessoAoCadastrarAbrigo() {

        //ARRANGE

        //ACT
        service.cadatrar(cadastroAbrigoDto);

        //ASSERTIVE
        then(repository).should().save(new Abrigo(cadastroAbrigoDto));
    }

    @Test
    void verificacaoDeDadosExistentesAoCadastrar() {

        //ARRANGE
        given(repository.existsByNomeOrTelefoneOrEmail(cadastroAbrigoDto.nome(), cadastroAbrigoDto.telefone(),
                cadastroAbrigoDto.email())).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows( ValidacaoException.class, () -> service.cadatrar(cadastroAbrigoDto));

    }

    @Test
    void verificacaoListarPetsDoAbrigoPeloNome(){

        //ARRANGE
        String nome = "Bob";
        given(repository.findByNome(nome)).willReturn(Optional.of(abrigo));

        //ACT
        service.listarPetsDoAbrigo(nome);

        //ASSERT
        then(petRepository).should().findByAbrigo(abrigo);
    }
}