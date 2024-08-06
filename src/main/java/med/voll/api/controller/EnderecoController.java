package med.voll.api.controller;

import med.voll.api.domain.endereco.ConsultaEnderecoService;
import med.voll.api.domain.endereco.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private ConsultaEnderecoService consultaEnderecoService;

    @GetMapping("/{cep}")
    public Endereco consultarEndereco(@PathVariable String cep) {
        return consultaEnderecoService.consultarEnderecoPorCep(cep);
    }
}