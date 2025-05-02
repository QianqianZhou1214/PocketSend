package com.tomato.pocketsend.pocketsend_backend.controller;


import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.service.FileService;
import com.tomato.pocketsend.pocketsend_backend.service.FileServiceImpl;
import com.tomato.pocketsend.pocketsend_backend.service.WebSocketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    public static final String FILE_PATH = "/api/files";
    public static final String FILE_PATH_ID = FILE_PATH + "/{fileId}";

    public static final String FILE_UPLOAD = FILE_PATH + "/upload";

    private final FileService fileService;

    @Autowired
    private WebSocketService webSocketService;


    @PostMapping(FILE_UPLOAD)
    public ResponseEntity<FileDTO> uploadFile(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "text", required = false) String text,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        FileDTO savedFile = fileService.handleUpload(file, text, userId);

        // WebSocket
        webSocketService.broadcastMessage("refresh");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/files/" + savedFile.getId());
        return new ResponseEntity<>(savedFile, headers, HttpStatus.CREATED);

    }


    @GetMapping(value = FILE_PATH_ID)
    public ResponseEntity<byte[]> getFileById(@PathVariable("fileId") Long id) {
        return fileService.getFileById(id)
                .map(file -> ResponseEntity.ok()
                        .contentType(MediaType.valueOf(file.getFiletype()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                        .body(file.getContent()))
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping(value = FILE_PATH_ID)
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        Optional<FileDTO> fileOpt = fileService.getFileById(id);
        if (fileOpt.isEmpty() || !fileOpt.get().getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to delete this file.");
        }

        fileService.deleteFileById(id);
        return ResponseEntity.ok("File deleted successfully.");
    }

    @GetMapping(value = FILE_PATH)
    public ResponseEntity<List<Map<String, Object>>> getAllFiles(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Map<String, Object>> files = fileService.getFilesForUser(userId);
        return ResponseEntity.ok(files);

    }
}
