package compiler;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Semantico implements Constants
{
    private SemanticTable semanticTable = new SemanticTable();
    
    private List<Symbol> symbolTable =  new ArrayList<>();
    
    private Stack actualScope = new Stack();
    private Stack expStack = new Stack();
    private Stack operation = new Stack();
    
    private int scope = 0;
    private int params_position = 0;
    
    Symbol actualSymbol = new Symbol();
    
    public void clearTable() {
        this.symbolTable = new ArrayList<>();
        this.params_position = 0;
        this.actualSymbol = new Symbol();
        this.scope = 0;
        actualScope = new Stack();
        actualScope.push(scope);
        
        /*for (Symbol s : symbolTable) {
            System.out.println(s.type);
            System.out.println(s.id);
            System.out.println(s.initialized);
            System.out.println(s.used);
            System.out.println(s.params);
            System.out.println(s.params_position);
            System.out.println(s.vector);
            System.out.println(s.scope);
            System.out.println(s.matriz);
            System.out.println(s.function);
            
        }*/
    }
    
    private boolean executeExp() {
        int num1 = (int) expStack.pop();
        int op = (int) expStack.pop();
        int num2 = (int) expStack.pop();

        int result = SemanticTable.resultType(num1, num2, op);
        switch(result) {
            case 0:
                expStack.push(result);
                return true;
            case 1: 
                expStack.push(result);
                return true;
                //TINHA QUE MOSTRAR WARNING, N SEI COMO VOU FAZER ISSO!
            case 2: 
                return false;
        }
        return false;
    }
    
    public List<Symbol> getList () {
        return this.symbolTable;
    }
    
    public void flush() {
       this.actualSymbol = new Symbol();
    }
    
    public void addSymboltoList() {
        symbolTable.add(actualSymbol);
        flush();
    }
    
    public boolean verifyExistingVariable(String variableName, int variableScope) {
        if (!symbolTable.isEmpty()) {
            for (Symbol b : symbolTable) {
                if (b.id.equals(variableName) && b.scope <= variableScope) {
                    return true;
                }
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
                break;
            case 2:
                if (verifyExistingVariable(token.getLexeme(), (int) actualScope.lastElement())) {
                    throw new Exception("Função já existente!");
                }
                
                actualSymbol.id = token.getLexeme();
                actualSymbol.function = true;
                actualSymbol.scope = (int) actualScope.lastElement();
                this.scope++;
                this.actualScope.push(this.scope);
                
                break;
            case 3:
                addSymboltoList();
                break;
            case 4:
                //funcao nao esta recebendo parametros
                addSymboltoList();
                break;
            case 5:
                params_position++;
                actualSymbol.params_position = params_position;
                actualSymbol.scope = (int) actualScope.lastElement();
                actualSymbol.params = true;
                break;
            case 6:
                
                if (verifyExistingVariable(token.getLexeme(), (int) actualScope.lastElement())) {
                    throw new Exception("Váriavel já existente!");
                }
                
                actualSymbol.id = token.getLexeme();
                actualSymbol.scope = (int) actualScope.lastElement();
                addSymboltoList();
                break;
            case 7:
                if (verifyExistingVariable(token.getLexeme(), (int) actualScope.lastElement())) {
                    throw new Exception("Váriavel já existente!");
                }
                
                actualSymbol.id = token.getLexeme();
                actualSymbol.vector = true;
                actualSymbol.scope = (int) actualScope.lastElement();
                addSymboltoList();
                break;
            case 8:
                params_position = 0;
                break;
            case 9:
                this.actualScope.pop();
                break;
              
            //10 - 10 se pah |  declaração de variaveis 
            case 10:
                actualSymbol.scope = (int) actualScope.lastElement();
                break; 
               
            //LOOP'S 15-?
            case 15:
                flush();
                this.scope++;
                this.actualScope.push(this.scope);
                break;
            case 16:
                this.actualScope.pop();
                break;
            case 17:
                flush();
                this.scope++;
                this.actualScope.push(this.scope);
                break;
            case 18:
                flush();
                this.scope++;
                this.actualScope.push(this.scope);
                break;
                
            // exp 50 - ?
            case 50:
                expStack.push(SemanticTable.FLO);
                break;
            case 51:
                expStack.push(SemanticTable.INT);
                break;
            case 52: 
                // binario
                break;
            case 53:
                for (Symbol b : symbolTable) {
                    if (b.id.equals(token.getLexeme()) && b.scope <= (int) actualScope.lastElement()) {
                        switch (b.type) {
                            case "int":
                                expStack.push(0);
                                break;
                            case "float":
                                expStack.push(1);
                                break;
                            case "char":
                                expStack.push(2);
                                break;
                            case "string":
                                expStack.push(3);
                                break;
                            case "boolean":
                                expStack.push(4);
                                break;
                            default:
                                break;
                        }
                    }
                }
                break;
            
            case 54:
                expStack.push(3);
                break;
                
            case 75:
                expStack.push(0);
                break;
            case 76:
                expStack.push(1);
                break;
            case 77:
                expStack.push(2);
                break;
            case 78: 
                expStack.push(3);
                break;
            case 79:
                break;
            case 80:
                if (!executeExp()) {
                    throw new Exception("Expressão mal formulada");
                }
                break;
            case 81:
                if (!executeExp()) {
                    throw new Exception("Expressão mal formulada");
                }
                break;
        }
    }	
}


/*


{

int a () {

	if ( 1 + 1 ) {
		int b;
	
	}

	int b;
}

int b () {



}



}
*/