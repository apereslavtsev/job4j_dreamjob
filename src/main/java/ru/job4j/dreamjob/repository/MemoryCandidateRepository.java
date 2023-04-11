package ru.job4j.dreamjob.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.runner.notification.RunListener.ThreadSafe;

import ru.job4j.dreamjob.model.Candidate;

@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {

    private AtomicInteger atomicInt = new AtomicInteger(1);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    
    public MemoryCandidateRepository() {
        save(new Candidate(0, "Igor", "Intern Java Developer description", LocalDateTime.now(), 1, 0));
        save(new Candidate(0, "Vladislav", "Junior Java Developer description", LocalDateTime.now(), 2, 0));
        save(new Candidate(0, "Oleg", "Junior+ Java Developer description", LocalDateTime.now(), 3, 0));
        save(new Candidate(0, "Mark", "Middle Java Developer description", LocalDateTime.now(), 4, 0));
        save(new Candidate(0, "Stanislav", "Middle+ Java Developer description", LocalDateTime.now(), 5, 0));
        save(new Candidate(0, "Dmitry", "Senior Java Developer description", LocalDateTime.now(), 6, 0));
    }
    
    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(atomicInt.getAndIncrement());
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
                        candidate.getName(), candidate.getDescription(), 
                        oldCandidate.getCreationDate(), candidate.getCityId(), candidate.getFileId())) != null;
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
