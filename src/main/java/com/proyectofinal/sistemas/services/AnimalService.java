package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.AnimalRequest;
import com.proyectofinal.sistemas.dto.AnimalResponse;
import com.proyectofinal.sistemas.models.Animal;
import com.proyectofinal.sistemas.models.Especie;
import com.proyectofinal.sistemas.repositories.AnimalRepository;
import com.proyectofinal.sistemas.repositories.EspecieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final EspecieRepository especieRepository;

    public AnimalService(AnimalRepository animalRepository, EspecieRepository especieRepository) {
        this.animalRepository = animalRepository;
        this.especieRepository = especieRepository;

    }

    @Transactional(readOnly = true)
    public List<AnimalResponse> listarTodos() {
        return animalRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AnimalResponse buscarPorId(Long id) {
        return animalRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal no encontrado"));

    }

    @Transactional
    public AnimalResponse crear(AnimalRequest request) {
        Animal animal = new Animal(
                buscarEspecie(request.especieId()),
                request.edad(),
                request.peso());
        return toResponse(animalRepository.save(animal));
    }

    @Transactional
    public AnimalResponse actualizar(Long id, AnimalRequest request) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal no encontrado"));
        animal.setEspecie(buscarEspecie(request.especieId()));
        animal.setEdad(request.edad());
        animal.setPeso(request.peso());
        return toResponse(animalRepository.save(animal));
    }

   @Transactional
    public void eliminar(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal no encontrado");
        }
        animalRepository.deleteById(id);
    }

    private Especie buscarEspecie(Integer id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especie no encontrada"));
    }

    private AnimalResponse toResponse(Animal animal) {
        return new AnimalResponse(
                animal.getId(),
                animal.getEspecie().getIdEspecie(),
                animal.getEspecie().getNombre(),
                animal.getEdad(),
                animal.getPeso()
        );
    }
}