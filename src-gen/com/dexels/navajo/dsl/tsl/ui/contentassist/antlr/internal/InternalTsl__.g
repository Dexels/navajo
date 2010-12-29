lexer grammar InternalTsl;
@header {
package com.dexels.navajo.dsl.tsl.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;
}

T38 : 'message' ;
T39 : 'comment' ;
T40 : '.' ;
T41 : 'debug' ;
T42 : 'include' ;
T43 : 'property' ;
T44 : 'required' ;
T45 : 'validations' ;
T46 : 'check' ;
T47 : 'break' ;
T48 : 'option' ;
T49 : 'param' ;
T50 : 'methods' ;
T51 : 'method' ;
T52 : 'field' ;
T53 : 'expression' ;
T54 : '=' ;
T55 : ':' ;
T56 : '(' ;
T57 : ')' ;
T58 : ',' ;
T59 : '+' ;
T60 : '-' ;
T61 : '}' ;
T62 : 'OR' ;
T63 : 'AND' ;
T64 : '==' ;
T65 : '!=' ;
T66 : '*' ;
T67 : '!' ;
T68 : '{' ;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14960
RULE_XMLHEAD : '<?' ( options {greedy=false;} : . )*'?>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14962
RULE_XMLCOMMENT : '<!--' ( options {greedy=false;} : . )*'-->';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14964
RULE_QUOTEQ : '"=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14966
RULE_SEMICOLONQUOTE : ';"';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14968
RULE_XML_START_ENDTAG : '</';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14970
RULE_XML_TAG_END : '>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14972
RULE_XML_TAG_SINGLEEND : '/>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14974
RULE_XML_TAG_START : '<';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14976
RULE_EMPTYSTRING : '""';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14978
RULE_ATTRIBUTESTRING : '"' ~(('='|'"'))* '"';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14980
RULE_MAPKEYWORD : 'map';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14982
RULE_NAVASCRIPT_KEYWORD : ('navascript'|'tsl');

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14984
RULE_XML_GT : '&gt;';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14986
RULE_XML_LT : '&lt;';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14988
RULE_XML_GTEQ : '&gt;=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14990
RULE_XML_LTEQ : '&lt;=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14992
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14994
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14996
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 14998
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15000
RULE_TRUE : ('true'|'TRUE');

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15002
RULE_FALSE : ('false'|'FALSE');

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15004
RULE_NULL : ('null'|'NULL');

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15006
RULE_TODAY : ('today'|'TODAY');

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15008
RULE_FORALL : 'FORALL';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15010
RULE_PARENT : '..';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15012
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15014
RULE_AT : '@';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15016
RULE_LITERALSTRING : ('\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\''|'<![CDATA[' ( options {greedy=false;} : . )*']]>');

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15018
RULE_SQBRACKET_OPEN : '[';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15020
RULE_SQBRACKET_CLOSE : ']';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15022
RULE_TML_SEPARATOR : '/';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15024
RULE_TML_EXISTS : '?';

// $ANTLR src "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g" 15026
RULE_DOLLAR : '$';


