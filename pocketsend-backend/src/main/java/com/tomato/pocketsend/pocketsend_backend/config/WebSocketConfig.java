package com.tomato.pocketsend.pocketsend_backend.config;

import com.tomato.pocketsend.pocketsend_backend.service.WebSocketService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketService webSocketService;

    public WebSocketConfig(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketService, "/ws").setAllowedOrigins("*");

    }
}
