package com.edutech.courses.repository;

import com.edutech.courses.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByActivoTrue();
    List<Curso> findByMateria(String materia);
    List<Curso> findByNivel(String nivel);
    List<Curso> findByProfesorId(Long profesorId);
    Optional<Curso> findByCodigo(String codigo);
}
