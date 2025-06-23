package com.edutech.courses.controller;

import com.edutech.courses.model.Coordinador;
import com.edutech.courses.service.CoordinadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinadores")
@RequiredArgsConstructor
@Tag(name = "Coordinadores", description = "Gestión de coordinadores académicos")
public class CoordinadorController {

    private final CoordinadorService service;

    @Operation(summary = "Listar todos los coordinadores")
    @ApiResponse(responseCode = "200", description = "Lista completa de Coordinadores")
    @GetMapping
    public List<Coordinador> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar coordinador por ID")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Coordindador encontrado"),
    @ApiResponse(responseCode = "404", description = "Coordindador no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Coordinador> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar coordinador por RUN")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Coordindador encontrado por RUN"),
    @ApiResponse(responseCode = "404", description = "Coordindador no encontrado")
    })
    @GetMapping("/run/{run}")
    public ResponseEntity<Coordinador> buscarPorRun(@PathVariable String run) {
        return service.buscarPorRun(run)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo coordinador")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Coordindador creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<Coordinador> crear(@RequestBody Coordinador coordinador) {
        return ResponseEntity.status(201).body(service.crear(coordinador));
    }

    @Operation(summary = "Eliminar coordinador")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Coordinador eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Coordinador no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

