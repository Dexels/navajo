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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_XML_TAG_END", "RULE_XML_TAG_SINGLEEND", "RULE_XML_TAG_START", "RULE_MAPKEYWORD", "RULE_XML_START_ENDTAG", "RULE_NAVASCRIPT_KEYWORD", "RULE_ID", "RULE_QUOTEQ", "RULE_SEMICOLONQUOTE", "RULE_ATTRIBUTESTRING", "RULE_EMPTYSTRING", "RULE_PARENT", "RULE_SQBRACKET_OPEN", "RULE_TML_SEPARATOR", "RULE_AT", "RULE_SQBRACKET_CLOSE", "RULE_TML_EXISTS", "RULE_DOLLAR", "RULE_XML_LT", "RULE_XML_GT", "RULE_XML_LTEQ", "RULE_XML_GTEQ", "RULE_INT", "RULE_LITERALSTRING", "RULE_FORALL", "RULE_NULL", "RULE_TODAY", "RULE_TRUE", "RULE_FALSE", "RULE_XMLHEAD", "RULE_XMLCOMMENT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'debug'", "'include'", "'property'", "'required'", "'validations'", "'check'", "'comment'", "'break'", "'option'", "'param'", "'message'", "'methods'", "'method'", "'field'", "'expression'", "':'", "'='", "'.'", "'('", "','", "')'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'{'", "'}'"
    };
    public static final int RULE_XML_TAG_END=4;
    public static final int RULE_ATTRIBUTESTRING=13;
    public static final int RULE_ID=10;
    public static final int RULE_XMLCOMMENT=34;
    public static final int RULE_PARENT=15;
    public static final int RULE_SQBRACKET_OPEN=16;
    public static final int RULE_XML_LT=22;
    public static final int RULE_NAVASCRIPT_KEYWORD=9;
    public static final int RULE_QUOTEQ=11;
    public static final int RULE_XMLHEAD=33;
    public static final int RULE_LITERALSTRING=27;
    public static final int RULE_XML_GTEQ=25;
    public static final int RULE_TML_SEPARATOR=17;
    public static final int RULE_SL_COMMENT=36;
    public static final int EOF=-1;
    public static final int RULE_NULL=29;
    public static final int RULE_ML_COMMENT=35;
    public static final int RULE_TRUE=31;
    public static final int RULE_FORALL=28;
    public static final int RULE_DOLLAR=21;
    public static final int RULE_FALSE=32;
    public static final int RULE_TML_EXISTS=20;
    public static final int RULE_EMPTYSTRING=14;
    public static final int RULE_TODAY=30;
    public static final int RULE_SQBRACKET_CLOSE=19;
    public static final int RULE_XML_LTEQ=24;
    public static final int RULE_SEMICOLONQUOTE=12;
    public static final int RULE_INT=26;
    public static final int RULE_XML_START_ENDTAG=8;
    public static final int RULE_WS=37;
    public static final int RULE_XML_TAG_START=6;
    public static final int RULE_XML_GT=23;
    public static final int RULE_MAPKEYWORD=7;
    public static final int RULE_XML_TAG_SINGLEEND=5;
    public static final int RULE_AT=18;

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:86:1: ruleTml returns [EObject current=null] : ( ruleNAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:91:6: ( ( ruleNAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( ruleNAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( ruleNAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:93:5: ruleNAVASCRIPT_START () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getTmlAccess().getNAVASCRIPT_STARTParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleNAVASCRIPT_START_in_ruleTml126);
            ruleNAVASCRIPT_START();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:100:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:101:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:111:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_ID||LA1_0==44||LA1_0==48) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:112:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:112:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:113:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleTml155);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("135:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END ) | RULE_XML_TAG_SINGLEEND )", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:5: RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )* ruleNAVASCRIPT_END
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleTml167); 
                     
                        createLeafNode(grammarAccess.getTmlAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:139:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_methods_7_0= ruleMethods ) ) | ( (lv_children_8_0= ruleDebugTag ) ) | ( (lv_children_9_0= ruleInclude ) ) | ( (lv_children_10_0= ruleValidations ) ) | ( (lv_children_11_0= ruleComment ) ) )*
                    loop2:
                    do {
                        int alt2=9;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==RULE_XML_TAG_START) ) {
                            switch ( input.LA(2) ) {
                            case 39:
                                {
                                alt2=6;
                                }
                                break;
                            case 44:
                                {
                                alt2=8;
                                }
                                break;
                            case 48:
                                {
                                alt2=1;
                                }
                                break;
                            case 47:
                                {
                                alt2=3;
                                }
                                break;
                            case 49:
                                {
                                alt2=4;
                                }
                                break;
                            case 38:
                                {
                                alt2=5;
                                }
                                break;
                            case RULE_MAPKEYWORD:
                                {
                                alt2=2;
                                }
                                break;
                            case 42:
                                {
                                alt2=7;
                                }
                                break;

                            }

                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:139:2: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:139:2: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:140:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:140:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:141:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleTml188);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:164:6: ( (lv_children_5_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:164:6: ( (lv_children_5_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:165:1: (lv_children_5_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:165:1: (lv_children_5_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:166:3: lv_children_5_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenMapParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleTml215);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:189:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:189:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:190:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:190:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:191:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenParamParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleTml242);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:214:6: ( (lv_methods_7_0= ruleMethods ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:214:6: ( (lv_methods_7_0= ruleMethods ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:215:1: (lv_methods_7_0= ruleMethods )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:215:1: (lv_methods_7_0= ruleMethods )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:3: lv_methods_7_0= ruleMethods
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getMethodsMethodsParserRuleCall_3_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethods_in_ruleTml269);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:239:6: ( (lv_children_8_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:239:6: ( (lv_children_8_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:240:1: (lv_children_8_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:240:1: (lv_children_8_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:241:3: lv_children_8_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenDebugTagParserRuleCall_3_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleTml296);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:264:6: ( (lv_children_9_0= ruleInclude ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:264:6: ( (lv_children_9_0= ruleInclude ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:265:1: (lv_children_9_0= ruleInclude )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:265:1: (lv_children_9_0= ruleInclude )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:266:3: lv_children_9_0= ruleInclude
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenIncludeParserRuleCall_3_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleInclude_in_ruleTml323);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:289:6: ( (lv_children_10_0= ruleValidations ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:289:6: ( (lv_children_10_0= ruleValidations ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:290:1: (lv_children_10_0= ruleValidations )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:290:1: (lv_children_10_0= ruleValidations )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:291:3: lv_children_10_0= ruleValidations
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenValidationsParserRuleCall_3_0_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleValidations_in_ruleTml350);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:314:6: ( (lv_children_11_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:314:6: ( (lv_children_11_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:315:1: (lv_children_11_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:315:1: (lv_children_11_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:316:3: lv_children_11_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenCommentParserRuleCall_3_0_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleTml377);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getTmlAccess().getNAVASCRIPT_ENDParserRuleCall_3_0_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleNAVASCRIPT_END_in_ruleTml395);
                    ruleNAVASCRIPT_END();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:347:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml410); 
                     
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


    // $ANTLR start entryRuleDEBUG_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:359:1: entryRuleDEBUG_START_TAG returns [String current=null] : iv_ruleDEBUG_START_TAG= ruleDEBUG_START_TAG EOF ;
    public final String entryRuleDEBUG_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDEBUG_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:360:2: (iv_ruleDEBUG_START_TAG= ruleDEBUG_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:361:2: iv_ruleDEBUG_START_TAG= ruleDEBUG_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getDEBUG_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleDEBUG_START_TAG_in_entryRuleDEBUG_START_TAG447);
            iv_ruleDEBUG_START_TAG=ruleDEBUG_START_TAG();
            _fsp--;

             current =iv_ruleDEBUG_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDEBUG_START_TAG458); 

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
    // $ANTLR end entryRuleDEBUG_START_TAG


    // $ANTLR start ruleDEBUG_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:1: ruleDEBUG_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'debug' ) ;
    public final AntlrDatatypeRuleToken ruleDEBUG_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:373:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'debug' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:374:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'debug' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:374:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'debug' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:374:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'debug'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleDEBUG_START_TAG498); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getDEBUG_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,38,FOLLOW_38_in_ruleDEBUG_START_TAG516); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getDEBUG_START_TAGAccess().getDebugKeyword_1(), null); 
                

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
    // $ANTLR end ruleDEBUG_START_TAG


    // $ANTLR start entryRuleDEBUG_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:395:1: entryRuleDEBUG_END_TAG returns [String current=null] : iv_ruleDEBUG_END_TAG= ruleDEBUG_END_TAG EOF ;
    public final String entryRuleDEBUG_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDEBUG_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:396:2: (iv_ruleDEBUG_END_TAG= ruleDEBUG_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:397:2: iv_ruleDEBUG_END_TAG= ruleDEBUG_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getDEBUG_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleDEBUG_END_TAG_in_entryRuleDEBUG_END_TAG557);
            iv_ruleDEBUG_END_TAG=ruleDEBUG_END_TAG();
            _fsp--;

             current =iv_ruleDEBUG_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDEBUG_END_TAG568); 

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
    // $ANTLR end entryRuleDEBUG_END_TAG


    // $ANTLR start ruleDEBUG_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:404:1: ruleDEBUG_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_END_0= RULE_XML_TAG_END kw= 'debug' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleDEBUG_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_END_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:409:6: ( (this_XML_TAG_END_0= RULE_XML_TAG_END kw= 'debug' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:410:1: (this_XML_TAG_END_0= RULE_XML_TAG_END kw= 'debug' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:410:1: (this_XML_TAG_END_0= RULE_XML_TAG_END kw= 'debug' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:410:6: this_XML_TAG_END_0= RULE_XML_TAG_END kw= 'debug' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_TAG_END_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleDEBUG_END_TAG608); 

            		current.merge(this_XML_TAG_END_0);
                
             
                createLeafNode(grammarAccess.getDEBUG_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,38,FOLLOW_38_in_ruleDEBUG_END_TAG626); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getDEBUG_END_TAGAccess().getDebugKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleDEBUG_END_TAG641); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getDEBUG_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleDEBUG_END_TAG


    // $ANTLR start entryRuleMAPSTARTKEYWORD
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:440:1: entryRuleMAPSTARTKEYWORD returns [String current=null] : iv_ruleMAPSTARTKEYWORD= ruleMAPSTARTKEYWORD EOF ;
    public final String entryRuleMAPSTARTKEYWORD() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMAPSTARTKEYWORD = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:441:2: (iv_ruleMAPSTARTKEYWORD= ruleMAPSTARTKEYWORD EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:442:2: iv_ruleMAPSTARTKEYWORD= ruleMAPSTARTKEYWORD EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMAPSTARTKEYWORDRule(), currentNode); 
            pushFollow(FOLLOW_ruleMAPSTARTKEYWORD_in_entryRuleMAPSTARTKEYWORD689);
            iv_ruleMAPSTARTKEYWORD=ruleMAPSTARTKEYWORD();
            _fsp--;

             current =iv_ruleMAPSTARTKEYWORD.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMAPSTARTKEYWORD700); 

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
    // $ANTLR end entryRuleMAPSTARTKEYWORD


    // $ANTLR start ruleMAPSTARTKEYWORD
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:449:1: ruleMAPSTARTKEYWORD returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START this_MAPKEYWORD_1= RULE_MAPKEYWORD ) ;
    public final AntlrDatatypeRuleToken ruleMAPSTARTKEYWORD() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token this_MAPKEYWORD_1=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:454:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START this_MAPKEYWORD_1= RULE_MAPKEYWORD ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:455:1: (this_XML_TAG_START_0= RULE_XML_TAG_START this_MAPKEYWORD_1= RULE_MAPKEYWORD )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:455:1: (this_XML_TAG_START_0= RULE_XML_TAG_START this_MAPKEYWORD_1= RULE_MAPKEYWORD )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:455:6: this_XML_TAG_START_0= RULE_XML_TAG_START this_MAPKEYWORD_1= RULE_MAPKEYWORD
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleMAPSTARTKEYWORD740); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getMAPSTARTKEYWORDAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            this_MAPKEYWORD_1=(Token)input.LT(1);
            match(input,RULE_MAPKEYWORD,FOLLOW_RULE_MAPKEYWORD_in_ruleMAPSTARTKEYWORD760); 

            		current.merge(this_MAPKEYWORD_1);
                
             
                createLeafNode(grammarAccess.getMAPSTARTKEYWORDAccess().getMAPKEYWORDTerminalRuleCall_1(), null); 
                

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
    // $ANTLR end ruleMAPSTARTKEYWORD


    // $ANTLR start entryRuleINCLUDE_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:477:1: entryRuleINCLUDE_START_TAG returns [String current=null] : iv_ruleINCLUDE_START_TAG= ruleINCLUDE_START_TAG EOF ;
    public final String entryRuleINCLUDE_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINCLUDE_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:2: (iv_ruleINCLUDE_START_TAG= ruleINCLUDE_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:479:2: iv_ruleINCLUDE_START_TAG= ruleINCLUDE_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getINCLUDE_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleINCLUDE_START_TAG_in_entryRuleINCLUDE_START_TAG806);
            iv_ruleINCLUDE_START_TAG=ruleINCLUDE_START_TAG();
            _fsp--;

             current =iv_ruleINCLUDE_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleINCLUDE_START_TAG817); 

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
    // $ANTLR end entryRuleINCLUDE_START_TAG


    // $ANTLR start ruleINCLUDE_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:486:1: ruleINCLUDE_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'include' ) ;
    public final AntlrDatatypeRuleToken ruleINCLUDE_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:491:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'include' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:492:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'include' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:492:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'include' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:492:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'include'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleINCLUDE_START_TAG857); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getINCLUDE_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,39,FOLLOW_39_in_ruleINCLUDE_START_TAG875); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getINCLUDE_START_TAGAccess().getIncludeKeyword_1(), null); 
                

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
    // $ANTLR end ruleINCLUDE_START_TAG


    // $ANTLR start entryRulePROPERTY_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:513:1: entryRulePROPERTY_START_TAG returns [String current=null] : iv_rulePROPERTY_START_TAG= rulePROPERTY_START_TAG EOF ;
    public final String entryRulePROPERTY_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePROPERTY_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:514:2: (iv_rulePROPERTY_START_TAG= rulePROPERTY_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:515:2: iv_rulePROPERTY_START_TAG= rulePROPERTY_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPROPERTY_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_rulePROPERTY_START_TAG_in_entryRulePROPERTY_START_TAG916);
            iv_rulePROPERTY_START_TAG=rulePROPERTY_START_TAG();
            _fsp--;

             current =iv_rulePROPERTY_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePROPERTY_START_TAG927); 

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
    // $ANTLR end entryRulePROPERTY_START_TAG


    // $ANTLR start rulePROPERTY_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:522:1: rulePROPERTY_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'property' ) ;
    public final AntlrDatatypeRuleToken rulePROPERTY_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:527:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'property' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:528:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'property' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:528:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'property' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:528:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'property'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_rulePROPERTY_START_TAG967); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getPROPERTY_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,40,FOLLOW_40_in_rulePROPERTY_START_TAG985); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getPROPERTY_START_TAGAccess().getPropertyKeyword_1(), null); 
                

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
    // $ANTLR end rulePROPERTY_START_TAG


    // $ANTLR start entryRuleREQUIRED_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:549:1: entryRuleREQUIRED_START_TAG returns [String current=null] : iv_ruleREQUIRED_START_TAG= ruleREQUIRED_START_TAG EOF ;
    public final String entryRuleREQUIRED_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleREQUIRED_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:550:2: (iv_ruleREQUIRED_START_TAG= ruleREQUIRED_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:551:2: iv_ruleREQUIRED_START_TAG= ruleREQUIRED_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getREQUIRED_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleREQUIRED_START_TAG_in_entryRuleREQUIRED_START_TAG1026);
            iv_ruleREQUIRED_START_TAG=ruleREQUIRED_START_TAG();
            _fsp--;

             current =iv_ruleREQUIRED_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleREQUIRED_START_TAG1037); 

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
    // $ANTLR end entryRuleREQUIRED_START_TAG


    // $ANTLR start ruleREQUIRED_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:558:1: ruleREQUIRED_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'required' ) ;
    public final AntlrDatatypeRuleToken ruleREQUIRED_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:563:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'required' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:564:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'required' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:564:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'required' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:564:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'required'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleREQUIRED_START_TAG1077); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getREQUIRED_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,41,FOLLOW_41_in_ruleREQUIRED_START_TAG1095); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getREQUIRED_START_TAGAccess().getRequiredKeyword_1(), null); 
                

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
    // $ANTLR end ruleREQUIRED_START_TAG


    // $ANTLR start entryRuleVALIDATIONS_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:585:1: entryRuleVALIDATIONS_START_TAG returns [String current=null] : iv_ruleVALIDATIONS_START_TAG= ruleVALIDATIONS_START_TAG EOF ;
    public final String entryRuleVALIDATIONS_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVALIDATIONS_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:586:2: (iv_ruleVALIDATIONS_START_TAG= ruleVALIDATIONS_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:587:2: iv_ruleVALIDATIONS_START_TAG= ruleVALIDATIONS_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getVALIDATIONS_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleVALIDATIONS_START_TAG_in_entryRuleVALIDATIONS_START_TAG1136);
            iv_ruleVALIDATIONS_START_TAG=ruleVALIDATIONS_START_TAG();
            _fsp--;

             current =iv_ruleVALIDATIONS_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVALIDATIONS_START_TAG1147); 

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
    // $ANTLR end entryRuleVALIDATIONS_START_TAG


    // $ANTLR start ruleVALIDATIONS_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:594:1: ruleVALIDATIONS_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'validations' ) ;
    public final AntlrDatatypeRuleToken ruleVALIDATIONS_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:599:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'validations' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:600:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'validations' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:600:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'validations' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:600:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'validations'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleVALIDATIONS_START_TAG1187); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getVALIDATIONS_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,42,FOLLOW_42_in_ruleVALIDATIONS_START_TAG1205); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getVALIDATIONS_START_TAGAccess().getValidationsKeyword_1(), null); 
                

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
    // $ANTLR end ruleVALIDATIONS_START_TAG


    // $ANTLR start entryRuleCHECK_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:621:1: entryRuleCHECK_START_TAG returns [String current=null] : iv_ruleCHECK_START_TAG= ruleCHECK_START_TAG EOF ;
    public final String entryRuleCHECK_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCHECK_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:622:2: (iv_ruleCHECK_START_TAG= ruleCHECK_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:623:2: iv_ruleCHECK_START_TAG= ruleCHECK_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCHECK_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleCHECK_START_TAG_in_entryRuleCHECK_START_TAG1246);
            iv_ruleCHECK_START_TAG=ruleCHECK_START_TAG();
            _fsp--;

             current =iv_ruleCHECK_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCHECK_START_TAG1257); 

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
    // $ANTLR end entryRuleCHECK_START_TAG


    // $ANTLR start ruleCHECK_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:630:1: ruleCHECK_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'check' ) ;
    public final AntlrDatatypeRuleToken ruleCHECK_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:635:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'check' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:636:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'check' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:636:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'check' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:636:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'check'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleCHECK_START_TAG1297); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getCHECK_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,43,FOLLOW_43_in_ruleCHECK_START_TAG1315); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getCHECK_START_TAGAccess().getCheckKeyword_1(), null); 
                

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
    // $ANTLR end ruleCHECK_START_TAG


    // $ANTLR start entryRuleCOMMENT_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:657:1: entryRuleCOMMENT_START_TAG returns [String current=null] : iv_ruleCOMMENT_START_TAG= ruleCOMMENT_START_TAG EOF ;
    public final String entryRuleCOMMENT_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCOMMENT_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:658:2: (iv_ruleCOMMENT_START_TAG= ruleCOMMENT_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:659:2: iv_ruleCOMMENT_START_TAG= ruleCOMMENT_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCOMMENT_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleCOMMENT_START_TAG_in_entryRuleCOMMENT_START_TAG1356);
            iv_ruleCOMMENT_START_TAG=ruleCOMMENT_START_TAG();
            _fsp--;

             current =iv_ruleCOMMENT_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCOMMENT_START_TAG1367); 

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
    // $ANTLR end entryRuleCOMMENT_START_TAG


    // $ANTLR start ruleCOMMENT_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:666:1: ruleCOMMENT_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'comment' ) ;
    public final AntlrDatatypeRuleToken ruleCOMMENT_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:671:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'comment' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:672:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'comment' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:672:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'comment' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:672:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'comment'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleCOMMENT_START_TAG1407); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getCOMMENT_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,44,FOLLOW_44_in_ruleCOMMENT_START_TAG1425); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getCOMMENT_START_TAGAccess().getCommentKeyword_1(), null); 
                

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
    // $ANTLR end ruleCOMMENT_START_TAG


    // $ANTLR start entryRuleBREAK_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:693:1: entryRuleBREAK_START_TAG returns [String current=null] : iv_ruleBREAK_START_TAG= ruleBREAK_START_TAG EOF ;
    public final String entryRuleBREAK_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBREAK_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:694:2: (iv_ruleBREAK_START_TAG= ruleBREAK_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:695:2: iv_ruleBREAK_START_TAG= ruleBREAK_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getBREAK_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleBREAK_START_TAG_in_entryRuleBREAK_START_TAG1466);
            iv_ruleBREAK_START_TAG=ruleBREAK_START_TAG();
            _fsp--;

             current =iv_ruleBREAK_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBREAK_START_TAG1477); 

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
    // $ANTLR end entryRuleBREAK_START_TAG


    // $ANTLR start ruleBREAK_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:702:1: ruleBREAK_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'break' ) ;
    public final AntlrDatatypeRuleToken ruleBREAK_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:707:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'break' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:708:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'break' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:708:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'break' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:708:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'break'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleBREAK_START_TAG1517); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getBREAK_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,45,FOLLOW_45_in_ruleBREAK_START_TAG1535); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getBREAK_START_TAGAccess().getBreakKeyword_1(), null); 
                

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
    // $ANTLR end ruleBREAK_START_TAG


    // $ANTLR start entryRuleOPTION_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:729:1: entryRuleOPTION_START_TAG returns [String current=null] : iv_ruleOPTION_START_TAG= ruleOPTION_START_TAG EOF ;
    public final String entryRuleOPTION_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOPTION_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:730:2: (iv_ruleOPTION_START_TAG= ruleOPTION_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:731:2: iv_ruleOPTION_START_TAG= ruleOPTION_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOPTION_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleOPTION_START_TAG_in_entryRuleOPTION_START_TAG1576);
            iv_ruleOPTION_START_TAG=ruleOPTION_START_TAG();
            _fsp--;

             current =iv_ruleOPTION_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOPTION_START_TAG1587); 

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
    // $ANTLR end entryRuleOPTION_START_TAG


    // $ANTLR start ruleOPTION_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:738:1: ruleOPTION_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'option' ) ;
    public final AntlrDatatypeRuleToken ruleOPTION_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:743:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'option' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:744:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'option' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:744:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'option' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:744:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'option'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleOPTION_START_TAG1627); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getOPTION_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,46,FOLLOW_46_in_ruleOPTION_START_TAG1645); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getOPTION_START_TAGAccess().getOptionKeyword_1(), null); 
                

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
    // $ANTLR end ruleOPTION_START_TAG


    // $ANTLR start entryRuleBREAK_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:765:1: entryRuleBREAK_END_TAG returns [String current=null] : iv_ruleBREAK_END_TAG= ruleBREAK_END_TAG EOF ;
    public final String entryRuleBREAK_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBREAK_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:766:2: (iv_ruleBREAK_END_TAG= ruleBREAK_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:767:2: iv_ruleBREAK_END_TAG= ruleBREAK_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getBREAK_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleBREAK_END_TAG_in_entryRuleBREAK_END_TAG1686);
            iv_ruleBREAK_END_TAG=ruleBREAK_END_TAG();
            _fsp--;

             current =iv_ruleBREAK_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBREAK_END_TAG1697); 

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
    // $ANTLR end entryRuleBREAK_END_TAG


    // $ANTLR start ruleBREAK_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:774:1: ruleBREAK_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'break' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleBREAK_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:779:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'break' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:780:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'break' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:780:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'break' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:780:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'break' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleBREAK_END_TAG1737); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getBREAK_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,45,FOLLOW_45_in_ruleBREAK_END_TAG1755); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getBREAK_END_TAGAccess().getBreakKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleBREAK_END_TAG1770); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getBREAK_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleBREAK_END_TAG


    // $ANTLR start entryRuleOPTION_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:808:1: entryRuleOPTION_END_TAG returns [String current=null] : iv_ruleOPTION_END_TAG= ruleOPTION_END_TAG EOF ;
    public final String entryRuleOPTION_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOPTION_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:809:2: (iv_ruleOPTION_END_TAG= ruleOPTION_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:810:2: iv_ruleOPTION_END_TAG= ruleOPTION_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOPTION_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleOPTION_END_TAG_in_entryRuleOPTION_END_TAG1816);
            iv_ruleOPTION_END_TAG=ruleOPTION_END_TAG();
            _fsp--;

             current =iv_ruleOPTION_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOPTION_END_TAG1827); 

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
    // $ANTLR end entryRuleOPTION_END_TAG


    // $ANTLR start ruleOPTION_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:817:1: ruleOPTION_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'option' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleOPTION_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:822:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'option' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:823:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'option' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:823:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'option' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:823:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'option' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleOPTION_END_TAG1867); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getOPTION_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,46,FOLLOW_46_in_ruleOPTION_END_TAG1885); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getOPTION_END_TAGAccess().getOptionKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleOPTION_END_TAG1900); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getOPTION_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleOPTION_END_TAG


    // $ANTLR start entryRuleREQUIRED_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:851:1: entryRuleREQUIRED_END_TAG returns [String current=null] : iv_ruleREQUIRED_END_TAG= ruleREQUIRED_END_TAG EOF ;
    public final String entryRuleREQUIRED_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleREQUIRED_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:852:2: (iv_ruleREQUIRED_END_TAG= ruleREQUIRED_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:853:2: iv_ruleREQUIRED_END_TAG= ruleREQUIRED_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getREQUIRED_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleREQUIRED_END_TAG_in_entryRuleREQUIRED_END_TAG1946);
            iv_ruleREQUIRED_END_TAG=ruleREQUIRED_END_TAG();
            _fsp--;

             current =iv_ruleREQUIRED_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleREQUIRED_END_TAG1957); 

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
    // $ANTLR end entryRuleREQUIRED_END_TAG


    // $ANTLR start ruleREQUIRED_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:860:1: ruleREQUIRED_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'required' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleREQUIRED_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:865:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'required' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:866:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'required' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:866:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'required' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:866:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'required' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleREQUIRED_END_TAG1997); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getREQUIRED_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,41,FOLLOW_41_in_ruleREQUIRED_END_TAG2015); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getREQUIRED_END_TAGAccess().getRequiredKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleREQUIRED_END_TAG2030); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getREQUIRED_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleREQUIRED_END_TAG


    // $ANTLR start entryRulePROPERTY_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:894:1: entryRulePROPERTY_END_TAG returns [String current=null] : iv_rulePROPERTY_END_TAG= rulePROPERTY_END_TAG EOF ;
    public final String entryRulePROPERTY_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePROPERTY_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:895:2: (iv_rulePROPERTY_END_TAG= rulePROPERTY_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:896:2: iv_rulePROPERTY_END_TAG= rulePROPERTY_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPROPERTY_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_rulePROPERTY_END_TAG_in_entryRulePROPERTY_END_TAG2076);
            iv_rulePROPERTY_END_TAG=rulePROPERTY_END_TAG();
            _fsp--;

             current =iv_rulePROPERTY_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePROPERTY_END_TAG2087); 

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
    // $ANTLR end entryRulePROPERTY_END_TAG


    // $ANTLR start rulePROPERTY_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:903:1: rulePROPERTY_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'property' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken rulePROPERTY_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:908:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'property' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:909:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'property' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:909:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'property' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:909:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'property' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_rulePROPERTY_END_TAG2127); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getPROPERTY_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,40,FOLLOW_40_in_rulePROPERTY_END_TAG2145); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getPROPERTY_END_TAGAccess().getPropertyKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_rulePROPERTY_END_TAG2160); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getPROPERTY_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end rulePROPERTY_END_TAG


    // $ANTLR start entryRuleCOMMENT_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:937:1: entryRuleCOMMENT_END_TAG returns [String current=null] : iv_ruleCOMMENT_END_TAG= ruleCOMMENT_END_TAG EOF ;
    public final String entryRuleCOMMENT_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCOMMENT_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:938:2: (iv_ruleCOMMENT_END_TAG= ruleCOMMENT_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:939:2: iv_ruleCOMMENT_END_TAG= ruleCOMMENT_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCOMMENT_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleCOMMENT_END_TAG_in_entryRuleCOMMENT_END_TAG2206);
            iv_ruleCOMMENT_END_TAG=ruleCOMMENT_END_TAG();
            _fsp--;

             current =iv_ruleCOMMENT_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCOMMENT_END_TAG2217); 

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
    // $ANTLR end entryRuleCOMMENT_END_TAG


    // $ANTLR start ruleCOMMENT_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:946:1: ruleCOMMENT_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'comment' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleCOMMENT_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:951:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'comment' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:952:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'comment' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:952:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'comment' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:952:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'comment' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleCOMMENT_END_TAG2257); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getCOMMENT_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,44,FOLLOW_44_in_ruleCOMMENT_END_TAG2275); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getCOMMENT_END_TAGAccess().getCommentKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleCOMMENT_END_TAG2290); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getCOMMENT_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleCOMMENT_END_TAG


    // $ANTLR start entryRuleVALIDATIONS_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:980:1: entryRuleVALIDATIONS_END_TAG returns [String current=null] : iv_ruleVALIDATIONS_END_TAG= ruleVALIDATIONS_END_TAG EOF ;
    public final String entryRuleVALIDATIONS_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVALIDATIONS_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:981:2: (iv_ruleVALIDATIONS_END_TAG= ruleVALIDATIONS_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:982:2: iv_ruleVALIDATIONS_END_TAG= ruleVALIDATIONS_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getVALIDATIONS_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleVALIDATIONS_END_TAG_in_entryRuleVALIDATIONS_END_TAG2336);
            iv_ruleVALIDATIONS_END_TAG=ruleVALIDATIONS_END_TAG();
            _fsp--;

             current =iv_ruleVALIDATIONS_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVALIDATIONS_END_TAG2347); 

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
    // $ANTLR end entryRuleVALIDATIONS_END_TAG


    // $ANTLR start ruleVALIDATIONS_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:989:1: ruleVALIDATIONS_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'validations' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleVALIDATIONS_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:994:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'validations' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:995:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'validations' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:995:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'validations' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:995:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'validations' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleVALIDATIONS_END_TAG2387); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getVALIDATIONS_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,42,FOLLOW_42_in_ruleVALIDATIONS_END_TAG2405); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getVALIDATIONS_END_TAGAccess().getValidationsKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleVALIDATIONS_END_TAG2420); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getVALIDATIONS_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleVALIDATIONS_END_TAG


    // $ANTLR start entryRuleCHECK_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1023:1: entryRuleCHECK_END_TAG returns [String current=null] : iv_ruleCHECK_END_TAG= ruleCHECK_END_TAG EOF ;
    public final String entryRuleCHECK_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCHECK_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1024:2: (iv_ruleCHECK_END_TAG= ruleCHECK_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1025:2: iv_ruleCHECK_END_TAG= ruleCHECK_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCHECK_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleCHECK_END_TAG_in_entryRuleCHECK_END_TAG2466);
            iv_ruleCHECK_END_TAG=ruleCHECK_END_TAG();
            _fsp--;

             current =iv_ruleCHECK_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCHECK_END_TAG2477); 

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
    // $ANTLR end entryRuleCHECK_END_TAG


    // $ANTLR start ruleCHECK_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1032:1: ruleCHECK_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'check' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleCHECK_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1037:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'check' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1038:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'check' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1038:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'check' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1038:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'check' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleCHECK_END_TAG2517); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getCHECK_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,43,FOLLOW_43_in_ruleCHECK_END_TAG2535); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getCHECK_END_TAGAccess().getCheckKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleCHECK_END_TAG2550); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getCHECK_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleCHECK_END_TAG


    // $ANTLR start entryRulePARAM_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1066:1: entryRulePARAM_END_TAG returns [String current=null] : iv_rulePARAM_END_TAG= rulePARAM_END_TAG EOF ;
    public final String entryRulePARAM_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePARAM_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1067:2: (iv_rulePARAM_END_TAG= rulePARAM_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1068:2: iv_rulePARAM_END_TAG= rulePARAM_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPARAM_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_rulePARAM_END_TAG_in_entryRulePARAM_END_TAG2596);
            iv_rulePARAM_END_TAG=rulePARAM_END_TAG();
            _fsp--;

             current =iv_rulePARAM_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePARAM_END_TAG2607); 

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
    // $ANTLR end entryRulePARAM_END_TAG


    // $ANTLR start rulePARAM_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1075:1: rulePARAM_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'param' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken rulePARAM_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1080:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'param' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1081:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'param' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1081:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'param' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1081:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'param' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_rulePARAM_END_TAG2647); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getPARAM_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,47,FOLLOW_47_in_rulePARAM_END_TAG2665); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getPARAM_END_TAGAccess().getParamKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_rulePARAM_END_TAG2680); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getPARAM_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end rulePARAM_END_TAG


    // $ANTLR start entryRuleMESSAGE_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1109:1: entryRuleMESSAGE_END_TAG returns [String current=null] : iv_ruleMESSAGE_END_TAG= ruleMESSAGE_END_TAG EOF ;
    public final String entryRuleMESSAGE_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMESSAGE_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1110:2: (iv_ruleMESSAGE_END_TAG= ruleMESSAGE_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1111:2: iv_ruleMESSAGE_END_TAG= ruleMESSAGE_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMESSAGE_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleMESSAGE_END_TAG_in_entryRuleMESSAGE_END_TAG2726);
            iv_ruleMESSAGE_END_TAG=ruleMESSAGE_END_TAG();
            _fsp--;

             current =iv_ruleMESSAGE_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMESSAGE_END_TAG2737); 

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
    // $ANTLR end entryRuleMESSAGE_END_TAG


    // $ANTLR start ruleMESSAGE_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1118:1: ruleMESSAGE_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'message' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleMESSAGE_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1123:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'message' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1124:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'message' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1124:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'message' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1124:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'message' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleMESSAGE_END_TAG2777); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getMESSAGE_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,48,FOLLOW_48_in_ruleMESSAGE_END_TAG2795); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getMESSAGE_END_TAGAccess().getMessageKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMESSAGE_END_TAG2810); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getMESSAGE_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleMESSAGE_END_TAG


    // $ANTLR start entryRuleMETHODS_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1152:1: entryRuleMETHODS_END_TAG returns [String current=null] : iv_ruleMETHODS_END_TAG= ruleMETHODS_END_TAG EOF ;
    public final String entryRuleMETHODS_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMETHODS_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1153:2: (iv_ruleMETHODS_END_TAG= ruleMETHODS_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1154:2: iv_ruleMETHODS_END_TAG= ruleMETHODS_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMETHODS_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleMETHODS_END_TAG_in_entryRuleMETHODS_END_TAG2856);
            iv_ruleMETHODS_END_TAG=ruleMETHODS_END_TAG();
            _fsp--;

             current =iv_ruleMETHODS_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMETHODS_END_TAG2867); 

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
    // $ANTLR end entryRuleMETHODS_END_TAG


    // $ANTLR start ruleMETHODS_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1161:1: ruleMETHODS_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'methods' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleMETHODS_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1166:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'methods' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1167:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'methods' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1167:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'methods' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1167:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'methods' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleMETHODS_END_TAG2907); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getMETHODS_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,49,FOLLOW_49_in_ruleMETHODS_END_TAG2925); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getMETHODS_END_TAGAccess().getMethodsKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMETHODS_END_TAG2940); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getMETHODS_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleMETHODS_END_TAG


    // $ANTLR start entryRuleMETHOD_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1195:1: entryRuleMETHOD_END_TAG returns [String current=null] : iv_ruleMETHOD_END_TAG= ruleMETHOD_END_TAG EOF ;
    public final String entryRuleMETHOD_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMETHOD_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1196:2: (iv_ruleMETHOD_END_TAG= ruleMETHOD_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1197:2: iv_ruleMETHOD_END_TAG= ruleMETHOD_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMETHOD_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleMETHOD_END_TAG_in_entryRuleMETHOD_END_TAG2986);
            iv_ruleMETHOD_END_TAG=ruleMETHOD_END_TAG();
            _fsp--;

             current =iv_ruleMETHOD_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMETHOD_END_TAG2997); 

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
    // $ANTLR end entryRuleMETHOD_END_TAG


    // $ANTLR start ruleMETHOD_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1204:1: ruleMETHOD_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'method' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleMETHOD_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1209:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'method' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1210:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'method' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1210:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'method' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1210:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'method' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleMETHOD_END_TAG3037); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getMETHOD_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,50,FOLLOW_50_in_ruleMETHOD_END_TAG3055); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getMETHOD_END_TAGAccess().getMethodKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMETHOD_END_TAG3070); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getMETHOD_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleMETHOD_END_TAG


    // $ANTLR start entryRuleFIELD_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1238:1: entryRuleFIELD_END_TAG returns [String current=null] : iv_ruleFIELD_END_TAG= ruleFIELD_END_TAG EOF ;
    public final String entryRuleFIELD_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFIELD_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1239:2: (iv_ruleFIELD_END_TAG= ruleFIELD_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1240:2: iv_ruleFIELD_END_TAG= ruleFIELD_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFIELD_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleFIELD_END_TAG_in_entryRuleFIELD_END_TAG3116);
            iv_ruleFIELD_END_TAG=ruleFIELD_END_TAG();
            _fsp--;

             current =iv_ruleFIELD_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFIELD_END_TAG3127); 

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
    // $ANTLR end entryRuleFIELD_END_TAG


    // $ANTLR start ruleFIELD_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1247:1: ruleFIELD_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'field' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleFIELD_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1252:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'field' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1253:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'field' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1253:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'field' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1253:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'field' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleFIELD_END_TAG3167); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getFIELD_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,51,FOLLOW_51_in_ruleFIELD_END_TAG3185); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getFIELD_END_TAGAccess().getFieldKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleFIELD_END_TAG3200); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getFIELD_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleFIELD_END_TAG


    // $ANTLR start entryRuleEXPRESSION_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1281:1: entryRuleEXPRESSION_START_TAG returns [String current=null] : iv_ruleEXPRESSION_START_TAG= ruleEXPRESSION_START_TAG EOF ;
    public final String entryRuleEXPRESSION_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEXPRESSION_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1282:2: (iv_ruleEXPRESSION_START_TAG= ruleEXPRESSION_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1283:2: iv_ruleEXPRESSION_START_TAG= ruleEXPRESSION_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEXPRESSION_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleEXPRESSION_START_TAG_in_entryRuleEXPRESSION_START_TAG3246);
            iv_ruleEXPRESSION_START_TAG=ruleEXPRESSION_START_TAG();
            _fsp--;

             current =iv_ruleEXPRESSION_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEXPRESSION_START_TAG3257); 

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
    // $ANTLR end entryRuleEXPRESSION_START_TAG


    // $ANTLR start ruleEXPRESSION_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1290:1: ruleEXPRESSION_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'expression' ) ;
    public final AntlrDatatypeRuleToken ruleEXPRESSION_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1295:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'expression' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1296:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'expression' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1296:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'expression' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1296:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'expression'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleEXPRESSION_START_TAG3297); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getEXPRESSION_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,52,FOLLOW_52_in_ruleEXPRESSION_START_TAG3315); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getEXPRESSION_START_TAGAccess().getExpressionKeyword_1(), null); 
                

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
    // $ANTLR end ruleEXPRESSION_START_TAG


    // $ANTLR start entryRuleEXPRESSION_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1317:1: entryRuleEXPRESSION_END_TAG returns [String current=null] : iv_ruleEXPRESSION_END_TAG= ruleEXPRESSION_END_TAG EOF ;
    public final String entryRuleEXPRESSION_END_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEXPRESSION_END_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1318:2: (iv_ruleEXPRESSION_END_TAG= ruleEXPRESSION_END_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1319:2: iv_ruleEXPRESSION_END_TAG= ruleEXPRESSION_END_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEXPRESSION_END_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleEXPRESSION_END_TAG_in_entryRuleEXPRESSION_END_TAG3356);
            iv_ruleEXPRESSION_END_TAG=ruleEXPRESSION_END_TAG();
            _fsp--;

             current =iv_ruleEXPRESSION_END_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEXPRESSION_END_TAG3367); 

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
    // $ANTLR end entryRuleEXPRESSION_END_TAG


    // $ANTLR start ruleEXPRESSION_END_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1326:1: ruleEXPRESSION_END_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'expression' this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleEXPRESSION_END_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token kw=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1331:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'expression' this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1332:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'expression' this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1332:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'expression' this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1332:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG kw= 'expression' this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleEXPRESSION_END_TAG3407); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getEXPRESSION_END_TAGAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,52,FOLLOW_52_in_ruleEXPRESSION_END_TAG3425); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getEXPRESSION_END_TAGAccess().getExpressionKeyword_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleEXPRESSION_END_TAG3440); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getEXPRESSION_END_TAGAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleEXPRESSION_END_TAG


    // $ANTLR start entryRulePARAM_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:1: entryRulePARAM_START_TAG returns [String current=null] : iv_rulePARAM_START_TAG= rulePARAM_START_TAG EOF ;
    public final String entryRulePARAM_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePARAM_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1361:2: (iv_rulePARAM_START_TAG= rulePARAM_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1362:2: iv_rulePARAM_START_TAG= rulePARAM_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPARAM_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_rulePARAM_START_TAG_in_entryRulePARAM_START_TAG3486);
            iv_rulePARAM_START_TAG=rulePARAM_START_TAG();
            _fsp--;

             current =iv_rulePARAM_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePARAM_START_TAG3497); 

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
    // $ANTLR end entryRulePARAM_START_TAG


    // $ANTLR start rulePARAM_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1369:1: rulePARAM_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'param' ) ;
    public final AntlrDatatypeRuleToken rulePARAM_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1374:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'param' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1375:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'param' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1375:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'param' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1375:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'param'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_rulePARAM_START_TAG3537); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getPARAM_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,47,FOLLOW_47_in_rulePARAM_START_TAG3555); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getPARAM_START_TAGAccess().getParamKeyword_1(), null); 
                

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
    // $ANTLR end rulePARAM_START_TAG


    // $ANTLR start entryRuleMESSAGE_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1396:1: entryRuleMESSAGE_START_TAG returns [String current=null] : iv_ruleMESSAGE_START_TAG= ruleMESSAGE_START_TAG EOF ;
    public final String entryRuleMESSAGE_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMESSAGE_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1397:2: (iv_ruleMESSAGE_START_TAG= ruleMESSAGE_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1398:2: iv_ruleMESSAGE_START_TAG= ruleMESSAGE_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMESSAGE_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleMESSAGE_START_TAG_in_entryRuleMESSAGE_START_TAG3596);
            iv_ruleMESSAGE_START_TAG=ruleMESSAGE_START_TAG();
            _fsp--;

             current =iv_ruleMESSAGE_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMESSAGE_START_TAG3607); 

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
    // $ANTLR end entryRuleMESSAGE_START_TAG


    // $ANTLR start ruleMESSAGE_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1405:1: ruleMESSAGE_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'message' ) ;
    public final AntlrDatatypeRuleToken ruleMESSAGE_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1410:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'message' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1411:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'message' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1411:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'message' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1411:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'message'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleMESSAGE_START_TAG3647); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getMESSAGE_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,48,FOLLOW_48_in_ruleMESSAGE_START_TAG3665); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getMESSAGE_START_TAGAccess().getMessageKeyword_1(), null); 
                

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
    // $ANTLR end ruleMESSAGE_START_TAG


    // $ANTLR start entryRuleMETHOD_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1432:1: entryRuleMETHOD_START_TAG returns [String current=null] : iv_ruleMETHOD_START_TAG= ruleMETHOD_START_TAG EOF ;
    public final String entryRuleMETHOD_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMETHOD_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1433:2: (iv_ruleMETHOD_START_TAG= ruleMETHOD_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1434:2: iv_ruleMETHOD_START_TAG= ruleMETHOD_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMETHOD_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleMETHOD_START_TAG_in_entryRuleMETHOD_START_TAG3706);
            iv_ruleMETHOD_START_TAG=ruleMETHOD_START_TAG();
            _fsp--;

             current =iv_ruleMETHOD_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMETHOD_START_TAG3717); 

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
    // $ANTLR end entryRuleMETHOD_START_TAG


    // $ANTLR start ruleMETHOD_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1441:1: ruleMETHOD_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'method' ) ;
    public final AntlrDatatypeRuleToken ruleMETHOD_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1446:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'method' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1447:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'method' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1447:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'method' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1447:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'method'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleMETHOD_START_TAG3757); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getMETHOD_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,50,FOLLOW_50_in_ruleMETHOD_START_TAG3775); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getMETHOD_START_TAGAccess().getMethodKeyword_1(), null); 
                

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
    // $ANTLR end ruleMETHOD_START_TAG


    // $ANTLR start entryRuleMETHODS_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1468:1: entryRuleMETHODS_START_TAG returns [String current=null] : iv_ruleMETHODS_START_TAG= ruleMETHODS_START_TAG EOF ;
    public final String entryRuleMETHODS_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMETHODS_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1469:2: (iv_ruleMETHODS_START_TAG= ruleMETHODS_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1470:2: iv_ruleMETHODS_START_TAG= ruleMETHODS_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMETHODS_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleMETHODS_START_TAG_in_entryRuleMETHODS_START_TAG3816);
            iv_ruleMETHODS_START_TAG=ruleMETHODS_START_TAG();
            _fsp--;

             current =iv_ruleMETHODS_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMETHODS_START_TAG3827); 

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
    // $ANTLR end entryRuleMETHODS_START_TAG


    // $ANTLR start ruleMETHODS_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1477:1: ruleMETHODS_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'methods' ) ;
    public final AntlrDatatypeRuleToken ruleMETHODS_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1482:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'methods' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1483:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'methods' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1483:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'methods' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1483:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'methods'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleMETHODS_START_TAG3867); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getMETHODS_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,49,FOLLOW_49_in_ruleMETHODS_START_TAG3885); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getMETHODS_START_TAGAccess().getMethodsKeyword_1(), null); 
                

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
    // $ANTLR end ruleMETHODS_START_TAG


    // $ANTLR start entryRuleFIELD_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1504:1: entryRuleFIELD_START_TAG returns [String current=null] : iv_ruleFIELD_START_TAG= ruleFIELD_START_TAG EOF ;
    public final String entryRuleFIELD_START_TAG() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFIELD_START_TAG = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1505:2: (iv_ruleFIELD_START_TAG= ruleFIELD_START_TAG EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1506:2: iv_ruleFIELD_START_TAG= ruleFIELD_START_TAG EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFIELD_START_TAGRule(), currentNode); 
            pushFollow(FOLLOW_ruleFIELD_START_TAG_in_entryRuleFIELD_START_TAG3926);
            iv_ruleFIELD_START_TAG=ruleFIELD_START_TAG();
            _fsp--;

             current =iv_ruleFIELD_START_TAG.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFIELD_START_TAG3937); 

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
    // $ANTLR end entryRuleFIELD_START_TAG


    // $ANTLR start ruleFIELD_START_TAG
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1513:1: ruleFIELD_START_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'field' ) ;
    public final AntlrDatatypeRuleToken ruleFIELD_START_TAG() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1518:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'field' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1519:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'field' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1519:1: (this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'field' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1519:6: this_XML_TAG_START_0= RULE_XML_TAG_START kw= 'field'
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleFIELD_START_TAG3977); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getFIELD_START_TAGAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            kw=(Token)input.LT(1);
            match(input,51,FOLLOW_51_in_ruleFIELD_START_TAG3995); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getFIELD_START_TAGAccess().getFieldKeyword_1(), null); 
                

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
    // $ANTLR end ruleFIELD_START_TAG


    // $ANTLR start entryRuleNAVASCRIPT_START
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1540:1: entryRuleNAVASCRIPT_START returns [String current=null] : iv_ruleNAVASCRIPT_START= ruleNAVASCRIPT_START EOF ;
    public final String entryRuleNAVASCRIPT_START() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNAVASCRIPT_START = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1541:2: (iv_ruleNAVASCRIPT_START= ruleNAVASCRIPT_START EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1542:2: iv_ruleNAVASCRIPT_START= ruleNAVASCRIPT_START EOF
            {
             currentNode = createCompositeNode(grammarAccess.getNAVASCRIPT_STARTRule(), currentNode); 
            pushFollow(FOLLOW_ruleNAVASCRIPT_START_in_entryRuleNAVASCRIPT_START4036);
            iv_ruleNAVASCRIPT_START=ruleNAVASCRIPT_START();
            _fsp--;

             current =iv_ruleNAVASCRIPT_START.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNAVASCRIPT_START4047); 

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
    // $ANTLR end entryRuleNAVASCRIPT_START


    // $ANTLR start ruleNAVASCRIPT_START
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1549:1: ruleNAVASCRIPT_START returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_TAG_START_0= RULE_XML_TAG_START this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD ) ;
    public final AntlrDatatypeRuleToken ruleNAVASCRIPT_START() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_TAG_START_0=null;
        Token this_NAVASCRIPT_KEYWORD_1=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1554:6: ( (this_XML_TAG_START_0= RULE_XML_TAG_START this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1555:1: (this_XML_TAG_START_0= RULE_XML_TAG_START this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1555:1: (this_XML_TAG_START_0= RULE_XML_TAG_START this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1555:6: this_XML_TAG_START_0= RULE_XML_TAG_START this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD
            {
            this_XML_TAG_START_0=(Token)input.LT(1);
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleNAVASCRIPT_START4087); 

            		current.merge(this_XML_TAG_START_0);
                
             
                createLeafNode(grammarAccess.getNAVASCRIPT_STARTAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            this_NAVASCRIPT_KEYWORD_1=(Token)input.LT(1);
            match(input,RULE_NAVASCRIPT_KEYWORD,FOLLOW_RULE_NAVASCRIPT_KEYWORD_in_ruleNAVASCRIPT_START4107); 

            		current.merge(this_NAVASCRIPT_KEYWORD_1);
                
             
                createLeafNode(grammarAccess.getNAVASCRIPT_STARTAccess().getNAVASCRIPT_KEYWORDTerminalRuleCall_1(), null); 
                

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
    // $ANTLR end ruleNAVASCRIPT_START


    // $ANTLR start entryRuleNAVASCRIPT_END
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1577:1: entryRuleNAVASCRIPT_END returns [String current=null] : iv_ruleNAVASCRIPT_END= ruleNAVASCRIPT_END EOF ;
    public final String entryRuleNAVASCRIPT_END() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNAVASCRIPT_END = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1578:2: (iv_ruleNAVASCRIPT_END= ruleNAVASCRIPT_END EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1579:2: iv_ruleNAVASCRIPT_END= ruleNAVASCRIPT_END EOF
            {
             currentNode = createCompositeNode(grammarAccess.getNAVASCRIPT_ENDRule(), currentNode); 
            pushFollow(FOLLOW_ruleNAVASCRIPT_END_in_entryRuleNAVASCRIPT_END4153);
            iv_ruleNAVASCRIPT_END=ruleNAVASCRIPT_END();
            _fsp--;

             current =iv_ruleNAVASCRIPT_END.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNAVASCRIPT_END4164); 

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
    // $ANTLR end entryRuleNAVASCRIPT_END


    // $ANTLR start ruleNAVASCRIPT_END
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1586:1: ruleNAVASCRIPT_END returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD this_XML_TAG_END_2= RULE_XML_TAG_END ) ;
    public final AntlrDatatypeRuleToken ruleNAVASCRIPT_END() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_XML_START_ENDTAG_0=null;
        Token this_NAVASCRIPT_KEYWORD_1=null;
        Token this_XML_TAG_END_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1591:6: ( (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD this_XML_TAG_END_2= RULE_XML_TAG_END ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1592:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD this_XML_TAG_END_2= RULE_XML_TAG_END )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1592:1: (this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD this_XML_TAG_END_2= RULE_XML_TAG_END )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1592:6: this_XML_START_ENDTAG_0= RULE_XML_START_ENDTAG this_NAVASCRIPT_KEYWORD_1= RULE_NAVASCRIPT_KEYWORD this_XML_TAG_END_2= RULE_XML_TAG_END
            {
            this_XML_START_ENDTAG_0=(Token)input.LT(1);
            match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleNAVASCRIPT_END4204); 

            		current.merge(this_XML_START_ENDTAG_0);
                
             
                createLeafNode(grammarAccess.getNAVASCRIPT_ENDAccess().getXML_START_ENDTAGTerminalRuleCall_0(), null); 
                
            this_NAVASCRIPT_KEYWORD_1=(Token)input.LT(1);
            match(input,RULE_NAVASCRIPT_KEYWORD,FOLLOW_RULE_NAVASCRIPT_KEYWORD_in_ruleNAVASCRIPT_END4224); 

            		current.merge(this_NAVASCRIPT_KEYWORD_1);
                
             
                createLeafNode(grammarAccess.getNAVASCRIPT_ENDAccess().getNAVASCRIPT_KEYWORDTerminalRuleCall_1(), null); 
                
            this_XML_TAG_END_2=(Token)input.LT(1);
            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleNAVASCRIPT_END4244); 

            		current.merge(this_XML_TAG_END_2);
                
             
                createLeafNode(grammarAccess.getNAVASCRIPT_ENDAccess().getXML_TAG_ENDTerminalRuleCall_2(), null); 
                

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
    // $ANTLR end ruleNAVASCRIPT_END


    // $ANTLR start entryRuleAttributeName
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1621:1: entryRuleAttributeName returns [String current=null] : iv_ruleAttributeName= ruleAttributeName EOF ;
    public final String entryRuleAttributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAttributeName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1622:2: (iv_ruleAttributeName= ruleAttributeName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1623:2: iv_ruleAttributeName= ruleAttributeName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAttributeNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleAttributeName_in_entryRuleAttributeName4290);
            iv_ruleAttributeName=ruleAttributeName();
            _fsp--;

             current =iv_ruleAttributeName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAttributeName4301); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1630:1: ruleAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= 'message' | kw= 'comment' ) ;
    public final AntlrDatatypeRuleToken ruleAttributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1635:6: ( (this_ID_0= RULE_ID | kw= 'message' | kw= 'comment' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1636:1: (this_ID_0= RULE_ID | kw= 'message' | kw= 'comment' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1636:1: (this_ID_0= RULE_ID | kw= 'message' | kw= 'comment' )
            int alt4=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt4=1;
                }
                break;
            case 48:
                {
                alt4=2;
                }
                break;
            case 44:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("1636:1: (this_ID_0= RULE_ID | kw= 'message' | kw= 'comment' )", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1636:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAttributeName4341); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getAttributeNameAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1645:2: kw= 'message'
                    {
                    kw=(Token)input.LT(1);
                    match(input,48,FOLLOW_48_in_ruleAttributeName4365); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getAttributeNameAccess().getMessageKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1652:2: kw= 'comment'
                    {
                    kw=(Token)input.LT(1);
                    match(input,44,FOLLOW_44_in_ruleAttributeName4384); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getAttributeNameAccess().getCommentKeyword_2(), null); 
                        

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
    // $ANTLR end ruleAttributeName


    // $ANTLR start entryRulePossibleExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1665:1: entryRulePossibleExpression returns [EObject current=null] : iv_rulePossibleExpression= rulePossibleExpression EOF ;
    public final EObject entryRulePossibleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePossibleExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1666:2: (iv_rulePossibleExpression= rulePossibleExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1667:2: iv_rulePossibleExpression= rulePossibleExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPossibleExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression4424);
            iv_rulePossibleExpression=rulePossibleExpression();
            _fsp--;

             current =iv_rulePossibleExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePossibleExpression4434); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1674:1: rulePossibleExpression returns [EObject current=null] : ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) ) ;
    public final EObject rulePossibleExpression() throws RecognitionException {
        EObject current = null;

        Token lv_namespace_0_0=null;
        Token lv_value_7_0=null;
        AntlrDatatypeRuleToken lv_key_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1679:6: ( ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1680:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1680:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1680:2: ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )? ( (lv_key_2_0= ruleAttributeName ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1680:2: ( ( (lv_namespace_0_0= RULE_ID ) ) ':' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_ID) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==53) ) {
                    alt5=1;
                }
            }
            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1680:3: ( (lv_namespace_0_0= RULE_ID ) ) ':'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1680:3: ( (lv_namespace_0_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1681:1: (lv_namespace_0_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1681:1: (lv_namespace_0_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1682:3: lv_namespace_0_0= RULE_ID
                    {
                    lv_namespace_0_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePossibleExpression4477); 

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

                    match(input,53,FOLLOW_53_in_rulePossibleExpression4492); 

                            createLeafNode(grammarAccess.getPossibleExpressionAccess().getColonKeyword_0_1(), null); 
                        

                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1708:3: ( (lv_key_2_0= ruleAttributeName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1709:1: (lv_key_2_0= ruleAttributeName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1709:1: (lv_key_2_0= ruleAttributeName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1710:3: lv_key_2_0= ruleAttributeName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getKeyAttributeNameParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAttributeName_in_rulePossibleExpression4515);
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

            match(input,54,FOLLOW_54_in_rulePossibleExpression4525); 

                    createLeafNode(grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1736:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )
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
                    new NoViableAltException("1736:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1736:2: ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1736:2: ( RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1736:3: RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) RULE_SEMICOLONQUOTE
                    {
                    match(input,RULE_QUOTEQ,FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression4536); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getQUOTEQTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1740:1: ( (lv_expressionValue_5_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1741:1: (lv_expressionValue_5_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1741:1: (lv_expressionValue_5_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1742:3: lv_expressionValue_5_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_3_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_rulePossibleExpression4556);
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

                    match(input,RULE_SEMICOLONQUOTE,FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression4565); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getSEMICOLONQUOTETerminalRuleCall_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1769:6: ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1769:6: ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1770:1: (lv_value_7_0= RULE_ATTRIBUTESTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1770:1: (lv_value_7_0= RULE_ATTRIBUTESTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1771:3: lv_value_7_0= RULE_ATTRIBUTESTRING
                    {
                    lv_value_7_0=(Token)input.LT(1);
                    match(input,RULE_ATTRIBUTESTRING,FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression4588); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1794:6: RULE_EMPTYSTRING
                    {
                    match(input,RULE_EMPTYSTRING,FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression4608); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1806:1: entryRuleMethods returns [EObject current=null] : iv_ruleMethods= ruleMethods EOF ;
    public final EObject entryRuleMethods() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethods = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1807:2: (iv_ruleMethods= ruleMethods EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1808:2: iv_ruleMethods= ruleMethods EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMethodsRule(), currentNode); 
            pushFollow(FOLLOW_ruleMethods_in_entryRuleMethods4644);
            iv_ruleMethods=ruleMethods();
            _fsp--;

             current =iv_ruleMethods; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethods4654); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1815:1: ruleMethods returns [EObject current=null] : ( ruleMETHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethods() throws RecognitionException {
        EObject current = null;

        EObject lv_method_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1820:6: ( ( ruleMETHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1821:1: ( ruleMETHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1821:1: ( ruleMETHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1822:5: ruleMETHODS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getMethodsAccess().getMETHODS_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleMETHODS_START_TAG_in_ruleMethods4695);
            ruleMETHODS_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1829:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1830:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1840:2: ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("1840:2: ( ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1840:3: ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1840:3: ( RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1840:4: RULE_XML_TAG_END ( (lv_method_3_0= ruleMethod ) )* ruleMETHODS_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethods4714); 
                     
                        createLeafNode(grammarAccess.getMethodsAccess().getXML_TAG_ENDTerminalRuleCall_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1844:1: ( (lv_method_3_0= ruleMethod ) )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==RULE_XML_TAG_START) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1845:1: (lv_method_3_0= ruleMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1845:1: (lv_method_3_0= ruleMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1846:3: lv_method_3_0= ruleMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMethodsAccess().getMethodMethodParserRuleCall_2_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethod_in_ruleMethods4734);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getMethodsAccess().getMETHODS_END_TAGParserRuleCall_2_0_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleMETHODS_END_TAG_in_ruleMethods4751);
                    ruleMETHODS_END_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1877:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods4766); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1889:1: entryRuleMethod returns [EObject current=null] : iv_ruleMethod= ruleMethod EOF ;
    public final EObject entryRuleMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1890:2: (iv_ruleMethod= ruleMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1891:2: iv_ruleMethod= ruleMethod EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMethodRule(), currentNode); 
            pushFollow(FOLLOW_ruleMethod_in_entryRuleMethod4802);
            iv_ruleMethod=ruleMethod();
            _fsp--;

             current =iv_ruleMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethod4812); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1898:1: ruleMethod returns [EObject current=null] : ( ruleMETHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethod() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1903:6: ( ( ruleMETHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1904:1: ( ruleMETHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1904:1: ( ruleMETHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1905:5: ruleMETHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getMethodAccess().getMETHOD_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleMETHOD_START_TAG_in_ruleMethod4853);
            ruleMETHOD_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1912:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1913:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1923:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_ID||LA9_0==44||LA9_0==48) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1924:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1924:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1925:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMethodAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMethod4882);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1947:3: ( ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("1947:3: ( ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1947:4: ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1947:4: ( RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1947:5: RULE_XML_TAG_END ( (lv_children_4_0= ruleRequired ) )* ruleMETHOD_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethod4894); 
                     
                        createLeafNode(grammarAccess.getMethodAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1951:1: ( (lv_children_4_0= ruleRequired ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==RULE_XML_TAG_START) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1952:1: (lv_children_4_0= ruleRequired )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1952:1: (lv_children_4_0= ruleRequired )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1953:3: lv_children_4_0= ruleRequired
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMethodAccess().getChildrenRequiredParserRuleCall_3_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRequired_in_ruleMethod4914);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getMethodAccess().getMETHOD_END_TAGParserRuleCall_3_0_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleMETHOD_END_TAG_in_ruleMethod4931);
                    ruleMETHOD_END_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1984:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod4946); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1996:1: entryRuleValidations returns [EObject current=null] : iv_ruleValidations= ruleValidations EOF ;
    public final EObject entryRuleValidations() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValidations = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1997:2: (iv_ruleValidations= ruleValidations EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1998:2: iv_ruleValidations= ruleValidations EOF
            {
             currentNode = createCompositeNode(grammarAccess.getValidationsRule(), currentNode); 
            pushFollow(FOLLOW_ruleValidations_in_entryRuleValidations4982);
            iv_ruleValidations=ruleValidations();
            _fsp--;

             current =iv_ruleValidations; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleValidations4992); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2005:1: ruleValidations returns [EObject current=null] : ( ruleVALIDATIONS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleValidations() throws RecognitionException {
        EObject current = null;

        EObject lv_children_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2010:6: ( ( ruleVALIDATIONS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2011:1: ( ruleVALIDATIONS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2011:1: ( ruleVALIDATIONS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2012:5: ruleVALIDATIONS_START_TAG () ( ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getValidationsAccess().getVALIDATIONS_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleVALIDATIONS_START_TAG_in_ruleValidations5033);
            ruleVALIDATIONS_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2019:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2020:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:2: ( ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("2030:2: ( ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:3: ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:3: ( RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:4: RULE_XML_TAG_END ( (lv_children_3_0= ruleCheck ) )* ruleVALIDATIONS_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleValidations5052); 
                     
                        createLeafNode(grammarAccess.getValidationsAccess().getXML_TAG_ENDTerminalRuleCall_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2034:1: ( (lv_children_3_0= ruleCheck ) )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==RULE_XML_TAG_START) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2035:1: (lv_children_3_0= ruleCheck )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2035:1: (lv_children_3_0= ruleCheck )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2036:3: lv_children_3_0= ruleCheck
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getValidationsAccess().getChildrenCheckParserRuleCall_2_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleCheck_in_ruleValidations5072);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getValidationsAccess().getVALIDATIONS_END_TAGParserRuleCall_2_0_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleVALIDATIONS_END_TAG_in_ruleValidations5089);
                    ruleVALIDATIONS_END_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2067:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleValidations5104); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2079:1: entryRuleCheck returns [EObject current=null] : iv_ruleCheck= ruleCheck EOF ;
    public final EObject entryRuleCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheck = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2080:2: (iv_ruleCheck= ruleCheck EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2081:2: iv_ruleCheck= ruleCheck EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCheckRule(), currentNode); 
            pushFollow(FOLLOW_ruleCheck_in_entryRuleCheck5140);
            iv_ruleCheck=ruleCheck();
            _fsp--;

             current =iv_ruleCheck; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCheck5150); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2088:1: ruleCheck returns [EObject current=null] : ( ruleCHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleCheck() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expression_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2093:6: ( ( ruleCHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2094:1: ( ruleCHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2094:1: ( ruleCHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2095:5: ruleCHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getCheckAccess().getCHECK_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleCHECK_START_TAG_in_ruleCheck5191);
            ruleCHECK_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2102:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2103:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2113:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_ID||LA14_0==44||LA14_0==48) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2114:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2114:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2115:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getCheckAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleCheck5220);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2137:3: ( ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("2137:3: ( ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2137:4: ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2137:4: ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2137:5: RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleCHECK_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleCheck5232); 
                     
                        createLeafNode(grammarAccess.getCheckAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2141:1: ( (lv_expression_4_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2142:1: (lv_expression_4_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2142:1: (lv_expression_4_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2143:3: lv_expression_4_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getCheckAccess().getExpressionTopLevelParserRuleCall_3_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleCheck5252);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getCheckAccess().getCHECK_END_TAGParserRuleCall_3_0_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleCHECK_END_TAG_in_ruleCheck5268);
                    ruleCHECK_END_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2174:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleCheck5283); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2186:1: entryRuleComment returns [EObject current=null] : iv_ruleComment= ruleComment EOF ;
    public final EObject entryRuleComment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComment = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2187:2: (iv_ruleComment= ruleComment EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2188:2: iv_ruleComment= ruleComment EOF
            {
             currentNode = createCompositeNode(grammarAccess.getCommentRule(), currentNode); 
            pushFollow(FOLLOW_ruleComment_in_entryRuleComment5319);
            iv_ruleComment=ruleComment();
            _fsp--;

             current =iv_ruleComment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComment5329); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2195:1: ruleComment returns [EObject current=null] : ( ruleCOMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleCOMMENT_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleComment() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2200:6: ( ( ruleCOMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleCOMMENT_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2201:1: ( ruleCOMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleCOMMENT_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2201:1: ( ruleCOMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleCOMMENT_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2202:5: ruleCOMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleCOMMENT_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getCommentAccess().getCOMMENT_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleCOMMENT_START_TAG_in_ruleComment5370);
            ruleCOMMENT_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2209:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2210:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2220:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_ID||LA16_0==44||LA16_0==48) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2221:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2221:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2222:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getCommentAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleComment5399);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2244:3: ( ( RULE_XML_TAG_END ruleCOMMENT_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("2244:3: ( ( RULE_XML_TAG_END ruleCOMMENT_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2244:4: ( RULE_XML_TAG_END ruleCOMMENT_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2244:4: ( RULE_XML_TAG_END ruleCOMMENT_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2244:5: RULE_XML_TAG_END ruleCOMMENT_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleComment5411); 
                     
                        createLeafNode(grammarAccess.getCommentAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getCommentAccess().getCOMMENT_END_TAGParserRuleCall_3_0_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleCOMMENT_END_TAG_in_ruleComment5426);
                    ruleCOMMENT_END_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2257:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleComment5441); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2269:1: entryRuleBreak returns [EObject current=null] : iv_ruleBreak= ruleBreak EOF ;
    public final EObject entryRuleBreak() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBreak = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2270:2: (iv_ruleBreak= ruleBreak EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2271:2: iv_ruleBreak= ruleBreak EOF
            {
             currentNode = createCompositeNode(grammarAccess.getBreakRule(), currentNode); 
            pushFollow(FOLLOW_ruleBreak_in_entryRuleBreak5477);
            iv_ruleBreak=ruleBreak();
            _fsp--;

             current =iv_ruleBreak; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBreak5487); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2278:1: ruleBreak returns [EObject current=null] : ( ruleBREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleBREAK_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleBreak() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2283:6: ( ( ruleBREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleBREAK_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2284:1: ( ruleBREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleBREAK_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2284:1: ( ruleBREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleBREAK_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2285:5: ruleBREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ruleBREAK_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getBreakAccess().getBREAK_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleBREAK_START_TAG_in_ruleBreak5528);
            ruleBREAK_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2292:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2293:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2303:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==RULE_ID||LA18_0==44||LA18_0==48) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2304:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2304:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2305:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getBreakAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleBreak5557);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2327:3: ( ( RULE_XML_TAG_END ruleBREAK_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("2327:3: ( ( RULE_XML_TAG_END ruleBREAK_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2327:4: ( RULE_XML_TAG_END ruleBREAK_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2327:4: ( RULE_XML_TAG_END ruleBREAK_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2327:5: RULE_XML_TAG_END ruleBREAK_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleBreak5569); 
                     
                        createLeafNode(grammarAccess.getBreakAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getBreakAccess().getBREAK_END_TAGParserRuleCall_3_0_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleBREAK_END_TAG_in_ruleBreak5584);
                    ruleBREAK_END_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2340:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleBreak5599); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2352:1: entryRuleInclude returns [EObject current=null] : iv_ruleInclude= ruleInclude EOF ;
    public final EObject entryRuleInclude() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInclude = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2353:2: (iv_ruleInclude= ruleInclude EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2354:2: iv_ruleInclude= ruleInclude EOF
            {
             currentNode = createCompositeNode(grammarAccess.getIncludeRule(), currentNode); 
            pushFollow(FOLLOW_ruleInclude_in_entryRuleInclude5635);
            iv_ruleInclude=ruleInclude();
            _fsp--;

             current =iv_ruleInclude; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleInclude5645); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2361:1: ruleInclude returns [EObject current=null] : ( ruleINCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_SINGLEEND ) ;
    public final EObject ruleInclude() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2366:6: ( ( ruleINCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2367:1: ( ruleINCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_SINGLEEND )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2367:1: ( ruleINCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_SINGLEEND )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2368:5: ruleINCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_SINGLEEND
            {
             
                    currentNode=createCompositeNode(grammarAccess.getIncludeAccess().getINCLUDE_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleINCLUDE_START_TAG_in_ruleInclude5686);
            ruleINCLUDE_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2375:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2376:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==RULE_ID||LA20_0==44||LA20_0==48) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2387:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2387:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2388:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getIncludeAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleInclude5715);
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

            match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleInclude5725); 
             
                createLeafNode(grammarAccess.getIncludeAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3(), null); 
                

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2422:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2423:2: (iv_ruleMessage= ruleMessage EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2424:2: iv_ruleMessage= ruleMessage EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMessageRule(), currentNode); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage5760);
            iv_ruleMessage=ruleMessage();
            _fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage5770); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2431:1: ruleMessage returns [EObject current=null] : ( ruleMESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2436:6: ( ( ruleMESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2437:1: ( ruleMESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2437:1: ( ruleMESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2438:5: ruleMESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getMessageAccess().getMESSAGE_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleMESSAGE_START_TAG_in_ruleMessage5811);
            ruleMESSAGE_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2445:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2446:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2456:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE_ID||LA21_0==44||LA21_0==48) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2457:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2457:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2458:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMessage5840);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2480:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("2480:3: ( ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG ) | RULE_XML_TAG_SINGLEEND )", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2480:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2480:4: ( RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2480:5: RULE_XML_TAG_END ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ruleMESSAGE_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMessage5852); 
                     
                        createLeafNode(grammarAccess.getMessageAccess().getXML_TAG_ENDTerminalRuleCall_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2484:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )*
                    loop22:
                    do {
                        int alt22=10;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==RULE_XML_TAG_START) ) {
                            switch ( input.LA(2) ) {
                            case 48:
                                {
                                alt22=1;
                                }
                                break;
                            case 51:
                                {
                                alt22=7;
                                }
                                break;
                            case RULE_MAPKEYWORD:
                                {
                                alt22=4;
                                }
                                break;
                            case 45:
                                {
                                alt22=9;
                                }
                                break;
                            case RULE_ID:
                                {
                                alt22=5;
                                }
                                break;
                            case 40:
                                {
                                alt22=2;
                                }
                                break;
                            case 44:
                                {
                                alt22=8;
                                }
                                break;
                            case 38:
                                {
                                alt22=6;
                                }
                                break;
                            case 47:
                                {
                                alt22=3;
                                }
                                break;

                            }

                        }


                        switch (alt22) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2484:2: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2484:2: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2485:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2485:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2486:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMessage5873);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2509:6: ( (lv_children_5_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2509:6: ( (lv_children_5_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2510:1: (lv_children_5_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2510:1: (lv_children_5_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2511:3: lv_children_5_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMessage5900);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2534:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2534:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2535:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2535:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2536:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenParamParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMessage5927);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2559:6: ( (lv_children_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2559:6: ( (lv_children_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2560:1: (lv_children_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2560:1: (lv_children_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2561:3: lv_children_7_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMessage5954);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2584:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2584:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2585:1: (lv_children_8_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2585:1: (lv_children_8_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2586:3: lv_children_8_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapMethodParserRuleCall_3_0_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMessage5981);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2609:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2609:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2610:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2610:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2611:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenDebugTagParserRuleCall_3_0_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMessage6008);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2634:6: ( (lv_children_10_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2634:6: ( (lv_children_10_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2635:1: (lv_children_10_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2635:1: (lv_children_10_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2636:3: lv_children_10_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenFieldParserRuleCall_3_0_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMessage6035);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2659:6: ( (lv_children_11_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2659:6: ( (lv_children_11_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2660:1: (lv_children_11_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2660:1: (lv_children_11_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2661:3: lv_children_11_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenCommentParserRuleCall_3_0_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMessage6062);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2684:6: ( (lv_children_12_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2684:6: ( (lv_children_12_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2685:1: (lv_children_12_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2685:1: (lv_children_12_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2686:3: lv_children_12_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenBreakParserRuleCall_3_0_1_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMessage6089);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getMessageAccess().getMESSAGE_END_TAGParserRuleCall_3_0_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleMESSAGE_END_TAG_in_ruleMessage6107);
                    ruleMESSAGE_END_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2717:6: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage6122); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2729:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2730:2: (iv_ruleMap= ruleMap EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2731:2: iv_ruleMap= ruleMap EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapRule(), currentNode); 
            pushFollow(FOLLOW_ruleMap_in_entryRuleMap6158);
            iv_ruleMap=ruleMap();
            _fsp--;

             current =iv_ruleMap; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMap6168); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2738:1: ruleMap returns [EObject current=null] : ( ruleMAPSTARTKEYWORD () ( ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) ) ) ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

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

        AntlrDatatypeRuleToken lv_mapClosingName_20_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2743:6: ( ( ruleMAPSTARTKEYWORD () ( ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2744:1: ( ruleMAPSTARTKEYWORD () ( ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2744:1: ( ruleMAPSTARTKEYWORD () ( ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2745:5: ruleMAPSTARTKEYWORD () ( ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) ) )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getMapAccess().getMAPSTARTKEYWORDParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleMAPSTARTKEYWORD_in_ruleMap6209);
            ruleMAPSTARTKEYWORD();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2752:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2753:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2763:2: ( ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==55) ) {
                alt26=1;
            }
            else if ( ((LA26_0>=RULE_XML_TAG_END && LA26_0<=RULE_XML_TAG_SINGLEEND)||LA26_0==RULE_ID||LA26_0==44||LA26_0==48) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("2763:2: ( ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* )", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2763:3: ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2763:3: ( '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2763:5: '.' ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )*
                    {
                    match(input,55,FOLLOW_55_in_ruleMap6229); 

                            createLeafNode(grammarAccess.getMapAccess().getFullStopKeyword_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2767:1: ( (lv_mapName_3_0= ruleMapId ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2768:1: (lv_mapName_3_0= ruleMapId )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2768:1: (lv_mapName_3_0= ruleMapId )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2769:3: lv_mapName_3_0= ruleMapId
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapNameMapIdParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapId_in_ruleMap6250);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2791:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==RULE_ID||LA24_0==44||LA24_0==48) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2792:1: (lv_attributes_4_0= rulePossibleExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2792:1: (lv_attributes_4_0= rulePossibleExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2793:3: lv_attributes_4_0= rulePossibleExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_0_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap6271);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2816:6: ( (lv_attributes_5_0= rulePossibleExpression ) )*
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2816:6: ( (lv_attributes_5_0= rulePossibleExpression ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==RULE_ID||LA25_0==44||LA25_0==48) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2817:1: (lv_attributes_5_0= rulePossibleExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2817:1: (lv_attributes_5_0= rulePossibleExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2818:3: lv_attributes_5_0= rulePossibleExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap6300);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2840:4: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) ) )
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
                    new NoViableAltException("2840:4: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) ) )", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2840:5: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap6312); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2845:6: ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2845:6: ( RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2845:7: RULE_XML_TAG_END ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END )
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap6327); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2849:1: ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )*
                    loop27:
                    do {
                        int alt27=10;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0==RULE_XML_TAG_START) ) {
                            switch ( input.LA(2) ) {
                            case RULE_ID:
                                {
                                alt27=5;
                                }
                                break;
                            case 51:
                                {
                                alt27=7;
                                }
                                break;
                            case 48:
                                {
                                alt27=1;
                                }
                                break;
                            case 47:
                                {
                                alt27=3;
                                }
                                break;
                            case 40:
                                {
                                alt27=2;
                                }
                                break;
                            case RULE_MAPKEYWORD:
                                {
                                alt27=4;
                                }
                                break;
                            case 45:
                                {
                                alt27=9;
                                }
                                break;
                            case 38:
                                {
                                alt27=6;
                                }
                                break;
                            case 44:
                                {
                                alt27=8;
                                }
                                break;

                            }

                        }


                        switch (alt27) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2849:2: ( (lv_children_8_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2849:2: ( (lv_children_8_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2850:1: (lv_children_8_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2850:1: (lv_children_8_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2851:3: lv_children_8_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMap6348);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2874:6: ( (lv_children_9_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2874:6: ( (lv_children_9_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2875:1: (lv_children_9_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2875:1: (lv_children_9_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2876:3: lv_children_9_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMap6375);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2899:6: ( (lv_children_10_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2899:6: ( (lv_children_10_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2900:1: (lv_children_10_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2900:1: (lv_children_10_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2901:3: lv_children_10_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenParamParserRuleCall_3_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMap6402);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2924:6: ( (lv_children_11_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2924:6: ( (lv_children_11_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2925:1: (lv_children_11_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2925:1: (lv_children_11_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2926:3: lv_children_11_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_3_1_1_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMap6429);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2949:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2949:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2950:1: (lv_children_12_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2950:1: (lv_children_12_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2951:3: lv_children_12_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapMethodParserRuleCall_3_1_1_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMap6456);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2974:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2974:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2975:1: (lv_children_13_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2975:1: (lv_children_13_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2976:3: lv_children_13_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenDebugTagParserRuleCall_3_1_1_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMap6483);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2999:6: ( (lv_children_14_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2999:6: ( (lv_children_14_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3000:1: (lv_children_14_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3000:1: (lv_children_14_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3001:3: lv_children_14_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenFieldParserRuleCall_3_1_1_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMap6510);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3024:6: ( (lv_children_15_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3024:6: ( (lv_children_15_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3025:1: (lv_children_15_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3025:1: (lv_children_15_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3026:3: lv_children_15_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenCommentParserRuleCall_3_1_1_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMap6537);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3049:6: ( (lv_children_16_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3049:6: ( (lv_children_16_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3050:1: (lv_children_16_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3050:1: (lv_children_16_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3051:3: lv_children_16_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenBreakParserRuleCall_3_1_1_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMap6564);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3073:4: ( RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3073:5: RULE_XML_START_ENDTAG RULE_MAPKEYWORD ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )? RULE_XML_TAG_END
                    {
                    match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleMap6576); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getXML_START_ENDTAGTerminalRuleCall_3_1_2_0(), null); 
                        
                    match(input,RULE_MAPKEYWORD,FOLLOW_RULE_MAPKEYWORD_in_ruleMap6584); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getMAPKEYWORDTerminalRuleCall_3_1_2_1(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3081:1: ( '.' ( (lv_mapClosingName_20_0= ruleMapId ) ) )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==55) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3081:3: '.' ( (lv_mapClosingName_20_0= ruleMapId ) )
                            {
                            match(input,55,FOLLOW_55_in_ruleMap6594); 

                                    createLeafNode(grammarAccess.getMapAccess().getFullStopKeyword_3_1_2_2_0(), null); 
                                
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3085:1: ( (lv_mapClosingName_20_0= ruleMapId ) )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3086:1: (lv_mapClosingName_20_0= ruleMapId )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3086:1: (lv_mapClosingName_20_0= ruleMapId )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3087:3: lv_mapClosingName_20_0= ruleMapId
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapClosingNameMapIdParserRuleCall_3_1_2_2_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleMapId_in_ruleMap6615);
                            lv_mapClosingName_20_0=ruleMapId();
                            _fsp--;


                            	        if (current==null) {
                            	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                            	            associateNodeWithAstElement(currentNode.getParent(), current);
                            	        }
                            	        try {
                            	       		set(
                            	       			current, 
                            	       			"mapClosingName",
                            	        		lv_mapClosingName_20_0, 
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

                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap6626); 
                     
                        createLeafNode(grammarAccess.getMapAccess().getXML_TAG_ENDTerminalRuleCall_3_1_2_3(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3121:1: entryRuleMapId returns [String current=null] : iv_ruleMapId= ruleMapId EOF ;
    public final String entryRuleMapId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMapId = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3122:2: (iv_ruleMapId= ruleMapId EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3123:2: iv_ruleMapId= ruleMapId EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapIdRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapId_in_entryRuleMapId6665);
            iv_ruleMapId=ruleMapId();
            _fsp--;

             current =iv_ruleMapId.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapId6676); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3130:1: ruleMapId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleMapId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3135:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3136:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapId6715); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3151:1: entryRuleRequired returns [EObject current=null] : iv_ruleRequired= ruleRequired EOF ;
    public final EObject entryRuleRequired() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequired = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3152:2: (iv_ruleRequired= ruleRequired EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3153:2: iv_ruleRequired= ruleRequired EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRequiredRule(), currentNode); 
            pushFollow(FOLLOW_ruleRequired_in_entryRuleRequired6759);
            iv_ruleRequired=ruleRequired();
            _fsp--;

             current =iv_ruleRequired; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequired6769); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3160:1: ruleRequired returns [EObject current=null] : ( ruleREQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleREQUIRED_END_TAG ) ) ) ;
    public final EObject ruleRequired() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3165:6: ( ( ruleREQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleREQUIRED_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3166:1: ( ruleREQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleREQUIRED_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3166:1: ( ruleREQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleREQUIRED_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3167:5: ruleREQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleREQUIRED_END_TAG ) )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getRequiredAccess().getREQUIRED_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleREQUIRED_START_TAG_in_ruleRequired6810);
            ruleREQUIRED_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3174:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3175:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3185:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_ID||LA30_0==44||LA30_0==48) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3186:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3186:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3187:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getRequiredAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleRequired6839);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3209:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleREQUIRED_END_TAG ) )
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
                    new NoViableAltException("3209:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleREQUIRED_END_TAG ) )", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3209:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleRequired6850); 
                     
                        createLeafNode(grammarAccess.getRequiredAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3214:6: ( RULE_XML_TAG_END ruleREQUIRED_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3214:6: ( RULE_XML_TAG_END ruleREQUIRED_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3214:7: RULE_XML_TAG_END ruleREQUIRED_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleRequired6865); 
                     
                        createLeafNode(grammarAccess.getRequiredAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getRequiredAccess().getREQUIRED_END_TAGParserRuleCall_3_1_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleREQUIRED_END_TAG_in_ruleRequired6880);
                    ruleREQUIRED_END_TAG();
                    _fsp--;

                     
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
    // $ANTLR end ruleRequired


    // $ANTLR start entryRuleProperty
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3234:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3235:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3236:2: iv_ruleProperty= ruleProperty EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPropertyRule(), currentNode); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty6917);
            iv_ruleProperty=ruleProperty();
            _fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty6927); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3243:1: ruleProperty returns [EObject current=null] : ( rulePROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3248:6: ( ( rulePROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3249:1: ( rulePROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3249:1: ( rulePROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3250:5: rulePROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG ) )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getPROPERTY_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_rulePROPERTY_START_TAG_in_ruleProperty6968);
            rulePROPERTY_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3257:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3258:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3268:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==RULE_ID||LA32_0==44||LA32_0==48) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3269:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3269:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3270:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleProperty6997);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3292:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG ) )
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
                    new NoViableAltException("3292:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG ) )", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3292:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty7008); 
                     
                        createLeafNode(grammarAccess.getPropertyAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3297:6: ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3297:6: ( RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3297:7: RULE_XML_TAG_END ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* rulePROPERTY_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleProperty7023); 
                     
                        createLeafNode(grammarAccess.getPropertyAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3301:1: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )*
                    loop33:
                    do {
                        int alt33=3;
                        int LA33_0 = input.LA(1);

                        if ( (LA33_0==RULE_XML_TAG_START) ) {
                            int LA33_2 = input.LA(2);

                            if ( (LA33_2==46||LA33_2==52) ) {
                                alt33=1;
                            }
                            else if ( (LA33_2==RULE_MAPKEYWORD) ) {
                                alt33=2;
                            }


                        }


                        switch (alt33) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3301:2: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3301:2: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3302:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3302:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3303:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleProperty7044);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3326:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3326:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3327:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3327:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3328:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getChildrenMapParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleProperty7071);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getPROPERTY_END_TAGParserRuleCall_3_1_2(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePROPERTY_END_TAG_in_ruleProperty7089);
                    rulePROPERTY_END_TAG();
                    _fsp--;

                     
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
    // $ANTLR end ruleProperty


    // $ANTLR start entryRuleParam
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3366:1: entryRuleParam returns [EObject current=null] : iv_ruleParam= ruleParam EOF ;
    public final EObject entryRuleParam() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3367:2: (iv_ruleParam= ruleParam EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3368:2: iv_ruleParam= ruleParam EOF
            {
             currentNode = createCompositeNode(grammarAccess.getParamRule(), currentNode); 
            pushFollow(FOLLOW_ruleParam_in_entryRuleParam7126);
            iv_ruleParam=ruleParam();
            _fsp--;

             current =iv_ruleParam; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParam7136); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3375:1: ruleParam returns [EObject current=null] : ( rulePARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG ) ) ) ;
    public final EObject ruleParam() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3380:6: ( ( rulePARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3381:1: ( rulePARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3381:1: ( rulePARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3382:5: rulePARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG ) )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getParamAccess().getPARAM_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_rulePARAM_START_TAG_in_ruleParam7177);
            rulePARAM_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3389:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3390:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3400:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_ID||LA35_0==44||LA35_0==48) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3401:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3401:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3402:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleParam7206);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3424:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG ) )
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
                    new NoViableAltException("3424:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG ) )", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3424:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam7217); 
                     
                        createLeafNode(grammarAccess.getParamAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3429:6: ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3429:6: ( RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3429:7: RULE_XML_TAG_END ( (lv_children_5_0= ruleExpressionOrOption ) )* rulePARAM_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleParam7232); 
                     
                        createLeafNode(grammarAccess.getParamAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3433:1: ( (lv_children_5_0= ruleExpressionOrOption ) )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==RULE_XML_TAG_START) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3434:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3434:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3435:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getParamAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleParam7252);
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
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                     
                            currentNode=createCompositeNode(grammarAccess.getParamAccess().getPARAM_END_TAGParserRuleCall_3_1_2(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePARAM_END_TAG_in_ruleParam7269);
                    rulePARAM_END_TAG();
                    _fsp--;

                     
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
    // $ANTLR end ruleParam


    // $ANTLR start entryRuleMapMethod
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3473:1: entryRuleMapMethod returns [EObject current=null] : iv_ruleMapMethod= ruleMapMethod EOF ;
    public final EObject entryRuleMapMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3474:2: (iv_ruleMapMethod= ruleMapMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3475:2: iv_ruleMapMethod= ruleMapMethod EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapMethodRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapMethod_in_entryRuleMapMethod7306);
            iv_ruleMapMethod=ruleMapMethod();
            _fsp--;

             current =iv_ruleMapMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapMethod7316); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3482:1: ruleMapMethod returns [EObject current=null] : ( RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) ) ) ;
    public final EObject ruleMapMethod() throws RecognitionException {
        EObject current = null;

        Token lv_mapName_1_0=null;
        Token lv_methodName_3_0=null;
        Token lv_methodClosingName_9_0=null;
        Token lv_methodClosingMethod_11_0=null;
        EObject lv_attributes_4_0 = null;

        EObject lv_expression_7_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3487:6: ( ( RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:1: ( RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:1: ( RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3488:2: RULE_XML_TAG_START ( (lv_mapName_1_0= RULE_ID ) ) '.' ( (lv_methodName_3_0= RULE_ID ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) )
            {
            match(input,RULE_XML_TAG_START,FOLLOW_RULE_XML_TAG_START_in_ruleMapMethod7350); 
             
                createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_STARTTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3492:1: ( (lv_mapName_1_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3493:1: (lv_mapName_1_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3493:1: (lv_mapName_1_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3494:3: lv_mapName_1_0= RULE_ID
            {
            lv_mapName_1_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod7366); 

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

            match(input,55,FOLLOW_55_in_ruleMapMethod7381); 

                    createLeafNode(grammarAccess.getMapMethodAccess().getFullStopKeyword_2(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3520:1: ( (lv_methodName_3_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3521:1: (lv_methodName_3_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3521:1: (lv_methodName_3_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3522:3: lv_methodName_3_0= RULE_ID
            {
            lv_methodName_3_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod7398); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3544:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==RULE_ID||LA38_0==44||LA38_0==48) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3545:1: (lv_attributes_4_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3545:1: (lv_attributes_4_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3546:3: lv_attributes_4_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getAttributesPossibleExpressionParserRuleCall_4_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMapMethod7424);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3568:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==RULE_XML_TAG_SINGLEEND) ) {
                alt39=1;
            }
            else if ( (LA39_0==RULE_XML_TAG_END) ) {
                alt39=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3568:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END ) )", 39, 0, input);

                throw nvae;
            }
            switch (alt39) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3568:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod7435); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_SINGLEENDTerminalRuleCall_5_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3573:6: ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3573:6: ( RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3573:7: RULE_XML_TAG_END ( (lv_expression_7_0= ruleTopLevel ) ) RULE_XML_START_ENDTAG ( (lv_methodClosingName_9_0= RULE_ID ) ) '.' ( (lv_methodClosingMethod_11_0= RULE_ID ) ) RULE_XML_TAG_END
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod7450); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_TAG_ENDTerminalRuleCall_5_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3577:1: ( (lv_expression_7_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3578:1: (lv_expression_7_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3578:1: (lv_expression_7_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3579:3: lv_expression_7_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getMapMethodAccess().getExpressionTopLevelParserRuleCall_5_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleMapMethod7470);
                    lv_expression_7_0=ruleTopLevel();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapMethodRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"expression",
                    	        		lv_expression_7_0, 
                    	        		"TopLevel", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    match(input,RULE_XML_START_ENDTAG,FOLLOW_RULE_XML_START_ENDTAG_in_ruleMapMethod7479); 
                     
                        createLeafNode(grammarAccess.getMapMethodAccess().getXML_START_ENDTAGTerminalRuleCall_5_1_2(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3605:1: ( (lv_methodClosingName_9_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3606:1: (lv_methodClosingName_9_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3606:1: (lv_methodClosingName_9_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3607:3: lv_methodClosingName_9_0= RULE_ID
                    {
                    lv_methodClosingName_9_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod7495); 

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

                    match(input,55,FOLLOW_55_in_ruleMapMethod7510); 

                            createLeafNode(grammarAccess.getMapMethodAccess().getFullStopKeyword_5_1_4(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3633:1: ( (lv_methodClosingMethod_11_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3634:1: (lv_methodClosingMethod_11_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3634:1: (lv_methodClosingMethod_11_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3635:3: lv_methodClosingMethod_11_0= RULE_ID
                    {
                    lv_methodClosingMethod_11_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod7527); 

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

                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod7541); 
                     
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3669:1: entryRuleField returns [EObject current=null] : iv_ruleField= ruleField EOF ;
    public final EObject entryRuleField() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleField = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3670:2: (iv_ruleField= ruleField EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3671:2: iv_ruleField= ruleField EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFieldRule(), currentNode); 
            pushFollow(FOLLOW_ruleField_in_entryRuleField7578);
            iv_ruleField=ruleField();
            _fsp--;

             current =iv_ruleField; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleField7588); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3678:1: ruleField returns [EObject current=null] : ( ruleFIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_END ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG ) ) ) ;
    public final EObject ruleField() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_children_8_0 = null;

        EObject lv_children_9_0 = null;

        EObject lv_children_10_0 = null;

        EObject lv_children_11_0 = null;

        EObject lv_children_12_0 = null;

        EObject lv_children_13_0 = null;

        EObject lv_children_14_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3683:6: ( ( ruleFIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_END ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3684:1: ( ruleFIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_END ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3684:1: ( ruleFIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_END ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3685:5: ruleFIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* RULE_XML_TAG_END ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG ) )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getFieldAccess().getFIELD_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleFIELD_START_TAG_in_ruleField7629);
            ruleFIELD_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3692:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3693:5: 
            {
             
                    temp=factory.create(grammarAccess.getFieldAccess().getParamAction_1().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getFieldAccess().getParamAction_1(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3703:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==RULE_ID||LA40_0==44||LA40_0==48) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3704:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3704:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3705:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleField7658);
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
            	    break loop40;
                }
            } while (true);

            match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleField7668); 
             
                createLeafNode(grammarAccess.getFieldAccess().getXML_TAG_ENDTerminalRuleCall_3(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3731:1: ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG ) )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==RULE_XML_TAG_SINGLEEND) ) {
                alt42=1;
            }
            else if ( (LA42_0==RULE_XML_TAG_START||LA42_0==RULE_XML_START_ENDTAG) ) {
                alt42=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("3731:1: ( RULE_XML_TAG_SINGLEEND | ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG ) )", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3731:2: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleField7677); 
                     
                        createLeafNode(grammarAccess.getFieldAccess().getXML_TAG_SINGLEENDTerminalRuleCall_4_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3736:6: ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3736:6: ( ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3736:7: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )* ruleFIELD_END_TAG
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3736:7: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleParam ) ) | ( (lv_children_9_0= ruleMap ) ) | ( (lv_children_10_0= ruleMapMethod ) ) | ( (lv_children_11_0= ruleDebugTag ) ) | ( (lv_children_12_0= ruleField ) ) | ( (lv_children_13_0= ruleComment ) ) | ( (lv_children_14_0= ruleBreak ) ) )*
                    loop41:
                    do {
                        int alt41=11;
                        int LA41_0 = input.LA(1);

                        if ( (LA41_0==RULE_XML_TAG_START) ) {
                            switch ( input.LA(2) ) {
                            case 45:
                                {
                                alt41=10;
                                }
                                break;
                            case 38:
                                {
                                alt41=7;
                                }
                                break;
                            case 46:
                            case 52:
                                {
                                alt41=1;
                                }
                                break;
                            case RULE_ID:
                                {
                                alt41=6;
                                }
                                break;
                            case 44:
                                {
                                alt41=9;
                                }
                                break;
                            case 40:
                                {
                                alt41=3;
                                }
                                break;
                            case 51:
                                {
                                alt41=8;
                                }
                                break;
                            case 48:
                                {
                                alt41=2;
                                }
                                break;
                            case 47:
                                {
                                alt41=4;
                                }
                                break;
                            case RULE_MAPKEYWORD:
                                {
                                alt41=5;
                                }
                                break;

                            }

                        }


                        switch (alt41) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3736:8: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3736:8: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3737:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3737:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3738:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenExpressionOrOptionParserRuleCall_4_1_0_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleField7705);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3761:6: ( (lv_children_6_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3761:6: ( (lv_children_6_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3762:1: (lv_children_6_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3762:1: (lv_children_6_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3763:3: lv_children_6_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenMessageParserRuleCall_4_1_0_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleField7732);
                    	    lv_children_6_0=ruleMessage();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3786:6: ( (lv_children_7_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3786:6: ( (lv_children_7_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3787:1: (lv_children_7_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3787:1: (lv_children_7_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3788:3: lv_children_7_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenPropertyParserRuleCall_4_1_0_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleField7759);
                    	    lv_children_7_0=ruleProperty();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3811:6: ( (lv_children_8_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3811:6: ( (lv_children_8_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3812:1: (lv_children_8_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3812:1: (lv_children_8_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3813:3: lv_children_8_0= ruleParam
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenParamParserRuleCall_4_1_0_3_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleField7786);
                    	    lv_children_8_0=ruleParam();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3836:6: ( (lv_children_9_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3836:6: ( (lv_children_9_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3837:1: (lv_children_9_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3837:1: (lv_children_9_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3838:3: lv_children_9_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenMapParserRuleCall_4_1_0_4_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleField7813);
                    	    lv_children_9_0=ruleMap();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3861:6: ( (lv_children_10_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3861:6: ( (lv_children_10_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3862:1: (lv_children_10_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3862:1: (lv_children_10_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3863:3: lv_children_10_0= ruleMapMethod
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenMapMethodParserRuleCall_4_1_0_5_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleField7840);
                    	    lv_children_10_0=ruleMapMethod();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3886:6: ( (lv_children_11_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3886:6: ( (lv_children_11_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3887:1: (lv_children_11_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3887:1: (lv_children_11_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3888:3: lv_children_11_0= ruleDebugTag
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenDebugTagParserRuleCall_4_1_0_6_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleField7867);
                    	    lv_children_11_0=ruleDebugTag();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3911:6: ( (lv_children_12_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3911:6: ( (lv_children_12_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3912:1: (lv_children_12_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3912:1: (lv_children_12_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3913:3: lv_children_12_0= ruleField
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenFieldParserRuleCall_4_1_0_7_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleField7894);
                    	    lv_children_12_0=ruleField();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_12_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3936:6: ( (lv_children_13_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3936:6: ( (lv_children_13_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3937:1: (lv_children_13_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3937:1: (lv_children_13_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3938:3: lv_children_13_0= ruleComment
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenCommentParserRuleCall_4_1_0_8_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleField7921);
                    	    lv_children_13_0=ruleComment();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_13_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3961:6: ( (lv_children_14_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3961:6: ( (lv_children_14_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3962:1: (lv_children_14_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3962:1: (lv_children_14_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3963:3: lv_children_14_0= ruleBreak
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getFieldAccess().getChildrenBreakParserRuleCall_4_1_0_9_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleField7948);
                    	    lv_children_14_0=ruleBreak();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getFieldRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"children",
                    	    	        		lv_children_14_0, 
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
                    	    break loop41;
                        }
                    } while (true);

                     
                            currentNode=createCompositeNode(grammarAccess.getFieldAccess().getFIELD_END_TAGParserRuleCall_4_1_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFIELD_END_TAG_in_ruleField7966);
                    ruleFIELD_END_TAG();
                    _fsp--;

                     
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
    // $ANTLR end ruleField


    // $ANTLR start entryRuleDebugTag
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4001:1: entryRuleDebugTag returns [EObject current=null] : iv_ruleDebugTag= ruleDebugTag EOF ;
    public final EObject entryRuleDebugTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDebugTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4002:2: (iv_ruleDebugTag= ruleDebugTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4003:2: iv_ruleDebugTag= ruleDebugTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getDebugTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleDebugTag_in_entryRuleDebugTag8003);
            iv_ruleDebugTag=ruleDebugTag();
            _fsp--;

             current =iv_ruleDebugTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDebugTag8013); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4010:1: ruleDebugTag returns [EObject current=null] : ( ruleDEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG ) ) ) ;
    public final EObject ruleDebugTag() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expression_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4015:6: ( ( ruleDEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4016:1: ( ruleDEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4016:1: ( ruleDEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4017:5: ruleDEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG ) )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getDEBUG_START_TAGParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleDEBUG_START_TAG_in_ruleDebugTag8054);
            ruleDEBUG_START_TAG();
            _fsp--;

             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4024:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4025:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4035:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==RULE_ID||LA43_0==44||LA43_0==48) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4036:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4036:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4037:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleDebugTag8083);
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
            	    break loop43;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4059:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG ) )
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
                    new NoViableAltException("4059:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG ) )", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4059:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag8094); 
                     
                        createLeafNode(grammarAccess.getDebugTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4064:6: ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4064:6: ( RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4064:7: RULE_XML_TAG_END ( (lv_expression_5_0= ruleTopLevel ) ) ruleDEBUG_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag8109); 
                     
                        createLeafNode(grammarAccess.getDebugTagAccess().getXML_TAG_ENDTerminalRuleCall_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4068:1: ( (lv_expression_5_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4069:1: (lv_expression_5_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4069:1: (lv_expression_5_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4070:3: lv_expression_5_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getExpressionTopLevelParserRuleCall_3_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleDebugTag8129);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getDebugTagAccess().getDEBUG_END_TAGParserRuleCall_3_1_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleDEBUG_END_TAG_in_ruleDebugTag8145);
                    ruleDEBUG_END_TAG();
                    _fsp--;

                     
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
    // $ANTLR end ruleDebugTag


    // $ANTLR start entryRuleExpressionOrOption
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4108:1: entryRuleExpressionOrOption returns [EObject current=null] : iv_ruleExpressionOrOption= ruleExpressionOrOption EOF ;
    public final EObject entryRuleExpressionOrOption() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionOrOption = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4109:2: (iv_ruleExpressionOrOption= ruleExpressionOrOption EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4110:2: iv_ruleExpressionOrOption= ruleExpressionOrOption EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionOrOptionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionOrOption_in_entryRuleExpressionOrOption8182);
            iv_ruleExpressionOrOption=ruleExpressionOrOption();
            _fsp--;

             current =iv_ruleExpressionOrOption; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionOrOption8192); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4117:1: ruleExpressionOrOption returns [EObject current=null] : ( ( ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( ruleOPTION_START_TAG this_Option_3= ruleOption ) ) ;
    public final EObject ruleExpressionOrOption() throws RecognitionException {
        EObject current = null;

        EObject this_ExpressionTag_1 = null;

        EObject this_Option_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4122:6: ( ( ( ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( ruleOPTION_START_TAG this_Option_3= ruleOption ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4123:1: ( ( ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( ruleOPTION_START_TAG this_Option_3= ruleOption ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4123:1: ( ( ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( ruleOPTION_START_TAG this_Option_3= ruleOption ) )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==RULE_XML_TAG_START) ) {
                int LA45_1 = input.LA(2);

                if ( (LA45_1==52) ) {
                    alt45=1;
                }
                else if ( (LA45_1==46) ) {
                    alt45=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("4123:1: ( ( ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( ruleOPTION_START_TAG this_Option_3= ruleOption ) )", 45, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("4123:1: ( ( ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | ( ruleOPTION_START_TAG this_Option_3= ruleOption ) )", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4123:2: ( ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4123:2: ( ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4124:5: ruleEXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionOrOptionAccess().getEXPRESSION_START_TAGParserRuleCall_0_0(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleEXPRESSION_START_TAG_in_ruleExpressionOrOption8234);
                    ruleEXPRESSION_START_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionOrOptionAccess().getExpressionTagParserRuleCall_0_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleExpressionTag_in_ruleExpressionOrOption8255);
                    this_ExpressionTag_1=ruleExpressionTag();
                    _fsp--;

                     
                            current = this_ExpressionTag_1; 
                            currentNode = currentNode.getParent();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4141:6: ( ruleOPTION_START_TAG this_Option_3= ruleOption )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4141:6: ( ruleOPTION_START_TAG this_Option_3= ruleOption )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4142:5: ruleOPTION_START_TAG this_Option_3= ruleOption
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionOrOptionAccess().getOPTION_START_TAGParserRuleCall_1_0(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleOPTION_START_TAG_in_ruleExpressionOrOption8278);
                    ruleOPTION_START_TAG();
                    _fsp--;

                     
                            currentNode = currentNode.getParent();
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionOrOptionAccess().getOptionParserRuleCall_1_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleOption_in_ruleExpressionOrOption8299);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4168:1: entryRuleExpressionTag returns [EObject current=null] : iv_ruleExpressionTag= ruleExpressionTag EOF ;
    public final EObject entryRuleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4169:2: (iv_ruleExpressionTag= ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4170:2: iv_ruleExpressionTag= ruleExpressionTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag8337);
            iv_ruleExpressionTag=ruleExpressionTag();
            _fsp--;

             current =iv_ruleExpressionTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag8347); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4177:1: ruleExpressionTag returns [EObject current=null] : ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG ) ) ) ;
    public final EObject ruleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_1_0 = null;

        EObject lv_expression_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4182:6: ( ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4183:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4183:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4183:2: () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4183:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4184:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4194:2: ( (lv_attributes_1_0= rulePossibleExpression ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==RULE_ID||LA46_0==44||LA46_0==48) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4195:1: (lv_attributes_1_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4195:1: (lv_attributes_1_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4196:3: lv_attributes_1_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getAttributesPossibleExpressionParserRuleCall_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleExpressionTag8402);
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
            	    break loop46;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4218:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG ) )
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
                    new NoViableAltException("4218:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG ) )", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4218:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag8413); 
                     
                        createLeafNode(grammarAccess.getExpressionTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4223:6: ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4223:6: ( RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4223:7: RULE_XML_TAG_END ( (lv_expression_4_0= ruleTopLevel ) ) ruleEXPRESSION_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag8428); 
                     
                        createLeafNode(grammarAccess.getExpressionTagAccess().getXML_TAG_ENDTerminalRuleCall_2_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4227:1: ( (lv_expression_4_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4228:1: (lv_expression_4_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4228:1: (lv_expression_4_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4229:3: lv_expression_4_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getExpressionTopLevelParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleExpressionTag8448);
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

                     
                            currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getEXPRESSION_END_TAGParserRuleCall_2_1_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleEXPRESSION_END_TAG_in_ruleExpressionTag8464);
                    ruleEXPRESSION_END_TAG();
                    _fsp--;

                     
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
    // $ANTLR end ruleExpressionTag


    // $ANTLR start entryRuleOption
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4267:1: entryRuleOption returns [EObject current=null] : iv_ruleOption= ruleOption EOF ;
    public final EObject entryRuleOption() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOption = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4268:2: (iv_ruleOption= ruleOption EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4269:2: iv_ruleOption= ruleOption EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOptionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOption_in_entryRuleOption8501);
            iv_ruleOption=ruleOption();
            _fsp--;

             current =iv_ruleOption; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOption8511); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4276:1: ruleOption returns [EObject current=null] : ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleOPTION_END_TAG ) ) ) ;
    public final EObject ruleOption() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4281:6: ( ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleOPTION_END_TAG ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4282:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleOPTION_END_TAG ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4282:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleOPTION_END_TAG ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4282:2: () ( (lv_attributes_1_0= rulePossibleExpression ) )* ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleOPTION_END_TAG ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4282:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4283:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4293:2: ( (lv_attributes_1_0= rulePossibleExpression ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==RULE_ID||LA48_0==44||LA48_0==48) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4294:1: (lv_attributes_1_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4294:1: (lv_attributes_1_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4295:3: lv_attributes_1_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOptionAccess().getAttributesPossibleExpressionParserRuleCall_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleOption8566);
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
            	    break loop48;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4317:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleOPTION_END_TAG ) )
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
                    new NoViableAltException("4317:3: ( RULE_XML_TAG_SINGLEEND | ( RULE_XML_TAG_END ruleOPTION_END_TAG ) )", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4317:4: RULE_XML_TAG_SINGLEEND
                    {
                    match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleOption8577); 
                     
                        createLeafNode(grammarAccess.getOptionAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4322:6: ( RULE_XML_TAG_END ruleOPTION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4322:6: ( RULE_XML_TAG_END ruleOPTION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4322:7: RULE_XML_TAG_END ruleOPTION_END_TAG
                    {
                    match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleOption8592); 
                     
                        createLeafNode(grammarAccess.getOptionAccess().getXML_TAG_ENDTerminalRuleCall_2_1_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getOptionAccess().getOPTION_END_TAGParserRuleCall_2_1_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleOPTION_END_TAG_in_ruleOption8607);
                    ruleOPTION_END_TAG();
                    _fsp--;

                     
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
    // $ANTLR end ruleOption


    // $ANTLR start entryRuleTopLevel
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4342:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4343:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4344:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTopLevelRule(), currentNode); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel8644);
            iv_ruleTopLevel=ruleTopLevel();
            _fsp--;

             current =iv_ruleTopLevel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel8654); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4351:1: ruleTopLevel returns [EObject current=null] : ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4356:6: ( ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4357:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4357:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4358:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4358:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4359:3: lv_toplevelExpression_0_0= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel8699);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4389:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4390:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4391:2: iv_rulePathElement= rulePathElement EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathElementRule(), currentNode); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement8735);
            iv_rulePathElement=rulePathElement();
            _fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement8746); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4398:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_PARENT_2=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4403:6: ( (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4404:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4404:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            int alt50=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt50=1;
                }
                break;
            case 55:
                {
                alt50=2;
                }
                break;
            case RULE_PARENT:
                {
                alt50=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("4404:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4404:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement8786); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4413:2: kw= '.'
                    {
                    kw=(Token)input.LT(1);
                    match(input,55,FOLLOW_55_in_rulePathElement8810); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:10: this_PARENT_2= RULE_PARENT
                    {
                    this_PARENT_2=(Token)input.LT(1);
                    match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_rulePathElement8831); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4434:1: entryRuleTmlExpression returns [EObject current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final EObject entryRuleTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4435:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4436:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression8876);
            iv_ruleTmlExpression=ruleTmlExpression();
            _fsp--;

             current =iv_ruleTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression8886); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4443:1: ruleTmlExpression returns [EObject current=null] : ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_1_0=null;
        Token lv_param_2_0=null;
        AntlrDatatypeRuleToken lv_elements_3_0 = null;

        AntlrDatatypeRuleToken lv_elements_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4448:6: ( ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4449:1: ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4449:1: ( RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4449:2: RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE
            {
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression8920); 
             
                createLeafNode(grammarAccess.getTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4453:1: ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==RULE_TML_SEPARATOR) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4454:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4454:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4455:3: lv_absolute_1_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_1_0=(Token)input.LT(1);
                    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression8936); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4477:3: ( (lv_param_2_0= RULE_AT ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_AT) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4478:1: (lv_param_2_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4478:1: (lv_param_2_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4479:3: lv_param_2_0= RULE_AT
                    {
                    lv_param_2_0=(Token)input.LT(1);
                    match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleTmlExpression8959); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4501:3: ( (lv_elements_3_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4502:1: (lv_elements_3_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4502:1: (lv_elements_3_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4503:3: lv_elements_3_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression8986);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4525:2: ( RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==RULE_TML_SEPARATOR) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4525:3: RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression8996); 
            	     
            	        createLeafNode(grammarAccess.getTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_4_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4529:1: ( (lv_elements_5_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4530:1: (lv_elements_5_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4530:1: (lv_elements_5_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4531:3: lv_elements_5_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression9016);
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
            	    break loop53;
                }
            } while (true);

            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression9027); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4565:1: entryRuleExistsTmlExpression returns [EObject current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final EObject entryRuleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4566:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4567:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExistsTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression9062);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();
            _fsp--;

             current =iv_ruleExistsTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression9072); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4574:1: ruleExistsTmlExpression returns [EObject current=null] : ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_2_0=null;
        Token lv_param_3_0=null;
        AntlrDatatypeRuleToken lv_elements_4_0 = null;

        AntlrDatatypeRuleToken lv_elements_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4579:6: ( ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4580:1: ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4580:1: ( RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4580:2: RULE_TML_EXISTS RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* RULE_SQBRACKET_CLOSE
            {
            match(input,RULE_TML_EXISTS,FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression9106); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getTML_EXISTSTerminalRuleCall_0(), null); 
                
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression9114); 
             
                createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4588:1: ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_TML_SEPARATOR) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4589:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4589:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4590:3: lv_absolute_2_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_2_0=(Token)input.LT(1);
                    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression9130); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4612:3: ( (lv_param_3_0= RULE_AT ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_AT) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4613:1: (lv_param_3_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4613:1: (lv_param_3_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4614:3: lv_param_3_0= RULE_AT
                    {
                    lv_param_3_0=(Token)input.LT(1);
                    match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleExistsTmlExpression9153); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4636:3: ( (lv_elements_4_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4637:1: (lv_elements_4_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4637:1: (lv_elements_4_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4638:3: lv_elements_4_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression9180);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4660:2: ( RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==RULE_TML_SEPARATOR) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4660:3: RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) )
            	    {
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression9190); 
            	     
            	        createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_5_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4664:1: ( (lv_elements_6_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4665:1: (lv_elements_6_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4665:1: (lv_elements_6_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4666:3: lv_elements_6_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_5_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression9210);
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
            	    break loop56;
                }
            } while (true);

            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression9221); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4700:1: entryRuleMapReferenceParams returns [EObject current=null] : iv_ruleMapReferenceParams= ruleMapReferenceParams EOF ;
    public final EObject entryRuleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapReferenceParams = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4701:2: (iv_ruleMapReferenceParams= ruleMapReferenceParams EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4702:2: iv_ruleMapReferenceParams= ruleMapReferenceParams EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapReferenceParamsRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams9256);
            iv_ruleMapReferenceParams=ruleMapReferenceParams();
            _fsp--;

             current =iv_ruleMapReferenceParams; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapReferenceParams9266); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4709:1: ruleMapReferenceParams returns [EObject current=null] : ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' ) ;
    public final EObject ruleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        EObject lv_getterParams_1_0 = null;

        EObject lv_getterParams_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4714:6: ( ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4715:1: ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4715:1: ( '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4715:3: '(' ( (lv_getterParams_1_0= ruleLiteral ) ) ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* ')'
            {
            match(input,56,FOLLOW_56_in_ruleMapReferenceParams9301); 

                    createLeafNode(grammarAccess.getMapReferenceParamsAccess().getLeftParenthesisKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4719:1: ( (lv_getterParams_1_0= ruleLiteral ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4720:1: (lv_getterParams_1_0= ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4720:1: (lv_getterParams_1_0= ruleLiteral )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4721:3: lv_getterParams_1_0= ruleLiteral
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams9322);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4743:2: ( ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==57) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4743:4: ',' ( (lv_getterParams_3_0= ruleLiteral ) )
            	    {
            	    match(input,57,FOLLOW_57_in_ruleMapReferenceParams9333); 

            	            createLeafNode(grammarAccess.getMapReferenceParamsAccess().getCommaKeyword_2_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4747:1: ( (lv_getterParams_3_0= ruleLiteral ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4748:1: (lv_getterParams_3_0= ruleLiteral )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4748:1: (lv_getterParams_3_0= ruleLiteral )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4749:3: lv_getterParams_3_0= ruleLiteral
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams9354);
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
            	    break loop57;
                }
            } while (true);

            match(input,58,FOLLOW_58_in_ruleMapReferenceParams9366); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4783:1: entryRuleMapGetReference returns [EObject current=null] : iv_ruleMapGetReference= ruleMapGetReference EOF ;
    public final EObject entryRuleMapGetReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapGetReference = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4784:2: (iv_ruleMapGetReference= ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4785:2: iv_ruleMapGetReference= ruleMapGetReference EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapGetReferenceRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference9402);
            iv_ruleMapGetReference=ruleMapGetReference();
            _fsp--;

             current =iv_ruleMapGetReference; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference9412); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4792:1: ruleMapGetReference returns [EObject current=null] : ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) ;
    public final EObject ruleMapGetReference() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        Token lv_elements_1_0=null;
        Token lv_elements_3_0=null;
        EObject lv_referenceParams_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4797:6: ( ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4798:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4798:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4798:2: ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4798:2: ( (lv_operations_0_0= RULE_DOLLAR ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4799:1: (lv_operations_0_0= RULE_DOLLAR )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4799:1: (lv_operations_0_0= RULE_DOLLAR )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4800:3: lv_operations_0_0= RULE_DOLLAR
            {
            lv_operations_0_0=(Token)input.LT(1);
            match(input,RULE_DOLLAR,FOLLOW_RULE_DOLLAR_in_ruleMapGetReference9454); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4822:2: ( ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==RULE_PARENT) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4822:3: ( (lv_elements_1_0= RULE_PARENT ) ) RULE_TML_SEPARATOR
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4822:3: ( (lv_elements_1_0= RULE_PARENT ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4823:1: (lv_elements_1_0= RULE_PARENT )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4823:1: (lv_elements_1_0= RULE_PARENT )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4824:3: lv_elements_1_0= RULE_PARENT
            	    {
            	    lv_elements_1_0=(Token)input.LT(1);
            	    match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_ruleMapGetReference9477); 

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

            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference9491); 
            	     
            	        createLeafNode(grammarAccess.getMapGetReferenceAccess().getTML_SEPARATORTerminalRuleCall_1_1(), null); 
            	        

            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4850:3: ( (lv_elements_3_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4851:1: (lv_elements_3_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4851:1: (lv_elements_3_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4852:3: lv_elements_3_0= RULE_ID
            {
            lv_elements_3_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapGetReference9509); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4874:2: ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==56) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4875:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4875:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4876:3: lv_referenceParams_4_0= ruleMapReferenceParams
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getMapGetReferenceAccess().getReferenceParamsMapReferenceParamsParserRuleCall_3_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference9535);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4906:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4907:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4908:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression9572);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression9582); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4915:1: ruleOrExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4920:6: ( ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4921:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4921:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4921:2: ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4921:2: ( (lv_parameters_0_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4922:1: (lv_parameters_0_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4922:1: (lv_parameters_0_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4923:3: lv_parameters_0_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression9628);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4945:2: ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==59) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4945:3: ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4945:3: ( (lv_operations_1_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4946:1: (lv_operations_1_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4946:1: (lv_operations_1_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4947:3: lv_operations_1_0= 'OR'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,59,FOLLOW_59_in_ruleOrExpression9647); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4966:2: ( (lv_parameters_2_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4967:1: (lv_parameters_2_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4967:1: (lv_parameters_2_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4968:3: lv_parameters_2_0= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression9681);
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
            	    break loop60;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4998:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4999:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5000:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression9719);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression9729); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5007:1: ruleAndExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5012:6: ( ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5013:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5013:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5013:2: ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5013:2: ( (lv_parameters_0_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5014:1: (lv_parameters_0_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5014:1: (lv_parameters_0_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5015:3: lv_parameters_0_0= ruleEqualityExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression9775);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5037:2: ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==60) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5037:3: ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5037:3: ( (lv_operations_1_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5038:1: (lv_operations_1_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5038:1: (lv_operations_1_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5039:3: lv_operations_1_0= 'AND'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,60,FOLLOW_60_in_ruleAndExpression9794); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5058:2: ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5059:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5059:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5060:3: lv_parameters_2_0= ruleEqualityExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression9828);
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
            	    break loop61;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5090:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5091:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5092:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEqualityExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression9866);
            iv_ruleEqualityExpression=ruleEqualityExpression();
            _fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression9876); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5099:1: ruleEqualityExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5104:6: ( ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5105:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5105:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5105:2: ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5105:2: ( (lv_parameters_0_0= ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5106:1: (lv_parameters_0_0= ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5106:1: (lv_parameters_0_0= ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5107:3: lv_parameters_0_0= ruleRelationalExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression9922);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5129:2: ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            int alt62=3;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==61) ) {
                alt62=1;
            }
            else if ( (LA62_0==62) ) {
                alt62=2;
            }
            switch (alt62) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5129:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5129:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5129:4: ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5129:4: ( (lv_operations_1_0= '==' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5130:1: (lv_operations_1_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5130:1: (lv_operations_1_0= '==' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5131:3: lv_operations_1_0= '=='
                    {
                    lv_operations_1_0=(Token)input.LT(1);
                    match(input,61,FOLLOW_61_in_ruleEqualityExpression9942); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5150:2: ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5151:1: (lv_parameters_2_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5151:1: (lv_parameters_2_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5152:3: lv_parameters_2_0= ruleRelationalExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression9976);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5175:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5175:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5175:7: ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5175:7: ( (lv_operations_3_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5176:1: (lv_operations_3_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5176:1: (lv_operations_3_0= '!=' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5177:3: lv_operations_3_0= '!='
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,62,FOLLOW_62_in_ruleEqualityExpression10002); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5196:2: ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5197:1: (lv_parameters_4_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5197:1: (lv_parameters_4_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5198:3: lv_parameters_4_0= ruleRelationalExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression10036);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5228:1: entryRuleRelationalExpression returns [EObject current=null] : iv_ruleRelationalExpression= ruleRelationalExpression EOF ;
    public final EObject entryRuleRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationalExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5229:2: (iv_ruleRelationalExpression= ruleRelationalExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5230:2: iv_ruleRelationalExpression= ruleRelationalExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getRelationalExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression10075);
            iv_ruleRelationalExpression=ruleRelationalExpression();
            _fsp--;

             current =iv_ruleRelationalExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRelationalExpression10085); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5237:1: ruleRelationalExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5242:6: ( ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5243:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5243:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5243:2: () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5243:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5244:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5254:2: ( (lv_parameters_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5255:1: (lv_parameters_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5255:1: (lv_parameters_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5256:3: lv_parameters_1_0= ruleAdditiveExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10140);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5278:2: ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            int alt63=5;
            switch ( input.LA(1) ) {
                case RULE_XML_LT:
                    {
                    alt63=1;
                    }
                    break;
                case RULE_XML_GT:
                    {
                    alt63=2;
                    }
                    break;
                case RULE_XML_LTEQ:
                    {
                    alt63=3;
                    }
                    break;
                case RULE_XML_GTEQ:
                    {
                    alt63=4;
                    }
                    break;
            }

            switch (alt63) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5278:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5278:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5278:4: ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5278:4: ( (lv_operations_2_0= RULE_XML_LT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5279:1: (lv_operations_2_0= RULE_XML_LT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5279:1: (lv_operations_2_0= RULE_XML_LT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5280:3: lv_operations_2_0= RULE_XML_LT
                    {
                    lv_operations_2_0=(Token)input.LT(1);
                    match(input,RULE_XML_LT,FOLLOW_RULE_XML_LT_in_ruleRelationalExpression10159); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5302:2: ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5303:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5303:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5304:3: lv_parameters_3_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10185);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5327:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5327:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5327:7: ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5327:7: ( (lv_operations_4_0= RULE_XML_GT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5328:1: (lv_operations_4_0= RULE_XML_GT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5328:1: (lv_operations_4_0= RULE_XML_GT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5329:3: lv_operations_4_0= RULE_XML_GT
                    {
                    lv_operations_4_0=(Token)input.LT(1);
                    match(input,RULE_XML_GT,FOLLOW_RULE_XML_GT_in_ruleRelationalExpression10210); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5351:2: ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5352:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5352:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5353:3: lv_parameters_5_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10236);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5376:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5376:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5376:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5376:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5377:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5377:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5378:3: lv_operations_6_0= RULE_XML_LTEQ
                    {
                    lv_operations_6_0=(Token)input.LT(1);
                    match(input,RULE_XML_LTEQ,FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression10261); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5400:2: ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5401:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5401:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5402:3: lv_parameters_7_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_2_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10287);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5425:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5425:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5425:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5425:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5426:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5426:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5427:3: lv_operations_8_0= RULE_XML_GTEQ
                    {
                    lv_operations_8_0=(Token)input.LT(1);
                    match(input,RULE_XML_GTEQ,FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression10312); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5449:2: ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5450:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5450:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5451:3: lv_parameters_9_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_3_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10338);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5481:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5482:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5483:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAdditiveExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression10377);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();
            _fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression10387); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5490:1: ruleAdditiveExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5495:6: ( ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5496:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5496:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5496:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5496:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5497:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5497:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5498:3: lv_parameters_0_0= ruleMultiplicativeExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression10433);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5520:2: ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            loop64:
            do {
                int alt64=3;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==63) ) {
                    alt64=1;
                }
                else if ( (LA64_0==64) ) {
                    alt64=2;
                }


                switch (alt64) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5520:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5520:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5520:5: '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,63,FOLLOW_63_in_ruleAdditiveExpression10445); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5524:1: ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5525:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5525:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5526:3: lv_parameters_2_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression10466);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5549:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5549:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5549:8: '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,64,FOLLOW_64_in_ruleAdditiveExpression10484); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5553:1: ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5554:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5554:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5555:3: lv_parameters_4_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression10505);
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
    // $ANTLR end ruleAdditiveExpression


    // $ANTLR start entryRuleMultiplicativeExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5585:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5586:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5587:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression10544);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();
            _fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression10554); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5594:1: ruleMultiplicativeExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5599:6: ( ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5600:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5600:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5600:2: ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5600:2: ( (lv_parameters_0_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5601:1: (lv_parameters_0_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5601:1: (lv_parameters_0_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5602:3: lv_parameters_0_0= ruleUnaryExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression10600);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5624:2: ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            loop65:
            do {
                int alt65=3;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==65) ) {
                    alt65=1;
                }
                else if ( (LA65_0==RULE_TML_SEPARATOR) ) {
                    alt65=2;
                }


                switch (alt65) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5624:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5624:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5624:4: ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5624:4: ( (lv_operations_1_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5625:1: (lv_operations_1_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5625:1: (lv_operations_1_0= '*' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5626:3: lv_operations_1_0= '*'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,65,FOLLOW_65_in_ruleMultiplicativeExpression10620); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5645:2: ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5646:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5646:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5647:3: lv_parameters_2_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression10654);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5670:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5670:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5670:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5670:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5671:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5671:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5672:3: lv_operations_3_0= RULE_TML_SEPARATOR
            	    {
            	    lv_operations_3_0=(Token)input.LT(1);
            	    match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression10679); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5694:2: ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5695:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5695:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5696:3: lv_parameters_4_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression10705);
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
            	    break loop65;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5726:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5727:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5728:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getUnaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression10744);
            iv_ruleUnaryExpression=ruleUnaryExpression();
            _fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression10754); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5735:1: ruleUnaryExpression returns [EObject current=null] : ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        EObject lv_parameters_1_0 = null;

        EObject this_PrimaryExpression_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5740:6: ( ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5741:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5741:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==66) ) {
                alt66=1;
            }
            else if ( (LA66_0==RULE_ID||LA66_0==RULE_SQBRACKET_OPEN||(LA66_0>=RULE_TML_EXISTS && LA66_0<=RULE_DOLLAR)||(LA66_0>=RULE_INT && LA66_0<=RULE_FALSE)||LA66_0==56||LA66_0==67) ) {
                alt66=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5741:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )", 66, 0, input);

                throw nvae;
            }
            switch (alt66) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5741:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5741:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5741:3: ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5741:3: ( (lv_operations_0_0= '!' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5742:1: (lv_operations_0_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5742:1: (lv_operations_0_0= '!' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5743:3: lv_operations_0_0= '!'
                    {
                    lv_operations_0_0=(Token)input.LT(1);
                    match(input,66,FOLLOW_66_in_ruleUnaryExpression10798); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5762:2: ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5763:1: (lv_parameters_1_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5763:1: (lv_parameters_1_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5764:3: lv_parameters_1_0= rulePrimaryExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression10832);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5788:5: this_PrimaryExpression_2= rulePrimaryExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression10861);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5804:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5805:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5806:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPrimaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression10896);
            iv_rulePrimaryExpression=rulePrimaryExpression();
            _fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression10906); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5813:1: rulePrimaryExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5818:6: ( ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5819:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5819:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==RULE_ID||LA67_0==RULE_SQBRACKET_OPEN||(LA67_0>=RULE_TML_EXISTS && LA67_0<=RULE_DOLLAR)||(LA67_0>=RULE_INT && LA67_0<=RULE_FALSE)||LA67_0==67) ) {
                alt67=1;
            }
            else if ( (LA67_0==56) ) {
                alt67=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("5819:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5819:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5819:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5820:1: (lv_parameters_0_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5820:1: (lv_parameters_0_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5821:3: lv_parameters_0_0= ruleLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression10952);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5844:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5844:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5844:8: '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')'
                    {
                    match(input,56,FOLLOW_56_in_rulePrimaryExpression10969); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5848:1: ( (lv_parameters_2_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5849:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5849:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5850:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression10990);
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

                    match(input,58,FOLLOW_58_in_rulePrimaryExpression11000); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5884:1: entryRuleFunctionName returns [String current=null] : iv_ruleFunctionName= ruleFunctionName EOF ;
    public final String entryRuleFunctionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFunctionName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5885:2: (iv_ruleFunctionName= ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5886:2: iv_ruleFunctionName= ruleFunctionName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName11038);
            iv_ruleFunctionName=ruleFunctionName();
            _fsp--;

             current =iv_ruleFunctionName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName11049); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5893:1: ruleFunctionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFunctionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5898:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5899:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName11088); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5914:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5915:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5916:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionCallRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall11132);
            iv_ruleFunctionCall=ruleFunctionCall();
            _fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall11142); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5923:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5928:6: ( ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5929:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5929:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5929:2: ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')'
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5929:2: ( (lv_name_0_0= ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5930:1: (lv_name_0_0= ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5930:1: (lv_name_0_0= ruleFunctionName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5931:3: lv_name_0_0= ruleFunctionName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleFunctionName_in_ruleFunctionCall11188);
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

            match(input,56,FOLLOW_56_in_ruleFunctionCall11198); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5957:1: ( (lv_parameters_2_0= ruleOrExpression ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_ID||LA68_0==RULE_SQBRACKET_OPEN||(LA68_0>=RULE_TML_EXISTS && LA68_0<=RULE_DOLLAR)||(LA68_0>=RULE_INT && LA68_0<=RULE_FALSE)||LA68_0==56||(LA68_0>=66 && LA68_0<=67)) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5958:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5958:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5959:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall11219);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5981:3: ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==57) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5981:5: ',' ( (lv_parameters_4_0= ruleOrExpression ) )
            	    {
            	    match(input,57,FOLLOW_57_in_ruleFunctionCall11231); 

            	            createLeafNode(grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5985:1: ( (lv_parameters_4_0= ruleOrExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5986:1: (lv_parameters_4_0= ruleOrExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5986:1: (lv_parameters_4_0= ruleOrExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:5987:3: lv_parameters_4_0= ruleOrExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall11252);
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
            	    break loop69;
                }
            } while (true);

            match(input,58,FOLLOW_58_in_ruleFunctionCall11264); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6021:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6022:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6023:2: iv_ruleLiteral= ruleLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral11300);
            iv_ruleLiteral=ruleLiteral();
            _fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral11310); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6030:1: ruleLiteral returns [EObject current=null] : ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) ) ;
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


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6035:6: ( ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6036:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6036:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )
            int alt72=12;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt72=1;
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt72=2;
                }
                break;
            case RULE_FORALL:
                {
                alt72=3;
                }
                break;
            case RULE_ID:
                {
                alt72=4;
                }
                break;
            case 67:
                {
                alt72=5;
                }
                break;
            case RULE_NULL:
                {
                alt72=6;
                }
                break;
            case RULE_TODAY:
                {
                alt72=7;
                }
                break;
            case RULE_TRUE:
                {
                alt72=8;
                }
                break;
            case RULE_FALSE:
                {
                alt72=9;
                }
                break;
            case RULE_SQBRACKET_OPEN:
                {
                alt72=10;
                }
                break;
            case RULE_TML_EXISTS:
                {
                alt72=11;
                }
                break;
            case RULE_DOLLAR:
                {
                alt72=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("6036:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6036:2: ( () RULE_INT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6036:2: ( () RULE_INT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6036:3: () RULE_INT
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6036:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6037:5: 
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

                    match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleLiteral11354); 
                     
                        createLeafNode(grammarAccess.getLiteralAccess().getINTTerminalRuleCall_0_1(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6052:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6052:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6053:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6053:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6054:3: lv_valueString_2_0= RULE_LITERALSTRING
                    {
                    lv_valueString_2_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral11377); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6077:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6077:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6077:7: ( (lv_operations_3_0= RULE_FORALL ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6077:7: ( (lv_operations_3_0= RULE_FORALL ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6078:1: (lv_operations_3_0= RULE_FORALL )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6078:1: (lv_operations_3_0= RULE_FORALL )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6079:3: lv_operations_3_0= RULE_FORALL
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,RULE_FORALL,FOLLOW_RULE_FORALL_in_ruleLiteral11406); 

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

                    match(input,56,FOLLOW_56_in_ruleLiteral11421); 

                            createLeafNode(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6105:1: ( (lv_valueString_5_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6106:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6106:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6107:3: lv_valueString_5_0= RULE_LITERALSTRING
                    {
                    lv_valueString_5_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral11438); 

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

                    match(input,57,FOLLOW_57_in_ruleLiteral11453); 

                            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_2_3(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6133:1: ( (lv_parameters_7_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6134:1: (lv_parameters_7_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6134:1: (lv_parameters_7_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6135:3: lv_parameters_7_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral11474);
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

                    match(input,58,FOLLOW_58_in_ruleLiteral11484); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5(), null); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6162:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6162:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6163:1: (lv_parameters_9_0= ruleFunctionCall )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6163:1: (lv_parameters_9_0= ruleFunctionCall )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6164:3: lv_parameters_9_0= ruleFunctionCall
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersFunctionCallParserRuleCall_3_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral11512);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6187:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6187:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6187:7: ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6187:7: ( (lv_expressionType_10_0= '{' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6188:1: (lv_expressionType_10_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6188:1: (lv_expressionType_10_0= '{' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6189:3: lv_expressionType_10_0= '{'
                    {
                    lv_expressionType_10_0=(Token)input.LT(1);
                    match(input,67,FOLLOW_67_in_ruleLiteral11537); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6208:2: ( (lv_parameters_11_0= ruleOrExpression ) )?
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==RULE_ID||LA70_0==RULE_SQBRACKET_OPEN||(LA70_0>=RULE_TML_EXISTS && LA70_0<=RULE_DOLLAR)||(LA70_0>=RULE_INT && LA70_0<=RULE_FALSE)||LA70_0==56||(LA70_0>=66 && LA70_0<=67)) ) {
                        alt70=1;
                    }
                    switch (alt70) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6209:1: (lv_parameters_11_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6209:1: (lv_parameters_11_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6210:3: lv_parameters_11_0= ruleOrExpression
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral11571);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6232:3: ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( (LA71_0==57) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6232:5: ',' ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    {
                    	    match(input,57,FOLLOW_57_in_ruleLiteral11583); 

                    	            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0(), null); 
                    	        
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6236:1: ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6237:1: (lv_parameters_13_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6237:1: (lv_parameters_13_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6238:3: lv_parameters_13_0= ruleOrExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral11604);
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
                    	    break loop71;
                        }
                    } while (true);

                    match(input,68,FOLLOW_68_in_ruleLiteral11616); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3(), null); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6265:6: ( (lv_elements_15_0= RULE_NULL ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6265:6: ( (lv_elements_15_0= RULE_NULL ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6266:1: (lv_elements_15_0= RULE_NULL )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6266:1: (lv_elements_15_0= RULE_NULL )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6267:3: lv_elements_15_0= RULE_NULL
                    {
                    lv_elements_15_0=(Token)input.LT(1);
                    match(input,RULE_NULL,FOLLOW_RULE_NULL_in_ruleLiteral11640); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6290:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6290:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6291:1: (lv_elements_16_0= RULE_TODAY )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6291:1: (lv_elements_16_0= RULE_TODAY )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6292:3: lv_elements_16_0= RULE_TODAY
                    {
                    lv_elements_16_0=(Token)input.LT(1);
                    match(input,RULE_TODAY,FOLLOW_RULE_TODAY_in_ruleLiteral11668); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6315:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6315:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6316:1: (lv_elements_17_0= RULE_TRUE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6316:1: (lv_elements_17_0= RULE_TRUE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6317:3: lv_elements_17_0= RULE_TRUE
                    {
                    lv_elements_17_0=(Token)input.LT(1);
                    match(input,RULE_TRUE,FOLLOW_RULE_TRUE_in_ruleLiteral11696); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6340:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6340:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6341:1: (lv_elements_18_0= RULE_FALSE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6341:1: (lv_elements_18_0= RULE_FALSE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6342:3: lv_elements_18_0= RULE_FALSE
                    {
                    lv_elements_18_0=(Token)input.LT(1);
                    match(input,RULE_FALSE,FOLLOW_RULE_FALSE_in_ruleLiteral11724); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6365:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6365:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6366:1: (lv_parameters_19_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6366:1: (lv_parameters_19_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6367:3: lv_parameters_19_0= ruleTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral11756);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6390:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6390:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6391:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6391:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6392:3: lv_parameters_20_0= ruleExistsTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral11783);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6415:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6415:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6416:1: (lv_parameters_21_0= ruleMapGetReference )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6416:1: (lv_parameters_21_0= ruleMapGetReference )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:6417:3: lv_parameters_21_0= ruleMapGetReference
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapGetReference_in_ruleLiteral11810);
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
    public static final BitSet FOLLOW_ruleNAVASCRIPT_START_in_ruleTml126 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleTml155 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleTml167 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleTml188 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMap_in_ruleTml215 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleParam_in_ruleTml242 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMethods_in_ruleTml269 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleTml296 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleInclude_in_ruleTml323 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleValidations_in_ruleTml350 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleComment_in_ruleTml377 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleNAVASCRIPT_END_in_ruleTml395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDEBUG_START_TAG_in_entryRuleDEBUG_START_TAG447 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDEBUG_START_TAG458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleDEBUG_START_TAG498 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_ruleDEBUG_START_TAG516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDEBUG_END_TAG_in_entryRuleDEBUG_END_TAG557 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDEBUG_END_TAG568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleDEBUG_END_TAG608 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_ruleDEBUG_END_TAG626 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleDEBUG_END_TAG641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMAPSTARTKEYWORD_in_entryRuleMAPSTARTKEYWORD689 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMAPSTARTKEYWORD700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleMAPSTARTKEYWORD740 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RULE_MAPKEYWORD_in_ruleMAPSTARTKEYWORD760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleINCLUDE_START_TAG_in_entryRuleINCLUDE_START_TAG806 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleINCLUDE_START_TAG817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleINCLUDE_START_TAG857 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_ruleINCLUDE_START_TAG875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePROPERTY_START_TAG_in_entryRulePROPERTY_START_TAG916 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePROPERTY_START_TAG927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_rulePROPERTY_START_TAG967 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_rulePROPERTY_START_TAG985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleREQUIRED_START_TAG_in_entryRuleREQUIRED_START_TAG1026 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleREQUIRED_START_TAG1037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleREQUIRED_START_TAG1077 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleREQUIRED_START_TAG1095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVALIDATIONS_START_TAG_in_entryRuleVALIDATIONS_START_TAG1136 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVALIDATIONS_START_TAG1147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleVALIDATIONS_START_TAG1187 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_ruleVALIDATIONS_START_TAG1205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCHECK_START_TAG_in_entryRuleCHECK_START_TAG1246 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCHECK_START_TAG1257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleCHECK_START_TAG1297 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_ruleCHECK_START_TAG1315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCOMMENT_START_TAG_in_entryRuleCOMMENT_START_TAG1356 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCOMMENT_START_TAG1367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleCOMMENT_START_TAG1407 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_ruleCOMMENT_START_TAG1425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBREAK_START_TAG_in_entryRuleBREAK_START_TAG1466 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBREAK_START_TAG1477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleBREAK_START_TAG1517 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_ruleBREAK_START_TAG1535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOPTION_START_TAG_in_entryRuleOPTION_START_TAG1576 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOPTION_START_TAG1587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleOPTION_START_TAG1627 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ruleOPTION_START_TAG1645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBREAK_END_TAG_in_entryRuleBREAK_END_TAG1686 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBREAK_END_TAG1697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleBREAK_END_TAG1737 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_ruleBREAK_END_TAG1755 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleBREAK_END_TAG1770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOPTION_END_TAG_in_entryRuleOPTION_END_TAG1816 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOPTION_END_TAG1827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleOPTION_END_TAG1867 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ruleOPTION_END_TAG1885 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleOPTION_END_TAG1900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleREQUIRED_END_TAG_in_entryRuleREQUIRED_END_TAG1946 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleREQUIRED_END_TAG1957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleREQUIRED_END_TAG1997 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleREQUIRED_END_TAG2015 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleREQUIRED_END_TAG2030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePROPERTY_END_TAG_in_entryRulePROPERTY_END_TAG2076 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePROPERTY_END_TAG2087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_rulePROPERTY_END_TAG2127 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_rulePROPERTY_END_TAG2145 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_rulePROPERTY_END_TAG2160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCOMMENT_END_TAG_in_entryRuleCOMMENT_END_TAG2206 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCOMMENT_END_TAG2217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleCOMMENT_END_TAG2257 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_ruleCOMMENT_END_TAG2275 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleCOMMENT_END_TAG2290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVALIDATIONS_END_TAG_in_entryRuleVALIDATIONS_END_TAG2336 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVALIDATIONS_END_TAG2347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleVALIDATIONS_END_TAG2387 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_ruleVALIDATIONS_END_TAG2405 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleVALIDATIONS_END_TAG2420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCHECK_END_TAG_in_entryRuleCHECK_END_TAG2466 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCHECK_END_TAG2477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleCHECK_END_TAG2517 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_ruleCHECK_END_TAG2535 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleCHECK_END_TAG2550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePARAM_END_TAG_in_entryRulePARAM_END_TAG2596 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePARAM_END_TAG2607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_rulePARAM_END_TAG2647 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_rulePARAM_END_TAG2665 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_rulePARAM_END_TAG2680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMESSAGE_END_TAG_in_entryRuleMESSAGE_END_TAG2726 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMESSAGE_END_TAG2737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleMESSAGE_END_TAG2777 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_ruleMESSAGE_END_TAG2795 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMESSAGE_END_TAG2810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMETHODS_END_TAG_in_entryRuleMETHODS_END_TAG2856 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMETHODS_END_TAG2867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleMETHODS_END_TAG2907 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_ruleMETHODS_END_TAG2925 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMETHODS_END_TAG2940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMETHOD_END_TAG_in_entryRuleMETHOD_END_TAG2986 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMETHOD_END_TAG2997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleMETHOD_END_TAG3037 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_ruleMETHOD_END_TAG3055 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMETHOD_END_TAG3070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFIELD_END_TAG_in_entryRuleFIELD_END_TAG3116 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFIELD_END_TAG3127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleFIELD_END_TAG3167 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_ruleFIELD_END_TAG3185 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleFIELD_END_TAG3200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEXPRESSION_START_TAG_in_entryRuleEXPRESSION_START_TAG3246 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEXPRESSION_START_TAG3257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleEXPRESSION_START_TAG3297 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_ruleEXPRESSION_START_TAG3315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEXPRESSION_END_TAG_in_entryRuleEXPRESSION_END_TAG3356 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEXPRESSION_END_TAG3367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleEXPRESSION_END_TAG3407 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_ruleEXPRESSION_END_TAG3425 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleEXPRESSION_END_TAG3440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePARAM_START_TAG_in_entryRulePARAM_START_TAG3486 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePARAM_START_TAG3497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_rulePARAM_START_TAG3537 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_rulePARAM_START_TAG3555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMESSAGE_START_TAG_in_entryRuleMESSAGE_START_TAG3596 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMESSAGE_START_TAG3607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleMESSAGE_START_TAG3647 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_ruleMESSAGE_START_TAG3665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMETHOD_START_TAG_in_entryRuleMETHOD_START_TAG3706 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMETHOD_START_TAG3717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleMETHOD_START_TAG3757 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_ruleMETHOD_START_TAG3775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMETHODS_START_TAG_in_entryRuleMETHODS_START_TAG3816 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMETHODS_START_TAG3827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleMETHODS_START_TAG3867 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_ruleMETHODS_START_TAG3885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFIELD_START_TAG_in_entryRuleFIELD_START_TAG3926 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFIELD_START_TAG3937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleFIELD_START_TAG3977 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_ruleFIELD_START_TAG3995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNAVASCRIPT_START_in_entryRuleNAVASCRIPT_START4036 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNAVASCRIPT_START4047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleNAVASCRIPT_START4087 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_KEYWORD_in_ruleNAVASCRIPT_START4107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNAVASCRIPT_END_in_entryRuleNAVASCRIPT_END4153 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNAVASCRIPT_END4164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleNAVASCRIPT_END4204 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_KEYWORD_in_ruleNAVASCRIPT_END4224 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleNAVASCRIPT_END4244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttributeName_in_entryRuleAttributeName4290 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAttributeName4301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAttributeName4341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_ruleAttributeName4365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ruleAttributeName4384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression4424 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePossibleExpression4434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePossibleExpression4477 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_rulePossibleExpression4492 = new BitSet(new long[]{0x0001100000000400L});
    public static final BitSet FOLLOW_ruleAttributeName_in_rulePossibleExpression4515 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_rulePossibleExpression4525 = new BitSet(new long[]{0x0000000000006800L});
    public static final BitSet FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression4536 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleTopLevel_in_rulePossibleExpression4556 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression4565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression4588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression4608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethods_in_entryRuleMethods4644 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethods4654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMETHODS_START_TAG_in_ruleMethods4695 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethods4714 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMethod_in_ruleMethods4734 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMETHODS_END_TAG_in_ruleMethods4751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods4766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethod_in_entryRuleMethod4802 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethod4812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMETHOD_START_TAG_in_ruleMethod4853 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMethod4882 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethod4894 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleRequired_in_ruleMethod4914 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMETHOD_END_TAG_in_ruleMethod4931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod4946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValidations_in_entryRuleValidations4982 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValidations4992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVALIDATIONS_START_TAG_in_ruleValidations5033 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleValidations5052 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleCheck_in_ruleValidations5072 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleVALIDATIONS_END_TAG_in_ruleValidations5089 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleValidations5104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCheck_in_entryRuleCheck5140 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCheck5150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCHECK_START_TAG_in_ruleCheck5191 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleCheck5220 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleCheck5232 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleCheck5252 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleCHECK_END_TAG_in_ruleCheck5268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleCheck5283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComment_in_entryRuleComment5319 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComment5329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCOMMENT_START_TAG_in_ruleComment5370 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleComment5399 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleComment5411 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleCOMMENT_END_TAG_in_ruleComment5426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleComment5441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBreak_in_entryRuleBreak5477 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBreak5487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBREAK_START_TAG_in_ruleBreak5528 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleBreak5557 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleBreak5569 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleBREAK_END_TAG_in_ruleBreak5584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleBreak5599 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInclude_in_entryRuleInclude5635 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInclude5645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleINCLUDE_START_TAG_in_ruleInclude5686 = new BitSet(new long[]{0x0001100000000420L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleInclude5715 = new BitSet(new long[]{0x0001100000000420L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleInclude5725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage5760 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage5770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMESSAGE_START_TAG_in_ruleMessage5811 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMessage5840 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMessage5852 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMessage5873 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMessage5900 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMessage5927 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMessage5954 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMessage5981 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMessage6008 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleField_in_ruleMessage6035 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMessage6062 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMessage6089 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMESSAGE_END_TAG_in_ruleMessage6107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage6122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_entryRuleMap6158 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMap6168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMAPSTARTKEYWORD_in_ruleMap6209 = new BitSet(new long[]{0x0081100000000430L});
    public static final BitSet FOLLOW_55_in_ruleMap6229 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMap6250 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap6271 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap6300 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap6312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap6327 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMap6348 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMap6375 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMap6402 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMap6429 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMap6456 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMap6483 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleField_in_ruleMap6510 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMap6537 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMap6564 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleMap6576 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RULE_MAPKEYWORD_in_ruleMap6584 = new BitSet(new long[]{0x0080000000000010L});
    public static final BitSet FOLLOW_55_in_ruleMap6594 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMap6615 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap6626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapId_in_entryRuleMapId6665 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapId6676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapId6715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequired_in_entryRuleRequired6759 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequired6769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleREQUIRED_START_TAG_in_ruleRequired6810 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleRequired6839 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleRequired6850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleRequired6865 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleREQUIRED_END_TAG_in_ruleRequired6880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty6917 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty6927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePROPERTY_START_TAG_in_ruleProperty6968 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleProperty6997 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty7008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleProperty7023 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleProperty7044 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMap_in_ruleProperty7071 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_rulePROPERTY_END_TAG_in_ruleProperty7089 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParam_in_entryRuleParam7126 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParam7136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePARAM_START_TAG_in_ruleParam7177 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleParam7206 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam7217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleParam7232 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleParam7252 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_rulePARAM_END_TAG_in_ruleParam7269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapMethod_in_entryRuleMapMethod7306 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapMethod7316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_START_in_ruleMapMethod7350 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod7366 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_ruleMapMethod7381 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod7398 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMapMethod7424 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod7435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod7450 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleMapMethod7470 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_XML_START_ENDTAG_in_ruleMapMethod7479 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod7495 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_ruleMapMethod7510 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod7527 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod7541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleField_in_entryRuleField7578 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleField7588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFIELD_START_TAG_in_ruleField7629 = new BitSet(new long[]{0x0001100000000410L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleField7658 = new BitSet(new long[]{0x0001100000000410L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleField7668 = new BitSet(new long[]{0x0000000000000160L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleField7677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleField7705 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleField7732 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleField7759 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleParam_in_ruleField7786 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMap_in_ruleField7813 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleField7840 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleField7867 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleField_in_ruleField7894 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleComment_in_ruleField7921 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleField7948 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_ruleFIELD_END_TAG_in_ruleField7966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDebugTag_in_entryRuleDebugTag8003 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDebugTag8013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDEBUG_START_TAG_in_ruleDebugTag8054 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleDebugTag8083 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag8094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag8109 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleDebugTag8129 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleDEBUG_END_TAG_in_ruleDebugTag8145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_entryRuleExpressionOrOption8182 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionOrOption8192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEXPRESSION_START_TAG_in_ruleExpressionOrOption8234 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleExpressionOrOption8255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOPTION_START_TAG_in_ruleExpressionOrOption8278 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_ruleOption_in_ruleExpressionOrOption8299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag8337 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag8347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleExpressionTag8402 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag8413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag8428 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleExpressionTag8448 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleEXPRESSION_END_TAG_in_ruleExpressionTag8464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOption_in_entryRuleOption8501 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOption8511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleOption8566 = new BitSet(new long[]{0x0001100000000430L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleOption8577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleOption8592 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ruleOPTION_END_TAG_in_ruleOption8607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel8644 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel8654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel8699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement8735 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement8746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement8786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_rulePathElement8810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARENT_in_rulePathElement8831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression8876 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression8886 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression8920 = new BitSet(new long[]{0x0080000000068400L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression8936 = new BitSet(new long[]{0x0080000000048400L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleTmlExpression8959 = new BitSet(new long[]{0x0080000000008400L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression8986 = new BitSet(new long[]{0x00000000000A0000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression8996 = new BitSet(new long[]{0x0080000000008400L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression9016 = new BitSet(new long[]{0x00000000000A0000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression9027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression9062 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression9072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression9106 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression9114 = new BitSet(new long[]{0x0080000000068400L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression9130 = new BitSet(new long[]{0x0080000000048400L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleExistsTmlExpression9153 = new BitSet(new long[]{0x0080000000008400L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression9180 = new BitSet(new long[]{0x00000000000A0000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression9190 = new BitSet(new long[]{0x0080000000008400L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression9210 = new BitSet(new long[]{0x00000000000A0000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression9221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams9256 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapReferenceParams9266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_ruleMapReferenceParams9301 = new BitSet(new long[]{0x00000001FC310400L,0x0000000000000008L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams9322 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_57_in_ruleMapReferenceParams9333 = new BitSet(new long[]{0x00000001FC310400L,0x0000000000000008L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams9354 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_58_in_ruleMapReferenceParams9366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference9402 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference9412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOLLAR_in_ruleMapGetReference9454 = new BitSet(new long[]{0x0000000000008400L});
    public static final BitSet FOLLOW_RULE_PARENT_in_ruleMapGetReference9477 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference9491 = new BitSet(new long[]{0x0000000000008400L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapGetReference9509 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference9535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression9572 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression9582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression9628 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_ruleOrExpression9647 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression9681 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression9719 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression9729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression9775 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_ruleAndExpression9794 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression9828 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression9866 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression9876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression9922 = new BitSet(new long[]{0x6000000000000002L});
    public static final BitSet FOLLOW_61_in_ruleEqualityExpression9942 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression9976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_ruleEqualityExpression10002 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression10036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression10075 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRelationalExpression10085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10140 = new BitSet(new long[]{0x0000000003C00002L});
    public static final BitSet FOLLOW_RULE_XML_LT_in_ruleRelationalExpression10159 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GT_in_ruleRelationalExpression10210 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression10261 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression10312 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression10338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression10377 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression10387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression10433 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_63_in_ruleAdditiveExpression10445 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression10466 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_ruleAdditiveExpression10484 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression10505 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression10544 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression10554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression10600 = new BitSet(new long[]{0x0000000000020002L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_ruleMultiplicativeExpression10620 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression10654 = new BitSet(new long[]{0x0000000000020002L,0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression10679 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression10705 = new BitSet(new long[]{0x0000000000020002L,0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression10744 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression10754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_ruleUnaryExpression10798 = new BitSet(new long[]{0x01000001FC310400L,0x0000000000000008L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression10832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression10861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression10896 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression10906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression10952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_rulePrimaryExpression10969 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression10990 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_rulePrimaryExpression11000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName11038 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName11049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName11088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall11132 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall11142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_ruleFunctionCall11188 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_ruleFunctionCall11198 = new BitSet(new long[]{0x07000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall11219 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_57_in_ruleFunctionCall11231 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall11252 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_58_in_ruleFunctionCall11264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral11300 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral11310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleLiteral11354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral11377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FORALL_in_ruleLiteral11406 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_ruleLiteral11421 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral11438 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_ruleLiteral11453 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral11474 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_ruleLiteral11484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral11512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_ruleLiteral11537 = new BitSet(new long[]{0x03000001FC310400L,0x000000000000001CL});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral11571 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_57_in_ruleLiteral11583 = new BitSet(new long[]{0x01000001FC310400L,0x000000000000000CL});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral11604 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_ruleLiteral11616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NULL_in_ruleLiteral11640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TODAY_in_ruleLiteral11668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TRUE_in_ruleLiteral11696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FALSE_in_ruleLiteral11724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral11756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral11783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_ruleLiteral11810 = new BitSet(new long[]{0x0000000000000002L});

}