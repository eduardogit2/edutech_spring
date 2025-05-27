package com.edutech.courses.controller;

import com.edutech.courses.model.Profesor;
import com.edutech.courses.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService service;

    @GetMapping
    public List<Profesor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profesor> crear(@RequestBody Profesor profesor) {
        return ResponseEntity.status(201).body(service.crear(profesor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<Profesor> buscarPorRun(@PathVariable String run) {
    return service.buscarPorRun(run)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

}
