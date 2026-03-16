package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean //Anotação do próprio Spring
    private AdocaoService service;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDto> dtoJackson;

    @Autowired
    private JacksonTester<AprovacaoAdocaoDto> aproDTOJackson;

    @Autowired
    private JacksonTester<ReprovacaoAdocaoDto> reproDTOJackson;

    @Test
    @DisplayName("Deve retornar código 400 ao solicitar adoção com erro")
    void verificaoDeErroSolicitar() throws Exception {

        //ARRANGE
        String json = "{}"; //Vazio = Inválido

        //ACT
        var response = mockMvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        //ASSERTIVE
        Assertions.assertEquals(400,response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 ao solicitar adoção com Sucesso!")
    void verificacaoDeSucessoSolicitacao() throws Exception {

        //ARRANGE
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l,1l, "Motivo Qualquer");

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/adocoes")
                        .content(dtoJackson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("Adoção solciitada com sucesso!", response.getContentAsString());
    }

    @Test
    @DisplayName("Deve retornar código 200 ao fazer aprovação da solicitação")
    void verificacaoDeSucessoAprovacao() throws Exception{

        //ARRANGE
        AprovacaoAdocaoDto dto = new AprovacaoAdocaoDto(10l);

        //ACT
        var response = mockMvc.perform(
                put("/adocoes/aprovar")
                        .content(aproDTOJackson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 ao reprovar uma solicitação")
    void verificaoDeSucessoReprovacao()throws Exception{

        //ARRANGE
        ReprovacaoAdocaoDto dto = new ReprovacaoAdocaoDto(20l, "Justificativa plausível");

        //ACT
        var response = mockMvc.perform(
                put("/adocoes/reprovar")
                        .content(reproDTOJackson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        
        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());
    }
}