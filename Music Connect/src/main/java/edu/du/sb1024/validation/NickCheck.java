package edu.du.sb1024.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NickCheckValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NickCheck {
    String message() default "이미 존재하는 닉네임입니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
