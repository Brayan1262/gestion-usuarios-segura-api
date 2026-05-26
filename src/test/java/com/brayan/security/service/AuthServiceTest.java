package com.brayan.security.service;

import com.brayan.security.dto.AuthResponse;
import com.brayan.security.dto.LoginRequest;
import com.brayan.security.dto.RegisterRequest;
import com.brayan.security.dto.UserResponse;
import com.brayan.security.entity.Role;
import com.brayan.security.entity.RoleName;
import com.brayan.security.entity.User;
import com.brayan.security.exception.BadRequestException;
import com.brayan.security.exception.ResourceNotFoundException;
import com.brayan.security.repository.RoleRepository;
import com.brayan.security.repository.UserRepository;
import com.brayan.security.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerShouldCreateUser() {
        RegisterRequest request = new RegisterRequest("John", "Doe", "john@example.com", "password123");
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        Role userRole = Role.builder()
                .id(1L)
                .name(RoleName.USER)
                .description("Usuario general")
                .build();
        when(roleRepository.findByName(RoleName.USER)).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");

        User savedUser = User.builder()
                .id(1L)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password("encoded-password")
                .enabled(true)
                .roles(Set.of(userRole))
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("john@example.com", response.getEmail());
        assertTrue(response.getRoles().contains("USER"));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerShouldThrowWhenEmailExists() {
        RegisterRequest request = new RegisterRequest("John", "Doe", "john@example.com", "password123");
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.register(request));
        assertEquals("El email ya se encuentra registrado", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void loginShouldReturnAuthResponseWhenCredentialsValid() {
        LoginRequest request = new LoginRequest("john@example.com", "password123");
        Role userRole = Role.builder().id(1L).name(RoleName.USER).build();
        User user = User.builder()
                .id(1L)
                .email(request.getEmail())
                .password("encoded-password")
                .enabled(true)
                .roles(Set.of(userRole))
                .build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("token-abc");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("token-abc", response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals(1L, response.getUserId());
        assertTrue(response.getRoles().contains("USER"));
    }

    @Test
    void loginShouldThrowWhenUserNotFound() {
        LoginRequest request = new LoginRequest("john@example.com", "password123");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.login(request));
        assertEquals("Credenciales inválidas", exception.getMessage());
    }

    @Test
    void loginShouldThrowWhenPasswordIncorrect() {
        LoginRequest request = new LoginRequest("john@example.com", "password123");
        User user = User.builder()
                .id(1L)
                .email(request.getEmail())
                .password("encoded-password")
                .enabled(true)
                .roles(Collections.emptySet())
                .build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.login(request));
        assertEquals("Credenciales inválidas", exception.getMessage());
    }

    @Test
    void loginShouldThrowWhenUserDisabled() {
        LoginRequest request = new LoginRequest("john@example.com", "password123");
        User user = User.builder()
                .id(1L)
                .email(request.getEmail())
                .password("encoded-password")
                .enabled(false)
                .roles(Collections.emptySet())
                .build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.login(request));
        assertEquals("Usuario desactivado", exception.getMessage());
    }
}
