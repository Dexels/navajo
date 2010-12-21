lexer grammar InternalNavajoExpression;
@header {
package com.dexels.navajo.dsl.expression.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

T26 : '.' ;
T27 : '(' ;
T28 : ',' ;
T29 : ')' ;
T30 : 'OR' ;
T31 : 'AND' ;
T32 : '==' ;
T33 : '!=' ;
T34 : '+' ;
T35 : '-' ;
T36 : '*' ;
T37 : '!' ;
T38 : '{' ;
T39 : '}' ;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2181
RULE_XML_GT : '&gt;';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2183
RULE_XML_LT : '&lt;';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2185
RULE_XML_GTEQ : '&gt;=';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2187
RULE_XML_LTEQ : '&lt;=';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2189
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2191
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2193
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2195
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2197
RULE_TRUE : ('true'|'TRUE');

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2199
RULE_FALSE : ('false'|'FALSE');

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2201
RULE_NULL : ('null'|'NULL');

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2203
RULE_TODAY : ('today'|'TODAY');

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2205
RULE_FORALL : 'FORALL';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2207
RULE_PARENT : '..';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2209
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2211
RULE_AT : '@';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2213
RULE_LITERALSTRING : ('\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\''|'<![CDATA[' ( options {greedy=false;} : . )*']]>');

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2215
RULE_SQBRACKET_OPEN : '[';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2217
RULE_SQBRACKET_CLOSE : ']';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2219
RULE_TML_SEPARATOR : '/';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2221
RULE_TML_EXISTS : '?';

// $ANTLR src "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g" 2223
RULE_DOLLAR : '$';


