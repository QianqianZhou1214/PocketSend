package com.tomato.pocketsend.pocketsend_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = true)
    private String filetype;

    @Lob
    @Column(columnDefinition = "LONGBLOB", nullable = false)
    private byte[] content;

    @Column(nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @Transient
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public FileEntity() {
    }

    public FileEntity(Long id, String filename, String filetype, byte[] content, LocalDateTime uploadedAt, String url) {
        this.id = id;
        this.filename = filename;
        this.filetype = filetype;
        this.content = content;
        this.uploadedAt = uploadedAt;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
