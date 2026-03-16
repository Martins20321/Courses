package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorService service;

    @Autowired
    private JacksonTester<CadastroTutorDto> cadastroDTOJackson;

    @Test
    @DisplayName("Deve retornar código 200 ao fazer o método cadastrar de tutor")
    void verificaoDeSucessoAoCadastrarTutor() throws Exception {

        //ARRANGE
        CadastroTutorDto dto = new CadastroTutorDto("Ricardo", "(61)91234-6789", "ricardo@email.com");

        //ACT
        var response = mockMvc.perform(
                post("/tutores")
                        .content(cadastroDTOJackson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 ao fazer cadastro errado do tutor. Ex: Número sem seguir o padrão")
    void verificacaoDeErroAoCadastrarTutor() throws Exception {

        //ARRANGE
        CadastroTutorDto dto = new CadastroTutorDto("Pedro João", "8323123213213", "pedro@email.com");

        //ACT
        var response = mockMvc.perform(
                post("/tutores")
                        .content(cadastroDTOJackson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(400, response.getStatus());
    }

    
}