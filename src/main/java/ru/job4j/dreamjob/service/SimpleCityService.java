package ru.job4j.dreamjob.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.repository.CityRepository;

@Service
public class SimpleCityService implements CityService {

    private final CityRepository cityRepository;
    
    public SimpleCityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    
    @Override
    public Collection<City> getAll() {
        return cityRepository.getAll();
    }

}
