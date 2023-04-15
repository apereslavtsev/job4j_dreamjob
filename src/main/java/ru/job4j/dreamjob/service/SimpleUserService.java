package ru.job4j.dreamjob.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.UserRepository;

@Service
public class SimpleUserService implements UserService {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleUserService.class.getName());
    
    UserRepository userRepository;
    
    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception exception) {  
            LOG.error("Exception in save user", exception);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

}
