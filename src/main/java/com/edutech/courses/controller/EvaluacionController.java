package com.edutech.courses.controller;

import com.edutech.courses.model.Evaluacion;
import com.edutech.courses.service.EvaluacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService service;

    @GetMapping
    public List<Evaluacion> listar() {
        return service.listar();
    }

    @GetMapping("/curso/{cursoId}")
    public List<Evaluacion> porCurso(@PathVariable Long cursoId) {
        return service.porCurso(cursoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Evaluacion> crear(@RequestBody Evaluacion evaluacion) {
        return ResponseEntity.status(201).body(service.crear(evaluacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
