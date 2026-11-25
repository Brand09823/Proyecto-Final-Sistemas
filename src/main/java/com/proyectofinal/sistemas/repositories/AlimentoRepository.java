package com.proyectofinal.sistemas.repositories;

import com.proyectofinal.sistemas.models.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Integer> {
    boolean existsByNutrientes_IdNutriente(Integer idNutriente);
}