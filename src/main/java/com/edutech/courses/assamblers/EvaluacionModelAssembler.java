package com.edutech.courses.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import com.edutech.courses.controller.EvaluacionControllerV2;
import com.edutech.courses.model.Evaluacion;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
            linkTo(methodOn(EvaluacionControllerV2.class).buscar(evaluacion.getId())).withSelfRel(),
            linkTo(methodOn(EvaluacionControllerV2.class).listar()).withRel("evaluaciones"));
    }
}