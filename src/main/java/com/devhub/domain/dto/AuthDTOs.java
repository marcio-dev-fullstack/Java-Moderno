package com.devhub.domain.dto;

public class AuthDTOs {
    public record RegisterRequest(String username, String email, String password, String role) {}
    public record LoginRequest(String email, String password) {}
    public record AuthResponse(String token, String tokenType, String username, String email, String role) {}
}
