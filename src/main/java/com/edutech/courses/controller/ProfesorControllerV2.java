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

import com.edutech.courses.assamblers.ProfesorModelAssembler;
import com.edutech.courses.model.Profesor;
import com.edutech.courses.service.ProfesorService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/profesores")
@RequiredArgsConstructor
public class ProfesorControllerV2 {
    @Autowired
    private final ProfesorService service;
    @Autowired
    private final ProfesorModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Profesor>> listar() {
        List<EntityModel<Profesor>> profesores = service.listar().stream()
            .map(assembler::toModel).toList();
        return CollectionModel.of(profesores,
            linkTo(methodOn(ProfesorControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Profesor>> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Profesor>> crear(@RequestBody Profesor profesor) {
        Profesor creado = service.crear(profesor);
        return ResponseEntity.created(linkTo(methodOn(ProfesorControllerV2.class).buscar(creado.getId())).toUri())
            .body(assembler.toModel(creado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}