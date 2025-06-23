package com.edutech.courses.controller;

import com.edutech.courses.model.Evaluacion;
import com.edutech.courses.service.EvaluacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
@Tag(name = "Evaluaciones", description = "Gestión de evaluaciones de los cursos")
public class EvaluacionController {

    private final EvaluacionService service;

    @Operation(summary = "Listar evaluaciones")
    @ApiResponse(responseCode = "200", description = "Lista completa de Evaluaciones")
    @GetMapping
    public List<Evaluacion> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar evaluaciones por curso")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evaluacion del curso encontradas"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @GetMapping("/curso/{cursoId}")
    public List<Evaluacion> porCurso(@PathVariable Long cursoId) {
        return service.porCurso(cursoId);
    }

    @Operation(summary = "Buscar evaluación por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evaluacion encontrada"),
        @ApiResponse(responseCode = "404", description = "Evaluacion no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear evaluación")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Evaluacion creada con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<Evaluacion> crear(@RequestBody Evaluacion evaluacion) {
        return ResponseEntity.status(201).body(service.crear(evaluacion));
    }

    @Operation(summary = "Eliminar evaluación")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Evaluacion eliminada con exito"),
        @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada ")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
