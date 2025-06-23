package com.edutech.courses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.edutech.courses.assamblers.AlumnoModelAssembler;
import com.edutech.courses.model.Alumno;
import com.edutech.courses.service.AlumnoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/alumnos")
@RequiredArgsConstructor
public class AlumnoControllerV2 {
    @Autowired
    private final AlumnoService service;
    @Autowired
    private final AlumnoModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Alumno>> listar() {
        List<EntityModel<Alumno>> alumnos = service.listar().stream()
            .map(assembler::toModel).toList();
        return CollectionModel.of(alumnos,
            linkTo(methodOn(AlumnoControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Alumno>> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Alumno>> crear(@RequestBody Alumno alumno) {
        Alumno creado = service.crear(alumno);
        return ResponseEntity.created(linkTo(methodOn(AlumnoControllerV2.class).buscar(creado.getId())).toUri())
            .body(assembler.toModel(creado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}