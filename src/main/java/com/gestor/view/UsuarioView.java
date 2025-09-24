package com.gestor.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import com.gestor.model.Perfil;
import com.gestor.model.Usuario;
import java.awt.event.MouseAdapter;

// ATENÇÃO: Herda de JPanel, não de JFrame!
public class UsuarioView extends JPanel {

    private JTextField campoNome, campoCpf, campoEmail, campoLogin, campoSenha;
    private JComboBox<Perfil> comboPerfil;
    private JButton btnAdicionar, btnDeletar;
    private JList<Usuario> listaUsuarios;
    private DefaultListModel<Usuario> listModel;
    

    public UsuarioView() {
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE ENTRADA (FORMULÁRIO) ---
        JPanel painelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        
        campoNome = new JTextField();
        campoCpf = new JTextField();
        campoEmail = new JTextField();
        campoLogin = new JTextField();
        campoSenha = new JPasswordField(); // Usa JPasswordField para senhas
        comboPerfil = new JComboBox<>(Perfil.values()); // Popula o dropdown com os enums

        painelFormulario.add(new JLabel("Nome Completo:"));
        painelFormulario.add(campoNome);
        painelFormulario.add(new JLabel("CPF:"));
        painelFormulario.add(campoCpf);
        painelFormulario.add(new JLabel("E-mail:"));
        painelFormulario.add(campoEmail);
        painelFormulario.add(new JLabel("Login:"));
        painelFormulario.add(campoLogin);
        painelFormulario.add(new JLabel("Senha:"));
        painelFormulario.add(campoSenha);
        painelFormulario.add(new JLabel("Perfil:"));
        painelFormulario.add(comboPerfil);
        
        btnAdicionar = new JButton("Adicionar Usuário");
        painelFormulario.add(new JLabel()); // Célula vazia para alinhar o botão
        painelFormulario.add(btnAdicionar);

        add(painelFormulario, BorderLayout.NORTH);

        // --- LISTA DE USUÁRIOS ---
        listModel = new DefaultListModel<>();
        listaUsuarios = new JList<>(listModel);
        add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);

        // --- BOTÃO DE DELETAR ---
        JPanel painelBotoes = new JPanel();
        btnDeletar = new JButton("Deletar Usuário Selecionado");
        painelBotoes.add(btnDeletar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    // --- Métodos para o Controller interagir com a View ---

    public String getNome() { return campoNome.getText(); }
    public String getCpf() { return campoCpf.getText(); }
    public String getEmail() { return campoEmail.getText(); }
    public String getLogin() { return campoLogin.getText(); }
    public String getSenha() { return campoSenha.getText(); }
    public Perfil getPerfil() { return (Perfil) comboPerfil.getSelectedItem(); }
    public Usuario getUsuarioSelecionado() { return listaUsuarios.getSelectedValue(); }

    public void adicionarListener(ActionListener listener) {
        btnAdicionar.addActionListener(listener);
    }
    
    public void deletarListener(ActionListener listener) {
        btnDeletar.addActionListener(listener);
    }
    
    public void addDetalhesListener(MouseAdapter listener) {
    listaUsuarios.addMouseListener(listener);
    }

    public void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoEmail.setText("");
        campoLogin.setText("");
        campoSenha.setText("");
        comboPerfil.setSelectedIndex(0);
    }

    public void atualizarListaUsuarios(List<Usuario> usuarios) {
        listModel.clear();
        for (Usuario u : usuarios) {
            listModel.addElement(u);
        }
    }
    
    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}