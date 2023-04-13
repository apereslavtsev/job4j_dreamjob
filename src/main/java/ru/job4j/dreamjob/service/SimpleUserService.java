package ru.job4j.dreamjob.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.UserRepository;

@Service
public class SimpleUserService implements UserService {
    
    UserRepository userRepository;
    
    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

}
