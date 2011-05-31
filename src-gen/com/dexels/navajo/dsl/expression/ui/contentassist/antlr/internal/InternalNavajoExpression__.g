lexer grammar InternalNavajoExpression;
@header {
package com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;
}

T24 : '.' ;
T25 : '(' ;
T26 : ')' ;
T27 : ',' ;
T28 : '+' ;
T29 : '-' ;
T30 : '#' ;
T31 : '}' ;
T32 : 'OR' ;
T33 : 'AND' ;
T34 : '==' ;
T35 : '!=' ;
T36 : '*' ;
T37 : '!' ;
T38 : '{' ;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5112
RULE_XML_GT : '&gt;';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5114
RULE_XML_LT : '&lt;';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5116
RULE_XML_GTEQ : '&gt;=';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5118
RULE_XML_LTEQ : '&lt;=';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5120
RULE_NUMBER : ('0'..'9')+ ('.' ('0'..'9')+)?;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5122
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5124
RULE_TRUE : ('true'|'TRUE');

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5126
RULE_FALSE : ('false'|'FALSE');

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5128
RULE_NULL : ('null'|'NULL');

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5130
RULE_TODAY : ('today'|'TODAY');

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5132
RULE_FORALL : 'FORALL';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5134
RULE_PARENT : '..';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5136
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5138
RULE_AT : '@';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5140
RULE_LITERALSTRING : ('\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\''|'<![CDATA[' ( options {greedy=false;} : . )*']]>');

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5142
RULE_SQBRACKET_OPEN : '[';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5144
RULE_SQBRACKET_CLOSE : ']';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5146
RULE_TML_SEPARATOR : '/';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5148
RULE_TML_EXISTS : '?';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 5150
RULE_DOLLAR : '$';


