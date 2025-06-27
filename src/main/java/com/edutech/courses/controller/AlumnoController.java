package com.edutech.courses.controller;

import com.edutech.courses.model.Alumno;
import com.edutech.courses.service.AlumnoService;

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
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
@Tag(name = "Alumnos", description = "Operaciones relacionadas con los alumnos")
public class AlumnoController {

    private final AlumnoService service;

    @Operation(summary = "Listar todos los alumnos")
    @ApiResponse(responseCode = "200", 
    description = "Listado exitoso",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class))
    )
    @GetMapping
    public List<Alumno> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar alumno por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alumno encontrado",
        content = @Content(schema = @Schema(implementation = Alumno.class))),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> buscar(@Parameter(description = "ID del alumno a buscar") @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar alumno por RUN")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alumno encontrado por RUN",
        content = @Content(schema = @Schema(implementation = Alumno.class))
        ),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @GetMapping("/run/{run}")
    public ResponseEntity<Alumno> buscarPorRun(@Parameter(description = "RUN del alumno") @PathVariable String run) {
        return service.buscarPorRun(run)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo alumno")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Alumno creado exitosamente",
        content = @Content(schema = @Schema(implementation = Alumno.class))
        ),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno alumno) {
        return ResponseEntity.status(201).body(service.crear(alumno));
    }

    @Operation(summary = "Eliminar alumno por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Alumno eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del alumno a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
