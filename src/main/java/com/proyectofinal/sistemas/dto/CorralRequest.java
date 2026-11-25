package com.proyectofinal.sistemas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CorralRequest(
    @NotBlank @Size(max = 50) String nombreCorral,
    @NotNull @Positive Integer capacidad
) {
    
}