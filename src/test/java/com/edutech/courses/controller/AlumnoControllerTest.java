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

import com.edutech.courses.model.Alumno;
import com.edutech.courses.service.AlumnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;


@WebMvcTest(AlumnoController.class)
class AlumnoControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private AlumnoService alumnoService;
    @Autowired private ObjectMapper objectMapper;
    private Alumno alumno;

    @BeforeEach
    void setUp() {
        alumno = Alumno.builder()
            .id(1L)
            .run("12.345.678-9")
            .nombre("Juan")
            .email("juan@correo.com").build();
    }

    @Test
    void testListar() throws Exception {
        when(alumnoService.listar()).thenReturn(List.of(alumno));
        mockMvc.perform(get("/api/alumnos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].run").value("12.345.678-9"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(alumnoService.buscarPorId(1L)).thenReturn(Optional.of(alumno));
        mockMvc.perform(get("/api/alumnos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.run").value("12.345.678-9"));
    }

    @Test
    void testCrear() throws Exception {
        when(alumnoService.crear(any())).thenReturn(alumno);
        mockMvc.perform(post("/api/alumnos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(alumno)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.run").value("12.345.678-9"));
    }
}