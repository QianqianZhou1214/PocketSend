package com.tomato.pocketsend.pocketsend_backend.model;

import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Builder
@Data
public class UserDTO {
    private UUID id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
