package com.edutech.courses.controller;

import com.edutech.courses.model.Profesor;
import com.edutech.courses.service.ProfesorService;

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
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
@Tag(name = "Profesores", description = "Operaciones relacionadas con los profesores") 
public class ProfesorController {

    private final ProfesorService service;

    @Operation(summary = "Listar todos los profesores")
    @ApiResponse(responseCode = "200",
            description = "Listado exitoso de profesores", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class)) 
    )
    @GetMapping
    public List<Profesor> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar profesor por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesor encontrado", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class)) 
            ),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> buscar(@Parameter(description = "ID del profesor a buscar") @PathVariable Long id) { 
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar profesor por RUN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesor encontrado por RUN", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class))
            ),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @GetMapping("/run/{run}")
    public ResponseEntity<Profesor> buscarPorRun(@Parameter(description = "RUN del profesor a buscar") @PathVariable String run) { 
        return service.buscarPorRun(run)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo profesor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profesor creado exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class)) 
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos") 
    })
    @PostMapping
    public ResponseEntity<Profesor> crear(@RequestBody Profesor profesor) {
        return ResponseEntity.status(201).body(service.crear(profesor));
    }

    @Operation(summary = "Eliminar profesor por ID") 
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Profesor eliminado exitosamente"), 
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del profesor a eliminar") @PathVariable Long id) { 
        return ResponseEntity.noContent().build();
    }
}
