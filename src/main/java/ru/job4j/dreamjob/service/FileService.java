package ru.job4j.dreamjob.service;

import java.util.Optional;

import ru.job4j.dreamjob.model.File;
import ru.job4j.dreamjob.dto.FileDto;

public interface FileService {
    
    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    boolean deleteById(int id);

}
