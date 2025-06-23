package com.edutech.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coordinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$", message = "RUN no válido")
    @Column(nullable = false, unique = true)
    private String run;

    @NotBlank
    private String nombre;

    private String apellidoPaterno;
    private String apellidoMaterno;

    @Email
    @Column(unique = true)
    private String email;

    private String telefono;

    @OneToMany(mappedBy = "coordinador", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "coordinador-curso")
    private List<Curso> cursos;
}
