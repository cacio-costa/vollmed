package med.voll.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import med.voll.api.domain.consulta.AgendarConsultaService;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosListagemConsulta;
import med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/consultas")
@AllArgsConstructor
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final AgendarConsultaService consultaService;


    @PostMapping
    public ResponseEntity<DadosDetalhamentoConsulta> agendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        DadosDetalhamentoConsulta detalhamento = consultaService.agendarConsulta(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhamento);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsulta>> listarConsultas(
            @RequestParam(value = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data,
            @PageableDefault(sort = "data", direction = Sort.Direction.ASC) Pageable paginacao) {
        if (data == null) {
            data = LocalDateTime.now();
        }

        Page<Consulta> consultas = consultaRepository.findAllByDataGreaterThan(data, paginacao);
        Page<DadosListagemConsulta> consultaDTOs = consultas.map(DadosListagemConsulta::new);

        return ResponseEntity.ok(consultaDTOs);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<DadosRelatorioConsultaMensal>> gerarRelatorioConsultas(
            @RequestParam("anoMes") String anoMes) {
        String[] partes = anoMes.split("-");
        String ano = partes[0];
        String mes = partes[1];

        List<DadosRelatorioConsultaMensal> relatorio = consultaRepository.contarConsultasPorMedicoNoMes(ano, mes);
        return ResponseEntity.ok(relatorio);
    }
}
