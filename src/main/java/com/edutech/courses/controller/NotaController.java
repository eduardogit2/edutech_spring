package com.edutech.courses.controller;

import com.edutech.courses.model.Nota;
import com.edutech.courses.service.NotaService;

import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Notas", description = "Registro de calificaciones")
public class NotaController {

    private final NotaService service;

    @Operation(summary = "Listar notas por alumno")
    @ApiResponse(responseCode = "200", description = "Lista completa de notas por alumno")
    @GetMapping("/alumno/{alumnoId}")
    public List<Nota> porAlumno(@PathVariable Long alumnoId) {
        return service.porAlumno(alumnoId);
    }

    @Operation(summary = "Registrar nueva nota")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Nota registrada con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<Nota> registrar(@RequestBody Nota nota) {
        return ResponseEntity.status(201).body(service.registrar(nota));
    }

    @Operation(summary = "Eliminar nota por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Nota eliminada con exito"),
        @ApiResponse(responseCode = "404", description = "Datos invalidos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

