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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_ATTRIBUTESTRING", "RULE_INT", "RULE_LITERALSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'<navascript'", "'>'", "'</navascript>'", "'/>'", "'='", "'\"='", "';\"'", "'<message'", "'</message>'", "'<map.'", "'</map.'", "'<property'", "'</property>'", "'<expression>'", "'</expression>'", "'.'", "'..'", "'['", "'/'", "']'", "'?'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'('", "')'", "'FORALL'", "','", "'{'", "'}'", "'NULL'", "'TODAY'", "'TRUE'", "'FALSE'"
    };
    public static final int RULE_ATTRIBUTESTRING=5;
    public static final int RULE_ID=4;
    public static final int RULE_INT=6;
    public static final int RULE_LITERALSTRING=7;
    public static final int RULE_WS=10;
    public static final int RULE_SL_COMMENT=9;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=8;

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:86:1: ruleTml returns [EObject current=null] : ( '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' ) ) ;
    public final EObject ruleTml() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:91:6: ( ( '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:1: ( '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:92:3: '<navascript' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' )
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:3: ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' )
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
                    new NoViableAltException("131:3: ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' ) | '/>' )", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:4: ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:4: ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:131:6: '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</navascript>'
                    {
                    match(input,12,FOLLOW_12_in_ruleTml163); 

                            createLeafNode(grammarAccess.getTmlAccess().getGreaterThanSignKeyword_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:2: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:2: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:136:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:136:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:137:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getTmlAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleTml185);
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
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleTml212);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:210:1: rulePossibleExpression returns [EObject current=null] : ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) ) ) ;
    public final EObject rulePossibleExpression() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token lv_value_5_0=null;
        EObject lv_expressionValue_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:215:6: ( ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:1: ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:1: ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:2: ( (lv_key_0_0= RULE_ID ) ) '=' ( ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) )
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
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:1: ( ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) )
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
                    new NoViableAltException("244:1: ( ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) )", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:2: ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:2: ( '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:4: '\"=' ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"'
                    {
                    match(input,16,FOLLOW_16_in_rulePossibleExpression357); 

                            createLeafNode(grammarAccess.getPossibleExpressionAccess().getQuotationMarkEqualsSignKeyword_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:248:1: ( (lv_expressionValue_3_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:249:1: (lv_expressionValue_3_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:249:1: (lv_expressionValue_3_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:250:3: lv_expressionValue_3_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_rulePossibleExpression378);
                    lv_expressionValue_3_0=ruleTopLevel();
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
                    	        		"TopLevel", 
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:318:1: ruleMessage returns [EObject current=null] : ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:323:6: ( ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:324:1: ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:324:1: ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:324:3: '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' )
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:363:3: ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' )
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
                    new NoViableAltException("363:3: ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' )", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:363:4: ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:363:4: ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:363:6: '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>'
                    {
                    match(input,12,FOLLOW_12_in_ruleMessage542); 

                            createLeafNode(grammarAccess.getMessageAccess().getGreaterThanSignKeyword_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:367:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:367:2: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:367:2: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:369:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMessage564);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:392:6: ( (lv_children_5_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:392:6: ( (lv_children_5_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:393:1: (lv_children_5_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:393:1: (lv_children_5_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:394:3: lv_children_5_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMessage591);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:417:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:417:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:418:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:418:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:419:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMessage618);
                    	    lv_children_6_0=ruleMap();
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:467:1: ruleMap returns [EObject current=null] : ( () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' ) ) ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token lv_mapName_2_0=null;
        Token lv_mapClosingName_10_0=null;
        EObject lv_attributes_3_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_children_8_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:472:6: ( ( () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:473:1: ( () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:473:1: ( () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:473:2: () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) ) ( '/>' | ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:473:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:474:5: 
            {
             
                    temp=factory.create(grammarAccess.getMapAccess().getMapAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getMapAccess().getMapAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            match(input,20,FOLLOW_20_in_ruleMap738); 

                    createLeafNode(grammarAccess.getMapAccess().getMapKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:488:1: ( (lv_mapName_2_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:489:1: (lv_mapName_2_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:489:1: (lv_mapName_2_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:490:3: lv_mapName_2_0= RULE_ID
            {
            lv_mapName_2_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMap755); 

            			createLeafNode(grammarAccess.getMapAccess().getMapNameIDTerminalRuleCall_2_0(), "mapName"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"mapName",
            	        		lv_mapName_2_0, 
            	        		"ID", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:512:2: ( (lv_attributes_3_0= rulePossibleExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:513:1: (lv_attributes_3_0= rulePossibleExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:513:1: (lv_attributes_3_0= rulePossibleExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:514:3: lv_attributes_3_0= rulePossibleExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap781);
            lv_attributes_3_0=rulePossibleExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:536:2: ( '/>' | ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' ) )
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
                    new NoViableAltException("536:2: ( '/>' | ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' ) )", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:536:4: '/>'
                    {
                    match(input,14,FOLLOW_14_in_ruleMap792); 

                            createLeafNode(grammarAccess.getMapAccess().getSolidusGreaterThanSignKeyword_4_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:541:6: ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:541:6: ( '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:541:8: '>' ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_10_0= RULE_ID ) ) '>'
                    {
                    match(input,12,FOLLOW_12_in_ruleMap809); 

                            createLeafNode(grammarAccess.getMapAccess().getGreaterThanSignKeyword_4_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:545:1: ( ( (lv_children_6_0= ruleMessage ) ) | ( (lv_children_7_0= ruleProperty ) ) | ( (lv_children_8_0= ruleMap ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:545:2: ( (lv_children_6_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:545:2: ( (lv_children_6_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:546:1: (lv_children_6_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:546:1: (lv_children_6_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:547:3: lv_children_6_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_4_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMap831);
                    	    lv_children_6_0=ruleMessage();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:570:6: ( (lv_children_7_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:570:6: ( (lv_children_7_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:571:1: (lv_children_7_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:571:1: (lv_children_7_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:572:3: lv_children_7_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_4_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMap858);
                    	    lv_children_7_0=ruleProperty();
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:595:6: ( (lv_children_8_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:595:6: ( (lv_children_8_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:596:1: (lv_children_8_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:596:1: (lv_children_8_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:597:3: lv_children_8_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_4_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMap885);
                    	    lv_children_8_0=ruleMap();
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

                    match(input,21,FOLLOW_21_in_ruleMap897); 

                            createLeafNode(grammarAccess.getMapAccess().getMapKeyword_4_1_2(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:623:1: ( (lv_mapClosingName_10_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:624:1: (lv_mapClosingName_10_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:624:1: (lv_mapClosingName_10_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:625:3: lv_mapClosingName_10_0= RULE_ID
                    {
                    lv_mapClosingName_10_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMap914); 

                    			createLeafNode(grammarAccess.getMapAccess().getMapClosingNameIDTerminalRuleCall_4_1_3_0(), "mapClosingName"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"mapClosingName",
                    	        		lv_mapClosingName_10_0, 
                    	        		"ID", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,12,FOLLOW_12_in_ruleMap929); 

                            createLeafNode(grammarAccess.getMapAccess().getGreaterThanSignKeyword_4_1_4(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:659:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:660:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:661:2: iv_ruleProperty= ruleProperty EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPropertyRule(), currentNode); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty967);
            iv_ruleProperty=ruleProperty();
            _fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty977); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:668:1: ruleProperty returns [EObject current=null] : ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:673:6: ( ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:674:1: ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:674:1: ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:674:3: '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )
            {
            match(input,22,FOLLOW_22_in_ruleProperty1012); 

                    createLeafNode(grammarAccess.getPropertyAccess().getPropertyKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:678:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:679:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:689:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:690:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:690:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:691:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleProperty1042);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:713:3: ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )
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
                    new NoViableAltException("713:3: ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:713:5: '/>'
                    {
                    match(input,14,FOLLOW_14_in_ruleProperty1054); 

                            createLeafNode(grammarAccess.getPropertyAccess().getSolidusGreaterThanSignKeyword_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:718:6: ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:718:6: ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:718:8: '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>'
                    {
                    match(input,12,FOLLOW_12_in_ruleProperty1071); 

                            createLeafNode(grammarAccess.getPropertyAccess().getGreaterThanSignKeyword_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:722:1: ( (lv_expressionValue_5_0= ruleExpressionTag ) )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==24) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:723:1: (lv_expressionValue_5_0= ruleExpressionTag )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:723:1: (lv_expressionValue_5_0= ruleExpressionTag )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:724:3: lv_expressionValue_5_0= ruleExpressionTag
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getExpressionValueExpressionTagParserRuleCall_3_1_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleExpressionTag_in_ruleProperty1092);
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

                    match(input,23,FOLLOW_23_in_ruleProperty1103); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:758:1: entryRuleExpressionTag returns [EObject current=null] : iv_ruleExpressionTag= ruleExpressionTag EOF ;
    public final EObject entryRuleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:759:2: (iv_ruleExpressionTag= ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:760:2: iv_ruleExpressionTag= ruleExpressionTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag1141);
            iv_ruleExpressionTag=ruleExpressionTag();
            _fsp--;

             current =iv_ruleExpressionTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag1151); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:767:1: ruleExpressionTag returns [EObject current=null] : ( '<expression>' ( (lv_value_1_0= ruleTopLevel ) ) '</expression>' ) ;
    public final EObject ruleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject lv_value_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:772:6: ( ( '<expression>' ( (lv_value_1_0= ruleTopLevel ) ) '</expression>' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:773:1: ( '<expression>' ( (lv_value_1_0= ruleTopLevel ) ) '</expression>' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:773:1: ( '<expression>' ( (lv_value_1_0= ruleTopLevel ) ) '</expression>' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:773:3: '<expression>' ( (lv_value_1_0= ruleTopLevel ) ) '</expression>'
            {
            match(input,24,FOLLOW_24_in_ruleExpressionTag1186); 

                    createLeafNode(grammarAccess.getExpressionTagAccess().getExpressionKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:777:1: ( (lv_value_1_0= ruleTopLevel ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:778:1: (lv_value_1_0= ruleTopLevel )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:778:1: (lv_value_1_0= ruleTopLevel )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:779:3: lv_value_1_0= ruleTopLevel
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getValueTopLevelParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleTopLevel_in_ruleExpressionTag1207);
            lv_value_1_0=ruleTopLevel();
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
            	        		"TopLevel", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,25,FOLLOW_25_in_ruleExpressionTag1217); 

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


    // $ANTLR start entryRuleTopLevel
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:813:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:814:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:815:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTopLevelRule(), currentNode); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel1253);
            iv_ruleTopLevel=ruleTopLevel();
            _fsp--;

             current =iv_ruleTopLevel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel1263); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:822:1: ruleTopLevel returns [EObject current=null] : ( () ( (lv_toplevelExpression_1_0= ruleOrExpression ) ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:827:6: ( ( () ( (lv_toplevelExpression_1_0= ruleOrExpression ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:828:1: ( () ( (lv_toplevelExpression_1_0= ruleOrExpression ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:828:1: ( () ( (lv_toplevelExpression_1_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:828:2: () ( (lv_toplevelExpression_1_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:828:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:829:5: 
            {
             
                    temp=factory.create(grammarAccess.getTopLevelAccess().getTopLevelAction_0().getType().getClassifier());
                    current = temp; 
                    temp = null;
                    CompositeNode newNode = createCompositeNode(grammarAccess.getTopLevelAccess().getTopLevelAction_0(), currentNode.getParent());
                newNode.getChildren().add(currentNode);
                moveLookaheadInfo(currentNode, newNode);
                currentNode = newNode; 
                    associateNodeWithAstElement(currentNode, current); 
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:839:2: ( (lv_toplevelExpression_1_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:840:1: (lv_toplevelExpression_1_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:840:1: (lv_toplevelExpression_1_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:841:3: lv_toplevelExpression_1_0= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel1318);
            lv_toplevelExpression_1_0=ruleOrExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getTopLevelRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"toplevelExpression",
            	        		lv_toplevelExpression_1_0, 
            	        		"OrExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:871:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:872:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:873:2: iv_rulePathElement= rulePathElement EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathElementRule(), currentNode); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement1355);
            iv_rulePathElement=rulePathElement();
            _fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement1366); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:880:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:885:6: ( (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:886:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:886:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
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
                    new NoViableAltException("886:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:886:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement1406); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:895:2: kw= '.'
                    {
                    kw=(Token)input.LT(1);
                    match(input,26,FOLLOW_26_in_rulePathElement1430); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:902:2: kw= '..'
                    {
                    kw=(Token)input.LT(1);
                    match(input,27,FOLLOW_27_in_rulePathElement1449); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:915:1: entryRulePathSequence returns [String current=null] : iv_rulePathSequence= rulePathSequence EOF ;
    public final String entryRulePathSequence() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathSequence = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:916:2: (iv_rulePathSequence= rulePathSequence EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:917:2: iv_rulePathSequence= rulePathSequence EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathSequenceRule(), currentNode); 
            pushFollow(FOLLOW_rulePathSequence_in_entryRulePathSequence1490);
            iv_rulePathSequence=rulePathSequence();
            _fsp--;

             current =iv_rulePathSequence.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathSequence1501); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:924:1: rulePathSequence returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' ) ;
    public final AntlrDatatypeRuleToken rulePathSequence() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_PathElement_2 = null;

        AntlrDatatypeRuleToken this_PathElement_4 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:929:6: ( (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:930:1: (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:930:1: (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:931:2: kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']'
            {
            kw=(Token)input.LT(1);
            match(input,28,FOLLOW_28_in_rulePathSequence1539); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getPathSequenceAccess().getLeftSquareBracketKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:936:1: (kw= '/' )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==29) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:937:2: kw= '/'
                    {
                    kw=(Token)input.LT(1);
                    match(input,29,FOLLOW_29_in_rulePathSequence1553); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathSequenceAccess().getSolidusKeyword_1(), null); 
                        

                    }
                    break;

            }

             
                    currentNode=createCompositeNode(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_2(), currentNode); 
                
            pushFollow(FOLLOW_rulePathElement_in_rulePathSequence1577);
            this_PathElement_2=rulePathElement();
            _fsp--;


            		current.merge(this_PathElement_2);
                
             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:953:1: (kw= '/' this_PathElement_4= rulePathElement )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==29) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:954:2: kw= '/' this_PathElement_4= rulePathElement
            	    {
            	    kw=(Token)input.LT(1);
            	    match(input,29,FOLLOW_29_in_rulePathSequence1596); 

            	            current.merge(kw);
            	            createLeafNode(grammarAccess.getPathSequenceAccess().getSolidusKeyword_3_0(), null); 
            	        
            	     
            	            currentNode=createCompositeNode(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_3_1(), currentNode); 
            	        
            	    pushFollow(FOLLOW_rulePathElement_in_rulePathSequence1618);
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
            match(input,30,FOLLOW_30_in_rulePathSequence1638); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:984:1: entryRuleTmlExpression returns [String current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final String entryRuleTmlExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:985:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:986:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression1679);
            iv_ruleTmlExpression=ruleTmlExpression();
            _fsp--;

             current =iv_ruleTmlExpression.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression1690); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:993:1: ruleTmlExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_PathSequence_0= rulePathSequence ;
    public final AntlrDatatypeRuleToken ruleTmlExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_PathSequence_0 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:998:6: (this_PathSequence_0= rulePathSequence )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1000:5: this_PathSequence_0= rulePathSequence
            {
             
                    currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getPathSequenceParserRuleCall(), currentNode); 
                
            pushFollow(FOLLOW_rulePathSequence_in_ruleTmlExpression1736);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1018:1: entryRuleExistsTmlExpression returns [String current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final String entryRuleExistsTmlExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1019:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1020:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExistsTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression1781);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();
            _fsp--;

             current =iv_ruleExistsTmlExpression.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression1792); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1027:1: ruleExistsTmlExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '?' this_TmlExpression_1= ruleTmlExpression ) ;
    public final AntlrDatatypeRuleToken ruleExistsTmlExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_TmlExpression_1 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1032:6: ( (kw= '?' this_TmlExpression_1= ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1033:1: (kw= '?' this_TmlExpression_1= ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1033:1: (kw= '?' this_TmlExpression_1= ruleTmlExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1034:2: kw= '?' this_TmlExpression_1= ruleTmlExpression
            {
            kw=(Token)input.LT(1);
            match(input,31,FOLLOW_31_in_ruleExistsTmlExpression1830); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getQuestionMarkKeyword_0(), null); 
                
             
                    currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getTmlExpressionParserRuleCall_1(), currentNode); 
                
            pushFollow(FOLLOW_ruleTmlExpression_in_ruleExistsTmlExpression1852);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1058:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1059:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1060:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression1897);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression1907); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1067:1: ruleOrExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1072:6: ( ( () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1073:1: ( () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1073:1: ( () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1073:2: () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1073:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1074:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1084:2: ( (lv_parameters_1_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1085:1: (lv_parameters_1_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1085:1: (lv_parameters_1_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1086:3: lv_parameters_1_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression1962);
            lv_parameters_1_0=ruleAndExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_1_0, 
            	        		"AndExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1108:2: ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==32) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1108:3: ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1108:3: ( (lv_operations_2_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1109:1: (lv_operations_2_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1109:1: (lv_operations_2_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1110:3: lv_operations_2_0= 'OR'
            	    {
            	    lv_operations_2_0=(Token)input.LT(1);
            	    match(input,32,FOLLOW_32_in_ruleOrExpression1981); 

            	            createLeafNode(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_2_0_0(), "operations"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operations", lv_operations_2_0, "OR", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1129:2: ( (lv_parameters_3_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1130:1: (lv_parameters_3_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1130:1: (lv_parameters_3_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1131:3: lv_parameters_3_0= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression2015);
            	    lv_parameters_3_0=ruleAndExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_3_0, 
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1161:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1162:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1163:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression2053);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression2063); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1170:1: ruleAndExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1175:6: ( ( () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1176:1: ( () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1176:1: ( () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1176:2: () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1176:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1177:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1187:2: ( (lv_parameters_1_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1188:1: (lv_parameters_1_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1188:1: (lv_parameters_1_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1189:3: lv_parameters_1_0= ruleEqualityExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression2118);
            lv_parameters_1_0=ruleEqualityExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_1_0, 
            	        		"EqualityExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1211:2: ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==33) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1211:3: ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1211:3: ( (lv_operations_2_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1212:1: (lv_operations_2_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1212:1: (lv_operations_2_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1213:3: lv_operations_2_0= 'AND'
            	    {
            	    lv_operations_2_0=(Token)input.LT(1);
            	    match(input,33,FOLLOW_33_in_ruleAndExpression2137); 

            	            createLeafNode(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_2_0_0(), "operations"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operations", lv_operations_2_0, "AND", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1232:2: ( (lv_parameters_3_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1233:1: (lv_parameters_3_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1233:1: (lv_parameters_3_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1234:3: lv_parameters_3_0= ruleEqualityExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression2171);
            	    lv_parameters_3_0=ruleEqualityExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_3_0, 
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1264:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1265:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1266:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEqualityExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression2209);
            iv_ruleEqualityExpression=ruleEqualityExpression();
            _fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression2219); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1273:1: ruleEqualityExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        Token lv_operations_4_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1278:6: ( ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1279:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1279:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1279:2: () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1279:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1280:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1290:2: ( (lv_parameters_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1291:1: (lv_parameters_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1291:1: (lv_parameters_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1292:3: lv_parameters_1_0= ruleAdditiveExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2274);
            lv_parameters_1_0=ruleAdditiveExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:2: ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )?
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:3: ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:3: ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:4: ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:4: ( (lv_operations_2_0= '==' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1315:1: (lv_operations_2_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1315:1: (lv_operations_2_0= '==' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1316:3: lv_operations_2_0= '=='
                    {
                    lv_operations_2_0=(Token)input.LT(1);
                    match(input,34,FOLLOW_34_in_ruleEqualityExpression2294); 

                            createLeafNode(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_2_0_0_0(), "operations"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "operations", lv_operations_2_0, "==", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1335:2: ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1336:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1336:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1337:3: lv_parameters_3_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2328);
                    lv_parameters_3_0=ruleAdditiveExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:6: ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:6: ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:7: ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1360:7: ( (lv_operations_4_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1361:1: (lv_operations_4_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1361:1: (lv_operations_4_0= '!=' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1362:3: lv_operations_4_0= '!='
                    {
                    lv_operations_4_0=(Token)input.LT(1);
                    match(input,35,FOLLOW_35_in_ruleEqualityExpression2354); 

                            createLeafNode(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_2_1_0_0(), "operations"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "operations", lv_operations_4_0, "!=", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1381:2: ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1382:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1382:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1383:3: lv_parameters_5_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2388);
                    lv_parameters_5_0=ruleAdditiveExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getEqualityExpressionRule().getType().getClassifier());
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1413:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1414:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1415:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAdditiveExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression2427);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();
            _fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression2437); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1422:1: ruleAdditiveExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1427:6: ( ( () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1428:1: ( () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1428:1: ( () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1428:2: () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1428:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1429:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1439:2: ( (lv_parameters_1_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1440:1: (lv_parameters_1_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1440:1: (lv_parameters_1_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1441:3: lv_parameters_1_0= ruleMultiplicativeExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2492);
            lv_parameters_1_0=ruleMultiplicativeExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_1_0, 
            	        		"MultiplicativeExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1463:2: ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )*
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1463:3: ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1463:3: ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1463:5: '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,36,FOLLOW_36_in_ruleAdditiveExpression2504); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_2_0_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1467:1: ( (lv_parameters_3_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1468:1: (lv_parameters_3_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1468:1: (lv_parameters_3_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1469:3: lv_parameters_3_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2525);
            	    lv_parameters_3_0=ruleMultiplicativeExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_3_0, 
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1492:6: ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1492:6: ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1492:8: '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,37,FOLLOW_37_in_ruleAdditiveExpression2543); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_2_1_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1496:1: ( (lv_parameters_5_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1497:1: (lv_parameters_5_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1497:1: (lv_parameters_5_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1498:3: lv_parameters_5_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2564);
            	    lv_parameters_5_0=ruleMultiplicativeExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAdditiveExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_5_0, 
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1528:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1529:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1530:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression2603);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();
            _fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression2613); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1537:1: ruleMultiplicativeExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        Token lv_operations_4_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1542:6: ( ( () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1543:1: ( () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1543:1: ( () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1543:2: () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1543:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1544:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1554:2: ( (lv_parameters_1_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1555:1: (lv_parameters_1_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1555:1: (lv_parameters_1_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1556:3: lv_parameters_1_0= ruleUnaryExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2668);
            lv_parameters_1_0=ruleUnaryExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"parameters",
            	        		lv_parameters_1_0, 
            	        		"UnaryExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1578:2: ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )*
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1578:3: ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1578:3: ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1578:4: ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1578:4: ( (lv_operations_2_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1579:1: (lv_operations_2_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1579:1: (lv_operations_2_0= '*' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1580:3: lv_operations_2_0= '*'
            	    {
            	    lv_operations_2_0=(Token)input.LT(1);
            	    match(input,38,FOLLOW_38_in_ruleMultiplicativeExpression2688); 

            	            createLeafNode(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_2_0_0_0(), "operations"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operations", lv_operations_2_0, "*", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1599:2: ( (lv_parameters_3_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1600:1: (lv_parameters_3_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1600:1: (lv_parameters_3_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1601:3: lv_parameters_3_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2722);
            	    lv_parameters_3_0=ruleUnaryExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_3_0, 
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:6: ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:6: ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:7: ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1624:7: ( (lv_operations_4_0= '/' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1625:1: (lv_operations_4_0= '/' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1625:1: (lv_operations_4_0= '/' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1626:3: lv_operations_4_0= '/'
            	    {
            	    lv_operations_4_0=(Token)input.LT(1);
            	    match(input,29,FOLLOW_29_in_ruleMultiplicativeExpression2748); 

            	            createLeafNode(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_2_1_0_0(), "operations"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operations", lv_operations_4_0, "/", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1645:2: ( (lv_parameters_5_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1646:1: (lv_parameters_5_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1646:1: (lv_parameters_5_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1647:3: lv_parameters_5_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2782);
            	    lv_parameters_5_0=ruleUnaryExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        try {
            	    	       		add(
            	    	       			current, 
            	    	       			"parameters",
            	    	        		lv_parameters_5_0, 
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1677:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1678:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1679:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getUnaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression2821);
            iv_ruleUnaryExpression=ruleUnaryExpression();
            _fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression2831); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1686:1: ruleUnaryExpression returns [EObject current=null] : ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_2_0 = null;

        EObject this_PrimaryExpression_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1691:6: ( ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1692:1: ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1692:1: ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==39) ) {
                alt21=1;
            }
            else if ( (LA21_0==RULE_ID||(LA21_0>=RULE_INT && LA21_0<=RULE_LITERALSTRING)||LA21_0==28||LA21_0==31||LA21_0==40||LA21_0==42||LA21_0==44||(LA21_0>=46 && LA21_0<=49)) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1692:1: ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1692:2: ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1692:2: ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1692:3: () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1692:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1693:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1703:2: ( (lv_operations_1_0= '!' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1704:1: (lv_operations_1_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1704:1: (lv_operations_1_0= '!' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1705:3: lv_operations_1_0= '!'
                    {
                    lv_operations_1_0=(Token)input.LT(1);
                    match(input,39,FOLLOW_39_in_ruleUnaryExpression2884); 

                            createLeafNode(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_1_0(), "operations"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getUnaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "operations", lv_operations_1_0, "!", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1724:2: ( (lv_parameters_2_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1725:1: (lv_parameters_2_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1725:1: (lv_parameters_2_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1726:3: lv_parameters_2_0= rulePrimaryExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2918);
                    lv_parameters_2_0=rulePrimaryExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getUnaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_2_0, 
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1750:5: this_PrimaryExpression_3= rulePrimaryExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2947);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1766:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1767:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1768:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPrimaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression2982);
            iv_rulePrimaryExpression=rulePrimaryExpression();
            _fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression2992); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1775:1: rulePrimaryExpression returns [EObject current=null] : ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1780:6: ( ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1781:1: ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1781:1: ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_ID||(LA22_0>=RULE_INT && LA22_0<=RULE_LITERALSTRING)||LA22_0==28||LA22_0==31||LA22_0==42||LA22_0==44||(LA22_0>=46 && LA22_0<=49)) ) {
                alt22=1;
            }
            else if ( (LA22_0==40) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1781:1: ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) )", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1781:2: ( () ( (lv_parameters_1_0= ruleLiteral ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1781:2: ( () ( (lv_parameters_1_0= ruleLiteral ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1781:3: () ( (lv_parameters_1_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1781:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1782:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1792:2: ( (lv_parameters_1_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1793:1: (lv_parameters_1_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1793:1: (lv_parameters_1_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1794:3: lv_parameters_1_0= ruleLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression3048);
                    lv_parameters_1_0=ruleLiteral();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPrimaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_1_0, 
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1817:6: ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1817:6: ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1817:8: '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')'
                    {
                    match(input,40,FOLLOW_40_in_rulePrimaryExpression3066); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1821:1: ( (lv_parameters_3_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1822:1: (lv_parameters_3_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1822:1: (lv_parameters_3_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1823:3: lv_parameters_3_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression3087);
                    lv_parameters_3_0=ruleOrExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getPrimaryExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"parameters",
                    	        		lv_parameters_3_0, 
                    	        		"OrExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    match(input,41,FOLLOW_41_in_rulePrimaryExpression3097); 

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


    // $ANTLR start entryRuleFunctionCall
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1857:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1858:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1859:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionCallRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall3134);
            iv_ruleFunctionCall=ruleFunctionCall();
            _fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall3144); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1866:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1871:6: ( ( ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:1: ( ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:1: ( ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:2: ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')'
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:2: ( (lv_name_0_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1873:1: (lv_name_0_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1873:1: (lv_name_0_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1874:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionCall3186); 

            			createLeafNode(grammarAccess.getFunctionCallAccess().getNameIDTerminalRuleCall_0_0(), "name"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFunctionCallRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"name",
            	        		lv_name_0_0, 
            	        		"ID", 
            	        		lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            match(input,40,FOLLOW_40_in_ruleFunctionCall3201); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1900:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1901:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1901:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1902:3: RULE_ID
            {

            			if (current==null) {
            	            current = factory.create(grammarAccess.getFunctionCallRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
                    
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionCall3219); 

            		createLeafNode(grammarAccess.getFunctionCallAccess().getOperandsFunctionOperandsCrossReference_2_0(), "operands"); 
            	

            }


            }

            match(input,41,FOLLOW_41_in_ruleFunctionCall3229); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1928:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1929:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1930:2: iv_ruleLiteral= ruleLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral3267);
            iv_ruleLiteral=ruleLiteral();
            _fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral3277); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1937:1: ruleLiteral returns [EObject current=null] : ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) ) ;
    public final EObject ruleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_valueString_1_0=null;
        Token lv_valueString_3_0=null;
        Token lv_operations_5_0=null;
        Token lv_valueString_7_0=null;
        Token lv_expressionType_17_0=null;
        Token lv_elements_23_0=null;
        Token lv_elements_25_0=null;
        Token lv_elements_27_0=null;
        Token lv_elements_29_0=null;
        EObject lv_parameters_9_0 = null;

        EObject this_FunctionCall_11 = null;

        AntlrDatatypeRuleToken lv_elements_13_0 = null;

        AntlrDatatypeRuleToken lv_elements_15_0 = null;

        EObject lv_parameters_18_0 = null;

        EObject lv_parameters_20_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1942:6: ( ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1943:1: ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1943:1: ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) )
            int alt25=11;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt25=1;
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt25=2;
                }
                break;
            case 42:
                {
                alt25=3;
                }
                break;
            case RULE_ID:
                {
                alt25=4;
                }
                break;
            case 31:
                {
                alt25=5;
                }
                break;
            case 28:
                {
                alt25=6;
                }
                break;
            case 44:
                {
                alt25=7;
                }
                break;
            case 46:
                {
                alt25=8;
                }
                break;
            case 47:
                {
                alt25=9;
                }
                break;
            case 48:
                {
                alt25=10;
                }
                break;
            case 49:
                {
                alt25=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("1943:1: ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) )", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1943:2: ( () ( (lv_valueString_1_0= RULE_INT ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1943:2: ( () ( (lv_valueString_1_0= RULE_INT ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1943:3: () ( (lv_valueString_1_0= RULE_INT ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1943:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1944:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1954:2: ( (lv_valueString_1_0= RULE_INT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1955:1: (lv_valueString_1_0= RULE_INT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1955:1: (lv_valueString_1_0= RULE_INT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1956:3: lv_valueString_1_0= RULE_INT
                    {
                    lv_valueString_1_0=(Token)input.LT(1);
                    match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleLiteral3329); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getValueStringINTTerminalRuleCall_0_1_0(), "valueString"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"valueString",
                    	        		lv_valueString_1_0, 
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1979:6: ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1979:6: ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1979:7: () ( (lv_valueString_3_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1979:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1980:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_1_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_1_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1990:2: ( (lv_valueString_3_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1991:1: (lv_valueString_3_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1991:1: (lv_valueString_3_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1992:3: lv_valueString_3_0= RULE_LITERALSTRING
                    {
                    lv_valueString_3_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3368); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_1_0(), "valueString"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"valueString",
                    	        		lv_valueString_3_0, 
                    	        		"LITERALSTRING", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2015:6: ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2015:6: ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2015:7: () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2015:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2016:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2026:2: ( (lv_operations_5_0= 'FORALL' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2027:1: (lv_operations_5_0= 'FORALL' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2027:1: (lv_operations_5_0= 'FORALL' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2028:3: lv_operations_5_0= 'FORALL'
                    {
                    lv_operations_5_0=(Token)input.LT(1);
                    match(input,42,FOLLOW_42_in_ruleLiteral3408); 

                            createLeafNode(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_1_0(), "operations"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "operations", lv_operations_5_0, "FORALL", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,40,FOLLOW_40_in_ruleLiteral3431); 

                            createLeafNode(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_2(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2051:1: ( (lv_valueString_7_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2052:1: (lv_valueString_7_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2052:1: (lv_valueString_7_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2053:3: lv_valueString_7_0= RULE_LITERALSTRING
                    {
                    lv_valueString_7_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3448); 

                    			createLeafNode(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_3_0(), "valueString"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"valueString",
                    	        		lv_valueString_7_0, 
                    	        		"LITERALSTRING", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,43,FOLLOW_43_in_ruleLiteral3463); 

                            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_2_4(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2079:1: ( (lv_parameters_9_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2080:1: (lv_parameters_9_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2080:1: (lv_parameters_9_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2081:3: lv_parameters_9_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_5_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3484);
                    lv_parameters_9_0=ruleOrExpression();
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
                    	        		"OrExpression", 
                    	        		currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }


                    }

                    match(input,41,FOLLOW_41_in_ruleLiteral3494); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_6(), null); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2109:5: this_FunctionCall_11= ruleFunctionCall
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral3523);
                    this_FunctionCall_11=ruleFunctionCall();
                    _fsp--;

                     
                            current = this_FunctionCall_11; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2118:6: ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2118:6: ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2118:7: () ( (lv_elements_13_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2118:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2119:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2129:2: ( (lv_elements_13_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2130:1: (lv_elements_13_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2130:1: (lv_elements_13_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2131:3: lv_elements_13_0= ruleExistsTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getElementsExistsTmlExpressionParserRuleCall_4_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral3559);
                    lv_elements_13_0=ruleExistsTmlExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"elements",
                    	        		lv_elements_13_0, 
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
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2154:6: ( () ( (lv_elements_15_0= ruleTmlExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2154:6: ( () ( (lv_elements_15_0= ruleTmlExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2154:7: () ( (lv_elements_15_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2154:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2155:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2165:2: ( (lv_elements_15_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2166:1: (lv_elements_15_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2166:1: (lv_elements_15_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2167:3: lv_elements_15_0= ruleTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getElementsTmlExpressionParserRuleCall_5_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral3597);
                    lv_elements_15_0=ruleTmlExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        try {
                    	       		add(
                    	       			current, 
                    	       			"elements",
                    	        		lv_elements_15_0, 
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
                case 7 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2190:6: ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2190:6: ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2190:7: () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2190:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2191:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2201:2: ( (lv_expressionType_17_0= '{' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2202:1: (lv_expressionType_17_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2202:1: (lv_expressionType_17_0= '{' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2203:3: lv_expressionType_17_0= '{'
                    {
                    lv_expressionType_17_0=(Token)input.LT(1);
                    match(input,44,FOLLOW_44_in_ruleLiteral3632); 

                            createLeafNode(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_6_1_0(), "expressionType"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "expressionType", lv_expressionType_17_0, "{", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2222:2: ( (lv_parameters_18_0= ruleOrExpression ) )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==RULE_ID||(LA23_0>=RULE_INT && LA23_0<=RULE_LITERALSTRING)||LA23_0==28||LA23_0==31||(LA23_0>=39 && LA23_0<=40)||LA23_0==42||LA23_0==44||(LA23_0>=46 && LA23_0<=49)) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2223:1: (lv_parameters_18_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2223:1: (lv_parameters_18_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2224:3: lv_parameters_18_0= ruleOrExpression
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_2_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3666);
                            lv_parameters_18_0=ruleOrExpression();
                            _fsp--;


                            	        if (current==null) {
                            	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                            	            associateNodeWithAstElement(currentNode.getParent(), current);
                            	        }
                            	        try {
                            	       		add(
                            	       			current, 
                            	       			"parameters",
                            	        		lv_parameters_18_0, 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2246:3: ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==43) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2246:5: ',' ( (lv_parameters_20_0= ruleOrExpression ) )
                    	    {
                    	    match(input,43,FOLLOW_43_in_ruleLiteral3678); 

                    	            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_6_3_0(), null); 
                    	        
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2250:1: ( (lv_parameters_20_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2251:1: (lv_parameters_20_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2251:1: (lv_parameters_20_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2252:3: lv_parameters_20_0= ruleOrExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_3_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3699);
                    	    lv_parameters_20_0=ruleOrExpression();
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

                    match(input,45,FOLLOW_45_in_ruleLiteral3711); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_6_4(), null); 
                        

                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2279:6: ( () ( (lv_elements_23_0= 'NULL' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2279:6: ( () ( (lv_elements_23_0= 'NULL' ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2279:7: () ( (lv_elements_23_0= 'NULL' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2279:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2280:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2290:2: ( (lv_elements_23_0= 'NULL' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2291:1: (lv_elements_23_0= 'NULL' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2291:1: (lv_elements_23_0= 'NULL' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2292:3: lv_elements_23_0= 'NULL'
                    {
                    lv_elements_23_0=(Token)input.LT(1);
                    match(input,46,FOLLOW_46_in_ruleLiteral3746); 

                            createLeafNode(grammarAccess.getLiteralAccess().getElementsNULLKeyword_7_1_0(), "elements"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "elements", lv_elements_23_0, "NULL", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2312:6: ( () ( (lv_elements_25_0= 'TODAY' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2312:6: ( () ( (lv_elements_25_0= 'TODAY' ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2312:7: () ( (lv_elements_25_0= 'TODAY' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2312:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2313:5: 
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2323:2: ( (lv_elements_25_0= 'TODAY' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2324:1: (lv_elements_25_0= 'TODAY' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2324:1: (lv_elements_25_0= 'TODAY' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2325:3: lv_elements_25_0= 'TODAY'
                    {
                    lv_elements_25_0=(Token)input.LT(1);
                    match(input,47,FOLLOW_47_in_ruleLiteral3794); 

                            createLeafNode(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_8_1_0(), "elements"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "elements", lv_elements_25_0, "TODAY", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2345:6: ( () ( (lv_elements_27_0= 'TRUE' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2345:6: ( () ( (lv_elements_27_0= 'TRUE' ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2345:7: () ( (lv_elements_27_0= 'TRUE' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2345:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2346:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_9_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_9_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2356:2: ( (lv_elements_27_0= 'TRUE' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:1: (lv_elements_27_0= 'TRUE' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:1: (lv_elements_27_0= 'TRUE' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2358:3: lv_elements_27_0= 'TRUE'
                    {
                    lv_elements_27_0=(Token)input.LT(1);
                    match(input,48,FOLLOW_48_in_ruleLiteral3842); 

                            createLeafNode(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_9_1_0(), "elements"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "elements", lv_elements_27_0, "TRUE", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 11 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2378:6: ( () ( (lv_elements_29_0= 'FALSE' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2378:6: ( () ( (lv_elements_29_0= 'FALSE' ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2378:7: () ( (lv_elements_29_0= 'FALSE' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2378:7: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2379:5: 
                    {
                     
                            temp=factory.create(grammarAccess.getLiteralAccess().getExpressionAction_10_0().getType().getClassifier());
                            current = temp; 
                            temp = null;
                            CompositeNode newNode = createCompositeNode(grammarAccess.getLiteralAccess().getExpressionAction_10_0(), currentNode.getParent());
                        newNode.getChildren().add(currentNode);
                        moveLookaheadInfo(currentNode, newNode);
                        currentNode = newNode; 
                            associateNodeWithAstElement(currentNode, current); 
                        

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2389:2: ( (lv_elements_29_0= 'FALSE' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2390:1: (lv_elements_29_0= 'FALSE' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2390:1: (lv_elements_29_0= 'FALSE' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2391:3: lv_elements_29_0= 'FALSE'
                    {
                    lv_elements_29_0=(Token)input.LT(1);
                    match(input,49,FOLLOW_49_in_ruleLiteral3890); 

                            createLeafNode(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_10_1_0(), "elements"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getLiteralRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		add(current, "elements", lv_elements_29_0, "FALSE", lastConsumedNode);
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
    public static final BitSet FOLLOW_16_in_rulePossibleExpression357 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleTopLevel_in_rulePossibleExpression378 = new BitSet(new long[]{0x0000000000020000L});
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
    public static final BitSet FOLLOW_20_in_ruleMap738 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMap755 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap781 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleMap792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleMap809 = new BitSet(new long[]{0x0000000000740000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMap831 = new BitSet(new long[]{0x0000000000740000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMap858 = new BitSet(new long[]{0x0000000000740000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMap885 = new BitSet(new long[]{0x0000000000740000L});
    public static final BitSet FOLLOW_21_in_ruleMap897 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMap914 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleMap929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty967 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleProperty1012 = new BitSet(new long[]{0x0000000000005010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleProperty1042 = new BitSet(new long[]{0x0000000000005010L});
    public static final BitSet FOLLOW_14_in_ruleProperty1054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleProperty1071 = new BitSet(new long[]{0x0000000001800000L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleProperty1092 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleProperty1103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag1141 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag1151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleExpressionTag1186 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleExpressionTag1207 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleExpressionTag1217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel1253 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel1263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel1318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement1355 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement1366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement1406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rulePathElement1430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rulePathElement1449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_entryRulePathSequence1490 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathSequence1501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rulePathSequence1539 = new BitSet(new long[]{0x000000002C000010L});
    public static final BitSet FOLLOW_29_in_rulePathSequence1553 = new BitSet(new long[]{0x000000000C000010L});
    public static final BitSet FOLLOW_rulePathElement_in_rulePathSequence1577 = new BitSet(new long[]{0x0000000060000000L});
    public static final BitSet FOLLOW_29_in_rulePathSequence1596 = new BitSet(new long[]{0x000000000C000010L});
    public static final BitSet FOLLOW_rulePathElement_in_rulePathSequence1618 = new BitSet(new long[]{0x0000000060000000L});
    public static final BitSet FOLLOW_30_in_rulePathSequence1638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression1679 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression1690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_ruleTmlExpression1736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression1781 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression1792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleExistsTmlExpression1830 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleExistsTmlExpression1852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression1897 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression1907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression1962 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_32_in_ruleOrExpression1981 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression2015 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression2053 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression2063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression2118 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_ruleAndExpression2137 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression2171 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression2209 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression2219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2274 = new BitSet(new long[]{0x0000000C00000002L});
    public static final BitSet FOLLOW_34_in_ruleEqualityExpression2294 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleEqualityExpression2354 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression2427 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression2437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2492 = new BitSet(new long[]{0x0000003000000002L});
    public static final BitSet FOLLOW_36_in_ruleAdditiveExpression2504 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2525 = new BitSet(new long[]{0x0000003000000002L});
    public static final BitSet FOLLOW_37_in_ruleAdditiveExpression2543 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2564 = new BitSet(new long[]{0x0000003000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression2603 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression2613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2668 = new BitSet(new long[]{0x0000004020000002L});
    public static final BitSet FOLLOW_38_in_ruleMultiplicativeExpression2688 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2722 = new BitSet(new long[]{0x0000004020000002L});
    public static final BitSet FOLLOW_29_in_ruleMultiplicativeExpression2748 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2782 = new BitSet(new long[]{0x0000004020000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression2821 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression2831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleUnaryExpression2884 = new BitSet(new long[]{0x0003D500900000D0L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression2982 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression2992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression3048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_rulePrimaryExpression3066 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression3087 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_rulePrimaryExpression3097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall3134 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall3144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionCall3186 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_ruleFunctionCall3201 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionCall3219 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleFunctionCall3229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral3267 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral3277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleLiteral3329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_ruleLiteral3408 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_ruleLiteral3431 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3448 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_ruleLiteral3463 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3484 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleLiteral3494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral3523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral3559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral3597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ruleLiteral3632 = new BitSet(new long[]{0x0003FD80900000D0L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3666 = new BitSet(new long[]{0x0000280000000000L});
    public static final BitSet FOLLOW_43_in_ruleLiteral3678 = new BitSet(new long[]{0x0003D580900000D0L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3699 = new BitSet(new long[]{0x0000280000000000L});
    public static final BitSet FOLLOW_45_in_ruleLiteral3711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ruleLiteral3746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_ruleLiteral3794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_ruleLiteral3842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_ruleLiteral3890 = new BitSet(new long[]{0x0000000000000002L});

}