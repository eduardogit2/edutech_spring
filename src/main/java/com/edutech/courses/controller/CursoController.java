package com.edutech.courses.controller;

import com.edutech.courses.model.Curso;
import com.edutech.courses.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Cursos", description = "Administración de cursos")
public class CursoController {

    private final CursoService service;

    @Operation(summary = "Listar todos los cursos")
    @ApiResponse(responseCode = "200", description = "Lista completa de Cursos")
    @GetMapping
    public List<Curso> listar() {
        return service.listar();
    }

    @Operation(summary = "Listar cursos activos")
    @ApiResponse(responseCode = "200", description = "Lista de cursos activos")
    @GetMapping("/activos")
    public List<Curso> listarActivos() {
        return service.listarActivos();
    }

    @Operation(summary = "Buscar curso por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Curso encontrado por ID"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar curso por codigo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Curso encontrado por su codigo"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Curso> buscarPorCodigo(@PathVariable String codigo) {
        return service.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo curso")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Curso creado con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
        return ResponseEntity.status(201).body(service.crear(curso));
    }

    @Operation(summary = "Eliminar curso")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Curso eliminado con exito"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
