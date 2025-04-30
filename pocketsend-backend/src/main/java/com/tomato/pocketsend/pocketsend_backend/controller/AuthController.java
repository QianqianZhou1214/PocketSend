package com.tomato.pocketsend.pocketsend_backend.controller;

import com.tomato.pocketsend.pocketsend_backend.model.UserDTO;
import com.tomato.pocketsend.pocketsend_backend.utils.JwtTokenUtil;
import com.tomato.pocketsend.pocketsend_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody Map<String, String> payload) {
        UserDTO user = userService.register(payload.get("username"), payload.get("email"), payload.get("password"));
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> payload) {
        String token = userService.login(payload.get("identifier"), payload.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestHeader("Authorization") String authHeader,
                                              @RequestBody Map<String, String> payload) {
        String token = authHeader.replace("Bearer ", "");
        UUID userId = new JwtTokenUtil().extractUserId(token);
        UserDTO updated = userService.updateUser(userId, payload.get("username"), payload.get("email"), payload.get("password"));
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok().build();
    }
}
