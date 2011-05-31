package com.dexels.navajo.dsl.tsl.parser.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.xtext.parsetree.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.xtext.conversion.ValueConverterException;
import com.dexels.navajo.dsl.tsl.services.TslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_XMLHEAD", "RULE_NAVASCRIPT_START", "RULE_XML_TAG_END", "RULE_NAVASCRIPT_END", "RULE_XML_TAG_SINGLEEND", "RULE_ID", "RULE_QUOTEQ", "RULE_SEMICOLONQUOTE", "RULE_ATTRIBUTESTRING", "RULE_EMPTYSTRING", "RULE_METHODS_START_TAG", "RULE_METHODS_END_TAG", "RULE_METHOD_START_TAG", "RULE_METHOD_END_TAG", "RULE_VALIDATIONS_START_TAG", "RULE_VALIDATIONS_END_TAG", "RULE_CHECK_START_TAG", "RULE_CHECK_END_TAG", "RULE_COMMENT_START_TAG", "RULE_COMMENT_END_TAG", "RULE_BREAK_START_TAG", "RULE_BREAK_END_TAG", "RULE_INCLUDE_START_TAG", "RULE_INCLUDE_END_TAG", "RULE_MESSAGE_START_TAG", "RULE_MESSAGE_END_TAG", "RULE_MAPSTARTKEYWORD", "RULE_DOT", "RULE_MAPENDKEYWORD", "RULE_REQUIRED_START_TAG", "RULE_REQUIRED_END_TAG", "RULE_PROPERTY_START_TAG", "RULE_PROPERTY_END_TAG", "RULE_PARAM_START_TAG", "RULE_PARAM_END_TAG", "RULE_MAP_METHOD_STARTTAG_START", "RULE_MAP_METHOD_ENDTAG_START", "RULE_FIELD_START_TAG", "RULE_FIELD_END_TAG", "RULE_DEBUG_START_TAG", "RULE_DEBUG_END_TAG", "RULE_EXPRESSION_START_TAG", "RULE_OPTION_START_TAG", "RULE_EXPRESSION_END_TAG", "RULE_OPTION_END_TAG", "RULE_PARENT", "RULE_SQBRACKET_OPEN", "RULE_TML_SEPARATOR", "RULE_AT", "RULE_SQBRACKET_CLOSE", "RULE_TML_EXISTS", "RULE_DOLLAR", "RULE_XML_LT", "RULE_XML_GT", "RULE_XML_LTEQ", "RULE_XML_GTEQ", "RULE_NUMBER", "RULE_LITERALSTRING", "RULE_FORALL", "RULE_NULL", "RULE_TODAY", "RULE_TRUE", "RULE_FALSE", "RULE_XMLCOMMENT", "RULE_XML_START_ENDTAG", "RULE_WS", "':'", "'='", "'('", "','", "')'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'#'", "'{'", "'}'"
    };
    public static final int RULE_OPTION_END_TAG=48;
    public static final int RULE_NAVASCRIPT_END=7;
    public static final int RULE_CHECK_START_TAG=20;
    public static final int RULE_ID=9;
    public static final int RULE_XMLCOMMENT=67;
    public static final int RULE_COMMENT_END_TAG=23;
    public static final int RULE_PARENT=49;
    public static final int RULE_SQBRACKET_OPEN=50;
    public static final int RULE_COMMENT_START_TAG=22;
    public static final int RULE_QUOTEQ=10;
    public static final int RULE_EXPRESSION_END_TAG=47;
    public static final int RULE_XMLHEAD=4;
    public static final int RULE_REQUIRED_START_TAG=33;
    public static final int RULE_METHODS_END_TAG=15;
    public static final int RULE_LITERALSTRING=61;
    public static final int EOF=-1;
    public static final int RULE_BREAK_END_TAG=25;
    public static final int RULE_FORALL=62;
    public static final int RULE_FALSE=66;
    public static final int RULE_DOT=31;
    public static final int RULE_OPTION_START_TAG=46;
    public static final int RULE_EMPTYSTRING=13;
    public static final int RULE_NUMBER=60;
    public static final int RULE_TODAY=64;
    public static final int RULE_METHOD_START_TAG=16;
    public static final int RULE_XML_LTEQ=58;
    public static final int RULE_FIELD_START_TAG=41;
    public static final int RULE_METHOD_END_TAG=17;
    public static final int RULE_CHECK_END_TAG=21;
    public static final int RULE_INCLUDE_START_TAG=26;
    public static final int RULE_REQUIRED_END_TAG=34;
    public static final int RULE_INCLUDE_END_TAG=27;
    public static final int RULE_MAPENDKEYWORD=32;
    public static final int RULE_DEBUG_START_TAG=43;
    public static final int RULE_FIELD_END_TAG=42;
    public static final int RULE_XML_TAG_SINGLEEND=8;
    public static final int RULE_PROPERTY_START_TAG=35;
    public static final int RULE_ATTRIBUTESTRING=12;
    public static final int RULE_XML_TAG_END=6;
    public static final int RULE_MESSAGE_START_TAG=28;
    public static final int RULE_XML_LT=56;
    public static final int RULE_MAP_METHOD_STARTTAG_START=39;
    public static final int RULE_MESSAGE_END_TAG=29;
    public static final int RULE_XML_GTEQ=59;
    public static final int RULE_TML_SEPARATOR=51;
    public static final int RULE_NULL=63;
    public static final int RULE_TRUE=65;
    public static final int RULE_PROPERTY_END_TAG=36;
    public static final int RULE_DOLLAR=55;
    public static final int RULE_EXPRESSION_START_TAG=45;
    public static final int RULE_TML_EXISTS=54;
    public static final int RULE_VALIDATIONS_START_TAG=18;
    public static final int RULE_BREAK_START_TAG=24;
    public static final int RULE_NAVASCRIPT_START=5;
    public static final int RULE_SQBRACKET_CLOSE=53;
    public static final int RULE_DEBUG_END_TAG=44;
    public static final int RULE_METHODS_START_TAG=14;
    public static final int RULE_MAPSTARTKEYWORD=30;
    public static final int RULE_SEMICOLONQUOTE=11;
    public static final int RULE_XML_START_ENDTAG=68;
    public static final int RULE_VALIDATIONS_END_TAG=19;
    public static final int RULE_WS=69;
    public static final int RULE_XML_GT=57;
    public static final int RULE_MAP_METHOD_ENDTAG_START=40;
    public static final int RULE_PARAM_END_TAG=38;
    public static final int RULE_AT=52;
    public static final int RULE_PARAM_START_TAG=37;

        public InternalTslParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g"; }



     	private TslGrammarAccess grammarAccess;
     	
        public InternalTslParser(TokenStream input, IAstFactory factory, TslGrammarAccess grammarAccess) {
            this(input);
            this.factory = factory;
            registerRules(grammarAccess.getGrammar());
            this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected InputStream getTokenFile() {
        	ClassLoader classLoader = getClass().getClassLoader();
        	return classLoader.getResourceAsStream("com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.tokens");
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Tml";	
       	}
       	
       	@Override
       	protected TslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start entryRuleTml
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:77:1: entryRuleTml returns [EObject current=null] : iv_ruleTml= ruleTml EOF ;
    public final EObject entryRuleTml() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTml = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:78:2: (iv_ruleTml= ruleTml EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:79:2: iv_ruleTml= ruleTml EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlRule(), currentNode); 
            pushFollow(FOLLOW_ruleTml_in_entryRuleTml75);
            iv_ruleTml=ruleTml();
            _fsp--;

             current =iv_ruleTml; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTml85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleTml


    // $ANTLR start ruleTml
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:86:1: ruleTml returns [EObject current=null] : ( () ( RULE_XMLHEAD )? RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleTml() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_3_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_methods_8_0 = null;

        EObject lv_children_9_0 = null;

        EObject lv_children_10_0 = null;

        EObject lv_children_11_0 = null;

        EObject lv_children_12_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:91:6: ( ( () ( RULE_XMLHEAD )? RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( () ( RULE_XMLHEAD )? RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( () ( RULE_XMLHEAD )? RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:2: () ( RULE_XMLHEAD )? RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:93:5: 
            {
             
                    temp=factory.create(grammarAccess.getTmlAccess().getTmlAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getTmlAccess().getTmlAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:103:2: ( RULE_XMLHEAD )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_XMLHEAD) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:103:3: RULE_XMLHEAD
                    {
                    match(input,RULE_XMLHEAD,FOLLOW_RULE_XMLHEAD_in_ruleTml129); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getXMLHeadTerminalRuleCall_1(), null); 
                        

                    }
                    break;

            }

            match(input,RULE_NAVASCRIPT_START,FOLLOW_RULE_NAVASCRIPT_START_in_ruleTml139); 
             
                createLeafNode(grammarAccess.getTmlAccess().getNAVASCRIPT_STARTTerminalRuleCall_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:111:1: ( (lv_attributes_3_0= rulePossibleExpression ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==RULE_ID) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:112:1: (lv_attributes_3_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:112:1: (lv_attributes_3_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:113:3: lv_attributes_3_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getAttributesPossibleExpressionParserRuleCall_3_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleTml159);
            	    lv_attributes_3_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_3_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:3: ( ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_XML_TAG_END) ) {
                alt4=1;
            }
            else if ( (LA4_0==RULE_XML_TAG_SINGLEEND) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("135:3: ( ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:4: ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:4: ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:5: RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* RULE_NAVASCRIPT_END
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleTml171); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getXML_TAG_ENDTerminalRuleCall_4_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:139:1: ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )*
                    loop3:
                    do {
                        int alt3=9;
                        switch ( input.LA(1) ) {
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt3=1;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt3=2;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt3=3;
                            }
                            break;
                        case RULE_METHODS_START_TAG:
                            {
                            alt3=4;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt3=5;
                            }
                            break;
                        case RULE_INCLUDE_START_TAG:
                            {
                            alt3=6;
                            }
                            break;
                        case RULE_VALIDATIONS_START_TAG:
                            {
                            alt3=7;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt3=8;
                            }
                            break;

                        }

                        switch (alt3) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:139:2: ( (lv_children_5_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:139:2: ( (lv_children_5_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:140:1: (lv_children_5_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:140:1: (lv_children_5_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:141:3: lv_children_5_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenMessageParserRuleCall_4_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleTml192);
                    	    lv_children_5_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_5_0, 
                    	    	        		"Message", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:164:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:164:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:165:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:165:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:166:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenMapParserRuleCall_4_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleTml219);
                    	    lv_children_6_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_6_0, 
                    	    	        		"Map", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:189:6: ( (lv_children_7_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:189:6: ( (lv_children_7_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:190:1: (lv_children_7_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:190:1: (lv_children_7_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:191:3: lv_children_7_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenParamParserRuleCall_4_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleTml246);
                    	    lv_children_7_0=ruleParam();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_7_0, 
                    	    	        		"Param", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:214:6: ( (lv_methods_8_0= ruleMethods ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:214:6: ( (lv_methods_8_0= ruleMethods ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:215:1: (lv_methods_8_0= ruleMethods )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:215:1: (lv_methods_8_0= ruleMethods )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:3: lv_methods_8_0= ruleMethods
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getMethodsMethodsParserRuleCall_4_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethods_in_ruleTml273);
                    	    lv_methods_8_0=ruleMethods();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"methods",
                    	    	        		lv_methods_8_0, 
                    	    	        		"Methods", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:239:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:239:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:240:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:240:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:241:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenDebugTagParserRuleCall_4_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleTml300);
                    	    lv_children_9_0=ruleDebugTag();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_9_0, 
                    	    	        		"DebugTag", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:264:6: ( (lv_children_10_0= ruleInclude ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:264:6: ( (lv_children_10_0= ruleInclude ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:265:1: (lv_children_10_0= ruleInclude )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:265:1: (lv_children_10_0= ruleInclude )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:266:3: lv_children_10_0= ruleInclude
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenIncludeParserRuleCall_4_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleInclude_in_ruleTml327);
                    	    lv_children_10_0=ruleInclude();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_10_0, 
                    	    	        		"Include", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:289:6: ( (lv_children_11_0= ruleValidations ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:289:6: ( (lv_children_11_0= ruleValidations ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:290:1: (lv_children_11_0= ruleValidations )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:290:1: (lv_children_11_0= ruleValidations )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:291:3: lv_children_11_0= ruleValidations
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenValidationsParserRuleCall_4_0_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleValidations_in_ruleTml354);
                    	    lv_children_11_0=ruleValidations();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_11_0, 
                    	    	        		"Validations", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 8 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:314:6: ( (lv_children_12_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:314:6: ( (lv_children_12_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:315:1: (lv_children_12_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:315:1: (lv_children_12_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:316:3: lv_children_12_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenCommentParserRuleCall_4_0_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleTml381);
                    	    lv_children_12_0=ruleComment();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_12_0, 
                    	    	        		"Comment", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    match(input,RULE_NAVASCRIPT_END,FOLLOW_RULE_NAVASCRIPT_END_in_ruleTml392); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getNAVASCRIPT_ENDTerminalRuleCall_4_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:343:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml407); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getXML_TAG_SINGLEENDTerminalRuleCall_4_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleTml


    // $ANTLR start entryRuleAttributeName
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:355:1: entryRuleAttributeName returns [String current=null] : iv_ruleAttributeName= ruleAttributeName EOF ;
    public final String entryRuleAttributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAttributeName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:356:2: (iv_ruleAttributeName= ruleAttributeName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:357:2: iv_ruleAttributeName= ruleAttributeName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAttributeNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleAttributeName_in_entryRuleAttributeName444);
            iv_ruleAttributeName=ruleAttributeName();
            _fsp--;

             current =iv_ruleAttributeName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAttributeName455); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleAttributeName


    // $ANTLR start ruleAttributeName
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:364:1: ruleAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleAttributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:369:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:370:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAttributeName494); 

            		current.merge(this_ID_0);
                
             
                createLeafNode(grammarAccess.getAttributeNameAccess().getIDTerminalRuleCall(), null); 
                

            }

             resetLookahead(); 
            	    lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleAttributeName


    // $ANTLR start entryRulePossibleExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:385:1: entryRulePossibleExpression returns [EObject current=null] : iv_rulePossibleExpression= rulePossibleExpression EOF ;
    public final EObject entryRulePossibleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePossibleExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:386:2: (iv_rulePossibleExpression= rulePossibleExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:387:2: iv_rulePossibleExpression= rulePossibleExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPossibleExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression538);
            iv_rulePossibleExpression=rulePossibleExpression();
            _fsp--;

             current =iv_rulePossibleExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePossibleExpression548); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRulePossibleExpression


    // $ANTLR start rulePossibleExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:394:1: rulePossibleExpression returns [EObject current=null] : ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) ) ;
    public final EObject rulePossibleExpression() throws RecognitionException {
        EObject current = null;

        Token lv_namespace_0_0=null;
        Token lv_endToken_6_0=null;
        Token lv_value_7_0=null;
        Token lv_value_8_0=null;
        AntlrDatatypeRuleToken lv_key_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:399:6: ( ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:400:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:400:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:400:2: ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:400:2: ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_ID) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==70) ) {
                    alt5=1;
                }
            }
            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:400:3: ( (lv_namespace_0_0= RULE_ID ) ) ':'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:400:3: ( (lv_namespace_0_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:401:1: (lv_namespace_0_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:401:1: (lv_namespace_0_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:402:3: lv_namespace_0_0= RULE_ID
                    {
                    lv_namespace_0_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePossibleExpression591); 

                    			createLeafNode(grammarAccess.getPossibleExpressionAccess().getNamespaceIDTerminalRuleCall_0_0_0(), "namespace"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"namespace",
                    	        		lv_namespace_0_0, 
                    	        		"ID", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,70,FOLLOW_70_in_rulePossibleExpression606); 

                            createLeafNode(grammarAccess.getPossibleExpressionAccess().getColonKeyword_0_1(), null); 
                        

                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:428:3: ( (lv_key_2_0= ruleAttributeName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:429:1: (lv_key_2_0= ruleAttributeName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:429:1: (lv_key_2_0= ruleAttributeName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:430:3: lv_key_2_0= ruleAttributeName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getKeyAttributeNameParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAttributeName_in_rulePossibleExpression629);
            lv_key_2_0=ruleAttributeName();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"key",
            	        		lv_key_2_0, 
            	        		"AttributeName", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,71,FOLLOW_71_in_rulePossibleExpression639); 

                    createLeafNode(grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:456:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case RULE_QUOTEQ:
                {
                alt6=1;
                }
                break;
            case RULE_ATTRIBUTESTRING:
                {
                alt6=2;
                }
                break;
            case RULE_EMPTYSTRING:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("456:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) )", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:456:2: ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:456:2: ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:456:3: RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) )
                    {
                    match(input,RULE_QUOTEQ,FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression650); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getQUOTEQTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:460:1: ( (lv_expressionValue_5_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:461:1: (lv_expressionValue_5_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:461:1: (lv_expressionValue_5_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:462:3: lv_expressionValue_5_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_3_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_rulePossibleExpression670);
                    lv_expressionValue_5_0=ruleTopLevel();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"expressionValue",
                    	        		lv_expressionValue_5_0, 
                    	        		"TopLevel", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:484:2: ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:485:1: (lv_endToken_6_0= RULE_SEMICOLONQUOTE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:485:1: (lv_endToken_6_0= RULE_SEMICOLONQUOTE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:486:3: lv_endToken_6_0= RULE_SEMICOLONQUOTE
                    {
                    lv_endToken_6_0=(Token)input.LT(1);
                    match(input,RULE_SEMICOLONQUOTE,FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression687); 

                    			createLeafNode(grammarAccess.getPossibleExpressionAccess().getEndTokenSEMICOLONQUOTETerminalRuleCall_3_0_2_0(), "endToken"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"endToken",
                    	        		lv_endToken_6_0, 
                    	        		"SEMICOLONQUOTE", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:509:6: ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:509:6: ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:510:1: (lv_value_7_0= RULE_ATTRIBUTESTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:510:1: (lv_value_7_0= RULE_ATTRIBUTESTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:511:3: lv_value_7_0= RULE_ATTRIBUTESTRING
                    {
                    lv_value_7_0=(Token)input.LT(1);
                    match(input,RULE_ATTRIBUTESTRING,FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression716); 

                    			createLeafNode(grammarAccess.getPossibleExpressionAccess().getValueATTRIBUTESTRINGTerminalRuleCall_3_1_0(), "value"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"value",
                    	        		lv_value_7_0, 
                    	        		"ATTRIBUTESTRING", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:534:6: ( (lv_value_8_0= RULE_EMPTYSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:534:6: ( (lv_value_8_0= RULE_EMPTYSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:535:1: (lv_value_8_0= RULE_EMPTYSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:535:1: (lv_value_8_0= RULE_EMPTYSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:536:3: lv_value_8_0= RULE_EMPTYSTRING
                    {
                    lv_value_8_0=(Token)input.LT(1);
                    match(input,RULE_EMPTYSTRING,FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression744); 

                    			createLeafNode(grammarAccess.getPossibleExpressionAccess().getValueEMPTYSTRINGTerminalRuleCall_3_2_0(), "value"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"value",
                    	        		lv_value_8_0, 
                    	        		"EMPTYSTRING", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end rulePossibleExpression


    // $ANTLR start entryRuleMethods
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:566:1: entryRuleMethods returns [EObject current=null] : iv_ruleMethods= ruleMethods EOF ;
    public final EObject entryRuleMethods() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethods = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:567:2: (iv_ruleMethods= ruleMethods EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:568:2: iv_ruleMethods= ruleMethods EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMethodsRule(), currentNode); 
            pushFollow(FOLLOW_ruleMethods_in_entryRuleMethods786);
            iv_ruleMethods=ruleMethods();
            _fsp--;

             current =iv_ruleMethods; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethods796); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMethods


    // $ANTLR start ruleMethods
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:575:1: ruleMethods returns [EObject current=null] : ( RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethods() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_2_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_method_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:580:6: ( ( RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:581:1: ( RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:581:1: ( RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:581:2: RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_METHODS_START_TAG,FOLLOW_RULE_METHODS_START_TAG_in_ruleMethods830); 
             
                createLeafNode(grammarAccess.getMethodsAccess().getMETHODS_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:585:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:586:5: 
            {
             
                    temp=factory.create(grammarAccess.getMethodsAccess().getMethodsAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getMethodsAccess().getMethodsAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:596:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_XML_TAG_END) ) {
                alt8=1;
            }
            else if ( (LA8_0==RULE_XML_TAG_SINGLEEND) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("596:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:596:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:596:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:596:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:596:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:597:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:597:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:598:3: lv_splitTag_2_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_2_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethods857); 

                    			createLeafNode(grammarAccess.getMethodsAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_2_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMethodsRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:620:2: ( (lv_method_3_0= ruleMethod ) )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==RULE_METHOD_START_TAG) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:621:1: (lv_method_3_0= ruleMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:621:1: (lv_method_3_0= ruleMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:622:3: lv_method_3_0= ruleMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMethodsAccess().getMethodMethodParserRuleCall_2_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethod_in_ruleMethods883);
                    	    lv_method_3_0=ruleMethod();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMethodsRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"method",
                    	    	        		lv_method_3_0, 
                    	    	        		"Method", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:644:3: ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:645:1: (lv_closedTag_4_0= RULE_METHODS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:645:1: (lv_closedTag_4_0= RULE_METHODS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:646:3: lv_closedTag_4_0= RULE_METHODS_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_METHODS_END_TAG,FOLLOW_RULE_METHODS_END_TAG_in_ruleMethods901); 

                    			createLeafNode(grammarAccess.getMethodsAccess().getClosedTagMETHODS_END_TAGTerminalRuleCall_2_0_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMethodsRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"METHODS_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:669:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods922); 
                     
                        createLeafNode(grammarAccess.getMethodsAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMethods


    // $ANTLR start entryRuleMethod
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:681:1: entryRuleMethod returns [EObject current=null] : iv_ruleMethod= ruleMethod EOF ;
    public final EObject entryRuleMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:682:2: (iv_ruleMethod= ruleMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:683:2: iv_ruleMethod= ruleMethod EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMethodRule(), currentNode); 
            pushFollow(FOLLOW_ruleMethod_in_entryRuleMethod958);
            iv_ruleMethod=ruleMethod();
            _fsp--;

             current =iv_ruleMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethod968); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMethod


    // $ANTLR start ruleMethod
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:690:1: ruleMethod returns [EObject current=null] : ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethod() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:695:6: ( ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:696:1: ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:696:1: ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:696:2: RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_METHOD_START_TAG,FOLLOW_RULE_METHOD_START_TAG_in_ruleMethod1002); 
             
                createLeafNode(grammarAccess.getMethodAccess().getMETHOD_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:700:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:701:5: 
            {
             
                    temp=factory.create(grammarAccess.getMethodAccess().getMethodAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getMethodAccess().getMethodAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_ID) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:712:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:712:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:713:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMethodAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMethod1031);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMethodRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:735:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_XML_TAG_END) ) {
                alt11=1;
            }
            else if ( (LA11_0==RULE_XML_TAG_SINGLEEND) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("735:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:735:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:735:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:735:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:735:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:736:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:736:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:737:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethod1051); 

                    			createLeafNode(grammarAccess.getMethodAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:759:2: ( (lv_children_4_0= ruleRequired ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==RULE_REQUIRED_START_TAG) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:760:1: (lv_children_4_0= ruleRequired )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:760:1: (lv_children_4_0= ruleRequired )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:761:3: lv_children_4_0= ruleRequired
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMethodAccess().getChildrenRequiredParserRuleCall_3_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRequired_in_ruleMethod1077);
                    	    lv_children_4_0=ruleRequired();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_4_0, 
                    	    	        		"Required", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:783:3: ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:784:1: (lv_closedTag_5_0= RULE_METHOD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:784:1: (lv_closedTag_5_0= RULE_METHOD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:785:3: lv_closedTag_5_0= RULE_METHOD_END_TAG
                    {
                    lv_closedTag_5_0=(Token)input.LT(1);
                    match(input,RULE_METHOD_END_TAG,FOLLOW_RULE_METHOD_END_TAG_in_ruleMethod1095); 

                    			createLeafNode(grammarAccess.getMethodAccess().getClosedTagMETHOD_END_TAGTerminalRuleCall_3_0_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"METHOD_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:808:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod1116); 
                     
                        createLeafNode(grammarAccess.getMethodAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMethod


    // $ANTLR start entryRuleValidations
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:820:1: entryRuleValidations returns [EObject current=null] : iv_ruleValidations= ruleValidations EOF ;
    public final EObject entryRuleValidations() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValidations = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:821:2: (iv_ruleValidations= ruleValidations EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:822:2: iv_ruleValidations= ruleValidations EOF
            {
             currentNode = createCompositeNode(grammarAccess.getValidationsRule(), currentNode); 
            pushFollow(FOLLOW_ruleValidations_in_entryRuleValidations1152);
            iv_ruleValidations=ruleValidations();
            _fsp--;

             current =iv_ruleValidations; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleValidations1162); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleValidations


    // $ANTLR start ruleValidations
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:829:1: ruleValidations returns [EObject current=null] : ( RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleValidations() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_2_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_children_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:834:6: ( ( RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:835:1: ( RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:835:1: ( RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:835:2: RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_VALIDATIONS_START_TAG,FOLLOW_RULE_VALIDATIONS_START_TAG_in_ruleValidations1196); 
             
                createLeafNode(grammarAccess.getValidationsAccess().getVALIDATIONS_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:839:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:840:5: 
            {
             
                    temp=factory.create(grammarAccess.getValidationsAccess().getValidationsAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getValidationsAccess().getValidationsAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_XML_TAG_END) ) {
                alt13=1;
            }
            else if ( (LA13_0==RULE_XML_TAG_SINGLEEND) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("850:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:851:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:851:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:852:3: lv_splitTag_2_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_2_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleValidations1223); 

                    			createLeafNode(grammarAccess.getValidationsAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_2_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getValidationsRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:874:2: ( (lv_children_3_0= ruleCheck ) )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==RULE_CHECK_START_TAG) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:875:1: (lv_children_3_0= ruleCheck )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:875:1: (lv_children_3_0= ruleCheck )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:876:3: lv_children_3_0= ruleCheck
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getValidationsAccess().getChildrenCheckParserRuleCall_2_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleCheck_in_ruleValidations1249);
                    	    lv_children_3_0=ruleCheck();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getValidationsRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_3_0, 
                    	    	        		"Check", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:898:3: ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:899:1: (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:899:1: (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:900:3: lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_VALIDATIONS_END_TAG,FOLLOW_RULE_VALIDATIONS_END_TAG_in_ruleValidations1267); 

                    			createLeafNode(grammarAccess.getValidationsAccess().getClosedTagVALIDATIONS_END_TAGTerminalRuleCall_2_0_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getValidationsRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"VALIDATIONS_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:923:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleValidations1288); 
                     
                        createLeafNode(grammarAccess.getValidationsAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleValidations


    // $ANTLR start entryRuleCheck
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:935:1: entryRuleCheck returns [EObject current=null] : iv_ruleCheck= ruleCheck EOF ;
    public final EObject entryRuleCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheck = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:936:2: (iv_ruleCheck= ruleCheck EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:937:2: iv_ruleCheck= ruleCheck EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCheckRule(), currentNode); 
            pushFollow(FOLLOW_ruleCheck_in_entryRuleCheck1324);
            iv_ruleCheck=ruleCheck();
            _fsp--;

             current =iv_ruleCheck; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCheck1334); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleCheck


    // $ANTLR start ruleCheck
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:944:1: ruleCheck returns [EObject current=null] : ( RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleCheck() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_expression_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:949:6: ( ( RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:950:1: ( RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:950:1: ( RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:950:2: RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_CHECK_START_TAG,FOLLOW_RULE_CHECK_START_TAG_in_ruleCheck1368); 
             
                createLeafNode(grammarAccess.getCheckAccess().getCHECK_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:954:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:955:5: 
            {
             
                    temp=factory.create(grammarAccess.getCheckAccess().getCheckAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getCheckAccess().getCheckAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:965:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_ID) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:966:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:966:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:967:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getCheckAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleCheck1397);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getCheckRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:989:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_XML_TAG_END) ) {
                alt15=1;
            }
            else if ( (LA15_0==RULE_XML_TAG_SINGLEEND) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("989:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:989:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:989:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:989:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:989:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:990:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:990:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:991:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleCheck1417); 

                    			createLeafNode(grammarAccess.getCheckAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getCheckRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1013:2: ( (lv_expression_4_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1014:1: (lv_expression_4_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1014:1: (lv_expression_4_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1015:3: lv_expression_4_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getCheckAccess().getExpressionTopLevelParserRuleCall_3_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleCheck1443);
                    lv_expression_4_0=ruleTopLevel();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getCheckRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"expression",
                    	        		lv_expression_4_0, 
                    	        		"TopLevel", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1037:2: ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1038:1: (lv_closedTag_5_0= RULE_CHECK_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1038:1: (lv_closedTag_5_0= RULE_CHECK_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1039:3: lv_closedTag_5_0= RULE_CHECK_END_TAG
                    {
                    lv_closedTag_5_0=(Token)input.LT(1);
                    match(input,RULE_CHECK_END_TAG,FOLLOW_RULE_CHECK_END_TAG_in_ruleCheck1460); 

                    			createLeafNode(grammarAccess.getCheckAccess().getClosedTagCHECK_END_TAGTerminalRuleCall_3_0_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getCheckRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"CHECK_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1062:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleCheck1481); 
                     
                        createLeafNode(grammarAccess.getCheckAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleCheck


    // $ANTLR start entryRuleComment
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1074:1: entryRuleComment returns [EObject current=null] : iv_ruleComment= ruleComment EOF ;
    public final EObject entryRuleComment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComment = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1075:2: (iv_ruleComment= ruleComment EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1076:2: iv_ruleComment= ruleComment EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCommentRule(), currentNode); 
            pushFollow(FOLLOW_ruleComment_in_entryRuleComment1517);
            iv_ruleComment=ruleComment();
            _fsp--;

             current =iv_ruleComment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComment1527); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleComment


    // $ANTLR start ruleComment
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1083:1: ruleComment returns [EObject current=null] : ( RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleComment() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1088:6: ( ( RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1089:1: ( RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1089:1: ( RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1089:2: RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_COMMENT_START_TAG,FOLLOW_RULE_COMMENT_START_TAG_in_ruleComment1561); 
             
                createLeafNode(grammarAccess.getCommentAccess().getCOMMENT_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1093:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1094:5: 
            {
             
                    temp=factory.create(grammarAccess.getCommentAccess().getCommentAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getCommentAccess().getCommentAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1104:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_ID) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1105:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1105:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1106:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getCommentAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleComment1590);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getCommentRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1128:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==RULE_XML_TAG_END) ) {
                alt17=1;
            }
            else if ( (LA17_0==RULE_XML_TAG_SINGLEEND) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1128:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1128:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1128:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1128:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1128:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1129:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1129:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1130:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleComment1610); 

                    			createLeafNode(grammarAccess.getCommentAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getCommentRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1152:2: ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1153:1: (lv_closedTag_4_0= RULE_COMMENT_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1153:1: (lv_closedTag_4_0= RULE_COMMENT_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1154:3: lv_closedTag_4_0= RULE_COMMENT_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_COMMENT_END_TAG,FOLLOW_RULE_COMMENT_END_TAG_in_ruleComment1632); 

                    			createLeafNode(grammarAccess.getCommentAccess().getClosedTagCOMMENT_END_TAGTerminalRuleCall_3_0_1_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getCommentRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"COMMENT_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1177:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleComment1653); 
                     
                        createLeafNode(grammarAccess.getCommentAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleComment


    // $ANTLR start entryRuleBreak
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1189:1: entryRuleBreak returns [EObject current=null] : iv_ruleBreak= ruleBreak EOF ;
    public final EObject entryRuleBreak() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBreak = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1190:2: (iv_ruleBreak= ruleBreak EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1191:2: iv_ruleBreak= ruleBreak EOF
            {
             currentNode = createCompositeNode(grammarAccess.getBreakRule(), currentNode); 
            pushFollow(FOLLOW_ruleBreak_in_entryRuleBreak1689);
            iv_ruleBreak=ruleBreak();
            _fsp--;

             current =iv_ruleBreak; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBreak1699); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleBreak


    // $ANTLR start ruleBreak
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1198:1: ruleBreak returns [EObject current=null] : ( RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleBreak() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1203:6: ( ( RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1204:1: ( RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1204:1: ( RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1204:2: RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_BREAK_START_TAG,FOLLOW_RULE_BREAK_START_TAG_in_ruleBreak1733); 
             
                createLeafNode(grammarAccess.getBreakAccess().getBREAK_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1208:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1209:5: 
            {
             
                    temp=factory.create(grammarAccess.getBreakAccess().getBreakAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getBreakAccess().getBreakAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==RULE_ID) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1220:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1220:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1221:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getBreakAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleBreak1762);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getBreakRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1243:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==RULE_XML_TAG_END) ) {
                alt19=1;
            }
            else if ( (LA19_0==RULE_XML_TAG_SINGLEEND) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1243:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1243:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1243:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1243:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1243:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1244:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1244:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1245:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleBreak1782); 

                    			createLeafNode(grammarAccess.getBreakAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getBreakRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1267:2: ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1268:1: (lv_closedTag_4_0= RULE_BREAK_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1268:1: (lv_closedTag_4_0= RULE_BREAK_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1269:3: lv_closedTag_4_0= RULE_BREAK_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_BREAK_END_TAG,FOLLOW_RULE_BREAK_END_TAG_in_ruleBreak1804); 

                    			createLeafNode(grammarAccess.getBreakAccess().getClosedTagBREAK_END_TAGTerminalRuleCall_3_0_1_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getBreakRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"BREAK_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1292:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleBreak1825); 
                     
                        createLeafNode(grammarAccess.getBreakAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleBreak


    // $ANTLR start entryRuleInclude
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1304:1: entryRuleInclude returns [EObject current=null] : iv_ruleInclude= ruleInclude EOF ;
    public final EObject entryRuleInclude() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInclude = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1305:2: (iv_ruleInclude= ruleInclude EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1306:2: iv_ruleInclude= ruleInclude EOF
            {
             currentNode = createCompositeNode(grammarAccess.getIncludeRule(), currentNode); 
            pushFollow(FOLLOW_ruleInclude_in_entryRuleInclude1861);
            iv_ruleInclude=ruleInclude();
            _fsp--;

             current =iv_ruleInclude; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleInclude1871); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleInclude


    // $ANTLR start ruleInclude
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1313:1: ruleInclude returns [EObject current=null] : ( RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleInclude() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1318:6: ( ( RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1319:1: ( RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1319:1: ( RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1319:2: RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_INCLUDE_START_TAG,FOLLOW_RULE_INCLUDE_START_TAG_in_ruleInclude1905); 
             
                createLeafNode(grammarAccess.getIncludeAccess().getINCLUDE_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1323:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1324:5: 
            {
             
                    temp=factory.create(grammarAccess.getIncludeAccess().getMethodAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getIncludeAccess().getMethodAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1334:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==RULE_ID) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1335:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1335:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1336:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getIncludeAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleInclude1934);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getIncludeRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_XML_TAG_END) ) {
                alt21=1;
            }
            else if ( (LA21_0==RULE_XML_TAG_SINGLEEND) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1358:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1359:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1359:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleInclude1954); 

                    			createLeafNode(grammarAccess.getIncludeAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getIncludeRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1382:2: ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1383:1: (lv_closedTag_4_0= RULE_INCLUDE_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1383:1: (lv_closedTag_4_0= RULE_INCLUDE_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1384:3: lv_closedTag_4_0= RULE_INCLUDE_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_INCLUDE_END_TAG,FOLLOW_RULE_INCLUDE_END_TAG_in_ruleInclude1976); 

                    			createLeafNode(grammarAccess.getIncludeAccess().getClosedTagINCLUDE_END_TAGTerminalRuleCall_3_0_1_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getIncludeRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"INCLUDE_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1407:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleInclude1997); 
                     
                        createLeafNode(grammarAccess.getIncludeAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleInclude


    // $ANTLR start entryRuleMessage
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1419:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1420:2: (iv_ruleMessage= ruleMessage EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1421:2: iv_ruleMessage= ruleMessage EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMessageRule(), currentNode); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage2033);
            iv_ruleMessage=ruleMessage();
            _fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage2043); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMessage


    // $ANTLR start ruleMessage
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1428:1: ruleMessage returns [EObject current=null] : ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_13_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_children_8_0 = null;

        EObject lv_children_9_0 = null;

        EObject lv_children_10_0 = null;

        EObject lv_children_11_0 = null;

        EObject lv_children_12_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1433:6: ( ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1434:1: ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1434:1: ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1434:2: RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_MESSAGE_START_TAG,FOLLOW_RULE_MESSAGE_START_TAG_in_ruleMessage2077); 
             
                createLeafNode(grammarAccess.getMessageAccess().getMESSAGE_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1438:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1439:5: 
            {
             
                    temp=factory.create(grammarAccess.getMessageAccess().getMessageAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getMessageAccess().getMessageAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1449:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==RULE_ID) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1450:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1450:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1451:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMessage2106);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RULE_XML_TAG_END) ) {
                alt24=1;
            }
            else if ( (LA24_0==RULE_XML_TAG_SINGLEEND) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1473:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1474:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1474:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1475:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMessage2126); 

                    			createLeafNode(grammarAccess.getMessageAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1497:2: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )*
                    loop23:
                    do {
                        int alt23=10;
                        switch ( input.LA(1) ) {
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt23=1;
                            }
                            break;
                        case RULE_PROPERTY_START_TAG:
                            {
                            alt23=2;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt23=3;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt23=4;
                            }
                            break;
                        case RULE_MAP_METHOD_STARTTAG_START:
                            {
                            alt23=5;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt23=6;
                            }
                            break;
                        case RULE_FIELD_START_TAG:
                            {
                            alt23=7;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt23=8;
                            }
                            break;
                        case RULE_BREAK_START_TAG:
                            {
                            alt23=9;
                            }
                            break;

                        }

                        switch (alt23) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1497:3: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1497:3: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1498:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1498:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1499:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMessage2153);
                    	    lv_children_4_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_4_0, 
                    	    	        		"Message", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1522:6: ( (lv_children_5_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1522:6: ( (lv_children_5_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1523:1: (lv_children_5_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1523:1: (lv_children_5_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1524:3: lv_children_5_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMessage2180);
                    	    lv_children_5_0=ruleProperty();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_5_0, 
                    	    	        		"Property", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1547:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1547:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1548:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1548:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1549:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenParamParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMessage2207);
                    	    lv_children_6_0=ruleParam();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_6_0, 
                    	    	        		"Param", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1572:6: ( (lv_children_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1572:6: ( (lv_children_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1573:1: (lv_children_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1573:1: (lv_children_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1574:3: lv_children_7_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMessage2234);
                    	    lv_children_7_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_7_0, 
                    	    	        		"Map", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1597:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1597:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1598:1: (lv_children_8_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1598:1: (lv_children_8_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1599:3: lv_children_8_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapMethodParserRuleCall_3_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMessage2261);
                    	    lv_children_8_0=ruleMapMethod();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_8_0, 
                    	    	        		"MapMethod", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1622:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1622:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1623:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1623:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenDebugTagParserRuleCall_3_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMessage2288);
                    	    lv_children_9_0=ruleDebugTag();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_9_0, 
                    	    	        		"DebugTag", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1647:6: ( (lv_children_10_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1647:6: ( (lv_children_10_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1648:1: (lv_children_10_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1648:1: (lv_children_10_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1649:3: lv_children_10_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenFieldParserRuleCall_3_0_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMessage2315);
                    	    lv_children_10_0=ruleField();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_10_0, 
                    	    	        		"Field", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 8 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1672:6: ( (lv_children_11_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1672:6: ( (lv_children_11_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1673:1: (lv_children_11_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1673:1: (lv_children_11_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1674:3: lv_children_11_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenCommentParserRuleCall_3_0_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMessage2342);
                    	    lv_children_11_0=ruleComment();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_11_0, 
                    	    	        		"Comment", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 9 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1697:6: ( (lv_children_12_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1697:6: ( (lv_children_12_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1698:1: (lv_children_12_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1698:1: (lv_children_12_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1699:3: lv_children_12_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenBreakParserRuleCall_3_0_1_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMessage2369);
                    	    lv_children_12_0=ruleBreak();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_12_0, 
                    	    	        		"Break", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1721:4: ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1722:1: (lv_closedTag_13_0= RULE_MESSAGE_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1722:1: (lv_closedTag_13_0= RULE_MESSAGE_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1723:3: lv_closedTag_13_0= RULE_MESSAGE_END_TAG
                    {
                    lv_closedTag_13_0=(Token)input.LT(1);
                    match(input,RULE_MESSAGE_END_TAG,FOLLOW_RULE_MESSAGE_END_TAG_in_ruleMessage2388); 

                    			createLeafNode(grammarAccess.getMessageAccess().getClosedTagMESSAGE_END_TAGTerminalRuleCall_3_0_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"MESSAGE_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1746:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage2409); 
                     
                        createLeafNode(grammarAccess.getMessageAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1(), null); 
                        

                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMessage


    // $ANTLR start entryRuleMap
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1758:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1759:2: (iv_ruleMap= ruleMap EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1760:2: iv_ruleMap= ruleMap EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapRule(), currentNode); 
            pushFollow(FOLLOW_ruleMap_in_entryRuleMap2445);
            iv_ruleMap=ruleMap();
            _fsp--;

             current =iv_ruleMap; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMap2455); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMap


    // $ANTLR start ruleMap
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1767:1: ruleMap returns [EObject current=null] : ( RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_7_0=null;
        Token lv_closedTag_20_0=null;
        AntlrDatatypeRuleToken lv_mapName_3_0 = null;

        EObject lv_attributes_4_0 = null;

        EObject lv_attributes_5_0 = null;

        EObject lv_children_8_0 = null;

        EObject lv_children_9_0 = null;

        EObject lv_children_10_0 = null;

        EObject lv_children_11_0 = null;

        EObject lv_children_12_0 = null;

        EObject lv_children_13_0 = null;

        EObject lv_children_14_0 = null;

        EObject lv_children_15_0 = null;

        EObject lv_children_16_0 = null;

        AntlrDatatypeRuleToken lv_mapClosingName_19_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1772:6: ( ( RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1773:1: ( RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1773:1: ( RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1773:2: RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) )
            {
            match(input,RULE_MAPSTARTKEYWORD,FOLLOW_RULE_MAPSTARTKEYWORD_in_ruleMap2489); 
             
                createLeafNode(grammarAccess.getMapAccess().getMAPSTARTKEYWORDTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1777:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1778:5: 
            {
             
                    temp=factory.create(grammarAccess.getMapAccess().getMapAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getMapAccess().getMapAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1788:2: ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==RULE_DOT) ) {
                alt27=1;
            }
            else if ( (LA27_0==RULE_XML_TAG_END||(LA27_0>=RULE_XML_TAG_SINGLEEND && LA27_0<=RULE_ID)) ) {
                alt27=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1788:2: ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* )", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1788:3: ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1788:3: ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1788:4: RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )*
                    {
                    match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMap2508); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getDOTTerminalRuleCall_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1792:1: ( (lv_mapName_3_0= ruleMapId ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1793:1: (lv_mapName_3_0= ruleMapId )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1793:1: (lv_mapName_3_0= ruleMapId )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1794:3: lv_mapName_3_0= ruleMapId
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapNameMapIdParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapId_in_ruleMap2528);
                    lv_mapName_3_0=ruleMapId();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"mapName",
                    	        		lv_mapName_3_0, 
                    	        		"MapId", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1816:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==RULE_ID) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1817:1: (lv_attributes_4_0= rulePossibleExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1817:1: (lv_attributes_4_0= rulePossibleExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1818:3: lv_attributes_4_0= rulePossibleExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_0_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap2549);
                    	    lv_attributes_4_0=rulePossibleExpression();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"attributes",
                    	    	        		lv_attributes_4_0, 
                    	    	        		"PossibleExpression", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1841:6: ( (lv_attributes_5_0= rulePossibleExpression ) )*
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1841:6: ( (lv_attributes_5_0= rulePossibleExpression ) )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==RULE_ID) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1842:1: (lv_attributes_5_0= rulePossibleExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1842:1: (lv_attributes_5_0= rulePossibleExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1843:3: lv_attributes_5_0= rulePossibleExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap2578);
                    	    lv_attributes_5_0=rulePossibleExpression();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"attributes",
                    	    	        		lv_attributes_5_0, 
                    	    	        		"PossibleExpression", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop26;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1865:4: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_XML_TAG_SINGLEEND) ) {
                alt30=1;
            }
            else if ( (LA30_0==RULE_XML_TAG_END) ) {
                alt30=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1865:4: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) )", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1865:5: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap2590); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1870:6: ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1870:6: ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1870:7: ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1870:7: ( (lv_splitTag_7_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1871:1: (lv_splitTag_7_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1871:1: (lv_splitTag_7_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:3: lv_splitTag_7_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_7_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap2613); 

                    			createLeafNode(grammarAccess.getMapAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1894:2: ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )*
                    loop28:
                    do {
                        int alt28=10;
                        switch ( input.LA(1) ) {
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt28=1;
                            }
                            break;
                        case RULE_PROPERTY_START_TAG:
                            {
                            alt28=2;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt28=3;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt28=4;
                            }
                            break;
                        case RULE_MAP_METHOD_STARTTAG_START:
                            {
                            alt28=5;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt28=6;
                            }
                            break;
                        case RULE_FIELD_START_TAG:
                            {
                            alt28=7;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt28=8;
                            }
                            break;
                        case RULE_BREAK_START_TAG:
                            {
                            alt28=9;
                            }
                            break;

                        }

                        switch (alt28) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1894:3: ( (lv_children_8_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1894:3: ( (lv_children_8_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1895:1: (lv_children_8_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1895:1: (lv_children_8_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1896:3: lv_children_8_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMap2640);
                    	    lv_children_8_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_8_0, 
                    	    	        		"Message", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1919:6: ( (lv_children_9_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1919:6: ( (lv_children_9_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1920:1: (lv_children_9_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1920:1: (lv_children_9_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1921:3: lv_children_9_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMap2667);
                    	    lv_children_9_0=ruleProperty();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_9_0, 
                    	    	        		"Property", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1944:6: ( (lv_children_10_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1944:6: ( (lv_children_10_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1945:1: (lv_children_10_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1945:1: (lv_children_10_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1946:3: lv_children_10_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenParamParserRuleCall_3_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMap2694);
                    	    lv_children_10_0=ruleParam();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_10_0, 
                    	    	        		"Param", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1969:6: ( (lv_children_11_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1969:6: ( (lv_children_11_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1970:1: (lv_children_11_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1970:1: (lv_children_11_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1971:3: lv_children_11_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_3_1_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMap2721);
                    	    lv_children_11_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_11_0, 
                    	    	        		"Map", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1994:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1994:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1995:1: (lv_children_12_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1995:1: (lv_children_12_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1996:3: lv_children_12_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapMethodParserRuleCall_3_1_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMap2748);
                    	    lv_children_12_0=ruleMapMethod();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_12_0, 
                    	    	        		"MapMethod", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2019:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2019:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2020:1: (lv_children_13_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2020:1: (lv_children_13_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2021:3: lv_children_13_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenDebugTagParserRuleCall_3_1_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMap2775);
                    	    lv_children_13_0=ruleDebugTag();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_13_0, 
                    	    	        		"DebugTag", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2044:6: ( (lv_children_14_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2044:6: ( (lv_children_14_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2045:1: (lv_children_14_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2045:1: (lv_children_14_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2046:3: lv_children_14_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenFieldParserRuleCall_3_1_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMap2802);
                    	    lv_children_14_0=ruleField();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_14_0, 
                    	    	        		"Field", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 8 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2069:6: ( (lv_children_15_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2069:6: ( (lv_children_15_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2070:1: (lv_children_15_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2070:1: (lv_children_15_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2071:3: lv_children_15_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenCommentParserRuleCall_3_1_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMap2829);
                    	    lv_children_15_0=ruleComment();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_15_0, 
                    	    	        		"Comment", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 9 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2094:6: ( (lv_children_16_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2094:6: ( (lv_children_16_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2095:1: (lv_children_16_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2095:1: (lv_children_16_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2096:3: lv_children_16_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenBreakParserRuleCall_3_1_1_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMap2856);
                    	    lv_children_16_0=ruleBreak();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_16_0, 
                    	    	        		"Break", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop28;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2118:4: ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2118:5: RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) )
                    {
                    match(input,RULE_MAPENDKEYWORD,FOLLOW_RULE_MAPENDKEYWORD_in_ruleMap2868); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getMAPENDKEYWORDTerminalRuleCall_3_1_2_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2122:1: ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==RULE_DOT) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2122:2: RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) )
                            {
                            match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMap2877); 
                             
                                createLeafNode(grammarAccess.getMapAccess().getDOTTerminalRuleCall_3_1_2_1_0(), null); 
                                
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2126:1: ( (lv_mapClosingName_19_0= ruleMapId ) )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2127:1: (lv_mapClosingName_19_0= ruleMapId )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2127:1: (lv_mapClosingName_19_0= ruleMapId )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2128:3: lv_mapClosingName_19_0= ruleMapId
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapClosingNameMapIdParserRuleCall_3_1_2_1_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleMapId_in_ruleMap2897);
                            lv_mapClosingName_19_0=ruleMapId();
                            _fsp--;


                            	        if (current==null) {
                            	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                            	            associateNodeWithAstElement(currentNode.getParent(), current);
                            	        }
                            	        try {
                            	       		set(
                            	       			current, 
                            	       			"mapClosingName",
                            	        		lv_mapClosingName_19_0, 
                            	        		"MapId", 
                            	        		currentNode);
                            	        } catch (ValueConverterException vce) {
                            				handleValueConverterException(vce);
                            	        }
                            	        currentNode = currentNode.getParent();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2150:4: ( (lv_closedTag_20_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2151:1: (lv_closedTag_20_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2151:1: (lv_closedTag_20_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2152:3: lv_closedTag_20_0= RULE_XML_TAG_END
                    {
                    lv_closedTag_20_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap2916); 

                    			createLeafNode(grammarAccess.getMapAccess().getClosedTagXML_TAG_ENDTerminalRuleCall_3_1_2_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMap


    // $ANTLR start entryRuleMapId
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2182:1: entryRuleMapId returns [String current=null] : iv_ruleMapId= ruleMapId EOF ;
    public final String entryRuleMapId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMapId = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2183:2: (iv_ruleMapId= ruleMapId EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2184:2: iv_ruleMapId= ruleMapId EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapIdRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapId_in_entryRuleMapId2961);
            iv_ruleMapId=ruleMapId();
            _fsp--;

             current =iv_ruleMapId.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapId2972); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMapId


    // $ANTLR start ruleMapId
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2191:1: ruleMapId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleMapId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2196:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2197:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapId3011); 

            		current.merge(this_ID_0);
                
             
                createLeafNode(grammarAccess.getMapIdAccess().getIDTerminalRuleCall(), null); 
                

            }

             resetLookahead(); 
            	    lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMapId


    // $ANTLR start entryRuleRequired
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2212:1: entryRuleRequired returns [EObject current=null] : iv_ruleRequired= ruleRequired EOF ;
    public final EObject entryRuleRequired() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequired = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2213:2: (iv_ruleRequired= ruleRequired EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2214:2: iv_ruleRequired= ruleRequired EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRequiredRule(), currentNode); 
            pushFollow(FOLLOW_ruleRequired_in_entryRuleRequired3055);
            iv_ruleRequired=ruleRequired();
            _fsp--;

             current =iv_ruleRequired; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequired3065); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleRequired


    // $ANTLR start ruleRequired
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2221:1: ruleRequired returns [EObject current=null] : ( RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) ) ;
    public final EObject ruleRequired() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2226:6: ( ( RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2227:1: ( RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2227:1: ( RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2227:2: RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) )
            {
            match(input,RULE_REQUIRED_START_TAG,FOLLOW_RULE_REQUIRED_START_TAG_in_ruleRequired3099); 
             
                createLeafNode(grammarAccess.getRequiredAccess().getREQUIRED_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2231:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2232:5: 
            {
             
                    temp=factory.create(grammarAccess.getRequiredAccess().getRequiredAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getRequiredAccess().getRequiredAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2242:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==RULE_ID) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2243:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2243:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2244:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getRequiredAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleRequired3128);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getRequiredRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2266:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==RULE_XML_TAG_SINGLEEND) ) {
                alt32=1;
            }
            else if ( (LA32_0==RULE_XML_TAG_END) ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2266:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) )", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2266:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleRequired3139); 
                     
                        createLeafNode(grammarAccess.getRequiredAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2271:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2271:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2271:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2271:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2272:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2272:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2273:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleRequired3162); 

                    			createLeafNode(grammarAccess.getRequiredAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRequiredRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2295:2: ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2296:1: (lv_closedTag_5_0= RULE_REQUIRED_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2296:1: (lv_closedTag_5_0= RULE_REQUIRED_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2297:3: lv_closedTag_5_0= RULE_REQUIRED_END_TAG
                    {
                    lv_closedTag_5_0=(Token)input.LT(1);
                    match(input,RULE_REQUIRED_END_TAG,FOLLOW_RULE_REQUIRED_END_TAG_in_ruleRequired3184); 

                    			createLeafNode(grammarAccess.getRequiredAccess().getClosedTagREQUIRED_END_TAGTerminalRuleCall_3_1_1_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRequiredRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"REQUIRED_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleRequired


    // $ANTLR start entryRuleProperty
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2327:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2328:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2329:2: iv_ruleProperty= ruleProperty EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPropertyRule(), currentNode); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty3227);
            iv_ruleProperty=ruleProperty();
            _fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty3237); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleProperty


    // $ANTLR start ruleProperty
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2336:1: ruleProperty returns [EObject current=null] : ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_7_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2341:6: ( ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2342:1: ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2342:1: ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2342:2: RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) )
            {
            match(input,RULE_PROPERTY_START_TAG,FOLLOW_RULE_PROPERTY_START_TAG_in_ruleProperty3271); 
             
                createLeafNode(grammarAccess.getPropertyAccess().getPROPERTY_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2346:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2347:5: 
            {
             
                    temp=factory.create(grammarAccess.getPropertyAccess().getPropertyAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getPropertyAccess().getPropertyAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==RULE_ID) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2358:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2358:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2359:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleProperty3300);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getPropertyRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2381:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_XML_TAG_SINGLEEND) ) {
                alt35=1;
            }
            else if ( (LA35_0==RULE_XML_TAG_END) ) {
                alt35=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2381:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) )", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2381:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty3311); 
                     
                        createLeafNode(grammarAccess.getPropertyAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2387:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2387:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2388:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleProperty3334); 

                    			createLeafNode(grammarAccess.getPropertyAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPropertyRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2410:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )*
                    loop34:
                    do {
                        int alt34=3;
                        int LA34_0 = input.LA(1);

                        if ( ((LA34_0>=RULE_EXPRESSION_START_TAG && LA34_0<=RULE_OPTION_START_TAG)) ) {
                            alt34=1;
                        }
                        else if ( (LA34_0==RULE_MAPSTARTKEYWORD) ) {
                            alt34=2;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2410:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2410:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2411:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2411:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2412:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleProperty3361);
                    	    lv_children_5_0=ruleExpressionOrOption();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getPropertyRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_5_0, 
                    	    	        		"ExpressionOrOption", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2435:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2435:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2436:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2436:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2437:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getChildrenMapParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleProperty3388);
                    	    lv_children_6_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getPropertyRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_6_0, 
                    	    	        		"Map", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2459:4: ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2460:1: (lv_closedTag_7_0= RULE_PROPERTY_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2460:1: (lv_closedTag_7_0= RULE_PROPERTY_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2461:3: lv_closedTag_7_0= RULE_PROPERTY_END_TAG
                    {
                    lv_closedTag_7_0=(Token)input.LT(1);
                    match(input,RULE_PROPERTY_END_TAG,FOLLOW_RULE_PROPERTY_END_TAG_in_ruleProperty3407); 

                    			createLeafNode(grammarAccess.getPropertyAccess().getClosedTagPROPERTY_END_TAGTerminalRuleCall_3_1_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPropertyRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"PROPERTY_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleProperty


    // $ANTLR start entryRuleParam
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2491:1: entryRuleParam returns [EObject current=null] : iv_ruleParam= ruleParam EOF ;
    public final EObject entryRuleParam() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2492:2: (iv_ruleParam= ruleParam EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2493:2: iv_ruleParam= ruleParam EOF
            {
             currentNode = createCompositeNode(grammarAccess.getParamRule(), currentNode); 
            pushFollow(FOLLOW_ruleParam_in_entryRuleParam3450);
            iv_ruleParam=ruleParam();
            _fsp--;

             current =iv_ruleParam; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParam3460); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleParam


    // $ANTLR start ruleParam
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2500:1: ruleParam returns [EObject current=null] : ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) ) ;
    public final EObject ruleParam() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_7_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2505:6: ( ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2506:1: ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2506:1: ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2506:2: RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) )
            {
            match(input,RULE_PARAM_START_TAG,FOLLOW_RULE_PARAM_START_TAG_in_ruleParam3494); 
             
                createLeafNode(grammarAccess.getParamAccess().getPARAM_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2510:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2511:5: 
            {
             
                    temp=factory.create(grammarAccess.getParamAccess().getParamAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getParamAccess().getParamAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2521:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==RULE_ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2522:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2522:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2523:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleParam3523);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getParamRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2545:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==RULE_XML_TAG_SINGLEEND) ) {
                alt38=1;
            }
            else if ( (LA38_0==RULE_XML_TAG_END) ) {
                alt38=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2545:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) )", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2545:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam3534); 
                     
                        createLeafNode(grammarAccess.getParamAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2550:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2550:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2550:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2550:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2551:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2551:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2552:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleParam3557); 

                    			createLeafNode(grammarAccess.getParamAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getParamRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2574:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )*
                    loop37:
                    do {
                        int alt37=3;
                        int LA37_0 = input.LA(1);

                        if ( ((LA37_0>=RULE_EXPRESSION_START_TAG && LA37_0<=RULE_OPTION_START_TAG)) ) {
                            alt37=1;
                        }
                        else if ( (LA37_0==RULE_MAPSTARTKEYWORD) ) {
                            alt37=2;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2574:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2574:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2575:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2575:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2576:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleParam3584);
                    	    lv_children_5_0=ruleExpressionOrOption();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getParamRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_5_0, 
                    	    	        		"ExpressionOrOption", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2599:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2599:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2600:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2600:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2601:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getChildrenMapParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleParam3611);
                    	    lv_children_6_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getParamRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_6_0, 
                    	    	        		"Map", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2623:4: ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2624:1: (lv_closedTag_7_0= RULE_PARAM_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2624:1: (lv_closedTag_7_0= RULE_PARAM_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2625:3: lv_closedTag_7_0= RULE_PARAM_END_TAG
                    {
                    lv_closedTag_7_0=(Token)input.LT(1);
                    match(input,RULE_PARAM_END_TAG,FOLLOW_RULE_PARAM_END_TAG_in_ruleParam3630); 

                    			createLeafNode(grammarAccess.getParamAccess().getClosedTagPARAM_END_TAGTerminalRuleCall_3_1_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getParamRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"PARAM_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleParam


    // $ANTLR start entryRuleMapMethod
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2655:1: entryRuleMapMethod returns [EObject current=null] : iv_ruleMapMethod= ruleMapMethod EOF ;
    public final EObject entryRuleMapMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2656:2: (iv_ruleMapMethod= ruleMapMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2657:2: iv_ruleMapMethod= ruleMapMethod EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapMethodRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapMethod_in_entryRuleMapMethod3673);
            iv_ruleMapMethod=ruleMapMethod();
            _fsp--;

             current =iv_ruleMapMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapMethod3683); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMapMethod


    // $ANTLR start ruleMapMethod
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2664:1: ruleMapMethod returns [EObject current=null] : ( RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) ) ) ;
    public final EObject ruleMapMethod() throws RecognitionException {
        EObject current = null;

        Token lv_mapName_1_0=null;
        Token lv_splitTag_6_0=null;
        Token lv_closedTag_17_0=null;
        Token lv_methodClosingName_18_0=null;
        Token lv_methodClosingMethod_20_0=null;
        AntlrDatatypeRuleToken lv_methodName_3_0 = null;

        EObject lv_attributes_4_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_children_8_0 = null;

        EObject lv_children_9_0 = null;

        EObject lv_children_10_0 = null;

        EObject lv_children_11_0 = null;

        EObject lv_children_12_0 = null;

        EObject lv_children_13_0 = null;

        EObject lv_children_14_0 = null;

        EObject lv_children_15_0 = null;

        EObject lv_children_16_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2669:6: ( ( RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2670:1: ( RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2670:1: ( RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2670:2: RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) )
            {
            match(input,RULE_MAP_METHOD_STARTTAG_START,FOLLOW_RULE_MAP_METHOD_STARTTAG_START_in_ruleMapMethod3717); 
             
                createLeafNode(grammarAccess.getMapMethodAccess().getMAP_METHOD_STARTTAG_STARTTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2674:1: ( (lv_mapName_1_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2675:1: (lv_mapName_1_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2675:1: (lv_mapName_1_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2676:3: lv_mapName_1_0= RULE_ID
            {
            lv_mapName_1_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod3733); 

            			createLeafNode(grammarAccess.getMapMethodAccess().getMapNameIDTerminalRuleCall_1_0(), "mapName"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"mapName",
            	        		lv_mapName_1_0, 
            	        		"ID", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMapMethod3747); 
             
                createLeafNode(grammarAccess.getMapMethodAccess().getDOTTerminalRuleCall_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2702:1: ( (lv_methodName_3_0= ruleAttributeName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2703:1: (lv_methodName_3_0= ruleAttributeName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2703:1: (lv_methodName_3_0= ruleAttributeName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2704:3: lv_methodName_3_0= ruleAttributeName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getMethodNameAttributeNameParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAttributeName_in_ruleMapMethod3767);
            lv_methodName_3_0=ruleAttributeName();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"methodName",
            	        		lv_methodName_3_0, 
            	        		"AttributeName", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2726:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==RULE_ID) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2727:1: (lv_attributes_4_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2727:1: (lv_attributes_4_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2728:3: lv_attributes_4_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getAttributesPossibleExpressionParserRuleCall_4_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMapMethod3788);
            	    lv_attributes_4_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_4_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2750:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_XML_TAG_SINGLEEND) ) {
                alt41=1;
            }
            else if ( (LA41_0==RULE_XML_TAG_END) ) {
                alt41=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2750:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) )", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2750:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod3799); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_SINGLEENDTerminalRuleCall_5_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2755:6: ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2755:6: ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2755:7: ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2755:7: ( (lv_splitTag_6_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2756:1: (lv_splitTag_6_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2756:1: (lv_splitTag_6_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2757:3: lv_splitTag_6_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_6_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod3822); 

                    			createLeafNode(grammarAccess.getMapMethodAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_5_1_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2779:2: ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )*
                    loop40:
                    do {
                        int alt40=11;
                        switch ( input.LA(1) ) {
                        case RULE_EXPRESSION_START_TAG:
                        case RULE_OPTION_START_TAG:
                            {
                            alt40=1;
                            }
                            break;
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt40=2;
                            }
                            break;
                        case RULE_PROPERTY_START_TAG:
                            {
                            alt40=3;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt40=4;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt40=5;
                            }
                            break;
                        case RULE_MAP_METHOD_STARTTAG_START:
                            {
                            alt40=6;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt40=7;
                            }
                            break;
                        case RULE_FIELD_START_TAG:
                            {
                            alt40=8;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt40=9;
                            }
                            break;
                        case RULE_BREAK_START_TAG:
                            {
                            alt40=10;
                            }
                            break;

                        }

                        switch (alt40) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2779:3: ( (lv_children_7_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2779:3: ( (lv_children_7_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:1: (lv_children_7_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:1: (lv_children_7_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2781:3: lv_children_7_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenExpressionOrOptionParserRuleCall_5_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleMapMethod3849);
                    	    lv_children_7_0=ruleExpressionOrOption();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_7_0, 
                    	    	        		"ExpressionOrOption", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2804:6: ( (lv_children_8_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2804:6: ( (lv_children_8_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2805:1: (lv_children_8_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2805:1: (lv_children_8_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2806:3: lv_children_8_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMessageParserRuleCall_5_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMapMethod3876);
                    	    lv_children_8_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_8_0, 
                    	    	        		"Message", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2829:6: ( (lv_children_9_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2829:6: ( (lv_children_9_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2830:1: (lv_children_9_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2830:1: (lv_children_9_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2831:3: lv_children_9_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenPropertyParserRuleCall_5_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMapMethod3903);
                    	    lv_children_9_0=ruleProperty();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_9_0, 
                    	    	        		"Property", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2854:6: ( (lv_children_10_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2854:6: ( (lv_children_10_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2855:1: (lv_children_10_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2855:1: (lv_children_10_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2856:3: lv_children_10_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenParamParserRuleCall_5_1_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMapMethod3930);
                    	    lv_children_10_0=ruleParam();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_10_0, 
                    	    	        		"Param", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2879:6: ( (lv_children_11_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2879:6: ( (lv_children_11_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2880:1: (lv_children_11_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2880:1: (lv_children_11_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2881:3: lv_children_11_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMapParserRuleCall_5_1_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMapMethod3957);
                    	    lv_children_11_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_11_0, 
                    	    	        		"Map", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2904:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2904:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2905:1: (lv_children_12_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2905:1: (lv_children_12_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2906:3: lv_children_12_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMapMethodParserRuleCall_5_1_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMapMethod3984);
                    	    lv_children_12_0=ruleMapMethod();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_12_0, 
                    	    	        		"MapMethod", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2929:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2929:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2930:1: (lv_children_13_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2930:1: (lv_children_13_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2931:3: lv_children_13_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenDebugTagParserRuleCall_5_1_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMapMethod4011);
                    	    lv_children_13_0=ruleDebugTag();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_13_0, 
                    	    	        		"DebugTag", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 8 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2954:6: ( (lv_children_14_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2954:6: ( (lv_children_14_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2955:1: (lv_children_14_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2955:1: (lv_children_14_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2956:3: lv_children_14_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenFieldParserRuleCall_5_1_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMapMethod4038);
                    	    lv_children_14_0=ruleField();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_14_0, 
                    	    	        		"Field", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 9 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2979:6: ( (lv_children_15_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2979:6: ( (lv_children_15_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2980:1: (lv_children_15_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2980:1: (lv_children_15_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2981:3: lv_children_15_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenCommentParserRuleCall_5_1_1_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMapMethod4065);
                    	    lv_children_15_0=ruleComment();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_15_0, 
                    	    	        		"Comment", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 10 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3004:6: ( (lv_children_16_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3004:6: ( (lv_children_16_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3005:1: (lv_children_16_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3005:1: (lv_children_16_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3006:3: lv_children_16_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenBreakParserRuleCall_5_1_1_9_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMapMethod4092);
                    	    lv_children_16_0=ruleBreak();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_16_0, 
                    	    	        		"Break", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop40;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3028:4: ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3029:1: (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3029:1: (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3030:3: lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START
                    {
                    lv_closedTag_17_0=(Token)input.LT(1);
                    match(input,RULE_MAP_METHOD_ENDTAG_START,FOLLOW_RULE_MAP_METHOD_ENDTAG_START_in_ruleMapMethod4111); 

                    			createLeafNode(grammarAccess.getMapMethodAccess().getClosedTagMAP_METHOD_ENDTAG_STARTTerminalRuleCall_5_1_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"MAP_METHOD_ENDTAG_START", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3052:2: ( (lv_methodClosingName_18_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3053:1: (lv_methodClosingName_18_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3053:1: (lv_methodClosingName_18_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3054:3: lv_methodClosingName_18_0= RULE_ID
                    {
                    lv_methodClosingName_18_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod4133); 

                    			createLeafNode(grammarAccess.getMapMethodAccess().getMethodClosingNameIDTerminalRuleCall_5_1_3_0(), "methodClosingName"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"methodClosingName",
                    	        		lv_methodClosingName_18_0, 
                    	        		"ID", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMapMethod4147); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getDOTTerminalRuleCall_5_1_4(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3080:1: ( (lv_methodClosingMethod_20_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3081:1: (lv_methodClosingMethod_20_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3081:1: (lv_methodClosingMethod_20_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3082:3: lv_methodClosingMethod_20_0= RULE_ID
                    {
                    lv_methodClosingMethod_20_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod4163); 

                    			createLeafNode(grammarAccess.getMapMethodAccess().getMethodClosingMethodIDTerminalRuleCall_5_1_5_0(), "methodClosingMethod"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"methodClosingMethod",
                    	        		lv_methodClosingMethod_20_0, 
                    	        		"ID", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod4177); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_ENDTerminalRuleCall_5_1_6(), null); 
                        

                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMapMethod


    // $ANTLR start entryRuleField
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3116:1: entryRuleField returns [EObject current=null] : iv_ruleField= ruleField EOF ;
    public final EObject entryRuleField() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleField = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3117:2: (iv_ruleField= ruleField EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3118:2: iv_ruleField= ruleField EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFieldRule(), currentNode); 
            pushFollow(FOLLOW_ruleField_in_entryRuleField4214);
            iv_ruleField=ruleField();
            _fsp--;

             current =iv_ruleField; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleField4224); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleField


    // $ANTLR start ruleField
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3125:1: ruleField returns [EObject current=null] : ( RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) ) ;
    public final EObject ruleField() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_12_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_children_8_0 = null;

        EObject lv_children_9_0 = null;

        EObject lv_children_10_0 = null;

        EObject lv_children_11_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3130:6: ( ( RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3131:1: ( RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3131:1: ( RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3131:2: RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) )
            {
            match(input,RULE_FIELD_START_TAG,FOLLOW_RULE_FIELD_START_TAG_in_ruleField4258); 
             
                createLeafNode(grammarAccess.getFieldAccess().getFIELD_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3135:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3136:5: 
            {
             
                    temp=factory.create(grammarAccess.getFieldAccess().getFieldAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getFieldAccess().getFieldAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3146:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==RULE_ID) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3147:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3147:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3148:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleField4287);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3170:3: ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==RULE_XML_TAG_SINGLEEND) ) {
                alt44=1;
            }
            else if ( (LA44_0==RULE_XML_TAG_END) ) {
                alt44=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3170:3: ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) )", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3170:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleField4298); 
                     
                        createLeafNode(grammarAccess.getFieldAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:6: ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:6: ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:7: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:7: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:8: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )*
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:8: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3176:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3176:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3177:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleField4322); 

                    			createLeafNode(grammarAccess.getFieldAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3199:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )*
                    loop43:
                    do {
                        int alt43=8;
                        switch ( input.LA(1) ) {
                        case RULE_EXPRESSION_START_TAG:
                        case RULE_OPTION_START_TAG:
                            {
                            alt43=1;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt43=2;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt43=3;
                            }
                            break;
                        case RULE_MAP_METHOD_STARTTAG_START:
                            {
                            alt43=4;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt43=5;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt43=6;
                            }
                            break;
                        case RULE_BREAK_START_TAG:
                            {
                            alt43=7;
                            }
                            break;

                        }

                        switch (alt43) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3199:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3199:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3200:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3200:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3201:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleField4349);
                    	    lv_children_5_0=ruleExpressionOrOption();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_5_0, 
                    	    	        		"ExpressionOrOption", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3224:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3224:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3225:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3225:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3226:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenParamParserRuleCall_3_1_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleField4376);
                    	    lv_children_6_0=ruleParam();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_6_0, 
                    	    	        		"Param", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3249:6: ( (lv_children_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3249:6: ( (lv_children_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3250:1: (lv_children_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3250:1: (lv_children_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3251:3: lv_children_7_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenMapParserRuleCall_3_1_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleField4403);
                    	    lv_children_7_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_7_0, 
                    	    	        		"Map", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3274:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3274:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3275:1: (lv_children_8_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3275:1: (lv_children_8_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3276:3: lv_children_8_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenMapMethodParserRuleCall_3_1_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleField4430);
                    	    lv_children_8_0=ruleMapMethod();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_8_0, 
                    	    	        		"MapMethod", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3299:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3299:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3300:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3300:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3301:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenDebugTagParserRuleCall_3_1_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleField4457);
                    	    lv_children_9_0=ruleDebugTag();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_9_0, 
                    	    	        		"DebugTag", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3324:6: ( (lv_children_10_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3324:6: ( (lv_children_10_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3325:1: (lv_children_10_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3325:1: (lv_children_10_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3326:3: lv_children_10_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenCommentParserRuleCall_3_1_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleField4484);
                    	    lv_children_10_0=ruleComment();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_10_0, 
                    	    	        		"Comment", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3349:6: ( (lv_children_11_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3349:6: ( (lv_children_11_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3350:1: (lv_children_11_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3350:1: (lv_children_11_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3351:3: lv_children_11_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenBreakParserRuleCall_3_1_0_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleField4511);
                    	    lv_children_11_0=ruleBreak();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_11_0, 
                    	    	        		"Break", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop43;
                        }
                    } while (true);


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3373:5: ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3374:1: (lv_closedTag_12_0= RULE_FIELD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3374:1: (lv_closedTag_12_0= RULE_FIELD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3375:3: lv_closedTag_12_0= RULE_FIELD_END_TAG
                    {
                    lv_closedTag_12_0=(Token)input.LT(1);
                    match(input,RULE_FIELD_END_TAG,FOLLOW_RULE_FIELD_END_TAG_in_ruleField4531); 

                    			createLeafNode(grammarAccess.getFieldAccess().getClosedTagFIELD_END_TAGTerminalRuleCall_3_1_1_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"FIELD_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleField


    // $ANTLR start entryRuleDebugTag
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3405:1: entryRuleDebugTag returns [EObject current=null] : iv_ruleDebugTag= ruleDebugTag EOF ;
    public final EObject entryRuleDebugTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDebugTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3406:2: (iv_ruleDebugTag= ruleDebugTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3407:2: iv_ruleDebugTag= ruleDebugTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getDebugTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleDebugTag_in_entryRuleDebugTag4574);
            iv_ruleDebugTag=ruleDebugTag();
            _fsp--;

             current =iv_ruleDebugTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDebugTag4584); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleDebugTag


    // $ANTLR start ruleDebugTag
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3414:1: ruleDebugTag returns [EObject current=null] : ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) ) ;
    public final EObject ruleDebugTag() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_6_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_expression_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3419:6: ( ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3420:1: ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3420:1: ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3420:2: RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) )
            {
            match(input,RULE_DEBUG_START_TAG,FOLLOW_RULE_DEBUG_START_TAG_in_ruleDebugTag4618); 
             
                createLeafNode(grammarAccess.getDebugTagAccess().getDEBUG_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3424:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3425:5: 
            {
             
                    temp=factory.create(grammarAccess.getDebugTagAccess().getDebugTagAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getDebugTagAccess().getDebugTagAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3435:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==RULE_ID) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3436:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3436:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3437:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleDebugTag4647);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getDebugTagRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_2_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3459:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==RULE_XML_TAG_SINGLEEND) ) {
                alt47=1;
            }
            else if ( (LA47_0==RULE_XML_TAG_END) ) {
                alt47=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3459:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) )", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3459:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag4658); 
                     
                        createLeafNode(grammarAccess.getDebugTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3464:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3464:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3464:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3464:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3465:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3465:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3466:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag4681); 

                    			createLeafNode(grammarAccess.getDebugTagAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getDebugTagRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:2: ( (lv_expression_5_0= ruleTopLevel ) )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==RULE_ID||LA46_0==RULE_SQBRACKET_OPEN||(LA46_0>=RULE_TML_EXISTS && LA46_0<=RULE_DOLLAR)||(LA46_0>=RULE_NUMBER && LA46_0<=RULE_FALSE)||LA46_0==72||LA46_0==82||LA46_0==84) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3489:1: (lv_expression_5_0= ruleTopLevel )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3489:1: (lv_expression_5_0= ruleTopLevel )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3490:3: lv_expression_5_0= ruleTopLevel
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getExpressionTopLevelParserRuleCall_3_1_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleTopLevel_in_ruleDebugTag4707);
                            lv_expression_5_0=ruleTopLevel();
                            _fsp--;


                            	        if (current==null) {
                            	            current = factory.create(grammarAccess.getDebugTagRule().getType().getClassifier());
                            	            associateNodeWithAstElement(currentNode.getParent(), current);
                            	        }
                            	        try {
                            	       		set(
                            	       			current, 
                            	       			"expression",
                            	        		lv_expression_5_0, 
                            	        		"TopLevel", 
                            	        		currentNode);
                            	        } catch (ValueConverterException vce) {
                            				handleValueConverterException(vce);
                            	        }
                            	        currentNode = currentNode.getParent();
                            	    

                            }


                            }
                            break;

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3512:3: ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3513:1: (lv_closedTag_6_0= RULE_DEBUG_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3513:1: (lv_closedTag_6_0= RULE_DEBUG_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3514:3: lv_closedTag_6_0= RULE_DEBUG_END_TAG
                    {
                    lv_closedTag_6_0=(Token)input.LT(1);
                    match(input,RULE_DEBUG_END_TAG,FOLLOW_RULE_DEBUG_END_TAG_in_ruleDebugTag4725); 

                    			createLeafNode(grammarAccess.getDebugTagAccess().getClosedTagDEBUG_END_TAGTerminalRuleCall_3_1_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getDebugTagRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"DEBUG_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleDebugTag


    // $ANTLR start entryRuleExpressionOrOption
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3544:1: entryRuleExpressionOrOption returns [EObject current=null] : iv_ruleExpressionOrOption= ruleExpressionOrOption EOF ;
    public final EObject entryRuleExpressionOrOption() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionOrOption = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3545:2: (iv_ruleExpressionOrOption= ruleExpressionOrOption EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3546:2: iv_ruleExpressionOrOption= ruleExpressionOrOption EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionOrOptionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionOrOption_in_entryRuleExpressionOrOption4768);
            iv_ruleExpressionOrOption=ruleExpressionOrOption();
            _fsp--;

             current =iv_ruleExpressionOrOption; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionOrOption4778); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleExpressionOrOption


    // $ANTLR start ruleExpressionOrOption
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3553:1: ruleExpressionOrOption returns [EObject current=null] : ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) ) ;
    public final EObject ruleExpressionOrOption() throws RecognitionException {
        EObject current = null;

        EObject this_ExpressionTag_1 = null;

        EObject this_Option_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3558:6: ( ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3559:1: ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3559:1: ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) )
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==RULE_EXPRESSION_START_TAG) ) {
                alt48=1;
            }
            else if ( (LA48_0==RULE_OPTION_START_TAG) ) {
                alt48=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3559:1: ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) )", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3559:2: ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3559:2: ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3559:3: RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag
                    {
                    match(input,RULE_EXPRESSION_START_TAG,FOLLOW_RULE_EXPRESSION_START_TAG_in_ruleExpressionOrOption4813); 
                     
                        createLeafNode(grammarAccess.getExpressionOrOptionAccess().getEXPRESSION_START_TAGTerminalRuleCall_0_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionOrOptionAccess().getExpressionTagParserRuleCall_0_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleExpressionTag_in_ruleExpressionOrOption4834);
                    this_ExpressionTag_1=ruleExpressionTag();
                    _fsp--;

                     
                            current = this_ExpressionTag_1; 
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3573:6: ( RULE_OPTION_START_TAG this_Option_3= ruleOption )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3573:6: ( RULE_OPTION_START_TAG this_Option_3= ruleOption )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3573:7: RULE_OPTION_START_TAG this_Option_3= ruleOption
                    {
                    match(input,RULE_OPTION_START_TAG,FOLLOW_RULE_OPTION_START_TAG_in_ruleExpressionOrOption4850); 
                     
                        createLeafNode(grammarAccess.getExpressionOrOptionAccess().getOPTION_START_TAGTerminalRuleCall_1_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionOrOptionAccess().getOptionParserRuleCall_1_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleOption_in_ruleExpressionOrOption4871);
                    this_Option_3=ruleOption();
                    _fsp--;

                     
                            current = this_Option_3; 
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleExpressionOrOption


    // $ANTLR start entryRuleExpressionTag
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3594:1: entryRuleExpressionTag returns [EObject current=null] : iv_ruleExpressionTag= ruleExpressionTag EOF ;
    public final EObject entryRuleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3595:2: (iv_ruleExpressionTag= ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3596:2: iv_ruleExpressionTag= ruleExpressionTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag4907);
            iv_ruleExpressionTag=ruleExpressionTag();
            _fsp--;

             current =iv_ruleExpressionTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag4917); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleExpressionTag


    // $ANTLR start ruleExpressionTag
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3603:1: ruleExpressionTag returns [EObject current=null] : ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) ) ;
    public final EObject ruleExpressionTag() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_1_0 = null;

        EObject lv_expression_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3608:6: ( ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3609:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3609:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3609:2: () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3609:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3610:5: 
            {
             
                    temp=factory.create(grammarAccess.getExpressionTagAccess().getExpressionTagAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getExpressionTagAccess().getExpressionTagAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3620:2: ( (lv_attributes_1_0= rulePossibleExpression ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==RULE_ID) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3621:1: (lv_attributes_1_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3621:1: (lv_attributes_1_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3622:3: lv_attributes_1_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getAttributesPossibleExpressionParserRuleCall_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleExpressionTag4972);
            	    lv_attributes_1_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getExpressionTagRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_1_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3644:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==RULE_XML_TAG_SINGLEEND) ) {
                alt50=1;
            }
            else if ( (LA50_0==RULE_XML_TAG_END) ) {
                alt50=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3644:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) )", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3644:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag4983); 
                     
                        createLeafNode(grammarAccess.getExpressionTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3649:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3649:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3649:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3649:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3650:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3650:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3651:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag5006); 

                    			createLeafNode(grammarAccess.getExpressionTagAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_2_1_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getExpressionTagRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3673:2: ( (lv_expression_4_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3674:1: (lv_expression_4_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3674:1: (lv_expression_4_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3675:3: lv_expression_4_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getExpressionTopLevelParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleExpressionTag5032);
                    lv_expression_4_0=ruleTopLevel();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getExpressionTagRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"expression",
                    	        		lv_expression_4_0, 
                    	        		"TopLevel", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3697:2: ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3698:1: (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3698:1: (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3699:3: lv_closedTag_5_0= RULE_EXPRESSION_END_TAG
                    {
                    lv_closedTag_5_0=(Token)input.LT(1);
                    match(input,RULE_EXPRESSION_END_TAG,FOLLOW_RULE_EXPRESSION_END_TAG_in_ruleExpressionTag5049); 

                    			createLeafNode(grammarAccess.getExpressionTagAccess().getClosedTagEXPRESSION_END_TAGTerminalRuleCall_2_1_2_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getExpressionTagRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"EXPRESSION_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleExpressionTag


    // $ANTLR start entryRuleOption
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3729:1: entryRuleOption returns [EObject current=null] : iv_ruleOption= ruleOption EOF ;
    public final EObject entryRuleOption() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOption = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3730:2: (iv_ruleOption= ruleOption EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3731:2: iv_ruleOption= ruleOption EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOptionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOption_in_entryRuleOption5092);
            iv_ruleOption=ruleOption();
            _fsp--;

             current =iv_ruleOption; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOption5102); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleOption


    // $ANTLR start ruleOption
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3738:1: ruleOption returns [EObject current=null] : ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) ) ;
    public final EObject ruleOption() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3743:6: ( ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3744:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3744:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3744:2: () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3744:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3745:5: 
            {
             
                    temp=factory.create(grammarAccess.getOptionAccess().getOptionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getOptionAccess().getOptionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3755:2: ( (lv_attributes_1_0= rulePossibleExpression ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==RULE_ID) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3756:1: (lv_attributes_1_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3756:1: (lv_attributes_1_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3757:3: lv_attributes_1_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOptionAccess().getAttributesPossibleExpressionParserRuleCall_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleOption5157);
            	    lv_attributes_1_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getOptionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"attributes",
            	    	        		lv_attributes_1_0, 
            	    	        		"PossibleExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3779:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_XML_TAG_SINGLEEND) ) {
                alt52=1;
            }
            else if ( (LA52_0==RULE_XML_TAG_END) ) {
                alt52=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3779:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) )", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3779:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleOption5168); 
                     
                        createLeafNode(grammarAccess.getOptionAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3784:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3784:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3784:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3784:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3785:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3785:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3786:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleOption5191); 

                    			createLeafNode(grammarAccess.getOptionAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_2_1_0_0(), "splitTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getOptionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"splitTag",
                    	        		true, 
                    	        		"XML_TAG_END", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3808:2: ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3809:1: (lv_closedTag_4_0= RULE_OPTION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3809:1: (lv_closedTag_4_0= RULE_OPTION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3810:3: lv_closedTag_4_0= RULE_OPTION_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_OPTION_END_TAG,FOLLOW_RULE_OPTION_END_TAG_in_ruleOption5213); 

                    			createLeafNode(grammarAccess.getOptionAccess().getClosedTagOPTION_END_TAGTerminalRuleCall_2_1_1_0(), "closedTag"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getOptionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"closedTag",
                    	        		true, 
                    	        		"OPTION_END_TAG", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleOption


    // $ANTLR start entryRuleTopLevel
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3840:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3841:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3842:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTopLevelRule(), currentNode); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel5256);
            iv_ruleTopLevel=ruleTopLevel();
            _fsp--;

             current =iv_ruleTopLevel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel5266); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleTopLevel


    // $ANTLR start ruleTopLevel
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3849:1: ruleTopLevel returns [EObject current=null] : ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3854:6: ( ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3855:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3855:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3856:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3856:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3857:3: lv_toplevelExpression_0_0= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel5311);
            lv_toplevelExpression_0_0=ruleOrExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getTopLevelRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"toplevelExpression",
            	        		lv_toplevelExpression_0_0, 
            	        		"OrExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleTopLevel


    // $ANTLR start entryRulePathElement
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3887:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3888:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3889:2: iv_rulePathElement= rulePathElement EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathElementRule(), currentNode); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement5347);
            iv_rulePathElement=rulePathElement();
            _fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement5358); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRulePathElement


    // $ANTLR start rulePathElement
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3896:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_PARENT_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3901:6: ( (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3902:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3902:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            int alt53=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt53=1;
                }
                break;
            case RULE_DOT:
                {
                alt53=2;
                }
                break;
            case RULE_PARENT:
                {
                alt53=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("3902:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )", 53, 0, input);

                throw nvae;
            }

            switch (alt53) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3902:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement5398); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3911:2: kw= '.'
                    {
                    kw=(Token)input.LT(1);
                    match(input,RULE_DOT,FOLLOW_RULE_DOT_in_rulePathElement5422); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3917:10: this_PARENT_2= RULE_PARENT
                    {
                    this_PARENT_2=(Token)input.LT(1);
                    match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_rulePathElement5443); 

                    		current.merge(this_PARENT_2);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getPARENTTerminalRuleCall_2(), null); 
                        

                    }
                    break;

            }


            }

             resetLookahead(); 
            	    lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end rulePathElement


    // $ANTLR start entryRuleTmlExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3932:1: entryRuleTmlExpression returns [EObject current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final EObject entryRuleTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3933:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3934:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression5488);
            iv_ruleTmlExpression=ruleTmlExpression();
            _fsp--;

             current =iv_ruleTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression5498); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleTmlExpression


    // $ANTLR start ruleTmlExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3941:1: ruleTmlExpression returns [EObject current=null] : ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_1_0=null;
        Token lv_param_2_0=null;
        AntlrDatatypeRuleToken lv_elements_3_0 = null;

        AntlrDatatypeRuleToken lv_elements_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3946:6: ( ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3947:1: ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3947:1: ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3947:2: RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE
            {
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression5532); 
             
                createLeafNode(grammarAccess.getTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3951:1: ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_TML_SEPARATOR) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3952:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3952:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3953:3: lv_absolute_1_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_1_0=(Token)input.LT(1);
                    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5548); 

                    			createLeafNode(grammarAccess.getTmlExpressionAccess().getAbsoluteTML_SEPARATORTerminalRuleCall_1_0(), "absolute"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getTmlExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"absolute",
                    	        		true, 
                    	        		"TML_SEPARATOR", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3975:3: ( (lv_param_2_0= RULE_AT ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_AT) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3976:1: (lv_param_2_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3976:1: (lv_param_2_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3977:3: lv_param_2_0= RULE_AT
                    {
                    lv_param_2_0=(Token)input.LT(1);
                    match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleTmlExpression5571); 

                    			createLeafNode(grammarAccess.getTmlExpressionAccess().getParamATTerminalRuleCall_2_0(), "param"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getTmlExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"param",
                    	        		true, 
                    	        		"AT", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3999:3: ( (lv_elements_3_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4000:1: (lv_elements_3_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4000:1: (lv_elements_3_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4001:3: lv_elements_3_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression5598);
            lv_elements_3_0=rulePathElement();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getTmlExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"elements",
            	        		lv_elements_3_0, 
            	        		"PathElement", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4023:2: ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==RULE_TML_SEPARATOR) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4023:3: RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5608); 
            	     
            	        createLeafNode(grammarAccess.getTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_4_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4027:1: ( (lv_elements_5_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4028:1: (lv_elements_5_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4028:1: (lv_elements_5_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4029:3: lv_elements_5_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression5628);
            	    lv_elements_5_0=rulePathElement();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getTmlExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"elements",
            	    	        		lv_elements_5_0, 
            	    	        		"PathElement", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression5639); 
             
                createLeafNode(grammarAccess.getTmlExpressionAccess().getSQBRACKET_CLOSETerminalRuleCall_5(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleTmlExpression


    // $ANTLR start entryRuleExistsTmlExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4063:1: entryRuleExistsTmlExpression returns [EObject current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final EObject entryRuleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4064:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4065:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExistsTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression5674);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();
            _fsp--;

             current =iv_ruleExistsTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression5684); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleExistsTmlExpression


    // $ANTLR start ruleExistsTmlExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4072:1: ruleExistsTmlExpression returns [EObject current=null] : ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_2_0=null;
        Token lv_param_3_0=null;
        AntlrDatatypeRuleToken lv_elements_4_0 = null;

        AntlrDatatypeRuleToken lv_elements_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4077:6: ( ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4078:1: ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4078:1: ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4078:2: RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE
            {
            match(input,RULE_TML_EXISTS,FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression5718); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getTML_EXISTSTerminalRuleCall_0(), null); 
                
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression5726); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4086:1: ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==RULE_TML_SEPARATOR) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4087:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4087:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4088:3: lv_absolute_2_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_2_0=(Token)input.LT(1);
                    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5742); 

                    			createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteTML_SEPARATORTerminalRuleCall_2_0(), "absolute"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getExistsTmlExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"absolute",
                    	        		true, 
                    	        		"TML_SEPARATOR", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4110:3: ( (lv_param_3_0= RULE_AT ) )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==RULE_AT) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4111:1: (lv_param_3_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4111:1: (lv_param_3_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4112:3: lv_param_3_0= RULE_AT
                    {
                    lv_param_3_0=(Token)input.LT(1);
                    match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleExistsTmlExpression5765); 

                    			createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getParamATTerminalRuleCall_3_0(), "param"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getExistsTmlExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"param",
                    	        		true, 
                    	        		"AT", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4134:3: ( (lv_elements_4_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4135:1: (lv_elements_4_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4135:1: (lv_elements_4_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4136:3: lv_elements_4_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression5792);
            lv_elements_4_0=rulePathElement();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getExistsTmlExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"elements",
            	        		lv_elements_4_0, 
            	        		"PathElement", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4158:2: ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==RULE_TML_SEPARATOR) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4158:3: RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5802); 
            	     
            	        createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_5_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4162:1: ( (lv_elements_6_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4163:1: (lv_elements_6_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4163:1: (lv_elements_6_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4164:3: lv_elements_6_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_5_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression5822);
            	    lv_elements_6_0=rulePathElement();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getExistsTmlExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"elements",
            	    	        		lv_elements_6_0, 
            	    	        		"PathElement", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);

            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression5833); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_CLOSETerminalRuleCall_6(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleExistsTmlExpression


    // $ANTLR start entryRuleMapReferenceParams
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4198:1: entryRuleMapReferenceParams returns [EObject current=null] : iv_ruleMapReferenceParams= ruleMapReferenceParams EOF ;
    public final EObject entryRuleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapReferenceParams = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4199:2: (iv_ruleMapReferenceParams= ruleMapReferenceParams EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4200:2: iv_ruleMapReferenceParams= ruleMapReferenceParams EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapReferenceParamsRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams5868);
            iv_ruleMapReferenceParams=ruleMapReferenceParams();
            _fsp--;

             current =iv_ruleMapReferenceParams; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapReferenceParams5878); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMapReferenceParams


    // $ANTLR start ruleMapReferenceParams
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4207:1: ruleMapReferenceParams returns [EObject current=null] : ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' ) ;
    public final EObject ruleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        EObject lv_getterParams_1_0 = null;

        EObject lv_getterParams_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4212:6: ( ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4213:1: ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4213:1: ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4213:3: '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')'
            {
            match(input,72,FOLLOW_72_in_ruleMapReferenceParams5913); 

                    createLeafNode(grammarAccess.getMapReferenceParamsAccess().getLeftParenthesisKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4217:1: ( (lv_getterParams_1_0= ruleLiteral ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4218:1: (lv_getterParams_1_0= ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4218:1: (lv_getterParams_1_0= ruleLiteral )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4219:3: lv_getterParams_1_0= ruleLiteral
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams5934);
            lv_getterParams_1_0=ruleLiteral();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapReferenceParamsRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"getterParams",
            	        		lv_getterParams_1_0, 
            	        		"Literal", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4241:2: ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==73) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4241:4: ',' ( (lv_getterParams_3_0= ruleLiteral ) )
            	    {
            	    match(input,73,FOLLOW_73_in_ruleMapReferenceParams5945); 

            	            createLeafNode(grammarAccess.getMapReferenceParamsAccess().getCommaKeyword_2_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4245:1: ( (lv_getterParams_3_0= ruleLiteral ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4246:1: (lv_getterParams_3_0= ruleLiteral )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4246:1: (lv_getterParams_3_0= ruleLiteral )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4247:3: lv_getterParams_3_0= ruleLiteral
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams5966);
            	    lv_getterParams_3_0=ruleLiteral();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMapReferenceParamsRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"getterParams",
            	    	        		lv_getterParams_3_0, 
            	    	        		"Literal", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);

            match(input,74,FOLLOW_74_in_ruleMapReferenceParams5978); 

                    createLeafNode(grammarAccess.getMapReferenceParamsAccess().getRightParenthesisKeyword_3(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMapReferenceParams


    // $ANTLR start entryRuleMapGetReference
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4281:1: entryRuleMapGetReference returns [EObject current=null] : iv_ruleMapGetReference= ruleMapGetReference EOF ;
    public final EObject entryRuleMapGetReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapGetReference = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4282:2: (iv_ruleMapGetReference= ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4283:2: iv_ruleMapGetReference= ruleMapGetReference EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapGetReferenceRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference6014);
            iv_ruleMapGetReference=ruleMapGetReference();
            _fsp--;

             current =iv_ruleMapGetReference; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference6024); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMapGetReference


    // $ANTLR start ruleMapGetReference
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4290:1: ruleMapGetReference returns [EObject current=null] : ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) ;
    public final EObject ruleMapGetReference() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        Token lv_elements_1_0=null;
        Token lv_elements_3_0=null;
        EObject lv_referenceParams_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4295:6: ( ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:2: ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:2: ( (lv_operations_0_0= RULE_DOLLAR ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4297:1: (lv_operations_0_0= RULE_DOLLAR )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4297:1: (lv_operations_0_0= RULE_DOLLAR )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4298:3: lv_operations_0_0= RULE_DOLLAR
            {
            lv_operations_0_0=(Token)input.LT(1);
            match(input,RULE_DOLLAR,FOLLOW_RULE_DOLLAR_in_ruleMapGetReference6066); 

            			createLeafNode(grammarAccess.getMapGetReferenceAccess().getOperationsDOLLARTerminalRuleCall_0_0(), "operations"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapGetReferenceRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"operations",
            	        		lv_operations_0_0, 
            	        		"DOLLAR", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4320:2: ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==RULE_PARENT) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4320:3: ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4320:3: ( (lv_elements_1_0= RULE_PARENT ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4321:1: (lv_elements_1_0= RULE_PARENT )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4321:1: (lv_elements_1_0= RULE_PARENT )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4322:3: lv_elements_1_0= RULE_PARENT
            	    {
            	    lv_elements_1_0=(Token)input.LT(1);
            	    match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_ruleMapGetReference6089); 

            	    			createLeafNode(grammarAccess.getMapGetReferenceAccess().getElementsPARENTTerminalRuleCall_1_0_0(), "elements"); 
            	    		

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMapGetReferenceRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"elements",
            	    	        		lv_elements_1_0, 
            	    	        		"PARENT", 
            	    	        		lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference6103); 
            	     
            	        createLeafNode(grammarAccess.getMapGetReferenceAccess().getTML_SEPARATORTerminalRuleCall_1_1(), null); 
            	        

            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4348:3: ( (lv_elements_3_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4349:1: (lv_elements_3_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4349:1: (lv_elements_3_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4350:3: lv_elements_3_0= RULE_ID
            {
            lv_elements_3_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapGetReference6121); 

            			createLeafNode(grammarAccess.getMapGetReferenceAccess().getElementsIDTerminalRuleCall_2_0(), "elements"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapGetReferenceRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"elements",
            	        		lv_elements_3_0, 
            	        		"ID", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4372:2: ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==72) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4373:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4373:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4374:3: lv_referenceParams_4_0= ruleMapReferenceParams
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getMapGetReferenceAccess().getReferenceParamsMapReferenceParamsParserRuleCall_3_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference6147);
                    lv_referenceParams_4_0=ruleMapReferenceParams();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapGetReferenceRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"referenceParams",
                    	        		lv_referenceParams_4_0, 
                    	        		"MapReferenceParams", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMapGetReference


    // $ANTLR start entryRuleOrExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4404:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4405:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4406:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression6184);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression6194); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleOrExpression


    // $ANTLR start ruleOrExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4413:1: ruleOrExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4418:6: ( ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:2: ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:2: ( (lv_parameters_0_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4420:1: (lv_parameters_0_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4420:1: (lv_parameters_0_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4421:3: lv_parameters_0_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression6240);
            lv_parameters_0_0=ruleAndExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_0_0, 
            	        		"AndExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4443:2: ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==75) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4443:3: ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4443:3: ( (lv_operations_1_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4444:1: (lv_operations_1_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4444:1: (lv_operations_1_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4445:3: lv_operations_1_0= 'OR'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,75,FOLLOW_75_in_ruleOrExpression6259); 

            	            createLeafNode(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0(), "operations"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operations", lv_operations_1_0, "OR", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4464:2: ( (lv_parameters_2_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4465:1: (lv_parameters_2_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4465:1: (lv_parameters_2_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4466:3: lv_parameters_2_0= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression6293);
            	    lv_parameters_2_0=ruleAndExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_2_0, 
            	    	        		"AndExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleOrExpression


    // $ANTLR start entryRuleAndExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4496:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4497:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4498:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression6331);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression6341); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleAndExpression


    // $ANTLR start ruleAndExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4505:1: ruleAndExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4510:6: ( ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4511:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4511:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4511:2: ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4511:2: ( (lv_parameters_0_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4512:1: (lv_parameters_0_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4512:1: (lv_parameters_0_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4513:3: lv_parameters_0_0= ruleEqualityExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression6387);
            lv_parameters_0_0=ruleEqualityExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_0_0, 
            	        		"EqualityExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4535:2: ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==76) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4535:3: ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4535:3: ( (lv_operations_1_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4536:1: (lv_operations_1_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4536:1: (lv_operations_1_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4537:3: lv_operations_1_0= 'AND'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,76,FOLLOW_76_in_ruleAndExpression6406); 

            	            createLeafNode(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0(), "operations"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operations", lv_operations_1_0, "AND", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4556:2: ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4557:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4557:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4558:3: lv_parameters_2_0= ruleEqualityExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression6440);
            	    lv_parameters_2_0=ruleEqualityExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_2_0, 
            	    	        		"EqualityExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleAndExpression


    // $ANTLR start entryRuleEqualityExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4588:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4589:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4590:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEqualityExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression6478);
            iv_ruleEqualityExpression=ruleEqualityExpression();
            _fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression6488); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleEqualityExpression


    // $ANTLR start ruleEqualityExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4597:1: ruleEqualityExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4602:6: ( ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:2: ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:2: ( (lv_parameters_0_0= ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4604:1: (lv_parameters_0_0= ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4604:1: (lv_parameters_0_0= ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4605:3: lv_parameters_0_0= ruleRelationalExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6534);
            lv_parameters_0_0=ruleRelationalExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_0_0, 
            	        		"RelationalExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4627:2: ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            int alt65=3;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==77) ) {
                alt65=1;
            }
            else if ( (LA65_0==78) ) {
                alt65=2;
            }
            switch (alt65) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4627:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4627:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4627:4: ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4627:4: ( (lv_operations_1_0= '==' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4628:1: (lv_operations_1_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4628:1: (lv_operations_1_0= '==' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4629:3: lv_operations_1_0= '=='
                    {
                    lv_operations_1_0=(Token)input.LT(1);
                    match(input,77,FOLLOW_77_in_ruleEqualityExpression6554); 

                            createLeafNode(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0(), "operations"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "operations", lv_operations_1_0, "==", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4648:2: ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4649:1: (lv_parameters_2_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4649:1: (lv_parameters_2_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4650:3: lv_parameters_2_0= ruleRelationalExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6588);
                    lv_parameters_2_0=ruleRelationalExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_2_0, 
                    	        		"RelationalExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4673:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4673:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4673:7: ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4673:7: ( (lv_operations_3_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4674:1: (lv_operations_3_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4674:1: (lv_operations_3_0= '!=' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4675:3: lv_operations_3_0= '!='
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,78,FOLLOW_78_in_ruleEqualityExpression6614); 

                            createLeafNode(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0(), "operations"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "operations", lv_operations_3_0, "!=", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4694:2: ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4695:1: (lv_parameters_4_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4695:1: (lv_parameters_4_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4696:3: lv_parameters_4_0= ruleRelationalExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6648);
                    lv_parameters_4_0=ruleRelationalExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_4_0, 
                    	        		"RelationalExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleEqualityExpression


    // $ANTLR start entryRuleRelationalExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4726:1: entryRuleRelationalExpression returns [EObject current=null] : iv_ruleRelationalExpression= ruleRelationalExpression EOF ;
    public final EObject entryRuleRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationalExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4727:2: (iv_ruleRelationalExpression= ruleRelationalExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4728:2: iv_ruleRelationalExpression= ruleRelationalExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRelationalExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression6687);
            iv_ruleRelationalExpression=ruleRelationalExpression();
            _fsp--;

             current =iv_ruleRelationalExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRelationalExpression6697); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleRelationalExpression


    // $ANTLR start ruleRelationalExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4735:1: ruleRelationalExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) ;
    public final EObject ruleRelationalExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        Token lv_operations_4_0=null;
        Token lv_operations_6_0=null;
        Token lv_operations_8_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;

        EObject lv_parameters_7_0 = null;

        EObject lv_parameters_9_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4740:6: ( ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4741:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4741:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4741:2: () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4741:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4742:5: 
            {
             
                    temp=factory.create(grammarAccess.getRelationalExpressionAccess().getExpressionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getRelationalExpressionAccess().getExpressionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4752:2: ( (lv_parameters_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4753:1: (lv_parameters_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4753:1: (lv_parameters_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4754:3: lv_parameters_1_0= ruleAdditiveExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6752);
            lv_parameters_1_0=ruleAdditiveExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_1_0, 
            	        		"AdditiveExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4776:2: ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            int alt66=5;
            switch ( input.LA(1) ) {
                case RULE_XML_LT:
                    {
                    alt66=1;
                    }
                    break;
                case RULE_XML_GT:
                    {
                    alt66=2;
                    }
                    break;
                case RULE_XML_LTEQ:
                    {
                    alt66=3;
                    }
                    break;
                case RULE_XML_GTEQ:
                    {
                    alt66=4;
                    }
                    break;
            }

            switch (alt66) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4776:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4776:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4776:4: ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4776:4: ( (lv_operations_2_0= RULE_XML_LT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4777:1: (lv_operations_2_0= RULE_XML_LT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4777:1: (lv_operations_2_0= RULE_XML_LT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4778:3: lv_operations_2_0= RULE_XML_LT
                    {
                    lv_operations_2_0=(Token)input.LT(1);
                    match(input,RULE_XML_LT,FOLLOW_RULE_XML_LT_in_ruleRelationalExpression6771); 

                    			createLeafNode(grammarAccess.getRelationalExpressionAccess().getOperationsXML_LTTerminalRuleCall_2_0_0_0(), "operations"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operations",
                    	        		lv_operations_2_0, 
                    	        		"XML_LT", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4800:2: ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4801:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4801:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4802:3: lv_parameters_3_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6797);
                    lv_parameters_3_0=ruleAdditiveExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_3_0, 
                    	        		"AdditiveExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4825:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4825:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4825:7: ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4825:7: ( (lv_operations_4_0= RULE_XML_GT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4826:1: (lv_operations_4_0= RULE_XML_GT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4826:1: (lv_operations_4_0= RULE_XML_GT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4827:3: lv_operations_4_0= RULE_XML_GT
                    {
                    lv_operations_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_GT,FOLLOW_RULE_XML_GT_in_ruleRelationalExpression6822); 

                    			createLeafNode(grammarAccess.getRelationalExpressionAccess().getOperationsXML_GTTerminalRuleCall_2_1_0_0(), "operations"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operations",
                    	        		lv_operations_4_0, 
                    	        		"XML_GT", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4849:2: ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4850:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4850:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4851:3: lv_parameters_5_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6848);
                    lv_parameters_5_0=ruleAdditiveExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_5_0, 
                    	        		"AdditiveExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4874:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4874:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4874:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4874:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4875:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4875:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4876:3: lv_operations_6_0= RULE_XML_LTEQ
                    {
                    lv_operations_6_0=(Token)input.LT(1);
                    match(input,RULE_XML_LTEQ,FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression6873); 

                    			createLeafNode(grammarAccess.getRelationalExpressionAccess().getOperationsXML_LTEQTerminalRuleCall_2_2_0_0(), "operations"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operations",
                    	        		lv_operations_6_0, 
                    	        		"XML_LTEQ", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4898:2: ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4899:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4899:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4900:3: lv_parameters_7_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_2_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6899);
                    lv_parameters_7_0=ruleAdditiveExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_7_0, 
                    	        		"AdditiveExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4923:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4923:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4923:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4923:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4924:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4924:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4925:3: lv_operations_8_0= RULE_XML_GTEQ
                    {
                    lv_operations_8_0=(Token)input.LT(1);
                    match(input,RULE_XML_GTEQ,FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression6924); 

                    			createLeafNode(grammarAccess.getRelationalExpressionAccess().getOperationsXML_GTEQTerminalRuleCall_2_3_0_0(), "operations"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operations",
                    	        		lv_operations_8_0, 
                    	        		"XML_GTEQ", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4947:2: ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4948:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4948:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4949:3: lv_parameters_9_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_3_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6950);
                    lv_parameters_9_0=ruleAdditiveExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getRelationalExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_9_0, 
                    	        		"AdditiveExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleRelationalExpression


    // $ANTLR start entryRuleAdditiveExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4979:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4980:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4981:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAdditiveExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression6989);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();
            _fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression6999); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleAdditiveExpression


    // $ANTLR start ruleAdditiveExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4988:1: ruleAdditiveExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4993:6: ( ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4994:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4994:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4994:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4994:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4995:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4995:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4996:3: lv_parameters_0_0= ruleMultiplicativeExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7045);
            lv_parameters_0_0=ruleMultiplicativeExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_0_0, 
            	        		"MultiplicativeExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5018:2: ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            loop67:
            do {
                int alt67=3;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==79) ) {
                    alt67=1;
                }
                else if ( (LA67_0==80) ) {
                    alt67=2;
                }


                switch (alt67) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5018:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5018:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5018:5: '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,79,FOLLOW_79_in_ruleAdditiveExpression7057); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5022:1: ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5023:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5023:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5024:3: lv_parameters_2_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7078);
            	    lv_parameters_2_0=ruleMultiplicativeExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_2_0, 
            	    	        		"MultiplicativeExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5047:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5047:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5047:8: '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,80,FOLLOW_80_in_ruleAdditiveExpression7096); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5051:1: ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5052:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5052:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5053:3: lv_parameters_4_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7117);
            	    lv_parameters_4_0=ruleMultiplicativeExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_4_0, 
            	    	        		"MultiplicativeExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleAdditiveExpression


    // $ANTLR start entryRuleMultiplicativeExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5083:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5084:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5085:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression7156);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();
            _fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression7166); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleMultiplicativeExpression


    // $ANTLR start ruleMultiplicativeExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5092:1: ruleMultiplicativeExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5097:6: ( ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:2: ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:2: ( (lv_parameters_0_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5099:1: (lv_parameters_0_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5099:1: (lv_parameters_0_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5100:3: lv_parameters_0_0= ruleUnaryExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7212);
            lv_parameters_0_0=ruleUnaryExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_0_0, 
            	        		"UnaryExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5122:2: ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            loop68:
            do {
                int alt68=3;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==81) ) {
                    alt68=1;
                }
                else if ( (LA68_0==RULE_TML_SEPARATOR) ) {
                    alt68=2;
                }


                switch (alt68) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5122:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5122:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5122:4: ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5122:4: ( (lv_operations_1_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5123:1: (lv_operations_1_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5123:1: (lv_operations_1_0= '*' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5124:3: lv_operations_1_0= '*'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,81,FOLLOW_81_in_ruleMultiplicativeExpression7232); 

            	            createLeafNode(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0(), "operations"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operations", lv_operations_1_0, "*", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5143:2: ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5144:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5144:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5145:3: lv_parameters_2_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7266);
            	    lv_parameters_2_0=ruleUnaryExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_2_0, 
            	    	        		"UnaryExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5168:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5168:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5168:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5168:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5169:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5169:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5170:3: lv_operations_3_0= RULE_TML_SEPARATOR
            	    {
            	    lv_operations_3_0=(Token)input.LT(1);
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression7291); 

            	    			createLeafNode(grammarAccess.getMultiplicativeExpressionAccess().getOperationsTML_SEPARATORTerminalRuleCall_1_1_0_0(), "operations"); 
            	    		

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"operations",
            	    	        		lv_operations_3_0, 
            	    	        		"TML_SEPARATOR", 
            	    	        		lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5192:2: ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5193:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5193:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5194:3: lv_parameters_4_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7317);
            	    lv_parameters_4_0=ruleUnaryExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_4_0, 
            	    	        		"UnaryExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleMultiplicativeExpression


    // $ANTLR start entryRuleUnaryExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5224:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5225:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5226:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getUnaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression7356);
            iv_ruleUnaryExpression=ruleUnaryExpression();
            _fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression7366); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleUnaryExpression


    // $ANTLR start ruleUnaryExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5233:1: ruleUnaryExpression returns [EObject current=null] : ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        EObject lv_parameters_1_0 = null;

        EObject this_PrimaryExpression_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5238:6: ( ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5239:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5239:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==82) ) {
                alt69=1;
            }
            else if ( (LA69_0==RULE_ID||LA69_0==RULE_SQBRACKET_OPEN||(LA69_0>=RULE_TML_EXISTS && LA69_0<=RULE_DOLLAR)||(LA69_0>=RULE_NUMBER && LA69_0<=RULE_FALSE)||LA69_0==72||LA69_0==84) ) {
                alt69=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5239:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )", 69, 0, input);

                throw nvae;
            }
            switch (alt69) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5239:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5239:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5239:3: ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5239:3: ( (lv_operations_0_0= '!' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5240:1: (lv_operations_0_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5240:1: (lv_operations_0_0= '!' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5241:3: lv_operations_0_0= '!'
                    {
                    lv_operations_0_0=(Token)input.LT(1);
                    match(input,82,FOLLOW_82_in_ruleUnaryExpression7410); 

                            createLeafNode(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0(), "operations"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getUnaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "operations", lv_operations_0_0, "!", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5260:2: ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5261:1: (lv_parameters_1_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5261:1: (lv_parameters_1_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5262:3: lv_parameters_1_0= rulePrimaryExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7444);
                    lv_parameters_1_0=rulePrimaryExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getUnaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_1_0, 
                    	        		"PrimaryExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5286:5: this_PrimaryExpression_2= rulePrimaryExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7473);
                    this_PrimaryExpression_2=rulePrimaryExpression();
                    _fsp--;

                     
                            current = this_PrimaryExpression_2; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleUnaryExpression


    // $ANTLR start entryRulePrimaryExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5302:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5303:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5304:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPrimaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression7508);
            iv_rulePrimaryExpression=rulePrimaryExpression();
            _fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression7518); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRulePrimaryExpression


    // $ANTLR start rulePrimaryExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5311:1: rulePrimaryExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5316:6: ( ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5317:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5317:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==RULE_ID||LA70_0==RULE_SQBRACKET_OPEN||(LA70_0>=RULE_TML_EXISTS && LA70_0<=RULE_DOLLAR)||(LA70_0>=RULE_NUMBER && LA70_0<=RULE_FALSE)||LA70_0==84) ) {
                alt70=1;
            }
            else if ( (LA70_0==72) ) {
                alt70=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5317:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5317:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5317:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5318:1: (lv_parameters_0_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5318:1: (lv_parameters_0_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5319:3: lv_parameters_0_0= ruleLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression7564);
                    lv_parameters_0_0=ruleLiteral();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPrimaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_0_0, 
                    	        		"Literal", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5342:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5342:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5342:8: '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')'
                    {
                    match(input,72,FOLLOW_72_in_rulePrimaryExpression7581); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5346:1: ( (lv_parameters_2_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5347:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5347:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5348:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression7602);
                    lv_parameters_2_0=ruleOrExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPrimaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_2_0, 
                    	        		"OrExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    match(input,74,FOLLOW_74_in_rulePrimaryExpression7612); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getRightParenthesisKeyword_1_2(), null); 
                        

                    }


                    }
                    break;

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end rulePrimaryExpression


    // $ANTLR start entryRuleFunctionName
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5382:1: entryRuleFunctionName returns [String current=null] : iv_ruleFunctionName= ruleFunctionName EOF ;
    public final String entryRuleFunctionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFunctionName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5383:2: (iv_ruleFunctionName= ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5384:2: iv_ruleFunctionName= ruleFunctionName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName7650);
            iv_ruleFunctionName=ruleFunctionName();
            _fsp--;

             current =iv_ruleFunctionName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName7661); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleFunctionName


    // $ANTLR start ruleFunctionName
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5391:1: ruleFunctionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFunctionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5396:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5397:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName7700); 

            		current.merge(this_ID_0);
                
             
                createLeafNode(grammarAccess.getFunctionNameAccess().getIDTerminalRuleCall(), null); 
                

            }

             resetLookahead(); 
            	    lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleFunctionName


    // $ANTLR start entryRuleFunctionCall
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5412:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5413:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5414:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionCallRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall7744);
            iv_ruleFunctionCall=ruleFunctionCall();
            _fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall7754); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleFunctionCall


    // $ANTLR start ruleFunctionCall
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5421:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5426:6: ( ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5427:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5427:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5427:2: ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')'
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5427:2: ( (lv_name_0_0= ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5428:1: (lv_name_0_0= ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5428:1: (lv_name_0_0= ruleFunctionName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5429:3: lv_name_0_0= ruleFunctionName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleFunctionName_in_ruleFunctionCall7800);
            lv_name_0_0=ruleFunctionName();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFunctionCallRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"name",
            	        		lv_name_0_0, 
            	        		"FunctionName", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,72,FOLLOW_72_in_ruleFunctionCall7810); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5455:1: ( (lv_parameters_2_0= ruleOrExpression ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==RULE_ID||LA71_0==RULE_SQBRACKET_OPEN||(LA71_0>=RULE_TML_EXISTS && LA71_0<=RULE_DOLLAR)||(LA71_0>=RULE_NUMBER && LA71_0<=RULE_FALSE)||LA71_0==72||LA71_0==82||LA71_0==84) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5456:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5456:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5457:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall7831);
                    lv_parameters_2_0=ruleOrExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getFunctionCallRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_2_0, 
                    	        		"OrExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5479:3: ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==73) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5479:5: ',' ( (lv_parameters_4_0= ruleOrExpression ) )
            	    {
            	    match(input,73,FOLLOW_73_in_ruleFunctionCall7843); 

            	            createLeafNode(grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5483:1: ( (lv_parameters_4_0= ruleOrExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5484:1: (lv_parameters_4_0= ruleOrExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5484:1: (lv_parameters_4_0= ruleOrExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5485:3: lv_parameters_4_0= ruleOrExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall7864);
            	    lv_parameters_4_0=ruleOrExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getFunctionCallRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_4_0, 
            	    	        		"OrExpression", 
            	    	        		currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);

            match(input,74,FOLLOW_74_in_ruleFunctionCall7876); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_4(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleFunctionCall


    // $ANTLR start entryRuleDateLiteral
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5519:1: entryRuleDateLiteral returns [EObject current=null] : iv_ruleDateLiteral= ruleDateLiteral EOF ;
    public final EObject entryRuleDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDateLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5520:2: (iv_ruleDateLiteral= ruleDateLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5521:2: iv_ruleDateLiteral= ruleDateLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getDateLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral7912);
            iv_ruleDateLiteral=ruleDateLiteral();
            _fsp--;

             current =iv_ruleDateLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDateLiteral7922); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleDateLiteral


    // $ANTLR start ruleDateLiteral
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5528:1: ruleDateLiteral returns [EObject current=null] : ( () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER ) ;
    public final EObject ruleDateLiteral() throws RecognitionException {
        EObject current = null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5533:6: ( ( () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5534:1: ( () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5534:1: ( () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5534:2: () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5534:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5535:5: 
            {
             
                    temp=factory.create(grammarAccess.getDateLiteralAccess().getExpressionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getDateLiteralAccess().getExpressionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral7965); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_1(), null); 
                
            match(input,83,FOLLOW_83_in_ruleDateLiteral7974); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_2(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral7983); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_3(), null); 
                
            match(input,83,FOLLOW_83_in_ruleDateLiteral7992); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_4(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8001); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_5(), null); 
                
            match(input,83,FOLLOW_83_in_ruleDateLiteral8010); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_6(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8019); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_7(), null); 
                
            match(input,83,FOLLOW_83_in_ruleDateLiteral8028); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_8(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8037); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_9(), null); 
                
            match(input,83,FOLLOW_83_in_ruleDateLiteral8046); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_10(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8055); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_11(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleDateLiteral


    // $ANTLR start entryRuleLiteral
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5597:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5598:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5599:2: iv_ruleLiteral= ruleLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral8090);
            iv_ruleLiteral=ruleLiteral();
            _fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral8100); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleLiteral


    // $ANTLR start ruleLiteral
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5606:1: ruleLiteral returns [EObject current=null] : ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) ) ;
    public final EObject ruleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_valueString_2_0=null;
        Token lv_operations_3_0=null;
        Token lv_valueString_5_0=null;
        Token lv_expressionType_10_0=null;
        Token lv_elements_15_0=null;
        Token lv_elements_16_0=null;
        Token lv_elements_17_0=null;
        Token lv_elements_18_0=null;
        EObject lv_parameters_7_0 = null;

        EObject lv_parameters_9_0 = null;

        EObject lv_parameters_11_0 = null;

        EObject lv_parameters_13_0 = null;

        EObject lv_parameters_19_0 = null;

        EObject lv_parameters_20_0 = null;

        EObject lv_parameters_21_0 = null;

        EObject lv_parameters_22_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5611:6: ( ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5612:1: ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5612:1: ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )
            int alt75=13;
            switch ( input.LA(1) ) {
            case RULE_NUMBER:
                {
                int LA75_1 = input.LA(2);

                if ( (LA75_1==83) ) {
                    alt75=13;
                }
                else if ( (LA75_1==EOF||LA75_1==RULE_SEMICOLONQUOTE||LA75_1==RULE_CHECK_END_TAG||LA75_1==RULE_DEBUG_END_TAG||LA75_1==RULE_EXPRESSION_END_TAG||LA75_1==RULE_TML_SEPARATOR||(LA75_1>=RULE_XML_LT && LA75_1<=RULE_XML_GTEQ)||(LA75_1>=73 && LA75_1<=81)||LA75_1==85) ) {
                    alt75=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("5612:1: ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )", 75, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt75=2;
                }
                break;
            case RULE_FORALL:
                {
                alt75=3;
                }
                break;
            case RULE_ID:
                {
                alt75=4;
                }
                break;
            case 84:
                {
                alt75=5;
                }
                break;
            case RULE_NULL:
                {
                alt75=6;
                }
                break;
            case RULE_TODAY:
                {
                alt75=7;
                }
                break;
            case RULE_TRUE:
                {
                alt75=8;
                }
                break;
            case RULE_FALSE:
                {
                alt75=9;
                }
                break;
            case RULE_SQBRACKET_OPEN:
                {
                alt75=10;
                }
                break;
            case RULE_TML_EXISTS:
                {
                alt75=11;
                }
                break;
            case RULE_DOLLAR:
                {
                alt75=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("5612:1: ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5612:2: ( () RULE_NUMBER )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5612:2: ( () RULE_NUMBER )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5612:3: () RULE_NUMBER
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5612:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5613:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_0_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_0_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleLiteral8144); 
                     
                        createLeafNode(grammarAccess.getLiteralAccess().getNUMBERTerminalRuleCall_0_1(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5628:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5628:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5629:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5629:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5630:3: lv_valueString_2_0= RULE_LITERALSTRING
                    {
                    lv_valueString_2_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8167); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_0(), "valueString"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"valueString",
                    	        		lv_valueString_2_0, 
                    	        		"LITERALSTRING", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5653:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5653:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5653:7: ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5653:7: ( (lv_operations_3_0= RULE_FORALL ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5654:1: (lv_operations_3_0= RULE_FORALL )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5654:1: (lv_operations_3_0= RULE_FORALL )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5655:3: lv_operations_3_0= RULE_FORALL
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,RULE_FORALL,FOLLOW_RULE_FORALL_in_ruleLiteral8196); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getOperationsFORALLTerminalRuleCall_2_0_0(), "operations"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operations",
                    	        		lv_operations_3_0, 
                    	        		"FORALL", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,72,FOLLOW_72_in_ruleLiteral8211); 

                            createLeafNode(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5681:1: ( (lv_valueString_5_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5682:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5682:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5683:3: lv_valueString_5_0= RULE_LITERALSTRING
                    {
                    lv_valueString_5_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8228); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_2_0(), "valueString"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"valueString",
                    	        		lv_valueString_5_0, 
                    	        		"LITERALSTRING", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,73,FOLLOW_73_in_ruleLiteral8243); 

                            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_2_3(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5709:1: ( (lv_parameters_7_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5710:1: (lv_parameters_7_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5710:1: (lv_parameters_7_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5711:3: lv_parameters_7_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8264);
                    lv_parameters_7_0=ruleOrExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_7_0, 
                    	        		"OrExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    match(input,74,FOLLOW_74_in_ruleLiteral8274); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5(), null); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5738:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5738:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5739:1: (lv_parameters_9_0= ruleFunctionCall )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5739:1: (lv_parameters_9_0= ruleFunctionCall )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5740:3: lv_parameters_9_0= ruleFunctionCall
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersFunctionCallParserRuleCall_3_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral8302);
                    lv_parameters_9_0=ruleFunctionCall();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_9_0, 
                    	        		"FunctionCall", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5763:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5763:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5763:7: ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5763:7: ( (lv_expressionType_10_0= '{' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5764:1: (lv_expressionType_10_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5764:1: (lv_expressionType_10_0= '{' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5765:3: lv_expressionType_10_0= '{'
                    {
                    lv_expressionType_10_0=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_ruleLiteral8327); 

                            createLeafNode(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0(), "expressionType"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "expressionType", lv_expressionType_10_0, "{", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5784:2: ( (lv_parameters_11_0= ruleOrExpression ) )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==RULE_ID||LA73_0==RULE_SQBRACKET_OPEN||(LA73_0>=RULE_TML_EXISTS && LA73_0<=RULE_DOLLAR)||(LA73_0>=RULE_NUMBER && LA73_0<=RULE_FALSE)||LA73_0==72||LA73_0==82||LA73_0==84) ) {
                        alt73=1;
                    }
                    switch (alt73) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5785:1: (lv_parameters_11_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5785:1: (lv_parameters_11_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5786:3: lv_parameters_11_0= ruleOrExpression
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8361);
                            lv_parameters_11_0=ruleOrExpression();
                            _fsp--;


                            	        if (current==null) {
                            	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                            	            associateNodeWithAstElement(currentNode.getParent(), current);
                            	        }
                            	        try {
                            	       		add(
                            	       			current, 
                            	       			"parameters",
                            	        		lv_parameters_11_0, 
                            	        		"OrExpression", 
                            	        		currentNode);
                            	        } catch (ValueConverterException vce) {
                            				handleValueConverterException(vce);
                            	        }
                            	        currentNode = currentNode.getParent();
                            	    

                            }


                            }
                            break;

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5808:3: ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )*
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==73) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5808:5: ',' ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    {
                    	    match(input,73,FOLLOW_73_in_ruleLiteral8373); 

                    	            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0(), null); 
                    	        
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5812:1: ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5813:1: (lv_parameters_13_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5813:1: (lv_parameters_13_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5814:3: lv_parameters_13_0= ruleOrExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8394);
                    	    lv_parameters_13_0=ruleOrExpression();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"parameters",
                    	    	        		lv_parameters_13_0, 
                    	    	        		"OrExpression", 
                    	    	        		currentNode);
                    	    	        } catch (ValueConverterException vce) {
                    	    				handleValueConverterException(vce);
                    	    	        }
                    	    	        currentNode = currentNode.getParent();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop74;
                        }
                    } while (true);

                    match(input,85,FOLLOW_85_in_ruleLiteral8406); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3(), null); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5841:6: ( (lv_elements_15_0= RULE_NULL ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5841:6: ( (lv_elements_15_0= RULE_NULL ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5842:1: (lv_elements_15_0= RULE_NULL )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5842:1: (lv_elements_15_0= RULE_NULL )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5843:3: lv_elements_15_0= RULE_NULL
                    {
                    lv_elements_15_0=(Token)input.LT(1);
                    match(input,RULE_NULL,FOLLOW_RULE_NULL_in_ruleLiteral8430); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getElementsNULLTerminalRuleCall_5_0(), "elements"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"elements",
                    	        		lv_elements_15_0, 
                    	        		"NULL", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 7 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5866:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5866:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5867:1: (lv_elements_16_0= RULE_TODAY )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5867:1: (lv_elements_16_0= RULE_TODAY )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5868:3: lv_elements_16_0= RULE_TODAY
                    {
                    lv_elements_16_0=(Token)input.LT(1);
                    match(input,RULE_TODAY,FOLLOW_RULE_TODAY_in_ruleLiteral8458); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getElementsTODAYTerminalRuleCall_6_0(), "elements"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"elements",
                    	        		lv_elements_16_0, 
                    	        		"TODAY", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5891:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5891:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5892:1: (lv_elements_17_0= RULE_TRUE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5892:1: (lv_elements_17_0= RULE_TRUE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5893:3: lv_elements_17_0= RULE_TRUE
                    {
                    lv_elements_17_0=(Token)input.LT(1);
                    match(input,RULE_TRUE,FOLLOW_RULE_TRUE_in_ruleLiteral8486); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getElementsTRUETerminalRuleCall_7_0(), "elements"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"elements",
                    	        		lv_elements_17_0, 
                    	        		"TRUE", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5916:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5916:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5917:1: (lv_elements_18_0= RULE_FALSE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5917:1: (lv_elements_18_0= RULE_FALSE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5918:3: lv_elements_18_0= RULE_FALSE
                    {
                    lv_elements_18_0=(Token)input.LT(1);
                    match(input,RULE_FALSE,FOLLOW_RULE_FALSE_in_ruleLiteral8514); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getElementsFALSETerminalRuleCall_8_0(), "elements"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"elements",
                    	        		lv_elements_18_0, 
                    	        		"FALSE", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 10 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5941:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5941:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5942:1: (lv_parameters_19_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5942:1: (lv_parameters_19_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5943:3: lv_parameters_19_0= ruleTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral8546);
                    lv_parameters_19_0=ruleTmlExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_19_0, 
                    	        		"TmlExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }
                    break;
                case 11 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5966:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5966:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5967:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5967:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5968:3: lv_parameters_20_0= ruleExistsTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral8573);
                    lv_parameters_20_0=ruleExistsTmlExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_20_0, 
                    	        		"ExistsTmlExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }
                    break;
                case 12 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5991:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5991:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5992:1: (lv_parameters_21_0= ruleMapGetReference )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5992:1: (lv_parameters_21_0= ruleMapGetReference )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5993:3: lv_parameters_21_0= ruleMapGetReference
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapGetReference_in_ruleLiteral8600);
                    lv_parameters_21_0=ruleMapGetReference();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_21_0, 
                    	        		"MapGetReference", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }
                    break;
                case 13 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6016:6: ( (lv_parameters_22_0= ruleDateLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6016:6: ( (lv_parameters_22_0= ruleDateLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6017:1: (lv_parameters_22_0= ruleDateLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6017:1: (lv_parameters_22_0= ruleDateLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6018:3: lv_parameters_22_0= ruleDateLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersDateLiteralParserRuleCall_12_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleDateLiteral_in_ruleLiteral8627);
                    lv_parameters_22_0=ruleDateLiteral();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_22_0, 
                    	        		"DateLiteral", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }


                    }
                    break;

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleLiteral


 

    public static final BitSet FOLLOW_ruleTml_in_entryRuleTml75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTml85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XMLHEAD_in_ruleTml129 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_START_in_ruleTml139 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleTml159 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleTml171 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleTml192 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleMap_in_ruleTml219 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleParam_in_ruleTml246 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleMethods_in_ruleTml273 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleTml300 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleInclude_in_ruleTml327 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleValidations_in_ruleTml354 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleComment_in_ruleTml381 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_END_in_ruleTml392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttributeName_in_entryRuleAttributeName444 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAttributeName455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAttributeName494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression538 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePossibleExpression548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePossibleExpression591 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_rulePossibleExpression606 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ruleAttributeName_in_rulePossibleExpression629 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_rulePossibleExpression639 = new BitSet(new long[]{0x0000000000003400L});
    public static final BitSet FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression650 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleTopLevel_in_rulePossibleExpression670 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethods_in_entryRuleMethods786 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethods796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_METHODS_START_TAG_in_ruleMethods830 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethods857 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_ruleMethod_in_ruleMethods883 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_RULE_METHODS_END_TAG_in_ruleMethods901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethod_in_entryRuleMethod958 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethod968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_METHOD_START_TAG_in_ruleMethod1002 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMethod1031 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethod1051 = new BitSet(new long[]{0x0000000200020000L});
    public static final BitSet FOLLOW_ruleRequired_in_ruleMethod1077 = new BitSet(new long[]{0x0000000200020000L});
    public static final BitSet FOLLOW_RULE_METHOD_END_TAG_in_ruleMethod1095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod1116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValidations_in_entryRuleValidations1152 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValidations1162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALIDATIONS_START_TAG_in_ruleValidations1196 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleValidations1223 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_ruleCheck_in_ruleValidations1249 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_RULE_VALIDATIONS_END_TAG_in_ruleValidations1267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleValidations1288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCheck_in_entryRuleCheck1324 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCheck1334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_CHECK_START_TAG_in_ruleCheck1368 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleCheck1397 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleCheck1417 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleCheck1443 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RULE_CHECK_END_TAG_in_ruleCheck1460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleCheck1481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComment_in_entryRuleComment1517 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComment1527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_COMMENT_START_TAG_in_ruleComment1561 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleComment1590 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleComment1610 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RULE_COMMENT_END_TAG_in_ruleComment1632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleComment1653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBreak_in_entryRuleBreak1689 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBreak1699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BREAK_START_TAG_in_ruleBreak1733 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleBreak1762 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleBreak1782 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_RULE_BREAK_END_TAG_in_ruleBreak1804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleBreak1825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInclude_in_entryRuleInclude1861 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInclude1871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INCLUDE_START_TAG_in_ruleInclude1905 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleInclude1934 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleInclude1954 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RULE_INCLUDE_END_TAG_in_ruleInclude1976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleInclude1997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage2033 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage2043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MESSAGE_START_TAG_in_ruleMessage2077 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMessage2106 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMessage2126 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMessage2153 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMessage2180 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMessage2207 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMessage2234 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMessage2261 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMessage2288 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMessage2315 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMessage2342 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMessage2369 = new BitSet(new long[]{0x00000AA871400000L});
    public static final BitSet FOLLOW_RULE_MESSAGE_END_TAG_in_ruleMessage2388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage2409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_entryRuleMap2445 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMap2455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MAPSTARTKEYWORD_in_ruleMap2489 = new BitSet(new long[]{0x0000000080000340L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMap2508 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMap2528 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap2549 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap2578 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap2590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap2613 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMap2640 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMap2667 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMap2694 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMap2721 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMap2748 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMap2775 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMap2802 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMap2829 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMap2856 = new BitSet(new long[]{0x00000AA951400000L});
    public static final BitSet FOLLOW_RULE_MAPENDKEYWORD_in_ruleMap2868 = new BitSet(new long[]{0x0000000080000040L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMap2877 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMap2897 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap2916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapId_in_entryRuleMapId2961 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapId2972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapId3011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequired_in_entryRuleRequired3055 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequired3065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_REQUIRED_START_TAG_in_ruleRequired3099 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleRequired3128 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleRequired3139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleRequired3162 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_RULE_REQUIRED_END_TAG_in_ruleRequired3184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty3227 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty3237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PROPERTY_START_TAG_in_ruleProperty3271 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleProperty3300 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty3311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleProperty3334 = new BitSet(new long[]{0x0000601040000000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleProperty3361 = new BitSet(new long[]{0x0000601040000000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleProperty3388 = new BitSet(new long[]{0x0000601040000000L});
    public static final BitSet FOLLOW_RULE_PROPERTY_END_TAG_in_ruleProperty3407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParam_in_entryRuleParam3450 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParam3460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARAM_START_TAG_in_ruleParam3494 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleParam3523 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam3534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleParam3557 = new BitSet(new long[]{0x0000604040000000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleParam3584 = new BitSet(new long[]{0x0000604040000000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleParam3611 = new BitSet(new long[]{0x0000604040000000L});
    public static final BitSet FOLLOW_RULE_PARAM_END_TAG_in_ruleParam3630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapMethod_in_entryRuleMapMethod3673 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapMethod3683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MAP_METHOD_STARTTAG_START_in_ruleMapMethod3717 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod3733 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMapMethod3747 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ruleAttributeName_in_ruleMapMethod3767 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMapMethod3788 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod3799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod3822 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleMapMethod3849 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMapMethod3876 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMapMethod3903 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMapMethod3930 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMapMethod3957 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMapMethod3984 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMapMethod4011 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMapMethod4038 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMapMethod4065 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMapMethod4092 = new BitSet(new long[]{0x00006BA851400000L});
    public static final BitSet FOLLOW_RULE_MAP_METHOD_ENDTAG_START_in_ruleMapMethod4111 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod4133 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMapMethod4147 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod4163 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod4177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleField_in_entryRuleField4214 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleField4224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FIELD_START_TAG_in_ruleField4258 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleField4287 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleField4298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleField4322 = new BitSet(new long[]{0x00006CA041400000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleField4349 = new BitSet(new long[]{0x00006CA041400000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleField4376 = new BitSet(new long[]{0x00006CA041400000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleField4403 = new BitSet(new long[]{0x00006CA041400000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleField4430 = new BitSet(new long[]{0x00006CA041400000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleField4457 = new BitSet(new long[]{0x00006CA041400000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleField4484 = new BitSet(new long[]{0x00006CA041400000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleField4511 = new BitSet(new long[]{0x00006CA041400000L});
    public static final BitSet FOLLOW_RULE_FIELD_END_TAG_in_ruleField4531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDebugTag_in_entryRuleDebugTag4574 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDebugTag4584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DEBUG_START_TAG_in_ruleDebugTag4618 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleDebugTag4647 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag4658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag4681 = new BitSet(new long[]{0xF0C4100000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleDebugTag4707 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_RULE_DEBUG_END_TAG_in_ruleDebugTag4725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_entryRuleExpressionOrOption4768 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionOrOption4778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EXPRESSION_START_TAG_in_ruleExpressionOrOption4813 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleExpressionOrOption4834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_OPTION_START_TAG_in_ruleExpressionOrOption4850 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_ruleOption_in_ruleExpressionOrOption4871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag4907 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag4917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleExpressionTag4972 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag4983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag5006 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleExpressionTag5032 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_RULE_EXPRESSION_END_TAG_in_ruleExpressionTag5049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOption_in_entryRuleOption5092 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOption5102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleOption5157 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleOption5168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleOption5191 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_RULE_OPTION_END_TAG_in_ruleOption5213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel5256 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel5266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel5311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement5347 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement5358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement5398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOT_in_rulePathElement5422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARENT_in_rulePathElement5443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression5488 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression5498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression5532 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5548 = new BitSet(new long[]{0x0012000080000200L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleTmlExpression5571 = new BitSet(new long[]{0x0002000080000200L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression5598 = new BitSet(new long[]{0x0028000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5608 = new BitSet(new long[]{0x0002000080000200L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression5628 = new BitSet(new long[]{0x0028000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression5639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression5674 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression5684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression5718 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression5726 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5742 = new BitSet(new long[]{0x0012000080000200L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleExistsTmlExpression5765 = new BitSet(new long[]{0x0002000080000200L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression5792 = new BitSet(new long[]{0x0028000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5802 = new BitSet(new long[]{0x0002000080000200L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression5822 = new BitSet(new long[]{0x0028000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression5833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams5868 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapReferenceParams5878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_ruleMapReferenceParams5913 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000100007L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams5934 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_73_in_ruleMapReferenceParams5945 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000100007L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams5966 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_74_in_ruleMapReferenceParams5978 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference6014 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference6024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOLLAR_in_ruleMapGetReference6066 = new BitSet(new long[]{0x0002000000000200L});
    public static final BitSet FOLLOW_RULE_PARENT_in_ruleMapGetReference6089 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference6103 = new BitSet(new long[]{0x0002000000000200L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapGetReference6121 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference6147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression6184 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression6194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression6240 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_ruleOrExpression6259 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression6293 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression6331 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression6341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression6387 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ruleAndExpression6406 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression6440 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression6478 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression6488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6534 = new BitSet(new long[]{0x0000000000000002L,0x0000000000006000L});
    public static final BitSet FOLLOW_77_in_ruleEqualityExpression6554 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_ruleEqualityExpression6614 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression6687 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRelationalExpression6697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6752 = new BitSet(new long[]{0x0F00000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LT_in_ruleRelationalExpression6771 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GT_in_ruleRelationalExpression6822 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression6873 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression6924 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression6989 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression6999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7045 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_79_in_ruleAdditiveExpression7057 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7078 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_80_in_ruleAdditiveExpression7096 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7117 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression7156 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression7166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7212 = new BitSet(new long[]{0x0008000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_ruleMultiplicativeExpression7232 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7266 = new BitSet(new long[]{0x0008000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression7291 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7317 = new BitSet(new long[]{0x0008000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression7356 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression7366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_ruleUnaryExpression7410 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000100107L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression7508 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression7518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression7564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_rulePrimaryExpression7581 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression7602 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_rulePrimaryExpression7612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName7650 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName7661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName7700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall7744 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall7754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_ruleFunctionCall7800 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_ruleFunctionCall7810 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140707L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall7831 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_73_in_ruleFunctionCall7843 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall7864 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_74_in_ruleFunctionCall7876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral7912 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDateLiteral7922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral7965 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral7974 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral7983 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral7992 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8001 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral8010 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8019 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral8028 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8037 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral8046 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral8090 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral8100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleLiteral8144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FORALL_in_ruleLiteral8196 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_ruleLiteral8211 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8228 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_ruleLiteral8243 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8264 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_ruleLiteral8274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral8302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_ruleLiteral8327 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000340307L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8361 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200200L});
    public static final BitSet FOLLOW_73_in_ruleLiteral8373 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8394 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200200L});
    public static final BitSet FOLLOW_85_in_ruleLiteral8406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NULL_in_ruleLiteral8430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TODAY_in_ruleLiteral8458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TRUE_in_ruleLiteral8486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FALSE_in_ruleLiteral8514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral8546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral8573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_ruleLiteral8600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_ruleLiteral8627 = new BitSet(new long[]{0x0000000000000002L});

}