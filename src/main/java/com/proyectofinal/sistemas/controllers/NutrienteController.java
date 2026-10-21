package com.proyectofinal.sistemas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import com.proyectofinal.sistemas.models.Nutriente;
import com.proyectofinal.sistemas.repositories.AlimentoRepository;
import com.proyectofinal.sistemas.repositories.NutrienteRepository;

@RestController
@RequestMapping("/nutrientes")
public class NutrienteController {

    private final NutrienteRepository nutrienteRepository;
    private final AlimentoRepository alimentoRepository;

    public NutrienteController(
            NutrienteRepository nutrienteRepository,
            AlimentoRepository alimentoRepository) {
        this.nutrienteRepository = nutrienteRepository;
        this.alimentoRepository = alimentoRepository;
    }

    @GetMapping
    public List<Nutriente> findAll() {
        return nutrienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Nutriente findById(@PathVariable Long id) {
        return nutrienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Nutriente> create(@Valid @RequestBody Nutriente nutriente) {
        nutriente.setNombre(nutriente.getNombre().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(nutrienteRepository.save(nutriente));
    }

    @PutMapping("/{id}")
    public Nutriente update(@PathVariable Long id, @Valid @RequestBody Nutriente updatedNutriente) {
        Nutriente nutriente = findById(id);
        nutriente.setNombre(updatedNutriente.getNombre().trim());
        nutriente.setDescripcion(updatedNutriente.getDescripcion());
        return nutrienteRepository.save(nutriente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Nutriente nutriente = findById(id);
        if (alimentoRepository.existsByNutrientes_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "No se puede eliminar un nutriente asociado a un alimento");
        }
        nutrienteRepository.delete(nutriente);
        return ResponseEntity.noContent().build();
    }
}
