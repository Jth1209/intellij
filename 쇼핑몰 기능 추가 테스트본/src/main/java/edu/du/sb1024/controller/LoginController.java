package edu.du.sb1024.controller;

import edu.du.sb1024.entity.AuthInfo;
import edu.du.sb1024.entity.Member;
import edu.du.sb1024.entity.PasswordCheck;
import edu.du.sb1024.entity.UserInfo;
import edu.du.sb1024.service.AuthService;
import edu.du.sb1024.service.FindPasswordService;
import edu.du.sb1024.spring.WrongIdPasswordException;
import edu.du.sb1024.validation.ChangePwdCommand;
import edu.du.sb1024.validation.LoginCommand;
import edu.du.sb1024.validation.LoginCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Autowired
    private FindPasswordService findPasswordService;

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

    @GetMapping("/findPassword")
    public String findPassword(Model model) {
        model.addAttribute("user",new UserInfo());
        return "/info/auth/findUser";
    }

    @PostMapping("/findPassword")
    public String findPassword(@Valid @ModelAttribute("user") UserInfo user,Errors errors,Model model){
        if(errors.hasErrors()){
            return"/info/auth/findUser";
        }
        String email = user.getEmail();
        model.addAttribute("pc",new PasswordCheck());
        model.addAttribute("email",email);
        return "/info/auth/changePwd2";
    }
}
