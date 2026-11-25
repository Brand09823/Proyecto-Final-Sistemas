package com.proyectofinal.sistemas.controllers;

import com.proyectofinal.sistemas.dto.NutrienteRequest;
import com.proyectofinal.sistemas.dto.NutrienteResponse;
import com.proyectofinal.sistemas.services.NutrienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrientes")
public class NutrienteController {

    private final NutrienteService nutrienteService;

    public NutrienteController(NutrienteService nutrienteService) {
        this.nutrienteService = nutrienteService;
    }

    @GetMapping
    public List<NutrienteResponse> listarTodos() {
        return nutrienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public NutrienteResponse buscarPorId(@PathVariable Integer id) {
        return nutrienteService.buscarPorId(id);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<NutrienteResponse> crear(@Valid @RequestBody NutrienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(nutrienteService.crear(request));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public NutrienteResponse actualizar(@PathVariable Integer id, @Valid @RequestBody NutrienteRequest request) {
        return nutrienteService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        nutrienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}