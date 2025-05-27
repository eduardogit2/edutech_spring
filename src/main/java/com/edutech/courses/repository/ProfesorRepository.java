package com.edutech.courses.repository;

import com.edutech.courses.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByEmail(String email);
    Optional<Profesor> findByRun(String run);
}
