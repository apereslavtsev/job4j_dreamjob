package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

@Controller
@RequestMapping("/users") 
public class UserController {
    
    UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;        
    }
    
    @GetMapping("/register")
    String getRegistationPage() {
        return "users/register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            userService.save(user);
            return "redirect:/vacancies";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }
    
}
