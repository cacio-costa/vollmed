package med.voll.api.domain.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class TelefoneValidoValidator implements ConstraintValidator<TelefoneValido, String> {

    private static final List<String> DDD_VALIDOS = Arrays.asList(
            "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "21", "22", "24", "27", "28",
            "31", "32", "33", "34", "35", "37", "38",
            "41", "42", "43", "44", "45", "46",
            "47", "48", "49",
            "51", "53", "54", "55",
            "61", "62", "64", "65", "66", "67",
            "68", "69",
            "71", "73", "74", "75", "77",
            "79",
            "81", "82", "83", "84", "85", "86", "87", "88", "89",
            "91", "92", "93", "94", "95", "96", "97", "98", "99"
    );

    @Override
    public void initialize(TelefoneValido constraintAnnotation) {
    }

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        if (telefone == null || telefone.isBlank()) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        char firstChar = telefone.charAt(0);
        for (char c : telefone.toCharArray()) {
            if (c != firstChar) {
                break;
            }
            if (c == telefone.charAt(telefone.length() - 1)) {
                return false;
            }
        }

        // Verifica se o DDD é válido
        String ddd = telefone.substring(0, 2);
        if (!DDD_VALIDOS.contains(ddd)) {
            return false;
        }

        return true;
    }
}

