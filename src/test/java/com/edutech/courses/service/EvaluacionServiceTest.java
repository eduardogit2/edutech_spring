package com.edutech.courses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.courses.model.Evaluacion;
import com.edutech.courses.repository.EvaluacionRepository;

@ExtendWith(MockitoExtension.class)
class EvaluacionServiceTest {

    @Mock private EvaluacionRepository evaluacionRepository;
    @InjectMocks private EvaluacionService evaluacionService;
    private Evaluacion evaluacion;

    @BeforeEach
    void setUp() {
        evaluacion = Evaluacion.builder()
                .id(1L)
                .nombre("Prueba Final")
                .peso(30.0)
                .build();
    }

    @Test
    void testListar() {
        when(evaluacionRepository.findAll()).thenReturn(List.of(evaluacion));
        assertFalse(evaluacionService.listar().isEmpty());
    }

    @Test
    void testBuscarPorId() {
        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacion));
        assertTrue(evaluacionService.buscarPorId(1L).isPresent());
    }

    @Test
    void testPorCurso() {
        when(evaluacionRepository.findByCursoId(1L)).thenReturn(List.of(evaluacion));
        assertFalse(evaluacionService.porCurso(1L).isEmpty());
    }

    @Test
    void testCrear() {
        when(evaluacionRepository.save(any())).thenReturn(evaluacion);
        assertEquals("Prueba Final", evaluacionService.crear(evaluacion).getNombre());
    }

    @Test
    void testEliminar() {
        evaluacionService.eliminar(1L);
        verify(evaluacionRepository).deleteById(1L);
    }
}