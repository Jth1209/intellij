package edu.du.sb1024.validation;


import edu.du.sb1024.entity.Member;
import edu.du.sb1024.spring.MemberRegisterService;
import edu.du.sb1024.spring.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdPasswordMatchValidator implements ConstraintValidator<IdPasswordMatch , LoginCommand> {

    @Autowired
    private MemberRegisterService memberRegisterService;


    @Override
    public boolean isValid(LoginCommand loginCommand, ConstraintValidatorContext context) {
        boolean isValid = memberRegisterService.idPasswordMatch(loginCommand.getEmail(),loginCommand.getPassword());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("아이디나 비밀번호를 다시 입력해주세요.")
                    .addPropertyNode("rememberEmail")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
