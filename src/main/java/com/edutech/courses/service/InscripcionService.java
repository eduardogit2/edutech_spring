package com.edutech.courses.service;

import com.edutech.courses.model.Inscripcion;
import com.edutech.courses.repository.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final InscripcionRepository repository;

    public List<Inscripcion> listar() {
        return repository.findAll();
    }

    public List<Inscripcion> porCurso(Long cursoId) {
        return repository.findByCursoId(cursoId);
    }

    public List<Inscripcion> porAlumno(Long alumnoId) {
        return repository.findByAlumnoId(alumnoId);
    }

    public Optional<Inscripcion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Inscripcion crear(Inscripcion i) {
        return repository.save(i);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
