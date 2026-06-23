package com.faculdade.crudweb.repository;

import com.faculdade.crudweb.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
