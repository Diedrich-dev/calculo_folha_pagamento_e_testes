package util.validacoes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @Retention(RUNTIME): Essa validação é apenas em tempo de execução;
 * @Target(): Define sobre quais estruturas de dados a anotação pode ser aplicada. Exemplo: ({FIELD}) A anotação apenas pode ser utilizada em fields, ou seja, em variáveis globais da classe;
 * @Constraint(validatedBy = DataClassValidator.class): Define que a classe DataClassValidator será responsável pela implementação da validação.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.TYPE_USE,
    ElementType.ANNOTATION_TYPE,
    ElementType.PARAMETER,
    ElementType.FIELD
})
@Constraint(validatedBy = CTPSValidator.class)
public @interface CTPS {
//  message: A mensagem emitida em caso de erro na validação;
    String message() default "CTPS inválida!";
//  groups: Para utilização de grupo de validações de Bean Validation;
    Class<?>[] groups() default {};
//  payloads: Para configurar o grau do erro de validação;
    Class<? extends Payload>[] payload() default {};
}