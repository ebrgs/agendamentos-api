package com.elias.gerenciador_tarefas_api.controller;

import com.elias.gerenciador_tarefas_api.model.Tarefa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.elias.gerenciador_tarefas_api.service.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {
    @Autowired
    private TarefaService service;

    @GetMapping
    public List<Tarefa> listarTodas() {
        return service.lsitarTodas();
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa){
        return ResponseEntity.ok(service.salvar(tarefa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@Valid @PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        Tarefa t = service.atualizar(id, tarefaAtualizada);

        if (t != null) {
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        Boolean sucesso = service.deletar(id);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body("Não foi possivel deletar(Não existe ou já está concluida)");
    }
}
