package ua.softserve.bandr.validation;

import javax.ejb.Stateless;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Created by bandr on 26.01.2016.
 */
@Stateless
public class EntityValidator {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public <T> ValidationResult validate(T entity){
        return new ValidationResult(validator.validate(entity));
    }
}
