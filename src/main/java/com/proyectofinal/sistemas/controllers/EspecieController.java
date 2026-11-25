package com.proyectofinal.sistemas.controllers;

import com.proyectofinal.sistemas.dto.EspecieRequest;
import com.proyectofinal.sistemas.dto.EspecieResponse;
import com.proyectofinal.sistemas.services.EspecieService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @GetMapping
    public List<EspecieResponse> listarTodas() {
        return especieService.listarTodas();
    }

    @GetMapping("/{id}")
    public EspecieResponse buscarPorId(@PathVariable Integer id) {
        return especieService.buscarPorId(id);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<EspecieResponse> crear(@Valid @RequestBody EspecieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(especieService.crear(request));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public EspecieResponse actualizar(@PathVariable Integer id, @Valid @RequestBody EspecieRequest request) {
        return especieService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        especieService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}