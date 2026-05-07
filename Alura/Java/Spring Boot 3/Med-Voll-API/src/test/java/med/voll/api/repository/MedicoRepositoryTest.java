package med.voll.api.repository;

import med.voll.api.domain.Consulta;
import med.voll.api.domain.Medico;
import med.voll.api.domain.Paciente;
import med.voll.api.domain.enums.Especialidade;
import med.voll.api.dto.DadosCadastroMedicoDTO;
import med.voll.api.dto.DadosCadastroPacienteDTO;
import med.voll.api.dto.DadosEnderecoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Utilizando o banco de dados original
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Deveria retornar null quando unico médico cadastrado não está disponível na data")
    void findRandomlyCenario1() {

        //ARRANGE
        //Marcando consulta para próxima segunda
        var proximaSegunda = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        Medico medico = cadastrarMedico("Medico", "medico@email", "213456", Especialidade.CARDIOLOGIA);
        Paciente paciente = cadastrarPaciente("paciente", "paciente@email", "12345678910");
        cadastrarConsulta(medico, paciente, proximaSegunda);

        //ACT
        var medicoLivre = repository.findRandomly(Especialidade.CARDIOLOGIA, proximaSegunda);
        //ASSERT
        //Só possui um médico no banco e ele tem consulta na próxima segunda
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria retornar médico quando ele estiver disponível na data")
    void findRandomlyCenario2() {

        //ARRANGE
        //Marcando consulta para próxima segunda
        var proximaSegunda = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        Medico medico = cadastrarMedico("Medico", "medico@email", "213456", Especialidade.CARDIOLOGIA);
        Paciente paciente = cadastrarPaciente("paciente", "paciente@email", "12345678910");
        cadastrarConsulta(medico, paciente, proximaSegunda);

        //ACT
        //Data livre para aquele médico
        var proximaTerca = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).atTime(14,00);
        var medicoLivre = repository.findRandomly(Especialidade.CARDIOLOGIA, proximaTerca);

        //ASSERT
        //Deve ser selecionado o médico livre
        assertThat(medicoLivre).isEqualTo(medico);
        assertThat(medicoLivre).isNotNull();
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        testEntityManager.persist(new Consulta(null, medico, paciente, data, null, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        testEntityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        testEntityManager.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedicoDTO dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedicoDTO(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPacienteDTO dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPacienteDTO(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEnderecoDTO dadosEndereco() {
        return new DadosEnderecoDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}