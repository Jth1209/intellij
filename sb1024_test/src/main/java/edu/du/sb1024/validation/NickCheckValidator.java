package edu.du.sb1024.validation;

import edu.du.sb1024.spring.MemberRegisterService;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NickCheckValidator implements ConstraintValidator<NickCheck, String>{

    @Autowired
    MemberRegisterService memberRegisterService;

    @Override
    public boolean isValid(String nick, ConstraintValidatorContext context) {
        boolean isValid = false;
        if (nick == null || nick.isEmpty()) {
            return true; // Null 또는 빈 문자열인 경우는 다른 검증에서 처리
        }
        if(memberRegisterService.checkNick(nick) != 1){
            isValid = true;
        }
        return isValid; // 이메일이 존재하면 false 반환
    }
}
