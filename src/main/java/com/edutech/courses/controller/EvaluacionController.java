package com.edutech.courses.controller;

import com.edutech.courses.model.Evaluacion;
import com.edutech.courses.service.EvaluacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter; 
import io.swagger.v3.oas.annotations.media.Content; 
import io.swagger.v3.oas.annotations.media.Schema; 
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
@Tag(name = "Evaluaciones", description = "Operaciones relacionadas con las evaluaciones") 
public class EvaluacionController {

    private final EvaluacionService service;

    @Operation(summary = "Listar todas las evaluaciones") 
    @ApiResponse(responseCode = "200",
            description = "Listado exitoso de evaluaciones", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class)) 
    )
    @GetMapping
    public List<Evaluacion> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar evaluaciones por ID de curso") 
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluaciones del curso encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class)) 
            ),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado o sin evaluaciones") 
    })
    @GetMapping("/curso/{cursoId}")
    public List<Evaluacion> porCurso(@Parameter(description = "ID del curso para buscar evaluaciones") @PathVariable Long cursoId) { 
        return service.porCurso(cursoId);
    }

    @Operation(summary = "Buscar evaluación por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluación encontrada", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class)) 
            ),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada") 
    })
    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> buscar(@Parameter(description = "ID de la evaluación a buscar") @PathVariable Long id) { 
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva evaluación")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evaluación creada exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class))
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos") 
    })
    @PostMapping
    public ResponseEntity<Evaluacion> crear(@RequestBody Evaluacion evaluacion) {
        return ResponseEntity.status(201).body(service.crear(evaluacion));
    }

    @Operation(summary = "Eliminar evaluación por ID") 
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Evaluación eliminada exitosamente"), 
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada") 
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la evaluación a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}