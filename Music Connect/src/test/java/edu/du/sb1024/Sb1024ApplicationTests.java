package edu.du.sb1024;

import edu.du.sb1024.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootTest
class Sb1024ApplicationTests {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        EntityManager em = emf.createEntityManager();
        List<Comment> com = em.createQuery("select c from Comment c", Comment.class).getResultList();
        com.removeIf(comments -> !comments.getBoard().getBoardIdx().equals(1));
        System.out.println(com);
    }
}
