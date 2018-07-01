package compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import BackEnd.AssemblyGenerator;
        
public class Semantico implements Constants
{
    private SemanticTable semanticTable = new SemanticTable();
    public List<Symbol> symbolWarning = new ArrayList<>();
    private List<Symbol> symbolTable =  new ArrayList<>();
    
    public List<AssemblyStruct> assemblyData = new ArrayList<>();
    public List<AssemblyStruct> assemblyText = new ArrayList<>();
    
    Symbol returnFunction = new Symbol();
    private String expRelational;
    
    
    
    private Stack vectorPusher = new Stack();
    private List<String> arithmeticVector = new ArrayList<>();
    
    private List<List> expController = new ArrayList<>();
    
    private Stack actualScope = new Stack();
    private Stack expStack = new Stack();
    private Stack operation = new Stack();
    private Stack signals = new Stack();
    private Stack paramsExp = new Stack();
    private Stack paramsNameFunction = new Stack();
            
    private Stack LeftAndRigthExpression = new Stack();
    
    private Stack tempStack = new Stack();
    private int scope = 0;
    private int params_position = 0;
    
    Symbol actualSymbol = new Symbol();
    AssemblyStruct actualAssembly = new AssemblyStruct();
    
    Symbol declarationAcu = new Symbol();
    String funcP = "";
    String vectorAcu = "";
    
    private int controlExp1000 = 1000;
    private boolean expVecotr = false;
    String isArithmetic;
    boolean isVector = false;
    boolean receiveSomething = false;
    private int countTemp = 0;
    private boolean isExp = false;
    
    private String indexVectorReceive = "";
    private boolean sideLeft = true;
    int count = 1;
    
    String doWhileAcu = "";
    boolean isDoWhile = false;
    public void clearTable() {
        isDoWhile = false;
        doWhileAcu = "";
        count = 1;
        expRelational = "";
        this.symbolTable = new ArrayList<>();
        this.actualSymbol = new Symbol();
        this.symbolWarning = new ArrayList<>();
        
        this.assemblyData = new ArrayList<>();
        this.assemblyText = new ArrayList<>();
        
        this.params_position = 0;
        this.scope = 0;
        actualScope = new Stack();
        actualScope.push(scope);
        
        this.expStack = new Stack();
        this.operation = new Stack();
        this.signals = new Stack();
        this.vectorPusher = new Stack();
        declarationAcu = new Symbol();
        vectorAcu = "";
        isArithmetic = "";
        arithmeticVector = new ArrayList<>();
        LeftAndRigthExpression = new Stack();
        
        expController = new ArrayList<>();
        List n = new ArrayList<>();
        expController.add(n);
        
        isVector = false;
        countTemp = 0;
        isExp = false;
        tempStack = new Stack();
        sideLeft = true;
        expVecotr = false;
        controlExp1000 = 1000;
        
        
        indexVectorReceive = "";
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
        returnFunction = new Symbol();
   
    }
    
    private void assemblyGeneretor(AssemblyStruct n2, String op, AssemblyStruct n1) {
        String command1 = "int".equals(n1.type) ? "LDI" : "LD";
        String command2 = "int".equals(n2.type) ? op.equals("+") ? "ADDI" : "SUBI" : op.equals("-") ? "SUBI" : "SUB";
        
        AssemblyStruct assembly = new AssemblyStruct(command1, n1.id);
        assemblyText.add(assembly);
            
        assembly = new AssemblyStruct(command2, n2.id);
        assemblyText.add(assembly);
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
        System.out.println(actualSymbol.type + " " + actualSymbol.id);
        symbolTable.add(actualSymbol);
        flush();
    }
    
    private void addAssemblyExpression() {
        
        if ((this.sideLeft || isArithmetic.equals("+")) && isExp) {
            AssemblyStruct assemblySTO1000 = new AssemblyStruct("STO", "1000");
            assemblyText.add(assemblySTO1000);
        }else if (!this.sideLeft && isExp) {
            AssemblyStruct assemblySTO1001 = new AssemblyStruct("STO", "1001");
            assemblyText.add(assemblySTO1001);
        }
        
    }
    
