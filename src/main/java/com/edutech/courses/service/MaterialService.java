package com.edutech.courses.service;

import com.edutech.courses.model.Material;
import com.edutech.courses.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository repository;

    public List<Material> listar() {
        return repository.findAll();
    }

    public List<Material> porCurso(Long cursoId) {
        return repository.findByCursoId(cursoId);
    }

    public List<Material> porEvaluacion(Long evaluacionId) {
        return repository.findByEvaluacionId(evaluacionId);
    }

    public Material crear(Material m) {
        return repository.save(m);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
