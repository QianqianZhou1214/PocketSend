package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.mappers.FileMapper;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import com.tomato.pocketsend.pocketsend_backend.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public FileDTO saveFile(FileDTO file) {

        return fileMapper.fileToFileDto(fileRepository
                .save(fileMapper.fileDtoToFile(file)));
    }

    @Override
    public List<FileDTO> getAllFiles() {
        return fileRepository.findAll().stream()
                .map(fileMapper::fileToFileDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getFilesForUser(UUID userId) {

        List<File> files = fileRepository.findAllByOwnerId(userId);
        List<Map<String, Object>> response = new ArrayList<>();
        Map<String, Object> fileData = new HashMap<>();
        for (File file : files) {
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

        return response;
    }

    @Override
    public Optional<FileDTO> getFileById(UUID id) {
        return Optional.ofNullable(fileMapper.fileToFileDto(fileRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public Boolean deleteFileById(UUID id) {
        if(fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
