package ru.job4j.dreamjob.service;

import java.util.Optional;

import ru.job4j.dreamjob.model.User;

public interface UserService {
    
    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

}
