package com.elias.gerenciador_tarefas_api.controller;

import com.elias.gerenciador_tarefas_api.model.Agendamento;
import com.elias.gerenciador_tarefas_api.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @GetMapping
    public List<Agendamento> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Object> criar(@Valid @RequestBody Agendamento agendamento) {
        try {
            return ResponseEntity.ok(service.criar(agendamento));
        } catch (RuntimeException e) {
            // Se der erro de horário ocupado, devolvemos 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelar(@PathVariable Long id) {
        try {
            service.cancelar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}