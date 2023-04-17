package ru.job4j.dreamjob.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


class FileControllerTest {
    
    private FileService fileService;

    private MultipartFile testFile;
    
    private FileController fileController;

    @BeforeEach
    void initServices() {        
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    void whenGetByIdThenReturnFileContent() throws Exception {
        testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
        
        var expectedFile = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        when(fileService.getFileById(any(Integer.class)))
            .thenReturn(Optional.of(expectedFile));
        
        var actualFile = fileController.getById(1).getBody();
        
        assertThat(actualFile).isEqualTo(expectedFile.getContent());
    }
    
    @Test
    void whenGetByInvalidIdThenReturnFileNotFound() throws Exception {
        when(fileService.getFileById(any(Integer.class)))
            .thenReturn(Optional.empty());
        
        var actualResult = fileController.getById(1);
        
        assertThat(actualResult).isEqualTo(ResponseEntity.notFound().build());
    }

}
