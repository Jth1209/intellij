package edu.du.sb1024.controller;

import edu.du.sb1024.spring.MemberRegisterService;
import edu.du.sb1024.spring.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
public class RegisterController {

    @Autowired
    private MemberRegisterService memberRegisterService;

    @GetMapping("/register")
    public String root() {
        return "redirect:/register/step1";
    }

    @RequestMapping("/register/step1")
    public String handleStep1() {
        return "/allow/step1";
    }

    @PostMapping("/register/step2")
    public String handleStep2(
            @RequestParam(value = "agree", defaultValue = "false") Boolean agree,
            Model model) {
        if (!agree) {
            return "/allow/step1";
        }
        model.addAttribute("registerRequest", new RegisterRequest());
        return "/allow/step2";
    }
//
//    @GetMapping("/register")
//    public String handleStep2Get() {
//        return "step2";
//    }

    @PostMapping("/register/step3")
    public String handleStep3(@Valid RegisterRequest regReq, Errors errors) {

        if(errors.hasErrors()) {
            return "/allow/step2";
        }

        memberRegisterService.regist(regReq);

//        String route = "";
//        int count = memberRegisterService.checkEmail(regReq.getEmail());
//        if (count == 1) {
//            route = "redirect:/register/alreadyHave ";
//        } else {
//            if (regReq.getPassword().equals(regReq.getConfirmPassword())) {
//                memberRegisterService.regist(regReq);
//                route = "register/step3";
//            }else{
//                route = "redirect:/register/passwordError";
//            }
//        }
        return "redirect:/login";
    }
//
//    @GetMapping("/register/alreadyHave")
//    public String handleStep3Get() {
//        return "register/alreadyHave";
//    }
//
//    @GetMapping("/register/passwordError")
//    public String handleStep4Get() {
//        return "register/passwordDenied";
//    }

}
