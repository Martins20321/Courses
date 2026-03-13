package br.com.alura.adopet.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}