package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema; 

@Entity
@Table(name = "profesor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del profesor", example = "1")
    private Long id;

    @NotBlank(message = "El RUN es obligatorio") 
    @Pattern(regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$", message = "RUN no válido")
    @Column(nullable = false, unique = true)
    @Schema(description = "RUN del profesor", example = "19.876.543-2")
    private String run;

    @NotBlank(message = "El nombre es obligatorio") 
    @Column(nullable = false) 
    @Schema(description = "Nombre del profesor", example = "Ana")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio") 
    @Column(nullable = false) 
    @Schema(description = "Apellido paterno del profesor", example = "González")
    private String apellidoPaterno;

    @Schema(description = "Apellido materno del profesor", example = "Tapia")
    private String apellidoMaterno;

    @NotBlank(message = "El correo electrónico es obligatorio") 
    @Email(message = "Formato de correo electrónico inválido") 
    @Column(unique = true, nullable = false) 
    @Schema(description = "Correo electrónico del profesor", example = "ana.gonzalez@edutech.cl")
    private String email;

    @Pattern(regexp = "^\\+56\\s?9\\s?\\d{4}\\s?\\d{4}$", message = "Teléfono no válido (formato +56 9 XXXX XXXX)") 
    @Schema(description = "Número de teléfono del profesor", example = "+56 9 9876 5432")
    private String telefono;

    @NotBlank(message = "La especialidad es obligatoria") 
    @Column(nullable = false) 
    @Schema(description = "Área de especialización del profesor", example = "Ciencias de la Computación")
    private String especialidad;

    @NotBlank(message = "El grado académico es obligatorio") 
    @Column(nullable = false) 
    @Schema(description = "Grado académico más alto del profesor", example = "Doctorado en IA")
    private String gradoAcademico;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) 
    @JsonManagedReference(value = "profesor-curso")
    @Schema(description = "Lista de cursos que imparte este profesor")
    private List<Curso> cursos;
}
