package com.brayan.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administración", description = "Rutas protegidas para administradores")
public class AdminController {

    @GetMapping("/dashboard")
    @Operation(summary = "Panel de administración", description = "Ruta protegida disponible solo para usuarios con rol ADMIN.")
    public String dashboard() {
        return "Acceso permitido: ADMIN";
    }
}
