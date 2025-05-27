package com.edutech.courses.controller;

import com.edutech.courses.model.Inscripcion;
import com.edutech.courses.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService service;

    @GetMapping
    public List<Inscripcion> listar() {
        return service.listar();
    }

    @GetMapping("/curso/{cursoId}")
    public List<Inscripcion> porCurso(@PathVariable Long cursoId) {
        return service.porCurso(cursoId);
    }

    @GetMapping("/alumno/{alumnoId}")
    public List<Inscripcion> porAlumno(@PathVariable Long alumnoId) {
        return service.porAlumno(alumnoId);
    }

    @PostMapping
    public ResponseEntity<Inscripcion> crear(@RequestBody Inscripcion inscripcion) {
        return ResponseEntity.status(201).body(service.crear(inscripcion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
