package com.proyectofinal.sistemas.controllers;

import com.proyectofinal.sistemas.models.Especie;
import com.proyectofinal.sistemas.services.EspecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    @Autowired
    private EspecieService especieService;

    @GetMapping
    public List<Especie> obtenerTodas() { return especieService.listarTodas(); }

    @GetMapping("/{id}")
    public Optional<Especie> obtenerPorId(@PathVariable Integer id) { return especieService.buscarPorId(id); }

    @PostMapping
    public Especie crearEspecie(@RequestBody Especie especie) { return especieService.guardar(especie); }

    @PutMapping("/{id}")
    public Especie actualizarEspecie(@PathVariable Integer id, @RequestBody Especie detalles) {
        return especieService.actualizar(id, detalles);
    }

    @DeleteMapping("/{id}")
    public void eliminarEspecie(@PathVariable Integer id) { especieService.eliminar(id); }
}