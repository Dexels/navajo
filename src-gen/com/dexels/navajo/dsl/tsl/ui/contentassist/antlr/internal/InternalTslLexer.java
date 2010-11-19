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
    public static final int RULE_ANY_OTHER=10;
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
    public static final int T48=48;
    public static final int T43=43;
    public static final int Tokens=49;
    public static final int RULE_SL_COMMENT=8;
    public static final int T42=42;
    public static final int T41=41;
    public static final int T40=40;
    public static final int T47=47;
    public static final int T46=46;
    public static final int T45=45;
    public static final int RULE_ML_COMMENT=7;
    public static final int T44=44;
    public static final int T11=11;
    public static final int T12=12;
    public static final int T13=13;
    public static final int T14=14;
    public static final int T15=15;
    public static final int RULE_WS=9;
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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:43:5: ( '{' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:43:7: '{'
            {
            match('{'); 

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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:44:5: ( 'NULL' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:44:7: 'NULL'
            {
            match("NULL"); 


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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:45:5: ( 'TODAY' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:45:7: 'TODAY'
            {
            match("TODAY"); 


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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:46:5: ( 'TRUE' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:46:7: 'TRUE'
            {
            match("TRUE"); 


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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:47:5: ( 'FALSE' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:47:7: 'FALSE'
            {
            match("FALSE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T48

    // $ANTLR start RULE_ATTRIBUTESTRING
    public final void mRULE_ATTRIBUTESTRING() throws RecognitionException {
        try {
            int _type = RULE_ATTRIBUTESTRING;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:22: ( ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\"') ) {
                int LA2_1 = input.LA(2);

                if ( ((LA2_1>='\u0000' && LA2_1<='!')||(LA2_1>='#' && LA2_1<='<')||(LA2_1>='>' && LA2_1<='\uFFFE')) ) {
                    int LA2_2 = input.LA(3);

                    if ( (LA2_2=='\"') ) {
                        int LA2_5 = input.LA(4);

                        if ( (LA2_5=='\"') ) {
                            alt2=1;
                        }
                        else {
                            alt2=3;}
                    }
                    else if ( ((LA2_2>='\u0000' && LA2_2<='!')||(LA2_2>='#' && LA2_2<='\uFFFE')) ) {
                        alt2=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("5706:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )", 2, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA2_1=='\"') ) {
                    int LA2_3 = input.LA(3);

                    if ( ((LA2_3>='\u0000' && LA2_3<='\uFFFE')) ) {
                        alt2=1;
                    }
                    else {
                        alt2=2;}
                }
                else if ( (LA2_1=='=') ) {
                    alt2=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("5706:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5706:24: ( '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"' | '\"\"' | '\"' ~ ( '\"' ) '\"' )", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:25: '\"' ~ ( '=' ) ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* ~ ( ';' ) '\"'
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

                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:36: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:37: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
                    	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:78: ~ ( ( '\\\\' | '\"' ) )
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
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:105: '\"\"'
                    {
                    match("\"\""); 


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5706:110: '\"' ~ ( '\"' ) '\"'
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

    // $ANTLR start RULE_ID
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5708:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5708:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5708:11: ( '^' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='^') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5708:11: '^'
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

            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5708:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
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
            	    break loop4;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_ID

    // $ANTLR start RULE_INT
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5710:10: ( ( '0' .. '9' )+ )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5710:12: ( '0' .. '9' )+
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5710:12: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5710:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5712:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5712:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5712:24: ( options {greedy=false; } : . )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='*') ) {
                    int LA6_1 = input.LA(2);

                    if ( (LA6_1=='/') ) {
                        alt6=2;
                    }
                    else if ( ((LA6_1>='\u0000' && LA6_1<='.')||(LA6_1>='0' && LA6_1<='\uFFFE')) ) {
                        alt6=1;
                    }


                }
                else if ( ((LA6_0>='\u0000' && LA6_0<=')')||(LA6_0>='+' && LA6_0<='\uFFFE')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5712:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop6;
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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5714:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5714:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5714:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\u0000' && LA7_0<='\t')||(LA7_0>='\u000B' && LA7_0<='\f')||(LA7_0>='\u000E' && LA7_0<='\uFFFE')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5714:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop7;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5714:40: ( ( '\\r' )? '\\n' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\n'||LA9_0=='\r') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5714:41: ( '\\r' )? '\\n'
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5714:41: ( '\\r' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='\r') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5714:41: '\\r'
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
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5716:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5716:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5716:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\t' && LA10_0<='\n')||LA10_0=='\r'||LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
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
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_WS

    // $ANTLR start RULE_ANY_OTHER
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5718:16: ( . )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5718:18: .
            {
            matchAny(); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RULE_ANY_OTHER

    public void mTokens() throws RecognitionException {
        // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:8: ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | RULE_ATTRIBUTESTRING | RULE_ID | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt11=45;
        int LA11_0 = input.LA(1);

        if ( (LA11_0=='/') ) {
            switch ( input.LA(2) ) {
            case '>':
                {
                alt11=1;
                }
                break;
            case '/':
                {
                alt11=43;
                }
                break;
            case '*':
                {
                alt11=42;
                }
                break;
            default:
                alt11=19;}

        }
        else if ( (LA11_0=='.') ) {
            int LA11_2 = input.LA(2);

            if ( (LA11_2=='.') ) {
                alt11=3;
            }
            else {
                alt11=2;}
        }
        else if ( (LA11_0=='<') ) {
            switch ( input.LA(2) ) {
            case '/':
                {
                switch ( input.LA(3) ) {
                case 'm':
                    {
                    int LA11_69 = input.LA(4);

                    if ( (LA11_69=='e') ) {
                        alt11=11;
                    }
                    else if ( (LA11_69=='a') ) {
                        alt11=13;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | RULE_ATTRIBUTESTRING | RULE_ID | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );", 11, 69, input);

                        throw nvae;
                    }
                    }
                    break;
                case 'n':
                    {
                    alt11=6;
                    }
                    break;
                case 'p':
                    {
                    alt11=15;
                    }
                    break;
                case 'e':
                    {
                    alt11=17;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | RULE_ATTRIBUTESTRING | RULE_ID | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );", 11, 36, input);

                    throw nvae;
                }

                }
                break;
            case 'n':
                {
                alt11=4;
                }
                break;
            case 'm':
                {
                int LA11_38 = input.LA(3);

                if ( (LA11_38=='a') ) {
                    alt11=12;
                }
                else if ( (LA11_38=='e') ) {
                    alt11=10;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | RULE_ATTRIBUTESTRING | RULE_ID | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );", 11, 38, input);

                    throw nvae;
                }
                }
                break;
            case 'e':
                {
                alt11=16;
                }
                break;
            case 'p':
                {
                alt11=14;
                }
                break;
            default:
                alt11=45;}

        }
        else if ( (LA11_0=='>') ) {
            alt11=5;
        }
        else if ( (LA11_0=='=') ) {
            int LA11_5 = input.LA(2);

            if ( (LA11_5=='=') ) {
                alt11=30;
            }
            else {
                alt11=7;}
        }
        else if ( (LA11_0=='\"') ) {
            int LA11_6 = input.LA(2);

            if ( ((LA11_6>='\u0000' && LA11_6<='<')||(LA11_6>='>' && LA11_6<='\uFFFE')) ) {
                alt11=39;
            }
            else if ( (LA11_6=='=') ) {
                int LA11_45 = input.LA(3);

                if ( (LA11_45=='\"') ) {
                    alt11=39;
                }
                else {
                    alt11=8;}
            }
            else {
                alt11=45;}
        }
        else if ( (LA11_0==';') ) {
            int LA11_7 = input.LA(2);

            if ( (LA11_7=='\"') ) {
                alt11=9;
            }
            else {
                alt11=45;}
        }
        else if ( (LA11_0=='[') ) {
            alt11=18;
        }
        else if ( (LA11_0==']') ) {
            alt11=20;
        }
        else if ( (LA11_0=='?') ) {
            alt11=21;
        }
        else if ( (LA11_0=='+') ) {
            alt11=22;
        }
        else if ( (LA11_0=='-') ) {
            alt11=23;
        }
        else if ( (LA11_0=='(') ) {
            alt11=24;
        }
        else if ( (LA11_0==')') ) {
            alt11=25;
        }
        else if ( (LA11_0==',') ) {
            alt11=26;
        }
        else if ( (LA11_0=='}') ) {
            alt11=27;
        }
        else if ( (LA11_0=='O') ) {
            int LA11_17 = input.LA(2);

            if ( (LA11_17=='R') ) {
                int LA11_56 = input.LA(3);

                if ( ((LA11_56>='0' && LA11_56<='9')||(LA11_56>='A' && LA11_56<='Z')||LA11_56=='_'||(LA11_56>='a' && LA11_56<='z')) ) {
                    alt11=40;
                }
                else {
                    alt11=28;}
            }
            else {
                alt11=40;}
        }
        else if ( (LA11_0=='A') ) {
            int LA11_18 = input.LA(2);

            if ( (LA11_18=='N') ) {
                int LA11_58 = input.LA(3);

                if ( (LA11_58=='D') ) {
                    int LA11_77 = input.LA(4);

                    if ( ((LA11_77>='0' && LA11_77<='9')||(LA11_77>='A' && LA11_77<='Z')||LA11_77=='_'||(LA11_77>='a' && LA11_77<='z')) ) {
                        alt11=40;
                    }
                    else {
                        alt11=29;}
                }
                else {
                    alt11=40;}
            }
            else {
                alt11=40;}
        }
        else if ( (LA11_0=='!') ) {
            int LA11_19 = input.LA(2);

            if ( (LA11_19=='=') ) {
                alt11=31;
            }
            else {
                alt11=33;}
        }
        else if ( (LA11_0=='*') ) {
            alt11=32;
        }
        else if ( (LA11_0=='{') ) {
            alt11=34;
        }
        else if ( (LA11_0=='N') ) {
            int LA11_22 = input.LA(2);

            if ( (LA11_22=='U') ) {
                int LA11_63 = input.LA(3);

                if ( (LA11_63=='L') ) {
                    int LA11_78 = input.LA(4);

                    if ( (LA11_78=='L') ) {
                        int LA11_85 = input.LA(5);

                        if ( ((LA11_85>='0' && LA11_85<='9')||(LA11_85>='A' && LA11_85<='Z')||LA11_85=='_'||(LA11_85>='a' && LA11_85<='z')) ) {
                            alt11=40;
                        }
                        else {
                            alt11=35;}
                    }
                    else {
                        alt11=40;}
                }
                else {
                    alt11=40;}
            }
            else {
                alt11=40;}
        }
        else if ( (LA11_0=='T') ) {
            switch ( input.LA(2) ) {
            case 'O':
                {
                int LA11_64 = input.LA(3);

                if ( (LA11_64=='D') ) {
                    int LA11_79 = input.LA(4);

                    if ( (LA11_79=='A') ) {
                        int LA11_86 = input.LA(5);

                        if ( (LA11_86=='Y') ) {
                            int LA11_90 = input.LA(6);

                            if ( ((LA11_90>='0' && LA11_90<='9')||(LA11_90>='A' && LA11_90<='Z')||LA11_90=='_'||(LA11_90>='a' && LA11_90<='z')) ) {
                                alt11=40;
                            }
                            else {
                                alt11=36;}
                        }
                        else {
                            alt11=40;}
                    }
                    else {
                        alt11=40;}
                }
                else {
                    alt11=40;}
                }
                break;
            case 'R':
                {
                int LA11_65 = input.LA(3);

                if ( (LA11_65=='U') ) {
                    int LA11_80 = input.LA(4);

                    if ( (LA11_80=='E') ) {
                        int LA11_87 = input.LA(5);

                        if ( ((LA11_87>='0' && LA11_87<='9')||(LA11_87>='A' && LA11_87<='Z')||LA11_87=='_'||(LA11_87>='a' && LA11_87<='z')) ) {
                            alt11=40;
                        }
                        else {
                            alt11=37;}
                    }
                    else {
                        alt11=40;}
                }
                else {
                    alt11=40;}
                }
                break;
            default:
                alt11=40;}

        }
        else if ( (LA11_0=='F') ) {
            int LA11_24 = input.LA(2);

            if ( (LA11_24=='A') ) {
                int LA11_66 = input.LA(3);

                if ( (LA11_66=='L') ) {
                    int LA11_81 = input.LA(4);

                    if ( (LA11_81=='S') ) {
                        int LA11_88 = input.LA(5);

                        if ( (LA11_88=='E') ) {
                            int LA11_92 = input.LA(6);

                            if ( ((LA11_92>='0' && LA11_92<='9')||(LA11_92>='A' && LA11_92<='Z')||LA11_92=='_'||(LA11_92>='a' && LA11_92<='z')) ) {
                                alt11=40;
                            }
                            else {
                                alt11=38;}
                        }
                        else {
                            alt11=40;}
                    }
                    else {
                        alt11=40;}
                }
                else {
                    alt11=40;}
            }
            else {
                alt11=40;}
        }
        else if ( (LA11_0=='^') ) {
            int LA11_25 = input.LA(2);

            if ( ((LA11_25>='A' && LA11_25<='Z')||LA11_25=='_'||(LA11_25>='a' && LA11_25<='z')) ) {
                alt11=40;
            }
            else {
                alt11=45;}
        }
        else if ( ((LA11_0>='B' && LA11_0<='E')||(LA11_0>='G' && LA11_0<='M')||(LA11_0>='P' && LA11_0<='S')||(LA11_0>='U' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')) ) {
            alt11=40;
        }
        else if ( ((LA11_0>='0' && LA11_0<='9')) ) {
            alt11=41;
        }
        else if ( ((LA11_0>='\t' && LA11_0<='\n')||LA11_0=='\r'||LA11_0==' ') ) {
            alt11=44;
        }
        else if ( ((LA11_0>='\u0000' && LA11_0<='\b')||(LA11_0>='\u000B' && LA11_0<='\f')||(LA11_0>='\u000E' && LA11_0<='\u001F')||(LA11_0>='#' && LA11_0<='\'')||LA11_0==':'||LA11_0=='@'||LA11_0=='\\'||LA11_0=='`'||LA11_0=='|'||(LA11_0>='~' && LA11_0<='\uFFFE')) ) {
            alt11=45;
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | T24 | T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | T35 | T36 | T37 | T38 | T39 | T40 | T41 | T42 | T43 | T44 | T45 | T46 | T47 | T48 | RULE_ATTRIBUTESTRING | RULE_ID | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );", 11, 0, input);

            throw nvae;
        }
        switch (alt11) {
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
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:162: RULE_ATTRIBUTESTRING
                {
                mRULE_ATTRIBUTESTRING(); 

                }
                break;
            case 40 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:183: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 41 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:191: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 42 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:200: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 43 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:216: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 44 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:232: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 45 :
                // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1:240: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


 

}