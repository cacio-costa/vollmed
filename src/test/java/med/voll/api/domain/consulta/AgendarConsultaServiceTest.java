package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AgendarConsultaServiceTest {


    @Test
    void agendarConsulta() {
        // Arrange
        Medico expectedMedico = new Medico();
        Paciente expectedPaciente = new Paciente();
        LocalDateTime dataDaConsulta = LocalDateTime.of(2021, 10, 10, 0, 0);
        Consulta expectedConsulta = new Consulta(expectedMedico, expectedPaciente, dataDaConsulta);

        ConsultaRepository consultaRepository = Mockito.mock(ConsultaRepository.class);
        Mockito.when(consultaRepository.save(Mockito.any(Consulta.class))).thenReturn(expectedConsulta);

        MedicoRepository medicoRepository = Mockito.mock(MedicoRepository.class);
        Mockito.when(medicoRepository.findById(1L)).thenReturn(java.util.Optional.of(expectedMedico));

        PacienteRepository pacienteRepository = Mockito.mock(PacienteRepository.class);
        Mockito.when(pacienteRepository.findById(1L)).thenReturn(java.util.Optional.of(expectedPaciente));

        List<ValidadorAgendamentoConsulta> validadores = new ArrayList<>();

        AgendarConsultaService agendarConsultaService = new AgendarConsultaService(consultaRepository, medicoRepository, pacienteRepository, validadores);
        DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(1L, 1L, dataDaConsulta, Especialidade.CARDIOLOGIA);

        // Act
        DadosDetalhamentoConsulta detalhamentoConsulta = agendarConsultaService.agendarConsulta(dados);

        // Assert
        assertNotNull(detalhamentoConsulta);
    }

}