package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "evaluacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la evaluación", example = "1") 
    private Long id;

    @NotBlank(message = "El nombre de la evaluación es obligatorio") 
    @Schema(description = "Nombre de la evaluación (ej: Examen Final, Tarea 1)", example = "Examen Parcial 1")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Descripción detallada de la evaluación", example = "Evaluación que cubre los temas de las unidades 1 a la 3.")
    private String descripcion;

    @NotNull(message = "La fecha de la evaluación es obligatoria") 
    @FutureOrPresent(message = "La fecha de la evaluación debe ser hoy o en el futuro") 
    @Column(nullable = false) 
    @Schema(description = "Fecha en la que se realizará la evaluación", example = "2025-08-15")
    private LocalDate fecha;

    @NotNull(message = "El peso de la evaluación es obligatorio") 
    @DecimalMin(value = "0.0", inclusive = false, message = "El peso debe ser mayor a 0.0")
    @DecimalMax(value = "100.0", message = "El peso no puede exceder 100.0") 
    @Column(nullable = false) 
    @Schema(description = "Peso porcentual de la evaluación sobre la nota final del curso", example = "25.0")
    private double peso; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false) 
    @JsonBackReference(value = "curso-evaluacion")
    @Schema(description = "Curso al que pertenece esta evaluación")
    private Curso curso;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonManagedReference(value = "evaluacion-nota")
    @Schema(description = "Lista de notas registradas para esta evaluación")
    private List<Nota> notas;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonManagedReference(value = "evaluacion-material")
    @Schema(description = "Lista de materiales de apoyo asociados a esta evaluación")
    private List<Material> materiales;
}
