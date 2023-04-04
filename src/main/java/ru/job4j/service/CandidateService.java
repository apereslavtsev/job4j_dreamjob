package ru.job4j.service;

import java.util.Collection;
import java.util.Optional;

import ru.job4j.model.Candidate;

public interface CandidateService {
    
    Candidate save(Candidate candidate);

    boolean deleteById(int id);

    boolean update(Candidate candidate);

    Optional<Candidate> findById(int id);

    Collection<Candidate> findAll();

}
