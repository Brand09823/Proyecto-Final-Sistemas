package com.proyectofinal.sistemas.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.proyectofinal.sistemas.repositories.AnimalRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimalRepository animalRepository;

    @BeforeEach
    void cleanDatabase() {
        animalRepository.deleteAll();
    }

    @Test
    void helloWorldEndpointIsAvailable() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    @Test
    void postAnimalPersistsAndGetReturnsIt() throws Exception {
        mockMvc.perform(post("/animales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Bovino","edad":3,"peso":450.5}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.especie").value("Bovino"));

        mockMvc.perform(get("/animales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].especie").value("Bovino"))
                .andExpect(jsonPath("$[0].peso").value(450.5));
    }

    @Test
    void postAnimalRejectsNonPositiveAgeAndWeight() throws Exception {
        mockMvc.perform(post("/animales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Bovino","edad":0,"peso":450.5}
                                """))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/animales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Bovino","edad":3,"peso":0}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByIdUpdateAndDeleteAnimal() throws Exception {
        mockMvc.perform(post("/animales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Bovino","edad":3,"peso":450.5}
                                """))
                .andExpect(status().isCreated());

        Long id = animalRepository.findAll().get(0).getId();

        mockMvc.perform(get("/animales/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especie").value("Bovino"));

        mockMvc.perform(put("/animales/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Caprino","edad":4,"peso":52.5}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.especie").value("Caprino"))
                .andExpect(jsonPath("$.edad").value(4))
                .andExpect(jsonPath("$.peso").value(52.5));

        mockMvc.perform(delete("/animales/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/animales/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUpdateAndDeleteReturnNotFoundForUnknownAnimal() throws Exception {
        mockMvc.perform(get("/animales/{id}", 999L))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/animales/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especie":"Caprino","edad":4,"peso":52.5}
                                """))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/animales/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
