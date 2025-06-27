package com.edutech.courses.controller;

import com.edutech.courses.model.Curso;
import com.edutech.courses.service.CursoService;

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
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "Operaciones relacionadas con los cursos") 
public class CursoController {

    private final CursoService service;

    @Operation(summary = "Listar todos los cursos")
    @ApiResponse(responseCode = "200",
            description = "Listado exitoso de cursos", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class)))
    @GetMapping
    public List<Curso> listar() {
        return service.listar();
    }

    @Operation(summary = "Listar cursos activos")
    @ApiResponse(responseCode = "200",
            description = "Listado exitoso de cursos activos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class)))
    @GetMapping("/activos")
    public List<Curso> listarActivos() {
        return service.listarActivos();
    }

    @Operation(summary = "Buscar curso por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado", 
                    content = @Content(schema = @Schema(implementation = Curso.class))
            ),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscar(@Parameter(description = "ID del curso a buscar") @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar curso por código") 
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado por código", 
                    content = @Content(schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Curso> buscarPorCodigo(@Parameter(description = "Código del curso a buscar") @PathVariable String codigo) { 
        return service.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo curso")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso creado exitosamente", 
                    content = @Content(schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos") 
    })
    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
        return ResponseEntity.status(201).body(service.crear(curso));
    }

    @Operation(summary = "Eliminar curso por ID") 
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Curso eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del curso a eliminar") @PathVariable Long id) { 
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}