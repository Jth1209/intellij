package edu.du.sb1024.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Log4j2
public class IndexController {
    @GetMapping("/")
    public String root(){
        return "/sample/all";
    }

    @GetMapping("/admin")
    public String admin(){
        return "/sample/admin";
    }
    @GetMapping("/member")
    public String member(){
        return "/sample/member";
    }
    @GetMapping("/login")
    public void login(String errorCode, String logout) {
        log.info("login 페이지..........");
        if (logout != null) {
            log.info("user logout..........");
        }
    }

    @GetMapping("/logout")//logout으로 쿠키 세션 다 날리기.
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/sample/login?logout";
    }
}
