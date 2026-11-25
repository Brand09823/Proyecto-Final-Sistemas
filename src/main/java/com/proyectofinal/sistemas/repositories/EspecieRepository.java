package com.proyectofinal.sistemas.repositories;

import com.proyectofinal.sistemas.models.Especie;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EspecieRepository extends JpaRepository<Especie, Integer> {
} 