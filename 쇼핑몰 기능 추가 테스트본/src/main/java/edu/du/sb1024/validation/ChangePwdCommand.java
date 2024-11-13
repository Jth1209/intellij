package edu.du.sb1024.validation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ChangePwdCommand {

	private String currentPassword;
	private String newPassword;

}
