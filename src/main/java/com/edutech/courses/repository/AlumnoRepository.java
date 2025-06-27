package com.edutech.courses.repository;

import com.edutech.courses.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByEmail(String email);
    Optional<Alumno> findByRun(String run); 
}
