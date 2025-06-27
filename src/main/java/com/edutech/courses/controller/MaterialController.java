package com.edutech.courses.controller;

import com.edutech.courses.model.Material;
import com.edutech.courses.service.MaterialService;

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
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
@Tag(name = "Materiales", description = "Operaciones relacionadas con los materiales") 
public class MaterialController {

    private final MaterialService service;

    @Operation(summary = "Listar todos los materiales") 
    @ApiResponse(responseCode = "200",
            description = "Listado exitoso de materiales", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Material.class)) 
    )
    @GetMapping
    public List<Material> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar materiales por ID de curso") 
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materiales del curso encontrados", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Material.class)) 
            ),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado o sin materiales asociados") 
    })
    @GetMapping("/curso/{cursoId}")
    public List<Material> porCurso(@Parameter(description = "ID del curso para buscar materiales") @PathVariable Long cursoId) {
        return service.porCurso(cursoId);
    }

    @Operation(summary = "Buscar materiales por ID de evaluación") 
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materiales de la evaluación encontrados", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Material.class)) 
            ),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada o sin materiales asociados") 
    })
    @GetMapping("/evaluacion/{evaluacionId}")
    public List<Material> porEvaluacion(@Parameter(description = "ID de la evaluación para buscar materiales") @PathVariable Long evaluacionId) { 
        return service.porEvaluacion(evaluacionId);
    }

    @Operation(summary = "Crear nuevo material") 
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Material creado exitosamente", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Material.class)) 
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos") 
    })
    @PostMapping
    public ResponseEntity<Material> crear(@RequestBody Material material) {
        return ResponseEntity.status(201).body(service.crear(material));
    }

    @Operation(summary = "Eliminar material por ID") 
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Material eliminado exitosamente"), 
            @ApiResponse(responseCode = "404", description = "Material no encontrado") 
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del material a eliminar") @PathVariable Long id) { 
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
