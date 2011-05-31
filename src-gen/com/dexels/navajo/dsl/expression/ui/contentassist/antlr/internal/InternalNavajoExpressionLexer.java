package com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalNavajoExpressionLexer extends Lexer {
    public static final int RULE_ID=4;
    public static final int RULE_PARENT=5;
    public static final int RULE_SQBRACKET_OPEN=6;
    public static final int RULE_XML_LT=13;
    public static final int T29=29;
    public static final int RULE_LITERALSTRING=17;
    public static final int T28=28;
    public static final int RULE_XML_GTEQ=16;
    public static final int T27=27;
    public static final int T26=26;
    public static final int RULE_TML_SEPARATOR=8;
    public static final int T25=25;
    public static final int Tokens=39;
    public static final int T24=24;
    public static final int EOF=-1;
    public static final int RULE_NULL=19;
    public static final int RULE_TRUE=21;
    public static final int RULE_DOLLAR=12;
    public static final int RULE_FORALL=18;
    public static final int RULE_FALSE=22;
    public static final int RULE_TML_EXISTS=9;
    public static final int RULE_NUMBER=10;
    public static final int RULE_TODAY=20;
    public static final int RULE_SQBRACKET_CLOSE=7;
    public static final int RULE_XML_LTEQ=15;
    public static final int T38=38;
    public static final int T37=37;
    public static final int T34=34;
    public static final int RULE_WS=23;
    public static final int T33=33;
    public static final int T36=36;
    public static final int RULE_XML_GT=14;
    public static final int T35=35;
    public static final int T30=30;
    public static final int T32=32;
    public static final int RULE_AT=11;
    public static final int T31=31;
    public InternalNavajoExpressionLexer() {;} 
    public InternalNavajoExpressionLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g"; }

    // $ANTLR start T24
    public final void mT24() throws RecognitionException {
        try {
            int _type = T24;
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:10:5: ( '.' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:10:7: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T24

    // $ANTLR start T25
    public final void mT25() throws RecognitionException {
        try {
            int _type = T25;
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:11:5: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:11:7: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T25

    // $ANTLR start T26
    public final void mT26() throws RecognitionException {
        try {
            int _type = T26;
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:12:5: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:12:7: ')'
            {
            match(')'); 

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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:13:5: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:13:7: ','
            {
            match(','); 

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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:14:5: ( '+' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:14:7: '+'
            {
            match('+'); 

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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:15:5: ( '-' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:15:7: '-'
            {
            match('-'); 

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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:16:5: ( '#' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:16:7: '#'
            {
            match('#'); 

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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:17:5: ( '}' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:17:7: '}'
            {
            match('}'); 

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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:18:5: ( 'OR' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:18:7: 'OR'
            {
            match("OR"); 


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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:19:5: ( 'AND' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:19:7: 'AND'
            {
            match("AND"); 


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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:20:5: ( '==' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:20:7: '=='
            {
            match("=="); 


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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:21:5: ( '!=' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:21:7: '!='
            {
            match("!="); 


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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:22:5: ( '*' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:22:7: '*'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:23:5: ( '!' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:23:7: '!'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:24:5: ( '{' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:24:7: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T38

    // $ANTLR start RULE_XML_GT
    public final void mRULE_XML_GT() throws RecognitionException {
        try {
            int _type = RULE_XML_GT;
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5112:13: ( '&gt;' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5112:15: '&gt;'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5114:13: ( '&lt;' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5114:15: '&lt;'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5116:15: ( '&gt;=' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5116:17: '&gt;='
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5118:15: ( '&lt;=' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5118:17: '&lt;='
            {
            match("&lt;="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_XML_LTEQ

    // $ANTLR start RULE_NUMBER
    public final void mRULE_NUMBER() throws RecognitionException {
        try {
            int _type = RULE_NUMBER;
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5120:13: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5120:15: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5120:15: ( '0' .. '9' )+
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
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5120:16: '0' .. '9'
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

            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5120:27: ( '.' ( '0' .. '9' )+ )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='.') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5120:28: '.' ( '0' .. '9' )+
                    {
                    match('.'); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5120:32: ( '0' .. '9' )+
                    int cnt2=0;
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5120:33: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt2 >= 1 ) break loop2;
                                EarlyExitException eee =
                                    new EarlyExitException(2, input);
                                throw eee;
                        }
                        cnt2++;
                    } while (true);


                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_NUMBER

    // $ANTLR start RULE_WS
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5122:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5122:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5122:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='\t' && LA4_0<='\n')||LA4_0=='\r'||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:
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
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5124:11: ( ( 'true' | 'TRUE' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5124:13: ( 'true' | 'TRUE' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5124:13: ( 'true' | 'TRUE' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='t') ) {
                alt5=1;
            }
            else if ( (LA5_0=='T') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5124:13: ( 'true' | 'TRUE' )", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5124:14: 'true'
                    {
                    match("true"); 


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5124:21: 'TRUE'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5126:12: ( ( 'false' | 'FALSE' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5126:14: ( 'false' | 'FALSE' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5126:14: ( 'false' | 'FALSE' )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='f') ) {
                alt6=1;
            }
            else if ( (LA6_0=='F') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5126:14: ( 'false' | 'FALSE' )", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5126:15: 'false'
                    {
                    match("false"); 


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5126:23: 'FALSE'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5128:11: ( ( 'null' | 'NULL' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5128:13: ( 'null' | 'NULL' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5128:13: ( 'null' | 'NULL' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='n') ) {
                alt7=1;
            }
            else if ( (LA7_0=='N') ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5128:13: ( 'null' | 'NULL' )", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5128:14: 'null'
                    {
                    match("null"); 


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5128:21: 'NULL'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5130:12: ( ( 'today' | 'TODAY' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5130:14: ( 'today' | 'TODAY' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5130:14: ( 'today' | 'TODAY' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='t') ) {
                alt8=1;
            }
            else if ( (LA8_0=='T') ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5130:14: ( 'today' | 'TODAY' )", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5130:15: 'today'
                    {
                    match("today"); 


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5130:23: 'TODAY'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5132:13: ( 'FORALL' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5132:15: 'FORALL'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5134:13: ( '..' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5134:15: '..'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5136:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5136:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5136:11: ( '^' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='^') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5136:11: '^'
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

            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5136:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:
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
            	    break loop10;
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5138:9: ( '@' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5138:11: '@'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:20: ( ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' | '<![CDATA[' ( options {greedy=false; } : . )* ']]>' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:22: ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' | '<![CDATA[' ( options {greedy=false; } : . )* ']]>' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:22: ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' | '<![CDATA[' ( options {greedy=false; } : . )* ']]>' )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='\'') ) {
                alt13=1;
            }
            else if ( (LA13_0=='<') ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5140:22: ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' | '<![CDATA[' ( options {greedy=false; } : . )* ']]>' )", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:23: '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:28: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop11:
                    do {
                        int alt11=3;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0=='\\') ) {
                            alt11=1;
                        }
                        else if ( ((LA11_0>='\u0000' && LA11_0<='&')||(LA11_0>='(' && LA11_0<='[')||(LA11_0>=']' && LA11_0<='\uFFFE')) ) {
                            alt11=2;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:29: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
                    	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:70: ~ ( ( '\\\\' | '\\'' ) )
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
                    	    break loop11;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:92: '<![CDATA[' ( options {greedy=false; } : . )* ']]>'
                    {
                    match("<![CDATA["); 

                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:104: ( options {greedy=false; } : . )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==']') ) {
                            int LA12_1 = input.LA(2);

                            if ( (LA12_1==']') ) {
                                int LA12_3 = input.LA(3);

                                if ( ((LA12_3>='\u0000' && LA12_3<='=')||(LA12_3>='?' && LA12_3<='\uFFFE')) ) {
                                    alt12=1;
                                }
                                else if ( (LA12_3=='>') ) {
                                    alt12=2;
                                }


                            }
                            else if ( ((LA12_1>='\u0000' && LA12_1<='\\')||(LA12_1>='^' && LA12_1<='\uFFFE')) ) {
                                alt12=1;
                            }


                        }
                        else if ( ((LA12_0>='\u0000' && LA12_0<='\\')||(LA12_0>='^' && LA12_0<='\uFFFE')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5140:132: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5142:21: ( '[' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5142:23: '['
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5144:22: ( ']' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5144:24: ']'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5146:20: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5146:22: '/'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5148:17: ( '?' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5148:19: '?'
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
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5150:13: ( '$' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5150:15: '$'
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
        // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:8: ( T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_NUMBER | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR )
        int alt14=35;
        switch ( input.LA(1) ) {
        case '.':
            {
            int LA14_1 = input.LA(2);

            if ( (LA14_1=='.') ) {
                alt14=27;
            }
            else {
                alt14=1;}
            }
            break;
        case '(':
            {
            alt14=2;
            }
            break;
        case ')':
            {
            alt14=3;
            }
            break;
        case ',':
            {
            alt14=4;
            }
            break;
        case '+':
            {
            alt14=5;
            }
            break;
        case '-':
            {
            alt14=6;
            }
            break;
        case '#':
            {
            alt14=7;
            }
            break;
        case '}':
            {
            alt14=8;
            }
            break;
        case 'O':
            {
            int LA14_9 = input.LA(2);

            if ( (LA14_9=='R') ) {
                int LA14_34 = input.LA(3);

                if ( ((LA14_34>='0' && LA14_34<='9')||(LA14_34>='A' && LA14_34<='Z')||LA14_34=='_'||(LA14_34>='a' && LA14_34<='z')) ) {
                    alt14=28;
                }
                else {
                    alt14=9;}
            }
            else {
                alt14=28;}
            }
            break;
        case 'A':
            {
            int LA14_10 = input.LA(2);

            if ( (LA14_10=='N') ) {
                int LA14_35 = input.LA(3);

                if ( (LA14_35=='D') ) {
                    int LA14_50 = input.LA(4);

                    if ( ((LA14_50>='0' && LA14_50<='9')||(LA14_50>='A' && LA14_50<='Z')||LA14_50=='_'||(LA14_50>='a' && LA14_50<='z')) ) {
                        alt14=28;
                    }
                    else {
                        alt14=10;}
                }
                else {
                    alt14=28;}
            }
            else {
                alt14=28;}
            }
            break;
        case '=':
            {
            alt14=11;
            }
            break;
        case '!':
            {
            int LA14_12 = input.LA(2);

            if ( (LA14_12=='=') ) {
                alt14=12;
            }
            else {
                alt14=14;}
            }
            break;
        case '*':
            {
            alt14=13;
            }
            break;
        case '{':
            {
            alt14=15;
            }
            break;
        case '&':
            {
            int LA14_15 = input.LA(2);

            if ( (LA14_15=='l') ) {
                int LA14_38 = input.LA(3);

                if ( (LA14_38=='t') ) {
                    int LA14_51 = input.LA(4);

                    if ( (LA14_51==';') ) {
                        int LA14_63 = input.LA(5);

                        if ( (LA14_63=='=') ) {
                            alt14=19;
                        }
                        else {
                            alt14=17;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("1:1: Tokens : ( T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_NUMBER | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 14, 51, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("1:1: Tokens : ( T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_NUMBER | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 14, 38, input);

                    throw nvae;
                }
            }
            else if ( (LA14_15=='g') ) {
                int LA14_39 = input.LA(3);

                if ( (LA14_39=='t') ) {
                    int LA14_52 = input.LA(4);

                    if ( (LA14_52==';') ) {
                        int LA14_64 = input.LA(5);

                        if ( (LA14_64=='=') ) {
                            alt14=18;
                        }
                        else {
                            alt14=16;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("1:1: Tokens : ( T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_NUMBER | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 14, 52, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("1:1: Tokens : ( T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_NUMBER | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 14, 39, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_NUMBER | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 14, 15, input);

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
            alt14=20;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt14=21;
            }
            break;
        case 't':
            {
            switch ( input.LA(2) ) {
            case 'r':
                {
                int LA14_40 = input.LA(3);

                if ( (LA14_40=='u') ) {
                    int LA14_53 = input.LA(4);

                    if ( (LA14_53=='e') ) {
                        int LA14_65 = input.LA(5);

                        if ( ((LA14_65>='0' && LA14_65<='9')||(LA14_65>='A' && LA14_65<='Z')||LA14_65=='_'||(LA14_65>='a' && LA14_65<='z')) ) {
                            alt14=28;
                        }
                        else {
                            alt14=22;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
                }
                break;
            case 'o':
                {
                int LA14_41 = input.LA(3);

                if ( (LA14_41=='d') ) {
                    int LA14_54 = input.LA(4);

                    if ( (LA14_54=='a') ) {
                        int LA14_66 = input.LA(5);

                        if ( (LA14_66=='y') ) {
                            int LA14_79 = input.LA(6);

                            if ( ((LA14_79>='0' && LA14_79<='9')||(LA14_79>='A' && LA14_79<='Z')||LA14_79=='_'||(LA14_79>='a' && LA14_79<='z')) ) {
                                alt14=28;
                            }
                            else {
                                alt14=25;}
                        }
                        else {
                            alt14=28;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
                }
                break;
            default:
                alt14=28;}

            }
            break;
        case 'T':
            {
            switch ( input.LA(2) ) {
            case 'R':
                {
                int LA14_42 = input.LA(3);

                if ( (LA14_42=='U') ) {
                    int LA14_55 = input.LA(4);

                    if ( (LA14_55=='E') ) {
                        int LA14_67 = input.LA(5);

                        if ( ((LA14_67>='0' && LA14_67<='9')||(LA14_67>='A' && LA14_67<='Z')||LA14_67=='_'||(LA14_67>='a' && LA14_67<='z')) ) {
                            alt14=28;
                        }
                        else {
                            alt14=22;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
                }
                break;
            case 'O':
                {
                int LA14_43 = input.LA(3);

                if ( (LA14_43=='D') ) {
                    int LA14_56 = input.LA(4);

                    if ( (LA14_56=='A') ) {
                        int LA14_68 = input.LA(5);

                        if ( (LA14_68=='Y') ) {
                            int LA14_80 = input.LA(6);

                            if ( ((LA14_80>='0' && LA14_80<='9')||(LA14_80>='A' && LA14_80<='Z')||LA14_80=='_'||(LA14_80>='a' && LA14_80<='z')) ) {
                                alt14=28;
                            }
                            else {
                                alt14=25;}
                        }
                        else {
                            alt14=28;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
                }
                break;
            default:
                alt14=28;}

            }
            break;
        case 'f':
            {
            int LA14_20 = input.LA(2);

            if ( (LA14_20=='a') ) {
                int LA14_44 = input.LA(3);

                if ( (LA14_44=='l') ) {
                    int LA14_57 = input.LA(4);

                    if ( (LA14_57=='s') ) {
                        int LA14_69 = input.LA(5);

                        if ( (LA14_69=='e') ) {
                            int LA14_81 = input.LA(6);

                            if ( ((LA14_81>='0' && LA14_81<='9')||(LA14_81>='A' && LA14_81<='Z')||LA14_81=='_'||(LA14_81>='a' && LA14_81<='z')) ) {
                                alt14=28;
                            }
                            else {
                                alt14=23;}
                        }
                        else {
                            alt14=28;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
            }
            else {
                alt14=28;}
            }
            break;
        case 'F':
            {
            switch ( input.LA(2) ) {
            case 'A':
                {
                int LA14_45 = input.LA(3);

                if ( (LA14_45=='L') ) {
                    int LA14_58 = input.LA(4);

                    if ( (LA14_58=='S') ) {
                        int LA14_70 = input.LA(5);

                        if ( (LA14_70=='E') ) {
                            int LA14_82 = input.LA(6);

                            if ( ((LA14_82>='0' && LA14_82<='9')||(LA14_82>='A' && LA14_82<='Z')||LA14_82=='_'||(LA14_82>='a' && LA14_82<='z')) ) {
                                alt14=28;
                            }
                            else {
                                alt14=23;}
                        }
                        else {
                            alt14=28;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
                }
                break;
            case 'O':
                {
                int LA14_46 = input.LA(3);

                if ( (LA14_46=='R') ) {
                    int LA14_59 = input.LA(4);

                    if ( (LA14_59=='A') ) {
                        int LA14_71 = input.LA(5);

                        if ( (LA14_71=='L') ) {
                            int LA14_83 = input.LA(6);

                            if ( (LA14_83=='L') ) {
                                int LA14_87 = input.LA(7);

                                if ( ((LA14_87>='0' && LA14_87<='9')||(LA14_87>='A' && LA14_87<='Z')||LA14_87=='_'||(LA14_87>='a' && LA14_87<='z')) ) {
                                    alt14=28;
                                }
                                else {
                                    alt14=26;}
                            }
                            else {
                                alt14=28;}
                        }
                        else {
                            alt14=28;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
                }
                break;
            default:
                alt14=28;}

            }
            break;
        case 'n':
            {
            int LA14_22 = input.LA(2);

            if ( (LA14_22=='u') ) {
                int LA14_47 = input.LA(3);

                if ( (LA14_47=='l') ) {
                    int LA14_60 = input.LA(4);

                    if ( (LA14_60=='l') ) {
                        int LA14_72 = input.LA(5);

                        if ( ((LA14_72>='0' && LA14_72<='9')||(LA14_72>='A' && LA14_72<='Z')||LA14_72=='_'||(LA14_72>='a' && LA14_72<='z')) ) {
                            alt14=28;
                        }
                        else {
                            alt14=24;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
            }
            else {
                alt14=28;}
            }
            break;
        case 'N':
            {
            int LA14_23 = input.LA(2);

            if ( (LA14_23=='U') ) {
                int LA14_48 = input.LA(3);

                if ( (LA14_48=='L') ) {
                    int LA14_61 = input.LA(4);

                    if ( (LA14_61=='L') ) {
                        int LA14_73 = input.LA(5);

                        if ( ((LA14_73>='0' && LA14_73<='9')||(LA14_73>='A' && LA14_73<='Z')||LA14_73=='_'||(LA14_73>='a' && LA14_73<='z')) ) {
                            alt14=28;
                        }
                        else {
                            alt14=24;}
                    }
                    else {
                        alt14=28;}
                }
                else {
                    alt14=28;}
            }
            else {
                alt14=28;}
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
            alt14=28;
            }
            break;
        case '@':
            {
            alt14=29;
            }
            break;
        case '\'':
        case '<':
            {
            alt14=30;
            }
            break;
        case '[':
            {
            alt14=31;
            }
            break;
        case ']':
            {
            alt14=32;
            }
            break;
        case '/':
            {
            alt14=33;
            }
            break;
        case '?':
            {
            alt14=34;
            }
            break;
        case '$':
            {
            alt14=35;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | RULE_XML_GT | RULE_XML_LT | RULE_XML_GTEQ | RULE_XML_LTEQ | RULE_NUMBER | RULE_WS | RULE_TRUE | RULE_FALSE | RULE_NULL | RULE_TODAY | RULE_FORALL | RULE_PARENT | RULE_ID | RULE_AT | RULE_LITERALSTRING | RULE_SQBRACKET_OPEN | RULE_SQBRACKET_CLOSE | RULE_TML_SEPARATOR | RULE_TML_EXISTS | RULE_DOLLAR );", 14, 0, input);

            throw nvae;
        }

        switch (alt14) {
            case 1 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:10: T24
                {
                mT24(); 

                }
                break;
            case 2 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:14: T25
                {
                mT25(); 

                }
                break;
            case 3 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:18: T26
                {
                mT26(); 

                }
                break;
            case 4 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:22: T27
                {
                mT27(); 

                }
                break;
            case 5 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:26: T28
                {
                mT28(); 

                }
                break;
            case 6 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:30: T29
                {
                mT29(); 

                }
                break;
            case 7 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:34: T30
                {
                mT30(); 

                }
                break;
            case 8 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:38: T31
                {
                mT31(); 

                }
                break;
            case 9 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:42: T32
                {
                mT32(); 

                }
                break;
            case 10 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:46: T33
                {
                mT33(); 

                }
                break;
            case 11 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:50: T34
                {
                mT34(); 

                }
                break;
            case 12 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:54: T35
                {
                mT35(); 

                }
                break;
            case 13 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:58: T36
                {
                mT36(); 

                }
                break;
            case 14 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:62: T37
                {
                mT37(); 

                }
                break;
            case 15 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:66: T38
                {
                mT38(); 

                }
                break;
            case 16 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:70: RULE_XML_GT
                {
                mRULE_XML_GT(); 

                }
                break;
            case 17 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:82: RULE_XML_LT
                {
                mRULE_XML_LT(); 

                }
                break;
            case 18 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:94: RULE_XML_GTEQ
                {
                mRULE_XML_GTEQ(); 

                }
                break;
            case 19 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:108: RULE_XML_LTEQ
                {
                mRULE_XML_LTEQ(); 

                }
                break;
            case 20 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:122: RULE_NUMBER
                {
                mRULE_NUMBER(); 

                }
                break;
            case 21 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:134: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 22 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:142: RULE_TRUE
                {
                mRULE_TRUE(); 

                }
                break;
            case 23 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:152: RULE_FALSE
                {
                mRULE_FALSE(); 

                }
                break;
            case 24 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:163: RULE_NULL
                {
                mRULE_NULL(); 

                }
                break;
            case 25 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:173: RULE_TODAY
                {
                mRULE_TODAY(); 

                }
                break;
            case 26 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:184: RULE_FORALL
                {
                mRULE_FORALL(); 

                }
                break;
            case 27 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:196: RULE_PARENT
                {
                mRULE_PARENT(); 

                }
                break;
            case 28 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:208: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 29 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:216: RULE_AT
                {
                mRULE_AT(); 

                }
                break;
            case 30 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:224: RULE_LITERALSTRING
                {
                mRULE_LITERALSTRING(); 

                }
                break;
            case 31 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:243: RULE_SQBRACKET_OPEN
                {
                mRULE_SQBRACKET_OPEN(); 

                }
                break;
            case 32 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:263: RULE_SQBRACKET_CLOSE
                {
                mRULE_SQBRACKET_CLOSE(); 

                }
                break;
            case 33 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:284: RULE_TML_SEPARATOR
                {
                mRULE_TML_SEPARATOR(); 

                }
                break;
            case 34 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:303: RULE_TML_EXISTS
                {
                mRULE_TML_EXISTS(); 

                }
                break;
            case 35 :
                // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1:319: RULE_DOLLAR
                {
                mRULE_DOLLAR(); 

                }
                break;

        }

    }


 

}