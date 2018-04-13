package compiler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private JScrollPane ScrollCodeArea = new JScrollPane();
    private JScrollPane ScrollResultCodeArea = new JScrollPane();
    
    private JButton ButtonExecute = new JButton("Execute");
    private JButton ButtonCompile = new JButton("Compile");
    
    private void __INIT__ () {
    
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setSize(700, 500);
        this.setMinimumSize(new Dimension(500, 400));
        this.setLayout(new BorderLayout());
        this.setTitle("Eleonora");
        
        CodeArea.setLineWrap(true);
        CodeArea.setColumns(5);
        CodeArea.setRows(15);
        
        ResultCodeArea.setEditable(false);
        ResultCodeArea.setColumns(5);
        ResultCodeArea.setRows(5);
        
        PanelCodeArea.add(BorderLayout.CENTER, )
    }
    
    
    
}
