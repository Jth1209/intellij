package edu.du.sb1024.validation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePwdCommand {

	private String currentPassword;
	private String newPassword;

}
