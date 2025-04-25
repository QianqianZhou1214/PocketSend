package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.mappers.FileMapper;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.repositories.FileRepository;
import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService{
    @Autowired
    private FileRepository fileRepository;
    private FileMapper fileMapper;

    public FileDTO saveFile(String filename, String filetype, byte[] content, User user) {
        File file = new File();
        file.setFilename(filename);
        file.setFiletype(filetype);
        file.setContent(content);
        file.setUploadedAt(LocalDateTime.now());
        file.setOwner(user);

        return fileMapper.fileToFileDto(fileRepository.save(file));
    }

    public List<FileDTO> getAllFiles() {
        return fileRepository.findAll().stream()
                .map(fileMapper::fileToFileDto)
                .collect(Collectors.toList());
    }

    public List<File> getFilesForUser(User user) {
        return fileRepository.findByOwner(user);
    }

    public Optional<File> getFileById(long id) {
        return fileRepository.findById(id);
    }

    public void deleteFile(long id) {
        fileRepository.deleteById(id);
    }

}
