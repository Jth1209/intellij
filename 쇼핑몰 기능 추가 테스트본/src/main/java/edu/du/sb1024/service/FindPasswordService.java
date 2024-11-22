package edu.du.sb1024.service;

import edu.du.sb1024.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
@RequiredArgsConstructor
public class FindPasswordService {
    private final EntityManagerFactory emf;

    public Long id(String email,String username){
        EntityManager em = emf.createEntityManager();
        Long id = em.createQuery("select m.id from Member m where m.email = :email and m.username = :username", Long.class).setParameter("email",email).setParameter("username",username).getSingleResult();
        em.close();
        return id;
    }
}
