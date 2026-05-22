package com.gym.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_transacao")
public class transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float valor;
    private String descricao;
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "fk_categoria")
    private categoria categoria;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private usuario usuario;

    // Getters
    public Long getId() { return id; }
    public float getValor() { return valor; }
    public String getDescricao() { return descricao; }
    public LocalDate getData() { return data; }
    public categoria getCategoria() { return categoria; }
    public usuario getUsuario() { return usuario; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setValor(float valor) { this.valor = valor; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setData(LocalDate data) { this.data = data; }
    public void setCategoria(categoria categoria) { this.categoria = categoria; }
    public void setUsuario(usuario usuario) { this.usuario = usuario; }
}
