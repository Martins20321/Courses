package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.service.AbrigoService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbrigoService service;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> abrigoDtoJacksonTester;

    @Test
    @DisplayName("Deve retornar código 200 ao fazer o get de todos os abrigos listados")
    void verficacaoDeSucessoAoRetornarAbrigos() throws Exception{

        //ARRANGE -- Get de Listagem

        //ACT
        var response = mockMvc.perform(
                get("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());

        //Verificando se o conteúdo não está vazio
        String content = response.getContentAsString();
        Assertions.assertFalse(content.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar código 200 (Sucesso) ao realizar cadastro de Abrigo")
    void verificacaoDeSucessoAoCadastrarAbrigo() throws Exception {

        //ARRANGE
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Pet+", "(61)91234-8214", "petmais@email.com");

        //ACT
        var response = mockMvc.perform(
                post("/abrigos")
                        .content(abrigoDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 ao realizar cadastro de Abrigo. Ex: Sem inserção de Informações")
    void verificacaoDeErroAoCadastrarAbrigo() throws Exception {

        //ARRANGE
        CadastroAbrigoDto dto = new CadastroAbrigoDto(null, null, null);

        //ACT
        var response = mockMvc.perform(
                post("/abrigos")
                        .content(abrigoDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(400, response.getStatus());
    }
}