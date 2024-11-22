package edu.du.sb1024.validation;


import edu.du.sb1024.entity.UserInfo;
import edu.du.sb1024.spring.MemberRegisterService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FindUserValidator implements ConstraintValidator<FindUserV , UserInfo> {

    @Autowired
    private MemberRegisterService memberRegisterService;


    @Override
    public boolean isValid(UserInfo userInfo, ConstraintValidatorContext context) {
        boolean isValid = memberRegisterService.findUser(userInfo.getEmail(),userInfo.getUsername());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("입력하신 정보를 가진 계정이 존재하지 않습니다.")
                    .addPropertyNode("errorCheck")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
