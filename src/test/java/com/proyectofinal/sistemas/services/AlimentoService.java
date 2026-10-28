package com.proyectofinal.sistemas.services;

import com.proyectofinal.sistemas.models.Alimento;
import com.proyectofinal.sistemas.repositories.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    public List<Alimento> listarTodos() { return alimentoRepository.findAll(); }
    public Optional<Alimento> buscarPorId(Integer id) { return alimentoRepository.findById(id); }
    public Alimento guardar(Alimento alimento) { return alimentoRepository.save(alimento); }
    
    public Alimento actualizar(Integer id, Alimento detalles) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));
        alimento.setNombreAlimento(detalles.getNombreAlimento());
        return alimentoRepository.save(alimento);
    }

    public void eliminar(Integer id) { alimentoRepository.deleteById(id); }
}