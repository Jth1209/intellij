package edu.du.sb1024.validation;


import edu.du.sb1024.entity.PasswordCheck;
import edu.du.sb1024.entity.UserInfo;
import edu.du.sb1024.spring.RegisterRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator2 implements ConstraintValidator<PasswordMatch , PasswordCheck> {

    @Override
    public boolean isValid(PasswordCheck pc, ConstraintValidatorContext context) {
        boolean isValid = pc.getPassword().equals(pc.getPasswordConfirm());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("입력하신 비밀번호와 확인된 비밀번호가 서로 다릅니다.")
                    .addPropertyNode("passwordConfirm")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
