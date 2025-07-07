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

import com.edutech.courses.model.Profesor;
import com.edutech.courses.service.ProfesorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;


@WebMvcTest(ProfesorController.class)
class ProfesorControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private ProfesorService profesorService;
    @Autowired private ObjectMapper objectMapper;
    private Profesor profesor;

    @BeforeEach
    void setUp() {
        profesor = Profesor.builder()
            .id(1L)
            .run("11.111.111-1")
            .nombre("Ana")
            .apellidoPaterno("González")
            .email("ana@correo.cl")
            .especialidad("Ciencias de la Computación")
            .gradoAcademico("Doctorado en IA")
            .build();

    }

    @Test
    void testListar() throws Exception {
        when(profesorService.listar()).thenReturn(List.of(profesor));
        mockMvc.perform(get("/api/profesores"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].run").value("11.111.111-1"));
    }

    @Test
    void testListar_Vacio() throws Exception {
        when(profesorService.listar()).thenReturn(List.of());
        mockMvc.perform(get("/api/profesores"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testBuscarPorId_Existente() throws Exception {
        when(profesorService.buscarPorId(1L)).thenReturn(Optional.of(profesor));
        mockMvc.perform(get("/api/profesores/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    void testBuscarPorId_NoExistente() throws Exception {
        when(profesorService.buscarPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/profesores/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCrear_Valido() throws Exception {
        when(profesorService.crear(any())).thenReturn(profesor);
        mockMvc.perform(post("/api/profesores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(profesor)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.run").value("11.111.111-1"));
    }

    @Test
    void testCrear_RunNulo_BadRequest() throws Exception {
        Profesor invalid = Profesor.builder()
            .id(2L)
            .run(null)
            .nombre("Pedro")
            .email("pedro@correo.cl")
            .build();

        mockMvc.perform(post("/api/profesores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalid)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testEliminar_Existente() throws Exception {
        when(profesorService.buscarPorId(1L)).thenReturn(Optional.of(profesor));
        mockMvc.perform(delete("/api/profesores/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testEliminar_NoExistente() throws Exception {
        when(profesorService.buscarPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/profesores/99"))
            .andExpect(status().isNotFound());
    }
}