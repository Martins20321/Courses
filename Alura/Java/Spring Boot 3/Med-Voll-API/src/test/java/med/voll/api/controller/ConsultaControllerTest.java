package med.voll.api.controller;

import med.voll.api.domain.Medico;
import med.voll.api.domain.Paciente;
import med.voll.api.domain.enums.Especialidade;
import med.voll.api.dto.*;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.service.AgendaConsultasService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    private Long existingMedicoId = 1l;
    private Long existingPacienteId = 2l;

    @Autowired
    private JacksonTester<DadosAgendamentoConsultaDTO> dadosAgendamentoDTOJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsultaDTO> dadosDetalhamentoDTOJson;

    @MockBean
    private AgendaConsultasService agendaConsultasService;

    @BeforeEach
    void initialization() {
        repository.deleteAll();
        medicoRepository.deleteAll();
        pacienteRepository.deleteAll();

        Medico medico = new Medico(new DadosCadastroMedicoDTO("Dr. Rodrigo Oliveira", "rodrigo.oliveira@voll.med",
                "11988887777", "123456", Especialidade.CARDIOLOGIA,
                new DadosEnderecoDTO("Rua das Flores", "Jardim", "01234567", "São Paulo", "SP", "Apto 12", "100")));
        Medico medicoSaved = medicoRepository.save(medico);
        this.existingMedicoId = medicoSaved.getId();

        Paciente paciente = new Paciente(new DadosCadastroPacienteDTO("Carla Souza", "carla.souza@outlook.com", "31966665555", "456.789.123-22", new DadosEnderecoDTO("Rua da Bahia", "Lourdes", "30160011", "Belo Horizonte", "MG", "Bloco B", "1020")));
        Paciente pacienteSaved = pacienteRepository.save(paciente);
        this.existingPacienteId = pacienteSaved.getId();
    }

    @Test
    @DisplayName("Deve retornar código 200(Sucesso) ao fazer um agendamento")
    @WithMockUser
    void agendarCenario1() throws Exception {

        //ARRANGE
        var dataConsulta = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(11, 0);
        DadosAgendamentoConsultaDTO dadosAgendamentoDTO = new DadosAgendamentoConsultaDTO(existingMedicoId, existingPacienteId, dataConsulta, Especialidade.CARDIOLOGIA);

        DadosDetalhamentoConsultaDTO dadosDetalhamentoConsultaDTO = new DadosDetalhamentoConsultaDTO(null, existingMedicoId, existingPacienteId, dataConsulta);

        when(agendaConsultasService.agendar(any(DadosAgendamentoConsultaDTO.class)))
                .thenReturn(dadosDetalhamentoConsultaDTO);

        //ACT
        var response = mockMvc.perform(
                post("/consultas/agendar")
                        .content(dadosAgendamentoDTOJson.write(dadosAgendamentoDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());

        var jsonEsperado = dadosDetalhamentoDTOJson.write(dadosDetalhamentoConsultaDTO).getJson();

        Assertions.assertEquals(jsonEsperado, response.getContentAsString());

    }

    @Test
    @DisplayName("Deve retornar código 400(Bad Request) ao fazer um agendamento com insformações erradas")
    @WithMockUser
    void agendarCenario2() throws Exception {

        //ARRANGE
        DadosAgendamentoConsultaDTO dadosAgendamentoDTO = new DadosAgendamentoConsultaDTO(existingMedicoId, existingPacienteId, LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        //ACT
        var response = mockMvc.perform(
                post("/consultas/agendar")
                        .content(dadosAgendamentoDTOJson.write(dadosAgendamentoDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }
}