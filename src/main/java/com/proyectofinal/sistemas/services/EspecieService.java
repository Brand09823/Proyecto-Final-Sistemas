package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.models.Especie;
import com.proyectofinal.sistemas.repositories.EspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    public List<Especie> listarTodas() { return especieRepository.findAll(); }
    public Optional<Especie> buscarPorId(Integer id) { return especieRepository.findById(id); }
    public Especie guardar(Especie especie) { return especieRepository.save(especie); }
    
    public Especie actualizar(Integer id, Especie detalles) {
        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));
        especie.setNombre(detalles.getNombre());
        return especieRepository.save(especie);
    }

    public void eliminar(Integer id) { especieRepository.deleteById(id); }
}