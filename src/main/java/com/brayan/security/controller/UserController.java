package com.brayan.security.controller;

import com.brayan.security.dto.UpdateUserRequest;
import com.brayan.security.dto.UserResponse;
import com.brayan.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = "Gestión de usuarios autenticados")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Obtener perfil", description = "Devuelve información básica del usuario autenticado.")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("Acceso permitido: usuario autenticado");
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Devuelve todos los usuarios registrados.")
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/active")
    @Operation(summary = "Listar usuarios activos", description = "Devuelve los usuarios que están habilitados.")
    public ResponseEntity<List<UserResponse>> findEnabledUsers() {
        return ResponseEntity.ok(userService.findEnabledUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario", description = "Busca un usuario por su ID.")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @PatchMapping("/{id}/disable")
    @Operation(summary = "Desactivar usuario", description = "Desactiva un usuario por su ID.")
    public ResponseEntity<UserResponse> disable(@PathVariable Long id) {
        return ResponseEntity.ok(userService.disable(id));
    }

    @PatchMapping("/{id}/enable")
    @Operation(summary = "Activar usuario", description = "Activa un usuario por su ID.")
    public ResponseEntity<UserResponse> enable(@PathVariable Long id) {
        return ResponseEntity.ok(userService.enable(id));
    }
}
