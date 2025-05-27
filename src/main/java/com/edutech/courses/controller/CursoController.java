package com.edutech.courses.controller;

import com.edutech.courses.model.Curso;
import com.edutech.courses.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService service;

    @GetMapping
    public List<Curso> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Curso> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
        return ResponseEntity.status(201).body(service.crear(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Curso> buscarPorCodigo(@PathVariable String codigo) {
    return service.buscarPorCodigo(codigo)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

}
