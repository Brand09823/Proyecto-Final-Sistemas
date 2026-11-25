package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.AnimalRequest;
import com.proyectofinal.sistemas.models.Animal;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private EspecieRepository especieRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    void listarTodosDevuelveRespuestas() {
        Especie especie = especie(4, "Bovino");
        Animal animal = new Animal(especie, 2, new BigDecimal("125.50"));
        animal.setId(7L);
        when(animalRepository.findAll()).thenReturn(List.of(animal));

        var result = animalService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(7L, result.get(0).id());
        assertEquals(4, result.get(0).especieId());
        assertEquals("Bovino", result.get(0).especie());
        assertEquals(2, result.get(0).edad());
        assertEquals(new BigDecimal("125.50"), result.get(0).peso());
    }

    @Test
    void buscarPorIdDevuelveAnimalExistente() {
        Animal animal = new Animal(especie(4, "Bovino"), 2, new BigDecimal("125.50"));
        animal.setId(7L);
        when(animalRepository.findById(7L)).thenReturn(Optional.of(animal));

        var result = animalService.buscarPorId(7L);

        assertEquals(7L, result.id());
        assertEquals("Bovino", result.especie());
    }

    @Test
    void buscarPorIdInexistenteDevuelveNotFound() {
        when(animalRepository.findById(404L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> animalService.buscarPorId(404L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void crearAsociaLaEspecieYGuardaElAnimal() {
        Especie especie = especie(4, "Bovino");
        when(especieRepository.findById(4)).thenReturn(Optional.of(especie));
        when(animalRepository.save(any(Animal.class))).thenAnswer(invocation -> {
            Animal saved = invocation.getArgument(0);
            saved.setId(7L);
            return saved;
        });

        var result = animalService.crear(
                new AnimalRequest(4, 2, new BigDecimal("125.50")));

        assertEquals(7L, result.id());
        assertEquals(4, result.especieId());
        assertEquals("Bovino", result.especie());
        assertEquals(2, result.edad());
        verify(animalRepository).save(any(Animal.class));
    }

    @Test
    void crearConEspecieInexistenteDevuelveNotFound() {
        when(especieRepository.findById(404)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> animalService.crear(
                        new AnimalRequest(404, 2, new BigDecimal("125.50"))));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(animalRepository, never()).save(any(Animal.class));
    }

    @Test
    void actualizarModificaElAnimalExistente() {
        Especie especieOriginal = especie(4, "Bovino");
        Especie especieNueva = especie(5, "Ovino");
        Animal animal = new Animal(especieOriginal, 2, new BigDecimal("125.50"));
        animal.setId(7L);

        when(animalRepository.findById(7L)).thenReturn(Optional.of(animal));
        when(especieRepository.findById(5)).thenReturn(Optional.of(especieNueva));
        when(animalRepository.save(any(Animal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = animalService.actualizar(
                7L,
                new AnimalRequest(5, 3, new BigDecimal("150.00")));

        assertEquals(7L, result.id());
        assertEquals(5, result.especieId());
        assertEquals("Ovino", result.especie());
        assertEquals(3, result.edad());
        assertEquals(new BigDecimal("150.00"), result.peso());
        verify(animalRepository).save(animal);
    }

    @Test
    void actualizarAnimalInexistenteDevuelveNotFound() {
        when(animalRepository.findById(404L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> animalService.actualizar(
                        404L,
                        new AnimalRequest(5, 3, new BigDecimal("150.00"))));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(animalRepository, never()).save(any(Animal.class));
    }

    @Test
    void eliminarAnimalExistente() {
        when(animalRepository.existsById(7L)).thenReturn(true);

        assertDoesNotThrow(() -> animalService.eliminar(7L));

        verify(animalRepository).deleteById(7L);
    }

    @Test
    void eliminarAnimalInexistenteDevuelveNotFound() {
        when(animalRepository.existsById(404L)).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> animalService.eliminar(404L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(animalRepository, never()).deleteById(anyLong());
    }

    private Especie especie(Integer id, String nombre) {
        Especie especie = new Especie(nombre);
        especie.setIdEspecie(id);
        return especie;
    }
}