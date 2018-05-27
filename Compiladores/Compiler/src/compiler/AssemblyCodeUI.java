package compiler;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class AssemblyCodeUI extends JFrame{
    
    private File file = null;
    
    private final JPanel panelMenuBar = new JPanel(new BorderLayout());
    private final JPanel panel = new JPanel(new BorderLayout());
    
    
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menu = new JMenu("Arquivo");
    
    private final JMenuItem item1 = new JMenuItem("Save .asm");
    
    
    private final JTextArea codeArea = new JTextArea();
    
    //trocar c por uma lista 
    public AssemblyCodeUI(List<AssemblyStruct> d, List<AssemblyStruct> t) {
       
        initActionListeners();
        this.setSize(700, 650);
        //this.setMinimumSize(new Dimension(700, 500));
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setTitle("Eleonora - Assembly Code");
        
        String data = ".data\n";
        String text = ".text\n";
        
        for (AssemblyStruct e : d) {
            data = data + e.id+":"+e.command+"\n";
        }
        for (AssemblyStruct e : t) {
            text = text + e.command+" "+e.id+"\n";
        }
        codeArea.setText(data + text);
        
        menu.add(item1);
        menuBar.add(menu);
        
        codeArea.setEditable(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        panelMenuBar.add(BorderLayout.NORTH, menuBar);
        panel.add(BorderLayout.CENTER, codeArea);
        
        add(BorderLayout.NORTH, panelMenuBar);
        add(BorderLayout.CENTER, panel);
        
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void initActionListeners() {
        item1.addActionListener((ActionEvent ae) -> {
            if (file == null){
                JFileChooser chooserSaveFile = new JFileChooser();
                
                chooserSaveFile.setCurrentDirectory(new File("/home"));
                
                if (chooserSaveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter fw = new FileWriter(chooserSaveFile.getSelectedFile());
                        codeArea.write(fw);
                        //file = chooserSaveFile.getSelectedFile();
                        this.setTitle("Eleonora    > " + file.getPath());
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
               
            }
        });
    }
}
