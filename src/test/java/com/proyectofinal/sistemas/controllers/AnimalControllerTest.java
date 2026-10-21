package com.proyectofinal.sistemas.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.proyectofinal.sistemas.repositories.AnimalRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
