package com.tomato.pocketsend.pocketsend_backend.service;

import com.tomato.pocketsend.pocketsend_backend.mappers.FileMapper;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import com.tomato.pocketsend.pocketsend_backend.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public List<FileDTO> getFilesForUser(UserDTO user) {
        return fileRepository.findByOwner(user);
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
