package com.brayan.security.security;

import com.brayan.security.entity.Role;
import com.brayan.security.entity.RoleName;
import com.brayan.security.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        String secret = Base64.getEncoder().encodeToString("secure-test-secret-key-2026-1234567890".getBytes(StandardCharsets.UTF_8));
        ReflectionTestUtils.setField(jwtService, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L);
    }

    @Test
    void generateTokenShouldReturnNonEmptyToken() {
        User user = User.builder()
                .id(1L)
                .email("john@example.com")
                .roles(Set.of(Role.builder().id(1L).name(RoleName.USER).build()))
                .build();

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void extractUsernameShouldReturnEmail() {
        User user = User.builder()
                .id(1L)
                .email("john@example.com")
                .roles(Set.of(Role.builder().id(1L).name(RoleName.USER).build()))
                .build();

        String token = jwtService.generateToken(user);

        assertEquals("john@example.com", jwtService.extractUsername(token));
    }

    @Test
    void isTokenValidShouldReturnTrueForValidUser() {
        User user = User.builder()
                .id(1L)
                .email("john@example.com")
                .roles(Set.of(Role.builder().id(1L).name(RoleName.USER).build()))
                .build();

        String token = jwtService.generateToken(user);

        assertTrue(jwtService.isTokenValid(token, user));
    }
}
