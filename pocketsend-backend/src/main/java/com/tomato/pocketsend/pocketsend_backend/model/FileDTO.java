package com.tomato.pocketsend.pocketsend_backend.model;

import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class FileDTO {
    private UUID id;

    @NotNull
    private String filename;


    private String filetype;

    @NotNull
    private byte[] content;

    private LocalDateTime uploadedAt = LocalDateTime.now();

    @NotNull
    private String url;

}
