package ru.job4j.service;

import java.util.Collection;
import java.util.Optional;

import ru.job4j.dto.FileDto;
import ru.job4j.model.Vacancy;

public interface VacancyService {
    
    Vacancy save(Vacancy vacancy, FileDto image);

    boolean deleteById(int id);

    boolean update(Vacancy vacancy, FileDto image);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();

}
