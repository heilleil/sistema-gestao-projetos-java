package com.gestor.model;

import java.util.Objects; 

public class Usuario {
    //private static long contadorId = 0; 

    private long id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    private Perfil perfil;

     public Usuario(long id, String nomeCompleto, String cpf, String email, String login, String senha, Perfil perfil) {
        this.id = id; // Apenas atribui o ID recebido
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Getters e Setters 
    public long getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    
    @Override
    public String toString() {
        return this.nomeCompleto + " (" + this.perfil.toString() + ")";
    }


    @Override
    public boolean equals(Object o) {
       
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        Usuario usuario = (Usuario) o;
        
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        
        return Objects.hash(id);
    }
}