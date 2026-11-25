package com.proyectofinal.sistemas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tCorral")
public class Corral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCorral")
    private Integer idCorral;

    @Column(name = "nombreCorral", nullable = false, unique = true, length = 50)
    private String nombreCorral;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    public Corral() {
    }

    public Corral(String nombreCorral, Integer capacidad) {
        this.nombreCorral = nombreCorral;
        this.capacidad = capacidad;
    }

    public Integer getIdCorral() {
        return idCorral;
    }

    public void setIdCorral(Integer idCorral) {
        this.idCorral = idCorral;
    }

    public String getNombreCorral() {
        return nombreCorral;
    }

    public void setNombreCorral(String nombreCorral) {
        this.nombreCorral = nombreCorral;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
}