package com.proyectofinal.sistemas.dto;

import java.util.List;

public record AlimentoResponse(
    Integer idAlimento,
    String nombreAlimento,
    List<NutrienteResponse> nutrientes
) {
    
}