package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.dao.FileRepository;
import com.tomato.pocketsend.pocketsend_backend.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public FileEntity saveFile(String filename, String filetype, byte[] content) {
        FileEntity file = new FileEntity();
        file.setFilename(filename);
        file.setFiletype(filetype);
        file.setContent(content);
        file.setUploadedAt(LocalDateTime.now());

        return fileRepository.save(file);
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public Optional<FileEntity> getFileById(long id) {
        return fileRepository.findById(id);
    }

    public void deleteFile(long id) {
        fileRepository.deleteById(id);
    }

}
