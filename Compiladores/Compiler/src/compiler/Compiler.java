package compiler;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Compiler {
    private Ide ide;
    
    public Compiler() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        java.awt.EventQueue.invokeLater(() -> {
             ide = new Ide();
             
             ide.onExecute((program) -> {
                 Lexico lexical = new Lexico(program);
                 
                 /*console_result = "";
                 try {
                     variables.clear();
                     syntactic.parse(lexical, semantic);
                     
                     ide.displayOutput(console_result);
                 } catch (LexicalError | SemanticError | SyntaticError ex) {
                     console_result = ex.getMessage();
                     ide.displayError(console_result);
                 }*/
             });
             
             ide.setVisible(true);
         });   
    }
    
    public static void main(String[] args) {
        new Compiler();
    }
}
