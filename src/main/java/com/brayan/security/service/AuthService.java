package com.brayan.security.service;

import com.brayan.security.dto.RegisterRequest;
import com.brayan.security.dto.UserResponse;
import com.brayan.security.entity.Role;
import com.brayan.security.entity.RoleName;
import com.brayan.security.entity.User;
import com.brayan.security.exception.BadRequestException;
import com.brayan.security.exception.ResourceNotFoundException;
import com.brayan.security.repository.RoleRepository;
import com.brayan.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.brayan.security.dto.LoginRequest;
import com.brayan.security.dto.AuthResponse;
import com.brayan.security.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya se encuentra registrado");
        }

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Rol USER no encontrado"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(roles)
                .build();

        User saved = userRepository.save(user);
        Objects.requireNonNull(saved);

        Set<String> roleNames = new HashSet<>();
        for (Role r : saved.getRoles()) {
            roleNames.add(r.getName().name());
        }

        return UserResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .enabled(saved.getEnabled())
                .roles(roleNames)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        if (user.getEnabled() == null || !user.getEnabled()) {
            throw new BadRequestException("Usuario desactivado");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user);

        Set<String> roleNames = new HashSet<>();
        for (Role r : user.getRoles()) {
            roleNames.add(r.getName().name());
        }

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(roleNames)
                .build();
    }
}
