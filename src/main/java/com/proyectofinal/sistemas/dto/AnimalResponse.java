package com.proyectofinal.sistemas.dto;

import java.math.BigDecimal;

public record AnimalResponse(
        Long id,
        Integer especieId,
        String especie,
        Integer edad,
        BigDecimal peso
) {
}