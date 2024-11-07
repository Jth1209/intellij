package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class BeginCotroller {
    @GetMapping("/")
    public String index(HttpSession session) {
        session.setAttribute("authInfo","박성희");
        return "index";
    }

    @GetMapping("/info/staff")
    public void info() {

    }
    @GetMapping("/login")
    public String login(){
        return "/info/login";
    }
    @GetMapping("/main")
    public String main(){
        return "/main";
    }

    @GetMapping("/password")
    public String password(){
        return "/info/changePwd";
    }

    @GetMapping("/board")
    public String board(@RequestParam("category") String category){
        if(category.equals("정보")){
            return "redirect:/";
        }else if(category.equals("잡담")){
            return "/info/login";
        }else{
            return "/info/boardList";
        }
    }
}
