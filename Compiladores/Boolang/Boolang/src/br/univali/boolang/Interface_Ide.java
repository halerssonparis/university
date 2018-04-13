package br.univali.boolang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

@SuppressWarnings("serial")
public class Interface_Ide extends JFrame {
    
    private JPanel panel_area_codigo = new JPanel(new BorderLayout());
    private JPanel panel_area_console = new JPanel(new BorderLayout());
    private JPanel panel_botao_executar = new JPanel(new FlowLayout());
    
    private JTextArea area_codigo = new JTextArea();
    private JTextArea area_console = new JTextArea();
    
    private JScrollPane scroll_area_codigo = new JScrollPane(area_codigo);
    private JScrollPane scroll_area_console = new JScrollPane(area_console);
    
    private JButton botao_executar = new JButton("Executar");
    private JCheckBox checkbox_binario = new JCheckBox("Saída binária");
    
    public void onExecute(Consumer<String> executionConsumer) { 
        botao_executar.addActionListener((ActionEvent e) -> {
            executionConsumer.accept(area_codigo.getText());
        });
    }
     
    public void displayOutput(String output) {
        area_console.setForeground(Color.BLACK);
        area_console.setText(output);
    }
    
    public void displayError(String error) {
        area_console.setForeground(Color.RED);
        area_console.setText(error);
    }
    
    public boolean isOutputBinary() {
        return checkbox_binario.isSelected();
    }
    
    public Interface_Ide() throws HeadlessException {
        init();
    }
    
    private void init() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Interface_Ide.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setSize(700, 500);
        this.setMinimumSize(new Dimension(500, 400));
        this.setLayout(new BorderLayout());
        this.setTitle("Boolang");
        
        area_console.setEditable(false);
        
        area_codigo.setLineWrap(false);
        area_codigo.setColumns(5);
        area_codigo.setRows(15);
        
        area_console.setLineWrap(true);
        area_console.setColumns(5);
        area_console.setRows(5);
        
        panel_area_codigo.add(BorderLayout.CENTER, scroll_area_codigo);
        panel_botao_executar.add(botao_executar);
        panel_botao_executar.add(checkbox_binario);
        panel_area_console.add(BorderLayout.SOUTH, scroll_area_console);
        
        panel_area_codigo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel_area_console.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel_botao_executar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        
        botao_executar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_executarActionPerformed(evt);
            }
        });
        
        add(BorderLayout.NORTH, panel_botao_executar);
        add(BorderLayout.CENTER, panel_area_codigo);
        add(BorderLayout.SOUTH, panel_area_console);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
    }
    
    private void botao_executarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_executarActionPerformed
        // TODO add your handling code here:
    }
}
