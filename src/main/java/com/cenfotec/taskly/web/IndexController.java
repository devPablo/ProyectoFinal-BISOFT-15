package com.cenfotec.taskly.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @GetMapping({"/","/login"})
    public String index(Model model) {
        return "index";
    }

    @GetMapping({"/tasks"})
    public String tasks(Model model) {
        return "tasks";
    }

    @GetMapping({"/signin"})
    public String signin(Model model) {
        return "signin";
    }
}
