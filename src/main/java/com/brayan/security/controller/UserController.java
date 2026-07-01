package com.brayan.security.controller;

import com.brayan.security.dto.UpdateUserRequest;
import com.brayan.security.dto.UserResponse;
import com.brayan.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Listar usuarios", description = "Devuelve todos los usuarios registrados con paginación.")
    public ResponseEntity<Page<UserResponse>> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/active")
    @Operation(summary = "Listar usuarios activos", description = "Devuelve los usuarios que están habilitados con paginación.")
    public ResponseEntity<Page<UserResponse>> findEnabledUsers(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(userService.findEnabledUsers(pageable));
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
