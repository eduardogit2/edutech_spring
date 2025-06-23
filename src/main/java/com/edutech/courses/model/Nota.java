package com.edutech.courses.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "1.0")
    @DecimalMax(value = "7.0")
    private double calificacion;

    @ManyToOne
    @JoinColumn(name = "evaluacion_id")
    @JsonBackReference(value = "evaluacion-nota")
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    @JsonBackReference(value = "alumno-nota")
    private Alumno alumno;
}
