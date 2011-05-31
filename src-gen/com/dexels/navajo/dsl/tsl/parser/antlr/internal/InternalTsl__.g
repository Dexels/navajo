lexer grammar InternalTsl;
@header {
package com.dexels.navajo.dsl.tsl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

T70 : ':' ;
T71 : '=' ;
T72 : '(' ;
T73 : ',' ;
T74 : ')' ;
T75 : 'OR' ;
T76 : 'AND' ;
T77 : '==' ;
T78 : '!=' ;
T79 : '+' ;
T80 : '-' ;
T81 : '*' ;
T82 : '!' ;
T83 : '#' ;
T84 : '{' ;
T85 : '}' ;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6047
RULE_XMLHEAD : '<?' ( options {greedy=false;} : . )*'?>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6049
RULE_XMLCOMMENT : '<!--' ( options {greedy=false;} : . )*'-->';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6051
RULE_QUOTEQ : '"=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6053
RULE_SEMICOLONQUOTE : ';"';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6055
RULE_DOT : '.';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6057
RULE_DEBUG_START_TAG : '<debug';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6059
RULE_DEBUG_END_TAG : '</debug' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6061
RULE_EMPTYSTRING : '""';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6063
RULE_ATTRIBUTESTRING : '"' ~(('='|'"'))* '"';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6065
RULE_XML_START_ENDTAG : '</';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6067
RULE_XML_TAG_END : '>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6069
RULE_XML_TAG_SINGLEEND : '/>';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6071
RULE_MAP_METHOD_STARTTAG_START : '<_';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6073
RULE_MAP_METHOD_ENDTAG_START : '</_';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6075
RULE_MAPENDKEYWORD : '</map';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6077
RULE_MAPSTARTKEYWORD : '<map';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6079
RULE_INCLUDE_START_TAG : '<include';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6081
RULE_PROPERTY_START_TAG : '<property';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6083
RULE_REQUIRED_START_TAG : '<required';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6085
RULE_VALIDATIONS_START_TAG : '<validations';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6087
RULE_CHECK_START_TAG : '<check';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6089
RULE_COMMENT_START_TAG : '<comment';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6091
RULE_BREAK_START_TAG : '<break';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6093
RULE_OPTION_START_TAG : '<option';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6095
RULE_BREAK_END_TAG : '</break' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6097
RULE_OPTION_END_TAG : '</option' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6099
RULE_REQUIRED_END_TAG : '</required' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6101
RULE_INCLUDE_END_TAG : '</include' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6103
RULE_PROPERTY_END_TAG : '</property' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6105
RULE_COMMENT_END_TAG : '</comment' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6107
RULE_VALIDATIONS_END_TAG : '</validations' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6109
RULE_CHECK_END_TAG : '</check' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6111
RULE_PARAM_END_TAG : '</param' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6113
RULE_MESSAGE_END_TAG : '</message' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6115
RULE_METHODS_END_TAG : '</methods' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6117
RULE_METHOD_END_TAG : '</method' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6119
RULE_FIELD_END_TAG : '</field' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6121
RULE_EXPRESSION_START_TAG : '<expression';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6123
RULE_EXPRESSION_END_TAG : '</expression' RULE_XML_TAG_END;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6125
RULE_PARAM_START_TAG : '<param';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6127
RULE_MESSAGE_START_TAG : '<message';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6129
RULE_METHOD_START_TAG : '<method';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6131
RULE_METHODS_START_TAG : '<methods';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6133
RULE_FIELD_START_TAG : '<field';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6135
RULE_NAVASCRIPT_START : ('<navascript'|'<tsl');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6137
RULE_NAVASCRIPT_END : ('</navascript' RULE_XML_TAG_END|'</tsl' RULE_XML_TAG_END);

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6139
RULE_XML_GT : '&gt;';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6141
RULE_XML_LT : '&lt;';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6143
RULE_XML_GTEQ : '&gt;=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6145
RULE_XML_LTEQ : '&lt;=';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6147
RULE_NUMBER : ('0'..'9')+ ('.' ('0'..'9')+)?;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6149
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6151
RULE_TRUE : ('true'|'TRUE');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6153
RULE_FALSE : ('false'|'FALSE');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6155
RULE_NULL : ('null'|'NULL');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6157
RULE_TODAY : ('today'|'TODAY');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6159
RULE_FORALL : 'FORALL';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6161
RULE_PARENT : '..';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6163
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6165
RULE_AT : '@';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6167
RULE_LITERALSTRING : ('\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\''|'<![CDATA[' ( options {greedy=false;} : . )*']]>');

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6169
RULE_SQBRACKET_OPEN : '[';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6171
RULE_SQBRACKET_CLOSE : ']';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6173
RULE_TML_SEPARATOR : '/';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6175
RULE_TML_EXISTS : '?';

// $ANTLR src "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g" 6177
RULE_DOLLAR : '$';


