package ru.job4j.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.stereotype.Repository;

import ru.job4j.model.Candidate;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    
    public MemoryCandidateRepository() {
        save(new Candidate(0, "Igor", "Intern Java Developer description", LocalDateTime.now()));
        save(new Candidate(0, "Vladislav", "Junior Java Developer description", LocalDateTime.now()));
        save(new Candidate(0, "Oleg", "Junior+ Java Developer description", LocalDateTime.now()));
        save(new Candidate(0, "Mark", "Middle Java Developer description", LocalDateTime.now()));
        save(new Candidate(0, "Stanislav", "Middle+ Java Developer description", LocalDateTime.now()));
        save(new Candidate(0, "Dmitry", "Senior Java Developer description", LocalDateTime.now()));
    }
    
    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(
                candidate.getId(), (id, oldCandidate) -> new Candidate(oldCandidate.getId(),
                        candidate.getName(), candidate.getDescription(), oldCandidate.getCreationDate())
                ) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }

}
