lexer grammar InternalTsl;
@header {
package com.dexels.navajo.dsl.tsl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

T38 : 'debug' ;
T39 : 'include' ;
T40 : 'property' ;
T41 : 'required' ;
T42 : 'option' ;
T43 : 'param' ;
T44 : 'message' ;
T45 : 'methods' ;
T46 : 'method' ;
T47 : 'field' ;
T48 : 'expression' ;
T49 : ':' ;
T50 : '=' ;
T51 : '.' ;
T52 : '(' ;
T53 : ',' ;
T54 : ')' ;
T55 : 'OR' ;
T56 : 'AND' ;
T57 : '==' ;
T58 : '!=' ;
T59 : '+' ;
T60 : '-' ;
T61 : '*' ;
T62 : '!' ;
T63 : '{' ;
T64 : '}' ;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5567
RULE_XMLHEAD : '<?' ( options {greedy=false;} : . )*'?>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5569
RULE_XMLCOMMENT : '<!--' ( options {greedy=false;} : . )*'-->';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5571
RULE_QUOTEQ : '"=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5573
RULE_SEMICOLONQUOTE : ';"';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5575
RULE_XML_START_ENDTAG : '</';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5577
RULE_XML_TAG_END : '>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5579
RULE_XML_TAG_SINGLEEND : '/>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5581
RULE_XML_TAG_START : '<';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5583
RULE_EMPTYSTRING : '""';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5585
RULE_ATTRIBUTESTRING : '"' ~(('='|'"'))* '"';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5587
RULE_MAPKEYWORD : 'map';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5589
RULE_NAVASCRIPT_KEYWORD : ('navascript'|'tsl');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5591
RULE_XML_GT : '&gt;';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5593
RULE_XML_LT : '&lt;';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5595
RULE_XML_GTEQ : '&gt;=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5597
RULE_XML_LTEQ : '&lt;=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5599
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5601
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5603
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5605
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5607
RULE_TRUE : ('true'|'TRUE');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5609
RULE_FALSE : ('false'|'FALSE');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5611
RULE_NULL : ('null'|'NULL');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5613
RULE_TODAY : ('today'|'TODAY');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5615
RULE_FORALL : 'FORALL';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5617
RULE_PARENT : '..';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5619
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5621
RULE_AT : '@';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5623
RULE_LITERALSTRING : ('\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\''|'<![CDATA[' ( options {greedy=false;} : . )*']]>');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5625
RULE_SQBRACKET_OPEN : '[';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5627
RULE_SQBRACKET_CLOSE : ']';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5629
RULE_TML_SEPARATOR : '/';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5631
RULE_TML_EXISTS : '?';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 5633
RULE_DOLLAR : '$';


