package com.edutech.courses.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

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

import com.edutech.courses.assamblers.InscripcionModelAssembler;
import com.edutech.courses.model.Inscripcion;
import com.edutech.courses.service.InscripcionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v2/inscripciones")
@RequiredArgsConstructor
public class InscripcionControllerV2 {
    @Autowired
    private final InscripcionService service;
    @Autowired
    private final InscripcionModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Inscripcion>> listar() {
        List<EntityModel<Inscripcion>> inscripciones = service.listar().stream()
            .map(assembler::toModel).toList();
        return CollectionModel.of(inscripciones,
            linkTo(methodOn(InscripcionControllerV2.class).listar()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Inscripcion>> crear(@RequestBody Inscripcion inscripcion) {
        Inscripcion creada = service.crear(inscripcion);
        return ResponseEntity.created(linkTo(methodOn(InscripcionControllerV2.class).listar()).toUri())
            .body(assembler.toModel(creada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alumno/{alumnoId}")
    public CollectionModel<EntityModel<Inscripcion>> porAlumno(@PathVariable Long alumnoId) {
        List<EntityModel<Inscripcion>> inscripciones = service.porAlumno(alumnoId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(inscripciones,
                linkTo(methodOn(InscripcionController.class).porAlumno(alumnoId)).withSelfRel());
    }
}