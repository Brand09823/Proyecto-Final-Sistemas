package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.NutrienteRequest;
import com.proyectofinal.sistemas.dto.NutrienteResponse;
import com.proyectofinal.sistemas.models.Nutriente;
import com.proyectofinal.sistemas.repositories.AlimentoRepository;
import com.proyectofinal.sistemas.repositories.NutrienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NutrienteService {

    private final NutrienteRepository nutrienteRepository;
    private final AlimentoRepository alimentoRepository;

    public NutrienteService(NutrienteRepository nutrienteRepository, AlimentoRepository alimentoRepository) {
        this.nutrienteRepository = nutrienteRepository;
        this.alimentoRepository = alimentoRepository;
    }

    @Transactional(readOnly = true)
    public List<NutrienteResponse> listarTodos() {
        return nutrienteRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public NutrienteResponse buscarPorId(Integer id) {
        return nutrienteRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nutriente no encontrado"));
    }

    @Transactional
    public NutrienteResponse crear(NutrienteRequest request) {
        return toResponse(nutrienteRepository.save(new Nutriente(request.nombreNutriente().trim())));
    }

    @Transactional
    public NutrienteResponse actualizar(Integer id, NutrienteRequest request) {
        Nutriente nutriente = nutrienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nutriente no encontrado"));
        nutriente.setNombreNutriente(request.nombreNutriente().trim());
        return toResponse(nutrienteRepository.save(nutriente));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!nutrienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nutriente no encontrado");
        }
        if (alimentoRepository.existsByNutrientes_IdNutriente(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar un nutriente asociado a alimentos");
        }
        nutrienteRepository.deleteById(id);
    }

    private NutrienteResponse toResponse(Nutriente nutriente) {
        return new NutrienteResponse(nutriente.getIdNutriente(), nutriente.getNombreNutriente());
    }
}