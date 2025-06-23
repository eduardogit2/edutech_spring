package com.edutech.courses.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

import com.edutech.courses.assamblers.EvaluacionModelAssembler;
import com.edutech.courses.model.Evaluacion;
import com.edutech.courses.service.EvaluacionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v2/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionControllerV2 {
    @Autowired
    private final EvaluacionService service;
    @Autowired
    private final EvaluacionModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Evaluacion>> listar() {
        List<EntityModel<Evaluacion>> evaluaciones = service.listar().stream()
            .map(assembler::toModel).toList();
        return CollectionModel.of(evaluaciones,
            linkTo(methodOn(EvaluacionControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Evaluacion>> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Evaluacion>> crear(@RequestBody Evaluacion evaluacion) {
        Evaluacion creada = service.crear(evaluacion);
        return ResponseEntity.created(linkTo(methodOn(EvaluacionControllerV2.class).buscar(creada.getId())).toUri())
            .body(assembler.toModel(creada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}