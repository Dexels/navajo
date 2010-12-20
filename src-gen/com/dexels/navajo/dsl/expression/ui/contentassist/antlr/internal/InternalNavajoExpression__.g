lexer grammar InternalNavajoExpression;
@header {
package com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;
}

T20 : '.' ;
T21 : '..' ;
T22 : '+' ;
T23 : '-' ;
T24 : '(' ;
T25 : ')' ;
T26 : ',' ;
T27 : '}' ;
T28 : 'OR' ;
T29 : 'AND' ;
T30 : '==' ;
T31 : '!=' ;
T32 : '*' ;
T33 : '!' ;
T34 : 'FORALL' ;
T35 : '{' ;
T36 : 'NULL' ;
T37 : 'TODAY' ;
T38 : 'TRUE' ;
T39 : 'FALSE' ;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4413
RULE_XML_GT : '&gt;';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4415
RULE_XML_LT : '&lt;';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4417
RULE_XML_GTEQ : '&gt;=';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4419
RULE_XML_LTEQ : '&lt;=';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4421
RULE_INT : ('0'..'9')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4423
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4425
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4427
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4429
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4431
RULE_AT : '@';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4433
RULE_LITERALSTRING : '\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4435
RULE_SQBRACKET_OPEN : '[';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4437
RULE_SQBRACKET_CLOSE : ']';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4439
RULE_TML_SEPARATOR : '/';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4441
RULE_TML_EXISTS : '?';

// $ANTLR src "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g" 4443
RULE_DOLLAR : '$';


