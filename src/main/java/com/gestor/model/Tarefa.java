package com.gestor.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tarefa {
    private long id;
    private String nome;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataPrazo;
    private StatusTarefa status;
    private long idProjeto; 

    
    public Tarefa(long id, String nome, String descricao, LocalDate dataPrazo, long idProjeto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.dataPrazo = dataPrazo;
        this.status = StatusTarefa.A_FAZER;
        this.idProjeto = idProjeto;
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDate getDataPrazo() { return dataPrazo; }
    public void setDataPrazo(LocalDate dataPrazo) { this.dataPrazo = dataPrazo; }
    public StatusTarefa getStatus() { return status; }
    public void setStatus(StatusTarefa status) { this.status = status; }
    public long getIdProjeto() { return idProjeto; }
    public void setIdProjeto(long idProjeto) { this.idProjeto = idProjeto; }
    
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return nome + " (" + status + ") - Prazo: " + dataPrazo.format(formatter);
    }
}