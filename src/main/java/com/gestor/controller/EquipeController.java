package com.gestor.controller;

import javax.swing.event.ListSelectionListener;

import com.gestor.controller.EquipeController.RemoverProjetoListener;
import com.gestor.model.*;
import com.gestor.service.*;
import com.gestor.view.EquipeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EquipeController {
    private EquipeView view;
    private BancoDeDados bd;
    private GerenciadorDados gerenciador;

    public EquipeController(EquipeView view, BancoDeDados bd, GerenciadorDados gerenciador) {
        this.view = view;
        this.bd = bd;
        this.gerenciador = gerenciador;

        // Adiciona listeners para os botões de CRUD de Equipe
        this.view.addAdicionarEquipeListener(new AdicionarEquipeListener());
        this.view.addDeletarEquipeListener(new DeletarEquipeListener());
        
        // Adiciona listeners para os botões de gerenciamento de Membros
        this.view.addAdicionarMembroListener(new AdicionarMembroListener());
        this.view.addRemoverMembroListener(new RemoverMembroListener());

        // Adiciona listeners para os botões de gerenciamento de Projetos
        this.view.addAdicionarProjetoListener(new AdicionarProjetoListener());
        this.view.addRemoverProjetoListener(new RemoverProjetoListener());

        // Listener principal: Ouve a SELEÇÃO na lista de equipes para atualizar os detalhes
        this.view.getListaEquipes().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarPaineisDeDetalhes();
            }
        });
        
        atualizarViewCompleta();
    }

    private void salvar() {
        gerenciador.salvarBanco(bd);
    }

    private void atualizarViewCompleta() {
        view.atualizarListaEquipes(bd.getEquipes());
        atualizarPaineisDeDetalhes();
    }

    // Este método centralizado atualiza os painéis de Membros e Projetos
    private void atualizarPaineisDeDetalhes() {
        Equipe equipeSelecionada = view.getListaEquipes().getSelectedValue();
        if (equipeSelecionada == null) {
            // Se nenhuma equipe está selecionada, limpa os painéis de detalhes
            view.atualizarListasMembros(bd.getUsuarios(), new ArrayList<>());
            view.atualizarListasProjetos(bd.getProjetos(), new ArrayList<>());
        } else {
            // Atualiza a lista de membros
            List<Usuario> membros = bd.getUsuarios().stream()
                .filter(u -> equipeSelecionada.getIdsMembros().contains(u.getId()))
                .collect(Collectors.toList());
            view.atualizarListasMembros(bd.getUsuarios(), membros);

            // Atualiza a lista de projetos
            List<Projeto> projetosDaEquipe = bd.getProjetos().stream()
                .filter(p -> equipeSelecionada.getIdsProjetos().contains(p.getId()))
                .collect(Collectors.toList());
            view.atualizarListasProjetos(bd.getProjetos(), projetosDaEquipe);
        }
    }

    // --- Listeners para CRUD de Equipes ---
    class AdicionarEquipeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nome = view.getNomeEquipe();
            String desc = view.getDescricaoEquipe();
            if(nome.trim().isEmpty()) {
                view.mostrarMensagem("O nome da equipe é obrigatório.");
                return;
            }
            long novoId = bd.getProximoId();
            bd.getEquipes().add(new Equipe(novoId, nome, desc));
            salvar();
            view.limparFormulario();
            atualizarViewCompleta();
        }
    }

    class DeletarEquipeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Equipe selecionada = view.getListaEquipes().getSelectedValue();
            if(selecionada == null) {
                view.mostrarMensagem("Selecione uma equipe para deletar.");
                return;
            }
            bd.getEquipes().remove(selecionada);
            salvar();
            atualizarViewCompleta();
        }
    }
    
    // --- Listeners para Gerenciamento de Membros ---
    class AdicionarMembroListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Equipe equipe = view.getListaEquipes().getSelectedValue();
            Usuario usuario = view.getUsuarioDisponivelSelecionado();
            if (equipe == null || usuario == null) {
                view.mostrarMensagem("Selecione uma equipe e um usuário disponível.");
                return;
            }
            if (!equipe.getIdsMembros().contains(usuario.getId())) {
                equipe.getIdsMembros().add(usuario.getId());
                salvar();
                atualizarPaineisDeDetalhes();
            }
        }
    }

    class RemoverMembroListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Equipe equipe = view.getListaEquipes().getSelectedValue();
            Usuario usuario = view.getMembroSelecionado();
            if (equipe == null || usuario == null) {
                view.mostrarMensagem("Selecione uma equipe e um membro para remover.");
                return;
            }
            equipe.getIdsMembros().remove(Long.valueOf(usuario.getId()));
            salvar();
            atualizarPaineisDeDetalhes();
        }
    }

    class AdicionarProjetoListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Equipe equipe = view.getListaEquipes().getSelectedValue();
        Projeto projeto = view.getProjetoDisponivelSelecionado();
        if (equipe == null || projeto == null) {
            view.mostrarMensagem("Selecione uma equipe e um projeto disponível.");
            return;
        }

        if (equipe.getIdsMembros().isEmpty()) {
            view.mostrarMensagem("A equipe precisa ter pelo menos um integrante para ser alocada a um projeto.");
            return; 
        }
        // =================================================================

        if (!equipe.getIdsProjetos().contains(projeto.getId())) {
            equipe.getIdsProjetos().add(projeto.getId());
            projeto.getIdsEquipes().add(equipe.getId());
            salvar();
            atualizarPaineisDeDetalhes();
        }
    }
    }

    class RemoverProjetoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Equipe equipe = view.getListaEquipes().getSelectedValue();
            Projeto projeto = view.getProjetoAtribuidoSelecionado();
            if (equipe == null || projeto == null) {
                view.mostrarMensagem("Selecione uma equipe e um projeto atribuído.");
                return;
            }
            equipe.getIdsProjetos().remove(Long.valueOf(projeto.getId()));
            projeto.getIdsEquipes().remove(Long.valueOf(equipe.getId())); // Atualização bidirecional
            salvar();
            atualizarPaineisDeDetalhes();
        }
    }
}