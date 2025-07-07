package com.edutech.courses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.edutech.courses.model.Curso;
import com.edutech.courses.repository.CursoRepository;



@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock private CursoRepository cursoRepository;
    @InjectMocks private CursoService cursoService;
    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = Curso.builder()
                .id(1L)
                .codigo("CUR123")
                .titulo("Matemáticas Avanzadas")
                .activo(true)
                .build();
    }

    @Test
    void testListar() {
        when(cursoRepository.findAll()).thenReturn(List.of(curso));
        List<Curso> cursos = cursoService.listar();
        assertFalse(cursos.isEmpty());
        assertEquals(1, cursos.size());
        assertEquals("CUR123", cursos.get(0).getCodigo());
    }

    @Test
    void testBuscarPorId_Existente() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        Optional<Curso> encontrado = cursoService.buscarPorId(1L);
        assertTrue(encontrado.isPresent());
        assertEquals("Matemáticas Avanzadas", encontrado.get().getTitulo());
    }

    @Test
    void testBuscarPorId_NoExistente() {
        when(cursoRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Curso> encontrado = cursoService.buscarPorId(2L);
        assertTrue(encontrado.isEmpty());
    }

    @Test
    void testCrearCurso() {
        when(cursoRepository.save(any())).thenReturn(curso);
        Curso creado = cursoService.crear(curso);
        assertNotNull(creado);
        assertEquals("CUR123", creado.getCodigo());
    }

    @Test
    void testEliminar() {
        cursoService.eliminar(1L);
        verify(cursoRepository).deleteById(1L);
    }
}
