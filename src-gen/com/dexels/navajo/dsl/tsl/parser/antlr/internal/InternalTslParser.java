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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_NAVASCRIPT_START", "RULE_XML_TAG_END", "RULE_NAVASCRIPT_END", "RULE_XML_TAG_SINGLEEND", "RULE_ID", "RULE_QUOTEQ", "RULE_SEMICOLONQUOTE", "RULE_ATTRIBUTESTRING", "RULE_EMPTYSTRING", "RULE_METHODS_START_TAG", "RULE_METHODS_END_TAG", "RULE_METHOD_START_TAG", "RULE_METHOD_END_TAG", "RULE_VALIDATIONS_START_TAG", "RULE_VALIDATIONS_END_TAG", "RULE_CHECK_START_TAG", "RULE_CHECK_END_TAG", "RULE_COMMENT_START_TAG", "RULE_COMMENT_END_TAG", "RULE_BREAK_START_TAG", "RULE_BREAK_END_TAG", "RULE_INCLUDE_START_TAG", "RULE_INCLUDE_END_TAG", "RULE_MESSAGE_START_TAG", "RULE_MESSAGE_END_TAG", "RULE_MAPSTARTKEYWORD", "RULE_DOT", "RULE_MAPENDKEYWORD", "RULE_REQUIRED_START_TAG", "RULE_REQUIRED_END_TAG", "RULE_PROPERTY_START_TAG", "RULE_PROPERTY_END_TAG", "RULE_PARAM_START_TAG", "RULE_PARAM_END_TAG", "RULE_MAP_METHOD_STARTTAG_START", "RULE_MAP_METHOD_ENDTAG_START", "RULE_FIELD_START_TAG", "RULE_FIELD_END_TAG", "RULE_DEBUG_START_TAG", "RULE_DEBUG_END_TAG", "RULE_EXPRESSION_START_TAG", "RULE_OPTION_START_TAG", "RULE_EXPRESSION_END_TAG", "RULE_OPTION_END_TAG", "RULE_PARENT", "RULE_SQBRACKET_OPEN", "RULE_TML_SEPARATOR", "RULE_AT", "RULE_SQBRACKET_CLOSE", "RULE_TML_EXISTS", "RULE_DOLLAR", "RULE_XML_LT", "RULE_XML_GT", "RULE_XML_LTEQ", "RULE_XML_GTEQ", "RULE_NUMBER", "RULE_LITERALSTRING", "RULE_FORALL", "RULE_NULL", "RULE_TODAY", "RULE_TRUE", "RULE_FALSE", "RULE_XMLHEAD", "RULE_XMLCOMMENT", "RULE_XML_START_ENDTAG", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "':'", "'='", "'('", "','", "')'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'#'", "'{'", "'}'"
    };
    public static final int RULE_OPTION_END_TAG=47;
    public static final int RULE_CHECK_START_TAG=19;
    public static final int RULE_NAVASCRIPT_END=6;
    public static final int RULE_ID=8;
    public static final int RULE_XMLCOMMENT=67;
    public static final int RULE_COMMENT_END_TAG=22;
    public static final int RULE_PARENT=48;
    public static final int RULE_SQBRACKET_OPEN=49;
    public static final int RULE_COMMENT_START_TAG=21;
    public static final int RULE_QUOTEQ=9;
    public static final int RULE_EXPRESSION_END_TAG=46;
    public static final int RULE_XMLHEAD=66;
    public static final int RULE_REQUIRED_START_TAG=32;
    public static final int RULE_METHODS_END_TAG=14;
    public static final int RULE_LITERALSTRING=60;
    public static final int EOF=-1;
    public static final int RULE_BREAK_END_TAG=24;
    public static final int RULE_FORALL=61;
    public static final int RULE_FALSE=65;
    public static final int RULE_DOT=30;
    public static final int RULE_OPTION_START_TAG=45;
    public static final int RULE_EMPTYSTRING=12;
    public static final int RULE_NUMBER=59;
    public static final int RULE_TODAY=63;
    public static final int RULE_METHOD_START_TAG=15;
    public static final int RULE_XML_LTEQ=57;
    public static final int RULE_FIELD_START_TAG=40;
    public static final int RULE_METHOD_END_TAG=16;
    public static final int RULE_CHECK_END_TAG=20;
    public static final int RULE_INCLUDE_START_TAG=25;
    public static final int RULE_REQUIRED_END_TAG=33;
    public static final int RULE_INCLUDE_END_TAG=26;
    public static final int RULE_MAPENDKEYWORD=31;
    public static final int RULE_DEBUG_START_TAG=42;
    public static final int RULE_FIELD_END_TAG=41;
    public static final int RULE_XML_TAG_SINGLEEND=7;
    public static final int RULE_PROPERTY_START_TAG=34;
    public static final int RULE_ATTRIBUTESTRING=11;
    public static final int RULE_XML_TAG_END=5;
    public static final int RULE_MESSAGE_START_TAG=27;
    public static final int RULE_XML_LT=55;
    public static final int RULE_MAP_METHOD_STARTTAG_START=38;
    public static final int RULE_MESSAGE_END_TAG=28;
    public static final int RULE_XML_GTEQ=58;
    public static final int RULE_TML_SEPARATOR=50;
    public static final int RULE_SL_COMMENT=70;
    public static final int RULE_NULL=62;
    public static final int RULE_ML_COMMENT=69;
    public static final int RULE_TRUE=64;
    public static final int RULE_PROPERTY_END_TAG=35;
    public static final int RULE_DOLLAR=54;
    public static final int RULE_EXPRESSION_START_TAG=44;
    public static final int RULE_TML_EXISTS=53;
    public static final int RULE_VALIDATIONS_START_TAG=17;
    public static final int RULE_BREAK_START_TAG=23;
    public static final int RULE_NAVASCRIPT_START=4;
    public static final int RULE_SQBRACKET_CLOSE=52;
    public static final int RULE_DEBUG_END_TAG=43;
    public static final int RULE_METHODS_START_TAG=13;
    public static final int RULE_MAPSTARTKEYWORD=29;
    public static final int RULE_SEMICOLONQUOTE=10;
    public static final int RULE_XML_START_ENDTAG=68;
    public static final int RULE_VALIDATIONS_END_TAG=18;
    public static final int RULE_WS=71;
    public static final int RULE_XML_GT=56;
    public static final int RULE_MAP_METHOD_ENDTAG_START=39;
    public static final int RULE_PARAM_END_TAG=37;
    public static final int RULE_AT=51;
    public static final int RULE_PARAM_START_TAG=36;

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:86:1: ruleTml returns [EObject current=null] : ( RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleTml() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_methods_7_0 = null;

        EObject lv_children_8_0 = null;

        EObject lv_children_9_0 = null;

        EObject lv_children_10_0 = null;

        EObject lv_children_11_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:91:6: ( ( RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:2: RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_NAVASCRIPT_START,FOLLOW_RULE_NAVASCRIPT_START_in_ruleTml119); 
             
                createLeafNode(grammarAccess.getTmlAccess().getNAVASCRIPT_STARTTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:96:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:97:5: 
            {
             
                    temp=factory.create(grammarAccess.getTmlAccess().getTmlAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getTmlAccess().getTmlAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:107:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_ID) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:108:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:108:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:109:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleTml148);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
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
            	    break loop1;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_XML_TAG_END) ) {
                alt3=1;
            }
            else if ( (LA3_0==RULE_XML_TAG_SINGLEEND) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("131:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:5: RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* RULE_NAVASCRIPT_END
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleTml160); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )*
                    loop2:
                    do {
                        int alt2=9;
                        switch ( input.LA(1) ) {
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt2=1;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt2=2;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt2=3;
                            }
                            break;
                        case RULE_METHODS_START_TAG:
                            {
                            alt2=4;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt2=5;
                            }
                            break;
                        case RULE_INCLUDE_START_TAG:
                            {
                            alt2=6;
                            }
                            break;
                        case RULE_VALIDATIONS_START_TAG:
                            {
                            alt2=7;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt2=8;
                            }
                            break;

                        }

                        switch (alt2) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:2: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:2: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:136:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:136:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:137:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleTml181);
                    	    lv_children_4_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:160:6: ( (lv_children_5_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:160:6: ( (lv_children_5_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:161:1: (lv_children_5_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:161:1: (lv_children_5_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:162:3: lv_children_5_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenMapParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleTml208);
                    	    lv_children_5_0=ruleMap();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:185:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:185:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:186:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:186:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:187:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenParamParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleTml235);
                    	    lv_children_6_0=ruleParam();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:210:6: ( (lv_methods_7_0= ruleMethods ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:210:6: ( (lv_methods_7_0= ruleMethods ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:211:1: (lv_methods_7_0= ruleMethods )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:211:1: (lv_methods_7_0= ruleMethods )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:212:3: lv_methods_7_0= ruleMethods
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getMethodsMethodsParserRuleCall_3_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethods_in_ruleTml262);
                    	    lv_methods_7_0=ruleMethods();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"methods",
                    	    	        		lv_methods_7_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:235:6: ( (lv_children_8_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:235:6: ( (lv_children_8_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:236:1: (lv_children_8_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:236:1: (lv_children_8_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:237:3: lv_children_8_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenDebugTagParserRuleCall_3_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleTml289);
                    	    lv_children_8_0=ruleDebugTag();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_8_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:260:6: ( (lv_children_9_0= ruleInclude ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:260:6: ( (lv_children_9_0= ruleInclude ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:261:1: (lv_children_9_0= ruleInclude )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:261:1: (lv_children_9_0= ruleInclude )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:262:3: lv_children_9_0= ruleInclude
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenIncludeParserRuleCall_3_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleInclude_in_ruleTml316);
                    	    lv_children_9_0=ruleInclude();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:285:6: ( (lv_children_10_0= ruleValidations ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:285:6: ( (lv_children_10_0= ruleValidations ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:286:1: (lv_children_10_0= ruleValidations )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:286:1: (lv_children_10_0= ruleValidations )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:287:3: lv_children_10_0= ruleValidations
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenValidationsParserRuleCall_3_0_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleValidations_in_ruleTml343);
                    	    lv_children_10_0=ruleValidations();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:310:6: ( (lv_children_11_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:310:6: ( (lv_children_11_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:311:1: (lv_children_11_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:311:1: (lv_children_11_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:312:3: lv_children_11_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenCommentParserRuleCall_3_0_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleTml370);
                    	    lv_children_11_0=ruleComment();
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
                    	    break loop2;
                        }
                    } while (true);

                    match(input,RULE_NAVASCRIPT_END,FOLLOW_RULE_NAVASCRIPT_END_in_ruleTml381); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getNAVASCRIPT_ENDTerminalRuleCall_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:339:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml396); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:351:1: entryRuleAttributeName returns [String current=null] : iv_ruleAttributeName= ruleAttributeName EOF ;
    public final String entryRuleAttributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAttributeName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:352:2: (iv_ruleAttributeName= ruleAttributeName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:353:2: iv_ruleAttributeName= ruleAttributeName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAttributeNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleAttributeName_in_entryRuleAttributeName433);
            iv_ruleAttributeName=ruleAttributeName();
            _fsp--;

             current =iv_ruleAttributeName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAttributeName444); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:360:1: ruleAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleAttributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:365:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:366:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAttributeName483); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:381:1: entryRulePossibleExpression returns [EObject current=null] : iv_rulePossibleExpression= rulePossibleExpression EOF ;
    public final EObject entryRulePossibleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePossibleExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:382:2: (iv_rulePossibleExpression= rulePossibleExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:383:2: iv_rulePossibleExpression= rulePossibleExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPossibleExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression527);
            iv_rulePossibleExpression=rulePossibleExpression();
            _fsp--;

             current =iv_rulePossibleExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePossibleExpression537); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:390:1: rulePossibleExpression returns [EObject current=null] : ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) ) ;
    public final EObject rulePossibleExpression() throws RecognitionException {
        EObject current = null;

        Token lv_namespace_0_0=null;
        Token lv_value_7_0=null;
        Token lv_value_8_0=null;
        AntlrDatatypeRuleToken lv_key_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:395:6: ( ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:396:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:396:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:396:2: ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:396:2: ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_ID) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==72) ) {
                    alt4=1;
                }
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:396:3: ( (lv_namespace_0_0= RULE_ID ) ) ':'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:396:3: ( (lv_namespace_0_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:397:1: (lv_namespace_0_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:397:1: (lv_namespace_0_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:398:3: lv_namespace_0_0= RULE_ID
                    {
                    lv_namespace_0_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePossibleExpression580); 

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

                    match(input,72,FOLLOW_72_in_rulePossibleExpression595); 

                            createLeafNode(grammarAccess.getPossibleExpressionAccess().getColonKeyword_0_1(), null); 
                        

                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:424:3: ( (lv_key_2_0= ruleAttributeName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:425:1: (lv_key_2_0= ruleAttributeName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:425:1: (lv_key_2_0= ruleAttributeName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:426:3: lv_key_2_0= ruleAttributeName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getKeyAttributeNameParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAttributeName_in_rulePossibleExpression618);
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

            match(input,73,FOLLOW_73_in_rulePossibleExpression628); 

                    createLeafNode(grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:452:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case RULE_QUOTEQ:
                {
                alt5=1;
                }
                break;
            case RULE_ATTRIBUTESTRING:
                {
                alt5=2;
                }
                break;
            case RULE_EMPTYSTRING:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("452:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) )", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:452:2: ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:452:2: ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:452:3: RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE
                    {
                    match(input,RULE_QUOTEQ,FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression639); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getQUOTEQTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:456:1: ( (lv_expressionValue_5_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:457:1: (lv_expressionValue_5_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:457:1: (lv_expressionValue_5_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:458:3: lv_expressionValue_5_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_3_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_rulePossibleExpression659);
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

                    match(input,RULE_SEMICOLONQUOTE,FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression668); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getSEMICOLONQUOTETerminalRuleCall_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:485:6: ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:485:6: ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:486:1: (lv_value_7_0= RULE_ATTRIBUTESTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:486:1: (lv_value_7_0= RULE_ATTRIBUTESTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:487:3: lv_value_7_0= RULE_ATTRIBUTESTRING
                    {
                    lv_value_7_0=(Token)input.LT(1);
                    match(input,RULE_ATTRIBUTESTRING,FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression691); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:510:6: ( (lv_value_8_0= RULE_EMPTYSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:510:6: ( (lv_value_8_0= RULE_EMPTYSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:511:1: (lv_value_8_0= RULE_EMPTYSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:511:1: (lv_value_8_0= RULE_EMPTYSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:512:3: lv_value_8_0= RULE_EMPTYSTRING
                    {
                    lv_value_8_0=(Token)input.LT(1);
                    match(input,RULE_EMPTYSTRING,FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression719); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:542:1: entryRuleMethods returns [EObject current=null] : iv_ruleMethods= ruleMethods EOF ;
    public final EObject entryRuleMethods() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethods = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:543:2: (iv_ruleMethods= ruleMethods EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:544:2: iv_ruleMethods= ruleMethods EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMethodsRule(), currentNode); 
            pushFollow(FOLLOW_ruleMethods_in_entryRuleMethods761);
            iv_ruleMethods=ruleMethods();
            _fsp--;

             current =iv_ruleMethods; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethods771); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:551:1: ruleMethods returns [EObject current=null] : ( RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethods() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_2_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_method_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:556:6: ( ( RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:557:1: ( RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:557:1: ( RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:557:2: RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_METHODS_START_TAG,FOLLOW_RULE_METHODS_START_TAG_in_ruleMethods805); 
             
                createLeafNode(grammarAccess.getMethodsAccess().getMETHODS_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:561:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:562:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:572:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_XML_TAG_END) ) {
                alt7=1;
            }
            else if ( (LA7_0==RULE_XML_TAG_SINGLEEND) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("572:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:572:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:572:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:572:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:572:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:573:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:573:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:574:3: lv_splitTag_2_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_2_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethods832); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:596:2: ( (lv_method_3_0= ruleMethod ) )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==RULE_METHOD_START_TAG) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:597:1: (lv_method_3_0= ruleMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:597:1: (lv_method_3_0= ruleMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:598:3: lv_method_3_0= ruleMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMethodsAccess().getMethodMethodParserRuleCall_2_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethod_in_ruleMethods858);
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
                    	    break loop6;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:620:3: ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:621:1: (lv_closedTag_4_0= RULE_METHODS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:621:1: (lv_closedTag_4_0= RULE_METHODS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:622:3: lv_closedTag_4_0= RULE_METHODS_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_METHODS_END_TAG,FOLLOW_RULE_METHODS_END_TAG_in_ruleMethods876); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:645:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods897); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:657:1: entryRuleMethod returns [EObject current=null] : iv_ruleMethod= ruleMethod EOF ;
    public final EObject entryRuleMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:658:2: (iv_ruleMethod= ruleMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:659:2: iv_ruleMethod= ruleMethod EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMethodRule(), currentNode); 
            pushFollow(FOLLOW_ruleMethod_in_entryRuleMethod933);
            iv_ruleMethod=ruleMethod();
            _fsp--;

             current =iv_ruleMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethod943); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:666:1: ruleMethod returns [EObject current=null] : ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethod() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:671:6: ( ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:672:1: ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:672:1: ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:672:2: RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_METHOD_START_TAG,FOLLOW_RULE_METHOD_START_TAG_in_ruleMethod977); 
             
                createLeafNode(grammarAccess.getMethodAccess().getMETHOD_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:676:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:677:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:687:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ID) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:688:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:688:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:689:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMethodAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMethod1006);
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
            	    break loop8;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_XML_TAG_END) ) {
                alt10=1;
            }
            else if ( (LA10_0==RULE_XML_TAG_SINGLEEND) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("711:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:712:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:712:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:713:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethod1026); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:735:2: ( (lv_children_4_0= ruleRequired ) )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==RULE_REQUIRED_START_TAG) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:736:1: (lv_children_4_0= ruleRequired )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:736:1: (lv_children_4_0= ruleRequired )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:737:3: lv_children_4_0= ruleRequired
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMethodAccess().getChildrenRequiredParserRuleCall_3_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRequired_in_ruleMethod1052);
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
                    	    break loop9;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:759:3: ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:760:1: (lv_closedTag_5_0= RULE_METHOD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:760:1: (lv_closedTag_5_0= RULE_METHOD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:761:3: lv_closedTag_5_0= RULE_METHOD_END_TAG
                    {
                    lv_closedTag_5_0=(Token)input.LT(1);
                    match(input,RULE_METHOD_END_TAG,FOLLOW_RULE_METHOD_END_TAG_in_ruleMethod1070); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:784:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod1091); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:796:1: entryRuleValidations returns [EObject current=null] : iv_ruleValidations= ruleValidations EOF ;
    public final EObject entryRuleValidations() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValidations = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:797:2: (iv_ruleValidations= ruleValidations EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:798:2: iv_ruleValidations= ruleValidations EOF
            {
             currentNode = createCompositeNode(grammarAccess.getValidationsRule(), currentNode); 
            pushFollow(FOLLOW_ruleValidations_in_entryRuleValidations1127);
            iv_ruleValidations=ruleValidations();
            _fsp--;

             current =iv_ruleValidations; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleValidations1137); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:805:1: ruleValidations returns [EObject current=null] : ( RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleValidations() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_2_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_children_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:810:6: ( ( RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:811:1: ( RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:811:1: ( RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:811:2: RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_VALIDATIONS_START_TAG,FOLLOW_RULE_VALIDATIONS_START_TAG_in_ruleValidations1171); 
             
                createLeafNode(grammarAccess.getValidationsAccess().getVALIDATIONS_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:815:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:816:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:826:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_XML_TAG_END) ) {
                alt12=1;
            }
            else if ( (LA12_0==RULE_XML_TAG_SINGLEEND) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("826:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:826:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:826:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:826:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:826:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:827:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:827:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:828:3: lv_splitTag_2_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_2_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleValidations1198); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:2: ( (lv_children_3_0= ruleCheck ) )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==RULE_CHECK_START_TAG) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:851:1: (lv_children_3_0= ruleCheck )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:851:1: (lv_children_3_0= ruleCheck )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:852:3: lv_children_3_0= ruleCheck
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getValidationsAccess().getChildrenCheckParserRuleCall_2_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleCheck_in_ruleValidations1224);
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
                    	    break loop11;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:874:3: ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:875:1: (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:875:1: (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:876:3: lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_VALIDATIONS_END_TAG,FOLLOW_RULE_VALIDATIONS_END_TAG_in_ruleValidations1242); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:899:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleValidations1263); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:911:1: entryRuleCheck returns [EObject current=null] : iv_ruleCheck= ruleCheck EOF ;
    public final EObject entryRuleCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheck = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:912:2: (iv_ruleCheck= ruleCheck EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:913:2: iv_ruleCheck= ruleCheck EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCheckRule(), currentNode); 
            pushFollow(FOLLOW_ruleCheck_in_entryRuleCheck1299);
            iv_ruleCheck=ruleCheck();
            _fsp--;

             current =iv_ruleCheck; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCheck1309); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:920:1: ruleCheck returns [EObject current=null] : ( RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleCheck() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_expression_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:925:6: ( ( RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:926:1: ( RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:926:1: ( RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:926:2: RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_CHECK_START_TAG,FOLLOW_RULE_CHECK_START_TAG_in_ruleCheck1343); 
             
                createLeafNode(grammarAccess.getCheckAccess().getCHECK_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:930:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:931:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:941:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_ID) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:942:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:942:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:943:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getCheckAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleCheck1372);
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
            	    break loop13;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:965:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_XML_TAG_END) ) {
                alt14=1;
            }
            else if ( (LA14_0==RULE_XML_TAG_SINGLEEND) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("965:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:965:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:965:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:965:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:965:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:966:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:966:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:967:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleCheck1392); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:989:2: ( (lv_expression_4_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:990:1: (lv_expression_4_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:990:1: (lv_expression_4_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:991:3: lv_expression_4_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getCheckAccess().getExpressionTopLevelParserRuleCall_3_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleCheck1418);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1013:2: ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1014:1: (lv_closedTag_5_0= RULE_CHECK_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1014:1: (lv_closedTag_5_0= RULE_CHECK_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1015:3: lv_closedTag_5_0= RULE_CHECK_END_TAG
                    {
                    lv_closedTag_5_0=(Token)input.LT(1);
                    match(input,RULE_CHECK_END_TAG,FOLLOW_RULE_CHECK_END_TAG_in_ruleCheck1435); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1038:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleCheck1456); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1050:1: entryRuleComment returns [EObject current=null] : iv_ruleComment= ruleComment EOF ;
    public final EObject entryRuleComment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComment = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1051:2: (iv_ruleComment= ruleComment EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1052:2: iv_ruleComment= ruleComment EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCommentRule(), currentNode); 
            pushFollow(FOLLOW_ruleComment_in_entryRuleComment1492);
            iv_ruleComment=ruleComment();
            _fsp--;

             current =iv_ruleComment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComment1502); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1059:1: ruleComment returns [EObject current=null] : ( RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleComment() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1064:6: ( ( RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1065:1: ( RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1065:1: ( RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1065:2: RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_COMMENT_START_TAG,FOLLOW_RULE_COMMENT_START_TAG_in_ruleComment1536); 
             
                createLeafNode(grammarAccess.getCommentAccess().getCOMMENT_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1069:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1070:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1080:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_ID) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1081:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1081:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1082:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getCommentAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleComment1565);
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
            	    break loop15;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1104:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_XML_TAG_END) ) {
                alt16=1;
            }
            else if ( (LA16_0==RULE_XML_TAG_SINGLEEND) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1104:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1104:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1104:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1104:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1104:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1105:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1105:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1106:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleComment1585); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1128:2: ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1129:1: (lv_closedTag_4_0= RULE_COMMENT_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1129:1: (lv_closedTag_4_0= RULE_COMMENT_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1130:3: lv_closedTag_4_0= RULE_COMMENT_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_COMMENT_END_TAG,FOLLOW_RULE_COMMENT_END_TAG_in_ruleComment1607); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1153:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleComment1628); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1165:1: entryRuleBreak returns [EObject current=null] : iv_ruleBreak= ruleBreak EOF ;
    public final EObject entryRuleBreak() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBreak = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1166:2: (iv_ruleBreak= ruleBreak EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1167:2: iv_ruleBreak= ruleBreak EOF
            {
             currentNode = createCompositeNode(grammarAccess.getBreakRule(), currentNode); 
            pushFollow(FOLLOW_ruleBreak_in_entryRuleBreak1664);
            iv_ruleBreak=ruleBreak();
            _fsp--;

             current =iv_ruleBreak; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBreak1674); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1174:1: ruleBreak returns [EObject current=null] : ( RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleBreak() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1179:6: ( ( RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1180:1: ( RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1180:1: ( RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1180:2: RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_BREAK_START_TAG,FOLLOW_RULE_BREAK_START_TAG_in_ruleBreak1708); 
             
                createLeafNode(grammarAccess.getBreakAccess().getBREAK_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1184:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1185:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1195:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==RULE_ID) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1196:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1196:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1197:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getBreakAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleBreak1737);
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
            	    break loop17;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_XML_TAG_END) ) {
                alt18=1;
            }
            else if ( (LA18_0==RULE_XML_TAG_SINGLEEND) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1219:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1220:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1220:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1221:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleBreak1757); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1243:2: ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1244:1: (lv_closedTag_4_0= RULE_BREAK_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1244:1: (lv_closedTag_4_0= RULE_BREAK_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1245:3: lv_closedTag_4_0= RULE_BREAK_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_BREAK_END_TAG,FOLLOW_RULE_BREAK_END_TAG_in_ruleBreak1779); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1268:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleBreak1800); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1280:1: entryRuleInclude returns [EObject current=null] : iv_ruleInclude= ruleInclude EOF ;
    public final EObject entryRuleInclude() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInclude = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1281:2: (iv_ruleInclude= ruleInclude EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1282:2: iv_ruleInclude= ruleInclude EOF
            {
             currentNode = createCompositeNode(grammarAccess.getIncludeRule(), currentNode); 
            pushFollow(FOLLOW_ruleInclude_in_entryRuleInclude1836);
            iv_ruleInclude=ruleInclude();
            _fsp--;

             current =iv_ruleInclude; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleInclude1846); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1289:1: ruleInclude returns [EObject current=null] : ( RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleInclude() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1294:6: ( ( RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1295:1: ( RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1295:1: ( RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1295:2: RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_INCLUDE_START_TAG,FOLLOW_RULE_INCLUDE_START_TAG_in_ruleInclude1880); 
             
                createLeafNode(grammarAccess.getIncludeAccess().getINCLUDE_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1299:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1300:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1310:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==RULE_ID) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1311:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1311:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1312:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getIncludeAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleInclude1909);
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
            	    break loop19;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1334:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_XML_TAG_END) ) {
                alt20=1;
            }
            else if ( (LA20_0==RULE_XML_TAG_SINGLEEND) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1334:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1334:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1334:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1334:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1334:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1335:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1335:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1336:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleInclude1929); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:2: ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1359:1: (lv_closedTag_4_0= RULE_INCLUDE_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1359:1: (lv_closedTag_4_0= RULE_INCLUDE_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:3: lv_closedTag_4_0= RULE_INCLUDE_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_INCLUDE_END_TAG,FOLLOW_RULE_INCLUDE_END_TAG_in_ruleInclude1951); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1383:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleInclude1972); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1395:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1396:2: (iv_ruleMessage= ruleMessage EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1397:2: iv_ruleMessage= ruleMessage EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMessageRule(), currentNode); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage2008);
            iv_ruleMessage=ruleMessage();
            _fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage2018); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1404:1: ruleMessage returns [EObject current=null] : ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1409:6: ( ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1410:1: ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1410:1: ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1410:2: RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_MESSAGE_START_TAG,FOLLOW_RULE_MESSAGE_START_TAG_in_ruleMessage2052); 
             
                createLeafNode(grammarAccess.getMessageAccess().getMESSAGE_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1414:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1415:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1425:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE_ID) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1426:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1426:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1427:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMessage2081);
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
            	    break loop21;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1449:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_XML_TAG_END) ) {
                alt23=1;
            }
            else if ( (LA23_0==RULE_XML_TAG_SINGLEEND) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1449:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | RULE_XML_TAG_SINGLEEND )", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1449:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1449:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1449:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1449:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1450:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1450:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1451:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMessage2101); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:2: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )*
                    loop22:
                    do {
                        int alt22=10;
                        switch ( input.LA(1) ) {
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt22=1;
                            }
                            break;
                        case RULE_PROPERTY_START_TAG:
                            {
                            alt22=2;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt22=3;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt22=4;
                            }
                            break;
                        case RULE_MAP_METHOD_STARTTAG_START:
                            {
                            alt22=5;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt22=6;
                            }
                            break;
                        case RULE_FIELD_START_TAG:
                            {
                            alt22=7;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt22=8;
                            }
                            break;
                        case RULE_BREAK_START_TAG:
                            {
                            alt22=9;
                            }
                            break;

                        }

                        switch (alt22) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:3: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:3: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1474:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1474:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1475:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMessage2128);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1498:6: ( (lv_children_5_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1498:6: ( (lv_children_5_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1499:1: (lv_children_5_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1499:1: (lv_children_5_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1500:3: lv_children_5_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMessage2155);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1523:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1523:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1524:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1524:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1525:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenParamParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMessage2182);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1548:6: ( (lv_children_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1548:6: ( (lv_children_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1549:1: (lv_children_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1549:1: (lv_children_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1550:3: lv_children_7_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMessage2209);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1573:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1573:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1574:1: (lv_children_8_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1574:1: (lv_children_8_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1575:3: lv_children_8_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapMethodParserRuleCall_3_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMessage2236);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1598:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1598:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1599:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1599:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1600:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenDebugTagParserRuleCall_3_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMessage2263);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1623:6: ( (lv_children_10_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1623:6: ( (lv_children_10_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:1: (lv_children_10_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:1: (lv_children_10_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1625:3: lv_children_10_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenFieldParserRuleCall_3_0_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMessage2290);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1648:6: ( (lv_children_11_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1648:6: ( (lv_children_11_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1649:1: (lv_children_11_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1649:1: (lv_children_11_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1650:3: lv_children_11_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenCommentParserRuleCall_3_0_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMessage2317);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1673:6: ( (lv_children_12_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1673:6: ( (lv_children_12_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1674:1: (lv_children_12_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1674:1: (lv_children_12_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1675:3: lv_children_12_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenBreakParserRuleCall_3_0_1_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMessage2344);
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
                    	    break loop22;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1697:4: ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1698:1: (lv_closedTag_13_0= RULE_MESSAGE_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1698:1: (lv_closedTag_13_0= RULE_MESSAGE_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1699:3: lv_closedTag_13_0= RULE_MESSAGE_END_TAG
                    {
                    lv_closedTag_13_0=(Token)input.LT(1);
                    match(input,RULE_MESSAGE_END_TAG,FOLLOW_RULE_MESSAGE_END_TAG_in_ruleMessage2363); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1722:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage2384); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1734:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1735:2: (iv_ruleMap= ruleMap EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1736:2: iv_ruleMap= ruleMap EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapRule(), currentNode); 
            pushFollow(FOLLOW_ruleMap_in_entryRuleMap2420);
            iv_ruleMap=ruleMap();
            _fsp--;

             current =iv_ruleMap; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMap2430); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1743:1: ruleMap returns [EObject current=null] : ( RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1748:6: ( ( RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1749:1: ( RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1749:1: ( RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1749:2: RULE_MAPSTARTKEYWORD () ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) )
            {
            match(input,RULE_MAPSTARTKEYWORD,FOLLOW_RULE_MAPSTARTKEYWORD_in_ruleMap2464); 
             
                createLeafNode(grammarAccess.getMapAccess().getMAPSTARTKEYWORDTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1753:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1754:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1764:2: ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_DOT) ) {
                alt26=1;
            }
            else if ( (LA26_0==RULE_XML_TAG_END||(LA26_0>=RULE_XML_TAG_SINGLEEND && LA26_0<=RULE_ID)) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1764:2: ( ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* )", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1764:3: ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1764:3: ( RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1764:4: RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )*
                    {
                    match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMap2483); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getDOTTerminalRuleCall_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1768:1: ( (lv_mapName_3_0= ruleMapId ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1769:1: (lv_mapName_3_0= ruleMapId )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1769:1: (lv_mapName_3_0= ruleMapId )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1770:3: lv_mapName_3_0= ruleMapId
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapNameMapIdParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapId_in_ruleMap2503);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1792:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==RULE_ID) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1793:1: (lv_attributes_4_0= rulePossibleExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1793:1: (lv_attributes_4_0= rulePossibleExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1794:3: lv_attributes_4_0= rulePossibleExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_0_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap2524);
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
                    	    break loop24;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1817:6: ( (lv_attributes_5_0= rulePossibleExpression ) )*
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1817:6: ( (lv_attributes_5_0= rulePossibleExpression ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==RULE_ID) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1818:1: (lv_attributes_5_0= rulePossibleExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1818:1: (lv_attributes_5_0= rulePossibleExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1819:3: lv_attributes_5_0= rulePossibleExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap2553);
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
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1841:4: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_XML_TAG_SINGLEEND) ) {
                alt29=1;
            }
            else if ( (LA29_0==RULE_XML_TAG_END) ) {
                alt29=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1841:4: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) )", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1841:5: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap2565); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1846:6: ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1846:6: ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1846:7: ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1846:7: ( (lv_splitTag_7_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1847:1: (lv_splitTag_7_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1847:1: (lv_splitTag_7_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1848:3: lv_splitTag_7_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_7_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap2588); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1870:2: ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )*
                    loop27:
                    do {
                        int alt27=10;
                        switch ( input.LA(1) ) {
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt27=1;
                            }
                            break;
                        case RULE_PROPERTY_START_TAG:
                            {
                            alt27=2;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt27=3;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt27=4;
                            }
                            break;
                        case RULE_MAP_METHOD_STARTTAG_START:
                            {
                            alt27=5;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt27=6;
                            }
                            break;
                        case RULE_FIELD_START_TAG:
                            {
                            alt27=7;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt27=8;
                            }
                            break;
                        case RULE_BREAK_START_TAG:
                            {
                            alt27=9;
                            }
                            break;

                        }

                        switch (alt27) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1870:3: ( (lv_children_8_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1870:3: ( (lv_children_8_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1871:1: (lv_children_8_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1871:1: (lv_children_8_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:3: lv_children_8_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMap2615);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1895:6: ( (lv_children_9_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1895:6: ( (lv_children_9_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1896:1: (lv_children_9_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1896:1: (lv_children_9_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1897:3: lv_children_9_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMap2642);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1920:6: ( (lv_children_10_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1920:6: ( (lv_children_10_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1921:1: (lv_children_10_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1921:1: (lv_children_10_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1922:3: lv_children_10_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenParamParserRuleCall_3_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMap2669);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1945:6: ( (lv_children_11_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1945:6: ( (lv_children_11_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1946:1: (lv_children_11_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1946:1: (lv_children_11_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1947:3: lv_children_11_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_3_1_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMap2696);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1970:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1970:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1971:1: (lv_children_12_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1971:1: (lv_children_12_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1972:3: lv_children_12_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapMethodParserRuleCall_3_1_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMap2723);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1995:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1995:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1996:1: (lv_children_13_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1996:1: (lv_children_13_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1997:3: lv_children_13_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenDebugTagParserRuleCall_3_1_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMap2750);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2020:6: ( (lv_children_14_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2020:6: ( (lv_children_14_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2021:1: (lv_children_14_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2021:1: (lv_children_14_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2022:3: lv_children_14_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenFieldParserRuleCall_3_1_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMap2777);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2045:6: ( (lv_children_15_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2045:6: ( (lv_children_15_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2046:1: (lv_children_15_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2046:1: (lv_children_15_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2047:3: lv_children_15_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenCommentParserRuleCall_3_1_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMap2804);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2070:6: ( (lv_children_16_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2070:6: ( (lv_children_16_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2071:1: (lv_children_16_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2071:1: (lv_children_16_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2072:3: lv_children_16_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenBreakParserRuleCall_3_1_1_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMap2831);
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
                    	    break loop27;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2094:4: ( RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2094:5: RULE_MAPENDKEYWORD ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) )
                    {
                    match(input,RULE_MAPENDKEYWORD,FOLLOW_RULE_MAPENDKEYWORD_in_ruleMap2843); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getMAPENDKEYWORDTerminalRuleCall_3_1_2_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2098:1: ( RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==RULE_DOT) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2098:2: RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) )
                            {
                            match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMap2852); 
                             
                                createLeafNode(grammarAccess.getMapAccess().getDOTTerminalRuleCall_3_1_2_1_0(), null); 
                                
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2102:1: ( (lv_mapClosingName_19_0= ruleMapId ) )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2103:1: (lv_mapClosingName_19_0= ruleMapId )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2103:1: (lv_mapClosingName_19_0= ruleMapId )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2104:3: lv_mapClosingName_19_0= ruleMapId
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapClosingNameMapIdParserRuleCall_3_1_2_1_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleMapId_in_ruleMap2872);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2126:4: ( (lv_closedTag_20_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2127:1: (lv_closedTag_20_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2127:1: (lv_closedTag_20_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2128:3: lv_closedTag_20_0= RULE_XML_TAG_END
                    {
                    lv_closedTag_20_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap2891); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2158:1: entryRuleMapId returns [String current=null] : iv_ruleMapId= ruleMapId EOF ;
    public final String entryRuleMapId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMapId = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2159:2: (iv_ruleMapId= ruleMapId EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2160:2: iv_ruleMapId= ruleMapId EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapIdRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapId_in_entryRuleMapId2936);
            iv_ruleMapId=ruleMapId();
            _fsp--;

             current =iv_ruleMapId.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapId2947); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2167:1: ruleMapId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleMapId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2172:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2173:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapId2986); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2188:1: entryRuleRequired returns [EObject current=null] : iv_ruleRequired= ruleRequired EOF ;
    public final EObject entryRuleRequired() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequired = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2189:2: (iv_ruleRequired= ruleRequired EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2190:2: iv_ruleRequired= ruleRequired EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRequiredRule(), currentNode); 
            pushFollow(FOLLOW_ruleRequired_in_entryRuleRequired3030);
            iv_ruleRequired=ruleRequired();
            _fsp--;

             current =iv_ruleRequired; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequired3040); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2197:1: ruleRequired returns [EObject current=null] : ( RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) ) ;
    public final EObject ruleRequired() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2202:6: ( ( RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2203:1: ( RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2203:1: ( RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2203:2: RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) )
            {
            match(input,RULE_REQUIRED_START_TAG,FOLLOW_RULE_REQUIRED_START_TAG_in_ruleRequired3074); 
             
                createLeafNode(grammarAccess.getRequiredAccess().getREQUIRED_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2207:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2208:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2218:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_ID) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2219:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2219:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2220:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getRequiredAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleRequired3103);
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
            	    break loop30;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2242:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==RULE_XML_TAG_SINGLEEND) ) {
                alt31=1;
            }
            else if ( (LA31_0==RULE_XML_TAG_END) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2242:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) )", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2242:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleRequired3114); 
                     
                        createLeafNode(grammarAccess.getRequiredAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2247:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2247:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2247:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2247:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2248:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2248:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2249:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleRequired3137); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2271:2: ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2272:1: (lv_closedTag_5_0= RULE_REQUIRED_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2272:1: (lv_closedTag_5_0= RULE_REQUIRED_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2273:3: lv_closedTag_5_0= RULE_REQUIRED_END_TAG
                    {
                    lv_closedTag_5_0=(Token)input.LT(1);
                    match(input,RULE_REQUIRED_END_TAG,FOLLOW_RULE_REQUIRED_END_TAG_in_ruleRequired3159); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2303:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2304:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2305:2: iv_ruleProperty= ruleProperty EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPropertyRule(), currentNode); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty3202);
            iv_ruleProperty=ruleProperty();
            _fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty3212); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2312:1: ruleProperty returns [EObject current=null] : ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_7_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2317:6: ( ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2318:1: ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2318:1: ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2318:2: RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) )
            {
            match(input,RULE_PROPERTY_START_TAG,FOLLOW_RULE_PROPERTY_START_TAG_in_ruleProperty3246); 
             
                createLeafNode(grammarAccess.getPropertyAccess().getPROPERTY_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2322:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2323:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2333:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_ID) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2334:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2334:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2335:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleProperty3275);
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
            	    break loop32;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==RULE_XML_TAG_SINGLEEND) ) {
                alt34=1;
            }
            else if ( (LA34_0==RULE_XML_TAG_END) ) {
                alt34=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2357:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) )", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty3286); 
                     
                        createLeafNode(grammarAccess.getPropertyAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2362:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2362:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2362:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2362:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2363:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2363:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2364:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleProperty3309); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )*
                    loop33:
                    do {
                        int alt33=3;
                        int LA33_0 = input.LA(1);

                        if ( ((LA33_0>=RULE_EXPRESSION_START_TAG && LA33_0<=RULE_OPTION_START_TAG)) ) {
                            alt33=1;
                        }
                        else if ( (LA33_0==RULE_MAPSTARTKEYWORD) ) {
                            alt33=2;
                        }


                        switch (alt33) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2387:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2387:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2388:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleProperty3336);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2411:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2411:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2412:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2412:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2413:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getChildrenMapParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleProperty3363);
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
                    	    break loop33;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2435:4: ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2436:1: (lv_closedTag_7_0= RULE_PROPERTY_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2436:1: (lv_closedTag_7_0= RULE_PROPERTY_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2437:3: lv_closedTag_7_0= RULE_PROPERTY_END_TAG
                    {
                    lv_closedTag_7_0=(Token)input.LT(1);
                    match(input,RULE_PROPERTY_END_TAG,FOLLOW_RULE_PROPERTY_END_TAG_in_ruleProperty3382); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2467:1: entryRuleParam returns [EObject current=null] : iv_ruleParam= ruleParam EOF ;
    public final EObject entryRuleParam() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2468:2: (iv_ruleParam= ruleParam EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2469:2: iv_ruleParam= ruleParam EOF
            {
             currentNode = createCompositeNode(grammarAccess.getParamRule(), currentNode); 
            pushFollow(FOLLOW_ruleParam_in_entryRuleParam3425);
            iv_ruleParam=ruleParam();
            _fsp--;

             current =iv_ruleParam; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParam3435); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2476:1: ruleParam returns [EObject current=null] : ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) ) ;
    public final EObject ruleParam() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_7_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2481:6: ( ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2482:1: ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2482:1: ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2482:2: RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) )
            {
            match(input,RULE_PARAM_START_TAG,FOLLOW_RULE_PARAM_START_TAG_in_ruleParam3469); 
             
                createLeafNode(grammarAccess.getParamAccess().getPARAM_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2486:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2487:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2497:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_ID) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2498:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2498:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2499:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleParam3498);
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
            	    break loop35;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2521:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) )
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_XML_TAG_SINGLEEND) ) {
                alt37=1;
            }
            else if ( (LA37_0==RULE_XML_TAG_END) ) {
                alt37=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2521:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) )", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2521:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam3509); 
                     
                        createLeafNode(grammarAccess.getParamAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2526:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2526:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2526:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2526:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2527:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2527:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2528:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleParam3532); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2550:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )*
                    loop36:
                    do {
                        int alt36=3;
                        int LA36_0 = input.LA(1);

                        if ( ((LA36_0>=RULE_EXPRESSION_START_TAG && LA36_0<=RULE_OPTION_START_TAG)) ) {
                            alt36=1;
                        }
                        else if ( (LA36_0==RULE_MAPSTARTKEYWORD) ) {
                            alt36=2;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2550:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2550:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2551:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2551:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2552:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleParam3559);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2575:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2575:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2576:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2576:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2577:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getChildrenMapParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleParam3586);
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
                    	    break loop36;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2599:4: ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2600:1: (lv_closedTag_7_0= RULE_PARAM_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2600:1: (lv_closedTag_7_0= RULE_PARAM_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2601:3: lv_closedTag_7_0= RULE_PARAM_END_TAG
                    {
                    lv_closedTag_7_0=(Token)input.LT(1);
                    match(input,RULE_PARAM_END_TAG,FOLLOW_RULE_PARAM_END_TAG_in_ruleParam3605); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2631:1: entryRuleMapMethod returns [EObject current=null] : iv_ruleMapMethod= ruleMapMethod EOF ;
    public final EObject entryRuleMapMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2632:2: (iv_ruleMapMethod= ruleMapMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2633:2: iv_ruleMapMethod= ruleMapMethod EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapMethodRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapMethod_in_entryRuleMapMethod3648);
            iv_ruleMapMethod=ruleMapMethod();
            _fsp--;

             current =iv_ruleMapMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapMethod3658); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2640:1: ruleMapMethod returns [EObject current=null] : ( RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) ) ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2645:6: ( ( RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2646:1: ( RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2646:1: ( RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2646:2: RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) )
            {
            match(input,RULE_MAP_METHOD_STARTTAG_START,FOLLOW_RULE_MAP_METHOD_STARTTAG_START_in_ruleMapMethod3692); 
             
                createLeafNode(grammarAccess.getMapMethodAccess().getMAP_METHOD_STARTTAG_STARTTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2650:1: ( (lv_mapName_1_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2651:1: (lv_mapName_1_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2651:1: (lv_mapName_1_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2652:3: lv_mapName_1_0= RULE_ID
            {
            lv_mapName_1_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod3708); 

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

            match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMapMethod3722); 
             
                createLeafNode(grammarAccess.getMapMethodAccess().getDOTTerminalRuleCall_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2678:1: ( (lv_methodName_3_0= ruleAttributeName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2679:1: (lv_methodName_3_0= ruleAttributeName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2679:1: (lv_methodName_3_0= ruleAttributeName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2680:3: lv_methodName_3_0= ruleAttributeName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getMethodNameAttributeNameParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAttributeName_in_ruleMapMethod3742);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2702:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==RULE_ID) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2703:1: (lv_attributes_4_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2703:1: (lv_attributes_4_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2704:3: lv_attributes_4_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getAttributesPossibleExpressionParserRuleCall_4_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMapMethod3763);
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
            	    break loop38;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2726:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==RULE_XML_TAG_SINGLEEND) ) {
                alt40=1;
            }
            else if ( (LA40_0==RULE_XML_TAG_END) ) {
                alt40=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2726:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END ) )", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2726:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod3774); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_SINGLEENDTerminalRuleCall_5_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2731:6: ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2731:6: ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2731:7: ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) RULE_XML_TAG_END
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2731:7: ( (lv_splitTag_6_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2732:1: (lv_splitTag_6_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2732:1: (lv_splitTag_6_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2733:3: lv_splitTag_6_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_6_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod3797); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2755:2: ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )*
                    loop39:
                    do {
                        int alt39=11;
                        switch ( input.LA(1) ) {
                        case RULE_EXPRESSION_START_TAG:
                        case RULE_OPTION_START_TAG:
                            {
                            alt39=1;
                            }
                            break;
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt39=2;
                            }
                            break;
                        case RULE_PROPERTY_START_TAG:
                            {
                            alt39=3;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt39=4;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt39=5;
                            }
                            break;
                        case RULE_MAP_METHOD_STARTTAG_START:
                            {
                            alt39=6;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt39=7;
                            }
                            break;
                        case RULE_FIELD_START_TAG:
                            {
                            alt39=8;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt39=9;
                            }
                            break;
                        case RULE_BREAK_START_TAG:
                            {
                            alt39=10;
                            }
                            break;

                        }

                        switch (alt39) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2755:3: ( (lv_children_7_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2755:3: ( (lv_children_7_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2756:1: (lv_children_7_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2756:1: (lv_children_7_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2757:3: lv_children_7_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenExpressionOrOptionParserRuleCall_5_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleMapMethod3824);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:6: ( (lv_children_8_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:6: ( (lv_children_8_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2781:1: (lv_children_8_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2781:1: (lv_children_8_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2782:3: lv_children_8_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMessageParserRuleCall_5_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMapMethod3851);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2805:6: ( (lv_children_9_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2805:6: ( (lv_children_9_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2806:1: (lv_children_9_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2806:1: (lv_children_9_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2807:3: lv_children_9_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenPropertyParserRuleCall_5_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMapMethod3878);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2830:6: ( (lv_children_10_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2830:6: ( (lv_children_10_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2831:1: (lv_children_10_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2831:1: (lv_children_10_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2832:3: lv_children_10_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenParamParserRuleCall_5_1_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMapMethod3905);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2855:6: ( (lv_children_11_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2855:6: ( (lv_children_11_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2856:1: (lv_children_11_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2856:1: (lv_children_11_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2857:3: lv_children_11_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMapParserRuleCall_5_1_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMapMethod3932);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2880:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2880:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2881:1: (lv_children_12_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2881:1: (lv_children_12_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2882:3: lv_children_12_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMapMethodParserRuleCall_5_1_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMapMethod3959);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2905:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2905:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2906:1: (lv_children_13_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2906:1: (lv_children_13_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2907:3: lv_children_13_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenDebugTagParserRuleCall_5_1_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMapMethod3986);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2930:6: ( (lv_children_14_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2930:6: ( (lv_children_14_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2931:1: (lv_children_14_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2931:1: (lv_children_14_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2932:3: lv_children_14_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenFieldParserRuleCall_5_1_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMapMethod4013);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2955:6: ( (lv_children_15_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2955:6: ( (lv_children_15_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2956:1: (lv_children_15_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2956:1: (lv_children_15_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2957:3: lv_children_15_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenCommentParserRuleCall_5_1_1_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMapMethod4040);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2980:6: ( (lv_children_16_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2980:6: ( (lv_children_16_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2981:1: (lv_children_16_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2981:1: (lv_children_16_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2982:3: lv_children_16_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getChildrenBreakParserRuleCall_5_1_1_9_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMapMethod4067);
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
                    	    break loop39;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3004:4: ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3005:1: (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3005:1: (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3006:3: lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START
                    {
                    lv_closedTag_17_0=(Token)input.LT(1);
                    match(input,RULE_MAP_METHOD_ENDTAG_START,FOLLOW_RULE_MAP_METHOD_ENDTAG_START_in_ruleMapMethod4086); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3028:2: ( (lv_methodClosingName_18_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3029:1: (lv_methodClosingName_18_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3029:1: (lv_methodClosingName_18_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3030:3: lv_methodClosingName_18_0= RULE_ID
                    {
                    lv_methodClosingName_18_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod4108); 

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

                    match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMapMethod4122); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getDOTTerminalRuleCall_5_1_4(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3056:1: ( (lv_methodClosingMethod_20_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3057:1: (lv_methodClosingMethod_20_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3057:1: (lv_methodClosingMethod_20_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3058:3: lv_methodClosingMethod_20_0= RULE_ID
                    {
                    lv_methodClosingMethod_20_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod4138); 

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

                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod4152); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3092:1: entryRuleField returns [EObject current=null] : iv_ruleField= ruleField EOF ;
    public final EObject entryRuleField() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleField = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3093:2: (iv_ruleField= ruleField EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3094:2: iv_ruleField= ruleField EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFieldRule(), currentNode); 
            pushFollow(FOLLOW_ruleField_in_entryRuleField4189);
            iv_ruleField=ruleField();
            _fsp--;

             current =iv_ruleField; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleField4199); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3101:1: ruleField returns [EObject current=null] : ( RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3106:6: ( ( RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3107:1: ( RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3107:1: ( RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3107:2: RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) )
            {
            match(input,RULE_FIELD_START_TAG,FOLLOW_RULE_FIELD_START_TAG_in_ruleField4233); 
             
                createLeafNode(grammarAccess.getFieldAccess().getFIELD_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3111:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3112:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3122:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==RULE_ID) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3123:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3123:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3124:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleField4262);
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
            	    break loop41;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3146:3: ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==RULE_XML_TAG_SINGLEEND) ) {
                alt43=1;
            }
            else if ( (LA43_0==RULE_XML_TAG_END) ) {
                alt43=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3146:3: ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) )", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3146:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleField4273); 
                     
                        createLeafNode(grammarAccess.getFieldAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3151:6: ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3151:6: ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3151:7: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3151:7: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3151:8: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )*
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3151:8: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3152:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3152:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3153:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleField4297); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )*
                    loop42:
                    do {
                        int alt42=8;
                        switch ( input.LA(1) ) {
                        case RULE_EXPRESSION_START_TAG:
                        case RULE_OPTION_START_TAG:
                            {
                            alt42=1;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt42=2;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt42=3;
                            }
                            break;
                        case RULE_MAP_METHOD_STARTTAG_START:
                            {
                            alt42=4;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt42=5;
                            }
                            break;
                        case RULE_COMMENT_START_TAG:
                            {
                            alt42=6;
                            }
                            break;
                        case RULE_BREAK_START_TAG:
                            {
                            alt42=7;
                            }
                            break;

                        }

                        switch (alt42) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3176:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3176:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3177:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleField4324);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3200:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3200:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3201:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3201:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3202:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenParamParserRuleCall_3_1_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleField4351);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3225:6: ( (lv_children_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3225:6: ( (lv_children_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3226:1: (lv_children_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3226:1: (lv_children_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3227:3: lv_children_7_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenMapParserRuleCall_3_1_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleField4378);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3250:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3250:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3251:1: (lv_children_8_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3251:1: (lv_children_8_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3252:3: lv_children_8_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenMapMethodParserRuleCall_3_1_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleField4405);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3275:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3275:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3276:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3276:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3277:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenDebugTagParserRuleCall_3_1_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleField4432);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3300:6: ( (lv_children_10_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3300:6: ( (lv_children_10_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3301:1: (lv_children_10_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3301:1: (lv_children_10_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3302:3: lv_children_10_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenCommentParserRuleCall_3_1_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleField4459);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3325:6: ( (lv_children_11_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3325:6: ( (lv_children_11_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3326:1: (lv_children_11_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3326:1: (lv_children_11_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3327:3: lv_children_11_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenBreakParserRuleCall_3_1_0_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleField4486);
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
                    	    break loop42;
                        }
                    } while (true);


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3349:5: ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3350:1: (lv_closedTag_12_0= RULE_FIELD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3350:1: (lv_closedTag_12_0= RULE_FIELD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3351:3: lv_closedTag_12_0= RULE_FIELD_END_TAG
                    {
                    lv_closedTag_12_0=(Token)input.LT(1);
                    match(input,RULE_FIELD_END_TAG,FOLLOW_RULE_FIELD_END_TAG_in_ruleField4506); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3381:1: entryRuleDebugTag returns [EObject current=null] : iv_ruleDebugTag= ruleDebugTag EOF ;
    public final EObject entryRuleDebugTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDebugTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3382:2: (iv_ruleDebugTag= ruleDebugTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3383:2: iv_ruleDebugTag= ruleDebugTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getDebugTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleDebugTag_in_entryRuleDebugTag4549);
            iv_ruleDebugTag=ruleDebugTag();
            _fsp--;

             current =iv_ruleDebugTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDebugTag4559); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3390:1: ruleDebugTag returns [EObject current=null] : ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) ) ;
    public final EObject ruleDebugTag() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_4_0=null;
        Token lv_closedTag_6_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_expression_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3395:6: ( ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3396:1: ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3396:1: ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3396:2: RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) )
            {
            match(input,RULE_DEBUG_START_TAG,FOLLOW_RULE_DEBUG_START_TAG_in_ruleDebugTag4593); 
             
                createLeafNode(grammarAccess.getDebugTagAccess().getDEBUG_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3400:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3401:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3411:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==RULE_ID) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3412:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3412:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3413:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleDebugTag4622);
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
            	    break loop44;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3435:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) )
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==RULE_XML_TAG_SINGLEEND) ) {
                alt46=1;
            }
            else if ( (LA46_0==RULE_XML_TAG_END) ) {
                alt46=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3435:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) )", 46, 0, input);

                throw nvae;
            }
            switch (alt46) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3435:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag4633); 
                     
                        createLeafNode(grammarAccess.getDebugTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3440:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3440:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3440:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3440:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3441:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3441:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3442:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag4656); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3464:2: ( (lv_expression_5_0= ruleTopLevel ) )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==RULE_ID||LA45_0==RULE_SQBRACKET_OPEN||(LA45_0>=RULE_TML_EXISTS && LA45_0<=RULE_DOLLAR)||(LA45_0>=RULE_NUMBER && LA45_0<=RULE_FALSE)||LA45_0==74||LA45_0==84||LA45_0==86) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3465:1: (lv_expression_5_0= ruleTopLevel )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3465:1: (lv_expression_5_0= ruleTopLevel )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3466:3: lv_expression_5_0= ruleTopLevel
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getExpressionTopLevelParserRuleCall_3_1_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleTopLevel_in_ruleDebugTag4682);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:3: ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3489:1: (lv_closedTag_6_0= RULE_DEBUG_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3489:1: (lv_closedTag_6_0= RULE_DEBUG_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3490:3: lv_closedTag_6_0= RULE_DEBUG_END_TAG
                    {
                    lv_closedTag_6_0=(Token)input.LT(1);
                    match(input,RULE_DEBUG_END_TAG,FOLLOW_RULE_DEBUG_END_TAG_in_ruleDebugTag4700); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3520:1: entryRuleExpressionOrOption returns [EObject current=null] : iv_ruleExpressionOrOption= ruleExpressionOrOption EOF ;
    public final EObject entryRuleExpressionOrOption() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionOrOption = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3521:2: (iv_ruleExpressionOrOption= ruleExpressionOrOption EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3522:2: iv_ruleExpressionOrOption= ruleExpressionOrOption EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionOrOptionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionOrOption_in_entryRuleExpressionOrOption4743);
            iv_ruleExpressionOrOption=ruleExpressionOrOption();
            _fsp--;

             current =iv_ruleExpressionOrOption; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionOrOption4753); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3529:1: ruleExpressionOrOption returns [EObject current=null] : ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) ) ;
    public final EObject ruleExpressionOrOption() throws RecognitionException {
        EObject current = null;

        EObject this_ExpressionTag_1 = null;

        EObject this_Option_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3534:6: ( ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3535:1: ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3535:1: ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==RULE_EXPRESSION_START_TAG) ) {
                alt47=1;
            }
            else if ( (LA47_0==RULE_OPTION_START_TAG) ) {
                alt47=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3535:1: ( ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( RULE_OPTION_START_TAG this_Option_3= ruleOption ) )", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3535:2: ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3535:2: ( RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3535:3: RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag
                    {
                    match(input,RULE_EXPRESSION_START_TAG,FOLLOW_RULE_EXPRESSION_START_TAG_in_ruleExpressionOrOption4788); 
                     
                        createLeafNode(grammarAccess.getExpressionOrOptionAccess().getEXPRESSION_START_TAGTerminalRuleCall_0_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionOrOptionAccess().getExpressionTagParserRuleCall_0_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleExpressionTag_in_ruleExpressionOrOption4809);
                    this_ExpressionTag_1=ruleExpressionTag();
                    _fsp--;

                     
                            current = this_ExpressionTag_1; 
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3549:6: ( RULE_OPTION_START_TAG this_Option_3= ruleOption )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3549:6: ( RULE_OPTION_START_TAG this_Option_3= ruleOption )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3549:7: RULE_OPTION_START_TAG this_Option_3= ruleOption
                    {
                    match(input,RULE_OPTION_START_TAG,FOLLOW_RULE_OPTION_START_TAG_in_ruleExpressionOrOption4825); 
                     
                        createLeafNode(grammarAccess.getExpressionOrOptionAccess().getOPTION_START_TAGTerminalRuleCall_1_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionOrOptionAccess().getOptionParserRuleCall_1_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleOption_in_ruleExpressionOrOption4846);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3570:1: entryRuleExpressionTag returns [EObject current=null] : iv_ruleExpressionTag= ruleExpressionTag EOF ;
    public final EObject entryRuleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3571:2: (iv_ruleExpressionTag= ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3572:2: iv_ruleExpressionTag= ruleExpressionTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag4882);
            iv_ruleExpressionTag=ruleExpressionTag();
            _fsp--;

             current =iv_ruleExpressionTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag4892); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3579:1: ruleExpressionTag returns [EObject current=null] : ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) ) ;
    public final EObject ruleExpressionTag() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_1_0 = null;

        EObject lv_expression_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3584:6: ( ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3585:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3585:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3585:2: () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3585:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3586:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3596:2: ( (lv_attributes_1_0= rulePossibleExpression ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==RULE_ID) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3597:1: (lv_attributes_1_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3597:1: (lv_attributes_1_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3598:3: lv_attributes_1_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getAttributesPossibleExpressionParserRuleCall_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleExpressionTag4947);
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
            	    break loop48;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3620:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==RULE_XML_TAG_SINGLEEND) ) {
                alt49=1;
            }
            else if ( (LA49_0==RULE_XML_TAG_END) ) {
                alt49=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3620:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) )", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3620:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag4958); 
                     
                        createLeafNode(grammarAccess.getExpressionTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3625:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3625:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3625:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3625:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3626:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3626:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3627:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag4981); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3649:2: ( (lv_expression_4_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3650:1: (lv_expression_4_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3650:1: (lv_expression_4_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3651:3: lv_expression_4_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getExpressionTopLevelParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleExpressionTag5007);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3673:2: ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3674:1: (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3674:1: (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3675:3: lv_closedTag_5_0= RULE_EXPRESSION_END_TAG
                    {
                    lv_closedTag_5_0=(Token)input.LT(1);
                    match(input,RULE_EXPRESSION_END_TAG,FOLLOW_RULE_EXPRESSION_END_TAG_in_ruleExpressionTag5024); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3705:1: entryRuleOption returns [EObject current=null] : iv_ruleOption= ruleOption EOF ;
    public final EObject entryRuleOption() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOption = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3706:2: (iv_ruleOption= ruleOption EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3707:2: iv_ruleOption= ruleOption EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOptionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOption_in_entryRuleOption5067);
            iv_ruleOption=ruleOption();
            _fsp--;

             current =iv_ruleOption; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOption5077); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3714:1: ruleOption returns [EObject current=null] : ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) ) ;
    public final EObject ruleOption() throws RecognitionException {
        EObject current = null;

        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3719:6: ( ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3720:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3720:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3720:2: () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3720:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3721:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3731:2: ( (lv_attributes_1_0= rulePossibleExpression ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==RULE_ID) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3732:1: (lv_attributes_1_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3732:1: (lv_attributes_1_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3733:3: lv_attributes_1_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOptionAccess().getAttributesPossibleExpressionParserRuleCall_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleOption5132);
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
            	    break loop50;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3755:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==RULE_XML_TAG_SINGLEEND) ) {
                alt51=1;
            }
            else if ( (LA51_0==RULE_XML_TAG_END) ) {
                alt51=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3755:3: ( RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) )", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3755:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleOption5143); 
                     
                        createLeafNode(grammarAccess.getOptionAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3760:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3760:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3760:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3760:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3761:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3761:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3762:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)input.LT(1);
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleOption5166); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3784:2: ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3785:1: (lv_closedTag_4_0= RULE_OPTION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3785:1: (lv_closedTag_4_0= RULE_OPTION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3786:3: lv_closedTag_4_0= RULE_OPTION_END_TAG
                    {
                    lv_closedTag_4_0=(Token)input.LT(1);
                    match(input,RULE_OPTION_END_TAG,FOLLOW_RULE_OPTION_END_TAG_in_ruleOption5188); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3816:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3817:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3818:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTopLevelRule(), currentNode); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel5231);
            iv_ruleTopLevel=ruleTopLevel();
            _fsp--;

             current =iv_ruleTopLevel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel5241); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3825:1: ruleTopLevel returns [EObject current=null] : ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3830:6: ( ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3831:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3831:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3832:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3832:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3833:3: lv_toplevelExpression_0_0= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel5286);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3863:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3864:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3865:2: iv_rulePathElement= rulePathElement EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathElementRule(), currentNode); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement5322);
            iv_rulePathElement=rulePathElement();
            _fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement5333); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3872:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_PARENT_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3877:6: ( (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3878:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3878:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            int alt52=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt52=1;
                }
                break;
            case RULE_DOT:
                {
                alt52=2;
                }
                break;
            case RULE_PARENT:
                {
                alt52=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("3878:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )", 52, 0, input);

                throw nvae;
            }

            switch (alt52) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3878:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement5373); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3887:2: kw= '.'
                    {
                    kw=(Token)input.LT(1);
                    match(input,RULE_DOT,FOLLOW_RULE_DOT_in_rulePathElement5397); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3893:10: this_PARENT_2= RULE_PARENT
                    {
                    this_PARENT_2=(Token)input.LT(1);
                    match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_rulePathElement5418); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3908:1: entryRuleTmlExpression returns [EObject current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final EObject entryRuleTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3909:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3910:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression5463);
            iv_ruleTmlExpression=ruleTmlExpression();
            _fsp--;

             current =iv_ruleTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression5473); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3917:1: ruleTmlExpression returns [EObject current=null] : ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_1_0=null;
        Token lv_param_2_0=null;
        AntlrDatatypeRuleToken lv_elements_3_0 = null;

        AntlrDatatypeRuleToken lv_elements_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3922:6: ( ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3923:1: ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3923:1: ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3923:2: RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE
            {
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression5507); 
             
                createLeafNode(grammarAccess.getTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3927:1: ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==RULE_TML_SEPARATOR) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3928:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3928:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3929:3: lv_absolute_1_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_1_0=(Token)input.LT(1);
                    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5523); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3951:3: ( (lv_param_2_0= RULE_AT ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_AT) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3952:1: (lv_param_2_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3952:1: (lv_param_2_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3953:3: lv_param_2_0= RULE_AT
                    {
                    lv_param_2_0=(Token)input.LT(1);
                    match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleTmlExpression5546); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3975:3: ( (lv_elements_3_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3976:1: (lv_elements_3_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3976:1: (lv_elements_3_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3977:3: lv_elements_3_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression5573);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3999:2: ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==RULE_TML_SEPARATOR) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3999:3: RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5583); 
            	     
            	        createLeafNode(grammarAccess.getTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_4_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4003:1: ( (lv_elements_5_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4004:1: (lv_elements_5_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4004:1: (lv_elements_5_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4005:3: lv_elements_5_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression5603);
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
            	    break loop55;
                }
            } while (true);

            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression5614); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4039:1: entryRuleExistsTmlExpression returns [EObject current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final EObject entryRuleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4040:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4041:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExistsTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression5649);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();
            _fsp--;

             current =iv_ruleExistsTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression5659); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4048:1: ruleExistsTmlExpression returns [EObject current=null] : ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_2_0=null;
        Token lv_param_3_0=null;
        AntlrDatatypeRuleToken lv_elements_4_0 = null;

        AntlrDatatypeRuleToken lv_elements_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4053:6: ( ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4054:1: ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4054:1: ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4054:2: RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE
            {
            match(input,RULE_TML_EXISTS,FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression5693); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getTML_EXISTSTerminalRuleCall_0(), null); 
                
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression5701); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4062:1: ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==RULE_TML_SEPARATOR) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4063:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4063:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4064:3: lv_absolute_2_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_2_0=(Token)input.LT(1);
                    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5717); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4086:3: ( (lv_param_3_0= RULE_AT ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==RULE_AT) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4087:1: (lv_param_3_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4087:1: (lv_param_3_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4088:3: lv_param_3_0= RULE_AT
                    {
                    lv_param_3_0=(Token)input.LT(1);
                    match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleExistsTmlExpression5740); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4110:3: ( (lv_elements_4_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4111:1: (lv_elements_4_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4111:1: (lv_elements_4_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4112:3: lv_elements_4_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression5767);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4134:2: ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==RULE_TML_SEPARATOR) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4134:3: RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5777); 
            	     
            	        createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_5_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4138:1: ( (lv_elements_6_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4139:1: (lv_elements_6_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4139:1: (lv_elements_6_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4140:3: lv_elements_6_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_5_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression5797);
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
            	    break loop58;
                }
            } while (true);

            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression5808); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4174:1: entryRuleMapReferenceParams returns [EObject current=null] : iv_ruleMapReferenceParams= ruleMapReferenceParams EOF ;
    public final EObject entryRuleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapReferenceParams = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4175:2: (iv_ruleMapReferenceParams= ruleMapReferenceParams EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4176:2: iv_ruleMapReferenceParams= ruleMapReferenceParams EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapReferenceParamsRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams5843);
            iv_ruleMapReferenceParams=ruleMapReferenceParams();
            _fsp--;

             current =iv_ruleMapReferenceParams; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapReferenceParams5853); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4183:1: ruleMapReferenceParams returns [EObject current=null] : ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' ) ;
    public final EObject ruleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        EObject lv_getterParams_1_0 = null;

        EObject lv_getterParams_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4188:6: ( ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4189:1: ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4189:1: ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4189:3: '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')'
            {
            match(input,74,FOLLOW_74_in_ruleMapReferenceParams5888); 

                    createLeafNode(grammarAccess.getMapReferenceParamsAccess().getLeftParenthesisKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4193:1: ( (lv_getterParams_1_0= ruleLiteral ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4194:1: (lv_getterParams_1_0= ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4194:1: (lv_getterParams_1_0= ruleLiteral )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4195:3: lv_getterParams_1_0= ruleLiteral
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams5909);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4217:2: ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==75) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4217:4: ',' ( (lv_getterParams_3_0= ruleLiteral ) )
            	    {
            	    match(input,75,FOLLOW_75_in_ruleMapReferenceParams5920); 

            	            createLeafNode(grammarAccess.getMapReferenceParamsAccess().getCommaKeyword_2_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4221:1: ( (lv_getterParams_3_0= ruleLiteral ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4222:1: (lv_getterParams_3_0= ruleLiteral )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4222:1: (lv_getterParams_3_0= ruleLiteral )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4223:3: lv_getterParams_3_0= ruleLiteral
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams5941);
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
            	    break loop59;
                }
            } while (true);

            match(input,76,FOLLOW_76_in_ruleMapReferenceParams5953); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4257:1: entryRuleMapGetReference returns [EObject current=null] : iv_ruleMapGetReference= ruleMapGetReference EOF ;
    public final EObject entryRuleMapGetReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapGetReference = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4258:2: (iv_ruleMapGetReference= ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4259:2: iv_ruleMapGetReference= ruleMapGetReference EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapGetReferenceRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference5989);
            iv_ruleMapGetReference=ruleMapGetReference();
            _fsp--;

             current =iv_ruleMapGetReference; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference5999); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4266:1: ruleMapGetReference returns [EObject current=null] : ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) ;
    public final EObject ruleMapGetReference() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        Token lv_elements_1_0=null;
        Token lv_elements_3_0=null;
        EObject lv_referenceParams_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4271:6: ( ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4272:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4272:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4272:2: ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4272:2: ( (lv_operations_0_0= RULE_DOLLAR ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4273:1: (lv_operations_0_0= RULE_DOLLAR )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4273:1: (lv_operations_0_0= RULE_DOLLAR )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4274:3: lv_operations_0_0= RULE_DOLLAR
            {
            lv_operations_0_0=(Token)input.LT(1);
            match(input,RULE_DOLLAR,FOLLOW_RULE_DOLLAR_in_ruleMapGetReference6041); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:2: ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==RULE_PARENT) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:3: ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:3: ( (lv_elements_1_0= RULE_PARENT ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4297:1: (lv_elements_1_0= RULE_PARENT )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4297:1: (lv_elements_1_0= RULE_PARENT )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4298:3: lv_elements_1_0= RULE_PARENT
            	    {
            	    lv_elements_1_0=(Token)input.LT(1);
            	    match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_ruleMapGetReference6064); 

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

            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference6078); 
            	     
            	        createLeafNode(grammarAccess.getMapGetReferenceAccess().getTML_SEPARATORTerminalRuleCall_1_1(), null); 
            	        

            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4324:3: ( (lv_elements_3_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4325:1: (lv_elements_3_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4325:1: (lv_elements_3_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4326:3: lv_elements_3_0= RULE_ID
            {
            lv_elements_3_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapGetReference6096); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4348:2: ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==74) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4349:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4349:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4350:3: lv_referenceParams_4_0= ruleMapReferenceParams
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getMapGetReferenceAccess().getReferenceParamsMapReferenceParamsParserRuleCall_3_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference6122);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4380:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4381:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4382:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression6159);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression6169); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4389:1: ruleOrExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4394:6: ( ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4395:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4395:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4395:2: ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4395:2: ( (lv_parameters_0_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4396:1: (lv_parameters_0_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4396:1: (lv_parameters_0_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4397:3: lv_parameters_0_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression6215);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:2: ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==77) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:3: ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:3: ( (lv_operations_1_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4420:1: (lv_operations_1_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4420:1: (lv_operations_1_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4421:3: lv_operations_1_0= 'OR'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,77,FOLLOW_77_in_ruleOrExpression6234); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4440:2: ( (lv_parameters_2_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4441:1: (lv_parameters_2_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4441:1: (lv_parameters_2_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4442:3: lv_parameters_2_0= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression6268);
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
            	    break loop62;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4472:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4473:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4474:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression6306);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression6316); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4481:1: ruleAndExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4486:6: ( ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4487:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4487:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4487:2: ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4487:2: ( (lv_parameters_0_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4488:1: (lv_parameters_0_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4488:1: (lv_parameters_0_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4489:3: lv_parameters_0_0= ruleEqualityExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression6362);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4511:2: ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==78) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4511:3: ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4511:3: ( (lv_operations_1_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4512:1: (lv_operations_1_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4512:1: (lv_operations_1_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4513:3: lv_operations_1_0= 'AND'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,78,FOLLOW_78_in_ruleAndExpression6381); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4532:2: ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4533:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4533:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4534:3: lv_parameters_2_0= ruleEqualityExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression6415);
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
    // $ANTLR end ruleAndExpression


    // $ANTLR start entryRuleEqualityExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4564:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4565:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4566:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEqualityExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression6453);
            iv_ruleEqualityExpression=ruleEqualityExpression();
            _fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression6463); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4573:1: ruleEqualityExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4578:6: ( ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4579:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4579:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4579:2: ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4579:2: ( (lv_parameters_0_0= ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4580:1: (lv_parameters_0_0= ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4580:1: (lv_parameters_0_0= ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4581:3: lv_parameters_0_0= ruleRelationalExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6509);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:2: ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            int alt64=3;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==79) ) {
                alt64=1;
            }
            else if ( (LA64_0==80) ) {
                alt64=2;
            }
            switch (alt64) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:4: ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4603:4: ( (lv_operations_1_0= '==' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4604:1: (lv_operations_1_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4604:1: (lv_operations_1_0= '==' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4605:3: lv_operations_1_0= '=='
                    {
                    lv_operations_1_0=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_ruleEqualityExpression6529); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4624:2: ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4625:1: (lv_parameters_2_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4625:1: (lv_parameters_2_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4626:3: lv_parameters_2_0= ruleRelationalExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6563);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4649:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4649:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4649:7: ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4649:7: ( (lv_operations_3_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4650:1: (lv_operations_3_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4650:1: (lv_operations_3_0= '!=' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4651:3: lv_operations_3_0= '!='
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,80,FOLLOW_80_in_ruleEqualityExpression6589); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4670:2: ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4671:1: (lv_parameters_4_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4671:1: (lv_parameters_4_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4672:3: lv_parameters_4_0= ruleRelationalExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6623);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4702:1: entryRuleRelationalExpression returns [EObject current=null] : iv_ruleRelationalExpression= ruleRelationalExpression EOF ;
    public final EObject entryRuleRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationalExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4703:2: (iv_ruleRelationalExpression= ruleRelationalExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4704:2: iv_ruleRelationalExpression= ruleRelationalExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRelationalExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression6662);
            iv_ruleRelationalExpression=ruleRelationalExpression();
            _fsp--;

             current =iv_ruleRelationalExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRelationalExpression6672); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4711:1: ruleRelationalExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4716:6: ( ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4717:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4717:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4717:2: () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4717:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4718:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4728:2: ( (lv_parameters_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4729:1: (lv_parameters_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4729:1: (lv_parameters_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4730:3: lv_parameters_1_0= ruleAdditiveExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6727);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4752:2: ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            int alt65=5;
            switch ( input.LA(1) ) {
                case RULE_XML_LT:
                    {
                    alt65=1;
                    }
                    break;
                case RULE_XML_GT:
                    {
                    alt65=2;
                    }
                    break;
                case RULE_XML_LTEQ:
                    {
                    alt65=3;
                    }
                    break;
                case RULE_XML_GTEQ:
                    {
                    alt65=4;
                    }
                    break;
            }

            switch (alt65) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4752:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4752:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4752:4: ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4752:4: ( (lv_operations_2_0= RULE_XML_LT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4753:1: (lv_operations_2_0= RULE_XML_LT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4753:1: (lv_operations_2_0= RULE_XML_LT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4754:3: lv_operations_2_0= RULE_XML_LT
                    {
                    lv_operations_2_0=(Token)input.LT(1);
                    match(input,RULE_XML_LT,FOLLOW_RULE_XML_LT_in_ruleRelationalExpression6746); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4776:2: ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4777:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4777:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4778:3: lv_parameters_3_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6772);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4801:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4801:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4801:7: ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4801:7: ( (lv_operations_4_0= RULE_XML_GT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4802:1: (lv_operations_4_0= RULE_XML_GT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4802:1: (lv_operations_4_0= RULE_XML_GT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4803:3: lv_operations_4_0= RULE_XML_GT
                    {
                    lv_operations_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_GT,FOLLOW_RULE_XML_GT_in_ruleRelationalExpression6797); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4825:2: ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4826:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4826:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4827:3: lv_parameters_5_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6823);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4850:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4850:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4850:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4850:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4851:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4851:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4852:3: lv_operations_6_0= RULE_XML_LTEQ
                    {
                    lv_operations_6_0=(Token)input.LT(1);
                    match(input,RULE_XML_LTEQ,FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression6848); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4874:2: ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4875:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4875:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4876:3: lv_parameters_7_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_2_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6874);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4899:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4899:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4899:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4899:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4900:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4900:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4901:3: lv_operations_8_0= RULE_XML_GTEQ
                    {
                    lv_operations_8_0=(Token)input.LT(1);
                    match(input,RULE_XML_GTEQ,FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression6899); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4923:2: ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4924:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4924:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4925:3: lv_parameters_9_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_3_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6925);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4955:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4956:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4957:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAdditiveExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression6964);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();
            _fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression6974); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4964:1: ruleAdditiveExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4969:6: ( ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4970:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4970:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4970:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4970:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4971:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4971:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4972:3: lv_parameters_0_0= ruleMultiplicativeExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7020);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4994:2: ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            loop66:
            do {
                int alt66=3;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==81) ) {
                    alt66=1;
                }
                else if ( (LA66_0==82) ) {
                    alt66=2;
                }


                switch (alt66) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4994:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4994:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4994:5: '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,81,FOLLOW_81_in_ruleAdditiveExpression7032); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4998:1: ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4999:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4999:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5000:3: lv_parameters_2_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7053);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5023:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5023:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5023:8: '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,82,FOLLOW_82_in_ruleAdditiveExpression7071); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5027:1: ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5028:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5028:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5029:3: lv_parameters_4_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7092);
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
            	    break loop66;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5059:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5060:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5061:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression7131);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();
            _fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression7141); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5068:1: ruleMultiplicativeExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5073:6: ( ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5074:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5074:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5074:2: ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5074:2: ( (lv_parameters_0_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5075:1: (lv_parameters_0_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5075:1: (lv_parameters_0_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5076:3: lv_parameters_0_0= ruleUnaryExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7187);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:2: ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            loop67:
            do {
                int alt67=3;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==83) ) {
                    alt67=1;
                }
                else if ( (LA67_0==RULE_TML_SEPARATOR) ) {
                    alt67=2;
                }


                switch (alt67) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:4: ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5098:4: ( (lv_operations_1_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5099:1: (lv_operations_1_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5099:1: (lv_operations_1_0= '*' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5100:3: lv_operations_1_0= '*'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,83,FOLLOW_83_in_ruleMultiplicativeExpression7207); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5119:2: ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5120:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5120:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5121:3: lv_parameters_2_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7241);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5144:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5144:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5144:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5144:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5145:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5145:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5146:3: lv_operations_3_0= RULE_TML_SEPARATOR
            	    {
            	    lv_operations_3_0=(Token)input.LT(1);
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression7266); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5168:2: ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5169:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5169:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5170:3: lv_parameters_4_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7292);
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
    // $ANTLR end ruleMultiplicativeExpression


    // $ANTLR start entryRuleUnaryExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5200:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5201:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5202:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getUnaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression7331);
            iv_ruleUnaryExpression=ruleUnaryExpression();
            _fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression7341); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5209:1: ruleUnaryExpression returns [EObject current=null] : ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        EObject lv_parameters_1_0 = null;

        EObject this_PrimaryExpression_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5214:6: ( ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5215:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5215:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==84) ) {
                alt68=1;
            }
            else if ( (LA68_0==RULE_ID||LA68_0==RULE_SQBRACKET_OPEN||(LA68_0>=RULE_TML_EXISTS && LA68_0<=RULE_DOLLAR)||(LA68_0>=RULE_NUMBER && LA68_0<=RULE_FALSE)||LA68_0==74||LA68_0==86) ) {
                alt68=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5215:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )", 68, 0, input);

                throw nvae;
            }
            switch (alt68) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5215:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5215:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5215:3: ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5215:3: ( (lv_operations_0_0= '!' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5216:1: (lv_operations_0_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5216:1: (lv_operations_0_0= '!' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5217:3: lv_operations_0_0= '!'
                    {
                    lv_operations_0_0=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_ruleUnaryExpression7385); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5236:2: ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5237:1: (lv_parameters_1_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5237:1: (lv_parameters_1_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5238:3: lv_parameters_1_0= rulePrimaryExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7419);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5262:5: this_PrimaryExpression_2= rulePrimaryExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7448);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5278:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5279:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5280:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPrimaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression7483);
            iv_rulePrimaryExpression=rulePrimaryExpression();
            _fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression7493); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5287:1: rulePrimaryExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5292:6: ( ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5293:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5293:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==RULE_ID||LA69_0==RULE_SQBRACKET_OPEN||(LA69_0>=RULE_TML_EXISTS && LA69_0<=RULE_DOLLAR)||(LA69_0>=RULE_NUMBER && LA69_0<=RULE_FALSE)||LA69_0==86) ) {
                alt69=1;
            }
            else if ( (LA69_0==74) ) {
                alt69=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5293:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )", 69, 0, input);

                throw nvae;
            }
            switch (alt69) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5293:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5293:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5294:1: (lv_parameters_0_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5294:1: (lv_parameters_0_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5295:3: lv_parameters_0_0= ruleLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression7539);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5318:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5318:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5318:8: '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')'
                    {
                    match(input,74,FOLLOW_74_in_rulePrimaryExpression7556); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5322:1: ( (lv_parameters_2_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5323:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5323:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5324:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression7577);
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

                    match(input,76,FOLLOW_76_in_rulePrimaryExpression7587); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5358:1: entryRuleFunctionName returns [String current=null] : iv_ruleFunctionName= ruleFunctionName EOF ;
    public final String entryRuleFunctionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFunctionName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5359:2: (iv_ruleFunctionName= ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5360:2: iv_ruleFunctionName= ruleFunctionName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName7625);
            iv_ruleFunctionName=ruleFunctionName();
            _fsp--;

             current =iv_ruleFunctionName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName7636); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5367:1: ruleFunctionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFunctionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5372:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5373:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName7675); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5388:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5389:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5390:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionCallRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall7719);
            iv_ruleFunctionCall=ruleFunctionCall();
            _fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall7729); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5397:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5402:6: ( ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5403:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5403:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5403:2: ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')'
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5403:2: ( (lv_name_0_0= ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5404:1: (lv_name_0_0= ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5404:1: (lv_name_0_0= ruleFunctionName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5405:3: lv_name_0_0= ruleFunctionName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleFunctionName_in_ruleFunctionCall7775);
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

            match(input,74,FOLLOW_74_in_ruleFunctionCall7785); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5431:1: ( (lv_parameters_2_0= ruleOrExpression ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==RULE_ID||LA70_0==RULE_SQBRACKET_OPEN||(LA70_0>=RULE_TML_EXISTS && LA70_0<=RULE_DOLLAR)||(LA70_0>=RULE_NUMBER && LA70_0<=RULE_FALSE)||LA70_0==74||LA70_0==84||LA70_0==86) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5432:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5432:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5433:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall7806);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5455:3: ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==75) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5455:5: ',' ( (lv_parameters_4_0= ruleOrExpression ) )
            	    {
            	    match(input,75,FOLLOW_75_in_ruleFunctionCall7818); 

            	            createLeafNode(grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5459:1: ( (lv_parameters_4_0= ruleOrExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5460:1: (lv_parameters_4_0= ruleOrExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5460:1: (lv_parameters_4_0= ruleOrExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5461:3: lv_parameters_4_0= ruleOrExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall7839);
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
            	    break loop71;
                }
            } while (true);

            match(input,76,FOLLOW_76_in_ruleFunctionCall7851); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5495:1: entryRuleDateLiteral returns [EObject current=null] : iv_ruleDateLiteral= ruleDateLiteral EOF ;
    public final EObject entryRuleDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDateLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5496:2: (iv_ruleDateLiteral= ruleDateLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5497:2: iv_ruleDateLiteral= ruleDateLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getDateLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral7887);
            iv_ruleDateLiteral=ruleDateLiteral();
            _fsp--;

             current =iv_ruleDateLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDateLiteral7897); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5504:1: ruleDateLiteral returns [EObject current=null] : ( () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER ) ;
    public final EObject ruleDateLiteral() throws RecognitionException {
        EObject current = null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5509:6: ( ( () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5510:1: ( () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5510:1: ( () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5510:2: () RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER '#' RULE_NUMBER
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5510:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5511:5: 
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

            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral7940); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_1(), null); 
                
            match(input,85,FOLLOW_85_in_ruleDateLiteral7949); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_2(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral7958); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_3(), null); 
                
            match(input,85,FOLLOW_85_in_ruleDateLiteral7967); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_4(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral7976); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_5(), null); 
                
            match(input,85,FOLLOW_85_in_ruleDateLiteral7985); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_6(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral7994); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_7(), null); 
                
            match(input,85,FOLLOW_85_in_ruleDateLiteral8003); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_8(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8012); 
             
                createLeafNode(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_9(), null); 
                
            match(input,85,FOLLOW_85_in_ruleDateLiteral8021); 

                    createLeafNode(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_10(), null); 
                
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8030); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5573:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5574:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5575:2: iv_ruleLiteral= ruleLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral8065);
            iv_ruleLiteral=ruleLiteral();
            _fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral8075); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5582:1: ruleLiteral returns [EObject current=null] : ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5587:6: ( ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5588:1: ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5588:1: ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )
            int alt74=13;
            switch ( input.LA(1) ) {
            case RULE_NUMBER:
                {
                int LA74_1 = input.LA(2);

                if ( (LA74_1==85) ) {
                    alt74=13;
                }
                else if ( (LA74_1==EOF||LA74_1==RULE_SEMICOLONQUOTE||LA74_1==RULE_CHECK_END_TAG||LA74_1==RULE_DEBUG_END_TAG||LA74_1==RULE_EXPRESSION_END_TAG||LA74_1==RULE_TML_SEPARATOR||(LA74_1>=RULE_XML_LT && LA74_1<=RULE_XML_GTEQ)||(LA74_1>=75 && LA74_1<=83)||LA74_1==87) ) {
                    alt74=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("5588:1: ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )", 74, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt74=2;
                }
                break;
            case RULE_FORALL:
                {
                alt74=3;
                }
                break;
            case RULE_ID:
                {
                alt74=4;
                }
                break;
            case 86:
                {
                alt74=5;
                }
                break;
            case RULE_NULL:
                {
                alt74=6;
                }
                break;
            case RULE_TODAY:
                {
                alt74=7;
                }
                break;
            case RULE_TRUE:
                {
                alt74=8;
                }
                break;
            case RULE_FALSE:
                {
                alt74=9;
                }
                break;
            case RULE_SQBRACKET_OPEN:
                {
                alt74=10;
                }
                break;
            case RULE_TML_EXISTS:
                {
                alt74=11;
                }
                break;
            case RULE_DOLLAR:
                {
                alt74=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("5588:1: ( ( () RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )", 74, 0, input);

                throw nvae;
            }

            switch (alt74) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5588:2: ( () RULE_NUMBER )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5588:2: ( () RULE_NUMBER )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5588:3: () RULE_NUMBER
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5588:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5589:5: 
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

                    match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleLiteral8119); 
                     
                        createLeafNode(grammarAccess.getLiteralAccess().getNUMBERTerminalRuleCall_0_1(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5604:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5604:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5605:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5605:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5606:3: lv_valueString_2_0= RULE_LITERALSTRING
                    {
                    lv_valueString_2_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8142); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5629:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5629:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5629:7: ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5629:7: ( (lv_operations_3_0= RULE_FORALL ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5630:1: (lv_operations_3_0= RULE_FORALL )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5630:1: (lv_operations_3_0= RULE_FORALL )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5631:3: lv_operations_3_0= RULE_FORALL
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,RULE_FORALL,FOLLOW_RULE_FORALL_in_ruleLiteral8171); 

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

                    match(input,74,FOLLOW_74_in_ruleLiteral8186); 

                            createLeafNode(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5657:1: ( (lv_valueString_5_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5658:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5658:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5659:3: lv_valueString_5_0= RULE_LITERALSTRING
                    {
                    lv_valueString_5_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8203); 

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

                    match(input,75,FOLLOW_75_in_ruleLiteral8218); 

                            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_2_3(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5685:1: ( (lv_parameters_7_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5686:1: (lv_parameters_7_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5686:1: (lv_parameters_7_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5687:3: lv_parameters_7_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8239);
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

                    match(input,76,FOLLOW_76_in_ruleLiteral8249); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5(), null); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5714:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5714:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5715:1: (lv_parameters_9_0= ruleFunctionCall )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5715:1: (lv_parameters_9_0= ruleFunctionCall )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5716:3: lv_parameters_9_0= ruleFunctionCall
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersFunctionCallParserRuleCall_3_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral8277);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5739:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5739:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5739:7: ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5739:7: ( (lv_expressionType_10_0= '{' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5740:1: (lv_expressionType_10_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5740:1: (lv_expressionType_10_0= '{' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5741:3: lv_expressionType_10_0= '{'
                    {
                    lv_expressionType_10_0=(Token)input.LT(1);
                    match(input,86,FOLLOW_86_in_ruleLiteral8302); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5760:2: ( (lv_parameters_11_0= ruleOrExpression ) )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==RULE_ID||LA72_0==RULE_SQBRACKET_OPEN||(LA72_0>=RULE_TML_EXISTS && LA72_0<=RULE_DOLLAR)||(LA72_0>=RULE_NUMBER && LA72_0<=RULE_FALSE)||LA72_0==74||LA72_0==84||LA72_0==86) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5761:1: (lv_parameters_11_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5761:1: (lv_parameters_11_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5762:3: lv_parameters_11_0= ruleOrExpression
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8336);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5784:3: ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )*
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==75) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5784:5: ',' ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    {
                    	    match(input,75,FOLLOW_75_in_ruleLiteral8348); 

                    	            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0(), null); 
                    	        
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5788:1: ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5789:1: (lv_parameters_13_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5789:1: (lv_parameters_13_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5790:3: lv_parameters_13_0= ruleOrExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8369);
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
                    	    break loop73;
                        }
                    } while (true);

                    match(input,87,FOLLOW_87_in_ruleLiteral8381); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3(), null); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5817:6: ( (lv_elements_15_0= RULE_NULL ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5817:6: ( (lv_elements_15_0= RULE_NULL ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5818:1: (lv_elements_15_0= RULE_NULL )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5818:1: (lv_elements_15_0= RULE_NULL )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5819:3: lv_elements_15_0= RULE_NULL
                    {
                    lv_elements_15_0=(Token)input.LT(1);
                    match(input,RULE_NULL,FOLLOW_RULE_NULL_in_ruleLiteral8405); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5842:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5842:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5843:1: (lv_elements_16_0= RULE_TODAY )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5843:1: (lv_elements_16_0= RULE_TODAY )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5844:3: lv_elements_16_0= RULE_TODAY
                    {
                    lv_elements_16_0=(Token)input.LT(1);
                    match(input,RULE_TODAY,FOLLOW_RULE_TODAY_in_ruleLiteral8433); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5867:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5867:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5868:1: (lv_elements_17_0= RULE_TRUE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5868:1: (lv_elements_17_0= RULE_TRUE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5869:3: lv_elements_17_0= RULE_TRUE
                    {
                    lv_elements_17_0=(Token)input.LT(1);
                    match(input,RULE_TRUE,FOLLOW_RULE_TRUE_in_ruleLiteral8461); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5892:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5892:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5893:1: (lv_elements_18_0= RULE_FALSE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5893:1: (lv_elements_18_0= RULE_FALSE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5894:3: lv_elements_18_0= RULE_FALSE
                    {
                    lv_elements_18_0=(Token)input.LT(1);
                    match(input,RULE_FALSE,FOLLOW_RULE_FALSE_in_ruleLiteral8489); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5917:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5917:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5918:1: (lv_parameters_19_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5918:1: (lv_parameters_19_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5919:3: lv_parameters_19_0= ruleTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral8521);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5942:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5942:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5943:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5943:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5944:3: lv_parameters_20_0= ruleExistsTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral8548);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5967:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5967:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5968:1: (lv_parameters_21_0= ruleMapGetReference )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5968:1: (lv_parameters_21_0= ruleMapGetReference )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5969:3: lv_parameters_21_0= ruleMapGetReference
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapGetReference_in_ruleLiteral8575);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5992:6: ( (lv_parameters_22_0= ruleDateLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5992:6: ( (lv_parameters_22_0= ruleDateLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5993:1: (lv_parameters_22_0= ruleDateLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5993:1: (lv_parameters_22_0= ruleDateLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5994:3: lv_parameters_22_0= ruleDateLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersDateLiteralParserRuleCall_12_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleDateLiteral_in_ruleLiteral8602);
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
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_START_in_ruleTml119 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleTml148 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleTml160 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleTml181 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_ruleMap_in_ruleTml208 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_ruleParam_in_ruleTml235 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_ruleMethods_in_ruleTml262 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleTml289 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_ruleInclude_in_ruleTml316 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_ruleValidations_in_ruleTml343 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_ruleComment_in_ruleTml370 = new BitSet(new long[]{0x000004102A222040L});
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_END_in_ruleTml381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttributeName_in_entryRuleAttributeName433 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAttributeName444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAttributeName483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression527 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePossibleExpression537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePossibleExpression580 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_rulePossibleExpression595 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleAttributeName_in_rulePossibleExpression618 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_rulePossibleExpression628 = new BitSet(new long[]{0x0000000000001A00L});
    public static final BitSet FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression639 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleTopLevel_in_rulePossibleExpression659 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethods_in_entryRuleMethods761 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethods771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_METHODS_START_TAG_in_ruleMethods805 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethods832 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_ruleMethod_in_ruleMethods858 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_RULE_METHODS_END_TAG_in_ruleMethods876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethod_in_entryRuleMethod933 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethod943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_METHOD_START_TAG_in_ruleMethod977 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMethod1006 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethod1026 = new BitSet(new long[]{0x0000000100010000L});
    public static final BitSet FOLLOW_ruleRequired_in_ruleMethod1052 = new BitSet(new long[]{0x0000000100010000L});
    public static final BitSet FOLLOW_RULE_METHOD_END_TAG_in_ruleMethod1070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod1091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValidations_in_entryRuleValidations1127 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValidations1137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALIDATIONS_START_TAG_in_ruleValidations1171 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleValidations1198 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_ruleCheck_in_ruleValidations1224 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_RULE_VALIDATIONS_END_TAG_in_ruleValidations1242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleValidations1263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCheck_in_entryRuleCheck1299 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCheck1309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_CHECK_START_TAG_in_ruleCheck1343 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleCheck1372 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleCheck1392 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleCheck1418 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RULE_CHECK_END_TAG_in_ruleCheck1435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleCheck1456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComment_in_entryRuleComment1492 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComment1502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_COMMENT_START_TAG_in_ruleComment1536 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleComment1565 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleComment1585 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_RULE_COMMENT_END_TAG_in_ruleComment1607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleComment1628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBreak_in_entryRuleBreak1664 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBreak1674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BREAK_START_TAG_in_ruleBreak1708 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleBreak1737 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleBreak1757 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_RULE_BREAK_END_TAG_in_ruleBreak1779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleBreak1800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInclude_in_entryRuleInclude1836 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInclude1846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INCLUDE_START_TAG_in_ruleInclude1880 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleInclude1909 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleInclude1929 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_RULE_INCLUDE_END_TAG_in_ruleInclude1951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleInclude1972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage2008 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage2018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MESSAGE_START_TAG_in_ruleMessage2052 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMessage2081 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMessage2101 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMessage2128 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMessage2155 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMessage2182 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMessage2209 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMessage2236 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMessage2263 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMessage2290 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMessage2317 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMessage2344 = new BitSet(new long[]{0x0000055438A00000L});
    public static final BitSet FOLLOW_RULE_MESSAGE_END_TAG_in_ruleMessage2363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage2384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_entryRuleMap2420 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMap2430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MAPSTARTKEYWORD_in_ruleMap2464 = new BitSet(new long[]{0x00000000400001A0L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMap2483 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMap2503 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap2524 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap2553 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap2565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap2588 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMap2615 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMap2642 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMap2669 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMap2696 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMap2723 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMap2750 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMap2777 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMap2804 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMap2831 = new BitSet(new long[]{0x00000554A8A00000L});
    public static final BitSet FOLLOW_RULE_MAPENDKEYWORD_in_ruleMap2843 = new BitSet(new long[]{0x0000000040000020L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMap2852 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMap2872 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap2891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapId_in_entryRuleMapId2936 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapId2947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapId2986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequired_in_entryRuleRequired3030 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequired3040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_REQUIRED_START_TAG_in_ruleRequired3074 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleRequired3103 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleRequired3114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleRequired3137 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_RULE_REQUIRED_END_TAG_in_ruleRequired3159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty3202 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty3212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PROPERTY_START_TAG_in_ruleProperty3246 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleProperty3275 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty3286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleProperty3309 = new BitSet(new long[]{0x0000300820000000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleProperty3336 = new BitSet(new long[]{0x0000300820000000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleProperty3363 = new BitSet(new long[]{0x0000300820000000L});
    public static final BitSet FOLLOW_RULE_PROPERTY_END_TAG_in_ruleProperty3382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParam_in_entryRuleParam3425 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParam3435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARAM_START_TAG_in_ruleParam3469 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleParam3498 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam3509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleParam3532 = new BitSet(new long[]{0x0000302020000000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleParam3559 = new BitSet(new long[]{0x0000302020000000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleParam3586 = new BitSet(new long[]{0x0000302020000000L});
    public static final BitSet FOLLOW_RULE_PARAM_END_TAG_in_ruleParam3605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapMethod_in_entryRuleMapMethod3648 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapMethod3658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MAP_METHOD_STARTTAG_START_in_ruleMapMethod3692 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod3708 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMapMethod3722 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleAttributeName_in_ruleMapMethod3742 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMapMethod3763 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod3774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod3797 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleMapMethod3824 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMapMethod3851 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMapMethod3878 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMapMethod3905 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMapMethod3932 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMapMethod3959 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMapMethod3986 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMapMethod4013 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMapMethod4040 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMapMethod4067 = new BitSet(new long[]{0x000035D428A00000L});
    public static final BitSet FOLLOW_RULE_MAP_METHOD_ENDTAG_START_in_ruleMapMethod4086 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod4108 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMapMethod4122 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod4138 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod4152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleField_in_entryRuleField4189 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleField4199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FIELD_START_TAG_in_ruleField4233 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleField4262 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleField4273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleField4297 = new BitSet(new long[]{0x0000365020A00000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleField4324 = new BitSet(new long[]{0x0000365020A00000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleField4351 = new BitSet(new long[]{0x0000365020A00000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleField4378 = new BitSet(new long[]{0x0000365020A00000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleField4405 = new BitSet(new long[]{0x0000365020A00000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleField4432 = new BitSet(new long[]{0x0000365020A00000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleField4459 = new BitSet(new long[]{0x0000365020A00000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleField4486 = new BitSet(new long[]{0x0000365020A00000L});
    public static final BitSet FOLLOW_RULE_FIELD_END_TAG_in_ruleField4506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDebugTag_in_entryRuleDebugTag4549 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDebugTag4559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DEBUG_START_TAG_in_ruleDebugTag4593 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleDebugTag4622 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag4633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag4656 = new BitSet(new long[]{0xF862080000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleDebugTag4682 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_RULE_DEBUG_END_TAG_in_ruleDebugTag4700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_entryRuleExpressionOrOption4743 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionOrOption4753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EXPRESSION_START_TAG_in_ruleExpressionOrOption4788 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleExpressionOrOption4809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_OPTION_START_TAG_in_ruleExpressionOrOption4825 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_ruleOption_in_ruleExpressionOrOption4846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag4882 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag4892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleExpressionTag4947 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag4958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag4981 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleExpressionTag5007 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RULE_EXPRESSION_END_TAG_in_ruleExpressionTag5024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOption_in_entryRuleOption5067 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOption5077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleOption5132 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleOption5143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleOption5166 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_RULE_OPTION_END_TAG_in_ruleOption5188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel5231 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel5241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel5286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement5322 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement5333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement5373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOT_in_rulePathElement5397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARENT_in_rulePathElement5418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression5463 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression5473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression5507 = new BitSet(new long[]{0x000D000040000100L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5523 = new BitSet(new long[]{0x0009000040000100L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleTmlExpression5546 = new BitSet(new long[]{0x0001000040000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression5573 = new BitSet(new long[]{0x0014000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5583 = new BitSet(new long[]{0x0001000040000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression5603 = new BitSet(new long[]{0x0014000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression5614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression5649 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression5659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression5693 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression5701 = new BitSet(new long[]{0x000D000040000100L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5717 = new BitSet(new long[]{0x0009000040000100L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleExistsTmlExpression5740 = new BitSet(new long[]{0x0001000040000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression5767 = new BitSet(new long[]{0x0014000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5777 = new BitSet(new long[]{0x0001000040000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression5797 = new BitSet(new long[]{0x0014000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression5808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams5843 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapReferenceParams5853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_ruleMapReferenceParams5888 = new BitSet(new long[]{0xF862000000000100L,0x0000000000400003L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams5909 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_75_in_ruleMapReferenceParams5920 = new BitSet(new long[]{0xF862000000000100L,0x0000000000400003L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams5941 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_76_in_ruleMapReferenceParams5953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference5989 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference5999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOLLAR_in_ruleMapGetReference6041 = new BitSet(new long[]{0x0001000000000100L});
    public static final BitSet FOLLOW_RULE_PARENT_in_ruleMapGetReference6064 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference6078 = new BitSet(new long[]{0x0001000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapGetReference6096 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference6122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression6159 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression6169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression6215 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_ruleOrExpression6234 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression6268 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression6306 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression6316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression6362 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_ruleAndExpression6381 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression6415 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression6453 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression6463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6509 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_79_in_ruleEqualityExpression6529 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_ruleEqualityExpression6589 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression6662 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRelationalExpression6672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6727 = new BitSet(new long[]{0x0780000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LT_in_ruleRelationalExpression6746 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GT_in_ruleRelationalExpression6797 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression6848 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression6899 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression6964 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression6974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7020 = new BitSet(new long[]{0x0000000000000002L,0x0000000000060000L});
    public static final BitSet FOLLOW_81_in_ruleAdditiveExpression7032 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7053 = new BitSet(new long[]{0x0000000000000002L,0x0000000000060000L});
    public static final BitSet FOLLOW_82_in_ruleAdditiveExpression7071 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7092 = new BitSet(new long[]{0x0000000000000002L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression7131 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression7141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7187 = new BitSet(new long[]{0x0004000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleMultiplicativeExpression7207 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7241 = new BitSet(new long[]{0x0004000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression7266 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7292 = new BitSet(new long[]{0x0004000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression7331 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression7341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_ruleUnaryExpression7385 = new BitSet(new long[]{0xF862000000000100L,0x0000000000400403L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression7483 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression7493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression7539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_rulePrimaryExpression7556 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression7577 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_rulePrimaryExpression7587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName7625 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName7636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName7675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall7719 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall7729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_ruleFunctionCall7775 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_ruleFunctionCall7785 = new BitSet(new long[]{0xF862000000000100L,0x0000000000501C03L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall7806 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_75_in_ruleFunctionCall7818 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall7839 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_76_in_ruleFunctionCall7851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral7887 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDateLiteral7897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral7940 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_ruleDateLiteral7949 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral7958 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_ruleDateLiteral7967 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral7976 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_ruleDateLiteral7985 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral7994 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_ruleDateLiteral8003 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8012 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_ruleDateLiteral8021 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral8065 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral8075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleLiteral8119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FORALL_in_ruleLiteral8171 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_ruleLiteral8186 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8203 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_ruleLiteral8218 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8239 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ruleLiteral8249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral8277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_ruleLiteral8302 = new BitSet(new long[]{0xF862000000000100L,0x0000000000D00C03L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8336 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800800L});
    public static final BitSet FOLLOW_75_in_ruleLiteral8348 = new BitSet(new long[]{0xF862000000000100L,0x0000000000500403L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8369 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800800L});
    public static final BitSet FOLLOW_87_in_ruleLiteral8381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NULL_in_ruleLiteral8405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TODAY_in_ruleLiteral8433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TRUE_in_ruleLiteral8461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FALSE_in_ruleLiteral8489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral8521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral8548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_ruleLiteral8575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_ruleLiteral8602 = new BitSet(new long[]{0x0000000000000002L});

}