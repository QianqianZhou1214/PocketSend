package com.tomato.pocketsend.pocketsend_backend.controller;


import com.tomato.pocketsend.pocketsend_backend.entity.FileEntity;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.service.FileService;
import com.tomato.pocketsend.pocketsend_backend.service.WebSocketService;
import jakarta.servlet.http.HttpSession;
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

@RestController
@RequestMapping("/api/files")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private WebSocketService webSocketService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "text", required = false) String text,
            HttpSession session) {

        Object userObj = session.getAttribute("user");
        if (userObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = (User) userObj;

        try {
            String filename = null;
            String filetype = null;
            byte[] content = null;

            if (file != null && !file.isEmpty()) {
                filename = file.getOriginalFilename();
                filetype = file.getContentType();
                content = file.getBytes();
            } else if (text != null && !text.isEmpty()) {
                filename = "text_message.txt";
                filetype = "text/plain";
                content = text.getBytes(StandardCharsets.UTF_8);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
            // save files to database
            FileEntity savedFile = fileService.saveFile(
                    filename,
                    filetype,
                    content,
                    user
            );
            String baseUrl = System.getenv("SERVER_URL");
            if (baseUrl == null) {
                baseUrl = "http://localhost:8080"; // default
            }

            String downloadUrl = baseUrl + "/api/files/" + savedFile.getId();
            savedFile.setUrl(downloadUrl);

            webSocketService.broadcastMessage("refresh");

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
    public ResponseEntity<String> deleteFile(@PathVariable long id, HttpSession session) {
        Object userObj = session.getAttribute("user");
        if (userObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = (User) userObj;

        Optional<FileEntity> fileOpt = fileService.getFileById(id);
        if (fileOpt.isEmpty() || fileOpt.get().getOwner().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to delete this file.");
        }

        fileService.deleteFile(id);
        return ResponseEntity.ok("File deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllFiles(HttpSession session) {
        // get files only for current user
        Object userObj = session.getAttribute("user");
        if (userObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = (User) userObj;

        List<FileEntity> files = fileService.getFilesForUser(user);
        List<Map<String, Object>> response = new ArrayList<>();

        for (FileEntity file : files) {
            Map<String, Object> fileData = new HashMap<>();
            fileData.put("id", file.getId());
            fileData.put("filename", file.getFilename());
            fileData.put("filetype", file.getFiletype());
            fileData.put("uploadedAt", file.getUploadedAt());

            // if text, convert byte[] to String
            if ("text/plain".equals(file.getFiletype())) {
                fileData.put("content", new String(file.getContent(), StandardCharsets.UTF_8));
            } else {
                // other file types, Base64
                String base64Content = Base64.getEncoder().encodeToString(file.getContent());
                fileData.put("content", base64Content);
                fileData.put("url", "data:" + file.getFiletype() + ";base64," + base64Content);
            }

            response.add(fileData);
        }

        return ResponseEntity.ok(response);
    }
}
