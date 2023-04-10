package ru.job4j.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ru.job4j.model.City;

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
        cities.put(9, new City(9, "Вологда"));
        cities.put(10, new City(10, "Киров"));
        cities.put(11, new City(11, "Омск"));
        cities.put(12, new City(12, "Томск"));
        cities.put(13, new City(13, "Днепропетровск"));
        cities.put(14, new City(14, "Ростов"));
        cities.put(15, new City(15, "Ярославль"));
        cities.put(16, new City(16, "Муром"));
        cities.put(17, new City(17, "Кострома"));
        cities.put(18, new City(18, "Волхов"));
        cities.put(19, new City(18, "Новгород"));
        cities.put(19, new City(19, "Нижний Новгород"));
        cities.put(20, new City(20, "Архангельск"));
        cities.put(21, new City(21, "Екатеринбург"));
        cities.put(22, new City(22, "Волгодонск"));
        cities.put(23, new City(23, "Красноярск"));
        cities.put(24, new City(24, "Ашхабад"));
        cities.put(25, new City(25, "Тольяти"));
        cities.put(26, new City(26, "Североморск"));
        cities.put(27, new City(27, "Суздаль"));
        cities.put(28, new City(28, "Ландехпохья"));
    }
    

    @Override
    public Collection<City> getAll() {
        return cities.values();
    }

}
