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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_LITERALSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'.'", "'..'", "'['", "']'", "'/'", "'?'", "'+'", "'-'", "'('", "')'", "','", "'}'", "'$'", "'OR'", "'AND'", "'=='", "'!='", "'*'", "'!'", "'FORALL'", "'{'", "'NULL'", "'TODAY'", "'TRUE'", "'FALSE'"
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:70:1: ruleTopLevel : ( ( rule__TopLevel__ToplevelExpressionAssignment ) ) ;
    public final void ruleTopLevel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:74:2: ( ( ( rule__TopLevel__ToplevelExpressionAssignment ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:75:1: ( ( rule__TopLevel__ToplevelExpressionAssignment ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:75:1: ( ( rule__TopLevel__ToplevelExpressionAssignment ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:76:1: ( rule__TopLevel__ToplevelExpressionAssignment )
            {
             before(grammarAccess.getTopLevelAccess().getToplevelExpressionAssignment()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:77:1: ( rule__TopLevel__ToplevelExpressionAssignment )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:77:2: rule__TopLevel__ToplevelExpressionAssignment
            {
            pushFollow(FOLLOW_rule__TopLevel__ToplevelExpressionAssignment_in_ruleTopLevel94);
            rule__TopLevel__ToplevelExpressionAssignment();
            _fsp--;


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


    // $ANTLR start entryRuleTmlExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:117:1: entryRuleTmlExpression : ruleTmlExpression EOF ;
    public final void entryRuleTmlExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:118:1: ( ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:119:1: ruleTmlExpression EOF
            {
             before(grammarAccess.getTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression181);
            ruleTmlExpression();
            _fsp--;

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
    // $ANTLR end entryRuleTmlExpression


    // $ANTLR start ruleTmlExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:126:1: ruleTmlExpression : ( ( rule__TmlExpression__Group__0 ) ) ;
    public final void ruleTmlExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:130:2: ( ( ( rule__TmlExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:131:1: ( ( rule__TmlExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:131:1: ( ( rule__TmlExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:132:1: ( rule__TmlExpression__Group__0 )
            {
             before(grammarAccess.getTmlExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:133:1: ( rule__TmlExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:133:2: rule__TmlExpression__Group__0
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__0_in_ruleTmlExpression214);
            rule__TmlExpression__Group__0();
            _fsp--;


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
    // $ANTLR end ruleTmlExpression


    // $ANTLR start entryRuleExistsTmlExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:145:1: entryRuleExistsTmlExpression : ruleExistsTmlExpression EOF ;
    public final void entryRuleExistsTmlExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:146:1: ( ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:147:1: ruleExistsTmlExpression EOF
            {
             before(grammarAccess.getExistsTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression241);
            ruleExistsTmlExpression();
            _fsp--;

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
    // $ANTLR end entryRuleExistsTmlExpression


    // $ANTLR start ruleExistsTmlExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:154:1: ruleExistsTmlExpression : ( ( rule__ExistsTmlExpression__Group__0 ) ) ;
    public final void ruleExistsTmlExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:158:2: ( ( ( rule__ExistsTmlExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:159:1: ( ( rule__ExistsTmlExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:159:1: ( ( rule__ExistsTmlExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:160:1: ( rule__ExistsTmlExpression__Group__0 )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:161:1: ( rule__ExistsTmlExpression__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:161:2: rule__ExistsTmlExpression__Group__0
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__0_in_ruleExistsTmlExpression274);
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


    // $ANTLR start entryRuleMapGetReference
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:173:1: entryRuleMapGetReference : ruleMapGetReference EOF ;
    public final void entryRuleMapGetReference() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:174:1: ( ruleMapGetReference EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:175:1: ruleMapGetReference EOF
            {
             before(grammarAccess.getMapGetReferenceRule()); 
            pushFollow(FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference301);
            ruleMapGetReference();
            _fsp--;

             after(grammarAccess.getMapGetReferenceRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMapGetReference308); 

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
    // $ANTLR end entryRuleMapGetReference


    // $ANTLR start ruleMapGetReference
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:182:1: ruleMapGetReference : ( ( rule__MapGetReference__Group__0 ) ) ;
    public final void ruleMapGetReference() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:186:2: ( ( ( rule__MapGetReference__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:187:1: ( ( rule__MapGetReference__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:187:1: ( ( rule__MapGetReference__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:188:1: ( rule__MapGetReference__Group__0 )
            {
             before(grammarAccess.getMapGetReferenceAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:189:1: ( rule__MapGetReference__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:189:2: rule__MapGetReference__Group__0
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__0_in_ruleMapGetReference334);
            rule__MapGetReference__Group__0();
            _fsp--;


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
    // $ANTLR end ruleMapGetReference


    // $ANTLR start entryRuleOrExpression
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:201:1: entryRuleOrExpression : ruleOrExpression EOF ;
    public final void entryRuleOrExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:202:1: ( ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:203:1: ruleOrExpression EOF
            {
             before(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression361);
            ruleOrExpression();
            _fsp--;

             after(grammarAccess.getOrExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression368); 

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
            pushFollow(FOLLOW_rule__OrExpression__Group__0_in_ruleOrExpression394);
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
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression421);
            ruleAndExpression();
            _fsp--;

             after(grammarAccess.getAndExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression428); 

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
            pushFollow(FOLLOW_rule__AndExpression__Group__0_in_ruleAndExpression454);
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
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression481);
            ruleEqualityExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression488); 

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
            pushFollow(FOLLOW_rule__EqualityExpression__Group__0_in_ruleEqualityExpression514);
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
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression541);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getAdditiveExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression548); 

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
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__0_in_ruleAdditiveExpression574);
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
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression601);
            ruleMultiplicativeExpression();
            _fsp--;

             after(grammarAccess.getMultiplicativeExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression608); 

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
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__0_in_ruleMultiplicativeExpression634);
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
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression661);
            ruleUnaryExpression();
            _fsp--;

             after(grammarAccess.getUnaryExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression668); 

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
            pushFollow(FOLLOW_rule__UnaryExpression__Alternatives_in_ruleUnaryExpression694);
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
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression721);
            rulePrimaryExpression();
            _fsp--;

             after(grammarAccess.getPrimaryExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression728); 

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
            pushFollow(FOLLOW_rule__PrimaryExpression__Alternatives_in_rulePrimaryExpression754);
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


    // $ANTLR start entryRuleFunctionName
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:397:1: entryRuleFunctionName : ruleFunctionName EOF ;
    public final void entryRuleFunctionName() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:398:1: ( ruleFunctionName EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:399:1: ruleFunctionName EOF
            {
             before(grammarAccess.getFunctionNameRule()); 
            pushFollow(FOLLOW_ruleFunctionName_in_entryRuleFunctionName781);
            ruleFunctionName();
            _fsp--;

             after(grammarAccess.getFunctionNameRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionName788); 

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
    // $ANTLR end entryRuleFunctionName


    // $ANTLR start ruleFunctionName
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:406:1: ruleFunctionName : ( RULE_ID ) ;
    public final void ruleFunctionName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:410:2: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:411:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:411:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:412:1: RULE_ID
            {
             before(grammarAccess.getFunctionNameAccess().getIDTerminalRuleCall()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFunctionName814); 
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
    // $ANTLR end ruleFunctionName


    // $ANTLR start entryRuleFunctionCall
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:425:1: entryRuleFunctionCall : ruleFunctionCall EOF ;
    public final void entryRuleFunctionCall() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:426:1: ( ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:427:1: ruleFunctionCall EOF
            {
             before(grammarAccess.getFunctionCallRule()); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall840);
            ruleFunctionCall();
            _fsp--;

             after(grammarAccess.getFunctionCallRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall847); 

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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:434:1: ruleFunctionCall : ( ( rule__FunctionCall__Group__0 ) ) ;
    public final void ruleFunctionCall() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:438:2: ( ( ( rule__FunctionCall__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:439:1: ( ( rule__FunctionCall__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:439:1: ( ( rule__FunctionCall__Group__0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:440:1: ( rule__FunctionCall__Group__0 )
            {
             before(grammarAccess.getFunctionCallAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:441:1: ( rule__FunctionCall__Group__0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:441:2: rule__FunctionCall__Group__0
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__0_in_ruleFunctionCall873);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:453:1: entryRuleLiteral : ruleLiteral EOF ;
    public final void entryRuleLiteral() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:454:1: ( ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:455:1: ruleLiteral EOF
            {
             before(grammarAccess.getLiteralRule()); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral900);
            ruleLiteral();
            _fsp--;

             after(grammarAccess.getLiteralRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral907); 

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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:462:1: ruleLiteral : ( ( rule__Literal__Alternatives ) ) ;
    public final void ruleLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:466:2: ( ( ( rule__Literal__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:467:1: ( ( rule__Literal__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:467:1: ( ( rule__Literal__Alternatives ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:468:1: ( rule__Literal__Alternatives )
            {
             before(grammarAccess.getLiteralAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:469:1: ( rule__Literal__Alternatives )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:469:2: rule__Literal__Alternatives
            {
            pushFollow(FOLLOW_rule__Literal__Alternatives_in_ruleLiteral933);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:481:1: rule__PathElement__Alternatives : ( ( RULE_ID ) | ( '.' ) | ( '..' ) );
    public final void rule__PathElement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:485:1: ( ( RULE_ID ) | ( '.' ) | ( '..' ) )
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
                    new NoViableAltException("481:1: rule__PathElement__Alternatives : ( ( RULE_ID ) | ( '.' ) | ( '..' ) );", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:486:1: ( RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:486:1: ( RULE_ID )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:487:1: RULE_ID
                    {
                     before(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PathElement__Alternatives969); 
                     after(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:492:6: ( '.' )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:492:6: ( '.' )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:493:1: '.'
                    {
                     before(grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 
                    match(input,10,FOLLOW_10_in_rule__PathElement__Alternatives987); 
                     after(grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:500:6: ( '..' )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:500:6: ( '..' )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:501:1: '..'
                    {
                     before(grammarAccess.getPathElementAccess().getFullStopFullStopKeyword_2()); 
                    match(input,11,FOLLOW_11_in_rule__PathElement__Alternatives1007); 
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


    // $ANTLR start rule__EqualityExpression__Alternatives_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:513:1: rule__EqualityExpression__Alternatives_1 : ( ( ( rule__EqualityExpression__Group_1_0__0 ) ) | ( ( rule__EqualityExpression__Group_1_1__0 ) ) );
    public final void rule__EqualityExpression__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:517:1: ( ( ( rule__EqualityExpression__Group_1_0__0 ) ) | ( ( rule__EqualityExpression__Group_1_1__0 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==25) ) {
                alt2=1;
            }
            else if ( (LA2_0==26) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("513:1: rule__EqualityExpression__Alternatives_1 : ( ( ( rule__EqualityExpression__Group_1_0__0 ) ) | ( ( rule__EqualityExpression__Group_1_1__0 ) ) );", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:518:1: ( ( rule__EqualityExpression__Group_1_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:518:1: ( ( rule__EqualityExpression__Group_1_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:519:1: ( rule__EqualityExpression__Group_1_0__0 )
                    {
                     before(grammarAccess.getEqualityExpressionAccess().getGroup_1_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:520:1: ( rule__EqualityExpression__Group_1_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:520:2: rule__EqualityExpression__Group_1_0__0
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Group_1_0__0_in_rule__EqualityExpression__Alternatives_11041);
                    rule__EqualityExpression__Group_1_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getEqualityExpressionAccess().getGroup_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:524:6: ( ( rule__EqualityExpression__Group_1_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:524:6: ( ( rule__EqualityExpression__Group_1_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:525:1: ( rule__EqualityExpression__Group_1_1__0 )
                    {
                     before(grammarAccess.getEqualityExpressionAccess().getGroup_1_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:526:1: ( rule__EqualityExpression__Group_1_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:526:2: rule__EqualityExpression__Group_1_1__0
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Group_1_1__0_in_rule__EqualityExpression__Alternatives_11059);
                    rule__EqualityExpression__Group_1_1__0();
                    _fsp--;


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
    // $ANTLR end rule__EqualityExpression__Alternatives_1


    // $ANTLR start rule__AdditiveExpression__Alternatives_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:535:1: rule__AdditiveExpression__Alternatives_1 : ( ( ( rule__AdditiveExpression__Group_1_0__0 ) ) | ( ( rule__AdditiveExpression__Group_1_1__0 ) ) );
    public final void rule__AdditiveExpression__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:539:1: ( ( ( rule__AdditiveExpression__Group_1_0__0 ) ) | ( ( rule__AdditiveExpression__Group_1_1__0 ) ) )
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
                    new NoViableAltException("535:1: rule__AdditiveExpression__Alternatives_1 : ( ( ( rule__AdditiveExpression__Group_1_0__0 ) ) | ( ( rule__AdditiveExpression__Group_1_1__0 ) ) );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:540:1: ( ( rule__AdditiveExpression__Group_1_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:540:1: ( ( rule__AdditiveExpression__Group_1_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:541:1: ( rule__AdditiveExpression__Group_1_0__0 )
                    {
                     before(grammarAccess.getAdditiveExpressionAccess().getGroup_1_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:542:1: ( rule__AdditiveExpression__Group_1_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:542:2: rule__AdditiveExpression__Group_1_0__0
                    {
                    pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_0__0_in_rule__AdditiveExpression__Alternatives_11092);
                    rule__AdditiveExpression__Group_1_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getAdditiveExpressionAccess().getGroup_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:546:6: ( ( rule__AdditiveExpression__Group_1_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:546:6: ( ( rule__AdditiveExpression__Group_1_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:547:1: ( rule__AdditiveExpression__Group_1_1__0 )
                    {
                     before(grammarAccess.getAdditiveExpressionAccess().getGroup_1_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:548:1: ( rule__AdditiveExpression__Group_1_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:548:2: rule__AdditiveExpression__Group_1_1__0
                    {
                    pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_1__0_in_rule__AdditiveExpression__Alternatives_11110);
                    rule__AdditiveExpression__Group_1_1__0();
                    _fsp--;


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
    // $ANTLR end rule__AdditiveExpression__Alternatives_1


    // $ANTLR start rule__MultiplicativeExpression__Alternatives_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:557:1: rule__MultiplicativeExpression__Alternatives_1 : ( ( ( rule__MultiplicativeExpression__Group_1_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_1_1__0 ) ) );
    public final void rule__MultiplicativeExpression__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:561:1: ( ( ( rule__MultiplicativeExpression__Group_1_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_1_1__0 ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==27) ) {
                alt4=1;
            }
            else if ( (LA4_0==14) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("557:1: rule__MultiplicativeExpression__Alternatives_1 : ( ( ( rule__MultiplicativeExpression__Group_1_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_1_1__0 ) ) );", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:562:1: ( ( rule__MultiplicativeExpression__Group_1_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:562:1: ( ( rule__MultiplicativeExpression__Group_1_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:563:1: ( rule__MultiplicativeExpression__Group_1_0__0 )
                    {
                     before(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:564:1: ( rule__MultiplicativeExpression__Group_1_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:564:2: rule__MultiplicativeExpression__Group_1_0__0
                    {
                    pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_0__0_in_rule__MultiplicativeExpression__Alternatives_11143);
                    rule__MultiplicativeExpression__Group_1_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:568:6: ( ( rule__MultiplicativeExpression__Group_1_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:568:6: ( ( rule__MultiplicativeExpression__Group_1_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:569:1: ( rule__MultiplicativeExpression__Group_1_1__0 )
                    {
                     before(grammarAccess.getMultiplicativeExpressionAccess().getGroup_1_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:570:1: ( rule__MultiplicativeExpression__Group_1_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:570:2: rule__MultiplicativeExpression__Group_1_1__0
                    {
                    pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_1__0_in_rule__MultiplicativeExpression__Alternatives_11161);
                    rule__MultiplicativeExpression__Group_1_1__0();
                    _fsp--;


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
    // $ANTLR end rule__MultiplicativeExpression__Alternatives_1


    // $ANTLR start rule__UnaryExpression__Alternatives
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:579:1: rule__UnaryExpression__Alternatives : ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) );
    public final void rule__UnaryExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:583:1: ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==28) ) {
                alt5=1;
            }
            else if ( ((LA5_0>=RULE_ID && LA5_0<=RULE_LITERALSTRING)||LA5_0==12||LA5_0==15||LA5_0==18||LA5_0==22||(LA5_0>=29 && LA5_0<=34)) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("579:1: rule__UnaryExpression__Alternatives : ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) );", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:584:1: ( ( rule__UnaryExpression__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:584:1: ( ( rule__UnaryExpression__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:585:1: ( rule__UnaryExpression__Group_0__0 )
                    {
                     before(grammarAccess.getUnaryExpressionAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:586:1: ( rule__UnaryExpression__Group_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:586:2: rule__UnaryExpression__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__UnaryExpression__Group_0__0_in_rule__UnaryExpression__Alternatives1194);
                    rule__UnaryExpression__Group_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getUnaryExpressionAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:590:6: ( rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:590:6: ( rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:591:1: rulePrimaryExpression
                    {
                     before(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1()); 
                    pushFollow(FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__Alternatives1212);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:601:1: rule__PrimaryExpression__Alternatives : ( ( ( rule__PrimaryExpression__ParametersAssignment_0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) );
    public final void rule__PrimaryExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:605:1: ( ( ( rule__PrimaryExpression__ParametersAssignment_0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0>=RULE_ID && LA6_0<=RULE_LITERALSTRING)||LA6_0==12||LA6_0==15||LA6_0==22||(LA6_0>=29 && LA6_0<=34)) ) {
                alt6=1;
            }
            else if ( (LA6_0==18) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("601:1: rule__PrimaryExpression__Alternatives : ( ( ( rule__PrimaryExpression__ParametersAssignment_0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) );", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:606:1: ( ( rule__PrimaryExpression__ParametersAssignment_0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:606:1: ( ( rule__PrimaryExpression__ParametersAssignment_0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:607:1: ( rule__PrimaryExpression__ParametersAssignment_0 )
                    {
                     before(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:608:1: ( rule__PrimaryExpression__ParametersAssignment_0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:608:2: rule__PrimaryExpression__ParametersAssignment_0
                    {
                    pushFollow(FOLLOW_rule__PrimaryExpression__ParametersAssignment_0_in_rule__PrimaryExpression__Alternatives1244);
                    rule__PrimaryExpression__ParametersAssignment_0();
                    _fsp--;


                    }

                     after(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:612:6: ( ( rule__PrimaryExpression__Group_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:612:6: ( ( rule__PrimaryExpression__Group_1__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:613:1: ( rule__PrimaryExpression__Group_1__0 )
                    {
                     before(grammarAccess.getPrimaryExpressionAccess().getGroup_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:614:1: ( rule__PrimaryExpression__Group_1__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:614:2: rule__PrimaryExpression__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__0_in_rule__PrimaryExpression__Alternatives1262);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:623:1: rule__Literal__Alternatives : ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__ValueStringAssignment_1 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__ElementsAssignment_5 ) ) | ( ( rule__Literal__ElementsAssignment_6 ) ) | ( ( rule__Literal__ElementsAssignment_7 ) ) | ( ( rule__Literal__ElementsAssignment_8 ) ) | ( ( rule__Literal__ParametersAssignment_9 ) ) | ( ( rule__Literal__ParametersAssignment_10 ) ) | ( ( rule__Literal__ParametersAssignment_11 ) ) );
    public final void rule__Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:627:1: ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__ValueStringAssignment_1 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__ElementsAssignment_5 ) ) | ( ( rule__Literal__ElementsAssignment_6 ) ) | ( ( rule__Literal__ElementsAssignment_7 ) ) | ( ( rule__Literal__ElementsAssignment_8 ) ) | ( ( rule__Literal__ParametersAssignment_9 ) ) | ( ( rule__Literal__ParametersAssignment_10 ) ) | ( ( rule__Literal__ParametersAssignment_11 ) ) )
            int alt7=12;
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
            case 29:
                {
                alt7=3;
                }
                break;
            case RULE_ID:
                {
                alt7=4;
                }
                break;
            case 30:
                {
                alt7=5;
                }
                break;
            case 31:
                {
                alt7=6;
                }
                break;
            case 32:
                {
                alt7=7;
                }
                break;
            case 33:
                {
                alt7=8;
                }
                break;
            case 34:
                {
                alt7=9;
                }
                break;
            case 12:
                {
                alt7=10;
                }
                break;
            case 15:
                {
                alt7=11;
                }
                break;
            case 22:
                {
                alt7=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("623:1: rule__Literal__Alternatives : ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__ValueStringAssignment_1 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__ElementsAssignment_5 ) ) | ( ( rule__Literal__ElementsAssignment_6 ) ) | ( ( rule__Literal__ElementsAssignment_7 ) ) | ( ( rule__Literal__ElementsAssignment_8 ) ) | ( ( rule__Literal__ParametersAssignment_9 ) ) | ( ( rule__Literal__ParametersAssignment_10 ) ) | ( ( rule__Literal__ParametersAssignment_11 ) ) );", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:628:1: ( ( rule__Literal__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:628:1: ( ( rule__Literal__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:629:1: ( rule__Literal__Group_0__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:630:1: ( rule__Literal__Group_0__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:630:2: rule__Literal__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_0__0_in_rule__Literal__Alternatives1295);
                    rule__Literal__Group_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:634:6: ( ( rule__Literal__ValueStringAssignment_1 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:634:6: ( ( rule__Literal__ValueStringAssignment_1 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:635:1: ( rule__Literal__ValueStringAssignment_1 )
                    {
                     before(grammarAccess.getLiteralAccess().getValueStringAssignment_1()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:636:1: ( rule__Literal__ValueStringAssignment_1 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:636:2: rule__Literal__ValueStringAssignment_1
                    {
                    pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_1_in_rule__Literal__Alternatives1313);
                    rule__Literal__ValueStringAssignment_1();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getValueStringAssignment_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:640:6: ( ( rule__Literal__Group_2__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:640:6: ( ( rule__Literal__Group_2__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:641:1: ( rule__Literal__Group_2__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_2()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:642:1: ( rule__Literal__Group_2__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:642:2: rule__Literal__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_2__0_in_rule__Literal__Alternatives1331);
                    rule__Literal__Group_2__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:646:6: ( ruleFunctionCall )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:646:6: ( ruleFunctionCall )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:647:1: ruleFunctionCall
                    {
                     before(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3()); 
                    pushFollow(FOLLOW_ruleFunctionCall_in_rule__Literal__Alternatives1349);
                    ruleFunctionCall();
                    _fsp--;

                     after(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3()); 

                    }


                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:652:6: ( ( rule__Literal__Group_4__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:652:6: ( ( rule__Literal__Group_4__0 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:653:1: ( rule__Literal__Group_4__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_4()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:654:1: ( rule__Literal__Group_4__0 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:654:2: rule__Literal__Group_4__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_4__0_in_rule__Literal__Alternatives1366);
                    rule__Literal__Group_4__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_4()); 

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:658:6: ( ( rule__Literal__ElementsAssignment_5 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:658:6: ( ( rule__Literal__ElementsAssignment_5 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:659:1: ( rule__Literal__ElementsAssignment_5 )
                    {
                     before(grammarAccess.getLiteralAccess().getElementsAssignment_5()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:660:1: ( rule__Literal__ElementsAssignment_5 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:660:2: rule__Literal__ElementsAssignment_5
                    {
                    pushFollow(FOLLOW_rule__Literal__ElementsAssignment_5_in_rule__Literal__Alternatives1384);
                    rule__Literal__ElementsAssignment_5();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getElementsAssignment_5()); 

                    }


                    }
                    break;
                case 7 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:664:6: ( ( rule__Literal__ElementsAssignment_6 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:664:6: ( ( rule__Literal__ElementsAssignment_6 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:665:1: ( rule__Literal__ElementsAssignment_6 )
                    {
                     before(grammarAccess.getLiteralAccess().getElementsAssignment_6()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:666:1: ( rule__Literal__ElementsAssignment_6 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:666:2: rule__Literal__ElementsAssignment_6
                    {
                    pushFollow(FOLLOW_rule__Literal__ElementsAssignment_6_in_rule__Literal__Alternatives1402);
                    rule__Literal__ElementsAssignment_6();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getElementsAssignment_6()); 

                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:670:6: ( ( rule__Literal__ElementsAssignment_7 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:670:6: ( ( rule__Literal__ElementsAssignment_7 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:671:1: ( rule__Literal__ElementsAssignment_7 )
                    {
                     before(grammarAccess.getLiteralAccess().getElementsAssignment_7()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:672:1: ( rule__Literal__ElementsAssignment_7 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:672:2: rule__Literal__ElementsAssignment_7
                    {
                    pushFollow(FOLLOW_rule__Literal__ElementsAssignment_7_in_rule__Literal__Alternatives1420);
                    rule__Literal__ElementsAssignment_7();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getElementsAssignment_7()); 

                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:676:6: ( ( rule__Literal__ElementsAssignment_8 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:676:6: ( ( rule__Literal__ElementsAssignment_8 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:677:1: ( rule__Literal__ElementsAssignment_8 )
                    {
                     before(grammarAccess.getLiteralAccess().getElementsAssignment_8()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:678:1: ( rule__Literal__ElementsAssignment_8 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:678:2: rule__Literal__ElementsAssignment_8
                    {
                    pushFollow(FOLLOW_rule__Literal__ElementsAssignment_8_in_rule__Literal__Alternatives1438);
                    rule__Literal__ElementsAssignment_8();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getElementsAssignment_8()); 

                    }


                    }
                    break;
                case 10 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:682:6: ( ( rule__Literal__ParametersAssignment_9 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:682:6: ( ( rule__Literal__ParametersAssignment_9 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:683:1: ( rule__Literal__ParametersAssignment_9 )
                    {
                     before(grammarAccess.getLiteralAccess().getParametersAssignment_9()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:684:1: ( rule__Literal__ParametersAssignment_9 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:684:2: rule__Literal__ParametersAssignment_9
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_9_in_rule__Literal__Alternatives1456);
                    rule__Literal__ParametersAssignment_9();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getParametersAssignment_9()); 

                    }


                    }
                    break;
                case 11 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:688:6: ( ( rule__Literal__ParametersAssignment_10 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:688:6: ( ( rule__Literal__ParametersAssignment_10 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:689:1: ( rule__Literal__ParametersAssignment_10 )
                    {
                     before(grammarAccess.getLiteralAccess().getParametersAssignment_10()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:690:1: ( rule__Literal__ParametersAssignment_10 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:690:2: rule__Literal__ParametersAssignment_10
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_10_in_rule__Literal__Alternatives1474);
                    rule__Literal__ParametersAssignment_10();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getParametersAssignment_10()); 

                    }


                    }
                    break;
                case 12 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:694:6: ( ( rule__Literal__ParametersAssignment_11 ) )
                    {
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:694:6: ( ( rule__Literal__ParametersAssignment_11 ) )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:695:1: ( rule__Literal__ParametersAssignment_11 )
                    {
                     before(grammarAccess.getLiteralAccess().getParametersAssignment_11()); 
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:696:1: ( rule__Literal__ParametersAssignment_11 )
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:696:2: rule__Literal__ParametersAssignment_11
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_11_in_rule__Literal__Alternatives1492);
                    rule__Literal__ParametersAssignment_11();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getParametersAssignment_11()); 

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


    // $ANTLR start rule__TmlExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:707:1: rule__TmlExpression__Group__0 : rule__TmlExpression__Group__0__Impl rule__TmlExpression__Group__1 ;
    public final void rule__TmlExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:711:1: ( rule__TmlExpression__Group__0__Impl rule__TmlExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:712:2: rule__TmlExpression__Group__0__Impl rule__TmlExpression__Group__1
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__0__Impl_in_rule__TmlExpression__Group__01523);
            rule__TmlExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__1_in_rule__TmlExpression__Group__01526);
            rule__TmlExpression__Group__1();
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
    // $ANTLR end rule__TmlExpression__Group__0


    // $ANTLR start rule__TmlExpression__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:719:1: rule__TmlExpression__Group__0__Impl : ( '[' ) ;
    public final void rule__TmlExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:723:1: ( ( '[' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:724:1: ( '[' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:724:1: ( '[' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:725:1: '['
            {
             before(grammarAccess.getTmlExpressionAccess().getLeftSquareBracketKeyword_0()); 
            match(input,12,FOLLOW_12_in_rule__TmlExpression__Group__0__Impl1554); 
             after(grammarAccess.getTmlExpressionAccess().getLeftSquareBracketKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__Group__0__Impl


    // $ANTLR start rule__TmlExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:738:1: rule__TmlExpression__Group__1 : rule__TmlExpression__Group__1__Impl rule__TmlExpression__Group__2 ;
    public final void rule__TmlExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:742:1: ( rule__TmlExpression__Group__1__Impl rule__TmlExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:743:2: rule__TmlExpression__Group__1__Impl rule__TmlExpression__Group__2
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__1__Impl_in_rule__TmlExpression__Group__11585);
            rule__TmlExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__2_in_rule__TmlExpression__Group__11588);
            rule__TmlExpression__Group__2();
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
    // $ANTLR end rule__TmlExpression__Group__1


    // $ANTLR start rule__TmlExpression__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:750:1: rule__TmlExpression__Group__1__Impl : ( ( rule__TmlExpression__AbsoluteAssignment_1 )? ) ;
    public final void rule__TmlExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:754:1: ( ( ( rule__TmlExpression__AbsoluteAssignment_1 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:755:1: ( ( rule__TmlExpression__AbsoluteAssignment_1 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:755:1: ( ( rule__TmlExpression__AbsoluteAssignment_1 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:756:1: ( rule__TmlExpression__AbsoluteAssignment_1 )?
            {
             before(grammarAccess.getTmlExpressionAccess().getAbsoluteAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:757:1: ( rule__TmlExpression__AbsoluteAssignment_1 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==14) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:757:2: rule__TmlExpression__AbsoluteAssignment_1
                    {
                    pushFollow(FOLLOW_rule__TmlExpression__AbsoluteAssignment_1_in_rule__TmlExpression__Group__1__Impl1615);
                    rule__TmlExpression__AbsoluteAssignment_1();
                    _fsp--;


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
    // $ANTLR end rule__TmlExpression__Group__1__Impl


    // $ANTLR start rule__TmlExpression__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:767:1: rule__TmlExpression__Group__2 : rule__TmlExpression__Group__2__Impl rule__TmlExpression__Group__3 ;
    public final void rule__TmlExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:771:1: ( rule__TmlExpression__Group__2__Impl rule__TmlExpression__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:772:2: rule__TmlExpression__Group__2__Impl rule__TmlExpression__Group__3
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__2__Impl_in_rule__TmlExpression__Group__21646);
            rule__TmlExpression__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__3_in_rule__TmlExpression__Group__21649);
            rule__TmlExpression__Group__3();
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
    // $ANTLR end rule__TmlExpression__Group__2


    // $ANTLR start rule__TmlExpression__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:779:1: rule__TmlExpression__Group__2__Impl : ( ( rule__TmlExpression__ElementsAssignment_2 ) ) ;
    public final void rule__TmlExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:783:1: ( ( ( rule__TmlExpression__ElementsAssignment_2 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:784:1: ( ( rule__TmlExpression__ElementsAssignment_2 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:784:1: ( ( rule__TmlExpression__ElementsAssignment_2 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:785:1: ( rule__TmlExpression__ElementsAssignment_2 )
            {
             before(grammarAccess.getTmlExpressionAccess().getElementsAssignment_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:786:1: ( rule__TmlExpression__ElementsAssignment_2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:786:2: rule__TmlExpression__ElementsAssignment_2
            {
            pushFollow(FOLLOW_rule__TmlExpression__ElementsAssignment_2_in_rule__TmlExpression__Group__2__Impl1676);
            rule__TmlExpression__ElementsAssignment_2();
            _fsp--;


            }

             after(grammarAccess.getTmlExpressionAccess().getElementsAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__Group__2__Impl


    // $ANTLR start rule__TmlExpression__Group__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:796:1: rule__TmlExpression__Group__3 : rule__TmlExpression__Group__3__Impl rule__TmlExpression__Group__4 ;
    public final void rule__TmlExpression__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:800:1: ( rule__TmlExpression__Group__3__Impl rule__TmlExpression__Group__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:801:2: rule__TmlExpression__Group__3__Impl rule__TmlExpression__Group__4
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__3__Impl_in_rule__TmlExpression__Group__31706);
            rule__TmlExpression__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group__4_in_rule__TmlExpression__Group__31709);
            rule__TmlExpression__Group__4();
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
    // $ANTLR end rule__TmlExpression__Group__3


    // $ANTLR start rule__TmlExpression__Group__3__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:808:1: rule__TmlExpression__Group__3__Impl : ( ( rule__TmlExpression__Group_3__0 )* ) ;
    public final void rule__TmlExpression__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:812:1: ( ( ( rule__TmlExpression__Group_3__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:813:1: ( ( rule__TmlExpression__Group_3__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:813:1: ( ( rule__TmlExpression__Group_3__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:814:1: ( rule__TmlExpression__Group_3__0 )*
            {
             before(grammarAccess.getTmlExpressionAccess().getGroup_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:815:1: ( rule__TmlExpression__Group_3__0 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==14) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:815:2: rule__TmlExpression__Group_3__0
            	    {
            	    pushFollow(FOLLOW_rule__TmlExpression__Group_3__0_in_rule__TmlExpression__Group__3__Impl1736);
            	    rule__TmlExpression__Group_3__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

             after(grammarAccess.getTmlExpressionAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__Group__3__Impl


    // $ANTLR start rule__TmlExpression__Group__4
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:825:1: rule__TmlExpression__Group__4 : rule__TmlExpression__Group__4__Impl ;
    public final void rule__TmlExpression__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:829:1: ( rule__TmlExpression__Group__4__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:830:2: rule__TmlExpression__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group__4__Impl_in_rule__TmlExpression__Group__41767);
            rule__TmlExpression__Group__4__Impl();
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
    // $ANTLR end rule__TmlExpression__Group__4


    // $ANTLR start rule__TmlExpression__Group__4__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:836:1: rule__TmlExpression__Group__4__Impl : ( ']' ) ;
    public final void rule__TmlExpression__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:840:1: ( ( ']' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:841:1: ( ']' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:841:1: ( ']' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:842:1: ']'
            {
             before(grammarAccess.getTmlExpressionAccess().getRightSquareBracketKeyword_4()); 
            match(input,13,FOLLOW_13_in_rule__TmlExpression__Group__4__Impl1795); 
             after(grammarAccess.getTmlExpressionAccess().getRightSquareBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__Group__4__Impl


    // $ANTLR start rule__TmlExpression__Group_3__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:865:1: rule__TmlExpression__Group_3__0 : rule__TmlExpression__Group_3__0__Impl rule__TmlExpression__Group_3__1 ;
    public final void rule__TmlExpression__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:869:1: ( rule__TmlExpression__Group_3__0__Impl rule__TmlExpression__Group_3__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:870:2: rule__TmlExpression__Group_3__0__Impl rule__TmlExpression__Group_3__1
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group_3__0__Impl_in_rule__TmlExpression__Group_3__01836);
            rule__TmlExpression__Group_3__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__TmlExpression__Group_3__1_in_rule__TmlExpression__Group_3__01839);
            rule__TmlExpression__Group_3__1();
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
    // $ANTLR end rule__TmlExpression__Group_3__0


    // $ANTLR start rule__TmlExpression__Group_3__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:877:1: rule__TmlExpression__Group_3__0__Impl : ( '/' ) ;
    public final void rule__TmlExpression__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:881:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:882:1: ( '/' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:882:1: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:883:1: '/'
            {
             before(grammarAccess.getTmlExpressionAccess().getSolidusKeyword_3_0()); 
            match(input,14,FOLLOW_14_in_rule__TmlExpression__Group_3__0__Impl1867); 
             after(grammarAccess.getTmlExpressionAccess().getSolidusKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__Group_3__0__Impl


    // $ANTLR start rule__TmlExpression__Group_3__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:896:1: rule__TmlExpression__Group_3__1 : rule__TmlExpression__Group_3__1__Impl ;
    public final void rule__TmlExpression__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:900:1: ( rule__TmlExpression__Group_3__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:901:2: rule__TmlExpression__Group_3__1__Impl
            {
            pushFollow(FOLLOW_rule__TmlExpression__Group_3__1__Impl_in_rule__TmlExpression__Group_3__11898);
            rule__TmlExpression__Group_3__1__Impl();
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
    // $ANTLR end rule__TmlExpression__Group_3__1


    // $ANTLR start rule__TmlExpression__Group_3__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:907:1: rule__TmlExpression__Group_3__1__Impl : ( ( rule__TmlExpression__ElementsAssignment_3_1 ) ) ;
    public final void rule__TmlExpression__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:911:1: ( ( ( rule__TmlExpression__ElementsAssignment_3_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:912:1: ( ( rule__TmlExpression__ElementsAssignment_3_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:912:1: ( ( rule__TmlExpression__ElementsAssignment_3_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:913:1: ( rule__TmlExpression__ElementsAssignment_3_1 )
            {
             before(grammarAccess.getTmlExpressionAccess().getElementsAssignment_3_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:914:1: ( rule__TmlExpression__ElementsAssignment_3_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:914:2: rule__TmlExpression__ElementsAssignment_3_1
            {
            pushFollow(FOLLOW_rule__TmlExpression__ElementsAssignment_3_1_in_rule__TmlExpression__Group_3__1__Impl1925);
            rule__TmlExpression__ElementsAssignment_3_1();
            _fsp--;


            }

             after(grammarAccess.getTmlExpressionAccess().getElementsAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__Group_3__1__Impl


    // $ANTLR start rule__ExistsTmlExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:928:1: rule__ExistsTmlExpression__Group__0 : rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1 ;
    public final void rule__ExistsTmlExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:932:1: ( rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:933:2: rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__0__Impl_in_rule__ExistsTmlExpression__Group__01959);
            rule__ExistsTmlExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__1_in_rule__ExistsTmlExpression__Group__01962);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:940:1: rule__ExistsTmlExpression__Group__0__Impl : ( '?' ) ;
    public final void rule__ExistsTmlExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:944:1: ( ( '?' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:945:1: ( '?' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:945:1: ( '?' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:946:1: '?'
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getQuestionMarkKeyword_0()); 
            match(input,15,FOLLOW_15_in_rule__ExistsTmlExpression__Group__0__Impl1990); 
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:959:1: rule__ExistsTmlExpression__Group__1 : rule__ExistsTmlExpression__Group__1__Impl rule__ExistsTmlExpression__Group__2 ;
    public final void rule__ExistsTmlExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:963:1: ( rule__ExistsTmlExpression__Group__1__Impl rule__ExistsTmlExpression__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:964:2: rule__ExistsTmlExpression__Group__1__Impl rule__ExistsTmlExpression__Group__2
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__1__Impl_in_rule__ExistsTmlExpression__Group__12021);
            rule__ExistsTmlExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__2_in_rule__ExistsTmlExpression__Group__12024);
            rule__ExistsTmlExpression__Group__2();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:971:1: rule__ExistsTmlExpression__Group__1__Impl : ( '[' ) ;
    public final void rule__ExistsTmlExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:975:1: ( ( '[' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:976:1: ( '[' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:976:1: ( '[' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:977:1: '['
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getLeftSquareBracketKeyword_1()); 
            match(input,12,FOLLOW_12_in_rule__ExistsTmlExpression__Group__1__Impl2052); 
             after(grammarAccess.getExistsTmlExpressionAccess().getLeftSquareBracketKeyword_1()); 

            }


            }

        }
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


    // $ANTLR start rule__ExistsTmlExpression__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:990:1: rule__ExistsTmlExpression__Group__2 : rule__ExistsTmlExpression__Group__2__Impl rule__ExistsTmlExpression__Group__3 ;
    public final void rule__ExistsTmlExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:994:1: ( rule__ExistsTmlExpression__Group__2__Impl rule__ExistsTmlExpression__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:995:2: rule__ExistsTmlExpression__Group__2__Impl rule__ExistsTmlExpression__Group__3
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__2__Impl_in_rule__ExistsTmlExpression__Group__22083);
            rule__ExistsTmlExpression__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__3_in_rule__ExistsTmlExpression__Group__22086);
            rule__ExistsTmlExpression__Group__3();
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
    // $ANTLR end rule__ExistsTmlExpression__Group__2


    // $ANTLR start rule__ExistsTmlExpression__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1002:1: rule__ExistsTmlExpression__Group__2__Impl : ( ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )? ) ;
    public final void rule__ExistsTmlExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1006:1: ( ( ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1007:1: ( ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1007:1: ( ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1008:1: ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )?
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteAssignment_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1009:1: ( rule__ExistsTmlExpression__AbsoluteAssignment_2 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==14) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1009:2: rule__ExistsTmlExpression__AbsoluteAssignment_2
                    {
                    pushFollow(FOLLOW_rule__ExistsTmlExpression__AbsoluteAssignment_2_in_rule__ExistsTmlExpression__Group__2__Impl2113);
                    rule__ExistsTmlExpression__AbsoluteAssignment_2();
                    _fsp--;


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
    // $ANTLR end rule__ExistsTmlExpression__Group__2__Impl


    // $ANTLR start rule__ExistsTmlExpression__Group__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1019:1: rule__ExistsTmlExpression__Group__3 : rule__ExistsTmlExpression__Group__3__Impl rule__ExistsTmlExpression__Group__4 ;
    public final void rule__ExistsTmlExpression__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1023:1: ( rule__ExistsTmlExpression__Group__3__Impl rule__ExistsTmlExpression__Group__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1024:2: rule__ExistsTmlExpression__Group__3__Impl rule__ExistsTmlExpression__Group__4
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__3__Impl_in_rule__ExistsTmlExpression__Group__32144);
            rule__ExistsTmlExpression__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__4_in_rule__ExistsTmlExpression__Group__32147);
            rule__ExistsTmlExpression__Group__4();
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
    // $ANTLR end rule__ExistsTmlExpression__Group__3


    // $ANTLR start rule__ExistsTmlExpression__Group__3__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1031:1: rule__ExistsTmlExpression__Group__3__Impl : ( ( rule__ExistsTmlExpression__ElementsAssignment_3 ) ) ;
    public final void rule__ExistsTmlExpression__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1035:1: ( ( ( rule__ExistsTmlExpression__ElementsAssignment_3 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1036:1: ( ( rule__ExistsTmlExpression__ElementsAssignment_3 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1036:1: ( ( rule__ExistsTmlExpression__ElementsAssignment_3 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1037:1: ( rule__ExistsTmlExpression__ElementsAssignment_3 )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1038:1: ( rule__ExistsTmlExpression__ElementsAssignment_3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1038:2: rule__ExistsTmlExpression__ElementsAssignment_3
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__ElementsAssignment_3_in_rule__ExistsTmlExpression__Group__3__Impl2174);
            rule__ExistsTmlExpression__ElementsAssignment_3();
            _fsp--;


            }

             after(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__Group__3__Impl


    // $ANTLR start rule__ExistsTmlExpression__Group__4
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1048:1: rule__ExistsTmlExpression__Group__4 : rule__ExistsTmlExpression__Group__4__Impl rule__ExistsTmlExpression__Group__5 ;
    public final void rule__ExistsTmlExpression__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1052:1: ( rule__ExistsTmlExpression__Group__4__Impl rule__ExistsTmlExpression__Group__5 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1053:2: rule__ExistsTmlExpression__Group__4__Impl rule__ExistsTmlExpression__Group__5
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__4__Impl_in_rule__ExistsTmlExpression__Group__42204);
            rule__ExistsTmlExpression__Group__4__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__5_in_rule__ExistsTmlExpression__Group__42207);
            rule__ExistsTmlExpression__Group__5();
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
    // $ANTLR end rule__ExistsTmlExpression__Group__4


    // $ANTLR start rule__ExistsTmlExpression__Group__4__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1060:1: rule__ExistsTmlExpression__Group__4__Impl : ( ( rule__ExistsTmlExpression__Group_4__0 )* ) ;
    public final void rule__ExistsTmlExpression__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1064:1: ( ( ( rule__ExistsTmlExpression__Group_4__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1065:1: ( ( rule__ExistsTmlExpression__Group_4__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1065:1: ( ( rule__ExistsTmlExpression__Group_4__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1066:1: ( rule__ExistsTmlExpression__Group_4__0 )*
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getGroup_4()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1067:1: ( rule__ExistsTmlExpression__Group_4__0 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==14) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1067:2: rule__ExistsTmlExpression__Group_4__0
            	    {
            	    pushFollow(FOLLOW_rule__ExistsTmlExpression__Group_4__0_in_rule__ExistsTmlExpression__Group__4__Impl2234);
            	    rule__ExistsTmlExpression__Group_4__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

             after(grammarAccess.getExistsTmlExpressionAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__Group__4__Impl


    // $ANTLR start rule__ExistsTmlExpression__Group__5
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1077:1: rule__ExistsTmlExpression__Group__5 : rule__ExistsTmlExpression__Group__5__Impl ;
    public final void rule__ExistsTmlExpression__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1081:1: ( rule__ExistsTmlExpression__Group__5__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1082:2: rule__ExistsTmlExpression__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__5__Impl_in_rule__ExistsTmlExpression__Group__52265);
            rule__ExistsTmlExpression__Group__5__Impl();
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
    // $ANTLR end rule__ExistsTmlExpression__Group__5


    // $ANTLR start rule__ExistsTmlExpression__Group__5__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1088:1: rule__ExistsTmlExpression__Group__5__Impl : ( ']' ) ;
    public final void rule__ExistsTmlExpression__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1092:1: ( ( ']' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1093:1: ( ']' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1093:1: ( ']' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1094:1: ']'
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getRightSquareBracketKeyword_5()); 
            match(input,13,FOLLOW_13_in_rule__ExistsTmlExpression__Group__5__Impl2293); 
             after(grammarAccess.getExistsTmlExpressionAccess().getRightSquareBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__Group__5__Impl


    // $ANTLR start rule__ExistsTmlExpression__Group_4__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1119:1: rule__ExistsTmlExpression__Group_4__0 : rule__ExistsTmlExpression__Group_4__0__Impl rule__ExistsTmlExpression__Group_4__1 ;
    public final void rule__ExistsTmlExpression__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1123:1: ( rule__ExistsTmlExpression__Group_4__0__Impl rule__ExistsTmlExpression__Group_4__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1124:2: rule__ExistsTmlExpression__Group_4__0__Impl rule__ExistsTmlExpression__Group_4__1
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group_4__0__Impl_in_rule__ExistsTmlExpression__Group_4__02336);
            rule__ExistsTmlExpression__Group_4__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group_4__1_in_rule__ExistsTmlExpression__Group_4__02339);
            rule__ExistsTmlExpression__Group_4__1();
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
    // $ANTLR end rule__ExistsTmlExpression__Group_4__0


    // $ANTLR start rule__ExistsTmlExpression__Group_4__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1131:1: rule__ExistsTmlExpression__Group_4__0__Impl : ( '/' ) ;
    public final void rule__ExistsTmlExpression__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1135:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1136:1: ( '/' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1136:1: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1137:1: '/'
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getSolidusKeyword_4_0()); 
            match(input,14,FOLLOW_14_in_rule__ExistsTmlExpression__Group_4__0__Impl2367); 
             after(grammarAccess.getExistsTmlExpressionAccess().getSolidusKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__Group_4__0__Impl


    // $ANTLR start rule__ExistsTmlExpression__Group_4__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1150:1: rule__ExistsTmlExpression__Group_4__1 : rule__ExistsTmlExpression__Group_4__1__Impl ;
    public final void rule__ExistsTmlExpression__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1154:1: ( rule__ExistsTmlExpression__Group_4__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1155:2: rule__ExistsTmlExpression__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group_4__1__Impl_in_rule__ExistsTmlExpression__Group_4__12398);
            rule__ExistsTmlExpression__Group_4__1__Impl();
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
    // $ANTLR end rule__ExistsTmlExpression__Group_4__1


    // $ANTLR start rule__ExistsTmlExpression__Group_4__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1161:1: rule__ExistsTmlExpression__Group_4__1__Impl : ( ( rule__ExistsTmlExpression__ElementsAssignment_4_1 ) ) ;
    public final void rule__ExistsTmlExpression__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1165:1: ( ( ( rule__ExistsTmlExpression__ElementsAssignment_4_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1166:1: ( ( rule__ExistsTmlExpression__ElementsAssignment_4_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1166:1: ( ( rule__ExistsTmlExpression__ElementsAssignment_4_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1167:1: ( rule__ExistsTmlExpression__ElementsAssignment_4_1 )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_4_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1168:1: ( rule__ExistsTmlExpression__ElementsAssignment_4_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1168:2: rule__ExistsTmlExpression__ElementsAssignment_4_1
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__ElementsAssignment_4_1_in_rule__ExistsTmlExpression__Group_4__1__Impl2425);
            rule__ExistsTmlExpression__ElementsAssignment_4_1();
            _fsp--;


            }

             after(grammarAccess.getExistsTmlExpressionAccess().getElementsAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__Group_4__1__Impl


    // $ANTLR start rule__MapGetReference__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1182:1: rule__MapGetReference__Group__0 : rule__MapGetReference__Group__0__Impl rule__MapGetReference__Group__1 ;
    public final void rule__MapGetReference__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1186:1: ( rule__MapGetReference__Group__0__Impl rule__MapGetReference__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1187:2: rule__MapGetReference__Group__0__Impl rule__MapGetReference__Group__1
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__0__Impl_in_rule__MapGetReference__Group__02459);
            rule__MapGetReference__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MapGetReference__Group__1_in_rule__MapGetReference__Group__02462);
            rule__MapGetReference__Group__1();
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
    // $ANTLR end rule__MapGetReference__Group__0


    // $ANTLR start rule__MapGetReference__Group__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1194:1: rule__MapGetReference__Group__0__Impl : ( ( rule__MapGetReference__OperationsAssignment_0 ) ) ;
    public final void rule__MapGetReference__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1198:1: ( ( ( rule__MapGetReference__OperationsAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1199:1: ( ( rule__MapGetReference__OperationsAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1199:1: ( ( rule__MapGetReference__OperationsAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1200:1: ( rule__MapGetReference__OperationsAssignment_0 )
            {
             before(grammarAccess.getMapGetReferenceAccess().getOperationsAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1201:1: ( rule__MapGetReference__OperationsAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1201:2: rule__MapGetReference__OperationsAssignment_0
            {
            pushFollow(FOLLOW_rule__MapGetReference__OperationsAssignment_0_in_rule__MapGetReference__Group__0__Impl2489);
            rule__MapGetReference__OperationsAssignment_0();
            _fsp--;


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
    // $ANTLR end rule__MapGetReference__Group__0__Impl


    // $ANTLR start rule__MapGetReference__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1211:1: rule__MapGetReference__Group__1 : rule__MapGetReference__Group__1__Impl rule__MapGetReference__Group__2 ;
    public final void rule__MapGetReference__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1215:1: ( rule__MapGetReference__Group__1__Impl rule__MapGetReference__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1216:2: rule__MapGetReference__Group__1__Impl rule__MapGetReference__Group__2
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__1__Impl_in_rule__MapGetReference__Group__12519);
            rule__MapGetReference__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MapGetReference__Group__2_in_rule__MapGetReference__Group__12522);
            rule__MapGetReference__Group__2();
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
    // $ANTLR end rule__MapGetReference__Group__1


    // $ANTLR start rule__MapGetReference__Group__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1223:1: rule__MapGetReference__Group__1__Impl : ( ( rule__MapGetReference__ElementsAssignment_1 ) ) ;
    public final void rule__MapGetReference__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1227:1: ( ( ( rule__MapGetReference__ElementsAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1228:1: ( ( rule__MapGetReference__ElementsAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1228:1: ( ( rule__MapGetReference__ElementsAssignment_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1229:1: ( rule__MapGetReference__ElementsAssignment_1 )
            {
             before(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1230:1: ( rule__MapGetReference__ElementsAssignment_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1230:2: rule__MapGetReference__ElementsAssignment_1
            {
            pushFollow(FOLLOW_rule__MapGetReference__ElementsAssignment_1_in_rule__MapGetReference__Group__1__Impl2549);
            rule__MapGetReference__ElementsAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MapGetReference__Group__1__Impl


    // $ANTLR start rule__MapGetReference__Group__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1240:1: rule__MapGetReference__Group__2 : rule__MapGetReference__Group__2__Impl ;
    public final void rule__MapGetReference__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1244:1: ( rule__MapGetReference__Group__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1245:2: rule__MapGetReference__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group__2__Impl_in_rule__MapGetReference__Group__22579);
            rule__MapGetReference__Group__2__Impl();
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
    // $ANTLR end rule__MapGetReference__Group__2


    // $ANTLR start rule__MapGetReference__Group__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1251:1: rule__MapGetReference__Group__2__Impl : ( ( rule__MapGetReference__Group_2__0 )* ) ;
    public final void rule__MapGetReference__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1255:1: ( ( ( rule__MapGetReference__Group_2__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1256:1: ( ( rule__MapGetReference__Group_2__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1256:1: ( ( rule__MapGetReference__Group_2__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1257:1: ( rule__MapGetReference__Group_2__0 )*
            {
             before(grammarAccess.getMapGetReferenceAccess().getGroup_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1258:1: ( rule__MapGetReference__Group_2__0 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==14) ) {
                    int LA12_2 = input.LA(2);

                    if ( (LA12_2==RULE_ID) ) {
                        int LA12_3 = input.LA(3);

                        if ( (LA12_3==EOF||LA12_3==14||(LA12_3>=16 && LA12_3<=17)||(LA12_3>=19 && LA12_3<=21)||(LA12_3>=23 && LA12_3<=27)) ) {
                            alt12=1;
                        }


                    }
                    else if ( ((LA12_2>=10 && LA12_2<=11)) ) {
                        alt12=1;
                    }


                }


                switch (alt12) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1258:2: rule__MapGetReference__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__MapGetReference__Group_2__0_in_rule__MapGetReference__Group__2__Impl2606);
            	    rule__MapGetReference__Group_2__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getMapGetReferenceAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MapGetReference__Group__2__Impl


    // $ANTLR start rule__MapGetReference__Group_2__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1274:1: rule__MapGetReference__Group_2__0 : rule__MapGetReference__Group_2__0__Impl rule__MapGetReference__Group_2__1 ;
    public final void rule__MapGetReference__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1278:1: ( rule__MapGetReference__Group_2__0__Impl rule__MapGetReference__Group_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1279:2: rule__MapGetReference__Group_2__0__Impl rule__MapGetReference__Group_2__1
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group_2__0__Impl_in_rule__MapGetReference__Group_2__02643);
            rule__MapGetReference__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MapGetReference__Group_2__1_in_rule__MapGetReference__Group_2__02646);
            rule__MapGetReference__Group_2__1();
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
    // $ANTLR end rule__MapGetReference__Group_2__0


    // $ANTLR start rule__MapGetReference__Group_2__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1286:1: rule__MapGetReference__Group_2__0__Impl : ( '/' ) ;
    public final void rule__MapGetReference__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1290:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1291:1: ( '/' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1291:1: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1292:1: '/'
            {
             before(grammarAccess.getMapGetReferenceAccess().getSolidusKeyword_2_0()); 
            match(input,14,FOLLOW_14_in_rule__MapGetReference__Group_2__0__Impl2674); 
             after(grammarAccess.getMapGetReferenceAccess().getSolidusKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MapGetReference__Group_2__0__Impl


    // $ANTLR start rule__MapGetReference__Group_2__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1305:1: rule__MapGetReference__Group_2__1 : rule__MapGetReference__Group_2__1__Impl ;
    public final void rule__MapGetReference__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1309:1: ( rule__MapGetReference__Group_2__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1310:2: rule__MapGetReference__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__MapGetReference__Group_2__1__Impl_in_rule__MapGetReference__Group_2__12705);
            rule__MapGetReference__Group_2__1__Impl();
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
    // $ANTLR end rule__MapGetReference__Group_2__1


    // $ANTLR start rule__MapGetReference__Group_2__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1316:1: rule__MapGetReference__Group_2__1__Impl : ( ( rule__MapGetReference__ElementsAssignment_2_1 ) ) ;
    public final void rule__MapGetReference__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1320:1: ( ( ( rule__MapGetReference__ElementsAssignment_2_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1321:1: ( ( rule__MapGetReference__ElementsAssignment_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1321:1: ( ( rule__MapGetReference__ElementsAssignment_2_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1322:1: ( rule__MapGetReference__ElementsAssignment_2_1 )
            {
             before(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_2_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1323:1: ( rule__MapGetReference__ElementsAssignment_2_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1323:2: rule__MapGetReference__ElementsAssignment_2_1
            {
            pushFollow(FOLLOW_rule__MapGetReference__ElementsAssignment_2_1_in_rule__MapGetReference__Group_2__1__Impl2732);
            rule__MapGetReference__ElementsAssignment_2_1();
            _fsp--;


            }

             after(grammarAccess.getMapGetReferenceAccess().getElementsAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MapGetReference__Group_2__1__Impl


    // $ANTLR start rule__OrExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1337:1: rule__OrExpression__Group__0 : rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1 ;
    public final void rule__OrExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1341:1: ( rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1342:2: rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__0__Impl_in_rule__OrExpression__Group__02766);
            rule__OrExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group__1_in_rule__OrExpression__Group__02769);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1349:1: rule__OrExpression__Group__0__Impl : ( ( rule__OrExpression__ParametersAssignment_0 ) ) ;
    public final void rule__OrExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1353:1: ( ( ( rule__OrExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1354:1: ( ( rule__OrExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1354:1: ( ( rule__OrExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1355:1: ( rule__OrExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1356:1: ( rule__OrExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1356:2: rule__OrExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__OrExpression__ParametersAssignment_0_in_rule__OrExpression__Group__0__Impl2796);
            rule__OrExpression__ParametersAssignment_0();
            _fsp--;


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
    // $ANTLR end rule__OrExpression__Group__0__Impl


    // $ANTLR start rule__OrExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1366:1: rule__OrExpression__Group__1 : rule__OrExpression__Group__1__Impl ;
    public final void rule__OrExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1370:1: ( rule__OrExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1371:2: rule__OrExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__1__Impl_in_rule__OrExpression__Group__12826);
            rule__OrExpression__Group__1__Impl();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1377:1: rule__OrExpression__Group__1__Impl : ( ( rule__OrExpression__Group_1__0 )* ) ;
    public final void rule__OrExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1381:1: ( ( ( rule__OrExpression__Group_1__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1382:1: ( ( rule__OrExpression__Group_1__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1382:1: ( ( rule__OrExpression__Group_1__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1383:1: ( rule__OrExpression__Group_1__0 )*
            {
             before(grammarAccess.getOrExpressionAccess().getGroup_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1384:1: ( rule__OrExpression__Group_1__0 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==23) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1384:2: rule__OrExpression__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__OrExpression__Group_1__0_in_rule__OrExpression__Group__1__Impl2853);
            	    rule__OrExpression__Group_1__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
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
    // $ANTLR end rule__OrExpression__Group__1__Impl


    // $ANTLR start rule__OrExpression__Group_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1398:1: rule__OrExpression__Group_1__0 : rule__OrExpression__Group_1__0__Impl rule__OrExpression__Group_1__1 ;
    public final void rule__OrExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1402:1: ( rule__OrExpression__Group_1__0__Impl rule__OrExpression__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1403:2: rule__OrExpression__Group_1__0__Impl rule__OrExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__OrExpression__Group_1__0__Impl_in_rule__OrExpression__Group_1__02888);
            rule__OrExpression__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group_1__1_in_rule__OrExpression__Group_1__02891);
            rule__OrExpression__Group_1__1();
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
    // $ANTLR end rule__OrExpression__Group_1__0


    // $ANTLR start rule__OrExpression__Group_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1410:1: rule__OrExpression__Group_1__0__Impl : ( ( rule__OrExpression__OperationsAssignment_1_0 ) ) ;
    public final void rule__OrExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1414:1: ( ( ( rule__OrExpression__OperationsAssignment_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1415:1: ( ( rule__OrExpression__OperationsAssignment_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1415:1: ( ( rule__OrExpression__OperationsAssignment_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1416:1: ( rule__OrExpression__OperationsAssignment_1_0 )
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsAssignment_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1417:1: ( rule__OrExpression__OperationsAssignment_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1417:2: rule__OrExpression__OperationsAssignment_1_0
            {
            pushFollow(FOLLOW_rule__OrExpression__OperationsAssignment_1_0_in_rule__OrExpression__Group_1__0__Impl2918);
            rule__OrExpression__OperationsAssignment_1_0();
            _fsp--;


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
    // $ANTLR end rule__OrExpression__Group_1__0__Impl


    // $ANTLR start rule__OrExpression__Group_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1427:1: rule__OrExpression__Group_1__1 : rule__OrExpression__Group_1__1__Impl ;
    public final void rule__OrExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1431:1: ( rule__OrExpression__Group_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1432:2: rule__OrExpression__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__OrExpression__Group_1__1__Impl_in_rule__OrExpression__Group_1__12948);
            rule__OrExpression__Group_1__1__Impl();
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
    // $ANTLR end rule__OrExpression__Group_1__1


    // $ANTLR start rule__OrExpression__Group_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1438:1: rule__OrExpression__Group_1__1__Impl : ( ( rule__OrExpression__ParametersAssignment_1_1 ) ) ;
    public final void rule__OrExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1442:1: ( ( ( rule__OrExpression__ParametersAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1443:1: ( ( rule__OrExpression__ParametersAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1443:1: ( ( rule__OrExpression__ParametersAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1444:1: ( rule__OrExpression__ParametersAssignment_1_1 )
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1445:1: ( rule__OrExpression__ParametersAssignment_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1445:2: rule__OrExpression__ParametersAssignment_1_1
            {
            pushFollow(FOLLOW_rule__OrExpression__ParametersAssignment_1_1_in_rule__OrExpression__Group_1__1__Impl2975);
            rule__OrExpression__ParametersAssignment_1_1();
            _fsp--;


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
    // $ANTLR end rule__OrExpression__Group_1__1__Impl


    // $ANTLR start rule__AndExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1459:1: rule__AndExpression__Group__0 : rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1 ;
    public final void rule__AndExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1463:1: ( rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1464:2: rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__0__Impl_in_rule__AndExpression__Group__03009);
            rule__AndExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group__1_in_rule__AndExpression__Group__03012);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1471:1: rule__AndExpression__Group__0__Impl : ( ( rule__AndExpression__ParametersAssignment_0 ) ) ;
    public final void rule__AndExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1475:1: ( ( ( rule__AndExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1476:1: ( ( rule__AndExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1476:1: ( ( rule__AndExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1477:1: ( rule__AndExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getAndExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1478:1: ( rule__AndExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1478:2: rule__AndExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__AndExpression__ParametersAssignment_0_in_rule__AndExpression__Group__0__Impl3039);
            rule__AndExpression__ParametersAssignment_0();
            _fsp--;


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
    // $ANTLR end rule__AndExpression__Group__0__Impl


    // $ANTLR start rule__AndExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1488:1: rule__AndExpression__Group__1 : rule__AndExpression__Group__1__Impl ;
    public final void rule__AndExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1492:1: ( rule__AndExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1493:2: rule__AndExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__1__Impl_in_rule__AndExpression__Group__13069);
            rule__AndExpression__Group__1__Impl();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1499:1: rule__AndExpression__Group__1__Impl : ( ( rule__AndExpression__Group_1__0 )* ) ;
    public final void rule__AndExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1503:1: ( ( ( rule__AndExpression__Group_1__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1504:1: ( ( rule__AndExpression__Group_1__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1504:1: ( ( rule__AndExpression__Group_1__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1505:1: ( rule__AndExpression__Group_1__0 )*
            {
             before(grammarAccess.getAndExpressionAccess().getGroup_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1506:1: ( rule__AndExpression__Group_1__0 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==24) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1506:2: rule__AndExpression__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__AndExpression__Group_1__0_in_rule__AndExpression__Group__1__Impl3096);
            	    rule__AndExpression__Group_1__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
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
    // $ANTLR end rule__AndExpression__Group__1__Impl


    // $ANTLR start rule__AndExpression__Group_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1520:1: rule__AndExpression__Group_1__0 : rule__AndExpression__Group_1__0__Impl rule__AndExpression__Group_1__1 ;
    public final void rule__AndExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1524:1: ( rule__AndExpression__Group_1__0__Impl rule__AndExpression__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1525:2: rule__AndExpression__Group_1__0__Impl rule__AndExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__AndExpression__Group_1__0__Impl_in_rule__AndExpression__Group_1__03131);
            rule__AndExpression__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group_1__1_in_rule__AndExpression__Group_1__03134);
            rule__AndExpression__Group_1__1();
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
    // $ANTLR end rule__AndExpression__Group_1__0


    // $ANTLR start rule__AndExpression__Group_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1532:1: rule__AndExpression__Group_1__0__Impl : ( ( rule__AndExpression__OperationsAssignment_1_0 ) ) ;
    public final void rule__AndExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1536:1: ( ( ( rule__AndExpression__OperationsAssignment_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1537:1: ( ( rule__AndExpression__OperationsAssignment_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1537:1: ( ( rule__AndExpression__OperationsAssignment_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1538:1: ( rule__AndExpression__OperationsAssignment_1_0 )
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsAssignment_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1539:1: ( rule__AndExpression__OperationsAssignment_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1539:2: rule__AndExpression__OperationsAssignment_1_0
            {
            pushFollow(FOLLOW_rule__AndExpression__OperationsAssignment_1_0_in_rule__AndExpression__Group_1__0__Impl3161);
            rule__AndExpression__OperationsAssignment_1_0();
            _fsp--;


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
    // $ANTLR end rule__AndExpression__Group_1__0__Impl


    // $ANTLR start rule__AndExpression__Group_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1549:1: rule__AndExpression__Group_1__1 : rule__AndExpression__Group_1__1__Impl ;
    public final void rule__AndExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1553:1: ( rule__AndExpression__Group_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1554:2: rule__AndExpression__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__AndExpression__Group_1__1__Impl_in_rule__AndExpression__Group_1__13191);
            rule__AndExpression__Group_1__1__Impl();
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
    // $ANTLR end rule__AndExpression__Group_1__1


    // $ANTLR start rule__AndExpression__Group_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1560:1: rule__AndExpression__Group_1__1__Impl : ( ( rule__AndExpression__ParametersAssignment_1_1 ) ) ;
    public final void rule__AndExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1564:1: ( ( ( rule__AndExpression__ParametersAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1565:1: ( ( rule__AndExpression__ParametersAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1565:1: ( ( rule__AndExpression__ParametersAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1566:1: ( rule__AndExpression__ParametersAssignment_1_1 )
            {
             before(grammarAccess.getAndExpressionAccess().getParametersAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1567:1: ( rule__AndExpression__ParametersAssignment_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1567:2: rule__AndExpression__ParametersAssignment_1_1
            {
            pushFollow(FOLLOW_rule__AndExpression__ParametersAssignment_1_1_in_rule__AndExpression__Group_1__1__Impl3218);
            rule__AndExpression__ParametersAssignment_1_1();
            _fsp--;


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
    // $ANTLR end rule__AndExpression__Group_1__1__Impl


    // $ANTLR start rule__EqualityExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1581:1: rule__EqualityExpression__Group__0 : rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1 ;
    public final void rule__EqualityExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1585:1: ( rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1586:2: rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__0__Impl_in_rule__EqualityExpression__Group__03252);
            rule__EqualityExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group__1_in_rule__EqualityExpression__Group__03255);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1593:1: rule__EqualityExpression__Group__0__Impl : ( ( rule__EqualityExpression__ParametersAssignment_0 ) ) ;
    public final void rule__EqualityExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1597:1: ( ( ( rule__EqualityExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1598:1: ( ( rule__EqualityExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1598:1: ( ( rule__EqualityExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1599:1: ( rule__EqualityExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1600:1: ( rule__EqualityExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1600:2: rule__EqualityExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_0_in_rule__EqualityExpression__Group__0__Impl3282);
            rule__EqualityExpression__ParametersAssignment_0();
            _fsp--;


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
    // $ANTLR end rule__EqualityExpression__Group__0__Impl


    // $ANTLR start rule__EqualityExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1610:1: rule__EqualityExpression__Group__1 : rule__EqualityExpression__Group__1__Impl ;
    public final void rule__EqualityExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1614:1: ( rule__EqualityExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1615:2: rule__EqualityExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__1__Impl_in_rule__EqualityExpression__Group__13312);
            rule__EqualityExpression__Group__1__Impl();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1621:1: rule__EqualityExpression__Group__1__Impl : ( ( rule__EqualityExpression__Alternatives_1 )? ) ;
    public final void rule__EqualityExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1625:1: ( ( ( rule__EqualityExpression__Alternatives_1 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1626:1: ( ( rule__EqualityExpression__Alternatives_1 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1626:1: ( ( rule__EqualityExpression__Alternatives_1 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1627:1: ( rule__EqualityExpression__Alternatives_1 )?
            {
             before(grammarAccess.getEqualityExpressionAccess().getAlternatives_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1628:1: ( rule__EqualityExpression__Alternatives_1 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( ((LA15_0>=25 && LA15_0<=26)) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1628:2: rule__EqualityExpression__Alternatives_1
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Alternatives_1_in_rule__EqualityExpression__Group__1__Impl3339);
                    rule__EqualityExpression__Alternatives_1();
                    _fsp--;


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
    // $ANTLR end rule__EqualityExpression__Group__1__Impl


    // $ANTLR start rule__EqualityExpression__Group_1_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1642:1: rule__EqualityExpression__Group_1_0__0 : rule__EqualityExpression__Group_1_0__0__Impl rule__EqualityExpression__Group_1_0__1 ;
    public final void rule__EqualityExpression__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1646:1: ( rule__EqualityExpression__Group_1_0__0__Impl rule__EqualityExpression__Group_1_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1647:2: rule__EqualityExpression__Group_1_0__0__Impl rule__EqualityExpression__Group_1_0__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_0__0__Impl_in_rule__EqualityExpression__Group_1_0__03374);
            rule__EqualityExpression__Group_1_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_0__1_in_rule__EqualityExpression__Group_1_0__03377);
            rule__EqualityExpression__Group_1_0__1();
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
    // $ANTLR end rule__EqualityExpression__Group_1_0__0


    // $ANTLR start rule__EqualityExpression__Group_1_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1654:1: rule__EqualityExpression__Group_1_0__0__Impl : ( ( rule__EqualityExpression__OperationsAssignment_1_0_0 ) ) ;
    public final void rule__EqualityExpression__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1658:1: ( ( ( rule__EqualityExpression__OperationsAssignment_1_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1659:1: ( ( rule__EqualityExpression__OperationsAssignment_1_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1659:1: ( ( rule__EqualityExpression__OperationsAssignment_1_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1660:1: ( rule__EqualityExpression__OperationsAssignment_1_0_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1661:1: ( rule__EqualityExpression__OperationsAssignment_1_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1661:2: rule__EqualityExpression__OperationsAssignment_1_0_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__OperationsAssignment_1_0_0_in_rule__EqualityExpression__Group_1_0__0__Impl3404);
            rule__EqualityExpression__OperationsAssignment_1_0_0();
            _fsp--;


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
    // $ANTLR end rule__EqualityExpression__Group_1_0__0__Impl


    // $ANTLR start rule__EqualityExpression__Group_1_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1671:1: rule__EqualityExpression__Group_1_0__1 : rule__EqualityExpression__Group_1_0__1__Impl ;
    public final void rule__EqualityExpression__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1675:1: ( rule__EqualityExpression__Group_1_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1676:2: rule__EqualityExpression__Group_1_0__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_0__1__Impl_in_rule__EqualityExpression__Group_1_0__13434);
            rule__EqualityExpression__Group_1_0__1__Impl();
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
    // $ANTLR end rule__EqualityExpression__Group_1_0__1


    // $ANTLR start rule__EqualityExpression__Group_1_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1682:1: rule__EqualityExpression__Group_1_0__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_1_0_1 ) ) ;
    public final void rule__EqualityExpression__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1686:1: ( ( ( rule__EqualityExpression__ParametersAssignment_1_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1687:1: ( ( rule__EqualityExpression__ParametersAssignment_1_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1687:1: ( ( rule__EqualityExpression__ParametersAssignment_1_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1688:1: ( rule__EqualityExpression__ParametersAssignment_1_0_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1689:1: ( rule__EqualityExpression__ParametersAssignment_1_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1689:2: rule__EqualityExpression__ParametersAssignment_1_0_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_1_0_1_in_rule__EqualityExpression__Group_1_0__1__Impl3461);
            rule__EqualityExpression__ParametersAssignment_1_0_1();
            _fsp--;


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
    // $ANTLR end rule__EqualityExpression__Group_1_0__1__Impl


    // $ANTLR start rule__EqualityExpression__Group_1_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1703:1: rule__EqualityExpression__Group_1_1__0 : rule__EqualityExpression__Group_1_1__0__Impl rule__EqualityExpression__Group_1_1__1 ;
    public final void rule__EqualityExpression__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1707:1: ( rule__EqualityExpression__Group_1_1__0__Impl rule__EqualityExpression__Group_1_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1708:2: rule__EqualityExpression__Group_1_1__0__Impl rule__EqualityExpression__Group_1_1__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_1__0__Impl_in_rule__EqualityExpression__Group_1_1__03495);
            rule__EqualityExpression__Group_1_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_1__1_in_rule__EqualityExpression__Group_1_1__03498);
            rule__EqualityExpression__Group_1_1__1();
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
    // $ANTLR end rule__EqualityExpression__Group_1_1__0


    // $ANTLR start rule__EqualityExpression__Group_1_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1715:1: rule__EqualityExpression__Group_1_1__0__Impl : ( ( rule__EqualityExpression__OperationsAssignment_1_1_0 ) ) ;
    public final void rule__EqualityExpression__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1719:1: ( ( ( rule__EqualityExpression__OperationsAssignment_1_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1720:1: ( ( rule__EqualityExpression__OperationsAssignment_1_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1720:1: ( ( rule__EqualityExpression__OperationsAssignment_1_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1721:1: ( rule__EqualityExpression__OperationsAssignment_1_1_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_1_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1722:1: ( rule__EqualityExpression__OperationsAssignment_1_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1722:2: rule__EqualityExpression__OperationsAssignment_1_1_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__OperationsAssignment_1_1_0_in_rule__EqualityExpression__Group_1_1__0__Impl3525);
            rule__EqualityExpression__OperationsAssignment_1_1_0();
            _fsp--;


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
    // $ANTLR end rule__EqualityExpression__Group_1_1__0__Impl


    // $ANTLR start rule__EqualityExpression__Group_1_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1732:1: rule__EqualityExpression__Group_1_1__1 : rule__EqualityExpression__Group_1_1__1__Impl ;
    public final void rule__EqualityExpression__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1736:1: ( rule__EqualityExpression__Group_1_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1737:2: rule__EqualityExpression__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_1_1__1__Impl_in_rule__EqualityExpression__Group_1_1__13555);
            rule__EqualityExpression__Group_1_1__1__Impl();
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
    // $ANTLR end rule__EqualityExpression__Group_1_1__1


    // $ANTLR start rule__EqualityExpression__Group_1_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1743:1: rule__EqualityExpression__Group_1_1__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_1_1_1 ) ) ;
    public final void rule__EqualityExpression__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1747:1: ( ( ( rule__EqualityExpression__ParametersAssignment_1_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1748:1: ( ( rule__EqualityExpression__ParametersAssignment_1_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1748:1: ( ( rule__EqualityExpression__ParametersAssignment_1_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1749:1: ( rule__EqualityExpression__ParametersAssignment_1_1_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1750:1: ( rule__EqualityExpression__ParametersAssignment_1_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1750:2: rule__EqualityExpression__ParametersAssignment_1_1_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_1_1_1_in_rule__EqualityExpression__Group_1_1__1__Impl3582);
            rule__EqualityExpression__ParametersAssignment_1_1_1();
            _fsp--;


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
    // $ANTLR end rule__EqualityExpression__Group_1_1__1__Impl


    // $ANTLR start rule__AdditiveExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1764:1: rule__AdditiveExpression__Group__0 : rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1 ;
    public final void rule__AdditiveExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1768:1: ( rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1769:2: rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__0__Impl_in_rule__AdditiveExpression__Group__03616);
            rule__AdditiveExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group__1_in_rule__AdditiveExpression__Group__03619);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1776:1: rule__AdditiveExpression__Group__0__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_0 ) ) ;
    public final void rule__AdditiveExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1780:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1781:1: ( ( rule__AdditiveExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1781:1: ( ( rule__AdditiveExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1782:1: ( rule__AdditiveExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1783:1: ( rule__AdditiveExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1783:2: rule__AdditiveExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_0_in_rule__AdditiveExpression__Group__0__Impl3646);
            rule__AdditiveExpression__ParametersAssignment_0();
            _fsp--;


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
    // $ANTLR end rule__AdditiveExpression__Group__0__Impl


    // $ANTLR start rule__AdditiveExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1793:1: rule__AdditiveExpression__Group__1 : rule__AdditiveExpression__Group__1__Impl ;
    public final void rule__AdditiveExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1797:1: ( rule__AdditiveExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1798:2: rule__AdditiveExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__1__Impl_in_rule__AdditiveExpression__Group__13676);
            rule__AdditiveExpression__Group__1__Impl();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1804:1: rule__AdditiveExpression__Group__1__Impl : ( ( rule__AdditiveExpression__Alternatives_1 )* ) ;
    public final void rule__AdditiveExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1808:1: ( ( ( rule__AdditiveExpression__Alternatives_1 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1809:1: ( ( rule__AdditiveExpression__Alternatives_1 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1809:1: ( ( rule__AdditiveExpression__Alternatives_1 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1810:1: ( rule__AdditiveExpression__Alternatives_1 )*
            {
             before(grammarAccess.getAdditiveExpressionAccess().getAlternatives_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1811:1: ( rule__AdditiveExpression__Alternatives_1 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=16 && LA16_0<=17)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1811:2: rule__AdditiveExpression__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_rule__AdditiveExpression__Alternatives_1_in_rule__AdditiveExpression__Group__1__Impl3703);
            	    rule__AdditiveExpression__Alternatives_1();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
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
    // $ANTLR end rule__AdditiveExpression__Group__1__Impl


    // $ANTLR start rule__AdditiveExpression__Group_1_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1825:1: rule__AdditiveExpression__Group_1_0__0 : rule__AdditiveExpression__Group_1_0__0__Impl rule__AdditiveExpression__Group_1_0__1 ;
    public final void rule__AdditiveExpression__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1829:1: ( rule__AdditiveExpression__Group_1_0__0__Impl rule__AdditiveExpression__Group_1_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1830:2: rule__AdditiveExpression__Group_1_0__0__Impl rule__AdditiveExpression__Group_1_0__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_0__0__Impl_in_rule__AdditiveExpression__Group_1_0__03738);
            rule__AdditiveExpression__Group_1_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_0__1_in_rule__AdditiveExpression__Group_1_0__03741);
            rule__AdditiveExpression__Group_1_0__1();
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
    // $ANTLR end rule__AdditiveExpression__Group_1_0__0


    // $ANTLR start rule__AdditiveExpression__Group_1_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1837:1: rule__AdditiveExpression__Group_1_0__0__Impl : ( '+' ) ;
    public final void rule__AdditiveExpression__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1841:1: ( ( '+' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1842:1: ( '+' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1842:1: ( '+' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1843:1: '+'
            {
             before(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_1_0_0()); 
            match(input,16,FOLLOW_16_in_rule__AdditiveExpression__Group_1_0__0__Impl3769); 
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
    // $ANTLR end rule__AdditiveExpression__Group_1_0__0__Impl


    // $ANTLR start rule__AdditiveExpression__Group_1_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1856:1: rule__AdditiveExpression__Group_1_0__1 : rule__AdditiveExpression__Group_1_0__1__Impl ;
    public final void rule__AdditiveExpression__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1860:1: ( rule__AdditiveExpression__Group_1_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1861:2: rule__AdditiveExpression__Group_1_0__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_0__1__Impl_in_rule__AdditiveExpression__Group_1_0__13800);
            rule__AdditiveExpression__Group_1_0__1__Impl();
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
    // $ANTLR end rule__AdditiveExpression__Group_1_0__1


    // $ANTLR start rule__AdditiveExpression__Group_1_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1867:1: rule__AdditiveExpression__Group_1_0__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_1_0_1 ) ) ;
    public final void rule__AdditiveExpression__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1871:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_1_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1872:1: ( ( rule__AdditiveExpression__ParametersAssignment_1_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1872:1: ( ( rule__AdditiveExpression__ParametersAssignment_1_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1873:1: ( rule__AdditiveExpression__ParametersAssignment_1_0_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1874:1: ( rule__AdditiveExpression__ParametersAssignment_1_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1874:2: rule__AdditiveExpression__ParametersAssignment_1_0_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_0_1_in_rule__AdditiveExpression__Group_1_0__1__Impl3827);
            rule__AdditiveExpression__ParametersAssignment_1_0_1();
            _fsp--;


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
    // $ANTLR end rule__AdditiveExpression__Group_1_0__1__Impl


    // $ANTLR start rule__AdditiveExpression__Group_1_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1888:1: rule__AdditiveExpression__Group_1_1__0 : rule__AdditiveExpression__Group_1_1__0__Impl rule__AdditiveExpression__Group_1_1__1 ;
    public final void rule__AdditiveExpression__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1892:1: ( rule__AdditiveExpression__Group_1_1__0__Impl rule__AdditiveExpression__Group_1_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1893:2: rule__AdditiveExpression__Group_1_1__0__Impl rule__AdditiveExpression__Group_1_1__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_1__0__Impl_in_rule__AdditiveExpression__Group_1_1__03861);
            rule__AdditiveExpression__Group_1_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_1__1_in_rule__AdditiveExpression__Group_1_1__03864);
            rule__AdditiveExpression__Group_1_1__1();
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
    // $ANTLR end rule__AdditiveExpression__Group_1_1__0


    // $ANTLR start rule__AdditiveExpression__Group_1_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1900:1: rule__AdditiveExpression__Group_1_1__0__Impl : ( '-' ) ;
    public final void rule__AdditiveExpression__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1904:1: ( ( '-' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1905:1: ( '-' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1905:1: ( '-' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1906:1: '-'
            {
             before(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_1_1_0()); 
            match(input,17,FOLLOW_17_in_rule__AdditiveExpression__Group_1_1__0__Impl3892); 
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
    // $ANTLR end rule__AdditiveExpression__Group_1_1__0__Impl


    // $ANTLR start rule__AdditiveExpression__Group_1_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1919:1: rule__AdditiveExpression__Group_1_1__1 : rule__AdditiveExpression__Group_1_1__1__Impl ;
    public final void rule__AdditiveExpression__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1923:1: ( rule__AdditiveExpression__Group_1_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1924:2: rule__AdditiveExpression__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_1_1__1__Impl_in_rule__AdditiveExpression__Group_1_1__13923);
            rule__AdditiveExpression__Group_1_1__1__Impl();
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
    // $ANTLR end rule__AdditiveExpression__Group_1_1__1


    // $ANTLR start rule__AdditiveExpression__Group_1_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1930:1: rule__AdditiveExpression__Group_1_1__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_1_1_1 ) ) ;
    public final void rule__AdditiveExpression__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1934:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_1_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1935:1: ( ( rule__AdditiveExpression__ParametersAssignment_1_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1935:1: ( ( rule__AdditiveExpression__ParametersAssignment_1_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1936:1: ( rule__AdditiveExpression__ParametersAssignment_1_1_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1937:1: ( rule__AdditiveExpression__ParametersAssignment_1_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1937:2: rule__AdditiveExpression__ParametersAssignment_1_1_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_1_1_in_rule__AdditiveExpression__Group_1_1__1__Impl3950);
            rule__AdditiveExpression__ParametersAssignment_1_1_1();
            _fsp--;


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
    // $ANTLR end rule__AdditiveExpression__Group_1_1__1__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1951:1: rule__MultiplicativeExpression__Group__0 : rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1 ;
    public final void rule__MultiplicativeExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1955:1: ( rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1956:2: rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__0__Impl_in_rule__MultiplicativeExpression__Group__03984);
            rule__MultiplicativeExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__1_in_rule__MultiplicativeExpression__Group__03987);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1963:1: rule__MultiplicativeExpression__Group__0__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1967:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1968:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1968:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1969:1: ( rule__MultiplicativeExpression__ParametersAssignment_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1970:1: ( rule__MultiplicativeExpression__ParametersAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1970:2: rule__MultiplicativeExpression__ParametersAssignment_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_0_in_rule__MultiplicativeExpression__Group__0__Impl4014);
            rule__MultiplicativeExpression__ParametersAssignment_0();
            _fsp--;


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
    // $ANTLR end rule__MultiplicativeExpression__Group__0__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1980:1: rule__MultiplicativeExpression__Group__1 : rule__MultiplicativeExpression__Group__1__Impl ;
    public final void rule__MultiplicativeExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1984:1: ( rule__MultiplicativeExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1985:2: rule__MultiplicativeExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__1__Impl_in_rule__MultiplicativeExpression__Group__14044);
            rule__MultiplicativeExpression__Group__1__Impl();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1991:1: rule__MultiplicativeExpression__Group__1__Impl : ( ( rule__MultiplicativeExpression__Alternatives_1 )* ) ;
    public final void rule__MultiplicativeExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1995:1: ( ( ( rule__MultiplicativeExpression__Alternatives_1 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1996:1: ( ( rule__MultiplicativeExpression__Alternatives_1 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1996:1: ( ( rule__MultiplicativeExpression__Alternatives_1 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1997:1: ( rule__MultiplicativeExpression__Alternatives_1 )*
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getAlternatives_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1998:1: ( rule__MultiplicativeExpression__Alternatives_1 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==14||LA17_0==27) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:1998:2: rule__MultiplicativeExpression__Alternatives_1
            	    {
            	    pushFollow(FOLLOW_rule__MultiplicativeExpression__Alternatives_1_in_rule__MultiplicativeExpression__Group__1__Impl4071);
            	    rule__MultiplicativeExpression__Alternatives_1();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
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
    // $ANTLR end rule__MultiplicativeExpression__Group__1__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group_1_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2012:1: rule__MultiplicativeExpression__Group_1_0__0 : rule__MultiplicativeExpression__Group_1_0__0__Impl rule__MultiplicativeExpression__Group_1_0__1 ;
    public final void rule__MultiplicativeExpression__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2016:1: ( rule__MultiplicativeExpression__Group_1_0__0__Impl rule__MultiplicativeExpression__Group_1_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2017:2: rule__MultiplicativeExpression__Group_1_0__0__Impl rule__MultiplicativeExpression__Group_1_0__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_0__0__Impl_in_rule__MultiplicativeExpression__Group_1_0__04106);
            rule__MultiplicativeExpression__Group_1_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_0__1_in_rule__MultiplicativeExpression__Group_1_0__04109);
            rule__MultiplicativeExpression__Group_1_0__1();
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
    // $ANTLR end rule__MultiplicativeExpression__Group_1_0__0


    // $ANTLR start rule__MultiplicativeExpression__Group_1_0__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2024:1: rule__MultiplicativeExpression__Group_1_0__0__Impl : ( ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2028:1: ( ( ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2029:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2029:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2030:1: ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2031:1: ( rule__MultiplicativeExpression__OperationsAssignment_1_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2031:2: rule__MultiplicativeExpression__OperationsAssignment_1_0_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_1_0_0_in_rule__MultiplicativeExpression__Group_1_0__0__Impl4136);
            rule__MultiplicativeExpression__OperationsAssignment_1_0_0();
            _fsp--;


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
    // $ANTLR end rule__MultiplicativeExpression__Group_1_0__0__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group_1_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2041:1: rule__MultiplicativeExpression__Group_1_0__1 : rule__MultiplicativeExpression__Group_1_0__1__Impl ;
    public final void rule__MultiplicativeExpression__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2045:1: ( rule__MultiplicativeExpression__Group_1_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2046:2: rule__MultiplicativeExpression__Group_1_0__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_0__1__Impl_in_rule__MultiplicativeExpression__Group_1_0__14166);
            rule__MultiplicativeExpression__Group_1_0__1__Impl();
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
    // $ANTLR end rule__MultiplicativeExpression__Group_1_0__1


    // $ANTLR start rule__MultiplicativeExpression__Group_1_0__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2052:1: rule__MultiplicativeExpression__Group_1_0__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2056:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2057:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2057:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2058:1: ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2059:1: ( rule__MultiplicativeExpression__ParametersAssignment_1_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2059:2: rule__MultiplicativeExpression__ParametersAssignment_1_0_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_0_1_in_rule__MultiplicativeExpression__Group_1_0__1__Impl4193);
            rule__MultiplicativeExpression__ParametersAssignment_1_0_1();
            _fsp--;


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
    // $ANTLR end rule__MultiplicativeExpression__Group_1_0__1__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group_1_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2073:1: rule__MultiplicativeExpression__Group_1_1__0 : rule__MultiplicativeExpression__Group_1_1__0__Impl rule__MultiplicativeExpression__Group_1_1__1 ;
    public final void rule__MultiplicativeExpression__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2077:1: ( rule__MultiplicativeExpression__Group_1_1__0__Impl rule__MultiplicativeExpression__Group_1_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2078:2: rule__MultiplicativeExpression__Group_1_1__0__Impl rule__MultiplicativeExpression__Group_1_1__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_1__0__Impl_in_rule__MultiplicativeExpression__Group_1_1__04227);
            rule__MultiplicativeExpression__Group_1_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_1__1_in_rule__MultiplicativeExpression__Group_1_1__04230);
            rule__MultiplicativeExpression__Group_1_1__1();
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
    // $ANTLR end rule__MultiplicativeExpression__Group_1_1__0


    // $ANTLR start rule__MultiplicativeExpression__Group_1_1__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2085:1: rule__MultiplicativeExpression__Group_1_1__0__Impl : ( ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2089:1: ( ( ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2090:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2090:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2091:1: ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_1_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2092:1: ( rule__MultiplicativeExpression__OperationsAssignment_1_1_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2092:2: rule__MultiplicativeExpression__OperationsAssignment_1_1_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_1_1_0_in_rule__MultiplicativeExpression__Group_1_1__0__Impl4257);
            rule__MultiplicativeExpression__OperationsAssignment_1_1_0();
            _fsp--;


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
    // $ANTLR end rule__MultiplicativeExpression__Group_1_1__0__Impl


    // $ANTLR start rule__MultiplicativeExpression__Group_1_1__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2102:1: rule__MultiplicativeExpression__Group_1_1__1 : rule__MultiplicativeExpression__Group_1_1__1__Impl ;
    public final void rule__MultiplicativeExpression__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2106:1: ( rule__MultiplicativeExpression__Group_1_1__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2107:2: rule__MultiplicativeExpression__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_1_1__1__Impl_in_rule__MultiplicativeExpression__Group_1_1__14287);
            rule__MultiplicativeExpression__Group_1_1__1__Impl();
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
    // $ANTLR end rule__MultiplicativeExpression__Group_1_1__1


    // $ANTLR start rule__MultiplicativeExpression__Group_1_1__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2113:1: rule__MultiplicativeExpression__Group_1_1__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2117:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2118:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2118:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2119:1: ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2120:1: ( rule__MultiplicativeExpression__ParametersAssignment_1_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2120:2: rule__MultiplicativeExpression__ParametersAssignment_1_1_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_1_1_in_rule__MultiplicativeExpression__Group_1_1__1__Impl4314);
            rule__MultiplicativeExpression__ParametersAssignment_1_1_1();
            _fsp--;


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
    // $ANTLR end rule__MultiplicativeExpression__Group_1_1__1__Impl


    // $ANTLR start rule__UnaryExpression__Group_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2134:1: rule__UnaryExpression__Group_0__0 : rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1 ;
    public final void rule__UnaryExpression__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2138:1: ( rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2139:2: rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__0__Impl_in_rule__UnaryExpression__Group_0__04348);
            rule__UnaryExpression__Group_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__1_in_rule__UnaryExpression__Group_0__04351);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2146:1: rule__UnaryExpression__Group_0__0__Impl : ( ( rule__UnaryExpression__OperationsAssignment_0_0 ) ) ;
    public final void rule__UnaryExpression__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2150:1: ( ( ( rule__UnaryExpression__OperationsAssignment_0_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2151:1: ( ( rule__UnaryExpression__OperationsAssignment_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2151:1: ( ( rule__UnaryExpression__OperationsAssignment_0_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2152:1: ( rule__UnaryExpression__OperationsAssignment_0_0 )
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsAssignment_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2153:1: ( rule__UnaryExpression__OperationsAssignment_0_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2153:2: rule__UnaryExpression__OperationsAssignment_0_0
            {
            pushFollow(FOLLOW_rule__UnaryExpression__OperationsAssignment_0_0_in_rule__UnaryExpression__Group_0__0__Impl4378);
            rule__UnaryExpression__OperationsAssignment_0_0();
            _fsp--;


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
    // $ANTLR end rule__UnaryExpression__Group_0__0__Impl


    // $ANTLR start rule__UnaryExpression__Group_0__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2163:1: rule__UnaryExpression__Group_0__1 : rule__UnaryExpression__Group_0__1__Impl ;
    public final void rule__UnaryExpression__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2167:1: ( rule__UnaryExpression__Group_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2168:2: rule__UnaryExpression__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__1__Impl_in_rule__UnaryExpression__Group_0__14408);
            rule__UnaryExpression__Group_0__1__Impl();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2174:1: rule__UnaryExpression__Group_0__1__Impl : ( ( rule__UnaryExpression__ParametersAssignment_0_1 ) ) ;
    public final void rule__UnaryExpression__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2178:1: ( ( ( rule__UnaryExpression__ParametersAssignment_0_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2179:1: ( ( rule__UnaryExpression__ParametersAssignment_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2179:1: ( ( rule__UnaryExpression__ParametersAssignment_0_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2180:1: ( rule__UnaryExpression__ParametersAssignment_0_1 )
            {
             before(grammarAccess.getUnaryExpressionAccess().getParametersAssignment_0_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2181:1: ( rule__UnaryExpression__ParametersAssignment_0_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2181:2: rule__UnaryExpression__ParametersAssignment_0_1
            {
            pushFollow(FOLLOW_rule__UnaryExpression__ParametersAssignment_0_1_in_rule__UnaryExpression__Group_0__1__Impl4435);
            rule__UnaryExpression__ParametersAssignment_0_1();
            _fsp--;


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
    // $ANTLR end rule__UnaryExpression__Group_0__1__Impl


    // $ANTLR start rule__PrimaryExpression__Group_1__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2195:1: rule__PrimaryExpression__Group_1__0 : rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1 ;
    public final void rule__PrimaryExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2199:1: ( rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2200:2: rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__0__Impl_in_rule__PrimaryExpression__Group_1__04469);
            rule__PrimaryExpression__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__1_in_rule__PrimaryExpression__Group_1__04472);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2207:1: rule__PrimaryExpression__Group_1__0__Impl : ( '(' ) ;
    public final void rule__PrimaryExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2211:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2212:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2212:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2213:1: '('
            {
             before(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0()); 
            match(input,18,FOLLOW_18_in_rule__PrimaryExpression__Group_1__0__Impl4500); 
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2226:1: rule__PrimaryExpression__Group_1__1 : rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2 ;
    public final void rule__PrimaryExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2230:1: ( rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2231:2: rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__1__Impl_in_rule__PrimaryExpression__Group_1__14531);
            rule__PrimaryExpression__Group_1__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__2_in_rule__PrimaryExpression__Group_1__14534);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2238:1: rule__PrimaryExpression__Group_1__1__Impl : ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) ) ;
    public final void rule__PrimaryExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2242:1: ( ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2243:1: ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2243:1: ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2244:1: ( rule__PrimaryExpression__ParametersAssignment_1_1 )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2245:1: ( rule__PrimaryExpression__ParametersAssignment_1_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2245:2: rule__PrimaryExpression__ParametersAssignment_1_1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__ParametersAssignment_1_1_in_rule__PrimaryExpression__Group_1__1__Impl4561);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2255:1: rule__PrimaryExpression__Group_1__2 : rule__PrimaryExpression__Group_1__2__Impl ;
    public final void rule__PrimaryExpression__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2259:1: ( rule__PrimaryExpression__Group_1__2__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2260:2: rule__PrimaryExpression__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__2__Impl_in_rule__PrimaryExpression__Group_1__24591);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2266:1: rule__PrimaryExpression__Group_1__2__Impl : ( ')' ) ;
    public final void rule__PrimaryExpression__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2270:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2271:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2271:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2272:1: ')'
            {
             before(grammarAccess.getPrimaryExpressionAccess().getRightParenthesisKeyword_1_2()); 
            match(input,19,FOLLOW_19_in_rule__PrimaryExpression__Group_1__2__Impl4619); 
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2291:1: rule__FunctionCall__Group__0 : rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1 ;
    public final void rule__FunctionCall__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2295:1: ( rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2296:2: rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__0__Impl_in_rule__FunctionCall__Group__04656);
            rule__FunctionCall__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__1_in_rule__FunctionCall__Group__04659);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2303:1: rule__FunctionCall__Group__0__Impl : ( ( rule__FunctionCall__NameAssignment_0 ) ) ;
    public final void rule__FunctionCall__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2307:1: ( ( ( rule__FunctionCall__NameAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2308:1: ( ( rule__FunctionCall__NameAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2308:1: ( ( rule__FunctionCall__NameAssignment_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2309:1: ( rule__FunctionCall__NameAssignment_0 )
            {
             before(grammarAccess.getFunctionCallAccess().getNameAssignment_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2310:1: ( rule__FunctionCall__NameAssignment_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2310:2: rule__FunctionCall__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__FunctionCall__NameAssignment_0_in_rule__FunctionCall__Group__0__Impl4686);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2320:1: rule__FunctionCall__Group__1 : rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2 ;
    public final void rule__FunctionCall__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2324:1: ( rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2325:2: rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__1__Impl_in_rule__FunctionCall__Group__14716);
            rule__FunctionCall__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__2_in_rule__FunctionCall__Group__14719);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2332:1: rule__FunctionCall__Group__1__Impl : ( '(' ) ;
    public final void rule__FunctionCall__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2336:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2337:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2337:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2338:1: '('
            {
             before(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1()); 
            match(input,18,FOLLOW_18_in_rule__FunctionCall__Group__1__Impl4747); 
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2351:1: rule__FunctionCall__Group__2 : rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3 ;
    public final void rule__FunctionCall__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2355:1: ( rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2356:2: rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__2__Impl_in_rule__FunctionCall__Group__24778);
            rule__FunctionCall__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__3_in_rule__FunctionCall__Group__24781);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2363:1: rule__FunctionCall__Group__2__Impl : ( ( rule__FunctionCall__ParametersAssignment_2 )? ) ;
    public final void rule__FunctionCall__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2367:1: ( ( ( rule__FunctionCall__ParametersAssignment_2 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2368:1: ( ( rule__FunctionCall__ParametersAssignment_2 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2368:1: ( ( rule__FunctionCall__ParametersAssignment_2 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2369:1: ( rule__FunctionCall__ParametersAssignment_2 )?
            {
             before(grammarAccess.getFunctionCallAccess().getParametersAssignment_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2370:1: ( rule__FunctionCall__ParametersAssignment_2 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=RULE_ID && LA18_0<=RULE_LITERALSTRING)||LA18_0==12||LA18_0==15||LA18_0==18||LA18_0==22||(LA18_0>=28 && LA18_0<=34)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2370:2: rule__FunctionCall__ParametersAssignment_2
                    {
                    pushFollow(FOLLOW_rule__FunctionCall__ParametersAssignment_2_in_rule__FunctionCall__Group__2__Impl4808);
                    rule__FunctionCall__ParametersAssignment_2();
                    _fsp--;


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
    // $ANTLR end rule__FunctionCall__Group__2__Impl


    // $ANTLR start rule__FunctionCall__Group__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2380:1: rule__FunctionCall__Group__3 : rule__FunctionCall__Group__3__Impl rule__FunctionCall__Group__4 ;
    public final void rule__FunctionCall__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2384:1: ( rule__FunctionCall__Group__3__Impl rule__FunctionCall__Group__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2385:2: rule__FunctionCall__Group__3__Impl rule__FunctionCall__Group__4
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__3__Impl_in_rule__FunctionCall__Group__34839);
            rule__FunctionCall__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__4_in_rule__FunctionCall__Group__34842);
            rule__FunctionCall__Group__4();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2392:1: rule__FunctionCall__Group__3__Impl : ( ( rule__FunctionCall__Group_3__0 )* ) ;
    public final void rule__FunctionCall__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2396:1: ( ( ( rule__FunctionCall__Group_3__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2397:1: ( ( rule__FunctionCall__Group_3__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2397:1: ( ( rule__FunctionCall__Group_3__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2398:1: ( rule__FunctionCall__Group_3__0 )*
            {
             before(grammarAccess.getFunctionCallAccess().getGroup_3()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2399:1: ( rule__FunctionCall__Group_3__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==20) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2399:2: rule__FunctionCall__Group_3__0
            	    {
            	    pushFollow(FOLLOW_rule__FunctionCall__Group_3__0_in_rule__FunctionCall__Group__3__Impl4869);
            	    rule__FunctionCall__Group_3__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
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
    // $ANTLR end rule__FunctionCall__Group__3__Impl


    // $ANTLR start rule__FunctionCall__Group__4
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2409:1: rule__FunctionCall__Group__4 : rule__FunctionCall__Group__4__Impl ;
    public final void rule__FunctionCall__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2413:1: ( rule__FunctionCall__Group__4__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2414:2: rule__FunctionCall__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__4__Impl_in_rule__FunctionCall__Group__44900);
            rule__FunctionCall__Group__4__Impl();
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
    // $ANTLR end rule__FunctionCall__Group__4


    // $ANTLR start rule__FunctionCall__Group__4__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2420:1: rule__FunctionCall__Group__4__Impl : ( ')' ) ;
    public final void rule__FunctionCall__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2424:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2425:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2425:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2426:1: ')'
            {
             before(grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_4()); 
            match(input,19,FOLLOW_19_in_rule__FunctionCall__Group__4__Impl4928); 
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
    // $ANTLR end rule__FunctionCall__Group__4__Impl


    // $ANTLR start rule__FunctionCall__Group_3__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2449:1: rule__FunctionCall__Group_3__0 : rule__FunctionCall__Group_3__0__Impl rule__FunctionCall__Group_3__1 ;
    public final void rule__FunctionCall__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2453:1: ( rule__FunctionCall__Group_3__0__Impl rule__FunctionCall__Group_3__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2454:2: rule__FunctionCall__Group_3__0__Impl rule__FunctionCall__Group_3__1
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group_3__0__Impl_in_rule__FunctionCall__Group_3__04969);
            rule__FunctionCall__Group_3__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group_3__1_in_rule__FunctionCall__Group_3__04972);
            rule__FunctionCall__Group_3__1();
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
    // $ANTLR end rule__FunctionCall__Group_3__0


    // $ANTLR start rule__FunctionCall__Group_3__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2461:1: rule__FunctionCall__Group_3__0__Impl : ( ',' ) ;
    public final void rule__FunctionCall__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2465:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2466:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2466:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2467:1: ','
            {
             before(grammarAccess.getFunctionCallAccess().getCommaKeyword_3_0()); 
            match(input,20,FOLLOW_20_in_rule__FunctionCall__Group_3__0__Impl5000); 
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
    // $ANTLR end rule__FunctionCall__Group_3__0__Impl


    // $ANTLR start rule__FunctionCall__Group_3__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2480:1: rule__FunctionCall__Group_3__1 : rule__FunctionCall__Group_3__1__Impl ;
    public final void rule__FunctionCall__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2484:1: ( rule__FunctionCall__Group_3__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2485:2: rule__FunctionCall__Group_3__1__Impl
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group_3__1__Impl_in_rule__FunctionCall__Group_3__15031);
            rule__FunctionCall__Group_3__1__Impl();
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
    // $ANTLR end rule__FunctionCall__Group_3__1


    // $ANTLR start rule__FunctionCall__Group_3__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2491:1: rule__FunctionCall__Group_3__1__Impl : ( ( rule__FunctionCall__ParametersAssignment_3_1 ) ) ;
    public final void rule__FunctionCall__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2495:1: ( ( ( rule__FunctionCall__ParametersAssignment_3_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2496:1: ( ( rule__FunctionCall__ParametersAssignment_3_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2496:1: ( ( rule__FunctionCall__ParametersAssignment_3_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2497:1: ( rule__FunctionCall__ParametersAssignment_3_1 )
            {
             before(grammarAccess.getFunctionCallAccess().getParametersAssignment_3_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2498:1: ( rule__FunctionCall__ParametersAssignment_3_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2498:2: rule__FunctionCall__ParametersAssignment_3_1
            {
            pushFollow(FOLLOW_rule__FunctionCall__ParametersAssignment_3_1_in_rule__FunctionCall__Group_3__1__Impl5058);
            rule__FunctionCall__ParametersAssignment_3_1();
            _fsp--;


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
    // $ANTLR end rule__FunctionCall__Group_3__1__Impl


    // $ANTLR start rule__Literal__Group_0__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2512:1: rule__Literal__Group_0__0 : rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1 ;
    public final void rule__Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2516:1: ( rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2517:2: rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_0__0__Impl_in_rule__Literal__Group_0__05092);
            rule__Literal__Group_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_0__1_in_rule__Literal__Group_0__05095);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2524:1: rule__Literal__Group_0__0__Impl : ( () ) ;
    public final void rule__Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2528:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2529:1: ( () )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2529:1: ( () )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2530:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2531:1: ()
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2533:1: 
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2543:1: rule__Literal__Group_0__1 : rule__Literal__Group_0__1__Impl ;
    public final void rule__Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2547:1: ( rule__Literal__Group_0__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2548:2: rule__Literal__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_0__1__Impl_in_rule__Literal__Group_0__15153);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2554:1: rule__Literal__Group_0__1__Impl : ( RULE_INT ) ;
    public final void rule__Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2558:1: ( ( RULE_INT ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2559:1: ( RULE_INT )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2559:1: ( RULE_INT )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2560:1: RULE_INT
            {
             before(grammarAccess.getLiteralAccess().getINTTerminalRuleCall_0_1()); 
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__Literal__Group_0__1__Impl5180); 
             after(grammarAccess.getLiteralAccess().getINTTerminalRuleCall_0_1()); 

            }


            }

        }
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


    // $ANTLR start rule__Literal__Group_2__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2575:1: rule__Literal__Group_2__0 : rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1 ;
    public final void rule__Literal__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2579:1: ( rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2580:2: rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__0__Impl_in_rule__Literal__Group_2__05213);
            rule__Literal__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__1_in_rule__Literal__Group_2__05216);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2587:1: rule__Literal__Group_2__0__Impl : ( ( rule__Literal__OperationsAssignment_2_0 ) ) ;
    public final void rule__Literal__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2591:1: ( ( ( rule__Literal__OperationsAssignment_2_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2592:1: ( ( rule__Literal__OperationsAssignment_2_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2592:1: ( ( rule__Literal__OperationsAssignment_2_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2593:1: ( rule__Literal__OperationsAssignment_2_0 )
            {
             before(grammarAccess.getLiteralAccess().getOperationsAssignment_2_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2594:1: ( rule__Literal__OperationsAssignment_2_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2594:2: rule__Literal__OperationsAssignment_2_0
            {
            pushFollow(FOLLOW_rule__Literal__OperationsAssignment_2_0_in_rule__Literal__Group_2__0__Impl5243);
            rule__Literal__OperationsAssignment_2_0();
            _fsp--;


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
    // $ANTLR end rule__Literal__Group_2__0__Impl


    // $ANTLR start rule__Literal__Group_2__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2604:1: rule__Literal__Group_2__1 : rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2 ;
    public final void rule__Literal__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2608:1: ( rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2609:2: rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__1__Impl_in_rule__Literal__Group_2__15273);
            rule__Literal__Group_2__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__2_in_rule__Literal__Group_2__15276);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2616:1: rule__Literal__Group_2__1__Impl : ( '(' ) ;
    public final void rule__Literal__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2620:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2621:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2621:1: ( '(' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2622:1: '('
            {
             before(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_1()); 
            match(input,18,FOLLOW_18_in_rule__Literal__Group_2__1__Impl5304); 
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
    // $ANTLR end rule__Literal__Group_2__1__Impl


    // $ANTLR start rule__Literal__Group_2__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2635:1: rule__Literal__Group_2__2 : rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3 ;
    public final void rule__Literal__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2639:1: ( rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2640:2: rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__2__Impl_in_rule__Literal__Group_2__25335);
            rule__Literal__Group_2__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__3_in_rule__Literal__Group_2__25338);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2647:1: rule__Literal__Group_2__2__Impl : ( ( rule__Literal__ValueStringAssignment_2_2 ) ) ;
    public final void rule__Literal__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2651:1: ( ( ( rule__Literal__ValueStringAssignment_2_2 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2652:1: ( ( rule__Literal__ValueStringAssignment_2_2 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2652:1: ( ( rule__Literal__ValueStringAssignment_2_2 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2653:1: ( rule__Literal__ValueStringAssignment_2_2 )
            {
             before(grammarAccess.getLiteralAccess().getValueStringAssignment_2_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2654:1: ( rule__Literal__ValueStringAssignment_2_2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2654:2: rule__Literal__ValueStringAssignment_2_2
            {
            pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_2_2_in_rule__Literal__Group_2__2__Impl5365);
            rule__Literal__ValueStringAssignment_2_2();
            _fsp--;


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
    // $ANTLR end rule__Literal__Group_2__2__Impl


    // $ANTLR start rule__Literal__Group_2__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2664:1: rule__Literal__Group_2__3 : rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4 ;
    public final void rule__Literal__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2668:1: ( rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2669:2: rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__3__Impl_in_rule__Literal__Group_2__35395);
            rule__Literal__Group_2__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__4_in_rule__Literal__Group_2__35398);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2676:1: rule__Literal__Group_2__3__Impl : ( ',' ) ;
    public final void rule__Literal__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2680:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2681:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2681:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2682:1: ','
            {
             before(grammarAccess.getLiteralAccess().getCommaKeyword_2_3()); 
            match(input,20,FOLLOW_20_in_rule__Literal__Group_2__3__Impl5426); 
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
    // $ANTLR end rule__Literal__Group_2__3__Impl


    // $ANTLR start rule__Literal__Group_2__4
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2695:1: rule__Literal__Group_2__4 : rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5 ;
    public final void rule__Literal__Group_2__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2699:1: ( rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2700:2: rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__4__Impl_in_rule__Literal__Group_2__45457);
            rule__Literal__Group_2__4__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__5_in_rule__Literal__Group_2__45460);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2707:1: rule__Literal__Group_2__4__Impl : ( ( rule__Literal__ParametersAssignment_2_4 ) ) ;
    public final void rule__Literal__Group_2__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2711:1: ( ( ( rule__Literal__ParametersAssignment_2_4 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2712:1: ( ( rule__Literal__ParametersAssignment_2_4 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2712:1: ( ( rule__Literal__ParametersAssignment_2_4 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2713:1: ( rule__Literal__ParametersAssignment_2_4 )
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_2_4()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2714:1: ( rule__Literal__ParametersAssignment_2_4 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2714:2: rule__Literal__ParametersAssignment_2_4
            {
            pushFollow(FOLLOW_rule__Literal__ParametersAssignment_2_4_in_rule__Literal__Group_2__4__Impl5487);
            rule__Literal__ParametersAssignment_2_4();
            _fsp--;


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
    // $ANTLR end rule__Literal__Group_2__4__Impl


    // $ANTLR start rule__Literal__Group_2__5
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2724:1: rule__Literal__Group_2__5 : rule__Literal__Group_2__5__Impl ;
    public final void rule__Literal__Group_2__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2728:1: ( rule__Literal__Group_2__5__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2729:2: rule__Literal__Group_2__5__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__5__Impl_in_rule__Literal__Group_2__55517);
            rule__Literal__Group_2__5__Impl();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2735:1: rule__Literal__Group_2__5__Impl : ( ')' ) ;
    public final void rule__Literal__Group_2__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2739:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2740:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2740:1: ( ')' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2741:1: ')'
            {
             before(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_5()); 
            match(input,19,FOLLOW_19_in_rule__Literal__Group_2__5__Impl5545); 
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
    // $ANTLR end rule__Literal__Group_2__5__Impl


    // $ANTLR start rule__Literal__Group_4__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2766:1: rule__Literal__Group_4__0 : rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1 ;
    public final void rule__Literal__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2770:1: ( rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2771:2: rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__0__Impl_in_rule__Literal__Group_4__05588);
            rule__Literal__Group_4__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4__1_in_rule__Literal__Group_4__05591);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2778:1: rule__Literal__Group_4__0__Impl : ( ( rule__Literal__ExpressionTypeAssignment_4_0 ) ) ;
    public final void rule__Literal__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2782:1: ( ( ( rule__Literal__ExpressionTypeAssignment_4_0 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2783:1: ( ( rule__Literal__ExpressionTypeAssignment_4_0 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2783:1: ( ( rule__Literal__ExpressionTypeAssignment_4_0 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2784:1: ( rule__Literal__ExpressionTypeAssignment_4_0 )
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeAssignment_4_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2785:1: ( rule__Literal__ExpressionTypeAssignment_4_0 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2785:2: rule__Literal__ExpressionTypeAssignment_4_0
            {
            pushFollow(FOLLOW_rule__Literal__ExpressionTypeAssignment_4_0_in_rule__Literal__Group_4__0__Impl5618);
            rule__Literal__ExpressionTypeAssignment_4_0();
            _fsp--;


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
    // $ANTLR end rule__Literal__Group_4__0__Impl


    // $ANTLR start rule__Literal__Group_4__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2795:1: rule__Literal__Group_4__1 : rule__Literal__Group_4__1__Impl rule__Literal__Group_4__2 ;
    public final void rule__Literal__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2799:1: ( rule__Literal__Group_4__1__Impl rule__Literal__Group_4__2 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2800:2: rule__Literal__Group_4__1__Impl rule__Literal__Group_4__2
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__1__Impl_in_rule__Literal__Group_4__15648);
            rule__Literal__Group_4__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4__2_in_rule__Literal__Group_4__15651);
            rule__Literal__Group_4__2();
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2807:1: rule__Literal__Group_4__1__Impl : ( ( rule__Literal__ParametersAssignment_4_1 )? ) ;
    public final void rule__Literal__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2811:1: ( ( ( rule__Literal__ParametersAssignment_4_1 )? ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2812:1: ( ( rule__Literal__ParametersAssignment_4_1 )? )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2812:1: ( ( rule__Literal__ParametersAssignment_4_1 )? )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2813:1: ( rule__Literal__ParametersAssignment_4_1 )?
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_4_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2814:1: ( rule__Literal__ParametersAssignment_4_1 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>=RULE_ID && LA20_0<=RULE_LITERALSTRING)||LA20_0==12||LA20_0==15||LA20_0==18||LA20_0==22||(LA20_0>=28 && LA20_0<=34)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2814:2: rule__Literal__ParametersAssignment_4_1
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_4_1_in_rule__Literal__Group_4__1__Impl5678);
                    rule__Literal__ParametersAssignment_4_1();
                    _fsp--;


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
    // $ANTLR end rule__Literal__Group_4__1__Impl


    // $ANTLR start rule__Literal__Group_4__2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2824:1: rule__Literal__Group_4__2 : rule__Literal__Group_4__2__Impl rule__Literal__Group_4__3 ;
    public final void rule__Literal__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2828:1: ( rule__Literal__Group_4__2__Impl rule__Literal__Group_4__3 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2829:2: rule__Literal__Group_4__2__Impl rule__Literal__Group_4__3
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__2__Impl_in_rule__Literal__Group_4__25709);
            rule__Literal__Group_4__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4__3_in_rule__Literal__Group_4__25712);
            rule__Literal__Group_4__3();
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
    // $ANTLR end rule__Literal__Group_4__2


    // $ANTLR start rule__Literal__Group_4__2__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2836:1: rule__Literal__Group_4__2__Impl : ( ( rule__Literal__Group_4_2__0 )* ) ;
    public final void rule__Literal__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2840:1: ( ( ( rule__Literal__Group_4_2__0 )* ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2841:1: ( ( rule__Literal__Group_4_2__0 )* )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2841:1: ( ( rule__Literal__Group_4_2__0 )* )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2842:1: ( rule__Literal__Group_4_2__0 )*
            {
             before(grammarAccess.getLiteralAccess().getGroup_4_2()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2843:1: ( rule__Literal__Group_4_2__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==20) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2843:2: rule__Literal__Group_4_2__0
            	    {
            	    pushFollow(FOLLOW_rule__Literal__Group_4_2__0_in_rule__Literal__Group_4__2__Impl5739);
            	    rule__Literal__Group_4_2__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
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
    // $ANTLR end rule__Literal__Group_4__2__Impl


    // $ANTLR start rule__Literal__Group_4__3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2853:1: rule__Literal__Group_4__3 : rule__Literal__Group_4__3__Impl ;
    public final void rule__Literal__Group_4__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2857:1: ( rule__Literal__Group_4__3__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2858:2: rule__Literal__Group_4__3__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__3__Impl_in_rule__Literal__Group_4__35770);
            rule__Literal__Group_4__3__Impl();
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
    // $ANTLR end rule__Literal__Group_4__3


    // $ANTLR start rule__Literal__Group_4__3__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2864:1: rule__Literal__Group_4__3__Impl : ( '}' ) ;
    public final void rule__Literal__Group_4__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2868:1: ( ( '}' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2869:1: ( '}' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2869:1: ( '}' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2870:1: '}'
            {
             before(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_4_3()); 
            match(input,21,FOLLOW_21_in_rule__Literal__Group_4__3__Impl5798); 
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
    // $ANTLR end rule__Literal__Group_4__3__Impl


    // $ANTLR start rule__Literal__Group_4_2__0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2891:1: rule__Literal__Group_4_2__0 : rule__Literal__Group_4_2__0__Impl rule__Literal__Group_4_2__1 ;
    public final void rule__Literal__Group_4_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2895:1: ( rule__Literal__Group_4_2__0__Impl rule__Literal__Group_4_2__1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2896:2: rule__Literal__Group_4_2__0__Impl rule__Literal__Group_4_2__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_4_2__0__Impl_in_rule__Literal__Group_4_2__05837);
            rule__Literal__Group_4_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4_2__1_in_rule__Literal__Group_4_2__05840);
            rule__Literal__Group_4_2__1();
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
    // $ANTLR end rule__Literal__Group_4_2__0


    // $ANTLR start rule__Literal__Group_4_2__0__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2903:1: rule__Literal__Group_4_2__0__Impl : ( ',' ) ;
    public final void rule__Literal__Group_4_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2907:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2908:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2908:1: ( ',' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2909:1: ','
            {
             before(grammarAccess.getLiteralAccess().getCommaKeyword_4_2_0()); 
            match(input,20,FOLLOW_20_in_rule__Literal__Group_4_2__0__Impl5868); 
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
    // $ANTLR end rule__Literal__Group_4_2__0__Impl


    // $ANTLR start rule__Literal__Group_4_2__1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2922:1: rule__Literal__Group_4_2__1 : rule__Literal__Group_4_2__1__Impl ;
    public final void rule__Literal__Group_4_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2926:1: ( rule__Literal__Group_4_2__1__Impl )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2927:2: rule__Literal__Group_4_2__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_4_2__1__Impl_in_rule__Literal__Group_4_2__15899);
            rule__Literal__Group_4_2__1__Impl();
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
    // $ANTLR end rule__Literal__Group_4_2__1


    // $ANTLR start rule__Literal__Group_4_2__1__Impl
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2933:1: rule__Literal__Group_4_2__1__Impl : ( ( rule__Literal__ParametersAssignment_4_2_1 ) ) ;
    public final void rule__Literal__Group_4_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2937:1: ( ( ( rule__Literal__ParametersAssignment_4_2_1 ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2938:1: ( ( rule__Literal__ParametersAssignment_4_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2938:1: ( ( rule__Literal__ParametersAssignment_4_2_1 ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2939:1: ( rule__Literal__ParametersAssignment_4_2_1 )
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_4_2_1()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2940:1: ( rule__Literal__ParametersAssignment_4_2_1 )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2940:2: rule__Literal__ParametersAssignment_4_2_1
            {
            pushFollow(FOLLOW_rule__Literal__ParametersAssignment_4_2_1_in_rule__Literal__Group_4_2__1__Impl5926);
            rule__Literal__ParametersAssignment_4_2_1();
            _fsp--;


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
    // $ANTLR end rule__Literal__Group_4_2__1__Impl


    // $ANTLR start rule__TopLevel__ToplevelExpressionAssignment
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2955:1: rule__TopLevel__ToplevelExpressionAssignment : ( ruleOrExpression ) ;
    public final void rule__TopLevel__ToplevelExpressionAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2959:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2960:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2960:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2961:1: ruleOrExpression
            {
             before(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__TopLevel__ToplevelExpressionAssignment5965);
            ruleOrExpression();
            _fsp--;

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
    // $ANTLR end rule__TopLevel__ToplevelExpressionAssignment


    // $ANTLR start rule__TmlExpression__AbsoluteAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2970:1: rule__TmlExpression__AbsoluteAssignment_1 : ( ( '/' ) ) ;
    public final void rule__TmlExpression__AbsoluteAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2974:1: ( ( ( '/' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2975:1: ( ( '/' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2975:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2976:1: ( '/' )
            {
             before(grammarAccess.getTmlExpressionAccess().getAbsoluteSolidusKeyword_1_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2977:1: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2978:1: '/'
            {
             before(grammarAccess.getTmlExpressionAccess().getAbsoluteSolidusKeyword_1_0()); 
            match(input,14,FOLLOW_14_in_rule__TmlExpression__AbsoluteAssignment_16001); 
             after(grammarAccess.getTmlExpressionAccess().getAbsoluteSolidusKeyword_1_0()); 

            }

             after(grammarAccess.getTmlExpressionAccess().getAbsoluteSolidusKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__AbsoluteAssignment_1


    // $ANTLR start rule__TmlExpression__ElementsAssignment_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2993:1: rule__TmlExpression__ElementsAssignment_2 : ( rulePathElement ) ;
    public final void rule__TmlExpression__ElementsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2997:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2998:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2998:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:2999:1: rulePathElement
            {
             before(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_2_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__TmlExpression__ElementsAssignment_26040);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__ElementsAssignment_2


    // $ANTLR start rule__TmlExpression__ElementsAssignment_3_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3008:1: rule__TmlExpression__ElementsAssignment_3_1 : ( rulePathElement ) ;
    public final void rule__TmlExpression__ElementsAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3012:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3013:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3013:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3014:1: rulePathElement
            {
             before(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__TmlExpression__ElementsAssignment_3_16071);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getTmlExpressionAccess().getElementsPathElementParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__TmlExpression__ElementsAssignment_3_1


    // $ANTLR start rule__ExistsTmlExpression__AbsoluteAssignment_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3023:1: rule__ExistsTmlExpression__AbsoluteAssignment_2 : ( ( '/' ) ) ;
    public final void rule__ExistsTmlExpression__AbsoluteAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3027:1: ( ( ( '/' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3028:1: ( ( '/' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3028:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3029:1: ( '/' )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteSolidusKeyword_2_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3030:1: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3031:1: '/'
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteSolidusKeyword_2_0()); 
            match(input,14,FOLLOW_14_in_rule__ExistsTmlExpression__AbsoluteAssignment_26107); 
             after(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteSolidusKeyword_2_0()); 

            }

             after(grammarAccess.getExistsTmlExpressionAccess().getAbsoluteSolidusKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__AbsoluteAssignment_2


    // $ANTLR start rule__ExistsTmlExpression__ElementsAssignment_3
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3046:1: rule__ExistsTmlExpression__ElementsAssignment_3 : ( rulePathElement ) ;
    public final void rule__ExistsTmlExpression__ElementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3050:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3051:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3051:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3052:1: rulePathElement
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__ExistsTmlExpression__ElementsAssignment_36146);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__ElementsAssignment_3


    // $ANTLR start rule__ExistsTmlExpression__ElementsAssignment_4_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3061:1: rule__ExistsTmlExpression__ElementsAssignment_4_1 : ( rulePathElement ) ;
    public final void rule__ExistsTmlExpression__ElementsAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3065:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3066:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3066:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3067:1: rulePathElement
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__ExistsTmlExpression__ElementsAssignment_4_16177);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getExistsTmlExpressionAccess().getElementsPathElementParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExistsTmlExpression__ElementsAssignment_4_1


    // $ANTLR start rule__MapGetReference__OperationsAssignment_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3076:1: rule__MapGetReference__OperationsAssignment_0 : ( ( '$' ) ) ;
    public final void rule__MapGetReference__OperationsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3080:1: ( ( ( '$' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3081:1: ( ( '$' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3081:1: ( ( '$' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3082:1: ( '$' )
            {
             before(grammarAccess.getMapGetReferenceAccess().getOperationsDollarSignKeyword_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3083:1: ( '$' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3084:1: '$'
            {
             before(grammarAccess.getMapGetReferenceAccess().getOperationsDollarSignKeyword_0_0()); 
            match(input,22,FOLLOW_22_in_rule__MapGetReference__OperationsAssignment_06213); 
             after(grammarAccess.getMapGetReferenceAccess().getOperationsDollarSignKeyword_0_0()); 

            }

             after(grammarAccess.getMapGetReferenceAccess().getOperationsDollarSignKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MapGetReference__OperationsAssignment_0


    // $ANTLR start rule__MapGetReference__ElementsAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3099:1: rule__MapGetReference__ElementsAssignment_1 : ( rulePathElement ) ;
    public final void rule__MapGetReference__ElementsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3103:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3104:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3104:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3105:1: rulePathElement
            {
             before(grammarAccess.getMapGetReferenceAccess().getElementsPathElementParserRuleCall_1_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__MapGetReference__ElementsAssignment_16252);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getMapGetReferenceAccess().getElementsPathElementParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MapGetReference__ElementsAssignment_1


    // $ANTLR start rule__MapGetReference__ElementsAssignment_2_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3114:1: rule__MapGetReference__ElementsAssignment_2_1 : ( rulePathElement ) ;
    public final void rule__MapGetReference__ElementsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3118:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3119:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3119:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3120:1: rulePathElement
            {
             before(grammarAccess.getMapGetReferenceAccess().getElementsPathElementParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__MapGetReference__ElementsAssignment_2_16283);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getMapGetReferenceAccess().getElementsPathElementParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MapGetReference__ElementsAssignment_2_1


    // $ANTLR start rule__OrExpression__ParametersAssignment_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3129:1: rule__OrExpression__ParametersAssignment_0 : ( ruleAndExpression ) ;
    public final void rule__OrExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3133:1: ( ( ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3134:1: ( ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3134:1: ( ruleAndExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3135:1: ruleAndExpression
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_06314);
            ruleAndExpression();
            _fsp--;

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
    // $ANTLR end rule__OrExpression__ParametersAssignment_0


    // $ANTLR start rule__OrExpression__OperationsAssignment_1_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3144:1: rule__OrExpression__OperationsAssignment_1_0 : ( ( 'OR' ) ) ;
    public final void rule__OrExpression__OperationsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3148:1: ( ( ( 'OR' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3149:1: ( ( 'OR' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3149:1: ( ( 'OR' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3150:1: ( 'OR' )
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3151:1: ( 'OR' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3152:1: 'OR'
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_1_0_0()); 
            match(input,23,FOLLOW_23_in_rule__OrExpression__OperationsAssignment_1_06350); 
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
    // $ANTLR end rule__OrExpression__OperationsAssignment_1_0


    // $ANTLR start rule__OrExpression__ParametersAssignment_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3167:1: rule__OrExpression__ParametersAssignment_1_1 : ( ruleAndExpression ) ;
    public final void rule__OrExpression__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3171:1: ( ( ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3172:1: ( ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3172:1: ( ruleAndExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3173:1: ruleAndExpression
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_1_16389);
            ruleAndExpression();
            _fsp--;

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
    // $ANTLR end rule__OrExpression__ParametersAssignment_1_1


    // $ANTLR start rule__AndExpression__ParametersAssignment_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3182:1: rule__AndExpression__ParametersAssignment_0 : ( ruleEqualityExpression ) ;
    public final void rule__AndExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3186:1: ( ( ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3187:1: ( ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3187:1: ( ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3188:1: ruleEqualityExpression
            {
             before(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_06420);
            ruleEqualityExpression();
            _fsp--;

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
    // $ANTLR end rule__AndExpression__ParametersAssignment_0


    // $ANTLR start rule__AndExpression__OperationsAssignment_1_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3197:1: rule__AndExpression__OperationsAssignment_1_0 : ( ( 'AND' ) ) ;
    public final void rule__AndExpression__OperationsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3201:1: ( ( ( 'AND' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3202:1: ( ( 'AND' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3202:1: ( ( 'AND' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3203:1: ( 'AND' )
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3204:1: ( 'AND' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3205:1: 'AND'
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_1_0_0()); 
            match(input,24,FOLLOW_24_in_rule__AndExpression__OperationsAssignment_1_06456); 
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
    // $ANTLR end rule__AndExpression__OperationsAssignment_1_0


    // $ANTLR start rule__AndExpression__ParametersAssignment_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3220:1: rule__AndExpression__ParametersAssignment_1_1 : ( ruleEqualityExpression ) ;
    public final void rule__AndExpression__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3224:1: ( ( ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3225:1: ( ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3225:1: ( ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3226:1: ruleEqualityExpression
            {
             before(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_1_16495);
            ruleEqualityExpression();
            _fsp--;

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
    // $ANTLR end rule__AndExpression__ParametersAssignment_1_1


    // $ANTLR start rule__EqualityExpression__ParametersAssignment_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3235:1: rule__EqualityExpression__ParametersAssignment_0 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3239:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3240:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3240:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3241:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_06526);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__ParametersAssignment_0


    // $ANTLR start rule__EqualityExpression__OperationsAssignment_1_0_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3250:1: rule__EqualityExpression__OperationsAssignment_1_0_0 : ( ( '==' ) ) ;
    public final void rule__EqualityExpression__OperationsAssignment_1_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3254:1: ( ( ( '==' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3255:1: ( ( '==' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3255:1: ( ( '==' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3256:1: ( '==' )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3257:1: ( '==' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3258:1: '=='
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_1_0_0_0()); 
            match(input,25,FOLLOW_25_in_rule__EqualityExpression__OperationsAssignment_1_0_06562); 
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
    // $ANTLR end rule__EqualityExpression__OperationsAssignment_1_0_0


    // $ANTLR start rule__EqualityExpression__ParametersAssignment_1_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3273:1: rule__EqualityExpression__ParametersAssignment_1_0_1 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3277:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3278:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3278:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3279:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_1_0_16601);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__ParametersAssignment_1_0_1


    // $ANTLR start rule__EqualityExpression__OperationsAssignment_1_1_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3288:1: rule__EqualityExpression__OperationsAssignment_1_1_0 : ( ( '!=' ) ) ;
    public final void rule__EqualityExpression__OperationsAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3292:1: ( ( ( '!=' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3293:1: ( ( '!=' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3293:1: ( ( '!=' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3294:1: ( '!=' )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3295:1: ( '!=' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3296:1: '!='
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_1_1_0_0()); 
            match(input,26,FOLLOW_26_in_rule__EqualityExpression__OperationsAssignment_1_1_06637); 
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
    // $ANTLR end rule__EqualityExpression__OperationsAssignment_1_1_0


    // $ANTLR start rule__EqualityExpression__ParametersAssignment_1_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3311:1: rule__EqualityExpression__ParametersAssignment_1_1_1 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3315:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3316:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3316:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3317:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_1_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_1_1_16676);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__EqualityExpression__ParametersAssignment_1_1_1


    // $ANTLR start rule__AdditiveExpression__ParametersAssignment_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3326:1: rule__AdditiveExpression__ParametersAssignment_0 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3330:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3331:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3331:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3332:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_06707);
            ruleMultiplicativeExpression();
            _fsp--;

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
    // $ANTLR end rule__AdditiveExpression__ParametersAssignment_0


    // $ANTLR start rule__AdditiveExpression__ParametersAssignment_1_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3341:1: rule__AdditiveExpression__ParametersAssignment_1_0_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3345:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3346:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3346:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3347:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_1_0_16738);
            ruleMultiplicativeExpression();
            _fsp--;

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
    // $ANTLR end rule__AdditiveExpression__ParametersAssignment_1_0_1


    // $ANTLR start rule__AdditiveExpression__ParametersAssignment_1_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3356:1: rule__AdditiveExpression__ParametersAssignment_1_1_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3360:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3361:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3361:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3362:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_1_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_1_1_16769);
            ruleMultiplicativeExpression();
            _fsp--;

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
    // $ANTLR end rule__AdditiveExpression__ParametersAssignment_1_1_1


    // $ANTLR start rule__MultiplicativeExpression__ParametersAssignment_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3371:1: rule__MultiplicativeExpression__ParametersAssignment_0 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3375:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3376:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3376:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3377:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_06800);
            ruleUnaryExpression();
            _fsp--;

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
    // $ANTLR end rule__MultiplicativeExpression__ParametersAssignment_0


    // $ANTLR start rule__MultiplicativeExpression__OperationsAssignment_1_0_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3386:1: rule__MultiplicativeExpression__OperationsAssignment_1_0_0 : ( ( '*' ) ) ;
    public final void rule__MultiplicativeExpression__OperationsAssignment_1_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3390:1: ( ( ( '*' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3391:1: ( ( '*' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3391:1: ( ( '*' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3392:1: ( '*' )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3393:1: ( '*' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3394:1: '*'
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_1_0_0_0()); 
            match(input,27,FOLLOW_27_in_rule__MultiplicativeExpression__OperationsAssignment_1_0_06836); 
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
    // $ANTLR end rule__MultiplicativeExpression__OperationsAssignment_1_0_0


    // $ANTLR start rule__MultiplicativeExpression__ParametersAssignment_1_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3409:1: rule__MultiplicativeExpression__ParametersAssignment_1_0_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3413:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3414:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3414:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3415:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_1_0_16875);
            ruleUnaryExpression();
            _fsp--;

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
    // $ANTLR end rule__MultiplicativeExpression__ParametersAssignment_1_0_1


    // $ANTLR start rule__MultiplicativeExpression__OperationsAssignment_1_1_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3424:1: rule__MultiplicativeExpression__OperationsAssignment_1_1_0 : ( ( '/' ) ) ;
    public final void rule__MultiplicativeExpression__OperationsAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3428:1: ( ( ( '/' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3429:1: ( ( '/' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3429:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3430:1: ( '/' )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_1_1_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3431:1: ( '/' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3432:1: '/'
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_1_1_0_0()); 
            match(input,14,FOLLOW_14_in_rule__MultiplicativeExpression__OperationsAssignment_1_1_06911); 
             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_1_1_0_0()); 

            }

             after(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_1_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__MultiplicativeExpression__OperationsAssignment_1_1_0


    // $ANTLR start rule__MultiplicativeExpression__ParametersAssignment_1_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3447:1: rule__MultiplicativeExpression__ParametersAssignment_1_1_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3451:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3452:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3452:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3453:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_1_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_1_1_16950);
            ruleUnaryExpression();
            _fsp--;

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
    // $ANTLR end rule__MultiplicativeExpression__ParametersAssignment_1_1_1


    // $ANTLR start rule__UnaryExpression__OperationsAssignment_0_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3462:1: rule__UnaryExpression__OperationsAssignment_0_0 : ( ( '!' ) ) ;
    public final void rule__UnaryExpression__OperationsAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3466:1: ( ( ( '!' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3467:1: ( ( '!' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3467:1: ( ( '!' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3468:1: ( '!' )
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3469:1: ( '!' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3470:1: '!'
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_0_0()); 
            match(input,28,FOLLOW_28_in_rule__UnaryExpression__OperationsAssignment_0_06986); 
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
    // $ANTLR end rule__UnaryExpression__OperationsAssignment_0_0


    // $ANTLR start rule__UnaryExpression__ParametersAssignment_0_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3485:1: rule__UnaryExpression__ParametersAssignment_0_1 : ( rulePrimaryExpression ) ;
    public final void rule__UnaryExpression__ParametersAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3489:1: ( ( rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3490:1: ( rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3490:1: ( rulePrimaryExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3491:1: rulePrimaryExpression
            {
             before(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__ParametersAssignment_0_17025);
            rulePrimaryExpression();
            _fsp--;

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
    // $ANTLR end rule__UnaryExpression__ParametersAssignment_0_1


    // $ANTLR start rule__PrimaryExpression__ParametersAssignment_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3500:1: rule__PrimaryExpression__ParametersAssignment_0 : ( ruleLiteral ) ;
    public final void rule__PrimaryExpression__ParametersAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3504:1: ( ( ruleLiteral ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3505:1: ( ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3505:1: ( ruleLiteral )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3506:1: ruleLiteral
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleLiteral_in_rule__PrimaryExpression__ParametersAssignment_07056);
            ruleLiteral();
            _fsp--;

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
    // $ANTLR end rule__PrimaryExpression__ParametersAssignment_0


    // $ANTLR start rule__PrimaryExpression__ParametersAssignment_1_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3515:1: rule__PrimaryExpression__ParametersAssignment_1_1 : ( ruleOrExpression ) ;
    public final void rule__PrimaryExpression__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3519:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3520:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3520:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3521:1: ruleOrExpression
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__PrimaryExpression__ParametersAssignment_1_17087);
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
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3530:1: rule__FunctionCall__NameAssignment_0 : ( ruleFunctionName ) ;
    public final void rule__FunctionCall__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3534:1: ( ( ruleFunctionName ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3535:1: ( ruleFunctionName )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3535:1: ( ruleFunctionName )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3536:1: ruleFunctionName
            {
             before(grammarAccess.getFunctionCallAccess().getNameFunctionNameParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleFunctionName_in_rule__FunctionCall__NameAssignment_07118);
            ruleFunctionName();
            _fsp--;

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
    // $ANTLR end rule__FunctionCall__NameAssignment_0


    // $ANTLR start rule__FunctionCall__ParametersAssignment_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3545:1: rule__FunctionCall__ParametersAssignment_2 : ( ruleOrExpression ) ;
    public final void rule__FunctionCall__ParametersAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3549:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3550:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3550:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3551:1: ruleOrExpression
            {
             before(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__FunctionCall__ParametersAssignment_27149);
            ruleOrExpression();
            _fsp--;

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
    // $ANTLR end rule__FunctionCall__ParametersAssignment_2


    // $ANTLR start rule__FunctionCall__ParametersAssignment_3_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3560:1: rule__FunctionCall__ParametersAssignment_3_1 : ( ruleOrExpression ) ;
    public final void rule__FunctionCall__ParametersAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3564:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3565:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3565:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3566:1: ruleOrExpression
            {
             before(grammarAccess.getFunctionCallAccess().getParametersOrExpressionParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__FunctionCall__ParametersAssignment_3_17180);
            ruleOrExpression();
            _fsp--;

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
    // $ANTLR end rule__FunctionCall__ParametersAssignment_3_1


    // $ANTLR start rule__Literal__ValueStringAssignment_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3575:1: rule__Literal__ValueStringAssignment_1 : ( RULE_LITERALSTRING ) ;
    public final void rule__Literal__ValueStringAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3579:1: ( ( RULE_LITERALSTRING ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3580:1: ( RULE_LITERALSTRING )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3580:1: ( RULE_LITERALSTRING )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3581:1: RULE_LITERALSTRING
            {
             before(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_17211); 
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
    // $ANTLR end rule__Literal__ValueStringAssignment_1


    // $ANTLR start rule__Literal__OperationsAssignment_2_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3590:1: rule__Literal__OperationsAssignment_2_0 : ( ( 'FORALL' ) ) ;
    public final void rule__Literal__OperationsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3594:1: ( ( ( 'FORALL' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3595:1: ( ( 'FORALL' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3595:1: ( ( 'FORALL' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3596:1: ( 'FORALL' )
            {
             before(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3597:1: ( 'FORALL' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3598:1: 'FORALL'
            {
             before(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_0_0()); 
            match(input,29,FOLLOW_29_in_rule__Literal__OperationsAssignment_2_07247); 
             after(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_0_0()); 

            }

             after(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__OperationsAssignment_2_0


    // $ANTLR start rule__Literal__ValueStringAssignment_2_2
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3613:1: rule__Literal__ValueStringAssignment_2_2 : ( RULE_LITERALSTRING ) ;
    public final void rule__Literal__ValueStringAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3617:1: ( ( RULE_LITERALSTRING ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3618:1: ( RULE_LITERALSTRING )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3618:1: ( RULE_LITERALSTRING )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3619:1: RULE_LITERALSTRING
            {
             before(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_2_0()); 
            match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_2_27286); 
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
    // $ANTLR end rule__Literal__ValueStringAssignment_2_2


    // $ANTLR start rule__Literal__ParametersAssignment_2_4
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3628:1: rule__Literal__ParametersAssignment_2_4 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_2_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3632:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3633:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3633:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3634:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_4_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_2_47317);
            ruleOrExpression();
            _fsp--;

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
    // $ANTLR end rule__Literal__ParametersAssignment_2_4


    // $ANTLR start rule__Literal__ExpressionTypeAssignment_4_0
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3643:1: rule__Literal__ExpressionTypeAssignment_4_0 : ( ( '{' ) ) ;
    public final void rule__Literal__ExpressionTypeAssignment_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3647:1: ( ( ( '{' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3648:1: ( ( '{' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3648:1: ( ( '{' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3649:1: ( '{' )
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3650:1: ( '{' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3651:1: '{'
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_4_0_0()); 
            match(input,30,FOLLOW_30_in_rule__Literal__ExpressionTypeAssignment_4_07353); 
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
    // $ANTLR end rule__Literal__ExpressionTypeAssignment_4_0


    // $ANTLR start rule__Literal__ParametersAssignment_4_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3666:1: rule__Literal__ParametersAssignment_4_1 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3670:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3671:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3671:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3672:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_4_17392);
            ruleOrExpression();
            _fsp--;

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
    // $ANTLR end rule__Literal__ParametersAssignment_4_1


    // $ANTLR start rule__Literal__ParametersAssignment_4_2_1
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3681:1: rule__Literal__ParametersAssignment_4_2_1 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_4_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3685:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3686:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3686:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3687:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_4_2_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_4_2_17423);
            ruleOrExpression();
            _fsp--;

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
    // $ANTLR end rule__Literal__ParametersAssignment_4_2_1


    // $ANTLR start rule__Literal__ElementsAssignment_5
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3696:1: rule__Literal__ElementsAssignment_5 : ( ( 'NULL' ) ) ;
    public final void rule__Literal__ElementsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3700:1: ( ( ( 'NULL' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3701:1: ( ( 'NULL' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3701:1: ( ( 'NULL' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3702:1: ( 'NULL' )
            {
             before(grammarAccess.getLiteralAccess().getElementsNULLKeyword_5_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3703:1: ( 'NULL' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3704:1: 'NULL'
            {
             before(grammarAccess.getLiteralAccess().getElementsNULLKeyword_5_0()); 
            match(input,31,FOLLOW_31_in_rule__Literal__ElementsAssignment_57459); 
             after(grammarAccess.getLiteralAccess().getElementsNULLKeyword_5_0()); 

            }

             after(grammarAccess.getLiteralAccess().getElementsNULLKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__ElementsAssignment_5


    // $ANTLR start rule__Literal__ElementsAssignment_6
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3719:1: rule__Literal__ElementsAssignment_6 : ( ( 'TODAY' ) ) ;
    public final void rule__Literal__ElementsAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3723:1: ( ( ( 'TODAY' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3724:1: ( ( 'TODAY' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3724:1: ( ( 'TODAY' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3725:1: ( 'TODAY' )
            {
             before(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_6_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3726:1: ( 'TODAY' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3727:1: 'TODAY'
            {
             before(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_6_0()); 
            match(input,32,FOLLOW_32_in_rule__Literal__ElementsAssignment_67503); 
             after(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_6_0()); 

            }

             after(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__ElementsAssignment_6


    // $ANTLR start rule__Literal__ElementsAssignment_7
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3742:1: rule__Literal__ElementsAssignment_7 : ( ( 'TRUE' ) ) ;
    public final void rule__Literal__ElementsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3746:1: ( ( ( 'TRUE' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3747:1: ( ( 'TRUE' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3747:1: ( ( 'TRUE' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3748:1: ( 'TRUE' )
            {
             before(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_7_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3749:1: ( 'TRUE' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3750:1: 'TRUE'
            {
             before(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_7_0()); 
            match(input,33,FOLLOW_33_in_rule__Literal__ElementsAssignment_77547); 
             after(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_7_0()); 

            }

             after(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__ElementsAssignment_7


    // $ANTLR start rule__Literal__ElementsAssignment_8
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3765:1: rule__Literal__ElementsAssignment_8 : ( ( 'FALSE' ) ) ;
    public final void rule__Literal__ElementsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3769:1: ( ( ( 'FALSE' ) ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3770:1: ( ( 'FALSE' ) )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3770:1: ( ( 'FALSE' ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3771:1: ( 'FALSE' )
            {
             before(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_8_0()); 
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3772:1: ( 'FALSE' )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3773:1: 'FALSE'
            {
             before(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_8_0()); 
            match(input,34,FOLLOW_34_in_rule__Literal__ElementsAssignment_87591); 
             after(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_8_0()); 

            }

             after(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Literal__ElementsAssignment_8


    // $ANTLR start rule__Literal__ParametersAssignment_9
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3788:1: rule__Literal__ParametersAssignment_9 : ( ruleTmlExpression ) ;
    public final void rule__Literal__ParametersAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3792:1: ( ( ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3793:1: ( ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3793:1: ( ruleTmlExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3794:1: ruleTmlExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersTmlExpressionParserRuleCall_9_0()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_rule__Literal__ParametersAssignment_97630);
            ruleTmlExpression();
            _fsp--;

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
    // $ANTLR end rule__Literal__ParametersAssignment_9


    // $ANTLR start rule__Literal__ParametersAssignment_10
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3803:1: rule__Literal__ParametersAssignment_10 : ( ruleExistsTmlExpression ) ;
    public final void rule__Literal__ParametersAssignment_10() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3807:1: ( ( ruleExistsTmlExpression ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3808:1: ( ruleExistsTmlExpression )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3808:1: ( ruleExistsTmlExpression )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3809:1: ruleExistsTmlExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersExistsTmlExpressionParserRuleCall_10_0()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_rule__Literal__ParametersAssignment_107661);
            ruleExistsTmlExpression();
            _fsp--;

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
    // $ANTLR end rule__Literal__ParametersAssignment_10


    // $ANTLR start rule__Literal__ParametersAssignment_11
    // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3818:1: rule__Literal__ParametersAssignment_11 : ( ruleMapGetReference ) ;
    public final void rule__Literal__ParametersAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3822:1: ( ( ruleMapGetReference ) )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3823:1: ( ruleMapGetReference )
            {
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3823:1: ( ruleMapGetReference )
            // ../com.dexels.navajo.dsl.expression.ui/src-gen/com/dexels/navajo/dsl/expression/ui/contentassist/antlr/internal/InternalNavajoExpression.g:3824:1: ruleMapGetReference
            {
             before(grammarAccess.getLiteralAccess().getParametersMapGetReferenceParserRuleCall_11_0()); 
            pushFollow(FOLLOW_ruleMapGetReference_in_rule__Literal__ParametersAssignment_117692);
            ruleMapGetReference();
            _fsp--;

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
    // $ANTLR end rule__Literal__ParametersAssignment_11


 

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
    public static final BitSet FOLLOW_ruleMapGetReference_in_entryRuleMapGetReference301 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMapGetReference308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__0_in_ruleMapGetReference334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression361 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__0_in_ruleOrExpression394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression421 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__0_in_ruleAndExpression454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__0_in_ruleEqualityExpression514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression541 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__0_in_ruleAdditiveExpression574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__0_in_ruleMultiplicativeExpression634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression661 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Alternatives_in_ruleUnaryExpression694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression721 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Alternatives_in_rulePrimaryExpression754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_entryRuleFunctionName781 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionName788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFunctionName814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall840 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__0_in_ruleFunctionCall873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral900 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Alternatives_in_ruleLiteral933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PathElement__Alternatives969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_rule__PathElement__Alternatives987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__PathElement__Alternatives1007 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_0__0_in_rule__EqualityExpression__Alternatives_11041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_1__0_in_rule__EqualityExpression__Alternatives_11059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_0__0_in_rule__AdditiveExpression__Alternatives_11092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_1__0_in_rule__AdditiveExpression__Alternatives_11110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_0__0_in_rule__MultiplicativeExpression__Alternatives_11143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_1__0_in_rule__MultiplicativeExpression__Alternatives_11161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__0_in_rule__UnaryExpression__Alternatives1194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__Alternatives1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__ParametersAssignment_0_in_rule__PrimaryExpression__Alternatives1244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__0_in_rule__PrimaryExpression__Alternatives1262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__0_in_rule__Literal__Alternatives1295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_1_in_rule__Literal__Alternatives1313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__0_in_rule__Literal__Alternatives1331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_rule__Literal__Alternatives1349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__0_in_rule__Literal__Alternatives1366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_5_in_rule__Literal__Alternatives1384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_6_in_rule__Literal__Alternatives1402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_7_in_rule__Literal__Alternatives1420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_8_in_rule__Literal__Alternatives1438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_9_in_rule__Literal__Alternatives1456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_10_in_rule__Literal__Alternatives1474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_11_in_rule__Literal__Alternatives1492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__0__Impl_in_rule__TmlExpression__Group__01523 = new BitSet(new long[]{0x0000000000004C10L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__1_in_rule__TmlExpression__Group__01526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__TmlExpression__Group__0__Impl1554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__1__Impl_in_rule__TmlExpression__Group__11585 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__2_in_rule__TmlExpression__Group__11588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__AbsoluteAssignment_1_in_rule__TmlExpression__Group__1__Impl1615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__2__Impl_in_rule__TmlExpression__Group__21646 = new BitSet(new long[]{0x0000000000006000L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__3_in_rule__TmlExpression__Group__21649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__ElementsAssignment_2_in_rule__TmlExpression__Group__2__Impl1676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__3__Impl_in_rule__TmlExpression__Group__31706 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__4_in_rule__TmlExpression__Group__31709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group_3__0_in_rule__TmlExpression__Group__3__Impl1736 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group__4__Impl_in_rule__TmlExpression__Group__41767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__TmlExpression__Group__4__Impl1795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group_3__0__Impl_in_rule__TmlExpression__Group_3__01836 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group_3__1_in_rule__TmlExpression__Group_3__01839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__TmlExpression__Group_3__0__Impl1867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__Group_3__1__Impl_in_rule__TmlExpression__Group_3__11898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TmlExpression__ElementsAssignment_3_1_in_rule__TmlExpression__Group_3__1__Impl1925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__0__Impl_in_rule__ExistsTmlExpression__Group__01959 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__1_in_rule__ExistsTmlExpression__Group__01962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__ExistsTmlExpression__Group__0__Impl1990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__1__Impl_in_rule__ExistsTmlExpression__Group__12021 = new BitSet(new long[]{0x0000000000004C10L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__2_in_rule__ExistsTmlExpression__Group__12024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__ExistsTmlExpression__Group__1__Impl2052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__2__Impl_in_rule__ExistsTmlExpression__Group__22083 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__3_in_rule__ExistsTmlExpression__Group__22086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__AbsoluteAssignment_2_in_rule__ExistsTmlExpression__Group__2__Impl2113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__3__Impl_in_rule__ExistsTmlExpression__Group__32144 = new BitSet(new long[]{0x0000000000006000L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__4_in_rule__ExistsTmlExpression__Group__32147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__ElementsAssignment_3_in_rule__ExistsTmlExpression__Group__3__Impl2174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__4__Impl_in_rule__ExistsTmlExpression__Group__42204 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__5_in_rule__ExistsTmlExpression__Group__42207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group_4__0_in_rule__ExistsTmlExpression__Group__4__Impl2234 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__5__Impl_in_rule__ExistsTmlExpression__Group__52265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__ExistsTmlExpression__Group__5__Impl2293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group_4__0__Impl_in_rule__ExistsTmlExpression__Group_4__02336 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group_4__1_in_rule__ExistsTmlExpression__Group_4__02339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__ExistsTmlExpression__Group_4__0__Impl2367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group_4__1__Impl_in_rule__ExistsTmlExpression__Group_4__12398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__ElementsAssignment_4_1_in_rule__ExistsTmlExpression__Group_4__1__Impl2425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__0__Impl_in_rule__MapGetReference__Group__02459 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__1_in_rule__MapGetReference__Group__02462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__OperationsAssignment_0_in_rule__MapGetReference__Group__0__Impl2489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__1__Impl_in_rule__MapGetReference__Group__12519 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__2_in_rule__MapGetReference__Group__12522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__ElementsAssignment_1_in_rule__MapGetReference__Group__1__Impl2549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group__2__Impl_in_rule__MapGetReference__Group__22579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group_2__0_in_rule__MapGetReference__Group__2__Impl2606 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group_2__0__Impl_in_rule__MapGetReference__Group_2__02643 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group_2__1_in_rule__MapGetReference__Group_2__02646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__MapGetReference__Group_2__0__Impl2674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__Group_2__1__Impl_in_rule__MapGetReference__Group_2__12705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MapGetReference__ElementsAssignment_2_1_in_rule__MapGetReference__Group_2__1__Impl2732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__0__Impl_in_rule__OrExpression__Group__02766 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__1_in_rule__OrExpression__Group__02769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__ParametersAssignment_0_in_rule__OrExpression__Group__0__Impl2796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__1__Impl_in_rule__OrExpression__Group__12826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_1__0_in_rule__OrExpression__Group__1__Impl2853 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_1__0__Impl_in_rule__OrExpression__Group_1__02888 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_1__1_in_rule__OrExpression__Group_1__02891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__OperationsAssignment_1_0_in_rule__OrExpression__Group_1__0__Impl2918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_1__1__Impl_in_rule__OrExpression__Group_1__12948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__ParametersAssignment_1_1_in_rule__OrExpression__Group_1__1__Impl2975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__0__Impl_in_rule__AndExpression__Group__03009 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__1_in_rule__AndExpression__Group__03012 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__ParametersAssignment_0_in_rule__AndExpression__Group__0__Impl3039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__1__Impl_in_rule__AndExpression__Group__13069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_1__0_in_rule__AndExpression__Group__1__Impl3096 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_1__0__Impl_in_rule__AndExpression__Group_1__03131 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_1__1_in_rule__AndExpression__Group_1__03134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__OperationsAssignment_1_0_in_rule__AndExpression__Group_1__0__Impl3161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_1__1__Impl_in_rule__AndExpression__Group_1__13191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__ParametersAssignment_1_1_in_rule__AndExpression__Group_1__1__Impl3218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__0__Impl_in_rule__EqualityExpression__Group__03252 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__1_in_rule__EqualityExpression__Group__03255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_0_in_rule__EqualityExpression__Group__0__Impl3282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__1__Impl_in_rule__EqualityExpression__Group__13312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Alternatives_1_in_rule__EqualityExpression__Group__1__Impl3339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_0__0__Impl_in_rule__EqualityExpression__Group_1_0__03374 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_0__1_in_rule__EqualityExpression__Group_1_0__03377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__OperationsAssignment_1_0_0_in_rule__EqualityExpression__Group_1_0__0__Impl3404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_0__1__Impl_in_rule__EqualityExpression__Group_1_0__13434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_1_0_1_in_rule__EqualityExpression__Group_1_0__1__Impl3461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_1__0__Impl_in_rule__EqualityExpression__Group_1_1__03495 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_1__1_in_rule__EqualityExpression__Group_1_1__03498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__OperationsAssignment_1_1_0_in_rule__EqualityExpression__Group_1_1__0__Impl3525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_1_1__1__Impl_in_rule__EqualityExpression__Group_1_1__13555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_1_1_1_in_rule__EqualityExpression__Group_1_1__1__Impl3582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__0__Impl_in_rule__AdditiveExpression__Group__03616 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__1_in_rule__AdditiveExpression__Group__03619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_0_in_rule__AdditiveExpression__Group__0__Impl3646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__1__Impl_in_rule__AdditiveExpression__Group__13676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Alternatives_1_in_rule__AdditiveExpression__Group__1__Impl3703 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_0__0__Impl_in_rule__AdditiveExpression__Group_1_0__03738 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_0__1_in_rule__AdditiveExpression__Group_1_0__03741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__AdditiveExpression__Group_1_0__0__Impl3769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_0__1__Impl_in_rule__AdditiveExpression__Group_1_0__13800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_0_1_in_rule__AdditiveExpression__Group_1_0__1__Impl3827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_1__0__Impl_in_rule__AdditiveExpression__Group_1_1__03861 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_1__1_in_rule__AdditiveExpression__Group_1_1__03864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__AdditiveExpression__Group_1_1__0__Impl3892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_1_1__1__Impl_in_rule__AdditiveExpression__Group_1_1__13923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_1_1_in_rule__AdditiveExpression__Group_1_1__1__Impl3950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__0__Impl_in_rule__MultiplicativeExpression__Group__03984 = new BitSet(new long[]{0x0000000008004002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__1_in_rule__MultiplicativeExpression__Group__03987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_0_in_rule__MultiplicativeExpression__Group__0__Impl4014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__1__Impl_in_rule__MultiplicativeExpression__Group__14044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Alternatives_1_in_rule__MultiplicativeExpression__Group__1__Impl4071 = new BitSet(new long[]{0x0000000008004002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_0__0__Impl_in_rule__MultiplicativeExpression__Group_1_0__04106 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_0__1_in_rule__MultiplicativeExpression__Group_1_0__04109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_1_0_0_in_rule__MultiplicativeExpression__Group_1_0__0__Impl4136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_0__1__Impl_in_rule__MultiplicativeExpression__Group_1_0__14166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_0_1_in_rule__MultiplicativeExpression__Group_1_0__1__Impl4193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_1__0__Impl_in_rule__MultiplicativeExpression__Group_1_1__04227 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_1__1_in_rule__MultiplicativeExpression__Group_1_1__04230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_1_1_0_in_rule__MultiplicativeExpression__Group_1_1__0__Impl4257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_1_1__1__Impl_in_rule__MultiplicativeExpression__Group_1_1__14287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_1_1_in_rule__MultiplicativeExpression__Group_1_1__1__Impl4314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__0__Impl_in_rule__UnaryExpression__Group_0__04348 = new BitSet(new long[]{0x00000007E0449070L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__1_in_rule__UnaryExpression__Group_0__04351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__OperationsAssignment_0_0_in_rule__UnaryExpression__Group_0__0__Impl4378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__1__Impl_in_rule__UnaryExpression__Group_0__14408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__ParametersAssignment_0_1_in_rule__UnaryExpression__Group_0__1__Impl4435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__0__Impl_in_rule__PrimaryExpression__Group_1__04469 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__1_in_rule__PrimaryExpression__Group_1__04472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__PrimaryExpression__Group_1__0__Impl4500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__1__Impl_in_rule__PrimaryExpression__Group_1__14531 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__2_in_rule__PrimaryExpression__Group_1__14534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__ParametersAssignment_1_1_in_rule__PrimaryExpression__Group_1__1__Impl4561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__2__Impl_in_rule__PrimaryExpression__Group_1__24591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__PrimaryExpression__Group_1__2__Impl4619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__0__Impl_in_rule__FunctionCall__Group__04656 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__1_in_rule__FunctionCall__Group__04659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__NameAssignment_0_in_rule__FunctionCall__Group__0__Impl4686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__1__Impl_in_rule__FunctionCall__Group__14716 = new BitSet(new long[]{0x00000007F05C9070L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__2_in_rule__FunctionCall__Group__14719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__FunctionCall__Group__1__Impl4747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__2__Impl_in_rule__FunctionCall__Group__24778 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__3_in_rule__FunctionCall__Group__24781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__ParametersAssignment_2_in_rule__FunctionCall__Group__2__Impl4808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__3__Impl_in_rule__FunctionCall__Group__34839 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__4_in_rule__FunctionCall__Group__34842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group_3__0_in_rule__FunctionCall__Group__3__Impl4869 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__4__Impl_in_rule__FunctionCall__Group__44900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__FunctionCall__Group__4__Impl4928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group_3__0__Impl_in_rule__FunctionCall__Group_3__04969 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group_3__1_in_rule__FunctionCall__Group_3__04972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__FunctionCall__Group_3__0__Impl5000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group_3__1__Impl_in_rule__FunctionCall__Group_3__15031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__ParametersAssignment_3_1_in_rule__FunctionCall__Group_3__1__Impl5058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__0__Impl_in_rule__Literal__Group_0__05092 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__1_in_rule__Literal__Group_0__05095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__1__Impl_in_rule__Literal__Group_0__15153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__Literal__Group_0__1__Impl5180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__0__Impl_in_rule__Literal__Group_2__05213 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__1_in_rule__Literal__Group_2__05216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__OperationsAssignment_2_0_in_rule__Literal__Group_2__0__Impl5243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__1__Impl_in_rule__Literal__Group_2__15273 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__2_in_rule__Literal__Group_2__15276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Literal__Group_2__1__Impl5304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__2__Impl_in_rule__Literal__Group_2__25335 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__3_in_rule__Literal__Group_2__25338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_2_2_in_rule__Literal__Group_2__2__Impl5365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__3__Impl_in_rule__Literal__Group_2__35395 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__4_in_rule__Literal__Group_2__35398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Literal__Group_2__3__Impl5426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__4__Impl_in_rule__Literal__Group_2__45457 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__5_in_rule__Literal__Group_2__45460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_2_4_in_rule__Literal__Group_2__4__Impl5487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__5__Impl_in_rule__Literal__Group_2__55517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__Literal__Group_2__5__Impl5545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__0__Impl_in_rule__Literal__Group_4__05588 = new BitSet(new long[]{0x00000007F0749070L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__1_in_rule__Literal__Group_4__05591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ExpressionTypeAssignment_4_0_in_rule__Literal__Group_4__0__Impl5618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__1__Impl_in_rule__Literal__Group_4__15648 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__2_in_rule__Literal__Group_4__15651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_4_1_in_rule__Literal__Group_4__1__Impl5678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__2__Impl_in_rule__Literal__Group_4__25709 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__3_in_rule__Literal__Group_4__25712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4_2__0_in_rule__Literal__Group_4__2__Impl5739 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__3__Impl_in_rule__Literal__Group_4__35770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Literal__Group_4__3__Impl5798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4_2__0__Impl_in_rule__Literal__Group_4_2__05837 = new BitSet(new long[]{0x00000007F0449070L});
    public static final BitSet FOLLOW_rule__Literal__Group_4_2__1_in_rule__Literal__Group_4_2__05840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Literal__Group_4_2__0__Impl5868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4_2__1__Impl_in_rule__Literal__Group_4_2__15899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_4_2_1_in_rule__Literal__Group_4_2__1__Impl5926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__TopLevel__ToplevelExpressionAssignment5965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__TmlExpression__AbsoluteAssignment_16001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__TmlExpression__ElementsAssignment_26040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__TmlExpression__ElementsAssignment_3_16071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__ExistsTmlExpression__AbsoluteAssignment_26107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__ExistsTmlExpression__ElementsAssignment_36146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__ExistsTmlExpression__ElementsAssignment_4_16177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__MapGetReference__OperationsAssignment_06213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__MapGetReference__ElementsAssignment_16252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__MapGetReference__ElementsAssignment_2_16283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_06314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__OrExpression__OperationsAssignment_1_06350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_1_16389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_06420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__AndExpression__OperationsAssignment_1_06456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_1_16495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_06526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__EqualityExpression__OperationsAssignment_1_0_06562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_1_0_16601 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__EqualityExpression__OperationsAssignment_1_1_06637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_1_1_16676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_06707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_1_0_16738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_1_1_16769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_06800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__MultiplicativeExpression__OperationsAssignment_1_0_06836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_1_0_16875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__MultiplicativeExpression__OperationsAssignment_1_1_06911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_1_1_16950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__UnaryExpression__OperationsAssignment_0_06986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__ParametersAssignment_0_17025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rule__PrimaryExpression__ParametersAssignment_07056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__PrimaryExpression__ParametersAssignment_1_17087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionName_in_rule__FunctionCall__NameAssignment_07118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__FunctionCall__ParametersAssignment_27149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__FunctionCall__ParametersAssignment_3_17180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_17211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__Literal__OperationsAssignment_2_07247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_2_27286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_2_47317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__Literal__ExpressionTypeAssignment_4_07353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_4_17392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_4_2_17423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__Literal__ElementsAssignment_57459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__Literal__ElementsAssignment_67503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__Literal__ElementsAssignment_77547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__Literal__ElementsAssignment_87591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_rule__Literal__ParametersAssignment_97630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_rule__Literal__ParametersAssignment_107661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMapGetReference_in_rule__Literal__ParametersAssignment_117692 = new BitSet(new long[]{0x0000000000000002L});

}