package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.CandidateRepository;
import repository.MemoryCandidateRepository;

@Controller
@RequestMapping("/candidates") /* Работать с кандидатами будем по URI /candidates/** */
public class CandidateController {
    
    private final CandidateRepository candidateRepository = MemoryCandidateRepository.getInstance();
    
    @GetMapping
    private String getAll(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll());
        return "candidates/list";
    }

}
