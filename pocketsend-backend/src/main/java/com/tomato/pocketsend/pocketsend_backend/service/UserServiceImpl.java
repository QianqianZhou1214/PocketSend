package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.mappers.UserMapper;
import com.tomato.pocketsend.pocketsend_backend.model.RegisterRequest;
import com.tomato.pocketsend.pocketsend_backend.model.UpdateUserRequest;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import com.tomato.pocketsend.pocketsend_backend.repositories.UserRepository;
import com.tomato.pocketsend.pocketsend_backend.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDTO registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(PasswordUtil.hashPassword(request.getPassword()))
                .build();

        userRepository.save(newUser);
        return userMapper.userToUserDto(newUser);
    }


    @Override
    public Optional<User> findByUsernameOrEmail(String identifier) {
        return userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public UserDTO getUserDtoById(Long id) {
        return userMapper.userToUserDto(getUserById(id));
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        User user = getUserById(id);
        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(encodePassword(request.getPassword()));
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public boolean checkPassword(User user, String rawPassword) {
        return PasswordUtil.matches(rawPassword, user.getPassword());
    }

    @Override
    public String encodePassword(String rawPassword) {
        return PasswordUtil.hashPassword(rawPassword);
    }

}
