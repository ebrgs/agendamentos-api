package com.elias.gerenciador_tarefas_api.infra.security;

import com.elias.gerenciador_tarefas_api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null) {
            var login = tokenService.validarToken(token);
            UserDetails user = userRepository.findByLogin(login);

            // Se o token for válido e o utilizador existir...
            if (user != null) {
                // ...criamos a autenticação do Spring Security
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                // ...e salvamos no contexto (como se fosse uma sessão temporária só para essa requisição)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continua o fluxo (vai para o próximo filtro ou para o Controller)
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        // O header vem como "Bearer eyJhbGci...", removemos o prefixo para pegar só o token
        return authHeader.replace("Bearer ", "");
    }
}