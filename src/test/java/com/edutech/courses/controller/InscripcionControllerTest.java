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

import com.edutech.courses.model.Alumno;
import com.edutech.courses.model.Curso;
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
        var curso = Curso.builder()
            .id(1L)
            .codigo("CUR001")
            .titulo("Matemáticas")
            .materia("Matemáticas")
            .nivel("Básico")
            .creditos(4)
            .modalidad("Presencial")
            .cuposMaximos(30)
            .activo(true)
            .build();

        var alumno = Alumno.builder()
            .id(1L)
            .run("12.345.678-9")
            .nombre("Juan Pérez")
            .email("juan.perez@example.com")
            .build();

        inscripcion = Inscripcion.builder()
            .id(1L)
            .fechaInscripcion(java.time.LocalDate.now())
            .estado(Inscripcion.EstadoInscripcion.MATRICULADO)
            .curso(curso)
            .alumno(alumno)
            .build();
    }

    @Test
    void testListar() throws Exception {
        when(inscripcionService.listar()).thenReturn(List.of(inscripcion));
        mockMvc.perform(get("/api/inscripciones"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].estado").value("MATRICULADO"));
    }

    @Test
    void testListar_Vacio() throws Exception {
        when(inscripcionService.listar()).thenReturn(List.of());
        mockMvc.perform(get("/api/inscripciones"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testCrear_Valido() throws Exception {
        when(inscripcionService.crear(any())).thenReturn(inscripcion);
        mockMvc.perform(post("/api/inscripciones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inscripcion)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.estado").value("MATRICULADO"));
    }

    @Test
    void testCrear_EstadoNulo_BadRequest() throws Exception {
        Inscripcion inscripcionInvalida = Inscripcion.builder()
            .id(2L)
            .estado(null)
            .build();

        mockMvc.perform(post("/api/inscripciones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inscripcionInvalida)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testBuscarPorId_Existente() throws Exception {
        when(inscripcionService.buscarPorId(1L)).thenReturn(java.util.Optional.of(inscripcion));
        mockMvc.perform(get("/api/inscripciones/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("MATRICULADO"));
    }

    @Test
    void testBuscarPorId_NoExistente() throws Exception {
        when(inscripcionService.buscarPorId(99L)).thenReturn(java.util.Optional.empty());
        mockMvc.perform(get("/api/inscripciones/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar_Existente() throws Exception {
        when(inscripcionService.buscarPorId(1L)).thenReturn(java.util.Optional.of(inscripcion));
        mockMvc.perform(delete("/api/inscripciones/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testEliminar_NoExistente() throws Exception {
        when(inscripcionService.buscarPorId(99L)).thenReturn(java.util.Optional.empty());
        mockMvc.perform(delete("/api/inscripciones/99"))
            .andExpect(status().isNotFound());
    }
}