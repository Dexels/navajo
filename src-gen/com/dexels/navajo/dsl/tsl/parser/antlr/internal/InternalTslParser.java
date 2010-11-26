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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_QUOTEQ", "RULE_ATTRIBUTESTRING", "RULE_EMPTYSTRING", "RULE_INT", "RULE_LITERALSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'<navascript'", "'>'", "'</navascript>'", "'/>'", "'='", "';\"'", "'<message'", "'</message>'", "'</map.'", "'<map.'", "'<property'", "'</property>'", "'<expression>'", "'</expression>'", "'.'", "'..'", "'['", "'/'", "']'", "'?'", "'$'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'('", "')'", "','", "'FORALL'", "'{'", "'}'", "'NULL'", "'TODAY'", "'TRUE'", "'FALSE'"
    };
    public static final int RULE_ATTRIBUTESTRING=6;
    public static final int RULE_ID=4;
    public static final int RULE_EMPTYSTRING=7;
    public static final int RULE_QUOTEQ=5;
    public static final int RULE_INT=8;
    public static final int RULE_LITERALSTRING=9;
    public static final int RULE_WS=12;
    public static final int RULE_SL_COMMENT=11;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=10;

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
            match(input,13,FOLLOW_13_in_ruleTml120); 

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

            if ( (LA3_0==14) ) {
                alt3=1;
            }
            else if ( (LA3_0==16) ) {
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
                    match(input,14,FOLLOW_14_in_ruleTml163); 

                            createLeafNode(grammarAccess.getTmlAccess().getGreaterThanSignKeyword_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleMap ) ) )*
                    loop2:
                    do {
                        int alt2=3;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==19) ) {
                            alt2=1;
                        }
                        else if ( (LA2_0==22) ) {
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

                    match(input,15,FOLLOW_15_in_ruleTml224); 

                            createLeafNode(grammarAccess.getTmlAccess().getNavascriptKeyword_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:189:7: '/>'
                    {
                    match(input,16,FOLLOW_16_in_ruleTml241); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:210:1: rulePossibleExpression returns [EObject current=null] : ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) ) ;
    public final EObject rulePossibleExpression() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token lv_value_5_0=null;
        EObject lv_expressionValue_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:215:6: ( ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:1: ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:1: ( ( (lv_key_0_0= RULE_ID ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:216:2: ( (lv_key_0_0= RULE_ID ) ) '=' ( ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )
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

            match(input,17,FOLLOW_17_in_rulePossibleExpression345); 

                    createLeafNode(grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )
            int alt4=3;
            switch ( input.LA(1) ) {
            case RULE_QUOTEQ:
                {
                alt4=1;
                }
                break;
            case RULE_ATTRIBUTESTRING:
                {
                alt4=2;
                }
                break;
            case RULE_EMPTYSTRING:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("244:1: ( ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' ) | ( (lv_value_5_0= RULE_ATTRIBUTESTRING ) ) | RULE_EMPTYSTRING )", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:2: ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:2: ( RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:244:3: RULE_QUOTEQ ( (lv_expressionValue_3_0= ruleTopLevel ) ) ';\"'
                    {
                    match(input,RULE_QUOTEQ,FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression356); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getQUOTEQTerminalRuleCall_2_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:248:1: ( (lv_expressionValue_3_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:249:1: (lv_expressionValue_3_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:249:1: (lv_expressionValue_3_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:250:3: lv_expressionValue_3_0= ruleTopLevel
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_rulePossibleExpression376);
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

                    match(input,18,FOLLOW_18_in_rulePossibleExpression386); 

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
                    match(input,RULE_ATTRIBUTESTRING,FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression410); 

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
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:302:6: RULE_EMPTYSTRING
                    {
                    match(input,RULE_EMPTYSTRING,FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression430); 
                     
                        createLeafNode(grammarAccess.getPossibleExpressionAccess().getEMPTYSTRINGTerminalRuleCall_2_2(), null); 
                        

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:314:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:315:2: (iv_ruleMessage= ruleMessage EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:316:2: iv_ruleMessage= ruleMessage EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMessageRule(), currentNode); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage466);
            iv_ruleMessage=ruleMessage();
            _fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage476); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:323:1: ruleMessage returns [EObject current=null] : ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:328:6: ( ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:329:1: ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:329:1: ( '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:329:3: '<message' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' )
            {
            match(input,19,FOLLOW_19_in_ruleMessage511); 

                    createLeafNode(grammarAccess.getMessageAccess().getMessageKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:333:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:334:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:344:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==RULE_ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:345:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:345:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:346:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMessage541);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:3: ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==14) ) {
                alt7=1;
            }
            else if ( (LA7_0==16) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("368:3: ( ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' ) | '/>' )", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:4: ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:4: ( '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:368:6: '>' ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )* '</message>'
                    {
                    match(input,14,FOLLOW_14_in_ruleMessage554); 

                            createLeafNode(grammarAccess.getMessageAccess().getGreaterThanSignKeyword_3_0_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:372:1: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleMap ) ) )*
                    loop6:
                    do {
                        int alt6=4;
                        switch ( input.LA(1) ) {
                        case 19:
                            {
                            alt6=1;
                            }
                            break;
                        case 23:
                            {
                            alt6=2;
                            }
                            break;
                        case 22:
                            {
                            alt6=3;
                            }
                            break;

                        }

                        switch (alt6) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:372:2: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:372:2: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:373:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:373:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:374:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMessage576);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:397:6: ( (lv_children_5_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:397:6: ( (lv_children_5_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:398:1: (lv_children_5_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:398:1: (lv_children_5_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:399:3: lv_children_5_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMessage603);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:422:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:422:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:423:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:423:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:424:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMessage630);
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

                    match(input,20,FOLLOW_20_in_ruleMessage642); 

                            createLeafNode(grammarAccess.getMessageAccess().getMessageKeyword_3_0_2(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:451:7: '/>'
                    {
                    match(input,16,FOLLOW_16_in_ruleMessage659); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:463:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:464:2: (iv_ruleMap= ruleMap EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:465:2: iv_ruleMap= ruleMap EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapRule(), currentNode); 
            pushFollow(FOLLOW_ruleMap_in_entryRuleMap696);
            iv_ruleMap=ruleMap();
            _fsp--;

             current =iv_ruleMap; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMap706); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:472:1: ruleMap returns [EObject current=null] : (this_MapStart_0= ruleMapStart ( '/>' | ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' ) ) ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token lv_mapClosingName_7_0=null;
        EObject this_MapStart_0 = null;

        EObject lv_children_3_0 = null;

        EObject lv_children_4_0 = null;

        EObject lv_children_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:477:6: ( (this_MapStart_0= ruleMapStart ( '/>' | ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:1: (this_MapStart_0= ruleMapStart ( '/>' | ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:1: (this_MapStart_0= ruleMapStart ( '/>' | ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:479:5: this_MapStart_0= ruleMapStart ( '/>' | ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' ) )
            {
             
                    currentNode=createCompositeNode(grammarAccess.getMapAccess().getMapStartParserRuleCall_0(), currentNode); 
                
            pushFollow(FOLLOW_ruleMapStart_in_ruleMap753);
            this_MapStart_0=ruleMapStart();
            _fsp--;

             
                    current = this_MapStart_0; 
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:487:1: ( '/>' | ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==16) ) {
                alt9=1;
            }
            else if ( (LA9_0==14) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("487:1: ( '/>' | ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' ) )", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:487:3: '/>'
                    {
                    match(input,16,FOLLOW_16_in_ruleMap763); 

                            createLeafNode(grammarAccess.getMapAccess().getSolidusGreaterThanSignKeyword_1_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:492:6: ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:492:6: ( '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:492:8: '>' ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )* '</map.' ( (lv_mapClosingName_7_0= RULE_ID ) ) '>'
                    {
                    match(input,14,FOLLOW_14_in_ruleMap780); 

                            createLeafNode(grammarAccess.getMapAccess().getGreaterThanSignKeyword_1_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:496:1: ( ( (lv_children_3_0= ruleMessage ) ) | ( (lv_children_4_0= ruleProperty ) ) | ( (lv_children_5_0= ruleMap ) ) )*
                    loop8:
                    do {
                        int alt8=4;
                        switch ( input.LA(1) ) {
                        case 19:
                            {
                            alt8=1;
                            }
                            break;
                        case 23:
                            {
                            alt8=2;
                            }
                            break;
                        case 22:
                            {
                            alt8=3;
                            }
                            break;

                        }

                        switch (alt8) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:496:2: ( (lv_children_3_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:496:2: ( (lv_children_3_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:497:1: (lv_children_3_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:497:1: (lv_children_3_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:498:3: lv_children_3_0= ruleMessage
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_1_1_1_0_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMap802);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:521:6: ( (lv_children_4_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:521:6: ( (lv_children_4_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:522:1: (lv_children_4_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:522:1: (lv_children_4_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:523:3: lv_children_4_0= ruleProperty
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_1_1_1_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMap829);
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:546:6: ( (lv_children_5_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:546:6: ( (lv_children_5_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:547:1: (lv_children_5_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:547:1: (lv_children_5_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:548:3: lv_children_5_0= ruleMap
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_1_1_1_2_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMap856);
                    	    lv_children_5_0=ruleMap();
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

                    match(input,21,FOLLOW_21_in_ruleMap868); 

                            createLeafNode(grammarAccess.getMapAccess().getMapKeyword_1_1_2(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:574:1: ( (lv_mapClosingName_7_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:575:1: (lv_mapClosingName_7_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:575:1: (lv_mapClosingName_7_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:576:3: lv_mapClosingName_7_0= RULE_ID
                    {
                    lv_mapClosingName_7_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMap885); 

                    			createLeafNode(grammarAccess.getMapAccess().getMapClosingNameIDTerminalRuleCall_1_1_3_0(), "mapClosingName"); 
                    		

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getMapRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        try {
                    	       		set(
                    	       			current, 
                    	       			"mapClosingName",
                    	        		lv_mapClosingName_7_0, 
                    	        		"ID", 
                    	        		lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }

                    match(input,14,FOLLOW_14_in_ruleMap900); 

                            createLeafNode(grammarAccess.getMapAccess().getGreaterThanSignKeyword_1_1_4(), null); 
                        

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


    // $ANTLR start entryRuleMapStart
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:610:1: entryRuleMapStart returns [EObject current=null] : iv_ruleMapStart= ruleMapStart EOF ;
    public final EObject entryRuleMapStart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapStart = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:611:2: (iv_ruleMapStart= ruleMapStart EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:612:2: iv_ruleMapStart= ruleMapStart EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapStartRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapStart_in_entryRuleMapStart938);
            iv_ruleMapStart=ruleMapStart();
            _fsp--;

             current =iv_ruleMapStart; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapStart948); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:619:1: ruleMapStart returns [EObject current=null] : ( () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )* ) ;
    public final EObject ruleMapStart() throws RecognitionException {
        EObject current = null;

        Token lv_mapName_2_0=null;
        EObject lv_attributes_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:624:6: ( ( () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:625:1: ( () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:625:1: ( () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:625:2: () '<map.' ( (lv_mapName_2_0= RULE_ID ) ) ( (lv_attributes_3_0= rulePossibleExpression ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:625:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:626:5: 
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

            match(input,22,FOLLOW_22_in_ruleMapStart992); 

                    createLeafNode(grammarAccess.getMapStartAccess().getMapKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:640:1: ( (lv_mapName_2_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:641:1: (lv_mapName_2_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:641:1: (lv_mapName_2_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:642:3: lv_mapName_2_0= RULE_ID
            {
            lv_mapName_2_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapStart1009); 

            			createLeafNode(grammarAccess.getMapStartAccess().getMapNameIDTerminalRuleCall_2_0(), "mapName"); 
            		

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapStartRule().getType().getClassifier());
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:664:2: ( (lv_attributes_3_0= rulePossibleExpression ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:665:1: (lv_attributes_3_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:665:1: (lv_attributes_3_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:666:3: lv_attributes_3_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapStartAccess().getAttributesPossibleExpressionParserRuleCall_3_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMapStart1035);
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
            	    break loop10;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:696:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:697:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:698:2: iv_ruleProperty= ruleProperty EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPropertyRule(), currentNode); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty1072);
            iv_ruleProperty=ruleProperty();
            _fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty1082); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:705:1: ruleProperty returns [EObject current=null] : ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        EObject lv_attributes_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:710:6: ( ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:1: ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:1: ( '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:711:3: '<property' () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )
            {
            match(input,23,FOLLOW_23_in_ruleProperty1117); 

                    createLeafNode(grammarAccess.getPropertyAccess().getPropertyKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:715:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:716:5: 
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:726:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ID) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:727:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:727:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:728:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleProperty1147);
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
            	    break loop11;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:750:3: ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==16) ) {
                alt13=1;
            }
            else if ( (LA13_0==14) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("750:3: ( '/>' | ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' ) )", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:750:5: '/>'
                    {
                    match(input,16,FOLLOW_16_in_ruleProperty1159); 

                            createLeafNode(grammarAccess.getPropertyAccess().getSolidusGreaterThanSignKeyword_3_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:755:6: ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:755:6: ( '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:755:8: '>' ( (lv_expressionValue_5_0= ruleExpressionTag ) )? '</property>'
                    {
                    match(input,14,FOLLOW_14_in_ruleProperty1176); 

                            createLeafNode(grammarAccess.getPropertyAccess().getGreaterThanSignKeyword_3_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:759:1: ( (lv_expressionValue_5_0= ruleExpressionTag ) )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==25) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:760:1: (lv_expressionValue_5_0= ruleExpressionTag )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:760:1: (lv_expressionValue_5_0= ruleExpressionTag )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:761:3: lv_expressionValue_5_0= ruleExpressionTag
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getPropertyAccess().getExpressionValueExpressionTagParserRuleCall_3_1_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleExpressionTag_in_ruleProperty1197);
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

                    match(input,24,FOLLOW_24_in_ruleProperty1208); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:795:1: entryRuleExpressionTag returns [EObject current=null] : iv_ruleExpressionTag= ruleExpressionTag EOF ;
    public final EObject entryRuleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:796:2: (iv_ruleExpressionTag= ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:797:2: iv_ruleExpressionTag= ruleExpressionTag EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExpressionTagRule(), currentNode); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag1246);
            iv_ruleExpressionTag=ruleExpressionTag();
            _fsp--;

             current =iv_ruleExpressionTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag1256); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:804:1: ruleExpressionTag returns [EObject current=null] : ( '<expression>' ( (lv_expression_1_0= ruleTopLevel ) ) '</expression>' ) ;
    public final EObject ruleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject lv_expression_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:809:6: ( ( '<expression>' ( (lv_expression_1_0= ruleTopLevel ) ) '</expression>' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:810:1: ( '<expression>' ( (lv_expression_1_0= ruleTopLevel ) ) '</expression>' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:810:1: ( '<expression>' ( (lv_expression_1_0= ruleTopLevel ) ) '</expression>' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:810:3: '<expression>' ( (lv_expression_1_0= ruleTopLevel ) ) '</expression>'
            {
            match(input,25,FOLLOW_25_in_ruleExpressionTag1291); 

                    createLeafNode(grammarAccess.getExpressionTagAccess().getExpressionKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:814:1: ( (lv_expression_1_0= ruleTopLevel ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:815:1: (lv_expression_1_0= ruleTopLevel )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:815:1: (lv_expression_1_0= ruleTopLevel )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:816:3: lv_expression_1_0= ruleTopLevel
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExpressionTagAccess().getExpressionTopLevelParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleTopLevel_in_ruleExpressionTag1312);
            lv_expression_1_0=ruleTopLevel();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getExpressionTagRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		set(
            	       			current, 
            	       			"expression",
            	        		lv_expression_1_0, 
            	        		"TopLevel", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            match(input,26,FOLLOW_26_in_ruleExpressionTag1322); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:850:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:851:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:852:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTopLevelRule(), currentNode); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel1358);
            iv_ruleTopLevel=ruleTopLevel();
            _fsp--;

             current =iv_ruleTopLevel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel1368); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:859:1: ruleTopLevel returns [EObject current=null] : ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_0_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:864:6: ( ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:865:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:865:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:866:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:866:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:867:3: lv_toplevelExpression_0_0= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel1413);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:897:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:898:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:899:2: iv_rulePathElement= rulePathElement EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathElementRule(), currentNode); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement1449);
            iv_rulePathElement=rulePathElement();
            _fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement1460); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:906:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:911:6: ( (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:912:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:912:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            int alt14=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt14=1;
                }
                break;
            case 27:
                {
                alt14=2;
                }
                break;
            case 28:
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("912:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:912:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement1500); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:921:2: kw= '.'
                    {
                    kw=(Token)input.LT(1);
                    match(input,27,FOLLOW_27_in_rulePathElement1524); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:928:2: kw= '..'
                    {
                    kw=(Token)input.LT(1);
                    match(input,28,FOLLOW_28_in_rulePathElement1543); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:941:1: entryRuleTmlExpression returns [EObject current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final EObject entryRuleTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:942:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:943:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression1583);
            iv_ruleTmlExpression=ruleTmlExpression();
            _fsp--;

             current =iv_ruleTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression1593); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:950:1: ruleTmlExpression returns [EObject current=null] : ( '[' ( (lv_absolute_1_0= '/' ) )? ( (lv_elements_2_0= rulePathElement ) ) ( '/' ( (lv_elements_4_0= rulePathElement ) ) )* ']' ) ;
    public final EObject ruleTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_1_0=null;
        AntlrDatatypeRuleToken lv_elements_2_0 = null;

        AntlrDatatypeRuleToken lv_elements_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:955:6: ( ( '[' ( (lv_absolute_1_0= '/' ) )? ( (lv_elements_2_0= rulePathElement ) ) ( '/' ( (lv_elements_4_0= rulePathElement ) ) )* ']' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:956:1: ( '[' ( (lv_absolute_1_0= '/' ) )? ( (lv_elements_2_0= rulePathElement ) ) ( '/' ( (lv_elements_4_0= rulePathElement ) ) )* ']' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:956:1: ( '[' ( (lv_absolute_1_0= '/' ) )? ( (lv_elements_2_0= rulePathElement ) ) ( '/' ( (lv_elements_4_0= rulePathElement ) ) )* ']' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:956:3: '[' ( (lv_absolute_1_0= '/' ) )? ( (lv_elements_2_0= rulePathElement ) ) ( '/' ( (lv_elements_4_0= rulePathElement ) ) )* ']'
            {
            match(input,29,FOLLOW_29_in_ruleTmlExpression1628); 

                    createLeafNode(grammarAccess.getTmlExpressionAccess().getLeftSquareBracketKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:960:1: ( (lv_absolute_1_0= '/' ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==30) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:961:1: (lv_absolute_1_0= '/' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:961:1: (lv_absolute_1_0= '/' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:962:3: lv_absolute_1_0= '/'
                    {
                    lv_absolute_1_0=(Token)input.LT(1);
                    match(input,30,FOLLOW_30_in_ruleTmlExpression1646); 

                            createLeafNode(grammarAccess.getTmlExpressionAccess().getAbsoluteSolidusKeyword_1_0(), "absolute"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getTmlExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "absolute", true, "/", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:981:3: ( (lv_elements_2_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:982:1: (lv_elements_2_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:982:1: (lv_elements_2_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:983:3: lv_elements_2_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_2_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression1681);
            lv_elements_2_0=rulePathElement();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getTmlExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        try {
            	       		add(
            	       			current, 
            	       			"elements",
            	        		lv_elements_2_0, 
            	        		"PathElement", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1005:2: ( '/' ( (lv_elements_4_0= rulePathElement ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==30) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1005:4: '/' ( (lv_elements_4_0= rulePathElement ) )
            	    {
            	    match(input,30,FOLLOW_30_in_ruleTmlExpression1692); 

            	            createLeafNode(grammarAccess.getTmlExpressionAccess().getSolidusKeyword_3_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1009:1: ( (lv_elements_4_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1010:1: (lv_elements_4_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1010:1: (lv_elements_4_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1011:3: lv_elements_4_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression1713);
            	    lv_elements_4_0=rulePathElement();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getTmlExpressionRule().getType().getClassifier());
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


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            match(input,31,FOLLOW_31_in_ruleTmlExpression1725); 

                    createLeafNode(grammarAccess.getTmlExpressionAccess().getRightSquareBracketKeyword_4(), null); 
                

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1045:1: entryRuleExistsTmlExpression returns [EObject current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final EObject entryRuleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1046:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1047:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExistsTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression1761);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();
            _fsp--;

             current =iv_ruleExistsTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression1771); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1054:1: ruleExistsTmlExpression returns [EObject current=null] : ( '?' '[' ( (lv_absolute_2_0= '/' ) )? ( (lv_elements_3_0= rulePathElement ) ) ( '/' ( (lv_elements_5_0= rulePathElement ) ) )* ']' ) ;
    public final EObject ruleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        Token lv_absolute_2_0=null;
        AntlrDatatypeRuleToken lv_elements_3_0 = null;

        AntlrDatatypeRuleToken lv_elements_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1059:6: ( ( '?' '[' ( (lv_absolute_2_0= '/' ) )? ( (lv_elements_3_0= rulePathElement ) ) ( '/' ( (lv_elements_5_0= rulePathElement ) ) )* ']' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1060:1: ( '?' '[' ( (lv_absolute_2_0= '/' ) )? ( (lv_elements_3_0= rulePathElement ) ) ( '/' ( (lv_elements_5_0= rulePathElement ) ) )* ']' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1060:1: ( '?' '[' ( (lv_absolute_2_0= '/' ) )? ( (lv_elements_3_0= rulePathElement ) ) ( '/' ( (lv_elements_5_0= rulePathElement ) ) )* ']' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1060:3: '?' '[' ( (lv_absolute_2_0= '/' ) )? ( (lv_elements_3_0= rulePathElement ) ) ( '/' ( (lv_elements_5_0= rulePathElement ) ) )* ']'
            {
            match(input,32,FOLLOW_32_in_ruleExistsTmlExpression1806); 

                    createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getQuestionMarkKeyword_0(), null); 
                
            match(input,29,FOLLOW_29_in_ruleExistsTmlExpression1816); 

                    createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getLeftSquareBracketKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1068:1: ( (lv_absolute_2_0= '/' ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==30) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1069:1: (lv_absolute_2_0= '/' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1069:1: (lv_absolute_2_0= '/' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1070:3: lv_absolute_2_0= '/'
                    {
                    lv_absolute_2_0=(Token)input.LT(1);
                    match(input,30,FOLLOW_30_in_ruleExistsTmlExpression1834); 

                            createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteSolidusKeyword_2_0(), "absolute"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getExistsTmlExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "absolute", true, "/", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1089:3: ( (lv_elements_3_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1090:1: (lv_elements_3_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1090:1: (lv_elements_3_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1091:3: lv_elements_3_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression1869);
            lv_elements_3_0=rulePathElement();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getExistsTmlExpressionRule().getType().getClassifier());
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1113:2: ( '/' ( (lv_elements_5_0= rulePathElement ) ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==30) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1113:4: '/' ( (lv_elements_5_0= rulePathElement ) )
            	    {
            	    match(input,30,FOLLOW_30_in_ruleExistsTmlExpression1880); 

            	            createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getSolidusKeyword_4_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1117:1: ( (lv_elements_5_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1118:1: (lv_elements_5_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1118:1: (lv_elements_5_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1119:3: lv_elements_5_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression1901);
            	    lv_elements_5_0=rulePathElement();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getExistsTmlExpressionRule().getType().getClassifier());
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
            	    break loop18;
                }
            } while (true);

            match(input,31,FOLLOW_31_in_ruleExistsTmlExpression1913); 

                    createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getRightSquareBracketKeyword_5(), null); 
                

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1153:1: entryRuleMapGetReference returns [EObject current=null] : iv_ruleMapGetReference= ruleMapGetReference EOF ;
    public final EObject entryRuleMapGetReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapGetReference = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1154:2: (iv_ruleMapGetReference= ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1155:2: iv_ruleMapGetReference= ruleMapGetReference EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMapGetReferenceRule(), currentNode); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference1949);
            iv_ruleMapGetReference=ruleMapGetReference();
            _fsp--;

             current =iv_ruleMapGetReference; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference1959); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1162:1: ruleMapGetReference returns [EObject current=null] : ( ( (lv_operations_0_0= '$' ) ) ( (lv_elements_1_0= rulePathElement ) ) ( '/' ( (lv_elements_3_0= rulePathElement ) ) )* ) ;
    public final EObject ruleMapGetReference() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        AntlrDatatypeRuleToken lv_elements_1_0 = null;

        AntlrDatatypeRuleToken lv_elements_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1167:6: ( ( ( (lv_operations_0_0= '$' ) ) ( (lv_elements_1_0= rulePathElement ) ) ( '/' ( (lv_elements_3_0= rulePathElement ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1168:1: ( ( (lv_operations_0_0= '$' ) ) ( (lv_elements_1_0= rulePathElement ) ) ( '/' ( (lv_elements_3_0= rulePathElement ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1168:1: ( ( (lv_operations_0_0= '$' ) ) ( (lv_elements_1_0= rulePathElement ) ) ( '/' ( (lv_elements_3_0= rulePathElement ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1168:2: ( (lv_operations_0_0= '$' ) ) ( (lv_elements_1_0= rulePathElement ) ) ( '/' ( (lv_elements_3_0= rulePathElement ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1168:2: ( (lv_operations_0_0= '$' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1169:1: (lv_operations_0_0= '$' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1169:1: (lv_operations_0_0= '$' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1170:3: lv_operations_0_0= '$'
            {
            lv_operations_0_0=(Token)input.LT(1);
            match(input,33,FOLLOW_33_in_ruleMapGetReference2002); 

                    createLeafNode(grammarAccess.getMapGetReferenceAccess().getOperationsDollarSignKeyword_0_0(), "operations"); 
                

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getMapGetReferenceRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        
            	        try {
            	       		add(current, "operations", lv_operations_0_0, "$", lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1189:2: ( (lv_elements_1_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1190:1: (lv_elements_1_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1190:1: (lv_elements_1_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1191:3: lv_elements_1_0= rulePathElement
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMapGetReferenceAccess().getElementsPathElementParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleMapGetReference2036);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1213:2: ( '/' ( (lv_elements_3_0= rulePathElement ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==30) ) {
                    int LA19_2 = input.LA(2);

                    if ( (LA19_2==RULE_ID) ) {
                        int LA19_3 = input.LA(3);

                        if ( (LA19_3==EOF||LA19_3==18||LA19_3==26||LA19_3==30||(LA19_3>=34 && LA19_3<=40)||(LA19_3>=43 && LA19_3<=44)||LA19_3==47) ) {
                            alt19=1;
                        }


                    }
                    else if ( ((LA19_2>=27 && LA19_2<=28)) ) {
                        alt19=1;
                    }


                }


                switch (alt19) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1213:4: '/' ( (lv_elements_3_0= rulePathElement ) )
            	    {
            	    match(input,30,FOLLOW_30_in_ruleMapGetReference2047); 

            	            createLeafNode(grammarAccess.getMapGetReferenceAccess().getSolidusKeyword_2_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1217:1: ( (lv_elements_3_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1218:1: (lv_elements_3_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1218:1: (lv_elements_3_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:3: lv_elements_3_0= rulePathElement
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMapGetReferenceAccess().getElementsPathElementParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleMapGetReference2068);
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
    // $ANTLR end ruleMapGetReference


    // $ANTLR start entryRuleOrExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1249:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1250:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1251:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression2106);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression2116); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1258:1: ruleOrExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1263:6: ( ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1264:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1264:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1264:2: ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1264:2: ( (lv_parameters_0_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1265:1: (lv_parameters_0_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1265:1: (lv_parameters_0_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1266:3: lv_parameters_0_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression2162);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1288:2: ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==34) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1288:3: ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1288:3: ( (lv_operations_1_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1289:1: (lv_operations_1_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1289:1: (lv_operations_1_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1290:3: lv_operations_1_0= 'OR'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,34,FOLLOW_34_in_ruleOrExpression2181); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1309:2: ( (lv_parameters_2_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1310:1: (lv_parameters_2_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1310:1: (lv_parameters_2_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1311:3: lv_parameters_2_0= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression2215);
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
    // $ANTLR end ruleOrExpression


    // $ANTLR start entryRuleAndExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1341:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1342:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1343:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression2253);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression2263); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1350:1: ruleAndExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1355:6: ( ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1356:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1356:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1356:2: ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1356:2: ( (lv_parameters_0_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1357:1: (lv_parameters_0_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1357:1: (lv_parameters_0_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1358:3: lv_parameters_0_0= ruleEqualityExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression2309);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1380:2: ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==35) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1380:3: ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1380:3: ( (lv_operations_1_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1381:1: (lv_operations_1_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1381:1: (lv_operations_1_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1382:3: lv_operations_1_0= 'AND'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,35,FOLLOW_35_in_ruleAndExpression2328); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1401:2: ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1402:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1402:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1403:3: lv_parameters_2_0= ruleEqualityExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression2362);
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
            	    break loop21;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1433:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1434:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1435:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEqualityExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression2400);
            iv_ruleEqualityExpression=ruleEqualityExpression();
            _fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression2410); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1442:1: ruleEqualityExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1447:6: ( ( ( (lv_parameters_0_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1448:1: ( ( (lv_parameters_0_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1448:1: ( ( (lv_parameters_0_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1448:2: ( (lv_parameters_0_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1448:2: ( (lv_parameters_0_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1449:1: (lv_parameters_0_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1449:1: (lv_parameters_0_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1450:3: lv_parameters_0_0= ruleAdditiveExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2456);
            lv_parameters_0_0=ruleAdditiveExpression();
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
            	        		"AdditiveExpression", 
            	        		currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1472:2: ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) ) ) )?
            int alt22=3;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==36) ) {
                alt22=1;
            }
            else if ( (LA22_0==37) ) {
                alt22=2;
            }
            switch (alt22) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1472:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1472:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1472:4: ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1472:4: ( (lv_operations_1_0= '==' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:1: (lv_operations_1_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1473:1: (lv_operations_1_0= '==' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1474:3: lv_operations_1_0= '=='
                    {
                    lv_operations_1_0=(Token)input.LT(1);
                    match(input,36,FOLLOW_36_in_ruleEqualityExpression2476); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1493:2: ( (lv_parameters_2_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1494:1: (lv_parameters_2_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1494:1: (lv_parameters_2_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1495:3: lv_parameters_2_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2510);
                    lv_parameters_2_0=ruleAdditiveExpression();
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1518:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1518:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1518:7: ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1518:7: ( (lv_operations_3_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1519:1: (lv_operations_3_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1519:1: (lv_operations_3_0= '!=' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1520:3: lv_operations_3_0= '!='
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,37,FOLLOW_37_in_ruleEqualityExpression2536); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1539:2: ( (lv_parameters_4_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1540:1: (lv_parameters_4_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1540:1: (lv_parameters_4_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1541:3: lv_parameters_4_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2570);
                    lv_parameters_4_0=ruleAdditiveExpression();
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1571:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1572:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1573:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAdditiveExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression2609);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();
            _fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression2619); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1580:1: ruleAdditiveExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1585:6: ( ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1586:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1586:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1586:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1586:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1587:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1587:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1588:3: lv_parameters_0_0= ruleMultiplicativeExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2665);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1610:2: ( ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            loop23:
            do {
                int alt23=3;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==38) ) {
                    alt23=1;
                }
                else if ( (LA23_0==39) ) {
                    alt23=2;
                }


                switch (alt23) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1610:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1610:3: ( '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1610:5: '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,38,FOLLOW_38_in_ruleAdditiveExpression2677); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1614:1: ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1615:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1615:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1616:3: lv_parameters_2_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2698);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1639:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1639:6: ( '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1639:8: '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,39,FOLLOW_39_in_ruleAdditiveExpression2716); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1643:1: ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1644:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1644:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1645:3: lv_parameters_4_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2737);
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
            	    break loop23;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1675:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1676:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1677:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression2776);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();
            _fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression2786); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1684:1: ruleMultiplicativeExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1689:6: ( ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1690:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1690:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1690:2: ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1690:2: ( (lv_parameters_0_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1691:1: (lv_parameters_0_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1691:1: (lv_parameters_0_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1692:3: lv_parameters_0_0= ruleUnaryExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2832);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1714:2: ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            loop24:
            do {
                int alt24=3;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==40) ) {
                    alt24=1;
                }
                else if ( (LA24_0==30) ) {
                    alt24=2;
                }


                switch (alt24) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1714:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1714:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1714:4: ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1714:4: ( (lv_operations_1_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1715:1: (lv_operations_1_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1715:1: (lv_operations_1_0= '*' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1716:3: lv_operations_1_0= '*'
            	    {
            	    lv_operations_1_0=(Token)input.LT(1);
            	    match(input,40,FOLLOW_40_in_ruleMultiplicativeExpression2852); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1735:2: ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1736:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1736:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1737:3: lv_parameters_2_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2886);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1760:6: ( ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1760:6: ( ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1760:7: ( (lv_operations_3_0= '/' ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1760:7: ( (lv_operations_3_0= '/' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1761:1: (lv_operations_3_0= '/' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1761:1: (lv_operations_3_0= '/' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1762:3: lv_operations_3_0= '/'
            	    {
            	    lv_operations_3_0=(Token)input.LT(1);
            	    match(input,30,FOLLOW_30_in_ruleMultiplicativeExpression2912); 

            	            createLeafNode(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_1_1_0_0(), "operations"); 
            	        

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getMultiplicativeExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operations", lv_operations_3_0, "/", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1781:2: ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1782:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1782:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1783:3: lv_parameters_4_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2946);
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
    // $ANTLR end ruleMultiplicativeExpression


    // $ANTLR start entryRuleUnaryExpression
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1813:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1814:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1815:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getUnaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression2985);
            iv_ruleUnaryExpression=ruleUnaryExpression();
            _fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression2995); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1822:1: ruleUnaryExpression returns [EObject current=null] : ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        EObject lv_parameters_1_0 = null;

        EObject this_PrimaryExpression_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1827:6: ( ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1828:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1828:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==41) ) {
                alt25=1;
            }
            else if ( (LA25_0==RULE_ID||(LA25_0>=RULE_INT && LA25_0<=RULE_LITERALSTRING)||LA25_0==29||(LA25_0>=32 && LA25_0<=33)||LA25_0==42||(LA25_0>=45 && LA25_0<=46)||(LA25_0>=48 && LA25_0<=51)) ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1828:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1828:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1828:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1828:3: ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1828:3: ( (lv_operations_0_0= '!' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1829:1: (lv_operations_0_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1829:1: (lv_operations_0_0= '!' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1830:3: lv_operations_0_0= '!'
                    {
                    lv_operations_0_0=(Token)input.LT(1);
                    match(input,41,FOLLOW_41_in_ruleUnaryExpression3039); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1849:2: ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1850:1: (lv_parameters_1_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1850:1: (lv_parameters_1_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1851:3: lv_parameters_1_0= rulePrimaryExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression3073);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1875:5: this_PrimaryExpression_2= rulePrimaryExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression3102);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1891:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1892:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1893:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPrimaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression3137);
            iv_rulePrimaryExpression=rulePrimaryExpression();
            _fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression3147); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1900:1: rulePrimaryExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1905:6: ( ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1906:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1906:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_ID||(LA26_0>=RULE_INT && LA26_0<=RULE_LITERALSTRING)||LA26_0==29||(LA26_0>=32 && LA26_0<=33)||(LA26_0>=45 && LA26_0<=46)||(LA26_0>=48 && LA26_0<=51)) ) {
                alt26=1;
            }
            else if ( (LA26_0==42) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1906:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' ) )", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1906:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1906:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1907:1: (lv_parameters_0_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1907:1: (lv_parameters_0_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1908:3: lv_parameters_0_0= ruleLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression3193);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1931:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1931:6: ( '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1931:8: '(' ( (lv_parameters_2_0= ruleOrExpression ) ) ')'
                    {
                    match(input,42,FOLLOW_42_in_rulePrimaryExpression3210); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1935:1: ( (lv_parameters_2_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1936:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1936:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1937:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression3231);
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

                    match(input,43,FOLLOW_43_in_rulePrimaryExpression3241); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1971:1: entryRuleFunctionName returns [String current=null] : iv_ruleFunctionName= ruleFunctionName EOF ;
    public final String entryRuleFunctionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFunctionName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1972:2: (iv_ruleFunctionName= ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1973:2: iv_ruleFunctionName= ruleFunctionName EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionNameRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName3279);
            iv_ruleFunctionName=ruleFunctionName();
            _fsp--;

             current =iv_ruleFunctionName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName3290); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1980:1: ruleFunctionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFunctionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1985:6: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1986:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName3329); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2001:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2002:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2003:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionCallRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall3373);
            iv_ruleFunctionCall=ruleFunctionCall();
            _fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall3383); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2010:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2015:6: ( ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2016:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2016:1: ( ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2016:2: ( (lv_name_0_0= ruleFunctionName ) ) '(' ( (lv_parameters_2_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* ')'
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2016:2: ( (lv_name_0_0= ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2017:1: (lv_name_0_0= ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2017:1: (lv_name_0_0= ruleFunctionName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2018:3: lv_name_0_0= ruleFunctionName
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleFunctionName_in_ruleFunctionCall3429);
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

            match(input,42,FOLLOW_42_in_ruleFunctionCall3439); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2044:1: ( (lv_parameters_2_0= ruleOrExpression ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==RULE_ID||(LA27_0>=RULE_INT && LA27_0<=RULE_LITERALSTRING)||LA27_0==29||(LA27_0>=32 && LA27_0<=33)||(LA27_0>=41 && LA27_0<=42)||(LA27_0>=45 && LA27_0<=46)||(LA27_0>=48 && LA27_0<=51)) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2045:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2045:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2046:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall3460);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2068:3: ( ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==44) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2068:5: ',' ( (lv_parameters_4_0= ruleOrExpression ) )
            	    {
            	    match(input,44,FOLLOW_44_in_ruleFunctionCall3472); 

            	            createLeafNode(grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2072:1: ( (lv_parameters_4_0= ruleOrExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2073:1: (lv_parameters_4_0= ruleOrExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2073:1: (lv_parameters_4_0= ruleOrExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2074:3: lv_parameters_4_0= ruleOrExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall3493);
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
            	    break loop28;
                }
            } while (true);

            match(input,43,FOLLOW_43_in_ruleFunctionCall3505); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2108:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2109:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2110:2: iv_ruleLiteral= ruleLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral3541);
            iv_ruleLiteral=ruleLiteral();
            _fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral3551); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2117:1: ruleLiteral returns [EObject current=null] : ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2122:6: ( ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2123:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2123:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )
            int alt31=12;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt31=1;
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt31=2;
                }
                break;
            case 45:
                {
                alt31=3;
                }
                break;
            case RULE_ID:
                {
                alt31=4;
                }
                break;
            case 46:
                {
                alt31=5;
                }
                break;
            case 48:
                {
                alt31=6;
                }
                break;
            case 49:
                {
                alt31=7;
                }
                break;
            case 50:
                {
                alt31=8;
                }
                break;
            case 51:
                {
                alt31=9;
                }
                break;
            case 29:
                {
                alt31=10;
                }
                break;
            case 32:
                {
                alt31=11;
                }
                break;
            case 33:
                {
                alt31=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("2123:1: ( ( () RULE_INT ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_9= ruleFunctionCall | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' ) | ( (lv_elements_15_0= 'NULL' ) ) | ( (lv_elements_16_0= 'TODAY' ) ) | ( (lv_elements_17_0= 'TRUE' ) ) | ( (lv_elements_18_0= 'FALSE' ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) )", 31, 0, input);

                throw nvae;
            }

            switch (alt31) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2123:2: ( () RULE_INT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2123:2: ( () RULE_INT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2123:3: () RULE_INT
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2123:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2124:5: 
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

                    match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleLiteral3595); 
                     
                        createLeafNode(grammarAccess.getLiteralAccess().getINTTerminalRuleCall_0_1(), null); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2139:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2139:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2140:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2140:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2141:3: lv_valueString_2_0= RULE_LITERALSTRING
                    {
                    lv_valueString_2_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3618); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2164:6: ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2164:6: ( ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2164:7: ( (lv_operations_3_0= 'FORALL' ) ) '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_7_0= ruleOrExpression ) ) ')'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2164:7: ( (lv_operations_3_0= 'FORALL' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2165:1: (lv_operations_3_0= 'FORALL' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2165:1: (lv_operations_3_0= 'FORALL' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2166:3: lv_operations_3_0= 'FORALL'
                    {
                    lv_operations_3_0=(Token)input.LT(1);
                    match(input,45,FOLLOW_45_in_ruleLiteral3648); 

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

                    match(input,42,FOLLOW_42_in_ruleLiteral3671); 

                            createLeafNode(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2189:1: ( (lv_valueString_5_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2190:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2190:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2191:3: lv_valueString_5_0= RULE_LITERALSTRING
                    {
                    lv_valueString_5_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3688); 

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

                    match(input,44,FOLLOW_44_in_ruleLiteral3703); 

                            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_2_3(), null); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2217:1: ( (lv_parameters_7_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2218:1: (lv_parameters_7_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2218:1: (lv_parameters_7_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2219:3: lv_parameters_7_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3724);
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

                    match(input,43,FOLLOW_43_in_ruleLiteral3734); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5(), null); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2247:5: this_FunctionCall_9= ruleFunctionCall
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral3763);
                    this_FunctionCall_9=ruleFunctionCall();
                    _fsp--;

                     
                            current = this_FunctionCall_9; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2256:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2256:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2256:7: ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* '}'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2256:7: ( (lv_expressionType_10_0= '{' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2257:1: (lv_expressionType_10_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2257:1: (lv_expressionType_10_0= '{' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2258:3: lv_expressionType_10_0= '{'
                    {
                    lv_expressionType_10_0=(Token)input.LT(1);
                    match(input,46,FOLLOW_46_in_ruleLiteral3787); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2277:2: ( (lv_parameters_11_0= ruleOrExpression ) )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==RULE_ID||(LA29_0>=RULE_INT && LA29_0<=RULE_LITERALSTRING)||LA29_0==29||(LA29_0>=32 && LA29_0<=33)||(LA29_0>=41 && LA29_0<=42)||(LA29_0>=45 && LA29_0<=46)||(LA29_0>=48 && LA29_0<=51)) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2278:1: (lv_parameters_11_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2278:1: (lv_parameters_11_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2279:3: lv_parameters_11_0= ruleOrExpression
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3821);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2301:3: ( ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( (LA30_0==44) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2301:5: ',' ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    {
                    	    match(input,44,FOLLOW_44_in_ruleLiteral3833); 

                    	            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0(), null); 
                    	        
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2305:1: ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2306:1: (lv_parameters_13_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2306:1: (lv_parameters_13_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2307:3: lv_parameters_13_0= ruleOrExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3854);
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
                    	    break loop30;
                        }
                    } while (true);

                    match(input,47,FOLLOW_47_in_ruleLiteral3866); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3(), null); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2334:6: ( (lv_elements_15_0= 'NULL' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2334:6: ( (lv_elements_15_0= 'NULL' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2335:1: (lv_elements_15_0= 'NULL' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2335:1: (lv_elements_15_0= 'NULL' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2336:3: lv_elements_15_0= 'NULL'
                    {
                    lv_elements_15_0=(Token)input.LT(1);
                    match(input,48,FOLLOW_48_in_ruleLiteral3891); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2356:6: ( (lv_elements_16_0= 'TODAY' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2356:6: ( (lv_elements_16_0= 'TODAY' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:1: (lv_elements_16_0= 'TODAY' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2357:1: (lv_elements_16_0= 'TODAY' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2358:3: lv_elements_16_0= 'TODAY'
                    {
                    lv_elements_16_0=(Token)input.LT(1);
                    match(input,49,FOLLOW_49_in_ruleLiteral3928); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2378:6: ( (lv_elements_17_0= 'TRUE' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2378:6: ( (lv_elements_17_0= 'TRUE' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2379:1: (lv_elements_17_0= 'TRUE' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2379:1: (lv_elements_17_0= 'TRUE' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2380:3: lv_elements_17_0= 'TRUE'
                    {
                    lv_elements_17_0=(Token)input.LT(1);
                    match(input,50,FOLLOW_50_in_ruleLiteral3965); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2400:6: ( (lv_elements_18_0= 'FALSE' ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2400:6: ( (lv_elements_18_0= 'FALSE' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2401:1: (lv_elements_18_0= 'FALSE' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2401:1: (lv_elements_18_0= 'FALSE' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2402:3: lv_elements_18_0= 'FALSE'
                    {
                    lv_elements_18_0=(Token)input.LT(1);
                    match(input,51,FOLLOW_51_in_ruleLiteral4002); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2422:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2422:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2423:1: (lv_parameters_19_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2423:1: (lv_parameters_19_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2424:3: lv_parameters_19_0= ruleTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral4042);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2447:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2447:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2448:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2448:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2449:3: lv_parameters_20_0= ruleExistsTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral4069);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2472:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2472:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2473:1: (lv_parameters_21_0= ruleMapGetReference )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2473:1: (lv_parameters_21_0= ruleMapGetReference )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2474:3: lv_parameters_21_0= ruleMapGetReference
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleMapGetReference_in_ruleLiteral4096);
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
    public static final BitSet FOLLOW_13_in_ruleTml120 = new BitSet(new long[]{0x0000000000014010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleTml150 = new BitSet(new long[]{0x0000000000014010L});
    public static final BitSet FOLLOW_14_in_ruleTml163 = new BitSet(new long[]{0x0000000000488000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleTml185 = new BitSet(new long[]{0x0000000000488000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleTml212 = new BitSet(new long[]{0x0000000000488000L});
    public static final BitSet FOLLOW_15_in_ruleTml224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleTml241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression278 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePossibleExpression288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePossibleExpression330 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_rulePossibleExpression345 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression356 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleTopLevel_in_rulePossibleExpression376 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_rulePossibleExpression386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage466 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleMessage511 = new BitSet(new long[]{0x0000000000014010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMessage541 = new BitSet(new long[]{0x0000000000014010L});
    public static final BitSet FOLLOW_14_in_ruleMessage554 = new BitSet(new long[]{0x0000000000D80000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMessage576 = new BitSet(new long[]{0x0000000000D80000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMessage603 = new BitSet(new long[]{0x0000000000D80000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMessage630 = new BitSet(new long[]{0x0000000000D80000L});
    public static final BitSet FOLLOW_20_in_ruleMessage642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleMessage659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_entryRuleMap696 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMap706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapStart_in_ruleMap753 = new BitSet(new long[]{0x0000000000014000L});
    public static final BitSet FOLLOW_16_in_ruleMap763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleMap780 = new BitSet(new long[]{0x0000000000E80000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMap802 = new BitSet(new long[]{0x0000000000E80000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMap829 = new BitSet(new long[]{0x0000000000E80000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMap856 = new BitSet(new long[]{0x0000000000E80000L});
    public static final BitSet FOLLOW_21_in_ruleMap868 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMap885 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleMap900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapStart_in_entryRuleMapStart938 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapStart948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleMapStart992 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapStart1009 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMapStart1035 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty1072 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty1082 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleProperty1117 = new BitSet(new long[]{0x0000000000014010L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleProperty1147 = new BitSet(new long[]{0x0000000000014010L});
    public static final BitSet FOLLOW_16_in_ruleProperty1159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleProperty1176 = new BitSet(new long[]{0x0000000003000000L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleProperty1197 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleProperty1208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag1246 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag1256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleExpressionTag1291 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleExpressionTag1312 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_ruleExpressionTag1322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel1358 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel1368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel1413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement1449 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement1460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement1500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rulePathElement1524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rulePathElement1543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression1583 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression1593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleTmlExpression1628 = new BitSet(new long[]{0x0000000058000010L});
    public static final BitSet FOLLOW_30_in_ruleTmlExpression1646 = new BitSet(new long[]{0x0000000018000010L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression1681 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_30_in_ruleTmlExpression1692 = new BitSet(new long[]{0x0000000018000010L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression1713 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_31_in_ruleTmlExpression1725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression1761 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression1771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleExistsTmlExpression1806 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_ruleExistsTmlExpression1816 = new BitSet(new long[]{0x0000000058000010L});
    public static final BitSet FOLLOW_30_in_ruleExistsTmlExpression1834 = new BitSet(new long[]{0x0000000018000010L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression1869 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_30_in_ruleExistsTmlExpression1880 = new BitSet(new long[]{0x0000000018000010L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression1901 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_31_in_ruleExistsTmlExpression1913 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference1949 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference1959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_ruleMapGetReference2002 = new BitSet(new long[]{0x0000000018000010L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleMapGetReference2036 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_30_in_ruleMapGetReference2047 = new BitSet(new long[]{0x0000000018000010L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleMapGetReference2068 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression2106 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression2116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression2162 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_ruleOrExpression2181 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression2215 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression2253 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression2263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression2309 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_ruleAndExpression2328 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression2362 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression2400 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression2410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2456 = new BitSet(new long[]{0x0000003000000002L});
    public static final BitSet FOLLOW_36_in_ruleEqualityExpression2476 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleEqualityExpression2536 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression2570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression2609 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression2619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2665 = new BitSet(new long[]{0x000000C000000002L});
    public static final BitSet FOLLOW_38_in_ruleAdditiveExpression2677 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2698 = new BitSet(new long[]{0x000000C000000002L});
    public static final BitSet FOLLOW_39_in_ruleAdditiveExpression2716 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression2737 = new BitSet(new long[]{0x000000C000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression2776 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression2786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2832 = new BitSet(new long[]{0x0000010040000002L});
    public static final BitSet FOLLOW_40_in_ruleMultiplicativeExpression2852 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2886 = new BitSet(new long[]{0x0000010040000002L});
    public static final BitSet FOLLOW_30_in_ruleMultiplicativeExpression2912 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2946 = new BitSet(new long[]{0x0000010040000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression2985 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression2995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_ruleUnaryExpression3039 = new BitSet(new long[]{0x000F640320000310L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression3073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression3102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression3137 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression3147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression3193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_rulePrimaryExpression3210 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression3231 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_rulePrimaryExpression3241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName3279 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName3290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName3329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall3373 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall3383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_ruleFunctionCall3429 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_ruleFunctionCall3439 = new BitSet(new long[]{0x000F7E0320000310L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall3460 = new BitSet(new long[]{0x0000180000000000L});
    public static final BitSet FOLLOW_44_in_ruleFunctionCall3472 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall3493 = new BitSet(new long[]{0x0000180000000000L});
    public static final BitSet FOLLOW_43_in_ruleFunctionCall3505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral3541 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral3551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleLiteral3595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleLiteral3648 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_ruleLiteral3671 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3688 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_ruleLiteral3703 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3724 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_ruleLiteral3734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral3763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ruleLiteral3787 = new BitSet(new long[]{0x000FF60320000310L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3821 = new BitSet(new long[]{0x0000900000000000L});
    public static final BitSet FOLLOW_44_in_ruleLiteral3833 = new BitSet(new long[]{0x000F660320000310L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3854 = new BitSet(new long[]{0x0000900000000000L});
    public static final BitSet FOLLOW_47_in_ruleLiteral3866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_ruleLiteral3891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_ruleLiteral3928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_ruleLiteral3965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_ruleLiteral4002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral4042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral4069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_ruleLiteral4096 = new BitSet(new long[]{0x0000000000000002L});

}