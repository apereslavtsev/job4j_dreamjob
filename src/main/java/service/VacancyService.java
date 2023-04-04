package service;

import java.util.Collection;
import java.util.Optional;

import ru.job4j.model.Vacancy;

public interface VacancyService {
    
    Vacancy save(Vacancy vacancy);

    boolean deleteById(int id);

    boolean update(Vacancy vacancy);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();

}
