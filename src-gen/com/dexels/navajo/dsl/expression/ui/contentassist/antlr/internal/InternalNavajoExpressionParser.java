package com.dexels.navajo.dsl.expression.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.xtext.parsetree.*;
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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_LITERALSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'.'", "'..'", "'['", "'/'", "']'", "'?'", "'+'", "'-'", "'('", "')'", "','", "'}'", "'OR'", "'AND'", "'=='", "'!='", "'*'", "'!'", "'FORALL'", "'{'", "'NULL'", "'TODAY'", "'TRUE'", "'FALSE'"
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




    // $ANTLR start entryRuleTopLevel
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:61:1: entryRuleTopLevel : ruleTopLevel EOF ;
    public final void entryRuleTopLevel() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:62:1: ( ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:63:1: ruleTopLevel EOF
            {
             before(grammarAccess.getTopLevelRule()); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel61);
            ruleTopLevel();
            _fsp--;

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
    // $ANTLR end entryRuleTopLevel


    // $ANTLR start ruleTopLevel
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:70:1: ruleTopLevel : ( ( rule__TopLevel__Group__0 ) ) ;
    public final void ruleTopLevel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:74:2: ( ( ( rule__TopLevel__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:75:1: ( ( rule__TopLevel__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:75:1: ( ( rule__TopLevel__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:76:1: ( rule__TopLevel__Group__0 )
            {
             before(grammarAccess.getTopLevelAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:77:1: ( rule__TopLevel__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:77:2: rule__TopLevel__Group__0
            {
            pushFollow(FOLLOW_rule__TopLevel__Group__0_in_ruleTopLevel94);
            rule__TopLevel__Group__0();
            _fsp--;


            }

             after(grammarAccess.getTopLevelAccess().getGroup()); 

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
    // $ANTLR end ruleTopLevel


    // $ANTLR start entryRulePathElement
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:89:1: entryRulePathElement : rulePathElement EOF ;
    public final void entryRulePathElement() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:90:1: ( rulePathElement EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:91:1: rulePathElement EOF
            {
             before(grammarAccess.getPathElementRule()); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement121);
            rulePathElement();
            _fsp--;

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
    // $ANTLR end entryRulePathElement


    // $ANTLR start rulePathElement
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:98:1: rulePathElement : ( ( rule__PathElement__Alternatives ) ) ;
    public final void rulePathElement() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:102:2: ( ( ( rule__PathElement__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:103:1: ( ( rule__PathElement__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:103:1: ( ( rule__PathElement__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:104:1: ( rule__PathElement__Alternatives )
            {
             before(grammarAccess.getPathElementAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:105:1: ( rule__PathElement__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:105:2: rule__PathElement__Alternatives
            {
            pushFollow(FOLLOW_rule__PathElement__Alternatives_in_rulePathElement154);
            rule__PathElement__Alternatives();
            _fsp--;


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
    // $ANTLR end rulePathElement


    // $ANTLR start entryRulePathSequence
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:117:1: entryRulePathSequence : rulePathSequence EOF ;
    public final void entryRulePathSequence() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:118:1: ( rulePathSequence EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:119:1: rulePathSequence EOF
            {
             before(grammarAccess.getPathSequenceRule()); 
            pushFollow(FOLLOW_rulePathSequence_in_entryRulePathSequence181);
            rulePathSequence();
            _fsp--;

             after(grammarAccess.getPathSequenceRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathSequence188); 

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
    // $ANTLR end entryRulePathSequence


    // $ANTLR start rulePathSequence
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:126:1: rulePathSequence : ( ( rule__PathSequence__Group__0 ) ) ;
    public final void rulePathSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:130:2: ( ( ( rule__PathSequence__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:131:1: ( ( rule__PathSequence__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:131:1: ( ( rule__PathSequence__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:132:1: ( rule__PathSequence__Group__0 )
            {
             before(grammarAccess.getPathSequenceAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:133:1: ( rule__PathSequence__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:133:2: rule__PathSequence__Group__0
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__0_in_rulePathSequence214);
            rule__PathSequence__Group__0();
            _fsp--;


            }

             after(grammarAccess.getPathSequenceAccess().getGroup()); 

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
    // $ANTLR end rulePathSequence


    // $ANTLR start entryRuleTmlExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:145:1: entryRuleTmlExpression : ruleTmlExpression EOF ;
    public final void entryRuleTmlExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:146:1: ( ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:147:1: ruleTmlExpression EOF
            {
             before(grammarAccess.getTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression241);
            ruleTmlExpression();
            _fsp--;

             after(grammarAccess.getTmlExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression248); 

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
    // $ANTLR end entryRuleTmlExpression


    // $ANTLR start ruleTmlExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:154:1: ruleTmlExpression : ( rulePathSequence ) ;
    public final void ruleTmlExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:158:2: ( ( rulePathSequence ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:159:1: ( rulePathSequence )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:159:1: ( rulePathSequence )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:160:1: rulePathSequence
            {
             before(grammarAccess.getTmlExpressionAccess().getPathSequenceParserRuleCall()); 
            pushFollow(FOLLOW_rulePathSequence_in_ruleTmlExpression274);
            rulePathSequence();
            _fsp--;

             after(grammarAccess.getTmlExpressionAccess().getPathSequenceParserRuleCall()); 

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
    // $ANTLR end ruleTmlExpression


    // $ANTLR start entryRuleExistsTmlExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:173:1: entryRuleExistsTmlExpression : ruleExistsTmlExpression EOF ;
    public final void entryRuleExistsTmlExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:174:1: ( ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:175:1: ruleExistsTmlExpression EOF
            {
             before(grammarAccess.getExistsTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression300);
            ruleExistsTmlExpression();
            _fsp--;

             after(grammarAccess.getExistsTmlExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression307); 

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
    // $ANTLR end entryRuleExistsTmlExpression


    // $ANTLR start ruleExistsTmlExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:182:1: ruleExistsTmlExpression : ( ( rule__ExistsTmlExpression__Group__0 ) ) ;
    public final void ruleExistsTmlExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:186:2: ( ( ( rule__ExistsTmlExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:187:1: ( ( rule__ExistsTmlExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:187:1: ( ( rule__ExistsTmlExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:188:1: ( rule__ExistsTmlExpression__Group__0 )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:189:1: ( rule__ExistsTmlExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:189:2: rule__ExistsTmlExpression__Group__0
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__0_in_ruleExistsTmlExpression333);
            rule__ExistsTmlExpression__Group__0();
            _fsp--;


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
    // $ANTLR end ruleExistsTmlExpression


    // $ANTLR start entryRuleOrExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:201:1: entryRuleOrExpression : ruleOrExpression EOF ;
    public final void entryRuleOrExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:202:1: ( ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:203:1: ruleOrExpression EOF
            {
             before(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression360);
            ruleOrExpression();
            _fsp--;

             after(grammarAccess.getOrExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression367); 

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
    // $ANTLR end entryRuleOrExpression


    // $ANTLR start ruleOrExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:210:1: ruleOrExpression : ( ( rule__OrExpression__Group__0 ) ) ;
    public final void ruleOrExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:214:2: ( ( ( rule__OrExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:215:1: ( ( rule__OrExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:215:1: ( ( rule__OrExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:216:1: ( rule__OrExpression__Group__0 )
            {
             before(grammarAccess.getOrExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:217:1: ( rule__OrExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:217:2: rule__OrExpression__Group__0
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__0_in_ruleOrExpression393);
            rule__OrExpression__Group__0();
            _fsp--;


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
    // $ANTLR end ruleOrExpression


    // $ANTLR start entryRuleAndExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:229:1: entryRuleAndExpression : ruleAndExpression EOF ;
    public final void entryRuleAndExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:230:1: ( ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:231:1: ruleAndExpression EOF
            {
             before(grammarAccess.getAndExpressionRule()); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression420);
            ruleAndExpression();
            _fsp--;

             after(grammarAccess.getAndExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression427); 

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
    // $ANTLR end entryRuleAndExpression


    // $ANTLR start ruleAndExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:238:1: ruleAndExpression : ( ( rule__AndExpression__Group__0 ) ) ;
    public final void ruleAndExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:242:2: ( ( ( rule__AndExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:243:1: ( ( rule__AndExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:243:1: ( ( rule__AndExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:244:1: ( rule__AndExpression__Group__0 )
            {
             before(grammarAccess.getAndExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:245:1: ( rule__AndExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:245:2: rule__AndExpression__Group__0
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__0_in_ruleAndExpression453);
            rule__AndExpression__Group__0();
            _fsp--;


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
    // $ANTLR end ruleAndExpression


    // $ANTLR start entryRuleEqualityExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:257:1: entryRuleEqualityExpression : ruleEqualityExpression EOF ;
    public final void entryRuleEqualityExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:258:1: ( ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:259:1: ruleEqualityExpression EOF
            {
             before(grammarAccess.getEqualityExpressionRule()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression480);
            ruleEqualityExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression487); 

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
    // $ANTLR end entryRuleEqualityExpression


    // $ANTLR start ruleEqualityExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:266:1: ruleEqualityExpression : ( ( rule__EqualityExpression__Group__0 ) ) ;
    public final void ruleEqualityExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:270:2: ( ( ( rule__EqualityExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:271:1: ( ( rule__EqualityExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:271:1: ( ( rule__EqualityExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:272:1: ( rule__EqualityExpression__Group__0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:273:1: ( rule__EqualityExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:273:2: rule__EqualityExpression__Group__0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__0_in_ruleEqualityExpression513);
            rule__EqualityExpression__Group__0();
            _fsp--;


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
    // $ANTLR end ruleEqualityExpression


    // $ANTLR start entryRuleAdditiveExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:285:1: entryRuleAdditiveExpression : ruleAdditiveExpression EOF ;
    public final void entryRuleAdditiveExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:286:1: ( ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:287:1: ruleAdditiveExpression EOF
            {
             before(grammarAccess.getAdditiveExpressionRule()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression540);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getAdditiveExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression547); 

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
    // $ANTLR end entryRuleAdditiveExpression


    // $ANTLR start ruleAdditiveExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:294:1: ruleAdditiveExpression : ( ( rule__AdditiveExpression__Group__0 ) ) ;
    public final void ruleAdditiveExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:298:2: ( ( ( rule__AdditiveExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:299:1: ( ( rule__AdditiveExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:299:1: ( ( rule__AdditiveExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:300:1: ( rule__AdditiveExpression__Group__0 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:301:1: ( rule__AdditiveExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:301:2: rule__AdditiveExpression__Group__0
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__0_in_ruleAdditiveExpression573);
            rule__AdditiveExpression__Group__0();
            _fsp--;


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
    // $ANTLR end ruleAdditiveExpression


    // $ANTLR start entryRuleMultiplicativeExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:313:1: entryRuleMultiplicativeExpression : ruleMultiplicativeExpression EOF ;
    public final void entryRuleMultiplicativeExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:314:1: ( ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:315:1: ruleMultiplicativeExpression EOF
            {
             before(grammarAccess.getMultiplicativeExpressionRule()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression600);
            ruleMultiplicativeExpression();
            _fsp--;

             after(grammarAccess.getMultiplicativeExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression607); 

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
    // $ANTLR end entryRuleMultiplicativeExpression


    // $ANTLR start ruleMultiplicativeExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:322:1: ruleMultiplicativeExpression : ( ( rule__MultiplicativeExpression__Group__0 ) ) ;
    public final void ruleMultiplicativeExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:326:2: ( ( ( rule__MultiplicativeExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:327:1: ( ( rule__MultiplicativeExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:327:1: ( ( rule__MultiplicativeExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:328:1: ( rule__MultiplicativeExpression__Group__0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:329:1: ( rule__MultiplicativeExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:329:2: rule__MultiplicativeExpression__Group__0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__0_in_ruleMultiplicativeExpression633);
            rule__MultiplicativeExpression__Group__0();
            _fsp--;


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
    // $ANTLR end ruleMultiplicativeExpression


    // $ANTLR start entryRuleUnaryExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:341:1: entryRuleUnaryExpression : ruleUnaryExpression EOF ;
    public final void entryRuleUnaryExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:342:1: ( ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:343:1: ruleUnaryExpression EOF
            {
             before(grammarAccess.getUnaryExpressionRule()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression660);
            ruleUnaryExpression();
            _fsp--;

             after(grammarAccess.getUnaryExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression667); 

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
    // $ANTLR end entryRuleUnaryExpression


    // $ANTLR start ruleUnaryExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:350:1: ruleUnaryExpression : ( ( rule__UnaryExpression__Alternatives ) ) ;
    public final void ruleUnaryExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:354:2: ( ( ( rule__UnaryExpression__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:355:1: ( ( rule__UnaryExpression__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:355:1: ( ( rule__UnaryExpression__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:356:1: ( rule__UnaryExpression__Alternatives )
            {
             before(grammarAccess.getUnaryExpressionAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:357:1: ( rule__UnaryExpression__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:357:2: rule__UnaryExpression__Alternatives
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Alternatives_in_ruleUnaryExpression693);
            rule__UnaryExpression__Alternatives();
            _fsp--;


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
    // $ANTLR end ruleUnaryExpression


    // $ANTLR start entryRulePrimaryExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:369:1: entryRulePrimaryExpression : rulePrimaryExpression EOF ;
    public final void entryRulePrimaryExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:370:1: ( rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:371:1: rulePrimaryExpression EOF
            {
             before(grammarAccess.getPrimaryExpressionRule()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression720);
            rulePrimaryExpression();
            _fsp--;

             after(grammarAccess.getPrimaryExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression727); 

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
    // $ANTLR end entryRulePrimaryExpression


    // $ANTLR start rulePrimaryExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:378:1: rulePrimaryExpression : ( ( rule__PrimaryExpression__Alternatives ) ) ;
    public final void rulePrimaryExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:382:2: ( ( ( rule__PrimaryExpression__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:383:1: ( ( rule__PrimaryExpression__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:383:1: ( ( rule__PrimaryExpression__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:384:1: ( rule__PrimaryExpression__Alternatives )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:385:1: ( rule__PrimaryExpression__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:385:2: rule__PrimaryExpression__Alternatives
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Alternatives_in_rulePrimaryExpression753);
            rule__PrimaryExpression__Alternatives();
            _fsp--;


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
    // $ANTLR end rulePrimaryExpression


    // $ANTLR start entryRuleFunctionCall
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:397:1: entryRuleFunctionCall : ruleFunctionCall EOF ;
    public final void entryRuleFunctionCall() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:398:1: ( ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:399:1: ruleFunctionCall EOF
            {
             before(grammarAccess.getFunctionCallRule()); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall780);
            ruleFunctionCall();
            _fsp--;

             after(grammarAccess.getFunctionCallRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall787); 

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
    // $ANTLR end entryRuleFunctionCall


    // $ANTLR start ruleFunctionCall
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:406:1: ruleFunctionCall : ( ( rule__FunctionCall__Group__0 ) ) ;
    public final void ruleFunctionCall() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:410:2: ( ( ( rule__FunctionCall__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:411:1: ( ( rule__FunctionCall__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:411:1: ( ( rule__FunctionCall__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:412:1: ( rule__FunctionCall__Group__0 )
            {
             before(grammarAccess.getFunctionCallAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:413:1: ( rule__FunctionCall__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:413:2: rule__FunctionCall__Group__0
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__0_in_ruleFunctionCall813);
            rule__FunctionCall__Group__0();
            _fsp--;


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
    // $ANTLR end ruleFunctionCall


    // $ANTLR start entryRuleLiteral
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:427:1: entryRuleLiteral : ruleLiteral EOF ;
    public final void entryRuleLiteral() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:428:1: ( ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:429:1: ruleLiteral EOF
            {
             before(grammarAccess.getLiteralRule()); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral842);
            ruleLiteral();
            _fsp--;

             after(grammarAccess.getLiteralRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral849); 

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
    // $ANTLR end entryRuleLiteral


    // $ANTLR start ruleLiteral
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:436:1: ruleLiteral : ( ( rule__Literal__Alternatives ) ) ;
    public final void ruleLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:440:2: ( ( ( rule__Literal__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:441:1: ( ( rule__Literal__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:441:1: ( ( rule__Literal__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:442:1: ( rule__Literal__Alternatives )
            {
             before(grammarAccess.getLiteralAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:443:1: ( rule__Literal__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:443:2: rule__Literal__Alternatives
            {
            pushFollow(FOLLOW_rule__Literal__Alternatives_in_ruleLiteral875);
            rule__Literal__Alternatives();
            _fsp--;


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
    // $ANTLR end ruleLiteral


    // $ANTLR start rule__PathElement__Alternatives
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:455:1: rule__PathElement__Alternatives : ( ( RULE_ID ) | ( '.' ) | ( '..' ) );
    public final void rule__PathElement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:459:1: ( ( RULE_ID ) | ( '.' ) | ( '..' ) )
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
                    new NoViableAltException("455:1: rule__PathElement__Alternatives : ( ( RULE_ID ) | ( '.' ) | ( '..' ) );", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:460:1: ( RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:460:1: ( RULE_ID )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:461:1: RULE_ID
                    {
                     before(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PathElement__Alternatives911); 
                     after(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:466:6: ( '.' )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:466:6: ( '.' )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:467:1: '.'
                    {
                     before(grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 
                    match(input,10,FOLLOW_10_in_rule__PathElement__Alternatives929); 
                     after(grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:474:6: ( '..' )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:474:6: ( '..' )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:475:1: '..'
                    {
                     before(grammarAccess.getPathElementAccess().getFullStopFullStopKeyword_2()); 
                    match(input,11,FOLLOW_11_in_rule__PathElement__Alternatives949); 
                     after(grammarAccess.getPathElementAccess().getFullStopFullStopKeyword_2()); 

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
    // $ANTLR end rule__PathElement__Alternatives


    // $ANTLR start rule__EqualityExpression__Alternatives_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:487:1: rule__EqualityExpression__Alternatives_2 : ( ( ( rule__EqualityExpression__Group_2_0__0 ) ) | ( ( rule__EqualityExpression__Group_2_1__0 ) ) );
    public final void rule__EqualityExpression__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:491:1: ( ( ( rule__EqualityExpression__Group_2_0__0 ) ) | ( ( rule__EqualityExpression__Group_2_1__0 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==24) ) {
                alt2=1;
            }
            else if ( (LA2_0==25) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("487:1: rule__EqualityExpression__Alternatives_2 : ( ( ( rule__EqualityExpression__Group_2_0__0 ) ) | ( ( rule__EqualityExpression__Group_2_1__0 ) ) );", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:492:1: ( ( rule__EqualityExpression__Group_2_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:492:1: ( ( rule__EqualityExpression__Group_2_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:493:1: ( rule__EqualityExpression__Group_2_0__0 )
                    {
                     before(grammarAccess.getEqualityExpressionAccess().getGroup_2_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:494:1: ( rule__EqualityExpression__Group_2_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:494:2: rule__EqualityExpression__Group_2_0__0
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Group_2_0__0_in_rule__EqualityExpression__Alternatives_2983);
                    rule__EqualityExpression__Group_2_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getEqualityExpressionAccess().getGroup_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:498:6: ( ( rule__EqualityExpression__Group_2_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:498:6: ( ( rule__EqualityExpression__Group_2_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:499:1: ( rule__EqualityExpression__Group_2_1__0 )
                    {
                     before(grammarAccess.getEqualityExpressionAccess().getGroup_2_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:500:1: ( rule__EqualityExpression__Group_2_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:500:2: rule__EqualityExpression__Group_2_1__0
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Group_2_1__0_in_rule__EqualityExpression__Alternatives_21001);
                    rule__EqualityExpression__Group_2_1__0();
                    _fsp--;


                    }

                     after(grammarAccess.getEqualityExpressionAccess().getGroup_2_1()); 

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
    // $ANTLR end rule__EqualityExpression__Alternatives_2


    // $ANTLR start rule__AdditiveExpression__Alternatives_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:509:1: rule__AdditiveExpression__Alternatives_2 : ( ( ( rule__AdditiveExpression__Group_2_0__0 ) ) | ( ( rule__AdditiveExpression__Group_2_1__0 ) ) );
    public final void rule__AdditiveExpression__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:513:1: ( ( ( rule__AdditiveExpression__Group_2_0__0 ) ) | ( ( rule__AdditiveExpression__Group_2_1__0 ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==16) ) {
                alt3=1;
            }
            else if ( (LA3_0==17) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("509:1: rule__AdditiveExpression__Alternatives_2 : ( ( ( rule__AdditiveExpression__Group_2_0__0 ) ) | ( ( rule__AdditiveExpression__Group_2_1__0 ) ) );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:514:1: ( ( rule__AdditiveExpression__Group_2_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:514:1: ( ( rule__AdditiveExpression__Group_2_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:515:1: ( rule__AdditiveExpression__Group_2_0__0 )
                    {
                     before(grammarAccess.getAdditiveExpressionAccess().getGroup_2_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:516:1: ( rule__AdditiveExpression__Group_2_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:516:2: rule__AdditiveExpression__Group_2_0__0
                    {
                    pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_0__0_in_rule__AdditiveExpression__Alternatives_21034);
                    rule__AdditiveExpression__Group_2_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getAdditiveExpressionAccess().getGroup_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:520:6: ( ( rule__AdditiveExpression__Group_2_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:520:6: ( ( rule__AdditiveExpression__Group_2_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:521:1: ( rule__AdditiveExpression__Group_2_1__0 )
                    {
                     before(grammarAccess.getAdditiveExpressionAccess().getGroup_2_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:522:1: ( rule__AdditiveExpression__Group_2_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:522:2: rule__AdditiveExpression__Group_2_1__0
                    {
                    pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_1__0_in_rule__AdditiveExpression__Alternatives_21052);
                    rule__AdditiveExpression__Group_2_1__0();
                    _fsp--;


                    }

                     after(grammarAccess.getAdditiveExpressionAccess().getGroup_2_1()); 

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
    // $ANTLR end rule__AdditiveExpression__Alternatives_2


    // $ANTLR start rule__MultiplicativeExpression__Alternatives_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:531:1: rule__MultiplicativeExpression__Alternatives_2 : ( ( ( rule__MultiplicativeExpression__Group_2_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_2_1__0 ) ) );
    public final void rule__MultiplicativeExpression__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:535:1: ( ( ( rule__MultiplicativeExpression__Group_2_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_2_1__0 ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==26) ) {
                alt4=1;
            }
            else if ( (LA4_0==13) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("531:1: rule__MultiplicativeExpression__Alternatives_2 : ( ( ( rule__MultiplicativeExpression__Group_2_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_2_1__0 ) ) );", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:536:1: ( ( rule__MultiplicativeExpression__Group_2_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:536:1: ( ( rule__MultiplicativeExpression__Group_2_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:537:1: ( rule__MultiplicativeExpression__Group_2_0__0 )
                    {
                     before(grammarAccess.getMultiplicativeExpressionAccess().getGroup_2_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:538:1: ( rule__MultiplicativeExpression__Group_2_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:538:2: rule__MultiplicativeExpression__Group_2_0__0
                    {
                    pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_0__0_in_rule__MultiplicativeExpression__Alternatives_21085);
                    rule__MultiplicativeExpression__Group_2_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getMultiplicativeExpressionAccess().getGroup_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:542:6: ( ( rule__MultiplicativeExpression__Group_2_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:542:6: ( ( rule__MultiplicativeExpression__Group_2_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:543:1: ( rule__MultiplicativeExpression__Group_2_1__0 )
                    {
                     before(grammarAccess.getMultiplicativeExpressionAccess().getGroup_2_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:544:1: ( rule__MultiplicativeExpression__Group_2_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:544:2: rule__MultiplicativeExpression__Group_2_1__0
                    {
                    pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_1__0_in_rule__MultiplicativeExpression__Alternatives_21103);
                    rule__MultiplicativeExpression__Group_2_1__0();
                    _fsp--;


                    }

                     after(grammarAccess.getMultiplicativeExpressionAccess().getGroup_2_1()); 

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
    // $ANTLR end rule__MultiplicativeExpression__Alternatives_2


    // $ANTLR start rule__UnaryExpression__Alternatives
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:553:1: rule__UnaryExpression__Alternatives : ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) );
    public final void rule__UnaryExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:557:1: ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==27) ) {
                alt5=1;
            }
            else if ( ((LA5_0>=RULE_ID && LA5_0<=RULE_LITERALSTRING)||LA5_0==12||LA5_0==15||LA5_0==18||(LA5_0>=28 && LA5_0<=33)) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("553:1: rule__UnaryExpression__Alternatives : ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) );", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:558:1: ( ( rule__UnaryExpression__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:558:1: ( ( rule__UnaryExpression__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:559:1: ( rule__UnaryExpression__Group_0__0 )
                    {
                     before(grammarAccess.getUnaryExpressionAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:560:1: ( rule__UnaryExpression__Group_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:560:2: rule__UnaryExpression__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__UnaryExpression__Group_0__0_in_rule__UnaryExpression__Alternatives1136);
                    rule__UnaryExpression__Group_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getUnaryExpressionAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:564:6: ( rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:564:6: ( rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:565:1: rulePrimaryExpression
                    {
                     before(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1()); 
                    pushFollow(FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__Alternatives1154);
                    rulePrimaryExpression();
                    _fsp--;

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
    // $ANTLR end rule__UnaryExpression__Alternatives


    // $ANTLR start rule__PrimaryExpression__Alternatives
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:575:1: rule__PrimaryExpression__Alternatives : ( ( ( rule__PrimaryExpression__Group_0__0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) );
    public final void rule__PrimaryExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:579:1: ( ( ( rule__PrimaryExpression__Group_0__0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0>=RULE_ID && LA6_0<=RULE_LITERALSTRING)||LA6_0==12||LA6_0==15||(LA6_0>=28 && LA6_0<=33)) ) {
                alt6=1;
            }
            else if ( (LA6_0==18) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("575:1: rule__PrimaryExpression__Alternatives : ( ( ( rule__PrimaryExpression__Group_0__0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) );", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:580:1: ( ( rule__PrimaryExpression__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:580:1: ( ( rule__PrimaryExpression__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:581:1: ( rule__PrimaryExpression__Group_0__0 )
                    {
                     before(grammarAccess.getPrimaryExpressionAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:582:1: ( rule__PrimaryExpression__Group_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:582:2: rule__PrimaryExpression__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__PrimaryExpression__Group_0__0_in_rule__PrimaryExpression__Alternatives1186);
                    rule__PrimaryExpression__Group_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getPrimaryExpressionAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:586:6: ( ( rule__PrimaryExpression__Group_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:586:6: ( ( rule__PrimaryExpression__Group_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:587:1: ( rule__PrimaryExpression__Group_1__0 )
                    {
                     before(grammarAccess.getPrimaryExpressionAccess().getGroup_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:588:1: ( rule__PrimaryExpression__Group_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:588:2: rule__PrimaryExpression__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__0_in_rule__PrimaryExpression__Alternatives1204);
                    rule__PrimaryExpression__Group_1__0();
                    _fsp--;


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
    // $ANTLR end rule__PrimaryExpression__Alternatives


    // $ANTLR start rule__Literal__Alternatives
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:597:1: rule__Literal__Alternatives : ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__Group_5__0 ) ) | ( ( rule__Literal__Group_6__0 ) ) | ( ( rule__Literal__Group_7__0 ) ) | ( ( rule__Literal__Group_8__0 ) ) | ( ( rule__Literal__Group_9__0 ) ) | ( ( rule__Literal__Group_10__0 ) ) );
    public final void rule__Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:601:1: ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__Group_5__0 ) ) | ( ( rule__Literal__Group_6__0 ) ) | ( ( rule__Literal__Group_7__0 ) ) | ( ( rule__Literal__Group_8__0 ) ) | ( ( rule__Literal__Group_9__0 ) ) | ( ( rule__Literal__Group_10__0 ) ) )
            int alt7=11;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt7=1;
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt7=2;
                }
                break;
            case 28:
                {
                alt7=3;
                }
                break;
            case RULE_ID:
                {
                alt7=4;
                }
                break;
            case 15:
                {
                alt7=5;
                }
                break;
            case 12:
                {
                alt7=6;
                }
                break;
            case 29:
                {
                alt7=7;
                }
                break;
            case 30:
                {
                alt7=8;
                }
                break;
            case 31:
                {
                alt7=9;
                }
                break;
            case 32:
                {
                alt7=10;
                }
                break;
            case 33:
                {
                alt7=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("597:1: rule__Literal__Alternatives : ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__Group_5__0 ) ) | ( ( rule__Literal__Group_6__0 ) ) | ( ( rule__Literal__Group_7__0 ) ) | ( ( rule__Literal__Group_8__0 ) ) | ( ( rule__Literal__Group_9__0 ) ) | ( ( rule__Literal__Group_10__0 ) ) );", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:602:1: ( ( rule__Literal__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:602:1: ( ( rule__Literal__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:603:1: ( rule__Literal__Group_0__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:604:1: ( rule__Literal__Group_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:604:2: rule__Literal__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_0__0_in_rule__Literal__Alternatives1237);
                    rule__Literal__Group_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:608:6: ( ( rule__Literal__Group_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:608:6: ( ( rule__Literal__Group_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:609:1: ( rule__Literal__Group_1__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:610:1: ( rule__Literal__Group_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:610:2: rule__Literal__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_1__0_in_rule__Literal__Alternatives1255);
                    rule__Literal__Group_1__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:614:6: ( ( rule__Literal__Group_2__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:614:6: ( ( rule__Literal__Group_2__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:615:1: ( rule__Literal__Group_2__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_2()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:616:1: ( rule__Literal__Group_2__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:616:2: rule__Literal__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_2__0_in_rule__Literal__Alternatives1273);
                    rule__Literal__Group_2__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:620:6: ( ruleFunctionCall )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:620:6: ( ruleFunctionCall )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:621:1: ruleFunctionCall
                    {
                     before(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3()); 
                    pushFollow(FOLLOW_ruleFunctionCall_in_rule__Literal__Alternatives1291);
                    ruleFunctionCall();
                    _fsp--;

                     after(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3()); 

                    }


                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:626:6: ( ( rule__Literal__Group_4__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:626:6: ( ( rule__Literal__Group_4__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:627:1: ( rule__Literal__Group_4__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_4()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:628:1: ( rule__Literal__Group_4__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:628:2: rule__Literal__Group_4__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_4__0_in_rule__Literal__Alternatives1308);
                    rule__Literal__Group_4__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_4()); 

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:632:6: ( ( rule__Literal__Group_5__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:632:6: ( ( rule__Literal__Group_5__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:633:1: ( rule__Literal__Group_5__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_5()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:634:1: ( rule__Literal__Group_5__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:634:2: rule__Literal__Group_5__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_5__0_in_rule__Literal__Alternatives1326);
                    rule__Literal__Group_5__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_5()); 

                    }


                    }
                    break;
                case 7 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:638:6: ( ( rule__Literal__Group_6__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:638:6: ( ( rule__Literal__Group_6__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:639:1: ( rule__Literal__Group_6__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_6()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:640:1: ( rule__Literal__Group_6__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:640:2: rule__Literal__Group_6__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_6__0_in_rule__Literal__Alternatives1344);
                    rule__Literal__Group_6__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_6()); 

                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:644:6: ( ( rule__Literal__Group_7__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:644:6: ( ( rule__Literal__Group_7__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:645:1: ( rule__Literal__Group_7__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_7()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:646:1: ( rule__Literal__Group_7__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:646:2: rule__Literal__Group_7__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_7__0_in_rule__Literal__Alternatives1362);
                    rule__Literal__Group_7__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_7()); 

                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:650:6: ( ( rule__Literal__Group_8__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:650:6: ( ( rule__Literal__Group_8__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:651:1: ( rule__Literal__Group_8__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_8()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:652:1: ( rule__Literal__Group_8__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:652:2: rule__Literal__Group_8__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_8__0_in_rule__Literal__Alternatives1380);
                    rule__Literal__Group_8__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_8()); 

                    }


                    }
                    break;
                case 10 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:656:6: ( ( rule__Literal__Group_9__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:656:6: ( ( rule__Literal__Group_9__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:657:1: ( rule__Literal__Group_9__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_9()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:658:1: ( rule__Literal__Group_9__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:658:2: rule__Literal__Group_9__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_9__0_in_rule__Literal__Alternatives1398);
                    rule__Literal__Group_9__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_9()); 

                    }


                    }
                    break;
                case 11 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:662:6: ( ( rule__Literal__Group_10__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:662:6: ( ( rule__Literal__Group_10__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:663:1: ( rule__Literal__Group_10__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_10()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:664:1: ( rule__Literal__Group_10__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:664:2: rule__Literal__Group_10__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_10__0_in_rule__Literal__Alternatives1416);
                    rule__Literal__Group_10__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_10()); 

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
    // $ANTLR end rule__Literal__Alternatives


    // $ANTLR start rule__TopLevel__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:675:1: rule__TopLevel__Group__0 : rule__TopLevel__Group__0__Impl rule__TopLevel__Group__1 ;
    public final void rule__TopLevel__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:679:1: ( rule__TopLevel__Group__0__Impl rule__TopLevel__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:680:2: rule__TopLevel__Group__0__Impl rule__TopLevel__Group__1
            {
            pushFollow(FOLLOW_rule__TopLevel__Group__0__Impl_in_rule__TopLevel__Group__01447);
            rule__TopLevel__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__TopLevel__Group__1_in_rule__TopLevel__Group__01450);
            rule__TopLevel__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TopLevel__Group__0


    // $ANTLR start rule__TopLevel__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:687:1: rule__TopLevel__Group__0__Impl : ( () ) ;
    public final void rule__TopLevel__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:691:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:692:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:692:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:693:1: ()
            {
             before(grammarAccess.getTopLevelAccess().getTopLevelAction_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:694:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:696:1: 
            {
            }

             after(grammarAccess.getTopLevelAccess().getTopLevelAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TopLevel__Group__0__Impl


    // $ANTLR start rule__TopLevel__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:706:1: rule__TopLevel__Group__1 : rule__TopLevel__Group__1__Impl ;
    public final void rule__TopLevel__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:710:1: ( rule__TopLevel__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:711:2: rule__TopLevel__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__TopLevel__Group__1__Impl_in_rule__TopLevel__Group__11508);
            rule__TopLevel__Group__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TopLevel__Group__1


    // $ANTLR start rule__TopLevel__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:717:1: rule__TopLevel__Group__1__Impl : ( ( rule__TopLevel__ToplevelExpressionAssignment_1 ) ) ;
    public final void rule__TopLevel__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:721:1: ( ( ( rule__TopLevel__ToplevelExpressionAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:722:1: ( ( rule__TopLevel__ToplevelExpressionAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:722:1: ( ( rule__TopLevel__ToplevelExpressionAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:723:1: ( rule__TopLevel__ToplevelExpressionAssignment_1 )
            {
             before(grammarAccess.getTopLevelAccess().getToplevelExpressionAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:724:1: ( rule__TopLevel__ToplevelExpressionAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:724:2: rule__TopLevel__ToplevelExpressionAssignment_1
            {
            pushFollow(FOLLOW_rule__TopLevel__ToplevelExpressionAssignment_1_in_rule__TopLevel__Group__1__Impl1535);
            rule__TopLevel__ToplevelExpressionAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getTopLevelAccess().getToplevelExpressionAssignment_1()); 

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
    // $ANTLR end rule__TopLevel__Group__1__Impl


    // $ANTLR start rule__PathSequence__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:738:1: rule__PathSequence__Group__0 : rule__PathSequence__Group__0__Impl rule__PathSequence__Group__1 ;
    public final void rule__PathSequence__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:742:1: ( rule__PathSequence__Group__0__Impl rule__PathSequence__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:743:2: rule__PathSequence__Group__0__Impl rule__PathSequence__Group__1
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__0__Impl_in_rule__PathSequence__Group__01569);
            rule__PathSequence__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group__1_in_rule__PathSequence__Group__01572);
            rule__PathSequence__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PathSequence__Group__0


    // $ANTLR start rule__PathSequence__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:750:1: rule__PathSequence__Group__0__Impl : ( '[' ) ;
    public final void rule__PathSequence__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:754:1: ( ( '[' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:755:1: ( '[' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:755:1: ( '[' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:756:1: '['
            {
             before(grammarAccess.getPathSequenceAccess().getLeftSquareBracketKeyword_0()); 
            match(input,12,FOLLOW_12_in_rule__PathSequence__Group__0__Impl1600); 
             after(grammarAccess.getPathSequenceAccess().getLeftSquareBracketKeyword_0()); 

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
    // $ANTLR end rule__PathSequence__Group__0__Impl


    // $ANTLR start rule__PathSequence__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:769:1: rule__PathSequence__Group__1 : rule__PathSequence__Group__1__Impl rule__PathSequence__Group__2 ;
    public final void rule__PathSequence__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:773:1: ( rule__PathSequence__Group__1__Impl rule__PathSequence__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:774:2: rule__PathSequence__Group__1__Impl rule__PathSequence__Group__2
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__1__Impl_in_rule__PathSequence__Group__11631);
            rule__PathSequence__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group__2_in_rule__PathSequence__Group__11634);
            rule__PathSequence__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PathSequence__Group__1


    // $ANTLR start rule__PathSequence__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:781:1: rule__PathSequence__Group__1__Impl : ( ( '/' )? ) ;
    public final void rule__PathSequence__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:785:1: ( ( ( '/' )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:786:1: ( ( '/' )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:786:1: ( ( '/' )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:787:1: ( '/' )?
            {
             before(grammarAccess.getPathSequenceAccess().getSolidusKeyword_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:788:1: ( '/' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==13) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:789:2: '/'
                    {
                    match(input,13,FOLLOW_13_in_rule__PathSequence__Group__1__Impl1663); 

                    }
                    break;

            }

             after(grammarAccess.getPathSequenceAccess().getSolidusKeyword_1()); 

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
    // $ANTLR end rule__PathSequence__Group__1__Impl


    // $ANTLR start rule__PathSequence__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:800:1: rule__PathSequence__Group__2 : rule__PathSequence__Group__2__Impl rule__PathSequence__Group__3 ;
    public final void rule__PathSequence__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:804:1: ( rule__PathSequence__Group__2__Impl rule__PathSequence__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:805:2: rule__PathSequence__Group__2__Impl rule__PathSequence__Group__3
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__2__Impl_in_rule__PathSequence__Group__21696);
            rule__PathSequence__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group__3_in_rule__PathSequence__Group__21699);
            rule__PathSequence__Group__3();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PathSequence__Group__2


    // $ANTLR start rule__PathSequence__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:812:1: rule__PathSequence__Group__2__Impl : ( rulePathElement ) ;
    public final void rule__PathSequence__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:816:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:817:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:817:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:818:1: rulePathElement
            {
             before(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_2()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__PathSequence__Group__2__Impl1726);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_2()); 

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
    // $ANTLR end rule__PathSequence__Group__2__Impl


    // $ANTLR start rule__PathSequence__Group__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:829:1: rule__PathSequence__Group__3 : rule__PathSequence__Group__3__Impl rule__PathSequence__Group__4 ;
    public final void rule__PathSequence__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:833:1: ( rule__PathSequence__Group__3__Impl rule__PathSequence__Group__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:834:2: rule__PathSequence__Group__3__Impl rule__PathSequence__Group__4
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__3__Impl_in_rule__PathSequence__Group__31755);
            rule__PathSequence__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group__4_in_rule__PathSequence__Group__31758);
            rule__PathSequence__Group__4();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PathSequence__Group__3


    // $ANTLR start rule__PathSequence__Group__3__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:841:1: rule__PathSequence__Group__3__Impl : ( ( rule__PathSequence__Group_3__0 )* ) ;
    public final void rule__PathSequence__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:845:1: ( ( ( rule__PathSequence__Group_3__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:846:1: ( ( rule__PathSequence__Group_3__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:846:1: ( ( rule__PathSequence__Group_3__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:847:1: ( rule__PathSequence__Group_3__0 )*
            {
             before(grammarAccess.getPathSequenceAccess().getGroup_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:848:1: ( rule__PathSequence__Group_3__0 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==13) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:848:2: rule__PathSequence__Group_3__0
            	    {
            	    pushFollow(FOLLOW_rule__PathSequence__Group_3__0_in_rule__PathSequence__Group__3__Impl1785);
            	    rule__PathSequence__Group_3__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

             after(grammarAccess.getPathSequenceAccess().getGroup_3()); 

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
    // $ANTLR end rule__PathSequence__Group__3__Impl


    // $ANTLR start rule__PathSequence__Group__4
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:858:1: rule__PathSequence__Group__4 : rule__PathSequence__Group__4__Impl ;
    public final void rule__PathSequence__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:862:1: ( rule__PathSequence__Group__4__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:863:2: rule__PathSequence__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__4__Impl_in_rule__PathSequence__Group__41816);
            rule__PathSequence__Group__4__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PathSequence__Group__4


    // $ANTLR start rule__PathSequence__Group__4__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:869:1: rule__PathSequence__Group__4__Impl : ( ']' ) ;
    public final void rule__PathSequence__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:873:1: ( ( ']' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:874:1: ( ']' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:874:1: ( ']' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:875:1: ']'
            {
             before(grammarAccess.getPathSequenceAccess().getRightSquareBracketKeyword_4()); 
            match(input,14,FOLLOW_14_in_rule__PathSequence__Group__4__Impl1844); 
             after(grammarAccess.getPathSequenceAccess().getRightSquareBracketKeyword_4()); 

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
    // $ANTLR end rule__PathSequence__Group__4__Impl


    // $ANTLR start rule__PathSequence__Group_3__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:898:1: rule__PathSequence__Group_3__0 : rule__PathSequence__Group_3__0__Impl rule__PathSequence__Group_3__1 ;
    public final void rule__PathSequence__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:902:1: ( rule__PathSequence__Group_3__0__Impl rule__PathSequence__Group_3__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:903:2: rule__PathSequence__Group_3__0__Impl rule__PathSequence__Group_3__1
            {
            pushFollow(FOLLOW_rule__PathSequence__Group_3__0__Impl_in_rule__PathSequence__Group_3__01885);
            rule__PathSequence__Group_3__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group_3__1_in_rule__PathSequence__Group_3__01888);
            rule__PathSequence__Group_3__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PathSequence__Group_3__0


    // $ANTLR start rule__PathSequence__Group_3__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:910:1: rule__PathSequence__Group_3__0__Impl : ( '/' ) ;
    public final void rule__PathSequence__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:914:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:915:1: ( '/' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:915:1: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:916:1: '/'
            {
             before(grammarAccess.getPathSequenceAccess().getSolidusKeyword_3_0()); 
            match(input,13,FOLLOW_13_in_rule__PathSequence__Group_3__0__Impl1916); 
             after(grammarAccess.getPathSequenceAccess().getSolidusKeyword_3_0()); 

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
    // $ANTLR end rule__PathSequence__Group_3__0__Impl


    // $ANTLR start rule__PathSequence__Group_3__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:929:1: rule__PathSequence__Group_3__1 : rule__PathSequence__Group_3__1__Impl ;
    public final void rule__PathSequence__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:933:1: ( rule__PathSequence__Group_3__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:934:2: rule__PathSequence__Group_3__1__Impl
            {
            pushFollow(FOLLOW_rule__PathSequence__Group_3__1__Impl_in_rule__PathSequence__Group_3__11947);
            rule__PathSequence__Group_3__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PathSequence__Group_3__1


    // $ANTLR start rule__PathSequence__Group_3__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:940:1: rule__PathSequence__Group_3__1__Impl : ( rulePathElement ) ;
    public final void rule__PathSequence__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:944:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:945:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:945:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:946:1: rulePathElement
            {
             before(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_3_1()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__PathSequence__Group_3__1__Impl1974);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_3_1()); 

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
    // $ANTLR end rule__PathSequence__Group_3__1__Impl


    // $ANTLR start rule__ExistsTmlExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:961:1: rule__ExistsTmlExpression__Group__0 : rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1 ;
    public final void rule__ExistsTmlExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:965:1: ( rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:966:2: rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__0__Impl_in_rule__ExistsTmlExpression__Group__02007);
            rule__ExistsTmlExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__1_in_rule__ExistsTmlExpression__Group__02010);
            rule__ExistsTmlExpression__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__Group__0


    // $ANTLR start rule__ExistsTmlExpression__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:973:1: rule__ExistsTmlExpression__Group__0__Impl : ( '?' ) ;
    public final void rule__ExistsTmlExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:977:1: ( ( '?' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:978:1: ( '?' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:978:1: ( '?' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:979:1: '?'
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getQuestionMarkKeyword_0()); 
            match(input,15,FOLLOW_15_in_rule__ExistsTmlExpression__Group__0__Impl2038); 
             after(grammarAccess.getExistsTmlExpressionAccess().getQuestionMarkKeyword_0()); 

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
    // $ANTLR end rule__ExistsTmlExpression__Group__0__Impl


    // $ANTLR start rule__ExistsTmlExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:992:1: rule__ExistsTmlExpression__Group__1 : rule__ExistsTmlExpression__Group__1__Impl ;
    public final void rule__ExistsTmlExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:996:1: ( rule__ExistsTmlExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:997:2: rule__ExistsTmlExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__1__Impl_in_rule__ExistsTmlExpression__Group__12069);
            rule__ExistsTmlExpression__Group__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__Group__1


    // $ANTLR start rule__ExistsTmlExpression__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1003:1: rule__ExistsTmlExpression__Group__1__Impl : ( ruleTmlExpression ) ;
    public final void rule__ExistsTmlExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1007:1: ( ( ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1008:1: ( ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1008:1: ( ruleTmlExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1009:1: ruleTmlExpression
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getTmlExpressionParserRuleCall_1()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_rule__ExistsTmlExpression__Group__1__Impl2096);
            ruleTmlExpression();
            _fsp--;

             after(grammarAccess.getExistsTmlExpressionAccess().getTmlExpressionParserRuleCall_1()); 

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
    // $ANTLR end rule__ExistsTmlExpression__Group__1__Impl


    // $ANTLR start rule__OrExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1024:1: rule__OrExpression__Group__0 : rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1 ;
    public final void rule__OrExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1028:1: ( rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1029:2: rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__0__Impl_in_rule__OrExpression__Group__02129);
            rule__OrExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group__1_in_rule__OrExpression__Group__02132);
            rule__OrExpression__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__OrExpression__Group__0


    // $ANTLR start rule__OrExpression__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1036:1: rule__OrExpression__Group__0__Impl : ( () ) ;
    public final void rule__OrExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1040:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1041:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1041:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1042:1: ()
            {
             before(grammarAccess.getOrExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1043:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1045:1: 
            {
            }

             after(grammarAccess.getOrExpressionAccess().getExpressionAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__OrExpression__Group__0__Impl


    // $ANTLR start rule__OrExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1055:1: rule__OrExpression__Group__1 : rule__OrExpression__Group__1__Impl rule__OrExpression__Group__2 ;
    public final void rule__OrExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1059:1: ( rule__OrExpression__Group__1__Impl rule__OrExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1060:2: rule__OrExpression__Group__1__Impl rule__OrExpression__Group__2
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__1__Impl_in_rule__OrExpression__Group__12190);
            rule__OrExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group__2_in_rule__OrExpression__Group__12193);
            rule__OrExpression__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__OrExpression__Group__1


    // $ANTLR start rule__OrExpression__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1067:1: rule__OrExpression__Group__1__Impl : ( ( rule__OrExpression__ParametersAssignment_1 ) ) ;
    public final void rule__OrExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1071:1: ( ( ( rule__OrExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1072:1: ( ( rule__OrExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1072:1: ( ( rule__OrExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1073:1: ( rule__OrExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1074:1: ( rule__OrExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1074:2: rule__OrExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__OrExpression__ParametersAssignment_1_in_rule__OrExpression__Group__1__Impl2220);
            rule__OrExpression__ParametersAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getOrExpressionAccess().getParametersAssignment_1()); 

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
    // $ANTLR end rule__OrExpression__Group__1__Impl


    // $ANTLR start rule__OrExpression__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1084:1: rule__OrExpression__Group__2 : rule__OrExpression__Group__2__Impl ;
    public final void rule__OrExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1088:1: ( rule__OrExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1089:2: rule__OrExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__2__Impl_in_rule__OrExpression__Group__22250);
            rule__OrExpression__Group__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__OrExpression__Group__2


    // $ANTLR start rule__OrExpression__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1095:1: rule__OrExpression__Group__2__Impl : ( ( rule__OrExpression__Group_2__0 )* ) ;
    public final void rule__OrExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1099:1: ( ( ( rule__OrExpression__Group_2__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1100:1: ( ( rule__OrExpression__Group_2__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1100:1: ( ( rule__OrExpression__Group_2__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1101:1: ( rule__OrExpression__Group_2__0 )*
            {
             before(grammarAccess.getOrExpressionAccess().getGroup_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1102:1: ( rule__OrExpression__Group_2__0 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==22) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1102:2: rule__OrExpression__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__OrExpression__Group_2__0_in_rule__OrExpression__Group__2__Impl2277);
            	    rule__OrExpression__Group_2__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getOrExpressionAccess().getGroup_2()); 

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
    // $ANTLR end rule__OrExpression__Group__2__Impl


    // $ANTLR start rule__OrExpression__Group_2__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1118:1: rule__OrExpression__Group_2__0 : rule__OrExpression__Group_2__0__Impl rule__OrExpression__Group_2__1 ;
    public final void rule__OrExpression__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1122:1: ( rule__OrExpression__Group_2__0__Impl rule__OrExpression__Group_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1123:2: rule__OrExpression__Group_2__0__Impl rule__OrExpression__Group_2__1
            {
            pushFollow(FOLLOW_rule__OrExpression__Group_2__0__Impl_in_rule__OrExpression__Group_2__02314);
            rule__OrExpression__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group_2__1_in_rule__OrExpression__Group_2__02317);
            rule__OrExpression__Group_2__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__OrExpression__Group_2__0


    // $ANTLR start rule__OrExpression__Group_2__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1130:1: rule__OrExpression__Group_2__0__Impl : ( ( rule__OrExpression__OperationsAssignment_2_0 ) ) ;
    public final void rule__OrExpression__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1134:1: ( ( ( rule__OrExpression__OperationsAssignment_2_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1135:1: ( ( rule__OrExpression__OperationsAssignment_2_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1135:1: ( ( rule__OrExpression__OperationsAssignment_2_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1136:1: ( rule__OrExpression__OperationsAssignment_2_0 )
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsAssignment_2_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1137:1: ( rule__OrExpression__OperationsAssignment_2_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1137:2: rule__OrExpression__OperationsAssignment_2_0
            {
            pushFollow(FOLLOW_rule__OrExpression__OperationsAssignment_2_0_in_rule__OrExpression__Group_2__0__Impl2344);
            rule__OrExpression__OperationsAssignment_2_0();
            _fsp--;


            }

             after(grammarAccess.getOrExpressionAccess().getOperationsAssignment_2_0()); 

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
    // $ANTLR end rule__OrExpression__Group_2__0__Impl


    // $ANTLR start rule__OrExpression__Group_2__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1147:1: rule__OrExpression__Group_2__1 : rule__OrExpression__Group_2__1__Impl ;
    public final void rule__OrExpression__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1151:1: ( rule__OrExpression__Group_2__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1152:2: rule__OrExpression__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__OrExpression__Group_2__1__Impl_in_rule__OrExpression__Group_2__12374);
            rule__OrExpression__Group_2__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__OrExpression__Group_2__1


    // $ANTLR start rule__OrExpression__Group_2__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1158:1: rule__OrExpression__Group_2__1__Impl : ( ( rule__OrExpression__ParametersAssignment_2_1 ) ) ;
    public final void rule__OrExpression__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1162:1: ( ( ( rule__OrExpression__ParametersAssignment_2_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1163:1: ( ( rule__OrExpression__ParametersAssignment_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1163:1: ( ( rule__OrExpression__ParametersAssignment_2_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1164:1: ( rule__OrExpression__ParametersAssignment_2_1 )
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAssignment_2_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1165:1: ( rule__OrExpression__ParametersAssignment_2_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1165:2: rule__OrExpression__ParametersAssignment_2_1
            {
            pushFollow(FOLLOW_rule__OrExpression__ParametersAssignment_2_1_in_rule__OrExpression__Group_2__1__Impl2401);
            rule__OrExpression__ParametersAssignment_2_1();
            _fsp--;


            }

             after(grammarAccess.getOrExpressionAccess().getParametersAssignment_2_1()); 

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
    // $ANTLR end rule__OrExpression__Group_2__1__Impl


    // $ANTLR start rule__AndExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1179:1: rule__AndExpression__Group__0 : rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1 ;
    public final void rule__AndExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1183:1: ( rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1184:2: rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__0__Impl_in_rule__AndExpression__Group__02435);
            rule__AndExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group__1_in_rule__AndExpression__Group__02438);
            rule__AndExpression__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AndExpression__Group__0


    // $ANTLR start rule__AndExpression__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1191:1: rule__AndExpression__Group__0__Impl : ( () ) ;
    public final void rule__AndExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1195:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1196:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1196:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1197:1: ()
            {
             before(grammarAccess.getAndExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1198:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1200:1: 
            {
            }

             after(grammarAccess.getAndExpressionAccess().getExpressionAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AndExpression__Group__0__Impl


    // $ANTLR start rule__AndExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1210:1: rule__AndExpression__Group__1 : rule__AndExpression__Group__1__Impl rule__AndExpression__Group__2 ;
    public final void rule__AndExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1214:1: ( rule__AndExpression__Group__1__Impl rule__AndExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1215:2: rule__AndExpression__Group__1__Impl rule__AndExpression__Group__2
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__1__Impl_in_rule__AndExpression__Group__12496);
            rule__AndExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group__2_in_rule__AndExpression__Group__12499);
            rule__AndExpression__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AndExpression__Group__1


    // $ANTLR start rule__AndExpression__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1222:1: rule__AndExpression__Group__1__Impl : ( ( rule__AndExpression__ParametersAssignment_1 ) ) ;
    public final void rule__AndExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1226:1: ( ( ( rule__AndExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1227:1: ( ( rule__AndExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1227:1: ( ( rule__AndExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1228:1: ( rule__AndExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getAndExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1229:1: ( rule__AndExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1229:2: rule__AndExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__AndExpression__ParametersAssignment_1_in_rule__AndExpression__Group__1__Impl2526);
            rule__AndExpression__ParametersAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getAndExpressionAccess().getParametersAssignment_1()); 

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
    // $ANTLR end rule__AndExpression__Group__1__Impl


    // $ANTLR start rule__AndExpression__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1239:1: rule__AndExpression__Group__2 : rule__AndExpression__Group__2__Impl ;
    public final void rule__AndExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1243:1: ( rule__AndExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1244:2: rule__AndExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__2__Impl_in_rule__AndExpression__Group__22556);
            rule__AndExpression__Group__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AndExpression__Group__2


    // $ANTLR start rule__AndExpression__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1250:1: rule__AndExpression__Group__2__Impl : ( ( rule__AndExpression__Group_2__0 )* ) ;
    public final void rule__AndExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1254:1: ( ( ( rule__AndExpression__Group_2__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1255:1: ( ( rule__AndExpression__Group_2__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1255:1: ( ( rule__AndExpression__Group_2__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1256:1: ( rule__AndExpression__Group_2__0 )*
            {
             before(grammarAccess.getAndExpressionAccess().getGroup_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1257:1: ( rule__AndExpression__Group_2__0 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==23) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1257:2: rule__AndExpression__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__AndExpression__Group_2__0_in_rule__AndExpression__Group__2__Impl2583);
            	    rule__AndExpression__Group_2__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

             after(grammarAccess.getAndExpressionAccess().getGroup_2()); 

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
    // $ANTLR end rule__AndExpression__Group__2__Impl


    // $ANTLR start rule__AndExpression__Group_2__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1273:1: rule__AndExpression__Group_2__0 : rule__AndExpression__Group_2__0__Impl rule__AndExpression__Group_2__1 ;
    public final void rule__AndExpression__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1277:1: ( rule__AndExpression__Group_2__0__Impl rule__AndExpression__Group_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1278:2: rule__AndExpression__Group_2__0__Impl rule__AndExpression__Group_2__1
            {
            pushFollow(FOLLOW_rule__AndExpression__Group_2__0__Impl_in_rule__AndExpression__Group_2__02620);
            rule__AndExpression__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group_2__1_in_rule__AndExpression__Group_2__02623);
            rule__AndExpression__Group_2__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AndExpression__Group_2__0


    // $ANTLR start rule__AndExpression__Group_2__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1285:1: rule__AndExpression__Group_2__0__Impl : ( ( rule__AndExpression__OperationsAssignment_2_0 ) ) ;
    public final void rule__AndExpression__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1289:1: ( ( ( rule__AndExpression__OperationsAssignment_2_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1290:1: ( ( rule__AndExpression__OperationsAssignment_2_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1290:1: ( ( rule__AndExpression__OperationsAssignment_2_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1291:1: ( rule__AndExpression__OperationsAssignment_2_0 )
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsAssignment_2_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1292:1: ( rule__AndExpression__OperationsAssignment_2_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1292:2: rule__AndExpression__OperationsAssignment_2_0
            {
            pushFollow(FOLLOW_rule__AndExpression__OperationsAssignment_2_0_in_rule__AndExpression__Group_2__0__Impl2650);
            rule__AndExpression__OperationsAssignment_2_0();
            _fsp--;


            }

             after(grammarAccess.getAndExpressionAccess().getOperationsAssignment_2_0()); 

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
    // $ANTLR end rule__AndExpression__Group_2__0__Impl


    // $ANTLR start rule__AndExpression__Group_2__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1302:1: rule__AndExpression__Group_2__1 : rule__AndExpression__Group_2__1__Impl ;
    public final void rule__AndExpression__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1306:1: ( rule__AndExpression__Group_2__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1307:2: rule__AndExpression__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__AndExpression__Group_2__1__Impl_in_rule__AndExpression__Group_2__12680);
            rule__AndExpression__Group_2__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AndExpression__Group_2__1


    // $ANTLR start rule__AndExpression__Group_2__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1313:1: rule__AndExpression__Group_2__1__Impl : ( ( rule__AndExpression__ParametersAssignment_2_1 ) ) ;
    public final void rule__AndExpression__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1317:1: ( ( ( rule__AndExpression__ParametersAssignment_2_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1318:1: ( ( rule__AndExpression__ParametersAssignment_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1318:1: ( ( rule__AndExpression__ParametersAssignment_2_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1319:1: ( rule__AndExpression__ParametersAssignment_2_1 )
            {
             before(grammarAccess.getAndExpressionAccess().getParametersAssignment_2_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1320:1: ( rule__AndExpression__ParametersAssignment_2_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1320:2: rule__AndExpression__ParametersAssignment_2_1
            {
            pushFollow(FOLLOW_rule__AndExpression__ParametersAssignment_2_1_in_rule__AndExpression__Group_2__1__Impl2707);
            rule__AndExpression__ParametersAssignment_2_1();
            _fsp--;


            }

             after(grammarAccess.getAndExpressionAccess().getParametersAssignment_2_1()); 

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
    // $ANTLR end rule__AndExpression__Group_2__1__Impl


    // $ANTLR start rule__EqualityExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1334:1: rule__EqualityExpression__Group__0 : rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1 ;
    public final void rule__EqualityExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1338:1: ( rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1339:2: rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__0__Impl_in_rule__EqualityExpression__Group__02741);
            rule__EqualityExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group__1_in_rule__EqualityExpression__Group__02744);
            rule__EqualityExpression__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__Group__0


    // $ANTLR start rule__EqualityExpression__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1346:1: rule__EqualityExpression__Group__0__Impl : ( () ) ;
    public final void rule__EqualityExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1350:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1351:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1351:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1352:1: ()
            {
             before(grammarAccess.getEqualityExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1353:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1355:1: 
            {
            }

             after(grammarAccess.getEqualityExpressionAccess().getExpressionAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__Group__0__Impl


    // $ANTLR start rule__EqualityExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1365:1: rule__EqualityExpression__Group__1 : rule__EqualityExpression__Group__1__Impl rule__EqualityExpression__Group__2 ;
    public final void rule__EqualityExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1369:1: ( rule__EqualityExpression__Group__1__Impl rule__EqualityExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1370:2: rule__EqualityExpression__Group__1__Impl rule__EqualityExpression__Group__2
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__1__Impl_in_rule__EqualityExpression__Group__12802);
            rule__EqualityExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group__2_in_rule__EqualityExpression__Group__12805);
            rule__EqualityExpression__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__Group__1


    // $ANTLR start rule__EqualityExpression__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1377:1: rule__EqualityExpression__Group__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_1 ) ) ;
    public final void rule__EqualityExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1381:1: ( ( ( rule__EqualityExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1382:1: ( ( rule__EqualityExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1382:1: ( ( rule__EqualityExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1383:1: ( rule__EqualityExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1384:1: ( rule__EqualityExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1384:2: rule__EqualityExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_1_in_rule__EqualityExpression__Group__1__Impl2832);
            rule__EqualityExpression__ParametersAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1()); 

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
    // $ANTLR end rule__EqualityExpression__Group__1__Impl


    // $ANTLR start rule__EqualityExpression__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1394:1: rule__EqualityExpression__Group__2 : rule__EqualityExpression__Group__2__Impl ;
    public final void rule__EqualityExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1398:1: ( rule__EqualityExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1399:2: rule__EqualityExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__2__Impl_in_rule__EqualityExpression__Group__22862);
            rule__EqualityExpression__Group__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__Group__2


    // $ANTLR start rule__EqualityExpression__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1405:1: rule__EqualityExpression__Group__2__Impl : ( ( rule__EqualityExpression__Alternatives_2 )? ) ;
    public final void rule__EqualityExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1409:1: ( ( ( rule__EqualityExpression__Alternatives_2 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1410:1: ( ( rule__EqualityExpression__Alternatives_2 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1410:1: ( ( rule__EqualityExpression__Alternatives_2 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1411:1: ( rule__EqualityExpression__Alternatives_2 )?
            {
             before(grammarAccess.getEqualityExpressionAccess().getAlternatives_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1412:1: ( rule__EqualityExpression__Alternatives_2 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( ((LA12_0>=24 && LA12_0<=25)) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1412:2: rule__EqualityExpression__Alternatives_2
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Alternatives_2_in_rule__EqualityExpression__Group__2__Impl2889);
                    rule__EqualityExpression__Alternatives_2();
                    _fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEqualityExpressionAccess().getAlternatives_2()); 

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
    // $ANTLR end rule__EqualityExpression__Group__2__Impl


    // $ANTLR start rule__EqualityExpression__Group_2_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1428:1: rule__EqualityExpression__Group_2_0__0 : rule__EqualityExpression__Group_2_0__0__Impl rule__EqualityExpression__Group_2_0__1 ;
    public final void rule__EqualityExpression__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1432:1: ( rule__EqualityExpression__Group_2_0__0__Impl rule__EqualityExpression__Group_2_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1433:2: rule__EqualityExpression__Group_2_0__0__Impl rule__EqualityExpression__Group_2_0__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_0__0__Impl_in_rule__EqualityExpression__Group_2_0__02926);
            rule__EqualityExpression__Group_2_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_0__1_in_rule__EqualityExpression__Group_2_0__02929);
            rule__EqualityExpression__Group_2_0__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__Group_2_0__0


    // $ANTLR start rule__EqualityExpression__Group_2_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1440:1: rule__EqualityExpression__Group_2_0__0__Impl : ( ( rule__EqualityExpression__OperationsAssignment_2_0_0 ) ) ;
    public final void rule__EqualityExpression__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1444:1: ( ( ( rule__EqualityExpression__OperationsAssignment_2_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1445:1: ( ( rule__EqualityExpression__OperationsAssignment_2_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1445:1: ( ( rule__EqualityExpression__OperationsAssignment_2_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1446:1: ( rule__EqualityExpression__OperationsAssignment_2_0_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_2_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1447:1: ( rule__EqualityExpression__OperationsAssignment_2_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1447:2: rule__EqualityExpression__OperationsAssignment_2_0_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__OperationsAssignment_2_0_0_in_rule__EqualityExpression__Group_2_0__0__Impl2956);
            rule__EqualityExpression__OperationsAssignment_2_0_0();
            _fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_2_0_0()); 

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
    // $ANTLR end rule__EqualityExpression__Group_2_0__0__Impl


    // $ANTLR start rule__EqualityExpression__Group_2_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1457:1: rule__EqualityExpression__Group_2_0__1 : rule__EqualityExpression__Group_2_0__1__Impl ;
    public final void rule__EqualityExpression__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1461:1: ( rule__EqualityExpression__Group_2_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1462:2: rule__EqualityExpression__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_0__1__Impl_in_rule__EqualityExpression__Group_2_0__12986);
            rule__EqualityExpression__Group_2_0__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__Group_2_0__1


    // $ANTLR start rule__EqualityExpression__Group_2_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1468:1: rule__EqualityExpression__Group_2_0__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_2_0_1 ) ) ;
    public final void rule__EqualityExpression__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1472:1: ( ( ( rule__EqualityExpression__ParametersAssignment_2_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1473:1: ( ( rule__EqualityExpression__ParametersAssignment_2_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1473:1: ( ( rule__EqualityExpression__ParametersAssignment_2_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1474:1: ( rule__EqualityExpression__ParametersAssignment_2_0_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_2_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1475:1: ( rule__EqualityExpression__ParametersAssignment_2_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1475:2: rule__EqualityExpression__ParametersAssignment_2_0_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_2_0_1_in_rule__EqualityExpression__Group_2_0__1__Impl3013);
            rule__EqualityExpression__ParametersAssignment_2_0_1();
            _fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_2_0_1()); 

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
    // $ANTLR end rule__EqualityExpression__Group_2_0__1__Impl


    // $ANTLR start rule__EqualityExpression__Group_2_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1489:1: rule__EqualityExpression__Group_2_1__0 : rule__EqualityExpression__Group_2_1__0__Impl rule__EqualityExpression__Group_2_1__1 ;
    public final void rule__EqualityExpression__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1493:1: ( rule__EqualityExpression__Group_2_1__0__Impl rule__EqualityExpression__Group_2_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1494:2: rule__EqualityExpression__Group_2_1__0__Impl rule__EqualityExpression__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_1__0__Impl_in_rule__EqualityExpression__Group_2_1__03047);
            rule__EqualityExpression__Group_2_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_1__1_in_rule__EqualityExpression__Group_2_1__03050);
            rule__EqualityExpression__Group_2_1__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__Group_2_1__0


    // $ANTLR start rule__EqualityExpression__Group_2_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1501:1: rule__EqualityExpression__Group_2_1__0__Impl : ( ( rule__EqualityExpression__OperationsAssignment_2_1_0 ) ) ;
    public final void rule__EqualityExpression__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1505:1: ( ( ( rule__EqualityExpression__OperationsAssignment_2_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1506:1: ( ( rule__EqualityExpression__OperationsAssignment_2_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1506:1: ( ( rule__EqualityExpression__OperationsAssignment_2_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1507:1: ( rule__EqualityExpression__OperationsAssignment_2_1_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_2_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1508:1: ( rule__EqualityExpression__OperationsAssignment_2_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1508:2: rule__EqualityExpression__OperationsAssignment_2_1_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__OperationsAssignment_2_1_0_in_rule__EqualityExpression__Group_2_1__0__Impl3077);
            rule__EqualityExpression__OperationsAssignment_2_1_0();
            _fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_2_1_0()); 

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
    // $ANTLR end rule__EqualityExpression__Group_2_1__0__Impl


    // $ANTLR start rule__EqualityExpression__Group_2_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1518:1: rule__EqualityExpression__Group_2_1__1 : rule__EqualityExpression__Group_2_1__1__Impl ;
    public final void rule__EqualityExpression__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1522:1: ( rule__EqualityExpression__Group_2_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1523:2: rule__EqualityExpression__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_1__1__Impl_in_rule__EqualityExpression__Group_2_1__13107);
            rule__EqualityExpression__Group_2_1__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__Group_2_1__1


    // $ANTLR start rule__EqualityExpression__Group_2_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1529:1: rule__EqualityExpression__Group_2_1__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_2_1_1 ) ) ;
    public final void rule__EqualityExpression__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1533:1: ( ( ( rule__EqualityExpression__ParametersAssignment_2_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1534:1: ( ( rule__EqualityExpression__ParametersAssignment_2_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1534:1: ( ( rule__EqualityExpression__ParametersAssignment_2_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1535:1: ( rule__EqualityExpression__ParametersAssignment_2_1_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_2_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1536:1: ( rule__EqualityExpression__ParametersAssignment_2_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1536:2: rule__EqualityExpression__ParametersAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_2_1_1_in_rule__EqualityExpression__Group_2_1__1__Impl3134);
            rule__EqualityExpression__ParametersAssignment_2_1_1();
            _fsp--;


            }

             after(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_2_1_1()); 

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
    // $ANTLR end rule__EqualityExpression__Group_2_1__1__Impl


    // $ANTLR start rule__AdditiveExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1550:1: rule__AdditiveExpression__Group__0 : rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1 ;
    public final void rule__AdditiveExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1554:1: ( rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1555:2: rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__0__Impl_in_rule__AdditiveExpression__Group__03168);
            rule__AdditiveExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group__1_in_rule__AdditiveExpression__Group__03171);
            rule__AdditiveExpression__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AdditiveExpression__Group__0


    // $ANTLR start rule__AdditiveExpression__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1562:1: rule__AdditiveExpression__Group__0__Impl : ( () ) ;
    public final void rule__AdditiveExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1566:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1567:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1567:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1568:1: ()
            {
             before(grammarAccess.getAdditiveExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1569:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1571:1: 
            {
            }

             after(grammarAccess.getAdditiveExpressionAccess().getExpressionAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AdditiveExpression__Group__0__Impl


    // $ANTLR start rule__AdditiveExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1581:1: rule__AdditiveExpression__Group__1 : rule__AdditiveExpression__Group__1__Impl rule__AdditiveExpression__Group__2 ;
    public final void rule__AdditiveExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1585:1: ( rule__AdditiveExpression__Group__1__Impl rule__AdditiveExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1586:2: rule__AdditiveExpression__Group__1__Impl rule__AdditiveExpression__Group__2
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__1__Impl_in_rule__AdditiveExpression__Group__13229);
            rule__AdditiveExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group__2_in_rule__AdditiveExpression__Group__13232);
            rule__AdditiveExpression__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AdditiveExpression__Group__1


    // $ANTLR start rule__AdditiveExpression__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1593:1: rule__AdditiveExpression__Group__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_1 ) ) ;
    public final void rule__AdditiveExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1597:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1598:1: ( ( rule__AdditiveExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1598:1: ( ( rule__AdditiveExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1599:1: ( rule__AdditiveExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1600:1: ( rule__AdditiveExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1600:2: rule__AdditiveExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_in_rule__AdditiveExpression__Group__1__Impl3259);
            rule__AdditiveExpression__ParametersAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1()); 

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
    // $ANTLR end rule__AdditiveExpression__Group__1__Impl


    // $ANTLR start rule__AdditiveExpression__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1610:1: rule__AdditiveExpression__Group__2 : rule__AdditiveExpression__Group__2__Impl ;
    public final void rule__AdditiveExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1614:1: ( rule__AdditiveExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1615:2: rule__AdditiveExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__2__Impl_in_rule__AdditiveExpression__Group__23289);
            rule__AdditiveExpression__Group__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AdditiveExpression__Group__2


    // $ANTLR start rule__AdditiveExpression__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1621:1: rule__AdditiveExpression__Group__2__Impl : ( ( rule__AdditiveExpression__Alternatives_2 )* ) ;
    public final void rule__AdditiveExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1625:1: ( ( ( rule__AdditiveExpression__Alternatives_2 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1626:1: ( ( rule__AdditiveExpression__Alternatives_2 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1626:1: ( ( rule__AdditiveExpression__Alternatives_2 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1627:1: ( rule__AdditiveExpression__Alternatives_2 )*
            {
             before(grammarAccess.getAdditiveExpressionAccess().getAlternatives_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1628:1: ( rule__AdditiveExpression__Alternatives_2 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>=16 && LA13_0<=17)) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1628:2: rule__AdditiveExpression__Alternatives_2
            	    {
            	    pushFollow(FOLLOW_rule__AdditiveExpression__Alternatives_2_in_rule__AdditiveExpression__Group__2__Impl3316);
            	    rule__AdditiveExpression__Alternatives_2();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getAdditiveExpressionAccess().getAlternatives_2()); 

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
    // $ANTLR end rule__AdditiveExpression__Group__2__Impl


    // $ANTLR start rule__AdditiveExpression__Group_2_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1644:1: rule__AdditiveExpression__Group_2_0__0 : rule__AdditiveExpression__Group_2_0__0__Impl rule__AdditiveExpression__Group_2_0__1 ;
    public final void rule__AdditiveExpression__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1648:1: ( rule__AdditiveExpression__Group_2_0__0__Impl rule__AdditiveExpression__Group_2_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1649:2: rule__AdditiveExpression__Group_2_0__0__Impl rule__AdditiveExpression__Group_2_0__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_0__0__Impl_in_rule__AdditiveExpression__Group_2_0__03353);
            rule__AdditiveExpression__Group_2_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_0__1_in_rule__AdditiveExpression__Group_2_0__03356);
            rule__AdditiveExpression__Group_2_0__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AdditiveExpression__Group_2_0__0


    // $ANTLR start rule__AdditiveExpression__Group_2_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1656:1: rule__AdditiveExpression__Group_2_0__0__Impl : ( '+' ) ;
    public final void rule__AdditiveExpression__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1660:1: ( ( '+' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1661:1: ( '+' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1661:1: ( '+' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1662:1: '+'
            {
             before(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_2_0_0()); 
            match(input,16,FOLLOW_16_in_rule__AdditiveExpression__Group_2_0__0__Impl3384); 
             after(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_2_0_0()); 

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
    // $ANTLR end rule__AdditiveExpression__Group_2_0__0__Impl


    // $ANTLR start rule__AdditiveExpression__Group_2_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1675:1: rule__AdditiveExpression__Group_2_0__1 : rule__AdditiveExpression__Group_2_0__1__Impl ;
    public final void rule__AdditiveExpression__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1679:1: ( rule__AdditiveExpression__Group_2_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1680:2: rule__AdditiveExpression__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_0__1__Impl_in_rule__AdditiveExpression__Group_2_0__13415);
            rule__AdditiveExpression__Group_2_0__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AdditiveExpression__Group_2_0__1


    // $ANTLR start rule__AdditiveExpression__Group_2_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1686:1: rule__AdditiveExpression__Group_2_0__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_2_0_1 ) ) ;
    public final void rule__AdditiveExpression__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1690:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_2_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1691:1: ( ( rule__AdditiveExpression__ParametersAssignment_2_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1691:1: ( ( rule__AdditiveExpression__ParametersAssignment_2_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1692:1: ( rule__AdditiveExpression__ParametersAssignment_2_0_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_2_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1693:1: ( rule__AdditiveExpression__ParametersAssignment_2_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1693:2: rule__AdditiveExpression__ParametersAssignment_2_0_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_2_0_1_in_rule__AdditiveExpression__Group_2_0__1__Impl3442);
            rule__AdditiveExpression__ParametersAssignment_2_0_1();
            _fsp--;


            }

             after(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_2_0_1()); 

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
    // $ANTLR end rule__AdditiveExpression__Group_2_0__1__Impl


    // $ANTLR start rule__AdditiveExpression__Group_2_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1707:1: rule__AdditiveExpression__Group_2_1__0 : rule__AdditiveExpression__Group_2_1__0__Impl rule__AdditiveExpression__Group_2_1__1 ;
    public final void rule__AdditiveExpression__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1711:1: ( rule__AdditiveExpression__Group_2_1__0__Impl rule__AdditiveExpression__Group_2_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1712:2: rule__AdditiveExpression__Group_2_1__0__Impl rule__AdditiveExpression__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_1__0__Impl_in_rule__AdditiveExpression__Group_2_1__03476);
            rule__AdditiveExpression__Group_2_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_1__1_in_rule__AdditiveExpression__Group_2_1__03479);
            rule__AdditiveExpression__Group_2_1__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AdditiveExpression__Group_2_1__0


    // $ANTLR start rule__AdditiveExpression__Group_2_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1719:1: rule__AdditiveExpression__Group_2_1__0__Impl : ( '-' ) ;
    public final void rule__AdditiveExpression__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1723:1: ( ( '-' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1724:1: ( '-' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1724:1: ( '-' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1725:1: '-'
            {
             before(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_2_1_0()); 
            match(input,17,FOLLOW_17_in_rule__AdditiveExpression__Group_2_1__0__Impl3507); 
             after(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_2_1_0()); 

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
    // $ANTLR end rule__AdditiveExpression__Group_2_1__0__Impl


    // $ANTLR start rule__AdditiveExpression__Group_2_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1738:1: rule__AdditiveExpression__Group_2_1__1 : rule__AdditiveExpression__Group_2_1__1__Impl ;
    public final void rule__AdditiveExpression__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1742:1: ( rule__AdditiveExpression__Group_2_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1743:2: rule__AdditiveExpression__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_1__1__Impl_in_rule__AdditiveExpression__Group_2_1__13538);
            rule__AdditiveExpression__Group_2_1__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__AdditiveExpression__Group_2_1__1


    // $ANTLR start rule__AdditiveExpression__Group_2_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1749:1: rule__AdditiveExpression__Group_2_1__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_2_1_1 ) ) ;
    public final void rule__AdditiveExpression__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1753:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_2_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1754:1: ( ( rule__AdditiveExpression__ParametersAssignment_2_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1754:1: ( ( rule__AdditiveExpression__ParametersAssignment_2_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1755:1: ( rule__AdditiveExpression__ParametersAssignment_2_1_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_2_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1756:1: ( rule__AdditiveExpression__ParametersAssignment_2_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1756:2: rule__AdditiveExpression__ParametersAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_2_1_1_in_rule__AdditiveExpression__Group_2_1__1__Impl3565);
            rule__AdditiveExpression__ParametersAssignment_2_1_1();
            _fsp--;


            }

             after(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_2_1_1()); 

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
    // $ANTLR end rule__AdditiveExpression__Group_2_1__1__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1770:1: rule__MultiplicativeExpression__Group__0 : rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1 ;
    public final void rule__MultiplicativeExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1774:1: ( rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1775:2: rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__0__Impl_in_rule__MultiplicativeExpression__Group__03599);
            rule__MultiplicativeExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__1_in_rule__MultiplicativeExpression__Group__03602);
            rule__MultiplicativeExpression__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__Group__0


    // $ANTLR start rule__MultiplicativeExpression__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1782:1: rule__MultiplicativeExpression__Group__0__Impl : ( () ) ;
    public final void rule__MultiplicativeExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1786:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1787:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1787:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1788:1: ()
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1789:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1791:1: 
            {
            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getExpressionAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__Group__0__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1801:1: rule__MultiplicativeExpression__Group__1 : rule__MultiplicativeExpression__Group__1__Impl rule__MultiplicativeExpression__Group__2 ;
    public final void rule__MultiplicativeExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1805:1: ( rule__MultiplicativeExpression__Group__1__Impl rule__MultiplicativeExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1806:2: rule__MultiplicativeExpression__Group__1__Impl rule__MultiplicativeExpression__Group__2
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__1__Impl_in_rule__MultiplicativeExpression__Group__13660);
            rule__MultiplicativeExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__2_in_rule__MultiplicativeExpression__Group__13663);
            rule__MultiplicativeExpression__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__Group__1


    // $ANTLR start rule__MultiplicativeExpression__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1813:1: rule__MultiplicativeExpression__Group__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1817:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1818:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1818:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1819:1: ( rule__MultiplicativeExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1820:1: ( rule__MultiplicativeExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1820:2: rule__MultiplicativeExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_in_rule__MultiplicativeExpression__Group__1__Impl3690);
            rule__MultiplicativeExpression__ParametersAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1()); 

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
    // $ANTLR end rule__MultiplicativeExpression__Group__1__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1830:1: rule__MultiplicativeExpression__Group__2 : rule__MultiplicativeExpression__Group__2__Impl ;
    public final void rule__MultiplicativeExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1834:1: ( rule__MultiplicativeExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1835:2: rule__MultiplicativeExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__2__Impl_in_rule__MultiplicativeExpression__Group__23720);
            rule__MultiplicativeExpression__Group__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__Group__2


    // $ANTLR start rule__MultiplicativeExpression__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1841:1: rule__MultiplicativeExpression__Group__2__Impl : ( ( rule__MultiplicativeExpression__Alternatives_2 )* ) ;
    public final void rule__MultiplicativeExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1845:1: ( ( ( rule__MultiplicativeExpression__Alternatives_2 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1846:1: ( ( rule__MultiplicativeExpression__Alternatives_2 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1846:1: ( ( rule__MultiplicativeExpression__Alternatives_2 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1847:1: ( rule__MultiplicativeExpression__Alternatives_2 )*
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getAlternatives_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1848:1: ( rule__MultiplicativeExpression__Alternatives_2 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==13||LA14_0==26) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1848:2: rule__MultiplicativeExpression__Alternatives_2
            	    {
            	    pushFollow(FOLLOW_rule__MultiplicativeExpression__Alternatives_2_in_rule__MultiplicativeExpression__Group__2__Impl3747);
            	    rule__MultiplicativeExpression__Alternatives_2();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

             after(grammarAccess.getMultiplicativeExpressionAccess().getAlternatives_2()); 

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
    // $ANTLR end rule__MultiplicativeExpression__Group__2__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group_2_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1864:1: rule__MultiplicativeExpression__Group_2_0__0 : rule__MultiplicativeExpression__Group_2_0__0__Impl rule__MultiplicativeExpression__Group_2_0__1 ;
    public final void rule__MultiplicativeExpression__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1868:1: ( rule__MultiplicativeExpression__Group_2_0__0__Impl rule__MultiplicativeExpression__Group_2_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1869:2: rule__MultiplicativeExpression__Group_2_0__0__Impl rule__MultiplicativeExpression__Group_2_0__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_0__0__Impl_in_rule__MultiplicativeExpression__Group_2_0__03784);
            rule__MultiplicativeExpression__Group_2_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_0__1_in_rule__MultiplicativeExpression__Group_2_0__03787);
            rule__MultiplicativeExpression__Group_2_0__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__Group_2_0__0


    // $ANTLR start rule__MultiplicativeExpression__Group_2_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1876:1: rule__MultiplicativeExpression__Group_2_0__0__Impl : ( ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1880:1: ( ( ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1881:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1881:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1882:1: ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_2_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1883:1: ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1883:2: rule__MultiplicativeExpression__OperationsAssignment_2_0_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_2_0_0_in_rule__MultiplicativeExpression__Group_2_0__0__Impl3814);
            rule__MultiplicativeExpression__OperationsAssignment_2_0_0();
            _fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_2_0_0()); 

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
    // $ANTLR end rule__MultiplicativeExpression__Group_2_0__0__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group_2_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1893:1: rule__MultiplicativeExpression__Group_2_0__1 : rule__MultiplicativeExpression__Group_2_0__1__Impl ;
    public final void rule__MultiplicativeExpression__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1897:1: ( rule__MultiplicativeExpression__Group_2_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1898:2: rule__MultiplicativeExpression__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_0__1__Impl_in_rule__MultiplicativeExpression__Group_2_0__13844);
            rule__MultiplicativeExpression__Group_2_0__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__Group_2_0__1


    // $ANTLR start rule__MultiplicativeExpression__Group_2_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1904:1: rule__MultiplicativeExpression__Group_2_0__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1908:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1909:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1909:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1910:1: ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_2_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1911:1: ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1911:2: rule__MultiplicativeExpression__ParametersAssignment_2_0_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_2_0_1_in_rule__MultiplicativeExpression__Group_2_0__1__Impl3871);
            rule__MultiplicativeExpression__ParametersAssignment_2_0_1();
            _fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_2_0_1()); 

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
    // $ANTLR end rule__MultiplicativeExpression__Group_2_0__1__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group_2_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1925:1: rule__MultiplicativeExpression__Group_2_1__0 : rule__MultiplicativeExpression__Group_2_1__0__Impl rule__MultiplicativeExpression__Group_2_1__1 ;
    public final void rule__MultiplicativeExpression__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1929:1: ( rule__MultiplicativeExpression__Group_2_1__0__Impl rule__MultiplicativeExpression__Group_2_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1930:2: rule__MultiplicativeExpression__Group_2_1__0__Impl rule__MultiplicativeExpression__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_1__0__Impl_in_rule__MultiplicativeExpression__Group_2_1__03905);
            rule__MultiplicativeExpression__Group_2_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_1__1_in_rule__MultiplicativeExpression__Group_2_1__03908);
            rule__MultiplicativeExpression__Group_2_1__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__Group_2_1__0


    // $ANTLR start rule__MultiplicativeExpression__Group_2_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1937:1: rule__MultiplicativeExpression__Group_2_1__0__Impl : ( ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1941:1: ( ( ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1942:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1942:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1943:1: ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_2_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1944:1: ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1944:2: rule__MultiplicativeExpression__OperationsAssignment_2_1_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_2_1_0_in_rule__MultiplicativeExpression__Group_2_1__0__Impl3935);
            rule__MultiplicativeExpression__OperationsAssignment_2_1_0();
            _fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_2_1_0()); 

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
    // $ANTLR end rule__MultiplicativeExpression__Group_2_1__0__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group_2_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1954:1: rule__MultiplicativeExpression__Group_2_1__1 : rule__MultiplicativeExpression__Group_2_1__1__Impl ;
    public final void rule__MultiplicativeExpression__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1958:1: ( rule__MultiplicativeExpression__Group_2_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1959:2: rule__MultiplicativeExpression__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_1__1__Impl_in_rule__MultiplicativeExpression__Group_2_1__13965);
            rule__MultiplicativeExpression__Group_2_1__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__Group_2_1__1


    // $ANTLR start rule__MultiplicativeExpression__Group_2_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1965:1: rule__MultiplicativeExpression__Group_2_1__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1969:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1970:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1970:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1971:1: ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_2_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1972:1: ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1972:2: rule__MultiplicativeExpression__ParametersAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_2_1_1_in_rule__MultiplicativeExpression__Group_2_1__1__Impl3992);
            rule__MultiplicativeExpression__ParametersAssignment_2_1_1();
            _fsp--;


            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_2_1_1()); 

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
    // $ANTLR end rule__MultiplicativeExpression__Group_2_1__1__Impl


    // $ANTLR start rule__UnaryExpression__Group_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1986:1: rule__UnaryExpression__Group_0__0 : rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1 ;
    public final void rule__UnaryExpression__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1990:1: ( rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1991:2: rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__0__Impl_in_rule__UnaryExpression__Group_0__04026);
            rule__UnaryExpression__Group_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__1_in_rule__UnaryExpression__Group_0__04029);
            rule__UnaryExpression__Group_0__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__UnaryExpression__Group_0__0


    // $ANTLR start rule__UnaryExpression__Group_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1998:1: rule__UnaryExpression__Group_0__0__Impl : ( () ) ;
    public final void rule__UnaryExpression__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2002:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2003:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2003:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2004:1: ()
            {
             before(grammarAccess.getUnaryExpressionAccess().getExpressionAction_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2005:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2007:1: 
            {
            }

             after(grammarAccess.getUnaryExpressionAccess().getExpressionAction_0_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__UnaryExpression__Group_0__0__Impl


    // $ANTLR start rule__UnaryExpression__Group_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2017:1: rule__UnaryExpression__Group_0__1 : rule__UnaryExpression__Group_0__1__Impl rule__UnaryExpression__Group_0__2 ;
    public final void rule__UnaryExpression__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2021:1: ( rule__UnaryExpression__Group_0__1__Impl rule__UnaryExpression__Group_0__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2022:2: rule__UnaryExpression__Group_0__1__Impl rule__UnaryExpression__Group_0__2
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__1__Impl_in_rule__UnaryExpression__Group_0__14087);
            rule__UnaryExpression__Group_0__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__2_in_rule__UnaryExpression__Group_0__14090);
            rule__UnaryExpression__Group_0__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__UnaryExpression__Group_0__1


    // $ANTLR start rule__UnaryExpression__Group_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2029:1: rule__UnaryExpression__Group_0__1__Impl : ( ( rule__UnaryExpression__OperationsAssignment_0_1 ) ) ;
    public final void rule__UnaryExpression__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2033:1: ( ( ( rule__UnaryExpression__OperationsAssignment_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2034:1: ( ( rule__UnaryExpression__OperationsAssignment_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2034:1: ( ( rule__UnaryExpression__OperationsAssignment_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2035:1: ( rule__UnaryExpression__OperationsAssignment_0_1 )
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsAssignment_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2036:1: ( rule__UnaryExpression__OperationsAssignment_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2036:2: rule__UnaryExpression__OperationsAssignment_0_1
            {
            pushFollow(FOLLOW_rule__UnaryExpression__OperationsAssignment_0_1_in_rule__UnaryExpression__Group_0__1__Impl4117);
            rule__UnaryExpression__OperationsAssignment_0_1();
            _fsp--;


            }

             after(grammarAccess.getUnaryExpressionAccess().getOperationsAssignment_0_1()); 

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
    // $ANTLR end rule__UnaryExpression__Group_0__1__Impl


    // $ANTLR start rule__UnaryExpression__Group_0__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2046:1: rule__UnaryExpression__Group_0__2 : rule__UnaryExpression__Group_0__2__Impl ;
    public final void rule__UnaryExpression__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2050:1: ( rule__UnaryExpression__Group_0__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2051:2: rule__UnaryExpression__Group_0__2__Impl
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__2__Impl_in_rule__UnaryExpression__Group_0__24147);
            rule__UnaryExpression__Group_0__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__UnaryExpression__Group_0__2


    // $ANTLR start rule__UnaryExpression__Group_0__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2057:1: rule__UnaryExpression__Group_0__2__Impl : ( ( rule__UnaryExpression__ParametersAssignment_0_2 ) ) ;
    public final void rule__UnaryExpression__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2061:1: ( ( ( rule__UnaryExpression__ParametersAssignment_0_2 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2062:1: ( ( rule__UnaryExpression__ParametersAssignment_0_2 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2062:1: ( ( rule__UnaryExpression__ParametersAssignment_0_2 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2063:1: ( rule__UnaryExpression__ParametersAssignment_0_2 )
            {
             before(grammarAccess.getUnaryExpressionAccess().getParametersAssignment_0_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2064:1: ( rule__UnaryExpression__ParametersAssignment_0_2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2064:2: rule__UnaryExpression__ParametersAssignment_0_2
            {
            pushFollow(FOLLOW_rule__UnaryExpression__ParametersAssignment_0_2_in_rule__UnaryExpression__Group_0__2__Impl4174);
            rule__UnaryExpression__ParametersAssignment_0_2();
            _fsp--;


            }

             after(grammarAccess.getUnaryExpressionAccess().getParametersAssignment_0_2()); 

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
    // $ANTLR end rule__UnaryExpression__Group_0__2__Impl


    // $ANTLR start rule__PrimaryExpression__Group_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2080:1: rule__PrimaryExpression__Group_0__0 : rule__PrimaryExpression__Group_0__0__Impl rule__PrimaryExpression__Group_0__1 ;
    public final void rule__PrimaryExpression__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2084:1: ( rule__PrimaryExpression__Group_0__0__Impl rule__PrimaryExpression__Group_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2085:2: rule__PrimaryExpression__Group_0__0__Impl rule__PrimaryExpression__Group_0__1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_0__0__Impl_in_rule__PrimaryExpression__Group_0__04210);
            rule__PrimaryExpression__Group_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_0__1_in_rule__PrimaryExpression__Group_0__04213);
            rule__PrimaryExpression__Group_0__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PrimaryExpression__Group_0__0


    // $ANTLR start rule__PrimaryExpression__Group_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2092:1: rule__PrimaryExpression__Group_0__0__Impl : ( () ) ;
    public final void rule__PrimaryExpression__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2096:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2097:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2097:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2098:1: ()
            {
             before(grammarAccess.getPrimaryExpressionAccess().getExpressionAction_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2099:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2101:1: 
            {
            }

             after(grammarAccess.getPrimaryExpressionAccess().getExpressionAction_0_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PrimaryExpression__Group_0__0__Impl


    // $ANTLR start rule__PrimaryExpression__Group_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2111:1: rule__PrimaryExpression__Group_0__1 : rule__PrimaryExpression__Group_0__1__Impl ;
    public final void rule__PrimaryExpression__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2115:1: ( rule__PrimaryExpression__Group_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2116:2: rule__PrimaryExpression__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_0__1__Impl_in_rule__PrimaryExpression__Group_0__14271);
            rule__PrimaryExpression__Group_0__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PrimaryExpression__Group_0__1


    // $ANTLR start rule__PrimaryExpression__Group_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2122:1: rule__PrimaryExpression__Group_0__1__Impl : ( ( rule__PrimaryExpression__ParametersAssignment_0_1 ) ) ;
    public final void rule__PrimaryExpression__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2126:1: ( ( ( rule__PrimaryExpression__ParametersAssignment_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2127:1: ( ( rule__PrimaryExpression__ParametersAssignment_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2127:1: ( ( rule__PrimaryExpression__ParametersAssignment_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2128:1: ( rule__PrimaryExpression__ParametersAssignment_0_1 )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2129:1: ( rule__PrimaryExpression__ParametersAssignment_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2129:2: rule__PrimaryExpression__ParametersAssignment_0_1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__ParametersAssignment_0_1_in_rule__PrimaryExpression__Group_0__1__Impl4298);
            rule__PrimaryExpression__ParametersAssignment_0_1();
            _fsp--;


            }

             after(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_0_1()); 

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
    // $ANTLR end rule__PrimaryExpression__Group_0__1__Impl


    // $ANTLR start rule__PrimaryExpression__Group_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2143:1: rule__PrimaryExpression__Group_1__0 : rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1 ;
    public final void rule__PrimaryExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2147:1: ( rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2148:2: rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__0__Impl_in_rule__PrimaryExpression__Group_1__04332);
            rule__PrimaryExpression__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__1_in_rule__PrimaryExpression__Group_1__04335);
            rule__PrimaryExpression__Group_1__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PrimaryExpression__Group_1__0


    // $ANTLR start rule__PrimaryExpression__Group_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2155:1: rule__PrimaryExpression__Group_1__0__Impl : ( '(' ) ;
    public final void rule__PrimaryExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2159:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2160:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2160:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2161:1: '('
            {
             before(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0()); 
            match(input,18,FOLLOW_18_in_rule__PrimaryExpression__Group_1__0__Impl4363); 
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
    // $ANTLR end rule__PrimaryExpression__Group_1__0__Impl


    // $ANTLR start rule__PrimaryExpression__Group_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2174:1: rule__PrimaryExpression__Group_1__1 : rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2 ;
    public final void rule__PrimaryExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2178:1: ( rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2179:2: rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__1__Impl_in_rule__PrimaryExpression__Group_1__14394);
            rule__PrimaryExpression__Group_1__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__2_in_rule__PrimaryExpression__Group_1__14397);
            rule__PrimaryExpression__Group_1__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PrimaryExpression__Group_1__1


    // $ANTLR start rule__PrimaryExpression__Group_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2186:1: rule__PrimaryExpression__Group_1__1__Impl : ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) ) ;
    public final void rule__PrimaryExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2190:1: ( ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2191:1: ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2191:1: ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2192:1: ( rule__PrimaryExpression__ParametersAssignment_1_1 )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2193:1: ( rule__PrimaryExpression__ParametersAssignment_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2193:2: rule__PrimaryExpression__ParametersAssignment_1_1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__ParametersAssignment_1_1_in_rule__PrimaryExpression__Group_1__1__Impl4424);
            rule__PrimaryExpression__ParametersAssignment_1_1();
            _fsp--;


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
    // $ANTLR end rule__PrimaryExpression__Group_1__1__Impl


    // $ANTLR start rule__PrimaryExpression__Group_1__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2203:1: rule__PrimaryExpression__Group_1__2 : rule__PrimaryExpression__Group_1__2__Impl ;
    public final void rule__PrimaryExpression__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2207:1: ( rule__PrimaryExpression__Group_1__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2208:2: rule__PrimaryExpression__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__2__Impl_in_rule__PrimaryExpression__Group_1__24454);
            rule__PrimaryExpression__Group_1__2__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PrimaryExpression__Group_1__2


    // $ANTLR start rule__PrimaryExpression__Group_1__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2214:1: rule__PrimaryExpression__Group_1__2__Impl : ( ')' ) ;
    public final void rule__PrimaryExpression__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2218:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2219:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2219:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2220:1: ')'
            {
             before(grammarAccess.getPrimaryExpressionAccess().getRightParenthesisKeyword_1_2()); 
            match(input,19,FOLLOW_19_in_rule__PrimaryExpression__Group_1__2__Impl4482); 
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
    // $ANTLR end rule__PrimaryExpression__Group_1__2__Impl


    // $ANTLR start rule__FunctionCall__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2239:1: rule__FunctionCall__Group__0 : rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1 ;
    public final void rule__FunctionCall__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2243:1: ( rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2244:2: rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__0__Impl_in_rule__FunctionCall__Group__04519);
            rule__FunctionCall__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__1_in_rule__FunctionCall__Group__04522);
            rule__FunctionCall__Group__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__FunctionCall__Group__0


    // $ANTLR start rule__FunctionCall__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2251:1: rule__FunctionCall__Group__0__Impl : ( ( rule__FunctionCall__NameAssignment_0 ) ) ;
    public final void rule__FunctionCall__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2255:1: ( ( ( rule__FunctionCall__NameAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2256:1: ( ( rule__FunctionCall__NameAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2256:1: ( ( rule__FunctionCall__NameAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2257:1: ( rule__FunctionCall__NameAssignment_0 )
            {
             before(grammarAccess.getFunctionCallAccess().getNameAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2258:1: ( rule__FunctionCall__NameAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2258:2: rule__FunctionCall__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__FunctionCall__NameAssignment_0_in_rule__FunctionCall__Group__0__Impl4549);
            rule__FunctionCall__NameAssignment_0();
            _fsp--;


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
    // $ANTLR end rule__FunctionCall__Group__0__Impl


    // $ANTLR start rule__FunctionCall__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2268:1: rule__FunctionCall__Group__1 : rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2 ;
    public final void rule__FunctionCall__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2272:1: ( rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2273:2: rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__1__Impl_in_rule__FunctionCall__Group__14579);
            rule__FunctionCall__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__2_in_rule__FunctionCall__Group__14582);
            rule__FunctionCall__Group__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__FunctionCall__Group__1


    // $ANTLR start rule__FunctionCall__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2280:1: rule__FunctionCall__Group__1__Impl : ( '(' ) ;
    public final void rule__FunctionCall__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2284:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2285:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2285:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2286:1: '('
            {
             before(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1()); 
            match(input,18,FOLLOW_18_in_rule__FunctionCall__Group__1__Impl4610); 
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
    // $ANTLR end rule__FunctionCall__Group__1__Impl


    // $ANTLR start rule__FunctionCall__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2299:1: rule__FunctionCall__Group__2 : rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3 ;
    public final void rule__FunctionCall__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2303:1: ( rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2304:2: rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__2__Impl_in_rule__FunctionCall__Group__24641);
            rule__FunctionCall__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__3_in_rule__FunctionCall__Group__24644);
            rule__FunctionCall__Group__3();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__FunctionCall__Group__2


    // $ANTLR start rule__FunctionCall__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2311:1: rule__FunctionCall__Group__2__Impl : ( ( rule__FunctionCall__OperandsAssignment_2 ) ) ;
    public final void rule__FunctionCall__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2315:1: ( ( ( rule__FunctionCall__OperandsAssignment_2 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2316:1: ( ( rule__FunctionCall__OperandsAssignment_2 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2316:1: ( ( rule__FunctionCall__OperandsAssignment_2 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2317:1: ( rule__FunctionCall__OperandsAssignment_2 )
            {
             before(grammarAccess.getFunctionCallAccess().getOperandsAssignment_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2318:1: ( rule__FunctionCall__OperandsAssignment_2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2318:2: rule__FunctionCall__OperandsAssignment_2
            {
            pushFollow(FOLLOW_rule__FunctionCall__OperandsAssignment_2_in_rule__FunctionCall__Group__2__Impl4671);
            rule__FunctionCall__OperandsAssignment_2();
            _fsp--;


            }

             after(grammarAccess.getFunctionCallAccess().getOperandsAssignment_2()); 

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
    // $ANTLR end rule__FunctionCall__Group__2__Impl


    // $ANTLR start rule__FunctionCall__Group__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2328:1: rule__FunctionCall__Group__3 : rule__FunctionCall__Group__3__Impl ;
    public final void rule__FunctionCall__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2332:1: ( rule__FunctionCall__Group__3__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2333:2: rule__FunctionCall__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__3__Impl_in_rule__FunctionCall__Group__34701);
            rule__FunctionCall__Group__3__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__FunctionCall__Group__3


    // $ANTLR start rule__FunctionCall__Group__3__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2339:1: rule__FunctionCall__Group__3__Impl : ( ')' ) ;
    public final void rule__FunctionCall__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2343:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2344:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2344:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2345:1: ')'
            {
             before(grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_3()); 
            match(input,19,FOLLOW_19_in_rule__FunctionCall__Group__3__Impl4729); 
             after(grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_3()); 

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
    // $ANTLR end rule__FunctionCall__Group__3__Impl


    // $ANTLR start rule__Literal__Group_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2368:1: rule__Literal__Group_0__0 : rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1 ;
    public final void rule__Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2372:1: ( rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2373:2: rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_0__0__Impl_in_rule__Literal__Group_0__04770);
            rule__Literal__Group_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_0__1_in_rule__Literal__Group_0__04773);
            rule__Literal__Group_0__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_0__0


    // $ANTLR start rule__Literal__Group_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2380:1: rule__Literal__Group_0__0__Impl : ( () ) ;
    public final void rule__Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2384:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2385:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2385:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2386:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2387:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2389:1: 
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
    // $ANTLR end rule__Literal__Group_0__0__Impl


    // $ANTLR start rule__Literal__Group_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2399:1: rule__Literal__Group_0__1 : rule__Literal__Group_0__1__Impl ;
    public final void rule__Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2403:1: ( rule__Literal__Group_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2404:2: rule__Literal__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_0__1__Impl_in_rule__Literal__Group_0__14831);
            rule__Literal__Group_0__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_0__1


    // $ANTLR start rule__Literal__Group_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2410:1: rule__Literal__Group_0__1__Impl : ( ( rule__Literal__ValueStringAssignment_0_1 ) ) ;
    public final void rule__Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2414:1: ( ( ( rule__Literal__ValueStringAssignment_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2415:1: ( ( rule__Literal__ValueStringAssignment_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2415:1: ( ( rule__Literal__ValueStringAssignment_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2416:1: ( rule__Literal__ValueStringAssignment_0_1 )
            {
             before(grammarAccess.getLiteralAccess().getValueStringAssignment_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2417:1: ( rule__Literal__ValueStringAssignment_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2417:2: rule__Literal__ValueStringAssignment_0_1
            {
            pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_0_1_in_rule__Literal__Group_0__1__Impl4858);
            rule__Literal__ValueStringAssignment_0_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getValueStringAssignment_0_1()); 

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
    // $ANTLR end rule__Literal__Group_0__1__Impl


    // $ANTLR start rule__Literal__Group_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2431:1: rule__Literal__Group_1__0 : rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1 ;
    public final void rule__Literal__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2435:1: ( rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2436:2: rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_1__0__Impl_in_rule__Literal__Group_1__04892);
            rule__Literal__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_1__1_in_rule__Literal__Group_1__04895);
            rule__Literal__Group_1__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_1__0


    // $ANTLR start rule__Literal__Group_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2443:1: rule__Literal__Group_1__0__Impl : ( () ) ;
    public final void rule__Literal__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2447:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2448:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2448:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2449:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2450:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2452:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_1__0__Impl


    // $ANTLR start rule__Literal__Group_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2462:1: rule__Literal__Group_1__1 : rule__Literal__Group_1__1__Impl ;
    public final void rule__Literal__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2466:1: ( rule__Literal__Group_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2467:2: rule__Literal__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_1__1__Impl_in_rule__Literal__Group_1__14953);
            rule__Literal__Group_1__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_1__1


    // $ANTLR start rule__Literal__Group_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2473:1: rule__Literal__Group_1__1__Impl : ( ( rule__Literal__ValueStringAssignment_1_1 ) ) ;
    public final void rule__Literal__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2477:1: ( ( ( rule__Literal__ValueStringAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2478:1: ( ( rule__Literal__ValueStringAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2478:1: ( ( rule__Literal__ValueStringAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2479:1: ( rule__Literal__ValueStringAssignment_1_1 )
            {
             before(grammarAccess.getLiteralAccess().getValueStringAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2480:1: ( rule__Literal__ValueStringAssignment_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2480:2: rule__Literal__ValueStringAssignment_1_1
            {
            pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_1_1_in_rule__Literal__Group_1__1__Impl4980);
            rule__Literal__ValueStringAssignment_1_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getValueStringAssignment_1_1()); 

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
    // $ANTLR end rule__Literal__Group_1__1__Impl


    // $ANTLR start rule__Literal__Group_2__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2494:1: rule__Literal__Group_2__0 : rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1 ;
    public final void rule__Literal__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2498:1: ( rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2499:2: rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__0__Impl_in_rule__Literal__Group_2__05014);
            rule__Literal__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__1_in_rule__Literal__Group_2__05017);
            rule__Literal__Group_2__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_2__0


    // $ANTLR start rule__Literal__Group_2__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2506:1: rule__Literal__Group_2__0__Impl : ( () ) ;
    public final void rule__Literal__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2510:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2511:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2511:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2512:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_2_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2513:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2515:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_2_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_2__0__Impl


    // $ANTLR start rule__Literal__Group_2__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2525:1: rule__Literal__Group_2__1 : rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2 ;
    public final void rule__Literal__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2529:1: ( rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2530:2: rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__1__Impl_in_rule__Literal__Group_2__15075);
            rule__Literal__Group_2__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__2_in_rule__Literal__Group_2__15078);
            rule__Literal__Group_2__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_2__1


    // $ANTLR start rule__Literal__Group_2__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2537:1: rule__Literal__Group_2__1__Impl : ( ( rule__Literal__OperationsAssignment_2_1 ) ) ;
    public final void rule__Literal__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2541:1: ( ( ( rule__Literal__OperationsAssignment_2_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2542:1: ( ( rule__Literal__OperationsAssignment_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2542:1: ( ( rule__Literal__OperationsAssignment_2_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2543:1: ( rule__Literal__OperationsAssignment_2_1 )
            {
             before(grammarAccess.getLiteralAccess().getOperationsAssignment_2_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2544:1: ( rule__Literal__OperationsAssignment_2_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2544:2: rule__Literal__OperationsAssignment_2_1
            {
            pushFollow(FOLLOW_rule__Literal__OperationsAssignment_2_1_in_rule__Literal__Group_2__1__Impl5105);
            rule__Literal__OperationsAssignment_2_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getOperationsAssignment_2_1()); 

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
    // $ANTLR end rule__Literal__Group_2__1__Impl


    // $ANTLR start rule__Literal__Group_2__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2554:1: rule__Literal__Group_2__2 : rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3 ;
    public final void rule__Literal__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2558:1: ( rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2559:2: rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__2__Impl_in_rule__Literal__Group_2__25135);
            rule__Literal__Group_2__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__3_in_rule__Literal__Group_2__25138);
            rule__Literal__Group_2__3();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_2__2


    // $ANTLR start rule__Literal__Group_2__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2566:1: rule__Literal__Group_2__2__Impl : ( '(' ) ;
    public final void rule__Literal__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2570:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2571:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2571:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2572:1: '('
            {
             before(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_2()); 
            match(input,18,FOLLOW_18_in_rule__Literal__Group_2__2__Impl5166); 
             after(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_2()); 

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
    // $ANTLR end rule__Literal__Group_2__2__Impl


    // $ANTLR start rule__Literal__Group_2__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2585:1: rule__Literal__Group_2__3 : rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4 ;
    public final void rule__Literal__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2589:1: ( rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2590:2: rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__3__Impl_in_rule__Literal__Group_2__35197);
            rule__Literal__Group_2__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__4_in_rule__Literal__Group_2__35200);
            rule__Literal__Group_2__4();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_2__3


    // $ANTLR start rule__Literal__Group_2__3__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2597:1: rule__Literal__Group_2__3__Impl : ( ( rule__Literal__ValueStringAssignment_2_3 ) ) ;
    public final void rule__Literal__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2601:1: ( ( ( rule__Literal__ValueStringAssignment_2_3 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2602:1: ( ( rule__Literal__ValueStringAssignment_2_3 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2602:1: ( ( rule__Literal__ValueStringAssignment_2_3 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2603:1: ( rule__Literal__ValueStringAssignment_2_3 )
            {
             before(grammarAccess.getLiteralAccess().getValueStringAssignment_2_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2604:1: ( rule__Literal__ValueStringAssignment_2_3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2604:2: rule__Literal__ValueStringAssignment_2_3
            {
            pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_2_3_in_rule__Literal__Group_2__3__Impl5227);
            rule__Literal__ValueStringAssignment_2_3();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getValueStringAssignment_2_3()); 

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
    // $ANTLR end rule__Literal__Group_2__3__Impl


    // $ANTLR start rule__Literal__Group_2__4
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2614:1: rule__Literal__Group_2__4 : rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5 ;
    public final void rule__Literal__Group_2__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2618:1: ( rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2619:2: rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__4__Impl_in_rule__Literal__Group_2__45257);
            rule__Literal__Group_2__4__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__5_in_rule__Literal__Group_2__45260);
            rule__Literal__Group_2__5();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_2__4


    // $ANTLR start rule__Literal__Group_2__4__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2626:1: rule__Literal__Group_2__4__Impl : ( ',' ) ;
    public final void rule__Literal__Group_2__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2630:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2631:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2631:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2632:1: ','
            {
             before(grammarAccess.getLiteralAccess().getCommaKeyword_2_4()); 
            match(input,20,FOLLOW_20_in_rule__Literal__Group_2__4__Impl5288); 
             after(grammarAccess.getLiteralAccess().getCommaKeyword_2_4()); 

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
    // $ANTLR end rule__Literal__Group_2__4__Impl


    // $ANTLR start rule__Literal__Group_2__5
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2645:1: rule__Literal__Group_2__5 : rule__Literal__Group_2__5__Impl rule__Literal__Group_2__6 ;
    public final void rule__Literal__Group_2__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2649:1: ( rule__Literal__Group_2__5__Impl rule__Literal__Group_2__6 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2650:2: rule__Literal__Group_2__5__Impl rule__Literal__Group_2__6
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__5__Impl_in_rule__Literal__Group_2__55319);
            rule__Literal__Group_2__5__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__6_in_rule__Literal__Group_2__55322);
            rule__Literal__Group_2__6();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_2__5


    // $ANTLR start rule__Literal__Group_2__5__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2657:1: rule__Literal__Group_2__5__Impl : ( ( rule__Literal__ParametersAssignment_2_5 ) ) ;
    public final void rule__Literal__Group_2__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2661:1: ( ( ( rule__Literal__ParametersAssignment_2_5 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2662:1: ( ( rule__Literal__ParametersAssignment_2_5 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2662:1: ( ( rule__Literal__ParametersAssignment_2_5 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2663:1: ( rule__Literal__ParametersAssignment_2_5 )
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_2_5()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2664:1: ( rule__Literal__ParametersAssignment_2_5 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2664:2: rule__Literal__ParametersAssignment_2_5
            {
            pushFollow(FOLLOW_rule__Literal__ParametersAssignment_2_5_in_rule__Literal__Group_2__5__Impl5349);
            rule__Literal__ParametersAssignment_2_5();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getParametersAssignment_2_5()); 

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
    // $ANTLR end rule__Literal__Group_2__5__Impl


    // $ANTLR start rule__Literal__Group_2__6
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2674:1: rule__Literal__Group_2__6 : rule__Literal__Group_2__6__Impl ;
    public final void rule__Literal__Group_2__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2678:1: ( rule__Literal__Group_2__6__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2679:2: rule__Literal__Group_2__6__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__6__Impl_in_rule__Literal__Group_2__65379);
            rule__Literal__Group_2__6__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_2__6


    // $ANTLR start rule__Literal__Group_2__6__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2685:1: rule__Literal__Group_2__6__Impl : ( ')' ) ;
    public final void rule__Literal__Group_2__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2689:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2690:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2690:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2691:1: ')'
            {
             before(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_6()); 
            match(input,19,FOLLOW_19_in_rule__Literal__Group_2__6__Impl5407); 
             after(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_6()); 

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
    // $ANTLR end rule__Literal__Group_2__6__Impl


    // $ANTLR start rule__Literal__Group_4__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2718:1: rule__Literal__Group_4__0 : rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1 ;
    public final void rule__Literal__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2722:1: ( rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2723:2: rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__0__Impl_in_rule__Literal__Group_4__05452);
            rule__Literal__Group_4__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4__1_in_rule__Literal__Group_4__05455);
            rule__Literal__Group_4__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_4__0


    // $ANTLR start rule__Literal__Group_4__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2730:1: rule__Literal__Group_4__0__Impl : ( () ) ;
    public final void rule__Literal__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2734:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2735:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2735:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2736:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_4_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2737:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2739:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_4_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_4__0__Impl


    // $ANTLR start rule__Literal__Group_4__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2749:1: rule__Literal__Group_4__1 : rule__Literal__Group_4__1__Impl ;
    public final void rule__Literal__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2753:1: ( rule__Literal__Group_4__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2754:2: rule__Literal__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__1__Impl_in_rule__Literal__Group_4__15513);
            rule__Literal__Group_4__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_4__1


    // $ANTLR start rule__Literal__Group_4__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2760:1: rule__Literal__Group_4__1__Impl : ( ( rule__Literal__ElementsAssignment_4_1 ) ) ;
    public final void rule__Literal__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2764:1: ( ( ( rule__Literal__ElementsAssignment_4_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2765:1: ( ( rule__Literal__ElementsAssignment_4_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2765:1: ( ( rule__Literal__ElementsAssignment_4_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2766:1: ( rule__Literal__ElementsAssignment_4_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_4_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2767:1: ( rule__Literal__ElementsAssignment_4_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2767:2: rule__Literal__ElementsAssignment_4_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_4_1_in_rule__Literal__Group_4__1__Impl5540);
            rule__Literal__ElementsAssignment_4_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getElementsAssignment_4_1()); 

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
    // $ANTLR end rule__Literal__Group_4__1__Impl


    // $ANTLR start rule__Literal__Group_5__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2781:1: rule__Literal__Group_5__0 : rule__Literal__Group_5__0__Impl rule__Literal__Group_5__1 ;
    public final void rule__Literal__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2785:1: ( rule__Literal__Group_5__0__Impl rule__Literal__Group_5__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2786:2: rule__Literal__Group_5__0__Impl rule__Literal__Group_5__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_5__0__Impl_in_rule__Literal__Group_5__05574);
            rule__Literal__Group_5__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_5__1_in_rule__Literal__Group_5__05577);
            rule__Literal__Group_5__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_5__0


    // $ANTLR start rule__Literal__Group_5__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2793:1: rule__Literal__Group_5__0__Impl : ( () ) ;
    public final void rule__Literal__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2797:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2798:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2798:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2799:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_5_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2800:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2802:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_5_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_5__0__Impl


    // $ANTLR start rule__Literal__Group_5__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2812:1: rule__Literal__Group_5__1 : rule__Literal__Group_5__1__Impl ;
    public final void rule__Literal__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2816:1: ( rule__Literal__Group_5__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2817:2: rule__Literal__Group_5__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_5__1__Impl_in_rule__Literal__Group_5__15635);
            rule__Literal__Group_5__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_5__1


    // $ANTLR start rule__Literal__Group_5__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2823:1: rule__Literal__Group_5__1__Impl : ( ( rule__Literal__ElementsAssignment_5_1 ) ) ;
    public final void rule__Literal__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2827:1: ( ( ( rule__Literal__ElementsAssignment_5_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2828:1: ( ( rule__Literal__ElementsAssignment_5_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2828:1: ( ( rule__Literal__ElementsAssignment_5_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2829:1: ( rule__Literal__ElementsAssignment_5_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_5_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2830:1: ( rule__Literal__ElementsAssignment_5_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2830:2: rule__Literal__ElementsAssignment_5_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_5_1_in_rule__Literal__Group_5__1__Impl5662);
            rule__Literal__ElementsAssignment_5_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getElementsAssignment_5_1()); 

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
    // $ANTLR end rule__Literal__Group_5__1__Impl


    // $ANTLR start rule__Literal__Group_6__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2844:1: rule__Literal__Group_6__0 : rule__Literal__Group_6__0__Impl rule__Literal__Group_6__1 ;
    public final void rule__Literal__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2848:1: ( rule__Literal__Group_6__0__Impl rule__Literal__Group_6__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2849:2: rule__Literal__Group_6__0__Impl rule__Literal__Group_6__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__0__Impl_in_rule__Literal__Group_6__05696);
            rule__Literal__Group_6__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6__1_in_rule__Literal__Group_6__05699);
            rule__Literal__Group_6__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_6__0


    // $ANTLR start rule__Literal__Group_6__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2856:1: rule__Literal__Group_6__0__Impl : ( () ) ;
    public final void rule__Literal__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2860:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2861:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2861:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2862:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_6_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2863:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2865:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_6_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_6__0__Impl


    // $ANTLR start rule__Literal__Group_6__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2875:1: rule__Literal__Group_6__1 : rule__Literal__Group_6__1__Impl rule__Literal__Group_6__2 ;
    public final void rule__Literal__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2879:1: ( rule__Literal__Group_6__1__Impl rule__Literal__Group_6__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2880:2: rule__Literal__Group_6__1__Impl rule__Literal__Group_6__2
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__1__Impl_in_rule__Literal__Group_6__15757);
            rule__Literal__Group_6__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6__2_in_rule__Literal__Group_6__15760);
            rule__Literal__Group_6__2();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_6__1


    // $ANTLR start rule__Literal__Group_6__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2887:1: rule__Literal__Group_6__1__Impl : ( ( rule__Literal__ExpressionTypeAssignment_6_1 ) ) ;
    public final void rule__Literal__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2891:1: ( ( ( rule__Literal__ExpressionTypeAssignment_6_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2892:1: ( ( rule__Literal__ExpressionTypeAssignment_6_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2892:1: ( ( rule__Literal__ExpressionTypeAssignment_6_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2893:1: ( rule__Literal__ExpressionTypeAssignment_6_1 )
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeAssignment_6_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2894:1: ( rule__Literal__ExpressionTypeAssignment_6_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2894:2: rule__Literal__ExpressionTypeAssignment_6_1
            {
            pushFollow(FOLLOW_rule__Literal__ExpressionTypeAssignment_6_1_in_rule__Literal__Group_6__1__Impl5787);
            rule__Literal__ExpressionTypeAssignment_6_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getExpressionTypeAssignment_6_1()); 

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
    // $ANTLR end rule__Literal__Group_6__1__Impl


    // $ANTLR start rule__Literal__Group_6__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2904:1: rule__Literal__Group_6__2 : rule__Literal__Group_6__2__Impl rule__Literal__Group_6__3 ;
    public final void rule__Literal__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2908:1: ( rule__Literal__Group_6__2__Impl rule__Literal__Group_6__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2909:2: rule__Literal__Group_6__2__Impl rule__Literal__Group_6__3
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__2__Impl_in_rule__Literal__Group_6__25817);
            rule__Literal__Group_6__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6__3_in_rule__Literal__Group_6__25820);
            rule__Literal__Group_6__3();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_6__2


    // $ANTLR start rule__Literal__Group_6__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2916:1: rule__Literal__Group_6__2__Impl : ( ( rule__Literal__ParametersAssignment_6_2 )? ) ;
    public final void rule__Literal__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2920:1: ( ( ( rule__Literal__ParametersAssignment_6_2 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2921:1: ( ( rule__Literal__ParametersAssignment_6_2 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2921:1: ( ( rule__Literal__ParametersAssignment_6_2 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2922:1: ( rule__Literal__ParametersAssignment_6_2 )?
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_6_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2923:1: ( rule__Literal__ParametersAssignment_6_2 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( ((LA15_0>=RULE_ID && LA15_0<=RULE_LITERALSTRING)||LA15_0==12||LA15_0==15||LA15_0==18||(LA15_0>=27 && LA15_0<=33)) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2923:2: rule__Literal__ParametersAssignment_6_2
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_6_2_in_rule__Literal__Group_6__2__Impl5847);
                    rule__Literal__ParametersAssignment_6_2();
                    _fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLiteralAccess().getParametersAssignment_6_2()); 

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
    // $ANTLR end rule__Literal__Group_6__2__Impl


    // $ANTLR start rule__Literal__Group_6__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2933:1: rule__Literal__Group_6__3 : rule__Literal__Group_6__3__Impl rule__Literal__Group_6__4 ;
    public final void rule__Literal__Group_6__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2937:1: ( rule__Literal__Group_6__3__Impl rule__Literal__Group_6__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2938:2: rule__Literal__Group_6__3__Impl rule__Literal__Group_6__4
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__3__Impl_in_rule__Literal__Group_6__35878);
            rule__Literal__Group_6__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6__4_in_rule__Literal__Group_6__35881);
            rule__Literal__Group_6__4();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_6__3


    // $ANTLR start rule__Literal__Group_6__3__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2945:1: rule__Literal__Group_6__3__Impl : ( ( rule__Literal__Group_6_3__0 )* ) ;
    public final void rule__Literal__Group_6__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2949:1: ( ( ( rule__Literal__Group_6_3__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2950:1: ( ( rule__Literal__Group_6_3__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2950:1: ( ( rule__Literal__Group_6_3__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2951:1: ( rule__Literal__Group_6_3__0 )*
            {
             before(grammarAccess.getLiteralAccess().getGroup_6_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2952:1: ( rule__Literal__Group_6_3__0 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==20) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2952:2: rule__Literal__Group_6_3__0
            	    {
            	    pushFollow(FOLLOW_rule__Literal__Group_6_3__0_in_rule__Literal__Group_6__3__Impl5908);
            	    rule__Literal__Group_6_3__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

             after(grammarAccess.getLiteralAccess().getGroup_6_3()); 

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
    // $ANTLR end rule__Literal__Group_6__3__Impl


    // $ANTLR start rule__Literal__Group_6__4
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2962:1: rule__Literal__Group_6__4 : rule__Literal__Group_6__4__Impl ;
    public final void rule__Literal__Group_6__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2966:1: ( rule__Literal__Group_6__4__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2967:2: rule__Literal__Group_6__4__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__4__Impl_in_rule__Literal__Group_6__45939);
            rule__Literal__Group_6__4__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_6__4


    // $ANTLR start rule__Literal__Group_6__4__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2973:1: rule__Literal__Group_6__4__Impl : ( '}' ) ;
    public final void rule__Literal__Group_6__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2977:1: ( ( '}' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2978:1: ( '}' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2978:1: ( '}' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2979:1: '}'
            {
             before(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_6_4()); 
            match(input,21,FOLLOW_21_in_rule__Literal__Group_6__4__Impl5967); 
             after(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_6_4()); 

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
    // $ANTLR end rule__Literal__Group_6__4__Impl


    // $ANTLR start rule__Literal__Group_6_3__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3002:1: rule__Literal__Group_6_3__0 : rule__Literal__Group_6_3__0__Impl rule__Literal__Group_6_3__1 ;
    public final void rule__Literal__Group_6_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3006:1: ( rule__Literal__Group_6_3__0__Impl rule__Literal__Group_6_3__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3007:2: rule__Literal__Group_6_3__0__Impl rule__Literal__Group_6_3__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_6_3__0__Impl_in_rule__Literal__Group_6_3__06008);
            rule__Literal__Group_6_3__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6_3__1_in_rule__Literal__Group_6_3__06011);
            rule__Literal__Group_6_3__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_6_3__0


    // $ANTLR start rule__Literal__Group_6_3__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3014:1: rule__Literal__Group_6_3__0__Impl : ( ',' ) ;
    public final void rule__Literal__Group_6_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3018:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3019:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3019:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3020:1: ','
            {
             before(grammarAccess.getLiteralAccess().getCommaKeyword_6_3_0()); 
            match(input,20,FOLLOW_20_in_rule__Literal__Group_6_3__0__Impl6039); 
             after(grammarAccess.getLiteralAccess().getCommaKeyword_6_3_0()); 

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
    // $ANTLR end rule__Literal__Group_6_3__0__Impl


    // $ANTLR start rule__Literal__Group_6_3__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3033:1: rule__Literal__Group_6_3__1 : rule__Literal__Group_6_3__1__Impl ;
    public final void rule__Literal__Group_6_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3037:1: ( rule__Literal__Group_6_3__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3038:2: rule__Literal__Group_6_3__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_6_3__1__Impl_in_rule__Literal__Group_6_3__16070);
            rule__Literal__Group_6_3__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_6_3__1


    // $ANTLR start rule__Literal__Group_6_3__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3044:1: rule__Literal__Group_6_3__1__Impl : ( ( rule__Literal__ParametersAssignment_6_3_1 ) ) ;
    public final void rule__Literal__Group_6_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3048:1: ( ( ( rule__Literal__ParametersAssignment_6_3_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3049:1: ( ( rule__Literal__ParametersAssignment_6_3_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3049:1: ( ( rule__Literal__ParametersAssignment_6_3_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3050:1: ( rule__Literal__ParametersAssignment_6_3_1 )
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_6_3_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3051:1: ( rule__Literal__ParametersAssignment_6_3_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3051:2: rule__Literal__ParametersAssignment_6_3_1
            {
            pushFollow(FOLLOW_rule__Literal__ParametersAssignment_6_3_1_in_rule__Literal__Group_6_3__1__Impl6097);
            rule__Literal__ParametersAssignment_6_3_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getParametersAssignment_6_3_1()); 

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
    // $ANTLR end rule__Literal__Group_6_3__1__Impl


    // $ANTLR start rule__Literal__Group_7__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3065:1: rule__Literal__Group_7__0 : rule__Literal__Group_7__0__Impl rule__Literal__Group_7__1 ;
    public final void rule__Literal__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3069:1: ( rule__Literal__Group_7__0__Impl rule__Literal__Group_7__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3070:2: rule__Literal__Group_7__0__Impl rule__Literal__Group_7__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_7__0__Impl_in_rule__Literal__Group_7__06131);
            rule__Literal__Group_7__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_7__1_in_rule__Literal__Group_7__06134);
            rule__Literal__Group_7__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_7__0


    // $ANTLR start rule__Literal__Group_7__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3077:1: rule__Literal__Group_7__0__Impl : ( () ) ;
    public final void rule__Literal__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3081:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3082:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3082:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3083:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_7_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3084:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3086:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_7_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_7__0__Impl


    // $ANTLR start rule__Literal__Group_7__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3096:1: rule__Literal__Group_7__1 : rule__Literal__Group_7__1__Impl ;
    public final void rule__Literal__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3100:1: ( rule__Literal__Group_7__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3101:2: rule__Literal__Group_7__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_7__1__Impl_in_rule__Literal__Group_7__16192);
            rule__Literal__Group_7__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_7__1


    // $ANTLR start rule__Literal__Group_7__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3107:1: rule__Literal__Group_7__1__Impl : ( ( rule__Literal__ElementsAssignment_7_1 ) ) ;
    public final void rule__Literal__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3111:1: ( ( ( rule__Literal__ElementsAssignment_7_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3112:1: ( ( rule__Literal__ElementsAssignment_7_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3112:1: ( ( rule__Literal__ElementsAssignment_7_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3113:1: ( rule__Literal__ElementsAssignment_7_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_7_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3114:1: ( rule__Literal__ElementsAssignment_7_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3114:2: rule__Literal__ElementsAssignment_7_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_7_1_in_rule__Literal__Group_7__1__Impl6219);
            rule__Literal__ElementsAssignment_7_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getElementsAssignment_7_1()); 

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
    // $ANTLR end rule__Literal__Group_7__1__Impl


    // $ANTLR start rule__Literal__Group_8__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3128:1: rule__Literal__Group_8__0 : rule__Literal__Group_8__0__Impl rule__Literal__Group_8__1 ;
    public final void rule__Literal__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3132:1: ( rule__Literal__Group_8__0__Impl rule__Literal__Group_8__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3133:2: rule__Literal__Group_8__0__Impl rule__Literal__Group_8__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_8__0__Impl_in_rule__Literal__Group_8__06253);
            rule__Literal__Group_8__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_8__1_in_rule__Literal__Group_8__06256);
            rule__Literal__Group_8__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_8__0


    // $ANTLR start rule__Literal__Group_8__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3140:1: rule__Literal__Group_8__0__Impl : ( () ) ;
    public final void rule__Literal__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3144:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3145:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3145:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3146:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_8_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3147:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3149:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_8_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_8__0__Impl


    // $ANTLR start rule__Literal__Group_8__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3159:1: rule__Literal__Group_8__1 : rule__Literal__Group_8__1__Impl ;
    public final void rule__Literal__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3163:1: ( rule__Literal__Group_8__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3164:2: rule__Literal__Group_8__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_8__1__Impl_in_rule__Literal__Group_8__16314);
            rule__Literal__Group_8__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_8__1


    // $ANTLR start rule__Literal__Group_8__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3170:1: rule__Literal__Group_8__1__Impl : ( ( rule__Literal__ElementsAssignment_8_1 ) ) ;
    public final void rule__Literal__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3174:1: ( ( ( rule__Literal__ElementsAssignment_8_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3175:1: ( ( rule__Literal__ElementsAssignment_8_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3175:1: ( ( rule__Literal__ElementsAssignment_8_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3176:1: ( rule__Literal__ElementsAssignment_8_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_8_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3177:1: ( rule__Literal__ElementsAssignment_8_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3177:2: rule__Literal__ElementsAssignment_8_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_8_1_in_rule__Literal__Group_8__1__Impl6341);
            rule__Literal__ElementsAssignment_8_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getElementsAssignment_8_1()); 

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
    // $ANTLR end rule__Literal__Group_8__1__Impl


    // $ANTLR start rule__Literal__Group_9__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3191:1: rule__Literal__Group_9__0 : rule__Literal__Group_9__0__Impl rule__Literal__Group_9__1 ;
    public final void rule__Literal__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3195:1: ( rule__Literal__Group_9__0__Impl rule__Literal__Group_9__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3196:2: rule__Literal__Group_9__0__Impl rule__Literal__Group_9__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_9__0__Impl_in_rule__Literal__Group_9__06375);
            rule__Literal__Group_9__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_9__1_in_rule__Literal__Group_9__06378);
            rule__Literal__Group_9__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_9__0


    // $ANTLR start rule__Literal__Group_9__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3203:1: rule__Literal__Group_9__0__Impl : ( () ) ;
    public final void rule__Literal__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3207:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3208:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3208:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3209:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_9_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3210:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3212:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_9_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_9__0__Impl


    // $ANTLR start rule__Literal__Group_9__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3222:1: rule__Literal__Group_9__1 : rule__Literal__Group_9__1__Impl ;
    public final void rule__Literal__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3226:1: ( rule__Literal__Group_9__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3227:2: rule__Literal__Group_9__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_9__1__Impl_in_rule__Literal__Group_9__16436);
            rule__Literal__Group_9__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_9__1


    // $ANTLR start rule__Literal__Group_9__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3233:1: rule__Literal__Group_9__1__Impl : ( ( rule__Literal__ElementsAssignment_9_1 ) ) ;
    public final void rule__Literal__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3237:1: ( ( ( rule__Literal__ElementsAssignment_9_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3238:1: ( ( rule__Literal__ElementsAssignment_9_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3238:1: ( ( rule__Literal__ElementsAssignment_9_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3239:1: ( rule__Literal__ElementsAssignment_9_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_9_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3240:1: ( rule__Literal__ElementsAssignment_9_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3240:2: rule__Literal__ElementsAssignment_9_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_9_1_in_rule__Literal__Group_9__1__Impl6463);
            rule__Literal__ElementsAssignment_9_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getElementsAssignment_9_1()); 

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
    // $ANTLR end rule__Literal__Group_9__1__Impl


    // $ANTLR start rule__Literal__Group_10__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3254:1: rule__Literal__Group_10__0 : rule__Literal__Group_10__0__Impl rule__Literal__Group_10__1 ;
    public final void rule__Literal__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3258:1: ( rule__Literal__Group_10__0__Impl rule__Literal__Group_10__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3259:2: rule__Literal__Group_10__0__Impl rule__Literal__Group_10__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_10__0__Impl_in_rule__Literal__Group_10__06497);
            rule__Literal__Group_10__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_10__1_in_rule__Literal__Group_10__06500);
            rule__Literal__Group_10__1();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_10__0


    // $ANTLR start rule__Literal__Group_10__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3266:1: rule__Literal__Group_10__0__Impl : ( () ) ;
    public final void rule__Literal__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3270:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3271:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3271:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3272:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_10_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3273:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3275:1: 
            {
            }

             after(grammarAccess.getLiteralAccess().getExpressionAction_10_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_10__0__Impl


    // $ANTLR start rule__Literal__Group_10__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3285:1: rule__Literal__Group_10__1 : rule__Literal__Group_10__1__Impl ;
    public final void rule__Literal__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3289:1: ( rule__Literal__Group_10__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3290:2: rule__Literal__Group_10__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_10__1__Impl_in_rule__Literal__Group_10__16558);
            rule__Literal__Group_10__1__Impl();
            _fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__Group_10__1


    // $ANTLR start rule__Literal__Group_10__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3296:1: rule__Literal__Group_10__1__Impl : ( ( rule__Literal__ElementsAssignment_10_1 ) ) ;
    public final void rule__Literal__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3300:1: ( ( ( rule__Literal__ElementsAssignment_10_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3301:1: ( ( rule__Literal__ElementsAssignment_10_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3301:1: ( ( rule__Literal__ElementsAssignment_10_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3302:1: ( rule__Literal__ElementsAssignment_10_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_10_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3303:1: ( rule__Literal__ElementsAssignment_10_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3303:2: rule__Literal__ElementsAssignment_10_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_10_1_in_rule__Literal__Group_10__1__Impl6585);
            rule__Literal__ElementsAssignment_10_1();
            _fsp--;


            }

             after(grammarAccess.getLiteralAccess().getElementsAssignment_10_1()); 

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
    // $ANTLR end rule__Literal__Group_10__1__Impl


    // $ANTLR start rule__TopLevel__ToplevelExpressionAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3318:1: rule__TopLevel__ToplevelExpressionAssignment_1 : ( ruleOrExpression ) ;
    public final void rule__TopLevel__ToplevelExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3322:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3323:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3323:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3324:1: ruleOrExpression
            {
             before(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__TopLevel__ToplevelExpressionAssignment_16624);
            ruleOrExpression();
            _fsp--;

             after(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_1_0()); 

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
    // $ANTLR end rule__TopLevel__ToplevelExpressionAssignment_1


    // $ANTLR start rule__OrExpression__ParametersAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3333:1: rule__OrExpression__ParametersAssignment_1 : ( ruleAndExpression ) ;
    public final void rule__OrExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3337:1: ( ( ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3338:1: ( ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3338:1: ( ruleAndExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3339:1: ruleAndExpression
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_16655);
            ruleAndExpression();
            _fsp--;

             after(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_0()); 

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
    // $ANTLR end rule__OrExpression__ParametersAssignment_1


    // $ANTLR start rule__OrExpression__OperationsAssignment_2_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3348:1: rule__OrExpression__OperationsAssignment_2_0 : ( ( 'OR' ) ) ;
    public final void rule__OrExpression__OperationsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3352:1: ( ( ( 'OR' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3353:1: ( ( 'OR' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3353:1: ( ( 'OR' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3354:1: ( 'OR' )
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_2_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3355:1: ( 'OR' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3356:1: 'OR'
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_2_0_0()); 
            match(input,22,FOLLOW_22_in_rule__OrExpression__OperationsAssignment_2_06691); 
             after(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_2_0_0()); 

            }

             after(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_2_0_0()); 

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
    // $ANTLR end rule__OrExpression__OperationsAssignment_2_0


    // $ANTLR start rule__OrExpression__ParametersAssignment_2_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3371:1: rule__OrExpression__ParametersAssignment_2_1 : ( ruleAndExpression ) ;
    public final void rule__OrExpression__ParametersAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3375:1: ( ( ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3376:1: ( ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3376:1: ( ruleAndExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3377:1: ruleAndExpression
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_2_16730);
            ruleAndExpression();
            _fsp--;

             after(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_2_1_0()); 

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
    // $ANTLR end rule__OrExpression__ParametersAssignment_2_1


    // $ANTLR start rule__AndExpression__ParametersAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3386:1: rule__AndExpression__ParametersAssignment_1 : ( ruleEqualityExpression ) ;
    public final void rule__AndExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3390:1: ( ( ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3391:1: ( ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3391:1: ( ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3392:1: ruleEqualityExpression
            {
             before(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_16761);
            ruleEqualityExpression();
            _fsp--;

             after(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_0()); 

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
    // $ANTLR end rule__AndExpression__ParametersAssignment_1


    // $ANTLR start rule__AndExpression__OperationsAssignment_2_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3401:1: rule__AndExpression__OperationsAssignment_2_0 : ( ( 'AND' ) ) ;
    public final void rule__AndExpression__OperationsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3405:1: ( ( ( 'AND' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3406:1: ( ( 'AND' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3406:1: ( ( 'AND' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3407:1: ( 'AND' )
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_2_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3408:1: ( 'AND' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3409:1: 'AND'
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_2_0_0()); 
            match(input,23,FOLLOW_23_in_rule__AndExpression__OperationsAssignment_2_06797); 
             after(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_2_0_0()); 

            }

             after(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_2_0_0()); 

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
    // $ANTLR end rule__AndExpression__OperationsAssignment_2_0


    // $ANTLR start rule__AndExpression__ParametersAssignment_2_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3424:1: rule__AndExpression__ParametersAssignment_2_1 : ( ruleEqualityExpression ) ;
    public final void rule__AndExpression__ParametersAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3428:1: ( ( ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3429:1: ( ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3429:1: ( ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3430:1: ruleEqualityExpression
            {
             before(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_2_16836);
            ruleEqualityExpression();
            _fsp--;

             after(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_2_1_0()); 

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
    // $ANTLR end rule__AndExpression__ParametersAssignment_2_1


    // $ANTLR start rule__EqualityExpression__ParametersAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3439:1: rule__EqualityExpression__ParametersAssignment_1 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3443:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3444:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3444:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3445:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_16867);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0()); 

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
    // $ANTLR end rule__EqualityExpression__ParametersAssignment_1


    // $ANTLR start rule__EqualityExpression__OperationsAssignment_2_0_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3454:1: rule__EqualityExpression__OperationsAssignment_2_0_0 : ( ( '==' ) ) ;
    public final void rule__EqualityExpression__OperationsAssignment_2_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3458:1: ( ( ( '==' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3459:1: ( ( '==' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3459:1: ( ( '==' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3460:1: ( '==' )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_2_0_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3461:1: ( '==' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3462:1: '=='
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_2_0_0_0()); 
            match(input,24,FOLLOW_24_in_rule__EqualityExpression__OperationsAssignment_2_0_06903); 
             after(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_2_0_0_0()); 

            }

             after(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_2_0_0_0()); 

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
    // $ANTLR end rule__EqualityExpression__OperationsAssignment_2_0_0


    // $ANTLR start rule__EqualityExpression__ParametersAssignment_2_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3477:1: rule__EqualityExpression__ParametersAssignment_2_0_1 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3481:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3482:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3482:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3483:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_2_0_16942);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0()); 

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
    // $ANTLR end rule__EqualityExpression__ParametersAssignment_2_0_1


    // $ANTLR start rule__EqualityExpression__OperationsAssignment_2_1_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3492:1: rule__EqualityExpression__OperationsAssignment_2_1_0 : ( ( '!=' ) ) ;
    public final void rule__EqualityExpression__OperationsAssignment_2_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3496:1: ( ( ( '!=' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3497:1: ( ( '!=' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3497:1: ( ( '!=' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3498:1: ( '!=' )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_2_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3499:1: ( '!=' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3500:1: '!='
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_2_1_0_0()); 
            match(input,25,FOLLOW_25_in_rule__EqualityExpression__OperationsAssignment_2_1_06978); 
             after(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_2_1_0_0()); 

            }

             after(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_2_1_0_0()); 

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
    // $ANTLR end rule__EqualityExpression__OperationsAssignment_2_1_0


    // $ANTLR start rule__EqualityExpression__ParametersAssignment_2_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3515:1: rule__EqualityExpression__ParametersAssignment_2_1_1 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3519:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3520:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3520:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3521:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_2_1_17017);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0()); 

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
    // $ANTLR end rule__EqualityExpression__ParametersAssignment_2_1_1


    // $ANTLR start rule__AdditiveExpression__ParametersAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3530:1: rule__AdditiveExpression__ParametersAssignment_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3534:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3535:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3535:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3536:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_17048);
            ruleMultiplicativeExpression();
            _fsp--;

             after(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0()); 

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
    // $ANTLR end rule__AdditiveExpression__ParametersAssignment_1


    // $ANTLR start rule__AdditiveExpression__ParametersAssignment_2_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3545:1: rule__AdditiveExpression__ParametersAssignment_2_0_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3549:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3550:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3550:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3551:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_0_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_2_0_17079);
            ruleMultiplicativeExpression();
            _fsp--;

             after(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_0_1_0()); 

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
    // $ANTLR end rule__AdditiveExpression__ParametersAssignment_2_0_1


    // $ANTLR start rule__AdditiveExpression__ParametersAssignment_2_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3560:1: rule__AdditiveExpression__ParametersAssignment_2_1_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3564:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3565:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3565:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3566:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_2_1_17110);
            ruleMultiplicativeExpression();
            _fsp--;

             after(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_1_1_0()); 

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
    // $ANTLR end rule__AdditiveExpression__ParametersAssignment_2_1_1


    // $ANTLR start rule__MultiplicativeExpression__ParametersAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3575:1: rule__MultiplicativeExpression__ParametersAssignment_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3579:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3580:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3580:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3581:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_17141);
            ruleUnaryExpression();
            _fsp--;

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0()); 

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
    // $ANTLR end rule__MultiplicativeExpression__ParametersAssignment_1


    // $ANTLR start rule__MultiplicativeExpression__OperationsAssignment_2_0_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3590:1: rule__MultiplicativeExpression__OperationsAssignment_2_0_0 : ( ( '*' ) ) ;
    public final void rule__MultiplicativeExpression__OperationsAssignment_2_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3594:1: ( ( ( '*' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3595:1: ( ( '*' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3595:1: ( ( '*' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3596:1: ( '*' )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_2_0_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3597:1: ( '*' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3598:1: '*'
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_2_0_0_0()); 
            match(input,26,FOLLOW_26_in_rule__MultiplicativeExpression__OperationsAssignment_2_0_07177); 
             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_2_0_0_0()); 

            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_2_0_0_0()); 

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
    // $ANTLR end rule__MultiplicativeExpression__OperationsAssignment_2_0_0


    // $ANTLR start rule__MultiplicativeExpression__ParametersAssignment_2_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3613:1: rule__MultiplicativeExpression__ParametersAssignment_2_0_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3617:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3618:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3618:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3619:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_0_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_2_0_17216);
            ruleUnaryExpression();
            _fsp--;

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_0_1_0()); 

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
    // $ANTLR end rule__MultiplicativeExpression__ParametersAssignment_2_0_1


    // $ANTLR start rule__MultiplicativeExpression__OperationsAssignment_2_1_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3628:1: rule__MultiplicativeExpression__OperationsAssignment_2_1_0 : ( ( '/' ) ) ;
    public final void rule__MultiplicativeExpression__OperationsAssignment_2_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3632:1: ( ( ( '/' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3633:1: ( ( '/' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3633:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3634:1: ( '/' )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_2_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3635:1: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3636:1: '/'
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_2_1_0_0()); 
            match(input,13,FOLLOW_13_in_rule__MultiplicativeExpression__OperationsAssignment_2_1_07252); 
             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_2_1_0_0()); 

            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_2_1_0_0()); 

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
    // $ANTLR end rule__MultiplicativeExpression__OperationsAssignment_2_1_0


    // $ANTLR start rule__MultiplicativeExpression__ParametersAssignment_2_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3651:1: rule__MultiplicativeExpression__ParametersAssignment_2_1_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3655:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3656:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3656:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3657:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_2_1_17291);
            ruleUnaryExpression();
            _fsp--;

             after(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_1_1_0()); 

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
    // $ANTLR end rule__MultiplicativeExpression__ParametersAssignment_2_1_1


    // $ANTLR start rule__UnaryExpression__OperationsAssignment_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3666:1: rule__UnaryExpression__OperationsAssignment_0_1 : ( ( '!' ) ) ;
    public final void rule__UnaryExpression__OperationsAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3670:1: ( ( ( '!' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3671:1: ( ( '!' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3671:1: ( ( '!' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3672:1: ( '!' )
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3673:1: ( '!' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3674:1: '!'
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_1_0()); 
            match(input,27,FOLLOW_27_in_rule__UnaryExpression__OperationsAssignment_0_17327); 
             after(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_1_0()); 

            }

             after(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_1_0()); 

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
    // $ANTLR end rule__UnaryExpression__OperationsAssignment_0_1


    // $ANTLR start rule__UnaryExpression__ParametersAssignment_0_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3689:1: rule__UnaryExpression__ParametersAssignment_0_2 : ( rulePrimaryExpression ) ;
    public final void rule__UnaryExpression__ParametersAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3693:1: ( ( rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3694:1: ( rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3694:1: ( rulePrimaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3695:1: rulePrimaryExpression
            {
             before(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_2_0()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__ParametersAssignment_0_27366);
            rulePrimaryExpression();
            _fsp--;

             after(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_2_0()); 

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
    // $ANTLR end rule__UnaryExpression__ParametersAssignment_0_2


    // $ANTLR start rule__PrimaryExpression__ParametersAssignment_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3704:1: rule__PrimaryExpression__ParametersAssignment_0_1 : ( ruleLiteral ) ;
    public final void rule__PrimaryExpression__ParametersAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3708:1: ( ( ruleLiteral ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3709:1: ( ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3709:1: ( ruleLiteral )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3710:1: ruleLiteral
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_ruleLiteral_in_rule__PrimaryExpression__ParametersAssignment_0_17397);
            ruleLiteral();
            _fsp--;

             after(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_1_0()); 

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
    // $ANTLR end rule__PrimaryExpression__ParametersAssignment_0_1


    // $ANTLR start rule__PrimaryExpression__ParametersAssignment_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3719:1: rule__PrimaryExpression__ParametersAssignment_1_1 : ( ruleOrExpression ) ;
    public final void rule__PrimaryExpression__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3723:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3724:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3724:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3725:1: ruleOrExpression
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__PrimaryExpression__ParametersAssignment_1_17428);
            ruleOrExpression();
            _fsp--;

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
    // $ANTLR end rule__PrimaryExpression__ParametersAssignment_1_1


    // $ANTLR start rule__FunctionCall__NameAssignment_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3734:1: rule__FunctionCall__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__FunctionCall__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3738:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3739:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3739:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3740:1: RULE_ID
            {
             before(grammarAccess.getFunctionCallAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__FunctionCall__NameAssignment_07459); 
             after(grammarAccess.getFunctionCallAccess().getNameIDTerminalRuleCall_0_0()); 

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
    // $ANTLR end rule__FunctionCall__NameAssignment_0


    // $ANTLR start rule__FunctionCall__OperandsAssignment_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3749:1: rule__FunctionCall__OperandsAssignment_2 : ( ( RULE_ID ) ) ;
    public final void rule__FunctionCall__OperandsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3753:1: ( ( ( RULE_ID ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3754:1: ( ( RULE_ID ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3754:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3755:1: ( RULE_ID )
            {
             before(grammarAccess.getFunctionCallAccess().getOperandsFunctionOperandsCrossReference_2_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3756:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3757:1: RULE_ID
            {
             before(grammarAccess.getFunctionCallAccess().getOperandsFunctionOperandsIDTerminalRuleCall_2_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__FunctionCall__OperandsAssignment_27494); 
             after(grammarAccess.getFunctionCallAccess().getOperandsFunctionOperandsIDTerminalRuleCall_2_0_1()); 

            }

             after(grammarAccess.getFunctionCallAccess().getOperandsFunctionOperandsCrossReference_2_0()); 

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
    // $ANTLR end rule__FunctionCall__OperandsAssignment_2


    // $ANTLR start rule__Literal__ValueStringAssignment_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3770:1: rule__Literal__ValueStringAssignment_0_1 : ( RULE_INT ) ;
    public final void rule__Literal__ValueStringAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3774:1: ( ( RULE_INT ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3775:1: ( RULE_INT )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3775:1: ( RULE_INT )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3776:1: RULE_INT
            {
             before(grammarAccess.getLiteralAccess().getValueStringINTTerminalRuleCall_0_1_0()); 
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__Literal__ValueStringAssignment_0_17531); 
             after(grammarAccess.getLiteralAccess().getValueStringINTTerminalRuleCall_0_1_0()); 

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
    // $ANTLR end rule__Literal__ValueStringAssignment_0_1


    // $ANTLR start rule__Literal__ValueStringAssignment_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3785:1: rule__Literal__ValueStringAssignment_1_1 : ( RULE_LITERALSTRING ) ;
    public final void rule__Literal__ValueStringAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3789:1: ( ( RULE_LITERALSTRING ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3790:1: ( RULE_LITERALSTRING )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3790:1: ( RULE_LITERALSTRING )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3791:1: RULE_LITERALSTRING
            {
             before(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_1_17562); 
             after(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_1_0()); 

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
    // $ANTLR end rule__Literal__ValueStringAssignment_1_1


    // $ANTLR start rule__Literal__OperationsAssignment_2_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3800:1: rule__Literal__OperationsAssignment_2_1 : ( ( 'FORALL' ) ) ;
    public final void rule__Literal__OperationsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3804:1: ( ( ( 'FORALL' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3805:1: ( ( 'FORALL' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3805:1: ( ( 'FORALL' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3806:1: ( 'FORALL' )
            {
             before(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3807:1: ( 'FORALL' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3808:1: 'FORALL'
            {
             before(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_1_0()); 
            match(input,28,FOLLOW_28_in_rule__Literal__OperationsAssignment_2_17598); 
             after(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_1_0()); 

            }

             after(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_1_0()); 

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
    // $ANTLR end rule__Literal__OperationsAssignment_2_1


    // $ANTLR start rule__Literal__ValueStringAssignment_2_3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3823:1: rule__Literal__ValueStringAssignment_2_3 : ( RULE_LITERALSTRING ) ;
    public final void rule__Literal__ValueStringAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3827:1: ( ( RULE_LITERALSTRING ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3828:1: ( RULE_LITERALSTRING )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3828:1: ( RULE_LITERALSTRING )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3829:1: RULE_LITERALSTRING
            {
             before(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_3_0()); 
            match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_2_37637); 
             after(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_3_0()); 

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
    // $ANTLR end rule__Literal__ValueStringAssignment_2_3


    // $ANTLR start rule__Literal__ParametersAssignment_2_5
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3838:1: rule__Literal__ParametersAssignment_2_5 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_2_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3842:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3843:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3843:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3844:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_5_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_2_57668);
            ruleOrExpression();
            _fsp--;

             after(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_5_0()); 

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
    // $ANTLR end rule__Literal__ParametersAssignment_2_5


    // $ANTLR start rule__Literal__ElementsAssignment_4_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3853:1: rule__Literal__ElementsAssignment_4_1 : ( ruleExistsTmlExpression ) ;
    public final void rule__Literal__ElementsAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3857:1: ( ( ruleExistsTmlExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3858:1: ( ruleExistsTmlExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3858:1: ( ruleExistsTmlExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3859:1: ruleExistsTmlExpression
            {
             before(grammarAccess.getLiteralAccess().getElementsExistsTmlExpressionParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_rule__Literal__ElementsAssignment_4_17699);
            ruleExistsTmlExpression();
            _fsp--;

             after(grammarAccess.getLiteralAccess().getElementsExistsTmlExpressionParserRuleCall_4_1_0()); 

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
    // $ANTLR end rule__Literal__ElementsAssignment_4_1


    // $ANTLR start rule__Literal__ElementsAssignment_5_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3868:1: rule__Literal__ElementsAssignment_5_1 : ( ruleTmlExpression ) ;
    public final void rule__Literal__ElementsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3872:1: ( ( ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3873:1: ( ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3873:1: ( ruleTmlExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3874:1: ruleTmlExpression
            {
             before(grammarAccess.getLiteralAccess().getElementsTmlExpressionParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_rule__Literal__ElementsAssignment_5_17730);
            ruleTmlExpression();
            _fsp--;

             after(grammarAccess.getLiteralAccess().getElementsTmlExpressionParserRuleCall_5_1_0()); 

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
    // $ANTLR end rule__Literal__ElementsAssignment_5_1


    // $ANTLR start rule__Literal__ExpressionTypeAssignment_6_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3883:1: rule__Literal__ExpressionTypeAssignment_6_1 : ( ( '{' ) ) ;
    public final void rule__Literal__ExpressionTypeAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3887:1: ( ( ( '{' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3888:1: ( ( '{' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3888:1: ( ( '{' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3889:1: ( '{' )
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_6_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3890:1: ( '{' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3891:1: '{'
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_6_1_0()); 
            match(input,29,FOLLOW_29_in_rule__Literal__ExpressionTypeAssignment_6_17766); 
             after(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_6_1_0()); 

            }

             after(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_6_1_0()); 

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
    // $ANTLR end rule__Literal__ExpressionTypeAssignment_6_1


    // $ANTLR start rule__Literal__ParametersAssignment_6_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3906:1: rule__Literal__ParametersAssignment_6_2 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_6_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3910:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3911:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3911:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3912:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_2_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_6_27805);
            ruleOrExpression();
            _fsp--;

             after(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_2_0()); 

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
    // $ANTLR end rule__Literal__ParametersAssignment_6_2


    // $ANTLR start rule__Literal__ParametersAssignment_6_3_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3921:1: rule__Literal__ParametersAssignment_6_3_1 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_6_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3925:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3926:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3926:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3927:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_3_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_6_3_17836);
            ruleOrExpression();
            _fsp--;

             after(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_3_1_0()); 

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
    // $ANTLR end rule__Literal__ParametersAssignment_6_3_1


    // $ANTLR start rule__Literal__ElementsAssignment_7_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3936:1: rule__Literal__ElementsAssignment_7_1 : ( ( 'NULL' ) ) ;
    public final void rule__Literal__ElementsAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3940:1: ( ( ( 'NULL' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3941:1: ( ( 'NULL' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3941:1: ( ( 'NULL' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3942:1: ( 'NULL' )
            {
             before(grammarAccess.getLiteralAccess().getElementsNULLKeyword_7_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3943:1: ( 'NULL' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3944:1: 'NULL'
            {
             before(grammarAccess.getLiteralAccess().getElementsNULLKeyword_7_1_0()); 
            match(input,30,FOLLOW_30_in_rule__Literal__ElementsAssignment_7_17872); 
             after(grammarAccess.getLiteralAccess().getElementsNULLKeyword_7_1_0()); 

            }

             after(grammarAccess.getLiteralAccess().getElementsNULLKeyword_7_1_0()); 

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
    // $ANTLR end rule__Literal__ElementsAssignment_7_1


    // $ANTLR start rule__Literal__ElementsAssignment_8_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3959:1: rule__Literal__ElementsAssignment_8_1 : ( ( 'TODAY' ) ) ;
    public final void rule__Literal__ElementsAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3963:1: ( ( ( 'TODAY' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3964:1: ( ( 'TODAY' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3964:1: ( ( 'TODAY' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3965:1: ( 'TODAY' )
            {
             before(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_8_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3966:1: ( 'TODAY' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3967:1: 'TODAY'
            {
             before(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_8_1_0()); 
            match(input,31,FOLLOW_31_in_rule__Literal__ElementsAssignment_8_17916); 
             after(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_8_1_0()); 

            }

             after(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_8_1_0()); 

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
    // $ANTLR end rule__Literal__ElementsAssignment_8_1


    // $ANTLR start rule__Literal__ElementsAssignment_9_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3982:1: rule__Literal__ElementsAssignment_9_1 : ( ( 'TRUE' ) ) ;
    public final void rule__Literal__ElementsAssignment_9_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3986:1: ( ( ( 'TRUE' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3987:1: ( ( 'TRUE' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3987:1: ( ( 'TRUE' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3988:1: ( 'TRUE' )
            {
             before(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_9_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3989:1: ( 'TRUE' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3990:1: 'TRUE'
            {
             before(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_9_1_0()); 
            match(input,32,FOLLOW_32_in_rule__Literal__ElementsAssignment_9_17960); 
             after(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_9_1_0()); 

            }

             after(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_9_1_0()); 

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
    // $ANTLR end rule__Literal__ElementsAssignment_9_1


    // $ANTLR start rule__Literal__ElementsAssignment_10_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4005:1: rule__Literal__ElementsAssignment_10_1 : ( ( 'FALSE' ) ) ;
    public final void rule__Literal__ElementsAssignment_10_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4009:1: ( ( ( 'FALSE' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4010:1: ( ( 'FALSE' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4010:1: ( ( 'FALSE' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4011:1: ( 'FALSE' )
            {
             before(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_10_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4012:1: ( 'FALSE' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:4013:1: 'FALSE'
            {
             before(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_10_1_0()); 
            match(input,33,FOLLOW_33_in_rule__Literal__ElementsAssignment_10_18004); 
             after(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_10_1_0()); 

            }

             after(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_10_1_0()); 

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
    // $ANTLR end rule__Literal__ElementsAssignment_10_1


 

    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__Group__0_in_ruleTopLevel94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathElement__Alternatives_in_rulePathElement154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_entryRulePathSequence181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathSequence188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__0_in_rulePathSequence214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression241 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_ruleTmlExpression274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression300 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__0_in_ruleExistsTmlExpression333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression360 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__0_in_ruleOrExpression393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression420 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__0_in_ruleAndExpression453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression480 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__0_in_ruleEqualityExpression513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression540 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__0_in_ruleAdditiveExpression573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression600 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__0_in_ruleMultiplicativeExpression633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression660 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Alternatives_in_ruleUnaryExpression693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression720 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Alternatives_in_rulePrimaryExpression753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall780 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__0_in_ruleFunctionCall813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral842 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Alternatives_in_ruleLiteral875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PathElement__Alternatives911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_rule__PathElement__Alternatives929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__PathElement__Alternatives949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_0__0_in_rule__EqualityExpression__Alternatives_2983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_1__0_in_rule__EqualityExpression__Alternatives_21001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_0__0_in_rule__AdditiveExpression__Alternatives_21034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_1__0_in_rule__AdditiveExpression__Alternatives_21052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_0__0_in_rule__MultiplicativeExpression__Alternatives_21085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_1__0_in_rule__MultiplicativeExpression__Alternatives_21103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__0_in_rule__UnaryExpression__Alternatives1136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__Alternatives1154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_0__0_in_rule__PrimaryExpression__Alternatives1186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__0_in_rule__PrimaryExpression__Alternatives1204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__0_in_rule__Literal__Alternatives1237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_1__0_in_rule__Literal__Alternatives1255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__0_in_rule__Literal__Alternatives1273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_rule__Literal__Alternatives1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__0_in_rule__Literal__Alternatives1308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_5__0_in_rule__Literal__Alternatives1326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__0_in_rule__Literal__Alternatives1344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_7__0_in_rule__Literal__Alternatives1362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_8__0_in_rule__Literal__Alternatives1380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_9__0_in_rule__Literal__Alternatives1398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_10__0_in_rule__Literal__Alternatives1416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__Group__0__Impl_in_rule__TopLevel__Group__01447 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__TopLevel__Group__1_in_rule__TopLevel__Group__01450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__Group__1__Impl_in_rule__TopLevel__Group__11508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__ToplevelExpressionAssignment_1_in_rule__TopLevel__Group__1__Impl1535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__0__Impl_in_rule__PathSequence__Group__01569 = new BitSet(new long[]{0x0000000000002C10L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__1_in_rule__PathSequence__Group__01572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__PathSequence__Group__0__Impl1600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__1__Impl_in_rule__PathSequence__Group__11631 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__2_in_rule__PathSequence__Group__11634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__PathSequence__Group__1__Impl1663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__2__Impl_in_rule__PathSequence__Group__21696 = new BitSet(new long[]{0x0000000000006000L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__3_in_rule__PathSequence__Group__21699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__PathSequence__Group__2__Impl1726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__3__Impl_in_rule__PathSequence__Group__31755 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__4_in_rule__PathSequence__Group__31758 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group_3__0_in_rule__PathSequence__Group__3__Impl1785 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__4__Impl_in_rule__PathSequence__Group__41816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__PathSequence__Group__4__Impl1844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group_3__0__Impl_in_rule__PathSequence__Group_3__01885 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rule__PathSequence__Group_3__1_in_rule__PathSequence__Group_3__01888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__PathSequence__Group_3__0__Impl1916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group_3__1__Impl_in_rule__PathSequence__Group_3__11947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__PathSequence__Group_3__1__Impl1974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__0__Impl_in_rule__ExistsTmlExpression__Group__02007 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__1_in_rule__ExistsTmlExpression__Group__02010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__ExistsTmlExpression__Group__0__Impl2038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__1__Impl_in_rule__ExistsTmlExpression__Group__12069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_rule__ExistsTmlExpression__Group__1__Impl2096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__0__Impl_in_rule__OrExpression__Group__02129 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__1_in_rule__OrExpression__Group__02132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__1__Impl_in_rule__OrExpression__Group__12190 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__2_in_rule__OrExpression__Group__12193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__ParametersAssignment_1_in_rule__OrExpression__Group__1__Impl2220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__2__Impl_in_rule__OrExpression__Group__22250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_2__0_in_rule__OrExpression__Group__2__Impl2277 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_2__0__Impl_in_rule__OrExpression__Group_2__02314 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_2__1_in_rule__OrExpression__Group_2__02317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__OperationsAssignment_2_0_in_rule__OrExpression__Group_2__0__Impl2344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_2__1__Impl_in_rule__OrExpression__Group_2__12374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__ParametersAssignment_2_1_in_rule__OrExpression__Group_2__1__Impl2401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__0__Impl_in_rule__AndExpression__Group__02435 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__1_in_rule__AndExpression__Group__02438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__1__Impl_in_rule__AndExpression__Group__12496 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__2_in_rule__AndExpression__Group__12499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__ParametersAssignment_1_in_rule__AndExpression__Group__1__Impl2526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__2__Impl_in_rule__AndExpression__Group__22556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_2__0_in_rule__AndExpression__Group__2__Impl2583 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_2__0__Impl_in_rule__AndExpression__Group_2__02620 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_2__1_in_rule__AndExpression__Group_2__02623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__OperationsAssignment_2_0_in_rule__AndExpression__Group_2__0__Impl2650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_2__1__Impl_in_rule__AndExpression__Group_2__12680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__ParametersAssignment_2_1_in_rule__AndExpression__Group_2__1__Impl2707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__0__Impl_in_rule__EqualityExpression__Group__02741 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__1_in_rule__EqualityExpression__Group__02744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__1__Impl_in_rule__EqualityExpression__Group__12802 = new BitSet(new long[]{0x0000000003000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__2_in_rule__EqualityExpression__Group__12805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_1_in_rule__EqualityExpression__Group__1__Impl2832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__2__Impl_in_rule__EqualityExpression__Group__22862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Alternatives_2_in_rule__EqualityExpression__Group__2__Impl2889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_0__0__Impl_in_rule__EqualityExpression__Group_2_0__02926 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_0__1_in_rule__EqualityExpression__Group_2_0__02929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__OperationsAssignment_2_0_0_in_rule__EqualityExpression__Group_2_0__0__Impl2956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_0__1__Impl_in_rule__EqualityExpression__Group_2_0__12986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_2_0_1_in_rule__EqualityExpression__Group_2_0__1__Impl3013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_1__0__Impl_in_rule__EqualityExpression__Group_2_1__03047 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_1__1_in_rule__EqualityExpression__Group_2_1__03050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__OperationsAssignment_2_1_0_in_rule__EqualityExpression__Group_2_1__0__Impl3077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_1__1__Impl_in_rule__EqualityExpression__Group_2_1__13107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_2_1_1_in_rule__EqualityExpression__Group_2_1__1__Impl3134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__0__Impl_in_rule__AdditiveExpression__Group__03168 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__1_in_rule__AdditiveExpression__Group__03171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__1__Impl_in_rule__AdditiveExpression__Group__13229 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__2_in_rule__AdditiveExpression__Group__13232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_in_rule__AdditiveExpression__Group__1__Impl3259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__2__Impl_in_rule__AdditiveExpression__Group__23289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Alternatives_2_in_rule__AdditiveExpression__Group__2__Impl3316 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_0__0__Impl_in_rule__AdditiveExpression__Group_2_0__03353 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_0__1_in_rule__AdditiveExpression__Group_2_0__03356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__AdditiveExpression__Group_2_0__0__Impl3384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_0__1__Impl_in_rule__AdditiveExpression__Group_2_0__13415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_2_0_1_in_rule__AdditiveExpression__Group_2_0__1__Impl3442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_1__0__Impl_in_rule__AdditiveExpression__Group_2_1__03476 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_1__1_in_rule__AdditiveExpression__Group_2_1__03479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__AdditiveExpression__Group_2_1__0__Impl3507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_1__1__Impl_in_rule__AdditiveExpression__Group_2_1__13538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_2_1_1_in_rule__AdditiveExpression__Group_2_1__1__Impl3565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__0__Impl_in_rule__MultiplicativeExpression__Group__03599 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__1_in_rule__MultiplicativeExpression__Group__03602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__1__Impl_in_rule__MultiplicativeExpression__Group__13660 = new BitSet(new long[]{0x0000000004002002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__2_in_rule__MultiplicativeExpression__Group__13663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_in_rule__MultiplicativeExpression__Group__1__Impl3690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__2__Impl_in_rule__MultiplicativeExpression__Group__23720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Alternatives_2_in_rule__MultiplicativeExpression__Group__2__Impl3747 = new BitSet(new long[]{0x0000000004002002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_0__0__Impl_in_rule__MultiplicativeExpression__Group_2_0__03784 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_0__1_in_rule__MultiplicativeExpression__Group_2_0__03787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_2_0_0_in_rule__MultiplicativeExpression__Group_2_0__0__Impl3814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_0__1__Impl_in_rule__MultiplicativeExpression__Group_2_0__13844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_2_0_1_in_rule__MultiplicativeExpression__Group_2_0__1__Impl3871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_1__0__Impl_in_rule__MultiplicativeExpression__Group_2_1__03905 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_1__1_in_rule__MultiplicativeExpression__Group_2_1__03908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_2_1_0_in_rule__MultiplicativeExpression__Group_2_1__0__Impl3935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_1__1__Impl_in_rule__MultiplicativeExpression__Group_2_1__13965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_2_1_1_in_rule__MultiplicativeExpression__Group_2_1__1__Impl3992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__0__Impl_in_rule__UnaryExpression__Group_0__04026 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__1_in_rule__UnaryExpression__Group_0__04029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__1__Impl_in_rule__UnaryExpression__Group_0__14087 = new BitSet(new long[]{0x00000003F0049070L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__2_in_rule__UnaryExpression__Group_0__14090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__OperationsAssignment_0_1_in_rule__UnaryExpression__Group_0__1__Impl4117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__2__Impl_in_rule__UnaryExpression__Group_0__24147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__ParametersAssignment_0_2_in_rule__UnaryExpression__Group_0__2__Impl4174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_0__0__Impl_in_rule__PrimaryExpression__Group_0__04210 = new BitSet(new long[]{0x00000003F0009070L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_0__1_in_rule__PrimaryExpression__Group_0__04213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_0__1__Impl_in_rule__PrimaryExpression__Group_0__14271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__ParametersAssignment_0_1_in_rule__PrimaryExpression__Group_0__1__Impl4298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__0__Impl_in_rule__PrimaryExpression__Group_1__04332 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__1_in_rule__PrimaryExpression__Group_1__04335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__PrimaryExpression__Group_1__0__Impl4363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__1__Impl_in_rule__PrimaryExpression__Group_1__14394 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__2_in_rule__PrimaryExpression__Group_1__14397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__ParametersAssignment_1_1_in_rule__PrimaryExpression__Group_1__1__Impl4424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__2__Impl_in_rule__PrimaryExpression__Group_1__24454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__PrimaryExpression__Group_1__2__Impl4482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__0__Impl_in_rule__FunctionCall__Group__04519 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__1_in_rule__FunctionCall__Group__04522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__NameAssignment_0_in_rule__FunctionCall__Group__0__Impl4549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__1__Impl_in_rule__FunctionCall__Group__14579 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__2_in_rule__FunctionCall__Group__14582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__FunctionCall__Group__1__Impl4610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__2__Impl_in_rule__FunctionCall__Group__24641 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__3_in_rule__FunctionCall__Group__24644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__OperandsAssignment_2_in_rule__FunctionCall__Group__2__Impl4671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__3__Impl_in_rule__FunctionCall__Group__34701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__FunctionCall__Group__3__Impl4729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__0__Impl_in_rule__Literal__Group_0__04770 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__1_in_rule__Literal__Group_0__04773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__1__Impl_in_rule__Literal__Group_0__14831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_0_1_in_rule__Literal__Group_0__1__Impl4858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_1__0__Impl_in_rule__Literal__Group_1__04892 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_rule__Literal__Group_1__1_in_rule__Literal__Group_1__04895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_1__1__Impl_in_rule__Literal__Group_1__14953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_1_1_in_rule__Literal__Group_1__1__Impl4980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__0__Impl_in_rule__Literal__Group_2__05014 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__1_in_rule__Literal__Group_2__05017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__1__Impl_in_rule__Literal__Group_2__15075 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__2_in_rule__Literal__Group_2__15078 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__OperationsAssignment_2_1_in_rule__Literal__Group_2__1__Impl5105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__2__Impl_in_rule__Literal__Group_2__25135 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__3_in_rule__Literal__Group_2__25138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Literal__Group_2__2__Impl5166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__3__Impl_in_rule__Literal__Group_2__35197 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__4_in_rule__Literal__Group_2__35200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_2_3_in_rule__Literal__Group_2__3__Impl5227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__4__Impl_in_rule__Literal__Group_2__45257 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__5_in_rule__Literal__Group_2__45260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Literal__Group_2__4__Impl5288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__5__Impl_in_rule__Literal__Group_2__55319 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__6_in_rule__Literal__Group_2__55322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_2_5_in_rule__Literal__Group_2__5__Impl5349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__6__Impl_in_rule__Literal__Group_2__65379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__Literal__Group_2__6__Impl5407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__0__Impl_in_rule__Literal__Group_4__05452 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__1_in_rule__Literal__Group_4__05455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__1__Impl_in_rule__Literal__Group_4__15513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_4_1_in_rule__Literal__Group_4__1__Impl5540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_5__0__Impl_in_rule__Literal__Group_5__05574 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__Literal__Group_5__1_in_rule__Literal__Group_5__05577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_5__1__Impl_in_rule__Literal__Group_5__15635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_5_1_in_rule__Literal__Group_5__1__Impl5662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__0__Impl_in_rule__Literal__Group_6__05696 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__1_in_rule__Literal__Group_6__05699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__1__Impl_in_rule__Literal__Group_6__15757 = new BitSet(new long[]{0x00000003F8349070L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__2_in_rule__Literal__Group_6__15760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ExpressionTypeAssignment_6_1_in_rule__Literal__Group_6__1__Impl5787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__2__Impl_in_rule__Literal__Group_6__25817 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__3_in_rule__Literal__Group_6__25820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_6_2_in_rule__Literal__Group_6__2__Impl5847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__3__Impl_in_rule__Literal__Group_6__35878 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__4_in_rule__Literal__Group_6__35881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6_3__0_in_rule__Literal__Group_6__3__Impl5908 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__4__Impl_in_rule__Literal__Group_6__45939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Literal__Group_6__4__Impl5967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6_3__0__Impl_in_rule__Literal__Group_6_3__06008 = new BitSet(new long[]{0x00000003F8049070L});
    public static final BitSet FOLLOW_rule__Literal__Group_6_3__1_in_rule__Literal__Group_6_3__06011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Literal__Group_6_3__0__Impl6039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6_3__1__Impl_in_rule__Literal__Group_6_3__16070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_6_3_1_in_rule__Literal__Group_6_3__1__Impl6097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_7__0__Impl_in_rule__Literal__Group_7__06131 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_7__1_in_rule__Literal__Group_7__06134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_7__1__Impl_in_rule__Literal__Group_7__16192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_7_1_in_rule__Literal__Group_7__1__Impl6219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_8__0__Impl_in_rule__Literal__Group_8__06253 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_8__1_in_rule__Literal__Group_8__06256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_8__1__Impl_in_rule__Literal__Group_8__16314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_8_1_in_rule__Literal__Group_8__1__Impl6341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_9__0__Impl_in_rule__Literal__Group_9__06375 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_9__1_in_rule__Literal__Group_9__06378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_9__1__Impl_in_rule__Literal__Group_9__16436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_9_1_in_rule__Literal__Group_9__1__Impl6463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_10__0__Impl_in_rule__Literal__Group_10__06497 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_10__1_in_rule__Literal__Group_10__06500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_10__1__Impl_in_rule__Literal__Group_10__16558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_10_1_in_rule__Literal__Group_10__1__Impl6585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__TopLevel__ToplevelExpressionAssignment_16624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_16655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__OrExpression__OperationsAssignment_2_06691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_2_16730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_16761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__AndExpression__OperationsAssignment_2_06797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_2_16836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_16867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__EqualityExpression__OperationsAssignment_2_0_06903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_2_0_16942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__EqualityExpression__OperationsAssignment_2_1_06978 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_2_1_17017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_17048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_2_0_17079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_2_1_17110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_17141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__MultiplicativeExpression__OperationsAssignment_2_0_07177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_2_0_17216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MultiplicativeExpression__OperationsAssignment_2_1_07252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_2_1_17291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__UnaryExpression__OperationsAssignment_0_17327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__ParametersAssignment_0_27366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rule__PrimaryExpression__ParametersAssignment_0_17397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__PrimaryExpression__ParametersAssignment_1_17428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__FunctionCall__NameAssignment_07459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__FunctionCall__OperandsAssignment_27494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__Literal__ValueStringAssignment_0_17531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_1_17562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Literal__OperationsAssignment_2_17598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_2_37637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_2_57668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_rule__Literal__ElementsAssignment_4_17699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_rule__Literal__ElementsAssignment_5_17730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__Literal__ExpressionTypeAssignment_6_17766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_6_27805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_6_3_17836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__Literal__ElementsAssignment_7_17872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__Literal__ElementsAssignment_8_17916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__Literal__ElementsAssignment_9_17960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__Literal__ElementsAssignment_10_18004 = new BitSet(new long[]{0x0000000000000002L});

}