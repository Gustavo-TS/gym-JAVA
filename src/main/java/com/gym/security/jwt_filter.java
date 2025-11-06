package com.gym.security;

import com.gym.model.usuario;
import com.gym.repository.usuario_repository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class jwt_filter extends OncePerRequestFilter {

    @Autowired
    private jwt_service jwt_service;

    @Autowired
    private usuario_repository usuario_repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filter_chain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/auth")) {
            filter_chain.doFilter(request, response);
            return;
        }

        final String auth_header = request.getHeader("Authorization");
        final String token;
        final String email;

        if (auth_header == null || !auth_header.startsWith("Bearer ")) {
            filter_chain.doFilter(request, response);
            return;
        }

        token = auth_header.substring(7);
        email = jwt_service.extract_email(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            usuario usuario = usuario_repository.findByEmail(email).orElse(null);

            if (usuario != null && jwt_service.is_token_valid(token, usuario)) {

            }

            if (usuario != null && jwt_service.is_token_valid(token, usuario)) {
                UserDetails user_details = User.withUsername(usuario.getEmail())
                        .password(usuario.getSenha())
                        .authorities("USER")
                        .build();

                UsernamePasswordAuthenticationToken auth_token =
                        new UsernamePasswordAuthenticationToken(user_details, null, user_details.getAuthorities());
                auth_token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth_token);
            }
        }

        filter_chain.doFilter(request, response);
    }
}
