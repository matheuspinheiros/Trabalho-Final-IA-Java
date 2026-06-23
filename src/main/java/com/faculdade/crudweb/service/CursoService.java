package com.faculdade.crudweb.service;

import com.faculdade.crudweb.entity.Curso;
import com.faculdade.crudweb.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso nao encontrado"));
    }

    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void excluir(Long id) {
        Curso curso = buscarPorId(id);
        if (!curso.getAlunos().isEmpty()) {
            throw new IllegalArgumentException("Nao e possivel excluir curso com alunos cadastrados");
        }
        cursoRepository.deleteById(id);
    }
}
