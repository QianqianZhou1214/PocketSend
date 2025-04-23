package com.tomato.pocketsend.pocketsend_backend.mappers;


import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDTO dto);

    UserDTO userToUserDto(User user);
}