    private void generateAssembly(String lexeme, int code) {
        String instruction = code == 0 ? "LDI" : "LD";
        String instructionSUB = code == 0 ? "SUBI" : "SUB";
        String instructionADD = code == 0 ? "ADDI" : "ADD";
        
        //System.out.println(arithmeticVector.pop().equals("2"));
        if (isArithmetic.equals("")) {
            AssemblyStruct assembly = new AssemblyStruct(instruction, lexeme);
            assemblyText.add(assembly);
        } else {
            if (isArithmetic.equals("+")) {
                AssemblyStruct newAssembly = new AssemblyStruct(instructionADD, lexeme);
                assemblyText.add(newAssembly);   
            } else if (isArithmetic.equals("-")) {    
                AssemblyStruct newAssembly = new AssemblyStruct(instruction, lexeme);
                assemblyText.add(newAssembly);   
            }
        }
    }
  
    
    public boolean verifyExistingVariable(String variableName, int variableScope) {
        if (!symbolTable.isEmpty()) {
            for (Symbol b : symbolTable) {
                if (b.id.equals(variableName) && b.funcP.equals(this.funcP)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void printerson() {
        for (AssemblyStruct e : assemblyData) {
            System.out.println("command: " + e.command + " id: " + e.id);
        }
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
                actualSymbol.initialized = true;
                actualSymbol.funcP = "Global";
                
                AssemblyStruct newFunctionAssembly = new AssemblyStruct("_"+token.getLexeme()+":", "");
                assemblyText.add(newFunctionAssembly);
                returnFunction = actualSymbol;
                
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
                actualSymbol.initialized = true;
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
                
                actualAssembly.id = token.getLexeme();
                actualAssembly.command = "0";
                assemblyData.add(actualAssembly);
                
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
                
                //System.out.println(token.getLexeme());
                actualAssembly.id = token.getLexeme();
                actualAssembly.command = "0";
                assemblyData.add(actualAssembly);
                isVector = true;
                
                
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
                
            case 11:
                
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
                count++;
                AssemblyStruct assembly = new AssemblyStruct("JMP", "R"+(count));
                assemblyText.add(assembly);
                
                
                assembly = new AssemblyStruct("R"+(count - 1)+":", "");
                assemblyText.add(assembly);
                
                flush();
                this.scope++;
                this.actualScope.push(this.scope);
                break;
                
            case 21:
                AssemblyStruct newAssembly = new AssemblyStruct("R"+count+":", "");
                assemblyText.add(newAssembly);
                count++;
                break;
                
            case 25:
                newAssembly = new AssemblyStruct("R"+count+":", "");
                assemblyText.add(newAssembly); 
                count++;
                break;
                
            case 26:
                newAssembly = new AssemblyStruct("JMP", "R"+(--count));
                assemblyText.add(newAssembly); 
                
                count++;
                newAssembly = new AssemblyStruct("R"+count+":", "");
                assemblyText.add(newAssembly); 
                count++;
                break;
                
            case 27:
                isDoWhile = true;
                newAssembly = new AssemblyStruct("R"+count+":", "");
                assemblyText.add(newAssembly);
                break;
                
            case 28: 
                //newAssembly = new AssemblyStruct("R"+count+":", "");
                //assemblyText.add(newAssembly); 
                count++;
                break;
                
            // exp 50 - ?
            case 50:
                expStack.push(SemanticTable.FLO);
               
                break;
            case 51:
                AssemblyStruct ass1 = new AssemblyStruct("", token.getLexeme());
                ass1.type = "int";
                LeftAndRigthExpression.push(ass1);
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
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                                actualAssembly.type = "variable";
                                actualAssembly.id = token.getLexeme();
                                LeftAndRigthExpression.push(actualAssembly);
                                return;
                            case "float":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(1);
                                b.used = true;
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                             
                                return;
                            case "char":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas char");
                                }
                                expStack.push(2);
                                b.used = true;
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                                //generateAssembly(token.getLexeme(), 1);
                                return;
                            case "string":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas string");
                                }
                                expStack.push(3);
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                                //generateAssembly(token.getLexeme(), 1);
                                return;
                            case "boolean":
                                if (!signals.lastElement().equals("!")) {
                                    throw new Exception("Não pode negar uma boolean");
                                }
                                signals.pop();
                                expStack.push(4);
                                b.used = true;
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                                //generateAssembly(token.getLexeme(), 1);
                                return;
                        }
                    }
                }
                throw new Exception("Váriavel  '" + token.getLexeme() + "'  não declarada!");
                
                
            case 54:
                if (!signals.isEmpty()) {
                    throw new Exception("Não pode negar umas string");
                }
                
