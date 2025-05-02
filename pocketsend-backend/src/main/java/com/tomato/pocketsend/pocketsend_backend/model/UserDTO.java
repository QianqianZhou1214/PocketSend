package com.tomato.pocketsend.pocketsend_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
