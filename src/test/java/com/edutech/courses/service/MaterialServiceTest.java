package com.edutech.courses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.courses.model.Material;
import com.edutech.courses.repository.MaterialRepository;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock private MaterialRepository materialRepository;
    @InjectMocks private MaterialService materialService;
    private Material material;

    @BeforeEach
    void setUp() {
        material = Material.builder()
                .id(1L)
                .tipo("PDF")
                .url("http://recurso.com/pdf")
                .build();
    }

    @Test
    void testListar() {
        when(materialRepository.findAll()).thenReturn(List.of(material));
        assertFalse(materialService.listar().isEmpty());
    }

    @Test
    void testPorCurso() {
        when(materialRepository.findByCursoId(1L)).thenReturn(List.of(material));
        assertFalse(materialService.porCurso(1L).isEmpty());
    }

    @Test
    void testPorEvaluacion() {
        when(materialRepository.findByEvaluacionId(1L)).thenReturn(List.of(material));
        assertFalse(materialService.porEvaluacion(1L).isEmpty());
    }

    @Test
    void testCrear() {
        when(materialRepository.save(any())).thenReturn(material);
        assertEquals("PDF", materialService.crear(material).getTipo());
    }

    @Test
    void testEliminar() {
        materialService.eliminar(1L);
        verify(materialRepository).deleteById(1L);
    }
}
