package util.validacoes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CTPSValidator implements ConstraintValidator<CTPS, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value == null || value.toString().isEmpty() || validarCtps(value.toString());
    }

    public boolean validarCtps(String ctps) {

        //remover pontos e traços se houver
        ctps = ctps.replaceAll("\\.|-", "");

        // Verificar se a CTPS possui o número de caracteres correto
        if (ctps.length() != 7 && ctps.length() != 8) {
            return false;
        }

        // Verificar se a CTPS possui apenas dígitos repetidos
        if (ctps.matches("^(\\d)\\1*$")) {
            return false;
        }

        // Verificar se os dois últimos dígitos estão corretos 
        int[] multiplicadores = {9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        int digitoVerificador = Integer.parseInt(ctps.substring(ctps.length() - 1));
        int numeroSemDigito = Integer.parseInt(ctps.substring(0, ctps.length() - 1));
        int restoDivisao;

        for (int i = 0; i < multiplicadores.length; i++) {
            soma += Integer.parseInt(String.valueOf(numeroSemDigito).substring(i, i + 1)) * multiplicadores[i];
        }

        restoDivisao = soma % 11;
        if (restoDivisao == 10) {
            restoDivisao = 0;
        }

        return restoDivisao == digitoVerificador;
    }
}
