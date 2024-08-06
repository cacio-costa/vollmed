package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    Page<Consulta> findAllByDataGreaterThan(LocalDateTime data, Pageable paginacao);

    @Query("""
           select new med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal(c.medico.nome, c.medico.crm, count(c))
             from Consulta c
           where strftime('%Y', datetime(c.data / 1000, 'unixepoch')) = :ano
             and strftime('%m', datetime(c.data / 1000, 'unixepoch')) = :mes
           group by c.medico.nome, c.medico.crm
           """)
    List<DadosRelatorioConsultaMensal> contarConsultasPorMedicoNoMes(String ano, String mes);

}
