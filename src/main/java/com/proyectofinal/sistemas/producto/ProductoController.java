package com.proyectofinal.sistemas.producto;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.sistemas.models.Producto;
import com.proyectofinal.sistemas.repositories.ProductoRepository;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    @PostMapping
    public Producto addProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }
}
