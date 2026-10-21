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
    public Animal findById(@PathVariable Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Animal> create(@Valid @RequestBody Animal animal) {
        animal.setEspecie(animal.getEspecie().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(animalRepository.save(animal));
    }

    @PutMapping("/{id}")
    public Animal update(@PathVariable Long id, @Valid @RequestBody Animal updatedAnimal) {
        Animal animal = findById(id);
        animal.setEspecie(updatedAnimal.getEspecie().trim());
        animal.setEdad(updatedAnimal.getEdad());
        animal.setPeso(updatedAnimal.getPeso());
        return animalRepository.save(animal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Animal animal = findById(id);
        animalRepository.delete(animal);
        return ResponseEntity.noContent().build();
    }
}
