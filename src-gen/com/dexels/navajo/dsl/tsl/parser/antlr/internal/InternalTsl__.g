lexer grammar InternalTsl;
@header {
package com.dexels.navajo.dsl.tsl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

T13 : '<navascript' ;
T14 : '>' ;
T15 : '</navascript>' ;
T16 : '/>' ;
T17 : '=' ;
T18 : ';"' ;
T19 : '<message' ;
T20 : '</message>' ;
T21 : '</map.' ;
T22 : '<map.' ;
T23 : '<property' ;
T24 : '</property>' ;
T25 : '<expression>' ;
T26 : '</expression>' ;
T27 : '.' ;
T28 : '..' ;
T29 : '[' ;
T30 : '/' ;
T31 : ']' ;
T32 : '?' ;
T33 : '$' ;
T34 : 'OR' ;
T35 : 'AND' ;
T36 : '==' ;
T37 : '!=' ;
T38 : '+' ;
T39 : '-' ;
T40 : '*' ;
T41 : '!' ;
T42 : '(' ;
T43 : ')' ;
T44 : ',' ;
T45 : 'FORALL' ;
T46 : '{' ;
T47 : '}' ;
T48 : 'NULL' ;
T49 : 'TODAY' ;
T50 : 'TRUE' ;
T51 : 'FALSE' ;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2503
RULE_QUOTEQ : '"=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2505
RULE_EMPTYSTRING : '""';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2507
RULE_ATTRIBUTESTRING : '"' ~(('='|'"'))* '"';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2509
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2511
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2513
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2515
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2517
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 2519
RULE_LITERALSTRING : '\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'';


