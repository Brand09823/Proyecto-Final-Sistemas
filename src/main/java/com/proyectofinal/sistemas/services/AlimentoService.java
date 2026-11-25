package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.dto.AlimentoRequest;
import com.proyectofinal.sistemas.dto.AlimentoResponse;
import com.proyectofinal.sistemas.dto.NutrienteResponse;
import com.proyectofinal.sistemas.models.Alimento;
import com.proyectofinal.sistemas.models.Nutriente;
import com.proyectofinal.sistemas.repositories.AlimentoRepository;
import com.proyectofinal.sistemas.repositories.NutrienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;
    private final NutrienteRepository nutrienteRepository;

    public AlimentoService(AlimentoRepository alimentoRepository, NutrienteRepository nutrienteRepository) {
        this.alimentoRepository = alimentoRepository;
        this.nutrienteRepository = nutrienteRepository;
    }

    @Transactional(readOnly = true)
    public List<AlimentoResponse> listarTodos() {
        return alimentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AlimentoResponse buscarPorId(Integer id) {
        return alimentoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alimento no encontrado"));
    }

    @Transactional
    public AlimentoResponse crear(AlimentoRequest request) {
        Alimento alimento = new Alimento(request.nombreAlimento().trim());
        alimento.setNutrientes(buscarNutrientes(request.nutrientesIds()));
        return toResponse(alimentoRepository.save(alimento));
    }

    @Transactional
    public AlimentoResponse actualizar(Integer id, AlimentoRequest request) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alimento no encontrado"));
        alimento.setNombreAlimento(request.nombreAlimento().trim());
        alimento.setNutrientes(buscarNutrientes(request.nutrientesIds()));
        return toResponse(alimentoRepository.save(alimento));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!alimentoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alimento no encontrado");
        }
        alimentoRepository.deleteById(id);
    }

    private List<Nutriente> buscarNutrientes(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        Set<Integer> idsUnicos = new LinkedHashSet<>(ids);
        List<Nutriente> nutrientes = nutrienteRepository.findAllById(idsUnicos);
        if (nutrientes.size() != idsUnicos.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Uno o más nutrientes no existen");
        }
        return nutrientes;
    }

    private AlimentoResponse toResponse(Alimento alimento) {
        List<NutrienteResponse> nutrientes = alimento.getNutrientes().stream()
                .map(n -> new NutrienteResponse(n.getIdNutriente(), n.getNombreNutriente()))
                .toList();
        return new AlimentoResponse(alimento.getIdAlimento(), alimento.getNombreAlimento(), nutrientes);
    }
}