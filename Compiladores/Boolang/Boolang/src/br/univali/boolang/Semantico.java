package br.univali.boolang;

public class Semantico implements Constants {
    private SemanticConsumer handler = null;
    
    public void executeAction(int action, Token token) throws SemanticError {
        if (handler != null) {
            handler.consume(action, token);
        }
    }
    
    public void onAction(SemanticConsumer handler) {
        this.handler = handler;
    }
}