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
@Table(name = "coordinador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coordinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del coordinador", example = "1") 
    private Long id;

    @NotBlank(message = "El RUN es obligatorio") 
    @Pattern(regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$", message = "RUN no válido")
    @Column(nullable = false, unique = true)
    @Schema(description = "RUN del coordinador", example = "12.345.678-9")
    private String run;

    @NotBlank(message = "El nombre es obligatorio") 
    @Schema(description = "Nombre del coordinador", example = "Matias")
    private String nombre;

    @Schema(description = "Apellido paterno del coordinador", example = "Pancracio")
    private String apellidoPaterno;

    @Schema(description = "Apellido materno del coordinador", example = "Hernandez") 
    private String apellidoMaterno;

    @NotBlank(message = "El correo electrónico es obligatorio") 
    @Email(message = "Formato de correo electrónico inválido") 
    @Column(unique = true, nullable = false) 
    @Schema(description = "Correo electrónico del coordinador", example = "matias.pancracio@example.com") 
    private String email;

    @Pattern(regexp = "^\\+56\\s?9\\s?\\d{4}\\s?\\d{4}$", message = "Teléfono no válido (formato +56 9 XXXX XXXX)") 
    @Schema(description = "Número de teléfono del coordinador", example = "+56 9 1234 5678")
    private String telefono;

    @OneToMany(mappedBy = "coordinador", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "coordinador-curso")
    private List<Curso> cursos;
}
