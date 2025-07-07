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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edutech.courses.model.Coordinador;
import com.edutech.courses.service.CoordinadorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CoordinadorController.class)
class CoordinadorControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private CoordinadorService coordinadorService;
    @Autowired private ObjectMapper objectMapper;
    private Coordinador coordinador;

    @BeforeEach
    void setUp() {
        coordinador = Coordinador.builder()
            .id(1L)
            .run("18.345.678-2")
            .nombre("Carlos")
            .email("carlos@correo.com")
            .build();
    }

    @Test
    void testListar() throws Exception {
        when(coordinadorService.listar()).thenReturn(List.of(coordinador));
        mockMvc.perform(get("/api/coordinadores"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].run").value("18.345.678-2"));
    }

    @Test
    void testBuscarPorId_Existente() throws Exception {
        when(coordinadorService.buscarPorId(1L)).thenReturn(Optional.of(coordinador));
        mockMvc.perform(get("/api/coordinadores/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.run").value("18.345.678-2"));
    }

    @Test
    void testBuscarPorId_NoExistente() throws Exception {
        when(coordinadorService.buscarPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/coordinadores/2"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCrear_Valido() throws Exception {
        when(coordinadorService.crear(any())).thenReturn(coordinador);
        mockMvc.perform(post("/api/coordinadores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(coordinador)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.run").value("18.345.678-2"));
    }

    @Test
    void testCrear_RunInvalido() throws Exception {
        Coordinador invalido = Coordinador.builder()
            .run("")
            .nombre("Carlos")
            .email("carlos@correo.com")
            .build();
        mockMvc.perform(post("/api/coordinadores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalido)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testEliminar_Existente() throws Exception {
        when(coordinadorService.buscarPorId(1L)).thenReturn(Optional.of(coordinador));
            mockMvc.perform(delete("/api/coordinadores/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminar_NoExistente() throws Exception {
        when(coordinadorService.buscarPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/coordinadores/{id}", 2L)) 
            .andExpect(status().isNotFound());
    }
}
