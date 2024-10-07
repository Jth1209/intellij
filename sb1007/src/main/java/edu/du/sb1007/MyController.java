package edu.du.sb1007;

import edu.du.sb1007.dao.ISimplebbs;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {

    @Autowired
    ISimplebbs dao;

    @GetMapping("/")
    public String root(){
        return "redirect:list";
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("list",dao.listDao());
        return "list";
    }

    @GetMapping("/writeForm")
    public String writeForm(){
        return "writeForm";
    }

    @PostMapping("write")
    public String write(Model model, HttpServletRequest request){
        String writer = request.getParameter("writer");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        dao.writeDao(writer, title, content);
        return "redirect:list";
    }

    @GetMapping("/view")
    public String view(Model model, HttpServletRequest request){
        String id = request.getParameter("id");
        model.addAttribute("dto", dao.viewDao(id));
        return "view";
    }

    @GetMapping("/updateForm")
    public String updateForm(Model model, HttpServletRequest request){
        String id = request.getParameter("id");
        model.addAttribute("dto", dao.viewDao(id));
        return "updateForm";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request){
        String id = request.getParameter("id");
        String writer = request.getParameter("writer");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        dao.updateDao(id, writer, title, content);
        return "redirect:list";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request){
        String id = request.getParameter("id");
        dao.deleteDao(id);
        return "redirect:list";
    }
}
