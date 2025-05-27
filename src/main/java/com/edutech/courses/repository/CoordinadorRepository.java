package com.edutech.courses.repository;

import com.edutech.courses.model.Coordinador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinadorRepository extends JpaRepository<Coordinador, Long> {
    Optional<Coordinador> findByEmail(String email);
    Optional<Coordinador> findByRun(String run);

}
