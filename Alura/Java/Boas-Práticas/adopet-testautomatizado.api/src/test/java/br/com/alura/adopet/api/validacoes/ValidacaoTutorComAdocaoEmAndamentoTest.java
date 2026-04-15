package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento tutorComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    private List<Adocao> adocoes = new ArrayList<>();

    @Mock
    private Tutor tutor;

    private SolicitacaoAdocaoDto adocaoDto;

    @Test
    @DisplayName("Deve retornar informando que o tutor está com adoção em andamento")
    void verificarTutorComAdocaoEmAndamento() {

        this.adocaoDto = new SolicitacaoAdocaoDto(1l, 2l, "Motivo Qualquer");

        Adocao adocao = new Adocao();
        adocao.setTutor(tutor);
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);

        adocoes.add(adocao);

        when(tutorRepository.getReferenceById(adocaoDto.idTutor())).thenReturn(tutor);
        when(adocaoRepository.findAll()).thenReturn(adocoes);


        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> tutorComAdocaoEmAndamento.validar(adocaoDto));
        Assertions.assertEquals("Tutor já possui outra adoção aguardando avaliação!", exception.getMessage());
    }

    @Test
    void verificarPermissaoTutorSemAdocaoEmAndamento() {

        this.adocaoDto = new SolicitacaoAdocaoDto(1l, 2l, "Motivo Qualquer");

        Adocao adocao = new Adocao();
        adocao.setTutor(tutor);
        adocao.setStatus(StatusAdocao.APROVADO);

        adocoes.add(adocao);

        when(tutorRepository.getReferenceById(adocaoDto.idTutor())).thenReturn(tutor);
        when(adocaoRepository.findAll()).thenReturn(adocoes);

        Assertions.assertDoesNotThrow(() -> tutorComAdocaoEmAndamento.validar(adocaoDto));
    }

}