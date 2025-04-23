package com.tomato.pocketsend.pocketsend_backend.mappers;

import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import org.mapstruct.Mapper;

@Mapper
public interface FileMapper {
    File fileDtoToFile(FileDTO dto);

    FileDTO fileToFileDto(File file);
}
