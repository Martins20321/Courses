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

        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Petz", "99999999991", "petz.abrigo@email.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Bob", "Golden", 4, "Ouro", 4.0f), abrigo);

        CalculadoraProbabilidadeAdocao calc = new CalculadoraProbabilidadeAdocao();

        //Entrando no JUnit (Classe padrão Java para realizar testes automatizados)
        ProbabilidadeAdocao probabilidadeActual = calc.calcular(pet);
        ProbabilidadeAdocao probabilidadeExpected = ProbabilidadeAdocao.ALTA;

        Assertions.assertEquals(probabilidadeExpected, probabilidadeActual);
    }

    @Test
    @DisplayName("Probabilidade média para gatos idosos e peso baixo")
    void verificarRetornoProbabilidadeMedia(){

        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Petz", "99999999991", "petz.abrigo@email.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Bob", "Golden", 15, "Ouro", 4.0f), abrigo);

        CalculadoraProbabilidadeAdocao calc = new CalculadoraProbabilidadeAdocao();

        //Junit
        ProbabilidadeAdocao probabilidadeActual = calc.calcular(pet);
        ProbabilidadeAdocao probabilidadeExpected = ProbabilidadeAdocao.MEDIA;

        Assertions.assertEquals(probabilidadeExpected, probabilidadeActual);
    }
}