package ru.job4j.repository;

import java.util.Optional;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.stereotype.Repository;

import ru.job4j.model.File;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class MemoryFileRepository implements FileRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private final Map<Integer, File> files = new ConcurrentHashMap<>();

    @Override
    public File save(File file) {
        file.setId(nextId.incrementAndGet());
        files.put(file.getId(), file);
        return file;
    }

    @Override
    public Optional<File> findById(int id) {
        return Optional.ofNullable(files.get(id));
    }

    @Override
    public boolean deleteById(int id) {
        return files.remove(id) != null;
    }

}
