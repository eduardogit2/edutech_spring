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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Column(unique = true)
    private String codigo;

    @NotBlank
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank
    private String materia;

    @NotBlank
    private String nivel;

    @Min(value = 1, message = "El curso debe tener al menos 1 crédito")
    private int creditos;

    @Pattern(regexp = "Presencial|Online|Híbrido", message = "Modalidad inválida")
    private String modalidad;

    @Min(value = 1, message = "Debe haber al menos un cupo")
    private int cuposMaximos;

    @FutureOrPresent
    private LocalDate fechaInicio;

    @Future
    private LocalDate fechaFin;

    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    @JsonBackReference(value = "profesor-curso")
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "coordinador_id")
    @JsonBackReference(value = "coordinador-curso")
    private Coordinador coordinador;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "curso-inscripcion")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "curso-evaluacion")
    private List<Evaluacion> evaluaciones;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "curso-material")
    private List<Material> materiales;
}
