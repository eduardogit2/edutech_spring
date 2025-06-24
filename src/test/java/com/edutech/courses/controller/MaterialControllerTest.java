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

import com.edutech.courses.model.Material;
import com.edutech.courses.service.MaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MaterialController.class)
class MaterialControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private MaterialService materialService;
    @Autowired private ObjectMapper objectMapper;
    private Material material;

    @BeforeEach
    void setUp() {
        material = Material.builder()
            .id(1L)
            .tipo("PDF")
            .url("https://material.com/recurso.pdf")
            .build();
    }

    @Test
    void testListar() throws Exception {
        when(materialService.listar()).thenReturn(List.of(material));
        mockMvc.perform(get("/api/materiales"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tipo").value("PDF"));
    }

    @Test
    void testCrear() throws Exception {
        when(materialService.crear(any())).thenReturn(material);
        mockMvc.perform(post("/api/materiales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(material)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.tipo").value("PDF"));
    }
}