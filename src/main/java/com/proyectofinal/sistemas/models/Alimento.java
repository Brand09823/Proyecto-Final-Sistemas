package com.proyectofinal.sistemas.models;

import jakarta.persistence.*;
<<<<<<< HEAD
import java.util.List; 
=======
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)

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
    // Relación Muchos a Muchos (N:M): Colección de nutrientes dentro del alimento
    @ManyToMany
    @JoinTable(
        name = "tAlimentoNutriente",
        joinColumns = @JoinColumn(name = "idAlimento"),
        inverseJoinColumns = @JoinColumn(name = "idNutriente")
    )

    private List<Nutriente> nutrientes;

=======
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)
    public Alimento() {}

    public Alimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Integer getIdAlimento() { return idAlimento; }
    public void setIdAlimento(Integer idAlimento) { this.idAlimento = idAlimento; }

    public String getNombreAlimento() { return nombreAlimento; }
    public void setNombreAlimento(String nombreAlimento) { this.nombreAlimento = nombreAlimento; }
<<<<<<< HEAD

    public List<Nutriente> getNutrientes() { return nutrientes; }
    public void setNutrientes(List<Nutriente> nutrientes) { this.nutrientes = nutrientes; }
=======
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)
}