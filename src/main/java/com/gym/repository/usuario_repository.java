package com.gym.repository;

import com.gym.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface usuario_repository extends JpaRepository<usuario, Long> {
    Optional<usuario> findByEmail(String email);
}

