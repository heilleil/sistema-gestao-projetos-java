package com.gestor.model;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    //private static long contadorId = 0;

    private long id;
    private String nome;
    private String descricao;
    private List<Long> idsMembros = new ArrayList<>(); // IDs dos Usuários na equipe
    private List<Long> idsProjetos = new ArrayList<>(); // IDs dos Projetos em que a equipe atua

     public Equipe(long id, String nome, String descricao) {
        this.id = id; // ID recebido
        this.nome = nome;
        this.descricao = descricao;
    }

    // --- Getters e Setters ---
    public long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Long> getIdsMembros() { return idsMembros; }
    public void setIdsMembros(List<Long> idsMembros) { this.idsMembros = idsMembros; }
    public List<Long> getIdsProjetos() { return idsProjetos; }
    public void setIdsProjetos(List<Long> idsProjetos) { this.idsProjetos = idsProjetos; }

    @Override
    public String toString() {
        return this.nome;
    }
}