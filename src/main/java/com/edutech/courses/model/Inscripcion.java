package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema; 

@Entity
@Table(name = "inscripcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la inscripción", example = "1")
    private Long id;

    @NotNull(message = "La fecha de inscripción es obligatoria") 
    @Column(nullable = false) 
    @Schema(description = "Fecha en que se realizó la inscripción", example = "2024-06-27")
    private LocalDate fechaInscripcion;

    @NotNull(message = "El estado de la inscripción es obligatorio") 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) 
    @Schema(description = "Estado actual de la inscripción", example = "MATRICULADO")
    private EstadoInscripcion estado;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "curso_id", nullable = false) 
    @JsonBackReference(value = "curso-inscripcion")
    @Schema(description = "Curso al que se refiere esta inscripción")
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "alumno_id", nullable = false) 
    @JsonBackReference(value = "alumno-inscripcion")
    @Schema(description = "Alumno asociado a esta inscripción")
    private Alumno alumno;

    @Schema(description = "Posibles estados de una inscripción")
    public enum EstadoInscripcion {
        MATRICULADO,
        CANCELADO,
        COMPLETADO
    }
}
