package com.tomato.pocketsend.pocketsend_backend.model;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String email;
    private String password;
}
