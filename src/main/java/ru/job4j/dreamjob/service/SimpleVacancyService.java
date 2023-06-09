package ru.job4j.dreamjob.service;

import java.util.Collection;
import java.util.Optional;

import org.junit.runner.notification.RunListener.ThreadSafe;
import org.springframework.stereotype.Service;

import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.VacancyRepository;
import ru.job4j.dreamjob.dto.FileDto;

@ThreadSafe
@Service
public class SimpleVacancyService implements VacancyService {

    private final VacancyRepository vacancyRepository;
    
    private final FileService fileService;
    
    public SimpleVacancyService(VacancyRepository vacancyRepository, FileService fileService) { 
        this.vacancyRepository = vacancyRepository;
        this.fileService = fileService;
    }
    

    private void saveNewFile(Vacancy vacancy, FileDto image) {
        var file = fileService.save(image);
        vacancy.setFileId(file.getId());
    }

    @Override
    public Vacancy save(Vacancy vacancy, FileDto image) {
        saveNewFile(vacancy, image);        
        return vacancyRepository.save(vacancy);
    }
    
    @Override
    public boolean deleteById(int id) {
        var fileOptional = findById(id);
        if (fileOptional.isEmpty()) {
            return false;
        }
        var isDeleted = vacancyRepository.deleteById(id);
        fileService.deleteById(fileOptional.get().getFileId());
        return isDeleted;
    }

    @Override
    public boolean update(Vacancy vacancy, FileDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return vacancyRepository.update(vacancy);
        }
        /* если передан новый не пустой файл, то старый удаляем, а новый сохраняем */
        var oldFileId = vacancy.getFileId();
        saveNewFile(vacancy, image);
        var isUpdated = vacancyRepository.update(vacancy);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return vacancyRepository.findById(id);
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

}
