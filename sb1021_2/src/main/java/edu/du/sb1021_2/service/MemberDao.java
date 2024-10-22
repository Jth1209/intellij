package edu.du.sb1021_2.service;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import edu.du.sb1021_2.repository.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberDao implements MemberRepository{

	@PersistenceUnit//Unit을 써서 EntityManagerFactory를 생성해야 한다. 이 빡대가리 련아. PersistenceContext가 EntityManager 생성할 때 쓰는거고. 그냥 이렇게 외우셈. 이유가 없음;
	private EntityManagerFactory emf;

	public Member selectByEmail(String email) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		String q = "select m from Member m where m.email = :email";

		List<Member> results = em.createQuery(q, Member.class).setParameter("email", email).getResultList();

		em.getTransaction().commit();

		return results.isEmpty() ? null : results.get(0);
	}

	public void insert(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Member m = new Member();
		m.setEmail(member.getEmail());
		m.setName(member.getName());
		m.setPassword(PasswordEncoder().encode(member.getPassword()));
		m.setPasswordCheck(PasswordEncoder().encode(member.getPassword()));
		m.setRegisterDateTime(new Date());
		m.setRole("USER");
		em.persist(m);

		em.getTransaction().commit();
	}

	public void update(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Member m = em.find(Member.class,member.getId());
		m.setEmail(member.getEmail());
		m.setName(member.getName());
		m.setPassword(member.getPassword());
		m.setRegisterDateTime(new Date());

		em.persist(m);

		em.getTransaction().commit();
	}

	public List<Member> selectAll() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Member> q = em.createQuery("select m from Member m",Member.class);
		List<Member> members = q.getResultList();
		em.getTransaction().commit();
		return members.isEmpty() ? null : members;
	}

	public int userCount() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String query = "select count(m) from Member m";
		TypedQuery<Long> q = em.createQuery(query, Long.class);
		int count = Integer.parseInt(q.getSingleResult().toString());
		em.getTransaction().commit();
		return count;
	}
	private PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}