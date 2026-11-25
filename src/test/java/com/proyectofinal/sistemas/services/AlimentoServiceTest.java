package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.AlimentoRequest;
import com.proyectofinal.sistemas.models.Alimento;
import com.proyectofinal.sistemas.models.Nutriente;
import com.proyectofinal.sistemas.repositories.AlimentoRepository;
import com.proyectofinal.sistemas.repositories.NutrienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlimentoServiceTest {

    @Mock
    private AlimentoRepository alimentoRepository;

    @Mock
    private NutrienteRepository nutrienteRepository;

    @InjectMocks
    private AlimentoService alimentoService;

    @Test
    void crearAlimentoAsociaNutrientes() {
        Nutriente proteina = new Nutriente("Proteína");
        proteina.setIdNutriente(1);
        when(nutrienteRepository.findAllById(anyCollection())).thenReturn(List.of(proteina));
        when(alimentoRepository.save(any(Alimento.class))).thenAnswer(invocation -> {
            Alimento alimento = invocation.getArgument(0);
            alimento.setIdAlimento(10);
            return alimento;
        });

        var result = alimentoService.crear(new AlimentoRequest("Alfalfa", List.of(1)));

        assertEquals(10, result.idAlimento());
        assertEquals("Alfalfa", result.nombreAlimento());
        assertEquals(1, result.nutrientes().size());
        assertEquals("Proteína", result.nutrientes().get(0).nombreNutriente());
    }

    @Test
    void crearConNutrienteInexistenteDevuelveNotFound() {
        when(nutrienteRepository.findAllById(anyCollection())).thenReturn(List.of());

        assertThrows(ResponseStatusException.class,
                () -> alimentoService.crear(new AlimentoRequest("Alfalfa", List.of(404))));
        verify(alimentoRepository, never()).save(any());
    }

    @Test
    void alimentoInexistenteDevuelveNotFound() {
        when(alimentoRepository.findById(404)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> alimentoService.buscarPorId(404));
    }
}