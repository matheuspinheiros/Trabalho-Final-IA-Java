package com.faculdade.crudweb.config;

import com.faculdade.crudweb.entity.Aluno;
import com.faculdade.crudweb.entity.Atividade;
import com.faculdade.crudweb.entity.Curso;
import com.faculdade.crudweb.entity.StatusAtividade;
import com.faculdade.crudweb.repository.AlunoRepository;
import com.faculdade.crudweb.repository.AtividadeRepository;
import com.faculdade.crudweb.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;
    private final AtividadeRepository atividadeRepository;

    public DataLoader(CursoRepository cursoRepository, AlunoRepository alunoRepository, AtividadeRepository atividadeRepository) {
        this.cursoRepository = cursoRepository;
        this.alunoRepository = alunoRepository;
        this.atividadeRepository = atividadeRepository;
    }

    @Override
    public void run(String... args) {
        if (cursoRepository.count() > 0) {
            return;
        }

        Curso sistemas = new Curso();
        sistemas.setNome("Sistemas de Informação");
        sistemas.setTurno("Noturno");
        cursoRepository.save(sistemas);

        Curso eds = new Curso();
        eds.setNome("Engenharia de Software");
        eds.setTurno("Noturno");
        cursoRepository.save(eds);

        Aluno aluno1 = new Aluno();
        aluno1.setNome("Matheus Pinheiro dos Santos");
        aluno1.setEmail("mathp237@gmail.com");
        aluno1.setCurso(sistemas);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Caio Januario");
        aluno2.setEmail("caiojanuario@gmail.com");
        aluno2.setCurso(eds);
        alunoRepository.save(aluno2);

        Atividade atividade1 = new Atividade();
        atividade1.setTitulo("Projeto Spring Web CRUD com Thymeleaf");
        atividade1.setDescricao("Criar sistema web com CRUD completo e ThymeLeaf para relatórios com gráficos");
        atividade1.setDataEntrega(LocalDate.now().plusDays(7));
        atividade1.setStatus(StatusAtividade.EM_ANDAMENTO);
        atividade1.setAluno(aluno1);
        atividadeRepository.save(atividade1);

        Atividade atividade2 = new Atividade();
        atividade2.setTitulo("Diagrama UML");
        atividade2.setDescricao("Montar diagrama das entidades do sistema");
        atividade2.setDataEntrega(LocalDate.now().plusDays(3));
        atividade2.setStatus(StatusAtividade.PENDENTE);
        atividade2.setAluno(aluno2);
        atividadeRepository.save(atividade2);
    }
}
