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

import com.edutech.courses.model.Nota;
import com.edutech.courses.repository.NotaRepository;

@ExtendWith(MockitoExtension.class)
class NotaServiceTest {

    @Mock private NotaRepository notaRepository;
    @InjectMocks private NotaService notaService;
    private Nota nota;

    @BeforeEach
    void setUp() {
        nota = Nota.builder()
                .id(1L)
                .calificacion(6.5)
                .build();
    }

    @Test
    void testPorAlumno() {
        when(notaRepository.findByAlumnoId(1L)).thenReturn(List.of(nota));
        assertFalse(notaService.porAlumno(1L).isEmpty());
    }

    @Test
    void testPorEvaluacionYAlumno() {
        when(notaRepository.findByEvaluacionIdAndAlumnoId(1L, 1L)).thenReturn(Optional.of(nota));
        Optional<Nota> encontrada = notaService.porEvaluacionYAlumno(1L, 1L);
        assertTrue(encontrada.isPresent());
    }

    @Test
    void testRegistrar() {
        when(notaRepository.save(any())).thenReturn(nota);
        assertEquals(6.5, notaService.registrar(nota).getCalificacion());
    }

    @Test
    void testEliminar() {
        notaService.eliminar(1L);
        verify(notaRepository).deleteById(1L);
    }
}