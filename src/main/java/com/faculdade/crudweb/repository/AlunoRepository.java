package com.faculdade.crudweb.repository;

import com.faculdade.crudweb.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
