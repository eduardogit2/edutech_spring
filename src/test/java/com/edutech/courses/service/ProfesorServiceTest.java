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

import com.edutech.courses.model.Profesor;
import com.edutech.courses.repository.ProfesorRepository;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

    @Mock private ProfesorRepository profesorRepository;
    @InjectMocks private ProfesorService profesorService;
    private Profesor profesor;

    @BeforeEach
    void setUp() {
        profesor = Profesor.builder()
                .id(1L)
                .run("11.111.111-1")
                .nombre("Juan")
                .email("juan@correo.com")
                .build();
    }

    @Test
    void testListar() {
        when(profesorRepository.findAll()).thenReturn(List.of(profesor));
        List<Profesor> lista = profesorService.listar();
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorRun() {
        when(profesorRepository.findByRun("11.111.111-1")).thenReturn(Optional.of(profesor));
        Optional<Profesor> resultado = profesorService.buscarPorRun("11.111.111-1");
        assertTrue(resultado.isPresent());
    }

    @Test
    void testCrear() {
        when(profesorRepository.save(any())).thenReturn(profesor);
        Profesor creado = profesorService.crear(profesor);
        assertEquals("Juan", creado.getNombre());
    }

    @Test
    void testEliminar() {
        profesorService.eliminar(1L);
        verify(profesorRepository).deleteById(1L);
    }
}