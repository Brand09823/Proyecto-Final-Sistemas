package com.proyectofinal.sistemas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tNutriente")
public class Nutriente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNutriente")
    private Integer idNutriente;

    @Column(name = "nombreNutriente", nullable = false, unique = true, length = 50)
    private String nombreNutriente;

    public Nutriente() {
    }

    public Nutriente(String nombreNutriente) {
        this.nombreNutriente = nombreNutriente;
    }

    public Integer getIdNutriente() {
        return idNutriente;
    }

    public void setIdNutriente(Integer idNutriente) {
        this.idNutriente = idNutriente;
    }

    public String getNombreNutriente() {
        return nombreNutriente;
    }

    public void setNombreNutriente(String nombreNutriente) {
        this.nombreNutriente = nombreNutriente;
    }
}