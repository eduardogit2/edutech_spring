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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull
    @FutureOrPresent
    private LocalDate fecha;

    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "100.0")
    private double peso; // en porcentaje

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @JsonBackReference(value = "curso-evaluacion")
    private Curso curso;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "evaluacion-nota")
    private List<Nota> notas;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "evaluacion-material")
    private List<Material> materiales;
}
