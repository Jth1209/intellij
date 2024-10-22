package edu.du.sb1018;

import edu.du.sb1018.entity.Board;
import edu.du.sb1018.entity.Dept;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
class Sb1018ApplicationTests {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void contextLoads() {
    }

    @Test
    void 테스트(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Dept dept = em.find(Dept.class,10);
        System.out.println(dept);
        dept.setLoc("SEOUL");
        dept.setDname("연구소");

        em.getTransaction().commit();
    }

    @Test
    void 보드테스트(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Board> query = em.createQuery("select d from Board d", Board.class);
        List<Board> boards = query.getResultList();
        System.out.println(boards.size());

        em.getTransaction().commit();
    }
}
