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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_NAVASCRIPT_START", "RULE_XML_TAG_END", "RULE_NAVASCRIPT_END", "RULE_XML_TAG_SINGLEEND", "RULE_ID", "RULE_QUOTEQ", "RULE_SEMICOLONQUOTE", "RULE_ATTRIBUTESTRING", "RULE_EMPTYSTRING", "RULE_METHODS_START_TAG", "RULE_METHODS_END_TAG", "RULE_METHOD_START_TAG", "RULE_METHOD_END_TAG", "RULE_MESSAGE_START_TAG", "RULE_MESSAGE_END_TAG", "RULE_MAPENDKEYWORD", "RULE_MAPSTARTKEYWORD", "RULE_PROPERTY_START_TAG", "RULE_PROPERTY_END_TAG", "RULE_PARAM_START_TAG", "RULE_PARAM_END_TAG", "RULE_XML_TAG_START", "RULE_CDATA", "RULE_XML_START_ENDTAG", "RULE_DEBUG_START_TAG", "RULE_DEBUG_END_TAG", "RULE_EXPRESSION_START_TAG", "RULE_EXPRESSION_END_TAG", "RULE_SQBRACKET_OPEN", "RULE_TML_SEPARATOR", "RULE_AT", "RULE_SQBRACKET_CLOSE", "RULE_TML_EXISTS", "RULE_DOLLAR", "RULE_XML_LT", "RULE_XML_GT", "RULE_XML_LTEQ", "RULE_XML_GTEQ", "RULE_INT", "RULE_LITERALSTRING", "RULE_XMLHEAD", "RULE_XMLCOMMENT", "RULE_FIELD_END_TAG", "RULE_FIELD_START_TAG", "RULE_NAVASCRIPT_KEYWORD", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "':'", "'='", "'.'", "'..'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'('", "')'", "','", "'FORALL'", "'{'", "'}'", "'NULL'", "'TODAY'", "'TRUE'", "'FALSE'"
    };
    public static final int RULE_NAVASCRIPT_END=6;
    public static final int RULE_ID=8;
    public static final int RULE_XMLCOMMENT=45;
    public static final int RULE_SQBRACKET_OPEN=32;
    public static final int RULE_QUOTEQ=9;
    public static final int RULE_EXPRESSION_END_TAG=31;
    public static final int RULE_XMLHEAD=44;
    public static final int RULE_METHODS_END_TAG=14;
    public static final int RULE_LITERALSTRING=43;
    public static final int EOF=-1;
    public static final int RULE_EMPTYSTRING=12;
    public static final int RULE_METHOD_START_TAG=15;
    public static final int RULE_FIELD_START_TAG=47;
    public static final int RULE_XML_LTEQ=40;
    public static final int RULE_INT=42;
    public static final int RULE_METHOD_END_TAG=16;
    public static final int RULE_MAPENDKEYWORD=19;
    public static final int RULE_XML_TAG_START=25;
    public static final int RULE_DEBUG_START_TAG=28;
    public static final int RULE_FIELD_END_TAG=46;
    public static final int RULE_XML_TAG_SINGLEEND=7;
    public static final int RULE_PROPERTY_START_TAG=21;
    public static final int RULE_ATTRIBUTESTRING=11;
    public static final int RULE_XML_TAG_END=5;
    public static final int RULE_MESSAGE_START_TAG=17;
    public static final int RULE_NAVASCRIPT_KEYWORD=48;
    public static final int RULE_CDATA=26;
    public static final int RULE_XML_LT=38;
    public static final int RULE_MESSAGE_END_TAG=18;
    public static final int RULE_XML_GTEQ=41;
    public static final int RULE_TML_SEPARATOR=33;
    public static final int RULE_SL_COMMENT=50;
    public static final int RULE_ML_COMMENT=49;
    public static final int RULE_PROPERTY_END_TAG=22;
    public static final int RULE_EXPRESSION_START_TAG=30;
    public static final int RULE_DOLLAR=37;
    public static final int RULE_TML_EXISTS=36;
    public static final int RULE_NAVASCRIPT_START=4;
    public static final int RULE_SQBRACKET_CLOSE=35;
    public static final int RULE_DEBUG_END_TAG=29;
    public static final int RULE_METHODS_START_TAG=13;
    public static final int RULE_MAPSTARTKEYWORD=20;
    public static final int RULE_SEMICOLONQUOTE=10;
    public static final int RULE_XML_START_ENDTAG=27;
    public static final int RULE_WS=51;
    public static final int RULE_XML_GT=39;
    public static final int RULE_PARAM_END_TAG=24;
    public static final int RULE_PARAM_START_TAG=23;
    public static final int RULE_AT=34;

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:86:1: ruleTml returns [EObject current=null] : ( RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleTml() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_methods_7_0 = null;

        EObject lv_children_8_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:91:6: ( ( RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:2: RULE_NAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("131:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:5: RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* RULE_NAVASCRIPT_END
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleTml160); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )*
                    loop2:
                    do {
                        int alt2=6;
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

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    match(input,RULE_NAVASCRIPT_END,FOLLOW_RULE_NAVASCRIPT_END_in_ruleTml300); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getNAVASCRIPT_ENDTerminalRuleCall_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:264:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml315); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:276:1: entryRuleAttributeName returns [String current=null] : iv_ruleAttributeName= ruleAttributeName EOF ;
    public final String entryRuleAttributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAttributeName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:277:2: (iv_ruleAttributeName= ruleAttributeName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:278:2: iv_ruleAttributeName= ruleAttributeName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAttributeNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleAttributeName_in_entryRuleAttributeName352);
            iv_ruleAttributeName=ruleAttributeName();
            _fsp--;

             current =iv_ruleAttributeName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAttributeName363); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:285:1: ruleAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleAttributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:290:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:291:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAttributeName402); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:306:1: entryRulePossibleExpression returns [EObject current=null] : iv_rulePossibleExpression= rulePossibleExpression EOF ;
    public final EObject entryRulePossibleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePossibleExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:307:2: (iv_rulePossibleExpression= rulePossibleExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:308:2: iv_rulePossibleExpression= rulePossibleExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPossibleExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression446);
            iv_rulePossibleExpression=rulePossibleExpression();
            _fsp--;

             current =iv_rulePossibleExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePossibleExpression456); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:315:1: rulePossibleExpression returns [EObject current=null] : ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) ) ;
    public final EObject rulePossibleExpression() throws RecognitionException {
        EObject current = null;

        Token lv_namespace_0_0=null;
        Token lv_value_7_0=null;
        AntlrDatatypeRuleToken lv_key_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:320:6: ( ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:321:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:321:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:321:2: ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:321:2: ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_ID) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==52) ) {
                    alt4=1;
                }
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:321:3: ( (lv_namespace_0_0= RULE_ID ) ) ':'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:321:3: ( (lv_namespace_0_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:322:1: (lv_namespace_0_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:322:1: (lv_namespace_0_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:323:3: lv_namespace_0_0= RULE_ID
                    {
                    lv_namespace_0_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePossibleExpression499); 

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

                    match(input,52,FOLLOW_52_in_rulePossibleExpression514); 

                            createLeafNode(grammarAccess.getPossibleExpressionAccess().getColonKeyword_0_1(), null); 
                        

                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:349:3: ( (lv_key_2_0= ruleAttributeName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:350:1: (lv_key_2_0= ruleAttributeName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:350:1: (lv_key_2_0= ruleAttributeName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:351:3: lv_key_2_0= ruleAttributeName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getKeyAttributeNameParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAttributeName_in_rulePossibleExpression537);
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

            match(input,53,FOLLOW_53_in_rulePossibleExpression547); 

                    createLeafNode(grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:377:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )
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
                    new NoViableAltException("377:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:377:2: ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:377:2: ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:377:3: RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE
                    {
                    match(input,RULE_QUOTEQ,FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression558); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getQUOTEQTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:381:1: ( (lv_expressionValue_5_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:382:1: (lv_expressionValue_5_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:382:1: (lv_expressionValue_5_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:383:3: lv_expressionValue_5_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_3_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_rulePossibleExpression578);
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

                    match(input,RULE_SEMICOLONQUOTE,FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression587); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getSEMICOLONQUOTETerminalRuleCall_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:410:6: ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:410:6: ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:411:1: (lv_value_7_0= RULE_ATTRIBUTESTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:411:1: (lv_value_7_0= RULE_ATTRIBUTESTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:412:3: lv_value_7_0= RULE_ATTRIBUTESTRING
                    {
                    lv_value_7_0=(Token)input.LT(1);
                    match(input,RULE_ATTRIBUTESTRING,FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression610); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:435:6: RULE_EMPTYSTRING
                    {
                    match(input,RULE_EMPTYSTRING,FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression630); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getEMPTYSTRINGTerminalRuleCall_3_2(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:447:1: entryRuleMethods returns [EObject current=null] : iv_ruleMethods= ruleMethods EOF ;
    public final EObject entryRuleMethods() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethods = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:448:2: (iv_ruleMethods= ruleMethods EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:449:2: iv_ruleMethods= ruleMethods EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMethodsRule(), currentNode); 
            pushFollow(FOLLOW_ruleMethods_in_entryRuleMethods666);
            iv_ruleMethods=ruleMethods();
            _fsp--;

             current =iv_ruleMethods; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethods676); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:456:1: ruleMethods returns [EObject current=null] : ( RULE_METHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethods() throws RecognitionException {
        EObject current = null;

        EObject lv_method_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:461:6: ( ( RULE_METHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:462:1: ( RULE_METHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:462:1: ( RULE_METHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:462:2: RULE_METHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_METHODS_START_TAG,FOLLOW_RULE_METHODS_START_TAG_in_ruleMethods710); 
             
                createLeafNode(grammarAccess.getMethodsAccess().getMETHODS_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:466:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:467:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:477:2: ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("477:2: ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:477:3: ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:477:3: ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:477:4: RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* RULE_METHODS_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethods729); 
                     
                        createLeafNode(grammarAccess.getMethodsAccess().getXML_TAG_ENDTerminalRuleCall_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:481:1: ( (lv_method_3_0= ruleMethod ) )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==RULE_METHOD_START_TAG) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:482:1: (lv_method_3_0= ruleMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:482:1: (lv_method_3_0= ruleMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:483:3: lv_method_3_0= ruleMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMethodsAccess().getMethodMethodParserRuleCall_2_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethod_in_ruleMethods749);
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

                    match(input,RULE_METHODS_END_TAG,FOLLOW_RULE_METHODS_END_TAG_in_ruleMethods759); 
                     
                        createLeafNode(grammarAccess.getMethodsAccess().getMETHODS_END_TAGTerminalRuleCall_2_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:510:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods774); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:522:1: entryRuleMethod returns [EObject current=null] : iv_ruleMethod= ruleMethod EOF ;
    public final EObject entryRuleMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:523:2: (iv_ruleMethod= ruleMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:524:2: iv_ruleMethod= ruleMethod EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMethodRule(), currentNode); 
            pushFollow(FOLLOW_ruleMethod_in_entryRuleMethod810);
            iv_ruleMethod=ruleMethod();
            _fsp--;

             current =iv_ruleMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethod820); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:531:1: ruleMethod returns [EObject current=null] : ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END RULE_METHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethod() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:536:6: ( ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END RULE_METHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:537:1: ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END RULE_METHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:537:1: ( RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END RULE_METHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:537:2: RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END RULE_METHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_METHOD_START_TAG,FOLLOW_RULE_METHOD_START_TAG_in_ruleMethod854); 
             
                createLeafNode(grammarAccess.getMethodAccess().getMETHOD_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:541:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:542:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:552:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ID) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:553:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:553:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:554:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMethodAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMethod883);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:576:3: ( ( RULE_XML_TAG_END RULE_METHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_XML_TAG_END) ) {
                alt9=1;
            }
            else if ( (LA9_0==RULE_XML_TAG_SINGLEEND) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("576:3: ( ( RULE_XML_TAG_END RULE_METHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:576:4: ( RULE_XML_TAG_END RULE_METHOD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:576:4: ( RULE_XML_TAG_END RULE_METHOD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:576:5: RULE_XML_TAG_END RULE_METHOD_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethod895); 
                     
                        createLeafNode(grammarAccess.getMethodAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                    match(input,RULE_METHOD_END_TAG,FOLLOW_RULE_METHOD_END_TAG_in_ruleMethod903); 
                     
                        createLeafNode(grammarAccess.getMethodAccess().getMETHOD_END_TAGTerminalRuleCall_3_0_1(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:585:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod918); 
                     
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


    // $ANTLR start entryRuleMessage
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:597:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:598:2: (iv_ruleMessage= ruleMessage EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:599:2: iv_ruleMessage= ruleMessage EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMessageRule(), currentNode); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage954);
            iv_ruleMessage=ruleMessage();
            _fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage964); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:606:1: ruleMessage returns [EObject current=null] : ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_children_8_0 = null;

        EObject lv_children_9_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:611:6: ( ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:612:1: ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:612:1: ( RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:612:2: RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
            match(input,RULE_MESSAGE_START_TAG,FOLLOW_RULE_MESSAGE_START_TAG_in_ruleMessage998); 
             
                createLeafNode(grammarAccess.getMessageAccess().getMESSAGE_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:616:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:617:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:627:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:628:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:628:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:629:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMessage1027);
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
            	    break loop10;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:651:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("651:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:651:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:651:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:651:5: RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )* RULE_MESSAGE_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMessage1039); 
                     
                        createLeafNode(grammarAccess.getMessageAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:655:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) )*
                    loop11:
                    do {
                        int alt11=7;
                        switch ( input.LA(1) ) {
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt11=1;
                            }
                            break;
                        case RULE_PROPERTY_START_TAG:
                            {
                            alt11=2;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt11=3;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt11=4;
                            }
                            break;
                        case RULE_XML_TAG_START:
                            {
                            alt11=5;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt11=6;
                            }
                            break;

                        }

                        switch (alt11) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:655:2: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:655:2: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:656:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:656:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:657:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMessage1060);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:680:6: ( (lv_children_5_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:680:6: ( (lv_children_5_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:681:1: (lv_children_5_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:681:1: (lv_children_5_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:682:3: lv_children_5_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMessage1087);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:705:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:705:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:706:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:706:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:707:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenParamParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMessage1114);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:730:6: ( (lv_children_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:730:6: ( (lv_children_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:731:1: (lv_children_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:731:1: (lv_children_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:732:3: lv_children_7_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMessage1141);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:755:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:755:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:756:1: (lv_children_8_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:756:1: (lv_children_8_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:757:3: lv_children_8_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapMethodParserRuleCall_3_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMessage1168);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:780:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:780:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:781:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:781:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:782:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenDebugTagParserRuleCall_3_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMessage1195);
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

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    match(input,RULE_MESSAGE_END_TAG,FOLLOW_RULE_MESSAGE_END_TAG_in_ruleMessage1206); 
                     
                        createLeafNode(grammarAccess.getMessageAccess().getMESSAGE_END_TAGTerminalRuleCall_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:809:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage1221); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:821:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:822:2: (iv_ruleMap= ruleMap EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:823:2: iv_ruleMap= ruleMap EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapRule(), currentNode); 
            pushFollow(FOLLOW_ruleMap_in_entryRuleMap1257);
            iv_ruleMap=ruleMap();
            _fsp--;

             current =iv_ruleMap; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMap1267); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:830:1: ruleMap returns [EObject current=null] : (this_MapStart_0= ruleMapStart ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) ) ) ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        EObject this_MapStart_0 = null;

        EObject lv_children_3_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_children_8_0 = null;

        AntlrDatatypeRuleToken lv_mapClosingName_9_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:835:6: ( (this_MapStart_0= ruleMapStart ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:836:1: (this_MapStart_0= ruleMapStart ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:836:1: (this_MapStart_0= ruleMapStart ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:837:5: this_MapStart_0= ruleMapStart ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) ) )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapStartParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleMapStart_in_ruleMap1314);
            this_MapStart_0=ruleMapStart();
            _fsp--;

             
                    current = this_MapStart_0; 
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:845:1: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_XML_TAG_SINGLEEND) ) {
                alt14=1;
            }
            else if ( (LA14_0==RULE_XML_TAG_END) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("845:1: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) ) )", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:845:2: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap1323); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getXML_TAG_SINGLEENDTerminalRuleCall_1_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:6: ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:6: ( RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:7: RULE_XML_TAG_END ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )* ( (lv_mapClosingName_9_0= ruleMapEnd ) )
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap1338); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getXML_TAG_ENDTerminalRuleCall_1_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:854:1: ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleParam ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleMapMethod ) ) | ( (lv_children_8_0= ruleDebugTag ) ) )*
                    loop13:
                    do {
                        int alt13=7;
                        switch ( input.LA(1) ) {
                        case RULE_MESSAGE_START_TAG:
                            {
                            alt13=1;
                            }
                            break;
                        case RULE_PROPERTY_START_TAG:
                            {
                            alt13=2;
                            }
                            break;
                        case RULE_PARAM_START_TAG:
                            {
                            alt13=3;
                            }
                            break;
                        case RULE_MAPSTARTKEYWORD:
                            {
                            alt13=4;
                            }
                            break;
                        case RULE_XML_TAG_START:
                            {
                            alt13=5;
                            }
                            break;
                        case RULE_DEBUG_START_TAG:
                            {
                            alt13=6;
                            }
                            break;

                        }

                        switch (alt13) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:854:2: ( (lv_children_3_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:854:2: ( (lv_children_3_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:855:1: (lv_children_3_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:855:1: (lv_children_3_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:856:3: lv_children_3_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_1_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMap1359);
                    	    lv_children_3_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_3_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:879:6: ( (lv_children_4_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:879:6: ( (lv_children_4_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:880:1: (lv_children_4_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:880:1: (lv_children_4_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:881:3: lv_children_4_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_1_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMap1386);
                    	    lv_children_4_0=ruleProperty();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_4_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:904:6: ( (lv_children_5_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:904:6: ( (lv_children_5_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:905:1: (lv_children_5_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:905:1: (lv_children_5_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:906:3: lv_children_5_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenParamParserRuleCall_1_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMap1413);
                    	    lv_children_5_0=ruleParam();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_5_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:929:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:929:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:930:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:930:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:931:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_1_1_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMap1440);
                    	    lv_children_6_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
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
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:954:6: ( (lv_children_7_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:954:6: ( (lv_children_7_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:955:1: (lv_children_7_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:955:1: (lv_children_7_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:956:3: lv_children_7_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapMethodParserRuleCall_1_1_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMap1467);
                    	    lv_children_7_0=ruleMapMethod();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_7_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:979:6: ( (lv_children_8_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:979:6: ( (lv_children_8_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:980:1: (lv_children_8_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:980:1: (lv_children_8_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:981:3: lv_children_8_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenDebugTagParserRuleCall_1_1_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMap1494);
                    	    lv_children_8_0=ruleDebugTag();
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

                    	default :
                    	    break loop13;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1003:4: ( (lv_mapClosingName_9_0= ruleMapEnd ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1004:1: (lv_mapClosingName_9_0= ruleMapEnd )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1004:1: (lv_mapClosingName_9_0= ruleMapEnd )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1005:3: lv_mapClosingName_9_0= ruleMapEnd
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapClosingNameMapEndParserRuleCall_1_1_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapEnd_in_ruleMap1517);
                    lv_mapClosingName_9_0=ruleMapEnd();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"mapClosingName",
                    	        		lv_mapClosingName_9_0, 
                    	        		"MapEnd", 
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
    // $ANTLR end ruleMap


    // $ANTLR start entryRuleMapEnd
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1035:1: entryRuleMapEnd returns [String current=null] : iv_ruleMapEnd= ruleMapEnd EOF ;
    public final String entryRuleMapEnd() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMapEnd = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1036:2: (iv_ruleMapEnd= ruleMapEnd EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1037:2: iv_ruleMapEnd= ruleMapEnd EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapEndRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapEnd_in_entryRuleMapEnd1556);
            iv_ruleMapEnd=ruleMapEnd();
            _fsp--;

             current =iv_ruleMapEnd.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapEnd1567); 

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
    // $ANTLR end entryRuleMapEnd


    // $ANTLR start ruleMapEnd
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1044:1: ruleMapEnd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_MAPENDKEYWORD_0= RULE_MAPENDKEYWORD this_ID_1= RULE_ID this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleMapEnd() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_MAPENDKEYWORD_0=null;
        Token this_ID_1=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1049:6: ( (this_MAPENDKEYWORD_0= RULE_MAPENDKEYWORD this_ID_1= RULE_ID this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1050:1: (this_MAPENDKEYWORD_0= RULE_MAPENDKEYWORD this_ID_1= RULE_ID this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1050:1: (this_MAPENDKEYWORD_0= RULE_MAPENDKEYWORD this_ID_1= RULE_ID this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1050:6: this_MAPENDKEYWORD_0= RULE_MAPENDKEYWORD this_ID_1= RULE_ID this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_MAPENDKEYWORD_0=(Token)input.LT(1);
            match(input,RULE_MAPENDKEYWORD,FOLLOW_RULE_MAPENDKEYWORD_in_ruleMapEnd1607); 

            		current.merge(this_MAPENDKEYWORD_0);
                
             
                createLeafNode(grammarAccess.getMapEndAccess().getMAPENDKEYWORDTerminalRuleCall_0(), null); 
                
            this_ID_1=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapEnd1627); 

            		current.merge(this_ID_1);
                
             
                createLeafNode(grammarAccess.getMapEndAccess().getIDTerminalRuleCall_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapEnd1647); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getMapEndAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleMapEnd


    // $ANTLR start entryRuleMapId
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1079:1: entryRuleMapId returns [String current=null] : iv_ruleMapId= ruleMapId EOF ;
    public final String entryRuleMapId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMapId = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1080:2: (iv_ruleMapId= ruleMapId EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1081:2: iv_ruleMapId= ruleMapId EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapIdRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapId_in_entryRuleMapId1693);
            iv_ruleMapId=ruleMapId();
            _fsp--;

             current =iv_ruleMapId.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapId1704); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1088:1: ruleMapId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleMapId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1093:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1094:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapId1743); 

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


    // $ANTLR start entryRuleMapStart
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1109:1: entryRuleMapStart returns [EObject current=null] : iv_ruleMapStart= ruleMapStart EOF ;
    public final EObject entryRuleMapStart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapStart = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1110:2: (iv_ruleMapStart= ruleMapStart EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1111:2: iv_ruleMapStart= ruleMapStart EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapStartRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapStart_in_entryRuleMapStart1787);
            iv_ruleMapStart=ruleMapStart();
            _fsp--;

             current =iv_ruleMapStart; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapStart1797); 

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
    // $ANTLR end entryRuleMapStart


    // $ANTLR start ruleMapStart
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1118:1: ruleMapStart returns [EObject current=null] : ( () RULE_MAPSTARTKEYWORD ( (lv_mapName_2_0= ruleMapId ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )* ) ;
    public final EObject ruleMapStart() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_mapName_2_0 = null;

        EObject lv_attributes_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1123:6: ( ( () RULE_MAPSTARTKEYWORD ( (lv_mapName_2_0= ruleMapId ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1124:1: ( () RULE_MAPSTARTKEYWORD ( (lv_mapName_2_0= ruleMapId ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1124:1: ( () RULE_MAPSTARTKEYWORD ( (lv_mapName_2_0= ruleMapId ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1124:2: () RULE_MAPSTARTKEYWORD ( (lv_mapName_2_0= ruleMapId ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1124:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1125:5: 
            {
             
                    temp=factory.create(grammarAccess.getMapStartAccess().getMapAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getMapStartAccess().getMapAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            match(input,RULE_MAPSTARTKEYWORD,FOLLOW_RULE_MAPSTARTKEYWORD_in_ruleMapStart1840); 
             
                createLeafNode(grammarAccess.getMapStartAccess().getMAPSTARTKEYWORDTerminalRuleCall_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1139:1: ( (lv_mapName_2_0= ruleMapId ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1140:1: (lv_mapName_2_0= ruleMapId )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1140:1: (lv_mapName_2_0= ruleMapId )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1141:3: lv_mapName_2_0= ruleMapId
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapStartAccess().getMapNameMapIdParserRuleCall_2_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMapId_in_ruleMapStart1860);
            lv_mapName_2_0=ruleMapId();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapStartRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"mapName",
            	        		lv_mapName_2_0, 
            	        		"MapId", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1163:2: ( (lv_attributes_3_0= rulePossibleExpression ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_ID) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1164:1: (lv_attributes_3_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1164:1: (lv_attributes_3_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1165:3: lv_attributes_3_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapStartAccess().getAttributesPossibleExpressionParserRuleCall_3_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMapStart1881);
            	    lv_attributes_3_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMapStartRule().getType().getClassifier());
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
            	    break loop15;
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
    // $ANTLR end ruleMapStart


    // $ANTLR start entryRuleProperty
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1195:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1196:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1197:2: iv_ruleProperty= ruleProperty EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPropertyRule(), currentNode); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty1918);
            iv_ruleProperty=ruleProperty();
            _fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty1928); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1204:1: ruleProperty returns [EObject current=null] : ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1209:6: ( ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1210:1: ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1210:1: ( RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1210:2: RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG ) )
            {
            match(input,RULE_PROPERTY_START_TAG,FOLLOW_RULE_PROPERTY_START_TAG_in_ruleProperty1962); 
             
                createLeafNode(grammarAccess.getPropertyAccess().getPROPERTY_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1214:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1215:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1225:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_ID) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1226:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1226:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1227:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleProperty1991);
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
            	    break loop16;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1249:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_XML_TAG_SINGLEEND) ) {
                alt18=1;
            }
            else if ( (LA18_0==RULE_XML_TAG_END) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1249:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG ) )", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1249:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty2002); 
                     
                        createLeafNode(grammarAccess.getPropertyAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1254:6: ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1254:6: ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1254:7: RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PROPERTY_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleProperty2017); 
                     
                        createLeafNode(grammarAccess.getPropertyAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1258:1: ( (lv_expressionValue_5_0= ruleExpressionTag ) )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==RULE_EXPRESSION_START_TAG) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1259:1: (lv_expressionValue_5_0= ruleExpressionTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1259:1: (lv_expressionValue_5_0= ruleExpressionTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1260:3: lv_expressionValue_5_0= ruleExpressionTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getExpressionValueExpressionTagParserRuleCall_3_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionTag_in_ruleProperty2037);
                    	    lv_expressionValue_5_0=ruleExpressionTag();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getPropertyRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"expressionValue",
                    	    	        		lv_expressionValue_5_0, 
                    	    	        		"ExpressionTag", 
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

                    match(input,RULE_PROPERTY_END_TAG,FOLLOW_RULE_PROPERTY_END_TAG_in_ruleProperty2047); 
                     
                        createLeafNode(grammarAccess.getPropertyAccess().getPROPERTY_END_TAGTerminalRuleCall_3_1_2(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1294:1: entryRuleParam returns [EObject current=null] : iv_ruleParam= ruleParam EOF ;
    public final EObject entryRuleParam() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1295:2: (iv_ruleParam= ruleParam EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1296:2: iv_ruleParam= ruleParam EOF
            {
             currentNode = createCompositeNode(grammarAccess.getParamRule(), currentNode); 
            pushFollow(FOLLOW_ruleParam_in_entryRuleParam2084);
            iv_ruleParam=ruleParam();
            _fsp--;

             current =iv_ruleParam; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParam2094); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1303:1: ruleParam returns [EObject current=null] : ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG ) ) ) ;
    public final EObject ruleParam() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1308:6: ( ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1309:1: ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1309:1: ( RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1309:2: RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG ) )
            {
            match(input,RULE_PARAM_START_TAG,FOLLOW_RULE_PARAM_START_TAG_in_ruleParam2128); 
             
                createLeafNode(grammarAccess.getParamAccess().getPARAM_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1313:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1324:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==RULE_ID) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1325:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1325:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1326:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleParam2157);
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
            	    break loop19;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1348:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_XML_TAG_SINGLEEND) ) {
                alt21=1;
            }
            else if ( (LA21_0==RULE_XML_TAG_END) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1348:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG ) )", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1348:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam2168); 
                     
                        createLeafNode(grammarAccess.getParamAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1353:6: ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1353:6: ( RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1353:7: RULE_XML_TAG_END ( (lv_expressionValue_5_0= ruleExpressionTag ) )* RULE_PARAM_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleParam2183); 
                     
                        createLeafNode(grammarAccess.getParamAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1357:1: ( (lv_expressionValue_5_0= ruleExpressionTag ) )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==RULE_EXPRESSION_START_TAG) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:1: (lv_expressionValue_5_0= ruleExpressionTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:1: (lv_expressionValue_5_0= ruleExpressionTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1359:3: lv_expressionValue_5_0= ruleExpressionTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getExpressionValueExpressionTagParserRuleCall_3_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionTag_in_ruleParam2203);
                    	    lv_expressionValue_5_0=ruleExpressionTag();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getParamRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"expressionValue",
                    	    	        		lv_expressionValue_5_0, 
                    	    	        		"ExpressionTag", 
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

                    match(input,RULE_PARAM_END_TAG,FOLLOW_RULE_PARAM_END_TAG_in_ruleParam2213); 
                     
                        createLeafNode(grammarAccess.getParamAccess().getPARAM_END_TAGTerminalRuleCall_3_1_2(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1393:1: entryRuleMapMethod returns [EObject current=null] : iv_ruleMapMethod= ruleMapMethod EOF ;
    public final EObject entryRuleMapMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1394:2: (iv_ruleMapMethod= ruleMapMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1395:2: iv_ruleMapMethod= ruleMapMethod EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapMethodRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapMethod_in_entryRuleMapMethod2250);
            iv_ruleMapMethod=ruleMapMethod();
            _fsp--;

             current =iv_ruleMapMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapMethod2260); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1402:1: ruleMapMethod returns [EObject current=null] : ( RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) ) ) ;
    public final EObject ruleMapMethod() throws RecognitionException {
        EObject current = null;

        Token lv_mapName_1_0=null;
        Token lv_methodName_3_0=null;
        Token lv_methodClosingName_9_0=null;
        Token lv_methodClosingMethod_11_0=null;
        EObject lv_attributes_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1407:6: ( ( RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1408:1: ( RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1408:1: ( RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1408:2: RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) )
            {
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleMapMethod2294); 
             
                createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1412:1: ( (lv_mapName_1_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1413:1: (lv_mapName_1_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1413:1: (lv_mapName_1_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1414:3: lv_mapName_1_0= RULE_ID
            {
            lv_mapName_1_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod2310); 

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

            match(input,54,FOLLOW_54_in_ruleMapMethod2325); 

                    createLeafNode(grammarAccess.getMapMethodAccess().getFullStopKeyword_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1440:1: ( (lv_methodName_3_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1441:1: (lv_methodName_3_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1441:1: (lv_methodName_3_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1442:3: lv_methodName_3_0= RULE_ID
            {
            lv_methodName_3_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod2342); 

            			createLeafNode(grammarAccess.getMapMethodAccess().getMethodNameIDTerminalRuleCall_3_0(), "methodName"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"methodName",
            	        		lv_methodName_3_0, 
            	        		"ID", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1464:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==RULE_ID) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1465:1: (lv_attributes_4_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1465:1: (lv_attributes_4_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1466:3: lv_attributes_4_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getAttributesPossibleExpressionParserRuleCall_4_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMapMethod2368);
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
            	    break loop22;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1488:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RULE_XML_TAG_SINGLEEND) ) {
                alt24=1;
            }
            else if ( (LA24_0==RULE_XML_TAG_END) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1488:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) )", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1488:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod2379); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_SINGLEENDTerminalRuleCall_5_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1493:6: ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1493:6: ( RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1493:7: RULE_XML_TAG_END ( RULE_CDATA )* RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod2394); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_ENDTerminalRuleCall_5_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1497:1: ( RULE_CDATA )*
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==RULE_CDATA) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1497:2: RULE_CDATA
                    	    {
                    	    match(input,RULE_CDATA,FOLLOW_RULE_CDATA_in_ruleMapMethod2403); 
                    	     
                    	        createLeafNode(grammarAccess.getMapMethodAccess().getCDATATerminalRuleCall_5_1_1(), null); 
                    	        

                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);

                    match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleMapMethod2413); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_START_ENDTAGTerminalRuleCall_5_1_2(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1505:1: ( (lv_methodClosingName_9_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1506:1: (lv_methodClosingName_9_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1506:1: (lv_methodClosingName_9_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1507:3: lv_methodClosingName_9_0= RULE_ID
                    {
                    lv_methodClosingName_9_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod2429); 

                    			createLeafNode(grammarAccess.getMapMethodAccess().getMethodClosingNameIDTerminalRuleCall_5_1_3_0(), "methodClosingName"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"methodClosingName",
                    	        		lv_methodClosingName_9_0, 
                    	        		"ID", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,54,FOLLOW_54_in_ruleMapMethod2444); 

                            createLeafNode(grammarAccess.getMapMethodAccess().getFullStopKeyword_5_1_4(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1533:1: ( (lv_methodClosingMethod_11_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1534:1: (lv_methodClosingMethod_11_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1534:1: (lv_methodClosingMethod_11_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1535:3: lv_methodClosingMethod_11_0= RULE_ID
                    {
                    lv_methodClosingMethod_11_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod2461); 

                    			createLeafNode(grammarAccess.getMapMethodAccess().getMethodClosingMethodIDTerminalRuleCall_5_1_5_0(), "methodClosingMethod"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"methodClosingMethod",
                    	        		lv_methodClosingMethod_11_0, 
                    	        		"ID", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod2475); 
                     
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


    // $ANTLR start entryRuleDebugTag
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1571:1: entryRuleDebugTag returns [EObject current=null] : iv_ruleDebugTag= ruleDebugTag EOF ;
    public final EObject entryRuleDebugTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDebugTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1572:2: (iv_ruleDebugTag= ruleDebugTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1573:2: iv_ruleDebugTag= ruleDebugTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getDebugTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleDebugTag_in_entryRuleDebugTag2514);
            iv_ruleDebugTag=ruleDebugTag();
            _fsp--;

             current =iv_ruleDebugTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDebugTag2524); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1580:1: ruleDebugTag returns [EObject current=null] : ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG ) ) ) ;
    public final EObject ruleDebugTag() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expression_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1585:6: ( ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1586:1: ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1586:1: ( RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1586:2: RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG ) )
            {
            match(input,RULE_DEBUG_START_TAG,FOLLOW_RULE_DEBUG_START_TAG_in_ruleDebugTag2558); 
             
                createLeafNode(grammarAccess.getDebugTagAccess().getDEBUG_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1590:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1591:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1601:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==RULE_ID) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1602:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1602:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1603:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleDebugTag2587);
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
            	    break loop25;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1625:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_XML_TAG_SINGLEEND) ) {
                alt26=1;
            }
            else if ( (LA26_0==RULE_XML_TAG_END) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1625:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG ) )", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1625:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag2598); 
                     
                        createLeafNode(grammarAccess.getDebugTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1630:6: ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1630:6: ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1630:7: RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_DEBUG_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag2613); 
                     
                        createLeafNode(grammarAccess.getDebugTagAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1634:1: ( (lv_expression_5_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1635:1: (lv_expression_5_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1635:1: (lv_expression_5_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1636:3: lv_expression_5_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getExpressionTopLevelParserRuleCall_3_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleDebugTag2633);
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

                    match(input,RULE_DEBUG_END_TAG,FOLLOW_RULE_DEBUG_END_TAG_in_ruleDebugTag2642); 
                     
                        createLeafNode(grammarAccess.getDebugTagAccess().getDEBUG_END_TAGTerminalRuleCall_3_1_2(), null); 
                        

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


    // $ANTLR start entryRuleExpressionTag
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1670:1: entryRuleExpressionTag returns [EObject current=null] : iv_ruleExpressionTag= ruleExpressionTag EOF ;
    public final EObject entryRuleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1671:2: (iv_ruleExpressionTag= ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1672:2: iv_ruleExpressionTag= ruleExpressionTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag2679);
            iv_ruleExpressionTag=ruleExpressionTag();
            _fsp--;

             current =iv_ruleExpressionTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag2689); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1679:1: ruleExpressionTag returns [EObject current=null] : ( RULE_EXPRESSION_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG ) ) ) ;
    public final EObject ruleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expression_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1684:6: ( ( RULE_EXPRESSION_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1685:1: ( RULE_EXPRESSION_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1685:1: ( RULE_EXPRESSION_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1685:2: RULE_EXPRESSION_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG ) )
            {
            match(input,RULE_EXPRESSION_START_TAG,FOLLOW_RULE_EXPRESSION_START_TAG_in_ruleExpressionTag2723); 
             
                createLeafNode(grammarAccess.getExpressionTagAccess().getEXPRESSION_START_TAGTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1689:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1690:5: 
            {
             
                    temp=factory.create(grammarAccess.getExpressionTagAccess().getExpressionTagAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getExpressionTagAccess().getExpressionTagAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1700:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==RULE_ID) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1701:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1701:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1702:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleExpressionTag2752);
            	    lv_attributes_2_0=rulePossibleExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getExpressionTagRule().getType().getClassifier());
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
            	    break loop27;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1724:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG ) )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RULE_XML_TAG_SINGLEEND) ) {
                alt28=1;
            }
            else if ( (LA28_0==RULE_XML_TAG_END) ) {
                alt28=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1724:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG ) )", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1724:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag2763); 
                     
                        createLeafNode(grammarAccess.getExpressionTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1729:6: ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1729:6: ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1729:7: RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) RULE_EXPRESSION_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag2778); 
                     
                        createLeafNode(grammarAccess.getExpressionTagAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1733:1: ( (lv_expression_5_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1734:1: (lv_expression_5_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1734:1: (lv_expression_5_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1735:3: lv_expression_5_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getExpressionTopLevelParserRuleCall_3_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleExpressionTag2798);
                    lv_expression_5_0=ruleTopLevel();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getExpressionTagRule().getType().getClassifier());
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

                    match(input,RULE_EXPRESSION_END_TAG,FOLLOW_RULE_EXPRESSION_END_TAG_in_ruleExpressionTag2807); 
                     
                        createLeafNode(grammarAccess.getExpressionTagAccess().getEXPRESSION_END_TAGTerminalRuleCall_3_1_2(), null); 
                        

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


    // $ANTLR start entryRuleTopLevel
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1769:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1770:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1771:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTopLevelRule(), currentNode); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel2844);
            iv_ruleTopLevel=ruleTopLevel();
            _fsp--;

             current =iv_ruleTopLevel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel2854); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1778:1: ruleTopLevel returns [EObject current=null] : ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1783:6: ( ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1784:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1784:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1785:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1785:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1786:3: lv_toplevelExpression_0_0= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel2899);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1816:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1817:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1818:2: iv_rulePathElement= rulePathElement EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathElementRule(), currentNode); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement2935);
            iv_rulePathElement=rulePathElement();
            _fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement2946); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1825:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1830:6: ( (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1831:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1831:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            int alt29=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt29=1;
                }
                break;
            case 54:
                {
                alt29=2;
                }
                break;
            case 55:
                {
                alt29=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("1831:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )", 29, 0, input);

                throw nvae;
            }

            switch (alt29) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1831:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement2986); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1840:2: kw= '.'
                    {
                    kw=(Token)input.LT(1);
                    match(input,54,FOLLOW_54_in_rulePathElement3010); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1847:2: kw= '..'
                    {
                    kw=(Token)input.LT(1);
                    match(input,55,FOLLOW_55_in_rulePathElement3029); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopFullStopKeyword_2(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1860:1: entryRuleTmlExpression returns [EObject current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final EObject entryRuleTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1861:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1862:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression3069);
            iv_ruleTmlExpression=ruleTmlExpression();
            _fsp--;

             current =iv_ruleTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression3079); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1869:1: ruleTmlExpression returns [EObject current=null] : ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_1_0=null;
        Token lv_param_2_0=null;
        AntlrDatatypeRuleToken lv_elements_3_0 = null;

        AntlrDatatypeRuleToken lv_elements_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1874:6: ( ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1875:1: ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1875:1: ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1875:2: RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE
            {
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression3113); 
             
                createLeafNode(grammarAccess.getTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1879:1: ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_TML_SEPARATOR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1880:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1880:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1881:3: lv_absolute_1_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_1_0=(Token)input.LT(1);
                    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression3129); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1903:3: ( (lv_param_2_0= RULE_AT ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==RULE_AT) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1904:1: (lv_param_2_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1904:1: (lv_param_2_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1905:3: lv_param_2_0= RULE_AT
                    {
                    lv_param_2_0=(Token)input.LT(1);
                    match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleTmlExpression3152); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1927:3: ( (lv_elements_3_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1928:1: (lv_elements_3_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1928:1: (lv_elements_3_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1929:3: lv_elements_3_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression3179);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1951:2: ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_TML_SEPARATOR) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1951:3: RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression3189); 
            	     
            	        createLeafNode(grammarAccess.getTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_4_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1955:1: ( (lv_elements_5_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1956:1: (lv_elements_5_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1956:1: (lv_elements_5_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1957:3: lv_elements_5_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression3209);
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
            	    break loop32;
                }
            } while (true);

            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression3220); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1991:1: entryRuleExistsTmlExpression returns [EObject current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final EObject entryRuleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1992:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1993:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExistsTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression3255);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();
            _fsp--;

             current =iv_ruleExistsTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression3265); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2000:1: ruleExistsTmlExpression returns [EObject current=null] : ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_2_0=null;
        Token lv_param_3_0=null;
        AntlrDatatypeRuleToken lv_elements_4_0 = null;

        AntlrDatatypeRuleToken lv_elements_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2005:6: ( ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2006:1: ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2006:1: ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2006:2: RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE
            {
            match(input,RULE_TML_EXISTS,FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression3299); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getTML_EXISTSTerminalRuleCall_0(), null); 
                
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression3307); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2014:1: ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RULE_TML_SEPARATOR) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2015:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2015:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2016:3: lv_absolute_2_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_2_0=(Token)input.LT(1);
                    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression3323); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2038:3: ( (lv_param_3_0= RULE_AT ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==RULE_AT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2039:1: (lv_param_3_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2039:1: (lv_param_3_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2040:3: lv_param_3_0= RULE_AT
                    {
                    lv_param_3_0=(Token)input.LT(1);
                    match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleExistsTmlExpression3346); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2062:3: ( (lv_elements_4_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2063:1: (lv_elements_4_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2063:1: (lv_elements_4_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2064:3: lv_elements_4_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression3373);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2086:2: ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_TML_SEPARATOR) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2086:3: RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression3383); 
            	     
            	        createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_5_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2090:1: ( (lv_elements_6_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2091:1: (lv_elements_6_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2091:1: (lv_elements_6_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2092:3: lv_elements_6_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_5_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression3403);
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
            	    break loop35;
                }
            } while (true);

            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression3414); 
             
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


    // $ANTLR start entryRuleMapGetReference
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2126:1: entryRuleMapGetReference returns [EObject current=null] : iv_ruleMapGetReference= ruleMapGetReference EOF ;
    public final EObject entryRuleMapGetReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapGetReference = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2127:2: (iv_ruleMapGetReference= ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2128:2: iv_ruleMapGetReference= ruleMapGetReference EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapGetReferenceRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference3449);
            iv_ruleMapGetReference=ruleMapGetReference();
            _fsp--;

             current =iv_ruleMapGetReference; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference3459); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2135:1: ruleMapGetReference returns [EObject current=null] : ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( (lv_elements_1_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_3_0= rulePathElement ) ) )* ) ;
    public final EObject ruleMapGetReference() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        AntlrDatatypeRuleToken lv_elements_1_0 = null;

        AntlrDatatypeRuleToken lv_elements_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2140:6: ( ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( (lv_elements_1_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_3_0= rulePathElement ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2141:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( (lv_elements_1_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_3_0= rulePathElement ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2141:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( (lv_elements_1_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_3_0= rulePathElement ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2141:2: ( (lv_operations_0_0= RULE_DOLLAR ) ) ( (lv_elements_1_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_3_0= rulePathElement ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2141:2: ( (lv_operations_0_0= RULE_DOLLAR ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2142:1: (lv_operations_0_0= RULE_DOLLAR )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2142:1: (lv_operations_0_0= RULE_DOLLAR )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2143:3: lv_operations_0_0= RULE_DOLLAR
            {
            lv_operations_0_0=(Token)input.LT(1);
            match(input,RULE_DOLLAR,FOLLOW_RULE_DOLLAR_in_ruleMapGetReference3501); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2165:2: ( (lv_elements_1_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2166:1: (lv_elements_1_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2166:1: (lv_elements_1_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2167:3: lv_elements_1_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapGetReferenceAccess().getElementsPathElementParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleMapGetReference3527);
            lv_elements_1_0=rulePathElement();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapGetReferenceRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"elements",
            	        		lv_elements_1_0, 
            	        		"PathElement", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2189:2: ( RULE_TML_SEPARATOR ( (lv_elements_3_0= rulePathElement ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==RULE_TML_SEPARATOR) ) {
                    int LA36_2 = input.LA(2);

                    if ( (LA36_2==RULE_ID) ) {
                        int LA36_3 = input.LA(3);

                        if ( (LA36_3==EOF||LA36_3==RULE_SEMICOLONQUOTE||LA36_3==RULE_DEBUG_END_TAG||LA36_3==RULE_EXPRESSION_END_TAG||LA36_3==RULE_TML_SEPARATOR||(LA36_3>=RULE_XML_LT && LA36_3<=RULE_XML_GTEQ)||(LA36_3>=56 && LA36_3<=62)||(LA36_3>=65 && LA36_3<=66)||LA36_3==69) ) {
                            alt36=1;
                        }


                    }
                    else if ( ((LA36_2>=54 && LA36_2<=55)) ) {
                        alt36=1;
                    }


                }


                switch (alt36) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2189:3: RULE_TML_SEPARATOR ( (lv_elements_3_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference3537); 
            	     
            	        createLeafNode(grammarAccess.getMapGetReferenceAccess().getTML_SEPARATORTerminalRuleCall_2_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2193:1: ( (lv_elements_3_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2194:1: (lv_elements_3_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2194:1: (lv_elements_3_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2195:3: lv_elements_3_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapGetReferenceAccess().getElementsPathElementParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleMapGetReference3557);
            	    lv_elements_3_0=rulePathElement();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMapGetReferenceRule().getType().getClassifier());
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


            	    }
            	    break;

            	default :
            	    break loop36;
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
    // $ANTLR end ruleMapGetReference


    // $ANTLR start entryRuleOrExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2225:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2226:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2227:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression3595);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression3605); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2234:1: ruleOrExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2239:6: ( ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2240:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2240:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2240:2: ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2240:2: ( (lv_parameters_0_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2241:1: (lv_parameters_0_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2241:1: (lv_parameters_0_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2242:3: lv_parameters_0_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression3651);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2264:2: ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==56) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2264:3: ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2264:3: ( (lv_operations_1_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2265:1: (lv_operations_1_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2265:1: (lv_operations_1_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2266:3: lv_operations_1_0= 'OR'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,56,FOLLOW_56_in_ruleOrExpression3670); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2285:2: ( (lv_parameters_2_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2286:1: (lv_parameters_2_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2286:1: (lv_parameters_2_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2287:3: lv_parameters_2_0= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression3704);
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
            	    break loop37;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2317:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2318:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2319:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression3742);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression3752); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2326:1: ruleAndExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2331:6: ( ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2332:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2332:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2332:2: ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2332:2: ( (lv_parameters_0_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2333:1: (lv_parameters_0_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2333:1: (lv_parameters_0_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2334:3: lv_parameters_0_0= ruleEqualityExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression3798);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2356:2: ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==57) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2356:3: ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2356:3: ( (lv_operations_1_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:1: (lv_operations_1_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:1: (lv_operations_1_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2358:3: lv_operations_1_0= 'AND'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,57,FOLLOW_57_in_ruleAndExpression3817); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2377:2: ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2378:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2378:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2379:3: lv_parameters_2_0= ruleEqualityExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression3851);
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
            	    break loop38;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2409:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2410:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2411:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEqualityExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression3889);
            iv_ruleEqualityExpression=ruleEqualityExpression();
            _fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression3899); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2418:1: ruleEqualityExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2423:6: ( ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2424:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2424:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2424:2: ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2424:2: ( (lv_parameters_0_0= ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2425:1: (lv_parameters_0_0= ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2425:1: (lv_parameters_0_0= ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2426:3: lv_parameters_0_0= ruleRelationalExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression3945);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2448:2: ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            int alt39=3;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==58) ) {
                alt39=1;
            }
            else if ( (LA39_0==59) ) {
                alt39=2;
            }
            switch (alt39) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2448:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2448:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2448:4: ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2448:4: ( (lv_operations_1_0= '==' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2449:1: (lv_operations_1_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2449:1: (lv_operations_1_0= '==' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2450:3: lv_operations_1_0= '=='
                    {
                    lv_operations_1_0=(Token)input.LT(1);
                    match(input,58,FOLLOW_58_in_ruleEqualityExpression3965); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2469:2: ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2470:1: (lv_parameters_2_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2470:1: (lv_parameters_2_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2471:3: lv_parameters_2_0= ruleRelationalExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression3999);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2494:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2494:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2494:7: ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2494:7: ( (lv_operations_3_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2495:1: (lv_operations_3_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2495:1: (lv_operations_3_0= '!=' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2496:3: lv_operations_3_0= '!='
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,59,FOLLOW_59_in_ruleEqualityExpression4025); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2515:2: ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2516:1: (lv_parameters_4_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2516:1: (lv_parameters_4_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2517:3: lv_parameters_4_0= ruleRelationalExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression4059);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2547:1: entryRuleRelationalExpression returns [EObject current=null] : iv_ruleRelationalExpression= ruleRelationalExpression EOF ;
    public final EObject entryRuleRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationalExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2548:2: (iv_ruleRelationalExpression= ruleRelationalExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2549:2: iv_ruleRelationalExpression= ruleRelationalExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRelationalExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression4098);
            iv_ruleRelationalExpression=ruleRelationalExpression();
            _fsp--;

             current =iv_ruleRelationalExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRelationalExpression4108); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2556:1: ruleRelationalExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2561:6: ( ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2562:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2562:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2562:2: () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2562:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2563:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2573:2: ( (lv_parameters_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2574:1: (lv_parameters_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2574:1: (lv_parameters_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2575:3: lv_parameters_1_0= ruleAdditiveExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4163);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2597:2: ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            int alt40=5;
            switch ( input.LA(1) ) {
                case RULE_XML_LT:
                    {
                    alt40=1;
                    }
                    break;
                case RULE_XML_GT:
                    {
                    alt40=2;
                    }
                    break;
                case RULE_XML_LTEQ:
                    {
                    alt40=3;
                    }
                    break;
                case RULE_XML_GTEQ:
                    {
                    alt40=4;
                    }
                    break;
            }

            switch (alt40) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2597:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2597:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2597:4: ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2597:4: ( (lv_operations_2_0= RULE_XML_LT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2598:1: (lv_operations_2_0= RULE_XML_LT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2598:1: (lv_operations_2_0= RULE_XML_LT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2599:3: lv_operations_2_0= RULE_XML_LT
                    {
                    lv_operations_2_0=(Token)input.LT(1);
                    match(input,RULE_XML_LT,FOLLOW_RULE_XML_LT_in_ruleRelationalExpression4182); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2621:2: ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2622:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2622:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2623:3: lv_parameters_3_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4208);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2646:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2646:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2646:7: ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2646:7: ( (lv_operations_4_0= RULE_XML_GT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2647:1: (lv_operations_4_0= RULE_XML_GT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2647:1: (lv_operations_4_0= RULE_XML_GT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2648:3: lv_operations_4_0= RULE_XML_GT
                    {
                    lv_operations_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_GT,FOLLOW_RULE_XML_GT_in_ruleRelationalExpression4233); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2670:2: ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2671:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2671:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2672:3: lv_parameters_5_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4259);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2695:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2695:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2695:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2695:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2696:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2696:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2697:3: lv_operations_6_0= RULE_XML_LTEQ
                    {
                    lv_operations_6_0=(Token)input.LT(1);
                    match(input,RULE_XML_LTEQ,FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression4284); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2719:2: ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2720:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2720:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2721:3: lv_parameters_7_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_2_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4310);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2744:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2744:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2744:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2744:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2745:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2745:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2746:3: lv_operations_8_0= RULE_XML_GTEQ
                    {
                    lv_operations_8_0=(Token)input.LT(1);
                    match(input,RULE_XML_GTEQ,FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression4335); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2768:2: ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2769:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2769:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2770:3: lv_parameters_9_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_3_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4361);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2800:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2801:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2802:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAdditiveExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression4400);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();
            _fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression4410); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2809:1: ruleAdditiveExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2814:6: ( ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2815:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2815:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2815:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2815:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2816:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2816:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2817:3: lv_parameters_0_0= ruleMultiplicativeExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression4456);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2839:2: ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            loop41:
            do {
                int alt41=3;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==60) ) {
                    alt41=1;
                }
                else if ( (LA41_0==61) ) {
                    alt41=2;
                }


                switch (alt41) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2839:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2839:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2839:5: '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,60,FOLLOW_60_in_ruleAdditiveExpression4468); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2843:1: ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2844:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2844:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2845:3: lv_parameters_2_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression4489);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2868:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2868:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2868:8: '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,61,FOLLOW_61_in_ruleAdditiveExpression4507); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2872:1: ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2873:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2873:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2874:3: lv_parameters_4_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression4528);
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
            	    break loop41;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2904:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2905:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2906:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression4567);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();
            _fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression4577); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2913:1: ruleMultiplicativeExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2918:6: ( ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2919:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2919:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2919:2: ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2919:2: ( (lv_parameters_0_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2920:1: (lv_parameters_0_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2920:1: (lv_parameters_0_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2921:3: lv_parameters_0_0= ruleUnaryExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression4623);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2943:2: ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            loop42:
            do {
                int alt42=3;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==62) ) {
                    alt42=1;
                }
                else if ( (LA42_0==RULE_TML_SEPARATOR) ) {
                    alt42=2;
                }


                switch (alt42) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2943:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2943:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2943:4: ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2943:4: ( (lv_operations_1_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2944:1: (lv_operations_1_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2944:1: (lv_operations_1_0= '*' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2945:3: lv_operations_1_0= '*'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,62,FOLLOW_62_in_ruleMultiplicativeExpression4643); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2964:2: ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2965:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2965:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2966:3: lv_parameters_2_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression4677);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2989:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2989:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2989:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2989:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2990:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2990:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2991:3: lv_operations_3_0= RULE_TML_SEPARATOR
            	    {
            	    lv_operations_3_0=(Token)input.LT(1);
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression4702); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3013:2: ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3014:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3014:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3015:3: lv_parameters_4_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression4728);
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
            	    break loop42;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3045:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3046:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3047:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getUnaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression4767);
            iv_ruleUnaryExpression=ruleUnaryExpression();
            _fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression4777); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3054:1: ruleUnaryExpression returns [EObject current=null] : ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        EObject lv_parameters_1_0 = null;

        EObject this_PrimaryExpression_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3059:6: ( ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3060:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3060:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==63) ) {
                alt43=1;
            }
            else if ( (LA43_0==RULE_ID||LA43_0==RULE_SQBRACKET_OPEN||(LA43_0>=RULE_TML_EXISTS && LA43_0<=RULE_DOLLAR)||(LA43_0>=RULE_INT && LA43_0<=RULE_LITERALSTRING)||LA43_0==64||(LA43_0>=67 && LA43_0<=68)||(LA43_0>=70 && LA43_0<=73)) ) {
                alt43=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3060:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3060:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3060:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3060:3: ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3060:3: ( (lv_operations_0_0= '!' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3061:1: (lv_operations_0_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3061:1: (lv_operations_0_0= '!' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3062:3: lv_operations_0_0= '!'
                    {
                    lv_operations_0_0=(Token)input.LT(1);
                    match(input,63,FOLLOW_63_in_ruleUnaryExpression4821); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3081:2: ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3082:1: (lv_parameters_1_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3082:1: (lv_parameters_1_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3083:3: lv_parameters_1_0= rulePrimaryExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression4855);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3107:5: this_PrimaryExpression_2= rulePrimaryExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression4884);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3123:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3124:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3125:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPrimaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression4919);
            iv_rulePrimaryExpression=rulePrimaryExpression();
            _fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression4929); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3132:1: rulePrimaryExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3137:6: ( ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3138:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3138:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==RULE_ID||LA44_0==RULE_SQBRACKET_OPEN||(LA44_0>=RULE_TML_EXISTS && LA44_0<=RULE_DOLLAR)||(LA44_0>=RULE_INT && LA44_0<=RULE_LITERALSTRING)||(LA44_0>=67 && LA44_0<=68)||(LA44_0>=70 && LA44_0<=73)) ) {
                alt44=1;
            }
            else if ( (LA44_0==64) ) {
                alt44=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3138:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3138:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3138:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3139:1: (lv_parameters_0_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3139:1: (lv_parameters_0_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3140:3: lv_parameters_0_0= ruleLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression4975);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3163:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3163:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3163:8: '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')'
                    {
                    match(input,64,FOLLOW_64_in_rulePrimaryExpression4992); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3167:1: ( (lv_parameters_2_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3168:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3168:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3169:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression5013);
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

                    match(input,65,FOLLOW_65_in_rulePrimaryExpression5023); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3203:1: entryRuleFunctionName returns [String current=null] : iv_ruleFunctionName= ruleFunctionName EOF ;
    public final String entryRuleFunctionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFunctionName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3204:2: (iv_ruleFunctionName= ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3205:2: iv_ruleFunctionName= ruleFunctionName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName5061);
            iv_ruleFunctionName=ruleFunctionName();
            _fsp--;

             current =iv_ruleFunctionName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName5072); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3212:1: ruleFunctionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFunctionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3217:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3218:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName5111); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3233:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3234:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3235:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionCallRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall5155);
            iv_ruleFunctionCall=ruleFunctionCall();
            _fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall5165); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3242:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3247:6: ( ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3248:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3248:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3248:2: ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')'
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3248:2: ( (lv_name_0_0= ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3249:1: (lv_name_0_0= ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3249:1: (lv_name_0_0= ruleFunctionName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3250:3: lv_name_0_0= ruleFunctionName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleFunctionName_in_ruleFunctionCall5211);
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

            match(input,64,FOLLOW_64_in_ruleFunctionCall5221); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3276:1: ( (lv_parameters_2_0= ruleOrExpression ) )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==RULE_ID||LA45_0==RULE_SQBRACKET_OPEN||(LA45_0>=RULE_TML_EXISTS && LA45_0<=RULE_DOLLAR)||(LA45_0>=RULE_INT && LA45_0<=RULE_LITERALSTRING)||(LA45_0>=63 && LA45_0<=64)||(LA45_0>=67 && LA45_0<=68)||(LA45_0>=70 && LA45_0<=73)) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3277:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3277:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3278:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall5242);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3300:3: ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==66) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3300:5: ',' ( (lv_parameters_4_0= ruleOrExpression ) )
            	    {
            	    match(input,66,FOLLOW_66_in_ruleFunctionCall5254); 

            	            createLeafNode(grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3304:1: ( (lv_parameters_4_0= ruleOrExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3305:1: (lv_parameters_4_0= ruleOrExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3305:1: (lv_parameters_4_0= ruleOrExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3306:3: lv_parameters_4_0= ruleOrExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall5275);
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
            	    break loop46;
                }
            } while (true);

            match(input,65,FOLLOW_65_in_ruleFunctionCall5287); 

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


    // $ANTLR start entryRuleLiteral
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3340:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3341:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3342:2: iv_ruleLiteral= ruleLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral5323);
            iv_ruleLiteral=ruleLiteral();
            _fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral5333); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3349:1: ruleLiteral returns [EObject current=null] : ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) ) ;
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

        EObject this_FunctionCall_9 = null;

        EObject lv_parameters_11_0 = null;

        EObject lv_parameters_13_0 = null;

        EObject lv_parameters_19_0 = null;

        EObject lv_parameters_20_0 = null;

        EObject lv_parameters_21_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3354:6: ( ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3355:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3355:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )
            int alt49=12;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt49=1;
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt49=2;
                }
                break;
            case 67:
                {
                alt49=3;
                }
                break;
            case RULE_ID:
                {
                alt49=4;
                }
                break;
            case 68:
                {
                alt49=5;
                }
                break;
            case 70:
                {
                alt49=6;
                }
                break;
            case 71:
                {
                alt49=7;
                }
                break;
            case 72:
                {
                alt49=8;
                }
                break;
            case 73:
                {
                alt49=9;
                }
                break;
            case RULE_SQBRACKET_OPEN:
                {
                alt49=10;
                }
                break;
            case RULE_TML_EXISTS:
                {
                alt49=11;
                }
                break;
            case RULE_DOLLAR:
                {
                alt49=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("3355:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )", 49, 0, input);

                throw nvae;
            }

            switch (alt49) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3355:2: ( () RULE_INT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3355:2: ( () RULE_INT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3355:3: () RULE_INT
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3355:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3356:5: 
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

                    match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleLiteral5377); 
                     
                        createLeafNode(grammarAccess.getLiteralAccess().getINTTerminalRuleCall_0_1(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3371:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3371:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3372:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3372:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3373:3: lv_valueString_2_0= RULE_LITERALSTRING
                    {
                    lv_valueString_2_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral5400); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3396:6: ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3396:6: ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3396:7: ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3396:7: ( (lv_operations_3_0= 'FORALL' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3397:1: (lv_operations_3_0= 'FORALL' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3397:1: (lv_operations_3_0= 'FORALL' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3398:3: lv_operations_3_0= 'FORALL'
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,67,FOLLOW_67_in_ruleLiteral5430); 

                            createLeafNode(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_0_0(), "operations"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "operations", lv_operations_3_0, "FORALL", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,64,FOLLOW_64_in_ruleLiteral5453); 

                            createLeafNode(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3421:1: ( (lv_valueString_5_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3422:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3422:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3423:3: lv_valueString_5_0= RULE_LITERALSTRING
                    {
                    lv_valueString_5_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral5470); 

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

                    match(input,66,FOLLOW_66_in_ruleLiteral5485); 

                            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_2_3(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3449:1: ( (lv_parameters_7_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3450:1: (lv_parameters_7_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3450:1: (lv_parameters_7_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3451:3: lv_parameters_7_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral5506);
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

                    match(input,65,FOLLOW_65_in_ruleLiteral5516); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5(), null); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3479:5: this_FunctionCall_9= ruleFunctionCall
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral5545);
                    this_FunctionCall_9=ruleFunctionCall();
                    _fsp--;

                     
                            current = this_FunctionCall_9; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:7: ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:7: ( (lv_expressionType_10_0= '{' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3489:1: (lv_expressionType_10_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3489:1: (lv_expressionType_10_0= '{' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3490:3: lv_expressionType_10_0= '{'
                    {
                    lv_expressionType_10_0=(Token)input.LT(1);
                    match(input,68,FOLLOW_68_in_ruleLiteral5569); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3509:2: ( (lv_parameters_11_0= ruleOrExpression ) )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==RULE_ID||LA47_0==RULE_SQBRACKET_OPEN||(LA47_0>=RULE_TML_EXISTS && LA47_0<=RULE_DOLLAR)||(LA47_0>=RULE_INT && LA47_0<=RULE_LITERALSTRING)||(LA47_0>=63 && LA47_0<=64)||(LA47_0>=67 && LA47_0<=68)||(LA47_0>=70 && LA47_0<=73)) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3510:1: (lv_parameters_11_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3510:1: (lv_parameters_11_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3511:3: lv_parameters_11_0= ruleOrExpression
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral5603);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3533:3: ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )*
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( (LA48_0==66) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3533:5: ',' ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    {
                    	    match(input,66,FOLLOW_66_in_ruleLiteral5615); 

                    	            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0(), null); 
                    	        
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3537:1: ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3538:1: (lv_parameters_13_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3538:1: (lv_parameters_13_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3539:3: lv_parameters_13_0= ruleOrExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral5636);
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
                    	    break loop48;
                        }
                    } while (true);

                    match(input,69,FOLLOW_69_in_ruleLiteral5648); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3(), null); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3566:6: ( (lv_elements_15_0= 'NULL' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3566:6: ( (lv_elements_15_0= 'NULL' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3567:1: (lv_elements_15_0= 'NULL' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3567:1: (lv_elements_15_0= 'NULL' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3568:3: lv_elements_15_0= 'NULL'
                    {
                    lv_elements_15_0=(Token)input.LT(1);
                    match(input,70,FOLLOW_70_in_ruleLiteral5673); 

                            createLeafNode(grammarAccess.getLiteralAccess().getElementsNULLKeyword_5_0(), "elements"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "elements", lv_elements_15_0, "NULL", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 7 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3588:6: ( (lv_elements_16_0= 'TODAY' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3588:6: ( (lv_elements_16_0= 'TODAY' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3589:1: (lv_elements_16_0= 'TODAY' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3589:1: (lv_elements_16_0= 'TODAY' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3590:3: lv_elements_16_0= 'TODAY'
                    {
                    lv_elements_16_0=(Token)input.LT(1);
                    match(input,71,FOLLOW_71_in_ruleLiteral5710); 

                            createLeafNode(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_6_0(), "elements"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "elements", lv_elements_16_0, "TODAY", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3610:6: ( (lv_elements_17_0= 'TRUE' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3610:6: ( (lv_elements_17_0= 'TRUE' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3611:1: (lv_elements_17_0= 'TRUE' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3611:1: (lv_elements_17_0= 'TRUE' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3612:3: lv_elements_17_0= 'TRUE'
                    {
                    lv_elements_17_0=(Token)input.LT(1);
                    match(input,72,FOLLOW_72_in_ruleLiteral5747); 

                            createLeafNode(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_7_0(), "elements"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "elements", lv_elements_17_0, "TRUE", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3632:6: ( (lv_elements_18_0= 'FALSE' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3632:6: ( (lv_elements_18_0= 'FALSE' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3633:1: (lv_elements_18_0= 'FALSE' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3633:1: (lv_elements_18_0= 'FALSE' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3634:3: lv_elements_18_0= 'FALSE'
                    {
                    lv_elements_18_0=(Token)input.LT(1);
                    match(input,73,FOLLOW_73_in_ruleLiteral5784); 

                            createLeafNode(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_8_0(), "elements"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "elements", lv_elements_18_0, "FALSE", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }
                    break;
                case 10 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3654:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3654:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3655:1: (lv_parameters_19_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3655:1: (lv_parameters_19_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3656:3: lv_parameters_19_0= ruleTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral5824);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3679:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3679:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3680:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3680:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3681:3: lv_parameters_20_0= ruleExistsTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral5851);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3704:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3704:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3705:1: (lv_parameters_21_0= ruleMapGetReference )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3705:1: (lv_parameters_21_0= ruleMapGetReference )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3706:3: lv_parameters_21_0= ruleMapGetReference
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapGetReference_in_ruleLiteral5878);
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
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleTml160 = new BitSet(new long[]{0x0000000010922040L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleTml181 = new BitSet(new long[]{0x0000000010922040L});
    public static final BitSet FOLLOW_ruleMap_in_ruleTml208 = new BitSet(new long[]{0x0000000010922040L});
    public static final BitSet FOLLOW_ruleParam_in_ruleTml235 = new BitSet(new long[]{0x0000000010922040L});
    public static final BitSet FOLLOW_ruleMethods_in_ruleTml262 = new BitSet(new long[]{0x0000000010922040L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleTml289 = new BitSet(new long[]{0x0000000010922040L});
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_END_in_ruleTml300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttributeName_in_entryRuleAttributeName352 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAttributeName363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAttributeName402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression446 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePossibleExpression456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePossibleExpression499 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_rulePossibleExpression514 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleAttributeName_in_rulePossibleExpression537 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_rulePossibleExpression547 = new BitSet(new long[]{0x0000000000001A00L});
    public static final BitSet FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression558 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleTopLevel_in_rulePossibleExpression578 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethods_in_entryRuleMethods666 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethods676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_METHODS_START_TAG_in_ruleMethods710 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethods729 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_ruleMethod_in_ruleMethods749 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_RULE_METHODS_END_TAG_in_ruleMethods759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethod_in_entryRuleMethod810 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethod820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_METHOD_START_TAG_in_ruleMethod854 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMethod883 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethod895 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RULE_METHOD_END_TAG_in_ruleMethod903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage954 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MESSAGE_START_TAG_in_ruleMessage998 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMessage1027 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMessage1039 = new BitSet(new long[]{0x0000000012B60000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMessage1060 = new BitSet(new long[]{0x0000000012B60000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMessage1087 = new BitSet(new long[]{0x0000000012B60000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMessage1114 = new BitSet(new long[]{0x0000000012B60000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMessage1141 = new BitSet(new long[]{0x0000000012B60000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMessage1168 = new BitSet(new long[]{0x0000000012B60000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMessage1195 = new BitSet(new long[]{0x0000000012B60000L});
    public static final BitSet FOLLOW_RULE_MESSAGE_END_TAG_in_ruleMessage1206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage1221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_entryRuleMap1257 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMap1267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapStart_in_ruleMap1314 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap1323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap1338 = new BitSet(new long[]{0x0000000012BA0000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMap1359 = new BitSet(new long[]{0x0000000012BA0000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMap1386 = new BitSet(new long[]{0x0000000012BA0000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMap1413 = new BitSet(new long[]{0x0000000012BA0000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMap1440 = new BitSet(new long[]{0x0000000012BA0000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMap1467 = new BitSet(new long[]{0x0000000012BA0000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMap1494 = new BitSet(new long[]{0x0000000012BA0000L});
    public static final BitSet FOLLOW_ruleMapEnd_in_ruleMap1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapEnd_in_entryRuleMapEnd1556 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapEnd1567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MAPENDKEYWORD_in_ruleMapEnd1607 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapEnd1627 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapEnd1647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapId_in_entryRuleMapId1693 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapId1704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapId1743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapStart_in_entryRuleMapStart1787 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapStart1797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MAPSTARTKEYWORD_in_ruleMapStart1840 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMapStart1860 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMapStart1881 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty1918 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty1928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PROPERTY_START_TAG_in_ruleProperty1962 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleProperty1991 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty2002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleProperty2017 = new BitSet(new long[]{0x0000000040400000L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleProperty2037 = new BitSet(new long[]{0x0000000040400000L});
    public static final BitSet FOLLOW_RULE_PROPERTY_END_TAG_in_ruleProperty2047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParam_in_entryRuleParam2084 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParam2094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARAM_START_TAG_in_ruleParam2128 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleParam2157 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam2168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleParam2183 = new BitSet(new long[]{0x0000000041000000L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleParam2203 = new BitSet(new long[]{0x0000000041000000L});
    public static final BitSet FOLLOW_RULE_PARAM_END_TAG_in_ruleParam2213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapMethod_in_entryRuleMapMethod2250 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapMethod2260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleMapMethod2294 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod2310 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_ruleMapMethod2325 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod2342 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMapMethod2368 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod2379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod2394 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_RULE_CDATA_in_ruleMapMethod2403 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleMapMethod2413 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod2429 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_ruleMapMethod2444 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod2461 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod2475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDebugTag_in_entryRuleDebugTag2514 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDebugTag2524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DEBUG_START_TAG_in_ruleDebugTag2558 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleDebugTag2587 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag2598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag2613 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleDebugTag2633 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_RULE_DEBUG_END_TAG_in_ruleDebugTag2642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag2679 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag2689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EXPRESSION_START_TAG_in_ruleExpressionTag2723 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleExpressionTag2752 = new BitSet(new long[]{0x00000000000001A0L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag2763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag2778 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleExpressionTag2798 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_RULE_EXPRESSION_END_TAG_in_ruleExpressionTag2807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel2844 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel2854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel2899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement2935 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement2946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement2986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_rulePathElement3010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_rulePathElement3029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression3069 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression3079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression3113 = new BitSet(new long[]{0x00C0000600000100L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression3129 = new BitSet(new long[]{0x00C0000400000100L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleTmlExpression3152 = new BitSet(new long[]{0x00C0000000000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression3179 = new BitSet(new long[]{0x0000000A00000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression3189 = new BitSet(new long[]{0x00C0000000000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression3209 = new BitSet(new long[]{0x0000000A00000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression3220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression3255 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression3265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression3299 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression3307 = new BitSet(new long[]{0x00C0000600000100L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression3323 = new BitSet(new long[]{0x00C0000400000100L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleExistsTmlExpression3346 = new BitSet(new long[]{0x00C0000000000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression3373 = new BitSet(new long[]{0x0000000A00000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression3383 = new BitSet(new long[]{0x00C0000000000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression3403 = new BitSet(new long[]{0x0000000A00000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression3414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference3449 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference3459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOLLAR_in_ruleMapGetReference3501 = new BitSet(new long[]{0x00C0000000000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleMapGetReference3527 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference3537 = new BitSet(new long[]{0x00C0000000000100L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleMapGetReference3557 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression3595 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression3605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression3651 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_56_in_ruleOrExpression3670 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression3704 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression3742 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression3752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression3798 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_ruleAndExpression3817 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression3851 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression3889 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression3899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression3945 = new BitSet(new long[]{0x0C00000000000002L});
    public static final BitSet FOLLOW_58_in_ruleEqualityExpression3965 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression3999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_ruleEqualityExpression4025 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression4059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression4098 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRelationalExpression4108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4163 = new BitSet(new long[]{0x000003C000000002L});
    public static final BitSet FOLLOW_RULE_XML_LT_in_ruleRelationalExpression4182 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GT_in_ruleRelationalExpression4233 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression4284 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression4335 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression4361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression4400 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression4410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression4456 = new BitSet(new long[]{0x3000000000000002L});
    public static final BitSet FOLLOW_60_in_ruleAdditiveExpression4468 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression4489 = new BitSet(new long[]{0x3000000000000002L});
    public static final BitSet FOLLOW_61_in_ruleAdditiveExpression4507 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression4528 = new BitSet(new long[]{0x3000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression4567 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression4577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression4623 = new BitSet(new long[]{0x4000000200000002L});
    public static final BitSet FOLLOW_62_in_ruleMultiplicativeExpression4643 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression4677 = new BitSet(new long[]{0x4000000200000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression4702 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression4728 = new BitSet(new long[]{0x4000000200000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression4767 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression4777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_ruleUnaryExpression4821 = new BitSet(new long[]{0x00000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression4855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression4884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression4919 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression4929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression4975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_rulePrimaryExpression4992 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression5013 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_rulePrimaryExpression5023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName5061 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName5072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName5111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall5155 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall5165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_ruleFunctionCall5211 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_ruleFunctionCall5221 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003DFL});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall5242 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_66_in_ruleFunctionCall5254 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall5275 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_65_in_ruleFunctionCall5287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral5323 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral5333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleLiteral5377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral5400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_ruleLiteral5430 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_ruleLiteral5453 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral5470 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ruleLiteral5485 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral5506 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_ruleLiteral5516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral5545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_ruleLiteral5569 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003FDL});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral5603 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_ruleLiteral5615 = new BitSet(new long[]{0x80000C3100000100L,0x00000000000003D9L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral5636 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_69_in_ruleLiteral5648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_ruleLiteral5673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_ruleLiteral5710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_ruleLiteral5747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ruleLiteral5784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral5824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral5851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_ruleLiteral5878 = new BitSet(new long[]{0x0000000000000002L});

}