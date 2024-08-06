package med.voll.api.domain.consulta;

import lombok.AllArgsConstructor;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AgendarConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorAgendamentoConsulta> validadores;

    public DadosDetalhamentoConsulta agendarConsulta(DadosAgendamentoConsulta dados) {
        validadores.forEach(validador -> validador.validar(dados));

        Medico medico = medicoRepository.findById(dados.idMedico())
                .orElseThrow(() -> new ValidacaoException("Médico não encontrado"));

        Paciente paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new ValidacaoException("Paciente não encontrado"));

        Consulta consulta = consultaRepository.save(new Consulta(medico, paciente, dados.data()));

        return new DadosDetalhamentoConsulta(consulta);
    }

}
