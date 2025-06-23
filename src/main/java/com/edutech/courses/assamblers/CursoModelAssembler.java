package com.edutech.courses.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import com.edutech.courses.controller.CursoControllerV2;
import com.edutech.courses.model.Curso;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<Curso> toModel(Curso curso) {
        return EntityModel.of(curso,
            linkTo(methodOn(CursoControllerV2.class).buscar(curso.getId())).withSelfRel(),
            linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"));
    }
}