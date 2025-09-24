package com.gestor.controller;

import com.gestor.model.Projeto;
import com.gestor.model.Status;
import com.gestor.model.StatusTarefa;
import com.gestor.model.Tarefa;
import com.gestor.model.Usuario;
import com.gestor.service.BancoDeDados;
import com.gestor.service.GerenciadorDados;
import com.gestor.view.DetalhesView;
import com.gestor.view.ProjetoView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane; 

public class ProjetoController {
    
    private ProjetoView view;
    private BancoDeDados bd;
    private GerenciadorDados gerenciador;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

     public ProjetoController(ProjetoView view, BancoDeDados bd, GerenciadorDados gerenciador) {
        this.view = view;
        this.bd = bd;
        this.gerenciador = gerenciador;
        
        // Listeners para os botões do formulário de Projetos
        this.view.adicionarListener(new AdicionarProjetoListener());
        this.view.deletarListener(new DeletarProjetoListener()); 

        // Listener para o duplo-clique na lista de projetos (exibir detalhes)
        this.view.addDetalhesListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Projeto selecionado = view.getProjetoSelecionado();
                    if (selecionado != null) {
                        exibirDetalhesProjeto(selecionado);
                    }
                }
            }
        });

        // Listener para SELEÇÃO na lista de projetos (para atualizar as tarefas)
        this.view.getListaProjetos().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarPainelDeTarefas();
            }
        });
        
        this.view.addAdicionarTarefaListener(new AdicionarTarefaListener());
        this.view.addDetalhesTarefaListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Tarefa selecionada = view.getTarefaSelecionada();
            if (selecionada != null) {
                exibirEAtualizarTarefa(selecionada);
            }
        }
        }
        });
        
        // Carga inicial dos dads na view
        atualizarView();
    }
    
    

