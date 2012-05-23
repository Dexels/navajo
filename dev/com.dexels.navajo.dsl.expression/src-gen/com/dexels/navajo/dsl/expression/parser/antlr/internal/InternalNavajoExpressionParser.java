package com.dexels.navajo.dsl.expression.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import com.dexels.navajo.dsl.expression.services.NavajoExpressionGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalNavajoExpressionParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_PARENT", "RULE_SQBRACKET_OPEN", "RULE_TML_SEPARATOR", "RULE_AT", "RULE_SQBRACKET_CLOSE", "RULE_TML_EXISTS", "RULE_DOLLAR", "RULE_XML_LT", "RULE_XML_GT", "RULE_XML_LTEQ", "RULE_XML_GTEQ", "RULE_NUMBER", "RULE_LITERALSTRING", "RULE_FORALL", "RULE_NULL", "RULE_TODAY", "RULE_TRUE", "RULE_FALSE", "RULE_WS", "'.'", "'('", "','", "')'", "'OR'", "'AND'", "'=='", "'!='", "'+'", "'-'", "'*'", "'!'", "'#'", "'{'", "'}'"
    };
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int RULE_PARENT=5;
    public static final int T__27=27;
    public static final int RULE_SQBRACKET_OPEN=6;
    public static final int T__26=26;
    public static final int RULE_XML_LT=12;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int RULE_LITERALSTRING=17;
    public static final int RULE_XML_GTEQ=15;
    public static final int RULE_TML_SEPARATOR=7;
    public static final int EOF=-1;
    public static final int RULE_NULL=19;
    public static final int RULE_TRUE=21;
    public static final int RULE_FORALL=18;
    public static final int RULE_DOLLAR=11;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int RULE_FALSE=22;
    public static final int RULE_TML_EXISTS=10;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_NUMBER=16;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int RULE_TODAY=20;
    public static final int RULE_SQBRACKET_CLOSE=9;
    public static final int RULE_XML_LTEQ=14;
    public static final int RULE_WS=23;
    public static final int RULE_XML_GT=13;
    public static final int RULE_AT=8;

    // delegates
    // delegators


        public InternalNavajoExpressionParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalNavajoExpressionParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalNavajoExpressionParser.tokenNames; }
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g"; }



     	private NavajoExpressionGrammarAccess grammarAccess;
     	
        public InternalNavajoExpressionParser(TokenStream input, NavajoExpressionGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "TopLevel";	
       	}
       	
       	@Override
       	protected NavajoExpressionGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleTopLevel"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:67:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:68:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:69:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             newCompositeNode(grammarAccess.getTopLevelRule()); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel75);
            iv_ruleTopLevel=ruleTopLevel();

            state._fsp--;

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
    // $ANTLR end "entryRuleTopLevel"


    // $ANTLR start "ruleTopLevel"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:76:1: ruleTopLevel returns [EObject current=null] : ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_0_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:79:28: ( ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:80:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:80:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:81:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:81:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:82:3: lv_toplevelExpression_0_0= ruleOrExpression
            {
             
            	        newCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel130);
            lv_toplevelExpression_0_0=ruleOrExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getTopLevelRule());
            	        }
                   		set(
                   			current, 
                   			"toplevelExpression",
                    		lv_toplevelExpression_0_0, 
                    		"OrExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTopLevel"


    // $ANTLR start "entryRulePathElement"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:106:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:107:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:108:2: iv_rulePathElement= rulePathElement EOF
            {
             newCompositeNode(grammarAccess.getPathElementRule()); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement166);
            iv_rulePathElement=rulePathElement();

            state._fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement177); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePathElement"


    // $ANTLR start "rulePathElement"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:115:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_PARENT_2=null;

         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:118:28: ( (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:119:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:119:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            int alt1=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt1=1;
                }
                break;
            case 24:
                {
                alt1=2;
                }
                break;
            case RULE_PARENT:
                {
                alt1=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:119:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement217); 

                    		current.merge(this_ID_0);
                        
                     
                        newLeafNode(this_ID_0, grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:128:2: kw= '.'
                    {
                    kw=(Token)match(input,24,FOLLOW_24_in_rulePathElement241); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:134:10: this_PARENT_2= RULE_PARENT
                    {
                    this_PARENT_2=(Token)match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_rulePathElement262); 

                    		current.merge(this_PARENT_2);
                        
                     
                        newLeafNode(this_PARENT_2, grammarAccess.getPathElementAccess().getPARENTTerminalRuleCall_2()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePathElement"


    // $ANTLR start "entryRuleTmlExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:149:1: entryRuleTmlExpression returns [EObject current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final EObject entryRuleTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:150:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:151:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             newCompositeNode(grammarAccess.getTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression307);
            iv_ruleTmlExpression=ruleTmlExpression();

            state._fsp--;

             current =iv_ruleTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression317); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTmlExpression"


    // $ANTLR start "ruleTmlExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:158:1: ruleTmlExpression returns [EObject current=null] : (this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleTmlExpression() throws RecognitionException {
        EObject current = null;

        Token this_SQBRACKET_OPEN_0=null;
        Token lv_absolute_1_0=null;
        Token lv_param_2_0=null;
        Token this_TML_SEPARATOR_4=null;
        Token this_SQBRACKET_CLOSE_6=null;
        AntlrDatatypeRuleToken lv_elements_3_0 = null;

        AntlrDatatypeRuleToken lv_elements_5_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:161:28: ( (this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:162:1: (this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:162:1: (this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:162:2: this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE
            {
            this_SQBRACKET_OPEN_0=(Token)match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression353); 
             
                newLeafNode(this_SQBRACKET_OPEN_0, grammarAccess.getTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:166:1: ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_TML_SEPARATOR) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:167:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:167:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:168:3: lv_absolute_1_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_1_0=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression369); 

                    			newLeafNode(lv_absolute_1_0, grammarAccess.getTmlExpressionAccess().getAbsoluteTML_SEPARATORTerminalRuleCall_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getTmlExpressionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"absolute",
                            		true, 
                            		"TML_SEPARATOR");
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:184:3: ( (lv_param_2_0= RULE_AT ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_AT) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:185:1: (lv_param_2_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:185:1: (lv_param_2_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:186:3: lv_param_2_0= RULE_AT
                    {
                    lv_param_2_0=(Token)match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleTmlExpression392); 

                    			newLeafNode(lv_param_2_0, grammarAccess.getTmlExpressionAccess().getParamATTerminalRuleCall_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getTmlExpressionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"param",
                            		true, 
                            		"AT");
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:202:3: ( (lv_elements_3_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:203:1: (lv_elements_3_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:203:1: (lv_elements_3_0= rulePathElement )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:204:3: lv_elements_3_0= rulePathElement
            {
             
            	        newCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression419);
            lv_elements_3_0=rulePathElement();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getTmlExpressionRule());
            	        }
                   		add(
                   			current, 
                   			"elements",
                    		lv_elements_3_0, 
                    		"PathElement");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:220:2: (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==RULE_TML_SEPARATOR) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:220:3: this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) )
            	    {
            	    this_TML_SEPARATOR_4=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression431); 
            	     
            	        newLeafNode(this_TML_SEPARATOR_4, grammarAccess.getTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_4_0()); 
            	        
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:224:1: ( (lv_elements_5_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:225:1: (lv_elements_5_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:225:1: (lv_elements_5_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:226:3: lv_elements_5_0= rulePathElement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression451);
            	    lv_elements_5_0=rulePathElement();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getTmlExpressionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"elements",
            	            		lv_elements_5_0, 
            	            		"PathElement");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            this_SQBRACKET_CLOSE_6=(Token)match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression464); 
             
                newLeafNode(this_SQBRACKET_CLOSE_6, grammarAccess.getTmlExpressionAccess().getSQBRACKET_CLOSETerminalRuleCall_5()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTmlExpression"


    // $ANTLR start "entryRuleExistsTmlExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:254:1: entryRuleExistsTmlExpression returns [EObject current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final EObject entryRuleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:255:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:256:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             newCompositeNode(grammarAccess.getExistsTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression499);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();

            state._fsp--;

             current =iv_ruleExistsTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression509); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExistsTmlExpression"


    // $ANTLR start "ruleExistsTmlExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:263:1: ruleExistsTmlExpression returns [EObject current=null] : (this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE ) ;
    public final EObject ruleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        Token this_TML_EXISTS_0=null;
        Token this_SQBRACKET_OPEN_1=null;
        Token lv_absolute_2_0=null;
        Token lv_param_3_0=null;
        Token this_TML_SEPARATOR_5=null;
        Token this_SQBRACKET_CLOSE_7=null;
        AntlrDatatypeRuleToken lv_elements_4_0 = null;

        AntlrDatatypeRuleToken lv_elements_6_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:266:28: ( (this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:267:1: (this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:267:1: (this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:267:2: this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE
            {
            this_TML_EXISTS_0=(Token)match(input,RULE_TML_EXISTS,FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression545); 
             
                newLeafNode(this_TML_EXISTS_0, grammarAccess.getExistsTmlExpressionAccess().getTML_EXISTSTerminalRuleCall_0()); 
                
            this_SQBRACKET_OPEN_1=(Token)match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression555); 
             
                newLeafNode(this_SQBRACKET_OPEN_1, grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_1()); 
                
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:275:1: ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_TML_SEPARATOR) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:276:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:276:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:277:3: lv_absolute_2_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_2_0=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression571); 

                    			newLeafNode(lv_absolute_2_0, grammarAccess.getExistsTmlExpressionAccess().getAbsoluteTML_SEPARATORTerminalRuleCall_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getExistsTmlExpressionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"absolute",
                            		true, 
                            		"TML_SEPARATOR");
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:293:3: ( (lv_param_3_0= RULE_AT ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_AT) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:294:1: (lv_param_3_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:294:1: (lv_param_3_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:295:3: lv_param_3_0= RULE_AT
                    {
                    lv_param_3_0=(Token)match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleExistsTmlExpression594); 

                    			newLeafNode(lv_param_3_0, grammarAccess.getExistsTmlExpressionAccess().getParamATTerminalRuleCall_3_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getExistsTmlExpressionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"param",
                            		true, 
                            		"AT");
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:311:3: ( (lv_elements_4_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:312:1: (lv_elements_4_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:312:1: (lv_elements_4_0= rulePathElement )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:313:3: lv_elements_4_0= rulePathElement
            {
             
            	        newCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression621);
            lv_elements_4_0=rulePathElement();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExistsTmlExpressionRule());
            	        }
                   		add(
                   			current, 
                   			"elements",
                    		lv_elements_4_0, 
                    		"PathElement");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:329:2: (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_TML_SEPARATOR) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:329:3: this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) )
            	    {
            	    this_TML_SEPARATOR_5=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression633); 
            	     
            	        newLeafNode(this_TML_SEPARATOR_5, grammarAccess.getExistsTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_5_0()); 
            	        
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:333:1: ( (lv_elements_6_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:334:1: (lv_elements_6_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:334:1: (lv_elements_6_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:335:3: lv_elements_6_0= rulePathElement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_5_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression653);
            	    lv_elements_6_0=rulePathElement();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExistsTmlExpressionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"elements",
            	            		lv_elements_6_0, 
            	            		"PathElement");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            this_SQBRACKET_CLOSE_7=(Token)match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression666); 
             
                newLeafNode(this_SQBRACKET_CLOSE_7, grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_CLOSETerminalRuleCall_6()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExistsTmlExpression"


    // $ANTLR start "entryRuleMapReferenceParams"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:363:1: entryRuleMapReferenceParams returns [EObject current=null] : iv_ruleMapReferenceParams= ruleMapReferenceParams EOF ;
    public final EObject entryRuleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapReferenceParams = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:364:2: (iv_ruleMapReferenceParams= ruleMapReferenceParams EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:365:2: iv_ruleMapReferenceParams= ruleMapReferenceParams EOF
            {
             newCompositeNode(grammarAccess.getMapReferenceParamsRule()); 
            pushFollow(FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams701);
            iv_ruleMapReferenceParams=ruleMapReferenceParams();

            state._fsp--;

             current =iv_ruleMapReferenceParams; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapReferenceParams711); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMapReferenceParams"


    // $ANTLR start "ruleMapReferenceParams"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:372:1: ruleMapReferenceParams returns [EObject current=null] : (otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')' ) ;
    public final EObject ruleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_getterParams_1_0 = null;

        EObject lv_getterParams_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:375:28: ( (otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:376:1: (otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:376:1: (otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:376:3: otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')'
            {
            otherlv_0=(Token)match(input,25,FOLLOW_25_in_ruleMapReferenceParams748); 

                	newLeafNode(otherlv_0, grammarAccess.getMapReferenceParamsAccess().getLeftParenthesisKeyword_0());
                
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:380:1: ( (lv_getterParams_1_0= ruleLiteral ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:381:1: (lv_getterParams_1_0= ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:381:1: (lv_getterParams_1_0= ruleLiteral )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:382:3: lv_getterParams_1_0= ruleLiteral
            {
             
            	        newCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams769);
            lv_getterParams_1_0=ruleLiteral();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMapReferenceParamsRule());
            	        }
                   		add(
                   			current, 
                   			"getterParams",
                    		lv_getterParams_1_0, 
                    		"Literal");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:398:2: (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==26) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:398:4: otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) )
            	    {
            	    otherlv_2=(Token)match(input,26,FOLLOW_26_in_ruleMapReferenceParams782); 

            	        	newLeafNode(otherlv_2, grammarAccess.getMapReferenceParamsAccess().getCommaKeyword_2_0());
            	        
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:402:1: ( (lv_getterParams_3_0= ruleLiteral ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:403:1: (lv_getterParams_3_0= ruleLiteral )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:403:1: (lv_getterParams_3_0= ruleLiteral )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:404:3: lv_getterParams_3_0= ruleLiteral
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams803);
            	    lv_getterParams_3_0=ruleLiteral();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMapReferenceParamsRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"getterParams",
            	            		lv_getterParams_3_0, 
            	            		"Literal");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            otherlv_4=(Token)match(input,27,FOLLOW_27_in_ruleMapReferenceParams817); 

                	newLeafNode(otherlv_4, grammarAccess.getMapReferenceParamsAccess().getRightParenthesisKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMapReferenceParams"


    // $ANTLR start "entryRuleMapGetReference"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:432:1: entryRuleMapGetReference returns [EObject current=null] : iv_ruleMapGetReference= ruleMapGetReference EOF ;
    public final EObject entryRuleMapGetReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapGetReference = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:433:2: (iv_ruleMapGetReference= ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:434:2: iv_ruleMapGetReference= ruleMapGetReference EOF
            {
             newCompositeNode(grammarAccess.getMapGetReferenceRule()); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference853);
            iv_ruleMapGetReference=ruleMapGetReference();

            state._fsp--;

             current =iv_ruleMapGetReference; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference863); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMapGetReference"


    // $ANTLR start "ruleMapGetReference"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:441:1: ruleMapGetReference returns [EObject current=null] : ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) ;
    public final EObject ruleMapGetReference() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        Token lv_elements_1_0=null;
        Token this_TML_SEPARATOR_2=null;
        Token lv_elements_3_0=null;
        EObject lv_referenceParams_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:444:28: ( ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:445:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:445:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:445:2: ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:445:2: ( (lv_operations_0_0= RULE_DOLLAR ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:446:1: (lv_operations_0_0= RULE_DOLLAR )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:446:1: (lv_operations_0_0= RULE_DOLLAR )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:447:3: lv_operations_0_0= RULE_DOLLAR
            {
            lv_operations_0_0=(Token)match(input,RULE_DOLLAR,FOLLOW_RULE_DOLLAR_in_ruleMapGetReference905); 

            			newLeafNode(lv_operations_0_0, grammarAccess.getMapGetReferenceAccess().getOperationsDOLLARTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMapGetReferenceRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"operations",
                    		lv_operations_0_0, 
                    		"DOLLAR");
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:463:2: ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_PARENT) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:463:3: ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:463:3: ( (lv_elements_1_0= RULE_PARENT ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:464:1: (lv_elements_1_0= RULE_PARENT )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:464:1: (lv_elements_1_0= RULE_PARENT )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:465:3: lv_elements_1_0= RULE_PARENT
            	    {
            	    lv_elements_1_0=(Token)match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_ruleMapGetReference928); 

            	    			newLeafNode(lv_elements_1_0, grammarAccess.getMapGetReferenceAccess().getElementsPARENTTerminalRuleCall_1_0_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getMapGetReferenceRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"elements",
            	            		lv_elements_1_0, 
            	            		"PARENT");
            	    	    

            	    }


            	    }

            	    this_TML_SEPARATOR_2=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference944); 
            	     
            	        newLeafNode(this_TML_SEPARATOR_2, grammarAccess.getMapGetReferenceAccess().getTML_SEPARATORTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:485:3: ( (lv_elements_3_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:486:1: (lv_elements_3_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:486:1: (lv_elements_3_0= RULE_ID )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:487:3: lv_elements_3_0= RULE_ID
            {
            lv_elements_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapGetReference962); 

            			newLeafNode(lv_elements_3_0, grammarAccess.getMapGetReferenceAccess().getElementsIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMapGetReferenceRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"elements",
                    		lv_elements_3_0, 
                    		"ID");
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:503:2: ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==25) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:504:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:504:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:505:3: lv_referenceParams_4_0= ruleMapReferenceParams
                    {
                     
                    	        newCompositeNode(grammarAccess.getMapGetReferenceAccess().getReferenceParamsMapReferenceParamsParserRuleCall_3_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference988);
                    lv_referenceParams_4_0=ruleMapReferenceParams();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMapGetReferenceRule());
                    	        }
                           		set(
                           			current, 
                           			"referenceParams",
                            		lv_referenceParams_4_0, 
                            		"MapReferenceParams");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMapGetReference"


    // $ANTLR start "entryRuleOrExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:529:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:530:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:531:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             newCompositeNode(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression1025);
            iv_ruleOrExpression=ruleOrExpression();

            state._fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression1035); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrExpression"


    // $ANTLR start "ruleOrExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:538:1: ruleOrExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:541:28: ( ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:542:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:542:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:542:2: ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:542:2: ( (lv_parameters_0_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:543:1: (lv_parameters_0_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:543:1: (lv_parameters_0_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:544:3: lv_parameters_0_0= ruleAndExpression
            {
             
            	        newCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression1081);
            lv_parameters_0_0=ruleAndExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getOrExpressionRule());
            	        }
                   		add(
                   			current, 
                   			"parameters",
                    		lv_parameters_0_0, 
                    		"AndExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:560:2: ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==28) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:560:3: ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:560:3: ( (lv_operations_1_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:561:1: (lv_operations_1_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:561:1: (lv_operations_1_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:562:3: lv_operations_1_0= 'OR'
            	    {
            	    lv_operations_1_0=(Token)match(input,28,FOLLOW_28_in_ruleOrExpression1100); 

            	            newLeafNode(lv_operations_1_0, grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getOrExpressionRule());
            	    	        }
            	           		addWithLastConsumed(current, "operations", lv_operations_1_0, "OR");
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:575:2: ( (lv_parameters_2_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:576:1: (lv_parameters_2_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:576:1: (lv_parameters_2_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:577:3: lv_parameters_2_0= ruleAndExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression1134);
            	    lv_parameters_2_0=ruleAndExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getOrExpressionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"parameters",
            	            		lv_parameters_2_0, 
            	            		"AndExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrExpression"


    // $ANTLR start "entryRuleAndExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:601:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:602:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:603:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             newCompositeNode(grammarAccess.getAndExpressionRule()); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression1172);
            iv_ruleAndExpression=ruleAndExpression();

            state._fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression1182); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAndExpression"


    // $ANTLR start "ruleAndExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:610:1: ruleAndExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:613:28: ( ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:614:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:614:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:614:2: ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:614:2: ( (lv_parameters_0_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:615:1: (lv_parameters_0_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:615:1: (lv_parameters_0_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:616:3: lv_parameters_0_0= ruleEqualityExpression
            {
             
            	        newCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression1228);
            lv_parameters_0_0=ruleEqualityExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAndExpressionRule());
            	        }
                   		add(
                   			current, 
                   			"parameters",
                    		lv_parameters_0_0, 
                    		"EqualityExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:632:2: ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==29) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:632:3: ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:632:3: ( (lv_operations_1_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:633:1: (lv_operations_1_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:633:1: (lv_operations_1_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:634:3: lv_operations_1_0= 'AND'
            	    {
            	    lv_operations_1_0=(Token)match(input,29,FOLLOW_29_in_ruleAndExpression1247); 

            	            newLeafNode(lv_operations_1_0, grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getAndExpressionRule());
            	    	        }
            	           		addWithLastConsumed(current, "operations", lv_operations_1_0, "AND");
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:647:2: ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:648:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:648:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:649:3: lv_parameters_2_0= ruleEqualityExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression1281);
            	    lv_parameters_2_0=ruleEqualityExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAndExpressionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"parameters",
            	            		lv_parameters_2_0, 
            	            		"EqualityExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAndExpression"


    // $ANTLR start "entryRuleEqualityExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:673:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:674:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:675:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             newCompositeNode(grammarAccess.getEqualityExpressionRule()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression1319);
            iv_ruleEqualityExpression=ruleEqualityExpression();

            state._fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression1329); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEqualityExpression"


    // $ANTLR start "ruleEqualityExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:682:1: ruleEqualityExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:685:28: ( ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:686:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:686:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:686:2: ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:686:2: ( (lv_parameters_0_0= ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:687:1: (lv_parameters_0_0= ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:687:1: (lv_parameters_0_0= ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:688:3: lv_parameters_0_0= ruleRelationalExpression
            {
             
            	        newCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression1375);
            lv_parameters_0_0=ruleRelationalExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
            	        }
                   		add(
                   			current, 
                   			"parameters",
                    		lv_parameters_0_0, 
                    		"RelationalExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:704:2: ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            int alt13=3;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==30) ) {
                alt13=1;
            }
            else if ( (LA13_0==31) ) {
                alt13=2;
            }
            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:704:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:704:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:704:4: ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:704:4: ( (lv_operations_1_0= '==' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:705:1: (lv_operations_1_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:705:1: (lv_operations_1_0= '==' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:706:3: lv_operations_1_0= '=='
                    {
                    lv_operations_1_0=(Token)match(input,30,FOLLOW_30_in_ruleEqualityExpression1395); 

                            newLeafNode(lv_operations_1_0, grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getEqualityExpressionRule());
                    	        }
                           		addWithLastConsumed(current, "operations", lv_operations_1_0, "==");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:719:2: ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:720:1: (lv_parameters_2_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:720:1: (lv_parameters_2_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:721:3: lv_parameters_2_0= ruleRelationalExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression1429);
                    lv_parameters_2_0=ruleRelationalExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_2_0, 
                            		"RelationalExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:738:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:738:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:738:7: ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:738:7: ( (lv_operations_3_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:739:1: (lv_operations_3_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:739:1: (lv_operations_3_0= '!=' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:740:3: lv_operations_3_0= '!='
                    {
                    lv_operations_3_0=(Token)match(input,31,FOLLOW_31_in_ruleEqualityExpression1455); 

                            newLeafNode(lv_operations_3_0, grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getEqualityExpressionRule());
                    	        }
                           		addWithLastConsumed(current, "operations", lv_operations_3_0, "!=");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:753:2: ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:754:1: (lv_parameters_4_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:754:1: (lv_parameters_4_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:755:3: lv_parameters_4_0= ruleRelationalExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression1489);
                    lv_parameters_4_0=ruleRelationalExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_4_0, 
                            		"RelationalExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEqualityExpression"


    // $ANTLR start "entryRuleRelationalExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:779:1: entryRuleRelationalExpression returns [EObject current=null] : iv_ruleRelationalExpression= ruleRelationalExpression EOF ;
    public final EObject entryRuleRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationalExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:780:2: (iv_ruleRelationalExpression= ruleRelationalExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:781:2: iv_ruleRelationalExpression= ruleRelationalExpression EOF
            {
             newCompositeNode(grammarAccess.getRelationalExpressionRule()); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression1528);
            iv_ruleRelationalExpression=ruleRelationalExpression();

            state._fsp--;

             current =iv_ruleRelationalExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRelationalExpression1538); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRelationalExpression"


    // $ANTLR start "ruleRelationalExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:788:1: ruleRelationalExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) ;
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


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:791:28: ( ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:792:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:792:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:792:2: () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:792:2: ()
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:793:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getRelationalExpressionAccess().getExpressionAction_0(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:798:2: ( (lv_parameters_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:799:1: (lv_parameters_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:799:1: (lv_parameters_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:800:3: lv_parameters_1_0= ruleAdditiveExpression
            {
             
            	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1593);
            lv_parameters_1_0=ruleAdditiveExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
            	        }
                   		add(
                   			current, 
                   			"parameters",
                    		lv_parameters_1_0, 
                    		"AdditiveExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:816:2: ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            int alt14=5;
            switch ( input.LA(1) ) {
                case RULE_XML_LT:
                    {
                    alt14=1;
                    }
                    break;
                case RULE_XML_GT:
                    {
                    alt14=2;
                    }
                    break;
                case RULE_XML_LTEQ:
                    {
                    alt14=3;
                    }
                    break;
                case RULE_XML_GTEQ:
                    {
                    alt14=4;
                    }
                    break;
            }

            switch (alt14) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:816:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:816:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:816:4: ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:816:4: ( (lv_operations_2_0= RULE_XML_LT ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:817:1: (lv_operations_2_0= RULE_XML_LT )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:817:1: (lv_operations_2_0= RULE_XML_LT )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:818:3: lv_operations_2_0= RULE_XML_LT
                    {
                    lv_operations_2_0=(Token)match(input,RULE_XML_LT,FOLLOW_RULE_XML_LT_in_ruleRelationalExpression1612); 

                    			newLeafNode(lv_operations_2_0, grammarAccess.getRelationalExpressionAccess().getOperationsXML_LTTerminalRuleCall_2_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRelationalExpressionRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"operations",
                            		lv_operations_2_0, 
                            		"XML_LT");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:834:2: ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:835:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:835:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:836:3: lv_parameters_3_0= ruleAdditiveExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1638);
                    lv_parameters_3_0=ruleAdditiveExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_3_0, 
                            		"AdditiveExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:853:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:853:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:853:7: ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:853:7: ( (lv_operations_4_0= RULE_XML_GT ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:854:1: (lv_operations_4_0= RULE_XML_GT )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:854:1: (lv_operations_4_0= RULE_XML_GT )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:855:3: lv_operations_4_0= RULE_XML_GT
                    {
                    lv_operations_4_0=(Token)match(input,RULE_XML_GT,FOLLOW_RULE_XML_GT_in_ruleRelationalExpression1663); 

                    			newLeafNode(lv_operations_4_0, grammarAccess.getRelationalExpressionAccess().getOperationsXML_GTTerminalRuleCall_2_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRelationalExpressionRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"operations",
                            		lv_operations_4_0, 
                            		"XML_GT");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:871:2: ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:872:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:872:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:873:3: lv_parameters_5_0= ruleAdditiveExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1689);
                    lv_parameters_5_0=ruleAdditiveExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_5_0, 
                            		"AdditiveExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:890:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:890:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:890:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:890:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:891:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:891:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:892:3: lv_operations_6_0= RULE_XML_LTEQ
                    {
                    lv_operations_6_0=(Token)match(input,RULE_XML_LTEQ,FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression1714); 

                    			newLeafNode(lv_operations_6_0, grammarAccess.getRelationalExpressionAccess().getOperationsXML_LTEQTerminalRuleCall_2_2_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRelationalExpressionRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"operations",
                            		lv_operations_6_0, 
                            		"XML_LTEQ");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:908:2: ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:909:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:909:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:910:3: lv_parameters_7_0= ruleAdditiveExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_2_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1740);
                    lv_parameters_7_0=ruleAdditiveExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_7_0, 
                            		"AdditiveExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:927:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:927:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:927:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:927:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:928:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:928:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:929:3: lv_operations_8_0= RULE_XML_GTEQ
                    {
                    lv_operations_8_0=(Token)match(input,RULE_XML_GTEQ,FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression1765); 

                    			newLeafNode(lv_operations_8_0, grammarAccess.getRelationalExpressionAccess().getOperationsXML_GTEQTerminalRuleCall_2_3_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRelationalExpressionRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"operations",
                            		lv_operations_8_0, 
                            		"XML_GTEQ");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:945:2: ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:946:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:946:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:947:3: lv_parameters_9_0= ruleAdditiveExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1791);
                    lv_parameters_9_0=ruleAdditiveExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_9_0, 
                            		"AdditiveExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRelationalExpression"


    // $ANTLR start "entryRuleAdditiveExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:971:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:972:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:973:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             newCompositeNode(grammarAccess.getAdditiveExpressionRule()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression1830);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();

            state._fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression1840); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAdditiveExpression"


    // $ANTLR start "ruleAdditiveExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:980:1: ruleAdditiveExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:983:28: ( ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:984:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:984:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:984:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:984:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:985:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:985:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:986:3: lv_parameters_0_0= ruleMultiplicativeExpression
            {
             
            	        newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1886);
            lv_parameters_0_0=ruleMultiplicativeExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
            	        }
                   		add(
                   			current, 
                   			"parameters",
                    		lv_parameters_0_0, 
                    		"MultiplicativeExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1002:2: ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            loop15:
            do {
                int alt15=3;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==32) ) {
                    alt15=1;
                }
                else if ( (LA15_0==33) ) {
                    alt15=2;
                }


                switch (alt15) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1002:3: (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1002:3: (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1002:5: otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,32,FOLLOW_32_in_ruleAdditiveExpression1900); 

            	        	newLeafNode(otherlv_1, grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0());
            	        
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1006:1: ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1007:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1007:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1008:3: lv_parameters_2_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1921);
            	    lv_parameters_2_0=ruleMultiplicativeExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"parameters",
            	            		lv_parameters_2_0, 
            	            		"MultiplicativeExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1025:6: (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1025:6: (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1025:8: otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,33,FOLLOW_33_in_ruleAdditiveExpression1941); 

            	        	newLeafNode(otherlv_3, grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0());
            	        
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1029:1: ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1030:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1030:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1031:3: lv_parameters_4_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1962);
            	    lv_parameters_4_0=ruleMultiplicativeExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"parameters",
            	            		lv_parameters_4_0, 
            	            		"MultiplicativeExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAdditiveExpression"


    // $ANTLR start "entryRuleMultiplicativeExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1055:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1056:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1057:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             newCompositeNode(grammarAccess.getMultiplicativeExpressionRule()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression2001);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();

            state._fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression2011); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultiplicativeExpression"


    // $ANTLR start "ruleMultiplicativeExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1064:1: ruleMultiplicativeExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1067:28: ( ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1068:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1068:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1068:2: ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1068:2: ( (lv_parameters_0_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1069:1: (lv_parameters_0_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1069:1: (lv_parameters_0_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1070:3: lv_parameters_0_0= ruleUnaryExpression
            {
             
            	        newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2057);
            lv_parameters_0_0=ruleUnaryExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
            	        }
                   		add(
                   			current, 
                   			"parameters",
                    		lv_parameters_0_0, 
                    		"UnaryExpression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1086:2: ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            loop16:
            do {
                int alt16=3;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==34) ) {
                    alt16=1;
                }
                else if ( (LA16_0==RULE_TML_SEPARATOR) ) {
                    alt16=2;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1086:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1086:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1086:4: ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1086:4: ( (lv_operations_1_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1087:1: (lv_operations_1_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1087:1: (lv_operations_1_0= '*' )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1088:3: lv_operations_1_0= '*'
            	    {
            	    lv_operations_1_0=(Token)match(input,34,FOLLOW_34_in_ruleMultiplicativeExpression2077); 

            	            newLeafNode(lv_operations_1_0, grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getMultiplicativeExpressionRule());
            	    	        }
            	           		addWithLastConsumed(current, "operations", lv_operations_1_0, "*");
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1101:2: ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1102:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1102:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1103:3: lv_parameters_2_0= ruleUnaryExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2111);
            	    lv_parameters_2_0=ruleUnaryExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"parameters",
            	            		lv_parameters_2_0, 
            	            		"UnaryExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1120:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1120:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1120:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1120:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1121:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1121:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1122:3: lv_operations_3_0= RULE_TML_SEPARATOR
            	    {
            	    lv_operations_3_0=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression2136); 

            	    			newLeafNode(lv_operations_3_0, grammarAccess.getMultiplicativeExpressionAccess().getOperationsTML_SEPARATORTerminalRuleCall_1_1_0_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getMultiplicativeExpressionRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"operations",
            	            		lv_operations_3_0, 
            	            		"TML_SEPARATOR");
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1138:2: ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1139:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1139:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1140:3: lv_parameters_4_0= ruleUnaryExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2162);
            	    lv_parameters_4_0=ruleUnaryExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"parameters",
            	            		lv_parameters_4_0, 
            	            		"UnaryExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


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

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultiplicativeExpression"


    // $ANTLR start "entryRuleUnaryExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1164:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1165:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1166:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             newCompositeNode(grammarAccess.getUnaryExpressionRule()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression2201);
            iv_ruleUnaryExpression=ruleUnaryExpression();

            state._fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression2211); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnaryExpression"


    // $ANTLR start "ruleUnaryExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1173:1: ruleUnaryExpression returns [EObject current=null] : ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        EObject lv_parameters_1_0 = null;

        EObject this_PrimaryExpression_2 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1176:28: ( ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1177:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1177:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==35) ) {
                alt17=1;
            }
            else if ( (LA17_0==RULE_ID||LA17_0==RULE_SQBRACKET_OPEN||(LA17_0>=RULE_TML_EXISTS && LA17_0<=RULE_DOLLAR)||(LA17_0>=RULE_NUMBER && LA17_0<=RULE_FALSE)||LA17_0==25||LA17_0==37) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1177:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1177:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1177:3: ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1177:3: ( (lv_operations_0_0= '!' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1178:1: (lv_operations_0_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1178:1: (lv_operations_0_0= '!' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1179:3: lv_operations_0_0= '!'
                    {
                    lv_operations_0_0=(Token)match(input,35,FOLLOW_35_in_ruleUnaryExpression2255); 

                            newLeafNode(lv_operations_0_0, grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getUnaryExpressionRule());
                    	        }
                           		addWithLastConsumed(current, "operations", lv_operations_0_0, "!");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1192:2: ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1193:1: (lv_parameters_1_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1193:1: (lv_parameters_1_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1194:3: lv_parameters_1_0= rulePrimaryExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2289);
                    lv_parameters_1_0=rulePrimaryExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getUnaryExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_1_0, 
                            		"PrimaryExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1212:5: this_PrimaryExpression_2= rulePrimaryExpression
                    {
                     
                            newCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2318);
                    this_PrimaryExpression_2=rulePrimaryExpression();

                    state._fsp--;

                     
                            current = this_PrimaryExpression_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnaryExpression"


    // $ANTLR start "entryRulePrimaryExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1228:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1229:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1230:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             newCompositeNode(grammarAccess.getPrimaryExpressionRule()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression2353);
            iv_rulePrimaryExpression=rulePrimaryExpression();

            state._fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression2363); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimaryExpression"


    // $ANTLR start "rulePrimaryExpression"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1237:1: rulePrimaryExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleLiteral ) ) | (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1240:28: ( ( ( (lv_parameters_0_0= ruleLiteral ) ) | (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' ) ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1241:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' ) )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1241:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_ID||LA18_0==RULE_SQBRACKET_OPEN||(LA18_0>=RULE_TML_EXISTS && LA18_0<=RULE_DOLLAR)||(LA18_0>=RULE_NUMBER && LA18_0<=RULE_FALSE)||LA18_0==37) ) {
                alt18=1;
            }
            else if ( (LA18_0==25) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1241:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1241:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1242:1: (lv_parameters_0_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1242:1: (lv_parameters_0_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1243:3: lv_parameters_0_0= ruleLiteral
                    {
                     
                    	        newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression2409);
                    lv_parameters_0_0=ruleLiteral();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getPrimaryExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_0_0, 
                            		"Literal");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1260:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1260:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1260:8: otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,25,FOLLOW_25_in_rulePrimaryExpression2428); 

                        	newLeafNode(otherlv_1, grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0());
                        
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1264:1: ( (lv_parameters_2_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1265:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1265:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1266:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression2449);
                    lv_parameters_2_0=ruleOrExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getPrimaryExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_2_0, 
                            		"OrExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_3=(Token)match(input,27,FOLLOW_27_in_rulePrimaryExpression2461); 

                        	newLeafNode(otherlv_3, grammarAccess.getPrimaryExpressionAccess().getRightParenthesisKeyword_1_2());
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimaryExpression"


    // $ANTLR start "entryRuleFunctionName"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1294:1: entryRuleFunctionName returns [String current=null] : iv_ruleFunctionName= ruleFunctionName EOF ;
    public final String entryRuleFunctionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFunctionName = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1295:2: (iv_ruleFunctionName= ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1296:2: iv_ruleFunctionName= ruleFunctionName EOF
            {
             newCompositeNode(grammarAccess.getFunctionNameRule()); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName2499);
            iv_ruleFunctionName=ruleFunctionName();

            state._fsp--;

             current =iv_ruleFunctionName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName2510); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunctionName"


    // $ANTLR start "ruleFunctionName"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1303:1: ruleFunctionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFunctionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1306:28: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1307:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName2549); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getFunctionNameAccess().getIDTerminalRuleCall()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunctionName"


    // $ANTLR start "entryRuleFunctionCall"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1322:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1323:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1324:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             newCompositeNode(grammarAccess.getFunctionCallRule()); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall2593);
            iv_ruleFunctionCall=ruleFunctionCall();

            state._fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall2603); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunctionCall"


    // $ANTLR start "ruleFunctionCall"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1331:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')' ) ;
    public final EObject ruleFunctionCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1334:28: ( ( ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')' ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1335:1: ( ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')' )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1335:1: ( ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')' )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1335:2: ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')'
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1335:2: ( (lv_name_0_0= ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1336:1: (lv_name_0_0= ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1336:1: (lv_name_0_0= ruleFunctionName )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1337:3: lv_name_0_0= ruleFunctionName
            {
             
            	        newCompositeNode(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleFunctionName_in_ruleFunctionCall2649);
            lv_name_0_0=ruleFunctionName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getFunctionCallRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"FunctionName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleFunctionCall2661); 

                	newLeafNode(otherlv_1, grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1());
                
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1357:1: ( (lv_parameters_2_0= ruleOrExpression ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==RULE_ID||LA19_0==RULE_SQBRACKET_OPEN||(LA19_0>=RULE_TML_EXISTS && LA19_0<=RULE_DOLLAR)||(LA19_0>=RULE_NUMBER && LA19_0<=RULE_FALSE)||LA19_0==25||LA19_0==35||LA19_0==37) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1358:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1358:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1359:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall2682);
                    lv_parameters_2_0=ruleOrExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFunctionCallRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_2_0, 
                            		"OrExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1375:3: (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==26) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1375:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,26,FOLLOW_26_in_ruleFunctionCall2696); 

            	        	newLeafNode(otherlv_3, grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0());
            	        
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1379:1: ( (lv_parameters_4_0= ruleOrExpression ) )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1380:1: (lv_parameters_4_0= ruleOrExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1380:1: (lv_parameters_4_0= ruleOrExpression )
            	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1381:3: lv_parameters_4_0= ruleOrExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall2717);
            	    lv_parameters_4_0=ruleOrExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getFunctionCallRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"parameters",
            	            		lv_parameters_4_0, 
            	            		"OrExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            otherlv_5=(Token)match(input,27,FOLLOW_27_in_ruleFunctionCall2731); 

                	newLeafNode(otherlv_5, grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunctionCall"


    // $ANTLR start "entryRuleDateLiteral"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1409:1: entryRuleDateLiteral returns [EObject current=null] : iv_ruleDateLiteral= ruleDateLiteral EOF ;
    public final EObject entryRuleDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDateLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1410:2: (iv_ruleDateLiteral= ruleDateLiteral EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1411:2: iv_ruleDateLiteral= ruleDateLiteral EOF
            {
             newCompositeNode(grammarAccess.getDateLiteralRule()); 
            pushFollow(FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral2767);
            iv_ruleDateLiteral=ruleDateLiteral();

            state._fsp--;

             current =iv_ruleDateLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDateLiteral2777); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDateLiteral"


    // $ANTLR start "ruleDateLiteral"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1418:1: ruleDateLiteral returns [EObject current=null] : ( () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER ) ;
    public final EObject ruleDateLiteral() throws RecognitionException {
        EObject current = null;

        Token this_NUMBER_1=null;
        Token otherlv_2=null;
        Token this_NUMBER_3=null;
        Token otherlv_4=null;
        Token this_NUMBER_5=null;
        Token otherlv_6=null;
        Token this_NUMBER_7=null;
        Token otherlv_8=null;
        Token this_NUMBER_9=null;
        Token otherlv_10=null;
        Token this_NUMBER_11=null;

         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1421:28: ( ( () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1422:1: ( () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1422:1: ( () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1422:2: () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1422:2: ()
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1423:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getDateLiteralAccess().getExpressionAction_0(),
                        current);
                

            }

            this_NUMBER_1=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral2822); 
             
                newLeafNode(this_NUMBER_1, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_1()); 
                
            otherlv_2=(Token)match(input,36,FOLLOW_36_in_ruleDateLiteral2833); 

                	newLeafNode(otherlv_2, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_2());
                
            this_NUMBER_3=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral2844); 
             
                newLeafNode(this_NUMBER_3, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_3()); 
                
            otherlv_4=(Token)match(input,36,FOLLOW_36_in_ruleDateLiteral2855); 

                	newLeafNode(otherlv_4, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_4());
                
            this_NUMBER_5=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral2866); 
             
                newLeafNode(this_NUMBER_5, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_5()); 
                
            otherlv_6=(Token)match(input,36,FOLLOW_36_in_ruleDateLiteral2877); 

                	newLeafNode(otherlv_6, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_6());
                
            this_NUMBER_7=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral2888); 
             
                newLeafNode(this_NUMBER_7, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_7()); 
                
            otherlv_8=(Token)match(input,36,FOLLOW_36_in_ruleDateLiteral2899); 

                	newLeafNode(otherlv_8, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_8());
                
            this_NUMBER_9=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral2910); 
             
                newLeafNode(this_NUMBER_9, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_9()); 
                
            otherlv_10=(Token)match(input,36,FOLLOW_36_in_ruleDateLiteral2921); 

                	newLeafNode(otherlv_10, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_10());
                
            this_NUMBER_11=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral2932); 
             
                newLeafNode(this_NUMBER_11, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_11()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDateLiteral"


    // $ANTLR start "entryRuleLiteral"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1480:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1481:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1482:2: iv_ruleLiteral= ruleLiteral EOF
            {
             newCompositeNode(grammarAccess.getLiteralRule()); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral2967);
            iv_ruleLiteral=ruleLiteral();

            state._fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral2977); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLiteral"


    // $ANTLR start "ruleLiteral"
    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1489:1: ruleLiteral returns [EObject current=null] : ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) ) ;
    public final EObject ruleLiteral() throws RecognitionException {
        EObject current = null;

        Token this_NUMBER_1=null;
        Token lv_valueString_2_0=null;
        Token lv_operations_3_0=null;
        Token otherlv_4=null;
        Token lv_valueString_5_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_expressionType_10_0=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
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


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1492:28: ( ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) ) )
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1493:1: ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )
            {
            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1493:1: ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )
            int alt23=13;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1493:2: ( () this_NUMBER_1= RULE_NUMBER )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1493:2: ( () this_NUMBER_1= RULE_NUMBER )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1493:3: () this_NUMBER_1= RULE_NUMBER
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1493:3: ()
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1494:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getLiteralAccess().getExpressionAction_0_0(),
                                current);
                        

                    }

                    this_NUMBER_1=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleLiteral3023); 
                     
                        newLeafNode(this_NUMBER_1, grammarAccess.getLiteralAccess().getNUMBERTerminalRuleCall_0_1()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1504:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1504:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1505:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1505:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1506:3: lv_valueString_2_0= RULE_LITERALSTRING
                    {
                    lv_valueString_2_0=(Token)match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3046); 

                    			newLeafNode(lv_valueString_2_0, grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"valueString",
                            		lv_valueString_2_0, 
                            		"LITERALSTRING");
                    	    

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1523:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1523:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1523:7: ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')'
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1523:7: ( (lv_operations_3_0= RULE_FORALL ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1524:1: (lv_operations_3_0= RULE_FORALL )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1524:1: (lv_operations_3_0= RULE_FORALL )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1525:3: lv_operations_3_0= RULE_FORALL
                    {
                    lv_operations_3_0=(Token)match(input,RULE_FORALL,FOLLOW_RULE_FORALL_in_ruleLiteral3075); 

                    			newLeafNode(lv_operations_3_0, grammarAccess.getLiteralAccess().getOperationsFORALLTerminalRuleCall_2_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"operations",
                            		lv_operations_3_0, 
                            		"FORALL");
                    	    

                    }


                    }

                    otherlv_4=(Token)match(input,25,FOLLOW_25_in_ruleLiteral3092); 

                        	newLeafNode(otherlv_4, grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1());
                        
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1545:1: ( (lv_valueString_5_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1546:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1546:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1547:3: lv_valueString_5_0= RULE_LITERALSTRING
                    {
                    lv_valueString_5_0=(Token)match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3109); 

                    			newLeafNode(lv_valueString_5_0, grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"valueString",
                            		lv_valueString_5_0, 
                            		"LITERALSTRING");
                    	    

                    }


                    }

                    otherlv_6=(Token)match(input,26,FOLLOW_26_in_ruleLiteral3126); 

                        	newLeafNode(otherlv_6, grammarAccess.getLiteralAccess().getCommaKeyword_2_3());
                        
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1567:1: ( (lv_parameters_7_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1568:1: (lv_parameters_7_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1568:1: (lv_parameters_7_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1569:3: lv_parameters_7_0= ruleOrExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0()); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3147);
                    lv_parameters_7_0=ruleOrExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLiteralRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_7_0, 
                            		"OrExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_8=(Token)match(input,27,FOLLOW_27_in_ruleLiteral3159); 

                        	newLeafNode(otherlv_8, grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5());
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1590:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1590:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1591:1: (lv_parameters_9_0= ruleFunctionCall )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1591:1: (lv_parameters_9_0= ruleFunctionCall )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1592:3: lv_parameters_9_0= ruleFunctionCall
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersFunctionCallParserRuleCall_3_0()); 
                    	    
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral3187);
                    lv_parameters_9_0=ruleFunctionCall();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLiteralRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_9_0, 
                            		"FunctionCall");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1609:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1609:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1609:7: ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}'
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1609:7: ( (lv_expressionType_10_0= '{' ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1610:1: (lv_expressionType_10_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1610:1: (lv_expressionType_10_0= '{' )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1611:3: lv_expressionType_10_0= '{'
                    {
                    lv_expressionType_10_0=(Token)match(input,37,FOLLOW_37_in_ruleLiteral3212); 

                            newLeafNode(lv_expressionType_10_0, grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		setWithLastConsumed(current, "expressionType", lv_expressionType_10_0, "{");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1624:2: ( (lv_parameters_11_0= ruleOrExpression ) )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==RULE_ID||LA21_0==RULE_SQBRACKET_OPEN||(LA21_0>=RULE_TML_EXISTS && LA21_0<=RULE_DOLLAR)||(LA21_0>=RULE_NUMBER && LA21_0<=RULE_FALSE)||LA21_0==25||LA21_0==35||LA21_0==37) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1625:1: (lv_parameters_11_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1625:1: (lv_parameters_11_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1626:3: lv_parameters_11_0= ruleOrExpression
                            {
                             
                            	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3246);
                            lv_parameters_11_0=ruleOrExpression();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getLiteralRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"parameters",
                                    		lv_parameters_11_0, 
                                    		"OrExpression");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }
                            break;

                    }

                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1642:3: (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==26) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1642:5: otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    {
                    	    otherlv_12=(Token)match(input,26,FOLLOW_26_in_ruleLiteral3260); 

                    	        	newLeafNode(otherlv_12, grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0());
                    	        
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1646:1: ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1647:1: (lv_parameters_13_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1647:1: (lv_parameters_13_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1648:3: lv_parameters_13_0= ruleOrExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral3281);
                    	    lv_parameters_13_0=ruleOrExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getLiteralRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_13_0, 
                    	            		"OrExpression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop22;
                        }
                    } while (true);

                    otherlv_14=(Token)match(input,38,FOLLOW_38_in_ruleLiteral3295); 

                        	newLeafNode(otherlv_14, grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3());
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1669:6: ( (lv_elements_15_0= RULE_NULL ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1669:6: ( (lv_elements_15_0= RULE_NULL ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1670:1: (lv_elements_15_0= RULE_NULL )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1670:1: (lv_elements_15_0= RULE_NULL )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1671:3: lv_elements_15_0= RULE_NULL
                    {
                    lv_elements_15_0=(Token)match(input,RULE_NULL,FOLLOW_RULE_NULL_in_ruleLiteral3319); 

                    			newLeafNode(lv_elements_15_0, grammarAccess.getLiteralAccess().getElementsNULLTerminalRuleCall_5_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"elements",
                            		lv_elements_15_0, 
                            		"NULL");
                    	    

                    }


                    }


                    }
                    break;
                case 7 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1688:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1688:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1689:1: (lv_elements_16_0= RULE_TODAY )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1689:1: (lv_elements_16_0= RULE_TODAY )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1690:3: lv_elements_16_0= RULE_TODAY
                    {
                    lv_elements_16_0=(Token)match(input,RULE_TODAY,FOLLOW_RULE_TODAY_in_ruleLiteral3347); 

                    			newLeafNode(lv_elements_16_0, grammarAccess.getLiteralAccess().getElementsTODAYTerminalRuleCall_6_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"elements",
                            		lv_elements_16_0, 
                            		"TODAY");
                    	    

                    }


                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1707:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1707:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1708:1: (lv_elements_17_0= RULE_TRUE )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1708:1: (lv_elements_17_0= RULE_TRUE )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1709:3: lv_elements_17_0= RULE_TRUE
                    {
                    lv_elements_17_0=(Token)match(input,RULE_TRUE,FOLLOW_RULE_TRUE_in_ruleLiteral3375); 

                    			newLeafNode(lv_elements_17_0, grammarAccess.getLiteralAccess().getElementsTRUETerminalRuleCall_7_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"elements",
                            		lv_elements_17_0, 
                            		"TRUE");
                    	    

                    }


                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1726:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1726:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1727:1: (lv_elements_18_0= RULE_FALSE )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1727:1: (lv_elements_18_0= RULE_FALSE )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1728:3: lv_elements_18_0= RULE_FALSE
                    {
                    lv_elements_18_0=(Token)match(input,RULE_FALSE,FOLLOW_RULE_FALSE_in_ruleLiteral3403); 

                    			newLeafNode(lv_elements_18_0, grammarAccess.getLiteralAccess().getElementsFALSETerminalRuleCall_8_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"elements",
                            		lv_elements_18_0, 
                            		"FALSE");
                    	    

                    }


                    }


                    }
                    break;
                case 10 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1745:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1745:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1746:1: (lv_parameters_19_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1746:1: (lv_parameters_19_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1747:3: lv_parameters_19_0= ruleTmlExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral3435);
                    lv_parameters_19_0=ruleTmlExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLiteralRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_19_0, 
                            		"TmlExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 11 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1764:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1764:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1765:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1765:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1766:3: lv_parameters_20_0= ruleExistsTmlExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0()); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral3462);
                    lv_parameters_20_0=ruleExistsTmlExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLiteralRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_20_0, 
                            		"ExistsTmlExpression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 12 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1783:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1783:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1784:1: (lv_parameters_21_0= ruleMapGetReference )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1784:1: (lv_parameters_21_0= ruleMapGetReference )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1785:3: lv_parameters_21_0= ruleMapGetReference
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMapGetReference_in_ruleLiteral3489);
                    lv_parameters_21_0=ruleMapGetReference();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLiteralRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_21_0, 
                            		"MapGetReference");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 13 :
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1802:6: ( (lv_parameters_22_0= ruleDateLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1802:6: ( (lv_parameters_22_0= ruleDateLiteral ) )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1803:1: (lv_parameters_22_0= ruleDateLiteral )
                    {
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1803:1: (lv_parameters_22_0= ruleDateLiteral )
                    // ../com.dexels.navajo.dsl.expression/src-gen/com/dexels/navajo/dsl/expression/parser/antlr/internal/InternalNavajoExpression.g:1804:3: lv_parameters_22_0= ruleDateLiteral
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersDateLiteralParserRuleCall_12_0()); 
                    	    
                    pushFollow(FOLLOW_ruleDateLiteral_in_ruleLiteral3516);
                    lv_parameters_22_0=ruleDateLiteral();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLiteralRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_22_0, 
                            		"DateLiteral");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLiteral"

    // Delegated rules


    protected DFA23 dfa23 = new DFA23(this);
    static final String DFA23_eotS =
        "\17\uffff";
    static final String DFA23_eofS =
        "\1\uffff\1\16\15\uffff";
    static final String DFA23_minS =
        "\1\4\1\7\15\uffff";
    static final String DFA23_maxS =
        "\1\45\1\46\15\uffff";
    static final String DFA23_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1"+
        "\1";
    static final String DFA23_specialS =
        "\17\uffff}>";
    static final String[] DFA23_transitionS = {
            "\1\4\1\uffff\1\12\3\uffff\1\13\1\14\4\uffff\1\1\1\2\1\3\1\6"+
            "\1\7\1\10\1\11\16\uffff\1\5",
            "\1\16\4\uffff\4\16\12\uffff\11\16\1\uffff\1\15\1\uffff\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "1493:1: ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement166 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rulePathElement241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARENT_in_rulePathElement262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression307 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression353 = new BitSet(new long[]{0x00000000010001B0L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression369 = new BitSet(new long[]{0x00000000010001B0L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleTmlExpression392 = new BitSet(new long[]{0x00000000010001B0L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression419 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression431 = new BitSet(new long[]{0x00000000010001B0L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression451 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression499 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression545 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression555 = new BitSet(new long[]{0x00000000010001B0L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression571 = new BitSet(new long[]{0x00000000010001B0L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleExistsTmlExpression594 = new BitSet(new long[]{0x00000000010001B0L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression621 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression633 = new BitSet(new long[]{0x00000000010001B0L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression653 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams701 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapReferenceParams711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleMapReferenceParams748 = new BitSet(new long[]{0x00000020007F0C50L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams769 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_26_in_ruleMapReferenceParams782 = new BitSet(new long[]{0x00000020007F0C50L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams803 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_27_in_ruleMapReferenceParams817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference853 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOLLAR_in_ruleMapGetReference905 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_RULE_PARENT_in_ruleMapGetReference928 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference944 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapGetReference962 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression1025 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression1035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression1081 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_ruleOrExpression1100 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression1134 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression1172 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression1182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression1228 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_29_in_ruleAndExpression1247 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression1281 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression1319 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression1329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression1375 = new BitSet(new long[]{0x00000000C0000002L});
    public static final BitSet FOLLOW_30_in_ruleEqualityExpression1395 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression1429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleEqualityExpression1455 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression1489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression1528 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRelationalExpression1538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1593 = new BitSet(new long[]{0x000000000000F002L});
    public static final BitSet FOLLOW_RULE_XML_LT_in_ruleRelationalExpression1612 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GT_in_ruleRelationalExpression1663 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression1714 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression1765 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression1791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression1830 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression1840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1886 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_32_in_ruleAdditiveExpression1900 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1921 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_33_in_ruleAdditiveExpression1941 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression1962 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression2001 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression2011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2057 = new BitSet(new long[]{0x0000000400000082L});
    public static final BitSet FOLLOW_34_in_ruleMultiplicativeExpression2077 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2111 = new BitSet(new long[]{0x0000000400000082L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression2136 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression2162 = new BitSet(new long[]{0x0000000400000082L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression2201 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression2211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleUnaryExpression2255 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression2318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression2353 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression2363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression2409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rulePrimaryExpression2428 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression2449 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_rulePrimaryExpression2461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName2499 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName2510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName2549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall2593 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall2603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_ruleFunctionCall2649 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleFunctionCall2661 = new BitSet(new long[]{0x000000280E7F0C50L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall2682 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_26_in_ruleFunctionCall2696 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall2717 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_27_in_ruleFunctionCall2731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral2767 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDateLiteral2777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral2822 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_36_in_ruleDateLiteral2833 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral2844 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_36_in_ruleDateLiteral2855 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral2866 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_36_in_ruleDateLiteral2877 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral2888 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_36_in_ruleDateLiteral2899 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral2910 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_36_in_ruleDateLiteral2921 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral2932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral2967 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral2977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleLiteral3023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3046 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FORALL_in_ruleLiteral3075 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleLiteral3092 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral3109 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_ruleLiteral3126 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3147 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleLiteral3159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral3187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleLiteral3212 = new BitSet(new long[]{0x00000068067F0C50L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3246 = new BitSet(new long[]{0x0000004004000000L});
    public static final BitSet FOLLOW_26_in_ruleLiteral3260 = new BitSet(new long[]{0x00000028027F0C50L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral3281 = new BitSet(new long[]{0x0000004004000000L});
    public static final BitSet FOLLOW_38_in_ruleLiteral3295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NULL_in_ruleLiteral3319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TODAY_in_ruleLiteral3347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TRUE_in_ruleLiteral3375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FALSE_in_ruleLiteral3403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral3435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral3462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_ruleLiteral3489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_ruleLiteral3516 = new BitSet(new long[]{0x0000000000000002L});

}