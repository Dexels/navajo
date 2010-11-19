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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_ATTRIBUTESTRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'<navascript'", "'>'", "'</navascript>'", "'/>'", "'='", "'\"='", "';\"'", "'<message'", "'</message>'", "'<map.'", "'</map.'", "'<property'", "'</property>'", "'<expression>'", "'</expression>'", "'.'", "'..'", "'['", "'/'", "']'", "'?'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'('", "')'", "','", "'{'", "'}'", "'NULL'", "'TODAY'", "'TRUE'", "'FALSE'"
    };
    public static final int RULE_ATTRIBUTESTRING=5;
    public static final int RULE_ID=4;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_INT=6;
    public static final int RULE_WS=9;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:86:1: ruleTml returns [EObject current=null] : ( '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' ) ) ;
    public final EObject ruleTml() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_messages_4_0 = null;

        EObject lv_maps_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:91:6: ( ( '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:3: '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' )
            {
            match(input,11,FOLLOW_11_in_ruleTml120); 

                    createLeafNode(grammarAccess.getTmlAccess().getNavascriptKeyword_0(), null); 
                
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
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleTml150);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:3: ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==12) ) {
                alt3=1;
            }
            else if ( (LA3_0==14) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("131:3: ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' )", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:4: ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:4: ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:6: '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )* '</navascript>'
                    {
                    match(input,12,FOLLOW_12_in_ruleTml163); 

                            createLeafNode(grammarAccess.getTmlAccess().getGreaterThanSignKeyword_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:1: ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_maps_5_0= ruleMap ) ) )*
                    loop2:
                    do {
                        int alt2=3;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==18) ) {
                            alt2=1;
                        }
                        else if ( (LA2_0==20) ) {
                            alt2=2;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:2: ( (lv_messages_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:2: ( (lv_messages_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:136:1: (lv_messages_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:136:1: (lv_messages_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:137:3: lv_messages_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getMessagesMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleTml185);
                    	    lv_messages_4_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"messages",
                    	    	        		lv_messages_4_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:160:6: ( (lv_maps_5_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:160:6: ( (lv_maps_5_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:161:1: (lv_maps_5_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:161:1: (lv_maps_5_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:162:3: lv_maps_5_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getMapsMapParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleTml212);
                    	    lv_maps_5_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getTmlRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"maps",
                    	    	        		lv_maps_5_0, 
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
                    	    break loop2;
                        }
                    } while (true);

                    match(input,13,FOLLOW_13_in_ruleTml224); 

                            createLeafNode(grammarAccess.getTmlAccess().getNavascriptKeyword_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:189:7: '/>'
                    {
                    match(input,14,FOLLOW_14_in_ruleTml241); 

                            createLeafNode(grammarAccess.getTmlAccess().getSolidusGreaterThanSignKeyword_3_1(), null); 
                        

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


    // $ANTLR start entryRulePossibleExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:201:1: entryRulePossibleExpression returns [EObject current=null] : iv_rulePossibleExpression= rulePossibleExpression EOF ;
    public final EObject entryRulePossibleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePossibleExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:202:2: (iv_rulePossibleExpression= rulePossibleExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:203:2: iv_rulePossibleExpression= rulePossibleExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPossibleExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression278);
            iv_rulePossibleExpression=rulePossibleExpression();
            _fsp--;

             current =iv_rulePossibleExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePossibleExpression288); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:210:1: rulePossibleExpression returns [EObject current=null] : ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) ) ) ;
    public final EObject rulePossibleExpression() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token lv_value_5_0=null;
        EObject lv_expressionValue_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:215:6: ( ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:1: ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:1: ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:2: ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:2: ( (lv_key_0_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:217:1: (lv_key_0_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:217:1: (lv_key_0_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:218:3: lv_key_0_0= RULE_ID
            {
            lv_key_0_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePossibleExpression330); 

            			createLeafNode(grammarAccess.getPossibleExpressionAccess().getKeyIDTerminalRuleCall_0_0(), "key"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"key",
            	        		lv_key_0_0, 
            	        		"ID", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            match(input,15,FOLLOW_15_in_rulePossibleExpression345); 

                    createLeafNode(grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:1: ( ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==16) ) {
                alt4=1;
            }
            else if ( (LA4_0==RULE_ATTRIBUTESTRING) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("244:1: ( ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) )", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:2: ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:2: ( '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:4: '\"=' ( (lv_expressionValue_3_0= ruleExpression ) ) ';\"'
                    {
                    match(input,16,FOLLOW_16_in_rulePossibleExpression357); 

                            createLeafNode(grammarAccess.getPossibleExpressionAccess().getQuotationMarkEqualsSignKeyword_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:248:1: ( (lv_expressionValue_3_0= ruleExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:249:1: (lv_expressionValue_3_0= ruleExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:249:1: (lv_expressionValue_3_0= ruleExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:250:3: lv_expressionValue_3_0= ruleExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getExpressionValueExpressionParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExpression_in_rulePossibleExpression378);
                    lv_expressionValue_3_0=ruleExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"expressionValue",
                    	        		lv_expressionValue_3_0, 
                    	        		"Expression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    match(input,17,FOLLOW_17_in_rulePossibleExpression388); 

                            createLeafNode(grammarAccess.getPossibleExpressionAccess().getSemicolonQuotationMarkKeyword_2_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:277:6: ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:277:6: ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:278:1: (lv_value_5_0= RULE_ATTRIBUTESTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:278:1: (lv_value_5_0= RULE_ATTRIBUTESTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:279:3: lv_value_5_0= RULE_ATTRIBUTESTRING
                    {
                    lv_value_5_0=(Token)input.LT(1);
                    match(input,RULE_ATTRIBUTESTRING,FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression412); 

                    			createLeafNode(grammarAccess.getPossibleExpressionAccess().getValueATTRIBUTESTRINGTerminalRuleCall_2_1_0(), "value"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPossibleExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"value",
                    	        		lv_value_5_0, 
                    	        		"ATTRIBUTESTRING", 
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


    // $ANTLR start entryRuleMessage
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:309:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:310:2: (iv_ruleMessage= ruleMessage EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:311:2: iv_ruleMessage= ruleMessage EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMessageRule(), currentNode); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage454);
            iv_ruleMessage=ruleMessage();
            _fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage464); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:318:1: ruleMessage returns [EObject current=null] : ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_messages_4_0 = null;

        EObject lv_properties_5_0 = null;

        EObject lv_maps_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:323:6: ( ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:324:1: ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:324:1: ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:324:3: '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' ) | '/>' )
            {
            match(input,18,FOLLOW_18_in_ruleMessage499); 

                    createLeafNode(grammarAccess.getMessageAccess().getMessageKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:328:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:329:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:339:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==RULE_ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:340:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:340:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:341:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMessage529);
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
            	    break loop5;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:363:3: ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' ) | '/>' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==12) ) {
                alt7=1;
            }
            else if ( (LA7_0==14) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("363:3: ( ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' ) | '/>' )", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:363:4: ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:363:4: ( '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:363:6: '>' ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )* '</message>'
                    {
                    match(input,12,FOLLOW_12_in_ruleMessage542); 

                            createLeafNode(grammarAccess.getMessageAccess().getGreaterThanSignKeyword_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:367:1: ( ( (lv_messages_4_0= ruleMessage ) ) | ( (lv_properties_5_0= ruleProperty ) ) | ( (lv_maps_6_0= ruleMap ) ) )*
                    loop6:
                    do {
                        int alt6=4;
                        switch ( input.LA(1) ) {
                        case 18:
                            {
                            alt6=1;
                            }
                            break;
                        case 22:
                            {
                            alt6=2;
                            }
                            break;
                        case 20:
                            {
                            alt6=3;
                            }
                            break;

                        }

                        switch (alt6) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:367:2: ( (lv_messages_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:367:2: ( (lv_messages_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:1: (lv_messages_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:1: (lv_messages_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:369:3: lv_messages_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getMessagesMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMessage564);
                    	    lv_messages_4_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"messages",
                    	    	        		lv_messages_4_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:392:6: ( (lv_properties_5_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:392:6: ( (lv_properties_5_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:393:1: (lv_properties_5_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:393:1: (lv_properties_5_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:394:3: lv_properties_5_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getPropertiesPropertyParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMessage591);
                    	    lv_properties_5_0=ruleProperty();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"properties",
                    	    	        		lv_properties_5_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:417:6: ( (lv_maps_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:417:6: ( (lv_maps_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:418:1: (lv_maps_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:418:1: (lv_maps_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:419:3: lv_maps_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getMapsMapParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMessage618);
                    	    lv_maps_6_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMessageRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"maps",
                    	    	        		lv_maps_6_0, 
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
                    	    break loop6;
                        }
                    } while (true);

                    match(input,19,FOLLOW_19_in_ruleMessage630); 

                            createLeafNode(grammarAccess.getMessageAccess().getMessageKeyword_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:446:7: '/>'
                    {
                    match(input,14,FOLLOW_14_in_ruleMessage647); 

                            createLeafNode(grammarAccess.getMessageAccess().getSolidusGreaterThanSignKeyword_3_1(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:458:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:459:2: (iv_ruleMap= ruleMap EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:460:2: iv_ruleMap= ruleMap EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapRule(), currentNode); 
            pushFollow(FOLLOW_ruleMap_in_entryRuleMap684);
            iv_ruleMap=ruleMap();
            _fsp--;

             current =iv_ruleMap; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMap694); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:467:1: ruleMap returns [EObject current=null] : ( '<map.' ( (lv_mapName_1_0= RULE_ID ) ) ( (lv_attributes_2_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' ) ) ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token lv_mapName_1_0=null;
        Token lv_mapClosingName_9_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_messages_5_0 = null;

        EObject lv_properties_6_0 = null;

        EObject lv_maps_7_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:472:6: ( ( '<map.' ( (lv_mapName_1_0= RULE_ID ) ) ( (lv_attributes_2_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:473:1: ( '<map.' ( (lv_mapName_1_0= RULE_ID ) ) ( (lv_attributes_2_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:473:1: ( '<map.' ( (lv_mapName_1_0= RULE_ID ) ) ( (lv_attributes_2_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:473:3: '<map.' ( (lv_mapName_1_0= RULE_ID ) ) ( (lv_attributes_2_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' ) )
            {
            match(input,20,FOLLOW_20_in_ruleMap729); 

                    createLeafNode(grammarAccess.getMapAccess().getMapKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:477:1: ( (lv_mapName_1_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:1: (lv_mapName_1_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:1: (lv_mapName_1_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:479:3: lv_mapName_1_0= RULE_ID
            {
            lv_mapName_1_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMap746); 

            			createLeafNode(grammarAccess.getMapAccess().getMapNameIDTerminalRuleCall_1_0(), "mapName"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:501:2: ( (lv_attributes_2_0= rulePossibleExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:502:1: (lv_attributes_2_0= rulePossibleExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:502:1: (lv_attributes_2_0= rulePossibleExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:503:3: lv_attributes_2_0= rulePossibleExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap772);
            lv_attributes_2_0=rulePossibleExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:525:2: ( '/>' | ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==14) ) {
                alt9=1;
            }
            else if ( (LA9_0==12) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("525:2: ( '/>' | ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' ) )", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:525:4: '/>'
                    {
                    match(input,14,FOLLOW_14_in_ruleMap783); 

                            createLeafNode(grammarAccess.getMapAccess().getSolidusGreaterThanSignKeyword_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:530:6: ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:530:6: ( '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:530:8: '>' ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_9_0= RULE_ID ) ) '>'
                    {
                    match(input,12,FOLLOW_12_in_ruleMap800); 

                            createLeafNode(grammarAccess.getMapAccess().getGreaterThanSignKeyword_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:534:1: ( ( (lv_messages_5_0= ruleMessage ) ) | ( (lv_properties_6_0= ruleProperty ) ) | ( (lv_maps_7_0= ruleMap ) ) )*
                    loop8:
                    do {
                        int alt8=4;
                        switch ( input.LA(1) ) {
                        case 18:
                            {
                            alt8=1;
                            }
                            break;
                        case 22:
                            {
                            alt8=2;
                            }
                            break;
                        case 20:
                            {
                            alt8=3;
                            }
                            break;

                        }

                        switch (alt8) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:534:2: ( (lv_messages_5_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:534:2: ( (lv_messages_5_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:535:1: (lv_messages_5_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:535:1: (lv_messages_5_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:536:3: lv_messages_5_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMessagesMessageParserRuleCall_3_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMap822);
                    	    lv_messages_5_0=ruleMessage();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"messages",
                    	    	        		lv_messages_5_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:559:6: ( (lv_properties_6_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:559:6: ( (lv_properties_6_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:560:1: (lv_properties_6_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:560:1: (lv_properties_6_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:561:3: lv_properties_6_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getPropertiesPropertyParserRuleCall_3_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMap849);
                    	    lv_properties_6_0=ruleProperty();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"properties",
                    	    	        		lv_properties_6_0, 
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:584:6: ( (lv_maps_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:584:6: ( (lv_maps_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:585:1: (lv_maps_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:585:1: (lv_maps_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:586:3: lv_maps_7_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapsMapParserRuleCall_3_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMap876);
                    	    lv_maps_7_0=ruleMap();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"maps",
                    	    	        		lv_maps_7_0, 
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
                    	    break loop8;
                        }
                    } while (true);

                    match(input,21,FOLLOW_21_in_ruleMap888); 

                            createLeafNode(grammarAccess.getMapAccess().getMapKeyword_3_1_2(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:612:1: ( (lv_mapClosingName_9_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:613:1: (lv_mapClosingName_9_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:613:1: (lv_mapClosingName_9_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:614:3: lv_mapClosingName_9_0= RULE_ID
                    {
                    lv_mapClosingName_9_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMap905); 

                    			createLeafNode(grammarAccess.getMapAccess().getMapClosingNameIDTerminalRuleCall_3_1_3_0(), "mapClosingName"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"mapClosingName",
                    	        		lv_mapClosingName_9_0, 
                    	        		"ID", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,12,FOLLOW_12_in_ruleMap920); 

                            createLeafNode(grammarAccess.getMapAccess().getGreaterThanSignKeyword_3_1_4(), null); 
                        

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


    // $ANTLR start entryRuleProperty
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:648:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:649:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:650:2: iv_ruleProperty= ruleProperty EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPropertyRule(), currentNode); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty958);
            iv_ruleProperty=ruleProperty();
            _fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty968); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:657:1: ruleProperty returns [EObject current=null] : ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:662:6: ( ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:663:1: ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:663:1: ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:663:3: '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )
            {
            match(input,22,FOLLOW_22_in_ruleProperty1003); 

                    createLeafNode(grammarAccess.getPropertyAccess().getPropertyKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:667:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:668:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:678:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:679:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:679:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:680:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleProperty1033);
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
            	    break loop10;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:702:3: ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==14) ) {
                alt12=1;
            }
            else if ( (LA12_0==12) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("702:3: ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:702:5: '/>'
                    {
                    match(input,14,FOLLOW_14_in_ruleProperty1045); 

                            createLeafNode(grammarAccess.getPropertyAccess().getSolidusGreaterThanSignKeyword_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:707:6: ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:707:6: ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:707:8: '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>'
                    {
                    match(input,12,FOLLOW_12_in_ruleProperty1062); 

                            createLeafNode(grammarAccess.getPropertyAccess().getGreaterThanSignKeyword_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:1: ( (lv_expressionValue_5_0= ruleExpressionTag ) )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==24) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:712:1: (lv_expressionValue_5_0= ruleExpressionTag )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:712:1: (lv_expressionValue_5_0= ruleExpressionTag )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:713:3: lv_expressionValue_5_0= ruleExpressionTag
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getExpressionValueExpressionTagParserRuleCall_3_1_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleExpressionTag_in_ruleProperty1083);
                            lv_expressionValue_5_0=ruleExpressionTag();
                            _fsp--;


                            	        if (current==null) {
                            	            current = factory.create(grammarAccess.getPropertyRule().getType().getClassifier());
                            	            associateNodeWithAstElement(currentNode.getParent(), current);
                            	        }
                            	        try {
                            	       		set(
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

                    }

                    match(input,23,FOLLOW_23_in_ruleProperty1094); 

                            createLeafNode(grammarAccess.getPropertyAccess().getPropertyKeyword_3_1_2(), null); 
                        

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


    // $ANTLR start entryRuleExpressionTag
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:747:1: entryRuleExpressionTag returns [EObject current=null] : iv_ruleExpressionTag= ruleExpressionTag EOF ;
    public final EObject entryRuleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:748:2: (iv_ruleExpressionTag= ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:749:2: iv_ruleExpressionTag= ruleExpressionTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag1132);
            iv_ruleExpressionTag=ruleExpressionTag();
            _fsp--;

             current =iv_ruleExpressionTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag1142); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:756:1: ruleExpressionTag returns [EObject current=null] : ( '<expression>' ( (lv_value_1_0= ruleExpression ) ) '</expression>' ) ;
    public final EObject ruleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject lv_value_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:761:6: ( ( '<expression>' ( (lv_value_1_0= ruleExpression ) ) '</expression>' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:762:1: ( '<expression>' ( (lv_value_1_0= ruleExpression ) ) '</expression>' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:762:1: ( '<expression>' ( (lv_value_1_0= ruleExpression ) ) '</expression>' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:762:3: '<expression>' ( (lv_value_1_0= ruleExpression ) ) '</expression>'
            {
            match(input,24,FOLLOW_24_in_ruleExpressionTag1177); 

                    createLeafNode(grammarAccess.getExpressionTagAccess().getExpressionKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:766:1: ( (lv_value_1_0= ruleExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:767:1: (lv_value_1_0= ruleExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:767:1: (lv_value_1_0= ruleExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:768:3: lv_value_1_0= ruleExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getValueExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleExpression_in_ruleExpressionTag1198);
            lv_value_1_0=ruleExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getExpressionTagRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"value",
            	        		lv_value_1_0, 
            	        		"Expression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,25,FOLLOW_25_in_ruleExpressionTag1208); 

                    createLeafNode(grammarAccess.getExpressionTagAccess().getExpressionKeyword_2(), null); 
                

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


    // $ANTLR start entryRuleExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:802:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:803:2: (iv_ruleExpression= ruleExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:804:2: iv_ruleExpression= ruleExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpression_in_entryRuleExpression1244);
            iv_ruleExpression=ruleExpression();
            _fsp--;

             current =iv_ruleExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpression1254); 

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
    // $ANTLR end entryRuleExpression


    // $ANTLR start ruleExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:811:1: ruleExpression returns [EObject current=null] : ( (lv_expression_0_0= ruleOrExpression ) ) ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_expression_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:816:6: ( ( (lv_expression_0_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:817:1: ( (lv_expression_0_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:817:1: ( (lv_expression_0_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:818:1: (lv_expression_0_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:818:1: (lv_expression_0_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:819:3: lv_expression_0_0= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExpressionAccess().getExpressionOrExpressionParserRuleCall_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleExpression1299);
            lv_expression_0_0=ruleOrExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"expression",
            	        		lv_expression_0_0, 
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
    // $ANTLR end ruleExpression


    // $ANTLR start entryRulePathElement
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:849:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:851:2: iv_rulePathElement= rulePathElement EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathElementRule(), currentNode); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement1335);
            iv_rulePathElement=rulePathElement();
            _fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement1346); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:858:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:863:6: ( (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:864:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:864:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            int alt13=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt13=1;
                }
                break;
            case 26:
                {
                alt13=2;
                }
                break;
            case 27:
                {
                alt13=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("864:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:864:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement1386); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:873:2: kw= '.'
                    {
                    kw=(Token)input.LT(1);
                    match(input,26,FOLLOW_26_in_rulePathElement1410); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:880:2: kw= '..'
                    {
                    kw=(Token)input.LT(1);
                    match(input,27,FOLLOW_27_in_rulePathElement1429); 

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


    // $ANTLR start entryRulePathSequence
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:893:1: entryRulePathSequence returns [String current=null] : iv_rulePathSequence= rulePathSequence EOF ;
    public final String entryRulePathSequence() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathSequence = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:894:2: (iv_rulePathSequence= rulePathSequence EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:895:2: iv_rulePathSequence= rulePathSequence EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathSequenceRule(), currentNode); 
            pushFollow(FOLLOW_rulePathSequence_in_entryRulePathSequence1470);
            iv_rulePathSequence=rulePathSequence();
            _fsp--;

             current =iv_rulePathSequence.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathSequence1481); 

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
    // $ANTLR end entryRulePathSequence


    // $ANTLR start rulePathSequence
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:902:1: rulePathSequence returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' ) ;
    public final AntlrDatatypeRuleToken rulePathSequence() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_PathElement_2 = null;

        AntlrDatatypeRuleToken this_PathElement_4 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:907:6: ( (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:908:1: (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:908:1: (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:909:2: kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']'
            {
            kw=(Token)input.LT(1);
            match(input,28,FOLLOW_28_in_rulePathSequence1519); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getPathSequenceAccess().getLeftSquareBracketKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:914:1: (kw= '/' )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==29) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:915:2: kw= '/'
                    {
                    kw=(Token)input.LT(1);
                    match(input,29,FOLLOW_29_in_rulePathSequence1533); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathSequenceAccess().getSolidusKeyword_1(), null); 
                        

                    }
                    break;

            }

             
                    currentNode=createCompositeNode(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_2(), currentNode); 
                
            pushFollow(FOLLOW_rulePathElement_in_rulePathSequence1557);
            this_PathElement_2=rulePathElement();
            _fsp--;


            		current.merge(this_PathElement_2);
                
             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:931:1: (kw= '/' this_PathElement_4= rulePathElement )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==29) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:932:2: kw= '/' this_PathElement_4= rulePathElement
            	    {
            	    kw=(Token)input.LT(1);
            	    match(input,29,FOLLOW_29_in_rulePathSequence1576); 

            	            current.merge(kw);
            	            createLeafNode(grammarAccess.getPathSequenceAccess().getSolidusKeyword_3_0(), null); 
            	        
            	     
            	            currentNode=createCompositeNode(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_3_1(), currentNode); 
            	        
            	    pushFollow(FOLLOW_rulePathElement_in_rulePathSequence1598);
            	    this_PathElement_4=rulePathElement();
            	    _fsp--;


            	    		current.merge(this_PathElement_4);
            	        
            	     
            	            currentNode = currentNode.getParent();
            	        

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            kw=(Token)input.LT(1);
            match(input,30,FOLLOW_30_in_rulePathSequence1618); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getPathSequenceAccess().getRightSquareBracketKeyword_4(), null); 
                

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
    // $ANTLR end rulePathSequence


    // $ANTLR start entryRuleTmlExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:962:1: entryRuleTmlExpression returns [String current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final String entryRuleTmlExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:963:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:964:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression1659);
            iv_ruleTmlExpression=ruleTmlExpression();
            _fsp--;

             current =iv_ruleTmlExpression.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression1670); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:971:1: ruleTmlExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_PathSequence_0= rulePathSequence ;
    public final AntlrDatatypeRuleToken ruleTmlExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_PathSequence_0 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:976:6: (this_PathSequence_0= rulePathSequence )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:978:5: this_PathSequence_0= rulePathSequence
            {
             
                    currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getPathSequenceParserRuleCall(), currentNode); 
                
            pushFollow(FOLLOW_rulePathSequence_in_ruleTmlExpression1716);
            this_PathSequence_0=rulePathSequence();
            _fsp--;


            		current.merge(this_PathSequence_0);
                
             
                    currentNode = currentNode.getParent();
                

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:996:1: entryRuleExistsTmlExpression returns [String current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final String entryRuleExistsTmlExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:997:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:998:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExistsTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression1761);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();
            _fsp--;

             current =iv_ruleExistsTmlExpression.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression1772); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1005:1: ruleExistsTmlExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '?' this_TmlExpression_1= ruleTmlExpression ) ;
    public final AntlrDatatypeRuleToken ruleExistsTmlExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_TmlExpression_1 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1010:6: ( (kw= '?' this_TmlExpression_1= ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1011:1: (kw= '?' this_TmlExpression_1= ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1011:1: (kw= '?' this_TmlExpression_1= ruleTmlExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1012:2: kw= '?' this_TmlExpression_1= ruleTmlExpression
            {
            kw=(Token)input.LT(1);
            match(input,31,FOLLOW_31_in_ruleExistsTmlExpression1810); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getQuestionMarkKeyword_0(), null); 
                
             
                    currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getTmlExpressionParserRuleCall_1(), currentNode); 
                
            pushFollow(FOLLOW_ruleTmlExpression_in_ruleExistsTmlExpression1832);
            this_TmlExpression_1=ruleTmlExpression();
            _fsp--;


            		current.merge(this_TmlExpression_1);
                
             
                    currentNode = currentNode.getParent();
                

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


    // $ANTLR start entryRuleOrExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1036:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1037:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1038:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression1877);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression1887); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1045:1: ruleOrExpression returns [EObject current=null] : ( () ( (lv_operands_1_0= ruleAndExpression ) ) ( ( (lv_op_2_0= 'OR' ) ) ( (lv_operands_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject lv_operands_1_0 = null;

        EObject lv_operands_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1050:6: ( ( () ( (lv_operands_1_0= ruleAndExpression ) ) ( ( (lv_op_2_0= 'OR' ) ) ( (lv_operands_3_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1051:1: ( () ( (lv_operands_1_0= ruleAndExpression ) ) ( ( (lv_op_2_0= 'OR' ) ) ( (lv_operands_3_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1051:1: ( () ( (lv_operands_1_0= ruleAndExpression ) ) ( ( (lv_op_2_0= 'OR' ) ) ( (lv_operands_3_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1051:2: () ( (lv_operands_1_0= ruleAndExpression ) ) ( ( (lv_op_2_0= 'OR' ) ) ( (lv_operands_3_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1051:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1052:5: 
            {
             
                    temp=factory.create(grammarAccess.getOrExpressionAccess().getExpressionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getOrExpressionAccess().getExpressionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1062:2: ( (lv_operands_1_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1063:1: (lv_operands_1_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1063:1: (lv_operands_1_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1064:3: lv_operands_1_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getOperandsAndExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression1942);
            lv_operands_1_0=ruleAndExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"operands",
            	        		lv_operands_1_0, 
            	        		"AndExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1086:2: ( ( (lv_op_2_0= 'OR' ) ) ( (lv_operands_3_0= ruleAndExpression ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==32) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1086:3: ( (lv_op_2_0= 'OR' ) ) ( (lv_operands_3_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1086:3: ( (lv_op_2_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1087:1: (lv_op_2_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1087:1: (lv_op_2_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1088:3: lv_op_2_0= 'OR'
            	    {
            	    lv_op_2_0=(Token)input.LT(1);
            	    match(input,32,FOLLOW_32_in_ruleOrExpression1961); 

            	            createLeafNode(grammarAccess.getOrExpressionAccess().getOpORKeyword_2_0_0(), "op"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		set(current, "op", lv_op_2_0, "OR", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1107:2: ( (lv_operands_3_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1108:1: (lv_operands_3_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1108:1: (lv_operands_3_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1109:3: lv_operands_3_0= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getOperandsAndExpressionParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression1995);
            	    lv_operands_3_0=ruleAndExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"operands",
            	    	        		lv_operands_3_0, 
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
            	    break loop16;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1139:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1140:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1141:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression2033);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression2043); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1148:1: ruleAndExpression returns [EObject current=null] : ( () ( (lv_operands_1_0= ruleEqualityExpression ) ) ( ( (lv_op_2_0= 'AND' ) ) ( (lv_operands_3_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject lv_operands_1_0 = null;

        EObject lv_operands_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1153:6: ( ( () ( (lv_operands_1_0= ruleEqualityExpression ) ) ( ( (lv_op_2_0= 'AND' ) ) ( (lv_operands_3_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1154:1: ( () ( (lv_operands_1_0= ruleEqualityExpression ) ) ( ( (lv_op_2_0= 'AND' ) ) ( (lv_operands_3_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1154:1: ( () ( (lv_operands_1_0= ruleEqualityExpression ) ) ( ( (lv_op_2_0= 'AND' ) ) ( (lv_operands_3_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1154:2: () ( (lv_operands_1_0= ruleEqualityExpression ) ) ( ( (lv_op_2_0= 'AND' ) ) ( (lv_operands_3_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1154:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1155:5: 
            {
             
                    temp=factory.create(grammarAccess.getAndExpressionAccess().getExpressionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getAndExpressionAccess().getExpressionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1165:2: ( (lv_operands_1_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1166:1: (lv_operands_1_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1166:1: (lv_operands_1_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1167:3: lv_operands_1_0= ruleEqualityExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getOperandsEqualityExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression2098);
            lv_operands_1_0=ruleEqualityExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"operands",
            	        		lv_operands_1_0, 
            	        		"EqualityExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1189:2: ( ( (lv_op_2_0= 'AND' ) ) ( (lv_operands_3_0= ruleEqualityExpression ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==33) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1189:3: ( (lv_op_2_0= 'AND' ) ) ( (lv_operands_3_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1189:3: ( (lv_op_2_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1190:1: (lv_op_2_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1190:1: (lv_op_2_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1191:3: lv_op_2_0= 'AND'
            	    {
            	    lv_op_2_0=(Token)input.LT(1);
            	    match(input,33,FOLLOW_33_in_ruleAndExpression2117); 

            	            createLeafNode(grammarAccess.getAndExpressionAccess().getOpANDKeyword_2_0_0(), "op"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		set(current, "op", lv_op_2_0, "AND", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1210:2: ( (lv_operands_3_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1211:1: (lv_operands_3_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1211:1: (lv_operands_3_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1212:3: lv_operands_3_0= ruleEqualityExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getOperandsEqualityExpressionParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression2151);
            	    lv_operands_3_0=ruleEqualityExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"operands",
            	    	        		lv_operands_3_0, 
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
            	    break loop17;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1242:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1243:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1244:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEqualityExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression2189);
            iv_ruleEqualityExpression=ruleEqualityExpression();
            _fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression2199); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1251:1: ruleEqualityExpression returns [EObject current=null] : ( () ( (lv_operands_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        Token lv_op_4_0=null;
        EObject lv_operands_1_0 = null;

        EObject lv_operands_3_0 = null;

        EObject lv_operands_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1256:6: ( ( () ( (lv_operands_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1257:1: ( () ( (lv_operands_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1257:1: ( () ( (lv_operands_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1257:2: () ( (lv_operands_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1257:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1258:5: 
            {
             
                    temp=factory.create(grammarAccess.getEqualityExpressionAccess().getExpressionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getEqualityExpressionAccess().getExpressionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1268:2: ( (lv_operands_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1269:1: (lv_operands_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1269:1: (lv_operands_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1270:3: lv_operands_1_0= ruleAdditiveExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getOperandsAdditiveExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2254);
            lv_operands_1_0=ruleAdditiveExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"operands",
            	        		lv_operands_1_0, 
            	        		"AdditiveExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1292:2: ( ( ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) ) ) )?
            int alt18=3;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==34) ) {
                alt18=1;
            }
            else if ( (LA18_0==35) ) {
                alt18=2;
            }
            switch (alt18) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1292:3: ( ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1292:3: ( ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1292:4: ( (lv_op_2_0= '==' ) ) ( (lv_operands_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1292:4: ( (lv_op_2_0= '==' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1293:1: (lv_op_2_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1293:1: (lv_op_2_0= '==' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1294:3: lv_op_2_0= '=='
                    {
                    lv_op_2_0=(Token)input.LT(1);
                    match(input,34,FOLLOW_34_in_ruleEqualityExpression2274); 

                            createLeafNode(grammarAccess.getEqualityExpressionAccess().getOpEqualsSignEqualsSignKeyword_2_0_0_0(), "op"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "op", lv_op_2_0, "==", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1313:2: ( (lv_operands_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:1: (lv_operands_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:1: (lv_operands_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1315:3: lv_operands_3_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getOperandsAdditiveExpressionParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2308);
                    lv_operands_3_0=ruleAdditiveExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operands",
                    	        		lv_operands_3_0, 
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1338:6: ( ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1338:6: ( ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1338:7: ( (lv_op_4_0= '!=' ) ) ( (lv_operands_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1338:7: ( (lv_op_4_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1339:1: (lv_op_4_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1339:1: (lv_op_4_0= '!=' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1340:3: lv_op_4_0= '!='
                    {
                    lv_op_4_0=(Token)input.LT(1);
                    match(input,35,FOLLOW_35_in_ruleEqualityExpression2334); 

                            createLeafNode(grammarAccess.getEqualityExpressionAccess().getOpExclamationMarkEqualsSignKeyword_2_1_0_0(), "op"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "op", lv_op_4_0, "!=", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1359:2: ( (lv_operands_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:1: (lv_operands_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:1: (lv_operands_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1361:3: lv_operands_5_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getOperandsAdditiveExpressionParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2368);
                    lv_operands_5_0=ruleAdditiveExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operands",
                    	        		lv_operands_5_0, 
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
    // $ANTLR end ruleEqualityExpression


    // $ANTLR start entryRuleAdditiveExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1391:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1392:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1393:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAdditiveExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression2407);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();
            _fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression2417); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1400:1: ruleAdditiveExpression returns [EObject current=null] : ( () ( (lv_operands_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_operands_1_0 = null;

        EObject lv_operands_3_0 = null;

        EObject lv_operands_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1405:6: ( ( () ( (lv_operands_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1406:1: ( () ( (lv_operands_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1406:1: ( () ( (lv_operands_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1406:2: () ( (lv_operands_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1406:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1407:5: 
            {
             
                    temp=factory.create(grammarAccess.getAdditiveExpressionAccess().getExpressionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getExpressionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1417:2: ( (lv_operands_1_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1418:1: (lv_operands_1_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1418:1: (lv_operands_1_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1419:3: lv_operands_1_0= ruleMultiplicativeExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getOperandsMultiplicativeExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2472);
            lv_operands_1_0=ruleMultiplicativeExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"operands",
            	        		lv_operands_1_0, 
            	        		"MultiplicativeExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1441:2: ( ( '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) ) ) )*
            loop19:
            do {
                int alt19=3;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==36) ) {
                    alt19=1;
                }
                else if ( (LA19_0==37) ) {
                    alt19=2;
                }


                switch (alt19) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1441:3: ( '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1441:3: ( '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1441:5: '+' ( (lv_operands_3_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,36,FOLLOW_36_in_ruleAdditiveExpression2484); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_2_0_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1445:1: ( (lv_operands_3_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1446:1: (lv_operands_3_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1446:1: (lv_operands_3_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1447:3: lv_operands_3_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getOperandsMultiplicativeExpressionParserRuleCall_2_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2505);
            	    lv_operands_3_0=ruleMultiplicativeExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"operands",
            	    	        		lv_operands_3_0, 
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1470:6: ( '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1470:6: ( '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1470:8: '-' ( (lv_operands_5_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,37,FOLLOW_37_in_ruleAdditiveExpression2523); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_2_1_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1474:1: ( (lv_operands_5_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1475:1: (lv_operands_5_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1475:1: (lv_operands_5_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1476:3: lv_operands_5_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getOperandsMultiplicativeExpressionParserRuleCall_2_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2544);
            	    lv_operands_5_0=ruleMultiplicativeExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"operands",
            	    	        		lv_operands_5_0, 
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
            	    break loop19;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1506:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1507:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1508:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression2583);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();
            _fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression2593); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1515:1: ruleMultiplicativeExpression returns [EObject current=null] : ( () ( (lv_operands_1_0= ruleUnaryExpression ) ) ( ( ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        Token lv_op_4_0=null;
        EObject lv_operands_1_0 = null;

        EObject lv_operands_3_0 = null;

        EObject lv_operands_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1520:6: ( ( () ( (lv_operands_1_0= ruleUnaryExpression ) ) ( ( ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1521:1: ( () ( (lv_operands_1_0= ruleUnaryExpression ) ) ( ( ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1521:1: ( () ( (lv_operands_1_0= ruleUnaryExpression ) ) ( ( ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1521:2: () ( (lv_operands_1_0= ruleUnaryExpression ) ) ( ( ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1521:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1522:5: 
            {
             
                    temp=factory.create(grammarAccess.getMultiplicativeExpressionAccess().getExpressionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getExpressionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1532:2: ( (lv_operands_1_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1533:1: (lv_operands_1_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1533:1: (lv_operands_1_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1534:3: lv_operands_1_0= ruleUnaryExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getOperandsUnaryExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2648);
            lv_operands_1_0=ruleUnaryExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"operands",
            	        		lv_operands_1_0, 
            	        		"UnaryExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1556:2: ( ( ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) ) ) )*
            loop20:
            do {
                int alt20=3;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==38) ) {
                    alt20=1;
                }
                else if ( (LA20_0==29) ) {
                    alt20=2;
                }


                switch (alt20) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1556:3: ( ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1556:3: ( ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1556:4: ( (lv_op_2_0= '*' ) ) ( (lv_operands_3_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1556:4: ( (lv_op_2_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1557:1: (lv_op_2_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1557:1: (lv_op_2_0= '*' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1558:3: lv_op_2_0= '*'
            	    {
            	    lv_op_2_0=(Token)input.LT(1);
            	    match(input,38,FOLLOW_38_in_ruleMultiplicativeExpression2668); 

            	            createLeafNode(grammarAccess.getMultiplicativeExpressionAccess().getOpAsteriskKeyword_2_0_0_0(), "op"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		set(current, "op", lv_op_2_0, "*", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1577:2: ( (lv_operands_3_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1578:1: (lv_operands_3_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1578:1: (lv_operands_3_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1579:3: lv_operands_3_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getOperandsUnaryExpressionParserRuleCall_2_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2702);
            	    lv_operands_3_0=ruleUnaryExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"operands",
            	    	        		lv_operands_3_0, 
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1602:6: ( ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1602:6: ( ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1602:7: ( (lv_op_4_0= '/' ) ) ( (lv_operands_5_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1602:7: ( (lv_op_4_0= '/' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1603:1: (lv_op_4_0= '/' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1603:1: (lv_op_4_0= '/' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1604:3: lv_op_4_0= '/'
            	    {
            	    lv_op_4_0=(Token)input.LT(1);
            	    match(input,29,FOLLOW_29_in_ruleMultiplicativeExpression2728); 

            	            createLeafNode(grammarAccess.getMultiplicativeExpressionAccess().getOpSolidusKeyword_2_1_0_0(), "op"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		set(current, "op", lv_op_4_0, "/", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1623:2: ( (lv_operands_5_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:1: (lv_operands_5_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:1: (lv_operands_5_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1625:3: lv_operands_5_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getOperandsUnaryExpressionParserRuleCall_2_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2762);
            	    lv_operands_5_0=ruleUnaryExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"operands",
            	    	        		lv_operands_5_0, 
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
            	    break loop20;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1655:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1656:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1657:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getUnaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression2801);
            iv_ruleUnaryExpression=ruleUnaryExpression();
            _fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression2811); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1664:1: ruleUnaryExpression returns [EObject current=null] : ( ( () ( (lv_op_1_0= '!' ) ) ( (lv_operands_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_1_0=null;
        EObject lv_operands_2_0 = null;

        EObject this_PrimaryExpression_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1669:6: ( ( ( () ( (lv_op_1_0= '!' ) ) ( (lv_operands_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1670:1: ( ( () ( (lv_op_1_0= '!' ) ) ( (lv_operands_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1670:1: ( ( () ( (lv_op_1_0= '!' ) ) ( (lv_operands_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==39) ) {
                alt21=1;
            }
            else if ( (LA21_0==RULE_ID||LA21_0==RULE_INT||LA21_0==28||LA21_0==31||LA21_0==40||LA21_0==43||(LA21_0>=45 && LA21_0<=48)) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1670:1: ( ( () ( (lv_op_1_0= '!' ) ) ( (lv_operands_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1670:2: ( () ( (lv_op_1_0= '!' ) ) ( (lv_operands_2_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1670:2: ( () ( (lv_op_1_0= '!' ) ) ( (lv_operands_2_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1670:3: () ( (lv_op_1_0= '!' ) ) ( (lv_operands_2_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1670:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1671:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getUnaryExpressionAccess().getExpressionAction_0_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getUnaryExpressionAccess().getExpressionAction_0_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1681:2: ( (lv_op_1_0= '!' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1682:1: (lv_op_1_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1682:1: (lv_op_1_0= '!' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1683:3: lv_op_1_0= '!'
                    {
                    lv_op_1_0=(Token)input.LT(1);
                    match(input,39,FOLLOW_39_in_ruleUnaryExpression2864); 

                            createLeafNode(grammarAccess.getUnaryExpressionAccess().getOpExclamationMarkKeyword_0_1_0(), "op"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getUnaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "op", lv_op_1_0, "!", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1702:2: ( (lv_operands_2_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1703:1: (lv_operands_2_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1703:1: (lv_operands_2_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1704:3: lv_operands_2_0= rulePrimaryExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getOperandsPrimaryExpressionParserRuleCall_0_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2898);
                    lv_operands_2_0=rulePrimaryExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getUnaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operands",
                    	        		lv_operands_2_0, 
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1728:5: this_PrimaryExpression_3= rulePrimaryExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2927);
                    this_PrimaryExpression_3=rulePrimaryExpression();
                    _fsp--;

                     
                            current = this_PrimaryExpression_3; 
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1744:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1745:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1746:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPrimaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression2962);
            iv_rulePrimaryExpression=rulePrimaryExpression();
            _fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression2972); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1753:1: rulePrimaryExpression returns [EObject current=null] : ( ( () ( (lv_operands_1_0= ruleLiteral ) ) ) | ( '(' this_OrExpression_3= ruleOrExpression ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_operands_1_0 = null;

        EObject this_OrExpression_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1758:6: ( ( ( () ( (lv_operands_1_0= ruleLiteral ) ) ) | ( '(' this_OrExpression_3= ruleOrExpression ')' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1759:1: ( ( () ( (lv_operands_1_0= ruleLiteral ) ) ) | ( '(' this_OrExpression_3= ruleOrExpression ')' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1759:1: ( ( () ( (lv_operands_1_0= ruleLiteral ) ) ) | ( '(' this_OrExpression_3= ruleOrExpression ')' ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_ID||LA22_0==RULE_INT||LA22_0==28||LA22_0==31||LA22_0==43||(LA22_0>=45 && LA22_0<=48)) ) {
                alt22=1;
            }
            else if ( (LA22_0==40) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1759:1: ( ( () ( (lv_operands_1_0= ruleLiteral ) ) ) | ( '(' this_OrExpression_3= ruleOrExpression ')' ) )", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1759:2: ( () ( (lv_operands_1_0= ruleLiteral ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1759:2: ( () ( (lv_operands_1_0= ruleLiteral ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1759:3: () ( (lv_operands_1_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1759:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1760:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getPrimaryExpressionAccess().getExpressionAction_0_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getExpressionAction_0_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1770:2: ( (lv_operands_1_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1771:1: (lv_operands_1_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1771:1: (lv_operands_1_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1772:3: lv_operands_1_0= ruleLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getOperandsLiteralParserRuleCall_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression3028);
                    lv_operands_1_0=ruleLiteral();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPrimaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operands",
                    	        		lv_operands_1_0, 
                    	        		"Literal", 
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1795:6: ( '(' this_OrExpression_3= ruleOrExpression ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1795:6: ( '(' this_OrExpression_3= ruleOrExpression ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1795:8: '(' this_OrExpression_3= ruleOrExpression ')'
                    {
                    match(input,40,FOLLOW_40_in_rulePrimaryExpression3046); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                     
                            currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getOrExpressionParserRuleCall_1_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression3068);
                    this_OrExpression_3=ruleOrExpression();
                    _fsp--;

                     
                            current = this_OrExpression_3; 
                            currentNode = currentNode.getParent();
                        
                    match(input,41,FOLLOW_41_in_rulePrimaryExpression3077); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1820:1: entryRuleFunctionName returns [String current=null] : iv_ruleFunctionName= ruleFunctionName EOF ;
    public final String entryRuleFunctionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFunctionName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1821:2: (iv_ruleFunctionName= ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1822:2: iv_ruleFunctionName= ruleFunctionName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName3115);
            iv_ruleFunctionName=ruleFunctionName();
            _fsp--;

             current =iv_ruleFunctionName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName3126); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1829:1: ruleFunctionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFunctionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1834:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1835:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName3165); 

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


    // $ANTLR start entryRuleFunctionOperands
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1850:1: entryRuleFunctionOperands returns [EObject current=null] : iv_ruleFunctionOperands= ruleFunctionOperands EOF ;
    public final EObject entryRuleFunctionOperands() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionOperands = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1851:2: (iv_ruleFunctionOperands= ruleFunctionOperands EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1852:2: iv_ruleFunctionOperands= ruleFunctionOperands EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionOperandsRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionOperands_in_entryRuleFunctionOperands3209);
            iv_ruleFunctionOperands=ruleFunctionOperands();
            _fsp--;

             current =iv_ruleFunctionOperands; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionOperands3219); 

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
    // $ANTLR end entryRuleFunctionOperands


    // $ANTLR start ruleFunctionOperands
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1859:1: ruleFunctionOperands returns [EObject current=null] : ( () ( (lv_operands_1_0= ruleOrExpression ) )? ( ',' ( (lv_operands_3_0= ruleOrExpression ) ) )* ) ;
    public final EObject ruleFunctionOperands() throws RecognitionException {
        EObject current = null;

        EObject lv_operands_1_0 = null;

        EObject lv_operands_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1864:6: ( ( () ( (lv_operands_1_0= ruleOrExpression ) )? ( ',' ( (lv_operands_3_0= ruleOrExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1865:1: ( () ( (lv_operands_1_0= ruleOrExpression ) )? ( ',' ( (lv_operands_3_0= ruleOrExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1865:1: ( () ( (lv_operands_1_0= ruleOrExpression ) )? ( ',' ( (lv_operands_3_0= ruleOrExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1865:2: () ( (lv_operands_1_0= ruleOrExpression ) )? ( ',' ( (lv_operands_3_0= ruleOrExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1865:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1866:5: 
            {
             
                    temp=factory.create(grammarAccess.getFunctionOperandsAccess().getExpressionAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getFunctionOperandsAccess().getExpressionAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1876:2: ( (lv_operands_1_0= ruleOrExpression ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_ID||LA23_0==RULE_INT||LA23_0==28||LA23_0==31||(LA23_0>=39 && LA23_0<=40)||LA23_0==43||(LA23_0>=45 && LA23_0<=48)) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1877:1: (lv_operands_1_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1877:1: (lv_operands_1_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1878:3: lv_operands_1_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getFunctionOperandsAccess().getOperandsOrExpressionParserRuleCall_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionOperands3274);
                    lv_operands_1_0=ruleOrExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getFunctionOperandsRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"operands",
                    	        		lv_operands_1_0, 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1900:3: ( ',' ( (lv_operands_3_0= ruleOrExpression ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==42) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1900:5: ',' ( (lv_operands_3_0= ruleOrExpression ) )
            	    {
            	    match(input,42,FOLLOW_42_in_ruleFunctionOperands3286); 

            	            createLeafNode(grammarAccess.getFunctionOperandsAccess().getCommaKeyword_2_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1904:1: ( (lv_operands_3_0= ruleOrExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1905:1: (lv_operands_3_0= ruleOrExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1905:1: (lv_operands_3_0= ruleOrExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1906:3: lv_operands_3_0= ruleOrExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFunctionOperandsAccess().getOperandsOrExpressionParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionOperands3307);
            	    lv_operands_3_0=ruleOrExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getFunctionOperandsRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"operands",
            	    	        		lv_operands_3_0, 
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
            	    break loop24;
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
    // $ANTLR end ruleFunctionOperands


    // $ANTLR start entryRuleFunctionCall
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1936:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1937:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1938:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionCallRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall3345);
            iv_ruleFunctionCall=ruleFunctionCall();
            _fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall3355); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1945:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_op_0_0= ruleFunctionName ) ) '(' ( (lv_functionoperands_2_0= ruleFunctionOperands ) ) ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_op_0_0 = null;

        EObject lv_functionoperands_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1950:6: ( ( ( (lv_op_0_0= ruleFunctionName ) ) '(' ( (lv_functionoperands_2_0= ruleFunctionOperands ) ) ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1951:1: ( ( (lv_op_0_0= ruleFunctionName ) ) '(' ( (lv_functionoperands_2_0= ruleFunctionOperands ) ) ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1951:1: ( ( (lv_op_0_0= ruleFunctionName ) ) '(' ( (lv_functionoperands_2_0= ruleFunctionOperands ) ) ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1951:2: ( (lv_op_0_0= ruleFunctionName ) ) '(' ( (lv_functionoperands_2_0= ruleFunctionOperands ) ) ')'
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1951:2: ( (lv_op_0_0= ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1952:1: (lv_op_0_0= ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1952:1: (lv_op_0_0= ruleFunctionName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1953:3: lv_op_0_0= ruleFunctionName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getOpFunctionNameParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleFunctionName_in_ruleFunctionCall3401);
            lv_op_0_0=ruleFunctionName();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFunctionCallRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"op",
            	        		lv_op_0_0, 
            	        		"FunctionName", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,40,FOLLOW_40_in_ruleFunctionCall3411); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1979:1: ( (lv_functionoperands_2_0= ruleFunctionOperands ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1980:1: (lv_functionoperands_2_0= ruleFunctionOperands )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1980:1: (lv_functionoperands_2_0= ruleFunctionOperands )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1981:3: lv_functionoperands_2_0= ruleFunctionOperands
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getFunctionoperandsFunctionOperandsParserRuleCall_2_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleFunctionOperands_in_ruleFunctionCall3432);
            lv_functionoperands_2_0=ruleFunctionOperands();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFunctionCallRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"functionoperands",
            	        		lv_functionoperands_2_0, 
            	        		"FunctionOperands", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,41,FOLLOW_41_in_ruleFunctionCall3442); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_3(), null); 
                

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2015:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2016:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2017:2: iv_ruleLiteral= ruleLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral3478);
            iv_ruleLiteral=ruleLiteral();
            _fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral3488); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2024:1: ruleLiteral returns [EObject current=null] : ( ( () ( (lv_literal_1_0= RULE_INT ) ) ) | this_FunctionCall_2= ruleFunctionCall | ( () ( (lv_name_4_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_name_6_0= ruleTmlExpression ) ) ) | ( () ( (lv_op_8_0= '{' ) ) ( (lv_operands_9_0= ruleOrExpression ) )? ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_op_14_0= 'NULL' ) ) ) | ( () ( (lv_op_16_0= 'TODAY' ) ) ) | ( () ( (lv_op_18_0= 'TRUE' ) ) ) | ( () ( (lv_op_20_0= 'FALSE' ) ) ) ) ;
    public final EObject ruleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_literal_1_0=null;
        Token lv_op_8_0=null;
        Token lv_op_14_0=null;
        Token lv_op_16_0=null;
        Token lv_op_18_0=null;
        Token lv_op_20_0=null;
        EObject this_FunctionCall_2 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        AntlrDatatypeRuleToken lv_name_6_0 = null;

        EObject lv_operands_9_0 = null;

        EObject lv_operands_11_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2029:6: ( ( ( () ( (lv_literal_1_0= RULE_INT ) ) ) | this_FunctionCall_2= ruleFunctionCall | ( () ( (lv_name_4_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_name_6_0= ruleTmlExpression ) ) ) | ( () ( (lv_op_8_0= '{' ) ) ( (lv_operands_9_0= ruleOrExpression ) )? ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_op_14_0= 'NULL' ) ) ) | ( () ( (lv_op_16_0= 'TODAY' ) ) ) | ( () ( (lv_op_18_0= 'TRUE' ) ) ) | ( () ( (lv_op_20_0= 'FALSE' ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:1: ( ( () ( (lv_literal_1_0= RULE_INT ) ) ) | this_FunctionCall_2= ruleFunctionCall | ( () ( (lv_name_4_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_name_6_0= ruleTmlExpression ) ) ) | ( () ( (lv_op_8_0= '{' ) ) ( (lv_operands_9_0= ruleOrExpression ) )? ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_op_14_0= 'NULL' ) ) ) | ( () ( (lv_op_16_0= 'TODAY' ) ) ) | ( () ( (lv_op_18_0= 'TRUE' ) ) ) | ( () ( (lv_op_20_0= 'FALSE' ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:1: ( ( () ( (lv_literal_1_0= RULE_INT ) ) ) | this_FunctionCall_2= ruleFunctionCall | ( () ( (lv_name_4_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_name_6_0= ruleTmlExpression ) ) ) | ( () ( (lv_op_8_0= '{' ) ) ( (lv_operands_9_0= ruleOrExpression ) )? ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_op_14_0= 'NULL' ) ) ) | ( () ( (lv_op_16_0= 'TODAY' ) ) ) | ( () ( (lv_op_18_0= 'TRUE' ) ) ) | ( () ( (lv_op_20_0= 'FALSE' ) ) ) )
            int alt27=9;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt27=1;
                }
                break;
            case RULE_ID:
                {
                alt27=2;
                }
                break;
            case 31:
                {
                alt27=3;
                }
                break;
            case 28:
                {
                alt27=4;
                }
                break;
            case 43:
                {
                alt27=5;
                }
                break;
            case 45:
                {
                alt27=6;
                }
                break;
            case 46:
                {
                alt27=7;
                }
                break;
            case 47:
                {
                alt27=8;
                }
                break;
            case 48:
                {
                alt27=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("2030:1: ( ( () ( (lv_literal_1_0= RULE_INT ) ) ) | this_FunctionCall_2= ruleFunctionCall | ( () ( (lv_name_4_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_name_6_0= ruleTmlExpression ) ) ) | ( () ( (lv_op_8_0= '{' ) ) ( (lv_operands_9_0= ruleOrExpression ) )? ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_op_14_0= 'NULL' ) ) ) | ( () ( (lv_op_16_0= 'TODAY' ) ) ) | ( () ( (lv_op_18_0= 'TRUE' ) ) ) | ( () ( (lv_op_20_0= 'FALSE' ) ) ) )", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:2: ( () ( (lv_literal_1_0= RULE_INT ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:2: ( () ( (lv_literal_1_0= RULE_INT ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:3: () ( (lv_literal_1_0= RULE_INT ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2030:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2031:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2041:2: ( (lv_literal_1_0= RULE_INT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2042:1: (lv_literal_1_0= RULE_INT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2042:1: (lv_literal_1_0= RULE_INT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2043:3: lv_literal_1_0= RULE_INT
                    {
                    lv_literal_1_0=(Token)input.LT(1);
                    match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleLiteral3540); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getLiteralINTTerminalRuleCall_0_1_0(), "literal"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"literal",
                    	        		lv_literal_1_0, 
                    	        		"INT", 
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2067:5: this_FunctionCall_2= ruleFunctionCall
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral3574);
                    this_FunctionCall_2=ruleFunctionCall();
                    _fsp--;

                     
                            current = this_FunctionCall_2; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2076:6: ( () ( (lv_name_4_0= ruleExistsTmlExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2076:6: ( () ( (lv_name_4_0= ruleExistsTmlExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2076:7: () ( (lv_name_4_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2076:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2077:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_2_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_2_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2087:2: ( (lv_name_4_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2088:1: (lv_name_4_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2088:1: (lv_name_4_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2089:3: lv_name_4_0= ruleExistsTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getNameExistsTmlExpressionParserRuleCall_2_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral3610);
                    lv_name_4_0=ruleExistsTmlExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"name",
                    	        		lv_name_4_0, 
                    	        		"ExistsTmlExpression", 
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2112:6: ( () ( (lv_name_6_0= ruleTmlExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2112:6: ( () ( (lv_name_6_0= ruleTmlExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2112:7: () ( (lv_name_6_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2112:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2113:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_3_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_3_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2123:2: ( (lv_name_6_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2124:1: (lv_name_6_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2124:1: (lv_name_6_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2125:3: lv_name_6_0= ruleTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getNameTmlExpressionParserRuleCall_3_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral3648);
                    lv_name_6_0=ruleTmlExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"name",
                    	        		lv_name_6_0, 
                    	        		"TmlExpression", 
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
                case 5 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2148:6: ( () ( (lv_op_8_0= '{' ) ) ( (lv_operands_9_0= ruleOrExpression ) )? ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )* '}' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2148:6: ( () ( (lv_op_8_0= '{' ) ) ( (lv_operands_9_0= ruleOrExpression ) )? ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )* '}' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2148:7: () ( (lv_op_8_0= '{' ) ) ( (lv_operands_9_0= ruleOrExpression ) )? ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )* '}'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2148:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2149:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_4_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_4_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2159:2: ( (lv_op_8_0= '{' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2160:1: (lv_op_8_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2160:1: (lv_op_8_0= '{' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2161:3: lv_op_8_0= '{'
                    {
                    lv_op_8_0=(Token)input.LT(1);
                    match(input,43,FOLLOW_43_in_ruleLiteral3683); 

                            createLeafNode(grammarAccess.getLiteralAccess().getOpLeftCurlyBracketKeyword_4_1_0(), "op"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "op", lv_op_8_0, "{", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2180:2: ( (lv_operands_9_0= ruleOrExpression ) )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==RULE_ID||LA25_0==RULE_INT||LA25_0==28||LA25_0==31||(LA25_0>=39 && LA25_0<=40)||LA25_0==43||(LA25_0>=45 && LA25_0<=48)) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2181:1: (lv_operands_9_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2181:1: (lv_operands_9_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2182:3: lv_operands_9_0= ruleOrExpression
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getOperandsOrExpressionParserRuleCall_4_2_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3717);
                            lv_operands_9_0=ruleOrExpression();
                            _fsp--;


                            	        if (current==null) {
                            	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                            	            associateNodeWithAstElement(currentNode.getParent(), current);
                            	        }
                            	        try {
                            	       		add(
                            	       			current, 
                            	       			"operands",
                            	        		lv_operands_9_0, 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2204:3: ( ',' ( (lv_operands_11_0= ruleOrExpression ) ) )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==42) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2204:5: ',' ( (lv_operands_11_0= ruleOrExpression ) )
                    	    {
                    	    match(input,42,FOLLOW_42_in_ruleLiteral3729); 

                    	            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_4_3_0(), null); 
                    	        
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2208:1: ( (lv_operands_11_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2209:1: (lv_operands_11_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2209:1: (lv_operands_11_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2210:3: lv_operands_11_0= ruleOrExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getOperandsOrExpressionParserRuleCall_4_3_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3750);
                    	    lv_operands_11_0=ruleOrExpression();
                    	    _fsp--;


                    	    	        if (current==null) {
                    	    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	    	        }
                    	    	        try {
                    	    	       		add(
                    	    	       			current, 
                    	    	       			"operands",
                    	    	        		lv_operands_11_0, 
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
                    	    break loop26;
                        }
                    } while (true);

                    match(input,44,FOLLOW_44_in_ruleLiteral3762); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_4(), null); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2237:6: ( () ( (lv_op_14_0= 'NULL' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2237:6: ( () ( (lv_op_14_0= 'NULL' ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2237:7: () ( (lv_op_14_0= 'NULL' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2237:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2238:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_5_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_5_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2248:2: ( (lv_op_14_0= 'NULL' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2249:1: (lv_op_14_0= 'NULL' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2249:1: (lv_op_14_0= 'NULL' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2250:3: lv_op_14_0= 'NULL'
                    {
                    lv_op_14_0=(Token)input.LT(1);
                    match(input,45,FOLLOW_45_in_ruleLiteral3797); 

                            createLeafNode(grammarAccess.getLiteralAccess().getOpNULLKeyword_5_1_0(), "op"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "op", lv_op_14_0, "NULL", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2270:6: ( () ( (lv_op_16_0= 'TODAY' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2270:6: ( () ( (lv_op_16_0= 'TODAY' ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2270:7: () ( (lv_op_16_0= 'TODAY' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2270:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2271:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_6_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_6_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2281:2: ( (lv_op_16_0= 'TODAY' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2282:1: (lv_op_16_0= 'TODAY' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2282:1: (lv_op_16_0= 'TODAY' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2283:3: lv_op_16_0= 'TODAY'
                    {
                    lv_op_16_0=(Token)input.LT(1);
                    match(input,46,FOLLOW_46_in_ruleLiteral3845); 

                            createLeafNode(grammarAccess.getLiteralAccess().getOpTODAYKeyword_6_1_0(), "op"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "op", lv_op_16_0, "TODAY", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2303:6: ( () ( (lv_op_18_0= 'TRUE' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2303:6: ( () ( (lv_op_18_0= 'TRUE' ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2303:7: () ( (lv_op_18_0= 'TRUE' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2303:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2304:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_7_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_7_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2314:2: ( (lv_op_18_0= 'TRUE' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2315:1: (lv_op_18_0= 'TRUE' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2315:1: (lv_op_18_0= 'TRUE' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2316:3: lv_op_18_0= 'TRUE'
                    {
                    lv_op_18_0=(Token)input.LT(1);
                    match(input,47,FOLLOW_47_in_ruleLiteral3893); 

                            createLeafNode(grammarAccess.getLiteralAccess().getOpTRUEKeyword_7_1_0(), "op"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "op", lv_op_18_0, "TRUE", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2336:6: ( () ( (lv_op_20_0= 'FALSE' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2336:6: ( () ( (lv_op_20_0= 'FALSE' ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2336:7: () ( (lv_op_20_0= 'FALSE' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2336:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2337:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_8_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_8_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2347:2: ( (lv_op_20_0= 'FALSE' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2348:1: (lv_op_20_0= 'FALSE' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2348:1: (lv_op_20_0= 'FALSE' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2349:3: lv_op_20_0= 'FALSE'
                    {
                    lv_op_20_0=(Token)input.LT(1);
                    match(input,48,FOLLOW_48_in_ruleLiteral3941); 

                            createLeafNode(grammarAccess.getLiteralAccess().getOpFALSEKeyword_8_1_0(), "op"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "op", lv_op_20_0, "FALSE", lastConsumedNode);
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
    public static final BitSet FOLLOW_11_in_ruleTml120 = new BitSet(new long[]{0x0000000000005010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleTml150 = new BitSet(new long[]{0x0000000000005010L});
    public static final BitSet FOLLOW_12_in_ruleTml163 = new BitSet(new long[]{0x0000000000142000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleTml185 = new BitSet(new long[]{0x0000000000142000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleTml212 = new BitSet(new long[]{0x0000000000142000L});
    public static final BitSet FOLLOW_13_in_ruleTml224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleTml241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression278 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePossibleExpression288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePossibleExpression330 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_rulePossibleExpression345 = new BitSet(new long[]{0x0000000000010020L});
    public static final BitSet FOLLOW_16_in_rulePossibleExpression357 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleExpression_in_rulePossibleExpression378 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_rulePossibleExpression388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage454 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleMessage499 = new BitSet(new long[]{0x0000000000005010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMessage529 = new BitSet(new long[]{0x0000000000005010L});
    public static final BitSet FOLLOW_12_in_ruleMessage542 = new BitSet(new long[]{0x00000000005C0000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMessage564 = new BitSet(new long[]{0x00000000005C0000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMessage591 = new BitSet(new long[]{0x00000000005C0000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMessage618 = new BitSet(new long[]{0x00000000005C0000L});
    public static final BitSet FOLLOW_19_in_ruleMessage630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleMessage647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_entryRuleMap684 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMap694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_ruleMap729 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMap746 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap772 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleMap783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleMap800 = new BitSet(new long[]{0x0000000000740000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMap822 = new BitSet(new long[]{0x0000000000740000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMap849 = new BitSet(new long[]{0x0000000000740000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMap876 = new BitSet(new long[]{0x0000000000740000L});
    public static final BitSet FOLLOW_21_in_ruleMap888 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMap905 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleMap920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty958 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleProperty1003 = new BitSet(new long[]{0x0000000000005010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleProperty1033 = new BitSet(new long[]{0x0000000000005010L});
    public static final BitSet FOLLOW_14_in_ruleProperty1045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleProperty1062 = new BitSet(new long[]{0x0000000001800000L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleProperty1083 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleProperty1094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag1132 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag1142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleExpressionTag1177 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleExpression_in_ruleExpressionTag1198 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleExpressionTag1208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpression_in_entryRuleExpression1244 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpression1254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleExpression1299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement1335 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement1346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement1386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rulePathElement1410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rulePathElement1429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_entryRulePathSequence1470 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathSequence1481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rulePathSequence1519 = new BitSet(new long[]{0x000000002C000010L});
    public static final BitSet FOLLOW_29_in_rulePathSequence1533 = new BitSet(new long[]{0x000000000C000010L});
    public static final BitSet FOLLOW_rulePathElement_in_rulePathSequence1557 = new BitSet(new long[]{0x0000000060000000L});
    public static final BitSet FOLLOW_29_in_rulePathSequence1576 = new BitSet(new long[]{0x000000000C000010L});
    public static final BitSet FOLLOW_rulePathElement_in_rulePathSequence1598 = new BitSet(new long[]{0x0000000060000000L});
    public static final BitSet FOLLOW_30_in_rulePathSequence1618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression1659 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression1670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_ruleTmlExpression1716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression1761 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression1772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleExistsTmlExpression1810 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleExistsTmlExpression1832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression1877 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression1887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression1942 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_32_in_ruleOrExpression1961 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression1995 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression2033 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression2043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression2098 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_ruleAndExpression2117 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression2151 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression2189 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression2199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2254 = new BitSet(new long[]{0x0000000C00000002L});
    public static final BitSet FOLLOW_34_in_ruleEqualityExpression2274 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleEqualityExpression2334 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression2407 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression2417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2472 = new BitSet(new long[]{0x0000003000000002L});
    public static final BitSet FOLLOW_36_in_ruleAdditiveExpression2484 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2505 = new BitSet(new long[]{0x0000003000000002L});
    public static final BitSet FOLLOW_37_in_ruleAdditiveExpression2523 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2544 = new BitSet(new long[]{0x0000003000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression2583 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression2593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2648 = new BitSet(new long[]{0x0000004020000002L});
    public static final BitSet FOLLOW_38_in_ruleMultiplicativeExpression2668 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2702 = new BitSet(new long[]{0x0000004020000002L});
    public static final BitSet FOLLOW_29_in_ruleMultiplicativeExpression2728 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2762 = new BitSet(new long[]{0x0000004020000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression2801 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression2811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleUnaryExpression2864 = new BitSet(new long[]{0x0001E90090000050L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression2962 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression2972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression3028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_rulePrimaryExpression3046 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression3068 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_rulePrimaryExpression3077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName3115 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName3126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName3165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionOperands_in_entryRuleFunctionOperands3209 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionOperands3219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionOperands3274 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_42_in_ruleFunctionOperands3286 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionOperands3307 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall3345 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall3355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_ruleFunctionCall3401 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_ruleFunctionCall3411 = new BitSet(new long[]{0x0001EF8090000050L});
    public static final BitSet FOLLOW_ruleFunctionOperands_in_ruleFunctionCall3432 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleFunctionCall3442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral3478 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral3488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleLiteral3540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral3574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral3610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral3648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_ruleLiteral3683 = new BitSet(new long[]{0x0001FD8090000050L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3717 = new BitSet(new long[]{0x0000140000000000L});
    public static final BitSet FOLLOW_42_in_ruleLiteral3729 = new BitSet(new long[]{0x0001E98090000050L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3750 = new BitSet(new long[]{0x0000140000000000L});
    public static final BitSet FOLLOW_44_in_ruleLiteral3762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleLiteral3797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ruleLiteral3845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_ruleLiteral3893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_ruleLiteral3941 = new BitSet(new long[]{0x0000000000000002L});

}