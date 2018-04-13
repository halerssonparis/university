#Options
GenerateScanner = true
GenerateParser = true
Language = Java
ScannerName = Lexico
ParserName = Sintatico
SemanticName = Semantico
Package = br.univali
ScannerCaseSensitive = true
ScannerTable = Full
Input = Stream
Parser = SLR
#RegularDefinitions
ignoraveis: [\s\n\r\t]+
num: [01]+
var: [a-zA-Z_][a-zA-Z0-9_]*
#Tokens
 : {ignoraveis}
number: {num}
plus: "+"
minus: "-"
pow: "^"
mult: "*"
div: "/"
open: "("
show: "print"
close: ")"
variable: {var}
end: ";"
attrib: "<-"


#NonTerminals
<REC_EXPR>
<A>
<B>
<C>
<D>
<SHOW_EXPR>
<EXPR>
<ATTRIB_EXPR>
#Grammar
<REC_EXPR> ::= <EXPR> <REC_EXPR> | <EXPR> ;
<EXPR> ::= <SHOW_EXPR> end | <ATTRIB_EXPR> end ;
<SHOW_EXPR> ::= show <A> ;
<ATTRIB_EXPR> ::= variable attrib <A> ;
<A> ::= <A> plus <B> | <A> minus <B> | <B> ;
<B> ::= <B> mult <C> | <B> div <C> | <C> ;
<C> ::= <C> pow <D> | <D> ;
<D> ::= open <A> close | number | variable ;
