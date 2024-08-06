package med.voll.api.domain.endereco;

import med.voll.api.domain.endereco.dto.ViaCepResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsultaEnderecoService {

    private String viaCepServer;

    @Autowired
    public ConsultaEnderecoService(@Value("${viacep.server}") String viaCepServer) {
        this.viaCepServer = viaCepServer;
    }

    public Endereco consultarEnderecoPorCep(String cep) {
        String url = viaCepServer + "/ws/" + cep.replace("-", "") + "/json/";

        RestTemplate restTemplate = new RestTemplate();
        ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class, cep);

        return response.toEndereco();
    }
}