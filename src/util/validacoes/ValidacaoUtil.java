/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.validacoes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author andre
 * @param <T>
 */
public class ValidacaoUtil<T> {

    private final Class<T> entityClass;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public ValidacaoUtil(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public boolean validarEntidade(T entity) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return validarEntidade(entity, true);
    }

    public boolean validarEntidade(T entity, boolean validarEntidadeFilhas) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (validarEntidadeFilhas) {
            for (Field declaredField : entity.getClass().getDeclaredFields()) {
                if (declaredField.getType().getName().startsWith("modelo")) {
//                System.out.println("field: " + declaredField.getName() + " tipo: " + declaredField.getType().getName());
                    String metodo = "get" + declaredField.getName().substring(0, 1).toUpperCase() + declaredField.getName().substring(1);
                    Method met = entity.getClass().getMethod(metodo);
                    Object subClasse = met.invoke(entity);
                    if (subClasse != null) {
                        for (ConstraintViolation<Object> violation : validator.validate(subClasse)) {
                            constraintViolations.add((ConstraintViolation<T>) violation);
                        }
                    }
                }
            }
        }

        if (constraintViolations.isEmpty()) {
            return true;
        } else {
            String msgValidacao = gerarMsgValidacao(constraintViolations);
            JOptionPane.showMessageDialog(null, msgValidacao, "Falha na Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    private String gerarMsgValidacao(Set<ConstraintViolation<T>> constraintViolations) {
        String msgValidacao = "";
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            msgValidacao += "\n" + constraintViolation.getMessage();
        }
        return msgValidacao;
    }

    public void msgErroCadastro(Exception e, String operacao) {
        String tituloErroUser = "Falha ao " + operacao + " " + entityClass.getSimpleName();
        String msgErroUser = tituloErroUser;
        if (e instanceof javax.validation.ConstraintViolationException) {
            javax.validation.ConstraintViolationException erros = (javax.validation.ConstraintViolationException) e;
            for (ConstraintViolation<?> constraintViolation : erros.getConstraintViolations()) {
                msgErroUser += "\n" + constraintViolation.getMessage();
            }
        } else {
            msgErroUser += "\n" + e.getMessage();
        }

        JOptionPane.showMessageDialog(null, msgErroUser, tituloErroUser, JOptionPane.ERROR_MESSAGE);
    }

    public static void msgAviso(String tituloMsg, String msg) {
        JOptionPane.showMessageDialog(null, msg + "\n", tituloMsg, JOptionPane.WARNING_MESSAGE);
    }

    public static void msgAviso(String tituloMsg, String msg, Exception e) {
        JOptionPane.showMessageDialog(null, msg + "\n" + e.getMessage(), tituloMsg, JOptionPane.WARNING_MESSAGE);
    }
}
