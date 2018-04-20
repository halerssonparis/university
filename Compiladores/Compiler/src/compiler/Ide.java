package compiler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Ide extends JFrame{
    
    private File file = null;
    
    private final JPanel panelMenuBar = new JPanel(new BorderLayout());
    private final JPanel panelTotal = new JPanel(new BorderLayout());
    
    private final JPanel panelCodeArea = new JPanel(new BorderLayout());
    private final JPanel panelResultCodeArea = new JPanel(new BorderLayout());
    private final JPanel panelTopButtons = new JPanel(new FlowLayout());
    
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menu = new JMenu("files");
    private final JMenuItem item1 = new JMenuItem("Open File");
    private final JMenuItem item2 = new JMenuItem("Save File");
    private final JMenuItem item3 = new JMenuItem("About");
    
    private final JTextArea codeArea = new JTextArea();
    private final JTextArea resultCodeArea = new JTextArea();
    
    private final JScrollPane scrollCodeArea = new JScrollPane(codeArea);
    private final JScrollPane scrollResultCodeArea = new JScrollPane(resultCodeArea);
    
    private final JButton buttonExecute = new JButton("Execute");
    private final JButton buttonCompile = new JButton("Compile");
    
    public void onExecute(Consumer<String> executionConsumer) {
        buttonCompile.addActionListener((event) -> {
            executionConsumer.accept(codeArea.getText());
        });
    }
    
    public void displayValue(String value) {
        resultCodeArea.setText(value);
    }
    
    public void displayError(String error) {
        resultCodeArea.setText(error);
    }
    
    public Ide() {
        Init();
    }
    
    private void initActionListenersButtons() {
        item1.addActionListener((ActionEvent ae) -> {
            JFileChooser fileChooser = new JFileChooser();
            
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                
                file = fileChooser.getSelectedFile();
               
                try {
                    FileReader reader = new FileReader(file.getPath());
                    BufferedReader bf = new BufferedReader(reader);
                    String str = "";
                    
                    System.out.println(file);
                    while(bf.ready()) {
                        str += bf.readLine();
                        str += "\n";
                        //codeArea.append(bf.readLine());
                        //codeArea.append("\n");
                    }
                    codeArea.setText(str);
                    bf.close();
                    this.setTitle("Eleonora    > " + file.getPath());
                    
                } catch (IOException ex) {
                    displayError(ex.getMessage());
                }
                System.out.println(file);
            }
        });
        
        item2.addActionListener((ActionEvent ae) -> {
            if (file == null){
                JFileChooser chooserSaveFile = new JFileChooser();
                
                chooserSaveFile.setCurrentDirectory(new File("/home"));
                
                if (chooserSaveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter fw = new FileWriter(chooserSaveFile.getSelectedFile());
                        codeArea.write(fw);
                        file = chooserSaveFile.getSelectedFile();
                        this.setTitle("Eleonora    > " + file.getPath());
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
               
            }
            else {
                try {
                    String path = file.getPath();
                    file.delete();
                    file = new File(path);
                    file.createNewFile();
                    
                    FileWriter fileWrite = new FileWriter(file.getAbsoluteFile(), true);
                    codeArea.write(fileWrite);
                    fileWrite.close();
                } catch (IOException ex) {
                    Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        item3.addActionListener((ActionEvent ev) -> {
            JOptionPane.showMessageDialog(null, "O que importa é o importante! [1976, Clinton, Bill]");
        });
    }
    
    private void Init () {
    
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setSize(700, 500);
        //this.setMinimumSize(new Dimension(700, 500));
        this.setResizable(false);
        
        this.setLayout(new BorderLayout());
        this.setTitle("Eleonora");
        
        initActionListenersButtons();
        
        menuBar.add(menu);
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        panelMenuBar.add(menuBar);
        
        codeArea.setLineWrap(false);
        codeArea.setColumns(5);
        codeArea.setRows(15);
        
        resultCodeArea.setEditable(false);
        resultCodeArea.setLineWrap(true);
        resultCodeArea.setColumns(5);
        resultCodeArea.setRows(5);
        
        panelCodeArea.add(BorderLayout.CENTER, scrollCodeArea);
        panelResultCodeArea.add(BorderLayout.SOUTH, scrollResultCodeArea);
        panelTopButtons.add(buttonExecute);
        panelTopButtons.add(buttonCompile);
        
        
        panelCodeArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelResultCodeArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelTopButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        panelTotal.add(BorderLayout.NORTH, panelTopButtons);
        panelTotal.add(BorderLayout.CENTER, panelCodeArea);
        panelTotal.add(BorderLayout.SOUTH, panelResultCodeArea);
        
        add(BorderLayout.NORTH, panelMenuBar);
        add(BorderLayout.SOUTH, panelTotal);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
    }
}
