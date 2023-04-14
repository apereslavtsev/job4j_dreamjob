package ru.job4j.dreamjob.controller;

import javax.servlet.http.HttpSession;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.job4j.dreamjob.model.User;

@ThreadSafe
@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String getIndex(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("user", User.getDefaultUserIfAbsent(user));
        return "index";
    }

}