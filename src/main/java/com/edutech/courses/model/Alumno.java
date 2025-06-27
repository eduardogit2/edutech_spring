package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Idenitificador unico del alumno", example = "1")
    private Long id;

    @NotBlank(message = "El RUN es obligatorio")
    @Pattern(regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$", message = "RUN no válido")
    @Column(nullable = false, unique = true)
    @Schema(description = "RUN del alumno", example = "12.345.678-5")
    private String run;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del alumno", example = "Juan")
    private String nombre;
    @Schema(description = "Apellido paterno del alumno", example = "Perez")
    private String apellidoPaterno;
    @Schema(description = "Apellido materno del alumno", example = "Rodriguez")
    private String apellidoMaterno;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Column(unique = true, nullable = false)
    @Schema(description = "Correo electronico del alumno", example = "perezr@gmail.cl")
    private String email;
    
    @Past(message = "La fecha de nacimiento debe ser valida")
    @Schema(description = "Fecha de nacimiento", example = "2001-11-11")
    private LocalDate fechaNacimiento;
    @Schema(description = "Fecha de registro del alumno", example = "2024-06-01")
    private LocalDate fechaRegistro;

    @Pattern(regexp = "^\\+56\\s?9\\s?\\d{4}\\s?\\d{4}$", message = "Teléfono no válido (formato +56 9 XXXX XXXX)")
    @Schema(description = "Número de teléfono del alumno", example = "+56 9 1234 5678")
    private String telefono;
    @Schema(description = "Dirección del alumno", example = "pedro de valdia, concepcion")
    private String direccion;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "alumno-inscripcion")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "alumno-nota")
    private List<Nota> notas;
}
