package com.edutech.courses.service;

import com.edutech.courses.model.Profesor;
import com.edutech.courses.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository repository;

    public List<Profesor> listar() {
        return repository.findAll();
    }

    public Optional<Profesor> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Profesor crear(Profesor profesor) {
        return repository.save(profesor);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Optional<Profesor> buscarPorRun(String run) {
        return repository.findByRun(run);
    }

}
