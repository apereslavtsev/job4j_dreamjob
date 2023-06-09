package ru.job4j.dreamjob.controller;


import org.junit.runner.notification.RunListener.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.SimpleUserService;
import ru.job4j.dreamjob.service.VacancyService;
import ru.job4j.dreamjob.dto.FileDto;

@ThreadSafe
@Controller
@RequestMapping("/vacancies") /* Работать с кандидатами будем по URI /vacancies/** */
public class VacancyController {

    private final VacancyService vacancyService;
    
    private final CityService cityService;
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleUserService.class.getName());
    
    public VacancyController(VacancyService vacancyService,
            CityService cityService) {
        
        this.vacancyService = vacancyService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("vacancies", vacancyService.findAll());
        model.addAttribute("cities", cityService.getAll());
        return "vacancies/list";
    }
    
    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("cities", cityService.getAll());
        return "vacancies/create";
    }
    
    @PostMapping("/create")
    public String create(@ModelAttribute Vacancy vacancy, 
            @RequestParam MultipartFile file, Model model) {
        
        try {
            vacancyService.save(vacancy, 
                    new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/vacancies";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            LOG.error("Exception in save vacancy", e);
            return "errors/404";
        }
    }
    
    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var vacancyOptional = vacancyService.findById(id);
        if (vacancyOptional.isEmpty()) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("cities", cityService.getAll());
        model.addAttribute("vacancy", vacancyOptional.get());
        return "vacancies/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Vacancy vacancy, 
            @RequestParam MultipartFile file, Model model) {
        
        try {
            var isUpdated = vacancyService.update(vacancy, 
                    new FileDto(file.getOriginalFilename(), file.getBytes()));
            if (!isUpdated) {
                model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
                return "errors/404";
            }
            return "redirect:/vacancies";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            LOG.error("Exception in update vacancy", e);
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = vacancyService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/vacancies";
    }

}