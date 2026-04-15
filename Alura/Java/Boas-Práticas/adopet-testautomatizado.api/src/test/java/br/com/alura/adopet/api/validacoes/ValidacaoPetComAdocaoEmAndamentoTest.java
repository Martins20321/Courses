package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento adocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    private SolicitacaoAdocaoDto adocaoDto;

    @Test
    @DisplayName("Deve retornar informando que o pet está aguardando avaliação")
    void verificarPetComAdocaoEmAndamento() {

        this.adocaoDto = new SolicitacaoAdocaoDto(1l, 2l, "Motivo qualquer");

        when(adocaoRepository.existsByPetIdAndStatus(adocaoDto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(true);

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> adocaoEmAndamento.validar(adocaoDto));
        Assertions.assertEquals("Pet já está aguardando avaliação para ser adotado!", exception.getMessage());
    }

    @Test
    void verificarPermissãoDeSoliciatcaoPetComAdocaoInexistente() {

        this.adocaoDto = new SolicitacaoAdocaoDto(1l, 2l, "Motivo qualquer");

        when(adocaoRepository.existsByPetIdAndStatus(adocaoDto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> adocaoEmAndamento.validar(adocaoDto));
    }
}