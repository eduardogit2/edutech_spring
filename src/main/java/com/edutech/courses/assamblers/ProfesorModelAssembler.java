package com.edutech.courses.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import com.edutech.courses.controller.ProfesorControllerV2;
import com.edutech.courses.model.Profesor;

@Component
public class ProfesorModelAssembler implements RepresentationModelAssembler<Profesor, EntityModel<Profesor>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<Profesor> toModel(Profesor profesor) {
        return EntityModel.of(profesor,
            linkTo(methodOn(ProfesorControllerV2.class).buscar(profesor.getId())).withSelfRel(),
            linkTo(methodOn(ProfesorControllerV2.class).listar()).withRel("profesores"));
    }
}
