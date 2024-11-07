package edu.du.sb1024.entity;

import lombok.Getter;

@Getter
public class AuthInfo {
	
	private Long id;
	private String email;
	private String password;
	private String name;
	private String nick;
	private String role;
	
	public AuthInfo(Long id, String email, String password , String name , String nick, String role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.nick = nick;
		this.role = role;
	}


}
