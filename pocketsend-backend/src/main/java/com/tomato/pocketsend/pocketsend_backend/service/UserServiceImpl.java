package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.mappers.UserMapper;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import com.tomato.pocketsend.pocketsend_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public UserDTO saveNewUser(UserDTO user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }

        return userMapper.userToUserDto(userRepository
                .save(userMapper.userDtoToUser(user)));
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteUserById(UUID id) {
        return null;
    }

}
