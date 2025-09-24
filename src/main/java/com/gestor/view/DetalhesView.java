package com.gestor.view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;


public class DetalhesView extends JDialog {

    public DetalhesView(Frame owner, String title, Map<String, String> data) {
        super(owner, title, true); // true = modal (bloqueia a janela principal)
        
        setLayout(new BorderLayout(10, 10));
        
        // Usa HTML para permitir quebras de linha e negrito
        StringBuilder content = new StringBuilder("<html>");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            content.append("<b>").append(entry.getKey()).append(":</b> ")
                   .append(entry.getValue()).append("<br>");
        }
        content.append("</html>");

        JLabel label = new JLabel(content.toString());
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona uma margem interna
        
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose()); // dispose() fecha o JDialog

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        add(label, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack(); // Ajusta 
        setLocationRelativeTo(owner); // Centraliza
    }
}