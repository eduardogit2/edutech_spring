package com.edutech.courses.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tipo;

    @NotBlank
    private String url;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @JsonBackReference(value = "curso-material")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "evaluacion_id")
    @JsonBackReference(value = "evaluacion-material")
    private Evaluacion evaluacion;
}
