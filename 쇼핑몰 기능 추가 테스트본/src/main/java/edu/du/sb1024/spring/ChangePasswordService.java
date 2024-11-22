package edu.du.sb1024.spring;

import edu.du.sb1024.encoder.PasswordEncoder;
import edu.du.sb1024.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

	private final MemberDao memberDao;
	private final PasswordEncoder passwordEncoder;
	private final EntityManagerFactory emf;

//	@Transactional
	public void changePassword(String email, String oldPwd, String newPwd) {
		Optional<Member> member = memberDao.selectByEmail(email);
		if (member.isEmpty())
			throw new MemberNotFoundException();

		member.get().changePassword(oldPwd, newPwd);

		memberDao.update(member.get());
	}

	public void changePassword2(String email,String password){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Member member = em.createQuery("select m from Member m where m.email = :email",Member.class).setParameter("email",email).getSingleResult();
		member.setPassword(passwordEncoder.encrypt(email,password));
		em.merge(member);

		em.getTransaction().commit();
	}


}
