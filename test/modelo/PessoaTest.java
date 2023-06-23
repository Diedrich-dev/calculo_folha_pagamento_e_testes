package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import util.DateUtils;
import util.validacoes.CPF;

public class PessoaTest {

    private static ValidatorFactory factory;
    private static Validator validator;
    private static Pessoa pessoa;
    private static Set<ConstraintViolation<Pessoa>> violations;

    public PessoaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void instanciarModel() {
        pessoa = new Pessoa();
    }

    /**
     * Método que realiza as validações do objeto e retorna as violações que
     * ocorrem em um deteminado atributo (field).
     *
     * @param p variável do tipo Pessoa
     * @param field atributo a ser validado do objeto
     * @return
     */
    private Set<ConstraintViolation<Pessoa>> getViolationsField(Pessoa p, String field) {
        Set<ConstraintViolation<Pessoa>> violationsModel = validator.validate(p);
        return violationsModel.stream().filter(e -> e.getPropertyPath().toString().equalsIgnoreCase(field)).collect(Collectors.toSet());
    }

    @Test
    public void testarNomeEmBranco() {
        pessoa.setNome("    ");
        violations = getViolationsField(pessoa, "nome");
        assertTrue("validar @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("");
        violations = getViolationsField(pessoa, "nome");
        assertTrue("validar @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertTrue("validar @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome(null);
        violations = getViolationsField(pessoa, "nome");
        assertTrue("validar @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
    }

    @Test
    public void testarQuantidadeCaracteresNome() {

        pessoa.setNome("ed");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar 2 caracteres  @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertTrue("validar 2 caracteres @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar 2 caracteres @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("and");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar 3 caracteres @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar 3 caracteres  @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar 3 caracteres  @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("AndreLuizLuchesiAndreLuizLuchesiAndreLuizLuchesiAndreLuizLuchesiAndreLuizLuchesiAndreLuizLu");
        System.out.println("size: " + pessoa.getNome().length());
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar mais de 90 caracteres @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertTrue("validar mais de 90 caracteres @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar mais de 90 caracteres @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
    }

    @Test
    public void testarAcentuaçõesECaracteresEspeciais() {
        pessoa.setNome("áàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar acentuções válidas @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar acentuções válidas @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar acentuções válidas @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("Felipe D'Avila");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome com acentos apóstrofo @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome com acentos apóstrofo @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar nome com acentos apóstrofo @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("Brian O'Connor");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome com acento apóstrofo @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome com acento apóstrofo @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar nome com acento apóstrofo @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("Dom. João VI");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome com ponto @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome com ponto @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar nome com ponto @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("@ndr&");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar caracteres especiais @ndr& @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar caracteres especiais @ndr& @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar caracteres especiais @ndr& @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("Ca$$!o");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar caracteres especiais Ca$$!o @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar caracteres especiais Ca$$!o @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar caracteres especiais Ca$$!o @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("!@#$%^&*()`~<>,.?/+={}[]|:;");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar caracteres especiais @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar caracteres especiais @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar caracteres especiais @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

    }

    @Test
    public void testarNomeComNumeros() {
        pessoa.setNome("Luiz 14");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome com números @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome com números @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar nome com números @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("Dom Pedro 2");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome com o número 2 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome com o número 2 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar nome com o número 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("666");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome 666 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome 666 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar nome 666 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

    }

    @Test
    public void testarNomeValido() {
        pessoa.setNome("André Luiz");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome 1 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome 1 válidas @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar nome 1 válidas @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("Zé Eduardo Conceição Junior");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome 2 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome 2 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar nome 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("Dom. Pedro de Alcântara João Carlos");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome 3 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome 3 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar nome 3 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setNome("Pedro de Alcântara João Carlos Leopoldo Salvador Bibiano Francisco Xavier d Paula Leocádio");
        violations = getViolationsField(pessoa, "nome");
        assertFalse("validar nome com 90 caracteres @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar nome com 90 caracteres @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar nome com 90 caracteres @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

    }

    @Test
    public void testarCPFEmBranco() {
        pessoa.setCpf("    ");
        violations = getViolationsField(pessoa, "cpf");
        assertTrue("validar @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertTrue("validar @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("           ");
        violations = getViolationsField(pessoa, "cpf");
        assertTrue("validar 11 espaços em branco @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar 11 espaços em branco @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar 11 espaços em branco @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar 11 espaços em branco @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("");
        violations = getViolationsField(pessoa, "cpf");
        assertTrue("validar vazio @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertTrue("validar vazio@Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar vazio@Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar vazio @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf(null);
        violations = getViolationsField(pessoa, "cpf");
        assertTrue("validar null @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar null @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar null @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar null @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

    }

    @Test
    public void testarCPFcomCaracteresInvalidos() {
        pessoa.setCpf("605.124.010-XX");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar Caracteres Inválidos 1 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar Caracteres Inválidos 1 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar Caracteres Inválidos 1 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar Caracteres Inválidos 1 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("605.124.0!0-00");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar Caracteres Inválidos 2 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar Caracteres Inválidos 2 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar Caracteres Inválidos 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar Caracteres Inválidos 2 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("605 124.010-00");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar Caracteres Inválidos 3 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar Caracteres Inválidos 3 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar Caracteres Inválidos 3 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar Caracteres Inválidos 3 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("295.934.400 62");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar Caracteres Inválidos 4 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar Caracteres Inválidos 4 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar Caracteres Inválidos 4 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar Caracteres Inválidos 4 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("295,934.400-62");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar Caracteres Inválidos 5 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar Caracteres Inválidos 5 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar Caracteres Inválidos 5 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar Caracteres Inválidos 5 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("295.934.400.62");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar Caracteres Inválidos 6 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar Caracteres Inválidos 6 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertTrue("validar Caracteres Inválidos 6 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar Caracteres Inválidos 6 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

    }

    @Test
    public void testarCPFInvalidos(){
        pessoa.setCpf("511.221.620-45");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar CPF Inválidos 1 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar CPF Inválidos 1 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar CPF Inválidos 1 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar CPF Inválidos 1 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("63685510389");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar CPF Inválidos 2 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar CPF Inválidos 2 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar CPF Inválidos 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar CPF Inválidos 2 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("92733477960");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar CPF Inválidos 3 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar CPF Inválidos 3 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar CPF Inválidos 3 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar CPF Inválidos 3 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("02733477960");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar CPF Inválidos 4 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar CPF Inválidos 4 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar CPF Inválidos 4 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar CPF Inválidos 4 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("77893335500");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar CPF Inválidos 5 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar CPF Inválidos 5 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar CPF Inválidos 5 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertTrue("validar CPF Inválidos 5 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

    }

    @Test
    public void testarCPFValidos() {
        pessoa.setCpf("511.221.620-49");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar cpf 1 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar cpf 1 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar cpf 1 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar cpf 1 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("388.379.620-45");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar cpf 2 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar cpf 2 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar cpf 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar cpf 2 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("371.025.490-67");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar cpf 3 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar cpf 3 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar cpf 3 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar cpf 3 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("587.859.930-93");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar cpf 4 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar cpf 4 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar cpf 4 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar cpf 4 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("750.426.400-88");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar cpf 5 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar cpf 5 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar cpf 5 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar cpf 5 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("195484510-34");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar cpf 6 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar cpf 6 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar cpf 6 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar cpf 6 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("64912207020");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar cpf 7 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar cpf 7 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar cpf 7 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar cpf 7 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

        pessoa.setCpf("042.314.420-02");
        violations = getViolationsField(pessoa, "cpf");
        assertFalse("validar cpf 8 @NotBlank", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof NotBlank));
        assertFalse("validar cpf 8 @Size", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Size));
        assertFalse("validar cpf 8 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
        assertFalse("validar cpf 8 @CPF", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof CPF));

    }

    @Test
    public void testarEmailEmBranco(){
        pessoa.setEmail("");
        violations = getViolationsField(pessoa, "email");
        assertFalse("validar email vazio @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("     ");
        violations = getViolationsField(pessoa, "email");
        assertTrue("validar email em branco @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail(null);
        violations = getViolationsField(pessoa, "email");
        assertFalse("validar email nulo @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

    }

    @Test
    public void testarEmailInvalido(){
        pessoa.setEmail("email@");
        violations = getViolationsField(pessoa, "email");
        assertTrue("validar email inválido 1 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("joão@.gmail.com");
        violations = getViolationsField(pessoa, "email");
        assertTrue("validar email inválido 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("email.com.br");
        violations = getViolationsField(pessoa, "email");
        assertTrue("validar email inválido 3 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("*ana@gmail.com.br");
        violations = getViolationsField(pessoa, "email");
        assertTrue("validar email inválido 4 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("bruno@a.com.xyz");
        violations = getViolationsField(pessoa, "email");
        assertTrue("validar email inválido 5 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
    }

    @Test
    public void testarEmailValido(){
        pessoa.setEmail("eu123@univel.br");
        violations = getViolationsField(pessoa, "email");
        assertFalse("validar email válido 1 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("joao-gomes@gmail.com.br");
        violations = getViolationsField(pessoa, "email");
        assertFalse("validar email válido 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("1223_xxx@u.com");
        violations = getViolationsField(pessoa, "email");
        assertFalse("validar email válido 3 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("a@g.com");
        violations = getViolationsField(pessoa, "email");
        assertFalse("validar email válido 4 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setEmail("bruno.alve123@a.com.xy");
        violations = getViolationsField(pessoa, "email");
        assertFalse("validar email válido 5 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
    }

    @Test
    public void testarTelefoneEmBranco(){
        pessoa.setTelefone("");
        violations = getViolationsField(pessoa, "telefone");
        assertFalse("validar telefone vazio @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("     ");
        violations = getViolationsField(pessoa, "telefone");
        assertTrue("validar telefone em branco @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone(null);
        violations = getViolationsField(pessoa, "telefone");
        assertFalse("validar telefone nulo @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

    }

    @Test
    public void testarTelefoneInvalido(){
        pessoa.setTelefone("(000) 9999-1223");
        violations = getViolationsField(pessoa, "telefone");
        assertTrue("validar telefone inválido 1 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("(045) 29999-1223");
        violations = getViolationsField(pessoa, "telefone");
        assertTrue("validar telefone inválido 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("(045) 2999 1223");
        violations = getViolationsField(pessoa, "telefone");
        assertTrue("validar telefone inválido 3 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("(145)99999-1223");
        violations = getViolationsField(pessoa, "telefone");
        assertTrue("validar telefone inválido 4 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("x9999-1223");
        violations = getViolationsField(pessoa, "telefone");
        assertTrue("validar telefone inválido 5 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
    }

    @Test
    public void testarTelefoneValido(){
        pessoa.setTelefone("4532231223");
        violations = getViolationsField(pessoa, "telefone");
        assertFalse("validar telefone válido 1 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("(045) 99999-1223");
        violations = getViolationsField(pessoa, "telefone");
        assertFalse("validar telefone válido 2 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("45 2999-1223");
        violations = getViolationsField(pessoa, "telefone");
        assertFalse("validar telefone válido 3 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("(045)99999-1223");
        violations = getViolationsField(pessoa, "telefone");
        assertFalse("validar telefone válido 4 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));

        pessoa.setTelefone("2234-1223");
        violations = getViolationsField(pessoa, "telefone");
        assertFalse("validar telefone válido 5 @Pattern", violations.stream().anyMatch(v -> v.getConstraintDescriptor().getAnnotation() instanceof Pattern));
    }

}
