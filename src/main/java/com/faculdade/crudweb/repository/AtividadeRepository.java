package com.faculdade.crudweb.repository;

import com.faculdade.crudweb.entity.Atividade;
import com.faculdade.crudweb.entity.StatusAtividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    long countByStatus(StatusAtividade status);
}
