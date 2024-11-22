package edu.du.sb1024.controller;

import edu.du.sb1024.entity.AuthInfo;
import edu.du.sb1024.entity.PasswordCheck;
import edu.du.sb1024.spring.ChangePasswordService;
import edu.du.sb1024.spring.WrongIdPasswordException;
import edu.du.sb1024.validation.ChangePwdCommand;
import edu.du.sb1024.validation.ChangePwdCommandValidator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Setter
@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {

    @Autowired
    private ChangePasswordService changePasswordService;

    @GetMapping
    public String form(@ModelAttribute("command") ChangePwdCommand pwdCommand , HttpSession session , Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "/info/auth/changePwd";
    }

    @PostMapping
    public String form(@Valid @ModelAttribute("command") ChangePwdCommand pwdCommand, Errors errors, HttpSession session) {
		new ChangePwdCommandValidator().validate(pwdCommand, errors);
        if (errors.hasErrors()) {
            return "/info/auth/changePwd";
        }
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		try {
        changePasswordService.changePassword(authInfo.getEmail(), pwdCommand.getCurrentPassword(), pwdCommand.getNewPassword());
        ;
        return "redirect:/logout";
		}catch (WrongIdPasswordException e) {
			errors.rejectValue("currentPassword","notMatching");
			return "/info/auth/changePwd";
		}
    }

    @PostMapping("/user")
    public String form2(@Valid @ModelAttribute("pc") PasswordCheck pc, Errors errors, @RequestParam("email") String email) {
        if (errors.hasErrors()) {
            return "/info/auth/changePwd2";
        }
        System.out.println(email);
        changePasswordService.changePassword2(email,pc.getPassword());
        return "redirect:/login";
    }
}
