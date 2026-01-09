package com.elias.gerenciador_tarefas_api.repository;

import com.elias.gerenciador_tarefas_api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // Aqui podemos criar métodos mágicos de busca

    // Ex: Buscar se já existe alguém agendado naquele horário exato
    Optional<Agendamento> findByDataHora(LocalDateTime dataHora);
}