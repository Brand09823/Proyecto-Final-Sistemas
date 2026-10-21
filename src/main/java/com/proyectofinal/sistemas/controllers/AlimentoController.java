package com.proyectofinal.sistemas.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import com.proyectofinal.sistemas.dto.AlimentoRequest;
import com.proyectofinal.sistemas.models.Alimento;
import com.proyectofinal.sistemas.models.Nutriente;
import com.proyectofinal.sistemas.repositories.AlimentoRepository;
import com.proyectofinal.sistemas.repositories.NutrienteRepository;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    private final AlimentoRepository alimentoRepository;
    private final NutrienteRepository nutrienteRepository;

    public AlimentoController(
            AlimentoRepository alimentoRepository,
            NutrienteRepository nutrienteRepository) {
        this.alimentoRepository = alimentoRepository;
        this.nutrienteRepository = nutrienteRepository;
    }

    @GetMapping
    public List<Alimento> findAll() {
        return alimentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Alimento findById(@PathVariable Long id) {
        return alimentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Alimento> create(@Valid @RequestBody AlimentoRequest request) {
        Alimento alimento = new Alimento();
        applyRequest(alimento, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(alimentoRepository.save(alimento));
    }

    @PutMapping("/{id}")
    public Alimento update(@PathVariable Long id, @Valid @RequestBody AlimentoRequest request) {
        Alimento alimento = findById(id);
        applyRequest(alimento, request);
        return alimentoRepository.save(alimento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Alimento alimento = findById(id);
        alimentoRepository.delete(alimento);
        return ResponseEntity.noContent().build();
    }

    private void applyRequest(Alimento alimento, AlimentoRequest request) {
        Set<Nutriente> nutrientes = nutrienteRepository.findAllById(request.getNutrientes())
                .stream()
                .collect(Collectors.toSet());
        if (nutrientes.size() != request.getNutrientes().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uno o más nutrientes no existen");
        }
        alimento.setNombre(request.getNombre().trim());
        alimento.setDescripcion(request.getDescripcion());
        alimento.setNutrientes(nutrientes);
    }
}
