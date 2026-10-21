package com.proyectofinal.sistemas.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.proyectofinal.sistemas.repositories.AlimentoRepository;
import com.proyectofinal.sistemas.repositories.AnimalRepository;
import com.proyectofinal.sistemas.repositories.NutrienteRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GranjaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private NutrienteRepository nutrienteRepository;

    @BeforeEach
    void cleanDatabase() {
        alimentoRepository.deleteAll();
        animalRepository.deleteAll();
        nutrienteRepository.deleteAll();
    }

    @Test
    void createsAnimalAndRejectsInvalidWeight() throws Exception {
        mockMvc.perform(post("/animales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Bovino","edad":3,"peso":450.5}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.especie").value("Bovino"));

        Long animalId = animalRepository.findAll().get(0).getId();
        mockMvc.perform(get("/animales/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.peso").value(450.5));

        mockMvc.perform(put("/animales/{id}", animalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Bovino","edad":4,"peso":470}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.edad").value(4));

        mockMvc.perform(delete("/animales/{id}", animalId))
                .andExpect(status().isNoContent());

        mockMvc.perform(post("/animales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Bovino","edad":3,"peso":0}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createsFoodWithExistingNutrientsAndSupportsDelete() throws Exception {
        mockMvc.perform(post("/nutrientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Proteína","descripcion":"Aporte proteico"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());

        Long nutrienteId = nutrienteRepository.findAll().get(0).getId();
        mockMvc.perform(post("/alimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Alfalfa","descripcion":"Forraje","nutrientes":[%d]}
                                """.formatted(nutrienteId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Alfalfa"))
                .andExpect(jsonPath("$.nutrientes[0].nombre").value("Proteína"));

        Long alimentoId = alimentoRepository.findAll().get(0).getId();
        mockMvc.perform(get("/alimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nutrientes[0].nombre").value("Proteína"));

        mockMvc.perform(delete("/nutrientes/{id}", nutrienteId))
                .andExpect(status().isConflict());

        mockMvc.perform(put("/alimentos/{id}", alimentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Alfalfa fresca","descripcion":"Forraje","nutrientes":[%d]}
                                """.formatted(nutrienteId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Alfalfa fresca"));

        mockMvc.perform(delete("/alimentos/{id}", alimentoId))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/nutrientes/{id}", nutrienteId))
                .andExpect(status().isNoContent());
    }
}
