package com.edutech.courses.controller;

import com.edutech.courses.model.Profesor;
import com.edutech.courses.service.ProfesorService;

import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Profesores", description = "Gestión de profesores")
public class ProfesorController {

    private final ProfesorService service;

    @Operation(summary = "Listar profesores")
    @ApiResponse(responseCode = "200", description = "Lista completa de Profesores")
    @GetMapping
    public List<Profesor> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar profesor por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profesor encontrado"),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar profesor por RUN")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profesor encontrado"),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @GetMapping("/run/{run}")
    public ResponseEntity<Profesor> buscarPorRun(@PathVariable String run) {
        return service.buscarPorRun(run)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo profesor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Profesor creado con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<Profesor> crear(@RequestBody Profesor profesor) {
        return ResponseEntity.status(201).body(service.crear(profesor));
    }

    @Operation(summary = "Eliminar profesor por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Profesor eliminado con exito"),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
