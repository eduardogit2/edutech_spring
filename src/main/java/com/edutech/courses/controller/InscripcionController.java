package com.edutech.courses.controller;

import com.edutech.courses.model.Inscripcion;
import com.edutech.courses.service.InscripcionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@Tag(name = "Inscripciones", description = "Control de inscripciones a cursos")
public class InscripcionController {

    private final InscripcionService service;

    @Operation(summary = "Listar inscripciones")
    @ApiResponse(responseCode = "200", description = "Lista completa de Inscripciones")
    @GetMapping
    public List<Inscripcion> listar() {
        return service.listar();
    }

    @Operation(summary = "Listar inscripciones por curso")
    @ApiResponse(responseCode = "200", description = "Lista de inscripciones por curso")
    @GetMapping("/curso/{cursoId}")
    public List<Inscripcion> porCurso(@PathVariable Long cursoId) {
        return service.porCurso(cursoId);
    }

    @Operation(summary = "Listar inscripciones por alumno")
    @ApiResponse(responseCode = "200", description = "Lista inscripciones por alumno")
    @GetMapping("/alumno/{alumnoId}")
    public List<Inscripcion> porAlumno(@PathVariable Long alumnoId) {
        return service.porAlumno(alumnoId);
    }

    @Operation(summary = "Crear inscripcion")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Inscripcion creada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<Inscripcion> crear(@RequestBody Inscripcion inscripcion) {
        return ResponseEntity.status(201).body(service.crear(inscripcion));
    }

    @Operation(summary = "Eliminar inscripción")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Inscripcion eliminada con exito"),
        @ApiResponse(responseCode = "404", description = "Inscripcion no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
