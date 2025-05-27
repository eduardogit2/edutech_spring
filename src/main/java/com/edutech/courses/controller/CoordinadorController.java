package com.edutech.courses.controller;

import com.edutech.courses.model.Coordinador;
import com.edutech.courses.service.CoordinadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinadores")
@RequiredArgsConstructor
public class CoordinadorController {

    private final CoordinadorService service;

    @GetMapping
    public List<Coordinador> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coordinador> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Coordinador> crear(@RequestBody Coordinador coordinador) {
        return ResponseEntity.status(201).body(service.crear(coordinador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<Coordinador> buscarPorRun(@PathVariable String run) {
    return service.buscarPorRun(run)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

}
