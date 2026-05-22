package com.gym.controller;

import com.gym.model.usuario;
import com.gym.repository.usuario_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class usuario_controller {

    @Autowired
    private usuario_repository usuario_repository;

    @GetMapping
    public List<usuario> listar_usuarios() {
        return usuario_repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<usuario> buscar_por_id(@PathVariable Long id) {
        return usuario_repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<usuario> criar_usuario(@RequestBody usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario_repository.save(usuario));
    }


    @PutMapping("/{id}")
    public ResponseEntity<usuario> atualizar_usuario(@PathVariable Long id, @RequestBody usuario dados) {
        return usuario_repository.findById(id)
                .map(usuario -> {
                    usuario.setEmail(dados.getEmail());
                    usuario.setNome(dados.getNome());
                    return ResponseEntity.ok(usuario_repository.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar_usuario(@PathVariable Long id) {
        if (!usuario_repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuario_repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
