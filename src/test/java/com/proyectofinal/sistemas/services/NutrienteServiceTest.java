package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.NutrienteRequest;
import com.proyectofinal.sistemas.models.Nutriente;
import com.proyectofinal.sistemas.repositories.AlimentoRepository;
import com.proyectofinal.sistemas.repositories.NutrienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NutrienteServiceTest {

    @Mock
    private NutrienteRepository nutrienteRepository;

    @Mock
    private AlimentoRepository alimentoRepository;

    @InjectMocks
    private NutrienteService nutrienteService;

    @Test
    void crearNutrienteGuardaNombre() {
        when(nutrienteRepository.save(any(Nutriente.class))).thenAnswer(invocation -> {
            Nutriente nutriente = invocation.getArgument(0);
            nutriente.setIdNutriente(1);
            return nutriente;
        });

        var result = nutrienteService.crear(new NutrienteRequest("Proteína"));

        assertEquals(1, result.idNutriente());
        assertEquals("Proteína", result.nombreNutriente());
    }

    @Test
    void eliminarNutrienteAsociadoDevuelveConflict() {
        when(nutrienteRepository.existsById(1)).thenReturn(true);
        when(alimentoRepository.existsByNutrientes_IdNutriente(1)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> nutrienteService.eliminar(1));
        verify(nutrienteRepository, never()).deleteById(anyInt());
    }

    @Test
    void nutrienteInexistenteDevuelveNotFound() {
        when(nutrienteRepository.findById(404)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> nutrienteService.buscarPorId(404));
    }
}