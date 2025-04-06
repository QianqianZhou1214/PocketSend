package com.tomato.pocketsend.pocketsend_backend.controller;

import com.tomato.pocketsend.pocketsend_backend.entity.User;
import com.tomato.pocketsend.pocketsend_backend.service.AuthService;
import com.tomato.pocketsend.pocketsend_backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public User register(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String password) throws Exception {
        return userService.registerUser(username, email, password);
    }

    @PostMapping("/login")
    public String login(@RequestParam String identifier,
                        @RequestParam String password,
                        HttpSession session) throws Exception {
        User user = authService.authenticate(identifier, password);
        session.setAttribute("userId", user.getId());
        return "Login Successful";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged Out";
    }
}
