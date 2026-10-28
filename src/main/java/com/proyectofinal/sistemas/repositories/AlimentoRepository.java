package com.proyectofinal.sistemas.repositories;

import com.proyectofinal.sistemas.models.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Integer> {
}