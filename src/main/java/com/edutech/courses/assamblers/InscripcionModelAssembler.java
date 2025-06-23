package com.edutech.courses.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import com.edutech.courses.controller.InscripcionControllerV2;
import com.edutech.courses.model.Inscripcion;

@Component
public class InscripcionModelAssembler implements RepresentationModelAssembler<Inscripcion, EntityModel<Inscripcion>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<Inscripcion> toModel(Inscripcion inscripcion) {
        return EntityModel.of(inscripcion,
            linkTo(methodOn(InscripcionControllerV2.class).crear(inscripcion)).withRel("registrar"),
            linkTo(methodOn(InscripcionControllerV2.class).porAlumno(inscripcion.getAlumno().getId())).withRel("alumno-inscripciones"));
    }
}
