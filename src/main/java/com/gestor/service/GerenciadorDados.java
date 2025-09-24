package com.gestor.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class GerenciadorDados {
    // Nome do arquivo de banco de dados
    private static final String NOME_ARQUIVO_DB = "gestor_database.json";
    private String caminhoCompletoDoArquivo;
    private Gson gson;

    public GerenciadorDados() {
        // Constrói o caminho completo para o arquivo dentro da pasta do usuário
        String homeDir = System.getProperty("user.home");
        this.caminhoCompletoDoArquivo = homeDir + File.separator + NOME_ARQUIVO_DB;
        System.out.println("Banco de dados será salvo em: " + this.caminhoCompletoDoArquivo);

        // Configura o Gson com o nosso adaptador de LocalDate
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    }

    public BancoDeDados carregarBanco() {
        File arquivo = new File(caminhoCompletoDoArquivo);
        if (!arquivo.exists()) {
            return new BancoDeDados(); // Retorna um banco de dados novo se o arquivo não existe
        }
        try (FileReader reader = new FileReader(arquivo)) {
            BancoDeDados bd = gson.fromJson(reader, BancoDeDados.class);
            return bd != null ? bd : new BancoDeDados();
        } catch (IOException e) {
            e.printStackTrace();
            return new BancoDeDados();
        }
    }

    public void salvarBanco(BancoDeDados bd) {
        try (FileWriter writer = new FileWriter(caminhoCompletoDoArquivo)) {
            gson.toJson(bd, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}