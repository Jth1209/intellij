package edu.du.sb1024.spring;

import edu.du.sb1024.validation.EmailCheck;
import edu.du.sb1024.validation.NickCheck;
import edu.du.sb1024.validation.PasswordMatch;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Data
@PasswordMatch
public class RegisterRequest {

	@Email(message = "유효한 이메일 주소를 입력해주세요.")
	@NotBlank(message = "이메일은 필수입니다.")
	@EmailCheck
	private String email;
	@NotBlank
	@Size(min=6,message="비밀번호는 6자리 이상이어야 합니다.")
	private String password;
	@NotBlank(message = "비밀번호 확인은 필수입니다.")
	private String confirmPassword;
	@NotBlank(message = "사용자 이름은 필수입니다.")
	private String name;
	@NotBlank(message = "닉네임은 필수입니다.")
	@NickCheck
	private String nick;

    public boolean isPasswordEqualToConfirmPassword() {
		return password.equals(confirmPassword);
	}
}
