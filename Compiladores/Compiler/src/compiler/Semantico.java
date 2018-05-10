package compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Semantico implements Constants
{
    private SemanticTable semanticTable = new SemanticTable();
    public List<Symbol> symbolWarning = new ArrayList<>();
    
    private List<Symbol> symbolTable =  new ArrayList<>();
    
    private Stack actualScope = new Stack();
    private Stack expStack = new Stack();
    private Stack operation = new Stack();
    private Stack signals = new Stack();
    
    private int scope = 0;
    private int params_position = 0;
    
    Symbol actualSymbol = new Symbol();
    
    Symbol declarationAcu = new Symbol();
    String funcP = "";
    String vectorAcu = "";
    
    public void clearTable() {
        this.symbolTable = new ArrayList<>();
        this.actualSymbol = new Symbol();
        this.symbolWarning = new ArrayList<>();
        
        this.params_position = 0;
        this.scope = 0;
        actualScope = new Stack();
        actualScope.push(scope);
        
        this.expStack = new Stack();
        this.operation = new Stack();
        this.signals = new Stack();
        
        declarationAcu = new Symbol();
        vectorAcu = "";
        
        
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
    
    private String returnName(int id) {
        switch(id) {
            case 0: 
                return "int";
            case 1:
                return "float";
            case 2: 
                return "char";
            case 3:
                return "string";
            case 4:
                return "boolean";
        }
        return "";
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
            case 2: 
                expStack.push(result);
                return true;
            case 3:
                expStack.push(result);
                return true;
            case 4: 
                expStack.push(result);
                return true;
            case -1: 
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
                if (b.id.equals(variableName) && b.scope <= variableScope && b.funcP.equals(this.funcP)) {
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
                this.funcP = token.getLexeme();
                actualSymbol.funcP = "Global";
                
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
                actualSymbol.funcP = this.funcP;
                break;
            case 6:
                
                if (verifyExistingVariable(token.getLexeme(), (int) actualScope.lastElement())) {
                    throw new Exception("Váriavel já existente!");
                }
                
                if (actualSymbol.type.equals("")) {
                    //Trata casos como int a,b,c | b e c não vao ter tipo, dai adiciona o ultimo da tabela
                    actualSymbol.type = symbolTable.get(symbolTable.size()-1).type;
                }
                
                actualSymbol.id = token.getLexeme();
                actualSymbol.scope = (int) actualScope.lastElement();
                actualSymbol.funcP = this.funcP;
                addSymboltoList();
                break;
            case 7:
                if (verifyExistingVariable(token.getLexeme(), (int) actualScope.lastElement())) {
                    throw new Exception("Váriavel já existente!");
                }
                
                if (actualSymbol.type.equals("")) {
                    //Trata casos como int a,b,c | b e c não vao ter tipo, dai adiciona o ultimo da tabela
                    actualSymbol.type = symbolTable.get(symbolTable.size() - 2).type;
                }
                
                actualSymbol.id = token.getLexeme();
                actualSymbol.vector = true;
                actualSymbol.scope = (int) actualScope.lastElement();
                actualSymbol.funcP = this.funcP;
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
                    if (b.id.equals(token.getLexeme()) && b.scope <= (int) actualScope.lastElement() && b.funcP.equals(this.funcP)) {
                        switch (b.type) {
                            case "int":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(0);
                                b.used = true;
                                return;
                            case "float":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(1);
                                b.used = true;
                                return;
                            case "char":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas char");
                                }
                                expStack.push(2);
                                b.used = true;
                                return;
                            case "string":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas string");
                                }
                                expStack.push(3);
                                return;
                            case "boolean":
                                if (!signals.lastElement().equals("!")) {
                                    throw new Exception("Não pode negar uma boolean");
                                }
                                signals.pop();
                                expStack.push(4);
                                b.used = true;
                                return;
                        }
                    }
                }
                throw new Exception("Váriavel  '" + token.getLexeme() + "'  não declarada!");
                
                
            case 54:
                if (!signals.isEmpty()) {
                    throw new Exception("Não pode negar umas string");
                }
                expStack.push(SemanticTable.STR);
                break;
            case 55:
                if (!signals.isEmpty()) {
                    throw new Exception("Não pode negar umas boolean");
                }
                expStack.push(SemanticTable.BOO);
                break;
            case 56:
                if (!signals.isEmpty()) {
                    throw new Exception("Não pode negar umas boolean");
                }
                expStack.push(SemanticTable.CHA);
                break;
            case 57:
                for (Symbol b : symbolTable) {
                    if (b.function && b.id.equals(token.getLexeme())) {
                        switch (b.type) {
                            case "int":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(0);
                                b.used = true;
                                return;
                            case "float":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(1);
                                b.used = true;
                                return;
                            case "char":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas char");
                                }
                                expStack.push(2);
                                b.used = true;
                                return;
                            case "string":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas string");
                                }
                                expStack.push(3);
                                return;
                            case "boolean":
                                if (!signals.lastElement().equals("!")) {
                                    throw new Exception("Não pode negar uma boolean");
                                }
                                signals.pop();
                                expStack.push(4);
                                b.used = true;
                                return;
                        }
                    }
                }
                break;
             
            case 70:
                // Faz op
                break;
            case 71:
                // Faz op
                break;
            case 72:
                // Faz op
                break;
            case 73:
                // Faz op
                break;
            case 74:
                // Faz op
                break;
                
                
                
            case 75:
                expStack.push(SemanticTable.SUM);
                break;
            case 76:
                expStack.push(SemanticTable.SUB);
                break;
            case 77:
                expStack.push(SemanticTable.MUL);
                break;
            case 78: 
                expStack.push(SemanticTable.DIV);
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
                
            case 82:
                if (!executeExp()) {
                    throw new Exception("Expressão mal formulada");
                }
                break;
                
            case 83:
                signals.push(token.getLexeme());
                break;
                
            case 84:
                expStack.push(SemanticTable.REL);
                break;
                
            case 99:
                this.declarationAcu.type = "int";
                break;
                
            case 100:
                String value = returnName((int) expStack.lastElement());
                
                switch (declarationAcu.type) {
                    case "int":
                        int expResult = SemanticTable.atribType(0, (int) expStack.pop());
                        
                        if (expResult == 1) {
                            declarationAcu.message = "int -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResult == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                    case "float":
                        
                        int expResultFloat = SemanticTable.atribType(1, (int) expStack.pop());
                        if (expResultFloat == 1) {
                            declarationAcu.message = "float -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResultFloat == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                    case "char":
                        int expResultChar = SemanticTable.atribType(2, (int) expStack.pop());
                        if (expResultChar == 1) {
                            declarationAcu.message = "char -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResultChar == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                    case "string":
                        
                        int expResultString = SemanticTable.atribType(3, (int) expStack.pop());
                        if (expResultString == 1) {
                            declarationAcu.message = "string -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResultString == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                    case "boolean":
                        int expResultBool = SemanticTable.atribType(4, (int) expStack.pop());
                        if (expResultBool == 1) {
                            declarationAcu.message = "boolean -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResultBool == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                }
                break;
            
            case 101:
                for (Symbol b : symbolTable) {
                    if (b.id.equals(token.getLexeme())) {
                        b.initialized = true;
                        
                        declarationAcu = b;
                        this.vectorAcu = b.type;
                        System.out.println(vectorAcu);
                        return;
                    }
                }
                throw new Exception("Variável   '" + token.getLexeme() + "'   não declarada!");   
                
            case 102:
                System.out.println(this.vectorAcu);
                declarationAcu.type = this.vectorAcu;
                break;
             
            case 110:
                declarationAcu = symbolTable.get(symbolTable.size() - 1);
                symbolTable.get(symbolTable.size() - 1).initialized = true;
                break;
                
            case 120:
                for (Symbol s : symbolTable) {
                    if (s.id.equals(token.getLexeme())) {
                        return;
                    }
                }
                throw new Exception("Variável  '" + token.getLexeme() + "'   não foi declarada!");
            
            case 200:
                for (Symbol b: symbolTable) {
                    if (!b.used || !b.initialized) {
                        if (!(b.used)) {
                            b.message = b.message + " | Não usada";
                        }
                        if (!(b.initialized)) {
                            b.message = b.message + " | Não inicializada";
                        }
                        symbolWarning.add(b);
                    }
                }
                
                for (Symbol s : symbolWarning) {
                    System.out.println(s.id + " = " + s.message);
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