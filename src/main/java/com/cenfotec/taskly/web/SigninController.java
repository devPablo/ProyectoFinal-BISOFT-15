package com.cenfotec.taskly.web;

import com.cenfotec.taskly.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SigninController {

    @GetMapping({"/signin"})
    public String signin(Model model) {
        return "signin";
    }

    @RequestMapping(value = "/processForm", method= RequestMethod.POST)
    public String processForm(@ModelAttribute(value="user") User user) {
        System.out.println("Hola mundo");
        return "index";
    }
}
