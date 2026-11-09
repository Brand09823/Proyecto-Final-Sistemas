package com.proyectofinal.sistemas.models;

import jakarta.persistence.*;
<<<<<<< HEAD
<<<<<<< HEAD
import java.util.List; 
=======
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)
=======
import java.util.List; 
>>>>>>> f318744a6b65a78364f858c4307e3babd3937db5

@Entity
@Table(name = "tAlimento")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlimento")
    private Integer idAlimento;

    @Column(name = "nombreAlimento", nullable = false, unique = true, length = 50)
    private String nombreAlimento;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> f318744a6b65a78364f858c4307e3babd3937db5
    // Relación Muchos a Muchos (N:M): Colección de nutrientes dentro del alimento
    @ManyToMany
    @JoinTable(
        name = "tAlimentoNutriente",
        joinColumns = @JoinColumn(name = "idAlimento"),
        inverseJoinColumns = @JoinColumn(name = "idNutriente")
    )

    private List<Nutriente> nutrientes;

<<<<<<< HEAD
=======
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)
=======
>>>>>>> f318744a6b65a78364f858c4307e3babd3937db5
    public Alimento() {}

    public Alimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Integer getIdAlimento() { return idAlimento; }
    public void setIdAlimento(Integer idAlimento) { this.idAlimento = idAlimento; }

    public String getNombreAlimento() { return nombreAlimento; }
    public void setNombreAlimento(String nombreAlimento) { this.nombreAlimento = nombreAlimento; }
<<<<<<< HEAD
<<<<<<< HEAD

    public List<Nutriente> getNutrientes() { return nutrientes; }
    public void setNutrientes(List<Nutriente> nutrientes) { this.nutrientes = nutrientes; }
=======
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)
=======

    public List<Nutriente> getNutrientes() { return nutrientes; }
    public void setNutrientes(List<Nutriente> nutrientes) { this.nutrientes = nutrientes; }
>>>>>>> f318744a6b65a78364f858c4307e3babd3937db5
}