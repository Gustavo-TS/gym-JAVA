package com.gym.controller;

import com.gym.dto.login_request;
import com.gym.dto.auth_response;
import com.gym.model.usuario;
import com.gym.repository.usuario_repository;
import com.gym.security.jwt_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class auth_controller {

    @Autowired
    private usuario_repository usuario_repository;

    @Autowired
    private jwt_service jwt_service;

    @Autowired
    private BCryptPasswordEncoder password_encoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody login_request request) {
        usuario usuario = usuario_repository.findByEmail(request.getEmail()).orElse(null);

        if (usuario == null || !password_encoder.matches(request.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

        String token = jwt_service.generate_token(usuario);
        return ResponseEntity.ok(new auth_response(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody usuario usuario) {
        if (usuario_repository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }

        usuario.setSenha(password_encoder.encode(usuario.getSenha()));
        usuario_repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
