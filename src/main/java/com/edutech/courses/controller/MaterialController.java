package com.edutech.courses.controller;

import com.edutech.courses.model.Material;
import com.edutech.courses.service.MaterialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
@Tag(name = "Materiales", description = "Gestión de materiales complementarios")
public class MaterialController {

    private final MaterialService service;

    @Operation(summary = "Listar materiales")
    @ApiResponse(responseCode = "200", description = "Lista completa de Materiales")
    @GetMapping
    public List<Material> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar materiales por curso")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Material encontrado"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @GetMapping("/curso/{cursoId}")
    public List<Material> porCurso(@PathVariable Long cursoId) {
        return service.porCurso(cursoId);
    }

    @Operation(summary = "Buscar materiales por evaluación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Materiales encontrados"),
        @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada")
    })
    @GetMapping("/evaluacion/{evaluacionId}")
    public List<Material> porEvaluacion(@PathVariable Long evaluacionId) {
        return service.porEvaluacion(evaluacionId);
    }

    @Operation(summary = "Crear nuevo material")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Material creado con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<Material> crear(@RequestBody Material material) {
        return ResponseEntity.status(201).body(service.crear(material));
    }

    @Operation(summary = "Eliminar material por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Material eliminado con exito"),
        @ApiResponse(responseCode = "404", description = "Material no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

