package com.proyectofinal.sistemas.models;

import jakarta.persistence.*;
import java.util.List; 

@Entity
@Table(name = "tAlimento")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlimento")
    private Integer idAlimento;

    @Column(name = "nombreAlimento", nullable = false, unique = true, length = 50)
    private String nombreAlimento;

    // Relación Muchos a Muchos (N:M): Colección de nutrientes dentro del alimento
    @ManyToMany
    @JoinTable(
        name = "tAlimentoNutriente",
        joinColumns = @JoinColumn(name = "idAlimento"),
        inverseJoinColumns = @JoinColumn(name = "idNutriente")
    )

    private List<Nutriente> nutrientes;

    public Alimento() {}

    public Alimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Integer getIdAlimento() { return idAlimento; }
    public void setIdAlimento(Integer idAlimento) { this.idAlimento = idAlimento; }

    public String getNombreAlimento() { return nombreAlimento; }
    public void setNombreAlimento(String nombreAlimento) { this.nombreAlimento = nombreAlimento; }

    public List<Nutriente> getNutrientes() { return nutrientes; }
    public void setNutrientes(List<Nutriente> nutrientes) { this.nutrientes = nutrientes; }
}