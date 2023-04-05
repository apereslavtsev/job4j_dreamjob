package ru.job4j.repository;

import java.util.Collection;

import ru.job4j.model.City;

public interface CityRepository {
    Collection<City> getAll();
}
