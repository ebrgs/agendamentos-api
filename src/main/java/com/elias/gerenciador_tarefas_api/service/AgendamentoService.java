package com.elias.gerenciador_tarefas_api.service;

import com.elias.gerenciador_tarefas_api.model.Agendamento;
import com.elias.gerenciador_tarefas_api.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public Agendamento criar(Agendamento agendamento) {
        // REGRA 1: Não pode agendar no passado (O @Future na entidade já garante, mas podemos reforçar)

        // REGRA 2: Não pode haver dois clientes no mesmo horário exato
        Optional<Agendamento> conflito = repository.findByDataHora(agendamento.getDataHora());
        if (conflito.isPresent()) {
            throw new RuntimeException("Já existe um agendamento para este horário!");
        }

        agendamento.setStatus("CONFIRMADO"); // Nasce confirmado
        return repository.save(agendamento);
    }

    public void cancelar(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        if (agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível cancelar agendamentos passados");
        }

        agendamento.setStatus("CANCELADO");
        repository.save(agendamento);
    }
}