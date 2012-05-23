package com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import com.dexels.navajo.dsl.expression.services.NavajoExpressionGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalNavajoExpressionParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_PARENT", "RULE_SQBRACKET_OPEN", "RULE_SQBRACKET_CLOSE", "RULE_TML_SEPARATOR", "RULE_TML_EXISTS", "RULE_NUMBER", "RULE_AT", "RULE_DOLLAR", "RULE_XML_LT", "RULE_XML_GT", "RULE_XML_LTEQ", "RULE_XML_GTEQ", "RULE_LITERALSTRING", "RULE_FORALL", "RULE_NULL", "RULE_TODAY", "RULE_TRUE", "RULE_FALSE", "RULE_WS", "'.'", "'('", "')'", "','", "'+'", "'-'", "'#'", "'}'", "'OR'", "'AND'", "'=='", "'!='", "'*'", "'!'", "'{'"
    };
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int RULE_PARENT=5;
    public static final int T__27=27;
    public static final int RULE_SQBRACKET_OPEN=6;
    public static final int T__26=26;
    public static final int RULE_XML_LT=13;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int RULE_LITERALSTRING=17;
    public static final int RULE_XML_GTEQ=16;
    public static final int RULE_TML_SEPARATOR=8;
    public static final int EOF=-1;
    public static final int RULE_NULL=19;
    public static final int RULE_TRUE=21;
    public static final int RULE_FORALL=18;
    public static final int RULE_DOLLAR=12;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int RULE_FALSE=22;
    public static final int RULE_TML_EXISTS=9;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_NUMBER=10;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int RULE_TODAY=20;
    public static final int RULE_SQBRACKET_CLOSE=7;
    public static final int RULE_XML_LTEQ=15;
    public static final int RULE_WS=23;
    public static final int RULE_XML_GT=14;
    public static final int RULE_AT=11;

    // delegates
    // delegators


        public InternalNavajoExpressionParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalNavajoExpressionParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalNavajoExpressionParser.tokenNames; }
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g"; }


     
     	private NavajoExpressionGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(NavajoExpressionGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start "entryRuleTopLevel"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:60:1: entryRuleTopLevel : ruleTopLevel EOF ;
    public final void entryRuleTopLevel() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:61:1: ( ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:62:1: ruleTopLevel EOF
            {
             before(grammarAccess.getTopLevelRule()); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel61);
            ruleTopLevel();

            state._fsp--;

             after(grammarAccess.getTopLevelRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel68); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTopLevel"


    // $ANTLR start "ruleTopLevel"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:69:1: ruleTopLevel : ( ( rule__TopLevel__ToplevelExpressionAssignment ) ) ;
    public final void ruleTopLevel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:73:2: ( ( ( rule__TopLevel__ToplevelExpressionAssignment ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:74:1: ( ( rule__TopLevel__ToplevelExpressionAssignment ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:74:1: ( ( rule__TopLevel__ToplevelExpressionAssignment ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:75:1: ( rule__TopLevel__ToplevelExpressionAssignment )
            {
             before(grammarAccess.getTopLevelAccess().getToplevelExpressionAssignment()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:76:1: ( rule__TopLevel__ToplevelExpressionAssignment )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:76:2: rule__TopLevel__ToplevelExpressionAssignment
            {
            pushFollow(FOLLOW_rule__TopLevel__ToplevelExpressionAssignment_in_ruleTopLevel94);
            rule__TopLevel__ToplevelExpressionAssignment();

            state._fsp--;


            }

             after(grammarAccess.getTopLevelAccess().getToplevelExpressionAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTopLevel"


    // $ANTLR start "entryRulePathElement"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:88:1: entryRulePathElement : rulePathElement EOF ;
    public final void entryRulePathElement() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:89:1: ( rulePathElement EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:90:1: rulePathElement EOF
            {
             before(grammarAccess.getPathElementRule()); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement121);
            rulePathElement();

            state._fsp--;

             after(grammarAccess.getPathElementRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement128); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePathElement"


    // $ANTLR start "rulePathElement"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:97:1: rulePathElement : ( ( rule__PathElement__Alternatives ) ) ;
    public final void rulePathElement() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:101:2: ( ( ( rule__PathElement__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:102:1: ( ( rule__PathElement__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:102:1: ( ( rule__PathElement__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:103:1: ( rule__PathElement__Alternatives )
            {
             before(grammarAccess.getPathElementAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:104:1: ( rule__PathElement__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:104:2: rule__PathElement__Alternatives
            {
            pushFollow(FOLLOW_rule__PathElement__Alternatives_in_rulePathElement154);
            rule__PathElement__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPathElementAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePathElement"


    // $ANTLR start "entryRuleTmlExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:116:1: entryRuleTmlExpression : ruleTmlExpression EOF ;
    public final void entryRuleTmlExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:117:1: ( ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:118:1: ruleTmlExpression EOF
            {
             before(grammarAccess.getTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression181);
            ruleTmlExpression();

            state._fsp--;

             after(grammarAccess.getTmlExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression188); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTmlExpression"


    // $ANTLR start "ruleTmlExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:125:1: ruleTmlExpression : ( ( rule__TmlExpression__Group__0 ) ) ;
    public final void ruleTmlExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:129:2: ( ( ( rule__TmlExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:130:1: ( ( rule__TmlExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:130:1: ( ( rule__TmlExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:131:1: ( rule__TmlExpression__Group__0 )
            {
             before(grammarAccess.getTmlExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:132:1: ( rule__TmlExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:132:2: rule__TmlExpression__Group__0
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__0_in_ruleTmlExpression214);
            rule__TmlExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTmlExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTmlExpression"


    // $ANTLR start "entryRuleExistsTmlExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:144:1: entryRuleExistsTmlExpression : ruleExistsTmlExpression EOF ;
    public final void entryRuleExistsTmlExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:145:1: ( ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:146:1: ruleExistsTmlExpression EOF
            {
             before(grammarAccess.getExistsTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression241);
            ruleExistsTmlExpression();

            state._fsp--;

             after(grammarAccess.getExistsTmlExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression248); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleExistsTmlExpression"


    // $ANTLR start "ruleExistsTmlExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:153:1: ruleExistsTmlExpression : ( ( rule__ExistsTmlExpression__Group__0 ) ) ;
    public final void ruleExistsTmlExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:157:2: ( ( ( rule__ExistsTmlExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:158:1: ( ( rule__ExistsTmlExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:158:1: ( ( rule__ExistsTmlExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:159:1: ( rule__ExistsTmlExpression__Group__0 )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:160:1: ( rule__ExistsTmlExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:160:2: rule__ExistsTmlExpression__Group__0
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__0_in_ruleExistsTmlExpression274);
            rule__ExistsTmlExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getExistsTmlExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleExistsTmlExpression"


    // $ANTLR start "entryRuleMapReferenceParams"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:172:1: entryRuleMapReferenceParams : ruleMapReferenceParams EOF ;
    public final void entryRuleMapReferenceParams() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:173:1: ( ruleMapReferenceParams EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:174:1: ruleMapReferenceParams EOF
            {
             before(grammarAccess.getMapReferenceParamsRule()); 
            pushFollow(FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams301);
            ruleMapReferenceParams();

            state._fsp--;

             after(grammarAccess.getMapReferenceParamsRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapReferenceParams308); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMapReferenceParams"


    // $ANTLR start "ruleMapReferenceParams"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:181:1: ruleMapReferenceParams : ( ( rule__MapReferenceParams__Group__0 ) ) ;
    public final void ruleMapReferenceParams() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:185:2: ( ( ( rule__MapReferenceParams__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:186:1: ( ( rule__MapReferenceParams__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:186:1: ( ( rule__MapReferenceParams__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:187:1: ( rule__MapReferenceParams__Group__0 )
            {
             before(grammarAccess.getMapReferenceParamsAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:188:1: ( rule__MapReferenceParams__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:188:2: rule__MapReferenceParams__Group__0
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__Group__0_in_ruleMapReferenceParams334);
            rule__MapReferenceParams__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMapReferenceParamsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMapReferenceParams"


    // $ANTLR start "entryRuleMapGetReference"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:200:1: entryRuleMapGetReference : ruleMapGetReference EOF ;
    public final void entryRuleMapGetReference() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:201:1: ( ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:202:1: ruleMapGetReference EOF
            {
             before(grammarAccess.getMapGetReferenceRule()); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference361);
            ruleMapGetReference();

            state._fsp--;

             after(grammarAccess.getMapGetReferenceRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference368); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMapGetReference"


    // $ANTLR start "ruleMapGetReference"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:209:1: ruleMapGetReference : ( ( rule__MapGetReference__Group__0 ) ) ;
    public final void ruleMapGetReference() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:213:2: ( ( ( rule__MapGetReference__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:214:1: ( ( rule__MapGetReference__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:214:1: ( ( rule__MapGetReference__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:215:1: ( rule__MapGetReference__Group__0 )
            {
             before(grammarAccess.getMapGetReferenceAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:216:1: ( rule__MapGetReference__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:216:2: rule__MapGetReference__Group__0
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__0_in_ruleMapGetReference394);
            rule__MapGetReference__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMapGetReferenceAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMapGetReference"


    // $ANTLR start "entryRuleOrExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:228:1: entryRuleOrExpression : ruleOrExpression EOF ;
    public final void entryRuleOrExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:229:1: ( ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:230:1: ruleOrExpression EOF
            {
             before(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression421);
            ruleOrExpression();

            state._fsp--;

             after(grammarAccess.getOrExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression428); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleOrExpression"


    // $ANTLR start "ruleOrExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:237:1: ruleOrExpression : ( ( rule__OrExpression__Group__0 ) ) ;
    public final void ruleOrExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:241:2: ( ( ( rule__OrExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:242:1: ( ( rule__OrExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:242:1: ( ( rule__OrExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:243:1: ( rule__OrExpression__Group__0 )
            {
             before(grammarAccess.getOrExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:244:1: ( rule__OrExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:244:2: rule__OrExpression__Group__0
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__0_in_ruleOrExpression454);
            rule__OrExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getOrExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOrExpression"


    // $ANTLR start "entryRuleAndExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:256:1: entryRuleAndExpression : ruleAndExpression EOF ;
    public final void entryRuleAndExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:257:1: ( ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:258:1: ruleAndExpression EOF
            {
             before(grammarAccess.getAndExpressionRule()); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression481);
            ruleAndExpression();

            state._fsp--;

             after(grammarAccess.getAndExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression488); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAndExpression"


    // $ANTLR start "ruleAndExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:265:1: ruleAndExpression : ( ( rule__AndExpression__Group__0 ) ) ;
    public final void ruleAndExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:269:2: ( ( ( rule__AndExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:270:1: ( ( rule__AndExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:270:1: ( ( rule__AndExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:271:1: ( rule__AndExpression__Group__0 )
            {
             before(grammarAccess.getAndExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:272:1: ( rule__AndExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:272:2: rule__AndExpression__Group__0
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__0_in_ruleAndExpression514);
            rule__AndExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAndExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAndExpression"


    // $ANTLR start "entryRuleEqualityExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:284:1: entryRuleEqualityExpression : ruleEqualityExpression EOF ;
    public final void entryRuleEqualityExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:285:1: ( ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:286:1: ruleEqualityExpression EOF
            {
             before(grammarAccess.getEqualityExpressionRule()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression541);
            ruleEqualityExpression();

            state._fsp--;

             after(grammarAccess.getEqualityExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression548); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEqualityExpression"


    // $ANTLR start "ruleEqualityExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:293:1: ruleEqualityExpression : ( ( rule__EqualityExpression__Group__0 ) ) ;
    public final void ruleEqualityExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:297:2: ( ( ( rule__EqualityExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:298:1: ( ( rule__EqualityExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:298:1: ( ( rule__EqualityExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:299:1: ( rule__EqualityExpression__Group__0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:300:1: ( rule__EqualityExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:300:2: rule__EqualityExpression__Group__0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__0_in_ruleEqualityExpression574);
            rule__EqualityExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEqualityExpression"


    // $ANTLR start "entryRuleRelationalExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:312:1: entryRuleRelationalExpression : ruleRelationalExpression EOF ;
    public final void entryRuleRelationalExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:313:1: ( ruleRelationalExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:314:1: ruleRelationalExpression EOF
            {
             before(grammarAccess.getRelationalExpressionRule()); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression601);
            ruleRelationalExpression();

            state._fsp--;

             after(grammarAccess.getRelationalExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRelationalExpression608); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRelationalExpression"


    // $ANTLR start "ruleRelationalExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:321:1: ruleRelationalExpression : ( ( rule__RelationalExpression__Group__0 ) ) ;
    public final void ruleRelationalExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:325:2: ( ( ( rule__RelationalExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:326:1: ( ( rule__RelationalExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:326:1: ( ( rule__RelationalExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:327:1: ( rule__RelationalExpression__Group__0 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:328:1: ( rule__RelationalExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:328:2: rule__RelationalExpression__Group__0
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group__0_in_ruleRelationalExpression634);
            rule__RelationalExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRelationalExpression"


    // $ANTLR start "entryRuleAdditiveExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:340:1: entryRuleAdditiveExpression : ruleAdditiveExpression EOF ;
    public final void entryRuleAdditiveExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:341:1: ( ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:342:1: ruleAdditiveExpression EOF
            {
             before(grammarAccess.getAdditiveExpressionRule()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression661);
            ruleAdditiveExpression();

            state._fsp--;

             after(grammarAccess.getAdditiveExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression668); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAdditiveExpression"


    // $ANTLR start "ruleAdditiveExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:349:1: ruleAdditiveExpression : ( ( rule__AdditiveExpression__Group__0 ) ) ;
    public final void ruleAdditiveExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:353:2: ( ( ( rule__AdditiveExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:354:1: ( ( rule__AdditiveExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:354:1: ( ( rule__AdditiveExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:355:1: ( rule__AdditiveExpression__Group__0 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:356:1: ( rule__AdditiveExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:356:2: rule__AdditiveExpression__Group__0
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__0_in_ruleAdditiveExpression694);
            rule__AdditiveExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAdditiveExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAdditiveExpression"


    // $ANTLR start "entryRuleMultiplicativeExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:368:1: entryRuleMultiplicativeExpression : ruleMultiplicativeExpression EOF ;
    public final void entryRuleMultiplicativeExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:369:1: ( ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:370:1: ruleMultiplicativeExpression EOF
            {
             before(grammarAccess.getMultiplicativeExpressionRule()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression721);
            ruleMultiplicativeExpression();

            state._fsp--;

             after(grammarAccess.getMultiplicativeExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression728); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMultiplicativeExpression"


    // $ANTLR start "ruleMultiplicativeExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:377:1: ruleMultiplicativeExpression : ( ( rule__MultiplicativeExpression__Group__0 ) ) ;
    public final void ruleMultiplicativeExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:381:2: ( ( ( rule__MultiplicativeExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:382:1: ( ( rule__MultiplicativeExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:382:1: ( ( rule__MultiplicativeExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:383:1: ( rule__MultiplicativeExpression__Group__0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:384:1: ( rule__MultiplicativeExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:384:2: rule__MultiplicativeExpression__Group__0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__0_in_ruleMultiplicativeExpression754);
            rule__MultiplicativeExpression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMultiplicativeExpression"


    // $ANTLR start "entryRuleUnaryExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:396:1: entryRuleUnaryExpression : ruleUnaryExpression EOF ;
    public final void entryRuleUnaryExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:397:1: ( ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:398:1: ruleUnaryExpression EOF
            {
             before(grammarAccess.getUnaryExpressionRule()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression781);
            ruleUnaryExpression();

            state._fsp--;

             after(grammarAccess.getUnaryExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression788); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleUnaryExpression"


    // $ANTLR start "ruleUnaryExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:405:1: ruleUnaryExpression : ( ( rule__UnaryExpression__Alternatives ) ) ;
    public final void ruleUnaryExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:409:2: ( ( ( rule__UnaryExpression__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:410:1: ( ( rule__UnaryExpression__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:410:1: ( ( rule__UnaryExpression__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:411:1: ( rule__UnaryExpression__Alternatives )
            {
             before(grammarAccess.getUnaryExpressionAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:412:1: ( rule__UnaryExpression__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:412:2: rule__UnaryExpression__Alternatives
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Alternatives_in_ruleUnaryExpression814);
            rule__UnaryExpression__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getUnaryExpressionAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleUnaryExpression"


    // $ANTLR start "entryRulePrimaryExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:424:1: entryRulePrimaryExpression : rulePrimaryExpression EOF ;
    public final void entryRulePrimaryExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:425:1: ( rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:426:1: rulePrimaryExpression EOF
            {
             before(grammarAccess.getPrimaryExpressionRule()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression841);
            rulePrimaryExpression();

            state._fsp--;

             after(grammarAccess.getPrimaryExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression848); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePrimaryExpression"


    // $ANTLR start "rulePrimaryExpression"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:433:1: rulePrimaryExpression : ( ( rule__PrimaryExpression__Alternatives ) ) ;
    public final void rulePrimaryExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:437:2: ( ( ( rule__PrimaryExpression__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:438:1: ( ( rule__PrimaryExpression__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:438:1: ( ( rule__PrimaryExpression__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:439:1: ( rule__PrimaryExpression__Alternatives )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:440:1: ( rule__PrimaryExpression__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:440:2: rule__PrimaryExpression__Alternatives
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Alternatives_in_rulePrimaryExpression874);
            rule__PrimaryExpression__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPrimaryExpressionAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePrimaryExpression"


    // $ANTLR start "entryRuleFunctionName"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:452:1: entryRuleFunctionName : ruleFunctionName EOF ;
    public final void entryRuleFunctionName() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:453:1: ( ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:454:1: ruleFunctionName EOF
            {
             before(grammarAccess.getFunctionNameRule()); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName901);
            ruleFunctionName();

            state._fsp--;

             after(grammarAccess.getFunctionNameRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName908); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFunctionName"


    // $ANTLR start "ruleFunctionName"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:461:1: ruleFunctionName : ( RULE_ID ) ;
    public final void ruleFunctionName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:465:2: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:466:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:466:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:467:1: RULE_ID
            {
             before(grammarAccess.getFunctionNameAccess().getIDTerminalRuleCall()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName934); 
             after(grammarAccess.getFunctionNameAccess().getIDTerminalRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFunctionName"


    // $ANTLR start "entryRuleFunctionCall"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:480:1: entryRuleFunctionCall : ruleFunctionCall EOF ;
    public final void entryRuleFunctionCall() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:481:1: ( ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:482:1: ruleFunctionCall EOF
            {
             before(grammarAccess.getFunctionCallRule()); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall960);
            ruleFunctionCall();

            state._fsp--;

             after(grammarAccess.getFunctionCallRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall967); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFunctionCall"


    // $ANTLR start "ruleFunctionCall"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:489:1: ruleFunctionCall : ( ( rule__FunctionCall__Group__0 ) ) ;
    public final void ruleFunctionCall() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:493:2: ( ( ( rule__FunctionCall__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:494:1: ( ( rule__FunctionCall__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:494:1: ( ( rule__FunctionCall__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:495:1: ( rule__FunctionCall__Group__0 )
            {
             before(grammarAccess.getFunctionCallAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:496:1: ( rule__FunctionCall__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:496:2: rule__FunctionCall__Group__0
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__0_in_ruleFunctionCall993);
            rule__FunctionCall__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getFunctionCallAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFunctionCall"


    // $ANTLR start "entryRuleDateLiteral"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:508:1: entryRuleDateLiteral : ruleDateLiteral EOF ;
    public final void entryRuleDateLiteral() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:509:1: ( ruleDateLiteral EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:510:1: ruleDateLiteral EOF
            {
             before(grammarAccess.getDateLiteralRule()); 
            pushFollow(FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral1020);
            ruleDateLiteral();

            state._fsp--;

             after(grammarAccess.getDateLiteralRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDateLiteral1027); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDateLiteral"


    // $ANTLR start "ruleDateLiteral"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:517:1: ruleDateLiteral : ( ( rule__DateLiteral__Group__0 ) ) ;
    public final void ruleDateLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:521:2: ( ( ( rule__DateLiteral__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:522:1: ( ( rule__DateLiteral__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:522:1: ( ( rule__DateLiteral__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:523:1: ( rule__DateLiteral__Group__0 )
            {
             before(grammarAccess.getDateLiteralAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:524:1: ( rule__DateLiteral__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:524:2: rule__DateLiteral__Group__0
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__0_in_ruleDateLiteral1053);
            rule__DateLiteral__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDateLiteralAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDateLiteral"


    // $ANTLR start "entryRuleLiteral"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:536:1: entryRuleLiteral : ruleLiteral EOF ;
    public final void entryRuleLiteral() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:537:1: ( ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:538:1: ruleLiteral EOF
            {
             before(grammarAccess.getLiteralRule()); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral1080);
            ruleLiteral();

            state._fsp--;

             after(grammarAccess.getLiteralRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral1087); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLiteral"


    // $ANTLR start "ruleLiteral"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:545:1: ruleLiteral : ( ( rule__Literal__Alternatives ) ) ;
    public final void ruleLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:549:2: ( ( ( rule__Literal__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:550:1: ( ( rule__Literal__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:550:1: ( ( rule__Literal__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:551:1: ( rule__Literal__Alternatives )
            {
             before(grammarAccess.getLiteralAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:552:1: ( rule__Literal__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:552:2: rule__Literal__Alternatives
            {
            pushFollow(FOLLOW_rule__Literal__Alternatives_in_ruleLiteral1113);
            rule__Literal__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getLiteralAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLiteral"


    // $ANTLR start "rule__PathElement__Alternatives"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:564:1: rule__PathElement__Alternatives : ( ( RULE_ID ) | ( '.' ) | ( RULE_PARENT ) );
    public final void rule__PathElement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:568:1: ( ( RULE_ID ) | ( '.' ) | ( RULE_PARENT ) )
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
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:569:1: ( RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:569:1: ( RULE_ID )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:570:1: RULE_ID
                    {
                     before(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PathElement__Alternatives1149); 
                     after(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:575:6: ( '.' )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:575:6: ( '.' )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:576:1: '.'
                    {
                     before(grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 
                    match(input,24,FOLLOW_24_in_rule__PathElement__Alternatives1167); 
                     after(grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:583:6: ( RULE_PARENT )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:583:6: ( RULE_PARENT )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:584:1: RULE_PARENT
                    {
                     before(grammarAccess.getPathElementAccess().getPARENTTerminalRuleCall_2()); 
                    match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_rule__PathElement__Alternatives1186); 
                     after(grammarAccess.getPathElementAccess().getPARENTTerminalRuleCall_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PathElement__Alternatives"


    // $ANTLR start "rule__EqualityExpression__Alternatives_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:594:1: rule__EqualityExpression__Alternatives_1 : ( ( ( rule__EqualityExpression__Group_1_0__0 ) ) | ( ( rule__EqualityExpression__Group_1_1__0 ) ) );
    public final void rule__EqualityExpression__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:598:1: ( ( ( rule__EqualityExpression__Group_1_0__0 ) ) | ( ( rule__EqualityExpression__Group_1_1__0 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==34) ) {
                alt2=1;
            }
            else if ( (LA2_0==35) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:599:1: ( ( rule__EqualityExpression__Group_1_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:599:1: ( ( rule__EqualityExpression__Group_1_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:600:1: ( rule__EqualityExpression__Group_1_0__0 )
                    {
                     before(grammarAccess.getEqualityExpressionAccess().getGroup_1_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:601:1: ( rule__EqualityExpression__Group_1_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:601:2: rule__EqualityExpression__Group_1_0__0
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Group_1_0__0_in_rule__EqualityExpression__Alternatives_11218);
                    rule__EqualityExpression__Group_1_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getEqualityExpressionAccess().getGroup_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:605:6: ( ( rule__EqualityExpression__Group_1_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:605:6: ( ( rule__EqualityExpression__Group_1_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:606:1: ( rule__EqualityExpression__Group_1_1__0 )
                    {
                     before(grammarAccess.getEqualityExpressionAccess().getGroup_1_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:607:1: ( rule__EqualityExpression__Group_1_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:607:2: rule__EqualityExpression__Group_1_1__0
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Group_1_1__0_in_rule__EqualityExpression__Alternatives_11236);
                    rule__EqualityExpression__Group_1_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getEqualityExpressionAccess().getGroup_1_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Alternatives_1"


    // $ANTLR start "rule__RelationalExpression__Alternatives_2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:616:1: rule__RelationalExpression__Alternatives_2 : ( ( ( rule__RelationalExpression__Group_2_0__0 ) ) | ( ( rule__RelationalExpression__Group_2_1__0 ) ) | ( ( rule__RelationalExpression__Group_2_2__0 ) ) | ( ( rule__RelationalExpression__Group_2_3__0 ) ) );
    public final void rule__RelationalExpression__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:620:1: ( ( ( rule__RelationalExpression__Group_2_0__0 ) ) | ( ( rule__RelationalExpression__Group_2_1__0 ) ) | ( ( rule__RelationalExpression__Group_2_2__0 ) ) | ( ( rule__RelationalExpression__Group_2_3__0 ) ) )
            int alt3=4;
            switch ( input.LA(1) ) {
            case RULE_XML_LT:
                {
                alt3=1;
                }
                break;
            case RULE_XML_GT:
                {
                alt3=2;
                }
                break;
            case RULE_XML_LTEQ:
                {
                alt3=3;
                }
                break;
            case RULE_XML_GTEQ:
                {
                alt3=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:621:1: ( ( rule__RelationalExpression__Group_2_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:621:1: ( ( rule__RelationalExpression__Group_2_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:622:1: ( rule__RelationalExpression__Group_2_0__0 )
                    {
                     before(grammarAccess.getRelationalExpressionAccess().getGroup_2_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:623:1: ( rule__RelationalExpression__Group_2_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:623:2: rule__RelationalExpression__Group_2_0__0
                    {
                    pushFollow(FOLLOW_rule__RelationalExpression__Group_2_0__0_in_rule__RelationalExpression__Alternatives_21269);
                    rule__RelationalExpression__Group_2_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getRelationalExpressionAccess().getGroup_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:627:6: ( ( rule__RelationalExpression__Group_2_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:627:6: ( ( rule__RelationalExpression__Group_2_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:628:1: ( rule__RelationalExpression__Group_2_1__0 )
                    {
                     before(grammarAccess.getRelationalExpressionAccess().getGroup_2_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:629:1: ( rule__RelationalExpression__Group_2_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:629:2: rule__RelationalExpression__Group_2_1__0
                    {
                    pushFollow(FOLLOW_rule__RelationalExpression__Group_2_1__0_in_rule__RelationalExpression__Alternatives_21287);
                    rule__RelationalExpression__Group_2_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getRelationalExpressionAccess().getGroup_2_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:633:6: ( ( rule__RelationalExpression__Group_2_2__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:633:6: ( ( rule__RelationalExpression__Group_2_2__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:634:1: ( rule__RelationalExpression__Group_2_2__0 )
                    {
                     before(grammarAccess.getRelationalExpressionAccess().getGroup_2_2()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:635:1: ( rule__RelationalExpression__Group_2_2__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:635:2: rule__RelationalExpression__Group_2_2__0
                    {
                    pushFollow(FOLLOW_rule__RelationalExpression__Group_2_2__0_in_rule__RelationalExpression__Alternatives_21305);
                    rule__RelationalExpression__Group_2_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getRelationalExpressionAccess().getGroup_2_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:639:6: ( ( rule__RelationalExpression__Group_2_3__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:639:6: ( ( rule__RelationalExpression__Group_2_3__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:640:1: ( rule__RelationalExpression__Group_2_3__0 )
                    {
                     before(grammarAccess.getRelationalExpressionAccess().getGroup_2_3()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:641:1: ( rule__RelationalExpression__Group_2_3__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:641:2: rule__RelationalExpression__Group_2_3__0
                    {
                    pushFollow(FOLLOW_rule__RelationalExpression__Group_2_3__0_in_rule__RelationalExpression__Alternatives_21323);
                    rule__RelationalExpression__Group_2_3__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getRelationalExpressionAccess().getGroup_2_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Alternatives_2"


    // $ANTLR start "rule__AdditiveExpression__Alternatives_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:650:1: rule__AdditiveExpression__Alternatives_1 : ( ( ( rule__AdditiveExpression__Group_1_0__0 ) ) | ( ( rule__AdditiveExpression__Group_1_1__0 ) ) );
    public final void rule__AdditiveExpression__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:654:1: ( ( ( rule__AdditiveExpression__Group_1_0__0 ) ) | ( ( rule__AdditiveExpression__Group_1_1__0 ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==28) ) {
                alt4=1;
            }
            else if ( (LA4_0==29) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:655:1: ( ( rule__AdditiveExpression__Group_1_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:655:1: ( ( rule__AdditiveExpression__Group_1_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:656:1: ( rule__AdditiveExpression__Group_1_0__0 )
                    {
                     before(grammarAccess.getAdditiveExpressionAccess().getGroup_1_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:657:1: ( rule__AdditiveExpression__Group_1_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:657:2: rule__AdditiveExpression__Group_1_0__0
                    {
                    pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_0__0_in_rule__AdditiveExpression__Alternatives_11356);
                    rule__AdditiveExpression__Group_1_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getAdditiveExpressionAccess().getGroup_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:661:6: ( ( rule__AdditiveExpression__Group_1_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:661:6: ( ( rule__AdditiveExpression__Group_1_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:662:1: ( rule__AdditiveExpression__Group_1_1__0 )
                    {
                     before(grammarAccess.getAdditiveExpressionAccess().getGroup_1_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:663:1: ( rule__AdditiveExpression__Group_1_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:663:2: rule__AdditiveExpression__Group_1_1__0
                    {
                    pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_1__0_in_rule__AdditiveExpression__Alternatives_11374);
                    rule__AdditiveExpression__Group_1_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getAdditiveExpressionAccess().getGroup_1_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Alternatives_1"


    // $ANTLR start "rule__MultiplicativeExpression__Alternatives_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:672:1: rule__MultiplicativeExpression__Alternatives_1 : ( ( ( rule__MultiplicativeExpression__Group_1_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_1_1__0 ) ) );
    public final void rule__MultiplicativeExpression__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:676:1: ( ( ( rule__MultiplicativeExpression__Group_1_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_1_1__0 ) ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==36) ) {
                alt5=1;
            }
            else if ( (LA5_0==RULE_TML_SEPARATOR) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:677:1: ( ( rule__MultiplicativeExpression__Group_1_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:677:1: ( ( rule__MultiplicativeExpression__Group_1_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:678:1: ( rule__MultiplicativeExpression__Group_1_0__0 )
                    {
                     before(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:679:1: ( rule__MultiplicativeExpression__Group_1_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:679:2: rule__MultiplicativeExpression__Group_1_0__0
                    {
                    pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_0__0_in_rule__MultiplicativeExpression__Alternatives_11407);
                    rule__MultiplicativeExpression__Group_1_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:683:6: ( ( rule__MultiplicativeExpression__Group_1_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:683:6: ( ( rule__MultiplicativeExpression__Group_1_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:684:1: ( rule__MultiplicativeExpression__Group_1_1__0 )
                    {
                     before(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:685:1: ( rule__MultiplicativeExpression__Group_1_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:685:2: rule__MultiplicativeExpression__Group_1_1__0
                    {
                    pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_1__0_in_rule__MultiplicativeExpression__Alternatives_11425);
                    rule__MultiplicativeExpression__Group_1_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Alternatives_1"


    // $ANTLR start "rule__UnaryExpression__Alternatives"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:694:1: rule__UnaryExpression__Alternatives : ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) );
    public final void rule__UnaryExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:698:1: ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==37) ) {
                alt6=1;
            }
            else if ( (LA6_0==RULE_ID||LA6_0==RULE_SQBRACKET_OPEN||(LA6_0>=RULE_TML_EXISTS && LA6_0<=RULE_NUMBER)||LA6_0==RULE_DOLLAR||(LA6_0>=RULE_LITERALSTRING && LA6_0<=RULE_FALSE)||LA6_0==25||LA6_0==38) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:699:1: ( ( rule__UnaryExpression__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:699:1: ( ( rule__UnaryExpression__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:700:1: ( rule__UnaryExpression__Group_0__0 )
                    {
                     before(grammarAccess.getUnaryExpressionAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:701:1: ( rule__UnaryExpression__Group_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:701:2: rule__UnaryExpression__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__UnaryExpression__Group_0__0_in_rule__UnaryExpression__Alternatives1458);
                    rule__UnaryExpression__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getUnaryExpressionAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:705:6: ( rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:705:6: ( rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:706:1: rulePrimaryExpression
                    {
                     before(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1()); 
                    pushFollow(FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__Alternatives1476);
                    rulePrimaryExpression();

                    state._fsp--;

                     after(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryExpression__Alternatives"


    // $ANTLR start "rule__PrimaryExpression__Alternatives"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:716:1: rule__PrimaryExpression__Alternatives : ( ( ( rule__PrimaryExpression__ParametersAssignment_0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) );
    public final void rule__PrimaryExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:720:1: ( ( ( rule__PrimaryExpression__ParametersAssignment_0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_ID||LA7_0==RULE_SQBRACKET_OPEN||(LA7_0>=RULE_TML_EXISTS && LA7_0<=RULE_NUMBER)||LA7_0==RULE_DOLLAR||(LA7_0>=RULE_LITERALSTRING && LA7_0<=RULE_FALSE)||LA7_0==38) ) {
                alt7=1;
            }
            else if ( (LA7_0==25) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:721:1: ( ( rule__PrimaryExpression__ParametersAssignment_0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:721:1: ( ( rule__PrimaryExpression__ParametersAssignment_0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:722:1: ( rule__PrimaryExpression__ParametersAssignment_0 )
                    {
                     before(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:723:1: ( rule__PrimaryExpression__ParametersAssignment_0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:723:2: rule__PrimaryExpression__ParametersAssignment_0
                    {
                    pushFollow(FOLLOW_rule__PrimaryExpression__ParametersAssignment_0_in_rule__PrimaryExpression__Alternatives1508);
                    rule__PrimaryExpression__ParametersAssignment_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:727:6: ( ( rule__PrimaryExpression__Group_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:727:6: ( ( rule__PrimaryExpression__Group_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:728:1: ( rule__PrimaryExpression__Group_1__0 )
                    {
                     before(grammarAccess.getPrimaryExpressionAccess().getGroup_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:729:1: ( rule__PrimaryExpression__Group_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:729:2: rule__PrimaryExpression__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__0_in_rule__PrimaryExpression__Alternatives1526);
                    rule__PrimaryExpression__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getPrimaryExpressionAccess().getGroup_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__Alternatives"


    // $ANTLR start "rule__Literal__Alternatives"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:738:1: rule__Literal__Alternatives : ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__ValueStringAssignment_1 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ( rule__Literal__ParametersAssignment_3 ) ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__ElementsAssignment_5 ) ) | ( ( rule__Literal__ElementsAssignment_6 ) ) | ( ( rule__Literal__ElementsAssignment_7 ) ) | ( ( rule__Literal__ElementsAssignment_8 ) ) | ( ( rule__Literal__ParametersAssignment_9 ) ) | ( ( rule__Literal__ParametersAssignment_10 ) ) | ( ( rule__Literal__ParametersAssignment_11 ) ) | ( ( rule__Literal__ParametersAssignment_12 ) ) );
    public final void rule__Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:742:1: ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__ValueStringAssignment_1 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ( rule__Literal__ParametersAssignment_3 ) ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__ElementsAssignment_5 ) ) | ( ( rule__Literal__ElementsAssignment_6 ) ) | ( ( rule__Literal__ElementsAssignment_7 ) ) | ( ( rule__Literal__ElementsAssignment_8 ) ) | ( ( rule__Literal__ParametersAssignment_9 ) ) | ( ( rule__Literal__ParametersAssignment_10 ) ) | ( ( rule__Literal__ParametersAssignment_11 ) ) | ( ( rule__Literal__ParametersAssignment_12 ) ) )
            int alt8=13;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:743:1: ( ( rule__Literal__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:743:1: ( ( rule__Literal__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:744:1: ( rule__Literal__Group_0__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:745:1: ( rule__Literal__Group_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:745:2: rule__Literal__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_0__0_in_rule__Literal__Alternatives1559);
                    rule__Literal__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:749:6: ( ( rule__Literal__ValueStringAssignment_1 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:749:6: ( ( rule__Literal__ValueStringAssignment_1 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:750:1: ( rule__Literal__ValueStringAssignment_1 )
                    {
                     before(grammarAccess.getLiteralAccess().getValueStringAssignment_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:751:1: ( rule__Literal__ValueStringAssignment_1 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:751:2: rule__Literal__ValueStringAssignment_1
                    {
                    pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_1_in_rule__Literal__Alternatives1577);
                    rule__Literal__ValueStringAssignment_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getValueStringAssignment_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:755:6: ( ( rule__Literal__Group_2__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:755:6: ( ( rule__Literal__Group_2__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:756:1: ( rule__Literal__Group_2__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_2()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:757:1: ( rule__Literal__Group_2__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:757:2: rule__Literal__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_2__0_in_rule__Literal__Alternatives1595);
                    rule__Literal__Group_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:761:6: ( ( rule__Literal__ParametersAssignment_3 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:761:6: ( ( rule__Literal__ParametersAssignment_3 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:762:1: ( rule__Literal__ParametersAssignment_3 )
                    {
                     before(grammarAccess.getLiteralAccess().getParametersAssignment_3()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:763:1: ( rule__Literal__ParametersAssignment_3 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:763:2: rule__Literal__ParametersAssignment_3
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_3_in_rule__Literal__Alternatives1613);
                    rule__Literal__ParametersAssignment_3();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getParametersAssignment_3()); 

                    }


                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:767:6: ( ( rule__Literal__Group_4__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:767:6: ( ( rule__Literal__Group_4__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:768:1: ( rule__Literal__Group_4__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_4()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:769:1: ( rule__Literal__Group_4__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:769:2: rule__Literal__Group_4__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_4__0_in_rule__Literal__Alternatives1631);
                    rule__Literal__Group_4__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_4()); 

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:773:6: ( ( rule__Literal__ElementsAssignment_5 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:773:6: ( ( rule__Literal__ElementsAssignment_5 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:774:1: ( rule__Literal__ElementsAssignment_5 )
                    {
                     before(grammarAccess.getLiteralAccess().getElementsAssignment_5()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:775:1: ( rule__Literal__ElementsAssignment_5 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:775:2: rule__Literal__ElementsAssignment_5
                    {
                    pushFollow(FOLLOW_rule__Literal__ElementsAssignment_5_in_rule__Literal__Alternatives1649);
                    rule__Literal__ElementsAssignment_5();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getElementsAssignment_5()); 

                    }


                    }
                    break;
                case 7 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:779:6: ( ( rule__Literal__ElementsAssignment_6 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:779:6: ( ( rule__Literal__ElementsAssignment_6 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:780:1: ( rule__Literal__ElementsAssignment_6 )
                    {
                     before(grammarAccess.getLiteralAccess().getElementsAssignment_6()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:781:1: ( rule__Literal__ElementsAssignment_6 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:781:2: rule__Literal__ElementsAssignment_6
                    {
                    pushFollow(FOLLOW_rule__Literal__ElementsAssignment_6_in_rule__Literal__Alternatives1667);
                    rule__Literal__ElementsAssignment_6();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getElementsAssignment_6()); 

                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:785:6: ( ( rule__Literal__ElementsAssignment_7 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:785:6: ( ( rule__Literal__ElementsAssignment_7 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:786:1: ( rule__Literal__ElementsAssignment_7 )
                    {
                     before(grammarAccess.getLiteralAccess().getElementsAssignment_7()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:787:1: ( rule__Literal__ElementsAssignment_7 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:787:2: rule__Literal__ElementsAssignment_7
                    {
                    pushFollow(FOLLOW_rule__Literal__ElementsAssignment_7_in_rule__Literal__Alternatives1685);
                    rule__Literal__ElementsAssignment_7();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getElementsAssignment_7()); 

                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:791:6: ( ( rule__Literal__ElementsAssignment_8 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:791:6: ( ( rule__Literal__ElementsAssignment_8 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:792:1: ( rule__Literal__ElementsAssignment_8 )
                    {
                     before(grammarAccess.getLiteralAccess().getElementsAssignment_8()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:793:1: ( rule__Literal__ElementsAssignment_8 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:793:2: rule__Literal__ElementsAssignment_8
                    {
                    pushFollow(FOLLOW_rule__Literal__ElementsAssignment_8_in_rule__Literal__Alternatives1703);
                    rule__Literal__ElementsAssignment_8();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getElementsAssignment_8()); 

                    }


                    }
                    break;
                case 10 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:797:6: ( ( rule__Literal__ParametersAssignment_9 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:797:6: ( ( rule__Literal__ParametersAssignment_9 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:798:1: ( rule__Literal__ParametersAssignment_9 )
                    {
                     before(grammarAccess.getLiteralAccess().getParametersAssignment_9()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:799:1: ( rule__Literal__ParametersAssignment_9 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:799:2: rule__Literal__ParametersAssignment_9
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_9_in_rule__Literal__Alternatives1721);
                    rule__Literal__ParametersAssignment_9();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getParametersAssignment_9()); 

                    }


                    }
                    break;
                case 11 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:803:6: ( ( rule__Literal__ParametersAssignment_10 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:803:6: ( ( rule__Literal__ParametersAssignment_10 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:804:1: ( rule__Literal__ParametersAssignment_10 )
                    {
                     before(grammarAccess.getLiteralAccess().getParametersAssignment_10()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:805:1: ( rule__Literal__ParametersAssignment_10 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:805:2: rule__Literal__ParametersAssignment_10
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_10_in_rule__Literal__Alternatives1739);
                    rule__Literal__ParametersAssignment_10();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getParametersAssignment_10()); 

                    }


                    }
                    break;
                case 12 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:809:6: ( ( rule__Literal__ParametersAssignment_11 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:809:6: ( ( rule__Literal__ParametersAssignment_11 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:810:1: ( rule__Literal__ParametersAssignment_11 )
                    {
                     before(grammarAccess.getLiteralAccess().getParametersAssignment_11()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:811:1: ( rule__Literal__ParametersAssignment_11 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:811:2: rule__Literal__ParametersAssignment_11
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_11_in_rule__Literal__Alternatives1757);
                    rule__Literal__ParametersAssignment_11();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getParametersAssignment_11()); 

                    }


                    }
                    break;
                case 13 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:815:6: ( ( rule__Literal__ParametersAssignment_12 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:815:6: ( ( rule__Literal__ParametersAssignment_12 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:816:1: ( rule__Literal__ParametersAssignment_12 )
                    {
                     before(grammarAccess.getLiteralAccess().getParametersAssignment_12()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:817:1: ( rule__Literal__ParametersAssignment_12 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:817:2: rule__Literal__ParametersAssignment_12
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_12_in_rule__Literal__Alternatives1775);
                    rule__Literal__ParametersAssignment_12();

                    state._fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getParametersAssignment_12()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Alternatives"


    // $ANTLR start "rule__TmlExpression__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:828:1: rule__TmlExpression__Group__0 : rule__TmlExpression__Group__0__Impl rule__TmlExpression__Group__1 ;
    public final void rule__TmlExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:832:1: ( rule__TmlExpression__Group__0__Impl rule__TmlExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:833:2: rule__TmlExpression__Group__0__Impl rule__TmlExpression__Group__1
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__0__Impl_in_rule__TmlExpression__Group__01806);
            rule__TmlExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__1_in_rule__TmlExpression__Group__01809);
            rule__TmlExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__0"


    // $ANTLR start "rule__TmlExpression__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:840:1: rule__TmlExpression__Group__0__Impl : ( RULE_SQBRACKET_OPEN ) ;
    public final void rule__TmlExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:844:1: ( ( RULE_SQBRACKET_OPEN ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:845:1: ( RULE_SQBRACKET_OPEN )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:845:1: ( RULE_SQBRACKET_OPEN )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:846:1: RULE_SQBRACKET_OPEN
            {
             before(grammarAccess.getTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_0()); 
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_rule__TmlExpression__Group__0__Impl1836); 
             after(grammarAccess.getTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__0__Impl"


    // $ANTLR start "rule__TmlExpression__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:857:1: rule__TmlExpression__Group__1 : rule__TmlExpression__Group__1__Impl rule__TmlExpression__Group__2 ;
    public final void rule__TmlExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:861:1: ( rule__TmlExpression__Group__1__Impl rule__TmlExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:862:2: rule__TmlExpression__Group__1__Impl rule__TmlExpression__Group__2
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__1__Impl_in_rule__TmlExpression__Group__11865);
            rule__TmlExpression__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__2_in_rule__TmlExpression__Group__11868);
            rule__TmlExpression__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__1"


    // $ANTLR start "rule__TmlExpression__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:869:1: rule__TmlExpression__Group__1__Impl : ( ( rule__TmlExpression__AbsoluteAssignment_1 )? ) ;
    public final void rule__TmlExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:873:1: ( ( ( rule__TmlExpression__AbsoluteAssignment_1 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:874:1: ( ( rule__TmlExpression__AbsoluteAssignment_1 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:874:1: ( ( rule__TmlExpression__AbsoluteAssignment_1 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:875:1: ( rule__TmlExpression__AbsoluteAssignment_1 )?
            {
             before(grammarAccess.getTmlExpressionAccess().getAbsoluteAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:876:1: ( rule__TmlExpression__AbsoluteAssignment_1 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_TML_SEPARATOR) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:876:2: rule__TmlExpression__AbsoluteAssignment_1
                    {
                    pushFollow(FOLLOW_rule__TmlExpression__AbsoluteAssignment_1_in_rule__TmlExpression__Group__1__Impl1895);
                    rule__TmlExpression__AbsoluteAssignment_1();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTmlExpressionAccess().getAbsoluteAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__1__Impl"


    // $ANTLR start "rule__TmlExpression__Group__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:886:1: rule__TmlExpression__Group__2 : rule__TmlExpression__Group__2__Impl rule__TmlExpression__Group__3 ;
    public final void rule__TmlExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:890:1: ( rule__TmlExpression__Group__2__Impl rule__TmlExpression__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:891:2: rule__TmlExpression__Group__2__Impl rule__TmlExpression__Group__3
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__2__Impl_in_rule__TmlExpression__Group__21926);
            rule__TmlExpression__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__3_in_rule__TmlExpression__Group__21929);
            rule__TmlExpression__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__2"


    // $ANTLR start "rule__TmlExpression__Group__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:898:1: rule__TmlExpression__Group__2__Impl : ( ( rule__TmlExpression__ParamAssignment_2 )? ) ;
    public final void rule__TmlExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:902:1: ( ( ( rule__TmlExpression__ParamAssignment_2 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:903:1: ( ( rule__TmlExpression__ParamAssignment_2 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:903:1: ( ( rule__TmlExpression__ParamAssignment_2 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:904:1: ( rule__TmlExpression__ParamAssignment_2 )?
            {
             before(grammarAccess.getTmlExpressionAccess().getParamAssignment_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:905:1: ( rule__TmlExpression__ParamAssignment_2 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_AT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:905:2: rule__TmlExpression__ParamAssignment_2
                    {
                    pushFollow(FOLLOW_rule__TmlExpression__ParamAssignment_2_in_rule__TmlExpression__Group__2__Impl1956);
                    rule__TmlExpression__ParamAssignment_2();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTmlExpressionAccess().getParamAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__2__Impl"


    // $ANTLR start "rule__TmlExpression__Group__3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:915:1: rule__TmlExpression__Group__3 : rule__TmlExpression__Group__3__Impl rule__TmlExpression__Group__4 ;
    public final void rule__TmlExpression__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:919:1: ( rule__TmlExpression__Group__3__Impl rule__TmlExpression__Group__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:920:2: rule__TmlExpression__Group__3__Impl rule__TmlExpression__Group__4
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__3__Impl_in_rule__TmlExpression__Group__31987);
            rule__TmlExpression__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__4_in_rule__TmlExpression__Group__31990);
            rule__TmlExpression__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__3"


    // $ANTLR start "rule__TmlExpression__Group__3__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:927:1: rule__TmlExpression__Group__3__Impl : ( ( rule__TmlExpression__ElementsAssignment_3 ) ) ;
    public final void rule__TmlExpression__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:931:1: ( ( ( rule__TmlExpression__ElementsAssignment_3 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:932:1: ( ( rule__TmlExpression__ElementsAssignment_3 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:932:1: ( ( rule__TmlExpression__ElementsAssignment_3 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:933:1: ( rule__TmlExpression__ElementsAssignment_3 )
            {
             before(grammarAccess.getTmlExpressionAccess().getElementsAssignment_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:934:1: ( rule__TmlExpression__ElementsAssignment_3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:934:2: rule__TmlExpression__ElementsAssignment_3
            {
            pushFollow(FOLLOW_rule__TmlExpression__ElementsAssignment_3_in_rule__TmlExpression__Group__3__Impl2017);
            rule__TmlExpression__ElementsAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getTmlExpressionAccess().getElementsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__3__Impl"


    // $ANTLR start "rule__TmlExpression__Group__4"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:944:1: rule__TmlExpression__Group__4 : rule__TmlExpression__Group__4__Impl rule__TmlExpression__Group__5 ;
    public final void rule__TmlExpression__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:948:1: ( rule__TmlExpression__Group__4__Impl rule__TmlExpression__Group__5 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:949:2: rule__TmlExpression__Group__4__Impl rule__TmlExpression__Group__5
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__4__Impl_in_rule__TmlExpression__Group__42047);
            rule__TmlExpression__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__5_in_rule__TmlExpression__Group__42050);
            rule__TmlExpression__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__4"


    // $ANTLR start "rule__TmlExpression__Group__4__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:956:1: rule__TmlExpression__Group__4__Impl : ( ( rule__TmlExpression__Group_4__0 )* ) ;
    public final void rule__TmlExpression__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:960:1: ( ( ( rule__TmlExpression__Group_4__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:961:1: ( ( rule__TmlExpression__Group_4__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:961:1: ( ( rule__TmlExpression__Group_4__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:962:1: ( rule__TmlExpression__Group_4__0 )*
            {
             before(grammarAccess.getTmlExpressionAccess().getGroup_4()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:963:1: ( rule__TmlExpression__Group_4__0 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_TML_SEPARATOR) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:963:2: rule__TmlExpression__Group_4__0
            	    {
            	    pushFollow(FOLLOW_rule__TmlExpression__Group_4__0_in_rule__TmlExpression__Group__4__Impl2077);
            	    rule__TmlExpression__Group_4__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

             after(grammarAccess.getTmlExpressionAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__4__Impl"


    // $ANTLR start "rule__TmlExpression__Group__5"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:973:1: rule__TmlExpression__Group__5 : rule__TmlExpression__Group__5__Impl ;
    public final void rule__TmlExpression__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:977:1: ( rule__TmlExpression__Group__5__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:978:2: rule__TmlExpression__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__5__Impl_in_rule__TmlExpression__Group__52108);
            rule__TmlExpression__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__5"


    // $ANTLR start "rule__TmlExpression__Group__5__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:984:1: rule__TmlExpression__Group__5__Impl : ( RULE_SQBRACKET_CLOSE ) ;
    public final void rule__TmlExpression__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:988:1: ( ( RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:989:1: ( RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:989:1: ( RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:990:1: RULE_SQBRACKET_CLOSE
            {
             before(grammarAccess.getTmlExpressionAccess().getSQBRACKET_CLOSETerminalRuleCall_5()); 
            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_rule__TmlExpression__Group__5__Impl2135); 
             after(grammarAccess.getTmlExpressionAccess().getSQBRACKET_CLOSETerminalRuleCall_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group__5__Impl"


    // $ANTLR start "rule__TmlExpression__Group_4__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1013:1: rule__TmlExpression__Group_4__0 : rule__TmlExpression__Group_4__0__Impl rule__TmlExpression__Group_4__1 ;
    public final void rule__TmlExpression__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1017:1: ( rule__TmlExpression__Group_4__0__Impl rule__TmlExpression__Group_4__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1018:2: rule__TmlExpression__Group_4__0__Impl rule__TmlExpression__Group_4__1
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group_4__0__Impl_in_rule__TmlExpression__Group_4__02176);
            rule__TmlExpression__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group_4__1_in_rule__TmlExpression__Group_4__02179);
            rule__TmlExpression__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group_4__0"


    // $ANTLR start "rule__TmlExpression__Group_4__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1025:1: rule__TmlExpression__Group_4__0__Impl : ( RULE_TML_SEPARATOR ) ;
    public final void rule__TmlExpression__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1029:1: ( ( RULE_TML_SEPARATOR ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1030:1: ( RULE_TML_SEPARATOR )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1030:1: ( RULE_TML_SEPARATOR )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1031:1: RULE_TML_SEPARATOR
            {
             before(grammarAccess.getTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_4_0()); 
            match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_rule__TmlExpression__Group_4__0__Impl2206); 
             after(grammarAccess.getTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group_4__0__Impl"


    // $ANTLR start "rule__TmlExpression__Group_4__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1042:1: rule__TmlExpression__Group_4__1 : rule__TmlExpression__Group_4__1__Impl ;
    public final void rule__TmlExpression__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1046:1: ( rule__TmlExpression__Group_4__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1047:2: rule__TmlExpression__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group_4__1__Impl_in_rule__TmlExpression__Group_4__12235);
            rule__TmlExpression__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group_4__1"


    // $ANTLR start "rule__TmlExpression__Group_4__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1053:1: rule__TmlExpression__Group_4__1__Impl : ( ( rule__TmlExpression__ElementsAssignment_4_1 ) ) ;
    public final void rule__TmlExpression__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1057:1: ( ( ( rule__TmlExpression__ElementsAssignment_4_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1058:1: ( ( rule__TmlExpression__ElementsAssignment_4_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1058:1: ( ( rule__TmlExpression__ElementsAssignment_4_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1059:1: ( rule__TmlExpression__ElementsAssignment_4_1 )
            {
             before(grammarAccess.getTmlExpressionAccess().getElementsAssignment_4_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1060:1: ( rule__TmlExpression__ElementsAssignment_4_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1060:2: rule__TmlExpression__ElementsAssignment_4_1
            {
            pushFollow(FOLLOW_rule__TmlExpression__ElementsAssignment_4_1_in_rule__TmlExpression__Group_4__1__Impl2262);
            rule__TmlExpression__ElementsAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getTmlExpressionAccess().getElementsAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__Group_4__1__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1074:1: rule__ExistsTmlExpression__Group__0 : rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1 ;
    public final void rule__ExistsTmlExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1078:1: ( rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1079:2: rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__0__Impl_in_rule__ExistsTmlExpression__Group__02296);
            rule__ExistsTmlExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__1_in_rule__ExistsTmlExpression__Group__02299);
            rule__ExistsTmlExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__0"


    // $ANTLR start "rule__ExistsTmlExpression__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1086:1: rule__ExistsTmlExpression__Group__0__Impl : ( RULE_TML_EXISTS ) ;
    public final void rule__ExistsTmlExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1090:1: ( ( RULE_TML_EXISTS ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1091:1: ( RULE_TML_EXISTS )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1091:1: ( RULE_TML_EXISTS )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1092:1: RULE_TML_EXISTS
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getTML_EXISTSTerminalRuleCall_0()); 
            match(input,RULE_TML_EXISTS,FOLLOW_RULE_TML_EXISTS_in_rule__ExistsTmlExpression__Group__0__Impl2326); 
             after(grammarAccess.getExistsTmlExpressionAccess().getTML_EXISTSTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__0__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1103:1: rule__ExistsTmlExpression__Group__1 : rule__ExistsTmlExpression__Group__1__Impl rule__ExistsTmlExpression__Group__2 ;
    public final void rule__ExistsTmlExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1107:1: ( rule__ExistsTmlExpression__Group__1__Impl rule__ExistsTmlExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1108:2: rule__ExistsTmlExpression__Group__1__Impl rule__ExistsTmlExpression__Group__2
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__1__Impl_in_rule__ExistsTmlExpression__Group__12355);
            rule__ExistsTmlExpression__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__2_in_rule__ExistsTmlExpression__Group__12358);
            rule__ExistsTmlExpression__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__1"


    // $ANTLR start "rule__ExistsTmlExpression__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1115:1: rule__ExistsTmlExpression__Group__1__Impl : ( RULE_SQBRACKET_OPEN ) ;
    public final void rule__ExistsTmlExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1119:1: ( ( RULE_SQBRACKET_OPEN ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1120:1: ( RULE_SQBRACKET_OPEN )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1120:1: ( RULE_SQBRACKET_OPEN )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1121:1: RULE_SQBRACKET_OPEN
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_1()); 
            match(input,RULE_SQBRACKET_OPEN,FOLLOW_RULE_SQBRACKET_OPEN_in_rule__ExistsTmlExpression__Group__1__Impl2385); 
             after(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_OPENTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__1__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1132:1: rule__ExistsTmlExpression__Group__2 : rule__ExistsTmlExpression__Group__2__Impl rule__ExistsTmlExpression__Group__3 ;
    public final void rule__ExistsTmlExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1136:1: ( rule__ExistsTmlExpression__Group__2__Impl rule__ExistsTmlExpression__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1137:2: rule__ExistsTmlExpression__Group__2__Impl rule__ExistsTmlExpression__Group__3
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__2__Impl_in_rule__ExistsTmlExpression__Group__22414);
            rule__ExistsTmlExpression__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__3_in_rule__ExistsTmlExpression__Group__22417);
            rule__ExistsTmlExpression__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__2"


    // $ANTLR start "rule__ExistsTmlExpression__Group__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1144:1: rule__ExistsTmlExpression__Group__2__Impl : ( ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )? ) ;
    public final void rule__ExistsTmlExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1148:1: ( ( ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1149:1: ( ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1149:1: ( ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1150:1: ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )?
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteAssignment_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1151:1: ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_TML_SEPARATOR) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1151:2: rule__ExistsTmlExpression__AbsoluteAssignment_2
                    {
                    pushFollow(FOLLOW_rule__ExistsTmlExpression__AbsoluteAssignment_2_in_rule__ExistsTmlExpression__Group__2__Impl2444);
                    rule__ExistsTmlExpression__AbsoluteAssignment_2();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__2__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group__3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1161:1: rule__ExistsTmlExpression__Group__3 : rule__ExistsTmlExpression__Group__3__Impl rule__ExistsTmlExpression__Group__4 ;
    public final void rule__ExistsTmlExpression__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1165:1: ( rule__ExistsTmlExpression__Group__3__Impl rule__ExistsTmlExpression__Group__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1166:2: rule__ExistsTmlExpression__Group__3__Impl rule__ExistsTmlExpression__Group__4
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__3__Impl_in_rule__ExistsTmlExpression__Group__32475);
            rule__ExistsTmlExpression__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__4_in_rule__ExistsTmlExpression__Group__32478);
            rule__ExistsTmlExpression__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__3"


    // $ANTLR start "rule__ExistsTmlExpression__Group__3__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1173:1: rule__ExistsTmlExpression__Group__3__Impl : ( ( rule__ExistsTmlExpression__ParamAssignment_3 )? ) ;
    public final void rule__ExistsTmlExpression__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1177:1: ( ( ( rule__ExistsTmlExpression__ParamAssignment_3 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1178:1: ( ( rule__ExistsTmlExpression__ParamAssignment_3 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1178:1: ( ( rule__ExistsTmlExpression__ParamAssignment_3 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1179:1: ( rule__ExistsTmlExpression__ParamAssignment_3 )?
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getParamAssignment_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1180:1: ( rule__ExistsTmlExpression__ParamAssignment_3 )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_AT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1180:2: rule__ExistsTmlExpression__ParamAssignment_3
                    {
                    pushFollow(FOLLOW_rule__ExistsTmlExpression__ParamAssignment_3_in_rule__ExistsTmlExpression__Group__3__Impl2505);
                    rule__ExistsTmlExpression__ParamAssignment_3();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getExistsTmlExpressionAccess().getParamAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__3__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group__4"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1190:1: rule__ExistsTmlExpression__Group__4 : rule__ExistsTmlExpression__Group__4__Impl rule__ExistsTmlExpression__Group__5 ;
    public final void rule__ExistsTmlExpression__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1194:1: ( rule__ExistsTmlExpression__Group__4__Impl rule__ExistsTmlExpression__Group__5 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1195:2: rule__ExistsTmlExpression__Group__4__Impl rule__ExistsTmlExpression__Group__5
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__4__Impl_in_rule__ExistsTmlExpression__Group__42536);
            rule__ExistsTmlExpression__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__5_in_rule__ExistsTmlExpression__Group__42539);
            rule__ExistsTmlExpression__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__4"


    // $ANTLR start "rule__ExistsTmlExpression__Group__4__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1202:1: rule__ExistsTmlExpression__Group__4__Impl : ( ( rule__ExistsTmlExpression__ElementsAssignment_4 ) ) ;
    public final void rule__ExistsTmlExpression__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1206:1: ( ( ( rule__ExistsTmlExpression__ElementsAssignment_4 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1207:1: ( ( rule__ExistsTmlExpression__ElementsAssignment_4 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1207:1: ( ( rule__ExistsTmlExpression__ElementsAssignment_4 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1208:1: ( rule__ExistsTmlExpression__ElementsAssignment_4 )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_4()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1209:1: ( rule__ExistsTmlExpression__ElementsAssignment_4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1209:2: rule__ExistsTmlExpression__ElementsAssignment_4
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__ElementsAssignment_4_in_rule__ExistsTmlExpression__Group__4__Impl2566);
            rule__ExistsTmlExpression__ElementsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__4__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group__5"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1219:1: rule__ExistsTmlExpression__Group__5 : rule__ExistsTmlExpression__Group__5__Impl rule__ExistsTmlExpression__Group__6 ;
    public final void rule__ExistsTmlExpression__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1223:1: ( rule__ExistsTmlExpression__Group__5__Impl rule__ExistsTmlExpression__Group__6 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1224:2: rule__ExistsTmlExpression__Group__5__Impl rule__ExistsTmlExpression__Group__6
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__5__Impl_in_rule__ExistsTmlExpression__Group__52596);
            rule__ExistsTmlExpression__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__6_in_rule__ExistsTmlExpression__Group__52599);
            rule__ExistsTmlExpression__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__5"


    // $ANTLR start "rule__ExistsTmlExpression__Group__5__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1231:1: rule__ExistsTmlExpression__Group__5__Impl : ( ( rule__ExistsTmlExpression__Group_5__0 )* ) ;
    public final void rule__ExistsTmlExpression__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1235:1: ( ( ( rule__ExistsTmlExpression__Group_5__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1236:1: ( ( rule__ExistsTmlExpression__Group_5__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1236:1: ( ( rule__ExistsTmlExpression__Group_5__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1237:1: ( rule__ExistsTmlExpression__Group_5__0 )*
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getGroup_5()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1238:1: ( rule__ExistsTmlExpression__Group_5__0 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_TML_SEPARATOR) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1238:2: rule__ExistsTmlExpression__Group_5__0
            	    {
            	    pushFollow(FOLLOW_rule__ExistsTmlExpression__Group_5__0_in_rule__ExistsTmlExpression__Group__5__Impl2626);
            	    rule__ExistsTmlExpression__Group_5__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

             after(grammarAccess.getExistsTmlExpressionAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__5__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group__6"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1248:1: rule__ExistsTmlExpression__Group__6 : rule__ExistsTmlExpression__Group__6__Impl ;
    public final void rule__ExistsTmlExpression__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1252:1: ( rule__ExistsTmlExpression__Group__6__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1253:2: rule__ExistsTmlExpression__Group__6__Impl
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__6__Impl_in_rule__ExistsTmlExpression__Group__62657);
            rule__ExistsTmlExpression__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__6"


    // $ANTLR start "rule__ExistsTmlExpression__Group__6__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1259:1: rule__ExistsTmlExpression__Group__6__Impl : ( RULE_SQBRACKET_CLOSE ) ;
    public final void rule__ExistsTmlExpression__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1263:1: ( ( RULE_SQBRACKET_CLOSE ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1264:1: ( RULE_SQBRACKET_CLOSE )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1264:1: ( RULE_SQBRACKET_CLOSE )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1265:1: RULE_SQBRACKET_CLOSE
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_CLOSETerminalRuleCall_6()); 
            match(input,RULE_SQBRACKET_CLOSE,FOLLOW_RULE_SQBRACKET_CLOSE_in_rule__ExistsTmlExpression__Group__6__Impl2684); 
             after(grammarAccess.getExistsTmlExpressionAccess().getSQBRACKET_CLOSETerminalRuleCall_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group__6__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group_5__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1290:1: rule__ExistsTmlExpression__Group_5__0 : rule__ExistsTmlExpression__Group_5__0__Impl rule__ExistsTmlExpression__Group_5__1 ;
    public final void rule__ExistsTmlExpression__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1294:1: ( rule__ExistsTmlExpression__Group_5__0__Impl rule__ExistsTmlExpression__Group_5__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1295:2: rule__ExistsTmlExpression__Group_5__0__Impl rule__ExistsTmlExpression__Group_5__1
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group_5__0__Impl_in_rule__ExistsTmlExpression__Group_5__02727);
            rule__ExistsTmlExpression__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group_5__1_in_rule__ExistsTmlExpression__Group_5__02730);
            rule__ExistsTmlExpression__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group_5__0"


    // $ANTLR start "rule__ExistsTmlExpression__Group_5__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1302:1: rule__ExistsTmlExpression__Group_5__0__Impl : ( RULE_TML_SEPARATOR ) ;
    public final void rule__ExistsTmlExpression__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1306:1: ( ( RULE_TML_SEPARATOR ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1307:1: ( RULE_TML_SEPARATOR )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1307:1: ( RULE_TML_SEPARATOR )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1308:1: RULE_TML_SEPARATOR
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_5_0()); 
            match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_rule__ExistsTmlExpression__Group_5__0__Impl2757); 
             after(grammarAccess.getExistsTmlExpressionAccess().getTML_SEPARATORTerminalRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group_5__0__Impl"


    // $ANTLR start "rule__ExistsTmlExpression__Group_5__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1319:1: rule__ExistsTmlExpression__Group_5__1 : rule__ExistsTmlExpression__Group_5__1__Impl ;
    public final void rule__ExistsTmlExpression__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1323:1: ( rule__ExistsTmlExpression__Group_5__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1324:2: rule__ExistsTmlExpression__Group_5__1__Impl
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group_5__1__Impl_in_rule__ExistsTmlExpression__Group_5__12786);
            rule__ExistsTmlExpression__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group_5__1"


    // $ANTLR start "rule__ExistsTmlExpression__Group_5__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1330:1: rule__ExistsTmlExpression__Group_5__1__Impl : ( ( rule__ExistsTmlExpression__ElementsAssignment_5_1 ) ) ;
    public final void rule__ExistsTmlExpression__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1334:1: ( ( ( rule__ExistsTmlExpression__ElementsAssignment_5_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1335:1: ( ( rule__ExistsTmlExpression__ElementsAssignment_5_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1335:1: ( ( rule__ExistsTmlExpression__ElementsAssignment_5_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1336:1: ( rule__ExistsTmlExpression__ElementsAssignment_5_1 )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_5_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1337:1: ( rule__ExistsTmlExpression__ElementsAssignment_5_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1337:2: rule__ExistsTmlExpression__ElementsAssignment_5_1
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__ElementsAssignment_5_1_in_rule__ExistsTmlExpression__Group_5__1__Impl2813);
            rule__ExistsTmlExpression__ElementsAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__Group_5__1__Impl"


    // $ANTLR start "rule__MapReferenceParams__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1351:1: rule__MapReferenceParams__Group__0 : rule__MapReferenceParams__Group__0__Impl rule__MapReferenceParams__Group__1 ;
    public final void rule__MapReferenceParams__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1355:1: ( rule__MapReferenceParams__Group__0__Impl rule__MapReferenceParams__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1356:2: rule__MapReferenceParams__Group__0__Impl rule__MapReferenceParams__Group__1
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__Group__0__Impl_in_rule__MapReferenceParams__Group__02847);
            rule__MapReferenceParams__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MapReferenceParams__Group__1_in_rule__MapReferenceParams__Group__02850);
            rule__MapReferenceParams__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group__0"


    // $ANTLR start "rule__MapReferenceParams__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1363:1: rule__MapReferenceParams__Group__0__Impl : ( '(' ) ;
    public final void rule__MapReferenceParams__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1367:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1368:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1368:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1369:1: '('
            {
             before(grammarAccess.getMapReferenceParamsAccess().getLeftParenthesisKeyword_0()); 
            match(input,25,FOLLOW_25_in_rule__MapReferenceParams__Group__0__Impl2878); 
             after(grammarAccess.getMapReferenceParamsAccess().getLeftParenthesisKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group__0__Impl"


    // $ANTLR start "rule__MapReferenceParams__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1382:1: rule__MapReferenceParams__Group__1 : rule__MapReferenceParams__Group__1__Impl rule__MapReferenceParams__Group__2 ;
    public final void rule__MapReferenceParams__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1386:1: ( rule__MapReferenceParams__Group__1__Impl rule__MapReferenceParams__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1387:2: rule__MapReferenceParams__Group__1__Impl rule__MapReferenceParams__Group__2
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__Group__1__Impl_in_rule__MapReferenceParams__Group__12909);
            rule__MapReferenceParams__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MapReferenceParams__Group__2_in_rule__MapReferenceParams__Group__12912);
            rule__MapReferenceParams__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group__1"


    // $ANTLR start "rule__MapReferenceParams__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1394:1: rule__MapReferenceParams__Group__1__Impl : ( ( rule__MapReferenceParams__GetterParamsAssignment_1 ) ) ;
    public final void rule__MapReferenceParams__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1398:1: ( ( ( rule__MapReferenceParams__GetterParamsAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1399:1: ( ( rule__MapReferenceParams__GetterParamsAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1399:1: ( ( rule__MapReferenceParams__GetterParamsAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1400:1: ( rule__MapReferenceParams__GetterParamsAssignment_1 )
            {
             before(grammarAccess.getMapReferenceParamsAccess().getGetterParamsAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1401:1: ( rule__MapReferenceParams__GetterParamsAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1401:2: rule__MapReferenceParams__GetterParamsAssignment_1
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__GetterParamsAssignment_1_in_rule__MapReferenceParams__Group__1__Impl2939);
            rule__MapReferenceParams__GetterParamsAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getMapReferenceParamsAccess().getGetterParamsAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group__1__Impl"


    // $ANTLR start "rule__MapReferenceParams__Group__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1411:1: rule__MapReferenceParams__Group__2 : rule__MapReferenceParams__Group__2__Impl rule__MapReferenceParams__Group__3 ;
    public final void rule__MapReferenceParams__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1415:1: ( rule__MapReferenceParams__Group__2__Impl rule__MapReferenceParams__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1416:2: rule__MapReferenceParams__Group__2__Impl rule__MapReferenceParams__Group__3
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__Group__2__Impl_in_rule__MapReferenceParams__Group__22969);
            rule__MapReferenceParams__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MapReferenceParams__Group__3_in_rule__MapReferenceParams__Group__22972);
            rule__MapReferenceParams__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group__2"


    // $ANTLR start "rule__MapReferenceParams__Group__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1423:1: rule__MapReferenceParams__Group__2__Impl : ( ( rule__MapReferenceParams__Group_2__0 )* ) ;
    public final void rule__MapReferenceParams__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1427:1: ( ( ( rule__MapReferenceParams__Group_2__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1428:1: ( ( rule__MapReferenceParams__Group_2__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1428:1: ( ( rule__MapReferenceParams__Group_2__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1429:1: ( rule__MapReferenceParams__Group_2__0 )*
            {
             before(grammarAccess.getMapReferenceParamsAccess().getGroup_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1430:1: ( rule__MapReferenceParams__Group_2__0 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==27) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1430:2: rule__MapReferenceParams__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__MapReferenceParams__Group_2__0_in_rule__MapReferenceParams__Group__2__Impl2999);
            	    rule__MapReferenceParams__Group_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

             after(grammarAccess.getMapReferenceParamsAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group__2__Impl"


    // $ANTLR start "rule__MapReferenceParams__Group__3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1440:1: rule__MapReferenceParams__Group__3 : rule__MapReferenceParams__Group__3__Impl ;
    public final void rule__MapReferenceParams__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1444:1: ( rule__MapReferenceParams__Group__3__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1445:2: rule__MapReferenceParams__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__Group__3__Impl_in_rule__MapReferenceParams__Group__33030);
            rule__MapReferenceParams__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group__3"


    // $ANTLR start "rule__MapReferenceParams__Group__3__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1451:1: rule__MapReferenceParams__Group__3__Impl : ( ')' ) ;
    public final void rule__MapReferenceParams__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1455:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1456:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1456:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1457:1: ')'
            {
             before(grammarAccess.getMapReferenceParamsAccess().getRightParenthesisKeyword_3()); 
            match(input,26,FOLLOW_26_in_rule__MapReferenceParams__Group__3__Impl3058); 
             after(grammarAccess.getMapReferenceParamsAccess().getRightParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group__3__Impl"


    // $ANTLR start "rule__MapReferenceParams__Group_2__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1478:1: rule__MapReferenceParams__Group_2__0 : rule__MapReferenceParams__Group_2__0__Impl rule__MapReferenceParams__Group_2__1 ;
    public final void rule__MapReferenceParams__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1482:1: ( rule__MapReferenceParams__Group_2__0__Impl rule__MapReferenceParams__Group_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1483:2: rule__MapReferenceParams__Group_2__0__Impl rule__MapReferenceParams__Group_2__1
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__Group_2__0__Impl_in_rule__MapReferenceParams__Group_2__03097);
            rule__MapReferenceParams__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MapReferenceParams__Group_2__1_in_rule__MapReferenceParams__Group_2__03100);
            rule__MapReferenceParams__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group_2__0"


    // $ANTLR start "rule__MapReferenceParams__Group_2__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1490:1: rule__MapReferenceParams__Group_2__0__Impl : ( ',' ) ;
    public final void rule__MapReferenceParams__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1494:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1495:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1495:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1496:1: ','
            {
             before(grammarAccess.getMapReferenceParamsAccess().getCommaKeyword_2_0()); 
            match(input,27,FOLLOW_27_in_rule__MapReferenceParams__Group_2__0__Impl3128); 
             after(grammarAccess.getMapReferenceParamsAccess().getCommaKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group_2__0__Impl"


    // $ANTLR start "rule__MapReferenceParams__Group_2__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1509:1: rule__MapReferenceParams__Group_2__1 : rule__MapReferenceParams__Group_2__1__Impl ;
    public final void rule__MapReferenceParams__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1513:1: ( rule__MapReferenceParams__Group_2__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1514:2: rule__MapReferenceParams__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__Group_2__1__Impl_in_rule__MapReferenceParams__Group_2__13159);
            rule__MapReferenceParams__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group_2__1"


    // $ANTLR start "rule__MapReferenceParams__Group_2__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1520:1: rule__MapReferenceParams__Group_2__1__Impl : ( ( rule__MapReferenceParams__GetterParamsAssignment_2_1 ) ) ;
    public final void rule__MapReferenceParams__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1524:1: ( ( ( rule__MapReferenceParams__GetterParamsAssignment_2_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1525:1: ( ( rule__MapReferenceParams__GetterParamsAssignment_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1525:1: ( ( rule__MapReferenceParams__GetterParamsAssignment_2_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1526:1: ( rule__MapReferenceParams__GetterParamsAssignment_2_1 )
            {
             before(grammarAccess.getMapReferenceParamsAccess().getGetterParamsAssignment_2_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1527:1: ( rule__MapReferenceParams__GetterParamsAssignment_2_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1527:2: rule__MapReferenceParams__GetterParamsAssignment_2_1
            {
            pushFollow(FOLLOW_rule__MapReferenceParams__GetterParamsAssignment_2_1_in_rule__MapReferenceParams__Group_2__1__Impl3186);
            rule__MapReferenceParams__GetterParamsAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getMapReferenceParamsAccess().getGetterParamsAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__Group_2__1__Impl"


    // $ANTLR start "rule__MapGetReference__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1541:1: rule__MapGetReference__Group__0 : rule__MapGetReference__Group__0__Impl rule__MapGetReference__Group__1 ;
    public final void rule__MapGetReference__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1545:1: ( rule__MapGetReference__Group__0__Impl rule__MapGetReference__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1546:2: rule__MapGetReference__Group__0__Impl rule__MapGetReference__Group__1
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__0__Impl_in_rule__MapGetReference__Group__03220);
            rule__MapGetReference__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MapGetReference__Group__1_in_rule__MapGetReference__Group__03223);
            rule__MapGetReference__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group__0"


    // $ANTLR start "rule__MapGetReference__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1553:1: rule__MapGetReference__Group__0__Impl : ( ( rule__MapGetReference__OperationsAssignment_0 ) ) ;
    public final void rule__MapGetReference__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1557:1: ( ( ( rule__MapGetReference__OperationsAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1558:1: ( ( rule__MapGetReference__OperationsAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1558:1: ( ( rule__MapGetReference__OperationsAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1559:1: ( rule__MapGetReference__OperationsAssignment_0 )
            {
             before(grammarAccess.getMapGetReferenceAccess().getOperationsAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1560:1: ( rule__MapGetReference__OperationsAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1560:2: rule__MapGetReference__OperationsAssignment_0
            {
            pushFollow(FOLLOW_rule__MapGetReference__OperationsAssignment_0_in_rule__MapGetReference__Group__0__Impl3250);
            rule__MapGetReference__OperationsAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getMapGetReferenceAccess().getOperationsAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group__0__Impl"


    // $ANTLR start "rule__MapGetReference__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1570:1: rule__MapGetReference__Group__1 : rule__MapGetReference__Group__1__Impl rule__MapGetReference__Group__2 ;
    public final void rule__MapGetReference__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1574:1: ( rule__MapGetReference__Group__1__Impl rule__MapGetReference__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1575:2: rule__MapGetReference__Group__1__Impl rule__MapGetReference__Group__2
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__1__Impl_in_rule__MapGetReference__Group__13280);
            rule__MapGetReference__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MapGetReference__Group__2_in_rule__MapGetReference__Group__13283);
            rule__MapGetReference__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group__1"


    // $ANTLR start "rule__MapGetReference__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1582:1: rule__MapGetReference__Group__1__Impl : ( ( rule__MapGetReference__Group_1__0 )* ) ;
    public final void rule__MapGetReference__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1586:1: ( ( ( rule__MapGetReference__Group_1__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1587:1: ( ( rule__MapGetReference__Group_1__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1587:1: ( ( rule__MapGetReference__Group_1__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1588:1: ( rule__MapGetReference__Group_1__0 )*
            {
             before(grammarAccess.getMapGetReferenceAccess().getGroup_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1589:1: ( rule__MapGetReference__Group_1__0 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_PARENT) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1589:2: rule__MapGetReference__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__MapGetReference__Group_1__0_in_rule__MapGetReference__Group__1__Impl3310);
            	    rule__MapGetReference__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

             after(grammarAccess.getMapGetReferenceAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group__1__Impl"


    // $ANTLR start "rule__MapGetReference__Group__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1599:1: rule__MapGetReference__Group__2 : rule__MapGetReference__Group__2__Impl rule__MapGetReference__Group__3 ;
    public final void rule__MapGetReference__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1603:1: ( rule__MapGetReference__Group__2__Impl rule__MapGetReference__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1604:2: rule__MapGetReference__Group__2__Impl rule__MapGetReference__Group__3
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__2__Impl_in_rule__MapGetReference__Group__23341);
            rule__MapGetReference__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MapGetReference__Group__3_in_rule__MapGetReference__Group__23344);
            rule__MapGetReference__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group__2"


    // $ANTLR start "rule__MapGetReference__Group__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1611:1: rule__MapGetReference__Group__2__Impl : ( ( rule__MapGetReference__ElementsAssignment_2 ) ) ;
    public final void rule__MapGetReference__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1615:1: ( ( ( rule__MapGetReference__ElementsAssignment_2 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1616:1: ( ( rule__MapGetReference__ElementsAssignment_2 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1616:1: ( ( rule__MapGetReference__ElementsAssignment_2 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1617:1: ( rule__MapGetReference__ElementsAssignment_2 )
            {
             before(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1618:1: ( rule__MapGetReference__ElementsAssignment_2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1618:2: rule__MapGetReference__ElementsAssignment_2
            {
            pushFollow(FOLLOW_rule__MapGetReference__ElementsAssignment_2_in_rule__MapGetReference__Group__2__Impl3371);
            rule__MapGetReference__ElementsAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group__2__Impl"


    // $ANTLR start "rule__MapGetReference__Group__3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1628:1: rule__MapGetReference__Group__3 : rule__MapGetReference__Group__3__Impl ;
    public final void rule__MapGetReference__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1632:1: ( rule__MapGetReference__Group__3__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1633:2: rule__MapGetReference__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__3__Impl_in_rule__MapGetReference__Group__33401);
            rule__MapGetReference__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group__3"


    // $ANTLR start "rule__MapGetReference__Group__3__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1639:1: rule__MapGetReference__Group__3__Impl : ( ( rule__MapGetReference__ReferenceParamsAssignment_3 )? ) ;
    public final void rule__MapGetReference__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1643:1: ( ( ( rule__MapGetReference__ReferenceParamsAssignment_3 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1644:1: ( ( rule__MapGetReference__ReferenceParamsAssignment_3 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1644:1: ( ( rule__MapGetReference__ReferenceParamsAssignment_3 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1645:1: ( rule__MapGetReference__ReferenceParamsAssignment_3 )?
            {
             before(grammarAccess.getMapGetReferenceAccess().getReferenceParamsAssignment_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1646:1: ( rule__MapGetReference__ReferenceParamsAssignment_3 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==25) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1646:2: rule__MapGetReference__ReferenceParamsAssignment_3
                    {
                    pushFollow(FOLLOW_rule__MapGetReference__ReferenceParamsAssignment_3_in_rule__MapGetReference__Group__3__Impl3428);
                    rule__MapGetReference__ReferenceParamsAssignment_3();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMapGetReferenceAccess().getReferenceParamsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group__3__Impl"


    // $ANTLR start "rule__MapGetReference__Group_1__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1664:1: rule__MapGetReference__Group_1__0 : rule__MapGetReference__Group_1__0__Impl rule__MapGetReference__Group_1__1 ;
    public final void rule__MapGetReference__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1668:1: ( rule__MapGetReference__Group_1__0__Impl rule__MapGetReference__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1669:2: rule__MapGetReference__Group_1__0__Impl rule__MapGetReference__Group_1__1
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group_1__0__Impl_in_rule__MapGetReference__Group_1__03467);
            rule__MapGetReference__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MapGetReference__Group_1__1_in_rule__MapGetReference__Group_1__03470);
            rule__MapGetReference__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group_1__0"


    // $ANTLR start "rule__MapGetReference__Group_1__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1676:1: rule__MapGetReference__Group_1__0__Impl : ( ( rule__MapGetReference__ElementsAssignment_1_0 ) ) ;
    public final void rule__MapGetReference__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1680:1: ( ( ( rule__MapGetReference__ElementsAssignment_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1681:1: ( ( rule__MapGetReference__ElementsAssignment_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1681:1: ( ( rule__MapGetReference__ElementsAssignment_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1682:1: ( rule__MapGetReference__ElementsAssignment_1_0 )
            {
             before(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1683:1: ( rule__MapGetReference__ElementsAssignment_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1683:2: rule__MapGetReference__ElementsAssignment_1_0
            {
            pushFollow(FOLLOW_rule__MapGetReference__ElementsAssignment_1_0_in_rule__MapGetReference__Group_1__0__Impl3497);
            rule__MapGetReference__ElementsAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group_1__0__Impl"


    // $ANTLR start "rule__MapGetReference__Group_1__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1693:1: rule__MapGetReference__Group_1__1 : rule__MapGetReference__Group_1__1__Impl ;
    public final void rule__MapGetReference__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1697:1: ( rule__MapGetReference__Group_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1698:2: rule__MapGetReference__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group_1__1__Impl_in_rule__MapGetReference__Group_1__13527);
            rule__MapGetReference__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group_1__1"


    // $ANTLR start "rule__MapGetReference__Group_1__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1704:1: rule__MapGetReference__Group_1__1__Impl : ( RULE_TML_SEPARATOR ) ;
    public final void rule__MapGetReference__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1708:1: ( ( RULE_TML_SEPARATOR ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1709:1: ( RULE_TML_SEPARATOR )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1709:1: ( RULE_TML_SEPARATOR )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1710:1: RULE_TML_SEPARATOR
            {
             before(grammarAccess.getMapGetReferenceAccess().getTML_SEPARATORTerminalRuleCall_1_1()); 
            match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_rule__MapGetReference__Group_1__1__Impl3554); 
             after(grammarAccess.getMapGetReferenceAccess().getTML_SEPARATORTerminalRuleCall_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__Group_1__1__Impl"


    // $ANTLR start "rule__OrExpression__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1725:1: rule__OrExpression__Group__0 : rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1 ;
    public final void rule__OrExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1729:1: ( rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1730:2: rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__0__Impl_in_rule__OrExpression__Group__03587);
            rule__OrExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group__1_in_rule__OrExpression__Group__03590);
            rule__OrExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__Group__0"


    // $ANTLR start "rule__OrExpression__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1737:1: rule__OrExpression__Group__0__Impl : ( ( rule__OrExpression__ParametersAssignment_0 ) ) ;
    public final void rule__OrExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1741:1: ( ( ( rule__OrExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1742:1: ( ( rule__OrExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1742:1: ( ( rule__OrExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1743:1: ( rule__OrExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1744:1: ( rule__OrExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1744:2: rule__OrExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__OrExpression__ParametersAssignment_0_in_rule__OrExpression__Group__0__Impl3617);
            rule__OrExpression__ParametersAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getOrExpressionAccess().getParametersAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__Group__0__Impl"


    // $ANTLR start "rule__OrExpression__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1754:1: rule__OrExpression__Group__1 : rule__OrExpression__Group__1__Impl ;
    public final void rule__OrExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1758:1: ( rule__OrExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1759:2: rule__OrExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__1__Impl_in_rule__OrExpression__Group__13647);
            rule__OrExpression__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__Group__1"


    // $ANTLR start "rule__OrExpression__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1765:1: rule__OrExpression__Group__1__Impl : ( ( rule__OrExpression__Group_1__0 )* ) ;
    public final void rule__OrExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1769:1: ( ( ( rule__OrExpression__Group_1__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1770:1: ( ( rule__OrExpression__Group_1__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1770:1: ( ( rule__OrExpression__Group_1__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1771:1: ( rule__OrExpression__Group_1__0 )*
            {
             before(grammarAccess.getOrExpressionAccess().getGroup_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1772:1: ( rule__OrExpression__Group_1__0 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==32) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1772:2: rule__OrExpression__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__OrExpression__Group_1__0_in_rule__OrExpression__Group__1__Impl3674);
            	    rule__OrExpression__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

             after(grammarAccess.getOrExpressionAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__Group__1__Impl"


    // $ANTLR start "rule__OrExpression__Group_1__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1786:1: rule__OrExpression__Group_1__0 : rule__OrExpression__Group_1__0__Impl rule__OrExpression__Group_1__1 ;
    public final void rule__OrExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1790:1: ( rule__OrExpression__Group_1__0__Impl rule__OrExpression__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1791:2: rule__OrExpression__Group_1__0__Impl rule__OrExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__OrExpression__Group_1__0__Impl_in_rule__OrExpression__Group_1__03709);
            rule__OrExpression__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group_1__1_in_rule__OrExpression__Group_1__03712);
            rule__OrExpression__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__Group_1__0"


    // $ANTLR start "rule__OrExpression__Group_1__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1798:1: rule__OrExpression__Group_1__0__Impl : ( ( rule__OrExpression__OperationsAssignment_1_0 ) ) ;
    public final void rule__OrExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1802:1: ( ( ( rule__OrExpression__OperationsAssignment_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1803:1: ( ( rule__OrExpression__OperationsAssignment_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1803:1: ( ( rule__OrExpression__OperationsAssignment_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1804:1: ( rule__OrExpression__OperationsAssignment_1_0 )
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsAssignment_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1805:1: ( rule__OrExpression__OperationsAssignment_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1805:2: rule__OrExpression__OperationsAssignment_1_0
            {
            pushFollow(FOLLOW_rule__OrExpression__OperationsAssignment_1_0_in_rule__OrExpression__Group_1__0__Impl3739);
            rule__OrExpression__OperationsAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getOrExpressionAccess().getOperationsAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__Group_1__0__Impl"


    // $ANTLR start "rule__OrExpression__Group_1__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1815:1: rule__OrExpression__Group_1__1 : rule__OrExpression__Group_1__1__Impl ;
    public final void rule__OrExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1819:1: ( rule__OrExpression__Group_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1820:2: rule__OrExpression__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__OrExpression__Group_1__1__Impl_in_rule__OrExpression__Group_1__13769);
            rule__OrExpression__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__Group_1__1"


    // $ANTLR start "rule__OrExpression__Group_1__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1826:1: rule__OrExpression__Group_1__1__Impl : ( ( rule__OrExpression__ParametersAssignment_1_1 ) ) ;
    public final void rule__OrExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1830:1: ( ( ( rule__OrExpression__ParametersAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1831:1: ( ( rule__OrExpression__ParametersAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1831:1: ( ( rule__OrExpression__ParametersAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1832:1: ( rule__OrExpression__ParametersAssignment_1_1 )
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1833:1: ( rule__OrExpression__ParametersAssignment_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1833:2: rule__OrExpression__ParametersAssignment_1_1
            {
            pushFollow(FOLLOW_rule__OrExpression__ParametersAssignment_1_1_in_rule__OrExpression__Group_1__1__Impl3796);
            rule__OrExpression__ParametersAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getOrExpressionAccess().getParametersAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__Group_1__1__Impl"


    // $ANTLR start "rule__AndExpression__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1847:1: rule__AndExpression__Group__0 : rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1 ;
    public final void rule__AndExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1851:1: ( rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1852:2: rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__0__Impl_in_rule__AndExpression__Group__03830);
            rule__AndExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group__1_in_rule__AndExpression__Group__03833);
            rule__AndExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__Group__0"


    // $ANTLR start "rule__AndExpression__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1859:1: rule__AndExpression__Group__0__Impl : ( ( rule__AndExpression__ParametersAssignment_0 ) ) ;
    public final void rule__AndExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1863:1: ( ( ( rule__AndExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1864:1: ( ( rule__AndExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1864:1: ( ( rule__AndExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1865:1: ( rule__AndExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getAndExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1866:1: ( rule__AndExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1866:2: rule__AndExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__AndExpression__ParametersAssignment_0_in_rule__AndExpression__Group__0__Impl3860);
            rule__AndExpression__ParametersAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getAndExpressionAccess().getParametersAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__Group__0__Impl"


    // $ANTLR start "rule__AndExpression__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1876:1: rule__AndExpression__Group__1 : rule__AndExpression__Group__1__Impl ;
    public final void rule__AndExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1880:1: ( rule__AndExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1881:2: rule__AndExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__1__Impl_in_rule__AndExpression__Group__13890);
            rule__AndExpression__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__Group__1"


    // $ANTLR start "rule__AndExpression__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1887:1: rule__AndExpression__Group__1__Impl : ( ( rule__AndExpression__Group_1__0 )* ) ;
    public final void rule__AndExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1891:1: ( ( ( rule__AndExpression__Group_1__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1892:1: ( ( rule__AndExpression__Group_1__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1892:1: ( ( rule__AndExpression__Group_1__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1893:1: ( rule__AndExpression__Group_1__0 )*
            {
             before(grammarAccess.getAndExpressionAccess().getGroup_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1894:1: ( rule__AndExpression__Group_1__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==33) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1894:2: rule__AndExpression__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__AndExpression__Group_1__0_in_rule__AndExpression__Group__1__Impl3917);
            	    rule__AndExpression__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             after(grammarAccess.getAndExpressionAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__Group__1__Impl"


    // $ANTLR start "rule__AndExpression__Group_1__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1908:1: rule__AndExpression__Group_1__0 : rule__AndExpression__Group_1__0__Impl rule__AndExpression__Group_1__1 ;
    public final void rule__AndExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1912:1: ( rule__AndExpression__Group_1__0__Impl rule__AndExpression__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1913:2: rule__AndExpression__Group_1__0__Impl rule__AndExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__AndExpression__Group_1__0__Impl_in_rule__AndExpression__Group_1__03952);
            rule__AndExpression__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group_1__1_in_rule__AndExpression__Group_1__03955);
            rule__AndExpression__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__Group_1__0"


    // $ANTLR start "rule__AndExpression__Group_1__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1920:1: rule__AndExpression__Group_1__0__Impl : ( ( rule__AndExpression__OperationsAssignment_1_0 ) ) ;
    public final void rule__AndExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1924:1: ( ( ( rule__AndExpression__OperationsAssignment_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1925:1: ( ( rule__AndExpression__OperationsAssignment_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1925:1: ( ( rule__AndExpression__OperationsAssignment_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1926:1: ( rule__AndExpression__OperationsAssignment_1_0 )
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsAssignment_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1927:1: ( rule__AndExpression__OperationsAssignment_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1927:2: rule__AndExpression__OperationsAssignment_1_0
            {
            pushFollow(FOLLOW_rule__AndExpression__OperationsAssignment_1_0_in_rule__AndExpression__Group_1__0__Impl3982);
            rule__AndExpression__OperationsAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getAndExpressionAccess().getOperationsAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__Group_1__0__Impl"


    // $ANTLR start "rule__AndExpression__Group_1__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1937:1: rule__AndExpression__Group_1__1 : rule__AndExpression__Group_1__1__Impl ;
    public final void rule__AndExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1941:1: ( rule__AndExpression__Group_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1942:2: rule__AndExpression__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__AndExpression__Group_1__1__Impl_in_rule__AndExpression__Group_1__14012);
            rule__AndExpression__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__Group_1__1"


    // $ANTLR start "rule__AndExpression__Group_1__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1948:1: rule__AndExpression__Group_1__1__Impl : ( ( rule__AndExpression__ParametersAssignment_1_1 ) ) ;
    public final void rule__AndExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1952:1: ( ( ( rule__AndExpression__ParametersAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1953:1: ( ( rule__AndExpression__ParametersAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1953:1: ( ( rule__AndExpression__ParametersAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1954:1: ( rule__AndExpression__ParametersAssignment_1_1 )
            {
             before(grammarAccess.getAndExpressionAccess().getParametersAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1955:1: ( rule__AndExpression__ParametersAssignment_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1955:2: rule__AndExpression__ParametersAssignment_1_1
            {
            pushFollow(FOLLOW_rule__AndExpression__ParametersAssignment_1_1_in_rule__AndExpression__Group_1__1__Impl4039);
            rule__AndExpression__ParametersAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getAndExpressionAccess().getParametersAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__Group_1__1__Impl"


    // $ANTLR start "rule__EqualityExpression__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1969:1: rule__EqualityExpression__Group__0 : rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1 ;
    public final void rule__EqualityExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1973:1: ( rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1974:2: rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__0__Impl_in_rule__EqualityExpression__Group__04073);
            rule__EqualityExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group__1_in_rule__EqualityExpression__Group__04076);
            rule__EqualityExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group__0"


    // $ANTLR start "rule__EqualityExpression__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1981:1: rule__EqualityExpression__Group__0__Impl : ( ( rule__EqualityExpression__ParametersAssignment_0 ) ) ;
    public final void rule__EqualityExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1985:1: ( ( ( rule__EqualityExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1986:1: ( ( rule__EqualityExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1986:1: ( ( rule__EqualityExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1987:1: ( rule__EqualityExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1988:1: ( rule__EqualityExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1988:2: rule__EqualityExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_0_in_rule__EqualityExpression__Group__0__Impl4103);
            rule__EqualityExpression__ParametersAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group__0__Impl"


    // $ANTLR start "rule__EqualityExpression__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1998:1: rule__EqualityExpression__Group__1 : rule__EqualityExpression__Group__1__Impl ;
    public final void rule__EqualityExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2002:1: ( rule__EqualityExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2003:2: rule__EqualityExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__1__Impl_in_rule__EqualityExpression__Group__14133);
            rule__EqualityExpression__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group__1"


    // $ANTLR start "rule__EqualityExpression__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2009:1: rule__EqualityExpression__Group__1__Impl : ( ( rule__EqualityExpression__Alternatives_1 )? ) ;
    public final void rule__EqualityExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2013:1: ( ( ( rule__EqualityExpression__Alternatives_1 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2014:1: ( ( rule__EqualityExpression__Alternatives_1 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2014:1: ( ( rule__EqualityExpression__Alternatives_1 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2015:1: ( rule__EqualityExpression__Alternatives_1 )?
            {
             before(grammarAccess.getEqualityExpressionAccess().getAlternatives_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2016:1: ( rule__EqualityExpression__Alternatives_1 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>=34 && LA20_0<=35)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2016:2: rule__EqualityExpression__Alternatives_1
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Alternatives_1_in_rule__EqualityExpression__Group__1__Impl4160);
                    rule__EqualityExpression__Alternatives_1();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEqualityExpressionAccess().getAlternatives_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group__1__Impl"


    // $ANTLR start "rule__EqualityExpression__Group_1_0__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2030:1: rule__EqualityExpression__Group_1_0__0 : rule__EqualityExpression__Group_1_0__0__Impl rule__EqualityExpression__Group_1_0__1 ;
    public final void rule__EqualityExpression__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2034:1: ( rule__EqualityExpression__Group_1_0__0__Impl rule__EqualityExpression__Group_1_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2035:2: rule__EqualityExpression__Group_1_0__0__Impl rule__EqualityExpression__Group_1_0__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_0__0__Impl_in_rule__EqualityExpression__Group_1_0__04195);
            rule__EqualityExpression__Group_1_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_0__1_in_rule__EqualityExpression__Group_1_0__04198);
            rule__EqualityExpression__Group_1_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group_1_0__0"


    // $ANTLR start "rule__EqualityExpression__Group_1_0__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2042:1: rule__EqualityExpression__Group_1_0__0__Impl : ( ( rule__EqualityExpression__OperationsAssignment_1_0_0 ) ) ;
    public final void rule__EqualityExpression__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2046:1: ( ( ( rule__EqualityExpression__OperationsAssignment_1_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2047:1: ( ( rule__EqualityExpression__OperationsAssignment_1_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2047:1: ( ( rule__EqualityExpression__OperationsAssignment_1_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2048:1: ( rule__EqualityExpression__OperationsAssignment_1_0_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2049:1: ( rule__EqualityExpression__OperationsAssignment_1_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2049:2: rule__EqualityExpression__OperationsAssignment_1_0_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__OperationsAssignment_1_0_0_in_rule__EqualityExpression__Group_1_0__0__Impl4225);
            rule__EqualityExpression__OperationsAssignment_1_0_0();

            state._fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group_1_0__0__Impl"


    // $ANTLR start "rule__EqualityExpression__Group_1_0__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2059:1: rule__EqualityExpression__Group_1_0__1 : rule__EqualityExpression__Group_1_0__1__Impl ;
    public final void rule__EqualityExpression__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2063:1: ( rule__EqualityExpression__Group_1_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2064:2: rule__EqualityExpression__Group_1_0__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_0__1__Impl_in_rule__EqualityExpression__Group_1_0__14255);
            rule__EqualityExpression__Group_1_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group_1_0__1"


    // $ANTLR start "rule__EqualityExpression__Group_1_0__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2070:1: rule__EqualityExpression__Group_1_0__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_1_0_1 ) ) ;
    public final void rule__EqualityExpression__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2074:1: ( ( ( rule__EqualityExpression__ParametersAssignment_1_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2075:1: ( ( rule__EqualityExpression__ParametersAssignment_1_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2075:1: ( ( rule__EqualityExpression__ParametersAssignment_1_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2076:1: ( rule__EqualityExpression__ParametersAssignment_1_0_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2077:1: ( rule__EqualityExpression__ParametersAssignment_1_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2077:2: rule__EqualityExpression__ParametersAssignment_1_0_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_1_0_1_in_rule__EqualityExpression__Group_1_0__1__Impl4282);
            rule__EqualityExpression__ParametersAssignment_1_0_1();

            state._fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group_1_0__1__Impl"


    // $ANTLR start "rule__EqualityExpression__Group_1_1__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2091:1: rule__EqualityExpression__Group_1_1__0 : rule__EqualityExpression__Group_1_1__0__Impl rule__EqualityExpression__Group_1_1__1 ;
    public final void rule__EqualityExpression__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2095:1: ( rule__EqualityExpression__Group_1_1__0__Impl rule__EqualityExpression__Group_1_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2096:2: rule__EqualityExpression__Group_1_1__0__Impl rule__EqualityExpression__Group_1_1__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_1__0__Impl_in_rule__EqualityExpression__Group_1_1__04316);
            rule__EqualityExpression__Group_1_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_1__1_in_rule__EqualityExpression__Group_1_1__04319);
            rule__EqualityExpression__Group_1_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group_1_1__0"


    // $ANTLR start "rule__EqualityExpression__Group_1_1__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2103:1: rule__EqualityExpression__Group_1_1__0__Impl : ( ( rule__EqualityExpression__OperationsAssignment_1_1_0 ) ) ;
    public final void rule__EqualityExpression__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2107:1: ( ( ( rule__EqualityExpression__OperationsAssignment_1_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2108:1: ( ( rule__EqualityExpression__OperationsAssignment_1_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2108:1: ( ( rule__EqualityExpression__OperationsAssignment_1_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2109:1: ( rule__EqualityExpression__OperationsAssignment_1_1_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_1_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2110:1: ( rule__EqualityExpression__OperationsAssignment_1_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2110:2: rule__EqualityExpression__OperationsAssignment_1_1_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__OperationsAssignment_1_1_0_in_rule__EqualityExpression__Group_1_1__0__Impl4346);
            rule__EqualityExpression__OperationsAssignment_1_1_0();

            state._fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group_1_1__0__Impl"


    // $ANTLR start "rule__EqualityExpression__Group_1_1__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2120:1: rule__EqualityExpression__Group_1_1__1 : rule__EqualityExpression__Group_1_1__1__Impl ;
    public final void rule__EqualityExpression__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2124:1: ( rule__EqualityExpression__Group_1_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2125:2: rule__EqualityExpression__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_1__1__Impl_in_rule__EqualityExpression__Group_1_1__14376);
            rule__EqualityExpression__Group_1_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group_1_1__1"


    // $ANTLR start "rule__EqualityExpression__Group_1_1__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2131:1: rule__EqualityExpression__Group_1_1__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_1_1_1 ) ) ;
    public final void rule__EqualityExpression__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2135:1: ( ( ( rule__EqualityExpression__ParametersAssignment_1_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2136:1: ( ( rule__EqualityExpression__ParametersAssignment_1_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2136:1: ( ( rule__EqualityExpression__ParametersAssignment_1_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2137:1: ( rule__EqualityExpression__ParametersAssignment_1_1_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2138:1: ( rule__EqualityExpression__ParametersAssignment_1_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2138:2: rule__EqualityExpression__ParametersAssignment_1_1_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_1_1_1_in_rule__EqualityExpression__Group_1_1__1__Impl4403);
            rule__EqualityExpression__ParametersAssignment_1_1_1();

            state._fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__Group_1_1__1__Impl"


    // $ANTLR start "rule__RelationalExpression__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2152:1: rule__RelationalExpression__Group__0 : rule__RelationalExpression__Group__0__Impl rule__RelationalExpression__Group__1 ;
    public final void rule__RelationalExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2156:1: ( rule__RelationalExpression__Group__0__Impl rule__RelationalExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2157:2: rule__RelationalExpression__Group__0__Impl rule__RelationalExpression__Group__1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group__0__Impl_in_rule__RelationalExpression__Group__04437);
            rule__RelationalExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RelationalExpression__Group__1_in_rule__RelationalExpression__Group__04440);
            rule__RelationalExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group__0"


    // $ANTLR start "rule__RelationalExpression__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2164:1: rule__RelationalExpression__Group__0__Impl : ( () ) ;
    public final void rule__RelationalExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2168:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2169:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2169:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2170:1: ()
            {
             before(grammarAccess.getRelationalExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2171:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2173:1: 
            {
            }

             after(grammarAccess.getRelationalExpressionAccess().getExpressionAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group__0__Impl"


    // $ANTLR start "rule__RelationalExpression__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2183:1: rule__RelationalExpression__Group__1 : rule__RelationalExpression__Group__1__Impl rule__RelationalExpression__Group__2 ;
    public final void rule__RelationalExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2187:1: ( rule__RelationalExpression__Group__1__Impl rule__RelationalExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2188:2: rule__RelationalExpression__Group__1__Impl rule__RelationalExpression__Group__2
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group__1__Impl_in_rule__RelationalExpression__Group__14498);
            rule__RelationalExpression__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RelationalExpression__Group__2_in_rule__RelationalExpression__Group__14501);
            rule__RelationalExpression__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group__1"


    // $ANTLR start "rule__RelationalExpression__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2195:1: rule__RelationalExpression__Group__1__Impl : ( ( rule__RelationalExpression__ParametersAssignment_1 ) ) ;
    public final void rule__RelationalExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2199:1: ( ( ( rule__RelationalExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2200:1: ( ( rule__RelationalExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2200:1: ( ( rule__RelationalExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2201:1: ( rule__RelationalExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2202:1: ( rule__RelationalExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2202:2: rule__RelationalExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__ParametersAssignment_1_in_rule__RelationalExpression__Group__1__Impl4528);
            rule__RelationalExpression__ParametersAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group__1__Impl"


    // $ANTLR start "rule__RelationalExpression__Group__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2212:1: rule__RelationalExpression__Group__2 : rule__RelationalExpression__Group__2__Impl ;
    public final void rule__RelationalExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2216:1: ( rule__RelationalExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2217:2: rule__RelationalExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group__2__Impl_in_rule__RelationalExpression__Group__24558);
            rule__RelationalExpression__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group__2"


    // $ANTLR start "rule__RelationalExpression__Group__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2223:1: rule__RelationalExpression__Group__2__Impl : ( ( rule__RelationalExpression__Alternatives_2 )? ) ;
    public final void rule__RelationalExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2227:1: ( ( ( rule__RelationalExpression__Alternatives_2 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2228:1: ( ( rule__RelationalExpression__Alternatives_2 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2228:1: ( ( rule__RelationalExpression__Alternatives_2 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2229:1: ( rule__RelationalExpression__Alternatives_2 )?
            {
             before(grammarAccess.getRelationalExpressionAccess().getAlternatives_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2230:1: ( rule__RelationalExpression__Alternatives_2 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0>=RULE_XML_LT && LA21_0<=RULE_XML_GTEQ)) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2230:2: rule__RelationalExpression__Alternatives_2
                    {
                    pushFollow(FOLLOW_rule__RelationalExpression__Alternatives_2_in_rule__RelationalExpression__Group__2__Impl4585);
                    rule__RelationalExpression__Alternatives_2();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRelationalExpressionAccess().getAlternatives_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group__2__Impl"


    // $ANTLR start "rule__RelationalExpression__Group_2_0__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2246:1: rule__RelationalExpression__Group_2_0__0 : rule__RelationalExpression__Group_2_0__0__Impl rule__RelationalExpression__Group_2_0__1 ;
    public final void rule__RelationalExpression__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2250:1: ( rule__RelationalExpression__Group_2_0__0__Impl rule__RelationalExpression__Group_2_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2251:2: rule__RelationalExpression__Group_2_0__0__Impl rule__RelationalExpression__Group_2_0__1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_0__0__Impl_in_rule__RelationalExpression__Group_2_0__04622);
            rule__RelationalExpression__Group_2_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_0__1_in_rule__RelationalExpression__Group_2_0__04625);
            rule__RelationalExpression__Group_2_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_0__0"


    // $ANTLR start "rule__RelationalExpression__Group_2_0__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2258:1: rule__RelationalExpression__Group_2_0__0__Impl : ( ( rule__RelationalExpression__OperationsAssignment_2_0_0 ) ) ;
    public final void rule__RelationalExpression__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2262:1: ( ( ( rule__RelationalExpression__OperationsAssignment_2_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2263:1: ( ( rule__RelationalExpression__OperationsAssignment_2_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2263:1: ( ( rule__RelationalExpression__OperationsAssignment_2_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2264:1: ( rule__RelationalExpression__OperationsAssignment_2_0_0 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getOperationsAssignment_2_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2265:1: ( rule__RelationalExpression__OperationsAssignment_2_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2265:2: rule__RelationalExpression__OperationsAssignment_2_0_0
            {
            pushFollow(FOLLOW_rule__RelationalExpression__OperationsAssignment_2_0_0_in_rule__RelationalExpression__Group_2_0__0__Impl4652);
            rule__RelationalExpression__OperationsAssignment_2_0_0();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getOperationsAssignment_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_0__0__Impl"


    // $ANTLR start "rule__RelationalExpression__Group_2_0__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2275:1: rule__RelationalExpression__Group_2_0__1 : rule__RelationalExpression__Group_2_0__1__Impl ;
    public final void rule__RelationalExpression__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2279:1: ( rule__RelationalExpression__Group_2_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2280:2: rule__RelationalExpression__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_0__1__Impl_in_rule__RelationalExpression__Group_2_0__14682);
            rule__RelationalExpression__Group_2_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_0__1"


    // $ANTLR start "rule__RelationalExpression__Group_2_0__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2286:1: rule__RelationalExpression__Group_2_0__1__Impl : ( ( rule__RelationalExpression__ParametersAssignment_2_0_1 ) ) ;
    public final void rule__RelationalExpression__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2290:1: ( ( ( rule__RelationalExpression__ParametersAssignment_2_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2291:1: ( ( rule__RelationalExpression__ParametersAssignment_2_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2291:1: ( ( rule__RelationalExpression__ParametersAssignment_2_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2292:1: ( rule__RelationalExpression__ParametersAssignment_2_0_1 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_2_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2293:1: ( rule__RelationalExpression__ParametersAssignment_2_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2293:2: rule__RelationalExpression__ParametersAssignment_2_0_1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__ParametersAssignment_2_0_1_in_rule__RelationalExpression__Group_2_0__1__Impl4709);
            rule__RelationalExpression__ParametersAssignment_2_0_1();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_2_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_0__1__Impl"


    // $ANTLR start "rule__RelationalExpression__Group_2_1__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2307:1: rule__RelationalExpression__Group_2_1__0 : rule__RelationalExpression__Group_2_1__0__Impl rule__RelationalExpression__Group_2_1__1 ;
    public final void rule__RelationalExpression__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2311:1: ( rule__RelationalExpression__Group_2_1__0__Impl rule__RelationalExpression__Group_2_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2312:2: rule__RelationalExpression__Group_2_1__0__Impl rule__RelationalExpression__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_1__0__Impl_in_rule__RelationalExpression__Group_2_1__04743);
            rule__RelationalExpression__Group_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_1__1_in_rule__RelationalExpression__Group_2_1__04746);
            rule__RelationalExpression__Group_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_1__0"


    // $ANTLR start "rule__RelationalExpression__Group_2_1__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2319:1: rule__RelationalExpression__Group_2_1__0__Impl : ( ( rule__RelationalExpression__OperationsAssignment_2_1_0 ) ) ;
    public final void rule__RelationalExpression__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2323:1: ( ( ( rule__RelationalExpression__OperationsAssignment_2_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2324:1: ( ( rule__RelationalExpression__OperationsAssignment_2_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2324:1: ( ( rule__RelationalExpression__OperationsAssignment_2_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2325:1: ( rule__RelationalExpression__OperationsAssignment_2_1_0 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getOperationsAssignment_2_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2326:1: ( rule__RelationalExpression__OperationsAssignment_2_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2326:2: rule__RelationalExpression__OperationsAssignment_2_1_0
            {
            pushFollow(FOLLOW_rule__RelationalExpression__OperationsAssignment_2_1_0_in_rule__RelationalExpression__Group_2_1__0__Impl4773);
            rule__RelationalExpression__OperationsAssignment_2_1_0();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getOperationsAssignment_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_1__0__Impl"


    // $ANTLR start "rule__RelationalExpression__Group_2_1__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2336:1: rule__RelationalExpression__Group_2_1__1 : rule__RelationalExpression__Group_2_1__1__Impl ;
    public final void rule__RelationalExpression__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2340:1: ( rule__RelationalExpression__Group_2_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2341:2: rule__RelationalExpression__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_1__1__Impl_in_rule__RelationalExpression__Group_2_1__14803);
            rule__RelationalExpression__Group_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_1__1"


    // $ANTLR start "rule__RelationalExpression__Group_2_1__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2347:1: rule__RelationalExpression__Group_2_1__1__Impl : ( ( rule__RelationalExpression__ParametersAssignment_2_1_1 ) ) ;
    public final void rule__RelationalExpression__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2351:1: ( ( ( rule__RelationalExpression__ParametersAssignment_2_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2352:1: ( ( rule__RelationalExpression__ParametersAssignment_2_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2352:1: ( ( rule__RelationalExpression__ParametersAssignment_2_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2353:1: ( rule__RelationalExpression__ParametersAssignment_2_1_1 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_2_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2354:1: ( rule__RelationalExpression__ParametersAssignment_2_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2354:2: rule__RelationalExpression__ParametersAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__ParametersAssignment_2_1_1_in_rule__RelationalExpression__Group_2_1__1__Impl4830);
            rule__RelationalExpression__ParametersAssignment_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_1__1__Impl"


    // $ANTLR start "rule__RelationalExpression__Group_2_2__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2368:1: rule__RelationalExpression__Group_2_2__0 : rule__RelationalExpression__Group_2_2__0__Impl rule__RelationalExpression__Group_2_2__1 ;
    public final void rule__RelationalExpression__Group_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2372:1: ( rule__RelationalExpression__Group_2_2__0__Impl rule__RelationalExpression__Group_2_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2373:2: rule__RelationalExpression__Group_2_2__0__Impl rule__RelationalExpression__Group_2_2__1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_2__0__Impl_in_rule__RelationalExpression__Group_2_2__04864);
            rule__RelationalExpression__Group_2_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_2__1_in_rule__RelationalExpression__Group_2_2__04867);
            rule__RelationalExpression__Group_2_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_2__0"


    // $ANTLR start "rule__RelationalExpression__Group_2_2__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2380:1: rule__RelationalExpression__Group_2_2__0__Impl : ( ( rule__RelationalExpression__OperationsAssignment_2_2_0 ) ) ;
    public final void rule__RelationalExpression__Group_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2384:1: ( ( ( rule__RelationalExpression__OperationsAssignment_2_2_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2385:1: ( ( rule__RelationalExpression__OperationsAssignment_2_2_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2385:1: ( ( rule__RelationalExpression__OperationsAssignment_2_2_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2386:1: ( rule__RelationalExpression__OperationsAssignment_2_2_0 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getOperationsAssignment_2_2_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2387:1: ( rule__RelationalExpression__OperationsAssignment_2_2_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2387:2: rule__RelationalExpression__OperationsAssignment_2_2_0
            {
            pushFollow(FOLLOW_rule__RelationalExpression__OperationsAssignment_2_2_0_in_rule__RelationalExpression__Group_2_2__0__Impl4894);
            rule__RelationalExpression__OperationsAssignment_2_2_0();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getOperationsAssignment_2_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_2__0__Impl"


    // $ANTLR start "rule__RelationalExpression__Group_2_2__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2397:1: rule__RelationalExpression__Group_2_2__1 : rule__RelationalExpression__Group_2_2__1__Impl ;
    public final void rule__RelationalExpression__Group_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2401:1: ( rule__RelationalExpression__Group_2_2__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2402:2: rule__RelationalExpression__Group_2_2__1__Impl
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_2__1__Impl_in_rule__RelationalExpression__Group_2_2__14924);
            rule__RelationalExpression__Group_2_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_2__1"


    // $ANTLR start "rule__RelationalExpression__Group_2_2__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2408:1: rule__RelationalExpression__Group_2_2__1__Impl : ( ( rule__RelationalExpression__ParametersAssignment_2_2_1 ) ) ;
    public final void rule__RelationalExpression__Group_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2412:1: ( ( ( rule__RelationalExpression__ParametersAssignment_2_2_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2413:1: ( ( rule__RelationalExpression__ParametersAssignment_2_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2413:1: ( ( rule__RelationalExpression__ParametersAssignment_2_2_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2414:1: ( rule__RelationalExpression__ParametersAssignment_2_2_1 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_2_2_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2415:1: ( rule__RelationalExpression__ParametersAssignment_2_2_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2415:2: rule__RelationalExpression__ParametersAssignment_2_2_1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__ParametersAssignment_2_2_1_in_rule__RelationalExpression__Group_2_2__1__Impl4951);
            rule__RelationalExpression__ParametersAssignment_2_2_1();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_2_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_2__1__Impl"


    // $ANTLR start "rule__RelationalExpression__Group_2_3__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2429:1: rule__RelationalExpression__Group_2_3__0 : rule__RelationalExpression__Group_2_3__0__Impl rule__RelationalExpression__Group_2_3__1 ;
    public final void rule__RelationalExpression__Group_2_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2433:1: ( rule__RelationalExpression__Group_2_3__0__Impl rule__RelationalExpression__Group_2_3__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2434:2: rule__RelationalExpression__Group_2_3__0__Impl rule__RelationalExpression__Group_2_3__1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_3__0__Impl_in_rule__RelationalExpression__Group_2_3__04985);
            rule__RelationalExpression__Group_2_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_3__1_in_rule__RelationalExpression__Group_2_3__04988);
            rule__RelationalExpression__Group_2_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_3__0"


    // $ANTLR start "rule__RelationalExpression__Group_2_3__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2441:1: rule__RelationalExpression__Group_2_3__0__Impl : ( ( rule__RelationalExpression__OperationsAssignment_2_3_0 ) ) ;
    public final void rule__RelationalExpression__Group_2_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2445:1: ( ( ( rule__RelationalExpression__OperationsAssignment_2_3_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2446:1: ( ( rule__RelationalExpression__OperationsAssignment_2_3_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2446:1: ( ( rule__RelationalExpression__OperationsAssignment_2_3_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2447:1: ( rule__RelationalExpression__OperationsAssignment_2_3_0 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getOperationsAssignment_2_3_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2448:1: ( rule__RelationalExpression__OperationsAssignment_2_3_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2448:2: rule__RelationalExpression__OperationsAssignment_2_3_0
            {
            pushFollow(FOLLOW_rule__RelationalExpression__OperationsAssignment_2_3_0_in_rule__RelationalExpression__Group_2_3__0__Impl5015);
            rule__RelationalExpression__OperationsAssignment_2_3_0();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getOperationsAssignment_2_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_3__0__Impl"


    // $ANTLR start "rule__RelationalExpression__Group_2_3__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2458:1: rule__RelationalExpression__Group_2_3__1 : rule__RelationalExpression__Group_2_3__1__Impl ;
    public final void rule__RelationalExpression__Group_2_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2462:1: ( rule__RelationalExpression__Group_2_3__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2463:2: rule__RelationalExpression__Group_2_3__1__Impl
            {
            pushFollow(FOLLOW_rule__RelationalExpression__Group_2_3__1__Impl_in_rule__RelationalExpression__Group_2_3__15045);
            rule__RelationalExpression__Group_2_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_3__1"


    // $ANTLR start "rule__RelationalExpression__Group_2_3__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2469:1: rule__RelationalExpression__Group_2_3__1__Impl : ( ( rule__RelationalExpression__ParametersAssignment_2_3_1 ) ) ;
    public final void rule__RelationalExpression__Group_2_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2473:1: ( ( ( rule__RelationalExpression__ParametersAssignment_2_3_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2474:1: ( ( rule__RelationalExpression__ParametersAssignment_2_3_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2474:1: ( ( rule__RelationalExpression__ParametersAssignment_2_3_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2475:1: ( rule__RelationalExpression__ParametersAssignment_2_3_1 )
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_2_3_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2476:1: ( rule__RelationalExpression__ParametersAssignment_2_3_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2476:2: rule__RelationalExpression__ParametersAssignment_2_3_1
            {
            pushFollow(FOLLOW_rule__RelationalExpression__ParametersAssignment_2_3_1_in_rule__RelationalExpression__Group_2_3__1__Impl5072);
            rule__RelationalExpression__ParametersAssignment_2_3_1();

            state._fsp--;


            }

             after(grammarAccess.getRelationalExpressionAccess().getParametersAssignment_2_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__Group_2_3__1__Impl"


    // $ANTLR start "rule__AdditiveExpression__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2490:1: rule__AdditiveExpression__Group__0 : rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1 ;
    public final void rule__AdditiveExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2494:1: ( rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2495:2: rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__0__Impl_in_rule__AdditiveExpression__Group__05106);
            rule__AdditiveExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group__1_in_rule__AdditiveExpression__Group__05109);
            rule__AdditiveExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group__0"


    // $ANTLR start "rule__AdditiveExpression__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2502:1: rule__AdditiveExpression__Group__0__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_0 ) ) ;
    public final void rule__AdditiveExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2506:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2507:1: ( ( rule__AdditiveExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2507:1: ( ( rule__AdditiveExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2508:1: ( rule__AdditiveExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2509:1: ( rule__AdditiveExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2509:2: rule__AdditiveExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_0_in_rule__AdditiveExpression__Group__0__Impl5136);
            rule__AdditiveExpression__ParametersAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group__0__Impl"


    // $ANTLR start "rule__AdditiveExpression__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2519:1: rule__AdditiveExpression__Group__1 : rule__AdditiveExpression__Group__1__Impl ;
    public final void rule__AdditiveExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2523:1: ( rule__AdditiveExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2524:2: rule__AdditiveExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__1__Impl_in_rule__AdditiveExpression__Group__15166);
            rule__AdditiveExpression__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group__1"


    // $ANTLR start "rule__AdditiveExpression__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2530:1: rule__AdditiveExpression__Group__1__Impl : ( ( rule__AdditiveExpression__Alternatives_1 )* ) ;
    public final void rule__AdditiveExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2534:1: ( ( ( rule__AdditiveExpression__Alternatives_1 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2535:1: ( ( rule__AdditiveExpression__Alternatives_1 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2535:1: ( ( rule__AdditiveExpression__Alternatives_1 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2536:1: ( rule__AdditiveExpression__Alternatives_1 )*
            {
             before(grammarAccess.getAdditiveExpressionAccess().getAlternatives_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2537:1: ( rule__AdditiveExpression__Alternatives_1 )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>=28 && LA22_0<=29)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2537:2: rule__AdditiveExpression__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_rule__AdditiveExpression__Alternatives_1_in_rule__AdditiveExpression__Group__1__Impl5193);
            	    rule__AdditiveExpression__Alternatives_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

             after(grammarAccess.getAdditiveExpressionAccess().getAlternatives_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group__1__Impl"


    // $ANTLR start "rule__AdditiveExpression__Group_1_0__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2551:1: rule__AdditiveExpression__Group_1_0__0 : rule__AdditiveExpression__Group_1_0__0__Impl rule__AdditiveExpression__Group_1_0__1 ;
    public final void rule__AdditiveExpression__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2555:1: ( rule__AdditiveExpression__Group_1_0__0__Impl rule__AdditiveExpression__Group_1_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2556:2: rule__AdditiveExpression__Group_1_0__0__Impl rule__AdditiveExpression__Group_1_0__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_0__0__Impl_in_rule__AdditiveExpression__Group_1_0__05228);
            rule__AdditiveExpression__Group_1_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_0__1_in_rule__AdditiveExpression__Group_1_0__05231);
            rule__AdditiveExpression__Group_1_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group_1_0__0"


    // $ANTLR start "rule__AdditiveExpression__Group_1_0__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2563:1: rule__AdditiveExpression__Group_1_0__0__Impl : ( '+' ) ;
    public final void rule__AdditiveExpression__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2567:1: ( ( '+' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2568:1: ( '+' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2568:1: ( '+' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2569:1: '+'
            {
             before(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0()); 
            match(input,28,FOLLOW_28_in_rule__AdditiveExpression__Group_1_0__0__Impl5259); 
             after(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group_1_0__0__Impl"


    // $ANTLR start "rule__AdditiveExpression__Group_1_0__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2582:1: rule__AdditiveExpression__Group_1_0__1 : rule__AdditiveExpression__Group_1_0__1__Impl ;
    public final void rule__AdditiveExpression__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2586:1: ( rule__AdditiveExpression__Group_1_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2587:2: rule__AdditiveExpression__Group_1_0__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_0__1__Impl_in_rule__AdditiveExpression__Group_1_0__15290);
            rule__AdditiveExpression__Group_1_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group_1_0__1"


    // $ANTLR start "rule__AdditiveExpression__Group_1_0__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2593:1: rule__AdditiveExpression__Group_1_0__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_1_0_1 ) ) ;
    public final void rule__AdditiveExpression__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2597:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_1_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2598:1: ( ( rule__AdditiveExpression__ParametersAssignment_1_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2598:1: ( ( rule__AdditiveExpression__ParametersAssignment_1_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2599:1: ( rule__AdditiveExpression__ParametersAssignment_1_0_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2600:1: ( rule__AdditiveExpression__ParametersAssignment_1_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2600:2: rule__AdditiveExpression__ParametersAssignment_1_0_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_0_1_in_rule__AdditiveExpression__Group_1_0__1__Impl5317);
            rule__AdditiveExpression__ParametersAssignment_1_0_1();

            state._fsp--;


            }

             after(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group_1_0__1__Impl"


    // $ANTLR start "rule__AdditiveExpression__Group_1_1__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2614:1: rule__AdditiveExpression__Group_1_1__0 : rule__AdditiveExpression__Group_1_1__0__Impl rule__AdditiveExpression__Group_1_1__1 ;
    public final void rule__AdditiveExpression__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2618:1: ( rule__AdditiveExpression__Group_1_1__0__Impl rule__AdditiveExpression__Group_1_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2619:2: rule__AdditiveExpression__Group_1_1__0__Impl rule__AdditiveExpression__Group_1_1__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_1__0__Impl_in_rule__AdditiveExpression__Group_1_1__05351);
            rule__AdditiveExpression__Group_1_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_1__1_in_rule__AdditiveExpression__Group_1_1__05354);
            rule__AdditiveExpression__Group_1_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group_1_1__0"


    // $ANTLR start "rule__AdditiveExpression__Group_1_1__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2626:1: rule__AdditiveExpression__Group_1_1__0__Impl : ( '-' ) ;
    public final void rule__AdditiveExpression__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2630:1: ( ( '-' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2631:1: ( '-' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2631:1: ( '-' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2632:1: '-'
            {
             before(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0()); 
            match(input,29,FOLLOW_29_in_rule__AdditiveExpression__Group_1_1__0__Impl5382); 
             after(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group_1_1__0__Impl"


    // $ANTLR start "rule__AdditiveExpression__Group_1_1__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2645:1: rule__AdditiveExpression__Group_1_1__1 : rule__AdditiveExpression__Group_1_1__1__Impl ;
    public final void rule__AdditiveExpression__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2649:1: ( rule__AdditiveExpression__Group_1_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2650:2: rule__AdditiveExpression__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_1__1__Impl_in_rule__AdditiveExpression__Group_1_1__15413);
            rule__AdditiveExpression__Group_1_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group_1_1__1"


    // $ANTLR start "rule__AdditiveExpression__Group_1_1__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2656:1: rule__AdditiveExpression__Group_1_1__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_1_1_1 ) ) ;
    public final void rule__AdditiveExpression__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2660:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_1_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2661:1: ( ( rule__AdditiveExpression__ParametersAssignment_1_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2661:1: ( ( rule__AdditiveExpression__ParametersAssignment_1_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2662:1: ( rule__AdditiveExpression__ParametersAssignment_1_1_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2663:1: ( rule__AdditiveExpression__ParametersAssignment_1_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2663:2: rule__AdditiveExpression__ParametersAssignment_1_1_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_1_1_in_rule__AdditiveExpression__Group_1_1__1__Impl5440);
            rule__AdditiveExpression__ParametersAssignment_1_1_1();

            state._fsp--;


            }

             after(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__Group_1_1__1__Impl"


    // $ANTLR start "rule__MultiplicativeExpression__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2677:1: rule__MultiplicativeExpression__Group__0 : rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1 ;
    public final void rule__MultiplicativeExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2681:1: ( rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2682:2: rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__0__Impl_in_rule__MultiplicativeExpression__Group__05474);
            rule__MultiplicativeExpression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__1_in_rule__MultiplicativeExpression__Group__05477);
            rule__MultiplicativeExpression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group__0"


    // $ANTLR start "rule__MultiplicativeExpression__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2689:1: rule__MultiplicativeExpression__Group__0__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2693:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2694:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2694:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2695:1: ( rule__MultiplicativeExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2696:1: ( rule__MultiplicativeExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2696:2: rule__MultiplicativeExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_0_in_rule__MultiplicativeExpression__Group__0__Impl5504);
            rule__MultiplicativeExpression__ParametersAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group__0__Impl"


    // $ANTLR start "rule__MultiplicativeExpression__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2706:1: rule__MultiplicativeExpression__Group__1 : rule__MultiplicativeExpression__Group__1__Impl ;
    public final void rule__MultiplicativeExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2710:1: ( rule__MultiplicativeExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2711:2: rule__MultiplicativeExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__1__Impl_in_rule__MultiplicativeExpression__Group__15534);
            rule__MultiplicativeExpression__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group__1"


    // $ANTLR start "rule__MultiplicativeExpression__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2717:1: rule__MultiplicativeExpression__Group__1__Impl : ( ( rule__MultiplicativeExpression__Alternatives_1 )* ) ;
    public final void rule__MultiplicativeExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2721:1: ( ( ( rule__MultiplicativeExpression__Alternatives_1 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2722:1: ( ( rule__MultiplicativeExpression__Alternatives_1 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2722:1: ( ( rule__MultiplicativeExpression__Alternatives_1 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2723:1: ( rule__MultiplicativeExpression__Alternatives_1 )*
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getAlternatives_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2724:1: ( rule__MultiplicativeExpression__Alternatives_1 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==RULE_TML_SEPARATOR||LA23_0==36) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2724:2: rule__MultiplicativeExpression__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_rule__MultiplicativeExpression__Alternatives_1_in_rule__MultiplicativeExpression__Group__1__Impl5561);
            	    rule__MultiplicativeExpression__Alternatives_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

             after(grammarAccess.getMultiplicativeExpressionAccess().getAlternatives_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group__1__Impl"


    // $ANTLR start "rule__MultiplicativeExpression__Group_1_0__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2738:1: rule__MultiplicativeExpression__Group_1_0__0 : rule__MultiplicativeExpression__Group_1_0__0__Impl rule__MultiplicativeExpression__Group_1_0__1 ;
    public final void rule__MultiplicativeExpression__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2742:1: ( rule__MultiplicativeExpression__Group_1_0__0__Impl rule__MultiplicativeExpression__Group_1_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2743:2: rule__MultiplicativeExpression__Group_1_0__0__Impl rule__MultiplicativeExpression__Group_1_0__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_0__0__Impl_in_rule__MultiplicativeExpression__Group_1_0__05596);
            rule__MultiplicativeExpression__Group_1_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_0__1_in_rule__MultiplicativeExpression__Group_1_0__05599);
            rule__MultiplicativeExpression__Group_1_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group_1_0__0"


    // $ANTLR start "rule__MultiplicativeExpression__Group_1_0__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2750:1: rule__MultiplicativeExpression__Group_1_0__0__Impl : ( ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2754:1: ( ( ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2755:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2755:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2756:1: ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2757:1: ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2757:2: rule__MultiplicativeExpression__OperationsAssignment_1_0_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_1_0_0_in_rule__MultiplicativeExpression__Group_1_0__0__Impl5626);
            rule__MultiplicativeExpression__OperationsAssignment_1_0_0();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group_1_0__0__Impl"


    // $ANTLR start "rule__MultiplicativeExpression__Group_1_0__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2767:1: rule__MultiplicativeExpression__Group_1_0__1 : rule__MultiplicativeExpression__Group_1_0__1__Impl ;
    public final void rule__MultiplicativeExpression__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2771:1: ( rule__MultiplicativeExpression__Group_1_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2772:2: rule__MultiplicativeExpression__Group_1_0__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_0__1__Impl_in_rule__MultiplicativeExpression__Group_1_0__15656);
            rule__MultiplicativeExpression__Group_1_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group_1_0__1"


    // $ANTLR start "rule__MultiplicativeExpression__Group_1_0__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2778:1: rule__MultiplicativeExpression__Group_1_0__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2782:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2783:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2783:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2784:1: ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2785:1: ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2785:2: rule__MultiplicativeExpression__ParametersAssignment_1_0_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_0_1_in_rule__MultiplicativeExpression__Group_1_0__1__Impl5683);
            rule__MultiplicativeExpression__ParametersAssignment_1_0_1();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group_1_0__1__Impl"


    // $ANTLR start "rule__MultiplicativeExpression__Group_1_1__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2799:1: rule__MultiplicativeExpression__Group_1_1__0 : rule__MultiplicativeExpression__Group_1_1__0__Impl rule__MultiplicativeExpression__Group_1_1__1 ;
    public final void rule__MultiplicativeExpression__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2803:1: ( rule__MultiplicativeExpression__Group_1_1__0__Impl rule__MultiplicativeExpression__Group_1_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2804:2: rule__MultiplicativeExpression__Group_1_1__0__Impl rule__MultiplicativeExpression__Group_1_1__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_1__0__Impl_in_rule__MultiplicativeExpression__Group_1_1__05717);
            rule__MultiplicativeExpression__Group_1_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_1__1_in_rule__MultiplicativeExpression__Group_1_1__05720);
            rule__MultiplicativeExpression__Group_1_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group_1_1__0"


    // $ANTLR start "rule__MultiplicativeExpression__Group_1_1__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2811:1: rule__MultiplicativeExpression__Group_1_1__0__Impl : ( ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2815:1: ( ( ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2816:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2816:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2817:1: ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_1_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2818:1: ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2818:2: rule__MultiplicativeExpression__OperationsAssignment_1_1_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_1_1_0_in_rule__MultiplicativeExpression__Group_1_1__0__Impl5747);
            rule__MultiplicativeExpression__OperationsAssignment_1_1_0();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group_1_1__0__Impl"


    // $ANTLR start "rule__MultiplicativeExpression__Group_1_1__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2828:1: rule__MultiplicativeExpression__Group_1_1__1 : rule__MultiplicativeExpression__Group_1_1__1__Impl ;
    public final void rule__MultiplicativeExpression__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2832:1: ( rule__MultiplicativeExpression__Group_1_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2833:2: rule__MultiplicativeExpression__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_1__1__Impl_in_rule__MultiplicativeExpression__Group_1_1__15777);
            rule__MultiplicativeExpression__Group_1_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group_1_1__1"


    // $ANTLR start "rule__MultiplicativeExpression__Group_1_1__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2839:1: rule__MultiplicativeExpression__Group_1_1__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2843:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2844:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2844:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2845:1: ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2846:1: ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2846:2: rule__MultiplicativeExpression__ParametersAssignment_1_1_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_1_1_in_rule__MultiplicativeExpression__Group_1_1__1__Impl5804);
            rule__MultiplicativeExpression__ParametersAssignment_1_1_1();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__Group_1_1__1__Impl"


    // $ANTLR start "rule__UnaryExpression__Group_0__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2860:1: rule__UnaryExpression__Group_0__0 : rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1 ;
    public final void rule__UnaryExpression__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2864:1: ( rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2865:2: rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__0__Impl_in_rule__UnaryExpression__Group_0__05838);
            rule__UnaryExpression__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__1_in_rule__UnaryExpression__Group_0__05841);
            rule__UnaryExpression__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryExpression__Group_0__0"


    // $ANTLR start "rule__UnaryExpression__Group_0__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2872:1: rule__UnaryExpression__Group_0__0__Impl : ( ( rule__UnaryExpression__OperationsAssignment_0_0 ) ) ;
    public final void rule__UnaryExpression__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2876:1: ( ( ( rule__UnaryExpression__OperationsAssignment_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2877:1: ( ( rule__UnaryExpression__OperationsAssignment_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2877:1: ( ( rule__UnaryExpression__OperationsAssignment_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2878:1: ( rule__UnaryExpression__OperationsAssignment_0_0 )
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsAssignment_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2879:1: ( rule__UnaryExpression__OperationsAssignment_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2879:2: rule__UnaryExpression__OperationsAssignment_0_0
            {
            pushFollow(FOLLOW_rule__UnaryExpression__OperationsAssignment_0_0_in_rule__UnaryExpression__Group_0__0__Impl5868);
            rule__UnaryExpression__OperationsAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getUnaryExpressionAccess().getOperationsAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryExpression__Group_0__0__Impl"


    // $ANTLR start "rule__UnaryExpression__Group_0__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2889:1: rule__UnaryExpression__Group_0__1 : rule__UnaryExpression__Group_0__1__Impl ;
    public final void rule__UnaryExpression__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2893:1: ( rule__UnaryExpression__Group_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2894:2: rule__UnaryExpression__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__1__Impl_in_rule__UnaryExpression__Group_0__15898);
            rule__UnaryExpression__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryExpression__Group_0__1"


    // $ANTLR start "rule__UnaryExpression__Group_0__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2900:1: rule__UnaryExpression__Group_0__1__Impl : ( ( rule__UnaryExpression__ParametersAssignment_0_1 ) ) ;
    public final void rule__UnaryExpression__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2904:1: ( ( ( rule__UnaryExpression__ParametersAssignment_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2905:1: ( ( rule__UnaryExpression__ParametersAssignment_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2905:1: ( ( rule__UnaryExpression__ParametersAssignment_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2906:1: ( rule__UnaryExpression__ParametersAssignment_0_1 )
            {
             before(grammarAccess.getUnaryExpressionAccess().getParametersAssignment_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2907:1: ( rule__UnaryExpression__ParametersAssignment_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2907:2: rule__UnaryExpression__ParametersAssignment_0_1
            {
            pushFollow(FOLLOW_rule__UnaryExpression__ParametersAssignment_0_1_in_rule__UnaryExpression__Group_0__1__Impl5925);
            rule__UnaryExpression__ParametersAssignment_0_1();

            state._fsp--;


            }

             after(grammarAccess.getUnaryExpressionAccess().getParametersAssignment_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryExpression__Group_0__1__Impl"


    // $ANTLR start "rule__PrimaryExpression__Group_1__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2921:1: rule__PrimaryExpression__Group_1__0 : rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1 ;
    public final void rule__PrimaryExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2925:1: ( rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2926:2: rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__0__Impl_in_rule__PrimaryExpression__Group_1__05959);
            rule__PrimaryExpression__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__1_in_rule__PrimaryExpression__Group_1__05962);
            rule__PrimaryExpression__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__Group_1__0"


    // $ANTLR start "rule__PrimaryExpression__Group_1__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2933:1: rule__PrimaryExpression__Group_1__0__Impl : ( '(' ) ;
    public final void rule__PrimaryExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2937:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2938:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2938:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2939:1: '('
            {
             before(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0()); 
            match(input,25,FOLLOW_25_in_rule__PrimaryExpression__Group_1__0__Impl5990); 
             after(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__Group_1__0__Impl"


    // $ANTLR start "rule__PrimaryExpression__Group_1__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2952:1: rule__PrimaryExpression__Group_1__1 : rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2 ;
    public final void rule__PrimaryExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2956:1: ( rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2957:2: rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__1__Impl_in_rule__PrimaryExpression__Group_1__16021);
            rule__PrimaryExpression__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__2_in_rule__PrimaryExpression__Group_1__16024);
            rule__PrimaryExpression__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__Group_1__1"


    // $ANTLR start "rule__PrimaryExpression__Group_1__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2964:1: rule__PrimaryExpression__Group_1__1__Impl : ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) ) ;
    public final void rule__PrimaryExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2968:1: ( ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2969:1: ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2969:1: ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2970:1: ( rule__PrimaryExpression__ParametersAssignment_1_1 )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2971:1: ( rule__PrimaryExpression__ParametersAssignment_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2971:2: rule__PrimaryExpression__ParametersAssignment_1_1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__ParametersAssignment_1_1_in_rule__PrimaryExpression__Group_1__1__Impl6051);
            rule__PrimaryExpression__ParametersAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__Group_1__1__Impl"


    // $ANTLR start "rule__PrimaryExpression__Group_1__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2981:1: rule__PrimaryExpression__Group_1__2 : rule__PrimaryExpression__Group_1__2__Impl ;
    public final void rule__PrimaryExpression__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2985:1: ( rule__PrimaryExpression__Group_1__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2986:2: rule__PrimaryExpression__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__2__Impl_in_rule__PrimaryExpression__Group_1__26081);
            rule__PrimaryExpression__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__Group_1__2"


    // $ANTLR start "rule__PrimaryExpression__Group_1__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2992:1: rule__PrimaryExpression__Group_1__2__Impl : ( ')' ) ;
    public final void rule__PrimaryExpression__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2996:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2997:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2997:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2998:1: ')'
            {
             before(grammarAccess.getPrimaryExpressionAccess().getRightParenthesisKeyword_1_2()); 
            match(input,26,FOLLOW_26_in_rule__PrimaryExpression__Group_1__2__Impl6109); 
             after(grammarAccess.getPrimaryExpressionAccess().getRightParenthesisKeyword_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__Group_1__2__Impl"


    // $ANTLR start "rule__FunctionCall__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3017:1: rule__FunctionCall__Group__0 : rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1 ;
    public final void rule__FunctionCall__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3021:1: ( rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3022:2: rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__0__Impl_in_rule__FunctionCall__Group__06146);
            rule__FunctionCall__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__1_in_rule__FunctionCall__Group__06149);
            rule__FunctionCall__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__0"


    // $ANTLR start "rule__FunctionCall__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3029:1: rule__FunctionCall__Group__0__Impl : ( ( rule__FunctionCall__NameAssignment_0 ) ) ;
    public final void rule__FunctionCall__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3033:1: ( ( ( rule__FunctionCall__NameAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3034:1: ( ( rule__FunctionCall__NameAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3034:1: ( ( rule__FunctionCall__NameAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3035:1: ( rule__FunctionCall__NameAssignment_0 )
            {
             before(grammarAccess.getFunctionCallAccess().getNameAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3036:1: ( rule__FunctionCall__NameAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3036:2: rule__FunctionCall__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__FunctionCall__NameAssignment_0_in_rule__FunctionCall__Group__0__Impl6176);
            rule__FunctionCall__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getFunctionCallAccess().getNameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__0__Impl"


    // $ANTLR start "rule__FunctionCall__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3046:1: rule__FunctionCall__Group__1 : rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2 ;
    public final void rule__FunctionCall__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3050:1: ( rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3051:2: rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__1__Impl_in_rule__FunctionCall__Group__16206);
            rule__FunctionCall__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__2_in_rule__FunctionCall__Group__16209);
            rule__FunctionCall__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__1"


    // $ANTLR start "rule__FunctionCall__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3058:1: rule__FunctionCall__Group__1__Impl : ( '(' ) ;
    public final void rule__FunctionCall__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3062:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3063:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3063:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3064:1: '('
            {
             before(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1()); 
            match(input,25,FOLLOW_25_in_rule__FunctionCall__Group__1__Impl6237); 
             after(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__1__Impl"


    // $ANTLR start "rule__FunctionCall__Group__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3077:1: rule__FunctionCall__Group__2 : rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3 ;
    public final void rule__FunctionCall__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3081:1: ( rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3082:2: rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__2__Impl_in_rule__FunctionCall__Group__26268);
            rule__FunctionCall__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__3_in_rule__FunctionCall__Group__26271);
            rule__FunctionCall__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__2"


    // $ANTLR start "rule__FunctionCall__Group__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3089:1: rule__FunctionCall__Group__2__Impl : ( ( rule__FunctionCall__ParametersAssignment_2 )? ) ;
    public final void rule__FunctionCall__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3093:1: ( ( ( rule__FunctionCall__ParametersAssignment_2 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3094:1: ( ( rule__FunctionCall__ParametersAssignment_2 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3094:1: ( ( rule__FunctionCall__ParametersAssignment_2 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3095:1: ( rule__FunctionCall__ParametersAssignment_2 )?
            {
             before(grammarAccess.getFunctionCallAccess().getParametersAssignment_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3096:1: ( rule__FunctionCall__ParametersAssignment_2 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RULE_ID||LA24_0==RULE_SQBRACKET_OPEN||(LA24_0>=RULE_TML_EXISTS && LA24_0<=RULE_NUMBER)||LA24_0==RULE_DOLLAR||(LA24_0>=RULE_LITERALSTRING && LA24_0<=RULE_FALSE)||LA24_0==25||(LA24_0>=37 && LA24_0<=38)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3096:2: rule__FunctionCall__ParametersAssignment_2
                    {
                    pushFollow(FOLLOW_rule__FunctionCall__ParametersAssignment_2_in_rule__FunctionCall__Group__2__Impl6298);
                    rule__FunctionCall__ParametersAssignment_2();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getFunctionCallAccess().getParametersAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__2__Impl"


    // $ANTLR start "rule__FunctionCall__Group__3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3106:1: rule__FunctionCall__Group__3 : rule__FunctionCall__Group__3__Impl rule__FunctionCall__Group__4 ;
    public final void rule__FunctionCall__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3110:1: ( rule__FunctionCall__Group__3__Impl rule__FunctionCall__Group__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3111:2: rule__FunctionCall__Group__3__Impl rule__FunctionCall__Group__4
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__3__Impl_in_rule__FunctionCall__Group__36329);
            rule__FunctionCall__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__4_in_rule__FunctionCall__Group__36332);
            rule__FunctionCall__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__3"


    // $ANTLR start "rule__FunctionCall__Group__3__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3118:1: rule__FunctionCall__Group__3__Impl : ( ( rule__FunctionCall__Group_3__0 )* ) ;
    public final void rule__FunctionCall__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3122:1: ( ( ( rule__FunctionCall__Group_3__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3123:1: ( ( rule__FunctionCall__Group_3__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3123:1: ( ( rule__FunctionCall__Group_3__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3124:1: ( rule__FunctionCall__Group_3__0 )*
            {
             before(grammarAccess.getFunctionCallAccess().getGroup_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3125:1: ( rule__FunctionCall__Group_3__0 )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==27) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3125:2: rule__FunctionCall__Group_3__0
            	    {
            	    pushFollow(FOLLOW_rule__FunctionCall__Group_3__0_in_rule__FunctionCall__Group__3__Impl6359);
            	    rule__FunctionCall__Group_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

             after(grammarAccess.getFunctionCallAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__3__Impl"


    // $ANTLR start "rule__FunctionCall__Group__4"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3135:1: rule__FunctionCall__Group__4 : rule__FunctionCall__Group__4__Impl ;
    public final void rule__FunctionCall__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3139:1: ( rule__FunctionCall__Group__4__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3140:2: rule__FunctionCall__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__4__Impl_in_rule__FunctionCall__Group__46390);
            rule__FunctionCall__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__4"


    // $ANTLR start "rule__FunctionCall__Group__4__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3146:1: rule__FunctionCall__Group__4__Impl : ( ')' ) ;
    public final void rule__FunctionCall__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3150:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3151:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3151:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3152:1: ')'
            {
             before(grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_4()); 
            match(input,26,FOLLOW_26_in_rule__FunctionCall__Group__4__Impl6418); 
             after(grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group__4__Impl"


    // $ANTLR start "rule__FunctionCall__Group_3__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3175:1: rule__FunctionCall__Group_3__0 : rule__FunctionCall__Group_3__0__Impl rule__FunctionCall__Group_3__1 ;
    public final void rule__FunctionCall__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3179:1: ( rule__FunctionCall__Group_3__0__Impl rule__FunctionCall__Group_3__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3180:2: rule__FunctionCall__Group_3__0__Impl rule__FunctionCall__Group_3__1
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group_3__0__Impl_in_rule__FunctionCall__Group_3__06459);
            rule__FunctionCall__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group_3__1_in_rule__FunctionCall__Group_3__06462);
            rule__FunctionCall__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group_3__0"


    // $ANTLR start "rule__FunctionCall__Group_3__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3187:1: rule__FunctionCall__Group_3__0__Impl : ( ',' ) ;
    public final void rule__FunctionCall__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3191:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3192:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3192:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3193:1: ','
            {
             before(grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0()); 
            match(input,27,FOLLOW_27_in_rule__FunctionCall__Group_3__0__Impl6490); 
             after(grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group_3__0__Impl"


    // $ANTLR start "rule__FunctionCall__Group_3__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3206:1: rule__FunctionCall__Group_3__1 : rule__FunctionCall__Group_3__1__Impl ;
    public final void rule__FunctionCall__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3210:1: ( rule__FunctionCall__Group_3__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3211:2: rule__FunctionCall__Group_3__1__Impl
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group_3__1__Impl_in_rule__FunctionCall__Group_3__16521);
            rule__FunctionCall__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group_3__1"


    // $ANTLR start "rule__FunctionCall__Group_3__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3217:1: rule__FunctionCall__Group_3__1__Impl : ( ( rule__FunctionCall__ParametersAssignment_3_1 ) ) ;
    public final void rule__FunctionCall__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3221:1: ( ( ( rule__FunctionCall__ParametersAssignment_3_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3222:1: ( ( rule__FunctionCall__ParametersAssignment_3_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3222:1: ( ( rule__FunctionCall__ParametersAssignment_3_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3223:1: ( rule__FunctionCall__ParametersAssignment_3_1 )
            {
             before(grammarAccess.getFunctionCallAccess().getParametersAssignment_3_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3224:1: ( rule__FunctionCall__ParametersAssignment_3_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3224:2: rule__FunctionCall__ParametersAssignment_3_1
            {
            pushFollow(FOLLOW_rule__FunctionCall__ParametersAssignment_3_1_in_rule__FunctionCall__Group_3__1__Impl6548);
            rule__FunctionCall__ParametersAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getFunctionCallAccess().getParametersAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__Group_3__1__Impl"


    // $ANTLR start "rule__DateLiteral__Group__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3238:1: rule__DateLiteral__Group__0 : rule__DateLiteral__Group__0__Impl rule__DateLiteral__Group__1 ;
    public final void rule__DateLiteral__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3242:1: ( rule__DateLiteral__Group__0__Impl rule__DateLiteral__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3243:2: rule__DateLiteral__Group__0__Impl rule__DateLiteral__Group__1
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__0__Impl_in_rule__DateLiteral__Group__06582);
            rule__DateLiteral__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__1_in_rule__DateLiteral__Group__06585);
            rule__DateLiteral__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__0"


    // $ANTLR start "rule__DateLiteral__Group__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3250:1: rule__DateLiteral__Group__0__Impl : ( () ) ;
    public final void rule__DateLiteral__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3254:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3255:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3255:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3256:1: ()
            {
             before(grammarAccess.getDateLiteralAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3257:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3259:1: 
            {
            }

             after(grammarAccess.getDateLiteralAccess().getExpressionAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__0__Impl"


    // $ANTLR start "rule__DateLiteral__Group__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3269:1: rule__DateLiteral__Group__1 : rule__DateLiteral__Group__1__Impl rule__DateLiteral__Group__2 ;
    public final void rule__DateLiteral__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3273:1: ( rule__DateLiteral__Group__1__Impl rule__DateLiteral__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3274:2: rule__DateLiteral__Group__1__Impl rule__DateLiteral__Group__2
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__1__Impl_in_rule__DateLiteral__Group__16643);
            rule__DateLiteral__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__2_in_rule__DateLiteral__Group__16646);
            rule__DateLiteral__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__1"


    // $ANTLR start "rule__DateLiteral__Group__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3281:1: rule__DateLiteral__Group__1__Impl : ( RULE_NUMBER ) ;
    public final void rule__DateLiteral__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3285:1: ( ( RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3286:1: ( RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3286:1: ( RULE_NUMBER )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3287:1: RULE_NUMBER
            {
             before(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_1()); 
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__1__Impl6673); 
             after(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__1__Impl"


    // $ANTLR start "rule__DateLiteral__Group__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3298:1: rule__DateLiteral__Group__2 : rule__DateLiteral__Group__2__Impl rule__DateLiteral__Group__3 ;
    public final void rule__DateLiteral__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3302:1: ( rule__DateLiteral__Group__2__Impl rule__DateLiteral__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3303:2: rule__DateLiteral__Group__2__Impl rule__DateLiteral__Group__3
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__2__Impl_in_rule__DateLiteral__Group__26702);
            rule__DateLiteral__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__3_in_rule__DateLiteral__Group__26705);
            rule__DateLiteral__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__2"


    // $ANTLR start "rule__DateLiteral__Group__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3310:1: rule__DateLiteral__Group__2__Impl : ( '#' ) ;
    public final void rule__DateLiteral__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3314:1: ( ( '#' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3315:1: ( '#' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3315:1: ( '#' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3316:1: '#'
            {
             before(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_2()); 
            match(input,30,FOLLOW_30_in_rule__DateLiteral__Group__2__Impl6733); 
             after(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__2__Impl"


    // $ANTLR start "rule__DateLiteral__Group__3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3329:1: rule__DateLiteral__Group__3 : rule__DateLiteral__Group__3__Impl rule__DateLiteral__Group__4 ;
    public final void rule__DateLiteral__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3333:1: ( rule__DateLiteral__Group__3__Impl rule__DateLiteral__Group__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3334:2: rule__DateLiteral__Group__3__Impl rule__DateLiteral__Group__4
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__3__Impl_in_rule__DateLiteral__Group__36764);
            rule__DateLiteral__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__4_in_rule__DateLiteral__Group__36767);
            rule__DateLiteral__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__3"


    // $ANTLR start "rule__DateLiteral__Group__3__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3341:1: rule__DateLiteral__Group__3__Impl : ( RULE_NUMBER ) ;
    public final void rule__DateLiteral__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3345:1: ( ( RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3346:1: ( RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3346:1: ( RULE_NUMBER )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3347:1: RULE_NUMBER
            {
             before(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_3()); 
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__3__Impl6794); 
             after(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__3__Impl"


    // $ANTLR start "rule__DateLiteral__Group__4"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3358:1: rule__DateLiteral__Group__4 : rule__DateLiteral__Group__4__Impl rule__DateLiteral__Group__5 ;
    public final void rule__DateLiteral__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3362:1: ( rule__DateLiteral__Group__4__Impl rule__DateLiteral__Group__5 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3363:2: rule__DateLiteral__Group__4__Impl rule__DateLiteral__Group__5
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__4__Impl_in_rule__DateLiteral__Group__46823);
            rule__DateLiteral__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__5_in_rule__DateLiteral__Group__46826);
            rule__DateLiteral__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__4"


    // $ANTLR start "rule__DateLiteral__Group__4__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3370:1: rule__DateLiteral__Group__4__Impl : ( '#' ) ;
    public final void rule__DateLiteral__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3374:1: ( ( '#' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3375:1: ( '#' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3375:1: ( '#' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3376:1: '#'
            {
             before(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_4()); 
            match(input,30,FOLLOW_30_in_rule__DateLiteral__Group__4__Impl6854); 
             after(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__4__Impl"


    // $ANTLR start "rule__DateLiteral__Group__5"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3389:1: rule__DateLiteral__Group__5 : rule__DateLiteral__Group__5__Impl rule__DateLiteral__Group__6 ;
    public final void rule__DateLiteral__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3393:1: ( rule__DateLiteral__Group__5__Impl rule__DateLiteral__Group__6 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3394:2: rule__DateLiteral__Group__5__Impl rule__DateLiteral__Group__6
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__5__Impl_in_rule__DateLiteral__Group__56885);
            rule__DateLiteral__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__6_in_rule__DateLiteral__Group__56888);
            rule__DateLiteral__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__5"


    // $ANTLR start "rule__DateLiteral__Group__5__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3401:1: rule__DateLiteral__Group__5__Impl : ( RULE_NUMBER ) ;
    public final void rule__DateLiteral__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3405:1: ( ( RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3406:1: ( RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3406:1: ( RULE_NUMBER )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3407:1: RULE_NUMBER
            {
             before(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_5()); 
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__5__Impl6915); 
             after(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__5__Impl"


    // $ANTLR start "rule__DateLiteral__Group__6"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3418:1: rule__DateLiteral__Group__6 : rule__DateLiteral__Group__6__Impl rule__DateLiteral__Group__7 ;
    public final void rule__DateLiteral__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3422:1: ( rule__DateLiteral__Group__6__Impl rule__DateLiteral__Group__7 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3423:2: rule__DateLiteral__Group__6__Impl rule__DateLiteral__Group__7
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__6__Impl_in_rule__DateLiteral__Group__66944);
            rule__DateLiteral__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__7_in_rule__DateLiteral__Group__66947);
            rule__DateLiteral__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__6"


    // $ANTLR start "rule__DateLiteral__Group__6__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3430:1: rule__DateLiteral__Group__6__Impl : ( '#' ) ;
    public final void rule__DateLiteral__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3434:1: ( ( '#' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3435:1: ( '#' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3435:1: ( '#' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3436:1: '#'
            {
             before(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_6()); 
            match(input,30,FOLLOW_30_in_rule__DateLiteral__Group__6__Impl6975); 
             after(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__6__Impl"


    // $ANTLR start "rule__DateLiteral__Group__7"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3449:1: rule__DateLiteral__Group__7 : rule__DateLiteral__Group__7__Impl rule__DateLiteral__Group__8 ;
    public final void rule__DateLiteral__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3453:1: ( rule__DateLiteral__Group__7__Impl rule__DateLiteral__Group__8 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3454:2: rule__DateLiteral__Group__7__Impl rule__DateLiteral__Group__8
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__7__Impl_in_rule__DateLiteral__Group__77006);
            rule__DateLiteral__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__8_in_rule__DateLiteral__Group__77009);
            rule__DateLiteral__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__7"


    // $ANTLR start "rule__DateLiteral__Group__7__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3461:1: rule__DateLiteral__Group__7__Impl : ( RULE_NUMBER ) ;
    public final void rule__DateLiteral__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3465:1: ( ( RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3466:1: ( RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3466:1: ( RULE_NUMBER )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3467:1: RULE_NUMBER
            {
             before(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_7()); 
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__7__Impl7036); 
             after(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__7__Impl"


    // $ANTLR start "rule__DateLiteral__Group__8"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3478:1: rule__DateLiteral__Group__8 : rule__DateLiteral__Group__8__Impl rule__DateLiteral__Group__9 ;
    public final void rule__DateLiteral__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3482:1: ( rule__DateLiteral__Group__8__Impl rule__DateLiteral__Group__9 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3483:2: rule__DateLiteral__Group__8__Impl rule__DateLiteral__Group__9
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__8__Impl_in_rule__DateLiteral__Group__87065);
            rule__DateLiteral__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__9_in_rule__DateLiteral__Group__87068);
            rule__DateLiteral__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__8"


    // $ANTLR start "rule__DateLiteral__Group__8__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3490:1: rule__DateLiteral__Group__8__Impl : ( '#' ) ;
    public final void rule__DateLiteral__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3494:1: ( ( '#' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3495:1: ( '#' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3495:1: ( '#' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3496:1: '#'
            {
             before(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_8()); 
            match(input,30,FOLLOW_30_in_rule__DateLiteral__Group__8__Impl7096); 
             after(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__8__Impl"


    // $ANTLR start "rule__DateLiteral__Group__9"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3509:1: rule__DateLiteral__Group__9 : rule__DateLiteral__Group__9__Impl rule__DateLiteral__Group__10 ;
    public final void rule__DateLiteral__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3513:1: ( rule__DateLiteral__Group__9__Impl rule__DateLiteral__Group__10 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3514:2: rule__DateLiteral__Group__9__Impl rule__DateLiteral__Group__10
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__9__Impl_in_rule__DateLiteral__Group__97127);
            rule__DateLiteral__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__10_in_rule__DateLiteral__Group__97130);
            rule__DateLiteral__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__9"


    // $ANTLR start "rule__DateLiteral__Group__9__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3521:1: rule__DateLiteral__Group__9__Impl : ( RULE_NUMBER ) ;
    public final void rule__DateLiteral__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3525:1: ( ( RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3526:1: ( RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3526:1: ( RULE_NUMBER )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3527:1: RULE_NUMBER
            {
             before(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_9()); 
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__9__Impl7157); 
             after(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__9__Impl"


    // $ANTLR start "rule__DateLiteral__Group__10"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3538:1: rule__DateLiteral__Group__10 : rule__DateLiteral__Group__10__Impl rule__DateLiteral__Group__11 ;
    public final void rule__DateLiteral__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3542:1: ( rule__DateLiteral__Group__10__Impl rule__DateLiteral__Group__11 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3543:2: rule__DateLiteral__Group__10__Impl rule__DateLiteral__Group__11
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__10__Impl_in_rule__DateLiteral__Group__107186);
            rule__DateLiteral__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DateLiteral__Group__11_in_rule__DateLiteral__Group__107189);
            rule__DateLiteral__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__10"


    // $ANTLR start "rule__DateLiteral__Group__10__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3550:1: rule__DateLiteral__Group__10__Impl : ( '#' ) ;
    public final void rule__DateLiteral__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3554:1: ( ( '#' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3555:1: ( '#' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3555:1: ( '#' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3556:1: '#'
            {
             before(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_10()); 
            match(input,30,FOLLOW_30_in_rule__DateLiteral__Group__10__Impl7217); 
             after(grammarAccess.getDateLiteralAccess().getNumberSignKeyword_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__10__Impl"


    // $ANTLR start "rule__DateLiteral__Group__11"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3569:1: rule__DateLiteral__Group__11 : rule__DateLiteral__Group__11__Impl ;
    public final void rule__DateLiteral__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3573:1: ( rule__DateLiteral__Group__11__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3574:2: rule__DateLiteral__Group__11__Impl
            {
            pushFollow(FOLLOW_rule__DateLiteral__Group__11__Impl_in_rule__DateLiteral__Group__117248);
            rule__DateLiteral__Group__11__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__11"


    // $ANTLR start "rule__DateLiteral__Group__11__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3580:1: rule__DateLiteral__Group__11__Impl : ( RULE_NUMBER ) ;
    public final void rule__DateLiteral__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3584:1: ( ( RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3585:1: ( RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3585:1: ( RULE_NUMBER )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3586:1: RULE_NUMBER
            {
             before(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_11()); 
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__11__Impl7275); 
             after(grammarAccess.getDateLiteralAccess().getNUMBERTerminalRuleCall_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DateLiteral__Group__11__Impl"


    // $ANTLR start "rule__Literal__Group_0__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3621:1: rule__Literal__Group_0__0 : rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1 ;
    public final void rule__Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3625:1: ( rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3626:2: rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_0__0__Impl_in_rule__Literal__Group_0__07328);
            rule__Literal__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_0__1_in_rule__Literal__Group_0__07331);
            rule__Literal__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_0__0"


    // $ANTLR start "rule__Literal__Group_0__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3633:1: rule__Literal__Group_0__0__Impl : ( () ) ;
    public final void rule__Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3637:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3638:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3638:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3639:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3640:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3642:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_0_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_0__0__Impl"


    // $ANTLR start "rule__Literal__Group_0__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3652:1: rule__Literal__Group_0__1 : rule__Literal__Group_0__1__Impl ;
    public final void rule__Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3656:1: ( rule__Literal__Group_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3657:2: rule__Literal__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_0__1__Impl_in_rule__Literal__Group_0__17389);
            rule__Literal__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_0__1"


    // $ANTLR start "rule__Literal__Group_0__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3663:1: rule__Literal__Group_0__1__Impl : ( RULE_NUMBER ) ;
    public final void rule__Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3667:1: ( ( RULE_NUMBER ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3668:1: ( RULE_NUMBER )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3668:1: ( RULE_NUMBER )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3669:1: RULE_NUMBER
            {
             before(grammarAccess.getLiteralAccess().getNUMBERTerminalRuleCall_0_1()); 
            match(input,RULE_NUMBER,FOLLOW_RULE_NUMBER_in_rule__Literal__Group_0__1__Impl7416); 
             after(grammarAccess.getLiteralAccess().getNUMBERTerminalRuleCall_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_0__1__Impl"


    // $ANTLR start "rule__Literal__Group_2__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3684:1: rule__Literal__Group_2__0 : rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1 ;
    public final void rule__Literal__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3688:1: ( rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3689:2: rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__0__Impl_in_rule__Literal__Group_2__07449);
            rule__Literal__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__1_in_rule__Literal__Group_2__07452);
            rule__Literal__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__0"


    // $ANTLR start "rule__Literal__Group_2__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3696:1: rule__Literal__Group_2__0__Impl : ( ( rule__Literal__OperationsAssignment_2_0 ) ) ;
    public final void rule__Literal__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3700:1: ( ( ( rule__Literal__OperationsAssignment_2_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3701:1: ( ( rule__Literal__OperationsAssignment_2_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3701:1: ( ( rule__Literal__OperationsAssignment_2_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3702:1: ( rule__Literal__OperationsAssignment_2_0 )
            {
             before(grammarAccess.getLiteralAccess().getOperationsAssignment_2_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3703:1: ( rule__Literal__OperationsAssignment_2_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3703:2: rule__Literal__OperationsAssignment_2_0
            {
            pushFollow(FOLLOW_rule__Literal__OperationsAssignment_2_0_in_rule__Literal__Group_2__0__Impl7479);
            rule__Literal__OperationsAssignment_2_0();

            state._fsp--;


            }

             after(grammarAccess.getLiteralAccess().getOperationsAssignment_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__0__Impl"


    // $ANTLR start "rule__Literal__Group_2__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3713:1: rule__Literal__Group_2__1 : rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2 ;
    public final void rule__Literal__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3717:1: ( rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3718:2: rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__1__Impl_in_rule__Literal__Group_2__17509);
            rule__Literal__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__2_in_rule__Literal__Group_2__17512);
            rule__Literal__Group_2__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__1"


    // $ANTLR start "rule__Literal__Group_2__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3725:1: rule__Literal__Group_2__1__Impl : ( '(' ) ;
    public final void rule__Literal__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3729:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3730:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3730:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3731:1: '('
            {
             before(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1()); 
            match(input,25,FOLLOW_25_in_rule__Literal__Group_2__1__Impl7540); 
             after(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__1__Impl"


    // $ANTLR start "rule__Literal__Group_2__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3744:1: rule__Literal__Group_2__2 : rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3 ;
    public final void rule__Literal__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3748:1: ( rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3749:2: rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__2__Impl_in_rule__Literal__Group_2__27571);
            rule__Literal__Group_2__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__3_in_rule__Literal__Group_2__27574);
            rule__Literal__Group_2__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__2"


    // $ANTLR start "rule__Literal__Group_2__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3756:1: rule__Literal__Group_2__2__Impl : ( ( rule__Literal__ValueStringAssignment_2_2 ) ) ;
    public final void rule__Literal__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3760:1: ( ( ( rule__Literal__ValueStringAssignment_2_2 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3761:1: ( ( rule__Literal__ValueStringAssignment_2_2 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3761:1: ( ( rule__Literal__ValueStringAssignment_2_2 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3762:1: ( rule__Literal__ValueStringAssignment_2_2 )
            {
             before(grammarAccess.getLiteralAccess().getValueStringAssignment_2_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3763:1: ( rule__Literal__ValueStringAssignment_2_2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3763:2: rule__Literal__ValueStringAssignment_2_2
            {
            pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_2_2_in_rule__Literal__Group_2__2__Impl7601);
            rule__Literal__ValueStringAssignment_2_2();

            state._fsp--;


            }

             after(grammarAccess.getLiteralAccess().getValueStringAssignment_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__2__Impl"


    // $ANTLR start "rule__Literal__Group_2__3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3773:1: rule__Literal__Group_2__3 : rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4 ;
    public final void rule__Literal__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3777:1: ( rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3778:2: rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__3__Impl_in_rule__Literal__Group_2__37631);
            rule__Literal__Group_2__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__4_in_rule__Literal__Group_2__37634);
            rule__Literal__Group_2__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__3"


    // $ANTLR start "rule__Literal__Group_2__3__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3785:1: rule__Literal__Group_2__3__Impl : ( ',' ) ;
    public final void rule__Literal__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3789:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3790:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3790:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3791:1: ','
            {
             before(grammarAccess.getLiteralAccess().getCommaKeyword_2_3()); 
            match(input,27,FOLLOW_27_in_rule__Literal__Group_2__3__Impl7662); 
             after(grammarAccess.getLiteralAccess().getCommaKeyword_2_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__3__Impl"


    // $ANTLR start "rule__Literal__Group_2__4"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3804:1: rule__Literal__Group_2__4 : rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5 ;
    public final void rule__Literal__Group_2__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3808:1: ( rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3809:2: rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__4__Impl_in_rule__Literal__Group_2__47693);
            rule__Literal__Group_2__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__5_in_rule__Literal__Group_2__47696);
            rule__Literal__Group_2__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__4"


    // $ANTLR start "rule__Literal__Group_2__4__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3816:1: rule__Literal__Group_2__4__Impl : ( ( rule__Literal__ParametersAssignment_2_4 ) ) ;
    public final void rule__Literal__Group_2__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3820:1: ( ( ( rule__Literal__ParametersAssignment_2_4 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3821:1: ( ( rule__Literal__ParametersAssignment_2_4 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3821:1: ( ( rule__Literal__ParametersAssignment_2_4 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3822:1: ( rule__Literal__ParametersAssignment_2_4 )
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_2_4()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3823:1: ( rule__Literal__ParametersAssignment_2_4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3823:2: rule__Literal__ParametersAssignment_2_4
            {
            pushFollow(FOLLOW_rule__Literal__ParametersAssignment_2_4_in_rule__Literal__Group_2__4__Impl7723);
            rule__Literal__ParametersAssignment_2_4();

            state._fsp--;


            }

             after(grammarAccess.getLiteralAccess().getParametersAssignment_2_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__4__Impl"


    // $ANTLR start "rule__Literal__Group_2__5"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3833:1: rule__Literal__Group_2__5 : rule__Literal__Group_2__5__Impl ;
    public final void rule__Literal__Group_2__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3837:1: ( rule__Literal__Group_2__5__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3838:2: rule__Literal__Group_2__5__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__5__Impl_in_rule__Literal__Group_2__57753);
            rule__Literal__Group_2__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__5"


    // $ANTLR start "rule__Literal__Group_2__5__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3844:1: rule__Literal__Group_2__5__Impl : ( ')' ) ;
    public final void rule__Literal__Group_2__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3848:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3849:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3849:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3850:1: ')'
            {
             before(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5()); 
            match(input,26,FOLLOW_26_in_rule__Literal__Group_2__5__Impl7781); 
             after(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_2__5__Impl"


    // $ANTLR start "rule__Literal__Group_4__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3875:1: rule__Literal__Group_4__0 : rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1 ;
    public final void rule__Literal__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3879:1: ( rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3880:2: rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__0__Impl_in_rule__Literal__Group_4__07824);
            rule__Literal__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4__1_in_rule__Literal__Group_4__07827);
            rule__Literal__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4__0"


    // $ANTLR start "rule__Literal__Group_4__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3887:1: rule__Literal__Group_4__0__Impl : ( ( rule__Literal__ExpressionTypeAssignment_4_0 ) ) ;
    public final void rule__Literal__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3891:1: ( ( ( rule__Literal__ExpressionTypeAssignment_4_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3892:1: ( ( rule__Literal__ExpressionTypeAssignment_4_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3892:1: ( ( rule__Literal__ExpressionTypeAssignment_4_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3893:1: ( rule__Literal__ExpressionTypeAssignment_4_0 )
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeAssignment_4_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3894:1: ( rule__Literal__ExpressionTypeAssignment_4_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3894:2: rule__Literal__ExpressionTypeAssignment_4_0
            {
            pushFollow(FOLLOW_rule__Literal__ExpressionTypeAssignment_4_0_in_rule__Literal__Group_4__0__Impl7854);
            rule__Literal__ExpressionTypeAssignment_4_0();

            state._fsp--;


            }

             after(grammarAccess.getLiteralAccess().getExpressionTypeAssignment_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4__0__Impl"


    // $ANTLR start "rule__Literal__Group_4__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3904:1: rule__Literal__Group_4__1 : rule__Literal__Group_4__1__Impl rule__Literal__Group_4__2 ;
    public final void rule__Literal__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3908:1: ( rule__Literal__Group_4__1__Impl rule__Literal__Group_4__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3909:2: rule__Literal__Group_4__1__Impl rule__Literal__Group_4__2
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__1__Impl_in_rule__Literal__Group_4__17884);
            rule__Literal__Group_4__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4__2_in_rule__Literal__Group_4__17887);
            rule__Literal__Group_4__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4__1"


    // $ANTLR start "rule__Literal__Group_4__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3916:1: rule__Literal__Group_4__1__Impl : ( ( rule__Literal__ParametersAssignment_4_1 )? ) ;
    public final void rule__Literal__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3920:1: ( ( ( rule__Literal__ParametersAssignment_4_1 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3921:1: ( ( rule__Literal__ParametersAssignment_4_1 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3921:1: ( ( rule__Literal__ParametersAssignment_4_1 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3922:1: ( rule__Literal__ParametersAssignment_4_1 )?
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_4_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3923:1: ( rule__Literal__ParametersAssignment_4_1 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_ID||LA26_0==RULE_SQBRACKET_OPEN||(LA26_0>=RULE_TML_EXISTS && LA26_0<=RULE_NUMBER)||LA26_0==RULE_DOLLAR||(LA26_0>=RULE_LITERALSTRING && LA26_0<=RULE_FALSE)||LA26_0==25||(LA26_0>=37 && LA26_0<=38)) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3923:2: rule__Literal__ParametersAssignment_4_1
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_4_1_in_rule__Literal__Group_4__1__Impl7914);
                    rule__Literal__ParametersAssignment_4_1();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLiteralAccess().getParametersAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4__1__Impl"


    // $ANTLR start "rule__Literal__Group_4__2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3933:1: rule__Literal__Group_4__2 : rule__Literal__Group_4__2__Impl rule__Literal__Group_4__3 ;
    public final void rule__Literal__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3937:1: ( rule__Literal__Group_4__2__Impl rule__Literal__Group_4__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3938:2: rule__Literal__Group_4__2__Impl rule__Literal__Group_4__3
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__2__Impl_in_rule__Literal__Group_4__27945);
            rule__Literal__Group_4__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4__3_in_rule__Literal__Group_4__27948);
            rule__Literal__Group_4__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4__2"


    // $ANTLR start "rule__Literal__Group_4__2__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3945:1: rule__Literal__Group_4__2__Impl : ( ( rule__Literal__Group_4_2__0 )* ) ;
    public final void rule__Literal__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3949:1: ( ( ( rule__Literal__Group_4_2__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3950:1: ( ( rule__Literal__Group_4_2__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3950:1: ( ( rule__Literal__Group_4_2__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3951:1: ( rule__Literal__Group_4_2__0 )*
            {
             before(grammarAccess.getLiteralAccess().getGroup_4_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3952:1: ( rule__Literal__Group_4_2__0 )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==27) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3952:2: rule__Literal__Group_4_2__0
            	    {
            	    pushFollow(FOLLOW_rule__Literal__Group_4_2__0_in_rule__Literal__Group_4__2__Impl7975);
            	    rule__Literal__Group_4_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

             after(grammarAccess.getLiteralAccess().getGroup_4_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4__2__Impl"


    // $ANTLR start "rule__Literal__Group_4__3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3962:1: rule__Literal__Group_4__3 : rule__Literal__Group_4__3__Impl ;
    public final void rule__Literal__Group_4__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3966:1: ( rule__Literal__Group_4__3__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3967:2: rule__Literal__Group_4__3__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__3__Impl_in_rule__Literal__Group_4__38006);
            rule__Literal__Group_4__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4__3"


    // $ANTLR start "rule__Literal__Group_4__3__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3973:1: rule__Literal__Group_4__3__Impl : ( '}' ) ;
    public final void rule__Literal__Group_4__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3977:1: ( ( '}' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3978:1: ( '}' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3978:1: ( '}' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3979:1: '}'
            {
             before(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3()); 
            match(input,31,FOLLOW_31_in_rule__Literal__Group_4__3__Impl8034); 
             after(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4__3__Impl"


    // $ANTLR start "rule__Literal__Group_4_2__0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4000:1: rule__Literal__Group_4_2__0 : rule__Literal__Group_4_2__0__Impl rule__Literal__Group_4_2__1 ;
    public final void rule__Literal__Group_4_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4004:1: ( rule__Literal__Group_4_2__0__Impl rule__Literal__Group_4_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4005:2: rule__Literal__Group_4_2__0__Impl rule__Literal__Group_4_2__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_4_2__0__Impl_in_rule__Literal__Group_4_2__08073);
            rule__Literal__Group_4_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4_2__1_in_rule__Literal__Group_4_2__08076);
            rule__Literal__Group_4_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4_2__0"


    // $ANTLR start "rule__Literal__Group_4_2__0__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4012:1: rule__Literal__Group_4_2__0__Impl : ( ',' ) ;
    public final void rule__Literal__Group_4_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4016:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4017:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4017:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4018:1: ','
            {
             before(grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0()); 
            match(input,27,FOLLOW_27_in_rule__Literal__Group_4_2__0__Impl8104); 
             after(grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4_2__0__Impl"


    // $ANTLR start "rule__Literal__Group_4_2__1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4031:1: rule__Literal__Group_4_2__1 : rule__Literal__Group_4_2__1__Impl ;
    public final void rule__Literal__Group_4_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4035:1: ( rule__Literal__Group_4_2__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4036:2: rule__Literal__Group_4_2__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_4_2__1__Impl_in_rule__Literal__Group_4_2__18135);
            rule__Literal__Group_4_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4_2__1"


    // $ANTLR start "rule__Literal__Group_4_2__1__Impl"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4042:1: rule__Literal__Group_4_2__1__Impl : ( ( rule__Literal__ParametersAssignment_4_2_1 ) ) ;
    public final void rule__Literal__Group_4_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4046:1: ( ( ( rule__Literal__ParametersAssignment_4_2_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4047:1: ( ( rule__Literal__ParametersAssignment_4_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4047:1: ( ( rule__Literal__ParametersAssignment_4_2_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4048:1: ( rule__Literal__ParametersAssignment_4_2_1 )
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_4_2_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4049:1: ( rule__Literal__ParametersAssignment_4_2_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4049:2: rule__Literal__ParametersAssignment_4_2_1
            {
            pushFollow(FOLLOW_rule__Literal__ParametersAssignment_4_2_1_in_rule__Literal__Group_4_2__1__Impl8162);
            rule__Literal__ParametersAssignment_4_2_1();

            state._fsp--;


            }

             after(grammarAccess.getLiteralAccess().getParametersAssignment_4_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_4_2__1__Impl"


    // $ANTLR start "rule__TopLevel__ToplevelExpressionAssignment"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4064:1: rule__TopLevel__ToplevelExpressionAssignment : ( ruleOrExpression ) ;
    public final void rule__TopLevel__ToplevelExpressionAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4068:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4069:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4069:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4070:1: ruleOrExpression
            {
             before(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__TopLevel__ToplevelExpressionAssignment8201);
            ruleOrExpression();

            state._fsp--;

             after(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TopLevel__ToplevelExpressionAssignment"


    // $ANTLR start "rule__TmlExpression__AbsoluteAssignment_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4079:1: rule__TmlExpression__AbsoluteAssignment_1 : ( RULE_TML_SEPARATOR ) ;
    public final void rule__TmlExpression__AbsoluteAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4083:1: ( ( RULE_TML_SEPARATOR ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4084:1: ( RULE_TML_SEPARATOR )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4084:1: ( RULE_TML_SEPARATOR )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4085:1: RULE_TML_SEPARATOR
            {
             before(grammarAccess.getTmlExpressionAccess().getAbsoluteTML_SEPARATORTerminalRuleCall_1_0()); 
            match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_rule__TmlExpression__AbsoluteAssignment_18232); 
             after(grammarAccess.getTmlExpressionAccess().getAbsoluteTML_SEPARATORTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__AbsoluteAssignment_1"


    // $ANTLR start "rule__TmlExpression__ParamAssignment_2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4094:1: rule__TmlExpression__ParamAssignment_2 : ( RULE_AT ) ;
    public final void rule__TmlExpression__ParamAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4098:1: ( ( RULE_AT ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4099:1: ( RULE_AT )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4099:1: ( RULE_AT )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4100:1: RULE_AT
            {
             before(grammarAccess.getTmlExpressionAccess().getParamATTerminalRuleCall_2_0()); 
            match(input,RULE_AT,FOLLOW_RULE_AT_in_rule__TmlExpression__ParamAssignment_28263); 
             after(grammarAccess.getTmlExpressionAccess().getParamATTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__ParamAssignment_2"


    // $ANTLR start "rule__TmlExpression__ElementsAssignment_3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4109:1: rule__TmlExpression__ElementsAssignment_3 : ( rulePathElement ) ;
    public final void rule__TmlExpression__ElementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4113:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4114:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4114:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4115:1: rulePathElement
            {
             before(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__TmlExpression__ElementsAssignment_38294);
            rulePathElement();

            state._fsp--;

             after(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__ElementsAssignment_3"


    // $ANTLR start "rule__TmlExpression__ElementsAssignment_4_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4124:1: rule__TmlExpression__ElementsAssignment_4_1 : ( rulePathElement ) ;
    public final void rule__TmlExpression__ElementsAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4128:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4129:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4129:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4130:1: rulePathElement
            {
             before(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__TmlExpression__ElementsAssignment_4_18325);
            rulePathElement();

            state._fsp--;

             after(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TmlExpression__ElementsAssignment_4_1"


    // $ANTLR start "rule__ExistsTmlExpression__AbsoluteAssignment_2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4139:1: rule__ExistsTmlExpression__AbsoluteAssignment_2 : ( RULE_TML_SEPARATOR ) ;
    public final void rule__ExistsTmlExpression__AbsoluteAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4143:1: ( ( RULE_TML_SEPARATOR ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4144:1: ( RULE_TML_SEPARATOR )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4144:1: ( RULE_TML_SEPARATOR )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4145:1: RULE_TML_SEPARATOR
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteTML_SEPARATORTerminalRuleCall_2_0()); 
            match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_rule__ExistsTmlExpression__AbsoluteAssignment_28356); 
             after(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteTML_SEPARATORTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__AbsoluteAssignment_2"


    // $ANTLR start "rule__ExistsTmlExpression__ParamAssignment_3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4154:1: rule__ExistsTmlExpression__ParamAssignment_3 : ( RULE_AT ) ;
    public final void rule__ExistsTmlExpression__ParamAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4158:1: ( ( RULE_AT ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4159:1: ( RULE_AT )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4159:1: ( RULE_AT )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4160:1: RULE_AT
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getParamATTerminalRuleCall_3_0()); 
            match(input,RULE_AT,FOLLOW_RULE_AT_in_rule__ExistsTmlExpression__ParamAssignment_38387); 
             after(grammarAccess.getExistsTmlExpressionAccess().getParamATTerminalRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__ParamAssignment_3"


    // $ANTLR start "rule__ExistsTmlExpression__ElementsAssignment_4"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4169:1: rule__ExistsTmlExpression__ElementsAssignment_4 : ( rulePathElement ) ;
    public final void rule__ExistsTmlExpression__ElementsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4173:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4174:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4174:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4175:1: rulePathElement
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__ExistsTmlExpression__ElementsAssignment_48418);
            rulePathElement();

            state._fsp--;

             after(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__ElementsAssignment_4"


    // $ANTLR start "rule__ExistsTmlExpression__ElementsAssignment_5_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4184:1: rule__ExistsTmlExpression__ElementsAssignment_5_1 : ( rulePathElement ) ;
    public final void rule__ExistsTmlExpression__ElementsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4188:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4189:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4189:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4190:1: rulePathElement
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__ExistsTmlExpression__ElementsAssignment_5_18449);
            rulePathElement();

            state._fsp--;

             after(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExistsTmlExpression__ElementsAssignment_5_1"


    // $ANTLR start "rule__MapReferenceParams__GetterParamsAssignment_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4199:1: rule__MapReferenceParams__GetterParamsAssignment_1 : ( ruleLiteral ) ;
    public final void rule__MapReferenceParams__GetterParamsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4203:1: ( ( ruleLiteral ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4204:1: ( ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4204:1: ( ruleLiteral )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4205:1: ruleLiteral
            {
             before(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleLiteral_in_rule__MapReferenceParams__GetterParamsAssignment_18480);
            ruleLiteral();

            state._fsp--;

             after(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__GetterParamsAssignment_1"


    // $ANTLR start "rule__MapReferenceParams__GetterParamsAssignment_2_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4214:1: rule__MapReferenceParams__GetterParamsAssignment_2_1 : ( ruleLiteral ) ;
    public final void rule__MapReferenceParams__GetterParamsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4218:1: ( ( ruleLiteral ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4219:1: ( ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4219:1: ( ruleLiteral )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4220:1: ruleLiteral
            {
             before(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleLiteral_in_rule__MapReferenceParams__GetterParamsAssignment_2_18511);
            ruleLiteral();

            state._fsp--;

             after(grammarAccess.getMapReferenceParamsAccess().getGetterParamsLiteralParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapReferenceParams__GetterParamsAssignment_2_1"


    // $ANTLR start "rule__MapGetReference__OperationsAssignment_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4229:1: rule__MapGetReference__OperationsAssignment_0 : ( RULE_DOLLAR ) ;
    public final void rule__MapGetReference__OperationsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4233:1: ( ( RULE_DOLLAR ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4234:1: ( RULE_DOLLAR )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4234:1: ( RULE_DOLLAR )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4235:1: RULE_DOLLAR
            {
             before(grammarAccess.getMapGetReferenceAccess().getOperationsDOLLARTerminalRuleCall_0_0()); 
            match(input,RULE_DOLLAR,FOLLOW_RULE_DOLLAR_in_rule__MapGetReference__OperationsAssignment_08542); 
             after(grammarAccess.getMapGetReferenceAccess().getOperationsDOLLARTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__OperationsAssignment_0"


    // $ANTLR start "rule__MapGetReference__ElementsAssignment_1_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4244:1: rule__MapGetReference__ElementsAssignment_1_0 : ( RULE_PARENT ) ;
    public final void rule__MapGetReference__ElementsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4248:1: ( ( RULE_PARENT ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4249:1: ( RULE_PARENT )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4249:1: ( RULE_PARENT )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4250:1: RULE_PARENT
            {
             before(grammarAccess.getMapGetReferenceAccess().getElementsPARENTTerminalRuleCall_1_0_0()); 
            match(input,RULE_PARENT,FOLLOW_RULE_PARENT_in_rule__MapGetReference__ElementsAssignment_1_08573); 
             after(grammarAccess.getMapGetReferenceAccess().getElementsPARENTTerminalRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__ElementsAssignment_1_0"


    // $ANTLR start "rule__MapGetReference__ElementsAssignment_2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4259:1: rule__MapGetReference__ElementsAssignment_2 : ( RULE_ID ) ;
    public final void rule__MapGetReference__ElementsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4263:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4264:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4264:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4265:1: RULE_ID
            {
             before(grammarAccess.getMapGetReferenceAccess().getElementsIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__MapGetReference__ElementsAssignment_28604); 
             after(grammarAccess.getMapGetReferenceAccess().getElementsIDTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__ElementsAssignment_2"


    // $ANTLR start "rule__MapGetReference__ReferenceParamsAssignment_3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4274:1: rule__MapGetReference__ReferenceParamsAssignment_3 : ( ruleMapReferenceParams ) ;
    public final void rule__MapGetReference__ReferenceParamsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4278:1: ( ( ruleMapReferenceParams ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4279:1: ( ruleMapReferenceParams )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4279:1: ( ruleMapReferenceParams )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4280:1: ruleMapReferenceParams
            {
             before(grammarAccess.getMapGetReferenceAccess().getReferenceParamsMapReferenceParamsParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleMapReferenceParams_in_rule__MapGetReference__ReferenceParamsAssignment_38635);
            ruleMapReferenceParams();

            state._fsp--;

             after(grammarAccess.getMapGetReferenceAccess().getReferenceParamsMapReferenceParamsParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MapGetReference__ReferenceParamsAssignment_3"


    // $ANTLR start "rule__OrExpression__ParametersAssignment_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4289:1: rule__OrExpression__ParametersAssignment_0 : ( ruleAndExpression ) ;
    public final void rule__OrExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4293:1: ( ( ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4294:1: ( ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4294:1: ( ruleAndExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4295:1: ruleAndExpression
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_08666);
            ruleAndExpression();

            state._fsp--;

             after(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__ParametersAssignment_0"


    // $ANTLR start "rule__OrExpression__OperationsAssignment_1_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4304:1: rule__OrExpression__OperationsAssignment_1_0 : ( ( 'OR' ) ) ;
    public final void rule__OrExpression__OperationsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4308:1: ( ( ( 'OR' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4309:1: ( ( 'OR' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4309:1: ( ( 'OR' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4310:1: ( 'OR' )
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4311:1: ( 'OR' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4312:1: 'OR'
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0()); 
            match(input,32,FOLLOW_32_in_rule__OrExpression__OperationsAssignment_1_08702); 
             after(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0()); 

            }

             after(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__OperationsAssignment_1_0"


    // $ANTLR start "rule__OrExpression__ParametersAssignment_1_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4327:1: rule__OrExpression__ParametersAssignment_1_1 : ( ruleAndExpression ) ;
    public final void rule__OrExpression__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4331:1: ( ( ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4332:1: ( ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4332:1: ( ruleAndExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4333:1: ruleAndExpression
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_1_18741);
            ruleAndExpression();

            state._fsp--;

             after(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OrExpression__ParametersAssignment_1_1"


    // $ANTLR start "rule__AndExpression__ParametersAssignment_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4342:1: rule__AndExpression__ParametersAssignment_0 : ( ruleEqualityExpression ) ;
    public final void rule__AndExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4346:1: ( ( ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4347:1: ( ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4347:1: ( ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4348:1: ruleEqualityExpression
            {
             before(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_08772);
            ruleEqualityExpression();

            state._fsp--;

             after(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__ParametersAssignment_0"


    // $ANTLR start "rule__AndExpression__OperationsAssignment_1_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4357:1: rule__AndExpression__OperationsAssignment_1_0 : ( ( 'AND' ) ) ;
    public final void rule__AndExpression__OperationsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4361:1: ( ( ( 'AND' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4362:1: ( ( 'AND' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4362:1: ( ( 'AND' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4363:1: ( 'AND' )
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4364:1: ( 'AND' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4365:1: 'AND'
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0()); 
            match(input,33,FOLLOW_33_in_rule__AndExpression__OperationsAssignment_1_08808); 
             after(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0()); 

            }

             after(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__OperationsAssignment_1_0"


    // $ANTLR start "rule__AndExpression__ParametersAssignment_1_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4380:1: rule__AndExpression__ParametersAssignment_1_1 : ( ruleEqualityExpression ) ;
    public final void rule__AndExpression__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4384:1: ( ( ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4385:1: ( ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4385:1: ( ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4386:1: ruleEqualityExpression
            {
             before(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_1_18847);
            ruleEqualityExpression();

            state._fsp--;

             after(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AndExpression__ParametersAssignment_1_1"


    // $ANTLR start "rule__EqualityExpression__ParametersAssignment_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4395:1: rule__EqualityExpression__ParametersAssignment_0 : ( ruleRelationalExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4399:1: ( ( ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4400:1: ( ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4400:1: ( ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4401:1: ruleRelationalExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_rule__EqualityExpression__ParametersAssignment_08878);
            ruleRelationalExpression();

            state._fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__ParametersAssignment_0"


    // $ANTLR start "rule__EqualityExpression__OperationsAssignment_1_0_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4410:1: rule__EqualityExpression__OperationsAssignment_1_0_0 : ( ( '==' ) ) ;
    public final void rule__EqualityExpression__OperationsAssignment_1_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4414:1: ( ( ( '==' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4415:1: ( ( '==' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4415:1: ( ( '==' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4416:1: ( '==' )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4417:1: ( '==' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4418:1: '=='
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0()); 
            match(input,34,FOLLOW_34_in_rule__EqualityExpression__OperationsAssignment_1_0_08914); 
             after(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0()); 

            }

             after(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__OperationsAssignment_1_0_0"


    // $ANTLR start "rule__EqualityExpression__ParametersAssignment_1_0_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4433:1: rule__EqualityExpression__ParametersAssignment_1_0_1 : ( ruleRelationalExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4437:1: ( ( ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4438:1: ( ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4438:1: ( ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4439:1: ruleRelationalExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_0_1_0()); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_rule__EqualityExpression__ParametersAssignment_1_0_18953);
            ruleRelationalExpression();

            state._fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__ParametersAssignment_1_0_1"


    // $ANTLR start "rule__EqualityExpression__OperationsAssignment_1_1_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4448:1: rule__EqualityExpression__OperationsAssignment_1_1_0 : ( ( '!=' ) ) ;
    public final void rule__EqualityExpression__OperationsAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4452:1: ( ( ( '!=' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4453:1: ( ( '!=' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4453:1: ( ( '!=' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4454:1: ( '!=' )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4455:1: ( '!=' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4456:1: '!='
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0()); 
            match(input,35,FOLLOW_35_in_rule__EqualityExpression__OperationsAssignment_1_1_08989); 
             after(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0()); 

            }

             after(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__OperationsAssignment_1_1_0"


    // $ANTLR start "rule__EqualityExpression__ParametersAssignment_1_1_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4471:1: rule__EqualityExpression__ParametersAssignment_1_1_1 : ( ruleRelationalExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4475:1: ( ( ruleRelationalExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4476:1: ( ruleRelationalExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4476:1: ( ruleRelationalExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4477:1: ruleRelationalExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_1_1_0()); 
            pushFollow(FOLLOW_ruleRelationalExpression_in_rule__EqualityExpression__ParametersAssignment_1_1_19028);
            ruleRelationalExpression();

            state._fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersRelationalExpressionParserRuleCall_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EqualityExpression__ParametersAssignment_1_1_1"


    // $ANTLR start "rule__RelationalExpression__ParametersAssignment_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4486:1: rule__RelationalExpression__ParametersAssignment_1 : ( ruleAdditiveExpression ) ;
    public final void rule__RelationalExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4490:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4491:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4491:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4492:1: ruleAdditiveExpression
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_19059);
            ruleAdditiveExpression();

            state._fsp--;

             after(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__ParametersAssignment_1"


    // $ANTLR start "rule__RelationalExpression__OperationsAssignment_2_0_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4501:1: rule__RelationalExpression__OperationsAssignment_2_0_0 : ( RULE_XML_LT ) ;
    public final void rule__RelationalExpression__OperationsAssignment_2_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4505:1: ( ( RULE_XML_LT ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4506:1: ( RULE_XML_LT )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4506:1: ( RULE_XML_LT )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4507:1: RULE_XML_LT
            {
             before(grammarAccess.getRelationalExpressionAccess().getOperationsXML_LTTerminalRuleCall_2_0_0_0()); 
            match(input,RULE_XML_LT,FOLLOW_RULE_XML_LT_in_rule__RelationalExpression__OperationsAssignment_2_0_09090); 
             after(grammarAccess.getRelationalExpressionAccess().getOperationsXML_LTTerminalRuleCall_2_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__OperationsAssignment_2_0_0"


    // $ANTLR start "rule__RelationalExpression__ParametersAssignment_2_0_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4516:1: rule__RelationalExpression__ParametersAssignment_2_0_1 : ( ruleAdditiveExpression ) ;
    public final void rule__RelationalExpression__ParametersAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4520:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4521:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4521:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4522:1: ruleAdditiveExpression
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_2_0_19121);
            ruleAdditiveExpression();

            state._fsp--;

             after(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__ParametersAssignment_2_0_1"


    // $ANTLR start "rule__RelationalExpression__OperationsAssignment_2_1_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4531:1: rule__RelationalExpression__OperationsAssignment_2_1_0 : ( RULE_XML_GT ) ;
    public final void rule__RelationalExpression__OperationsAssignment_2_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4535:1: ( ( RULE_XML_GT ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4536:1: ( RULE_XML_GT )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4536:1: ( RULE_XML_GT )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4537:1: RULE_XML_GT
            {
             before(grammarAccess.getRelationalExpressionAccess().getOperationsXML_GTTerminalRuleCall_2_1_0_0()); 
            match(input,RULE_XML_GT,FOLLOW_RULE_XML_GT_in_rule__RelationalExpression__OperationsAssignment_2_1_09152); 
             after(grammarAccess.getRelationalExpressionAccess().getOperationsXML_GTTerminalRuleCall_2_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__OperationsAssignment_2_1_0"


    // $ANTLR start "rule__RelationalExpression__ParametersAssignment_2_1_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4546:1: rule__RelationalExpression__ParametersAssignment_2_1_1 : ( ruleAdditiveExpression ) ;
    public final void rule__RelationalExpression__ParametersAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4550:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4551:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4551:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4552:1: ruleAdditiveExpression
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_2_1_19183);
            ruleAdditiveExpression();

            state._fsp--;

             after(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__ParametersAssignment_2_1_1"


    // $ANTLR start "rule__RelationalExpression__OperationsAssignment_2_2_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4561:1: rule__RelationalExpression__OperationsAssignment_2_2_0 : ( RULE_XML_LTEQ ) ;
    public final void rule__RelationalExpression__OperationsAssignment_2_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4565:1: ( ( RULE_XML_LTEQ ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4566:1: ( RULE_XML_LTEQ )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4566:1: ( RULE_XML_LTEQ )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4567:1: RULE_XML_LTEQ
            {
             before(grammarAccess.getRelationalExpressionAccess().getOperationsXML_LTEQTerminalRuleCall_2_2_0_0()); 
            match(input,RULE_XML_LTEQ,FOLLOW_RULE_XML_LTEQ_in_rule__RelationalExpression__OperationsAssignment_2_2_09214); 
             after(grammarAccess.getRelationalExpressionAccess().getOperationsXML_LTEQTerminalRuleCall_2_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__OperationsAssignment_2_2_0"


    // $ANTLR start "rule__RelationalExpression__ParametersAssignment_2_2_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4576:1: rule__RelationalExpression__ParametersAssignment_2_2_1 : ( ruleAdditiveExpression ) ;
    public final void rule__RelationalExpression__ParametersAssignment_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4580:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4581:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4581:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4582:1: ruleAdditiveExpression
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_2_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_2_2_19245);
            ruleAdditiveExpression();

            state._fsp--;

             after(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__ParametersAssignment_2_2_1"


    // $ANTLR start "rule__RelationalExpression__OperationsAssignment_2_3_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4591:1: rule__RelationalExpression__OperationsAssignment_2_3_0 : ( RULE_XML_GTEQ ) ;
    public final void rule__RelationalExpression__OperationsAssignment_2_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4595:1: ( ( RULE_XML_GTEQ ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4596:1: ( RULE_XML_GTEQ )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4596:1: ( RULE_XML_GTEQ )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4597:1: RULE_XML_GTEQ
            {
             before(grammarAccess.getRelationalExpressionAccess().getOperationsXML_GTEQTerminalRuleCall_2_3_0_0()); 
            match(input,RULE_XML_GTEQ,FOLLOW_RULE_XML_GTEQ_in_rule__RelationalExpression__OperationsAssignment_2_3_09276); 
             after(grammarAccess.getRelationalExpressionAccess().getOperationsXML_GTEQTerminalRuleCall_2_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__OperationsAssignment_2_3_0"


    // $ANTLR start "rule__RelationalExpression__ParametersAssignment_2_3_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4606:1: rule__RelationalExpression__ParametersAssignment_2_3_1 : ( ruleAdditiveExpression ) ;
    public final void rule__RelationalExpression__ParametersAssignment_2_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4610:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4611:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4611:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4612:1: ruleAdditiveExpression
            {
             before(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_3_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_2_3_19307);
            ruleAdditiveExpression();

            state._fsp--;

             after(grammarAccess.getRelationalExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RelationalExpression__ParametersAssignment_2_3_1"


    // $ANTLR start "rule__AdditiveExpression__ParametersAssignment_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4621:1: rule__AdditiveExpression__ParametersAssignment_0 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4625:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4626:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4626:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4627:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_09338);
            ruleMultiplicativeExpression();

            state._fsp--;

             after(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__ParametersAssignment_0"


    // $ANTLR start "rule__AdditiveExpression__ParametersAssignment_1_0_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4636:1: rule__AdditiveExpression__ParametersAssignment_1_0_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4640:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4641:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4641:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4642:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_1_0_19369);
            ruleMultiplicativeExpression();

            state._fsp--;

             after(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__ParametersAssignment_1_0_1"


    // $ANTLR start "rule__AdditiveExpression__ParametersAssignment_1_1_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4651:1: rule__AdditiveExpression__ParametersAssignment_1_1_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4655:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4656:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4656:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4657:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_1_1_19400);
            ruleMultiplicativeExpression();

            state._fsp--;

             after(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdditiveExpression__ParametersAssignment_1_1_1"


    // $ANTLR start "rule__MultiplicativeExpression__ParametersAssignment_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4666:1: rule__MultiplicativeExpression__ParametersAssignment_0 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4670:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4671:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4671:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4672:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_09431);
            ruleUnaryExpression();

            state._fsp--;

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__ParametersAssignment_0"


    // $ANTLR start "rule__MultiplicativeExpression__OperationsAssignment_1_0_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4681:1: rule__MultiplicativeExpression__OperationsAssignment_1_0_0 : ( ( '*' ) ) ;
    public final void rule__MultiplicativeExpression__OperationsAssignment_1_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4685:1: ( ( ( '*' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4686:1: ( ( '*' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4686:1: ( ( '*' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4687:1: ( '*' )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4688:1: ( '*' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4689:1: '*'
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0()); 
            match(input,36,FOLLOW_36_in_rule__MultiplicativeExpression__OperationsAssignment_1_0_09467); 
             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0()); 

            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__OperationsAssignment_1_0_0"


    // $ANTLR start "rule__MultiplicativeExpression__ParametersAssignment_1_0_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4704:1: rule__MultiplicativeExpression__ParametersAssignment_1_0_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4708:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4709:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4709:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4710:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_1_0_19506);
            ruleUnaryExpression();

            state._fsp--;

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__ParametersAssignment_1_0_1"


    // $ANTLR start "rule__MultiplicativeExpression__OperationsAssignment_1_1_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4719:1: rule__MultiplicativeExpression__OperationsAssignment_1_1_0 : ( RULE_TML_SEPARATOR ) ;
    public final void rule__MultiplicativeExpression__OperationsAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4723:1: ( ( RULE_TML_SEPARATOR ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4724:1: ( RULE_TML_SEPARATOR )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4724:1: ( RULE_TML_SEPARATOR )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4725:1: RULE_TML_SEPARATOR
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsTML_SEPARATORTerminalRuleCall_1_1_0_0()); 
            match(input,RULE_TML_SEPARATOR,FOLLOW_RULE_TML_SEPARATOR_in_rule__MultiplicativeExpression__OperationsAssignment_1_1_09537); 
             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsTML_SEPARATORTerminalRuleCall_1_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__OperationsAssignment_1_1_0"


    // $ANTLR start "rule__MultiplicativeExpression__ParametersAssignment_1_1_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4734:1: rule__MultiplicativeExpression__ParametersAssignment_1_1_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4738:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4739:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4739:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4740:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_1_1_19568);
            ruleUnaryExpression();

            state._fsp--;

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__MultiplicativeExpression__ParametersAssignment_1_1_1"


    // $ANTLR start "rule__UnaryExpression__OperationsAssignment_0_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4749:1: rule__UnaryExpression__OperationsAssignment_0_0 : ( ( '!' ) ) ;
    public final void rule__UnaryExpression__OperationsAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4753:1: ( ( ( '!' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4754:1: ( ( '!' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4754:1: ( ( '!' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4755:1: ( '!' )
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4756:1: ( '!' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4757:1: '!'
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0()); 
            match(input,37,FOLLOW_37_in_rule__UnaryExpression__OperationsAssignment_0_09604); 
             after(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0()); 

            }

             after(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryExpression__OperationsAssignment_0_0"


    // $ANTLR start "rule__UnaryExpression__ParametersAssignment_0_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4772:1: rule__UnaryExpression__ParametersAssignment_0_1 : ( rulePrimaryExpression ) ;
    public final void rule__UnaryExpression__ParametersAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4776:1: ( ( rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4777:1: ( rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4777:1: ( rulePrimaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4778:1: rulePrimaryExpression
            {
             before(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__ParametersAssignment_0_19643);
            rulePrimaryExpression();

            state._fsp--;

             after(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryExpression__ParametersAssignment_0_1"


    // $ANTLR start "rule__PrimaryExpression__ParametersAssignment_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4787:1: rule__PrimaryExpression__ParametersAssignment_0 : ( ruleLiteral ) ;
    public final void rule__PrimaryExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4791:1: ( ( ruleLiteral ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4792:1: ( ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4792:1: ( ruleLiteral )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4793:1: ruleLiteral
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleLiteral_in_rule__PrimaryExpression__ParametersAssignment_09674);
            ruleLiteral();

            state._fsp--;

             after(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__ParametersAssignment_0"


    // $ANTLR start "rule__PrimaryExpression__ParametersAssignment_1_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4802:1: rule__PrimaryExpression__ParametersAssignment_1_1 : ( ruleOrExpression ) ;
    public final void rule__PrimaryExpression__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4806:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4807:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4807:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4808:1: ruleOrExpression
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__PrimaryExpression__ParametersAssignment_1_19705);
            ruleOrExpression();

            state._fsp--;

             after(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryExpression__ParametersAssignment_1_1"


    // $ANTLR start "rule__FunctionCall__NameAssignment_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4817:1: rule__FunctionCall__NameAssignment_0 : ( ruleFunctionName ) ;
    public final void rule__FunctionCall__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4821:1: ( ( ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4822:1: ( ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4822:1: ( ruleFunctionName )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4823:1: ruleFunctionName
            {
             before(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleFunctionName_in_rule__FunctionCall__NameAssignment_09736);
            ruleFunctionName();

            state._fsp--;

             after(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__NameAssignment_0"


    // $ANTLR start "rule__FunctionCall__ParametersAssignment_2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4832:1: rule__FunctionCall__ParametersAssignment_2 : ( ruleOrExpression ) ;
    public final void rule__FunctionCall__ParametersAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4836:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4837:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4837:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4838:1: ruleOrExpression
            {
             before(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__FunctionCall__ParametersAssignment_29767);
            ruleOrExpression();

            state._fsp--;

             after(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__ParametersAssignment_2"


    // $ANTLR start "rule__FunctionCall__ParametersAssignment_3_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4847:1: rule__FunctionCall__ParametersAssignment_3_1 : ( ruleOrExpression ) ;
    public final void rule__FunctionCall__ParametersAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4851:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4852:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4852:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4853:1: ruleOrExpression
            {
             before(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__FunctionCall__ParametersAssignment_3_19798);
            ruleOrExpression();

            state._fsp--;

             after(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FunctionCall__ParametersAssignment_3_1"


    // $ANTLR start "rule__Literal__ValueStringAssignment_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4862:1: rule__Literal__ValueStringAssignment_1 : ( RULE_LITERALSTRING ) ;
    public final void rule__Literal__ValueStringAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4866:1: ( ( RULE_LITERALSTRING ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4867:1: ( RULE_LITERALSTRING )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4867:1: ( RULE_LITERALSTRING )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4868:1: RULE_LITERALSTRING
            {
             before(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_19829); 
             after(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ValueStringAssignment_1"


    // $ANTLR start "rule__Literal__OperationsAssignment_2_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4877:1: rule__Literal__OperationsAssignment_2_0 : ( RULE_FORALL ) ;
    public final void rule__Literal__OperationsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4881:1: ( ( RULE_FORALL ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4882:1: ( RULE_FORALL )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4882:1: ( RULE_FORALL )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4883:1: RULE_FORALL
            {
             before(grammarAccess.getLiteralAccess().getOperationsFORALLTerminalRuleCall_2_0_0()); 
            match(input,RULE_FORALL,FOLLOW_RULE_FORALL_in_rule__Literal__OperationsAssignment_2_09860); 
             after(grammarAccess.getLiteralAccess().getOperationsFORALLTerminalRuleCall_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__OperationsAssignment_2_0"


    // $ANTLR start "rule__Literal__ValueStringAssignment_2_2"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4892:1: rule__Literal__ValueStringAssignment_2_2 : ( RULE_LITERALSTRING ) ;
    public final void rule__Literal__ValueStringAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4896:1: ( ( RULE_LITERALSTRING ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4897:1: ( RULE_LITERALSTRING )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4897:1: ( RULE_LITERALSTRING )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4898:1: RULE_LITERALSTRING
            {
             before(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_2_0()); 
            match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_2_29891); 
             after(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ValueStringAssignment_2_2"


    // $ANTLR start "rule__Literal__ParametersAssignment_2_4"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4907:1: rule__Literal__ParametersAssignment_2_4 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_2_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4911:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4912:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4912:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4913:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_2_49922);
            ruleOrExpression();

            state._fsp--;

             after(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ParametersAssignment_2_4"


    // $ANTLR start "rule__Literal__ParametersAssignment_3"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4922:1: rule__Literal__ParametersAssignment_3 : ( ruleFunctionCall ) ;
    public final void rule__Literal__ParametersAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4926:1: ( ( ruleFunctionCall ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4927:1: ( ruleFunctionCall )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4927:1: ( ruleFunctionCall )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4928:1: ruleFunctionCall
            {
             before(grammarAccess.getLiteralAccess().getParametersFunctionCallParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleFunctionCall_in_rule__Literal__ParametersAssignment_39953);
            ruleFunctionCall();

            state._fsp--;

             after(grammarAccess.getLiteralAccess().getParametersFunctionCallParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ParametersAssignment_3"


    // $ANTLR start "rule__Literal__ExpressionTypeAssignment_4_0"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4937:1: rule__Literal__ExpressionTypeAssignment_4_0 : ( ( '{' ) ) ;
    public final void rule__Literal__ExpressionTypeAssignment_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4941:1: ( ( ( '{' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4942:1: ( ( '{' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4942:1: ( ( '{' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4943:1: ( '{' )
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4944:1: ( '{' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4945:1: '{'
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0()); 
            match(input,38,FOLLOW_38_in_rule__Literal__ExpressionTypeAssignment_4_09989); 
             after(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0()); 

            }

             after(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ExpressionTypeAssignment_4_0"


    // $ANTLR start "rule__Literal__ParametersAssignment_4_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4960:1: rule__Literal__ParametersAssignment_4_1 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4964:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4965:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4965:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4966:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_4_110028);
            ruleOrExpression();

            state._fsp--;

             after(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ParametersAssignment_4_1"


    // $ANTLR start "rule__Literal__ParametersAssignment_4_2_1"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4975:1: rule__Literal__ParametersAssignment_4_2_1 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_4_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4979:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4980:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4980:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4981:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_4_2_110059);
            ruleOrExpression();

            state._fsp--;

             after(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ParametersAssignment_4_2_1"


    // $ANTLR start "rule__Literal__ElementsAssignment_5"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4990:1: rule__Literal__ElementsAssignment_5 : ( RULE_NULL ) ;
    public final void rule__Literal__ElementsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4994:1: ( ( RULE_NULL ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4995:1: ( RULE_NULL )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4995:1: ( RULE_NULL )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4996:1: RULE_NULL
            {
             before(grammarAccess.getLiteralAccess().getElementsNULLTerminalRuleCall_5_0()); 
            match(input,RULE_NULL,FOLLOW_RULE_NULL_in_rule__Literal__ElementsAssignment_510090); 
             after(grammarAccess.getLiteralAccess().getElementsNULLTerminalRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ElementsAssignment_5"


    // $ANTLR start "rule__Literal__ElementsAssignment_6"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5005:1: rule__Literal__ElementsAssignment_6 : ( RULE_TODAY ) ;
    public final void rule__Literal__ElementsAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5009:1: ( ( RULE_TODAY ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5010:1: ( RULE_TODAY )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5010:1: ( RULE_TODAY )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5011:1: RULE_TODAY
            {
             before(grammarAccess.getLiteralAccess().getElementsTODAYTerminalRuleCall_6_0()); 
            match(input,RULE_TODAY,FOLLOW_RULE_TODAY_in_rule__Literal__ElementsAssignment_610121); 
             after(grammarAccess.getLiteralAccess().getElementsTODAYTerminalRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ElementsAssignment_6"


    // $ANTLR start "rule__Literal__ElementsAssignment_7"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5020:1: rule__Literal__ElementsAssignment_7 : ( RULE_TRUE ) ;
    public final void rule__Literal__ElementsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5024:1: ( ( RULE_TRUE ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5025:1: ( RULE_TRUE )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5025:1: ( RULE_TRUE )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5026:1: RULE_TRUE
            {
             before(grammarAccess.getLiteralAccess().getElementsTRUETerminalRuleCall_7_0()); 
            match(input,RULE_TRUE,FOLLOW_RULE_TRUE_in_rule__Literal__ElementsAssignment_710152); 
             after(grammarAccess.getLiteralAccess().getElementsTRUETerminalRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ElementsAssignment_7"


    // $ANTLR start "rule__Literal__ElementsAssignment_8"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5035:1: rule__Literal__ElementsAssignment_8 : ( RULE_FALSE ) ;
    public final void rule__Literal__ElementsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5039:1: ( ( RULE_FALSE ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5040:1: ( RULE_FALSE )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5040:1: ( RULE_FALSE )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5041:1: RULE_FALSE
            {
             before(grammarAccess.getLiteralAccess().getElementsFALSETerminalRuleCall_8_0()); 
            match(input,RULE_FALSE,FOLLOW_RULE_FALSE_in_rule__Literal__ElementsAssignment_810183); 
             after(grammarAccess.getLiteralAccess().getElementsFALSETerminalRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ElementsAssignment_8"


    // $ANTLR start "rule__Literal__ParametersAssignment_9"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5050:1: rule__Literal__ParametersAssignment_9 : ( ruleTmlExpression ) ;
    public final void rule__Literal__ParametersAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5054:1: ( ( ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5055:1: ( ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5055:1: ( ruleTmlExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5056:1: ruleTmlExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_rule__Literal__ParametersAssignment_910214);
            ruleTmlExpression();

            state._fsp--;

             after(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ParametersAssignment_9"


    // $ANTLR start "rule__Literal__ParametersAssignment_10"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5065:1: rule__Literal__ParametersAssignment_10 : ( ruleExistsTmlExpression ) ;
    public final void rule__Literal__ParametersAssignment_10() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5069:1: ( ( ruleExistsTmlExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5070:1: ( ruleExistsTmlExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5070:1: ( ruleExistsTmlExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5071:1: ruleExistsTmlExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_rule__Literal__ParametersAssignment_1010245);
            ruleExistsTmlExpression();

            state._fsp--;

             after(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ParametersAssignment_10"


    // $ANTLR start "rule__Literal__ParametersAssignment_11"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5080:1: rule__Literal__ParametersAssignment_11 : ( ruleMapGetReference ) ;
    public final void rule__Literal__ParametersAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5084:1: ( ( ruleMapGetReference ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5085:1: ( ruleMapGetReference )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5085:1: ( ruleMapGetReference )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5086:1: ruleMapGetReference
            {
             before(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0()); 
            pushFollow(FOLLOW_ruleMapGetReference_in_rule__Literal__ParametersAssignment_1110276);
            ruleMapGetReference();

            state._fsp--;

             after(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ParametersAssignment_11"


    // $ANTLR start "rule__Literal__ParametersAssignment_12"
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5095:1: rule__Literal__ParametersAssignment_12 : ( ruleDateLiteral ) ;
    public final void rule__Literal__ParametersAssignment_12() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5099:1: ( ( ruleDateLiteral ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5100:1: ( ruleDateLiteral )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5100:1: ( ruleDateLiteral )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:5101:1: ruleDateLiteral
            {
             before(grammarAccess.getLiteralAccess().getParametersDateLiteralParserRuleCall_12_0()); 
            pushFollow(FOLLOW_ruleDateLiteral_in_rule__Literal__ParametersAssignment_1210307);
            ruleDateLiteral();

            state._fsp--;

             after(grammarAccess.getLiteralAccess().getParametersDateLiteralParserRuleCall_12_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ParametersAssignment_12"

    // Delegated rules


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\17\uffff";
    static final String DFA8_eofS =
        "\1\uffff\1\15\15\uffff";
    static final String DFA8_minS =
        "\1\4\1\10\15\uffff";
    static final String DFA8_maxS =
        "\1\46\1\44\15\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\1\1"+
        "\15";
    static final String DFA8_specialS =
        "\17\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\4\1\uffff\1\12\2\uffff\1\13\1\1\1\uffff\1\14\4\uffff\1\2"+
            "\1\3\1\6\1\7\1\10\1\11\17\uffff\1\5",
            "\1\15\4\uffff\4\15\11\uffff\4\15\1\16\6\15",
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

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "738:1: rule__Literal__Alternatives : ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__ValueStringAssignment_1 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ( rule__Literal__ParametersAssignment_3 ) ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__ElementsAssignment_5 ) ) | ( ( rule__Literal__ElementsAssignment_6 ) ) | ( ( rule__Literal__ElementsAssignment_7 ) ) | ( ( rule__Literal__ElementsAssignment_8 ) ) | ( ( rule__Literal__ParametersAssignment_9 ) ) | ( ( rule__Literal__ParametersAssignment_10 ) ) | ( ( rule__Literal__ParametersAssignment_11 ) ) | ( ( rule__Literal__ParametersAssignment_12 ) ) );";
        }
    }
 

    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__ToplevelExpressionAssignment_in_ruleTopLevel94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathElement__Alternatives_in_rulePathElement154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__0_in_ruleTmlExpression214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression241 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__0_in_ruleExistsTmlExpression274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_entryRuleMapReferenceParams301 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapReferenceParams308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group__0_in_ruleMapReferenceParams334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference361 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__0_in_ruleMapGetReference394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression421 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__0_in_ruleOrExpression454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__0_in_ruleAndExpression514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression541 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__0_in_ruleEqualityExpression574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_entryRuleRelationalExpression601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRelationalExpression608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group__0_in_ruleRelationalExpression634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression661 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__0_in_ruleAdditiveExpression694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression721 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__0_in_ruleMultiplicativeExpression754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression781 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Alternatives_in_ruleUnaryExpression814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression841 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Alternatives_in_rulePrimaryExpression874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName901 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall960 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__0_in_ruleFunctionCall993 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral1020 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDateLiteral1027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__0_in_ruleDateLiteral1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral1080 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral1087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Alternatives_in_ruleLiteral1113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PathElement__Alternatives1149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__PathElement__Alternatives1167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARENT_in_rule__PathElement__Alternatives1186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_0__0_in_rule__EqualityExpression__Alternatives_11218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_1__0_in_rule__EqualityExpression__Alternatives_11236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_0__0_in_rule__RelationalExpression__Alternatives_21269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_1__0_in_rule__RelationalExpression__Alternatives_21287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_2__0_in_rule__RelationalExpression__Alternatives_21305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_3__0_in_rule__RelationalExpression__Alternatives_21323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_0__0_in_rule__AdditiveExpression__Alternatives_11356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_1__0_in_rule__AdditiveExpression__Alternatives_11374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_0__0_in_rule__MultiplicativeExpression__Alternatives_11407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_1__0_in_rule__MultiplicativeExpression__Alternatives_11425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__0_in_rule__UnaryExpression__Alternatives1458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__Alternatives1476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__ParametersAssignment_0_in_rule__PrimaryExpression__Alternatives1508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__0_in_rule__PrimaryExpression__Alternatives1526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__0_in_rule__Literal__Alternatives1559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_1_in_rule__Literal__Alternatives1577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__0_in_rule__Literal__Alternatives1595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_3_in_rule__Literal__Alternatives1613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__0_in_rule__Literal__Alternatives1631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_5_in_rule__Literal__Alternatives1649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_6_in_rule__Literal__Alternatives1667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_7_in_rule__Literal__Alternatives1685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_8_in_rule__Literal__Alternatives1703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_9_in_rule__Literal__Alternatives1721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_10_in_rule__Literal__Alternatives1739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_11_in_rule__Literal__Alternatives1757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_12_in_rule__Literal__Alternatives1775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__0__Impl_in_rule__TmlExpression__Group__01806 = new BitSet(new long[]{0x0000000001000930L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__1_in_rule__TmlExpression__Group__01809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_rule__TmlExpression__Group__0__Impl1836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__1__Impl_in_rule__TmlExpression__Group__11865 = new BitSet(new long[]{0x0000000001000930L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__2_in_rule__TmlExpression__Group__11868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__AbsoluteAssignment_1_in_rule__TmlExpression__Group__1__Impl1895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__2__Impl_in_rule__TmlExpression__Group__21926 = new BitSet(new long[]{0x0000000001000930L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__3_in_rule__TmlExpression__Group__21929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__ParamAssignment_2_in_rule__TmlExpression__Group__2__Impl1956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__3__Impl_in_rule__TmlExpression__Group__31987 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__4_in_rule__TmlExpression__Group__31990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__ElementsAssignment_3_in_rule__TmlExpression__Group__3__Impl2017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__4__Impl_in_rule__TmlExpression__Group__42047 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__5_in_rule__TmlExpression__Group__42050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group_4__0_in_rule__TmlExpression__Group__4__Impl2077 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__5__Impl_in_rule__TmlExpression__Group__52108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_rule__TmlExpression__Group__5__Impl2135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group_4__0__Impl_in_rule__TmlExpression__Group_4__02176 = new BitSet(new long[]{0x0000000001000930L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group_4__1_in_rule__TmlExpression__Group_4__02179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_rule__TmlExpression__Group_4__0__Impl2206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group_4__1__Impl_in_rule__TmlExpression__Group_4__12235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__ElementsAssignment_4_1_in_rule__TmlExpression__Group_4__1__Impl2262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__0__Impl_in_rule__ExistsTmlExpression__Group__02296 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__1_in_rule__ExistsTmlExpression__Group__02299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_EXISTS_in_rule__ExistsTmlExpression__Group__0__Impl2326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__1__Impl_in_rule__ExistsTmlExpression__Group__12355 = new BitSet(new long[]{0x0000000001000930L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__2_in_rule__ExistsTmlExpression__Group__12358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_OPEN_in_rule__ExistsTmlExpression__Group__1__Impl2385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__2__Impl_in_rule__ExistsTmlExpression__Group__22414 = new BitSet(new long[]{0x0000000001000930L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__3_in_rule__ExistsTmlExpression__Group__22417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__AbsoluteAssignment_2_in_rule__ExistsTmlExpression__Group__2__Impl2444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__3__Impl_in_rule__ExistsTmlExpression__Group__32475 = new BitSet(new long[]{0x0000000001000930L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__4_in_rule__ExistsTmlExpression__Group__32478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__ParamAssignment_3_in_rule__ExistsTmlExpression__Group__3__Impl2505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__4__Impl_in_rule__ExistsTmlExpression__Group__42536 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__5_in_rule__ExistsTmlExpression__Group__42539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__ElementsAssignment_4_in_rule__ExistsTmlExpression__Group__4__Impl2566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__5__Impl_in_rule__ExistsTmlExpression__Group__52596 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__6_in_rule__ExistsTmlExpression__Group__52599 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group_5__0_in_rule__ExistsTmlExpression__Group__5__Impl2626 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__6__Impl_in_rule__ExistsTmlExpression__Group__62657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_SQBRACKET_CLOSE_in_rule__ExistsTmlExpression__Group__6__Impl2684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group_5__0__Impl_in_rule__ExistsTmlExpression__Group_5__02727 = new BitSet(new long[]{0x0000000001000930L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group_5__1_in_rule__ExistsTmlExpression__Group_5__02730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_rule__ExistsTmlExpression__Group_5__0__Impl2757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group_5__1__Impl_in_rule__ExistsTmlExpression__Group_5__12786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__ElementsAssignment_5_1_in_rule__ExistsTmlExpression__Group_5__1__Impl2813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group__0__Impl_in_rule__MapReferenceParams__Group__02847 = new BitSet(new long[]{0x00000040007E1650L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group__1_in_rule__MapReferenceParams__Group__02850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__MapReferenceParams__Group__0__Impl2878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group__1__Impl_in_rule__MapReferenceParams__Group__12909 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group__2_in_rule__MapReferenceParams__Group__12912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__GetterParamsAssignment_1_in_rule__MapReferenceParams__Group__1__Impl2939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group__2__Impl_in_rule__MapReferenceParams__Group__22969 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group__3_in_rule__MapReferenceParams__Group__22972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group_2__0_in_rule__MapReferenceParams__Group__2__Impl2999 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group__3__Impl_in_rule__MapReferenceParams__Group__33030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__MapReferenceParams__Group__3__Impl3058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group_2__0__Impl_in_rule__MapReferenceParams__Group_2__03097 = new BitSet(new long[]{0x00000040007E1650L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group_2__1_in_rule__MapReferenceParams__Group_2__03100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__MapReferenceParams__Group_2__0__Impl3128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__Group_2__1__Impl_in_rule__MapReferenceParams__Group_2__13159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapReferenceParams__GetterParamsAssignment_2_1_in_rule__MapReferenceParams__Group_2__1__Impl3186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__0__Impl_in_rule__MapGetReference__Group__03220 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__1_in_rule__MapGetReference__Group__03223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__OperationsAssignment_0_in_rule__MapGetReference__Group__0__Impl3250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__1__Impl_in_rule__MapGetReference__Group__13280 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__2_in_rule__MapGetReference__Group__13283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group_1__0_in_rule__MapGetReference__Group__1__Impl3310 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__2__Impl_in_rule__MapGetReference__Group__23341 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__3_in_rule__MapGetReference__Group__23344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__ElementsAssignment_2_in_rule__MapGetReference__Group__2__Impl3371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__3__Impl_in_rule__MapGetReference__Group__33401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__ReferenceParamsAssignment_3_in_rule__MapGetReference__Group__3__Impl3428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group_1__0__Impl_in_rule__MapGetReference__Group_1__03467 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group_1__1_in_rule__MapGetReference__Group_1__03470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__ElementsAssignment_1_0_in_rule__MapGetReference__Group_1__0__Impl3497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group_1__1__Impl_in_rule__MapGetReference__Group_1__13527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_rule__MapGetReference__Group_1__1__Impl3554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__0__Impl_in_rule__OrExpression__Group__03587 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__1_in_rule__OrExpression__Group__03590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__ParametersAssignment_0_in_rule__OrExpression__Group__0__Impl3617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__1__Impl_in_rule__OrExpression__Group__13647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_1__0_in_rule__OrExpression__Group__1__Impl3674 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_1__0__Impl_in_rule__OrExpression__Group_1__03709 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_1__1_in_rule__OrExpression__Group_1__03712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__OperationsAssignment_1_0_in_rule__OrExpression__Group_1__0__Impl3739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_1__1__Impl_in_rule__OrExpression__Group_1__13769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__ParametersAssignment_1_1_in_rule__OrExpression__Group_1__1__Impl3796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__0__Impl_in_rule__AndExpression__Group__03830 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__1_in_rule__AndExpression__Group__03833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__ParametersAssignment_0_in_rule__AndExpression__Group__0__Impl3860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__1__Impl_in_rule__AndExpression__Group__13890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_1__0_in_rule__AndExpression__Group__1__Impl3917 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_1__0__Impl_in_rule__AndExpression__Group_1__03952 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_1__1_in_rule__AndExpression__Group_1__03955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__OperationsAssignment_1_0_in_rule__AndExpression__Group_1__0__Impl3982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_1__1__Impl_in_rule__AndExpression__Group_1__14012 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__ParametersAssignment_1_1_in_rule__AndExpression__Group_1__1__Impl4039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__0__Impl_in_rule__EqualityExpression__Group__04073 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__1_in_rule__EqualityExpression__Group__04076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_0_in_rule__EqualityExpression__Group__0__Impl4103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__1__Impl_in_rule__EqualityExpression__Group__14133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Alternatives_1_in_rule__EqualityExpression__Group__1__Impl4160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_0__0__Impl_in_rule__EqualityExpression__Group_1_0__04195 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_0__1_in_rule__EqualityExpression__Group_1_0__04198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__OperationsAssignment_1_0_0_in_rule__EqualityExpression__Group_1_0__0__Impl4225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_0__1__Impl_in_rule__EqualityExpression__Group_1_0__14255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_1_0_1_in_rule__EqualityExpression__Group_1_0__1__Impl4282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_1__0__Impl_in_rule__EqualityExpression__Group_1_1__04316 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_1__1_in_rule__EqualityExpression__Group_1_1__04319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__OperationsAssignment_1_1_0_in_rule__EqualityExpression__Group_1_1__0__Impl4346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_1__1__Impl_in_rule__EqualityExpression__Group_1_1__14376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_1_1_1_in_rule__EqualityExpression__Group_1_1__1__Impl4403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group__0__Impl_in_rule__RelationalExpression__Group__04437 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group__1_in_rule__RelationalExpression__Group__04440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group__1__Impl_in_rule__RelationalExpression__Group__14498 = new BitSet(new long[]{0x000000000001E000L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group__2_in_rule__RelationalExpression__Group__14501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__ParametersAssignment_1_in_rule__RelationalExpression__Group__1__Impl4528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group__2__Impl_in_rule__RelationalExpression__Group__24558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Alternatives_2_in_rule__RelationalExpression__Group__2__Impl4585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_0__0__Impl_in_rule__RelationalExpression__Group_2_0__04622 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_0__1_in_rule__RelationalExpression__Group_2_0__04625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__OperationsAssignment_2_0_0_in_rule__RelationalExpression__Group_2_0__0__Impl4652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_0__1__Impl_in_rule__RelationalExpression__Group_2_0__14682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__ParametersAssignment_2_0_1_in_rule__RelationalExpression__Group_2_0__1__Impl4709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_1__0__Impl_in_rule__RelationalExpression__Group_2_1__04743 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_1__1_in_rule__RelationalExpression__Group_2_1__04746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__OperationsAssignment_2_1_0_in_rule__RelationalExpression__Group_2_1__0__Impl4773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_1__1__Impl_in_rule__RelationalExpression__Group_2_1__14803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__ParametersAssignment_2_1_1_in_rule__RelationalExpression__Group_2_1__1__Impl4830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_2__0__Impl_in_rule__RelationalExpression__Group_2_2__04864 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_2__1_in_rule__RelationalExpression__Group_2_2__04867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__OperationsAssignment_2_2_0_in_rule__RelationalExpression__Group_2_2__0__Impl4894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_2__1__Impl_in_rule__RelationalExpression__Group_2_2__14924 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__ParametersAssignment_2_2_1_in_rule__RelationalExpression__Group_2_2__1__Impl4951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_3__0__Impl_in_rule__RelationalExpression__Group_2_3__04985 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_3__1_in_rule__RelationalExpression__Group_2_3__04988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__OperationsAssignment_2_3_0_in_rule__RelationalExpression__Group_2_3__0__Impl5015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__Group_2_3__1__Impl_in_rule__RelationalExpression__Group_2_3__15045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RelationalExpression__ParametersAssignment_2_3_1_in_rule__RelationalExpression__Group_2_3__1__Impl5072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__0__Impl_in_rule__AdditiveExpression__Group__05106 = new BitSet(new long[]{0x0000000030000000L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__1_in_rule__AdditiveExpression__Group__05109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_0_in_rule__AdditiveExpression__Group__0__Impl5136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__1__Impl_in_rule__AdditiveExpression__Group__15166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Alternatives_1_in_rule__AdditiveExpression__Group__1__Impl5193 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_0__0__Impl_in_rule__AdditiveExpression__Group_1_0__05228 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_0__1_in_rule__AdditiveExpression__Group_1_0__05231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__AdditiveExpression__Group_1_0__0__Impl5259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_0__1__Impl_in_rule__AdditiveExpression__Group_1_0__15290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_0_1_in_rule__AdditiveExpression__Group_1_0__1__Impl5317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_1__0__Impl_in_rule__AdditiveExpression__Group_1_1__05351 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_1__1_in_rule__AdditiveExpression__Group_1_1__05354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__AdditiveExpression__Group_1_1__0__Impl5382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_1__1__Impl_in_rule__AdditiveExpression__Group_1_1__15413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_1_1_in_rule__AdditiveExpression__Group_1_1__1__Impl5440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__0__Impl_in_rule__MultiplicativeExpression__Group__05474 = new BitSet(new long[]{0x0000001000000100L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__1_in_rule__MultiplicativeExpression__Group__05477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_0_in_rule__MultiplicativeExpression__Group__0__Impl5504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__1__Impl_in_rule__MultiplicativeExpression__Group__15534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Alternatives_1_in_rule__MultiplicativeExpression__Group__1__Impl5561 = new BitSet(new long[]{0x0000001000000102L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_0__0__Impl_in_rule__MultiplicativeExpression__Group_1_0__05596 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_0__1_in_rule__MultiplicativeExpression__Group_1_0__05599 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_1_0_0_in_rule__MultiplicativeExpression__Group_1_0__0__Impl5626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_0__1__Impl_in_rule__MultiplicativeExpression__Group_1_0__15656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_0_1_in_rule__MultiplicativeExpression__Group_1_0__1__Impl5683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_1__0__Impl_in_rule__MultiplicativeExpression__Group_1_1__05717 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_1__1_in_rule__MultiplicativeExpression__Group_1_1__05720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_1_1_0_in_rule__MultiplicativeExpression__Group_1_1__0__Impl5747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_1__1__Impl_in_rule__MultiplicativeExpression__Group_1_1__15777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_1_1_in_rule__MultiplicativeExpression__Group_1_1__1__Impl5804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__0__Impl_in_rule__UnaryExpression__Group_0__05838 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__1_in_rule__UnaryExpression__Group_0__05841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__OperationsAssignment_0_0_in_rule__UnaryExpression__Group_0__0__Impl5868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__1__Impl_in_rule__UnaryExpression__Group_0__15898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__ParametersAssignment_0_1_in_rule__UnaryExpression__Group_0__1__Impl5925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__0__Impl_in_rule__PrimaryExpression__Group_1__05959 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__1_in_rule__PrimaryExpression__Group_1__05962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__PrimaryExpression__Group_1__0__Impl5990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__1__Impl_in_rule__PrimaryExpression__Group_1__16021 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__2_in_rule__PrimaryExpression__Group_1__16024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__ParametersAssignment_1_1_in_rule__PrimaryExpression__Group_1__1__Impl6051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__2__Impl_in_rule__PrimaryExpression__Group_1__26081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__PrimaryExpression__Group_1__2__Impl6109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__0__Impl_in_rule__FunctionCall__Group__06146 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__1_in_rule__FunctionCall__Group__06149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__NameAssignment_0_in_rule__FunctionCall__Group__0__Impl6176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__1__Impl_in_rule__FunctionCall__Group__16206 = new BitSet(new long[]{0x000000600E7E1650L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__2_in_rule__FunctionCall__Group__16209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__FunctionCall__Group__1__Impl6237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__2__Impl_in_rule__FunctionCall__Group__26268 = new BitSet(new long[]{0x000000600E7E1650L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__3_in_rule__FunctionCall__Group__26271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__ParametersAssignment_2_in_rule__FunctionCall__Group__2__Impl6298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__3__Impl_in_rule__FunctionCall__Group__36329 = new BitSet(new long[]{0x000000600E7E1650L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__4_in_rule__FunctionCall__Group__36332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group_3__0_in_rule__FunctionCall__Group__3__Impl6359 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__4__Impl_in_rule__FunctionCall__Group__46390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__FunctionCall__Group__4__Impl6418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group_3__0__Impl_in_rule__FunctionCall__Group_3__06459 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group_3__1_in_rule__FunctionCall__Group_3__06462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__FunctionCall__Group_3__0__Impl6490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group_3__1__Impl_in_rule__FunctionCall__Group_3__16521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__ParametersAssignment_3_1_in_rule__FunctionCall__Group_3__1__Impl6548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__0__Impl_in_rule__DateLiteral__Group__06582 = new BitSet(new long[]{0x00000040007E1650L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__1_in_rule__DateLiteral__Group__06585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__1__Impl_in_rule__DateLiteral__Group__16643 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__2_in_rule__DateLiteral__Group__16646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__1__Impl6673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__2__Impl_in_rule__DateLiteral__Group__26702 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__3_in_rule__DateLiteral__Group__26705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__DateLiteral__Group__2__Impl6733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__3__Impl_in_rule__DateLiteral__Group__36764 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__4_in_rule__DateLiteral__Group__36767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__3__Impl6794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__4__Impl_in_rule__DateLiteral__Group__46823 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__5_in_rule__DateLiteral__Group__46826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__DateLiteral__Group__4__Impl6854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__5__Impl_in_rule__DateLiteral__Group__56885 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__6_in_rule__DateLiteral__Group__56888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__5__Impl6915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__6__Impl_in_rule__DateLiteral__Group__66944 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__7_in_rule__DateLiteral__Group__66947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__DateLiteral__Group__6__Impl6975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__7__Impl_in_rule__DateLiteral__Group__77006 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__8_in_rule__DateLiteral__Group__77009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__7__Impl7036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__8__Impl_in_rule__DateLiteral__Group__87065 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__9_in_rule__DateLiteral__Group__87068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__DateLiteral__Group__8__Impl7096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__9__Impl_in_rule__DateLiteral__Group__97127 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__10_in_rule__DateLiteral__Group__97130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__9__Impl7157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__10__Impl_in_rule__DateLiteral__Group__107186 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__11_in_rule__DateLiteral__Group__107189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__DateLiteral__Group__10__Impl7217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DateLiteral__Group__11__Impl_in_rule__DateLiteral__Group__117248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_rule__DateLiteral__Group__11__Impl7275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__0__Impl_in_rule__Literal__Group_0__07328 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__1_in_rule__Literal__Group_0__07331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__1__Impl_in_rule__Literal__Group_0__17389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NUMBER_in_rule__Literal__Group_0__1__Impl7416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__0__Impl_in_rule__Literal__Group_2__07449 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__1_in_rule__Literal__Group_2__07452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__OperationsAssignment_2_0_in_rule__Literal__Group_2__0__Impl7479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__1__Impl_in_rule__Literal__Group_2__17509 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__2_in_rule__Literal__Group_2__17512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Literal__Group_2__1__Impl7540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__2__Impl_in_rule__Literal__Group_2__27571 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__3_in_rule__Literal__Group_2__27574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_2_2_in_rule__Literal__Group_2__2__Impl7601 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__3__Impl_in_rule__Literal__Group_2__37631 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__4_in_rule__Literal__Group_2__37634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__Literal__Group_2__3__Impl7662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__4__Impl_in_rule__Literal__Group_2__47693 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__5_in_rule__Literal__Group_2__47696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_2_4_in_rule__Literal__Group_2__4__Impl7723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__5__Impl_in_rule__Literal__Group_2__57753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__Literal__Group_2__5__Impl7781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__0__Impl_in_rule__Literal__Group_4__07824 = new BitSet(new long[]{0x000000608A7E1650L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__1_in_rule__Literal__Group_4__07827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ExpressionTypeAssignment_4_0_in_rule__Literal__Group_4__0__Impl7854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__1__Impl_in_rule__Literal__Group_4__17884 = new BitSet(new long[]{0x000000608A7E1650L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__2_in_rule__Literal__Group_4__17887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_4_1_in_rule__Literal__Group_4__1__Impl7914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__2__Impl_in_rule__Literal__Group_4__27945 = new BitSet(new long[]{0x000000608A7E1650L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__3_in_rule__Literal__Group_4__27948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4_2__0_in_rule__Literal__Group_4__2__Impl7975 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__3__Impl_in_rule__Literal__Group_4__38006 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__Literal__Group_4__3__Impl8034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4_2__0__Impl_in_rule__Literal__Group_4_2__08073 = new BitSet(new long[]{0x00000060027E1650L});
    public static final BitSet FOLLOW_rule__Literal__Group_4_2__1_in_rule__Literal__Group_4_2__08076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__Literal__Group_4_2__0__Impl8104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4_2__1__Impl_in_rule__Literal__Group_4_2__18135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_4_2_1_in_rule__Literal__Group_4_2__1__Impl8162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__TopLevel__ToplevelExpressionAssignment8201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_rule__TmlExpression__AbsoluteAssignment_18232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_AT_in_rule__TmlExpression__ParamAssignment_28263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__TmlExpression__ElementsAssignment_38294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__TmlExpression__ElementsAssignment_4_18325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_rule__ExistsTmlExpression__AbsoluteAssignment_28356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_AT_in_rule__ExistsTmlExpression__ParamAssignment_38387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__ExistsTmlExpression__ElementsAssignment_48418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__ExistsTmlExpression__ElementsAssignment_5_18449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rule__MapReferenceParams__GetterParamsAssignment_18480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rule__MapReferenceParams__GetterParamsAssignment_2_18511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOLLAR_in_rule__MapGetReference__OperationsAssignment_08542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_PARENT_in_rule__MapGetReference__ElementsAssignment_1_08573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__MapGetReference__ElementsAssignment_28604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapReferenceParams_in_rule__MapGetReference__ReferenceParamsAssignment_38635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_08666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__OrExpression__OperationsAssignment_1_08702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_1_18741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_08772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__AndExpression__OperationsAssignment_1_08808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_1_18847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_rule__EqualityExpression__ParametersAssignment_08878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__EqualityExpression__OperationsAssignment_1_0_08914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_rule__EqualityExpression__ParametersAssignment_1_0_18953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rule__EqualityExpression__OperationsAssignment_1_1_08989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRelationalExpression_in_rule__EqualityExpression__ParametersAssignment_1_1_19028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_19059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LT_in_rule__RelationalExpression__OperationsAssignment_2_0_09090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_2_0_19121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GT_in_rule__RelationalExpression__OperationsAssignment_2_1_09152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_2_1_19183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_LTEQ_in_rule__RelationalExpression__OperationsAssignment_2_2_09214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_2_2_19245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_XML_GTEQ_in_rule__RelationalExpression__OperationsAssignment_2_3_09276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__RelationalExpression__ParametersAssignment_2_3_19307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_09338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_1_0_19369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_1_1_19400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_09431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_rule__MultiplicativeExpression__OperationsAssignment_1_0_09467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_1_0_19506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TML_SEPARATOR_in_rule__MultiplicativeExpression__OperationsAssignment_1_1_09537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_1_1_19568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__UnaryExpression__OperationsAssignment_0_09604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__ParametersAssignment_0_19643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rule__PrimaryExpression__ParametersAssignment_09674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__PrimaryExpression__ParametersAssignment_1_19705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_rule__FunctionCall__NameAssignment_09736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__FunctionCall__ParametersAssignment_29767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__FunctionCall__ParametersAssignment_3_19798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_19829 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FORALL_in_rule__Literal__OperationsAssignment_2_09860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_2_29891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_2_49922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_rule__Literal__ParametersAssignment_39953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__Literal__ExpressionTypeAssignment_4_09989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_4_110028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_4_2_110059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NULL_in_rule__Literal__ElementsAssignment_510090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TODAY_in_rule__Literal__ElementsAssignment_610121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TRUE_in_rule__Literal__ElementsAssignment_710152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_FALSE_in_rule__Literal__ElementsAssignment_810183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_rule__Literal__ParametersAssignment_910214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_rule__Literal__ParametersAssignment_1010245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_rule__Literal__ParametersAssignment_1110276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_rule__Literal__ParametersAssignment_1210307 = new BitSet(new long[]{0x0000000000000002L});

}