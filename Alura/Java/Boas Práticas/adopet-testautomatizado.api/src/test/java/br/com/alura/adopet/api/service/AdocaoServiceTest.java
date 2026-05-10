package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService service;

    @Mock
    private PetRepository petRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private EmailService emailService;

    @Spy //Controle de comportamento do objeto
    private List<ValidacaoSolicitacaoAdocao> validadores = new ArrayList<>();
    @Mock
    private ValidacaoSolicitacaoAdocao validador1;
    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    @Mock
    private Pet pet;
    @Mock
    private Tutor tutor;
    @Mock
    private Abrigo abrigo;
    @Spy
    private Adocao adocao;

    private SolicitacaoAdocaoDto dto;
    private AprovacaoAdocaoDto aproDTO;
    private ReprovacaoAdocaoDto reprovacaoAdocaoDto;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Test
    @DisplayName("Deveria salvar a adoção quando for feita a solicitação")
    public void verificacaoSalvarAdocaoSolicitacao() {

        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10l, 13l, "Motivo de exemplo");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        //ACT
        service.solicitar(dto);

        //ASSERT
        BDDMockito.then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocaoSalva = adocaoCaptor.getValue();

        //Garantindo que foi salvo e com as informações corretas
        Assertions.assertEquals(pet, adocaoSalva.getPet());
        Assertions.assertEquals(tutor, adocaoSalva.getTutor());
        Assertions.assertEquals(dto.motivo(), adocaoSalva.getMotivo());
    }

    @Test
    @DisplayName("Deve verificar a chamada de validadores ao chamar o método de solicitar")
    void verificacaoChamadaValidadoresAdocaoSolicitar() {

        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(pet.getId(), tutor.getId(), "Motivo Qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validadores.addAll(Arrays.asList(validador1, validador2));

        //ACT
        service.solicitar(dto);

        //ASSERTS
        then(validador1).should().validar(dto);
        then(validador2).should().validar(dto);
    }

    @Test
    @DisplayName("Deveria aprovar a solicitação desejada")
    void verificacaoAprovacaoDeAdocao() {

        //ARRANGE
        this.aproDTO = new AprovacaoAdocaoDto(1l);
        given(adocaoRepository.getReferenceById(aproDTO.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(adocao.getTutor()).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("email@example.com");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        StatusAdocao expectedValue = StatusAdocao.APROVADO;

        //ACT
        service.aprovar(aproDTO);
        StatusAdocao actualValue = adocao.getStatus();

        //ASSERT
        then(adocao).should().marcarComoAprovada();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Deve disparar um email de sucesso ao aprovar uma adoção")
    void verificacaoDispararEmailAoAprovar() {

        //ARRANGE
        this.aproDTO = new AprovacaoAdocaoDto(1l);
        given(adocaoRepository.getReferenceById(aproDTO.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(adocao.getTutor()).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("email@example.com");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        //ACT
        service.aprovar(aproDTO);

        //ASSERT
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção aprovada",
                "Parabéns " + adocao.getTutor().getNome() + "!\n\nSua adoção do pet " + adocao.getPet().getNome() + ", solicitada em "
                        + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi aprovada.\nFavor entrar em contato com o abrigo "
                        + adocao.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet.");
    }

    @Test
    @DisplayName("Deve retornar uma reprovação ao solicitar a adoção")
    void verificacaoDeReprovacaoDeAdocao() {

        //ARRANGE
        this.reprovacaoAdocaoDto = new ReprovacaoAdocaoDto(10l, "Justicativa de exemplo");
        given(adocaoRepository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(adocao.getTutor()).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(adocao.getData()).willReturn(LocalDateTime.now());

        StatusAdocao expectedValue = StatusAdocao.REPROVADO;

        //ACT
        service.reprovar(reprovacaoAdocaoDto);
        StatusAdocao actualValue = adocao.getStatus();

        //ASSERT
        then(adocao).should().marcarComoReprovada(reprovacaoAdocaoDto.justificativa());
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Deve enviar email ao reprovar uma solicitação de adoção")
    void verificacaoDispararEmailAoReprovar() {

        //ARRANGE
        this.reprovacaoAdocaoDto = new ReprovacaoAdocaoDto(10l, "Justicativa de exemplo");
        given(adocaoRepository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(adocao.getTutor()).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(adocao.getData()).willReturn(LocalDateTime.now());

        //ACT
        service.reprovar(reprovacaoAdocaoDto);

        //ASSERTIVE
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em "
                        + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo "
                        + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.getJustificativaStatus());
    }
}