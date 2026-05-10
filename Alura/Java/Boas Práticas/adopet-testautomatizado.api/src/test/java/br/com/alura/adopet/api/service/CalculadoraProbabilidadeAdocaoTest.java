package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculadoraProbabilidadeAdocaoTest {

    @Test
    @DisplayName("Probabilidade alta para gatos jovens e peso baixo")
    void verificarRetornoProbabilidadeAlta() {

        //ARRANGE
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Petz", "99999999991", "petz.abrigo@email.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Bob", "Siamês", 4, "Ouro", 4.0f), abrigo);

        CalculadoraProbabilidadeAdocao calc = new CalculadoraProbabilidadeAdocao();

        //Entrando no JUnit (Classe padrão Java para realizar testes automatizados)
        //ACT
        ProbabilidadeAdocao probabilidadeActual = calc.calcular(pet);
        ProbabilidadeAdocao probabilidadeExpected = ProbabilidadeAdocao.ALTA;

        //ASSERT
        Assertions.assertEquals(probabilidadeExpected, probabilidadeActual);
    }

    @Test
    @DisplayName("Probabilidade média para gatos idosos e peso baixo")
    void verificarRetornoProbabilidadeMedia(){

        //ARRANGE
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Petz", "99999999991", "petz.abrigo@email.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Bob", "Siamês", 15, "Branca", 4.0f), abrigo);

        CalculadoraProbabilidadeAdocao calc = new CalculadoraProbabilidadeAdocao();

        //Junit
        //ACT
        ProbabilidadeAdocao probabilidadeActual = calc.calcular(pet);
        ProbabilidadeAdocao probabilidadeExpected = ProbabilidadeAdocao.MEDIA;

        //ASSERT
        Assertions.assertEquals(probabilidadeExpected, probabilidadeActual);
    }

    @Test
    @DisplayName("Probabilidade baixa para cachorros idosos e peso alto")
    void verificarProbabilidadeBaixa(){

        //ARRANGE
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Petz", "99999999991", "petz.abrigo@email.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.CACHORRO, "Snoop", "Golden", 16, "Laranja", 18.0f), abrigo);
        CalculadoraProbabilidadeAdocao calc = new CalculadoraProbabilidadeAdocao();

        //ACT
        ProbabilidadeAdocao probabilidadeActual = calc.calcular(pet);
        ProbabilidadeAdocao probabilidadeExpected = ProbabilidadeAdocao.BAIXA;

        //ASSERT
        Assertions.assertEquals(probabilidadeExpected, probabilidadeActual);
    }
}