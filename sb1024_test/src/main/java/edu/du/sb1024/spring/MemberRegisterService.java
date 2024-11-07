package edu.du.sb1024.spring;

import edu.du.sb1024.encoder.PasswordEncoder;
import edu.du.sb1024.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
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

	public int idPasswordMatch(Member member){
		int count = 0;
		EntityManager em = emf.createEntityManager();
		Member mem = em.find(Member.class, member.getEmail());
		if(mem != null){
			if(mem.getPassword().equals(member.getPassword())){
				count = 1;
			}
		}
		return count;
	}
}
