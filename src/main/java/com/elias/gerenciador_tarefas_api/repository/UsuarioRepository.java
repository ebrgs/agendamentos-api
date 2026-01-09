package com.elias.gerenciador_tarefas_api.repository;

import com.elias.gerenciador_tarefas_api.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String login);
}