package edu.du.sb1024.controller;

import edu.du.sb1024.encoder.PasswordEncoder;
import edu.du.sb1024.entity.AuthInfo;
import edu.du.sb1024.entity.Board;
import edu.du.sb1024.entity.BoardDto;
import edu.du.sb1024.entity.Member;
import edu.du.sb1024.repository.MemberRepository;
import edu.du.sb1024.service.AuthService;
import edu.du.sb1024.service.BoardService;
import edu.du.sb1024.spring.MemberRegisterService;
import edu.du.sb1024.validation.ChangePwdCommand;
import edu.du.sb1024.validation.NickCheckValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
public class BeginController {

    @Autowired
    BoardService boardService;
    @Autowired
    EntityManagerFactory emf;
    @Autowired
    MemberRegisterService mrs;

    @GetMapping("/")
    public String index(Model model) throws Exception {
        List<BoardDto> boards = boardService.selectFiveBoard();
        model.addAttribute("boards", boards);
        return "/allow/index";
    }

//        @PostConstruct
//    public void init() {
//        Member member = Member.builder()
//                .id(1001L)
//                .username("hong1")
//                .password(passwordEncoder.encrypt("hong1@aaa.com","1234"))
//                .email("hong1@aaa.com")
//                .role("ADMIN")
//                .build();
//        memberRepository.save(member);
//
//        member = Member.builder()
//                .id(1002L)
//                .username("test1")
//                .password(passwordEncoder.encrypt("test1@aaa.com","1234"))
//                .email("test1@aaa.com")
//                .role("USER")
//                .build();
//        memberRepository.save(member);
//
//        member = Member.builder()
//                .id(1003L)
//                .username("admin1")
//                .password(passwordEncoder.encrypt("123@aaa.com","1234"))
//                .email("123@aaa.com")
//                .role("ADMIN")
//                .build();
//        memberRepository.save(member);
//    }

    @GetMapping("/myinfo")
    public String myinfo(@ModelAttribute("command") ChangePwdCommand command, Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        EntityManager em = emf.createEntityManager();
        Member member = em.find(Member.class, authInfo.getId());
        model.addAttribute("member", member);
        em.close();
        return "/info/auth/staff";
    }

    @GetMapping("/update")
    public String update(@ModelAttribute("member") Member member, HttpSession session, Model model) {
        AuthInfo a = (AuthInfo) session.getAttribute("authInfo");
        Long id = a.getId();
        EntityManager em = emf.createEntityManager();
        Member mem = em.find(Member.class, id);
        model.addAttribute("member", mem);
//        System.out.println(mem.toString());
        em.close();
        return "/info/auth/updateInfo";
    }

    @PostMapping("/updateInfo")
    public String updateInfo(@RequestParam("nick") String nick, @RequestParam("name") String name, HttpSession session) {

        AuthInfo a = (AuthInfo) session.getAttribute("authInfo");
        Long id = a.getId();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Member mem = em.find(Member.class, id);
        mem.setNick(nick);
        mem.setUsername(name);//필요 없을수도 있음.
        em.persist(mem);

        em.getTransaction().commit();
        em.close();
        a.setNick(nick);
        return "redirect:/myinfo";
    }
}
