package com.tomato.pocketsend.pocketsend_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class FileDTO {
    private Long id;

    @NotNull
    private String filename;


    private String filetype;

    @NotNull
    private byte[] content;

    private LocalDateTime uploadedAt;

    @NotNull
    private String url;

    @NotNull
    private Long userId;


}
