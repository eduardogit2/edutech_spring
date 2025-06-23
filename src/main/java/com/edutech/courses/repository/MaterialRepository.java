package com.edutech.courses.repository;

import com.edutech.courses.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByCursoId(Long cursoId);
    List<Material> findByEvaluacionId(Long evaluacionId);
}

