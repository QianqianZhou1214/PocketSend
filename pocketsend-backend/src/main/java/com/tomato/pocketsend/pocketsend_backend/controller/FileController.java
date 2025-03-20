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

    @CrossOrigin(origins = "http://localhost:3000")

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // save files to database
            FileEntity savedFile = fileService.saveFile(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            String baseUrl = System.getenv("SERVER_URL");
            if (baseUrl == null) {
                baseUrl = "http://localhost:8080"; // default
            }

            String downloadUrl = baseUrl + "/api/files/" + savedFile.getId();
            savedFile.setUrl(downloadUrl);

            return ResponseEntity.ok(savedFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<byte[]> getFile(@PathVariable long id) {
        return fileService.getFileById(id)
                .map(file -> ResponseEntity.ok()
                        .contentType(MediaType.valueOf(file.getFiletype()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                        .body(file.getContent()))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<String> deleteFile(@PathVariable long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok("File deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        List<FileEntity> files = fileService.getAllFiles();

        String baseUrl = System.getenv("SERVER_URL");
        if (baseUrl == null) {
            baseUrl = "http://localhost:8080"; // default
        }

        for (FileEntity file : files) {
            String downloadUrl = baseUrl + "/api/files/" + file.getId();
            file.setUrl(downloadUrl);  // ensure every file has URL
        }

        return ResponseEntity.ok(files);
    }
}
