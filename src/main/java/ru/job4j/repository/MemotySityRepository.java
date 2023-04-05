package ru.job4j.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ru.job4j.model.City;

@Repository
public class MemotySityRepository implements CityRepository {
    
    private final Map<Integer, City> cities = new HashMap<>();
    
    public MemotySityRepository() {
        cities.put(1, new City(1, "Москва"));
        cities.put(2, new City(2, "Санкт-Петербург"));
        cities.put(3, new City(3, "Екатеринбург"));
        cities.put(4, new City(4, "Казань"));
        cities.put(5, new City(5, "Волгоград"));
        cities.put(6, new City(6, "Биробиджан"));
        cities.put(7, new City(7, "Краснодар"));
        cities.put(8, new City(8, "Мурманск"));
    }
    

    @Override
    public Collection<City> getAll() {
        return cities.values();
    }

}
