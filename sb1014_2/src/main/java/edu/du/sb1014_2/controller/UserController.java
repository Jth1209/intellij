package edu.du.sb1014_2.controller;

import edu.du.sb1014_2.entity.User;
import edu.du.sb1014_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequiredArgsConstructor
public class UserController {

    final UserRepository userRepository;

    @GetMapping("/user/index")
    public String index(){
        return "/user/index";
    }

    @GetMapping("/user/loginView")
    public String loginView() {
        return "/user/loginView";
    }

    @PostMapping("/user/loginProcess")
    public String loginProcess(@RequestParam String id, @RequestParam String password, Model model){
        int check = userRepository.userCount(id,password);
        model.addAttribute("check",check);
        if(check == 1){
            User user = userRepository.selectUser(id,password);
            model.addAttribute("user",user);
        }
        return "/user/index";
    }

    @GetMapping("/user/registerView")
    public String registerView(@ModelAttribute("register") User user){
        return "/user/registerView";
    }

    @PostMapping("/user/registerProcess")
    public String registerProcess(@ModelAttribute("register") User user){
        System.out.println(user);
        userRepository.saveAndFlush(user);
        return "redirect:/user/loginView";
    }

    @GetMapping("/user/updateView")
    public String updateView(@ModelAttribute("update") User user){
        return "/user/updateView";
    }

    @PostMapping("/user/updateProcess")
    public String updateProcess(@ModelAttribute("update") User user){
        userRepository.saveAndFlush(user);
        return "redirect:/user/loginView";
    }
}
