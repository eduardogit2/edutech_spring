package com.edutech.courses.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edutech.courses.model.Nota;
import com.edutech.courses.service.NotaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(NotaController.class)
class NotaControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private NotaService notaService;
    @Autowired private ObjectMapper objectMapper;
    private Nota nota;

    @BeforeEach
    void setUp() {
        nota = Nota.builder()
            .id(1L)
            .calificacion(6.5)
            .build();
    }

    @Test
    void testRegistrar() throws Exception {
        when(notaService.registrar(any())).thenReturn(nota);
        mockMvc.perform(post("/api/notas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nota)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.calificacion").value(6.5));
    }

    @Test
    void testPorAlumno() throws Exception {
        when(notaService.porAlumno(1L)).thenReturn(List.of(nota));
        mockMvc.perform(get("/api/notas/alumno/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].calificacion").value(6.5));
    }
}
