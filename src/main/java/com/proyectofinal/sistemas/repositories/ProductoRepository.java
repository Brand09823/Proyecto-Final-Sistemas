package com.proyectofinal.sistemas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.sistemas.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
