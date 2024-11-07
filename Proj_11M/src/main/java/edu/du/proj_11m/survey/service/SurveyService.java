package edu.du.proj_11m.survey.service;

import edu.du.proj_11m.survey.entity.AnsweredData;
import edu.du.proj_11m.survey.entity.Respondent;
import edu.du.proj_11m.userManagement.entity.AuthInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;

@Service
@Log4j2
public class SurveyService {
    @PersistenceUnit
    private EntityManagerFactory emf;

    public void save(AnsweredData data,String name) {
        // 트랜잭션 시작
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        log.info(data.toString());
            Respondent respondent = data.getRes();
            data.setUname(name);
            em.persist(respondent);
            em.persist(data);

        transaction.commit();
    }
}
