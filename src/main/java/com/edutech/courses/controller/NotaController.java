package com.edutech.courses.controller;

import com.edutech.courses.model.Nota;
import com.edutech.courses.service.NotaService;

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
@RequestMapping("/api/notas")
@RequiredArgsConstructor
@Tag(name = "Notas", description = "Operaciones relacionadas con las notas") 
public class NotaController {

    private final NotaService service;

    @Operation(summary = "Buscar notas por ID de alumno")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notas del alumno encontradas", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Nota.class))
            ),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado o sin notas asociadas") 
    })
    @GetMapping("/alumno/{alumnoId}")
    public List<Nota> porAlumno(@Parameter(description = "ID del alumno para buscar notas") @PathVariable Long alumnoId) { 
        return service.porAlumno(alumnoId);
    }

    @Operation(summary = "Registrar nueva nota") 
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Nota registrada exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Nota.class)) 
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos") 
    })
    @PostMapping
    public ResponseEntity<Nota> registrar(@RequestBody Nota nota) {
        return ResponseEntity.status(201).body(service.registrar(nota));
    }

    @Operation(summary = "Eliminar nota por ID") 
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Nota eliminada exitosamente"), 
            @ApiResponse(responseCode = "404", description = "Nota no encontrada") 
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la nota a eliminar") @PathVariable Long id) { 
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
