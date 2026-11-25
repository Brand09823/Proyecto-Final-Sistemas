package com.proyectofinal.sistemas.repositories;

import com.proyectofinal.sistemas.models.Corral;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CorralRepository extends JpaRepository<Corral, Integer> {
    
}