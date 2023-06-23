package util.validacoes;

import java.util.InputMismatchException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Andre
 */
public class ValidatorCPF implements ConstraintValidator<CPF, Object> {

    /**
     * A classe de validação implementa ConstraintValidator e precisa
     * sobrescrever o método isValid, que o método que vai considerar se o valor
     * recebido através do parâmetro value é valido ou não.
     * @param value propriedade que recebe o valor do atributo.
     * @param context
     * @return Retorna válido se o valor for nulo ou vazio ou se for um CPF válido!
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value == null || value.toString().isEmpty() || cpfValido(value.toString());
    }

    /**
     * Método para verificar se o número do CPF é válido!
     *
     * @param cpf
     * @return Retorna true se CPF é for válido, caso contrário retorna false
     */
    public boolean cpfValido(String cpf) {
        cpf = cpf.replaceAll("\\.|-", "");  

        // considera-se erro CPF's formados por uma sequencia de números iguais
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999") || (cpf.length() != 11)) {
            return (false);
        }

        char digito10, digito11;
        int soma;
        int resultado;
        int num;
        int peso;

        try {

            // Calculo do 1º Digito Verificador
            soma = 0;
            peso = 10;
            for (int i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                soma = soma + (num * peso);
                peso = peso - 1;
            }

            resultado = 11 - (soma % 11);
            if ((resultado == 10) || (resultado == 11)) {
                digito10 = '0';
            } else {
                digito10 = (char) (resultado + 48); // converte no respectivo caractere numerico
            }

            // Calculo do 2º Digito Verificador
            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                soma = soma + (num * peso);
                peso = peso - 1;
            }

            resultado = 11 - (soma % 11);
            if ((resultado == 10) || (resultado == 11)) {
                digito11 = '0';
            } else {
                digito11 = (char) (resultado + 48);
            }

            // Verifica se os dígitos verificadores calculados conferem com os dígitos informados.
            if ((digito10 == cpf.charAt(9)) && (digito11 == cpf.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }
}
