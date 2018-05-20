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
        
        System.out.println(symbolList.size());
        
        Object[][] data = new Object[symbolList.size()][12];
              
        for (int i = 0; i < symbolList.size(); i++) {
            
            Object[] newData = {
                symbolList.get(i).type, 
                symbolList.get(i).id, 
                String.valueOf(symbolList.get(i).initialized),
                String.valueOf(symbolList.get(i).used),
                String.valueOf(symbolList.get(i).scope),
                String.valueOf(symbolList.get(i).params),
                String.valueOf(symbolList.get(i).params_position),
                String.valueOf(symbolList.get(i).vector),
                String.valueOf(symbolList.get(i).matriz),
                String.valueOf(symbolList.get(i).function)
            };
            
            data[i] = newData;
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
