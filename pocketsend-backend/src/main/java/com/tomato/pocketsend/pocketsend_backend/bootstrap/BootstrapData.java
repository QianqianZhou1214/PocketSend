package com.tomato.pocketsend.pocketsend_backend.bootstrap;

import com.tomato.pocketsend.pocketsend_backend.entity.File;
import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.repositories.FileRepository;
import com.tomato.pocketsend.pocketsend_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadFileData();
        loadUserData();
    }

    private void loadUserData() {
        if(userRepository.count() == 0) {
            User user1 = User.builder()
                    .email("1234@email.com")
                    .username("user1")
                    .password("12345")
                    .build();

            User user2 = User.builder()
                    .email("5678@email.com")
                    .username("user2")
                    .password("abcde")
                    .build();

            User user3 = User.builder()
                    .email("91011@email.com")
                    .username("user3")
                    .password("1Efg")
                    .build();

            userRepository.saveAll(Arrays.asList(user1, user2, user3));
        }
    }

    private void loadFileData() {
        User user1 = userRepository.findAll().get(0);

        if(fileRepository.count() == 0) {
            File textFile = File.builder()
                    .filename("Hello.txt")
                    .filetype("text/plain")
                    .content("Hello PocketSend!".getBytes(StandardCharsets.UTF_8))
                    .owner(user1)
                    .url("http://localhost:8080/api/files/0")
                    .uploadedAt(LocalDateTime.now())
                    .build();

            File imageFile = File.builder()
                    .filename("Photo.png")
                    .filetype("image/png")
                    .content(new byte[] {1, 2, 3, 4, 5, 6})
                    .owner(user1)
                    .url("http://localhost:8080/api/files/1")
                    .uploadedAt(LocalDateTime.now())
                    .build();

            File pdfFile = File.builder()
                    .filename("Sample.pdf")
                    .filetype("application/pdf")
                    .content(new byte[]{37, 80, 68, 70, 45})
                    .owner(user1)
                    .url("http://localhost:8080/api/files/2")
                    .uploadedAt(LocalDateTime.now())
                    .build();

            fileRepository.save(textFile);
            fileRepository.save(imageFile);
            fileRepository.save(pdfFile);
        }

    }
}
