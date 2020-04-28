package com.cenfotec.taskly.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninController {

    @GetMapping({"/signin"})
    public String signin(Model model) {
        return "signin";
    }
}
