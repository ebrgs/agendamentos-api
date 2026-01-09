package com.elias.gerenciador_tarefas_api.controller;

import com.elias.gerenciador_tarefas_api.infra.security.TokenService;
import com.elias.gerenciador_tarefas_api.model.AuthenticationDTO;
import com.elias.gerenciador_tarefas_api.model.LoginResponseDTO;
import com.elias.gerenciador_tarefas_api.model.Usuario;
import com.elias.gerenciador_tarefas_api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        // Encapsula login e senha num token do Spring (ainda não autenticado)
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        // O Manager bate no banco, verifica a senha encriptada e autentica
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se deu certo, geramos o Token JWT
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid AuthenticationDTO data) {
        if (this.repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build(); // Já existe utilizador com este login
        }

        // Encripta a senha antes de salvar! Nunca salvar senha pura.
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Usuario newUser = new Usuario(data.login(), encryptedPassword);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}