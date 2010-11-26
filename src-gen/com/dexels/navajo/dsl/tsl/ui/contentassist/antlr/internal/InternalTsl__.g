lexer grammar InternalTsl;
@header {
package com.dexels.navajo.dsl.tsl.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;
}

T13 : '/>' ;
T14 : '.' ;
T15 : '..' ;
T16 : '<navascript' ;
T17 : '>' ;
T18 : '</navascript>' ;
T19 : '=' ;
T20 : ';"' ;
T21 : '<message' ;
T22 : '</message>' ;
T23 : '</map.' ;
T24 : '<map.' ;
T25 : '<property' ;
T26 : '</property>' ;
T27 : '<expression>' ;
T28 : '</expression>' ;
T29 : '[' ;
T30 : ']' ;
T31 : '/' ;
T32 : '?' ;
T33 : '+' ;
T34 : '-' ;
T35 : '(' ;
T36 : ')' ;
T37 : ',' ;
T38 : '}' ;
T39 : '$' ;
T40 : 'OR' ;
T41 : 'AND' ;
T42 : '==' ;
T43 : '!=' ;
T44 : '*' ;
T45 : '!' ;
T46 : 'FORALL' ;
T47 : '{' ;
T48 : 'NULL' ;
T49 : 'TODAY' ;
T50 : 'TRUE' ;
T51 : 'FALSE' ;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5818
RULE_QUOTEQ : '"=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5820
RULE_EMPTYSTRING : '""';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5822
RULE_ATTRIBUTESTRING : '"' ~(('='|'"'))* '"';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5824
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5826
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5828
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5830
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5832
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 5834
RULE_LITERALSTRING : '\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'';


