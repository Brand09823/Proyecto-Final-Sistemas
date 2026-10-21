package com.proyectofinal.sistemas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.sistemas.models.Alimento;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    boolean existsByNutrientes_Id(Long nutrienteId);
}