private void exibirEAtualizarTarefa(Tarefa tarefa) {
    // 1. Prepara a mensagem com os detalhes da tarefa usando HTML
    String detalhes = "<html>" +
            "<b>ID:</b> " + tarefa.getId() + "<br>" +
            "<b>Nome:</b> " + tarefa.getNome() + "<br>" +
            "<b>Descrição:</b> " + tarefa.getDescricao() + "<br>" +
            "<b>Data de Criação:</b> " + tarefa.getDataCriacao().format(dateFormatter) + "<br>" +
            "<b>Prazo:</b> " + tarefa.getDataPrazo().format(dateFormatter) + "<br>" +
            "<b>Status Atual:</b> " + tarefa.getStatus() +
            "</html>";

    // 2. Define as opções de botões que aparecerão na janela
    String[] options;
    switch (tarefa.getStatus()) {
        case A_FAZER:
            options = new String[]{"Iniciar Tarefa", "Fechar"};
            break;
        case EM_ANDAMENTO:
            options = new String[]{"Concluir Tarefa", "Fechar"};
            break;
        case CONCLUIDA:
            options = new String[]{"Reabrir Tarefa", "Fechar"};
            break;
        default:
            options = new String[]{"Fechar"};
            break;
    }

    // 3. Mostra a janela de diálogo interativa
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(view);
    int escolha = JOptionPane.showOptionDialog(
            owner,                      // Janela mãe
            new JLabel(detalhes),       // Mensagem 
            "Detalhes e Ações da Tarefa", // Título da janela
            JOptionPane.DEFAULT_OPTION, // Tipo de opção
            JOptionPane.INFORMATION_MESSAGE, // Ícone
            null,                       // Ícone customizado (nenhum)
            options,                    // Os textos dos botões
            options[0]                  // Botão padrão
    );

    // 4. Age de acordo com o botão que o usuário clicou
    // A escolha é o índice do botão no array 'options' (0, 1, 2...)
    if (escolha == 0) { // O primeiro botão sempre é a ação de mudança de status
        switch (tarefa.getStatus()) {
            case A_FAZER:
                tarefa.setStatus(StatusTarefa.EM_ANDAMENTO);
                break;
            case EM_ANDAMENTO:
                tarefa.setStatus(StatusTarefa.CONCLUIDA);
                break;
            case CONCLUIDA:
                tarefa.setStatus(StatusTarefa.A_FAZER);
                break;
        }
        salvarDados(); // Salva a mudança no arquivo JSON
        atualizarPainelDeTarefas(); // Atualiza a lista de tarefas na tela
    }
    // Se a escolha for outra (ex: "Fechar" ou o X da janela), nada acontece.
}

     
    private void atualizarPainelDeTarefas() {
        Projeto projetoSelecionado = view.getProjetoSelecionado();
        if (projetoSelecionado == null) {
            view.atualizarListaTarefas(new ArrayList<>()); // Limpa a lista se nenhum projeto for selecionado
        } else {
            // Filtra a lista principal de tarefas para pegar apenas as do projeto selecionado
            List<Tarefa> tarefasDoProjeto = bd.getTarefas().stream()
                .filter(t -> t.getIdProjeto() == projetoSelecionado.getId())
                .collect(Collectors.toList());
            view.atualizarListaTarefas(tarefasDoProjeto);
        }
    }
    
     //Listener para o botão de adicionar tarefas
    class AdicionarTarefaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Projeto projetoSelecionado = view.getProjetoSelecionado();
            if (projetoSelecionado == null) {
                view.mostrarMensagem("Primeiro, selecione um projeto para adicionar a tarefa.");
                return;
            }

            //  diálogos (JOptionPane) para pegar os dados da nova tarefa
            String nome = JOptionPane.showInputDialog(view, "Nome da Tarefa:", "Nova Tarefa", JOptionPane.PLAIN_MESSAGE);
            if (nome == null || nome.trim().isEmpty()) return; // Usuário cancelou ou não digitou nada
            
            String desc = JOptionPane.showInputDialog(view, "Descrição da Tarefa:", "Nova Tarefa", JOptionPane.PLAIN_MESSAGE);
            
            String prazoStr = JOptionPane.showInputDialog(view, "Prazo (dd/mm/aaaa):", "Nova Tarefa", JOptionPane.PLAIN_MESSAGE);
            LocalDate prazo;
            try {
                prazo = LocalDate.parse(prazoStr, dateFormatter);
            } catch (Exception ex) {
                view.mostrarMensagem("Formato de data inválido. A tarefa não foi criada.");
                return;
            }
            
            long novoId = bd.getProximoId();
            Tarefa novaTarefa = new Tarefa(novoId, nome, desc, prazo, projetoSelecionado.getId());
            bd.getTarefas().add(novaTarefa);
            
            salvarDados();
            atualizarPainelDeTarefas(); // Atualiza apenas o painel de tarefas
        }
    }

    private void salvarDados() {
        gerenciador.salvarBanco(bd);
    }

    public void atualizarView() {
        view.atualizarListaProjetos(bd.getProjetos());
        // Sempre que a view atualizar, atualiza também a lista de gerentes disponíveis
        view.setListaGerentes(bd.getUsuarios());
    }

    /**
     * Prepara os dados do projeto e exibe a janela de detalhes.
     * @param projeto O projeto a ser exibido.
     */
    private void exibirDetalhesProjeto(Projeto projeto) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("ID", String.valueOf(projeto.getId()));
        data.put("Nome do Projeto", projeto.getNome());
        data.put("Descrição", projeto.getDescricao());
        data.put("Data de Início", projeto.getDataInicio().format(dateFormatter));
        data.put("Data de Término Prevista", projeto.getDataTerminoPrevista().format(dateFormatter));
        data.put("Status", projeto.getStatus().toString());

        // Para encontrar o nome do gerente
        Optional<Usuario> gerenteOpt = bd.getUsuarios().stream()
                .filter(u -> u.getId() == projeto.getIdGerenteResponsavel())
                .findFirst();
        String nomeGerente = gerenteOpt.map(Usuario::getNomeCompleto).orElse("Não encontrado");
        
        data.put("ID do Gerente", String.valueOf(projeto.getIdGerenteResponsavel()));
        data.put("Nome do Gerente", nomeGerente);

        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(view);
        
        DetalhesView detalhesView = new DetalhesView(owner, "Detalhes do Projeto", data);
        detalhesView.setVisible(true);
    }
    
    // --- Listeners para os botões ---

    class AdicionarProjetoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nome = view.getNomeProjeto();
                String descricao = view.getDescricaoProjeto();
                LocalDate dataInicio = LocalDate.parse(view.getDataInicio(), dateFormatter);
                LocalDate dataTermino = LocalDate.parse(view.getDataTermino(), dateFormatter);
                Status status = view.getStatus();
                Usuario gerente = view.getGerenteSelecionado();

                if (nome.isEmpty() || gerente == null) {
                    view.mostrarMensagem("Nome do projeto e Gerente são obrigatórios.");
                    return;
                }

                long novoId = bd.getProximoId();
                Projeto novoProjeto = new Projeto(novoId, nome, descricao, dataInicio, dataTermino, status, gerente.getId());

                bd.getProjetos().add(novoProjeto);
                
                view.limparCampos();
                
                salvarDados();
                atualizarView();
            } catch (DateTimeParseException ex) {
                view.mostrarMensagem("Formato de data inválido. Use dd/mm/aaaa.");
            }
        }
    }

    class DeletarProjetoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Projeto projetoSelecionado = view.getProjetoSelecionado();

            if (projetoSelecionado == null) {
                view.mostrarMensagem("Por favor, selecione um projeto para deletar.");
                return;
            }

            bd.getProjetos().remove(projetoSelecionado);
            
            salvarDados();
            atualizarView();
        }
    }
}