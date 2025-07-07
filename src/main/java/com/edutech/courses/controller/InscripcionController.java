package com.edutech.courses.controller;

import com.edutech.courses.model.Inscripcion;
import com.edutech.courses.service.InscripcionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter; 
import io.swagger.v3.oas.annotations.media.Content; 
import io.swagger.v3.oas.annotations.media.Schema; 
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@Tag(name = "Inscripciones", description = "Operaciones relacionadas con las inscripciones a cursos") 
public class InscripcionController {

    private final InscripcionService service;

    @Operation(summary = "Listar todas las inscripciones") 
    @ApiResponse(responseCode = "200",
            description = "Listado exitoso de inscripciones", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inscripcion.class)) 
    )
    @GetMapping
    public List<Inscripcion> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar inscripción por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripción encontrada", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inscripcion.class))),
        @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar inscripciones por ID de curso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripciones del curso encontradas", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inscripcion.class)) 
            ),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado o sin inscripciones") 
    })
    @GetMapping("/curso/{cursoId}")
    public List<Inscripcion> porCurso(@Parameter(description = "ID del curso") @PathVariable Long cursoId) { 
        return service.porCurso(cursoId);
    }

    @Operation(summary = "Buscar inscripciones por ID de alumno") 
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripciones del alumno encontradas", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inscripcion.class)) 
            ),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado o sin inscripciones") 
    })
    @GetMapping("/alumno/{alumnoId}")
    public List<Inscripcion> porAlumno(@Parameter(description = "ID del alumno") @PathVariable Long alumnoId) { 
        return service.porAlumno(alumnoId);
    }

    @Operation(summary = "Crear nueva inscripción") 
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inscripción creada exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inscripcion.class)) 
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos") 
    })
    @PostMapping
    public ResponseEntity<Inscripcion> crear(@Valid @RequestBody Inscripcion inscripcion) {
        return ResponseEntity.status(201).body(service.crear(inscripcion));
    }

    @Operation(summary = "Eliminar inscripción por ID") 
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inscripción eliminada exitosamente"), 
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada") 
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
