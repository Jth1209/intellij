package edu.du.sb1024.spring;

import edu.du.sb1024.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

	private final MemberDao memberDao;

//	@Transactional
	public void changePassword(String email, String oldPwd, String newPwd) {
		Optional<Member> member = memberDao.selectByEmail(email);
		if (member.isEmpty())
			throw new MemberNotFoundException();

		member.get().changePassword(oldPwd, newPwd);

		memberDao.update(member.get());
	}


}
