package com.dexels.navajo.dsl.tsl.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
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
    public static final int RULE_REQUIRED_START_TAG=33;
    public static final int RULE_XMLHEAD=4;
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
    public static final int RULE_DEBUG_START_TAG=43;
    public static final int RULE_MAPENDKEYWORD=32;
    public static final int RULE_INCLUDE_END_TAG=27;
    public static final int RULE_FIELD_END_TAG=42;
    public static final int RULE_XML_TAG_SINGLEEND=8;
    public static final int RULE_PROPERTY_START_TAG=35;
    public static final int RULE_XML_TAG_END=6;
    public static final int RULE_ATTRIBUTESTRING=12;
    public static final int RULE_MESSAGE_START_TAG=28;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_XML_LT=56;
    public static final int RULE_MAP_METHOD_STARTTAG_START=39;
    public static final int RULE_MESSAGE_END_TAG=29;
    public static final int RULE_XML_GTEQ=59;
    public static final int RULE_TML_SEPARATOR=51;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int RULE_NULL=63;
    public static final int RULE_TRUE=65;
    public static final int RULE_PROPERTY_END_TAG=36;
    public static final int RULE_DOLLAR=55;
    public static final int RULE_EXPRESSION_START_TAG=45;
    public static final int RULE_TML_EXISTS=54;
    public static final int T__71=71;
    public static final int RULE_VALIDATIONS_START_TAG=18;
    public static final int T__72=72;
    public static final int T__70=70;
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
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int RULE_XML_GT=57;
    public static final int RULE_MAP_METHOD_ENDTAG_START=40;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int RULE_PARAM_END_TAG=38;
    public static final int T__79=79;
    public static final int RULE_AT=52;
    public static final int RULE_PARAM_START_TAG=37;
    public static final int T__78=78;
    public static final int T__77=77;

    // delegates
    // delegators


        public InternalTslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalTslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalTslParser.tokenNames; }
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g"; }



     	private TslGrammarAccess grammarAccess;
     	
        public InternalTslParser(TokenStream input, TslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Tml";	
       	}
       	
       	@Override
       	protected TslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleTml"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:67:1: entryRuleTml returns [EObject current=null] : iv_ruleTml= ruleTml EOF ;
    public final EObject entryRuleTml() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTml = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:68:2: (iv_ruleTml= ruleTml EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:69:2: iv_ruleTml= ruleTml EOF
            {
             newCompositeNode(grammarAccess.getTmlRule()); 
            pushFollow(FOLLOW_ruleTml_in_entryRuleTml75);
            iv_ruleTml=ruleTml();

            state._fsp--;

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
    // $ANTLR end "entryRuleTml"


    // $ANTLR start "ruleTml"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:76:1: ruleTml returns [EObject current=null] : ( () (this_XMLHead_1= RULE_XMLHEAD )? this_NAVASCRIPT_START_2= RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( (this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleTml() throws RecognitionException {
        EObject current = null;

        Token this_XMLHead_1=null;
        Token this_NAVASCRIPT_START_2=null;
        Token this_XML_TAG_END_4=null;
        Token this_NAVASCRIPT_END_13=null;
        Token this_XML_TAG_SINGLEEND_14=null;
        EObject lv_attributes_3_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;

        EObject lv_children_7_0 = null;

        EObject lv_methods_8_0 = null;

        EObject lv_children_9_0 = null;

        EObject lv_children_10_0 = null;

        EObject lv_children_11_0 = null;

        EObject lv_children_12_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:79:28: ( ( () (this_XMLHead_1= RULE_XMLHEAD )? this_NAVASCRIPT_START_2= RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( (this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:80:1: ( () (this_XMLHead_1= RULE_XMLHEAD )? this_NAVASCRIPT_START_2= RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( (this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:80:1: ( () (this_XMLHead_1= RULE_XMLHEAD )? this_NAVASCRIPT_START_2= RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( (this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:80:2: () (this_XMLHead_1= RULE_XMLHEAD )? this_NAVASCRIPT_START_2= RULE_NAVASCRIPT_START ( (lv_attributes_3_0= rulePossibleExpression ) )* ( (this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:80:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:81:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getTmlAccess().getTmlAction_0(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:86:2: (this_XMLHead_1= RULE_XMLHEAD )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_XMLHEAD) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:86:3: this_XMLHead_1= RULE_XMLHEAD
                    {
                    this_XMLHead_1=(Token)match(input,RULE_XMLHEAD,FOLLOW_RULE_XMLHEAD_in_ruleTml131); 
                     
                        newLeafNode(this_XMLHead_1, grammarAccess.getTmlAccess().getXMLHeadTerminalRuleCall_1()); 
                        

                    }
                    break;

            }

            this_NAVASCRIPT_START_2=(Token)match(input,RULE_NAVASCRIPT_START,FOLLOW_RULE_NAVASCRIPT_START_in_ruleTml143); 
             
                newLeafNode(this_NAVASCRIPT_START_2, grammarAccess.getTmlAccess().getNAVASCRIPT_STARTTerminalRuleCall_2()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:94:1: ( (lv_attributes_3_0= rulePossibleExpression ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==RULE_ID) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:95:1: (lv_attributes_3_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:95:1: (lv_attributes_3_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:96:3: lv_attributes_3_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getTmlAccess().getAttributesPossibleExpressionParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleTml163);
            	    lv_attributes_3_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_3_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:112:3: ( (this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:112:4: (this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:112:4: (this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:112:5: this_XML_TAG_END_4= RULE_XML_TAG_END ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )* this_NAVASCRIPT_END_13= RULE_NAVASCRIPT_END
                    {
                    this_XML_TAG_END_4=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleTml177); 
                     
                        newLeafNode(this_XML_TAG_END_4, grammarAccess.getTmlAccess().getXML_TAG_ENDTerminalRuleCall_4_0_0()); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:116:1: ( ( (lv_children_5_0= ruleMessage ) ) | ( (lv_children_6_0= ruleMap ) ) | ( (lv_children_7_0= ruleParam ) ) | ( (lv_methods_8_0= ruleMethods ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleInclude ) ) | ( (lv_children_11_0= ruleValidations ) ) | ( (lv_children_12_0= ruleComment ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:116:2: ( (lv_children_5_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:116:2: ( (lv_children_5_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:117:1: (lv_children_5_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:117:1: (lv_children_5_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:118:3: lv_children_5_0= ruleMessage
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTmlAccess().getChildrenMessageParserRuleCall_4_0_1_0_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleTml198);
                    	    lv_children_5_0=ruleMessage();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_5_0, 
                    	            		"Message");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:135:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:136:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:136:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:137:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTmlAccess().getChildrenMapParserRuleCall_4_0_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleTml225);
                    	    lv_children_6_0=ruleMap();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_6_0, 
                    	            		"Map");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:154:6: ( (lv_children_7_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:154:6: ( (lv_children_7_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:155:1: (lv_children_7_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:155:1: (lv_children_7_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:156:3: lv_children_7_0= ruleParam
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTmlAccess().getChildrenParamParserRuleCall_4_0_1_2_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleTml252);
                    	    lv_children_7_0=ruleParam();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_7_0, 
                    	            		"Param");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:173:6: ( (lv_methods_8_0= ruleMethods ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:173:6: ( (lv_methods_8_0= ruleMethods ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:174:1: (lv_methods_8_0= ruleMethods )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:174:1: (lv_methods_8_0= ruleMethods )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:175:3: lv_methods_8_0= ruleMethods
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTmlAccess().getMethodsMethodsParserRuleCall_4_0_1_3_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethods_in_ruleTml279);
                    	    lv_methods_8_0=ruleMethods();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"methods",
                    	            		lv_methods_8_0, 
                    	            		"Methods");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:192:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:192:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:193:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:193:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:194:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTmlAccess().getChildrenDebugTagParserRuleCall_4_0_1_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleTml306);
                    	    lv_children_9_0=ruleDebugTag();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_9_0, 
                    	            		"DebugTag");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:211:6: ( (lv_children_10_0= ruleInclude ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:211:6: ( (lv_children_10_0= ruleInclude ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:212:1: (lv_children_10_0= ruleInclude )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:212:1: (lv_children_10_0= ruleInclude )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:213:3: lv_children_10_0= ruleInclude
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTmlAccess().getChildrenIncludeParserRuleCall_4_0_1_5_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleInclude_in_ruleTml333);
                    	    lv_children_10_0=ruleInclude();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_10_0, 
                    	            		"Include");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:230:6: ( (lv_children_11_0= ruleValidations ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:230:6: ( (lv_children_11_0= ruleValidations ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:231:1: (lv_children_11_0= ruleValidations )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:231:1: (lv_children_11_0= ruleValidations )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:232:3: lv_children_11_0= ruleValidations
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTmlAccess().getChildrenValidationsParserRuleCall_4_0_1_6_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleValidations_in_ruleTml360);
                    	    lv_children_11_0=ruleValidations();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_11_0, 
                    	            		"Validations");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 8 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:249:6: ( (lv_children_12_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:249:6: ( (lv_children_12_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:250:1: (lv_children_12_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:250:1: (lv_children_12_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:251:3: lv_children_12_0= ruleComment
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTmlAccess().getChildrenCommentParserRuleCall_4_0_1_7_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleTml387);
                    	    lv_children_12_0=ruleComment();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTmlRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_12_0, 
                    	            		"Comment");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    this_NAVASCRIPT_END_13=(Token)match(input,RULE_NAVASCRIPT_END,FOLLOW_RULE_NAVASCRIPT_END_in_ruleTml400); 
                     
                        newLeafNode(this_NAVASCRIPT_END_13, grammarAccess.getTmlAccess().getNAVASCRIPT_ENDTerminalRuleCall_4_0_2()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:272:6: this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_14=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml417); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_14, grammarAccess.getTmlAccess().getXML_TAG_SINGLEENDTerminalRuleCall_4_1()); 
                        

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
    // $ANTLR end "ruleTml"


    // $ANTLR start "entryRuleAttributeName"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:284:1: entryRuleAttributeName returns [String current=null] : iv_ruleAttributeName= ruleAttributeName EOF ;
    public final String entryRuleAttributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAttributeName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:285:2: (iv_ruleAttributeName= ruleAttributeName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:286:2: iv_ruleAttributeName= ruleAttributeName EOF
            {
             newCompositeNode(grammarAccess.getAttributeNameRule()); 
            pushFollow(FOLLOW_ruleAttributeName_in_entryRuleAttributeName454);
            iv_ruleAttributeName=ruleAttributeName();

            state._fsp--;

             current =iv_ruleAttributeName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAttributeName465); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAttributeName"


    // $ANTLR start "ruleAttributeName"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:293:1: ruleAttributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleAttributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:296:28: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:297:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAttributeName504); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getAttributeNameAccess().getIDTerminalRuleCall()); 
                

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
    // $ANTLR end "ruleAttributeName"


    // $ANTLR start "entryRulePossibleExpression"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:312:1: entryRulePossibleExpression returns [EObject current=null] : iv_rulePossibleExpression= rulePossibleExpression EOF ;
    public final EObject entryRulePossibleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePossibleExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:313:2: (iv_rulePossibleExpression= rulePossibleExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:314:2: iv_rulePossibleExpression= rulePossibleExpression EOF
            {
             newCompositeNode(grammarAccess.getPossibleExpressionRule()); 
            pushFollow(FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression548);
            iv_rulePossibleExpression=rulePossibleExpression();

            state._fsp--;

             current =iv_rulePossibleExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePossibleExpression558); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePossibleExpression"


    // $ANTLR start "rulePossibleExpression"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:321:1: rulePossibleExpression returns [EObject current=null] : ( ( ( (lv_namespace_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_key_2_0= ruleAttributeName ) ) otherlv_3= '=' ( (this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) ) ;
    public final EObject rulePossibleExpression() throws RecognitionException {
        EObject current = null;

        Token lv_namespace_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token this_QUOTEQ_4=null;
        Token lv_endToken_6_0=null;
        Token lv_value_7_0=null;
        Token lv_value_8_0=null;
        AntlrDatatypeRuleToken lv_key_2_0 = null;

        EObject lv_expressionValue_5_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:324:28: ( ( ( ( (lv_namespace_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_key_2_0= ruleAttributeName ) ) otherlv_3= '=' ( (this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:325:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_key_2_0= ruleAttributeName ) ) otherlv_3= '=' ( (this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:325:1: ( ( ( (lv_namespace_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_key_2_0= ruleAttributeName ) ) otherlv_3= '=' ( (this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:325:2: ( ( (lv_namespace_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_key_2_0= ruleAttributeName ) ) otherlv_3= '=' ( (this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:325:2: ( ( (lv_namespace_0_0= RULE_ID ) ) otherlv_1= ':' )?
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:325:3: ( (lv_namespace_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:325:3: ( (lv_namespace_0_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:326:1: (lv_namespace_0_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:326:1: (lv_namespace_0_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:327:3: lv_namespace_0_0= RULE_ID
                    {
                    lv_namespace_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePossibleExpression601); 

                    			newLeafNode(lv_namespace_0_0, grammarAccess.getPossibleExpressionAccess().getNamespaceIDTerminalRuleCall_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPossibleExpressionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"namespace",
                            		lv_namespace_0_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_1=(Token)match(input,70,FOLLOW_70_in_rulePossibleExpression618); 

                        	newLeafNode(otherlv_1, grammarAccess.getPossibleExpressionAccess().getColonKeyword_0_1());
                        

                    }
                    break;

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:347:3: ( (lv_key_2_0= ruleAttributeName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:348:1: (lv_key_2_0= ruleAttributeName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:348:1: (lv_key_2_0= ruleAttributeName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:349:3: lv_key_2_0= ruleAttributeName
            {
             
            	        newCompositeNode(grammarAccess.getPossibleExpressionAccess().getKeyAttributeNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleAttributeName_in_rulePossibleExpression641);
            lv_key_2_0=ruleAttributeName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getPossibleExpressionRule());
            	        }
                   		set(
                   			current, 
                   			"key",
                    		lv_key_2_0, 
                    		"AttributeName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,71,FOLLOW_71_in_rulePossibleExpression653); 

                	newLeafNode(otherlv_3, grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_2());
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:369:1: ( (this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) ) | ( (lv_value_7_0= RULE_ATTRIBUTESTRING ) ) | ( (lv_value_8_0= RULE_EMPTYSTRING ) ) )
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
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:369:2: (this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:369:2: (this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:369:3: this_QUOTEQ_4= RULE_QUOTEQ ( (lv_expressionValue_5_0= ruleTopLevel ) ) ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) )
                    {
                    this_QUOTEQ_4=(Token)match(input,RULE_QUOTEQ,FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression666); 
                     
                        newLeafNode(this_QUOTEQ_4, grammarAccess.getPossibleExpressionAccess().getQUOTEQTerminalRuleCall_3_0_0()); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:373:1: ( (lv_expressionValue_5_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:374:1: (lv_expressionValue_5_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:374:1: (lv_expressionValue_5_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:375:3: lv_expressionValue_5_0= ruleTopLevel
                    {
                     
                    	        newCompositeNode(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_3_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_rulePossibleExpression686);
                    lv_expressionValue_5_0=ruleTopLevel();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getPossibleExpressionRule());
                    	        }
                           		set(
                           			current, 
                           			"expressionValue",
                            		lv_expressionValue_5_0, 
                            		"TopLevel");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:391:2: ( (lv_endToken_6_0= RULE_SEMICOLONQUOTE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:392:1: (lv_endToken_6_0= RULE_SEMICOLONQUOTE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:392:1: (lv_endToken_6_0= RULE_SEMICOLONQUOTE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:393:3: lv_endToken_6_0= RULE_SEMICOLONQUOTE
                    {
                    lv_endToken_6_0=(Token)match(input,RULE_SEMICOLONQUOTE,FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression703); 

                    			newLeafNode(lv_endToken_6_0, grammarAccess.getPossibleExpressionAccess().getEndTokenSEMICOLONQUOTETerminalRuleCall_3_0_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPossibleExpressionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"endToken",
                            		lv_endToken_6_0, 
                            		"SEMICOLONQUOTE");
                    	    

                    }


                    }


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
                    lv_value_7_0=(Token)match(input,RULE_ATTRIBUTESTRING,FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression732); 

                    			newLeafNode(lv_value_7_0, grammarAccess.getPossibleExpressionAccess().getValueATTRIBUTESTRINGTerminalRuleCall_3_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPossibleExpressionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_7_0, 
                            		"ATTRIBUTESTRING");
                    	    

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:429:6: ( (lv_value_8_0= RULE_EMPTYSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:429:6: ( (lv_value_8_0= RULE_EMPTYSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:430:1: (lv_value_8_0= RULE_EMPTYSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:430:1: (lv_value_8_0= RULE_EMPTYSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:431:3: lv_value_8_0= RULE_EMPTYSTRING
                    {
                    lv_value_8_0=(Token)match(input,RULE_EMPTYSTRING,FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression760); 

                    			newLeafNode(lv_value_8_0, grammarAccess.getPossibleExpressionAccess().getValueEMPTYSTRINGTerminalRuleCall_3_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPossibleExpressionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_8_0, 
                            		"EMPTYSTRING");
                    	    

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
    // $ANTLR end "rulePossibleExpression"


    // $ANTLR start "entryRuleMethods"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:455:1: entryRuleMethods returns [EObject current=null] : iv_ruleMethods= ruleMethods EOF ;
    public final EObject entryRuleMethods() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethods = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:456:2: (iv_ruleMethods= ruleMethods EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:457:2: iv_ruleMethods= ruleMethods EOF
            {
             newCompositeNode(grammarAccess.getMethodsRule()); 
            pushFollow(FOLLOW_ruleMethods_in_entryRuleMethods802);
            iv_ruleMethods=ruleMethods();

            state._fsp--;

             current =iv_ruleMethods; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethods812); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMethods"


    // $ANTLR start "ruleMethods"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:464:1: ruleMethods returns [EObject current=null] : (this_METHODS_START_TAG_0= RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethods() throws RecognitionException {
        EObject current = null;

        Token this_METHODS_START_TAG_0=null;
        Token lv_splitTag_2_0=null;
        Token lv_closedTag_4_0=null;
        Token this_XML_TAG_SINGLEEND_5=null;
        EObject lv_method_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:467:28: ( (this_METHODS_START_TAG_0= RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:468:1: (this_METHODS_START_TAG_0= RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:468:1: (this_METHODS_START_TAG_0= RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:468:2: this_METHODS_START_TAG_0= RULE_METHODS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
            {
            this_METHODS_START_TAG_0=(Token)match(input,RULE_METHODS_START_TAG,FOLLOW_RULE_METHODS_START_TAG_in_ruleMethods848); 
             
                newLeafNode(this_METHODS_START_TAG_0, grammarAccess.getMethodsAccess().getMETHODS_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:472:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:473:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getMethodsAccess().getMethodsAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_method_3_0= ruleMethod ) )* ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:478:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:479:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:479:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:480:3: lv_splitTag_2_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_2_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethods875); 

                    			newLeafNode(lv_splitTag_2_0, grammarAccess.getMethodsAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_2_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMethodsRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:496:2: ( (lv_method_3_0= ruleMethod ) )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==RULE_METHOD_START_TAG) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:497:1: (lv_method_3_0= ruleMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:497:1: (lv_method_3_0= ruleMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:498:3: lv_method_3_0= ruleMethod
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMethodsAccess().getMethodMethodParserRuleCall_2_0_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMethod_in_ruleMethods901);
                    	    lv_method_3_0=ruleMethod();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMethodsRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"method",
                    	            		lv_method_3_0, 
                    	            		"Method");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:514:3: ( (lv_closedTag_4_0= RULE_METHODS_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:515:1: (lv_closedTag_4_0= RULE_METHODS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:515:1: (lv_closedTag_4_0= RULE_METHODS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:516:3: lv_closedTag_4_0= RULE_METHODS_END_TAG
                    {
                    lv_closedTag_4_0=(Token)match(input,RULE_METHODS_END_TAG,FOLLOW_RULE_METHODS_END_TAG_in_ruleMethods919); 

                    			newLeafNode(lv_closedTag_4_0, grammarAccess.getMethodsAccess().getClosedTagMETHODS_END_TAGTerminalRuleCall_2_0_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMethodsRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"METHODS_END_TAG");
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:533:6: this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_5=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods942); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_5, grammarAccess.getMethodsAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_1()); 
                        

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
    // $ANTLR end "ruleMethods"


    // $ANTLR start "entryRuleMethod"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:545:1: entryRuleMethod returns [EObject current=null] : iv_ruleMethod= ruleMethod EOF ;
    public final EObject entryRuleMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:546:2: (iv_ruleMethod= ruleMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:547:2: iv_ruleMethod= ruleMethod EOF
            {
             newCompositeNode(grammarAccess.getMethodRule()); 
            pushFollow(FOLLOW_ruleMethod_in_entryRuleMethod978);
            iv_ruleMethod=ruleMethod();

            state._fsp--;

             current =iv_ruleMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethod988); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMethod"


    // $ANTLR start "ruleMethod"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:554:1: ruleMethod returns [EObject current=null] : (this_METHOD_START_TAG_0= RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMethod() throws RecognitionException {
        EObject current = null;

        Token this_METHOD_START_TAG_0=null;
        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        Token this_XML_TAG_SINGLEEND_6=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:557:28: ( (this_METHOD_START_TAG_0= RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:558:1: (this_METHOD_START_TAG_0= RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:558:1: (this_METHOD_START_TAG_0= RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:558:2: this_METHOD_START_TAG_0= RULE_METHOD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND )
            {
            this_METHOD_START_TAG_0=(Token)match(input,RULE_METHOD_START_TAG,FOLLOW_RULE_METHOD_START_TAG_in_ruleMethod1024); 
             
                newLeafNode(this_METHOD_START_TAG_0, grammarAccess.getMethodAccess().getMETHOD_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:562:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:563:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getMethodAccess().getMethodAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:568:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_ID) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:569:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:569:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:570:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMethodAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMethod1053);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMethodRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:586:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:586:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:586:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:586:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_children_4_0= ruleRequired ) )* ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:586:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:587:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:587:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:588:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMethod1073); 

                    			newLeafNode(lv_splitTag_3_0, grammarAccess.getMethodAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMethodRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:604:2: ( (lv_children_4_0= ruleRequired ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==RULE_REQUIRED_START_TAG) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:605:1: (lv_children_4_0= ruleRequired )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:605:1: (lv_children_4_0= ruleRequired )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:606:3: lv_children_4_0= ruleRequired
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMethodAccess().getChildrenRequiredParserRuleCall_3_0_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRequired_in_ruleMethod1099);
                    	    lv_children_4_0=ruleRequired();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_4_0, 
                    	            		"Required");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:622:3: ( (lv_closedTag_5_0= RULE_METHOD_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:623:1: (lv_closedTag_5_0= RULE_METHOD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:623:1: (lv_closedTag_5_0= RULE_METHOD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:624:3: lv_closedTag_5_0= RULE_METHOD_END_TAG
                    {
                    lv_closedTag_5_0=(Token)match(input,RULE_METHOD_END_TAG,FOLLOW_RULE_METHOD_END_TAG_in_ruleMethod1117); 

                    			newLeafNode(lv_closedTag_5_0, grammarAccess.getMethodAccess().getClosedTagMETHOD_END_TAGTerminalRuleCall_3_0_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMethodRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"METHOD_END_TAG");
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:641:6: this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_6=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod1140); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_6, grammarAccess.getMethodAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1()); 
                        

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
    // $ANTLR end "ruleMethod"


    // $ANTLR start "entryRuleValidations"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:653:1: entryRuleValidations returns [EObject current=null] : iv_ruleValidations= ruleValidations EOF ;
    public final EObject entryRuleValidations() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValidations = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:654:2: (iv_ruleValidations= ruleValidations EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:655:2: iv_ruleValidations= ruleValidations EOF
            {
             newCompositeNode(grammarAccess.getValidationsRule()); 
            pushFollow(FOLLOW_ruleValidations_in_entryRuleValidations1176);
            iv_ruleValidations=ruleValidations();

            state._fsp--;

             current =iv_ruleValidations; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleValidations1186); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValidations"


    // $ANTLR start "ruleValidations"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:662:1: ruleValidations returns [EObject current=null] : (this_VALIDATIONS_START_TAG_0= RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleValidations() throws RecognitionException {
        EObject current = null;

        Token this_VALIDATIONS_START_TAG_0=null;
        Token lv_splitTag_2_0=null;
        Token lv_closedTag_4_0=null;
        Token this_XML_TAG_SINGLEEND_5=null;
        EObject lv_children_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:665:28: ( (this_VALIDATIONS_START_TAG_0= RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:666:1: (this_VALIDATIONS_START_TAG_0= RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:666:1: (this_VALIDATIONS_START_TAG_0= RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:666:2: this_VALIDATIONS_START_TAG_0= RULE_VALIDATIONS_START_TAG () ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
            {
            this_VALIDATIONS_START_TAG_0=(Token)match(input,RULE_VALIDATIONS_START_TAG,FOLLOW_RULE_VALIDATIONS_START_TAG_in_ruleValidations1222); 
             
                newLeafNode(this_VALIDATIONS_START_TAG_0, grammarAccess.getValidationsAccess().getVALIDATIONS_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:670:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:671:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getValidationsAccess().getValidationsAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:676:2: ( ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:676:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:676:3: ( ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:676:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) ) ( (lv_children_3_0= ruleCheck ) )* ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:676:4: ( (lv_splitTag_2_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:677:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:677:1: (lv_splitTag_2_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:678:3: lv_splitTag_2_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_2_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleValidations1249); 

                    			newLeafNode(lv_splitTag_2_0, grammarAccess.getValidationsAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_2_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getValidationsRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:694:2: ( (lv_children_3_0= ruleCheck ) )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==RULE_CHECK_START_TAG) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:695:1: (lv_children_3_0= ruleCheck )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:695:1: (lv_children_3_0= ruleCheck )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:696:3: lv_children_3_0= ruleCheck
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getValidationsAccess().getChildrenCheckParserRuleCall_2_0_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleCheck_in_ruleValidations1275);
                    	    lv_children_3_0=ruleCheck();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getValidationsRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_3_0, 
                    	            		"Check");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:712:3: ( (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:713:1: (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:713:1: (lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:714:3: lv_closedTag_4_0= RULE_VALIDATIONS_END_TAG
                    {
                    lv_closedTag_4_0=(Token)match(input,RULE_VALIDATIONS_END_TAG,FOLLOW_RULE_VALIDATIONS_END_TAG_in_ruleValidations1293); 

                    			newLeafNode(lv_closedTag_4_0, grammarAccess.getValidationsAccess().getClosedTagVALIDATIONS_END_TAGTerminalRuleCall_2_0_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getValidationsRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"VALIDATIONS_END_TAG");
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:731:6: this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_5=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleValidations1316); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_5, grammarAccess.getValidationsAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_1()); 
                        

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
    // $ANTLR end "ruleValidations"


    // $ANTLR start "entryRuleCheck"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:743:1: entryRuleCheck returns [EObject current=null] : iv_ruleCheck= ruleCheck EOF ;
    public final EObject entryRuleCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheck = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:744:2: (iv_ruleCheck= ruleCheck EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:745:2: iv_ruleCheck= ruleCheck EOF
            {
             newCompositeNode(grammarAccess.getCheckRule()); 
            pushFollow(FOLLOW_ruleCheck_in_entryRuleCheck1352);
            iv_ruleCheck=ruleCheck();

            state._fsp--;

             current =iv_ruleCheck; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCheck1362); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCheck"


    // $ANTLR start "ruleCheck"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:752:1: ruleCheck returns [EObject current=null] : (this_CHECK_START_TAG_0= RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleCheck() throws RecognitionException {
        EObject current = null;

        Token this_CHECK_START_TAG_0=null;
        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        Token this_XML_TAG_SINGLEEND_6=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_expression_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:755:28: ( (this_CHECK_START_TAG_0= RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:756:1: (this_CHECK_START_TAG_0= RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:756:1: (this_CHECK_START_TAG_0= RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:756:2: this_CHECK_START_TAG_0= RULE_CHECK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND )
            {
            this_CHECK_START_TAG_0=(Token)match(input,RULE_CHECK_START_TAG,FOLLOW_RULE_CHECK_START_TAG_in_ruleCheck1398); 
             
                newLeafNode(this_CHECK_START_TAG_0, grammarAccess.getCheckAccess().getCHECK_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:760:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:761:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getCheckAccess().getCheckAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:766:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_ID) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:767:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:767:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:768:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getCheckAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleCheck1427);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getCheckRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:784:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:784:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:784:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:784:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:784:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:785:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:785:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:786:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleCheck1447); 

                    			newLeafNode(lv_splitTag_3_0, grammarAccess.getCheckAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getCheckRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:802:2: ( (lv_expression_4_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:803:1: (lv_expression_4_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:803:1: (lv_expression_4_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:804:3: lv_expression_4_0= ruleTopLevel
                    {
                     
                    	        newCompositeNode(grammarAccess.getCheckAccess().getExpressionTopLevelParserRuleCall_3_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleCheck1473);
                    lv_expression_4_0=ruleTopLevel();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getCheckRule());
                    	        }
                           		set(
                           			current, 
                           			"expression",
                            		lv_expression_4_0, 
                            		"TopLevel");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:820:2: ( (lv_closedTag_5_0= RULE_CHECK_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:821:1: (lv_closedTag_5_0= RULE_CHECK_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:821:1: (lv_closedTag_5_0= RULE_CHECK_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:822:3: lv_closedTag_5_0= RULE_CHECK_END_TAG
                    {
                    lv_closedTag_5_0=(Token)match(input,RULE_CHECK_END_TAG,FOLLOW_RULE_CHECK_END_TAG_in_ruleCheck1490); 

                    			newLeafNode(lv_closedTag_5_0, grammarAccess.getCheckAccess().getClosedTagCHECK_END_TAGTerminalRuleCall_3_0_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getCheckRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"CHECK_END_TAG");
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:839:6: this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_6=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleCheck1513); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_6, grammarAccess.getCheckAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1()); 
                        

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
    // $ANTLR end "ruleCheck"


    // $ANTLR start "entryRuleComment"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:851:1: entryRuleComment returns [EObject current=null] : iv_ruleComment= ruleComment EOF ;
    public final EObject entryRuleComment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComment = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:852:2: (iv_ruleComment= ruleComment EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:853:2: iv_ruleComment= ruleComment EOF
            {
             newCompositeNode(grammarAccess.getCommentRule()); 
            pushFollow(FOLLOW_ruleComment_in_entryRuleComment1549);
            iv_ruleComment=ruleComment();

            state._fsp--;

             current =iv_ruleComment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComment1559); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComment"


    // $ANTLR start "ruleComment"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:860:1: ruleComment returns [EObject current=null] : (this_COMMENT_START_TAG_0= RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleComment() throws RecognitionException {
        EObject current = null;

        Token this_COMMENT_START_TAG_0=null;
        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        Token this_XML_TAG_SINGLEEND_5=null;
        EObject lv_attributes_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:863:28: ( (this_COMMENT_START_TAG_0= RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:864:1: (this_COMMENT_START_TAG_0= RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:864:1: (this_COMMENT_START_TAG_0= RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:864:2: this_COMMENT_START_TAG_0= RULE_COMMENT_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
            {
            this_COMMENT_START_TAG_0=(Token)match(input,RULE_COMMENT_START_TAG,FOLLOW_RULE_COMMENT_START_TAG_in_ruleComment1595); 
             
                newLeafNode(this_COMMENT_START_TAG_0, grammarAccess.getCommentAccess().getCOMMENT_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:868:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:869:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getCommentAccess().getCommentAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:874:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_ID) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:875:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:875:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:876:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getCommentAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleComment1624);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getCommentRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:892:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:892:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:892:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:892:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:892:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:893:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:893:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:894:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleComment1644); 

                    			newLeafNode(lv_splitTag_3_0, grammarAccess.getCommentAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getCommentRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:910:2: ( (lv_closedTag_4_0= RULE_COMMENT_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:911:1: (lv_closedTag_4_0= RULE_COMMENT_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:911:1: (lv_closedTag_4_0= RULE_COMMENT_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:912:3: lv_closedTag_4_0= RULE_COMMENT_END_TAG
                    {
                    lv_closedTag_4_0=(Token)match(input,RULE_COMMENT_END_TAG,FOLLOW_RULE_COMMENT_END_TAG_in_ruleComment1666); 

                    			newLeafNode(lv_closedTag_4_0, grammarAccess.getCommentAccess().getClosedTagCOMMENT_END_TAGTerminalRuleCall_3_0_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getCommentRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"COMMENT_END_TAG");
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:929:6: this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_5=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleComment1689); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_5, grammarAccess.getCommentAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1()); 
                        

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
    // $ANTLR end "ruleComment"


    // $ANTLR start "entryRuleBreak"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:941:1: entryRuleBreak returns [EObject current=null] : iv_ruleBreak= ruleBreak EOF ;
    public final EObject entryRuleBreak() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBreak = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:942:2: (iv_ruleBreak= ruleBreak EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:943:2: iv_ruleBreak= ruleBreak EOF
            {
             newCompositeNode(grammarAccess.getBreakRule()); 
            pushFollow(FOLLOW_ruleBreak_in_entryRuleBreak1725);
            iv_ruleBreak=ruleBreak();

            state._fsp--;

             current =iv_ruleBreak; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBreak1735); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBreak"


    // $ANTLR start "ruleBreak"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:950:1: ruleBreak returns [EObject current=null] : (this_BREAK_START_TAG_0= RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleBreak() throws RecognitionException {
        EObject current = null;

        Token this_BREAK_START_TAG_0=null;
        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        Token this_XML_TAG_SINGLEEND_5=null;
        EObject lv_attributes_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:953:28: ( (this_BREAK_START_TAG_0= RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:954:1: (this_BREAK_START_TAG_0= RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:954:1: (this_BREAK_START_TAG_0= RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:954:2: this_BREAK_START_TAG_0= RULE_BREAK_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
            {
            this_BREAK_START_TAG_0=(Token)match(input,RULE_BREAK_START_TAG,FOLLOW_RULE_BREAK_START_TAG_in_ruleBreak1771); 
             
                newLeafNode(this_BREAK_START_TAG_0, grammarAccess.getBreakAccess().getBREAK_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:958:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:959:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getBreakAccess().getBreakAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:964:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==RULE_ID) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:965:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:965:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:966:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getBreakAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleBreak1800);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getBreakRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:982:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:982:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:982:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:982:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:982:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:983:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:983:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:984:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleBreak1820); 

                    			newLeafNode(lv_splitTag_3_0, grammarAccess.getBreakAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getBreakRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1000:2: ( (lv_closedTag_4_0= RULE_BREAK_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1001:1: (lv_closedTag_4_0= RULE_BREAK_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1001:1: (lv_closedTag_4_0= RULE_BREAK_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1002:3: lv_closedTag_4_0= RULE_BREAK_END_TAG
                    {
                    lv_closedTag_4_0=(Token)match(input,RULE_BREAK_END_TAG,FOLLOW_RULE_BREAK_END_TAG_in_ruleBreak1842); 

                    			newLeafNode(lv_closedTag_4_0, grammarAccess.getBreakAccess().getClosedTagBREAK_END_TAGTerminalRuleCall_3_0_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getBreakRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"BREAK_END_TAG");
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1019:6: this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_5=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleBreak1865); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_5, grammarAccess.getBreakAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1()); 
                        

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
    // $ANTLR end "ruleBreak"


    // $ANTLR start "entryRuleInclude"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1031:1: entryRuleInclude returns [EObject current=null] : iv_ruleInclude= ruleInclude EOF ;
    public final EObject entryRuleInclude() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInclude = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1032:2: (iv_ruleInclude= ruleInclude EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1033:2: iv_ruleInclude= ruleInclude EOF
            {
             newCompositeNode(grammarAccess.getIncludeRule()); 
            pushFollow(FOLLOW_ruleInclude_in_entryRuleInclude1901);
            iv_ruleInclude=ruleInclude();

            state._fsp--;

             current =iv_ruleInclude; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleInclude1911); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInclude"


    // $ANTLR start "ruleInclude"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1040:1: ruleInclude returns [EObject current=null] : (this_INCLUDE_START_TAG_0= RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleInclude() throws RecognitionException {
        EObject current = null;

        Token this_INCLUDE_START_TAG_0=null;
        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        Token this_XML_TAG_SINGLEEND_5=null;
        EObject lv_attributes_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1043:28: ( (this_INCLUDE_START_TAG_0= RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1044:1: (this_INCLUDE_START_TAG_0= RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1044:1: (this_INCLUDE_START_TAG_0= RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1044:2: this_INCLUDE_START_TAG_0= RULE_INCLUDE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
            {
            this_INCLUDE_START_TAG_0=(Token)match(input,RULE_INCLUDE_START_TAG,FOLLOW_RULE_INCLUDE_START_TAG_in_ruleInclude1947); 
             
                newLeafNode(this_INCLUDE_START_TAG_0, grammarAccess.getIncludeAccess().getINCLUDE_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1048:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1049:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getIncludeAccess().getMethodAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1054:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==RULE_ID) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1055:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1055:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1056:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getIncludeAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleInclude1976);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getIncludeRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1072:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1072:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1072:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1072:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1072:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1073:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1073:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1074:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleInclude1996); 

                    			newLeafNode(lv_splitTag_3_0, grammarAccess.getIncludeAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getIncludeRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1090:2: ( (lv_closedTag_4_0= RULE_INCLUDE_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1091:1: (lv_closedTag_4_0= RULE_INCLUDE_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1091:1: (lv_closedTag_4_0= RULE_INCLUDE_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1092:3: lv_closedTag_4_0= RULE_INCLUDE_END_TAG
                    {
                    lv_closedTag_4_0=(Token)match(input,RULE_INCLUDE_END_TAG,FOLLOW_RULE_INCLUDE_END_TAG_in_ruleInclude2018); 

                    			newLeafNode(lv_closedTag_4_0, grammarAccess.getIncludeAccess().getClosedTagINCLUDE_END_TAGTerminalRuleCall_3_0_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getIncludeRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"INCLUDE_END_TAG");
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1109:6: this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_5=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleInclude2041); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_5, grammarAccess.getIncludeAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1()); 
                        

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
    // $ANTLR end "ruleInclude"


    // $ANTLR start "entryRuleMessage"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1121:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1122:2: (iv_ruleMessage= ruleMessage EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1123:2: iv_ruleMessage= ruleMessage EOF
            {
             newCompositeNode(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage2077);
            iv_ruleMessage=ruleMessage();

            state._fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage2087); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessage"


    // $ANTLR start "ruleMessage"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1130:1: ruleMessage returns [EObject current=null] : (this_MESSAGE_START_TAG_0= RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        Token this_MESSAGE_START_TAG_0=null;
        Token lv_splitTag_3_0=null;
        Token lv_closedTag_13_0=null;
        Token this_XML_TAG_SINGLEEND_14=null;
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


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1133:28: ( (this_MESSAGE_START_TAG_0= RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1134:1: (this_MESSAGE_START_TAG_0= RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1134:1: (this_MESSAGE_START_TAG_0= RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1134:2: this_MESSAGE_START_TAG_0= RULE_MESSAGE_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND )
            {
            this_MESSAGE_START_TAG_0=(Token)match(input,RULE_MESSAGE_START_TAG,FOLLOW_RULE_MESSAGE_START_TAG_in_ruleMessage2123); 
             
                newLeafNode(this_MESSAGE_START_TAG_0, grammarAccess.getMessageAccess().getMESSAGE_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1138:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1139:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getMessageAccess().getMessageAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1144:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==RULE_ID) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1145:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1145:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1146:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMessage2152);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1162:3: ( ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) ) | this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND )
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
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1162:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1162:4: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1162:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )* ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1162:5: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1163:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1163:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1164:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMessage2172); 

                    			newLeafNode(lv_splitTag_3_0, grammarAccess.getMessageAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMessageRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1180:2: ( ( (lv_children_4_0= ruleMessage ) ) | ( (lv_children_5_0= ruleProperty ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleField ) ) | ( (lv_children_11_0= ruleComment ) ) | ( (lv_children_12_0= ruleBreak ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1180:3: ( (lv_children_4_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1180:3: ( (lv_children_4_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1181:1: (lv_children_4_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1181:1: (lv_children_4_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1182:3: lv_children_4_0= ruleMessage
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMessage2199);
                    	    lv_children_4_0=ruleMessage();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_4_0, 
                    	            		"Message");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1199:6: ( (lv_children_5_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1199:6: ( (lv_children_5_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1200:1: (lv_children_5_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1200:1: (lv_children_5_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1201:3: lv_children_5_0= ruleProperty
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMessage2226);
                    	    lv_children_5_0=ruleProperty();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_5_0, 
                    	            		"Property");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1218:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1218:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1219:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1220:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenParamParserRuleCall_3_0_1_2_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMessage2253);
                    	    lv_children_6_0=ruleParam();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_6_0, 
                    	            		"Param");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1237:6: ( (lv_children_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1237:6: ( (lv_children_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1238:1: (lv_children_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1238:1: (lv_children_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1239:3: lv_children_7_0= ruleMap
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_3_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMessage2280);
                    	    lv_children_7_0=ruleMap();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_7_0, 
                    	            		"Map");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1256:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1256:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1257:1: (lv_children_8_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1257:1: (lv_children_8_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1258:3: lv_children_8_0= ruleMapMethod
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenMapMethodParserRuleCall_3_0_1_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMessage2307);
                    	    lv_children_8_0=ruleMapMethod();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_8_0, 
                    	            		"MapMethod");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1275:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1275:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1276:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1276:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1277:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenDebugTagParserRuleCall_3_0_1_5_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMessage2334);
                    	    lv_children_9_0=ruleDebugTag();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_9_0, 
                    	            		"DebugTag");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1294:6: ( (lv_children_10_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1294:6: ( (lv_children_10_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1295:1: (lv_children_10_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1295:1: (lv_children_10_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1296:3: lv_children_10_0= ruleField
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenFieldParserRuleCall_3_0_1_6_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMessage2361);
                    	    lv_children_10_0=ruleField();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_10_0, 
                    	            		"Field");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 8 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1313:6: ( (lv_children_11_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1313:6: ( (lv_children_11_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:1: (lv_children_11_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1314:1: (lv_children_11_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1315:3: lv_children_11_0= ruleComment
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenCommentParserRuleCall_3_0_1_7_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMessage2388);
                    	    lv_children_11_0=ruleComment();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_11_0, 
                    	            		"Comment");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 9 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1332:6: ( (lv_children_12_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1332:6: ( (lv_children_12_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1333:1: (lv_children_12_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1333:1: (lv_children_12_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1334:3: lv_children_12_0= ruleBreak
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageAccess().getChildrenBreakParserRuleCall_3_0_1_8_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMessage2415);
                    	    lv_children_12_0=ruleBreak();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_12_0, 
                    	            		"Break");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1350:4: ( (lv_closedTag_13_0= RULE_MESSAGE_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1351:1: (lv_closedTag_13_0= RULE_MESSAGE_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1351:1: (lv_closedTag_13_0= RULE_MESSAGE_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1352:3: lv_closedTag_13_0= RULE_MESSAGE_END_TAG
                    {
                    lv_closedTag_13_0=(Token)match(input,RULE_MESSAGE_END_TAG,FOLLOW_RULE_MESSAGE_END_TAG_in_ruleMessage2434); 

                    			newLeafNode(lv_closedTag_13_0, grammarAccess.getMessageAccess().getClosedTagMESSAGE_END_TAGTerminalRuleCall_3_0_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMessageRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"MESSAGE_END_TAG");
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1369:6: this_XML_TAG_SINGLEEND_14= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_14=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage2457); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_14, grammarAccess.getMessageAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_1()); 
                        

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
    // $ANTLR end "ruleMessage"


    // $ANTLR start "entryRuleMap"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1381:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1382:2: (iv_ruleMap= ruleMap EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1383:2: iv_ruleMap= ruleMap EOF
            {
             newCompositeNode(grammarAccess.getMapRule()); 
            pushFollow(FOLLOW_ruleMap_in_entryRuleMap2493);
            iv_ruleMap=ruleMap();

            state._fsp--;

             current =iv_ruleMap; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMap2503); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMap"


    // $ANTLR start "ruleMap"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1390:1: ruleMap returns [EObject current=null] : (this_MAPSTARTKEYWORD_0= RULE_MAPSTARTKEYWORD () ( (this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) (this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token this_MAPSTARTKEYWORD_0=null;
        Token this_DOT_2=null;
        Token this_XML_TAG_SINGLEEND_6=null;
        Token lv_splitTag_7_0=null;
        Token this_MAPENDKEYWORD_17=null;
        Token this_DOT_18=null;
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


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1393:28: ( (this_MAPSTARTKEYWORD_0= RULE_MAPSTARTKEYWORD () ( (this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) (this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1394:1: (this_MAPSTARTKEYWORD_0= RULE_MAPSTARTKEYWORD () ( (this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) (this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1394:1: (this_MAPSTARTKEYWORD_0= RULE_MAPSTARTKEYWORD () ( (this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) (this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1394:2: this_MAPSTARTKEYWORD_0= RULE_MAPSTARTKEYWORD () ( (this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* ) (this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) )
            {
            this_MAPSTARTKEYWORD_0=(Token)match(input,RULE_MAPSTARTKEYWORD,FOLLOW_RULE_MAPSTARTKEYWORD_in_ruleMap2539); 
             
                newLeafNode(this_MAPSTARTKEYWORD_0, grammarAccess.getMapAccess().getMAPSTARTKEYWORDTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1398:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1399:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getMapAccess().getMapAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1404:2: ( (this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* ) | ( (lv_attributes_5_0= rulePossibleExpression ) )* )
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
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1404:3: (this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1404:3: (this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1404:4: this_DOT_2= RULE_DOT ( (lv_mapName_3_0= ruleMapId ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )*
                    {
                    this_DOT_2=(Token)match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMap2560); 
                     
                        newLeafNode(this_DOT_2, grammarAccess.getMapAccess().getDOTTerminalRuleCall_2_0_0()); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1408:1: ( (lv_mapName_3_0= ruleMapId ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1409:1: (lv_mapName_3_0= ruleMapId )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1409:1: (lv_mapName_3_0= ruleMapId )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1410:3: lv_mapName_3_0= ruleMapId
                    {
                     
                    	        newCompositeNode(grammarAccess.getMapAccess().getMapNameMapIdParserRuleCall_2_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMapId_in_ruleMap2580);
                    lv_mapName_3_0=ruleMapId();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	        }
                           		set(
                           			current, 
                           			"mapName",
                            		lv_mapName_3_0, 
                            		"MapId");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1426:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==RULE_ID) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1427:1: (lv_attributes_4_0= rulePossibleExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1427:1: (lv_attributes_4_0= rulePossibleExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1428:3: lv_attributes_4_0= rulePossibleExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_0_2_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap2601);
                    	    lv_attributes_4_0=rulePossibleExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"attributes",
                    	            		lv_attributes_4_0, 
                    	            		"PossibleExpression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1445:6: ( (lv_attributes_5_0= rulePossibleExpression ) )*
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1445:6: ( (lv_attributes_5_0= rulePossibleExpression ) )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==RULE_ID) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1446:1: (lv_attributes_5_0= rulePossibleExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1446:1: (lv_attributes_5_0= rulePossibleExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1447:3: lv_attributes_5_0= rulePossibleExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMap2630);
                    	    lv_attributes_5_0=rulePossibleExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"attributes",
                    	            		lv_attributes_5_0, 
                    	            		"PossibleExpression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1463:4: (this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) ) )
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
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1463:5: this_XML_TAG_SINGLEEND_6= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_6=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap2644); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_6, grammarAccess.getMapAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1468:6: ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1468:6: ( ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1468:7: ( (lv_splitTag_7_0= RULE_XML_TAG_END ) ) ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1468:7: ( (lv_splitTag_7_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1469:1: (lv_splitTag_7_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1469:1: (lv_splitTag_7_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1470:3: lv_splitTag_7_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_7_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap2667); 

                    			newLeafNode(lv_splitTag_7_0, grammarAccess.getMapAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMapRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1486:2: ( ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1486:3: ( (lv_children_8_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1486:3: ( (lv_children_8_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1487:1: (lv_children_8_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1487:1: (lv_children_8_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1488:3: lv_children_8_0= ruleMessage
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_3_1_1_0_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMap2694);
                    	    lv_children_8_0=ruleMessage();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_8_0, 
                    	            		"Message");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1505:6: ( (lv_children_9_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1505:6: ( (lv_children_9_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1506:1: (lv_children_9_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1506:1: (lv_children_9_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1507:3: lv_children_9_0= ruleProperty
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_3_1_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMap2721);
                    	    lv_children_9_0=ruleProperty();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_9_0, 
                    	            		"Property");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1524:6: ( (lv_children_10_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1524:6: ( (lv_children_10_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1525:1: (lv_children_10_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1525:1: (lv_children_10_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1526:3: lv_children_10_0= ruleParam
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenParamParserRuleCall_3_1_1_2_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMap2748);
                    	    lv_children_10_0=ruleParam();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_10_0, 
                    	            		"Param");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1543:6: ( (lv_children_11_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1543:6: ( (lv_children_11_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1544:1: (lv_children_11_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1544:1: (lv_children_11_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1545:3: lv_children_11_0= ruleMap
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_3_1_1_3_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMap2775);
                    	    lv_children_11_0=ruleMap();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_11_0, 
                    	            		"Map");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1562:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1562:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1563:1: (lv_children_12_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1563:1: (lv_children_12_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1564:3: lv_children_12_0= ruleMapMethod
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenMapMethodParserRuleCall_3_1_1_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMap2802);
                    	    lv_children_12_0=ruleMapMethod();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_12_0, 
                    	            		"MapMethod");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1581:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1581:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1582:1: (lv_children_13_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1582:1: (lv_children_13_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1583:3: lv_children_13_0= ruleDebugTag
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenDebugTagParserRuleCall_3_1_1_5_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMap2829);
                    	    lv_children_13_0=ruleDebugTag();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_13_0, 
                    	            		"DebugTag");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1600:6: ( (lv_children_14_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1600:6: ( (lv_children_14_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1601:1: (lv_children_14_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1601:1: (lv_children_14_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1602:3: lv_children_14_0= ruleField
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenFieldParserRuleCall_3_1_1_6_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMap2856);
                    	    lv_children_14_0=ruleField();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_14_0, 
                    	            		"Field");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 8 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1619:6: ( (lv_children_15_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1619:6: ( (lv_children_15_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1620:1: (lv_children_15_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1620:1: (lv_children_15_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1621:3: lv_children_15_0= ruleComment
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenCommentParserRuleCall_3_1_1_7_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMap2883);
                    	    lv_children_15_0=ruleComment();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_15_0, 
                    	            		"Comment");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 9 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1638:6: ( (lv_children_16_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1638:6: ( (lv_children_16_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1639:1: (lv_children_16_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1639:1: (lv_children_16_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1640:3: lv_children_16_0= ruleBreak
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapAccess().getChildrenBreakParserRuleCall_3_1_1_8_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMap2910);
                    	    lv_children_16_0=ruleBreak();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_16_0, 
                    	            		"Break");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop28;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1656:4: (this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1656:5: this_MAPENDKEYWORD_17= RULE_MAPENDKEYWORD (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )? ( (lv_closedTag_20_0= RULE_XML_TAG_END ) )
                    {
                    this_MAPENDKEYWORD_17=(Token)match(input,RULE_MAPENDKEYWORD,FOLLOW_RULE_MAPENDKEYWORD_in_ruleMap2924); 
                     
                        newLeafNode(this_MAPENDKEYWORD_17, grammarAccess.getMapAccess().getMAPENDKEYWORDTerminalRuleCall_3_1_2_0()); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1660:1: (this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) ) )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==RULE_DOT) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1660:2: this_DOT_18= RULE_DOT ( (lv_mapClosingName_19_0= ruleMapId ) )
                            {
                            this_DOT_18=(Token)match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMap2935); 
                             
                                newLeafNode(this_DOT_18, grammarAccess.getMapAccess().getDOTTerminalRuleCall_3_1_2_1_0()); 
                                
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1664:1: ( (lv_mapClosingName_19_0= ruleMapId ) )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1665:1: (lv_mapClosingName_19_0= ruleMapId )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1665:1: (lv_mapClosingName_19_0= ruleMapId )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1666:3: lv_mapClosingName_19_0= ruleMapId
                            {
                             
                            	        newCompositeNode(grammarAccess.getMapAccess().getMapClosingNameMapIdParserRuleCall_3_1_2_1_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleMapId_in_ruleMap2955);
                            lv_mapClosingName_19_0=ruleMapId();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getMapRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"mapClosingName",
                                    		lv_mapClosingName_19_0, 
                                    		"MapId");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1682:4: ( (lv_closedTag_20_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1683:1: (lv_closedTag_20_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1683:1: (lv_closedTag_20_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1684:3: lv_closedTag_20_0= RULE_XML_TAG_END
                    {
                    lv_closedTag_20_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMap2974); 

                    			newLeafNode(lv_closedTag_20_0, grammarAccess.getMapAccess().getClosedTagXML_TAG_ENDTerminalRuleCall_3_1_2_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMapRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


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
    // $ANTLR end "ruleMap"


    // $ANTLR start "entryRuleMapId"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1708:1: entryRuleMapId returns [String current=null] : iv_ruleMapId= ruleMapId EOF ;
    public final String entryRuleMapId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMapId = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1709:2: (iv_ruleMapId= ruleMapId EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1710:2: iv_ruleMapId= ruleMapId EOF
            {
             newCompositeNode(grammarAccess.getMapIdRule()); 
            pushFollow(FOLLOW_ruleMapId_in_entryRuleMapId3019);
            iv_ruleMapId=ruleMapId();

            state._fsp--;

             current =iv_ruleMapId.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapId3030); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMapId"


    // $ANTLR start "ruleMapId"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1717:1: ruleMapId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleMapId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1720:28: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1721:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapId3069); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getMapIdAccess().getIDTerminalRuleCall()); 
                

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
    // $ANTLR end "ruleMapId"


    // $ANTLR start "entryRuleRequired"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1736:1: entryRuleRequired returns [EObject current=null] : iv_ruleRequired= ruleRequired EOF ;
    public final EObject entryRuleRequired() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequired = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1737:2: (iv_ruleRequired= ruleRequired EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1738:2: iv_ruleRequired= ruleRequired EOF
            {
             newCompositeNode(grammarAccess.getRequiredRule()); 
            pushFollow(FOLLOW_ruleRequired_in_entryRuleRequired3113);
            iv_ruleRequired=ruleRequired();

            state._fsp--;

             current =iv_ruleRequired; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequired3123); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequired"


    // $ANTLR start "ruleRequired"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1745:1: ruleRequired returns [EObject current=null] : (this_REQUIRED_START_TAG_0= RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) ) ;
    public final EObject ruleRequired() throws RecognitionException {
        EObject current = null;

        Token this_REQUIRED_START_TAG_0=null;
        Token this_XML_TAG_SINGLEEND_3=null;
        Token lv_splitTag_4_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1748:28: ( (this_REQUIRED_START_TAG_0= RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1749:1: (this_REQUIRED_START_TAG_0= RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1749:1: (this_REQUIRED_START_TAG_0= RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1749:2: this_REQUIRED_START_TAG_0= RULE_REQUIRED_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) )
            {
            this_REQUIRED_START_TAG_0=(Token)match(input,RULE_REQUIRED_START_TAG,FOLLOW_RULE_REQUIRED_START_TAG_in_ruleRequired3159); 
             
                newLeafNode(this_REQUIRED_START_TAG_0, grammarAccess.getRequiredAccess().getREQUIRED_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1753:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1754:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getRequiredAccess().getRequiredAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1759:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==RULE_ID) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1760:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1760:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1761:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getRequiredAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleRequired3188);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getRequiredRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1777:3: (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) ) )
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
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1777:4: this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_3=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleRequired3201); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_3, grammarAccess.getRequiredAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1782:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1782:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1782:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1782:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1783:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1783:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1784:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleRequired3224); 

                    			newLeafNode(lv_splitTag_4_0, grammarAccess.getRequiredAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRequiredRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1800:2: ( (lv_closedTag_5_0= RULE_REQUIRED_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1801:1: (lv_closedTag_5_0= RULE_REQUIRED_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1801:1: (lv_closedTag_5_0= RULE_REQUIRED_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1802:3: lv_closedTag_5_0= RULE_REQUIRED_END_TAG
                    {
                    lv_closedTag_5_0=(Token)match(input,RULE_REQUIRED_END_TAG,FOLLOW_RULE_REQUIRED_END_TAG_in_ruleRequired3246); 

                    			newLeafNode(lv_closedTag_5_0, grammarAccess.getRequiredAccess().getClosedTagREQUIRED_END_TAGTerminalRuleCall_3_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRequiredRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"REQUIRED_END_TAG");
                    	    

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
    // $ANTLR end "ruleRequired"


    // $ANTLR start "entryRuleProperty"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1826:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1827:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1828:2: iv_ruleProperty= ruleProperty EOF
            {
             newCompositeNode(grammarAccess.getPropertyRule()); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty3289);
            iv_ruleProperty=ruleProperty();

            state._fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty3299); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProperty"


    // $ANTLR start "ruleProperty"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1835:1: ruleProperty returns [EObject current=null] : (this_PROPERTY_START_TAG_0= RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token this_PROPERTY_START_TAG_0=null;
        Token this_XML_TAG_SINGLEEND_3=null;
        Token lv_splitTag_4_0=null;
        Token lv_closedTag_7_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1838:28: ( (this_PROPERTY_START_TAG_0= RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1839:1: (this_PROPERTY_START_TAG_0= RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1839:1: (this_PROPERTY_START_TAG_0= RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1839:2: this_PROPERTY_START_TAG_0= RULE_PROPERTY_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) )
            {
            this_PROPERTY_START_TAG_0=(Token)match(input,RULE_PROPERTY_START_TAG,FOLLOW_RULE_PROPERTY_START_TAG_in_ruleProperty3335); 
             
                newLeafNode(this_PROPERTY_START_TAG_0, grammarAccess.getPropertyAccess().getPROPERTY_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1843:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1844:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getPropertyAccess().getPropertyAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1849:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==RULE_ID) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1850:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1850:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1851:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleProperty3364);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getPropertyRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1867:3: (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) ) )
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
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1867:4: this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_3=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty3377); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_3, grammarAccess.getPropertyAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1872:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1873:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1873:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1874:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleProperty3400); 

                    			newLeafNode(lv_splitTag_4_0, grammarAccess.getPropertyAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPropertyRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1890:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1890:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1890:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1891:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1891:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1892:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getPropertyAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_1_0_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleProperty3427);
                    	    lv_children_5_0=ruleExpressionOrOption();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getPropertyRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_5_0, 
                    	            		"ExpressionOrOption");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1909:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1909:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1910:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1910:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1911:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getPropertyAccess().getChildrenMapParserRuleCall_3_1_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleProperty3454);
                    	    lv_children_6_0=ruleMap();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getPropertyRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_6_0, 
                    	            		"Map");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1927:4: ( (lv_closedTag_7_0= RULE_PROPERTY_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1928:1: (lv_closedTag_7_0= RULE_PROPERTY_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1928:1: (lv_closedTag_7_0= RULE_PROPERTY_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1929:3: lv_closedTag_7_0= RULE_PROPERTY_END_TAG
                    {
                    lv_closedTag_7_0=(Token)match(input,RULE_PROPERTY_END_TAG,FOLLOW_RULE_PROPERTY_END_TAG_in_ruleProperty3473); 

                    			newLeafNode(lv_closedTag_7_0, grammarAccess.getPropertyAccess().getClosedTagPROPERTY_END_TAGTerminalRuleCall_3_1_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPropertyRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"PROPERTY_END_TAG");
                    	    

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
    // $ANTLR end "ruleProperty"


    // $ANTLR start "entryRuleParam"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1953:1: entryRuleParam returns [EObject current=null] : iv_ruleParam= ruleParam EOF ;
    public final EObject entryRuleParam() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1954:2: (iv_ruleParam= ruleParam EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1955:2: iv_ruleParam= ruleParam EOF
            {
             newCompositeNode(grammarAccess.getParamRule()); 
            pushFollow(FOLLOW_ruleParam_in_entryRuleParam3516);
            iv_ruleParam=ruleParam();

            state._fsp--;

             current =iv_ruleParam; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParam3526); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParam"


    // $ANTLR start "ruleParam"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1962:1: ruleParam returns [EObject current=null] : (this_PARAM_START_TAG_0= RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) ) ;
    public final EObject ruleParam() throws RecognitionException {
        EObject current = null;

        Token this_PARAM_START_TAG_0=null;
        Token this_XML_TAG_SINGLEEND_3=null;
        Token lv_splitTag_4_0=null;
        Token lv_closedTag_7_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_children_5_0 = null;

        EObject lv_children_6_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1965:28: ( (this_PARAM_START_TAG_0= RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1966:1: (this_PARAM_START_TAG_0= RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1966:1: (this_PARAM_START_TAG_0= RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1966:2: this_PARAM_START_TAG_0= RULE_PARAM_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) )
            {
            this_PARAM_START_TAG_0=(Token)match(input,RULE_PARAM_START_TAG,FOLLOW_RULE_PARAM_START_TAG_in_ruleParam3562); 
             
                newLeafNode(this_PARAM_START_TAG_0, grammarAccess.getParamAccess().getPARAM_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1970:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1971:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getParamAccess().getParamAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1976:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==RULE_ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1977:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1977:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1978:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getParamAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleParam3591);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getParamRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1994:3: (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) ) )
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
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1994:4: this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_3=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam3604); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_3, grammarAccess.getParamAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1999:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1999:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1999:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )* ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:1999:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2000:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2000:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2001:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleParam3627); 

                    			newLeafNode(lv_splitTag_4_0, grammarAccess.getParamAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getParamRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2017:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleMap ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2017:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2017:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2018:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2018:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2019:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getParamAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_1_0_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleParam3654);
                    	    lv_children_5_0=ruleExpressionOrOption();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getParamRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_5_0, 
                    	            		"ExpressionOrOption");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2036:6: ( (lv_children_6_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2036:6: ( (lv_children_6_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2037:1: (lv_children_6_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2037:1: (lv_children_6_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2038:3: lv_children_6_0= ruleMap
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getParamAccess().getChildrenMapParserRuleCall_3_1_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleParam3681);
                    	    lv_children_6_0=ruleMap();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getParamRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_6_0, 
                    	            		"Map");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2054:4: ( (lv_closedTag_7_0= RULE_PARAM_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2055:1: (lv_closedTag_7_0= RULE_PARAM_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2055:1: (lv_closedTag_7_0= RULE_PARAM_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2056:3: lv_closedTag_7_0= RULE_PARAM_END_TAG
                    {
                    lv_closedTag_7_0=(Token)match(input,RULE_PARAM_END_TAG,FOLLOW_RULE_PARAM_END_TAG_in_ruleParam3700); 

                    			newLeafNode(lv_closedTag_7_0, grammarAccess.getParamAccess().getClosedTagPARAM_END_TAGTerminalRuleCall_3_1_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getParamRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"PARAM_END_TAG");
                    	    

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
    // $ANTLR end "ruleParam"


    // $ANTLR start "entryRuleMapMethod"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2080:1: entryRuleMapMethod returns [EObject current=null] : iv_ruleMapMethod= ruleMapMethod EOF ;
    public final EObject entryRuleMapMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapMethod = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2081:2: (iv_ruleMapMethod= ruleMapMethod EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2082:2: iv_ruleMapMethod= ruleMapMethod EOF
            {
             newCompositeNode(grammarAccess.getMapMethodRule()); 
            pushFollow(FOLLOW_ruleMapMethod_in_entryRuleMapMethod3743);
            iv_ruleMapMethod=ruleMapMethod();

            state._fsp--;

             current =iv_ruleMapMethod; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapMethod3753); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMapMethod"


    // $ANTLR start "ruleMapMethod"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2089:1: ruleMapMethod returns [EObject current=null] : (this_MAP_METHOD_STARTTAG_START_0= RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) this_DOT_2= RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END ) ) ) ;
    public final EObject ruleMapMethod() throws RecognitionException {
        EObject current = null;

        Token this_MAP_METHOD_STARTTAG_START_0=null;
        Token lv_mapName_1_0=null;
        Token this_DOT_2=null;
        Token this_XML_TAG_SINGLEEND_5=null;
        Token lv_splitTag_6_0=null;
        Token lv_closedTag_17_0=null;
        Token lv_methodClosingName_18_0=null;
        Token this_DOT_19=null;
        Token lv_methodClosingMethod_20_0=null;
        Token this_XML_TAG_END_21=null;
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


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2092:28: ( (this_MAP_METHOD_STARTTAG_START_0= RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) this_DOT_2= RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2093:1: (this_MAP_METHOD_STARTTAG_START_0= RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) this_DOT_2= RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2093:1: (this_MAP_METHOD_STARTTAG_START_0= RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) this_DOT_2= RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2093:2: this_MAP_METHOD_STARTTAG_START_0= RULE_MAP_METHOD_STARTTAG_START ( (lv_mapName_1_0= RULE_ID ) ) this_DOT_2= RULE_DOT ( (lv_methodName_3_0= ruleAttributeName ) ) ( (lv_attributes_4_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END ) )
            {
            this_MAP_METHOD_STARTTAG_START_0=(Token)match(input,RULE_MAP_METHOD_STARTTAG_START,FOLLOW_RULE_MAP_METHOD_STARTTAG_START_in_ruleMapMethod3789); 
             
                newLeafNode(this_MAP_METHOD_STARTTAG_START_0, grammarAccess.getMapMethodAccess().getMAP_METHOD_STARTTAG_STARTTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2097:1: ( (lv_mapName_1_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2098:1: (lv_mapName_1_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2098:1: (lv_mapName_1_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2099:3: lv_mapName_1_0= RULE_ID
            {
            lv_mapName_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod3805); 

            			newLeafNode(lv_mapName_1_0, grammarAccess.getMapMethodAccess().getMapNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMapMethodRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"mapName",
                    		lv_mapName_1_0, 
                    		"ID");
            	    

            }


            }

            this_DOT_2=(Token)match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMapMethod3821); 
             
                newLeafNode(this_DOT_2, grammarAccess.getMapMethodAccess().getDOTTerminalRuleCall_2()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2119:1: ( (lv_methodName_3_0= ruleAttributeName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2120:1: (lv_methodName_3_0= ruleAttributeName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2120:1: (lv_methodName_3_0= ruleAttributeName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2121:3: lv_methodName_3_0= ruleAttributeName
            {
             
            	        newCompositeNode(grammarAccess.getMapMethodAccess().getMethodNameAttributeNameParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleAttributeName_in_ruleMapMethod3841);
            lv_methodName_3_0=ruleAttributeName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
            	        }
                   		set(
                   			current, 
                   			"methodName",
                    		lv_methodName_3_0, 
                    		"AttributeName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2137:2: ( (lv_attributes_4_0= rulePossibleExpression ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==RULE_ID) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2138:1: (lv_attributes_4_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2138:1: (lv_attributes_4_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2139:3: lv_attributes_4_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getAttributesPossibleExpressionParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleMapMethod3862);
            	    lv_attributes_4_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_4_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2155:3: (this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END ) )
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
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2155:4: this_XML_TAG_SINGLEEND_5= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_5=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod3875); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_5, grammarAccess.getMapMethodAccess().getXML_TAG_SINGLEENDTerminalRuleCall_5_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2160:6: ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2160:6: ( ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2160:7: ( (lv_splitTag_6_0= RULE_XML_TAG_END ) ) ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )* ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) ) ( (lv_methodClosingName_18_0= RULE_ID ) ) this_DOT_19= RULE_DOT ( (lv_methodClosingMethod_20_0= RULE_ID ) ) this_XML_TAG_END_21= RULE_XML_TAG_END
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2160:7: ( (lv_splitTag_6_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2161:1: (lv_splitTag_6_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2161:1: (lv_splitTag_6_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2162:3: lv_splitTag_6_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_6_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod3898); 

                    			newLeafNode(lv_splitTag_6_0, grammarAccess.getMapMethodAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_5_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMapMethodRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2178:2: ( ( (lv_children_7_0= ruleExpressionOrOption ) ) | ( (lv_children_8_0= ruleMessage ) ) | ( (lv_children_9_0= ruleProperty ) ) | ( (lv_children_10_0= ruleParam ) ) | ( (lv_children_11_0= ruleMap ) ) | ( (lv_children_12_0= ruleMapMethod ) ) | ( (lv_children_13_0= ruleDebugTag ) ) | ( (lv_children_14_0= ruleField ) ) | ( (lv_children_15_0= ruleComment ) ) | ( (lv_children_16_0= ruleBreak ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2178:3: ( (lv_children_7_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2178:3: ( (lv_children_7_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2179:1: (lv_children_7_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2179:1: (lv_children_7_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2180:3: lv_children_7_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenExpressionOrOptionParserRuleCall_5_1_1_0_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleMapMethod3925);
                    	    lv_children_7_0=ruleExpressionOrOption();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_7_0, 
                    	            		"ExpressionOrOption");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2197:6: ( (lv_children_8_0= ruleMessage ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2197:6: ( (lv_children_8_0= ruleMessage ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2198:1: (lv_children_8_0= ruleMessage )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2198:1: (lv_children_8_0= ruleMessage )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2199:3: lv_children_8_0= ruleMessage
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMessageParserRuleCall_5_1_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMessage_in_ruleMapMethod3952);
                    	    lv_children_8_0=ruleMessage();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_8_0, 
                    	            		"Message");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2216:6: ( (lv_children_9_0= ruleProperty ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2216:6: ( (lv_children_9_0= ruleProperty ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2217:1: (lv_children_9_0= ruleProperty )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2217:1: (lv_children_9_0= ruleProperty )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2218:3: lv_children_9_0= ruleProperty
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenPropertyParserRuleCall_5_1_1_2_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleProperty_in_ruleMapMethod3979);
                    	    lv_children_9_0=ruleProperty();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_9_0, 
                    	            		"Property");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2235:6: ( (lv_children_10_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2235:6: ( (lv_children_10_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2236:1: (lv_children_10_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2236:1: (lv_children_10_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2237:3: lv_children_10_0= ruleParam
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenParamParserRuleCall_5_1_1_3_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleMapMethod4006);
                    	    lv_children_10_0=ruleParam();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_10_0, 
                    	            		"Param");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2254:6: ( (lv_children_11_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2254:6: ( (lv_children_11_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2255:1: (lv_children_11_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2255:1: (lv_children_11_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2256:3: lv_children_11_0= ruleMap
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMapParserRuleCall_5_1_1_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleMapMethod4033);
                    	    lv_children_11_0=ruleMap();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_11_0, 
                    	            		"Map");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2273:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2273:6: ( (lv_children_12_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2274:1: (lv_children_12_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2274:1: (lv_children_12_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2275:3: lv_children_12_0= ruleMapMethod
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenMapMethodParserRuleCall_5_1_1_5_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleMapMethod4060);
                    	    lv_children_12_0=ruleMapMethod();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_12_0, 
                    	            		"MapMethod");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2292:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2292:6: ( (lv_children_13_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2293:1: (lv_children_13_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2293:1: (lv_children_13_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2294:3: lv_children_13_0= ruleDebugTag
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenDebugTagParserRuleCall_5_1_1_6_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleMapMethod4087);
                    	    lv_children_13_0=ruleDebugTag();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_13_0, 
                    	            		"DebugTag");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 8 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2311:6: ( (lv_children_14_0= ruleField ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2311:6: ( (lv_children_14_0= ruleField ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2312:1: (lv_children_14_0= ruleField )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2312:1: (lv_children_14_0= ruleField )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2313:3: lv_children_14_0= ruleField
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenFieldParserRuleCall_5_1_1_7_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleField_in_ruleMapMethod4114);
                    	    lv_children_14_0=ruleField();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_14_0, 
                    	            		"Field");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 9 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2330:6: ( (lv_children_15_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2330:6: ( (lv_children_15_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2331:1: (lv_children_15_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2331:1: (lv_children_15_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2332:3: lv_children_15_0= ruleComment
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenCommentParserRuleCall_5_1_1_8_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleMapMethod4141);
                    	    lv_children_15_0=ruleComment();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_15_0, 
                    	            		"Comment");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 10 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2349:6: ( (lv_children_16_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2349:6: ( (lv_children_16_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2350:1: (lv_children_16_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2350:1: (lv_children_16_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2351:3: lv_children_16_0= ruleBreak
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMapMethodAccess().getChildrenBreakParserRuleCall_5_1_1_9_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleMapMethod4168);
                    	    lv_children_16_0=ruleBreak();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMapMethodRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_16_0, 
                    	            		"Break");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop40;
                        }
                    } while (true);

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2367:4: ( (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2368:1: (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2368:1: (lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2369:3: lv_closedTag_17_0= RULE_MAP_METHOD_ENDTAG_START
                    {
                    lv_closedTag_17_0=(Token)match(input,RULE_MAP_METHOD_ENDTAG_START,FOLLOW_RULE_MAP_METHOD_ENDTAG_START_in_ruleMapMethod4187); 

                    			newLeafNode(lv_closedTag_17_0, grammarAccess.getMapMethodAccess().getClosedTagMAP_METHOD_ENDTAG_STARTTerminalRuleCall_5_1_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMapMethodRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"MAP_METHOD_ENDTAG_START");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2385:2: ( (lv_methodClosingName_18_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:1: (lv_methodClosingName_18_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2386:1: (lv_methodClosingName_18_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2387:3: lv_methodClosingName_18_0= RULE_ID
                    {
                    lv_methodClosingName_18_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod4209); 

                    			newLeafNode(lv_methodClosingName_18_0, grammarAccess.getMapMethodAccess().getMethodClosingNameIDTerminalRuleCall_5_1_3_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMapMethodRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"methodClosingName",
                            		lv_methodClosingName_18_0, 
                            		"ID");
                    	    

                    }


                    }

                    this_DOT_19=(Token)match(input,RULE_DOT,FOLLOW_RULE_DOT_in_ruleMapMethod4225); 
                     
                        newLeafNode(this_DOT_19, grammarAccess.getMapMethodAccess().getDOTTerminalRuleCall_5_1_4()); 
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2407:1: ( (lv_methodClosingMethod_20_0= RULE_ID ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2408:1: (lv_methodClosingMethod_20_0= RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2408:1: (lv_methodClosingMethod_20_0= RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2409:3: lv_methodClosingMethod_20_0= RULE_ID
                    {
                    lv_methodClosingMethod_20_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapMethod4241); 

                    			newLeafNode(lv_methodClosingMethod_20_0, grammarAccess.getMapMethodAccess().getMethodClosingMethodIDTerminalRuleCall_5_1_5_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMapMethodRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"methodClosingMethod",
                            		lv_methodClosingMethod_20_0, 
                            		"ID");
                    	    

                    }


                    }

                    this_XML_TAG_END_21=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod4257); 
                     
                        newLeafNode(this_XML_TAG_END_21, grammarAccess.getMapMethodAccess().getXML_TAG_ENDTerminalRuleCall_5_1_6()); 
                        

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
    // $ANTLR end "ruleMapMethod"


    // $ANTLR start "entryRuleField"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2437:1: entryRuleField returns [EObject current=null] : iv_ruleField= ruleField EOF ;
    public final EObject entryRuleField() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleField = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2438:2: (iv_ruleField= ruleField EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2439:2: iv_ruleField= ruleField EOF
            {
             newCompositeNode(grammarAccess.getFieldRule()); 
            pushFollow(FOLLOW_ruleField_in_entryRuleField4294);
            iv_ruleField=ruleField();

            state._fsp--;

             current =iv_ruleField; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleField4304); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleField"


    // $ANTLR start "ruleField"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2446:1: ruleField returns [EObject current=null] : (this_FIELD_START_TAG_0= RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) ) ;
    public final EObject ruleField() throws RecognitionException {
        EObject current = null;

        Token this_FIELD_START_TAG_0=null;
        Token this_XML_TAG_SINGLEEND_3=null;
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


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2449:28: ( (this_FIELD_START_TAG_0= RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2450:1: (this_FIELD_START_TAG_0= RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2450:1: (this_FIELD_START_TAG_0= RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2450:2: this_FIELD_START_TAG_0= RULE_FIELD_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) )
            {
            this_FIELD_START_TAG_0=(Token)match(input,RULE_FIELD_START_TAG,FOLLOW_RULE_FIELD_START_TAG_in_ruleField4340); 
             
                newLeafNode(this_FIELD_START_TAG_0, grammarAccess.getFieldAccess().getFIELD_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2454:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2455:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getFieldAccess().getFieldAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2460:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==RULE_ID) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2461:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2461:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2462:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getFieldAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleField4369);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2478:3: (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) ) )
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
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2478:4: this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_3=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleField4382); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_3, grammarAccess.getFieldAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2483:6: ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2483:6: ( ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2483:7: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* ) ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2483:7: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )* )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2483:8: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )*
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2483:8: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2484:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2484:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2485:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleField4406); 

                    			newLeafNode(lv_splitTag_4_0, grammarAccess.getFieldAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getFieldRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2501:2: ( ( (lv_children_5_0= ruleExpressionOrOption ) ) | ( (lv_children_6_0= ruleParam ) ) | ( (lv_children_7_0= ruleMap ) ) | ( (lv_children_8_0= ruleMapMethod ) ) | ( (lv_children_9_0= ruleDebugTag ) ) | ( (lv_children_10_0= ruleComment ) ) | ( (lv_children_11_0= ruleBreak ) ) )*
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
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2501:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2501:3: ( (lv_children_5_0= ruleExpressionOrOption ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2502:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2502:1: (lv_children_5_0= ruleExpressionOrOption )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2503:3: lv_children_5_0= ruleExpressionOrOption
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFieldAccess().getChildrenExpressionOrOptionParserRuleCall_3_1_0_1_0_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleExpressionOrOption_in_ruleField4433);
                    	    lv_children_5_0=ruleExpressionOrOption();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_5_0, 
                    	            		"ExpressionOrOption");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2520:6: ( (lv_children_6_0= ruleParam ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2520:6: ( (lv_children_6_0= ruleParam ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2521:1: (lv_children_6_0= ruleParam )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2521:1: (lv_children_6_0= ruleParam )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2522:3: lv_children_6_0= ruleParam
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFieldAccess().getChildrenParamParserRuleCall_3_1_0_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParam_in_ruleField4460);
                    	    lv_children_6_0=ruleParam();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_6_0, 
                    	            		"Param");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2539:6: ( (lv_children_7_0= ruleMap ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2539:6: ( (lv_children_7_0= ruleMap ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2540:1: (lv_children_7_0= ruleMap )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2540:1: (lv_children_7_0= ruleMap )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2541:3: lv_children_7_0= ruleMap
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFieldAccess().getChildrenMapParserRuleCall_3_1_0_1_2_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMap_in_ruleField4487);
                    	    lv_children_7_0=ruleMap();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_7_0, 
                    	            		"Map");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2558:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2558:6: ( (lv_children_8_0= ruleMapMethod ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2559:1: (lv_children_8_0= ruleMapMethod )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2559:1: (lv_children_8_0= ruleMapMethod )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2560:3: lv_children_8_0= ruleMapMethod
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFieldAccess().getChildrenMapMethodParserRuleCall_3_1_0_1_3_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleMapMethod_in_ruleField4514);
                    	    lv_children_8_0=ruleMapMethod();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_8_0, 
                    	            		"MapMethod");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 5 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2577:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2577:6: ( (lv_children_9_0= ruleDebugTag ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2578:1: (lv_children_9_0= ruleDebugTag )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2578:1: (lv_children_9_0= ruleDebugTag )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2579:3: lv_children_9_0= ruleDebugTag
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFieldAccess().getChildrenDebugTagParserRuleCall_3_1_0_1_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDebugTag_in_ruleField4541);
                    	    lv_children_9_0=ruleDebugTag();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_9_0, 
                    	            		"DebugTag");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 6 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2596:6: ( (lv_children_10_0= ruleComment ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2596:6: ( (lv_children_10_0= ruleComment ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2597:1: (lv_children_10_0= ruleComment )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2597:1: (lv_children_10_0= ruleComment )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2598:3: lv_children_10_0= ruleComment
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFieldAccess().getChildrenCommentParserRuleCall_3_1_0_1_5_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleComment_in_ruleField4568);
                    	    lv_children_10_0=ruleComment();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_10_0, 
                    	            		"Comment");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 7 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2615:6: ( (lv_children_11_0= ruleBreak ) )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2615:6: ( (lv_children_11_0= ruleBreak ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2616:1: (lv_children_11_0= ruleBreak )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2616:1: (lv_children_11_0= ruleBreak )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2617:3: lv_children_11_0= ruleBreak
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFieldAccess().getChildrenBreakParserRuleCall_3_1_0_1_6_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleBreak_in_ruleField4595);
                    	    lv_children_11_0=ruleBreak();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"children",
                    	            		lv_children_11_0, 
                    	            		"Break");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop43;
                        }
                    } while (true);


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2633:5: ( (lv_closedTag_12_0= RULE_FIELD_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2634:1: (lv_closedTag_12_0= RULE_FIELD_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2634:1: (lv_closedTag_12_0= RULE_FIELD_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2635:3: lv_closedTag_12_0= RULE_FIELD_END_TAG
                    {
                    lv_closedTag_12_0=(Token)match(input,RULE_FIELD_END_TAG,FOLLOW_RULE_FIELD_END_TAG_in_ruleField4615); 

                    			newLeafNode(lv_closedTag_12_0, grammarAccess.getFieldAccess().getClosedTagFIELD_END_TAGTerminalRuleCall_3_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getFieldRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"FIELD_END_TAG");
                    	    

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
    // $ANTLR end "ruleField"


    // $ANTLR start "entryRuleDebugTag"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2659:1: entryRuleDebugTag returns [EObject current=null] : iv_ruleDebugTag= ruleDebugTag EOF ;
    public final EObject entryRuleDebugTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDebugTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2660:2: (iv_ruleDebugTag= ruleDebugTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2661:2: iv_ruleDebugTag= ruleDebugTag EOF
            {
             newCompositeNode(grammarAccess.getDebugTagRule()); 
            pushFollow(FOLLOW_ruleDebugTag_in_entryRuleDebugTag4658);
            iv_ruleDebugTag=ruleDebugTag();

            state._fsp--;

             current =iv_ruleDebugTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDebugTag4668); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDebugTag"


    // $ANTLR start "ruleDebugTag"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2668:1: ruleDebugTag returns [EObject current=null] : (this_DEBUG_START_TAG_0= RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) ) ;
    public final EObject ruleDebugTag() throws RecognitionException {
        EObject current = null;

        Token this_DEBUG_START_TAG_0=null;
        Token this_XML_TAG_SINGLEEND_3=null;
        Token lv_splitTag_4_0=null;
        Token lv_closedTag_6_0=null;
        EObject lv_attributes_2_0 = null;

        EObject lv_expression_5_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2671:28: ( (this_DEBUG_START_TAG_0= RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2672:1: (this_DEBUG_START_TAG_0= RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2672:1: (this_DEBUG_START_TAG_0= RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2672:2: this_DEBUG_START_TAG_0= RULE_DEBUG_START_TAG () ( (lv_attributes_2_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) )
            {
            this_DEBUG_START_TAG_0=(Token)match(input,RULE_DEBUG_START_TAG,FOLLOW_RULE_DEBUG_START_TAG_in_ruleDebugTag4704); 
             
                newLeafNode(this_DEBUG_START_TAG_0, grammarAccess.getDebugTagAccess().getDEBUG_START_TAGTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2676:1: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2677:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getDebugTagAccess().getDebugTagAction_1(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2682:2: ( (lv_attributes_2_0= rulePossibleExpression ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==RULE_ID) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2683:1: (lv_attributes_2_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2683:1: (lv_attributes_2_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2684:3: lv_attributes_2_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getDebugTagAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleDebugTag4733);
            	    lv_attributes_2_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getDebugTagRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_2_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2700:3: (this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) ) )
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
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2700:4: this_XML_TAG_SINGLEEND_3= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_3=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag4746); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_3, grammarAccess.getDebugTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_3_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2705:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2705:6: ( ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2705:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) ) ( (lv_expression_5_0= ruleTopLevel ) )? ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2705:7: ( (lv_splitTag_4_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2706:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2706:1: (lv_splitTag_4_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2707:3: lv_splitTag_4_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_4_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag4769); 

                    			newLeafNode(lv_splitTag_4_0, grammarAccess.getDebugTagAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_3_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getDebugTagRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2723:2: ( (lv_expression_5_0= ruleTopLevel ) )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==RULE_ID||LA46_0==RULE_SQBRACKET_OPEN||(LA46_0>=RULE_TML_EXISTS && LA46_0<=RULE_DOLLAR)||(LA46_0>=RULE_NUMBER && LA46_0<=RULE_FALSE)||LA46_0==72||LA46_0==82||LA46_0==84) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2724:1: (lv_expression_5_0= ruleTopLevel )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2724:1: (lv_expression_5_0= ruleTopLevel )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2725:3: lv_expression_5_0= ruleTopLevel
                            {
                             
                            	        newCompositeNode(grammarAccess.getDebugTagAccess().getExpressionTopLevelParserRuleCall_3_1_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleTopLevel_in_ruleDebugTag4795);
                            lv_expression_5_0=ruleTopLevel();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getDebugTagRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"expression",
                                    		lv_expression_5_0, 
                                    		"TopLevel");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }
                            break;

                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2741:3: ( (lv_closedTag_6_0= RULE_DEBUG_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2742:1: (lv_closedTag_6_0= RULE_DEBUG_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2742:1: (lv_closedTag_6_0= RULE_DEBUG_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2743:3: lv_closedTag_6_0= RULE_DEBUG_END_TAG
                    {
                    lv_closedTag_6_0=(Token)match(input,RULE_DEBUG_END_TAG,FOLLOW_RULE_DEBUG_END_TAG_in_ruleDebugTag4813); 

                    			newLeafNode(lv_closedTag_6_0, grammarAccess.getDebugTagAccess().getClosedTagDEBUG_END_TAGTerminalRuleCall_3_1_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getDebugTagRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"DEBUG_END_TAG");
                    	    

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
    // $ANTLR end "ruleDebugTag"


    // $ANTLR start "entryRuleExpressionOrOption"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2767:1: entryRuleExpressionOrOption returns [EObject current=null] : iv_ruleExpressionOrOption= ruleExpressionOrOption EOF ;
    public final EObject entryRuleExpressionOrOption() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionOrOption = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2768:2: (iv_ruleExpressionOrOption= ruleExpressionOrOption EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2769:2: iv_ruleExpressionOrOption= ruleExpressionOrOption EOF
            {
             newCompositeNode(grammarAccess.getExpressionOrOptionRule()); 
            pushFollow(FOLLOW_ruleExpressionOrOption_in_entryRuleExpressionOrOption4856);
            iv_ruleExpressionOrOption=ruleExpressionOrOption();

            state._fsp--;

             current =iv_ruleExpressionOrOption; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionOrOption4866); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionOrOption"


    // $ANTLR start "ruleExpressionOrOption"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2776:1: ruleExpressionOrOption returns [EObject current=null] : ( (this_EXPRESSION_START_TAG_0= RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | (this_OPTION_START_TAG_2= RULE_OPTION_START_TAG this_Option_3= ruleOption ) ) ;
    public final EObject ruleExpressionOrOption() throws RecognitionException {
        EObject current = null;

        Token this_EXPRESSION_START_TAG_0=null;
        Token this_OPTION_START_TAG_2=null;
        EObject this_ExpressionTag_1 = null;

        EObject this_Option_3 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2779:28: ( ( (this_EXPRESSION_START_TAG_0= RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | (this_OPTION_START_TAG_2= RULE_OPTION_START_TAG this_Option_3= ruleOption ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:1: ( (this_EXPRESSION_START_TAG_0= RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | (this_OPTION_START_TAG_2= RULE_OPTION_START_TAG this_Option_3= ruleOption ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:1: ( (this_EXPRESSION_START_TAG_0= RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag ) | (this_OPTION_START_TAG_2= RULE_OPTION_START_TAG this_Option_3= ruleOption ) )
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
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:2: (this_EXPRESSION_START_TAG_0= RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:2: (this_EXPRESSION_START_TAG_0= RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2780:3: this_EXPRESSION_START_TAG_0= RULE_EXPRESSION_START_TAG this_ExpressionTag_1= ruleExpressionTag
                    {
                    this_EXPRESSION_START_TAG_0=(Token)match(input,RULE_EXPRESSION_START_TAG,FOLLOW_RULE_EXPRESSION_START_TAG_in_ruleExpressionOrOption4903); 
                     
                        newLeafNode(this_EXPRESSION_START_TAG_0, grammarAccess.getExpressionOrOptionAccess().getEXPRESSION_START_TAGTerminalRuleCall_0_0()); 
                        
                     
                            newCompositeNode(grammarAccess.getExpressionOrOptionAccess().getExpressionTagParserRuleCall_0_1()); 
                        
                    pushFollow(FOLLOW_ruleExpressionTag_in_ruleExpressionOrOption4924);
                    this_ExpressionTag_1=ruleExpressionTag();

                    state._fsp--;

                     
                            current = this_ExpressionTag_1; 
                            afterParserOrEnumRuleCall();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2794:6: (this_OPTION_START_TAG_2= RULE_OPTION_START_TAG this_Option_3= ruleOption )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2794:6: (this_OPTION_START_TAG_2= RULE_OPTION_START_TAG this_Option_3= ruleOption )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2794:7: this_OPTION_START_TAG_2= RULE_OPTION_START_TAG this_Option_3= ruleOption
                    {
                    this_OPTION_START_TAG_2=(Token)match(input,RULE_OPTION_START_TAG,FOLLOW_RULE_OPTION_START_TAG_in_ruleExpressionOrOption4942); 
                     
                        newLeafNode(this_OPTION_START_TAG_2, grammarAccess.getExpressionOrOptionAccess().getOPTION_START_TAGTerminalRuleCall_1_0()); 
                        
                     
                            newCompositeNode(grammarAccess.getExpressionOrOptionAccess().getOptionParserRuleCall_1_1()); 
                        
                    pushFollow(FOLLOW_ruleOption_in_ruleExpressionOrOption4963);
                    this_Option_3=ruleOption();

                    state._fsp--;

                     
                            current = this_Option_3; 
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleExpressionOrOption"


    // $ANTLR start "entryRuleExpressionTag"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2815:1: entryRuleExpressionTag returns [EObject current=null] : iv_ruleExpressionTag= ruleExpressionTag EOF ;
    public final EObject entryRuleExpressionTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionTag = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2816:2: (iv_ruleExpressionTag= ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2817:2: iv_ruleExpressionTag= ruleExpressionTag EOF
            {
             newCompositeNode(grammarAccess.getExpressionTagRule()); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag4999);
            iv_ruleExpressionTag=ruleExpressionTag();

            state._fsp--;

             current =iv_ruleExpressionTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag5009); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionTag"


    // $ANTLR start "ruleExpressionTag"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2824:1: ruleExpressionTag returns [EObject current=null] : ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) ) ;
    public final EObject ruleExpressionTag() throws RecognitionException {
        EObject current = null;

        Token this_XML_TAG_SINGLEEND_2=null;
        Token lv_splitTag_3_0=null;
        Token lv_closedTag_5_0=null;
        EObject lv_attributes_1_0 = null;

        EObject lv_expression_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2827:28: ( ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2828:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2828:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2828:2: () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2828:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2829:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getExpressionTagAccess().getExpressionTagAction_0(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2834:2: ( (lv_attributes_1_0= rulePossibleExpression ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==RULE_ID) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2835:1: (lv_attributes_1_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2835:1: (lv_attributes_1_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2836:3: lv_attributes_1_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExpressionTagAccess().getAttributesPossibleExpressionParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleExpressionTag5064);
            	    lv_attributes_1_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getExpressionTagRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_1_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2852:3: (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) ) )
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
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2852:4: this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_2=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag5077); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_2, grammarAccess.getExpressionTagAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2857:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2857:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2857:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_expression_4_0= ruleTopLevel ) ) ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2857:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2858:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2858:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2859:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag5100); 

                    			newLeafNode(lv_splitTag_3_0, grammarAccess.getExpressionTagAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_2_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getExpressionTagRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2875:2: ( (lv_expression_4_0= ruleTopLevel ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2876:1: (lv_expression_4_0= ruleTopLevel )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2876:1: (lv_expression_4_0= ruleTopLevel )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2877:3: lv_expression_4_0= ruleTopLevel
                    {
                     
                    	        newCompositeNode(grammarAccess.getExpressionTagAccess().getExpressionTopLevelParserRuleCall_2_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTopLevel_in_ruleExpressionTag5126);
                    lv_expression_4_0=ruleTopLevel();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getExpressionTagRule());
                    	        }
                           		set(
                           			current, 
                           			"expression",
                            		lv_expression_4_0, 
                            		"TopLevel");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2893:2: ( (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2894:1: (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2894:1: (lv_closedTag_5_0= RULE_EXPRESSION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2895:3: lv_closedTag_5_0= RULE_EXPRESSION_END_TAG
                    {
                    lv_closedTag_5_0=(Token)match(input,RULE_EXPRESSION_END_TAG,FOLLOW_RULE_EXPRESSION_END_TAG_in_ruleExpressionTag5143); 

                    			newLeafNode(lv_closedTag_5_0, grammarAccess.getExpressionTagAccess().getClosedTagEXPRESSION_END_TAGTerminalRuleCall_2_1_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getExpressionTagRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"EXPRESSION_END_TAG");
                    	    

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
    // $ANTLR end "ruleExpressionTag"


    // $ANTLR start "entryRuleOption"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2919:1: entryRuleOption returns [EObject current=null] : iv_ruleOption= ruleOption EOF ;
    public final EObject entryRuleOption() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOption = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2920:2: (iv_ruleOption= ruleOption EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2921:2: iv_ruleOption= ruleOption EOF
            {
             newCompositeNode(grammarAccess.getOptionRule()); 
            pushFollow(FOLLOW_ruleOption_in_entryRuleOption5186);
            iv_ruleOption=ruleOption();

            state._fsp--;

             current =iv_ruleOption; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOption5196); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOption"


    // $ANTLR start "ruleOption"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2928:1: ruleOption returns [EObject current=null] : ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) ) ;
    public final EObject ruleOption() throws RecognitionException {
        EObject current = null;

        Token this_XML_TAG_SINGLEEND_2=null;
        Token lv_splitTag_3_0=null;
        Token lv_closedTag_4_0=null;
        EObject lv_attributes_1_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2931:28: ( ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2932:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2932:1: ( () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2932:2: () ( (lv_attributes_1_0= rulePossibleExpression ) )* (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2932:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2933:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getOptionAccess().getOptionAction_0(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2938:2: ( (lv_attributes_1_0= rulePossibleExpression ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==RULE_ID) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2939:1: (lv_attributes_1_0= rulePossibleExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2939:1: (lv_attributes_1_0= rulePossibleExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2940:3: lv_attributes_1_0= rulePossibleExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getOptionAccess().getAttributesPossibleExpressionParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePossibleExpression_in_ruleOption5251);
            	    lv_attributes_1_0=rulePossibleExpression();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getOptionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_1_0, 
            	            		"PossibleExpression");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2956:3: (this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND | ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) ) )
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
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2956:4: this_XML_TAG_SINGLEEND_2= RULE_XML_TAG_SINGLEEND
                    {
                    this_XML_TAG_SINGLEEND_2=(Token)match(input,RULE_XML_TAG_SINGLEEND,FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleOption5264); 
                     
                        newLeafNode(this_XML_TAG_SINGLEEND_2, grammarAccess.getOptionAccess().getXML_TAG_SINGLEENDTerminalRuleCall_2_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2961:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2961:6: ( ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2961:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) ) ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2961:7: ( (lv_splitTag_3_0= RULE_XML_TAG_END ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2962:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2962:1: (lv_splitTag_3_0= RULE_XML_TAG_END )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2963:3: lv_splitTag_3_0= RULE_XML_TAG_END
                    {
                    lv_splitTag_3_0=(Token)match(input,RULE_XML_TAG_END,FOLLOW_RULE_XML_TAG_END_in_ruleOption5287); 

                    			newLeafNode(lv_splitTag_3_0, grammarAccess.getOptionAccess().getSplitTagXML_TAG_ENDTerminalRuleCall_2_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getOptionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"splitTag",
                            		true, 
                            		"XML_TAG_END");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2979:2: ( (lv_closedTag_4_0= RULE_OPTION_END_TAG ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2980:1: (lv_closedTag_4_0= RULE_OPTION_END_TAG )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2980:1: (lv_closedTag_4_0= RULE_OPTION_END_TAG )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:2981:3: lv_closedTag_4_0= RULE_OPTION_END_TAG
                    {
                    lv_closedTag_4_0=(Token)match(input,RULE_OPTION_END_TAG,FOLLOW_RULE_OPTION_END_TAG_in_ruleOption5309); 

                    			newLeafNode(lv_closedTag_4_0, grammarAccess.getOptionAccess().getClosedTagOPTION_END_TAGTerminalRuleCall_2_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getOptionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"closedTag",
                            		true, 
                            		"OPTION_END_TAG");
                    	    

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
    // $ANTLR end "ruleOption"


    // $ANTLR start "entryRuleTopLevel"
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3005:1: entryRuleTopLevel returns [EObject current=null] : iv_ruleTopLevel= ruleTopLevel EOF ;
    public final EObject entryRuleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTopLevel = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3006:2: (iv_ruleTopLevel= ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3007:2: iv_ruleTopLevel= ruleTopLevel EOF
            {
             newCompositeNode(grammarAccess.getTopLevelRule()); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel5352);
            iv_ruleTopLevel=ruleTopLevel();

            state._fsp--;

             current =iv_ruleTopLevel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel5362); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3014:1: ruleTopLevel returns [EObject current=null] : ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) ;
    public final EObject ruleTopLevel() throws RecognitionException {
        EObject current = null;

        EObject lv_toplevelExpression_0_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3017:28: ( ( (lv_toplevelExpression_0_0= ruleOrExpression ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3018:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3018:1: ( (lv_toplevelExpression_0_0= ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3019:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3019:1: (lv_toplevelExpression_0_0= ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3020:3: lv_toplevelExpression_0_0= ruleOrExpression
            {
             
            	        newCompositeNode(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleTopLevel5407);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3044:1: entryRulePathElement returns [String current=null] : iv_rulePathElement= rulePathElement EOF ;
    public final String entryRulePathElement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathElement = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3045:2: (iv_rulePathElement= rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3046:2: iv_rulePathElement= rulePathElement EOF
            {
             newCompositeNode(grammarAccess.getPathElementRule()); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement5443);
            iv_rulePathElement=rulePathElement();

            state._fsp--;

             current =iv_rulePathElement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement5454); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3053:1: rulePathElement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) ;
    public final AntlrDatatypeRuleToken rulePathElement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_PARENT_2=null;

         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3056:28: ( (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3057:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3057:1: (this_ID_0= RULE_ID | kw= '.' | this_PARENT_2= RULE_PARENT )
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
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }

            switch (alt53) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3057:6: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePathElement5494); 

                    		current.merge(this_ID_0);
                        
                     
                        newLeafNode(this_ID_0, grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3066:2: kw= '.'
                    {
                    kw=(Token)match(input,RULE_DOT,FOLLOW_RULE_DOT_in_rulePathElement5518); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3072:10: this_PARENT_2= RULE_PARENT
                    {
                    this_PARENT_2=(Token)match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_rulePathElement5539); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3087:1: entryRuleTmlExpression returns [EObject current=null] : iv_ruleTmlExpression= ruleTmlExpression EOF ;
    public final EObject entryRuleTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3088:2: (iv_ruleTmlExpression= ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3089:2: iv_ruleTmlExpression= ruleTmlExpression EOF
            {
             newCompositeNode(grammarAccess.getTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression5584);
            iv_ruleTmlExpression=ruleTmlExpression();

            state._fsp--;

             current =iv_ruleTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression5594); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3096:1: ruleTmlExpression returns [EObject current=null] : (this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3099:28: ( (this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3100:1: (this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3100:1: (this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3100:2: this_SQBRACKET_OPEN_0= RULE_SQBRACKET_OPEN ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )? ( (lv_param_2_0= RULE_AT ) )? ( (lv_elements_3_0= rulePathElement ) ) (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_6= RULE_SQBRACKET_CLOSE
            {
            this_SQBRACKET_OPEN_0=(Token)match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression5630); 
             
                newLeafNode(this_SQBRACKET_OPEN_0, grammarAccess.getTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_0()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3104:1: ( (lv_absolute_1_0= RULE_TML_SEPARATOR ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_TML_SEPARATOR) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3105:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3105:1: (lv_absolute_1_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3106:3: lv_absolute_1_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_1_0=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5646); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3122:3: ( (lv_param_2_0= RULE_AT ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_AT) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3123:1: (lv_param_2_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3123:1: (lv_param_2_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3124:3: lv_param_2_0= RULE_AT
                    {
                    lv_param_2_0=(Token)match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleTmlExpression5669); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3140:3: ( (lv_elements_3_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3141:1: (lv_elements_3_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3141:1: (lv_elements_3_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3142:3: lv_elements_3_0= rulePathElement
            {
             
            	        newCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression5696);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3158:2: (this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==RULE_TML_SEPARATOR) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3158:3: this_TML_SEPARATOR_4= RULE_TML_SEPARATOR ( (lv_elements_5_0= rulePathElement ) )
            	    {
            	    this_TML_SEPARATOR_4=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5708); 
            	     
            	        newLeafNode(this_TML_SEPARATOR_4, grammarAccess.getTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_4_0()); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3162:1: ( (lv_elements_5_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3163:1: (lv_elements_5_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3163:1: (lv_elements_5_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3164:3: lv_elements_5_0= rulePathElement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleTmlExpression5728);
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
            	    break loop56;
                }
            } while (true);

            this_SQBRACKET_CLOSE_6=(Token)match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression5741); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3192:1: entryRuleExistsTmlExpression returns [EObject current=null] : iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF ;
    public final EObject entryRuleExistsTmlExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExistsTmlExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3193:2: (iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3194:2: iv_ruleExistsTmlExpression= ruleExistsTmlExpression EOF
            {
             newCompositeNode(grammarAccess.getExistsTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression5776);
            iv_ruleExistsTmlExpression=ruleExistsTmlExpression();

            state._fsp--;

             current =iv_ruleExistsTmlExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression5786); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3201:1: ruleExistsTmlExpression returns [EObject current=null] : (this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3204:28: ( (this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3205:1: (this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3205:1: (this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3205:2: this_TML_EXISTS_0= RULE_TML_EXISTS this_SQBRACKET_OPEN_1= RULE_SQBRACKET_OPEN ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )? ( (lv_param_3_0= RULE_AT ) )? ( (lv_elements_4_0= rulePathElement ) ) (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )* this_SQBRACKET_CLOSE_7= RULE_SQBRACKET_CLOSE
            {
            this_TML_EXISTS_0=(Token)match(input,RULE_TML_EXISTS,FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression5822); 
             
                newLeafNode(this_TML_EXISTS_0, grammarAccess.getExistsTmlExpressionAccess().getTML_EXISTSTerminalRuleCall_0()); 
                
            this_SQBRACKET_OPEN_1=(Token)match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression5832); 
             
                newLeafNode(this_SQBRACKET_OPEN_1, grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_1()); 
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3213:1: ( (lv_absolute_2_0= RULE_TML_SEPARATOR ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==RULE_TML_SEPARATOR) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3214:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3214:1: (lv_absolute_2_0= RULE_TML_SEPARATOR )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3215:3: lv_absolute_2_0= RULE_TML_SEPARATOR
                    {
                    lv_absolute_2_0=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5848); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3231:3: ( (lv_param_3_0= RULE_AT ) )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==RULE_AT) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3232:1: (lv_param_3_0= RULE_AT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3232:1: (lv_param_3_0= RULE_AT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3233:3: lv_param_3_0= RULE_AT
                    {
                    lv_param_3_0=(Token)match(input,RULE_AT,FOLLOW_RULE_AT_in_ruleExistsTmlExpression5871); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3249:3: ( (lv_elements_4_0= rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3250:1: (lv_elements_4_0= rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3250:1: (lv_elements_4_0= rulePathElement )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3251:3: lv_elements_4_0= rulePathElement
            {
             
            	        newCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression5898);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3267:2: (this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) ) )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==RULE_TML_SEPARATOR) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3267:3: this_TML_SEPARATOR_5= RULE_TML_SEPARATOR ( (lv_elements_6_0= rulePathElement ) )
            	    {
            	    this_TML_SEPARATOR_5=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5910); 
            	     
            	        newLeafNode(this_TML_SEPARATOR_5, grammarAccess.getExistsTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_5_0()); 
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3271:1: ( (lv_elements_6_0= rulePathElement ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3272:1: (lv_elements_6_0= rulePathElement )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3272:1: (lv_elements_6_0= rulePathElement )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3273:3: lv_elements_6_0= rulePathElement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_5_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePathElement_in_ruleExistsTmlExpression5930);
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
            	    break loop59;
                }
            } while (true);

            this_SQBRACKET_CLOSE_7=(Token)match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression5943); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3301:1: entryRuleMapReferenceParams returns [EObject current=null] : iv_ruleMapReferenceParams= ruleMapReferenceParams EOF ;
    public final EObject entryRuleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapReferenceParams = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3302:2: (iv_ruleMapReferenceParams= ruleMapReferenceParams EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3303:2: iv_ruleMapReferenceParams= ruleMapReferenceParams EOF
            {
             newCompositeNode(grammarAccess.getMapReferenceParamsRule()); 
            pushFollow(FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams5978);
            iv_ruleMapReferenceParams=ruleMapReferenceParams();

            state._fsp--;

             current =iv_ruleMapReferenceParams; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapReferenceParams5988); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3310:1: ruleMapReferenceParams returns [EObject current=null] : (otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')' ) ;
    public final EObject ruleMapReferenceParams() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_getterParams_1_0 = null;

        EObject lv_getterParams_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3313:28: ( (otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3314:1: (otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3314:1: (otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3314:3: otherlv_0= '(' ( (lv_getterParams_1_0= ruleLiteral ) ) (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )* otherlv_4= ')'
            {
            otherlv_0=(Token)match(input,72,FOLLOW_72_in_ruleMapReferenceParams6025); 

                	newLeafNode(otherlv_0, grammarAccess.getMapReferenceParamsAccess().getLeftParenthesisKeyword_0());
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3318:1: ( (lv_getterParams_1_0= ruleLiteral ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3319:1: (lv_getterParams_1_0= ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3319:1: (lv_getterParams_1_0= ruleLiteral )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3320:3: lv_getterParams_1_0= ruleLiteral
            {
             
            	        newCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams6046);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3336:2: (otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==73) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3336:4: otherlv_2= ',' ( (lv_getterParams_3_0= ruleLiteral ) )
            	    {
            	    otherlv_2=(Token)match(input,73,FOLLOW_73_in_ruleMapReferenceParams6059); 

            	        	newLeafNode(otherlv_2, grammarAccess.getMapReferenceParamsAccess().getCommaKeyword_2_0());
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3340:1: ( (lv_getterParams_3_0= ruleLiteral ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3341:1: (lv_getterParams_3_0= ruleLiteral )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3341:1: (lv_getterParams_3_0= ruleLiteral )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3342:3: lv_getterParams_3_0= ruleLiteral
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLiteral_in_ruleMapReferenceParams6080);
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
            	    break loop60;
                }
            } while (true);

            otherlv_4=(Token)match(input,74,FOLLOW_74_in_ruleMapReferenceParams6094); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3370:1: entryRuleMapGetReference returns [EObject current=null] : iv_ruleMapGetReference= ruleMapGetReference EOF ;
    public final EObject entryRuleMapGetReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapGetReference = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3371:2: (iv_ruleMapGetReference= ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3372:2: iv_ruleMapGetReference= ruleMapGetReference EOF
            {
             newCompositeNode(grammarAccess.getMapGetReferenceRule()); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference6130);
            iv_ruleMapGetReference=ruleMapGetReference();

            state._fsp--;

             current =iv_ruleMapGetReference; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference6140); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3379:1: ruleMapGetReference returns [EObject current=null] : ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) ;
    public final EObject ruleMapGetReference() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        Token lv_elements_1_0=null;
        Token this_TML_SEPARATOR_2=null;
        Token lv_elements_3_0=null;
        EObject lv_referenceParams_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3382:28: ( ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3383:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3383:1: ( ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3383:2: ( (lv_operations_0_0= RULE_DOLLAR ) ) ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )* ( (lv_elements_3_0= RULE_ID ) ) ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3383:2: ( (lv_operations_0_0= RULE_DOLLAR ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3384:1: (lv_operations_0_0= RULE_DOLLAR )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3384:1: (lv_operations_0_0= RULE_DOLLAR )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3385:3: lv_operations_0_0= RULE_DOLLAR
            {
            lv_operations_0_0=(Token)match(input,RULE_DOLLAR,FOLLOW_RULE_DOLLAR_in_ruleMapGetReference6182); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3401:2: ( ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==RULE_PARENT) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3401:3: ( (lv_elements_1_0= RULE_PARENT ) ) this_TML_SEPARATOR_2= RULE_TML_SEPARATOR
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3401:3: ( (lv_elements_1_0= RULE_PARENT ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3402:1: (lv_elements_1_0= RULE_PARENT )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3402:1: (lv_elements_1_0= RULE_PARENT )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3403:3: lv_elements_1_0= RULE_PARENT
            	    {
            	    lv_elements_1_0=(Token)match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_ruleMapGetReference6205); 

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

            	    this_TML_SEPARATOR_2=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference6221); 
            	     
            	        newLeafNode(this_TML_SEPARATOR_2, grammarAccess.getMapGetReferenceAccess().getTML_SEPARATORTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3423:3: ( (lv_elements_3_0= RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3424:1: (lv_elements_3_0= RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3424:1: (lv_elements_3_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3425:3: lv_elements_3_0= RULE_ID
            {
            lv_elements_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMapGetReference6239); 

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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3441:2: ( (lv_referenceParams_4_0= ruleMapReferenceParams ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==72) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3442:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3442:1: (lv_referenceParams_4_0= ruleMapReferenceParams )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3443:3: lv_referenceParams_4_0= ruleMapReferenceParams
                    {
                     
                    	        newCompositeNode(grammarAccess.getMapGetReferenceAccess().getReferenceParamsMapReferenceParamsParserRuleCall_3_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference6265);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3467:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3468:2: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3469:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             newCompositeNode(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression6302);
            iv_ruleOrExpression=ruleOrExpression();

            state._fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression6312); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3476:1: ruleOrExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3479:28: ( ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3480:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3480:1: ( ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3480:2: ( (lv_parameters_0_0= ruleAndExpression ) ) ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3480:2: ( (lv_parameters_0_0= ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3481:1: (lv_parameters_0_0= ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3481:1: (lv_parameters_0_0= ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3482:3: lv_parameters_0_0= ruleAndExpression
            {
             
            	        newCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression6358);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3498:2: ( ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) ) )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==75) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3498:3: ( (lv_operations_1_0= 'OR' ) ) ( (lv_parameters_2_0= ruleAndExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3498:3: ( (lv_operations_1_0= 'OR' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3499:1: (lv_operations_1_0= 'OR' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3499:1: (lv_operations_1_0= 'OR' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3500:3: lv_operations_1_0= 'OR'
            	    {
            	    lv_operations_1_0=(Token)match(input,75,FOLLOW_75_in_ruleOrExpression6377); 

            	            newLeafNode(lv_operations_1_0, grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getOrExpressionRule());
            	    	        }
            	           		addWithLastConsumed(current, "operations", lv_operations_1_0, "OR");
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3513:2: ( (lv_parameters_2_0= ruleAndExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3514:1: (lv_parameters_2_0= ruleAndExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3514:1: (lv_parameters_2_0= ruleAndExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3515:3: lv_parameters_2_0= ruleAndExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression6411);
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
            	    break loop63;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3539:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3540:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3541:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             newCompositeNode(grammarAccess.getAndExpressionRule()); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression6449);
            iv_ruleAndExpression=ruleAndExpression();

            state._fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression6459); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3548:1: ruleAndExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3551:28: ( ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3552:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3552:1: ( ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3552:2: ( (lv_parameters_0_0= ruleEqualityExpression ) ) ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3552:2: ( (lv_parameters_0_0= ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3553:1: (lv_parameters_0_0= ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3553:1: (lv_parameters_0_0= ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3554:3: lv_parameters_0_0= ruleEqualityExpression
            {
             
            	        newCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression6505);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3570:2: ( ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==76) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3570:3: ( (lv_operations_1_0= 'AND' ) ) ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3570:3: ( (lv_operations_1_0= 'AND' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3571:1: (lv_operations_1_0= 'AND' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3571:1: (lv_operations_1_0= 'AND' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3572:3: lv_operations_1_0= 'AND'
            	    {
            	    lv_operations_1_0=(Token)match(input,76,FOLLOW_76_in_ruleAndExpression6524); 

            	            newLeafNode(lv_operations_1_0, grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getAndExpressionRule());
            	    	        }
            	           		addWithLastConsumed(current, "operations", lv_operations_1_0, "AND");
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3585:2: ( (lv_parameters_2_0= ruleEqualityExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3586:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3586:1: (lv_parameters_2_0= ruleEqualityExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3587:3: lv_parameters_2_0= ruleEqualityExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleEqualityExpression_in_ruleAndExpression6558);
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
            	    break loop64;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3611:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3612:2: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3613:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             newCompositeNode(grammarAccess.getEqualityExpressionRule()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression6596);
            iv_ruleEqualityExpression=ruleEqualityExpression();

            state._fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression6606); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3620:1: ruleEqualityExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3623:28: ( ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3624:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3624:1: ( ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3624:2: ( (lv_parameters_0_0= ruleRelationalExpression ) ) ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3624:2: ( (lv_parameters_0_0= ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3625:1: (lv_parameters_0_0= ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3625:1: (lv_parameters_0_0= ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3626:3: lv_parameters_0_0= ruleRelationalExpression
            {
             
            	        newCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6652);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3642:2: ( ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) ) | ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) ) )?
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3642:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3642:3: ( ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3642:4: ( (lv_operations_1_0= '==' ) ) ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3642:4: ( (lv_operations_1_0= '==' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3643:1: (lv_operations_1_0= '==' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3643:1: (lv_operations_1_0= '==' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3644:3: lv_operations_1_0= '=='
                    {
                    lv_operations_1_0=(Token)match(input,77,FOLLOW_77_in_ruleEqualityExpression6672); 

                            newLeafNode(lv_operations_1_0, grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getEqualityExpressionRule());
                    	        }
                           		addWithLastConsumed(current, "operations", lv_operations_1_0, "==");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3657:2: ( (lv_parameters_2_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3658:1: (lv_parameters_2_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3658:1: (lv_parameters_2_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3659:3: lv_parameters_2_0= ruleRelationalExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6706);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3676:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3676:6: ( ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3676:7: ( (lv_operations_3_0= '!=' ) ) ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3676:7: ( (lv_operations_3_0= '!=' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3677:1: (lv_operations_3_0= '!=' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3677:1: (lv_operations_3_0= '!=' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3678:3: lv_operations_3_0= '!='
                    {
                    lv_operations_3_0=(Token)match(input,78,FOLLOW_78_in_ruleEqualityExpression6732); 

                            newLeafNode(lv_operations_3_0, grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getEqualityExpressionRule());
                    	        }
                           		addWithLastConsumed(current, "operations", lv_operations_3_0, "!=");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3691:2: ( (lv_parameters_4_0= ruleRelationalExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3692:1: (lv_parameters_4_0= ruleRelationalExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3692:1: (lv_parameters_4_0= ruleRelationalExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3693:3: lv_parameters_4_0= ruleRelationalExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6766);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3717:1: entryRuleRelationalExpression returns [EObject current=null] : iv_ruleRelationalExpression= ruleRelationalExpression EOF ;
    public final EObject entryRuleRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationalExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3718:2: (iv_ruleRelationalExpression= ruleRelationalExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3719:2: iv_ruleRelationalExpression= ruleRelationalExpression EOF
            {
             newCompositeNode(grammarAccess.getRelationalExpressionRule()); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression6805);
            iv_ruleRelationalExpression=ruleRelationalExpression();

            state._fsp--;

             current =iv_ruleRelationalExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRelationalExpression6815); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3726:1: ruleRelationalExpression returns [EObject current=null] : ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3729:28: ( ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3730:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3730:1: ( () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )? )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3730:2: () ( (lv_parameters_1_0= ruleAdditiveExpression ) ) ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3730:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3731:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getRelationalExpressionAccess().getExpressionAction_0(),
                        current);
                

            }

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3736:2: ( (lv_parameters_1_0= ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3737:1: (lv_parameters_1_0= ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3737:1: (lv_parameters_1_0= ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3738:3: lv_parameters_1_0= ruleAdditiveExpression
            {
             
            	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6870);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3754:2: ( ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) ) | ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) ) )?
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3754:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3754:3: ( ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3754:4: ( (lv_operations_2_0= RULE_XML_LT ) ) ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3754:4: ( (lv_operations_2_0= RULE_XML_LT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3755:1: (lv_operations_2_0= RULE_XML_LT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3755:1: (lv_operations_2_0= RULE_XML_LT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3756:3: lv_operations_2_0= RULE_XML_LT
                    {
                    lv_operations_2_0=(Token)match(input,RULE_XML_LT,FOLLOW_RULE_XML_LT_in_ruleRelationalExpression6889); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3772:2: ( (lv_parameters_3_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3773:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3773:1: (lv_parameters_3_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3774:3: lv_parameters_3_0= ruleAdditiveExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6915);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3791:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3791:6: ( ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3791:7: ( (lv_operations_4_0= RULE_XML_GT ) ) ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3791:7: ( (lv_operations_4_0= RULE_XML_GT ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3792:1: (lv_operations_4_0= RULE_XML_GT )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3792:1: (lv_operations_4_0= RULE_XML_GT )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3793:3: lv_operations_4_0= RULE_XML_GT
                    {
                    lv_operations_4_0=(Token)match(input,RULE_XML_GT,FOLLOW_RULE_XML_GT_in_ruleRelationalExpression6940); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3809:2: ( (lv_parameters_5_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3810:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3810:1: (lv_parameters_5_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3811:3: lv_parameters_5_0= ruleAdditiveExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6966);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3828:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3828:6: ( ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3828:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) ) ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3828:7: ( (lv_operations_6_0= RULE_XML_LTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3829:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3829:1: (lv_operations_6_0= RULE_XML_LTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3830:3: lv_operations_6_0= RULE_XML_LTEQ
                    {
                    lv_operations_6_0=(Token)match(input,RULE_XML_LTEQ,FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression6991); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3846:2: ( (lv_parameters_7_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3847:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3847:1: (lv_parameters_7_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3848:3: lv_parameters_7_0= ruleAdditiveExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_2_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression7017);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3865:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3865:6: ( ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3865:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) ) ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3865:7: ( (lv_operations_8_0= RULE_XML_GTEQ ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3866:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3866:1: (lv_operations_8_0= RULE_XML_GTEQ )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3867:3: lv_operations_8_0= RULE_XML_GTEQ
                    {
                    lv_operations_8_0=(Token)match(input,RULE_XML_GTEQ,FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression7042); 

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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3883:2: ( (lv_parameters_9_0= ruleAdditiveExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3884:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3884:1: (lv_parameters_9_0= ruleAdditiveExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3885:3: lv_parameters_9_0= ruleAdditiveExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression7068);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3909:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3910:2: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3911:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             newCompositeNode(grammarAccess.getAdditiveExpressionRule()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression7107);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();

            state._fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression7117); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3918:1: ruleAdditiveExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3921:28: ( ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3922:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3922:1: ( ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3922:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) ) ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3922:2: ( (lv_parameters_0_0= ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3923:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3923:1: (lv_parameters_0_0= ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3924:3: lv_parameters_0_0= ruleMultiplicativeExpression
            {
             
            	        newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7163);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3940:2: ( (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) ) | (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) ) )*
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3940:3: (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3940:3: (otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3940:5: otherlv_1= '+' ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    {
            	    otherlv_1=(Token)match(input,79,FOLLOW_79_in_ruleAdditiveExpression7177); 

            	        	newLeafNode(otherlv_1, grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0());
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3944:1: ( (lv_parameters_2_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3945:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3945:1: (lv_parameters_2_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3946:3: lv_parameters_2_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7198);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3963:6: (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3963:6: (otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3963:8: otherlv_3= '-' ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,80,FOLLOW_80_in_ruleAdditiveExpression7218); 

            	        	newLeafNode(otherlv_3, grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0());
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3967:1: ( (lv_parameters_4_0= ruleMultiplicativeExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3968:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3968:1: (lv_parameters_4_0= ruleMultiplicativeExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3969:3: lv_parameters_4_0= ruleMultiplicativeExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7239);
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
            	    break loop67;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3993:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3994:2: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:3995:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             newCompositeNode(grammarAccess.getMultiplicativeExpressionRule()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression7278);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();

            state._fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression7288); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4002:1: ruleMultiplicativeExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_1_0=null;
        Token lv_operations_3_0=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_parameters_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4005:28: ( ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4006:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4006:1: ( ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )* )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4006:2: ( (lv_parameters_0_0= ruleUnaryExpression ) ) ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4006:2: ( (lv_parameters_0_0= ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4007:1: (lv_parameters_0_0= ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4007:1: (lv_parameters_0_0= ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4008:3: lv_parameters_0_0= ruleUnaryExpression
            {
             
            	        newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7334);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4024:2: ( ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) ) | ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) ) )*
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4024:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4024:3: ( ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4024:4: ( (lv_operations_1_0= '*' ) ) ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4024:4: ( (lv_operations_1_0= '*' ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4025:1: (lv_operations_1_0= '*' )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4025:1: (lv_operations_1_0= '*' )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4026:3: lv_operations_1_0= '*'
            	    {
            	    lv_operations_1_0=(Token)match(input,81,FOLLOW_81_in_ruleMultiplicativeExpression7354); 

            	            newLeafNode(lv_operations_1_0, grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0());
            	        

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getMultiplicativeExpressionRule());
            	    	        }
            	           		addWithLastConsumed(current, "operations", lv_operations_1_0, "*");
            	    	    

            	    }


            	    }

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4039:2: ( (lv_parameters_2_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4040:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4040:1: (lv_parameters_2_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4041:3: lv_parameters_2_0= ruleUnaryExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7388);
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
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4058:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4058:6: ( ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4058:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) ) ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4058:7: ( (lv_operations_3_0= RULE_TML_SEPARATOR ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4059:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4059:1: (lv_operations_3_0= RULE_TML_SEPARATOR )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4060:3: lv_operations_3_0= RULE_TML_SEPARATOR
            	    {
            	    lv_operations_3_0=(Token)match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression7413); 

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

            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4076:2: ( (lv_parameters_4_0= ruleUnaryExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4077:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4077:1: (lv_parameters_4_0= ruleUnaryExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4078:3: lv_parameters_4_0= ruleUnaryExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7439);
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
            	    break loop68;
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4102:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4103:2: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4104:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             newCompositeNode(grammarAccess.getUnaryExpressionRule()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression7478);
            iv_ruleUnaryExpression=ruleUnaryExpression();

            state._fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression7488); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4111:1: ruleUnaryExpression returns [EObject current=null] : ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        Token lv_operations_0_0=null;
        EObject lv_parameters_1_0 = null;

        EObject this_PrimaryExpression_2 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4114:28: ( ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4115:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4115:1: ( ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) ) | this_PrimaryExpression_2= rulePrimaryExpression )
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
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }
            switch (alt69) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4115:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4115:2: ( ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4115:3: ( (lv_operations_0_0= '!' ) ) ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4115:3: ( (lv_operations_0_0= '!' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4116:1: (lv_operations_0_0= '!' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4116:1: (lv_operations_0_0= '!' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4117:3: lv_operations_0_0= '!'
                    {
                    lv_operations_0_0=(Token)match(input,82,FOLLOW_82_in_ruleUnaryExpression7532); 

                            newLeafNode(lv_operations_0_0, grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getUnaryExpressionRule());
                    	        }
                           		addWithLastConsumed(current, "operations", lv_operations_0_0, "!");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4130:2: ( (lv_parameters_1_0= rulePrimaryExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4131:1: (lv_parameters_1_0= rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4131:1: (lv_parameters_1_0= rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4132:3: lv_parameters_1_0= rulePrimaryExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7566);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4150:5: this_PrimaryExpression_2= rulePrimaryExpression
                    {
                     
                            newCompositeNode(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7595);
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4166:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4167:2: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4168:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             newCompositeNode(grammarAccess.getPrimaryExpressionRule()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression7630);
            iv_rulePrimaryExpression=rulePrimaryExpression();

            state._fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression7640); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4175:1: rulePrimaryExpression returns [EObject current=null] : ( ( (lv_parameters_0_0= ruleLiteral ) ) | (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' ) ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_0_0 = null;

        EObject lv_parameters_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4178:28: ( ( ( (lv_parameters_0_0= ruleLiteral ) ) | (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4179:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4179:1: ( ( (lv_parameters_0_0= ruleLiteral ) ) | (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' ) )
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
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4179:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4179:2: ( (lv_parameters_0_0= ruleLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4180:1: (lv_parameters_0_0= ruleLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4180:1: (lv_parameters_0_0= ruleLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4181:3: lv_parameters_0_0= ruleLiteral
                    {
                     
                    	        newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleLiteral_in_rulePrimaryExpression7686);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4198:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4198:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4198:8: otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,72,FOLLOW_72_in_rulePrimaryExpression7705); 

                        	newLeafNode(otherlv_1, grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0());
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4202:1: ( (lv_parameters_2_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4203:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4203:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4204:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_rulePrimaryExpression7726);
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

                    otherlv_3=(Token)match(input,74,FOLLOW_74_in_rulePrimaryExpression7738); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4232:1: entryRuleFunctionName returns [String current=null] : iv_ruleFunctionName= ruleFunctionName EOF ;
    public final String entryRuleFunctionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFunctionName = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4233:2: (iv_ruleFunctionName= ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4234:2: iv_ruleFunctionName= ruleFunctionName EOF
            {
             newCompositeNode(grammarAccess.getFunctionNameRule()); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName7776);
            iv_ruleFunctionName=ruleFunctionName();

            state._fsp--;

             current =iv_ruleFunctionName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName7787); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4241:1: ruleFunctionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFunctionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4244:28: (this_ID_0= RULE_ID )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4245:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName7826); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4260:1: entryRuleFunctionCall returns [EObject current=null] : iv_ruleFunctionCall= ruleFunctionCall EOF ;
    public final EObject entryRuleFunctionCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunctionCall = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4261:2: (iv_ruleFunctionCall= ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4262:2: iv_ruleFunctionCall= ruleFunctionCall EOF
            {
             newCompositeNode(grammarAccess.getFunctionCallRule()); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall7870);
            iv_ruleFunctionCall=ruleFunctionCall();

            state._fsp--;

             current =iv_ruleFunctionCall; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall7880); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4269:1: ruleFunctionCall returns [EObject current=null] : ( ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')' ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4272:28: ( ( ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')' ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4273:1: ( ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')' )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4273:1: ( ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')' )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4273:2: ( (lv_name_0_0= ruleFunctionName ) ) otherlv_1= '(' ( (lv_parameters_2_0= ruleOrExpression ) )? (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )* otherlv_5= ')'
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4273:2: ( (lv_name_0_0= ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4274:1: (lv_name_0_0= ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4274:1: (lv_name_0_0= ruleFunctionName )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4275:3: lv_name_0_0= ruleFunctionName
            {
             
            	        newCompositeNode(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleFunctionName_in_ruleFunctionCall7926);
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

            otherlv_1=(Token)match(input,72,FOLLOW_72_in_ruleFunctionCall7938); 

                	newLeafNode(otherlv_1, grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1());
                
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4295:1: ( (lv_parameters_2_0= ruleOrExpression ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==RULE_ID||LA71_0==RULE_SQBRACKET_OPEN||(LA71_0>=RULE_TML_EXISTS && LA71_0<=RULE_DOLLAR)||(LA71_0>=RULE_NUMBER && LA71_0<=RULE_FALSE)||LA71_0==72||LA71_0==82||LA71_0==84) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:1: (lv_parameters_2_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4296:1: (lv_parameters_2_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4297:3: lv_parameters_2_0= ruleOrExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall7959);
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

            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4313:3: (otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) ) )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==73) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4313:5: otherlv_3= ',' ( (lv_parameters_4_0= ruleOrExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,73,FOLLOW_73_in_ruleFunctionCall7973); 

            	        	newLeafNode(otherlv_3, grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0());
            	        
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4317:1: ( (lv_parameters_4_0= ruleOrExpression ) )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4318:1: (lv_parameters_4_0= ruleOrExpression )
            	    {
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4318:1: (lv_parameters_4_0= ruleOrExpression )
            	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4319:3: lv_parameters_4_0= ruleOrExpression
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleOrExpression_in_ruleFunctionCall7994);
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
            	    break loop72;
                }
            } while (true);

            otherlv_5=(Token)match(input,74,FOLLOW_74_in_ruleFunctionCall8008); 

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4347:1: entryRuleDateLiteral returns [EObject current=null] : iv_ruleDateLiteral= ruleDateLiteral EOF ;
    public final EObject entryRuleDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDateLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4348:2: (iv_ruleDateLiteral= ruleDateLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4349:2: iv_ruleDateLiteral= ruleDateLiteral EOF
            {
             newCompositeNode(grammarAccess.getDateLiteralRule()); 
            pushFollow(FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral8044);
            iv_ruleDateLiteral=ruleDateLiteral();

            state._fsp--;

             current =iv_ruleDateLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDateLiteral8054); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4356:1: ruleDateLiteral returns [EObject current=null] : ( () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4359:28: ( ( () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4360:1: ( () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4360:1: ( () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4360:2: () this_NUMBER_1= RULE_NUMBER otherlv_2= '#' this_NUMBER_3= RULE_NUMBER otherlv_4= '#' this_NUMBER_5= RULE_NUMBER otherlv_6= '#' this_NUMBER_7= RULE_NUMBER otherlv_8= '#' this_NUMBER_9= RULE_NUMBER otherlv_10= '#' this_NUMBER_11= RULE_NUMBER
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4360:2: ()
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4361:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getDateLiteralAccess().getExpressionAction_0(),
                        current);
                

            }

            this_NUMBER_1=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8099); 
             
                newLeafNode(this_NUMBER_1, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_1()); 
                
            otherlv_2=(Token)match(input,83,FOLLOW_83_in_ruleDateLiteral8110); 

                	newLeafNode(otherlv_2, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_2());
                
            this_NUMBER_3=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8121); 
             
                newLeafNode(this_NUMBER_3, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_3()); 
                
            otherlv_4=(Token)match(input,83,FOLLOW_83_in_ruleDateLiteral8132); 

                	newLeafNode(otherlv_4, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_4());
                
            this_NUMBER_5=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8143); 
             
                newLeafNode(this_NUMBER_5, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_5()); 
                
            otherlv_6=(Token)match(input,83,FOLLOW_83_in_ruleDateLiteral8154); 

                	newLeafNode(otherlv_6, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_6());
                
            this_NUMBER_7=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8165); 
             
                newLeafNode(this_NUMBER_7, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_7()); 
                
            otherlv_8=(Token)match(input,83,FOLLOW_83_in_ruleDateLiteral8176); 

                	newLeafNode(otherlv_8, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_8());
                
            this_NUMBER_9=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8187); 
             
                newLeafNode(this_NUMBER_9, grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_9()); 
                
            otherlv_10=(Token)match(input,83,FOLLOW_83_in_ruleDateLiteral8198); 

                	newLeafNode(otherlv_10, grammarAccess.getDateLiteralAccess().getNumberSignKeyword_10());
                
            this_NUMBER_11=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleDateLiteral8209); 
             
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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4418:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4419:2: (iv_ruleLiteral= ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4420:2: iv_ruleLiteral= ruleLiteral EOF
            {
             newCompositeNode(grammarAccess.getLiteralRule()); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral8244);
            iv_ruleLiteral=ruleLiteral();

            state._fsp--;

             current =iv_ruleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral8254); 

            }

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
    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4427:1: ruleLiteral returns [EObject current=null] : ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) ) ;
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
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4430:28: ( ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) ) )
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4431:1: ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )
            {
            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4431:1: ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )
            int alt75=13;
            alt75 = dfa75.predict(input);
            switch (alt75) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4431:2: ( () this_NUMBER_1= RULE_NUMBER )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4431:2: ( () this_NUMBER_1= RULE_NUMBER )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4431:3: () this_NUMBER_1= RULE_NUMBER
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4431:3: ()
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4432:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getLiteralAccess().getExpressionAction_0_0(),
                                current);
                        

                    }

                    this_NUMBER_1=(Token)match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_ruleLiteral8300); 
                     
                        newLeafNode(this_NUMBER_1, grammarAccess.getLiteralAccess().getNUMBERTerminalRuleCall_0_1()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4442:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4442:6: ( (lv_valueString_2_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4443:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4443:1: (lv_valueString_2_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4444:3: lv_valueString_2_0= RULE_LITERALSTRING
                    {
                    lv_valueString_2_0=(Token)match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8323); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4461:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4461:6: ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4461:7: ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4461:7: ( (lv_operations_3_0= RULE_FORALL ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4462:1: (lv_operations_3_0= RULE_FORALL )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4462:1: (lv_operations_3_0= RULE_FORALL )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4463:3: lv_operations_3_0= RULE_FORALL
                    {
                    lv_operations_3_0=(Token)match(input,RULE_FORALL,FOLLOW_RULE_FORALL_in_ruleLiteral8352); 

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

                    otherlv_4=(Token)match(input,72,FOLLOW_72_in_ruleLiteral8369); 

                        	newLeafNode(otherlv_4, grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1());
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4483:1: ( (lv_valueString_5_0= RULE_LITERALSTRING ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4484:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4484:1: (lv_valueString_5_0= RULE_LITERALSTRING )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4485:3: lv_valueString_5_0= RULE_LITERALSTRING
                    {
                    lv_valueString_5_0=(Token)match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8386); 

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

                    otherlv_6=(Token)match(input,73,FOLLOW_73_in_ruleLiteral8403); 

                        	newLeafNode(otherlv_6, grammarAccess.getLiteralAccess().getCommaKeyword_2_3());
                        
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4505:1: ( (lv_parameters_7_0= ruleOrExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4506:1: (lv_parameters_7_0= ruleOrExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4506:1: (lv_parameters_7_0= ruleOrExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4507:3: lv_parameters_7_0= ruleOrExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0()); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8424);
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

                    otherlv_8=(Token)match(input,74,FOLLOW_74_in_ruleLiteral8436); 

                        	newLeafNode(otherlv_8, grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5());
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4528:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4528:6: ( (lv_parameters_9_0= ruleFunctionCall ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4529:1: (lv_parameters_9_0= ruleFunctionCall )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4529:1: (lv_parameters_9_0= ruleFunctionCall )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4530:3: lv_parameters_9_0= ruleFunctionCall
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersFunctionCallParserRuleCall_3_0()); 
                    	    
                    pushFollow(FOLLOW_ruleFunctionCall_in_ruleLiteral8464);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4547:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4547:6: ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4547:7: ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}'
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4547:7: ( (lv_expressionType_10_0= '{' ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4548:1: (lv_expressionType_10_0= '{' )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4548:1: (lv_expressionType_10_0= '{' )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4549:3: lv_expressionType_10_0= '{'
                    {
                    lv_expressionType_10_0=(Token)match(input,84,FOLLOW_84_in_ruleLiteral8489); 

                            newLeafNode(lv_expressionType_10_0, grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLiteralRule());
                    	        }
                           		setWithLastConsumed(current, "expressionType", lv_expressionType_10_0, "{");
                    	    

                    }


                    }

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4562:2: ( (lv_parameters_11_0= ruleOrExpression ) )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==RULE_ID||LA73_0==RULE_SQBRACKET_OPEN||(LA73_0>=RULE_TML_EXISTS && LA73_0<=RULE_DOLLAR)||(LA73_0>=RULE_NUMBER && LA73_0<=RULE_FALSE)||LA73_0==72||LA73_0==82||LA73_0==84) ) {
                        alt73=1;
                    }
                    switch (alt73) {
                        case 1 :
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4563:1: (lv_parameters_11_0= ruleOrExpression )
                            {
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4563:1: (lv_parameters_11_0= ruleOrExpression )
                            // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4564:3: lv_parameters_11_0= ruleOrExpression
                            {
                             
                            	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8523);
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

                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4580:3: (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )*
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==73) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4580:5: otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    {
                    	    otherlv_12=(Token)match(input,73,FOLLOW_73_in_ruleLiteral8537); 

                    	        	newLeafNode(otherlv_12, grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0());
                    	        
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4584:1: ( (lv_parameters_13_0= ruleOrExpression ) )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4585:1: (lv_parameters_13_0= ruleOrExpression )
                    	    {
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4585:1: (lv_parameters_13_0= ruleOrExpression )
                    	    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4586:3: lv_parameters_13_0= ruleOrExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOrExpression_in_ruleLiteral8558);
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
                    	    break loop74;
                        }
                    } while (true);

                    otherlv_14=(Token)match(input,85,FOLLOW_85_in_ruleLiteral8572); 

                        	newLeafNode(otherlv_14, grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3());
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4607:6: ( (lv_elements_15_0= RULE_NULL ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4607:6: ( (lv_elements_15_0= RULE_NULL ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4608:1: (lv_elements_15_0= RULE_NULL )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4608:1: (lv_elements_15_0= RULE_NULL )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4609:3: lv_elements_15_0= RULE_NULL
                    {
                    lv_elements_15_0=(Token)match(input,RULE_NULL,FOLLOW_RULE_NULL_in_ruleLiteral8596); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4626:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4626:6: ( (lv_elements_16_0= RULE_TODAY ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4627:1: (lv_elements_16_0= RULE_TODAY )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4627:1: (lv_elements_16_0= RULE_TODAY )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4628:3: lv_elements_16_0= RULE_TODAY
                    {
                    lv_elements_16_0=(Token)match(input,RULE_TODAY,FOLLOW_RULE_TODAY_in_ruleLiteral8624); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4645:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4645:6: ( (lv_elements_17_0= RULE_TRUE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4646:1: (lv_elements_17_0= RULE_TRUE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4646:1: (lv_elements_17_0= RULE_TRUE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4647:3: lv_elements_17_0= RULE_TRUE
                    {
                    lv_elements_17_0=(Token)match(input,RULE_TRUE,FOLLOW_RULE_TRUE_in_ruleLiteral8652); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4664:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4664:6: ( (lv_elements_18_0= RULE_FALSE ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4665:1: (lv_elements_18_0= RULE_FALSE )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4665:1: (lv_elements_18_0= RULE_FALSE )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4666:3: lv_elements_18_0= RULE_FALSE
                    {
                    lv_elements_18_0=(Token)match(input,RULE_FALSE,FOLLOW_RULE_FALSE_in_ruleLiteral8680); 

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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4683:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4683:6: ( (lv_parameters_19_0= ruleTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4684:1: (lv_parameters_19_0= ruleTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4684:1: (lv_parameters_19_0= ruleTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4685:3: lv_parameters_19_0= ruleTmlExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTmlExpression_in_ruleLiteral8712);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4702:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4702:6: ( (lv_parameters_20_0= ruleExistsTmlExpression ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4703:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4703:1: (lv_parameters_20_0= ruleExistsTmlExpression )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4704:3: lv_parameters_20_0= ruleExistsTmlExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0()); 
                    	    
                    pushFollow(FOLLOW_ruleExistsTmlExpression_in_ruleLiteral8739);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4721:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4721:6: ( (lv_parameters_21_0= ruleMapGetReference ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4722:1: (lv_parameters_21_0= ruleMapGetReference )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4722:1: (lv_parameters_21_0= ruleMapGetReference )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4723:3: lv_parameters_21_0= ruleMapGetReference
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMapGetReference_in_ruleLiteral8766);
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
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4740:6: ( (lv_parameters_22_0= ruleDateLiteral ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4740:6: ( (lv_parameters_22_0= ruleDateLiteral ) )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4741:1: (lv_parameters_22_0= ruleDateLiteral )
                    {
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4741:1: (lv_parameters_22_0= ruleDateLiteral )
                    // ../com.dexels.navajo.dsl.tsl/src-gen/com/dexels/navajo/dsl/tsl/parser/antlr/internal/InternalTsl.g:4742:3: lv_parameters_22_0= ruleDateLiteral
                    {
                     
                    	        newCompositeNode(grammarAccess.getLiteralAccess().getParametersDateLiteralParserRuleCall_12_0()); 
                    	    
                    pushFollow(FOLLOW_ruleDateLiteral_in_ruleLiteral8793);
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


    protected DFA75 dfa75 = new DFA75(this);
    static final String DFA75_eotS =
        "\17\uffff";
    static final String DFA75_eofS =
        "\1\uffff\1\15\15\uffff";
    static final String DFA75_minS =
        "\1\11\1\13\15\uffff";
    static final String DFA75_maxS =
        "\1\124\1\125\15\uffff";
    static final String DFA75_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\1\1"+
        "\15";
    static final String DFA75_specialS =
        "\17\uffff}>";
    static final String[] DFA75_transitionS = {
            "\1\4\50\uffff\1\12\3\uffff\1\13\1\14\4\uffff\1\1\1\2\1\3\1\6"+
            "\1\7\1\10\1\11\21\uffff\1\5",
            "\1\15\11\uffff\1\15\26\uffff\1\15\2\uffff\1\15\3\uffff\1\15"+
            "\4\uffff\4\15\15\uffff\11\15\1\uffff\1\16\1\uffff\1\15",
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

    static final short[] DFA75_eot = DFA.unpackEncodedString(DFA75_eotS);
    static final short[] DFA75_eof = DFA.unpackEncodedString(DFA75_eofS);
    static final char[] DFA75_min = DFA.unpackEncodedStringToUnsignedChars(DFA75_minS);
    static final char[] DFA75_max = DFA.unpackEncodedStringToUnsignedChars(DFA75_maxS);
    static final short[] DFA75_accept = DFA.unpackEncodedString(DFA75_acceptS);
    static final short[] DFA75_special = DFA.unpackEncodedString(DFA75_specialS);
    static final short[][] DFA75_transition;

    static {
        int numStates = DFA75_transitionS.length;
        DFA75_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA75_transition[i] = DFA.unpackEncodedString(DFA75_transitionS[i]);
        }
    }

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = DFA75_eot;
            this.eof = DFA75_eof;
            this.min = DFA75_min;
            this.max = DFA75_max;
            this.accept = DFA75_accept;
            this.special = DFA75_special;
            this.transition = DFA75_transition;
        }
        public String getDescription() {
            return "4431:1: ( ( () this_NUMBER_1= RULE_NUMBER ) | ( (lv_valueString_2_0= RULE_LITERALSTRING ) ) | ( ( (lv_operations_3_0= RULE_FORALL ) ) otherlv_4= '(' ( (lv_valueString_5_0= RULE_LITERALSTRING ) ) otherlv_6= ',' ( (lv_parameters_7_0= ruleOrExpression ) ) otherlv_8= ')' ) | ( (lv_parameters_9_0= ruleFunctionCall ) ) | ( ( (lv_expressionType_10_0= '{' ) ) ( (lv_parameters_11_0= ruleOrExpression ) )? (otherlv_12= ',' ( (lv_parameters_13_0= ruleOrExpression ) ) )* otherlv_14= '}' ) | ( (lv_elements_15_0= RULE_NULL ) ) | ( (lv_elements_16_0= RULE_TODAY ) ) | ( (lv_elements_17_0= RULE_TRUE ) ) | ( (lv_elements_18_0= RULE_FALSE ) ) | ( (lv_parameters_19_0= ruleTmlExpression ) ) | ( (lv_parameters_20_0= ruleExistsTmlExpression ) ) | ( (lv_parameters_21_0= ruleMapGetReference ) ) | ( (lv_parameters_22_0= ruleDateLiteral ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_ruleTml_in_entryRuleTml75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTml85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XMLHEAD_in_ruleTml131 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_START_in_ruleTml143 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleTml163 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleTml177 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleTml198 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleMap_in_ruleTml225 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleParam_in_ruleTml252 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleMethods_in_ruleTml279 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleTml306 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleInclude_in_ruleTml333 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleValidations_in_ruleTml360 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_ruleComment_in_ruleTml387 = new BitSet(new long[]{0x0000082054444080L});
    public static final BitSet FOLLOW_RULE_NAVASCRIPT_END_in_ruleTml400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleTml417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttributeName_in_entryRuleAttributeName454 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAttributeName465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAttributeName504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression548 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePossibleExpression558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePossibleExpression601 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_rulePossibleExpression618 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ruleAttributeName_in_rulePossibleExpression641 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_rulePossibleExpression653 = new BitSet(new long[]{0x0000000000003400L});
    public static final BitSet FOLLOW_RULE_QUOTEQ_in_rulePossibleExpression666 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleTopLevel_in_rulePossibleExpression686 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RULE_SEMICOLONQUOTE_in_rulePossibleExpression703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ATTRIBUTESTRING_in_rulePossibleExpression732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EMPTYSTRING_in_rulePossibleExpression760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethods_in_entryRuleMethods802 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethods812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_METHODS_START_TAG_in_ruleMethods848 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethods875 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_ruleMethod_in_ruleMethods901 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_RULE_METHODS_END_TAG_in_ruleMethods919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethods942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethod_in_entryRuleMethod978 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethod988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_METHOD_START_TAG_in_ruleMethod1024 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMethod1053 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMethod1073 = new BitSet(new long[]{0x0000000200020000L});
    public static final BitSet FOLLOW_ruleRequired_in_ruleMethod1099 = new BitSet(new long[]{0x0000000200020000L});
    public static final BitSet FOLLOW_RULE_METHOD_END_TAG_in_ruleMethod1117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMethod1140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValidations_in_entryRuleValidations1176 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValidations1186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALIDATIONS_START_TAG_in_ruleValidations1222 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleValidations1249 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_ruleCheck_in_ruleValidations1275 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_RULE_VALIDATIONS_END_TAG_in_ruleValidations1293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleValidations1316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCheck_in_entryRuleCheck1352 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCheck1362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_CHECK_START_TAG_in_ruleCheck1398 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleCheck1427 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleCheck1447 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleCheck1473 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RULE_CHECK_END_TAG_in_ruleCheck1490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleCheck1513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComment_in_entryRuleComment1549 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComment1559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_COMMENT_START_TAG_in_ruleComment1595 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleComment1624 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleComment1644 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RULE_COMMENT_END_TAG_in_ruleComment1666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleComment1689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBreak_in_entryRuleBreak1725 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBreak1735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_BREAK_START_TAG_in_ruleBreak1771 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleBreak1800 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleBreak1820 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_RULE_BREAK_END_TAG_in_ruleBreak1842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleBreak1865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInclude_in_entryRuleInclude1901 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInclude1911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INCLUDE_START_TAG_in_ruleInclude1947 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleInclude1976 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleInclude1996 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RULE_INCLUDE_END_TAG_in_ruleInclude2018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleInclude2041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage2077 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage2087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MESSAGE_START_TAG_in_ruleMessage2123 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMessage2152 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMessage2172 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMessage2199 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMessage2226 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMessage2253 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMessage2280 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMessage2307 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMessage2334 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMessage2361 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMessage2388 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMessage2415 = new BitSet(new long[]{0x00000AA875444000L});
    public static final BitSet FOLLOW_RULE_MESSAGE_END_TAG_in_ruleMessage2434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMessage2457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_entryRuleMap2493 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMap2503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MAPSTARTKEYWORD_in_ruleMap2539 = new BitSet(new long[]{0x0000000080000340L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMap2560 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMap2580 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap2601 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMap2630 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMap2644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap2667 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMap2694 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMap2721 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMap2748 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMap2775 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMap2802 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMap2829 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMap2856 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMap2883 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMap2910 = new BitSet(new long[]{0x00000AA955444000L});
    public static final BitSet FOLLOW_RULE_MAPENDKEYWORD_in_ruleMap2924 = new BitSet(new long[]{0x0000000080000040L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMap2935 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ruleMapId_in_ruleMap2955 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMap2974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapId_in_entryRuleMapId3019 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapId3030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapId3069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequired_in_entryRuleRequired3113 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequired3123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_REQUIRED_START_TAG_in_ruleRequired3159 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleRequired3188 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleRequired3201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleRequired3224 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_RULE_REQUIRED_END_TAG_in_ruleRequired3246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty3289 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty3299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PROPERTY_START_TAG_in_ruleProperty3335 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleProperty3364 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleProperty3377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleProperty3400 = new BitSet(new long[]{0x0000601040000000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleProperty3427 = new BitSet(new long[]{0x0000601040000000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleProperty3454 = new BitSet(new long[]{0x0000601040000000L});
    public static final BitSet FOLLOW_RULE_PROPERTY_END_TAG_in_ruleProperty3473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParam_in_entryRuleParam3516 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParam3526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARAM_START_TAG_in_ruleParam3562 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleParam3591 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleParam3604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleParam3627 = new BitSet(new long[]{0x0000604040000000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleParam3654 = new BitSet(new long[]{0x0000604040000000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleParam3681 = new BitSet(new long[]{0x0000604040000000L});
    public static final BitSet FOLLOW_RULE_PARAM_END_TAG_in_ruleParam3700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapMethod_in_entryRuleMapMethod3743 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapMethod3753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_MAP_METHOD_STARTTAG_START_in_ruleMapMethod3789 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod3805 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMapMethod3821 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ruleAttributeName_in_ruleMapMethod3841 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleMapMethod3862 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleMapMethod3875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod3898 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleMapMethod3925 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleMapMethod3952 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleMapMethod3979 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleMapMethod4006 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleMapMethod4033 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleMapMethod4060 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleMapMethod4087 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleField_in_ruleMapMethod4114 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleMapMethod4141 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleMapMethod4168 = new BitSet(new long[]{0x00006BA855444000L});
    public static final BitSet FOLLOW_RULE_MAP_METHOD_ENDTAG_START_in_ruleMapMethod4187 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod4209 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_RULE_DOT_in_ruleMapMethod4225 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapMethod4241 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleMapMethod4257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleField_in_entryRuleField4294 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleField4304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FIELD_START_TAG_in_ruleField4340 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleField4369 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleField4382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleField4406 = new BitSet(new long[]{0x00006EA855444000L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_ruleField4433 = new BitSet(new long[]{0x00006EA855444000L});
    public static final BitSet FOLLOW_ruleParam_in_ruleField4460 = new BitSet(new long[]{0x00006EA855444000L});
    public static final BitSet FOLLOW_ruleMap_in_ruleField4487 = new BitSet(new long[]{0x00006EA855444000L});
    public static final BitSet FOLLOW_ruleMapMethod_in_ruleField4514 = new BitSet(new long[]{0x00006EA855444000L});
    public static final BitSet FOLLOW_ruleDebugTag_in_ruleField4541 = new BitSet(new long[]{0x00006EA855444000L});
    public static final BitSet FOLLOW_ruleComment_in_ruleField4568 = new BitSet(new long[]{0x00006EA855444000L});
    public static final BitSet FOLLOW_ruleBreak_in_ruleField4595 = new BitSet(new long[]{0x00006EA855444000L});
    public static final BitSet FOLLOW_RULE_FIELD_END_TAG_in_ruleField4615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDebugTag_in_entryRuleDebugTag4658 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDebugTag4668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DEBUG_START_TAG_in_ruleDebugTag4704 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleDebugTag4733 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleDebugTag4746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleDebugTag4769 = new BitSet(new long[]{0xF0C4100000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleDebugTag4795 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_RULE_DEBUG_END_TAG_in_ruleDebugTag4813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionOrOption_in_entryRuleExpressionOrOption4856 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionOrOption4866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EXPRESSION_START_TAG_in_ruleExpressionOrOption4903 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_ruleExpressionOrOption4924 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_OPTION_START_TAG_in_ruleExpressionOrOption4942 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_ruleOption_in_ruleExpressionOrOption4963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag4999 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag5009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleExpressionTag5064 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleExpressionTag5077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleExpressionTag5100 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleTopLevel_in_ruleExpressionTag5126 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_RULE_EXPRESSION_END_TAG_in_ruleExpressionTag5143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOption_in_entryRuleOption5186 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOption5196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_ruleOption5251 = new BitSet(new long[]{0x0000000000000340L});
    public static final BitSet FOLLOW_RULE_XML_TAG_SINGLEEND_in_ruleOption5264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_TAG_END_in_ruleOption5287 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_RULE_OPTION_END_TAG_in_ruleOption5309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel5352 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel5362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleTopLevel5407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement5443 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement5454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePathElement5494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOT_in_rulePathElement5518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARENT_in_rulePathElement5539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression5584 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression5594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleTmlExpression5630 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5646 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleTmlExpression5669 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression5696 = new BitSet(new long[]{0x0028000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleTmlExpression5708 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleTmlExpression5728 = new BitSet(new long[]{0x0028000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleTmlExpression5741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression5776 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression5786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_EXISTS_in_ruleExistsTmlExpression5822 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_ruleExistsTmlExpression5832 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5848 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_RULE_AT_in_ruleExistsTmlExpression5871 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression5898 = new BitSet(new long[]{0x0028000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleExistsTmlExpression5910 = new BitSet(new long[]{0x001A000080000200L});
    public static final BitSet FOLLOW_rulePathElement_in_ruleExistsTmlExpression5930 = new BitSet(new long[]{0x0028000000000000L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_ruleExistsTmlExpression5943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams5978 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapReferenceParams5988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_ruleMapReferenceParams6025 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000100007L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams6046 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_73_in_ruleMapReferenceParams6059 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000100007L});
    public static final BitSet FOLLOW_ruleLiteral_in_ruleMapReferenceParams6080 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_74_in_ruleMapReferenceParams6094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference6130 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference6140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOLLAR_in_ruleMapGetReference6182 = new BitSet(new long[]{0x0002000000000200L});
    public static final BitSet FOLLOW_RULE_PARENT_in_ruleMapGetReference6205 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMapGetReference6221 = new BitSet(new long[]{0x0002000000000200L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMapGetReference6239 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_ruleMapGetReference6265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression6302 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression6312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression6358 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_ruleOrExpression6377 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression6411 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression6449 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression6459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression6505 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ruleAndExpression6524 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_ruleAndExpression6558 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression6596 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression6606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6652 = new BitSet(new long[]{0x0000000000000002L,0x0000000000006000L});
    public static final BitSet FOLLOW_77_in_ruleEqualityExpression6672 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_ruleEqualityExpression6732 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_ruleEqualityExpression6766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression6805 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRelationalExpression6815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6870 = new BitSet(new long[]{0x0F00000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LT_in_ruleRelationalExpression6889 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GT_in_ruleRelationalExpression6940 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression6966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LTEQ_in_ruleRelationalExpression6991 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression7017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GTEQ_in_ruleRelationalExpression7042 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_ruleRelationalExpression7068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression7107 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression7117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7163 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_79_in_ruleAdditiveExpression7177 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7198 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_80_in_ruleAdditiveExpression7218 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_ruleAdditiveExpression7239 = new BitSet(new long[]{0x0000000000000002L,0x0000000000018000L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression7278 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression7288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7334 = new BitSet(new long[]{0x0008000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_ruleMultiplicativeExpression7354 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7388 = new BitSet(new long[]{0x0008000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_ruleMultiplicativeExpression7413 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_ruleMultiplicativeExpression7439 = new BitSet(new long[]{0x0008000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression7478 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression7488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_ruleUnaryExpression7532 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_ruleUnaryExpression7595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression7630 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression7640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rulePrimaryExpression7686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_rulePrimaryExpression7705 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rulePrimaryExpression7726 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_rulePrimaryExpression7738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName7776 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName7787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName7826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall7870 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall7880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_ruleFunctionCall7926 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_ruleFunctionCall7938 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140707L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall7959 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_73_in_ruleFunctionCall7973 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFunctionCall7994 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_74_in_ruleFunctionCall8008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral8044 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDateLiteral8054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8099 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral8110 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8121 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral8132 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8143 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral8154 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral8176 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8187 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ruleDateLiteral8198 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleDateLiteral8209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral8244 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral8254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_ruleLiteral8300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FORALL_in_ruleLiteral8352 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_ruleLiteral8369 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_ruleLiteral8386 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_ruleLiteral8403 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8424 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_ruleLiteral8436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_ruleLiteral8464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_ruleLiteral8489 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000340307L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8523 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200200L});
    public static final BitSet FOLLOW_73_in_ruleLiteral8537 = new BitSet(new long[]{0xF0C4000000000200L,0x0000000000140107L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleLiteral8558 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200200L});
    public static final BitSet FOLLOW_85_in_ruleLiteral8572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NULL_in_ruleLiteral8596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TODAY_in_ruleLiteral8624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TRUE_in_ruleLiteral8652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FALSE_in_ruleLiteral8680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_ruleLiteral8712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_ruleLiteral8739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_ruleLiteral8766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_ruleLiteral8793 = new BitSet(new long[]{0x0000000000000002L});

}