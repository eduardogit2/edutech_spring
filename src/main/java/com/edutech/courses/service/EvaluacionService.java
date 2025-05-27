package com.edutech.courses.service;

import com.edutech.courses.model.Evaluacion;
import com.edutech.courses.repository.EvaluacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository repository;

    public List<Evaluacion> listar() {
        return repository.findAll();
    }

    public List<Evaluacion> porCurso(Long cursoId) {
        return repository.findByCursoId(cursoId);
    }

    public Optional<Evaluacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Evaluacion crear(Evaluacion evaluacion) {
        return repository.save(evaluacion);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
