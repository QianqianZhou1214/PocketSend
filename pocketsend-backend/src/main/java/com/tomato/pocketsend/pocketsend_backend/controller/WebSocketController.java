package com.tomato.pocketsend.pocketsend_backend.controller;

import com.tomato.pocketsend.pocketsend_backend.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ws")
public class WebSocketController {
    private final WebSocketService webSocketService;

    @Autowired
    public WebSocketController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @PostMapping("/broadcast")
    public void broadcastMessage(@RequestParam String message) {
        webSocketService.broadcastMessage(message);
    }
}
