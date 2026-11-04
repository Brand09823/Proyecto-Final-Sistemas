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

import jakarta.validation.Valid;
import com.proyectofinal.sistemas.models.Animal;
import com.proyectofinal.sistemas.repositories.AnimalRepository;

@RestController
@RequestMapping("/animales")
public class AnimalController {

    private final AnimalRepository animalRepository;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> findById(@PathVariable Long id) {
        return animalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Animal> create(@Valid @RequestBody Animal animal) {
        animal.setEspecie(animal.getEspecie().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(animalRepository.save(animal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> update(
            @PathVariable Long id,
            @Valid @RequestBody Animal details) {
        return animalRepository.findById(id)
                .map(animal -> {
                    animal.setEspecie(details.getEspecie().trim());
                    animal.setEdad(details.getEdad());
                    animal.setPeso(details.getPeso());
                    return ResponseEntity.ok(animalRepository.save(animal));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!animalRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        animalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
