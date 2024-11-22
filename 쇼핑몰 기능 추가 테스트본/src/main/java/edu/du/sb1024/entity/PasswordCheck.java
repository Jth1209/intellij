package edu.du.sb1024.entity;

import edu.du.sb1024.validation.PasswordMatch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@PasswordMatch
public class PasswordCheck {
    @NotBlank(message = "필수 정보입니다.")
    private String password;
    @NotBlank(message = "필수 정보입니다.")
    private String passwordConfirm;

}
