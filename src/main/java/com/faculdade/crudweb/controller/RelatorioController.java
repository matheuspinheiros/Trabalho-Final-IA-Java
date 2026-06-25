package com.faculdade.crudweb.controller;

import com.faculdade.crudweb.service.AlunoService;
import com.faculdade.crudweb.service.AtividadeService;
import com.faculdade.crudweb.service.CursoService;
import com.faculdade.crudweb.service.MistralIaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private final CursoService cursoService;
    private final AlunoService alunoService;
    private final AtividadeService atividadeService;
    private final MistralIaService mistralIaService;

    public RelatorioController(CursoService cursoService,
                               AlunoService alunoService,
                               AtividadeService atividadeService,
                               MistralIaService mistralIaService) {
        this.cursoService = cursoService;
        this.alunoService = alunoService;
        this.atividadeService = atividadeService;
        this.mistralIaService = mistralIaService;
    }

    @GetMapping
    public String dashboard(Model model) {
        int totalCursos = cursoService.listarTodos().size();
        int totalAlunos = alunoService.listarTodos().size();
        int totalAtividades = atividadeService.listarTodos().size();

        model.addAttribute("totalCursos", totalCursos);
        model.addAttribute("totalAlunos", totalAlunos);
        model.addAttribute("totalAtividades", totalAtividades);
        model.addAttribute("dadosStatus", atividadeService.totalPorStatus());
        model.addAttribute("resumoIA",
                mistralIaService.gerarResumoDashboard(totalCursos, totalAlunos, totalAtividades));
        return "relatorios/dashboard";
    }
}
