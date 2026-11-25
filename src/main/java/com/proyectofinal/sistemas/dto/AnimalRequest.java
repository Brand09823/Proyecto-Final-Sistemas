package com.proyectofinal.sistemas.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record AnimalRequest (
    @NotNull @Positive Integer especieId,
    @NotNull @Positive Integer edad,
    @NotNull @DecimalMin(value = "0.0" , inclusive = false ) BigDecimal peso
) {
    
}