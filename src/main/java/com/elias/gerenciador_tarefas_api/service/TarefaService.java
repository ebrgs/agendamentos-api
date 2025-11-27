package com.elias.gerenciador_tarefas_api.service;

import com.elias.gerenciador_tarefas_api.repository.TarefaRepository;
import com.elias.gerenciador_tarefas_api.model.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository repository;

    public List<Tarefa> lsitarTodas() {
        return repository.findAll();
    }

    public Tarefa salvar(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    public Tarefa atualizar(Long id, Tarefa tarefaAtualizada){
        Optional<Tarefa> tarefaExistente = repository.findById(id);

        if (tarefaExistente.isPresent()) {
            Tarefa t = tarefaExistente.get();
            t.setDescricao(tarefaAtualizada.getDescricao());
            t.setConcluida(tarefaAtualizada.isConcluida());
            return repository.save(t);
        }

        return null;
    }

    public boolean deletar(Long id) {
        Optional<Tarefa> tarefaEncontrada = repository.findById(id);

        if (tarefaEncontrada.isPresent()) {
            if (tarefaEncontrada.get().isConcluida()) {
                System.out.println("Não pode deletar uma tarefa que já foi concluída");
                return false;
            }

            repository.deleteById(id);
            return true;
        }

        return false;
    }
}
