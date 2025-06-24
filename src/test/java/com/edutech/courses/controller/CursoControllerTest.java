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

import com.edutech.courses.model.Curso;
import com.edutech.courses.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;


@WebMvcTest(CursoController.class)
class CursoControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private CursoService cursoService;
    @Autowired private ObjectMapper objectMapper;
    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = Curso.builder()
            .id(1L).codigo("CUR123").titulo("Matemáticas").activo(true).build();
    }

    @Test
    void testListar() throws Exception {
        when(cursoService.listar()).thenReturn(List.of(curso));
        mockMvc.perform(get("/api/cursos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].codigo").value("CUR123"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(cursoService.buscarPorId(1L)).thenReturn(Optional.of(curso));
        mockMvc.perform(get("/api/cursos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigo").value("CUR123"));
    }

    @Test
    void testCrear() throws Exception {
        when(cursoService.crear(any())).thenReturn(curso);
        mockMvc.perform(post("/api/cursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(curso)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.codigo").value("CUR123"));
    }
}