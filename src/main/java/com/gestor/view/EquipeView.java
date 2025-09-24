package com.gestor.view;

import javax.swing.*;
import com.gestor.model.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EquipeView extends JPanel {

    // Lista principal de equipes
    private JList<Equipe> listaEquipes;
    private DefaultListModel<Equipe> listaEquipesModel;

    // Formulário para nova equipe
    private JTextField campoNomeEquipe;
    private JTextField campoDescricaoEquipe;
    private JButton btnAdicionarEquipe;
    private JButton btnDeletarEquipe;

    // Gerenciamento de Membros
    private JList<Usuario> listaMembros;
    private DefaultListModel<Usuario> listaMembrosModel;
    private JList<Usuario> listaUsuariosDisponiveis;
    private DefaultListModel<Usuario> listaUsuariosDisponiveisModel;
    private JButton btnAdicionarMembro;
    private JButton btnRemoverMembro;

    // Gerenciamento de Projetos
    private JList<Projeto> listaProjetosAtribuidos;
    private DefaultListModel<Projeto> listaProjetosAtribuidosModel;
    private JList<Projeto> listaProjetosDisponiveis;
    private DefaultListModel<Projeto> listaProjetosDisponiveisModel;
    private JButton btnAdicionarProjeto;
    private JButton btnRemoverProjeto;


    public EquipeView() {
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL ESQUERDO: Lista de Equipes e Formulário ---
        JPanel painelEsquerdo = new JPanel(new BorderLayout(5, 5));
        listaEquipesModel = new DefaultListModel<>();
        listaEquipes = new JList<>(listaEquipesModel);
        painelEsquerdo.add(new JScrollPane(listaEquipes), BorderLayout.CENTER);

        JPanel painelForm = new JPanel(new GridLayout(0, 2, 5, 5));
        campoNomeEquipe = new JTextField();
        campoDescricaoEquipe = new JTextField();
        btnAdicionarEquipe = new JButton("Criar Nova Equipe");
        btnDeletarEquipe = new JButton("Deletar Equipe");
        painelForm.add(new JLabel("Nome:"));
        painelForm.add(campoNomeEquipe);
        painelForm.add(new JLabel("Descrição:"));
        painelForm.add(campoDescricaoEquipe);
        painelForm.add(btnAdicionarEquipe);
        painelForm.add(btnDeletarEquipe);
        painelEsquerdo.add(painelForm, BorderLayout.SOUTH);
        
        // --- PAINEL CENTRAL: Dividido em Membros (em cima) e Projetos (em baixo) ---
        JPanel painelCentral = new JPanel(new GridLayout(2, 1, 10, 10));

        // Painel de Membros
        JPanel painelMembros = new JPanel(new BorderLayout(10,10));
        painelMembros.setBorder(BorderFactory.createTitledBorder("Gerenciar Membros da Equipe"));
        listaUsuariosDisponiveisModel = new DefaultListModel<>();
        listaUsuariosDisponiveis = new JList<>(listaUsuariosDisponiveisModel);
        listaMembrosModel = new DefaultListModel<>();
        listaMembros = new JList<>(listaMembrosModel);
        listaUsuariosDisponiveis.setBackground(new Color(255, 220, 220)); // Vermelho claro
        listaMembros.setBackground(new Color(220, 255, 220)); // Verde claro

        JPanel painelBotoesMembros = new JPanel(new GridLayout(2, 1, 5, 5));
        btnAdicionarMembro = new JButton(">>");
        btnRemoverMembro = new JButton("<<");
        painelBotoesMembros.add(btnAdicionarMembro);
        painelBotoesMembros.add(btnRemoverMembro);
        painelMembros.add(new JScrollPane(listaUsuariosDisponiveis), BorderLayout.WEST);
        painelMembros.add(new JScrollPane(listaMembros), BorderLayout.EAST);
        painelMembros.add(painelBotoesMembros, BorderLayout.CENTER);

        // Painel de Projetos
        JPanel painelProjetos = new JPanel(new BorderLayout(10,10));
        painelProjetos.setBorder(BorderFactory.createTitledBorder("Gerenciar Projetos da Equipe"));
        listaProjetosDisponiveisModel = new DefaultListModel<>();
        listaProjetosDisponiveis = new JList<>(listaProjetosDisponiveisModel);
        listaProjetosAtribuidosModel = new DefaultListModel<>();
        listaProjetosAtribuidos = new JList<>(listaProjetosAtribuidosModel);
        listaProjetosDisponiveis.setBackground(new Color(255, 220, 220)); // Vermelho claro
        listaProjetosAtribuidos.setBackground(new Color(220, 255, 220)); // Verde claro

        JPanel painelBotoesProjetos = new JPanel(new GridLayout(2, 1, 5, 5));
        btnAdicionarProjeto = new JButton(">>");
        btnRemoverProjeto = new JButton("<<");
        painelBotoesProjetos.add(btnAdicionarProjeto);
        painelBotoesProjetos.add(btnRemoverProjeto);
        painelProjetos.add(new JScrollPane(listaProjetosDisponiveis), BorderLayout.WEST);
        painelProjetos.add(new JScrollPane(listaProjetosAtribuidos), BorderLayout.EAST);
        painelProjetos.add(painelBotoesProjetos, BorderLayout.CENTER);

        
        painelCentral.add(painelMembros);
        painelCentral.add(painelProjetos);
        
       
        add(painelEsquerdo, BorderLayout.WEST);
        add(painelCentral, BorderLayout.CENTER);
    }

    // --- Métodos para o Controller ---

    // Métodos para Equipes
    public JList<Equipe> getListaEquipes() { return listaEquipes; }
    public String getNomeEquipe() { return campoNomeEquipe.getText(); }
    public String getDescricaoEquipe() { return campoDescricaoEquipe.getText(); }
    public void addAdicionarEquipeListener(ActionListener l) { btnAdicionarEquipe.addActionListener(l); }
    public void addDeletarEquipeListener(ActionListener l) { btnDeletarEquipe.addActionListener(l); }
    
    // Métodos para Membros
    public Usuario getUsuarioDisponivelSelecionado() { return listaUsuariosDisponiveis.getSelectedValue(); }
    public Usuario getMembroSelecionado() { return listaMembros.getSelectedValue(); }
    public void addAdicionarMembroListener(ActionListener l) { btnAdicionarMembro.addActionListener(l); }
    public void addRemoverMembroListener(ActionListener l) { btnRemoverMembro.addActionListener(l); }

    // Métodos para Projetos
    public Projeto getProjetoDisponivelSelecionado() { return listaProjetosDisponiveis.getSelectedValue(); }
    public Projeto getProjetoAtribuidoSelecionado() { return listaProjetosAtribuidos.getSelectedValue(); }
    public void addAdicionarProjetoListener(ActionListener l) { btnAdicionarProjeto.addActionListener(l); }
    public void addRemoverProjetoListener(ActionListener l) { btnRemoverProjeto.addActionListener(l); }

    // Métodos de utilidade
    public void limparFormulario() {
        campoNomeEquipe.setText("");
        campoDescricaoEquipe.setText("");
    }
    
    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    
    // Métodos para atualização das listas
    public void atualizarListaEquipes(List<Equipe> equipes) {
        listaEquipesModel.clear();
        for(Equipe e : equipes) listaEquipesModel.addElement(e);
    }

    public void atualizarListasMembros(List<Usuario> todosUsuarios, List<Usuario> membrosDaEquipe) {
        listaUsuariosDisponiveisModel.clear();
        listaMembrosModel.clear();
        
        for(Usuario u : todosUsuarios) {
            if(!membrosDaEquipe.contains(u)) {
                listaUsuariosDisponiveisModel.addElement(u);
            }
        }
        for(Usuario u : membrosDaEquipe) {
            listaMembrosModel.addElement(u);
        }
    }

    public void atualizarListasProjetos(List<Projeto> todosProjetos, List<Projeto> projetosDaEquipe) {
        listaProjetosDisponiveisModel.clear();
        listaProjetosAtribuidosModel.clear();
        
        for(Projeto p : todosProjetos) {
            // Um projeto está "disponível" se não estiver na lista de projetos da equipe
            if(!projetosDaEquipe.contains(p)) {
                listaProjetosDisponiveisModel.addElement(p);
            }
        }
        for(Projeto p : projetosDaEquipe) {
            listaProjetosAtribuidosModel.addElement(p);
        }
    }
}