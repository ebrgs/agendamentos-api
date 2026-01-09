package com.elias.gerenciador_tarefas_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data; // Se estiver usando Lombok, senão gere Getters/Setters

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String cliente;

    @NotBlank(message = "A descrição do serviço é obrigatória")
    private String servico; // Ex: "Corte de Cabelo", "Consulta Médica"

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "O agendamento deve ser para uma data futura") // <--- Validação mágica do Java!
    private LocalDateTime dataHora;

    // Vamos simplificar o status como String por enquanto, mas o ideal seria um ENUM
    private String status = "PENDENTE";

    // --- GETTERS E SETTERS (Se não usar Lombok) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getServico() { return servico; }
    public void setServico(String servico) { this.servico = servico; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}