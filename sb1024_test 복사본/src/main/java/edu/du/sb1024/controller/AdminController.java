package edu.du.sb1024.controller;

import edu.du.sb1024.entity.Member;
import edu.du.sb1024.entity.Shipment;
import edu.du.sb1024.event.OrderEventPublisher;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@lombok.extern.slf4j.Slf4j
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    final EntityManagerFactory emf;

    private final OrderEventPublisher oep;

    @GetMapping("/admin")//권한이 유저인(실제 사용자)의 정보를 모두 확인할 수 있음. 아마 생성 순서(id 순서)에 따라 호출
    public String admin(Model model) {
        EntityManager em = emf.createEntityManager();
        List<Member> members = em.createQuery("select m from Member m where m.role = :role order by m.id desc", Member.class).setParameter("role", "user").getResultList();
        model.addAttribute("members", members);
        em.close();
        return "/admin/management";
    }

    //    @PostMapping("/admin")
//    public String show(Model model) {
//        EntityManager em = emf.createEntityManager();
//        List<Member> mems = em.createQuery("select m from Member m where m.role = :role order by m.id desc", Member.class).setParameter("role","user").getResultList();
//        model.addAttribute("members", mems);
//        return "/유저관리 페이지";
//    }
    @PostMapping("/admin/delete/{id}")//각각의 id 값을 받아서 삭제
    public String delete(@PathVariable Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Member member = em.find(Member.class, id);
        if (member != null) {
            Shipment shipment = em.createQuery("select s from Shipment s where s.member = :member", Shipment.class).setParameter("member",member).getSingleResult();
            log.info(String.valueOf(shipment.getMember().getId()));
            oep.adminSideEvent(shipment);
            em.remove(shipment);
            em.remove(member);
        }
        em.getTransaction().commit();
        em.close();
        return "redirect:/admin";
    }

    @PostMapping("/search/{keyword}")//검색창을 만들어 이름, 별명, 이메일등을 카테고리로 하여 검색함.
    public String search(@PathVariable String keyword, @RequestParam("search") String search, @ModelAttribute("members") Member member, Model model) {
        EntityManager em = emf.createEntityManager();
        List<Member> members = new ArrayList<>();
        if (keyword.equals("nick")) {
            members = em.createQuery("select m from Member m where m.nick like :search", Member.class).setParameter("search", search).getResultList();
        } else if (keyword.equals("name")) {
            members = em.createQuery("select m from Member m where m.username like :search", Member.class).setParameter("search", search).getResultList();
        } else if (keyword.equals("email")) {
            members = em.createQuery("select m from Member m where m.email like :search", Member.class).setParameter("search", search).getResultList();
        }
        model.addAttribute("members", members);
        return "/admin/management";
    }
}
