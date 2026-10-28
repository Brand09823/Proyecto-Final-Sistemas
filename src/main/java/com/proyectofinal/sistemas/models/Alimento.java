package com.proyectofinal.sistemas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tAlimento")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlimento")
    private Integer idAlimento;

    @Column(name = "nombreAlimento", nullable = false, unique = true, length = 50)
    private String nombreAlimento;

    public Alimento() {}

    public Alimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Integer getIdAlimento() { return idAlimento; }
    public void setIdAlimento(Integer idAlimento) { this.idAlimento = idAlimento; }

    public String getNombreAlimento() { return nombreAlimento; }
    public void setNombreAlimento(String nombreAlimento) { this.nombreAlimento = nombreAlimento; }
}