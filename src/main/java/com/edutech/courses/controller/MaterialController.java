package com.edutech.courses.controller;

import com.edutech.courses.model.Material;
import com.edutech.courses.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService service;

    @GetMapping
    public List<Material> listar() {
        return service.listar();
    }

    @GetMapping("/curso/{cursoId}")
    public List<Material> porCurso(@PathVariable Long cursoId) {
        return service.porCurso(cursoId);
    }

    @GetMapping("/evaluacion/{evaluacionId}")
    public List<Material> porEvaluacion(@PathVariable Long evaluacionId) {
        return service.porEvaluacion(evaluacionId);
    }

    @PostMapping
    public ResponseEntity<Material> crear(@RequestBody Material material) {
        return ResponseEntity.status(201).body(service.crear(material));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
