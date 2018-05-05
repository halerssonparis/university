package compiler;


import java.util.ArrayList;
import java.util.List;

public class Semantico implements Constants
{
    private List<Symbol> symbolTable =  new ArrayList<>();
    int scope = 0;
    int params_position = 0;
    
    Symbol actualSymbol = new Symbol();
    
    
    public void flush() {
       this.actualSymbol = new Symbol();
    }
    
    public void addSymboltoList() {
        symbolTable.add(actualSymbol);
        flush();
    }
    
    public boolean verifyExistingVariable(String variableName) {
        for (Symbol b : symbolTable) {
            if (b.id.equals(variableName)) {
                return true;
            }
        }
        return false;
    }
    
    public void executeAction(int action, Token token)	throws SemanticError, Exception
    {
        switch(action) {
            // 1 - 9 Trata de Funções
            case 1: 
                actualSymbol.type = token.getLexeme();
            case 2:
                if (verifyExistingVariable(token.getLexeme())) {
                    throw new Exception("Função já existente!");
                }
                
                actualSymbol.id = token.getLexeme();
                actualSymbol.function = true;
                actualSymbol.scope++;
            case 3:
                //funcao estao recebendo parametros
                actualSymbol.params = true;
                addSymboltoList();
            case 4:
                //funcao nao esta recebendo parametros
                addSymboltoList();
            case 5:
                actualSymbol.params_position = params_position;
                params_position++;
                actualSymbol.scope = this.scope;
            case 6:
                if (verifyExistingVariable(token.getLexeme())) {
                    throw new Exception("Nome já existente!");
                }
                actualSymbol.id = token.getLexeme();
                addSymboltoList();
            case 7:
                if (verifyExistingVariable(token.getLexeme())) {
                    throw new Exception("Nome já existente!");
                }
                
                actualSymbol.id = token.getLexeme();
                actualSymbol.vector = true;
                addSymboltoList();
            case 8:
                params_position = 0;
            case 9:
                this.scope--;
              
            //10 - 10 se pah |  declaração de variaveis 
            case 10:
                actualSymbol.scope = scope;
                
                
            //<EXP>
            case 11:
                
                
        }
    }	
}
