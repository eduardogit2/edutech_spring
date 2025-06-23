package com.edutech.courses.service;

import com.edutech.courses.model.Alumno;
import com.edutech.courses.repository.AlumnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlumnoService {

    private final AlumnoRepository repository;

    public List<Alumno> listar() {
        return repository.findAll();
    }

    public Optional<Alumno> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Alumno crear(Alumno alumno) {
        return repository.save(alumno);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Optional<Alumno> buscarPorRun(String run) {
    return repository.findByRun(run);
    }
}
