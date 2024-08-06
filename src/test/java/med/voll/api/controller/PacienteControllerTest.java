package med.voll.api.controller;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.endereco.dto.DadosEndereco;
import med.voll.api.domain.paciente.CadastroPacienteService;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.dto.DadosCadastroPaciente;
import med.voll.api.domain.paciente.dto.DadosDetalhamentoPaciente;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacienteRepository repository;

    @Test
    void testCadastrar() throws Exception {
        // Dados de entrada para o cadastro do paciente
        String pacienteJson = """
                    {
                        "nome": "João Silva",
                        "email": "joao.silva@example.com",
                        "cpf": "12345678900",
                        "telefone": "(11) 98448-1019",
                        "endereco": {
                            "logradouro": "Rua Exemplo",
                            "numero": "123",
                            "complemento": "Apto 1",
                            "bairro": "Centro",
                            "cidade": "São Paulo",
                            "uf": "SP",
                            "cep": "01001000"
                        }
                    }
                """;

        // Dados esperados de retorno após o cadastro
        DadosDetalhamentoPaciente dadosDetalhamento = new DadosDetalhamentoPaciente(
                1L, true, "João Silva", "joao.silva@example.com", "12345678900", "(11) 98448-1019",
                new Endereco("Rua Exemplo", "Centro", "01001000", "123", "Apto 1", "São Paulo", "SP")
        );

        // Realizar a requisição POST e verificar a resposta
        mockMvc.perform(post("/pacientes")
                        .contentType("application/json")
                        .content(pacienteJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(header().string("Location", "http://localhost/pacientes/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@example.com"))
                .andExpect(jsonPath("$.cpf").value("12345678900"))
                .andExpect(jsonPath("$.telefone").value("(11) 98448-1019"))
                .andExpect(jsonPath("$.endereco.logradouro").value("Rua Exemplo"))
                .andExpect(jsonPath("$.endereco.numero").value("123"))
                .andExpect(jsonPath("$.endereco.complemento").value("Apto 1"))
                .andExpect(jsonPath("$.endereco.bairro").value("Centro"))
                .andExpect(jsonPath("$.endereco.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.endereco.uf").value("SP"))
                .andExpect(jsonPath("$.endereco.cep").value("01001000"));
    }


    @Test
    void testListar() throws Exception {
        // Dados de entrada para a listagem de pacientes
        var paciente1 = new Paciente("João Silva", "joao.silva@example.com", "(11) 98448-1019", "12345678900",
                new Endereco("Rua Exemplo", "123", "Apto 1", "Centro", "São Paulo", "SP", "01001000"));
        var paciente2 = new Paciente("Maria Oliveira", "maria.oliveira@example.com", "(21) 98765-4321", "98765432100",
                new Endereco("Avenida Brasil", "456", "Casa", "Bairro Alto", "Rio de Janeiro", "RJ", "22041001"));

        // Salvar os pacientes no repositório
        repository.save(paciente1);
        repository.save(paciente2);

        // Realizar a requisição GET e verificar a resposta
        mockMvc.perform(get("/pacientes")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(paciente1.getId()))
                .andExpect(jsonPath("$.content[0].ativo").value(paciente1.getAtivo()))
                .andExpect(jsonPath("$.content[0].nome").value(paciente1.getNome()))
                .andExpect(jsonPath("$.content[0].email").value(paciente1.getEmail()))
                .andExpect(jsonPath("$.content[0].cpf").value(paciente1.getCpf()))
                .andExpect(jsonPath("$.content[1].id").value(paciente2.getId()))
                .andExpect(jsonPath("$.content[1].ativo").value(paciente2.getAtivo()))
                .andExpect(jsonPath("$.content[1].nome").value(paciente2.getNome()))
                .andExpect(jsonPath("$.content[1].email").value(paciente2.getEmail()))
                .andExpect(jsonPath("$.content[1].cpf").value(paciente2.getCpf()));
    }
}