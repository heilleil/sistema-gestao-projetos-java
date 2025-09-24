package com.gestor.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.stream.Collectors;

import com.gestor.model.Perfil;
import com.gestor.model.Projeto;
import com.gestor.model.Status;
import com.gestor.model.Tarefa;
import com.gestor.model.Usuario;

public class ProjetoView extends JPanel {

    // Componentes para o formulário de projetos
    private JTextField campoNome, campoDescricao, campoDataInicio, campoDataTermino;
    private JComboBox<Status> comboStatus;
    private JComboBox<Usuario> comboGerentes;
    private JButton btnAdicionar, btnDeletar;

    // Lista principal de projetos
    private JList<Projeto> listaProjetos;
    private DefaultListModel<Projeto> listModel;

    // Componentes para a área de tarefas
    private JList<Tarefa> listaTarefas;
    private DefaultListModel<Tarefa> listaTarefasModel;
    private JButton btnAdicionarTarefa;
    private JButton btnEditarTarefa;
    private JButton btnExcluirTarefa;

    public ProjetoView() {
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL SUPERIOR: Formulário de criação de projetos ---
        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        campoNome = new JTextField();
        campoDescricao = new JTextField();
        campoDataInicio = new JTextField();
        campoDataTermino = new JTextField();
        comboStatus = new JComboBox<>(Status.values());
        comboGerentes = new JComboBox<>();
        btnAdicionar = new JButton("Adicionar/Atualizar Projeto");
        btnDeletar = new JButton("Deletar Projeto");

        painelFormulario.add(new JLabel("Nome do Projeto:"));
        painelFormulario.add(campoNome);
        painelFormulario.add(new JLabel("Descrição:"));
        painelFormulario.add(campoDescricao);
        painelFormulario.add(new JLabel("Data de Início (dd/mm/aaaa):"));
        painelFormulario.add(campoDataInicio);
        painelFormulario.add(new JLabel("Data de Término (dd/mm/aaaa):"));
        painelFormulario.add(campoDataTermino);
        painelFormulario.add(new JLabel("Status:"));
        painelFormulario.add(comboStatus);
        painelFormulario.add(new JLabel("Gerente Responsável:"));
        painelFormulario.add(comboGerentes);
        painelFormulario.add(btnAdicionar);
        painelFormulario.add(btnDeletar);

        // --- PAINEL CENTRAL: Um painel dividido (SplitPane) ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.4); // 40% do espaço para a lista de projetos

        // Painel de cima do split: A lista de projetos
        JPanel painelListaProjetos = new JPanel(new BorderLayout());
        painelListaProjetos.setBorder(BorderFactory.createTitledBorder("Todos os Projetos"));
        listModel = new DefaultListModel<>();
        listaProjetos = new JList<>(listModel);
        painelListaProjetos.add(new JScrollPane(listaProjetos));

        // Painel de baixo do split: A lista de tarefas do projeto selecionado
        JPanel painelTarefas = new JPanel(new BorderLayout());
        painelTarefas.setBorder(BorderFactory.createTitledBorder("Tarefas do Projeto Selecionado"));
        listaTarefasModel = new DefaultListModel<>();
        listaTarefas = new JList<>(listaTarefasModel);
        painelTarefas.add(new JScrollPane(listaTarefas), BorderLayout.CENTER);

        // Botões para as tarefas
        JPanel painelBotoesTarefas = new JPanel();
        btnAdicionarTarefa = new JButton("Adicionar Tarefa");
        btnEditarTarefa = new JButton("Editar Tarefa");
        btnExcluirTarefa = new JButton("Excluir Tarefa");
        painelBotoesTarefas.add(btnAdicionarTarefa);
        // (Funcionalidades de editar e excluir podem ser adicionadas depois)
        // painelBotoesTarefas.add(btnEditarTarefa);
        // painelBotoesTarefas.add(btnExcluirTarefa);
        painelTarefas.add(painelBotoesTarefas, BorderLayout.SOUTH);

        splitPane.setTopComponent(painelListaProjetos);
        splitPane.setBottomComponent(painelTarefas);

        add(painelFormulario, BorderLayout.NORTH); // Formulário no topo da tela
        add(splitPane, BorderLayout.CENTER); // Painel dividido no resto
    }

    // --- Métodos para o Controller ---

    // Métodos para Projetos
    public String getNomeProjeto() { return campoNome.getText(); }
    public String getDescricaoProjeto() { return campoDescricao.getText(); }
    public String getDataInicio() { return campoDataInicio.getText(); }
    public String getDataTermino() { return campoDataTermino.getText(); }
    public Status getStatus() { return (Status) comboStatus.getSelectedItem(); }
    public Usuario getGerenteSelecionado() { return (Usuario) comboGerentes.getSelectedItem(); }
    public Projeto getProjetoSelecionado() { return listaProjetos.getSelectedValue(); }
    public JList<Projeto> getListaProjetos() { return listaProjetos; } // Para o ListSelectionListener

    // Métodos para Tarefas
    public Tarefa getTarefaSelecionada() { return listaTarefas.getSelectedValue(); }
    // Dentro de ProjetoView.java
    public void addDetalhesTarefaListener(MouseAdapter listener) {
    listaTarefas.addMouseListener(listener);
    }

    // Listeners
    public void adicionarListener(ActionListener listener) { btnAdicionar.addActionListener(listener); }
    public void deletarListener(ActionListener listener) { btnDeletar.addActionListener(listener); }
    public void addDetalhesListener(MouseAdapter listener) { listaProjetos.addMouseListener(listener); }
    public void addAdicionarTarefaListener(ActionListener l) { btnAdicionarTarefa.addActionListener(l); }

    // Métodos de atualização e utilidade
    public void limparCampos() {
        campoNome.setText("");
        campoDescricao.setText("");
        campoDataInicio.setText("");
        campoDataTermino.setText("");
        comboStatus.setSelectedIndex(0);
        comboGerentes.setSelectedIndex(-1);
    }

    public void atualizarListaProjetos(List<Projeto> projetos) {
        listModel.clear();
        for (Projeto p : projetos) {
            listModel.addElement(p);
        }
    }

    public void setListaGerentes(List<Usuario> usuarios) {
        // gerente selecionado
        Object gerenteSelecionadoAnteriormente = comboGerentes.getSelectedItem();
        
        comboGerentes.removeAllItems();
        // Filtro para mostrar apenas usuários com perfil GERENTE
        List<Usuario> gerentes = usuarios.stream()
                .filter(u -> u.getPerfil() == Perfil.GERENTE)
                .collect(Collectors.toList());
        
        for (Usuario gerente : gerentes) {
            comboGerentes.addItem(gerente);
        }
        
        
        comboGerentes.setSelectedItem(gerenteSelecionadoAnteriormente);
    }

    public void atualizarListaTarefas(List<Tarefa> tarefas) {
        listaTarefasModel.clear();
        for(Tarefa t : tarefas) {
            listaTarefasModel.addElement(t);
        }
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}