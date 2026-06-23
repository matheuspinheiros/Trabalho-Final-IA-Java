package com.faculdade.crudweb.controller;

import com.faculdade.crudweb.entity.Atividade;
import com.faculdade.crudweb.entity.StatusAtividade;
import com.faculdade.crudweb.service.AlunoService;
import com.faculdade.crudweb.service.AtividadeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/atividades")
public class AtividadeController {

    private final AtividadeService atividadeService;
    private final AlunoService alunoService;

    public AtividadeController(AtividadeService atividadeService, AlunoService alunoService) {
        this.atividadeService = atividadeService;
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("atividades", atividadeService.listarTodos());
        return "atividades/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("atividade", new Atividade());
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("statusOptions", StatusAtividade.values());
        return "atividades/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Atividade atividade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("alunos", alunoService.listarTodos());
            model.addAttribute("statusOptions", StatusAtividade.values());
            return "atividades/formulario";
        }
        atividadeService.salvar(atividade);
        return "redirect:/atividades";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("atividade", atividadeService.buscarPorId(id));
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("statusOptions", StatusAtividade.values());
        return "atividades/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        atividadeService.excluir(id);
        return "redirect:/atividades";
    }
}
