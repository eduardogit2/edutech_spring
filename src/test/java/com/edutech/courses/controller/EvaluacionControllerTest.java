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
import com.edutech.courses.model.Evaluacion;
import com.edutech.courses.model.Profesor;
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
            .id(1L)
            .nombre("Parcial 1")
            .peso(25.0)
            .build();
    }

    @Test
    void testListar() throws Exception {
        when(evaluacionService.listar()).thenReturn(List.of(evaluacion));
        mockMvc.perform(get("/api/evaluaciones"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Parcial 1"));
    }

    @Test
    void testBuscarPorId_Existente() throws Exception {
        when(evaluacionService.buscarPorId(1L)).thenReturn(Optional.of(evaluacion));
        mockMvc.perform(get("/api/evaluaciones/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.peso").value(25.0));
    }

    @Test
    void testBuscarPorId_NoExistente() throws Exception {
        when(evaluacionService.buscarPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/evaluaciones/2"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCrear_Valido() throws Exception {
        Curso curso = Curso.builder()
            .id(1L)
            .codigo("CUR456")
            .titulo("Álgebra Lineal")
            .materia("Matemáticas")
            .nivel("Intermedio")
            .creditos(6)
            .modalidad("Presencial")
            .cuposMaximos(30)
            .fechaInicio(LocalDate.now().plusDays(1))
            .fechaFin(LocalDate.now().plusMonths(3))
            .activo(true)
            .profesor(Profesor.builder()
                .id(1L)
                .run("11.111.111-1")
                .nombre("Profesor X")
                .email("profesor@correo.com")
                .build())
            .coordinador(Coordinador.builder()
                .id(1L)
                .run("22.222.222-2")
                .nombre("Coordinadora Y")
                .email("coordinadora@correo.com")
                .build())
            .build();

        Evaluacion evaluacionValida = Evaluacion.builder()
            .id(1L)
            .nombre("Examen Parcial 1")
            .descripcion("Contenidos: Unidades 1 a 3.")
            .fecha(LocalDate.now().plusDays(10)) 
            .peso(25.0)
            .curso(curso)
            .build();

        when(evaluacionService.crear(any())).thenReturn(evaluacionValida);

        mockMvc.perform(post("/api/evaluaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evaluacionValida)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombre").value("Examen Parcial 1"));
    }

    @Test
    void testCrear_NombreInvalido() throws Exception {
        Evaluacion evaluacionInvalida = Evaluacion.builder()
            .id(1L)
            .nombre("")  
            .peso(25.0)
            .build();
        mockMvc.perform(post("/api/evaluaciones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(evaluacionInvalida)))
            .andExpect(status().isBadRequest());
    }

    
    @Test
    void testEliminar_Existente() throws Exception {
        when(evaluacionService.buscarPorId(1L)).thenReturn(Optional.of(evaluacion));
        mockMvc.perform(delete("/api/evaluaciones/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testEliminar_NoExistente() throws Exception {
        when(evaluacionService.buscarPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/evaluaciones/2"))
            .andExpect(status().isNotFound());
    }
}
