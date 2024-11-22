package edu.du.sb1024.controller;

import edu.du.sb1024.entity.AuthInfo;
import edu.du.sb1024.entity.Board;
import edu.du.sb1024.entity.BoardDto;
import edu.du.sb1024.entity.Comment;
import edu.du.sb1024.service.BoardService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    final private EntityManagerFactory emf;
    final private BoardService boardService;

    @PostMapping("/comment/{id}")
    public String comment(@PathVariable("id") int id, @RequestParam("content") String content, HttpSession session, RedirectAttributes re) throws Exception {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");//내일 전면 수정
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Board board = em.find(Board.class,id);
        Comment comment = Comment.builder().nick(authInfo.getNick()).board(board).content(content).createdTime(new Date().toLocaleString()).build();
        em.persist(comment);
        em.getTransaction().commit();
        em.close();
        re.addFlashAttribute("flag","true");
        return "redirect:/board/openBoardDetail.do?boardIdx="+id;
    }
    @GetMapping("/comment/delete/{id}/{bid}")
    public String deleteComment(@PathVariable("id") int id , @PathVariable("bid") int bid , RedirectAttributes re) throws Exception {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.remove(em.find(Comment.class,id));

        em.getTransaction().commit();
        em.close();
        re.addFlashAttribute("flag","true");
        return "redirect:/board/openBoardDetail.do?boardIdx="+bid;
    }
}
