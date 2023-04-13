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
    public Optional<User> save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

}
