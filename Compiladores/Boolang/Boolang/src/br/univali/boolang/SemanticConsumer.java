 
package br.univali.boolang;

public interface SemanticConsumer {
  void consume(int action, Token token) throws SemanticError;
}
