package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface FileService {
    FileDTO saveFile(FileDTO file);

    FileDTO handleUpload(MultipartFile file, String text, UUID userId);

    List<FileDTO> getAllFiles();
    List<Map<String, Object>> getFilesForUser(UUID userId);
    Optional<FileDTO> getFileById(UUID id);
    Boolean deleteFileById(UUID id);


}
