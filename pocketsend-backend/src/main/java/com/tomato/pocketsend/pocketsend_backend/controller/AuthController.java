package com.tomato.pocketsend.pocketsend_backend.controller;

import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.model.*;
import com.tomato.pocketsend.pocketsend_backend.utils.JwtTokenUtil;
import com.tomato.pocketsend.pocketsend_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            UserDTO userDTO = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> optionalUser = userService.findByUsernameOrEmail(request.getIdentifier());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = optionalUser.get();
        if (!userService.checkPassword(user, request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtTokenUtil.generateToken(user);
        UserDTO userDTO = userService.getUserDtoById(user.getId());
        return ResponseEntity.ok(new LoginResponse(token, userDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.getUserDtoById(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserRequest request,
                                              HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
}
