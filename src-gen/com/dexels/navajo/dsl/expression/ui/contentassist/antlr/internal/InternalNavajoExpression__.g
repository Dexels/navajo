lexer grammar InternalNavajoExpression;
@header {
package com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;
}

T11 : '.' ;
T12 : '..' ;
T13 : '[' ;
T14 : '/' ;
T15 : ']' ;
T16 : '?' ;
T17 : '+' ;
T18 : '-' ;
T19 : '(' ;
T20 : ')' ;
T21 : ',' ;
T22 : '}' ;
T23 : 'OR' ;
T24 : 'AND' ;
T25 : '==' ;
T26 : '!=' ;
T27 : '*' ;
T28 : '!' ;
T29 : '{' ;
T30 : 'NULL' ;
T31 : 'TODAY' ;
T32 : 'TRUE' ;
T33 : 'FALSE' ;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 3817
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 3819
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 3821
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 3823
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 3825
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 3827
RULE_ATTRIBUTESTRING : '"' ~('=') ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'"')))* '"';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 3829
RULE_ANY_OTHER : .;


