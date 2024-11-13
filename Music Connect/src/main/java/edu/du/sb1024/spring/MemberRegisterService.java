package edu.du.sb1024.spring;

import edu.du.sb1024.encoder.PasswordEncoder;
import edu.du.sb1024.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberRegisterService {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EntityManagerFactory emf;

	public Long regist(RegisterRequest req) {
		Optional<Member> member = memberDao.selectByEmail(req.getEmail());
		if (member.isPresent()) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}

		Member newMember = Member.builder()
				.email(req.getEmail())
				.password(passwordEncoder.encrypt(req.getEmail(),req.getPassword()))
				.regdate(LocalDateTime.now())
				.role("USER")
				.username(req.getName())
				.nick(req.getNick())
				.build();
		System.out.println(newMember.getNick());
		memberDao.insert(newMember);
		System.out.println("====>" + newMember);
		return newMember.getId();
	}

	public int checkEmail(String email){
		Optional<Member> member = memberDao.selectByEmail(email);
		int count = 0;
		if (member.isPresent()){
			count = 1;
		}
		return count;
	}

	public int checkNick(String nick){
		EntityManager em = emf.createEntityManager();

		int count = em.createQuery("select m from Member m where m.nick = :nick",Member.class).setParameter("nick", nick).getResultList().size();
		return count;
	}

	public boolean idPasswordMatch(String email, String password){
		boolean count = false;
		EntityManager em = emf.createEntityManager();
		List<Member> mem = em.createQuery("select m from Member m where m.email=:email and m.password = :password",Member.class).setParameter("email", email).setParameter("password",passwordEncoder.encrypt(email,password)).getResultList();
		if(!mem.isEmpty()){
			count = true;
		}
		return count;
	}
}
