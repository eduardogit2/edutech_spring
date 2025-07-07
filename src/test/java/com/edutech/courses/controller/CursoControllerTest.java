package com.edutech.courses.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.edutech.courses.model.Coordinador;
import com.edutech.courses.model.Curso;
import com.edutech.courses.model.Profesor;
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
            .id(1L)
            .codigo("CUR123")
            .titulo("Matemáticas")
            .activo(true)
            .build();
    }

    @Test
    void testListar() throws Exception {
        when(cursoService.listar()).thenReturn(List.of(curso));
        mockMvc.perform(get("/api/cursos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].codigo").value("CUR123"));
    }

    @Test
    void testBuscarPorId_Existente() throws Exception {
        when(cursoService.buscarPorId(1L)).thenReturn(Optional.of(curso));
        mockMvc.perform(get("/api/cursos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigo").value("CUR123"));
    }

    @Test
    void testBuscarPorId_NoExistente() throws Exception {
        when(cursoService.buscarPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/cursos/2"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCrear_Valido() throws Exception {
        Profesor profesor = Profesor.builder()
            .id(1L)
            .run("12.345.678-9")
            .nombre("Pedro Profesor")
            .email("pedro@correo.com")
            .build();

        Coordinador coordinador = Coordinador.builder()
            .id(1L)
            .run("22.222.222-2")
            .nombre("Ana Coordinadora")
            .email("ana@correo.com")
            .build();

        Curso cursoValido = Curso.builder()
            .id(1L)
            .codigo("CUR123")
            .titulo("Matrices Avanzadas")
            .descripcion("Curso de matrices")
            .materia("Matemáticas")
            .nivel("Avanzado")
            .creditos(8)
            .modalidad("Presencial")
            .cuposMaximos(40)
            .fechaInicio(LocalDate.now().plusDays(1)) 
            .fechaFin(LocalDate.now().plusMonths(5))  
            .activo(true)
            .profesor(profesor)
            .coordinador(coordinador)
            .build();

        when(cursoService.crear(any())).thenReturn(cursoValido);

        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoValido)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.codigo").value("CUR123"))
            .andExpect(jsonPath("$.titulo").value("Matrices Avanzadas"));
    }

    @Test
    void testCrear_CodigoInvalido() throws Exception {
        Curso cursoInvalido = Curso.builder()
            .id(1L)
            .codigo("")
            .titulo("Matemáticas")
            .activo(true)
            .build();

        mockMvc.perform(post("/api/cursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cursoInvalido)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testEliminar_Existente() throws Exception {
        when(cursoService.buscarPorId(1L)).thenReturn(Optional.of(curso));

        mockMvc.perform(delete("/api/cursos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testEliminar_NoExistente() throws Exception {
        when(cursoService.buscarPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/cursos/2"))
            .andExpect(status().isNotFound());
    }
}
