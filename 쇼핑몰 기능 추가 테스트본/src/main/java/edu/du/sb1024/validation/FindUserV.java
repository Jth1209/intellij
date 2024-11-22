package edu.du.sb1024.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FindUserValidator.class)
@Target({ ElementType.FIELD , ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FindUserV {
    String message() default " ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
