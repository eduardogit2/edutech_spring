package com.edutech.courses.controller;

import com.edutech.courses.model.Alumno;
import com.edutech.courses.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService service;

    @GetMapping
    public List<Alumno> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<Alumno> buscarPorRun(@PathVariable String run) {
        return service.buscarPorRun(run)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno alumno) {
        return ResponseEntity.status(201).body(service.crear(alumno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
