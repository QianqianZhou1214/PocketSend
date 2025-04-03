package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.dao.FileRepository;
import com.tomato.pocketsend.pocketsend_backend.entity.FileEntity;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public FileEntity saveFile(String filename, String filetype, byte[] content, User user) {
        FileEntity file = new FileEntity();
        file.setFilename(filename);
        file.setFiletype(filetype);
        file.setContent(content);
        file.setUploadedAt(LocalDateTime.now());
        file.setOwner(user);

        return fileRepository.save(file);
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public List<FileEntity> getFilesForUser(User user) {
        return fileRepository.findByOwner(user);
    }

    public Optional<FileEntity> getFileById(long id) {
        return fileRepository.findById(id);
    }

    public void deleteFile(long id) {
        fileRepository.deleteById(id);
    }

}
