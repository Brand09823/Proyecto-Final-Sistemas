package com.proyectofinal.sistemas.models;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "alimentos")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    private String descripcion;

    @ManyToMany
    @JoinTable(
            name = "alimento_nutriente",
            joinColumns = @JoinColumn(name = "alimento_id"),
            inverseJoinColumns = @JoinColumn(name = "nutriente_id"))
    private Set<Nutriente> nutrientes = new LinkedHashSet<>();

    public Alimento() {
    }

    public Long getId() {
        return id;
    }

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

    public Set<Nutriente> getNutrientes() {
        return nutrientes;
    }

    public void setNutrientes(Set<Nutriente> nutrientes) {
        this.nutrientes = nutrientes;
    }
}
