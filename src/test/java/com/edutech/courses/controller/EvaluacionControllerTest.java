package com.edutech.courses.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edutech.courses.model.Evaluacion;
import com.edutech.courses.service.EvaluacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;


@WebMvcTest(EvaluacionController.class)
class EvaluacionControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private EvaluacionService evaluacionService;
    @Autowired private ObjectMapper objectMapper;
    private Evaluacion evaluacion;

    @BeforeEach
    void setUp() {
        evaluacion = Evaluacion.builder()
            .id(1L).nombre("Parcial 1").peso(25.0).build();
    }

    @Test
    void testListar() throws Exception {
        when(evaluacionService.listar()).thenReturn(List.of(evaluacion));
        mockMvc.perform(get("/api/evaluaciones"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Parcial 1"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(evaluacionService.buscarPorId(1L)).thenReturn(Optional.of(evaluacion));
        mockMvc.perform(get("/api/evaluaciones/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.peso").value(25.0));
    }

    @Test
    void testCrear() throws Exception {
        when(evaluacionService.crear(any())).thenReturn(evaluacion);
        mockMvc.perform(post("/api/evaluaciones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(evaluacion)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombre").value("Parcial 1"));
    }
}