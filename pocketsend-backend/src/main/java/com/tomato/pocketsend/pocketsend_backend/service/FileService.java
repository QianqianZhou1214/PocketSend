package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileService {
    FileDTO saveFile(FileDTO file);
    List<FileDTO> getAllFiles();
    List<FileDTO> getFilesForUser(UserDTO user);
    Optional<FileDTO> getFileById(UUID id);
    Boolean deleteFileById(UUID id);


}
