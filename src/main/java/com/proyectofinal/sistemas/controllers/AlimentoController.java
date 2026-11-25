package com.proyectofinal.sistemas.controllers;

import com.proyectofinal.sistemas.dto.AlimentoRequest;
import com.proyectofinal.sistemas.dto.AlimentoResponse;
import com.proyectofinal.sistemas.services.AlimentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alimentos")
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    @GetMapping
    public List<AlimentoResponse> listarTodos() {
        return alimentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public AlimentoResponse buscarPorId(@PathVariable Integer id) {
        return alimentoService.buscarPorId(id);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AlimentoResponse> crear(@Valid @RequestBody AlimentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alimentoService.crear(request));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public AlimentoResponse actualizar(@PathVariable Integer id, @Valid @RequestBody AlimentoRequest request) {
        return alimentoService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        alimentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}