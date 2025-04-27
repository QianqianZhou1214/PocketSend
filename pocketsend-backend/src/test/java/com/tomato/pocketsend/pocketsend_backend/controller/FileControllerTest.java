package com.tomato.pocketsend.pocketsend_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.service.FileService;
import com.tomato.pocketsend.pocketsend_backend.service.WebSocketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    FileService fileService;

    @MockitoBean
    WebSocketService webSocketService;

    @Test
    void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "Hello PocketSend!".getBytes()
        );

        UUID fakeId = UUID.randomUUID();
        FileDTO savedFile = FileDTO.builder()
                .id(fakeId)
                .filename("test.pdf")
                .filetype("application/pdf")
                .content("Hello PocketSend!".getBytes())
                .userId(UUID.randomUUID())
                .build();

        given(fileService.saveFile(any(FileDTO.class))).willReturn(savedFile);

        mockMvc.perform(multipart(FileController.FILE_PATH)
                        .file(file)
                        .sessionAttr("userId", UUID.randomUUID())
                        .with(csrf())
                        .with(user("testuser").roles("USER"))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }



}