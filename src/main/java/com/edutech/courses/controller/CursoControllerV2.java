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

import com.edutech.courses.assamblers.CursoModelAssembler;
import com.edutech.courses.model.Curso;
import com.edutech.courses.service.CursoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v2/cursos")
@RequiredArgsConstructor
public class CursoControllerV2 {
    @Autowired
    private final CursoService service;
    @Autowired
    private final CursoModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Curso>> listar() {
        List<EntityModel<Curso>> cursos = service.listar().stream()
            .map(assembler::toModel).toList();
        return CollectionModel.of(cursos,
            linkTo(methodOn(CursoControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Curso>> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Curso>> crear(@RequestBody Curso curso) {
        Curso creado = service.crear(curso);
        return ResponseEntity.created(linkTo(methodOn(CursoControllerV2.class).buscar(creado.getId())).toUri())
            .body(assembler.toModel(creado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
