package edu.du.sb1023_prob.controller;

import java.util.Arrays;
import java.util.List;

import edu.du.sb1023_prob.entity.AnsweredData;
import edu.du.sb1023_prob.entity.Question;
import edu.du.sb1023_prob.entity.Respondent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Controller
public class SurveyController {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@GetMapping("/survey.do")
	public String form(Model model) {
		List<Question> questions = createQuestions();
		model.addAttribute("questions", questions);
		return "/survey/surveyForm";
	}

	private List<Question> createQuestions() {
		Question q1 = new Question("당신의 역할은 무엇입니까?",
				Arrays.asList("서버", "프론트", "풀스택"));
		Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?",
				Arrays.asList("이클립스", "인텔리J", "서브라임"));
		Question q3 = new Question("하고 싶은 말을 적어주세요.");
		return Arrays.asList(q1, q2, q3);
	}

	@PostMapping("/submitted.do")
	public String submit(@ModelAttribute("ansData") AnsweredData data) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		// Respondent를 먼저 저장
		Respondent r = new Respondent();
		r.setLocation(data.getRes().getLocation());
		r.setAge(data.getRes().getAge());
		em.persist(r);

		// AnsweredData를 생성하고 Respondent를 설정한 후 저장
		AnsweredData a = new AnsweredData();
		a.setRes(r); // 여기에 저장된 Respondent 객체를 설정
		a.setResult(data.getResult());
		em.persist(a);

		//
		em.getTransaction().commit();
		return "/survey/submitted";
	}

}
