package com.tomato.pocketsend.pocketsend_backend.dao;

import com.tomato.pocketsend.pocketsend_backend.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
