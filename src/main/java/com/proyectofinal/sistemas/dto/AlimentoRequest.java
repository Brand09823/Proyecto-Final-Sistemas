package com.proyectofinal.sistemas.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlimentoRequest {

    @NotBlank
    private String nombre;

    private String descripcion;

    @NotNull
    private Set<Long> nutrientes = Set.of();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Long> getNutrientes() {
        return nutrientes;
    }

    public void setNutrientes(Set<Long> nutrientes) {
        this.nutrientes = nutrientes;
    }
}
