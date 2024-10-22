package edu.du.sb1014_2.controller;

import edu.du.sb1014_2.entity.Board;
import edu.du.sb1014_2.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Controller
@Log4j2
@Transactional
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/user/index";
    }

    @GetMapping("/board/openBoardList.do")
    public String openBoardList(Model model) throws Exception {
        List<Board> list = boardRepository.selectBoardList();
        log.info(list);
        model.addAttribute("list", list);
        return "/board/boardList";
    }

    @RequestMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception{
        return "/board/boardWrite";
    }

    @PostMapping("/board/insertBoard.do")
    public String insertBoard(HttpServletRequest request ,Model model) throws Exception{
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String content = request.getParameter("contents");
        Date date = new Date();
        String creator = "admin";
        Board board = new Board(title,content,0,creator," ",String.valueOf(date),null,"N");
//        board.setTitle(title);
//        board.setContents(content);
//        board.setHitCnt(0);
//        board.setCreatorId(creator);
//        board.setCreatedDatetime(String.valueOf(date));
//        board.setUpdatedDatetime(null);
//        board.setUpdaterId("");
//        board.setDeletedYn("N");
        boardRepository.save(board);//그냥 생성자를 하나 만들죠?

        return "redirect:/board/openBoardList.do";
    }

    @GetMapping("/board/openBoardDetail.do")
    public String openBoardDetail(@RequestParam int boardIdx, Model model) throws Exception{
		Board board = boardRepository.selectBoardById(boardIdx);
		model.addAttribute("board", board);
        return "/board/boardDetail";
    }

    @PostMapping("/board/updateBoard.do")
    public String updateBoard(HttpServletRequest request) throws Exception{
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String content = request.getParameter("contents");
        int boardIdx = Integer.parseInt(request.getParameter("boardIdx"));
        Date date = new Date();
        boardRepository.updateBoard(title,content,date,boardIdx);
        return "redirect:/board/openBoardList.do";
    }

    @PostMapping("/board/deleteBoard.do")
    public String deleteBoard(HttpServletRequest request) throws Exception{
        int boardIdx = Integer.parseInt(request.getParameter("boardIdx"));

        boardRepository.deleteBoardById(boardIdx);
        return "redirect:/board/openBoardList.do";
    }
}
