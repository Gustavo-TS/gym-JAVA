package com.gym.repository;

import com.gym.model.transacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface transacao_repository extends JpaRepository<transacao, Long> {

    List<transacao> findByUsuarioId(Long usuarioId);

    @Query("SELECT SUM(t.valor) FROM transacao t WHERE t.usuario.id = :usuarioId AND t.categoria.tipo = 'ganho'")
    Float calcular_income(@Param("usuarioId") Long usuarioId);

    @Query("SELECT SUM(t.valor) FROM transacao t WHERE t.usuario.id = :usuarioId AND t.categoria.tipo = 'gasto'")
    Float calcular_expense(@Param("usuarioId") Long usuarioId);
}
