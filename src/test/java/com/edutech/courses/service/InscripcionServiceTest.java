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
        List<Inscripcion> lista = inscripcionService.listar();
        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        assertEquals(Inscripcion.EstadoInscripcion.MATRICULADO, lista.get(0).getEstado());
    }

    @Test
    void testPorCurso() {
        when(inscripcionRepository.findByCursoId(1L)).thenReturn(List.of(inscripcion));
        List<Inscripcion> porCurso = inscripcionService.porCurso(1L);
        assertFalse(porCurso.isEmpty());
        assertEquals(1, porCurso.size());
    }

    @Test
    void testPorAlumno() {
        when(inscripcionRepository.findByAlumnoId(1L)).thenReturn(List.of(inscripcion));
        List<Inscripcion> porAlumno = inscripcionService.porAlumno(1L);
        assertFalse(porAlumno.isEmpty());
        assertEquals(1, porAlumno.size());
    }

    @Test
    void testCrear() {
        when(inscripcionRepository.save(any())).thenReturn(inscripcion);
        Inscripcion creado = inscripcionService.crear(inscripcion);
        assertEquals(Inscripcion.EstadoInscripcion.MATRICULADO, creado.getEstado());
        assertEquals(1L, creado.getId());
    }

    @Test
    void testEliminar() {
        inscripcionService.eliminar(1L);
        verify(inscripcionRepository).deleteById(1L);
    }

    @Test
    void testCrearConEstadoDiferente() {
        Inscripcion otra = Inscripcion.builder()
                .id(2L)
                .estado(Inscripcion.EstadoInscripcion.CANCELADO)
                .build();
        when(inscripcionRepository.save(any())).thenReturn(otra);
        Inscripcion creado = inscripcionService.crear(otra);
        assertEquals(Inscripcion.EstadoInscripcion.CANCELADO, creado.getEstado());
        assertEquals(2L, creado.getId());
    }
}
