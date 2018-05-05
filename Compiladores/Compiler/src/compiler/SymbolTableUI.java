package compiler;

import java.awt.BorderLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class SymbolTableUI extends JFrame{
    
    private JPanel panel = new JPanel(new BorderLayout());
    
    public SymbolTableUI(List<Symbol> symbolList) {
        
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Ide.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setSize(700, 650);
        //this.setMinimumSize(new Dimension(700, 500));
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setTitle("Eleonora");
        
        
        String[] names = {"Type", "id", "Init", "Used", "Scope", "Params", "Pos", "Vet", "Matriz", "Function"};
        //Object[][] data = {{"int", "a", "F", "T", "1", "F", "2", "T", "F", "T"}};
        
        Object[][] data = new Object[1][symbolList.size()];
                
        for (Symbol s : symbolList) {
            /*
            boolean initialized;
            boolean used;
            int scope;
            boolean params;
            int params_position;
            boolean vector;
            boolean matriz;
            //public boolean reference;
            boolean function;*/
            //String[] names = {"Type", "id", "Init", "Used", "Scope", "Params", "Pos", "Vet", "Matris", "Function"};
            Object[] ndata = {
                s.type, 
                s.id, 
                String.valueOf(s.initialized),
                String.valueOf(s.used),
                String.valueOf(s.scope),
                String.valueOf(s.params),
                String.valueOf(s.params_position),
                String.valueOf(s.vector),
                String.valueOf(s.matriz),
                String.valueOf(s.function)
            }; 
            data[0] = ndata;
        }
        
        
        
        JTable table = new JTable(data, names);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        
        
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        panel.add(table);
        add(panel);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
        setVisible(true);
    }
}
