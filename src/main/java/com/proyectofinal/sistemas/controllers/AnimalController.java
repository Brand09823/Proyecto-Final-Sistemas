package com.proyectofinal.sistemas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public ResponseEntity<Animal> create(@Valid @RequestBody Animal animal) {
        animal.setEspecie(animal.getEspecie().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(animalRepository.save(animal));
    }
}
