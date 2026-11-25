package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.EspecieRequest;
import com.proyectofinal.sistemas.models.Especie;
import com.proyectofinal.sistemas.repositories.AnimalRepository;
import com.proyectofinal.sistemas.repositories.EspecieRepository;
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
class EspecieServiceTest {

    @Mock
    private EspecieRepository especieRepository;

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private EspecieService especieService;

    @Test
    void listarTodasDevuelveDtos() {
        Especie especie = especie(1, "Bovino");
        when(especieRepository.findAll()).thenReturn(List.of(especie));

        var result = especieService.listarTodas();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).idEspecie());
        assertEquals("Bovino", result.get(0).nombre());
    }

    @Test
    void crearRecortaNombreYGuardaEspecie() {
        when(especieRepository.save(any(Especie.class))).thenAnswer(invocation -> {
            Especie saved = invocation.getArgument(0);
            saved.setIdEspecie(1);
            return saved;
        });

        var result = especieService.crear(new EspecieRequest(" Bovino "));

        assertEquals(1, result.idEspecie());
        assertEquals("Bovino", result.nombre());
    }

    @Test
    void buscarEspecieInexistenteDevuelveNotFound() {
        when(especieRepository.findById(404)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> especieService.buscarPorId(404));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void actualizarEspecieExistente() {
        Especie especie = especie(1, "Bovino");
        when(especieRepository.findById(1)).thenReturn(Optional.of(especie));
        when(especieRepository.save(any(Especie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = especieService.actualizar(1, new EspecieRequest("Ovino"));

        assertEquals("Ovino", result.nombre());
        verify(especieRepository).save(especie);
    }

    @Test
    void eliminarEspecieConAnimalesDevuelveConflict() {
        when(especieRepository.existsById(1)).thenReturn(true);
        when(animalRepository.existsByEspecie_IdEspecie(1)).thenReturn(true);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> especieService.eliminar(1));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        verify(especieRepository, never()).deleteById(anyInt());
    }

    private Especie especie(Integer id, String nombre) {
        Especie especie = new Especie(nombre);
        especie.setIdEspecie(id);
        return especie;
    }
}
