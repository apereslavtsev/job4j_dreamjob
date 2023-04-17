package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


class CandidateControllerTest {
    
    private CandidateService candidateService;

    private CityService cityService;

    private CandidateController candidateController;

    private MultipartFile testFile;
    
    private Model model;
    
    @BeforeEach
    public void initServices() {
        candidateService = mock(CandidateService.class);
        cityService = mock(CityService.class);
        candidateController = new CandidateController(candidateService, cityService);
        testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
        model = new ConcurrentModel();
    }

    @Test
    public void whenRequestCandidateListPageThenGetPageWithCandidates() {
        var candidate1 = new Candidate(1, "test1");
        var candidate2 = new Candidate(2, "test2");
        var expectedCandidates = List.of(candidate1, candidate2);
        when(candidateService.findAll()).thenReturn(expectedCandidates);

        var view = candidateController.getAll(model);
        var actualCandidate = model.getAttribute("candidates");

        assertThat(view).isEqualTo("candidates/list");
        assertThat(actualCandidate).isEqualTo(expectedCandidates);
    }

    @Test
    public void whenRequestCandidateCreationPageThenGetPageWithCities() {
        var city1 = new City(1, "Москва");
        var city2 = new City(2, "Санкт-Петербург");
        var expectedCities = List.of(city1, city2);
        when(cityService.getAll()).thenReturn(expectedCities);

        var model = new ConcurrentModel();
        var view = candidateController.getCreationPage(model);
        var actualCandidates = model.getAttribute("cities");

        assertThat(view).isEqualTo("candidates/create");
        assertThat(actualCandidates).isEqualTo(expectedCities);
    }

    @Test
    public void whenPostCandidateWithFileThenSameDataAndRedirectToCandidatesPage() throws Exception {
        var candidate = new Candidate(1, "test1", "desc1", now(), 1, 2);
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        var candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
        var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);        
        when(candidateService.save(candidateArgumentCaptor.capture(), 
                fileDtoArgumentCaptor.capture())).thenReturn(candidate);

        var view = candidateController.create(candidate, testFile, model);
        var actualCandidate = candidateArgumentCaptor.getValue();
        var actualFileDto = fileDtoArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/candidates");
        assertThat(actualCandidate).isEqualTo(candidate);
        assertThat(fileDto).usingRecursiveComparison().isEqualTo(actualFileDto);
    }

    @Test
    public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Failed to write file");
        when(candidateService.save(any(), any())).thenThrow(expectedException);

        var view = candidateController.create(new Candidate(), testFile, model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }
    
    @Test
    public void whenGetByIdThenReturnSameAndRedirectToOneCandidatePage() {
        var expectedCandidate = new Candidate(1, "test1", "desc1", now(), 1, 2);
        when(candidateService.findById(any(Integer.class)))
            .thenReturn(Optional.of(expectedCandidate));
        
        var view = candidateController.getById(model, 1);
        var actualCandidate = model.getAttribute("candidate");
        
        assertThat(view).isEqualTo("candidates/one");
        assertThat(actualCandidate).isEqualTo(expectedCandidate);
    }
    
    @Test
    public void whenGetByInvalidIdThenGetErrorPageWithMessage() {
        when(candidateService.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        
        var view = candidateController.getById(model, 1);
        var message = model.getAttribute("message");
        
        assertThat(view).isEqualTo("errors/404");
        assertThat(message).isEqualTo("Кандидат с указанным идентификатором не найден");
    }
    
    @Test
    public void whenUpdatePostCandidateWithFileThenSameDataAndRedirectToCandidatesPage() throws Exception {
        var candidate = new Candidate(1, "test1", "desc1", now(), 1, 2);
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        var candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
        var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);        
        when(candidateService.update(candidateArgumentCaptor.capture(), 
                fileDtoArgumentCaptor.capture())).thenReturn(true);

        var view = candidateController.update(candidate, testFile, model);
        var actualCandidate = candidateArgumentCaptor.getValue();
        var actualFileDto = fileDtoArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/candidates");
        assertThat(actualCandidate).isEqualTo(candidate);
        assertThat(fileDto).usingRecursiveComparison().isEqualTo(actualFileDto);
    }
    
    @Test
    public void whenUpdateByInvalidIdThenGetErrorPageWithMessage() {
        var candidate = new Candidate(1, "test1", "desc1", now(), 1, 2);
        when(candidateService.update(any(), any())).thenReturn(false);
        
        var view = candidateController.update(candidate, testFile, model);
        var message = model.getAttribute("message");
        
        assertThat(view).isEqualTo("errors/404");
        assertThat(message).isEqualTo("Кандидат с указанным идентификатором не найден");
    }
    
    @Test
    public void whenDeleteCandidateThenRedirectToCandidatesPage() throws Exception {
        when(candidateService.deleteById(any(Integer.class))).thenReturn(true);
        
        var view = candidateController.delete(model, 1);
        
        assertThat(view).isEqualTo("redirect:/candidates");
    }
    
    @Test
    public void whendeleteByInvalidIdThenGetErrorPageWithMessage() {
        when(candidateService.deleteById(any(Integer.class))).thenReturn(false);
        
        var view = candidateController.delete(model, 1);
        var message = model.getAttribute("message");
        
        assertThat(view).isEqualTo("errors/404");
        assertThat(message).isEqualTo("Кандидат с указанным идентификатором не найден");
    }

}
