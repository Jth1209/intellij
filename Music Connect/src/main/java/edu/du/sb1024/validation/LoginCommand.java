package edu.du.sb1024.validation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@IdPasswordMatch
public class LoginCommand {

    @NotBlank(message = "필수 정보입니다.")
    private String email;
    @NotBlank(message = "필수 정보입니다.")
    private String password;
    private String rememberEmail;

}
