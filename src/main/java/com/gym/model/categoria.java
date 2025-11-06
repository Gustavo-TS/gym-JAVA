package com.gym.model;

import jakarta.persistence.*;

@Entity
@Table(name = "t_categoria")
public class categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String tipo; // "ganho" ou "gasto"

    public Long getId() { return id; }
    public String getDescricao() { return descricao; }
    public String getTipo() { return tipo; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String descricao) { this.descricao = descricao; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
