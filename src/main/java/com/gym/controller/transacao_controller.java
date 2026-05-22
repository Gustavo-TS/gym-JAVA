package com.gym.controller;

import com.gym.model.transacao;
import com.gym.model.usuario;
import com.gym.repository.transacao_repository;
import com.gym.repository.usuario_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/transacoes")
@CrossOrigin
public class transacao_controller {

    @Autowired
    private transacao_repository transacao_repository;

    @Autowired
    private usuario_repository usuario_repository;

    @GetMapping
    public List<transacao> listar(@AuthenticationPrincipal UserDetails user_details) {
        usuario usuario = usuario_repository.findByEmail(user_details.getUsername()).orElseThrow();
        return transacao_repository.findByUsuarioId(usuario.getId())
                .stream()
                .sorted(Comparator.comparing(transacao::getData).reversed())
                .toList();
    }

    // ✅ Criar nova transação
    @PostMapping
    public ResponseEntity<transacao> criar(@RequestBody transacao transacao,
                                           @AuthenticationPrincipal UserDetails user_details) {
        usuario usuario = usuario_repository.findByEmail(user_details.getUsername()).orElseThrow();
        transacao.setUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacao_repository.save(transacao));
    }

    // ✅ Atualizar transação existente
    @PutMapping("/{id}")
    public ResponseEntity<transacao> atualizar(@PathVariable Long id,
                                               @RequestBody transacao dados,
                                               @AuthenticationPrincipal UserDetails user_details) {
        usuario usuario = usuario_repository.findByEmail(user_details.getUsername()).orElseThrow();

        return transacao_repository.findById(id)
                .filter(t -> t.getUsuario().getId().equals(usuario.getId()))
                .map(t -> {
                    t.setValor(dados.getValor());
                    t.setDescricao(dados.getDescricao());
                    t.setData(dados.getData());
                    t.setCategoria(dados.getCategoria());
                    return ResponseEntity.ok(transacao_repository.save(t));
                })
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    // ✅ Deletar transação
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetails user_details) {
        usuario usuario = usuario_repository.findByEmail(user_details.getUsername()).orElseThrow();

        return transacao_repository.findById(id)
                .filter(t -> t.getUsuario().getId().equals(usuario.getId()))
                .map(t -> {
                    transacao_repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    // ✅ Resumo financeiro do usuário
    @GetMapping("/resumo")
    public Map<String, Object> resumo(@AuthenticationPrincipal UserDetails user_details) {
        usuario usuario = usuario_repository.findByEmail(user_details.getUsername()).orElseThrow();

        Float income = transacao_repository.calcular_income(usuario.getId());
        Float expense = transacao_repository.calcular_expense(usuario.getId());
        Float balance = (income != null ? income : 0f) - (expense != null ? expense : 0f);

        Map<String, Object> resumo = new HashMap<>();
        resumo.put("nome", usuario.getNome());
        resumo.put("income", income != null ? income : 0f);
        resumo.put("expense", expense != null ? expense : 0f);
        resumo.put("balance", balance);

        return resumo;
    }
}
