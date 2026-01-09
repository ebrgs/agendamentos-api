package com.elias.gerenciador_tarefas_api.service;

import com.elias.gerenciador_tarefas_api.model.Agendamento;
import com.elias.gerenciador_tarefas_api.repository.AgendamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository repository;

    @InjectMocks
    private AgendamentoService service;

    @Test
    @DisplayName("Deve agendar com sucesso quando o horário está livre")
    void deveAgendarComSucesso() {
        // ARRANGE
        LocalDateTime dataHora = LocalDateTime.of(2030, 1, 1, 10, 0);

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setCliente("Cliente Teste");
        novoAgendamento.setDataHora(dataHora);

        // Mock: Quando buscar por horário, retorna VAZIO (horário livre)
        when(repository.findByDataHora(dataHora)).thenReturn(Optional.empty());
        // Mock: Quando salvar, retorna o próprio objeto
        when(repository.save(any(Agendamento.class))).thenReturn(novoAgendamento);

        // ACT
        Agendamento agendamentoCriado = service.criar(novoAgendamento);

        // ASSERT
        assertNotNull(agendamentoCriado);
        assertEquals("CONFIRMADO", agendamentoCriado.getStatus());
        verify(repository, times(1)).save(novoAgendamento);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar agendar em horário ocupado")
    void deveBarrarAgendamentoDuplicado() {
        // ARRANGE
        LocalDateTime dataHora = LocalDateTime.of(2030, 1, 1, 10, 0);

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setDataHora(dataHora);

        // Simulamos que JÁ EXISTE um agendamento no banco para essa hora
        Agendamento agendamentoExistente = new Agendamento();
        when(repository.findByDataHora(dataHora)).thenReturn(Optional.of(agendamentoExistente));

        // ACT & ASSERT
        // Aqui verificamos se o código LANÇA A EXCEÇÃO correta
        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            service.criar(novoAgendamento);
        });

        assertEquals("Já existe um agendamento para este horário!", erro.getMessage());

        // Garante que NÃO salvou nada
        verify(repository, never()).save(any());
    }
}