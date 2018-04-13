/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.boolang;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author 5928036
 */
public class Application {
    private Interface_Ide ide;
    private Sintatico syntactic = new Sintatico();
    private Semantico semantic = new Semantico();
    private String console_result = "";
    
    private Map<String, Integer> variables = new HashMap<>();
    private Stack<Integer> stack = new Stack<>();
    private Optional<String> targetVariable = Optional.empty();
    
    private void completeAssignment() {
        String value = targetVariable.orElseThrow(() -> {
            return new IllegalStateException("Tentativa de atribuição sem especificar variável!");
        });
        
        variables.put(value, stack.pop());
        targetVariable = Optional.empty();
    }
    
    private void displayValue() {
        int value = stack.pop();
        console_result = console_result + (ide.isOutputBinary() ? Integer.toBinaryString(value) : value) + "\n";
    }
        
    private void modifyStack(BiFunction<Integer, Integer, Integer> operation) {
        int a = stack.pop();
        int b = stack.pop();
        stack.push(operation.apply(a, b));  
    }
    
    private void handleAction(int action, Token token) throws SemanticError {
        switch (action) {
            case 0:
                targetVariable = Optional.of(token.getLexeme());
                break;
            case 1:
                completeAssignment();
                break;
            case 2:
                displayValue();
                break;
            case 3:
                stack.push(Integer.parseUnsignedInt(token.getLexeme(), 2));
                break;
            case 4:
                // Se a variável não existe
                if (!variables.containsKey(token.getLexeme())) {
                  throw new SemanticError("Variável não encontrada: \"" + token.getLexeme() + "\"", token.getPosition());
                }
                
                stack.push(variables.get(token.getLexeme()));
                break;
            case 5:
                modifyStack((a, b) -> a + b);
                break;
            case 6:
                modifyStack((a, b) -> b - a);
                break;
            case 7:
                modifyStack((a, b) -> a * b);
                break;
            case 8:
                modifyStack((a, b) -> b / a);
                break;
            case 9:
                modifyStack((a, b) -> (int) Math.pow(b, a));
                break;
        }
    }
    
    
     public Application() {
        
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        semantic.onAction((action, token) -> {
            handleAction(action, token);
        });
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //Interface ide = new Interface();
                //ide.setVisible(true);
                ide = new Interface_Ide();
                
                ide.onExecute((program) -> {
                    Lexico lexical = new Lexico(program);
            
                    console_result = "";
                    try {
                        variables.clear();
                        syntactic.parse(lexical, semantic);

                        ide.displayOutput(console_result);
                    } catch (LexicalError | SemanticError | SyntaticError ex) {
                        console_result = ex.getMessage();
                        ide.displayError(console_result);
                    }
                });
                
                ide.setVisible(true);
            }
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Application();
    }    
}
