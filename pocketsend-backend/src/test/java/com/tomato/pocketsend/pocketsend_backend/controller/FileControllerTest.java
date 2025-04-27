package com.tomato.pocketsend.pocketsend_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.service.FileService;
import com.tomato.pocketsend.pocketsend_backend.service.WebSocketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;


import java.nio.charset.StandardCharsets;
import java.util.*;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        given(fileService.handleUpload(any(MultipartFile.class),nullable(String.class),any(UUID.class))).willReturn(savedFile);

        mockMvc.perform(multipart(FileController.FILE_PATH)
                        .file(file)
                        .param("text", "")
                        .sessionAttr("userId", UUID.randomUUID())
                        .with(csrf())
                        .with(user("testuser").roles("USER"))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testGetFileById() throws Exception {
        UUID fileId = UUID.randomUUID();
        FileDTO file = FileDTO.builder()
                .id(fileId)
                .filename("test.pdf")
                .filetype("application/pdf")
                .content("Hello, PocketSend!".getBytes())
                .build();

        given(fileService.getFileById(fileId)).willReturn(Optional.of(file));

        mockMvc.perform(get(FileController.FILE_PATH_ID, fileId)
                        .sessionAttr("userId", UUID.randomUUID())
                        .with(csrf())
                        .with(user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.pdf\""))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(content().bytes("Hello, PocketSend!".getBytes()));

    }

    @Test
    void testGetAllFiles() throws Exception {
        UUID userId = UUID.randomUUID();

        Map<String, Object> file1 = new HashMap<>();
        file1.put("id", UUID.randomUUID());
        file1.put("filename", "file1.pdf");

        Map<String, Object> file2 = new HashMap<>();
        file2.put("id", UUID.randomUUID());
        file2.put("filename", "file2.docx");

        List<Map<String, Object>> files = Arrays.asList(file1, file2);

        given(fileService.getFilesForUser(eq(userId))).willReturn(files);

        mockMvc.perform(get(FileController.FILE_PATH)
                .sessionAttr("userId", userId)
                .with(csrf())
                .with(user("testuser").roles("USER"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].filename").value("file1.pdf"))
                .andExpect(jsonPath("$[1].filename").value("file2.docx"));

    }

    @Test
    void testDeleteFile() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID fileId = UUID.randomUUID();

        FileDTO fileDTO = FileDTO.builder()
                .id(fileId)
                .filename("test.pdf")
                .userId(userId)
                .build();

        given(fileService.getFileById(eq(fileId))).willReturn(Optional.of(fileDTO));

        mockMvc.perform(delete(FileController.FILE_PATH_ID, fileId)
                        .sessionAttr("userId", userId)
                        .with(csrf())
                        .with(user("testuser").roles("USER"))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("File deleted successfully."));

        then(fileService).should().deleteFileById(fileId);
    }

}