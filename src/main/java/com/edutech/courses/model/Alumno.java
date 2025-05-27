package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUN es obligatorio")
    @Pattern(regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$", message = "RUN no válido")
    @Column(nullable = false, unique = true)
    private String run;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String apellidoPaterno;
    private String apellidoMaterno;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Column(unique = true, nullable = false)
    private String email;

    @Past(message = "La fecha de nacimiento debe ser valida")
    private LocalDate fechaNacimiento;

    private LocalDate fechaRegistro;

    @Pattern(regexp = "^\\+56\\s?9\\s?\\d{4}\\s?\\d{4}$", message = "Teléfono no válido (formato +56 9 XXXX XXXX)")
    private String telefono;

    private String direccion;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "alumno-inscripcion")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "alumno-nota")
    private List<Nota> notas;
}
