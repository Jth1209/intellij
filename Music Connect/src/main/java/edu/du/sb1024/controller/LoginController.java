package edu.du.sb1024.controller;

import edu.du.sb1024.entity.AuthInfo;
import edu.du.sb1024.service.AuthService;
import edu.du.sb1024.spring.WrongIdPasswordException;
import edu.du.sb1024.validation.LoginCommand;
import edu.du.sb1024.validation.LoginCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public String form(LoginCommand loginCommand) {
        return "/allow/login";
    }

    @PostMapping
    public String submit(@Valid @ModelAttribute("loginCommand") LoginCommand loginCommand, Errors errors, HttpSession session) {
//        new LoginCommandValidator().validate(loginCommand, errors);
        if (errors.hasErrors()) {
            return "/allow/login";
        }
        AuthInfo authInfo = authService.authenticate(loginCommand.getEmail(), loginCommand.getPassword());
        session.setAttribute("authInfo", authInfo);
        return "redirect:/";
//		}catch (WrongIdPasswordException e) {
//			errors.reject("idPasswordNotMatching");
//			return "/allow/login";
//		}
    }
}
