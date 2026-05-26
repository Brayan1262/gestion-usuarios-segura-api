package com.brayan.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/support")
@Tag(name = "Soporte", description = "Rutas protegidas para soporte")
public class SupportController {

    @GetMapping("/panel")
    @Operation(summary = "Panel de soporte", description = "Ruta protegida disponible para usuarios ADMIN o SUPPORT.")
    public String panel() {
        return "Acceso permitido: ADMIN o SUPPORT";
    }
}
