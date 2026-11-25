package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.EspecieRequest;
import com.proyectofinal.sistemas.dto.EspecieResponse;
import com.proyectofinal.sistemas.models.Especie;
import com.proyectofinal.sistemas.repositories.AnimalRepository;
import com.proyectofinal.sistemas.repositories.EspecieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class EspecieService {

   private final EspecieRepository especieRepository;
   private final AnimalRepository animalRepository;

   public EspecieService(EspecieRepository especieRepository, AnimalRepository animalRepository) {
    this.especieRepository = especieRepository;
    this.animalRepository = animalRepository;
   }


   @Transactional(readOnly = true)
   public List<EspecieResponse> listarTodas() {
    return especieRepository.findAll().stream().map(this::toResponse).toList();
   }


   @Transactional(readOnly = true)
   public EspecieResponse buscarPorId(Integer id) {
    return especieRepository.findById(id)
           .map(this::toResponse)
           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especie no encontrada"));
   }

   @Transactional
   public EspecieResponse crear(EspecieRequest request) {
    return toResponse(especieRepository.save(new Especie(request.nombre().trim())));
   }

   @Transactional
   public EspecieResponse actualizar (Integer id, EspecieRequest request) {
    Especie especie = especieRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especie no encontrada"));
    especie.setNombre(request.nombre().trim());
    return toResponse(especieRepository.save(especie));
   }

   @Transactional
   public void eliminar(Integer id) {
    if (!especieRepository.existsById(id)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especie no encontrada");
    }
    if (animalRepository.existsByEspecie_IdEspecie(id)) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "No se eliminar una especie con animales asociados");
    }
    especieRepository.deleteById(id);
   }

   private EspecieResponse toResponse(Especie especie) {
    return new EspecieResponse(especie.getIdEspecie(), especie.getNombre());
   }
}