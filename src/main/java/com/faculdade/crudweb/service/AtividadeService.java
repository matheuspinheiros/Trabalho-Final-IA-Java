package com.faculdade.crudweb.service;

import com.faculdade.crudweb.entity.Atividade;
import com.faculdade.crudweb.entity.StatusAtividade;
import com.faculdade.crudweb.repository.AtividadeRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;

    public AtividadeService(AtividadeRepository atividadeRepository) {
        this.atividadeRepository = atividadeRepository;
    }

    public List<Atividade> listarTodos() {
        return atividadeRepository.findAll();
    }

    public Atividade buscarPorId(Long id) {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atividade nao encontrada"));
    }

    public Atividade salvar(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public void excluir(Long id) {
        atividadeRepository.deleteById(id);
    }

    public Map<String, Long> totalPorStatus() {
        Map<String, Long> dados = new LinkedHashMap<>();
        dados.put("Pendente", atividadeRepository.countByStatus(StatusAtividade.PENDENTE));
        dados.put("Em andamento", atividadeRepository.countByStatus(StatusAtividade.EM_ANDAMENTO));
        dados.put("Concluida", atividadeRepository.countByStatus(StatusAtividade.CONCLUIDA));
        return dados;
    }
}
