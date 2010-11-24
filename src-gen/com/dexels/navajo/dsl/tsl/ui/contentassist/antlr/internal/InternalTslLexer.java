package com.dexels.navajo.dsl.tsl.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTslLexer extends Lexer {
    public static final int RULE_ID=4;
    public static final int RULE_LITERALSTRING=7;
    public static final int T29=29;
    public static final int T28=28;
    public static final int T27=27;
    public static final int T26=26;
    public static final int T25=25;
    public static final int EOF=-1;
    public static final int T24=24;
    public static final int T23=23;
    public static final int T22=22;
    public static final int T21=21;
    public static final int T20=20;
    public static final int RULE_INT=6;
    public static final int T38=38;
    public static final int T37=37;
    public static final int T39=39;
    public static final int T34=34;
    public static final int T33=33;
    public static final int T36=36;
    public static final int T35=35;
    public static final int T30=30;
    public static final int T32=32;
    public static final int T31=31;
    public static final int RULE_ATTRIBUTESTRING=5;
    public static final int T49=49;
    public static final int T48=48;
    public static final int T43=43;
    public static final int Tokens=50;
    public static final int RULE_SL_COMMENT=9;
    public static final int T42=42;
    public static final int T41=41;
    public static final int T40=40;
    public static final int T47=47;
    public static final int T46=46;
    public static final int T45=45;
    public static final int RULE_ML_COMMENT=8;
    public static final int T44=44;
    public static final int T11=11;
    public static final int T12=12;
    public static final int T13=13;
    public static final int T14=14;
    public static final int T15=15;
    public static final int RULE_WS=10;
    public static final int T16=16;
    public static final int T17=17;
    public static final int T18=18;
    public static final int T19=19;
    public InternalTslLexer() {;} 
    public InternalTslLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g"; }

    // $ANTLR start T11
    public final void mT11() throws RecognitionException {
        try {
            int _type = T11;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:10:5: ( '/>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:10:7: '/>'
            {
            match("/>"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T11

    // $ANTLR start T12
    public final void mT12() throws RecognitionException {
        try {
            int _type = T12;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:11:5: ( '.' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:11:7: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T12

    // $ANTLR start T13
    public final void mT13() throws RecognitionException {
        try {
            int _type = T13;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:12:5: ( '..' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:12:7: '..'
            {
            match(".."); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T13

    // $ANTLR start T14
    public final void mT14() throws RecognitionException {
        try {
            int _type = T14;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:13:5: ( '<navascript' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:13:7: '<navascript'
            {
            match("<navascript"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T14

    // $ANTLR start T15
    public final void mT15() throws RecognitionException {
        try {
            int _type = T15;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:14:5: ( '>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:14:7: '>'
            {
            match('>'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T15

    // $ANTLR start T16
    public final void mT16() throws RecognitionException {
        try {
            int _type = T16;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:15:5: ( '</navascript>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:15:7: '</navascript>'
            {
            match("</navascript>"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T16

    // $ANTLR start T17
    public final void mT17() throws RecognitionException {
        try {
            int _type = T17;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:16:5: ( '=' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:16:7: '='
            {
            match('='); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T17

    // $ANTLR start T18
    public final void mT18() throws RecognitionException {
        try {
            int _type = T18;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:17:5: ( '\"=' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:17:7: '\"='
            {
            match("\"="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T18

    // $ANTLR start T19
    public final void mT19() throws RecognitionException {
        try {
            int _type = T19;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:18:5: ( ';\"' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:18:7: ';\"'
            {
            match(";\""); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T19

    // $ANTLR start T20
    public final void mT20() throws RecognitionException {
        try {
            int _type = T20;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:19:5: ( '<message' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:19:7: '<message'
            {
            match("<message"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T20

    // $ANTLR start T21
    public final void mT21() throws RecognitionException {
        try {
            int _type = T21;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:20:5: ( '</message>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:20:7: '</message>'
            {
            match("</message>"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T21

    // $ANTLR start T22
    public final void mT22() throws RecognitionException {
        try {
            int _type = T22;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:21:5: ( '<map.' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:21:7: '<map.'
            {
            match("<map."); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T22

    // $ANTLR start T23
    public final void mT23() throws RecognitionException {
        try {
            int _type = T23;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:22:5: ( '</map.' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:22:7: '</map.'
            {
            match("</map."); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T23

    // $ANTLR start T24
    public final void mT24() throws RecognitionException {
        try {
            int _type = T24;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:23:5: ( '<property' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:23:7: '<property'
            {
            match("<property"); 


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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:24:5: ( '</property>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:24:7: '</property>'
            {
            match("</property>"); 


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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:25:5: ( '<expression>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:25:7: '<expression>'
            {
            match("<expression>"); 


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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:26:5: ( '</expression>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:26:7: '</expression>'
            {
            match("</expression>"); 


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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:27:5: ( '[' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:27:7: '['
            {
            match('['); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:28:5: ( '/' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:28:7: '/'
            {
            match('/'); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:29:5: ( ']' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:29:7: ']'
            {
            match(']'); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:30:5: ( '?' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:30:7: '?'
            {
            match('?'); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:31:5: ( '+' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:31:7: '+'
            {
            match('+'); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:32:5: ( '-' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:32:7: '-'
            {
            match('-'); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:33:5: ( '(' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:33:7: '('
            {
            match('('); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:34:5: ( ')' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:34:7: ')'
            {
            match(')'); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:35:5: ( ',' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:35:7: ','
            {
            match(','); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:36:5: ( '}' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:36:7: '}'
            {
            match('}'); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:37:5: ( 'OR' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:37:7: 'OR'
            {
            match("OR"); 


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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:38:5: ( 'AND' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:38:7: 'AND'
            {
            match("AND"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T39

    // $ANTLR start T40
    public final void mT40() throws RecognitionException {
        try {
            int _type = T40;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:39:5: ( '==' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:39:7: '=='
            {
            match("=="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T40

    // $ANTLR start T41
    public final void mT41() throws RecognitionException {
        try {
            int _type = T41;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:40:5: ( '!=' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:40:7: '!='
            {
            match("!="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T41

    // $ANTLR start T42
    public final void mT42() throws RecognitionException {
        try {
            int _type = T42;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:41:5: ( '*' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:41:7: '*'
            {
            match('*'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T42

    // $ANTLR start T43
    public final void mT43() throws RecognitionException {
        try {
            int _type = T43;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:42:5: ( '!' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:42:7: '!'
            {
            match('!'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T43

    // $ANTLR start T44
    public final void mT44() throws RecognitionException {
        try {
            int _type = T44;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:43:5: ( 'FORALL' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:43:7: 'FORALL'
            {
            match("FORALL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T44

    // $ANTLR start T45
    public final void mT45() throws RecognitionException {
        try {
            int _type = T45;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:44:5: ( '{' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:44:7: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T45

    // $ANTLR start T46
    public final void mT46() throws RecognitionException {
        try {
            int _type = T46;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:45:5: ( 'NULL' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:45:7: 'NULL'
            {
            match("NULL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T46

    // $ANTLR start T47
    public final void mT47() throws RecognitionException {
        try {
            int _type = T47;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:46:5: ( 'TODAY' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:46:7: 'TODAY'
            {
            match("TODAY"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T47

    // $ANTLR start T48
    public final void mT48() throws RecognitionException {
        try {
            int _type = T48;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:47:5: ( 'TRUE' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:47:7: 'TRUE'
            {
            match("TRUE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T48

    // $ANTLR start T49
    public final void mT49() throws RecognitionException {
        try {
            int _type = T49;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:48:5: ( 'FALSE' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:48:7: 'FALSE'
            {
            match("FALSE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T49

    // $ANTLR start RULE_ATTRIBUTESTRING
    public final void mRULE_ATTRIBUTESTRING() throws RecognitionException {
        try {
            int _type = RULE_ATTRIBUTESTRING;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:22: ( ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\"') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='\"') ) {
                    int LA2_2 = input.LA(3);

                    if ( ((LA2_2>='\u0000' && LA2_2<='\uFFFE')) ) {
                        alt2=1;
                    }
                    else {
                        alt2=2;}
                }
                else if ( ((LA2_1>='\u0000' && LA2_1<='!')||(LA2_1>='#' && LA2_1<='<')||(LA2_1>='>' && LA2_1<='\uFFFE')) ) {
                    int LA2_3 = input.LA(3);

                    if ( (LA2_3=='\"') ) {
                        int LA2_7 = input.LA(4);

                        if ( (LA2_7=='\"') ) {
                            alt2=1;
                        }
                        else {
                            alt2=3;}
                    }
                    else if ( ((LA2_3>='\u0000' && LA2_3<='!')||(LA2_3>='#' && LA2_3<='\uFFFE')) ) {
                        alt2=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("5951:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )", 2, 3, input);

                        throw nvae;
                    }
                }
                else if ( (LA2_1=='=') ) {
                    alt2=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("5951:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5951:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:25: '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"'
                    {
                    match('\"'); 
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='<')||(input.LA(1)>='>' && input.LA(1)<='\uFFFE') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:36: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop1:
                    do {
                        int alt1=3;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0=='\\') ) {
                            int LA1_1 = input.LA(2);

                            if ( (LA1_1=='\"') ) {
                                int LA1_5 = input.LA(3);

                                if ( ((LA1_5>='\u0000' && LA1_5<='\uFFFE')) ) {
                                    alt1=1;
                                }


                            }
                            else if ( (LA1_1=='\''||LA1_1=='\\'||LA1_1=='b'||LA1_1=='f'||LA1_1=='n'||LA1_1=='r'||LA1_1=='t') ) {
                                alt1=1;
                            }


                        }
                        else if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<=':')||(LA1_0>='<' && LA1_0<='[')||(LA1_0>=']' && LA1_0<='\uFFFE')) ) {
                            int LA1_2 = input.LA(2);

                            if ( (LA1_2=='\"') ) {
                                int LA1_7 = input.LA(3);

                                if ( (LA1_7=='\"') ) {
                                    alt1=2;
                                }


                            }
                            else if ( ((LA1_2>='\u0000' && LA1_2<='!')||(LA1_2>='#' && LA1_2<='\uFFFE')) ) {
                                alt1=2;
                            }


                        }
                        else if ( (LA1_0==';') ) {
                            alt1=2;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:37: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
                    	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:78: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
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
                    	    break loop1;
                        }
                    } while (true);

                    if ( (input.LA(1)>='\u0000' && input.LA(1)<=':')||(input.LA(1)>='<' && input.LA(1)<='\uFFFE') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:105: '\"\"'
                    {
                    match("\"\""); 


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5951:110: '\"' ~ ( '\"' ) '\"'
                    {
                    match('\"'); 
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFE') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    match('\"'); 

                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_ATTRIBUTESTRING

    // $ANTLR start RULE_INT
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5953:10: ( ( '0' .. '9' )+ )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5953:12: ( '0' .. '9' )+
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5953:12: ( '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5953:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5955:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5955:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5955:24: ( options {greedy=false; } : . )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='*') ) {
                    int LA4_1 = input.LA(2);

                    if ( (LA4_1=='/') ) {
                        alt4=2;
                    }
                    else if ( ((LA4_1>='\u0000' && LA4_1<='.')||(LA4_1>='0' && LA4_1<='\uFFFE')) ) {
                        alt4=1;
                    }


                }
                else if ( ((LA4_0>='\u0000' && LA4_0<=')')||(LA4_0>='+' && LA4_0<='\uFFFE')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5955:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop4;
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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5957:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5957:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5957:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='\u0000' && LA5_0<='\t')||(LA5_0>='\u000B' && LA5_0<='\f')||(LA5_0>='\u000E' && LA5_0<='\uFFFE')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5957:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop5;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5957:40: ( ( '\\r' )? '\\n' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\n'||LA7_0=='\r') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5957:41: ( '\\r' )? '\\n'
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5957:41: ( '\\r' )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='\r') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5957:41: '\\r'
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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5959:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5959:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5959:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\t' && LA8_0<='\n')||LA8_0=='\r'||LA8_0==' ') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:
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
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_WS

    // $ANTLR start RULE_ID
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5961:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5961:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5961:11: ( '^' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='^') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5961:11: '^'
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

            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5961:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:
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

    // $ANTLR start RULE_LITERALSTRING
    public final void mRULE_LITERALSTRING() throws RecognitionException {
        try {
            int _type = RULE_LITERALSTRING;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5963:20: ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5963:22: '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
            {
            match('\''); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5963:27: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )*
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
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5963:28: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5963:69: ~ ( ( '\\\\' | '\\'' ) )
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

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_LITERALSTRING

    public void mTokens() throws RecognitionException {
        // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:8: ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | RULE_ATTRIBUTESTRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_LITERALSTRING )
        int alt12=46;
        switch ( input.LA(1) ) {
        case '/':
            {
            switch ( input.LA(2) ) {
            case '>':
                {
                alt12=1;
                }
                break;
            case '*':
                {
                alt12=42;
                }
                break;
            case '/':
                {
                alt12=43;
                }
                break;
            default:
                alt12=19;}

            }
            break;
        case '.':
            {
            int LA12_2 = input.LA(2);

            if ( (LA12_2=='.') ) {
                alt12=3;
            }
            else {
                alt12=2;}
            }
            break;
        case '<':
            {
            switch ( input.LA(2) ) {
            case 'n':
                {
                alt12=4;
                }
                break;
            case 'm':
                {
                int LA12_36 = input.LA(3);

                if ( (LA12_36=='e') ) {
                    alt12=10;
                }
                else if ( (LA12_36=='a') ) {
                    alt12=12;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | RULE_ATTRIBUTESTRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_LITERALSTRING );", 12, 36, input);

                    throw nvae;
                }
                }
                break;
            case '/':
                {
                switch ( input.LA(3) ) {
                case 'n':
                    {
                    alt12=6;
                    }
                    break;
                case 'm':
                    {
                    int LA12_56 = input.LA(4);

                    if ( (LA12_56=='a') ) {
                        alt12=13;
                    }
                    else if ( (LA12_56=='e') ) {
                        alt12=11;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | RULE_ATTRIBUTESTRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_LITERALSTRING );", 12, 56, input);

                        throw nvae;
                    }
                    }
                    break;
                case 'p':
                    {
                    alt12=15;
                    }
                    break;
                case 'e':
                    {
                    alt12=17;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | RULE_ATTRIBUTESTRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_LITERALSTRING );", 12, 37, input);

                    throw nvae;
                }

                }
                break;
            case 'p':
                {
                alt12=14;
                }
                break;
            case 'e':
                {
                alt12=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | RULE_ATTRIBUTESTRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_LITERALSTRING );", 12, 3, input);

                throw nvae;
            }

            }
            break;
        case '>':
            {
            alt12=5;
            }
            break;
        case '=':
            {
            int LA12_5 = input.LA(2);

            if ( (LA12_5=='=') ) {
                alt12=30;
            }
            else {
                alt12=7;}
            }
            break;
        case '\"':
            {
            int LA12_6 = input.LA(2);

            if ( (LA12_6=='=') ) {
                int LA12_42 = input.LA(3);

                if ( (LA12_42=='\"') ) {
                    alt12=40;
                }
                else {
                    alt12=8;}
            }
            else if ( ((LA12_6>='\u0000' && LA12_6<='<')||(LA12_6>='>' && LA12_6<='\uFFFE')) ) {
                alt12=40;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | RULE_ATTRIBUTESTRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_LITERALSTRING );", 12, 6, input);

                throw nvae;
            }
            }
            break;
        case ';':
            {
            alt12=9;
            }
            break;
        case '[':
            {
            alt12=18;
            }
            break;
        case ']':
            {
            alt12=20;
            }
            break;
        case '?':
            {
            alt12=21;
            }
            break;
        case '+':
            {
            alt12=22;
            }
            break;
        case '-':
            {
            alt12=23;
            }
            break;
        case '(':
            {
            alt12=24;
            }
            break;
        case ')':
            {
            alt12=25;
            }
            break;
        case ',':
            {
            alt12=26;
            }
            break;
        case '}':
            {
            alt12=27;
            }
            break;
        case 'O':
            {
            int LA12_17 = input.LA(2);

            if ( (LA12_17=='R') ) {
                int LA12_44 = input.LA(3);

                if ( ((LA12_44>='0' && LA12_44<='9')||(LA12_44>='A' && LA12_44<='Z')||LA12_44=='_'||(LA12_44>='a' && LA12_44<='z')) ) {
                    alt12=45;
                }
                else {
                    alt12=28;}
            }
            else {
                alt12=45;}
            }
            break;
        case 'A':
            {
            int LA12_18 = input.LA(2);

            if ( (LA12_18=='N') ) {
                int LA12_45 = input.LA(3);

                if ( (LA12_45=='D') ) {
                    int LA12_61 = input.LA(4);

                    if ( ((LA12_61>='0' && LA12_61<='9')||(LA12_61>='A' && LA12_61<='Z')||LA12_61=='_'||(LA12_61>='a' && LA12_61<='z')) ) {
                        alt12=45;
                    }
                    else {
                        alt12=29;}
                }
                else {
                    alt12=45;}
            }
            else {
                alt12=45;}
            }
            break;
        case '!':
            {
            int LA12_19 = input.LA(2);

            if ( (LA12_19=='=') ) {
                alt12=31;
            }
            else {
                alt12=33;}
            }
            break;
        case '*':
            {
            alt12=32;
            }
            break;
        case 'F':
            {
            switch ( input.LA(2) ) {
            case 'A':
                {
                int LA12_48 = input.LA(3);

                if ( (LA12_48=='L') ) {
                    int LA12_62 = input.LA(4);

                    if ( (LA12_62=='S') ) {
                        int LA12_70 = input.LA(5);

                        if ( (LA12_70=='E') ) {
                            int LA12_75 = input.LA(6);

                            if ( ((LA12_75>='0' && LA12_75<='9')||(LA12_75>='A' && LA12_75<='Z')||LA12_75=='_'||(LA12_75>='a' && LA12_75<='z')) ) {
                                alt12=45;
                            }
                            else {
                                alt12=39;}
                        }
                        else {
                            alt12=45;}
                    }
                    else {
                        alt12=45;}
                }
                else {
                    alt12=45;}
                }
                break;
            case 'O':
                {
                int LA12_49 = input.LA(3);

                if ( (LA12_49=='R') ) {
                    int LA12_63 = input.LA(4);

                    if ( (LA12_63=='A') ) {
                        int LA12_71 = input.LA(5);

                        if ( (LA12_71=='L') ) {
                            int LA12_76 = input.LA(6);

                            if ( (LA12_76=='L') ) {
                                int LA12_81 = input.LA(7);

                                if ( ((LA12_81>='0' && LA12_81<='9')||(LA12_81>='A' && LA12_81<='Z')||LA12_81=='_'||(LA12_81>='a' && LA12_81<='z')) ) {
                                    alt12=45;
                                }
                                else {
                                    alt12=34;}
                            }
                            else {
                                alt12=45;}
                        }
                        else {
                            alt12=45;}
                    }
                    else {
                        alt12=45;}
                }
                else {
                    alt12=45;}
                }
                break;
            default:
                alt12=45;}

            }
            break;
        case '{':
            {
            alt12=35;
            }
            break;
        case 'N':
            {
            int LA12_23 = input.LA(2);

            if ( (LA12_23=='U') ) {
                int LA12_50 = input.LA(3);

                if ( (LA12_50=='L') ) {
                    int LA12_64 = input.LA(4);

                    if ( (LA12_64=='L') ) {
                        int LA12_72 = input.LA(5);

                        if ( ((LA12_72>='0' && LA12_72<='9')||(LA12_72>='A' && LA12_72<='Z')||LA12_72=='_'||(LA12_72>='a' && LA12_72<='z')) ) {
                            alt12=45;
                        }
                        else {
                            alt12=36;}
                    }
                    else {
                        alt12=45;}
                }
                else {
                    alt12=45;}
            }
            else {
                alt12=45;}
            }
            break;
        case 'T':
            {
            switch ( input.LA(2) ) {
            case 'R':
                {
                int LA12_51 = input.LA(3);

                if ( (LA12_51=='U') ) {
                    int LA12_65 = input.LA(4);

                    if ( (LA12_65=='E') ) {
                        int LA12_73 = input.LA(5);

                        if ( ((LA12_73>='0' && LA12_73<='9')||(LA12_73>='A' && LA12_73<='Z')||LA12_73=='_'||(LA12_73>='a' && LA12_73<='z')) ) {
                            alt12=45;
                        }
                        else {
                            alt12=38;}
                    }
                    else {
                        alt12=45;}
                }
                else {
                    alt12=45;}
                }
                break;
            case 'O':
                {
                int LA12_52 = input.LA(3);

                if ( (LA12_52=='D') ) {
                    int LA12_66 = input.LA(4);

                    if ( (LA12_66=='A') ) {
                        int LA12_74 = input.LA(5);

                        if ( (LA12_74=='Y') ) {
                            int LA12_79 = input.LA(6);

                            if ( ((LA12_79>='0' && LA12_79<='9')||(LA12_79>='A' && LA12_79<='Z')||LA12_79=='_'||(LA12_79>='a' && LA12_79<='z')) ) {
                                alt12=45;
                            }
                            else {
                                alt12=37;}
                        }
                        else {
                            alt12=45;}
                    }
                    else {
                        alt12=45;}
                }
                else {
                    alt12=45;}
                }
                break;
            default:
                alt12=45;}

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
            alt12=41;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt12=44;
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
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt12=45;
            }
            break;
        case '\'':
            {
            alt12=46;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | T49 | RULE_ATTRIBUTESTRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_LITERALSTRING );", 12, 0, input);

            throw nvae;
        }

        switch (alt12) {
            case 1 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:10: T11
                {
                mT11(); 

                }
                break;
            case 2 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:14: T12
                {
                mT12(); 

                }
                break;
            case 3 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:18: T13
                {
                mT13(); 

                }
                break;
            case 4 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:22: T14
                {
                mT14(); 

                }
                break;
            case 5 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:26: T15
                {
                mT15(); 

                }
                break;
            case 6 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:30: T16
                {
                mT16(); 

                }
                break;
            case 7 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:34: T17
                {
                mT17(); 

                }
                break;
            case 8 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:38: T18
                {
                mT18(); 

                }
                break;
            case 9 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:42: T19
                {
                mT19(); 

                }
                break;
            case 10 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:46: T20
                {
                mT20(); 

                }
                break;
            case 11 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:50: T21
                {
                mT21(); 

                }
                break;
            case 12 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:54: T22
                {
                mT22(); 

                }
                break;
            case 13 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:58: T23
                {
                mT23(); 

                }
                break;
            case 14 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:62: T24
                {
                mT24(); 

                }
                break;
            case 15 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:66: T25
                {
                mT25(); 

                }
                break;
            case 16 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:70: T26
                {
                mT26(); 

                }
                break;
            case 17 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:74: T27
                {
                mT27(); 

                }
                break;
            case 18 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:78: T28
                {
                mT28(); 

                }
                break;
            case 19 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:82: T29
                {
                mT29(); 

                }
                break;
            case 20 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:86: T30
                {
                mT30(); 

                }
                break;
            case 21 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:90: T31
                {
                mT31(); 

                }
                break;
            case 22 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:94: T32
                {
                mT32(); 

                }
                break;
            case 23 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:98: T33
                {
                mT33(); 

                }
                break;
            case 24 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:102: T34
                {
                mT34(); 

                }
                break;
            case 25 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:106: T35
                {
                mT35(); 

                }
                break;
            case 26 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:110: T36
                {
                mT36(); 

                }
                break;
            case 27 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:114: T37
                {
                mT37(); 

                }
                break;
            case 28 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:118: T38
                {
                mT38(); 

                }
                break;
            case 29 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:122: T39
                {
                mT39(); 

                }
                break;
            case 30 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:126: T40
                {
                mT40(); 

                }
                break;
            case 31 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:130: T41
                {
                mT41(); 

                }
                break;
            case 32 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:134: T42
                {
                mT42(); 

                }
                break;
            case 33 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:138: T43
                {
                mT43(); 

                }
                break;
            case 34 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:142: T44
                {
                mT44(); 

                }
                break;
            case 35 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:146: T45
                {
                mT45(); 

                }
                break;
            case 36 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:150: T46
                {
                mT46(); 

                }
                break;
            case 37 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:154: T47
                {
                mT47(); 

                }
                break;
            case 38 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:158: T48
                {
                mT48(); 

                }
                break;
            case 39 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:162: T49
                {
                mT49(); 

                }
                break;
            case 40 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:166: RULE_ATTRIBUTESTRING
                {
                mRULE_ATTRIBUTESTRING(); 

                }
                break;
            case 41 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:187: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 42 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:196: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 43 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:212: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 44 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:228: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 45 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:236: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 46 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:244: RULE_LITERALSTRING
                {
                mRULE_LITERALSTRING(); 

                }
                break;

        }

    }


 

}