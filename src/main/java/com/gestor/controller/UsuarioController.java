package com.gestor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.gestor.model.Perfil;
import com.gestor.model.Usuario;
import com.gestor.service.BancoDeDados;
import com.gestor.service.GerenciadorDados;
import com.gestor.view.DetalhesView;
import com.gestor.view.UsuarioView;

public class UsuarioController {
    private UsuarioView view;
    private BancoDeDados bd;
    private GerenciadorDados gerenciador;

    public UsuarioController(UsuarioView view, BancoDeDados bd, GerenciadorDados gerenciador) {
        this.view = view;
        this.bd = bd;
        this.gerenciador = gerenciador;

        // Listeners para os botões de Adicionar e Deletar
        this.view.adicionarListener(new AdicionarListener());
        this.view.deletarListener(new DeletarListener());

        
        this.view.addDetalhesListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Verifica se o evento foi um duplo-clique (getClickCount() == 2)
                if (e.getClickCount() == 2) {
                    Usuario selecionado = view.getUsuarioSelecionado();
                    if (selecionado != null) {
                        exibirDetalhesUsuario(selecionado);
                    }
                }
            }
        });
        // =================================================================

        atualizarView();
    }

    private void atualizarView() {
        view.atualizarListaUsuarios(bd.getUsuarios());
    }

    private void salvarDados() {
        gerenciador.salvarBanco(bd);
    }
    
    /**
     * 
     * @param usuario usuário a ser exibido.
     */
    private void exibirDetalhesUsuario(Usuario usuario) {
        
        Map<String, String> data = new LinkedHashMap<>();
        data.put("ID", String.valueOf(usuario.getId()));
        data.put("Nome Completo", usuario.getNomeCompleto());
        data.put("CPF", usuario.getCpf());
        data.put("E-mail", usuario.getEmail());
        data.put("Login", usuario.getLogin());
        data.put("Senha", "********"); 
        data.put("Perfil", usuario.getPerfil().toString());

        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(view);
        
       
        DetalhesView detalhesView = new DetalhesView(owner, "Detalhes do Usuário", data);
        detalhesView.setVisible(true);
    }

    // --- Listeners para os botões ---

    class AdicionarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nome = view.getNome();
            String cpf = view.getCpf();
            String email = view.getEmail();
            String login = view.getLogin();
            String senha = view.getSenha();
            Perfil perfil = view.getPerfil();

            if (nome.isEmpty() || cpf.isEmpty() || login.isEmpty() || senha.isEmpty()) {
                view.mostrarMensagem("Todos os campos, exceto e-mail, são obrigatórios.");
                return;
            }

            long novoId = bd.getProximoId(); 
            // PASSA o ID para o construtor
            Usuario novoUsuario = new Usuario(novoId, nome, cpf, email, login, senha, perfil);

            bd.getUsuarios().add(novoUsuario);
            
            salvarDados();
            atualizarView();
            view.limparCampos();
        }
    }

    class DeletarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Usuario selecionado = view.getUsuarioSelecionado();
            if (selecionado == null) {
                view.mostrarMensagem("Selecione um usuário para deletar.");
                return;
            }
            bd.getUsuarios().remove(selecionado);
            salvarDados();
            atualizarView();
        }
    }
}