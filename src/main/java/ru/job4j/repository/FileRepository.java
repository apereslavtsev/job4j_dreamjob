package ru.job4j.repository;

import java.util.Optional;

import ru.job4j.model.File;

public interface FileRepository {
    
    File save(File file);

    Optional<File> findById(int id);

    boolean deleteById(int id);

}
