package com.proyectofinal.sistemas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record NutrienteRequest(
    @NotBlank @Size(max = 50) String nombreNutriente
) {

}