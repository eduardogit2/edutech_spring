package com.edutech.courses.repository;

import com.edutech.courses.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByCursoId(Long cursoId);
    List<Inscripcion> findByAlumnoId(Long alumnoId);
    boolean existsByCursoIdAndAlumnoId(Long cursoId, Long alumnoId);
}
