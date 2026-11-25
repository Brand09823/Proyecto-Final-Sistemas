package com.proyectofinal.sistemas.controllers;

import com.proyectofinal.sistemas.dto.AnimalRequest;
import com.proyectofinal.sistemas.dto.AnimalResponse;
import com.proyectofinal.sistemas.services.AnimalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animales")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public List<AnimalResponse> listarTodos() {
        return animalService.listarTodos();
    }

    @GetMapping("/{id}")
    public AnimalResponse buscarPorId(@PathVariable Long id) {
        return animalService.buscarPorId(id);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AnimalResponse> crear(@Valid @RequestBody AnimalRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(animalService.crear(request));
    }


    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public AnimalResponse actualizar(@PathVariable Long id, @Valid @RequestBody AnimalRequest request) {
       return animalService.actualizar(id, request);
    }


    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        animalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
