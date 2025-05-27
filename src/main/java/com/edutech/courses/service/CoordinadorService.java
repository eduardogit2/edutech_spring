package com.edutech.courses.service;

import com.edutech.courses.model.Coordinador;
import com.edutech.courses.repository.CoordinadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoordinadorService {

    private final CoordinadorRepository repository;

    public List<Coordinador> listar() {
        return repository.findAll();
    }

    public Optional<Coordinador> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Coordinador crear(Coordinador c) {
        return repository.save(c);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Optional<Coordinador> buscarPorRun(String run) {
    return repository.findByRun(run);
    }

}
