package com.edutech.courses.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import com.edutech.courses.controller.AlumnoControllerV2;
import com.edutech.courses.model.Alumno;

@Component
public class AlumnoModelAssembler implements RepresentationModelAssembler<Alumno, EntityModel<Alumno>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<Alumno> toModel(Alumno alumno) {
        return EntityModel.of(alumno,
            linkTo(methodOn(AlumnoControllerV2.class).buscar(alumno.getId())).withSelfRel(),
            linkTo(methodOn(AlumnoControllerV2.class).listar()).withRel("alumnos"));
    }
}
