package com.proyectofinal.sistemas.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
        "app.auth.username=testadmin",
        "app.auth.password=testpass",
        "app.jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY="
})
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void obtenerToken() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"testadmin","password":"testpass"}
                                """))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
        token = response.get("token").asString();
    }
    @Test
    void usuarioIncorrectoRecibeUnauthorized() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"otro-usuario","password":"testpass"}
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void openApiPublicaEsquemaBearerYEndpoints() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.components.securitySchemes.bearerAuth.scheme").value("bearer"))
                .andExpect(jsonPath("$.paths['/api/corrales'].get").exists());
    }

    @Test
    void crearYConsultarAnimalConJwt() throws Exception {
        String nombreEspecie = "Especie-" + UUID.randomUUID();

        MvcResult especieResult = mockMvc.perform(post("/api/especies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                java.util.Map.of("nombre", nombreEspecie))))
                .andExpect(status().isCreated())
                .andReturn();

        int especieId = objectMapper.readTree(especieResult.getResponse().getContentAsString())
                .get("idEspecie").asInt();

        MvcResult animalResult = mockMvc.perform(post("/animales")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"especieId":%d,"edad":3,"peso":450.50}
                                """.formatted(especieId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.especieId").value(especieId))
                .andReturn();

        long animalId = objectMapper.readTree(animalResult.getResponse().getContentAsString())
                .get("id").asLong();

        mockMvc.perform(get("/animales/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(animalId))
                .andExpect(jsonPath("$.especie").value(nombreEspecie));
    }

    @Test
    void escrituraSinJwtRespondeUnauthorized() throws Exception {
        mockMvc.perform(post("/api/especies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"SinToken"}
                                """))
                .andExpect(status().isUnauthorized());
    }
}
