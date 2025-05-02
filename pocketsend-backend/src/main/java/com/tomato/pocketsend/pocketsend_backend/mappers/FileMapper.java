package com.tomato.pocketsend.pocketsend_backend.mappers;

import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import org.mapstruct.*;

@Mapper
public interface FileMapper {
    @Mapping(target = "owner.id", source = "userId")
    File fileDtoToFile(FileDTO dto);

    @Mapping(target = "userId", source = "owner.id")
    FileDTO fileToFileDto(File file);


}