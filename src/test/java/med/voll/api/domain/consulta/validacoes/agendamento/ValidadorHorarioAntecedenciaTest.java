package med.voll.api.domain.consulta.validacoes.agendamento;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecedenciaTest {

    private final ValidadorHorarioAntecedencia validador = new ValidadorHorarioAntecedencia();

    @Test
    void deveLancarExcecaoSeConsultaForMenosDe30Minutos() {
        // Arrange
        var dados = new DadosAgendamentoConsulta(
                1L,
                1L,
                LocalDateTime.now().plusMinutes(20),
                null // Ou você pode passar uma Especialidade válida, dependendo do seu caso de uso
        );

        // Act & Assert
        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }

    @Test
    void naoDeveLancarExcecaoSeConsultaForMaisDe30Minutos() {
        // Arrange
        var dados = new DadosAgendamentoConsulta(
                1L,
                1L,
                LocalDateTime.now().plusMinutes(40),
                null // Ou você pode passar uma Especialidade válida, dependendo do seu caso de uso
        );

        // Act & Assert
        validador.validar(dados);
    }

    @Test
    void naoDeveLancarExcecaoSeConsultaForExatamente30Minutos() {
        // Arrange
        var dados = new DadosAgendamentoConsulta(
                1L,
                1L,
                LocalDateTime.now().plusMinutes(30).plusSeconds(1),
                null // Ou você pode passar uma Especialidade válida, dependendo do seu caso de uso
        );

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(dados));
    }
}
