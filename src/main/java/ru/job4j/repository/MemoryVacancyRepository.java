package ru.job4j.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.stereotype.Repository;

import ru.job4j.model.Vacancy;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {
    
    private AtomicInteger atomicInt = new AtomicInteger(1);

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    public MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Intern Java Developer description", LocalDateTime.now(), true, 1));
        save(new Vacancy(0, "Junior Java Developer", "Junior Java Developer description", LocalDateTime.now(), true, 2));
        save(new Vacancy(0, "Junior+ Java Developer", "Junior+ Java Developer description", LocalDateTime.now(), true, 3));
        save(new Vacancy(0, "Middle Java Developer", "Middle Java Developer description", LocalDateTime.now(), true, 4));
        save(new Vacancy(0, "Middle+ Java Developer", "Middle+ Java Developer description", LocalDateTime.now(), true, 5));
        save(new Vacancy(0, "Senior Java Developer", "Senior Java Developer description", LocalDateTime.now(), true, 6));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(atomicInt.getAndIncrement());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(
                vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
                vacancy.getTitle(), vacancy.getDescription(), oldVacancy.getCreationDate(), 
                vacancy.getVisible(), vacancy.getCityId())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}
