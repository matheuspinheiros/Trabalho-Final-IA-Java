package com.faculdade.crudweb.controller;

import com.faculdade.crudweb.service.AlunoService;
import com.faculdade.crudweb.service.AtividadeService;
import com.faculdade.crudweb.service.CursoService;
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

    public RelatorioController(CursoService cursoService,
                               AlunoService alunoService,
                               AtividadeService atividadeService) {
        this.cursoService = cursoService;
        this.alunoService = alunoService;
        this.atividadeService = atividadeService;
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
        return "relatorios/dashboard";
    }
}