                /*generateAssembly(token.getLexeme(), 0);
                expStack.push(SemanticTable.STR);*/
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
                        paramsNameFunction.push(b);
                        switch (b.type) {
                            case "int":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(SemanticTable.INT);
                                b.used = true;
                                return;
                            case "float":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(SemanticTable.FLO);
                                b.used = true;
                                return;
                            case "char":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas char");
                                }
                                expStack.push(SemanticTable.CHA);
                                b.used = true;
                                return;
                            case "string":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas string");
                                }
                                expStack.push(SemanticTable.STR);
                                return;
                            case "boolean":
                                if (!signals.lastElement().equals("!")) {
                                    throw new Exception("Não pode negar uma boolean");
                                }
                                signals.pop();
                                expStack.push(SemanticTable.BOO);
                                b.used = true;
                                return;
                        }
                    }
                }
                throw new Exception("Função   '" + token.getLexeme() + "'   não declarada!");
             
                
            case 60:
                
                 for (Symbol b : symbolTable) {
                    if (b.id.equals(token.getLexeme()) && b.scope <= (int) actualScope.lastElement() && b.funcP.equals(this.funcP)) {
                        
                        /*if (isArithmetic.equals("+") || isArithmetic.equals("-")) {
                            AssemblyStruct assembly = new AssemblyStruct("STO", "temp"+countTemp);
                            tempStack.push("temp"+countTemp);
                            assemblyText.add(assembly);
                            countTemp++;
                        }*/
                        //addAssemblyExpression();
                        List newList = new ArrayList<>();
                        expController.add(newList);
                        
                        vectorPusher.push(b.id);
                        this.arithmeticVector.add(isArithmetic);
                        isArithmetic = "";
                        isExp = true;
                        
                        switch (b.type) {
                            case "int":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(0);
                                b.used = true;
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                                this.vectorAcu = token.getLexeme();
                                //addAssemblyExpression();
                                return;
                            case "float":
                                if (!signals.isEmpty()) {
                                    signals.pop();
                                }
                                expStack.push(1);
                                b.used = true;
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                                //addAssemblyExpression();
                                this.vectorAcu = token.getLexeme();
                                return;
                            case "char":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas char");
                                }
                                expStack.push(2);
                                b.used = true;
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                               // generateAssembly(token.getLexeme(), 1);
                                return;
                            case "string":
                                if (!signals.isEmpty()) {
                                    throw new Exception("Não pode negar umas string");
                                }
                                expStack.push(3);
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                                //generateAssembly(token.getLexeme(), 1);
                                return;
                            case "boolean":
                                if (!signals.lastElement().equals("!")) {
                                    throw new Exception("Não pode negar uma boolean");
                                }
                                signals.pop();
                                expStack.push(4);
                                b.used = true;
                                
                                if (!b.initialized && b.warning == 0) {
                                    b.message = b.message + " | Váriavel sendo usada sem ser inicializada! ";
                                    b.warning = 1;
                                }
                                //generateAssembly(token.getLexeme(), 1);
                                return;
                        }
                    }
                }
                throw new Exception("Váriavel  '" + token.getLexeme() + "'  não declarada!");
                
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
                expController.get(expController.size() -1 ).add(token.getLexeme());
                expStack.push(SemanticTable.SUM);
                LeftAndRigthExpression.push(token.getLexeme());
                break;
            case 76:
                expController.get(expController.size() -1 ).add(token.getLexeme());
                expStack.push(SemanticTable.SUB);
                LeftAndRigthExpression.push(token.getLexeme());
                break;
            case 77:
                expController.get(expController.size() -1 ).add(token.getLexeme());
                expStack.push(SemanticTable.MUL);
                break;
            case 78: 
                expController.get(expController.size() -1 ).add(token.getLexeme());
                expStack.push(SemanticTable.DIV);
                break;
            case 79:
                break;
            case 80:
                if (!executeExp()) {
                    throw new Exception("Expressão mal formulada");
                }
               
                assemblyGeneretor((AssemblyStruct) LeftAndRigthExpression.pop(), (String) LeftAndRigthExpression.pop(), (AssemblyStruct) LeftAndRigthExpression.pop());
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
                if (LeftAndRigthExpression.size() == 1) {
                    AssemblyStruct c = (AssemblyStruct) LeftAndRigthExpression.pop();
                    String command = c.type == "int" ? "LDI" : "LD";
                    assembly = new AssemblyStruct(command, c.id);
                    assemblyText.add(assembly);
                }else {
                    assemblyGeneretor((AssemblyStruct) LeftAndRigthExpression.pop(), (String) LeftAndRigthExpression.pop(), (AssemblyStruct) LeftAndRigthExpression.pop());
                }
                
                assembly = new AssemblyStruct("STO", "temp2");
                assemblyText.add(assembly);
                
                assembly = new AssemblyStruct("LD", "temp1");
                assemblyText.add(assembly);
                
                assembly = new AssemblyStruct("SUB", "temp2");
                assemblyText.add(assembly);
                
                if (isDoWhile) {
                    assembly = new AssemblyStruct(doWhileAcu, "R"+count+":");
                    assemblyText.add(assembly);
                    isDoWhile = false;
                }else {
                    assembly = new AssemblyStruct(expRelational, "R"+count+":");
                    assemblyText.add(assembly);
                }
                break;
                
            case 83:
                signals.push(token.getLexeme());
                break;
                
            case 84:
                expStack.push(SemanticTable.REL);
                
                
                if (token.getLexeme().equals("<")) {
                    expRelational = "BGE";
                    doWhileAcu = "BLT";
                }
                else if (token.getLexeme().equals("<=")) {
                    expRelational = "BGT";
                    doWhileAcu = "BLE";
                }
                else if (token.getLexeme().equals("==")) {
                    expRelational = "BNE";
                    doWhileAcu = "BEQ";
                }
                else if (token.getLexeme().equals(">=")) {
                    expRelational = "BLT";
                    doWhileAcu = "BGE";
                }
                else if (token.getLexeme().equals(">")) {
                    expRelational = "BLE";
                    doWhileAcu = "BGT";
                }
                else if (token.getLexeme().equals("!=")) {
                    expRelational = "BEQ";
                    doWhileAcu = "BNE";
                }
                
                if (LeftAndRigthExpression.size() == 1) {
                    AssemblyStruct c = (AssemblyStruct) LeftAndRigthExpression.pop();
                    String command = c.type == "int" ? "LDI" : "LD";
                    AssemblyStruct assembly32 = new AssemblyStruct(command, c.id);
                    assemblyText.add(assembly32);
                }else {
                    assemblyGeneretor((AssemblyStruct) LeftAndRigthExpression.pop(), (String) LeftAndRigthExpression.pop(), (AssemblyStruct) LeftAndRigthExpression.pop());
                }
                 
                AssemblyStruct STO = new AssemblyStruct("STO", "temp1");
                assemblyText.add(STO);
                break;
                
            case 99:
                this.declarationAcu.type = "int";
                break;
                
            case 100:
                
                String value = returnName((int) expStack.lastElement());
                //isArithmetic = "";
                if (expController.size() == 1 ) {
                    isExp = false;
                }

                switch (declarationAcu.type) {
                    case "int":
                        int expResult = SemanticTable.atribType(SemanticTable.INT, (int) expStack.pop());
                        
                        if (expResult == 1) {
                            declarationAcu.message = " | int -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResult == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                    case "float":
                        
                        int expResultFloat = SemanticTable.atribType(SemanticTable.FLO, (int) expStack.pop());
                        if (expResultFloat == 1) {
                            declarationAcu.message = " | float -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResultFloat == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                    case "char":
                        int expResultChar = SemanticTable.atribType(SemanticTable.CHA, (int) expStack.pop());
                        if (expResultChar == 1) {
                            declarationAcu.message = " | char -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResultChar == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                    case "string":
                        
                        int expResultString = SemanticTable.atribType(SemanticTable.STR, (int) expStack.pop());
                        if (expResultString == 1) {
                            declarationAcu.message = " | string -> " + value + " " + ":Warning";
                            symbolWarning.add(declarationAcu);
                        }
                        else if (expResultString == -1) {
                            throw new Exception("Atribuição invalida! --- Tipos não compativeis");
                        }
                        break;
                    case "boolean":
                        int expResultBool = SemanticTable.atribType(SemanticTable.BOO, (int) expStack.pop());
                        if (expResultBool == 1) {
                            declarationAcu.message = " | boolean -> " + value + " " + ":Warning";
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
                        //this.vectorAcu = b.type;
                        
                        actualAssembly.id = token.getLexeme();
                        return;
                    }
                }
                throw new Exception("Variável   '" + token.getLexeme() + "'   não declarada!");   
                
            case 102:
                
                isVector = true;
                isExp = true;
                //O ERRO TA DEPOIS DESSA LINHA
                //declarationAcu.type = this.vectorAcu;
                
                /*indexVectorReceive = "temp"+countTemp;
                AssemblyStruct assemblySTOindex = new AssemblyStruct("STO", "1002");
                assemblyText.add(assemblySTOindex);
                
                countTemp++;*/
                
                break;
                        
            case 110:
                receiveSomething = true;
                isArithmetic = "";
                /*indexVectorReceive = "temp"+countTemp;
                assemblySTOindex = new AssemblyStruct("STO", indexVectorReceive);
                assemblyText.add(assemblySTOindex);
                
                countTemp++;*/
                declarationAcu = symbolTable.get(symbolTable.size() - 1);
                symbolTable.get(symbolTable.size() - 1).initialized = true;
                break;
                
            case 120:
                for (Symbol s : symbolTable) {
                    if (s.id.equals(token.getLexeme())) {
                        AssemblyStruct assemblyPUT = new AssemblyStruct("LD", "$in_port");
                        assemblyText.add(assemblyPUT);
                        
                        assemblyPUT = new AssemblyStruct("STO", s.id);
                        assemblyText.add(assemblyPUT);
                        return;
                    }
                }
                throw new Exception("Variável  '" + token.getLexeme() + "'   não foi declarada!");
            
            case 121:
                /*for (Symbol s : symbolTable) {
                    if (s.id.equals(token.getLexeme())) {
                        
                        AssemblyStruct assemblyECHO = new AssemblyStruct("LD", s.id);
                        assemblyText.add(assemblyECHO);
                        
                        AssemblyStruct assemblyPUT = new AssemblyStruct("STO", "$out_port");
                        assemblyText.add(assemblyPUT);
                        
                        return;
                    }*
                }*/
                throw new Exception("Variável  '" + token.getLexeme() + "'   não foi declarada!");
                
            case 150:
                paramsExp.push((int) expStack.pop());
                break;
                
            case 151:
                Symbol actualFunction = (Symbol) paramsNameFunction.pop();
                Stack expActulFunction = new Stack();
                
                for (Symbol s : symbolTable) {
                    if (s.params && s.funcP.equals(actualFunction.id)) {
                        switch (s.type) {
                            case "int":
                                expActulFunction.push(SemanticTable.INT);
                                break;
                            case "float":
                                expActulFunction.push(SemanticTable.FLO);
                                break;
                            case "char":
                                expActulFunction.push(SemanticTable.CHA);
                                break;
                            case "string":
                                expActulFunction.push(SemanticTable.STR);
                                break;
                            case "boolean":
                                expActulFunction.push(SemanticTable.BOO);
                                break;
                        }
                    }
                }
                
                while(!expActulFunction.empty()) {
                    if (expActulFunction.empty() || paramsExp.empty()) {
                        throw new Exception("Função  '" + actualFunction.id + "'  não definida!");
                    }
                    switch (SemanticTable.atribType((int) paramsExp.pop(), (int) expActulFunction.pop())) {
                        case 1:
                            //bitch
                            break;
                        case -1:
                            throw new Exception("Função  '" + actualFunction.id + "'  não definida!");
                    }
                }
                
                break;
                
            case 160:
                
                for (Symbol b: symbolTable) {
                    if (b.id.equals(returnFunction.id) && b.function) {
                        switch(b.type) {
                            case "int":
                                switch(semanticTable.atribType(semanticTable.INT, (int) expStack.pop())) {
                                    case -1:
                                        throw new Exception("return é diferente do tipo da função '" + b.id + "'");
                                }
                                return;
                            case "float":
                                switch(semanticTable.atribType(semanticTable.FLO, (int) expStack.pop())) {
                                    case -1:
                                        throw new Exception("return é diferente do tipo da função '" + b.id + "'");
                                }
                                return;
                                
                            case "char":
                                
                                switch(semanticTable.atribType(semanticTable.CHA, (int) expStack.pop())) {
                                    case -1:
                                        throw new Exception("return é diferente do tipo da função '" + b.id + "'");
                                }
                                return;
                             
                            case "string":
                                switch(semanticTable.atribType(semanticTable.STR, (int) expStack.pop())) {
                                    case -1:
                                        throw new Exception("return é diferente do tipo da função '" + b.id + "'");
                                }
                                return;
                                
                            case "boolean":
                                switch(semanticTable.atribType(semanticTable.BOO, (int) expStack.pop())) {
                                    case -1:
                                        throw new Exception("return é diferente do tipo da função '" + b.id + "'");
                                }
                                return;
                                
                        }
                    }
                }
                
                break;
                
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
            
                break;
        }   
    }	
}