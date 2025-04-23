package com.tomato.pocketsend.pocketsend_backend.repositories;

import com.tomato.pocketsend.pocketsend_backend.entity.FileEntity;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwner(User owner);

}
