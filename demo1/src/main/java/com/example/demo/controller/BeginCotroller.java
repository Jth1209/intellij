package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BeginCotroller {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/info/staff")
    public void info() {
    }

    @GetMapping("/info/survey")
    public void survey() {}

    @GetMapping("/info/order")
    public void order() {}

    @GetMapping("/info/item")
    public void item() {}

    @GetMapping("/admin/order")
    public String ordera(HttpSession session) {
        session.setAttribute("authInfo","admin");
        return "/admin/order";
    }
}
