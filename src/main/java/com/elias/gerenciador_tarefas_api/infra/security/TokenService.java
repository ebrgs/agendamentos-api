package com.elias.gerenciador_tarefas_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.elias.gerenciador_tarefas_api.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("agendamentos-api") // Quem emitiu?
                    .withSubject(usuario.getLogin()) // De quem é esse token? (Login)
                    .withExpiresAt(dataExpiracao()) // Até quando vale?
                    .sign(algorithm); // Assina digitalmente
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validarToken(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("agendamentos-api")
                    .build()
                    .verify(tokenJWT) // Se for inválido, lança exceção aqui
                    .getSubject(); // Retorna o Login que estava dentro do token
        } catch (JWTVerificationException exception) {
            return ""; // Se der erro, retorna vazio (não autenticado)
        }
    }

    // Define que o token expira em 2 horas
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}