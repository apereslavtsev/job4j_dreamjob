package ru.job4j.dreamjob.repository;

import java.util.Optional;

import ru.job4j.dreamjob.model.File;

public interface FileRepository {
    
    File save(File file);

    Optional<File> findById(int id);

    boolean deleteById(int id);

}
