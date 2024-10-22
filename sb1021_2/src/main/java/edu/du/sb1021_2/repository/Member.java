package edu.du.sb1021_2.repository;

import edu.du.sb1021_2.service.WrongIdPasswordException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private String name;
	private Date registerDateTime;
	private String passwordCheck;
	private String role;

	public Member(String email, String password, 
			String name, Date regDateTime, String role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.registerDateTime = regDateTime;
		this.role = role;
	}

	public void changePassword(String oldPassword, String newPassword) {
		if (!password.equals(oldPassword))
			throw new WrongIdPasswordException();
		this.password = newPassword;
	}

}
