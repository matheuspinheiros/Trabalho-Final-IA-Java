package com.faculdade.crudweb.controller;

import com.faculdade.crudweb.entity.Aluno;
import com.faculdade.crudweb.entity.Atividade;
import com.faculdade.crudweb.entity.Curso;
import com.faculdade.crudweb.entity.StatusAtividade;
import com.faculdade.crudweb.repository.AlunoRepository;
import com.faculdade.crudweb.repository.AtividadeRepository;
import com.faculdade.crudweb.repository.CursoRepository;
import com.faculdade.crudweb.service.AlunoService;
import com.faculdade.crudweb.service.MistralIaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class IaRestController {

    private final MistralIaService mistralIaService;
    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;
    private final AtividadeRepository atividadeRepository;
    private final AlunoService alunoService;
    private final ObjectMapper objectMapper;

    public IaRestController(MistralIaService mistralIaService,
                            CursoRepository cursoRepository,
                            AlunoRepository alunoRepository,
                            AtividadeRepository atividadeRepository,
                            AlunoService alunoService,
                            ObjectMapper objectMapper) {
        this.mistralIaService = mistralIaService;
        this.cursoRepository = cursoRepository;
        this.alunoRepository = alunoRepository;
        this.atividadeRepository = atividadeRepository;
        this.alunoService = alunoService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/api/ia/resumo")
    public String gerarResumo(@RequestParam int totalCursos,
                              @RequestParam int totalAlunos,
                              @RequestParam int totalAtividades) {
        return mistralIaService.gerarResumoDashboard(totalCursos, totalAlunos, totalAtividades);
    }

    @PostMapping("/api/ia/popular-dados")
    @Transactional
    public String popularDadosAcademicos() throws Exception {
        String jsonGerado = mistralIaService.gerarMassaDadosAcademica();
        JsonNode raiz = objectMapper.readTree(jsonGerado);
        JsonNode cursosNode = raiz.path("cursos");

        if (!cursosNode.isArray() || cursosNode.isEmpty()) {
            throw new IllegalArgumentException("O JSON retornado pela IA nao possui a lista de cursos no formato esperado.");
        }

        int totalCursosSalvos = 0;
        int totalAlunosSalvos = 0;

        for (JsonNode cursoNode : cursosNode) {
            Curso curso = new Curso();
            curso.setNome(cursoNode.path("nome").asText());
            curso.setTurno(cursoNode.path("turno").asText());

            Curso cursoSalvo = cursoRepository.save(curso);
            totalCursosSalvos++;

            JsonNode alunosNode = cursoNode.path("alunos");
            if (alunosNode.isArray()) {
                for (JsonNode alunoNode : alunosNode) {
                    Aluno aluno = new Aluno();
                    aluno.setNome(alunoNode.path("nome").asText());
                    aluno.setEmail(alunoNode.path("email").asText());
                    aluno.setCurso(cursoSalvo);

                    alunoRepository.save(aluno);
                    totalAlunosSalvos++;
                }
            }
        }

        return "Massa de dados gerada com sucesso. Cursos salvos: "
                + totalCursosSalvos + ". Alunos salvos: " + totalAlunosSalvos + ".";
    }

    @PostMapping("/api/ia/gerar-atividades")
    @Transactional
    public String gerarAtividadesComIa() throws Exception {
        List<Aluno> alunos = alunoService.listarTodos().stream()
                .limit(5)
                .toList();

        if (alunos.isEmpty()) {
            throw new IllegalStateException("Nao existem alunos cadastrados no banco para gerar atividades.");
        }

        String jsonGerado = mistralIaService.gerarAtividadesAcademicas(alunos);
        System.out.println("JSON da IA: " + jsonGerado);
        JsonNode raiz = objectMapper.readTree(jsonGerado);
        JsonNode alunosNode = raiz.path("alunos");

        if (!alunosNode.isArray() || alunosNode.isEmpty()) {
            throw new IllegalArgumentException("O JSON retornado pela IA nao possui a lista de alunos no formato esperado.");
        }

        List<Atividade> atividadesParaSalvar = new ArrayList<>();

        for (JsonNode alunoNode : alunosNode) {
            long alunoId = alunoNode.path("alunoId").asLong();
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado para o ID informado pela IA: " + alunoId));

            JsonNode atividadesNode = alunoNode.path("atividades");
            if (!atividadesNode.isArray()) {
                continue;
            }

            for (JsonNode atividadeNode : atividadesNode) {
                Atividade atividade = new Atividade();
                atividade.setTitulo(atividadeNode.path("titulo").asText());
                atividade.setDescricao(atividadeNode.path("descricao").asText());
                atividade.setDataEntrega(LocalDate.parse(atividadeNode.path("dataEntrega").asText()));
                atividade.setStatus(StatusAtividade.valueOf(atividadeNode.path("status").asText().trim().toUpperCase()));
                atividade.setAluno(aluno);
                atividadesParaSalvar.add(atividade);
            }
        }

        atividadeRepository.saveAll(atividadesParaSalvar);
        return "Atividades geradas com sucesso. Total salvo: " + atividadesParaSalvar.size() + ".";
    }
}
