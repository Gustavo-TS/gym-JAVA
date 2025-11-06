package com.gym.controller;

import com.gym.model.categoria;
import com.gym.repository.categoria_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin
public class categoria_controller {

    @Autowired
    private categoria_repository categoria_repository;

    @GetMapping
    public List<categoria> listar() {
        return categoria_repository.findAll();
    }

    @PostMapping
    public ResponseEntity<categoria> criar(@RequestBody categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria_repository.save(categoria));
    }
}
