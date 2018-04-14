package compiler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Ide extends JFrame{

    private JPanel PanelCodeArea = new JPanel(new BorderLayout());
    private JPanel PanelResultCodeArea = new JPanel(new BorderLayout());
    private JPanel PanelTopButtons = new JPanel(new BorderLayout());
    
    private JTextArea CodeArea = new JTextArea();
    private JTextArea ResultCodeArea = new JTextArea();
    
    private JScrollPane ScrollCodeArea = new JScrollPane(CodeArea);
    private JScrollPane ScrollResultCodeArea = new JScrollPane(ResultCodeArea);
    
    private JButton buttonExecute = new JButton("Execute");
    private JButton buttonCompile = new JButton("Compile");
    
    public void onExecute(Consumer<String> executionConsumer) {
        buttonExecute.addActionListener((event) -> {
            executionConsumer.accept(CodeArea.getText());
        });
    }
    
    public Ide() {
        Init();
    }
    
    private void Init () {
    
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setSize(700, 500);
        this.setMinimumSize(new Dimension(500, 400));
        this.setLayout(new BorderLayout());
        this.setTitle("Eleonora");
        
        CodeArea.setLineWrap(false);
        CodeArea.setColumns(5);
        CodeArea.setRows(15);
        
        ResultCodeArea.setEditable(false);
        ResultCodeArea.setLineWrap(true);
        ResultCodeArea.setColumns(5);
        ResultCodeArea.setRows(5);
        
        PanelCodeArea.add(BorderLayout.CENTER, ScrollCodeArea);
        PanelResultCodeArea.add(BorderLayout.SOUTH, ScrollResultCodeArea);
        PanelTopButtons.add(buttonCompile);
        PanelTopButtons.add(buttonExecute);
        
        PanelCodeArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        PanelResultCodeArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        PanelTopButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        add(BorderLayout.NORTH, PanelTopButtons);
        add(BorderLayout.CENTER, PanelCodeArea);
        add(BorderLayout.SOUTH, PanelResultCodeArea);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
    }
    
    
    
}
