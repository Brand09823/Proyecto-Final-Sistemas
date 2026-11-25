package com.proyectofinal.sistemas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

public record AlimentoRequest(
        @NotBlank @Size(max = 50) String nombreAlimento,
        List<@Positive Integer> nutrientesIds
) {
}