package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.model.RegisterRequest;
import com.tomato.pocketsend.pocketsend_backend.model.UpdateUserRequest;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import com.tomato.pocketsend.pocketsend_backend.repositories.UserRepository;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.utils.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {
    UserDTO registerUser(RegisterRequest request);

    Optional<User> findByUsernameOrEmail(String identifier);
    User getUserById(Long id);
    UserDTO getUserDtoById(Long id);
    UserDTO updateUser(Long id, UpdateUserRequest request);
    boolean checkPassword(User user, String rawPassword);
    String encodePassword(String rawPassword);
}


