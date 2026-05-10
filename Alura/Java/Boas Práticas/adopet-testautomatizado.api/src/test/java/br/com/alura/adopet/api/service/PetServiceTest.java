package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService service;

    @Mock
    private PetRepository repository;
    @Mock
    private Pet pet;
    @Mock
    private PetDto petDto;
    @Mock
    private Abrigo abrigo;
    @Mock
    private CadastroPetDto cadastroPetDto;

    @Test
    void verificacaoListarBuscasDisponiveis() {

        //ACT
        service.buscarPetsDisponiveis();

        //ASSERT
        then(repository).should().findAllByAdotadoFalse();
    }

    @Test
    void verificacaoDeSucessoAoCadastrarPet() {

        //ACT
        service.cadastrarPet(abrigo, cadastroPetDto);

        //ASSERT
        then(repository).should().save(new Pet(cadastroPetDto, abrigo));
    }

}