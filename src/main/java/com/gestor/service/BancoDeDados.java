package com.gestor.service;

import java.util.ArrayList;
import java.util.List;

import com.gestor.model.Equipe;
import com.gestor.model.Projeto;
import com.gestor.model.Tarefa;
import com.gestor.model.Usuario;

public class BancoDeDados {
    private long proximoIdDisponivel = 1; // contador central!

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Projeto> projetos = new ArrayList<>();
    private List<Equipe> equipes = new ArrayList<>();
    private List<Tarefa> tarefas = new ArrayList<>(); 

    // Novo método para obter o próximo ID único e já preparar o contador para o seguinte
    public long getProximoId() {
        return proximoIdDisponivel++;
    }


    // Getters e Setters
    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
    public List<Projeto> getProjetos() { return projetos; }
    public void setProjetos(List<Projeto> projetos) { this.projetos = projetos; }
    public List<Equipe> getEquipes() { return equipes; }
    public void setEquipes(List<Equipe> equipes) { this.equipes = equipes; }
      public List<Tarefa> getTarefas() { return tarefas; } 
    public void setTarefas(List<Tarefa> tarefas) { this.tarefas = tarefas; } 
}