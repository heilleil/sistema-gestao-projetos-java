package com.gestor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.gestor.controller.EquipeController;
import com.gestor.controller.ProjetoController;
import com.gestor.controller.UsuarioController;
import com.gestor.model.Projeto;
import com.gestor.model.Usuario;
import com.gestor.service.BancoDeDados;
import com.gestor.service.GerenciadorDados;
import com.gestor.view.EquipeView;
import com.gestor.view.ProjetoView;
import com.gestor.view.UsuarioView;
import java.util.ArrayList; 
import java.util.List;

public class App {
    

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        // 1. DADOS
        GerenciadorDados gerenciador = new GerenciadorDados();
        BancoDeDados bd = gerenciador.carregarBanco();

        // 2. VIEWS (PAINÉIS)
        UsuarioView painelUsuarios = new UsuarioView();
        ProjetoView painelProjetos = new ProjetoView();
        EquipeView painelEquipes = new EquipeView();

        // 3. CONTROLLERS 
        UsuarioController usuarioController = new UsuarioController(painelUsuarios, bd, gerenciador);
        ProjetoController projetoController = new ProjetoController(painelProjetos, bd, gerenciador);
        EquipeController equipeController = new EquipeController(painelEquipes, bd, gerenciador);

        // 4. JANELA PRINCIPAL
        JFrame frame = new JFrame("Sistema de Gestão");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Usuários", painelUsuarios); // Aba de índice 0
        abas.addTab("Projetos", painelProjetos); // Aba de índice 1
        abas.addTab("Equipes", painelEquipes);   // Aba de índice 2

        abas.addChangeListener(e -> {
            // índice da aba selecionada
            int indiceSelecionado = abas.getSelectedIndex();
            
            // (índice 1)...
            if (indiceSelecionado == 1) {
                System.out.println("Aba de Projetos selecionada. Atualizando lista de gerentes...");
                
                projetoController.atualizarView();
            }
            
            // outras abas if (indiceSelecionado == 2) { equipeController.atualizarViewCompleta(); }
        });
        // =================================================================

        frame.add(abas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    });
}
}