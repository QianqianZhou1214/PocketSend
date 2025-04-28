package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import com.tomato.pocketsend.pocketsend_backend.repositories.UserRepository;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {
    Optional<UserDTO> getUserById(UUID id);
    List<UserDTO> getAllUsers();
    UserDTO saveNewUser(UserDTO user) throws Exception;
    Optional<UserDTO> updateUserById(UUID id);
    Boolean deleteUserById(UUID id);
}


