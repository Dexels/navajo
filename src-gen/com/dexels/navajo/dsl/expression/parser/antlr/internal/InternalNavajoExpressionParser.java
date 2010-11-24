package com.dexels.navajo.dsl.expression.parser.antlr.internal; 

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
import com.dexels.navajo.dsl.expression.services.NavajoExpressionGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalNavajoExpressionParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_LITERALSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'.'", "'..'", "'['", "'/'", "']'", "'?'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'('", "')'", "'FORALL'", "','", "'{'", "'}'", "'NULL'", "'TODAY'", "'TRUE'", "'FALSE'"
    };
    public static final int RULE_ID=4;
    public static final int RULE_INT=5;
    public static final int RULE_LITERALSTRING=6;
    public static final int RULE_WS=9;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;

        public InternalNavajoExpressionParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g"; }



     	private NavajoExpressionGrammarAccess grammarAccess;
     	
        public InternalNavajoExpressionParser(TokenStream input, IAstFactory factory, NavajoExpressionGrammarAccess grammarAccess) {
            this(input);
            this.factory = factory;
            registerRules(grammarAccess.getGrammar());
            this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected InputStream getTokenFile() {
        	ClassLoader classLoader = getClass().getClassLoader();
        	return classLoader.getResourceAsStream("com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.tokens");
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "TopLevel";	
       	}
       	
       	@Override
       	protected NavajoExpressionGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start entryRuleTopLevel
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:77:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:78:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:79:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTopLevelRule(), currentNode); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel75);
            iv_ruleTopLevel=ruleTopLevel();
            _fsp--;

             current =iv_ruleTopLevel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel85); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:86:1: ruleTopLevel returns [EObject current=null] : ( () ( (lv_toplevelExpression_1_0= ruleOrExpression ) ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_1_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:91:6: ( ( () ( (lv_toplevelExpression_1_0= ruleOrExpression ) ) ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:92:1: ( () ( (lv_toplevelExpression_1_0= ruleOrExpression ) ) )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:92:1: ( () ( (lv_toplevelExpression_1_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:92:2: () ( (lv_toplevelExpression_1_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:92:2: ()
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:93:5: 
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:103:2: ( (lv_toplevelExpression_1_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:104:1: (lv_toplevelExpression_1_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:104:1: (lv_toplevelExpression_1_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:105:3: lv_toplevelExpression_1_0= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel140);
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:135:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:136:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:137:2: iv_rulePathElement= rulePathElement EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathElementRule(), currentNode); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement177);
            iv_rulePathElement=rulePathElement();
            _fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement188); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:144:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;

         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:149:6: ( (this_ID_0= RULE_ID | kw= '.' | kw= '..' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:150:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:150:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )
            int alt1=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt1=1;
                }
                break;
            case 10:
                {
                alt1=2;
                }
                break;
            case 11:
                {
                alt1=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("150:1: (this_ID_0= RULE_ID | kw= '.' | kw= '..' )", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:150:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement228); 

                    		current.merge(this_ID_0);
                        
                     
                        createLeafNode(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0(), null); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:159:2: kw= '.'
                    {
                    kw=(Token)input.LT(1);
                    match(input,10,FOLLOW_10_in_rulePathElement252); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathElementAccess().getFullStopKeyword_1(), null); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:166:2: kw= '..'
                    {
                    kw=(Token)input.LT(1);
                    match(input,11,FOLLOW_11_in_rulePathElement271); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:179:1: entryRulePathSequence returns [String current=null] : iv_rulePathSequence= rulePathSequence EOF ;
    public final String entryRulePathSequence() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathSequence = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:180:2: (iv_rulePathSequence= rulePathSequence EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:181:2: iv_rulePathSequence= rulePathSequence EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPathSequenceRule(), currentNode); 
            pushFollow(FOLLOW_rulePathSequence_in_entryRulePathSequence312);
            iv_rulePathSequence=rulePathSequence();
            _fsp--;

             current =iv_rulePathSequence.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathSequence323); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:188:1: rulePathSequence returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' ) ;
    public final AntlrDatatypeRuleToken rulePathSequence() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_PathElement_2 = null;

        AntlrDatatypeRuleToken this_PathElement_4 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:193:6: ( (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:194:1: (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:194:1: (kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:195:2: kw= '[' (kw= '/' )? this_PathElement_2= rulePathElement (kw= '/' this_PathElement_4= rulePathElement )* kw= ']'
            {
            kw=(Token)input.LT(1);
            match(input,12,FOLLOW_12_in_rulePathSequence361); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getPathSequenceAccess().getLeftSquareBracketKeyword_0(), null); 
                
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:200:1: (kw= '/' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==13) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:201:2: kw= '/'
                    {
                    kw=(Token)input.LT(1);
                    match(input,13,FOLLOW_13_in_rulePathSequence375); 

                            current.merge(kw);
                            createLeafNode(grammarAccess.getPathSequenceAccess().getSolidusKeyword_1(), null); 
                        

                    }
                    break;

            }

             
                    currentNode=createCompositeNode(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_2(), currentNode); 
                
            pushFollow(FOLLOW_rulePathElement_in_rulePathSequence399);
            this_PathElement_2=rulePathElement();
            _fsp--;


            		current.merge(this_PathElement_2);
                
             
                    currentNode = currentNode.getParent();
                
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:217:1: (kw= '/' this_PathElement_4= rulePathElement )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==13) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:218:2: kw= '/' this_PathElement_4= rulePathElement
            	    {
            	    kw=(Token)input.LT(1);
            	    match(input,13,FOLLOW_13_in_rulePathSequence418); 

            	            current.merge(kw);
            	            createLeafNode(grammarAccess.getPathSequenceAccess().getSolidusKeyword_3_0(), null); 
            	        
            	     
            	            currentNode=createCompositeNode(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_3_1(), currentNode); 
            	        
            	    pushFollow(FOLLOW_rulePathElement_in_rulePathSequence440);
            	    this_PathElement_4=rulePathElement();
            	    _fsp--;


            	    		current.merge(this_PathElement_4);
            	        
            	     
            	            currentNode = currentNode.getParent();
            	        

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            kw=(Token)input.LT(1);
            match(input,14,FOLLOW_14_in_rulePathSequence460); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:248:1: entryRuleTmlExpression returns [String current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final String entryRuleTmlExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:249:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:250:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression501);
            iv_ruleTmlExpression=ruleTmlExpression();
            _fsp--;

             current =iv_ruleTmlExpression.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression512); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:257:1: ruleTmlExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_PathSequence_0= rulePathSequence ;
    public final AntlrDatatypeRuleToken ruleTmlExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_PathSequence_0 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:262:6: (this_PathSequence_0= rulePathSequence )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:264:5: this_PathSequence_0= rulePathSequence
            {
             
                    currentNode=createCompositeNode(grammarAccess.getTmlExpressionAccess().getPathSequenceParserRuleCall(), currentNode); 
                
            pushFollow(FOLLOW_rulePathSequence_in_ruleTmlExpression558);
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:282:1: entryRuleExistsTmlExpression returns [String current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final String entryRuleExistsTmlExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:283:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:284:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getExistsTmlExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression603);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();
            _fsp--;

             current =iv_ruleExistsTmlExpression.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression614); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:291:1: ruleExistsTmlExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '?' this_TmlExpression_1= ruleTmlExpression ) ;
    public final AntlrDatatypeRuleToken ruleExistsTmlExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_TmlExpression_1 = null;


         setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:296:6: ( (kw= '?' this_TmlExpression_1= ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:297:1: (kw= '?' this_TmlExpression_1= ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:297:1: (kw= '?' this_TmlExpression_1= ruleTmlExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:298:2: kw= '?' this_TmlExpression_1= ruleTmlExpression
            {
            kw=(Token)input.LT(1);
            match(input,15,FOLLOW_15_in_ruleExistsTmlExpression652); 

                    current.merge(kw);
                    createLeafNode(grammarAccess.getExistsTmlExpressionAccess().getQuestionMarkKeyword_0(), null); 
                
             
                    currentNode=createCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getTmlExpressionParserRuleCall_1(), currentNode); 
                
            pushFollow(FOLLOW_ruleTmlExpression_in_ruleExistsTmlExpression674);
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:322:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:323:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:324:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression719);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression729); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:331:1: ruleOrExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:336:6: ( ( () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:337:1: ( () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:337:1: ( () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:337:2: () ( (lv_parameters_1_0= ruleAndExpression ) ) ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:337:2: ()
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:338:5: 
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:348:2: ( (lv_parameters_1_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:349:1: (lv_parameters_1_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:349:1: (lv_parameters_1_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:350:3: lv_parameters_1_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression784);
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:372:2: ( ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==16) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:372:3: ( (lv_operations_2_0= 'OR' ) ) ( (lv_parameters_3_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:372:3: ( (lv_operations_2_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:373:1: (lv_operations_2_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:373:1: (lv_operations_2_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:374:3: lv_operations_2_0= 'OR'
            	    {
            	    lv_operations_2_0=(Token)input.LT(1);
            	    match(input,16,FOLLOW_16_in_ruleOrExpression803); 

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

            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:393:2: ( (lv_parameters_3_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:394:1: (lv_parameters_3_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:394:1: (lv_parameters_3_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:395:3: lv_parameters_3_0= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression837);
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
            	    break loop4;
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:425:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:426:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:427:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression875);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression885); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:434:1: ruleAndExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:439:6: ( ( () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:440:1: ( () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:440:1: ( () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:440:2: () ( (lv_parameters_1_0= ruleEqualityExpression ) ) ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:440:2: ()
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:441:5: 
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:451:2: ( (lv_parameters_1_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:452:1: (lv_parameters_1_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:452:1: (lv_parameters_1_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:453:3: lv_parameters_1_0= ruleEqualityExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression940);
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:475:2: ( ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==17) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:475:3: ( (lv_operations_2_0= 'AND' ) ) ( (lv_parameters_3_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:475:3: ( (lv_operations_2_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:476:1: (lv_operations_2_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:476:1: (lv_operations_2_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:477:3: lv_operations_2_0= 'AND'
            	    {
            	    lv_operations_2_0=(Token)input.LT(1);
            	    match(input,17,FOLLOW_17_in_ruleAndExpression959); 

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

            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:496:2: ( (lv_parameters_3_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:497:1: (lv_parameters_3_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:497:1: (lv_parameters_3_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:498:3: lv_parameters_3_0= ruleEqualityExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_2_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression993);
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
            	    break loop5;
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:528:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:529:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:530:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getEqualityExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression1031);
            iv_ruleEqualityExpression=ruleEqualityExpression();
            _fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression1041); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:537:1: ruleEqualityExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        Token lv_operations_4_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:542:6: ( ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:543:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:543:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:543:2: () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:543:2: ()
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:544:5: 
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:554:2: ( (lv_parameters_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:555:1: (lv_parameters_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:555:1: (lv_parameters_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:556:3: lv_parameters_1_0= ruleAdditiveExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression1096);
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:578:2: ( ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) )?
            int alt6=3;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==18) ) {
                alt6=1;
            }
            else if ( (LA6_0==19) ) {
                alt6=2;
            }
            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:578:3: ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:578:3: ( ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:578:4: ( (lv_operations_2_0= '==' ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:578:4: ( (lv_operations_2_0= '==' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:579:1: (lv_operations_2_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:579:1: (lv_operations_2_0= '==' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:580:3: lv_operations_2_0= '=='
                    {
                    lv_operations_2_0=(Token)input.LT(1);
                    match(input,18,FOLLOW_18_in_ruleEqualityExpression1116); 

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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:599:2: ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:600:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:600:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:601:3: lv_parameters_3_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression1150);
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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:624:6: ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:624:6: ( ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:624:7: ( (lv_operations_4_0= '!=' ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:624:7: ( (lv_operations_4_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:625:1: (lv_operations_4_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:625:1: (lv_operations_4_0= '!=' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:626:3: lv_operations_4_0= '!='
                    {
                    lv_operations_4_0=(Token)input.LT(1);
                    match(input,19,FOLLOW_19_in_ruleEqualityExpression1176); 

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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:645:2: ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:646:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:646:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:647:3: lv_parameters_5_0= ruleAdditiveExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression1210);
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:677:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:678:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:679:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAdditiveExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression1249);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();
            _fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression1259); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:686:1: ruleAdditiveExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:691:6: ( ( () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:692:1: ( () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:692:1: ( () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:692:2: () ( (lv_parameters_1_0= ruleMultiplicativeExpression ) ) ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:692:2: ()
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:693:5: 
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:703:2: ( (lv_parameters_1_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:704:1: (lv_parameters_1_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:704:1: (lv_parameters_1_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:705:3: lv_parameters_1_0= ruleMultiplicativeExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1314);
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:727:2: ( ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) ) | ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) ) )*
            loop7:
            do {
                int alt7=3;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==20) ) {
                    alt7=1;
                }
                else if ( (LA7_0==21) ) {
                    alt7=2;
                }


                switch (alt7) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:727:3: ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:727:3: ( '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:727:5: '+' ( (lv_parameters_3_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,20,FOLLOW_20_in_ruleAdditiveExpression1326); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_2_0_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:731:1: ( (lv_parameters_3_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:732:1: (lv_parameters_3_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:732:1: (lv_parameters_3_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:733:3: lv_parameters_3_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1347);
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
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:756:6: ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:756:6: ( '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:756:8: '-' ( (lv_parameters_5_0= ruleMultiplicativeExpression ) )
            	    {
            	    match(input,21,FOLLOW_21_in_ruleAdditiveExpression1365); 

            	            createLeafNode(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_2_1_0(), null); 
            	        
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:760:1: ( (lv_parameters_5_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:761:1: (lv_parameters_5_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:761:1: (lv_parameters_5_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:762:3: lv_parameters_5_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1386);
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
            	    break loop7;
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:792:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:793:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:794:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getMultiplicativeExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression1425);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();
            _fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression1435); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:801:1: ruleMultiplicativeExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_2_0=null;
        Token lv_operations_4_0=null;
        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:806:6: ( ( () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:807:1: ( () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:807:1: ( () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:807:2: () ( (lv_parameters_1_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:807:2: ()
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:808:5: 
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:818:2: ( (lv_parameters_1_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:819:1: (lv_parameters_1_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:819:1: (lv_parameters_1_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:820:3: lv_parameters_1_0= ruleUnaryExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression1490);
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

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:842:2: ( ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) ) )*
            loop8:
            do {
                int alt8=3;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==22) ) {
                    alt8=1;
                }
                else if ( (LA8_0==13) ) {
                    alt8=2;
                }


                switch (alt8) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:842:3: ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:842:3: ( ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:842:4: ( (lv_operations_2_0= '*' ) ) ( (lv_parameters_3_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:842:4: ( (lv_operations_2_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:843:1: (lv_operations_2_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:843:1: (lv_operations_2_0= '*' )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:844:3: lv_operations_2_0= '*'
            	    {
            	    lv_operations_2_0=(Token)input.LT(1);
            	    match(input,22,FOLLOW_22_in_ruleMultiplicativeExpression1510); 

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

            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:863:2: ( (lv_parameters_3_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:864:1: (lv_parameters_3_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:864:1: (lv_parameters_3_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:865:3: lv_parameters_3_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_0_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression1544);
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
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:888:6: ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:888:6: ( ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:888:7: ( (lv_operations_4_0= '/' ) ) ( (lv_parameters_5_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:888:7: ( (lv_operations_4_0= '/' ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:889:1: (lv_operations_4_0= '/' )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:889:1: (lv_operations_4_0= '/' )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:890:3: lv_operations_4_0= '/'
            	    {
            	    lv_operations_4_0=(Token)input.LT(1);
            	    match(input,13,FOLLOW_13_in_ruleMultiplicativeExpression1570); 

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

            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:909:2: ( (lv_parameters_5_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:910:1: (lv_parameters_5_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:910:1: (lv_parameters_5_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:911:3: lv_parameters_5_0= ruleUnaryExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression1604);
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
            	    break loop8;
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:941:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:942:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:943:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getUnaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression1643);
            iv_ruleUnaryExpression=ruleUnaryExpression();
            _fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression1653); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:950:1: ruleUnaryExpression returns [EObject current=null] : ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_2_0 = null;

        EObject this_PrimaryExpression_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:955:6: ( ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:956:1: ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:956:1: ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==23) ) {
                alt9=1;
            }
            else if ( ((LA9_0>=RULE_ID && LA9_0<=RULE_LITERALSTRING)||LA9_0==12||LA9_0==15||LA9_0==24||LA9_0==26||LA9_0==28||(LA9_0>=30 && LA9_0<=33)) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("956:1: ( ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_3= rulePrimaryExpression )", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:956:2: ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:956:2: ( () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:956:3: () ( (lv_operations_1_0= '!' ) ) ( (lv_parameters_2_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:956:3: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:957:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:967:2: ( (lv_operations_1_0= '!' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:968:1: (lv_operations_1_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:968:1: (lv_operations_1_0= '!' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:969:3: lv_operations_1_0= '!'
                    {
                    lv_operations_1_0=(Token)input.LT(1);
                    match(input,23,FOLLOW_23_in_ruleUnaryExpression1706); 

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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:988:2: ( (lv_parameters_2_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:989:1: (lv_parameters_2_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:989:1: (lv_parameters_2_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:990:3: lv_parameters_2_0= rulePrimaryExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_2_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression1740);
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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1014:5: this_PrimaryExpression_3= rulePrimaryExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression1769);
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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1030:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1031:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1032:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getPrimaryExpressionRule(), currentNode); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression1804);
            iv_rulePrimaryExpression=rulePrimaryExpression();
            _fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression1814); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1039:1: rulePrimaryExpression returns [EObject current=null] : ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_parameters_1_0 = null;

        EObject lv_parameters_3_0 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1044:6: ( ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1045:1: ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1045:1: ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( ((LA10_0>=RULE_ID && LA10_0<=RULE_LITERALSTRING)||LA10_0==12||LA10_0==15||LA10_0==26||LA10_0==28||(LA10_0>=30 && LA10_0<=33)) ) {
                alt10=1;
            }
            else if ( (LA10_0==24) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1045:1: ( ( () ( (lv_parameters_1_0= ruleLiteral ) ) ) | ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' ) )", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1045:2: ( () ( (lv_parameters_1_0= ruleLiteral ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1045:2: ( () ( (lv_parameters_1_0= ruleLiteral ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1045:3: () ( (lv_parameters_1_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1045:3: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1046:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1056:2: ( (lv_parameters_1_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1057:1: (lv_parameters_1_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1057:1: (lv_parameters_1_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1058:3: lv_parameters_1_0= ruleLiteral
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression1870);
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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1081:6: ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1081:6: ( '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1081:8: '(' ( (lv_parameters_3_0= ruleOrExpression ) ) ')'
                    {
                    match(input,24,FOLLOW_24_in_rulePrimaryExpression1888); 

                            createLeafNode(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1085:1: ( (lv_parameters_3_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1086:1: (lv_parameters_3_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1086:1: (lv_parameters_3_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1087:3: lv_parameters_3_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression1909);
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

                    match(input,25,FOLLOW_25_in_rulePrimaryExpression1919); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1121:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1122:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1123:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFunctionCallRule(), currentNode); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall1956);
            iv_ruleFunctionCall=ruleFunctionCall();
            _fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall1966); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1130:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1135:6: ( ( ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1136:1: ( ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1136:1: ( ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1136:2: ( (lv_name_0_0= RULE_ID ) ) '(' ( ( RULE_ID ) ) ')'
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1136:2: ( (lv_name_0_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1137:1: (lv_name_0_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1137:1: (lv_name_0_0= RULE_ID )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1138:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionCall2008); 

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

            match(input,24,FOLLOW_24_in_ruleFunctionCall2023); 

                    createLeafNode(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1(), null); 
                
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1164:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1165:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1165:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1166:3: RULE_ID
            {

            			if (current==null) {
            	            current = factory.create(grammarAccess.getFunctionCallRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
                    
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionCall2041); 

            		createLeafNode(grammarAccess.getFunctionCallAccess().getOperandsFunctionOperandsCrossReference_2_0(), "operands"); 
            	

            }


            }

            match(input,25,FOLLOW_25_in_ruleFunctionCall2051); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1192:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1193:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1194:2: iv_ruleLiteral= ruleLiteral EOF
            {
             currentNode = createCompositeNode(grammarAccess.getLiteralRule(), currentNode); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral2089);
            iv_ruleLiteral=ruleLiteral();
            _fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral2099); 

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
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1201:1: ruleLiteral returns [EObject current=null] : ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) ) ;
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
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1206:6: ( ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1207:1: ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1207:1: ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) )
            int alt13=11;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt13=1;
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt13=2;
                }
                break;
            case 26:
                {
                alt13=3;
                }
                break;
            case RULE_ID:
                {
                alt13=4;
                }
                break;
            case 15:
                {
                alt13=5;
                }
                break;
            case 12:
                {
                alt13=6;
                }
                break;
            case 28:
                {
                alt13=7;
                }
                break;
            case 30:
                {
                alt13=8;
                }
                break;
            case 31:
                {
                alt13=9;
                }
                break;
            case 32:
                {
                alt13=10;
                }
                break;
            case 33:
                {
                alt13=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("1207:1: ( ( () ( (lv_valueString_1_0= RULE_INT ) ) ) | ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) ) | ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' ) | this_FunctionCall_11= ruleFunctionCall | ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) ) | ( () ( (lv_elements_15_0= ruleTmlExpression ) ) ) | ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' ) | ( () ( (lv_elements_23_0= 'NULL' ) ) ) | ( () ( (lv_elements_25_0= 'TODAY' ) ) ) | ( () ( (lv_elements_27_0= 'TRUE' ) ) ) | ( () ( (lv_elements_29_0= 'FALSE' ) ) ) )", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1207:2: ( () ( (lv_valueString_1_0= RULE_INT ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1207:2: ( () ( (lv_valueString_1_0= RULE_INT ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1207:3: () ( (lv_valueString_1_0= RULE_INT ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1207:3: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1208:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1218:2: ( (lv_valueString_1_0= RULE_INT ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1219:1: (lv_valueString_1_0= RULE_INT )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1219:1: (lv_valueString_1_0= RULE_INT )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1220:3: lv_valueString_1_0= RULE_INT
                    {
                    lv_valueString_1_0=(Token)input.LT(1);
                    match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleLiteral2151); 

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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1243:6: ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1243:6: ( () ( (lv_valueString_3_0= RULE_LITERALSTRING ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1243:7: () ( (lv_valueString_3_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1243:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1244:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1254:2: ( (lv_valueString_3_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1255:1: (lv_valueString_3_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1255:1: (lv_valueString_3_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1256:3: lv_valueString_3_0= RULE_LITERALSTRING
                    {
                    lv_valueString_3_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral2190); 

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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1279:6: ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1279:6: ( () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1279:7: () ( (lv_operations_5_0= 'FORALL' ) ) '(' ( (lv_valueString_7_0= RULE_LITERALSTRING ) ) ',' ( (lv_parameters_9_0= ruleOrExpression ) ) ')'
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1279:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1280:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1290:2: ( (lv_operations_5_0= 'FORALL' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1291:1: (lv_operations_5_0= 'FORALL' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1291:1: (lv_operations_5_0= 'FORALL' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1292:3: lv_operations_5_0= 'FORALL'
                    {
                    lv_operations_5_0=(Token)input.LT(1);
                    match(input,26,FOLLOW_26_in_ruleLiteral2230); 

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

                    match(input,24,FOLLOW_24_in_ruleLiteral2253); 

                            createLeafNode(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_2(), null); 
                        
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1315:1: ( (lv_valueString_7_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1316:1: (lv_valueString_7_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1316:1: (lv_valueString_7_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1317:3: lv_valueString_7_0= RULE_LITERALSTRING
                    {
                    lv_valueString_7_0=(Token)input.LT(1);
                    match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral2270); 

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

                    match(input,27,FOLLOW_27_in_ruleLiteral2285); 

                            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_2_4(), null); 
                        
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1343:1: ( (lv_parameters_9_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1344:1: (lv_parameters_9_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1344:1: (lv_parameters_9_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1345:3: lv_parameters_9_0= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_5_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral2306);
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

                    match(input,25,FOLLOW_25_in_ruleLiteral2316); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_6(), null); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1373:5: this_FunctionCall_11= ruleFunctionCall
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral2345);
                    this_FunctionCall_11=ruleFunctionCall();
                    _fsp--;

                     
                            current = this_FunctionCall_11; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1382:6: ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1382:6: ( () ( (lv_elements_13_0= ruleExistsTmlExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1382:7: () ( (lv_elements_13_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1382:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1383:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1393:2: ( (lv_elements_13_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1394:1: (lv_elements_13_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1394:1: (lv_elements_13_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1395:3: lv_elements_13_0= ruleExistsTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getElementsExistsTmlExpressionParserRuleCall_4_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral2381);
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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1418:6: ( () ( (lv_elements_15_0= ruleTmlExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1418:6: ( () ( (lv_elements_15_0= ruleTmlExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1418:7: () ( (lv_elements_15_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1418:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1419:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1429:2: ( (lv_elements_15_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1430:1: (lv_elements_15_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1430:1: (lv_elements_15_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1431:3: lv_elements_15_0= ruleTmlExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getElementsTmlExpressionParserRuleCall_5_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral2419);
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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1454:6: ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1454:6: ( () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1454:7: () ( (lv_expressionType_17_0= '{' ) ) ( (lv_parameters_18_0= ruleOrExpression ) )? ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )* '}'
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1454:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1455:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1465:2: ( (lv_expressionType_17_0= '{' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1466:1: (lv_expressionType_17_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1466:1: (lv_expressionType_17_0= '{' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1467:3: lv_expressionType_17_0= '{'
                    {
                    lv_expressionType_17_0=(Token)input.LT(1);
                    match(input,28,FOLLOW_28_in_ruleLiteral2454); 

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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1486:2: ( (lv_parameters_18_0= ruleOrExpression ) )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( ((LA11_0>=RULE_ID && LA11_0<=RULE_LITERALSTRING)||LA11_0==12||LA11_0==15||(LA11_0>=23 && LA11_0<=24)||LA11_0==26||LA11_0==28||(LA11_0>=30 && LA11_0<=33)) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1487:1: (lv_parameters_18_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1487:1: (lv_parameters_18_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1488:3: lv_parameters_18_0= ruleOrExpression
                            {
                             
                            	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_2_0(), currentNode); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral2488);
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1510:3: ( ',' ( (lv_parameters_20_0= ruleOrExpression ) ) )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==27) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1510:5: ',' ( (lv_parameters_20_0= ruleOrExpression ) )
                    	    {
                    	    match(input,27,FOLLOW_27_in_ruleLiteral2500); 

                    	            createLeafNode(grammarAccess.getLiteralAccess().getCommaKeyword_6_3_0(), null); 
                    	        
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1514:1: ( (lv_parameters_20_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1515:1: (lv_parameters_20_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1515:1: (lv_parameters_20_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1516:3: lv_parameters_20_0= ruleOrExpression
                    	    {
                    	     
                    	    	        currentNode=createCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_3_1_0(), currentNode); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral2521);
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
                    	    break loop12;
                        }
                    } while (true);

                    match(input,29,FOLLOW_29_in_ruleLiteral2533); 

                            createLeafNode(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_6_4(), null); 
                        

                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1543:6: ( () ( (lv_elements_23_0= 'NULL' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1543:6: ( () ( (lv_elements_23_0= 'NULL' ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1543:7: () ( (lv_elements_23_0= 'NULL' ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1543:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1544:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1554:2: ( (lv_elements_23_0= 'NULL' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1555:1: (lv_elements_23_0= 'NULL' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1555:1: (lv_elements_23_0= 'NULL' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1556:3: lv_elements_23_0= 'NULL'
                    {
                    lv_elements_23_0=(Token)input.LT(1);
                    match(input,30,FOLLOW_30_in_ruleLiteral2568); 

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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1576:6: ( () ( (lv_elements_25_0= 'TODAY' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1576:6: ( () ( (lv_elements_25_0= 'TODAY' ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1576:7: () ( (lv_elements_25_0= 'TODAY' ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1576:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1577:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1587:2: ( (lv_elements_25_0= 'TODAY' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1588:1: (lv_elements_25_0= 'TODAY' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1588:1: (lv_elements_25_0= 'TODAY' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1589:3: lv_elements_25_0= 'TODAY'
                    {
                    lv_elements_25_0=(Token)input.LT(1);
                    match(input,31,FOLLOW_31_in_ruleLiteral2616); 

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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1609:6: ( () ( (lv_elements_27_0= 'TRUE' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1609:6: ( () ( (lv_elements_27_0= 'TRUE' ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1609:7: () ( (lv_elements_27_0= 'TRUE' ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1609:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1610:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1620:2: ( (lv_elements_27_0= 'TRUE' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1621:1: (lv_elements_27_0= 'TRUE' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1621:1: (lv_elements_27_0= 'TRUE' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1622:3: lv_elements_27_0= 'TRUE'
                    {
                    lv_elements_27_0=(Token)input.LT(1);
                    match(input,32,FOLLOW_32_in_ruleLiteral2664); 

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
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1642:6: ( () ( (lv_elements_29_0= 'FALSE' ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1642:6: ( () ( (lv_elements_29_0= 'FALSE' ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1642:7: () ( (lv_elements_29_0= 'FALSE' ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1642:7: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1643:5: 
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

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1653:2: ( (lv_elements_29_0= 'FALSE' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1654:1: (lv_elements_29_0= 'FALSE' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1654:1: (lv_elements_29_0= 'FALSE' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1655:3: lv_elements_29_0= 'FALSE'
                    {
                    lv_elements_29_0=(Token)input.LT(1);
                    match(input,33,FOLLOW_33_in_ruleLiteral2712); 

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


 

    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement177 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_rulePathElement252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rulePathElement271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_entryRulePathSequence312 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathSequence323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rulePathSequence361 = new BitSet(new long[]{0x0000000000002C10L});
    public static final BitSet FOLLOW_13_in_rulePathSequence375 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rulePathElement_in_rulePathSequence399 = new BitSet(new long[]{0x0000000000006000L});
    public static final BitSet FOLLOW_13_in_rulePathSequence418 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rulePathElement_in_rulePathSequence440 = new BitSet(new long[]{0x0000000000006000L});
    public static final BitSet FOLLOW_14_in_rulePathSequence460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression501 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_ruleTmlExpression558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression603 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleExistsTmlExpression652 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleExistsTmlExpression674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression719 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression784 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_16_in_ruleOrExpression803 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression837 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression875 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression940 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_17_in_ruleAndExpression959 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression993 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression1031 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression1041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression1096 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_18_in_ruleEqualityExpression1116 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression1150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleEqualityExpression1176 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleEqualityExpression1210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression1249 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression1259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1314 = new BitSet(new long[]{0x0000000000300002L});
    public static final BitSet FOLLOW_20_in_ruleAdditiveExpression1326 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1347 = new BitSet(new long[]{0x0000000000300002L});
    public static final BitSet FOLLOW_21_in_ruleAdditiveExpression1365 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1386 = new BitSet(new long[]{0x0000000000300002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression1425 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression1435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression1490 = new BitSet(new long[]{0x0000000000402002L});
    public static final BitSet FOLLOW_22_in_ruleMultiplicativeExpression1510 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression1544 = new BitSet(new long[]{0x0000000000402002L});
    public static final BitSet FOLLOW_13_in_ruleMultiplicativeExpression1570 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression1604 = new BitSet(new long[]{0x0000000000402002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression1643 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression1653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleUnaryExpression1706 = new BitSet(new long[]{0x00000003D5009070L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression1740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression1769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression1804 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression1814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression1870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rulePrimaryExpression1888 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression1909 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_rulePrimaryExpression1919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall1956 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall1966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionCall2008 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleFunctionCall2023 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionCall2041 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleFunctionCall2051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral2089 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral2099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleLiteral2151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral2190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleLiteral2230 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleLiteral2253 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral2270 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleLiteral2285 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral2306 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleLiteral2316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral2345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral2381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral2419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleLiteral2454 = new BitSet(new long[]{0x00000003FD809070L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral2488 = new BitSet(new long[]{0x0000000028000000L});
    public static final BitSet FOLLOW_27_in_ruleLiteral2500 = new BitSet(new long[]{0x00000003D5809070L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral2521 = new BitSet(new long[]{0x0000000028000000L});
    public static final BitSet FOLLOW_29_in_ruleLiteral2533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_ruleLiteral2568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleLiteral2616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleLiteral2664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_ruleLiteral2712 = new BitSet(new long[]{0x0000000000000002L});

}