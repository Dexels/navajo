package com.dexels.navajo.dsl.expression.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalNavajoExpressionLexer extends Lexer {
    public static final int RULE_ID=4;
    public static final int RULE_PARENT=5;
    public static final int RULE_SQBRACKET_OPEN=6;
    public static final int RULE_XML_LT=12;
    public static final int T29=29;
    public static final int RULE_LITERALSTRING=17;
    public static final int T28=28;
    public static final int RULE_XML_GTEQ=15;
    public static final int T27=27;
    public static final int T26=26;
    public static final int RULE_TML_SEPARATOR=7;
    public static final int Tokens=40;
    public static final int EOF=-1;
    public static final int RULE_SL_COMMENT=24;
    public static final int RULE_NULL=19;
    public static final int RULE_ML_COMMENT=23;
    public static final int RULE_TRUE=21;
    public static final int RULE_DOLLAR=11;
    public static final int RULE_FORALL=18;
    public static final int RULE_FALSE=22;
    public static final int RULE_TML_EXISTS=10;
    public static final int RULE_SQBRACKET_CLOSE=9;
    public static final int RULE_TODAY=20;
    public static final int RULE_XML_LTEQ=14;
    public static final int RULE_INT=16;
    public static final int T38=38;
    public static final int T37=37;
    public static final int T39=39;
    public static final int T34=34;
    public static final int RULE_WS=25;
    public static final int T33=33;
    public static final int T36=36;
    public static final int RULE_XML_GT=13;
    public static final int T35=35;
    public static final int T30=30;
    public static final int T32=32;
    public static final int RULE_AT=8;
    public static final int T31=31;
    public InternalNavajoExpressionLexer() {;} 
    public InternalNavajoExpressionLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g"; }

    // $ANTLR start T26
    public final void mT26() throws RecognitionException {
        try {
            int _type = T26;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:10:5: ( '.' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:10:7: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T26

    // $ANTLR start T27
    public final void mT27() throws RecognitionException {
        try {
            int _type = T27;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:11:5: ( '(' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:11:7: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T27

    // $ANTLR start T28
    public final void mT28() throws RecognitionException {
        try {
            int _type = T28;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:12:5: ( ',' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:12:7: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T28

    // $ANTLR start T29
    public final void mT29() throws RecognitionException {
        try {
            int _type = T29;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:13:5: ( ')' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:13:7: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T29

    // $ANTLR start T30
    public final void mT30() throws RecognitionException {
        try {
            int _type = T30;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:14:5: ( 'OR' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:14:7: 'OR'
            {
            match("OR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T30

    // $ANTLR start T31
    public final void mT31() throws RecognitionException {
        try {
            int _type = T31;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:15:5: ( 'AND' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:15:7: 'AND'
            {
            match("AND"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T31

    // $ANTLR start T32
    public final void mT32() throws RecognitionException {
        try {
            int _type = T32;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:16:5: ( '==' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:16:7: '=='
            {
            match("=="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T32

    // $ANTLR start T33
    public final void mT33() throws RecognitionException {
        try {
            int _type = T33;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:17:5: ( '!=' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:17:7: '!='
            {
            match("!="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T33

    // $ANTLR start T34
    public final void mT34() throws RecognitionException {
        try {
            int _type = T34;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:18:5: ( '+' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:18:7: '+'
            {
            match('+'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T34

    // $ANTLR start T35
    public final void mT35() throws RecognitionException {
        try {
            int _type = T35;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:19:5: ( '-' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:19:7: '-'
            {
            match('-'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T35

    // $ANTLR start T36
    public final void mT36() throws RecognitionException {
        try {
            int _type = T36;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:20:5: ( '*' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:20:7: '*'
            {
            match('*'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T36

    // $ANTLR start T37
    public final void mT37() throws RecognitionException {
        try {
            int _type = T37;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:21:5: ( '!' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:21:7: '!'
            {
            match('!'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T37

    // $ANTLR start T38
    public final void mT38() throws RecognitionException {
        try {
            int _type = T38;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:22:5: ( '{' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:22:7: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T38

    // $ANTLR start T39
    public final void mT39() throws RecognitionException {
        try {
            int _type = T39;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:23:5: ( '}' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:23:7: '}'
            {
            match('}'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T39

    // $ANTLR start RULE_XML_GT
    public final void mRULE_XML_GT() throws RecognitionException {
        try {
            int _type = RULE_XML_GT;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2181:13: ( '&gt;' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2181:15: '&gt;'
            {
            match("&gt;"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_XML_GT

    // $ANTLR start RULE_XML_LT
    public final void mRULE_XML_LT() throws RecognitionException {
        try {
            int _type = RULE_XML_LT;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2183:13: ( '&lt;' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2183:15: '&lt;'
            {
            match("&lt;"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_XML_LT

    // $ANTLR start RULE_XML_GTEQ
    public final void mRULE_XML_GTEQ() throws RecognitionException {
        try {
            int _type = RULE_XML_GTEQ;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2185:15: ( '&gt;=' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2185:17: '&gt;='
            {
            match("&gt;="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_XML_GTEQ

    // $ANTLR start RULE_XML_LTEQ
    public final void mRULE_XML_LTEQ() throws RecognitionException {
        try {
            int _type = RULE_XML_LTEQ;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2187:15: ( '&lt;=' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2187:17: '&lt;='
            {
            match("&lt;="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_XML_LTEQ

    // $ANTLR start RULE_INT
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2189:10: ( ( '0' .. '9' )+ )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2189:12: ( '0' .. '9' )+
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2189:12: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2189:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_INT

    // $ANTLR start RULE_ML_COMMENT
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2191:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2191:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2191:24: ( options {greedy=false; } : . )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='*') ) {
                    int LA2_1 = input.LA(2);

                    if ( (LA2_1=='/') ) {
                        alt2=2;
                    }
                    else if ( ((LA2_1>='\u0000' && LA2_1<='.')||(LA2_1>='0' && LA2_1<='\uFFFE')) ) {
                        alt2=1;
                    }


                }
                else if ( ((LA2_0>='\u0000' && LA2_0<=')')||(LA2_0>='+' && LA2_0<='\uFFFE')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2191:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match("*/"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_ML_COMMENT

    // $ANTLR start RULE_SL_COMMENT
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2193:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2193:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2193:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFE')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2193:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2193:40: ( ( '\\r' )? '\\n' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\n'||LA5_0=='\r') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2193:41: ( '\\r' )? '\\n'
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2193:41: ( '\\r' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='\r') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2193:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_SL_COMMENT

    // $ANTLR start RULE_WS
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2195:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2195:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2195:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='\t' && LA6_0<='\n')||LA6_0=='\r'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_WS

    // $ANTLR start RULE_TRUE
    public final void mRULE_TRUE() throws RecognitionException {
        try {
            int _type = RULE_TRUE;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2197:11: ( ( 'true' | 'TRUE' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2197:13: ( 'true' | 'TRUE' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2197:13: ( 'true' | 'TRUE' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='t') ) {
                alt7=1;
            }
            else if ( (LA7_0=='T') ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2197:13: ( 'true' | 'TRUE' )", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2197:14: 'true'
                    {
                    match("true"); 


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2197:21: 'TRUE'
                    {
                    match("TRUE"); 


                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_TRUE

    // $ANTLR start RULE_FALSE
    public final void mRULE_FALSE() throws RecognitionException {
        try {
            int _type = RULE_FALSE;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2199:12: ( ( 'false' | 'FALSE' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2199:14: ( 'false' | 'FALSE' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2199:14: ( 'false' | 'FALSE' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='f') ) {
                alt8=1;
            }
            else if ( (LA8_0=='F') ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2199:14: ( 'false' | 'FALSE' )", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2199:15: 'false'
                    {
                    match("false"); 


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2199:23: 'FALSE'
                    {
                    match("FALSE"); 


                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_FALSE

    // $ANTLR start RULE_NULL
    public final void mRULE_NULL() throws RecognitionException {
        try {
            int _type = RULE_NULL;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2201:11: ( ( 'null' | 'NULL' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2201:13: ( 'null' | 'NULL' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2201:13: ( 'null' | 'NULL' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='n') ) {
                alt9=1;
            }
            else if ( (LA9_0=='N') ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2201:13: ( 'null' | 'NULL' )", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2201:14: 'null'
                    {
                    match("null"); 


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2201:21: 'NULL'
                    {
                    match("NULL"); 


                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_NULL

    // $ANTLR start RULE_TODAY
    public final void mRULE_TODAY() throws RecognitionException {
        try {
            int _type = RULE_TODAY;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2203:12: ( ( 'today' | 'TODAY' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2203:14: ( 'today' | 'TODAY' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2203:14: ( 'today' | 'TODAY' )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='t') ) {
                alt10=1;
            }
            else if ( (LA10_0=='T') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2203:14: ( 'today' | 'TODAY' )", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2203:15: 'today'
                    {
                    match("today"); 


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2203:23: 'TODAY'
                    {
                    match("TODAY"); 


                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_TODAY

    // $ANTLR start RULE_FORALL
    public final void mRULE_FORALL() throws RecognitionException {
        try {
            int _type = RULE_FORALL;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2205:13: ( 'FORALL' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2205:15: 'FORALL'
            {
            match("FORALL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_FORALL

    // $ANTLR start RULE_PARENT
    public final void mRULE_PARENT() throws RecognitionException {
        try {
            int _type = RULE_PARENT;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2207:13: ( '..' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2207:15: '..'
            {
            match(".."); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_PARENT

    // $ANTLR start RULE_ID
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2209:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2209:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2209:11: ( '^' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='^') ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2209:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2209:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='0' && LA12_0<='9')||(LA12_0>='A' && LA12_0<='Z')||LA12_0=='_'||(LA12_0>='a' && LA12_0<='z')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_ID

    // $ANTLR start RULE_AT
    public final void mRULE_AT() throws RecognitionException {
        try {
            int _type = RULE_AT;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2211:9: ( '@' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2211:11: '@'
            {
            match('@'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_AT

    // $ANTLR start RULE_LITERALSTRING
    public final void mRULE_LITERALSTRING() throws RecognitionException {
        try {
            int _type = RULE_LITERALSTRING;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:20: ( ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' | '<![CDATA[' ( options {greedy=false; } : . )* ']]>' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:22: ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' | '<![CDATA[' ( options {greedy=false; } : . )* ']]>' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:22: ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' | '<![CDATA[' ( options {greedy=false; } : . )* ']]>' )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\'') ) {
                alt15=1;
            }
            else if ( (LA15_0=='<') ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2213:22: ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' | '<![CDATA[' ( options {greedy=false; } : . )* ']]>' )", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:23: '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:28: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop13:
                    do {
                        int alt13=3;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0=='\\') ) {
                            alt13=1;
                        }
                        else if ( ((LA13_0>='\u0000' && LA13_0<='&')||(LA13_0>='(' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFE')) ) {
                            alt13=2;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:29: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
                    	    {
                    	    match('\\'); 
                    	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recover(mse);    throw mse;
                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:70: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse =
                    	            new MismatchedSetException(null,input);
                    	        recover(mse);    throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:92: '<![CDATA[' ( options {greedy=false; } : . )* ']]>'
                    {
                    match("<![CDATA["); 

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:104: ( options {greedy=false; } : . )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==']') ) {
                            int LA14_1 = input.LA(2);

                            if ( (LA14_1==']') ) {
                                int LA14_3 = input.LA(3);

                                if ( (LA14_3=='>') ) {
                                    alt14=2;
                                }
                                else if ( ((LA14_3>='\u0000' && LA14_3<='=')||(LA14_3>='?' && LA14_3<='\uFFFE')) ) {
                                    alt14=1;
                                }


                            }
                            else if ( ((LA14_1>='\u0000' && LA14_1<='\\')||(LA14_1>='^' && LA14_1<='\uFFFE')) ) {
                                alt14=1;
                            }


                        }
                        else if ( ((LA14_0>='\u0000' && LA14_0<='\\')||(LA14_0>='^' && LA14_0<='\uFFFE')) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2213:132: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);

                    match("]]>"); 


                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_LITERALSTRING

    // $ANTLR start RULE_SQBRACKET_OPEN
    public final void mRULE_SQBRACKET_OPEN() throws RecognitionException {
        try {
            int _type = RULE_SQBRACKET_OPEN;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2215:21: ( '[' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2215:23: '['
            {
            match('['); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_SQBRACKET_OPEN

    // $ANTLR start RULE_SQBRACKET_CLOSE
    public final void mRULE_SQBRACKET_CLOSE() throws RecognitionException {
        try {
            int _type = RULE_SQBRACKET_CLOSE;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2217:22: ( ']' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2217:24: ']'
            {
            match(']'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_SQBRACKET_CLOSE

    // $ANTLR start RULE_TML_SEPARATOR
    public final void mRULE_TML_SEPARATOR() throws RecognitionException {
        try {
            int _type = RULE_TML_SEPARATOR;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2219:20: ( '/' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2219:22: '/'
            {
            match('/'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_TML_SEPARATOR

    // $ANTLR start RULE_TML_EXISTS
    public final void mRULE_TML_EXISTS() throws RecognitionException {
        try {
            int _type = RULE_TML_EXISTS;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2221:17: ( '?' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2221:19: '?'
            {
            match('?'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_TML_EXISTS

    // $ANTLR start RULE_DOLLAR
    public final void mRULE_DOLLAR() throws RecognitionException {
        try {
            int _type = RULE_DOLLAR;
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2223:13: ( '$' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:2223:15: '$'
            {
            match('$'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_DOLLAR

    public void mTokens() throws RecognitionException {
        // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:8: ( T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR )
        int alt16=36;
        switch ( input.LA(1) ) {
        case '.':
            {
            int LA16_1 = input.LA(2);

            if ( (LA16_1=='.') ) {
                alt16=28;
            }
            else {
                alt16=1;}
            }
            break;
        case '(':
            {
            alt16=2;
            }
            break;
        case ',':
            {
            alt16=3;
            }
            break;
        case ')':
            {
            alt16=4;
            }
            break;
        case 'O':
            {
            int LA16_5 = input.LA(2);

            if ( (LA16_5=='R') ) {
                int LA16_33 = input.LA(3);

                if ( ((LA16_33>='0' && LA16_33<='9')||(LA16_33>='A' && LA16_33<='Z')||LA16_33=='_'||(LA16_33>='a' && LA16_33<='z')) ) {
                    alt16=29;
                }
                else {
                    alt16=5;}
            }
            else {
                alt16=29;}
            }
            break;
        case 'A':
            {
            int LA16_6 = input.LA(2);

            if ( (LA16_6=='N') ) {
                int LA16_34 = input.LA(3);

                if ( (LA16_34=='D') ) {
                    int LA16_52 = input.LA(4);

                    if ( ((LA16_52>='0' && LA16_52<='9')||(LA16_52>='A' && LA16_52<='Z')||LA16_52=='_'||(LA16_52>='a' && LA16_52<='z')) ) {
                        alt16=29;
                    }
                    else {
                        alt16=6;}
                }
                else {
                    alt16=29;}
            }
            else {
                alt16=29;}
            }
            break;
        case '=':
            {
            alt16=7;
            }
            break;
        case '!':
            {
            int LA16_8 = input.LA(2);

            if ( (LA16_8=='=') ) {
                alt16=8;
            }
            else {
                alt16=12;}
            }
            break;
        case '+':
            {
            alt16=9;
            }
            break;
        case '-':
            {
            alt16=10;
            }
            break;
        case '*':
            {
            alt16=11;
            }
            break;
        case '{':
            {
            alt16=13;
            }
            break;
        case '}':
            {
            alt16=14;
            }
            break;
        case '&':
            {
            int LA16_14 = input.LA(2);

            if ( (LA16_14=='l') ) {
                int LA16_37 = input.LA(3);

                if ( (LA16_37=='t') ) {
                    int LA16_53 = input.LA(4);

                    if ( (LA16_53==';') ) {
                        int LA16_65 = input.LA(5);

                        if ( (LA16_65=='=') ) {
                            alt16=18;
                        }
                        else {
                            alt16=16;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("1:1: Tokens : ( T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 16, 53, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("1:1: Tokens : ( T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 16, 37, input);

                    throw nvae;
                }
            }
            else if ( (LA16_14=='g') ) {
                int LA16_38 = input.LA(3);

                if ( (LA16_38=='t') ) {
                    int LA16_54 = input.LA(4);

                    if ( (LA16_54==';') ) {
                        int LA16_66 = input.LA(5);

                        if ( (LA16_66=='=') ) {
                            alt16=17;
                        }
                        else {
                            alt16=15;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("1:1: Tokens : ( T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 16, 54, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("1:1: Tokens : ( T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 16, 38, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 16, 14, input);

                throw nvae;
            }
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt16=19;
            }
            break;
        case '/':
            {
            switch ( input.LA(2) ) {
            case '*':
                {
                alt16=20;
                }
                break;
            case '/':
                {
                alt16=21;
                }
                break;
            default:
                alt16=34;}

            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt16=22;
            }
            break;
        case 't':
            {
            switch ( input.LA(2) ) {
            case 'r':
                {
                int LA16_42 = input.LA(3);

                if ( (LA16_42=='u') ) {
                    int LA16_55 = input.LA(4);

                    if ( (LA16_55=='e') ) {
                        int LA16_67 = input.LA(5);

                        if ( ((LA16_67>='0' && LA16_67<='9')||(LA16_67>='A' && LA16_67<='Z')||LA16_67=='_'||(LA16_67>='a' && LA16_67<='z')) ) {
                            alt16=29;
                        }
                        else {
                            alt16=23;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
                }
                break;
            case 'o':
                {
                int LA16_43 = input.LA(3);

                if ( (LA16_43=='d') ) {
                    int LA16_56 = input.LA(4);

                    if ( (LA16_56=='a') ) {
                        int LA16_68 = input.LA(5);

                        if ( (LA16_68=='y') ) {
                            int LA16_81 = input.LA(6);

                            if ( ((LA16_81>='0' && LA16_81<='9')||(LA16_81>='A' && LA16_81<='Z')||LA16_81=='_'||(LA16_81>='a' && LA16_81<='z')) ) {
                                alt16=29;
                            }
                            else {
                                alt16=26;}
                        }
                        else {
                            alt16=29;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
                }
                break;
            default:
                alt16=29;}

            }
            break;
        case 'T':
            {
            switch ( input.LA(2) ) {
            case 'R':
                {
                int LA16_44 = input.LA(3);

                if ( (LA16_44=='U') ) {
                    int LA16_57 = input.LA(4);

                    if ( (LA16_57=='E') ) {
                        int LA16_69 = input.LA(5);

                        if ( ((LA16_69>='0' && LA16_69<='9')||(LA16_69>='A' && LA16_69<='Z')||LA16_69=='_'||(LA16_69>='a' && LA16_69<='z')) ) {
                            alt16=29;
                        }
                        else {
                            alt16=23;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
                }
                break;
            case 'O':
                {
                int LA16_45 = input.LA(3);

                if ( (LA16_45=='D') ) {
                    int LA16_58 = input.LA(4);

                    if ( (LA16_58=='A') ) {
                        int LA16_70 = input.LA(5);

                        if ( (LA16_70=='Y') ) {
                            int LA16_82 = input.LA(6);

                            if ( ((LA16_82>='0' && LA16_82<='9')||(LA16_82>='A' && LA16_82<='Z')||LA16_82=='_'||(LA16_82>='a' && LA16_82<='z')) ) {
                                alt16=29;
                            }
                            else {
                                alt16=26;}
                        }
                        else {
                            alt16=29;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
                }
                break;
            default:
                alt16=29;}

            }
            break;
        case 'f':
            {
            int LA16_20 = input.LA(2);

            if ( (LA16_20=='a') ) {
                int LA16_46 = input.LA(3);

                if ( (LA16_46=='l') ) {
                    int LA16_59 = input.LA(4);

                    if ( (LA16_59=='s') ) {
                        int LA16_71 = input.LA(5);

                        if ( (LA16_71=='e') ) {
                            int LA16_83 = input.LA(6);

                            if ( ((LA16_83>='0' && LA16_83<='9')||(LA16_83>='A' && LA16_83<='Z')||LA16_83=='_'||(LA16_83>='a' && LA16_83<='z')) ) {
                                alt16=29;
                            }
                            else {
                                alt16=24;}
                        }
                        else {
                            alt16=29;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
            }
            else {
                alt16=29;}
            }
            break;
        case 'F':
            {
            switch ( input.LA(2) ) {
            case 'A':
                {
                int LA16_47 = input.LA(3);

                if ( (LA16_47=='L') ) {
                    int LA16_60 = input.LA(4);

                    if ( (LA16_60=='S') ) {
                        int LA16_72 = input.LA(5);

                        if ( (LA16_72=='E') ) {
                            int LA16_84 = input.LA(6);

                            if ( ((LA16_84>='0' && LA16_84<='9')||(LA16_84>='A' && LA16_84<='Z')||LA16_84=='_'||(LA16_84>='a' && LA16_84<='z')) ) {
                                alt16=29;
                            }
                            else {
                                alt16=24;}
                        }
                        else {
                            alt16=29;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
                }
                break;
            case 'O':
                {
                int LA16_48 = input.LA(3);

                if ( (LA16_48=='R') ) {
                    int LA16_61 = input.LA(4);

                    if ( (LA16_61=='A') ) {
                        int LA16_73 = input.LA(5);

                        if ( (LA16_73=='L') ) {
                            int LA16_85 = input.LA(6);

                            if ( (LA16_85=='L') ) {
                                int LA16_89 = input.LA(7);

                                if ( ((LA16_89>='0' && LA16_89<='9')||(LA16_89>='A' && LA16_89<='Z')||LA16_89=='_'||(LA16_89>='a' && LA16_89<='z')) ) {
                                    alt16=29;
                                }
                                else {
                                    alt16=27;}
                            }
                            else {
                                alt16=29;}
                        }
                        else {
                            alt16=29;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
                }
                break;
            default:
                alt16=29;}

            }
            break;
        case 'n':
            {
            int LA16_22 = input.LA(2);

            if ( (LA16_22=='u') ) {
                int LA16_49 = input.LA(3);

                if ( (LA16_49=='l') ) {
                    int LA16_62 = input.LA(4);

                    if ( (LA16_62=='l') ) {
                        int LA16_74 = input.LA(5);

                        if ( ((LA16_74>='0' && LA16_74<='9')||(LA16_74>='A' && LA16_74<='Z')||LA16_74=='_'||(LA16_74>='a' && LA16_74<='z')) ) {
                            alt16=29;
                        }
                        else {
                            alt16=25;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
            }
            else {
                alt16=29;}
            }
            break;
        case 'N':
            {
            int LA16_23 = input.LA(2);

            if ( (LA16_23=='U') ) {
                int LA16_50 = input.LA(3);

                if ( (LA16_50=='L') ) {
                    int LA16_63 = input.LA(4);

                    if ( (LA16_63=='L') ) {
                        int LA16_75 = input.LA(5);

                        if ( ((LA16_75>='0' && LA16_75<='9')||(LA16_75>='A' && LA16_75<='Z')||LA16_75=='_'||(LA16_75>='a' && LA16_75<='z')) ) {
                            alt16=29;
                        }
                        else {
                            alt16=25;}
                    }
                    else {
                        alt16=29;}
                }
                else {
                    alt16=29;}
            }
            else {
                alt16=29;}
            }
            break;
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '^':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt16=29;
            }
            break;
        case '@':
            {
            alt16=30;
            }
            break;
        case '\'':
        case '<':
            {
            alt16=31;
            }
            break;
        case '[':
            {
            alt16=32;
            }
            break;
        case ']':
            {
            alt16=33;
            }
            break;
        case '?':
            {
            alt16=35;
            }
            break;
        case '$':
            {
            alt16=36;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 16, 0, input);

            throw nvae;
        }

        switch (alt16) {
            case 1 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:10: T26
                {
                mT26(); 

                }
                break;
            case 2 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:14: T27
                {
                mT27(); 

                }
                break;
            case 3 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:18: T28
                {
                mT28(); 

                }
                break;
            case 4 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:22: T29
                {
                mT29(); 

                }
                break;
            case 5 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:26: T30
                {
                mT30(); 

                }
                break;
            case 6 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:30: T31
                {
                mT31(); 

                }
                break;
            case 7 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:34: T32
                {
                mT32(); 

                }
                break;
            case 8 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:38: T33
                {
                mT33(); 

                }
                break;
            case 9 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:42: T34
                {
                mT34(); 

                }
                break;
            case 10 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:46: T35
                {
                mT35(); 

                }
                break;
            case 11 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:50: T36
                {
                mT36(); 

                }
                break;
            case 12 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:54: T37
                {
                mT37(); 

                }
                break;
            case 13 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:58: T38
                {
                mT38(); 

                }
                break;
            case 14 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:62: T39
                {
                mT39(); 

                }
                break;
            case 15 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:66: RULE_XML_GT
                {
                mRULE_XML_GT(); 

                }
                break;
            case 16 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:78: RULE_XML_LT
                {
                mRULE_XML_LT(); 

                }
                break;
            case 17 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:90: RULE_XML_GTEQ
                {
                mRULE_XML_GTEQ(); 

                }
                break;
            case 18 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:104: RULE_XML_LTEQ
                {
                mRULE_XML_LTEQ(); 

                }
                break;
            case 19 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:118: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 20 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:127: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 21 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:143: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 22 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:159: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 23 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:167: RULE_TRUE
                {
                mRULE_TRUE(); 

                }
                break;
            case 24 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:177: RULE_FALSE
                {
                mRULE_FALSE(); 

                }
                break;
            case 25 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:188: RULE_NULL
                {
                mRULE_NULL(); 

                }
                break;
            case 26 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:198: RULE_TODAY
                {
                mRULE_TODAY(); 

                }
                break;
            case 27 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:209: RULE_FORALL
                {
                mRULE_FORALL(); 

                }
                break;
            case 28 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:221: RULE_PARENT
                {
                mRULE_PARENT(); 

                }
                break;
            case 29 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:233: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 30 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:241: RULE_AT
                {
                mRULE_AT(); 

                }
                break;
            case 31 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:249: RULE_LITERALSTRING
                {
                mRULE_LITERALSTRING(); 

                }
                break;
            case 32 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:268: RULE_SQBRACKET_OPEN
                {
                mRULE_SQBRACKET_OPEN(); 

                }
                break;
            case 33 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:288: RULE_SQBRACKET_CLOSE
                {
                mRULE_SQBRACKET_CLOSE(); 

                }
                break;
            case 34 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:309: RULE_TML_SEPARATOR
                {
                mRULE_TML_SEPARATOR(); 

                }
                break;
            case 35 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:328: RULE_TML_EXISTS
                {
                mRULE_TML_EXISTS(); 

                }
                break;
            case 36 :
                // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1:344: RULE_DOLLAR
                {
                mRULE_DOLLAR(); 

                }
                break;

        }

    }


 

}