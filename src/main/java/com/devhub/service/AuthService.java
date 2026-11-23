package com.devhub.service;

import com.devhub.config.JwtTokenProvider;
import com.devhub.domain.Role;
import com.devhub.domain.User;
import com.devhub.domain.dto.AuthDTOs.*;
import com.devhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Erro: Email já cadastrado no sistema!");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Erro: Nome de usuário já existe!");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role() != null ? request.role() : Role.ROLE_USER)
                .build();

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = tokenProvider.generateToken(authentication);

        return new AuthResponseDTO(token, "Bearer", user.getUsername(), user.getEmail(), user.getRole());
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = (User) authentication.getPrincipal();
        String token = tokenProvider.generateToken(authentication);

        return new AuthResponseDTO(token, "Bearer", user.getUsername(), user.getEmail(), user.getRole());
    }
}
