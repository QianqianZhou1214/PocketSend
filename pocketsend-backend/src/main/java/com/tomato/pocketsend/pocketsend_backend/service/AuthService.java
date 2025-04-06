package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.dao.UserRepository;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    // login authenticate logic

    @Autowired
    private UserRepository userRepository;

    public User authenticate(String identifier, String rawPassword) throws Exception {
        Optional<User> userOpt = identifier.contains("@")
                ? userRepository.findByEmail(identifier)
                : userRepository.findByUsername(identifier);

        if (userOpt.isEmpty()) throw new Exception("User not found");

        User user = userOpt.get();
        if (!PasswordUtil.matches(rawPassword, user.getPassword())) {
            throw new Exception("Invalid password");
        }
        return user;
    }

}
