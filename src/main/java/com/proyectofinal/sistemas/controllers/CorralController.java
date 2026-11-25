package com.proyectofinal.sistemas.controllers;

import com.proyectofinal.sistemas.dto.CorralRequest;
import com.proyectofinal.sistemas.dto.CorralResponse;
import com.proyectofinal.sistemas.services.CorralService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corrales")
public class CorralController {

    private final CorralService corralService;

    public CorralController(CorralService corralService) {
        this.corralService = corralService;
    }

    @GetMapping
    public List<CorralResponse> listarTodos() {
        return corralService.listarTodos();
    }

    @GetMapping("/{id}")
    public CorralResponse buscarPorId(@PathVariable Integer id) {
        return corralService.buscarPorId(id);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CorralResponse> crear(@Valid @RequestBody CorralRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(corralService.crear(request));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public CorralResponse actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody CorralRequest request) {
        return corralService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        corralService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}