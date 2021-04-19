package br.com.contmatic.empresav2.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringStartsWith;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * @author Pedro
 *
 */
public interface TestesUtil extends TestesModel, TestesLogger {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    default public ExpectedException constrainViolationEsperada(ExpectedException thrown, String contextoEsperado) {

        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage(StringStartsWith.startsWith(CONSTRAINT_VIOLATED));
        thrown.expectMessage(StringContains.containsString(contextoEsperado));

        return thrown;
    }

    default public ExpectedException constrainViolationEsperada(ExpectedException thrown, String contextoEsperado1, String contextoEsperado2) {

        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage(StringStartsWith.startsWith(CONSTRAINT_VIOLATED));
        thrown.expectMessage(StringContains.containsString(contextoEsperado1));
        thrown.expectMessage(StringContains.containsString(contextoEsperado2));

        return thrown;
    }
}
