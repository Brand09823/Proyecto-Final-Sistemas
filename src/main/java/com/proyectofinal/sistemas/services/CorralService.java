package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.CorralRequest;
import com.proyectofinal.sistemas.dto.CorralResponse;
import com.proyectofinal.sistemas.models.Corral;
import com.proyectofinal.sistemas.repositories.CorralRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CorralService {
    private final CorralRepository corralRepository;

public CorralService(CorralRepository corralRepository) {
  this.corralRepository = corralRepository;
}

@Transactional(readOnly = true) 
public List<CorralResponse> listarTodos() {
    return corralRepository.findAll().stream()
           .map(this:: toResponse)
           .toList();
}   
@Transactional(readOnly = true) 
public CorralResponse buscarPorId(Integer id) {
    return corralRepository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "corral no encontrado"));
}   
@Transactional
    public CorralResponse crear(CorralRequest request) {
        Corral corral = new Corral(
                request.nombreCorral().trim(),
                request.capacidad());
        return toResponse(corralRepository.save(corral));
    }

    @Transactional
    public CorralResponse actualizar(Integer id, CorralRequest request) {
        Corral corral = corralRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Corral no encontrado"));

        corral.setNombreCorral(request.nombreCorral().trim());
        corral.setCapacidad(request.capacidad());
        return toResponse(corralRepository.save(corral));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!corralRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corral no encontrado");
        }
        corralRepository.deleteById(id);
    }

    private CorralResponse toResponse(Corral corral) {
        return new CorralResponse(
                corral.getIdCorral(),
                corral.getNombreCorral(),
                corral.getCapacidad());
    }
}