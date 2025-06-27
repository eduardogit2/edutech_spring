package com.edutech.courses.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull; 
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema; 

@Entity
@Table(name = "nota")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la nota", example = "1")
    private Long id;

    @NotNull(message = "La calificación es obligatoria") 
    @DecimalMin(value = "1.0", message = "La calificación mínima es 1.0") 
    @DecimalMax(value = "7.0", message = "La calificación máxima es 7.0") 
    @Column(nullable = false) 
    @Schema(description = "Calificación obtenida por el alumno en la evaluación", example = "5.5")
    private Double calificacion;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "evaluacion_id", nullable = false) 
    @JsonBackReference(value = "evaluacion-nota")
    @Schema(description = "Evaluación a la que corresponde esta nota")
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "alumno_id", nullable = false) 
    @JsonBackReference(value = "alumno-nota")
    @Schema(description = "Alumno que recibió esta nota")
    private Alumno alumno;

}
