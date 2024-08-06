package med.voll.api.domain.paciente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.endereco.dto.DadosEndereco;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.validacao.TelefoneValido;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,

//        @TelefoneValido
        @NotBlank
        @Pattern(
                regexp = "\\(?(?:[14689][1-9]|[27][1-9])\\)? ?(?:9[1-9]\\d{3}|[2-8]\\d{3})-\\d{4}",
                message = "Formato de telefone inválido ou contém números repetidos"
        )
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
        String cpf,

        @NotNull @Valid DadosEndereco endereco) {

        public Paciente toEntity() {
                return new Paciente(nome, email, telefone, cpf, endereco.toModel());
        }
}
