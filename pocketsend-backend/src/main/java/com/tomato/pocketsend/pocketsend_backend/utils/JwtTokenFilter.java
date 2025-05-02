package com.tomato.pocketsend.pocketsend_backend.utils;

import com.tomato.pocketsend.pocketsend_backend.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("=== JwtTokenFilter Called ===");
        System.out.println("Request URI: " + request.getRequestURI());

        if (request.getRequestURI().startsWith("/ws")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        System.out.println("Authorization header: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtTokenUtil.validateToken(token)) {
                Long userId = jwtTokenUtil.getUserIdFromToken(token);
                System.out.println("JWT validated, userId = " + userId);
                request.setAttribute("userId", userId);
            } else {
                System.out.println("JWT validation failed.");
            }
        } else {
            System.out.println("No valid Bearer token.");
        }

        filterChain.doFilter(request, response);
    }
}
