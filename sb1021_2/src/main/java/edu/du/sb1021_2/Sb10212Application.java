package edu.du.sb1021_2;

import edu.du.sb1021_2.repository.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Date;

@SpringBootApplication
public class Sb10212Application {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public static void main(String[] args) {
        SpringApplication.run(Sb10212Application.class, args);
    }

    @PostConstruct
    public void init() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Member member = new Member();
        member.setName("user1");
        member.setEmail("user1");
        member.setPassword(PasswordEncoder().encode("1234"));
        member.setPasswordCheck(PasswordEncoder().encode("1234"));
        member.setRegisterDateTime(new Date());
        member.setRole("USER");
        em.persist(member);

        em.getTransaction().commit();
    }
    private PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
