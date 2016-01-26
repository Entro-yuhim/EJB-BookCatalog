package ua.softserve.bandr.validation;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Created by bandr on 26.01.2016.
 */
public class ValidationResult<T> {


    public ValidationResult(Set<ConstraintViolation<T>> validate) {
        constraintFailures = validate;
    }

    private Set<ConstraintViolation<T>> constraintFailures;

    public String getErrorMessage() {
        String result = "";
        for (ConstraintViolation violation : constraintFailures) {
            result += violation.getMessage() + " ";
        }
        return result;
    }

    public boolean isValid(){
        return constraintFailures.isEmpty();
    }


}
