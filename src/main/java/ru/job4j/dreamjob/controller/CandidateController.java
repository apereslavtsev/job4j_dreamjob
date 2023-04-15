package ru.job4j.dreamjob.controller;


import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.dto.FileDto;

@ThreadSafe
@Controller
@RequestMapping("/candidates") /* Работать с кандидатами будем по URI /candidates/** */
public class CandidateController {
    
    private final CandidateService candidateService;
    
    private final CityService cityService;
    
    public CandidateController(CandidateService candidateService, CityService cityService) {
        this.candidateService = candidateService;        
        this.cityService = cityService;
    }
    
    @GetMapping
    private String getAll(Model model, HttpSession session) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/list";
    }
    
    @GetMapping("/create")
    public String getCreationPage(Model model, HttpSession session) {
        model.addAttribute("cities", cityService.getAll());
        return "candidates/create";
    }
    
    @PostMapping("/create")
    public String create(@ModelAttribute Candidate candidate, 
            @RequestParam MultipartFile file, Model model) {
        
        try {
            candidateService.save(candidate, 
                    new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/candidates";
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(Model model, 
            @PathVariable int id, HttpSession session) {
        
        var candidateOptional = candidateService.findById(id);
        if (candidateOptional.isEmpty()) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("cities", cityService.getAll());
        model.addAttribute("candidate", candidateOptional.get());
        return "candidates/one";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Candidate candidate, 
            @RequestParam MultipartFile file) {
        
        try {
            var isUpdated = candidateService.update(candidate,
                    new FileDto(file.getOriginalFilename(), file.getBytes()));
            if (!isUpdated) {
                model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
                return "errors/404";
            }
            return "redirect:/candidates";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id, HttpSession session) {
        var isDeleted = candidateService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/candidates";
    }
}
