package br.com.contmatic.empresav2.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.message.ExitMessage;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Pedro
 *
 */
public interface Validacao {                

    /**
     * Método de auxílio que verifica se alguma restrição foi infringída durante o processo de criação de um objeto.
     *
     *
     * @throws ConstraintViolationException Caso alguma restrição tenha sido violada
     */
    public default Object verificaConstrain(Object classe) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> violacoes = validator.validate(classe);        

        try {
            checkArgument(violacoes.isEmpty()); return classe;
                        
        } catch (IllegalArgumentException e) { // Fiz dessa forma para poder lançar uma ConstrainViolationException.         
            throw new ConstraintViolationException("Restrições foram violadas.\n" +
                    retornaConstrains(violacoes) + "O Contato não foi salvo", violacoes);
        }
    }

    /**
     * Método interno que recebe a lista de violações críadas por um objeto. Constroí um 
     * StringBuilder contendo todas as constrains, e retorna o mesmo StringBuilder 
     *
     * @param infracoes - A lista contendo todas as infrações verificadas pelo validator.
     */
    public static StringBuilder retornaConstrains(Set<ConstraintViolation<Object>> infracoes) {
        StringBuilder violacoes = new StringBuilder();
        
        for(ConstraintViolation<Object> error : infracoes) {
            violacoes.append(String.format("atributo: [%s], valor: [%s], message: [%s]%n", error.getPropertyPath(), error.getInvalidValue(), error.getMessage()));
        }
        return violacoes;
    }
}
