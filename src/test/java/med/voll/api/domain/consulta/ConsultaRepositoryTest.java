package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import med.voll.api.domain.medico.MedicoRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@ComponentScan(basePackages = "med.voll.api")
public class ConsultaRepositoryTest {

    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Test
    public void contarConsultasPorMedicoNoMes() {
        Endereco endereco = new Endereco("Avenida X", "456", "Bairro Y", "Cidade Z", "Estado W", "87654-321", "Complemento B");

        Medico medico1 = new Medico(
                "Dr. João Silva",
                "joao.silva@example.com",
                "123456789",
                "CRM1234",
                Especialidade.CARDIOLOGIA,
                endereco
        );

        Medico medico2 = new Medico(
                "Dra. Maria Oliveira",
                "maria.oliveira@example.com",
                "987654321",
                "CRM5678",
                Especialidade.DERMATOLOGIA, endereco
        );

        Paciente paciente = new Paciente(
                "Carlos Souza",
                "carlos.souza@example.com",
                "123456789",
                "CPF123456789",
                endereco
        );

        Consulta consulta1 = new Consulta(
                medico1,
                paciente,
                LocalDateTime.of(2023, 10, 1, 10, 0)
        );

        Consulta consulta2 = new Consulta(
                medico1,
                paciente,
                LocalDateTime.of(2023, 10, 2, 11, 0)
        );

        Consulta consulta3 = new Consulta(
                medico2,
                paciente,
                LocalDateTime.of(2023, 10, 3, 12, 0)
        );

        Consulta consulta4 = new Consulta(
                medico2,
                paciente,
                LocalDateTime.of(2023, 10, 4, 13, 0)
        );

        Consulta consulta5 = new Consulta(
                medico2,
                paciente,
                LocalDateTime.of(2023, 10, 5, 14, 0)
        );

        // Save all entities
        medicoRepository.save(medico1);
        medicoRepository.save(medico2);

        pacienteRepository.save(paciente);

        consultaRepository.save(consulta1);
        consultaRepository.save(consulta2);
        consultaRepository.save(consulta3);
        consultaRepository.save(consulta4);
        consultaRepository.save(consulta5);

        List<DadosRelatorioConsultaMensal> relatorio = consultaRepository.contarConsultasPorMedicoNoMes("2023", "10");

        assertEquals(2, relatorio.size());

        DadosRelatorioConsultaMensal relatorioMedico1 = relatorio.stream()
                .filter(r -> r.nome().equals("Dr. João Silva"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Relatório do Dr. João Silva não encontrado"));

        DadosRelatorioConsultaMensal relatorioMedico2 = relatorio.stream()
                .filter(r -> r.nome().equals("Dra. Maria Oliveira"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Relatório da Dra. Maria Oliveira não encontrado"));

        assertEquals(2, relatorioMedico1.quantidadeConsultasNoMes());
        assertEquals(3, relatorioMedico2.quantidadeConsultasNoMes());


        // Assert
    }
}