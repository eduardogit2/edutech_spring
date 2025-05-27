package com.edutech.courses.service;

import com.edutech.courses.model.Nota;
import com.edutech.courses.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotaService {

    private final NotaRepository repository;

    public List<Nota> porAlumno(Long alumnoId) {
        return repository.findByAlumnoId(alumnoId);
    }

    public Optional<Nota> porEvaluacionYAlumno(Long evaluacionId, Long alumnoId) {
        return repository.findByEvaluacionIdAndAlumnoId(evaluacionId, alumnoId);
    }

    public Nota registrar(Nota nota) {
        return repository.save(nota);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}

