package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema; 

@Entity
@Table(name = "material")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del material", example = "1")
    private Long id;

    @NotBlank(message = "El tipo de material es obligatorio") 
    @Column(nullable = false) 
    @Schema(description = "Tipo de material (ej: PDF, Video, Enlace, Documento)", example = "PDF")
    private String tipo;

    @NotBlank(message = "La URL del material es obligatoria") 
    @Column(nullable = false) 
    @Schema(description = "URL o enlace al material", example = "https://example.com/materiales/documento.pdf")
    private String url;

    @Schema(description = "Descripción detallada del material", example = "Documento de apoyo sobre la unidad 1: Introducción a las matrices.")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "curso_id", nullable = false) 
    @JsonBackReference(value = "curso-material")
    @Schema(description = "Curso al que pertenece este material")
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "evaluacion_id") 
    @JsonBackReference(value = "evaluacion-material")
    @Schema(description = "Evaluación a la que está asociado este material (opcional)")
    private Evaluacion evaluacion;
}
