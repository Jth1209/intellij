package edu.du.sb1024.controller;

import edu.du.sb1024.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    final EntityManagerFactory emf;

    @GetMapping("/admin")
    public String admin(Model model) {
        EntityManager em = emf.createEntityManager();
        List<Member> mems = em.createQuery("select m from Member m where m.role = :role order by m.id desc", Member.class).setParameter("role","user").getResultList();
        model.addAttribute("members", mems);
        return "/유저관리 페이지 ";
    }
//    @PostMapping("/admin")
//    public String show(Model model) {
//        EntityManager em = emf.createEntityManager();
//        List<Member> mems = em.createQuery("select m from Member m where m.role = :role order by m.id desc", Member.class).setParameter("role","user").getResultList();
//        model.addAttribute("members", mems);
//        return "/유저관리 페이지";
//    }
    @PostMapping("/admin/delete/{id}")
    public String delete(@PathVariable Long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Member member = em.find(Member.class, id);
        em.remove(member);
        em.getTransaction().commit();
        return "redirect:/admin";
    }
}
