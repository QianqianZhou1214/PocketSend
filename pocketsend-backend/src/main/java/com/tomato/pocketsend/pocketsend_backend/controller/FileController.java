package com.tomato.pocketsend.pocketsend_backend.controller;


import com.tomato.pocketsend.pocketsend_backend.dto.FileUploadResponse;
import com.tomato.pocketsend.pocketsend_backend.entity.FileEntity;
import com.tomato.pocketsend.pocketsend_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // save files to database
            FileEntity savedFile = fileService.saveFile(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            String baseUrl = System.getenv("SERVER_URL"); // "https://pocketsend.com"
            if (baseUrl == null) {
                baseUrl = "http://localhost:8080"; // default
            }
            String downloadUrl = baseUrl + "/api/files/" + savedFile.getId();
            return ResponseEntity.ok(new FileUploadResponse(savedFile.getId(), savedFile.getFilename(), downloadUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable long id) {
        return fileService.getFileById(id)
                .map(file -> ResponseEntity.ok()
                        .contentType(MediaType.valueOf(file.getFiletype()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                        .body(file.getContent()))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok("File deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }
}
