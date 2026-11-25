package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.CorralRequest;
import com.proyectofinal.sistemas.models.Corral;
import com.proyectofinal.sistemas.repositories.CorralRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorralServiceTest {

    @Mock
    private CorralRepository corralRepository;

    @InjectMocks
    private CorralService corralService;

    @Test
    void listarTodosDevuelveDtos() {
        Corral corral = corral(1, "Corral Norte", 25);
        when(corralRepository.findAll()).thenReturn(List.of(corral));

        var result = corralService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).idCorral());
        assertEquals("Corral Norte", result.get(0).nombreCorral());
        assertEquals(25, result.get(0).capacidad());
    }

    @Test
    void crearGuardaCorral() {
        when(corralRepository.save(any(Corral.class))).thenAnswer(invocation -> {
            Corral saved = invocation.getArgument(0);
            saved.setIdCorral(1);
            return saved;
        });

        var result = corralService.crear(new CorralRequest(" Corral Norte ", 25));

        assertEquals(1, result.idCorral());
        assertEquals("Corral Norte", result.nombreCorral());
        assertEquals(25, result.capacidad());
    }

    @Test
    void buscarCorralInexistenteDevuelveNotFound() {
        when(corralRepository.findById(404)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> corralService.buscarPorId(404));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void actualizarCorralExistente() {
        Corral corral = corral(1, "Corral Norte", 25);
        when(corralRepository.findById(1)).thenReturn(Optional.of(corral));
        when(corralRepository.save(any(Corral.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = corralService.actualizar(1, new CorralRequest("Corral Sur", 40));

        assertEquals("Corral Sur", result.nombreCorral());
        assertEquals(40, result.capacidad());
        verify(corralRepository).save(corral);
    }

    @Test
    void eliminarCorralInexistenteDevuelveNotFound() {
        when(corralRepository.existsById(404)).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> corralService.eliminar(404));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(corralRepository, never()).deleteById(anyInt());
    }

    private Corral corral(Integer id, String nombre, Integer capacidad) {
        Corral corral = new Corral(nombre, capacidad);
        corral.setIdCorral(id);
        return corral;
    }
}
