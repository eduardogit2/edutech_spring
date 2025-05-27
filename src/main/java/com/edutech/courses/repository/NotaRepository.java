package com.edutech.courses.repository;

import com.edutech.courses.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByAlumnoId(Long alumnoId);
    List<Nota> findByEvaluacionCursoIdAndAlumnoId(Long cursoId, Long alumnoId);
    Optional<Nota> findByEvaluacionIdAndAlumnoId(Long evaluacionId, Long alumnoId);
}
