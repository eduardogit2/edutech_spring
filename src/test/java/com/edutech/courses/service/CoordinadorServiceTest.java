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

import com.edutech.courses.model.Coordinador;
import com.edutech.courses.repository.CoordinadorRepository;

@ExtendWith(MockitoExtension.class)
class CoordinadorServiceTest {

    @Mock private CoordinadorRepository coordinadorRepository;
    @InjectMocks private CoordinadorService coordinadorService;
    private Coordinador coordinador;

    @BeforeEach
    void setUp() {
        coordinador = Coordinador.builder()
                .id(1L)
                .run("13.456.789-0")
                .nombre("Laura")
                .email("laura@correo.com")
                .build();
    }

    @Test
    void testListar() {
        when(coordinadorRepository.findAll()).thenReturn(List.of(coordinador));
        assertFalse(coordinadorService.listar().isEmpty());
    }

    @Test
    void testBuscarPorRun() {
        when(coordinadorRepository.findByRun("13.456.789-0")).thenReturn(Optional.of(coordinador));
        assertTrue(coordinadorService.buscarPorRun("13.456.789-0").isPresent());
    }

    @Test
    void testCrear() {
        when(coordinadorRepository.save(any())).thenReturn(coordinador);
        assertEquals("Laura", coordinadorService.crear(coordinador).getNombre());
    }

    @Test
    void testEliminar() {
        coordinadorService.eliminar(1L);
        verify(coordinadorRepository).deleteById(1L);
    }
}
