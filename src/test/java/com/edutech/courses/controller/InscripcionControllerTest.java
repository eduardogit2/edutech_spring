package com.edutech.courses.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.courses.model.Inscripcion;
import com.edutech.courses.service.InscripcionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;


@WebMvcTest(InscripcionController.class)
class InscripcionControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private InscripcionService inscripcionService;
    @Autowired private ObjectMapper objectMapper;
    private Inscripcion inscripcion;

    @BeforeEach
    void setUp() {
        inscripcion = Inscripcion.builder()
            .id(1L)
            .estado(Inscripcion.EstadoInscripcion.MATRICULADO).build();
    }

    @Test
    void testListar() throws Exception {
        when(inscripcionService.listar()).thenReturn(List.of(inscripcion));
        mockMvc.perform(get("/api/inscripciones"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].estado").value("MATRICULADO"));
    }

    @Test
    void testCrear() throws Exception {
        when(inscripcionService.crear(any())).thenReturn(inscripcion);
        mockMvc.perform(post("/api/inscripciones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inscripcion)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.estado").value("MATRICULADO"));
    }
}