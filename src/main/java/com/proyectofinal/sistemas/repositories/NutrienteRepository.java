package com.proyectofinal.sistemas.repositories;

import com.proyectofinal.sistemas.models.Nutriente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrienteRepository extends JpaRepository<Nutriente, Integer> {
}