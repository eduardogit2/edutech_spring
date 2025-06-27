package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del curso", example = "1") 
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Column(unique = true, nullable = false) 
    @Schema(description = "Código único del curso", example = "CUR123") 
    private String codigo;

    @NotBlank(message = "El título es obligatorio") 
    @Schema(description = "Nombre o título del curso", example = "Matrices Avanzadas") 
    private String titulo;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Descripción detallada del curso", example = "Este curso cubre técnicas avanzadas en el cálculo con matrices y sus aplicaciones.") 
    private String descripcion;

    @NotBlank(message = "La materia es obligatoria") 
    @Schema(description = "Materia principal a la que pertenece el curso", example = "Matemáticas") 
    private String materia;

    @NotBlank(message = "El nivel del curso es obligatorio") 
    @Schema(description = "Nivel de dificultad del curso (Ej: Básico, Intermedio, Avanzado)", example = "Avanzado")
    private String nivel;

    @Min(value = 1, message = "El curso debe tener al menos 1 crédito")
    @Schema(description = "Número de créditos que otorga el curso", example = "8") 
    private int creditos;

    @NotBlank(message = "La modalidad es obligatoria") 
    @Pattern(regexp = "Presencial|Online|Híbrido", message = "La modalidad debe ser 'Presencial', 'Online' o 'Híbrido'") 
    @Column(nullable = false) 
    @Schema(description = "Modalidad de impartición del curso", example = "Presencial") 
    private String modalidad;

    @Min(value = 1, message = "Debe haber al menos un cupo disponible") 
    @Schema(description = "Número máximo de estudiantes permitidos en el curso", example = "40") 
    private int cuposMaximos;

    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro") 
    @Column(nullable = false) 
    @Schema(description = "Fecha de inicio del curso", example = "2025-07-01")
    private LocalDate fechaInicio;

    @Future(message = "La fecha de fin debe ser en el futuro") 
    @Column(nullable = false) 
    @Schema(description = "Fecha de finalización del curso", example = "2025-12-31")
    private LocalDate fechaFin;

    @Schema(description = "Estado de actividad del curso (true si está activo, false si no)", example = "true") 
    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "profesor_id", nullable = false) 
    @JsonBackReference(value = "profesor-curso")
    @Schema(description = "Profesor asignado al curso")
    private Profesor profesor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinador_id", nullable = false) 
    @JsonBackReference(value = "coordinador-curso")
    @Schema(description = "Coordinador encargado del curso")
    private Coordinador coordinador;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonManagedReference(value = "curso-inscripcion")
    @Schema(description = "Lista de inscripciones asociadas a este curso")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonManagedReference(value = "curso-evaluacion")
    @Schema(description = "Lista de evaluaciones asociadas a este curso")
    private List<Evaluacion> evaluaciones;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonManagedReference(value = "curso-material")
    @Schema(description = "Lista de materiales de apoyo asociados a este curso")
    private List<Material> materiales;
}
