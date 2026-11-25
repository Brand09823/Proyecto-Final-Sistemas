package com.proyectofinal.sistemas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.sistemas.models.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
