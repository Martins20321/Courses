package med.voll.api.controller;

import com.jayway.jsonpath.JsonPath;
import med.voll.api.domain.Medico;
import med.voll.api.domain.enums.Especialidade;
import med.voll.api.dto.DadosAtualizacaoMedicoDTO;
import med.voll.api.dto.DadosCadastroMedicoDTO;
import med.voll.api.dto.DadosEnderecoDTO;
import med.voll.api.repository.MedicoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@WithMockUser
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private JacksonTester<DadosCadastroMedicoDTO> medicoDTOJacksonTester;

    @Autowired
    private JacksonTester<DadosAtualizacaoMedicoDTO> dadosAtualizacaoMedicoDTOJacksonTester;

    private Long existingMedicoId;
    private Long noneExistentId = 99l;

    @BeforeEach
    void initialization(){
        repository.deleteAll();

        Medico medico = new Medico(new DadosCadastroMedicoDTO("Dr. Rodrigo Oliveira", "rodrigo.oliveira@voll.med",
                "11988887777", "123456", Especialidade.CARDIOLOGIA,
                new DadosEnderecoDTO("Rua das Flores", "Jardim", "01234567", "São Paulo", "SP", "Apto 12", "100")));
        Medico medicoSaved = repository.save(medico);
        this.existingMedicoId = medicoSaved.getId();
    }
    @Test
    @DisplayName("Deve retornar código 201 (Created) ao cadastrar um médico")
    void cadastrarMedicoCenarioSucesso() throws Exception {

        //ARRANGE
        DadosCadastroMedicoDTO dadosCadastroMedicoDTO = new DadosCadastroMedicoDTO("Dr. Paulo Roberto", "paulo.roberto@voll.med",
                "31966665555", "987123", Especialidade.ORTOPEDIA,
                new DadosEnderecoDTO("Rua da Bahia", "Lourdes", "30160011", "Belo Horizonte",
                        "MG", "Sala 304", "1020"));

        //ACT
        var response = mockMvc.perform(
                post("/medicos")
                        .content(medicoDTOJacksonTester.write(dadosCadastroMedicoDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 (BadRequest) ao cadastrar um médico. Ex: Formato de CRM errado")
    void cadastrarMedicoCenarioErro() throws Exception {

        //ARRANGE
        DadosCadastroMedicoDTO dadosCadastroMedicoDTO = new DadosCadastroMedicoDTO("Dr. Paulo Roberto", "paulo.roberto@voll.med",
                "31966665555", "3217731266332", Especialidade.ORTOPEDIA,
                new DadosEnderecoDTO("Rua da Bahia", "Lourdes", "30160011", "Belo Horizonte",
                        "MG", "Sala 304", "1020"));

        //ACT
        var response = mockMvc.perform(
                post("/medicos")
                        .content(medicoDTOJacksonTester.write(dadosCadastroMedicoDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());

        String exceptionEsperada = "crm: Formato do CRM é inválido";
        var json = response.getContentAsString(StandardCharsets.UTF_8);
        String exceptionAtual = JsonPath.read(json, "$.error");

        Assertions.assertEquals(exceptionEsperada, exceptionAtual);
    }

    @Test
    @DisplayName("Deve retornar código 200 (Sucesso) ao listar todos os médicos")
    void listarMedicoCenarioSucesso() throws Exception {

        //ARRANGE


        //ACT
        var response = mockMvc.perform(
                get("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 (Sucesso) ao listar médico por Id")
    void listarPorIdMedicoCenarioSucesso() throws Exception {

        //ARRANGE

        //ACT
        var response = mockMvc.perform(
                get("/medicos/{id}", existingMedicoId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 (Not Found) ao listar médico por Id")
    void listarPorIdMedicoCenarioErro() throws Exception {

        //ARRANGE

        //ACT
        var response = mockMvc.perform(
                get("/medicos/{id}", noneExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(404, response.getStatus());

        String exceptionExperada = "Resource not found";
        var json = response.getContentAsString();
        String exceptionAtual = JsonPath.read(json, "$.error");
        Assertions.assertEquals(exceptionExperada, exceptionAtual);
    }

    @Test
    @DisplayName("Deve retornar código 200 (Sucesso) ao atualizar médico por Id")
    void atualizarPorIdMedicoCenarioSucesso() throws Exception {

        //ARRANGE
        DadosAtualizacaoMedicoDTO atualizacaoMedicoDTO = new DadosAtualizacaoMedicoDTO("Medico", "6193212-3213", null);

        //ACT
        var response = mockMvc.perform(
                put("/medicos/{id}", existingMedicoId)
                        .content(dadosAtualizacaoMedicoDTOJacksonTester.write(atualizacaoMedicoDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 (Not found) ao atualizar médico por Id")
    void atualizarPorIdMedicoCenarioErro() throws Exception {

        //ARRANGE
        DadosAtualizacaoMedicoDTO atualizacaoMedicoDTO = new DadosAtualizacaoMedicoDTO("Medico", "6193212-3213", null);

        //ACT
        var response = mockMvc.perform(
                put("/medicos/{id}", noneExistentId)
                        .content(dadosAtualizacaoMedicoDTOJacksonTester.write(atualizacaoMedicoDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(404, response.getStatus());

        String exceptionExperada = "Resource not found";
        var json = response.getContentAsString();
        String exceptionAtual = JsonPath.read(json, "$.error");
        Assertions.assertEquals(exceptionExperada, exceptionAtual);
    }

    @Test
    @DisplayName("Deve retornar código 204 (No Content) ao deletar médico por Id")
    void deletarPorIdMedicoCenarioSucesso() throws Exception {

        //ARRANGE

        //ACT
        var response = mockMvc.perform(
                delete("/medicos/{id}", existingMedicoId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(204, response.getStatus());
    }
}