package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
    @MockBean
    private PetService petService;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> abrigoDtoJacksonTester;
    @Autowired
    private JacksonTester<CadastroPetDto> petDtoJacksonTester;

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

    @Test
    @DisplayName("Deve retornar código 200 ao realizar o get dos Pets de determinado abrigo por nome")
    void verificacaoDeSucessoAoListarPetsDoAbrigoNome() throws Exception {

        //ARRANGE
        String nome = "Abrigo feliz";
        //ACT
        var response = mockMvc.perform(
                get("/abrigos/{nome}/pets", nome)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());

        String content = response.getContentAsString();
        Assertions.assertFalse(content.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar código 200 ao realizar o get dos Pets de determinado abrigo pelo Id")
    void verificacaoDeSucessoAoListarPetsDoAbrigoId() throws Exception {
        //ARRANGE
        String id = "10";

        //ACT
        var response = mockMvc.perform(
                get("/abrigos/{id}/pets", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 ao realizar o get, pois são objetos não encontrados")
    void verificacaoDeErroAoListarPetsDoAbrigoId() throws Exception{

        //ARRANGE
        String idInexstente = "999999";

        when(service.listarPetsDoAbrigo(idInexstente)).thenThrow(new ValidacaoException("Abrigo não encontrado"));

        //ACT
        var response = mockMvc.perform(
                get("/abrigos/{id}/pets", idInexstente)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 ao realizar cadastro de pet pelo abrigo passando o id")
    void verificacaoAoCadastrarPetPeloAbrigo() throws Exception{

        //ARRANGE
        CadastroPetDto dto = new CadastroPetDto(TipoPet.CACHORRO, "bob", "Golden", 10, "Loiro", 15.0f);
        String id = "9";

        //ACT
        var response = mockMvc.perform(
                post("/abrigos/{id}/pets", id)
                        .content(petDtoJacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());
    }
}