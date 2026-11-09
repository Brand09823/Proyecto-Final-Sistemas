package com.proyectofinal.sistemas.controllers;

import com.proyectofinal.sistemas.models.Alimento;
import com.proyectofinal.sistemas.services.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping
    public List<Alimento> obtenerTodos() { return alimentoService.listarTodos(); }

    @GetMapping("/{id}")
    public Optional<Alimento> obtenerPorId(@PathVariable Integer id) {
        return alimentoService.buscarPorId(id);
    }

    @PostMapping
    public Alimento crearAlimento(@RequestBody Alimento alimento) {
        return alimentoService.guardar(alimento);
    }

    @PutMapping("/{id}")
    public Alimento actualizarAlimento(@PathVariable Integer id, @RequestBody Alimento detalles) {
        return alimentoService.actualizar(id, detalles);
    }


    @DeleteMapping("/{id}")
    public void eliminarAlimento(@PathVariable Integer id) {
        alimentoService.eliminar(id);
    }

}
