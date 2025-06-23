package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate fechaInscripcion;

    @Enumerated(EnumType.STRING)
    private EstadoInscripcion estado;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @JsonBackReference(value = "curso-inscripcion")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    @JsonBackReference(value = "alumno-inscripcion")
    private Alumno alumno;

    public enum EstadoInscripcion {
        MATRICULADO,
        CANCELADO,
        COMPLETADO
    }
}

