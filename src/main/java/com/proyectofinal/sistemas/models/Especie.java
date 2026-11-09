package com.proyectofinal.sistemas.models;

import jakarta.persistence.*;
<<<<<<< HEAD
<<<<<<< HEAD
import java.until.List;
=======
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)
=======
import java.until.List;
>>>>>>> f318744a6b65a78364f858c4307e3babd3937db5

@Entity
@Table(name = "tEspecie")
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEspecie")
    private Integer idEspecie;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> f318744a6b65a78364f858c4307e3babd3937db5
    
    //Relacion 1 a Muchos (1:N): Una especie tiene una coleccion de animales
    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animales;
<<<<<<< HEAD
=======
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)
=======
>>>>>>> f318744a6b65a78364f858c4307e3babd3937db5

    public Especie() {}

    public Especie(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdEspecie() { return idEspecie; }
    public void setIdEspecie(Integer idEspecie) { this.idEspecie = idEspecie; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
<<<<<<< HEAD
<<<<<<< HEAD

    public List<Animal> getAnimales() { return animales; }
    public void setAnimales(List<Animal> animales) { this.animales = animales; } 
} // 
=======
}
>>>>>>> 0833fb0 (Se agregan catalogos de Alimento y Especie con servicios y controladores)
=======

    public List<Animal> getAnimales() { return animales; }
    public void setAnimales(List<Animal> animales) { this.animales = animales; } 
} // 
>>>>>>> f318744a6b65a78364f858c4307e3babd3937db5
