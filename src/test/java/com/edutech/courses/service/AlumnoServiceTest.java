package com.edutech.courses.service;

import com.edutech.courses.model.Alumno;
import com.edutech.courses.repository.AlumnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    private Alumno alumno;

    @BeforeEach
    void setUp() {
        alumno = Alumno.builder()
            .id(1L)
            .run("12.345.678-9")
            .nombre("Eduardo")
            .apellidoPaterno("Uribe")
            .email("eduardo@mail.cl")
            .fechaNacimiento(LocalDate.of(2000, 1, 1))
            .telefono("+56 9 1234 5678")
            .direccion("Av. Providencia 1234, Santiago")
            .build();
    }

    @Test
    void testCrearAlumno() {
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        Alumno creado = alumnoService.crear(alumno);

        assertNotNull(creado);
        assertEquals(alumno.getRun(), creado.getRun());
        verify(alumnoRepository).save(alumno);
    }

    @Test
    void testBuscarPorId_Encontrado() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));

        Optional<Alumno> encontrado = alumnoService.buscarPorId(1L);

        assertTrue(encontrado.isPresent());
        assertEquals(alumno.getId(), encontrado.get().getId());
    }

    @Test
    void testBuscarPorRun_Existente() {
        when(alumnoRepository.findByRun("12.345.678-9")).thenReturn(Optional.of(alumno));

        Optional<Alumno> resultado = alumnoService.buscarPorRun("12.345.678-9");

        assertTrue(resultado.isPresent());
        assertEquals("12.345.678-9", resultado.get().getRun());
    }

    @Test
    void testEliminarAlumno() {
        doNothing().when(alumnoRepository).deleteById(1L);

        alumnoService.eliminar(1L);

        verify(alumnoRepository).deleteById(1L);
    }
}
