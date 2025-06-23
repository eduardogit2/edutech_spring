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

import com.edutech.courses.model.Inscripcion;
import com.edutech.courses.repository.InscripcionRepository;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    @Mock private InscripcionRepository inscripcionRepository;
    @InjectMocks private InscripcionService inscripcionService;
    private Inscripcion inscripcion;

    @BeforeEach
    void setUp() {
        inscripcion = Inscripcion.builder()
                .id(1L)
                .estado(Inscripcion.EstadoInscripcion.MATRICULADO)
                .build();
    }

    @Test
    void testListar() {
        when(inscripcionRepository.findAll()).thenReturn(List.of(inscripcion));
        assertFalse(inscripcionService.listar().isEmpty());
    }

    @Test
    void testPorCurso() {
        when(inscripcionRepository.findByCursoId(1L)).thenReturn(List.of(inscripcion));
        assertFalse(inscripcionService.porCurso(1L).isEmpty());
    }

    @Test
    void testPorAlumno() {
        when(inscripcionRepository.findByAlumnoId(1L)).thenReturn(List.of(inscripcion));
        assertFalse(inscripcionService.porAlumno(1L).isEmpty());
    }

    @Test
    void testCrear() {
        when(inscripcionRepository.save(any())).thenReturn(inscripcion);
        assertEquals(Inscripcion.EstadoInscripcion.MATRICULADO, inscripcionService.crear(inscripcion).getEstado());
    }

    @Test
    void testEliminar() {
        inscripcionService.eliminar(1L);
        verify(inscripcionRepository).deleteById(1L);
    }
}