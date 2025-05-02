package com.tomato.pocketsend.pocketsend_backend.repositories;

import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.model.FileDTO;
import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByOwnerId(Long id);

}
