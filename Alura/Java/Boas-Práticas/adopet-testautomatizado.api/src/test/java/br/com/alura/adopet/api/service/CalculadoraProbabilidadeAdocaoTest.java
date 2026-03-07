package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculadoraProbabilidadeAdocaoTest {

    @Test
    void cenario01() {
        //idade 4 anos, peso 4 kilos = Probabilidade alta

        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo Petz", "99999999991", "petz.abrigo@email.com"));
        Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Bob", "Golden", 4, "Ouro", 4.0f), abrigo);

        CalculadoraProbabilidadeAdocao calc = new CalculadoraProbabilidadeAdocao();
        
        //Entrando no JUnit
        ProbabilidadeAdocao probabilidadeActual = calc.calcular(pet);
        ProbabilidadeAdocao probabilidadeExpected = ProbabilidadeAdocao.ALTA;

        Assertions.assertEquals(probabilidadeExpected, probabilidadeActual);
    }
}