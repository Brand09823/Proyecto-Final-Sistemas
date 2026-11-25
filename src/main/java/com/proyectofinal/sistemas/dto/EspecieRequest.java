package com.proyectofinal.sistemas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EspecieRequest (
    @NotBlank @Size(max = 50) String nombre
)   {
    
}
