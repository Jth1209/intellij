package edu.du.sb1024.entity;

import edu.du.sb1024.validation.FindUserV;
import edu.du.sb1024.validation.PasswordMatch;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@FindUserV
public class UserInfo {
    @NotBlank(message = "필수 정보입니다")
    private String username;
    @NotBlank(message = "필수 정보입니다")
    private String email;
    private String errorCheck;
}
