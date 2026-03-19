package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService servive;

    @Test
    void verificacaoDeSucessoAoListarTodosOsPetsDisponíveis() throws Exception{

        //ARRANGE

        //ACT
        var response = mockMvc.perform(
                get("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        
        //ASSERTIVE
        Assertions.assertEquals(200, response.getStatus());
    }
}