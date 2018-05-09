package compiler;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Compiler {
    private Ide ide;
    private Sintatico syntatic = new Sintatico();
    private Semantico semantic = new Semantico();
            
    public Compiler() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        semantic = new Semantico();
       
        
        java.awt.EventQueue.invokeLater(() -> {
             ide = new Ide();
             
             ide.onExecute((program) -> {
                 Lexico lexical = new Lexico(program);
                 
                 //ide.displayValue(program);
                 try {
                     semantic.clearTable();
                     syntatic.parse(lexical, semantic);
                     
                     ide.displayValue("Sucesso ao compilar!");
                     
                 } catch (Exception ex) {
                     ide.displayError(ex.getMessage());
                 } 
             });
             
             ide.onOpenTable( b -> {
                 SymbolTableUI tableSymbolUI = new SymbolTableUI(semantic.getList());
             });
             //});
             ide.setVisible(true);
         });   
    }
    
    public static void main(String[] args) {
        new Compiler();
    }
}
