package com.gestor.model;

import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List; 

public class Projeto {
    //private static long contadorId = 0;

    private long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private Status status;
    private long idGerenteResponsavel; // ID do Usuario gerente
    private List<Long> idsEquipes = new ArrayList<>(); 


    public Projeto(long id, String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, Status status, long idGerenteResponsavel) {
        this.id = id; // Apenas atribui o ID recebido
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = status;
        this.idGerenteResponsavel = idGerenteResponsavel;
    }

    // --- Getters e Setters ---
    public long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataTerminoPrevista() { return dataTerminoPrevista; }
    public void setDataTerminoPrevista(LocalDate dataTerminoPrevista) { this.dataTerminoPrevista = dataTerminoPrevista; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public long getIdGerenteResponsavel() { return idGerenteResponsavel; }
    public void setIdGerenteResponsavel(long idGerenteResponsavel) { this.idGerenteResponsavel = idGerenteResponsavel; }
    public List<Long> getIdsEquipes() { return idsEquipes; } 
    public void setIdsEquipes(List<Long> idsEquipes) { this.idsEquipes = idsEquipes; } 


    @Override
    public String toString() {
        return this.nome + " (" + this.status + ")";
    }
}