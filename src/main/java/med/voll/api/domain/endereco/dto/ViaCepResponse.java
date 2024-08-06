package med.voll.api.domain.endereco.dto;

import lombok.Data;
import med.voll.api.domain.endereco.Endereco;

@Data
public class ViaCepResponse {
    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    // Método para construir Endereço a partir de ViaCepResponse
    public Endereco toEndereco() {
        return new Endereco(
            logradouro, bairro, cep, null, complemento, localidade, uf
        );
    }
}