package com.tomato.pocketsend.pocketsend_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

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
    private User owner;


}
