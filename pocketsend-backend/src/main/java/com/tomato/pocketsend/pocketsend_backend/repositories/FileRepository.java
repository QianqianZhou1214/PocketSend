package com.tomato.pocketsend.pocketsend_backend.repositories;

import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface FileRepository extends JpaRepository<File, UUID> {
    List<File> findByOwner(User owner);

}
