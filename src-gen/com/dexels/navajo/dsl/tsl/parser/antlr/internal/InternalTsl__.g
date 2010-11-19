lexer grammar InternalTsl;
@header {
package com.dexels.navajo.dsl.tsl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

T11 : '<navascript' ;
T12 : '>' ;
T13 : '</navascript>' ;
T14 : '/>' ;
T15 : '=' ;
T16 : '"=' ;
T17 : ';"' ;
T18 : '<message' ;
T19 : '</message>' ;
T20 : '<map.' ;
T21 : '</map.' ;
T22 : '<property' ;
T23 : '</property>' ;
T24 : '<expression>' ;
T25 : '</expression>' ;
T26 : '.' ;
T27 : '..' ;
T28 : '[' ;
T29 : '/' ;
T30 : ']' ;
T31 : '?' ;
T32 : 'OR' ;
T33 : 'AND' ;
T34 : '==' ;
T35 : '!=' ;
T36 : '+' ;
T37 : '-' ;
T38 : '*' ;
T39 : '!' ;
T40 : '(' ;
T41 : ')' ;
T42 : ',' ;
T43 : '{' ;
T44 : '}' ;
T45 : 'NULL' ;
T46 : 'TODAY' ;
T47 : 'TRUE' ;
T48 : 'FALSE' ;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2375
RULE_ATTRIBUTESTRING : ('"' ~('=') ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'"')))* ~(';') '"'|'""'|'"' ~('"') '"');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2377
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2379
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2381
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2383
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2385
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2387
RULE_ANY_OTHER : .;


