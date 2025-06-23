package com.edutech.courses.service;

import com.edutech.courses.model.Curso;
import com.edutech.courses.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository repository;

    public List<Curso> listar() {
        return repository.findAll();
    }

    public List<Curso> listarActivos() {
        return repository.findByActivoTrue();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Curso crear(Curso curso) {
        return repository.save(curso);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Optional<Curso> buscarPorCodigo(String codigo) {
    return repository.findByCodigo(codigo);
    }

}
