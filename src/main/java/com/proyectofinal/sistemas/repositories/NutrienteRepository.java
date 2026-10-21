package com.proyectofinal.sistemas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.sistemas.models.Nutriente;

public interface NutrienteRepository extends JpaRepository<Nutriente, Long> {
}
