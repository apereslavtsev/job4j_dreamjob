package ru.job4j.service;

import java.util.Optional;

import ru.job4j.dto.FileDto;
import ru.job4j.model.File;

public interface FileService {
    
    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    boolean deleteById(int id);

}
