package com.devhub.domain.dto;

import com.devhub.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDTOs {

    public record RegisterRequestDTO(
        @NotBlank(message = "O nome de usuário é obrigatório") String username,
        @NotBlank @Email(message = "Email inválido") String email,
        @NotBlank @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") String password,
        Role role
    ) {}

    public record AuthRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String password
    ) {}

    public record AuthResponseDTO(
        String token,
        String tokenType,
        String username,
        String email,
        Role role
    ) {}
}
