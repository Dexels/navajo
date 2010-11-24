lexer grammar InternalNavajoExpression;
@header {
package com.dexels.navajo.dsl.expression.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

T10 : '.' ;
T11 : '..' ;
T12 : '[' ;
T13 : '/' ;
T14 : ']' ;
T15 : '?' ;
T16 : 'OR' ;
T17 : 'AND' ;
T18 : '==' ;
T19 : '!=' ;
T20 : '+' ;
T21 : '-' ;
T22 : '*' ;
T23 : '!' ;
T24 : '(' ;
T25 : ')' ;
T26 : 'FORALL' ;
T27 : ',' ;
T28 : '{' ;
T29 : '}' ;
T30 : 'NULL' ;
T31 : 'TODAY' ;
T32 : 'TRUE' ;
T33 : 'FALSE' ;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 1681
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 1683
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 1685
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 1687
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 1689
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 1691
RULE_LITERALSTRING : '\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'';


