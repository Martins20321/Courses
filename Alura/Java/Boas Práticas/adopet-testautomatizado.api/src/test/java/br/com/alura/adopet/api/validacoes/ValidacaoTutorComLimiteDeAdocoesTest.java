package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validador;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    private SolicitacaoAdocaoDto adocaoDto;
    private List<Adocao> adocoes = new ArrayList<>();

    @Mock
    private Tutor tutor;

    @Test
    void verificarTutorComLimiteDeAdocoes() {

        this.adocaoDto = new SolicitacaoAdocaoDto(1l,2l, "Motivo Qualquer");

        //Simulando que o tutor já tem 5 adoções aprovadas
        for(int i = 0; i<= 5; i ++) {
            Adocao adocaoExistente = new Adocao();
            adocaoExistente.setTutor(tutor);
            adocaoExistente.setStatus(StatusAdocao.APROVADO);
            adocoes.add(adocaoExistente);
        }

        when(tutorRepository.getReferenceById(adocaoDto.idTutor())).thenReturn(tutor);
        when(adocaoRepository.findAll()).thenReturn(adocoes);

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> validador.validar(adocaoDto));
        Assertions.assertEquals("Tutor chegou ao limite máximo de 5 adoções!", exception.getMessage());
    }

}