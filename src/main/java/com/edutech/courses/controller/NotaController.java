package com.edutech.courses.controller;

import com.edutech.courses.model.Nota;
import com.edutech.courses.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService service;

    @GetMapping("/alumno/{alumnoId}")
    public List<Nota> porAlumno(@PathVariable Long alumnoId) {
        return service.porAlumno(alumnoId);
    }

    @PostMapping
    public ResponseEntity<Nota> registrar(@RequestBody Nota nota) {
        return ResponseEntity.status(201).body(service.registrar(nota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
