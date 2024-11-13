package edu.du.sb1024.service;

import edu.du.sb1024.encoder.PasswordEncoder;
import edu.du.sb1024.entity.AuthInfo;
import edu.du.sb1024.entity.Member;
import edu.du.sb1024.spring.MemberDao;
import edu.du.sb1024.spring.WrongIdPasswordException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Setter
@Service
public class AuthService {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public AuthService(MemberDao memberDao) {
		super();
		this.memberDao = memberDao;
	}
	
	public AuthService() {
		super();
	}

    public AuthInfo authenticate(String email, String password) {
		Optional<Member> member = memberDao.selectByEmail(email);
		if(member.isEmpty()) {
			throw new WrongIdPasswordException();
		}
		if(!passwordEncoder.encrypt(email,password).equals(member.get().getPassword())) {
			throw new WrongIdPasswordException();
		}
		return new AuthInfo(member.get().getId(),member.get().getEmail(),member.get().getPassword(),member.get().getUsername(),member.get().getNick(),member.get().getRole());
	}
}
