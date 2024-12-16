package com.zagbor.click.service;

import com.zagbor.click.model.User;
import com.zagbor.click.repository.UserRepository;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public String registerUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует.");
        }

        User newUser = new User();
        newUser.setId(UUID.randomUUID());
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // Хеширование пароля

        userRepository.saveAndFlush(newUser);
        return "Пользователь успешно зарегистрирован.";
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
