package com.dexels.navajo.dsl.tsl.ui.contentassist.antlr.internal; 

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
import com.dexels.navajo.dsl.tsl.services.TslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTslParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_ATTRIBUTESTRING", "RULE_INT", "RULE_LITERALSTRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'/>'", "'.'", "'..'", "'<navascript'", "'>'", "'</navascript>'", "'='", "'\"='", "';\"'", "'<message'", "'</message>'", "'<map.'", "'</map.'", "'<property'", "'</property>'", "'<expression>'", "'</expression>'", "'['", "'/'", "']'", "'?'", "'+'", "'-'", "'('", "')'", "','", "'}'", "'OR'", "'AND'", "'=='", "'!='", "'*'", "'!'", "'FORALL'", "'{'", "'NULL'", "'TODAY'", "'TRUE'", "'FALSE'"
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
    public String getGrammarFileName() { return "../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g"; }


     
     	private TslGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(TslGrammarAccess grammarAccess) {
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




    // $ANTLR start entryRuleTml
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:61:1: entryRuleTml : ruleTml EOF ;
    public final void entryRuleTml() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:62:1: ( ruleTml EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:63:1: ruleTml EOF
            {
             before(grammarAccess.getTmlRule()); 
            pushFollow(FOLLOW_ruleTml_in_entryRuleTml61);
            ruleTml();
            _fsp--;

             after(grammarAccess.getTmlRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTml68); 

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
    // $ANTLR end entryRuleTml


    // $ANTLR start ruleTml
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:70:1: ruleTml : ( ( rule__Tml__Group__0 ) ) ;
    public final void ruleTml() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:74:2: ( ( ( rule__Tml__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:75:1: ( ( rule__Tml__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:75:1: ( ( rule__Tml__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:76:1: ( rule__Tml__Group__0 )
            {
             before(grammarAccess.getTmlAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:77:1: ( rule__Tml__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:77:2: rule__Tml__Group__0
            {
            pushFollow(FOLLOW_rule__Tml__Group__0_in_ruleTml94);
            rule__Tml__Group__0();
            _fsp--;


            }

             after(grammarAccess.getTmlAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleTml


    // $ANTLR start entryRulePossibleExpression
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:89:1: entryRulePossibleExpression : rulePossibleExpression EOF ;
    public final void entryRulePossibleExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:90:1: ( rulePossibleExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:91:1: rulePossibleExpression EOF
            {
             before(grammarAccess.getPossibleExpressionRule()); 
            pushFollow(FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression121);
            rulePossibleExpression();
            _fsp--;

             after(grammarAccess.getPossibleExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePossibleExpression128); 

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
    // $ANTLR end entryRulePossibleExpression


    // $ANTLR start rulePossibleExpression
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:98:1: rulePossibleExpression : ( ( rule__PossibleExpression__Group__0 ) ) ;
    public final void rulePossibleExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:102:2: ( ( ( rule__PossibleExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:103:1: ( ( rule__PossibleExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:103:1: ( ( rule__PossibleExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:104:1: ( rule__PossibleExpression__Group__0 )
            {
             before(grammarAccess.getPossibleExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:105:1: ( rule__PossibleExpression__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:105:2: rule__PossibleExpression__Group__0
            {
            pushFollow(FOLLOW_rule__PossibleExpression__Group__0_in_rulePossibleExpression154);
            rule__PossibleExpression__Group__0();
            _fsp--;


            }

             after(grammarAccess.getPossibleExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rulePossibleExpression


    // $ANTLR start entryRuleMessage
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:117:1: entryRuleMessage : ruleMessage EOF ;
    public final void entryRuleMessage() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:118:1: ( ruleMessage EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:119:1: ruleMessage EOF
            {
             before(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage181);
            ruleMessage();
            _fsp--;

             after(grammarAccess.getMessageRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage188); 

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
    // $ANTLR end entryRuleMessage


    // $ANTLR start ruleMessage
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:126:1: ruleMessage : ( ( rule__Message__Group__0 ) ) ;
    public final void ruleMessage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:130:2: ( ( ( rule__Message__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:131:1: ( ( rule__Message__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:131:1: ( ( rule__Message__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:132:1: ( rule__Message__Group__0 )
            {
             before(grammarAccess.getMessageAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:133:1: ( rule__Message__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:133:2: rule__Message__Group__0
            {
            pushFollow(FOLLOW_rule__Message__Group__0_in_ruleMessage214);
            rule__Message__Group__0();
            _fsp--;


            }

             after(grammarAccess.getMessageAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleMessage


    // $ANTLR start entryRuleMap
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:145:1: entryRuleMap : ruleMap EOF ;
    public final void entryRuleMap() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:146:1: ( ruleMap EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:147:1: ruleMap EOF
            {
             before(grammarAccess.getMapRule()); 
            pushFollow(FOLLOW_ruleMap_in_entryRuleMap241);
            ruleMap();
            _fsp--;

             after(grammarAccess.getMapRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMap248); 

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
    // $ANTLR end entryRuleMap


    // $ANTLR start ruleMap
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:154:1: ruleMap : ( ( rule__Map__Group__0 ) ) ;
    public final void ruleMap() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:158:2: ( ( ( rule__Map__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:159:1: ( ( rule__Map__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:159:1: ( ( rule__Map__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:160:1: ( rule__Map__Group__0 )
            {
             before(grammarAccess.getMapAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:161:1: ( rule__Map__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:161:2: rule__Map__Group__0
            {
            pushFollow(FOLLOW_rule__Map__Group__0_in_ruleMap274);
            rule__Map__Group__0();
            _fsp--;


            }

             after(grammarAccess.getMapAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleMap


    // $ANTLR start entryRuleProperty
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:173:1: entryRuleProperty : ruleProperty EOF ;
    public final void entryRuleProperty() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:174:1: ( ruleProperty EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:175:1: ruleProperty EOF
            {
             before(grammarAccess.getPropertyRule()); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty301);
            ruleProperty();
            _fsp--;

             after(grammarAccess.getPropertyRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty308); 

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
    // $ANTLR end entryRuleProperty


    // $ANTLR start ruleProperty
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:182:1: ruleProperty : ( ( rule__Property__Group__0 ) ) ;
    public final void ruleProperty() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:186:2: ( ( ( rule__Property__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:187:1: ( ( rule__Property__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:187:1: ( ( rule__Property__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:188:1: ( rule__Property__Group__0 )
            {
             before(grammarAccess.getPropertyAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:189:1: ( rule__Property__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:189:2: rule__Property__Group__0
            {
            pushFollow(FOLLOW_rule__Property__Group__0_in_ruleProperty334);
            rule__Property__Group__0();
            _fsp--;


            }

             after(grammarAccess.getPropertyAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleProperty


    // $ANTLR start entryRuleExpressionTag
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:201:1: entryRuleExpressionTag : ruleExpressionTag EOF ;
    public final void entryRuleExpressionTag() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:202:1: ( ruleExpressionTag EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:203:1: ruleExpressionTag EOF
            {
             before(grammarAccess.getExpressionTagRule()); 
            pushFollow(FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag361);
            ruleExpressionTag();
            _fsp--;

             after(grammarAccess.getExpressionTagRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpressionTag368); 

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
    // $ANTLR end entryRuleExpressionTag


    // $ANTLR start ruleExpressionTag
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:210:1: ruleExpressionTag : ( ( rule__ExpressionTag__Group__0 ) ) ;
    public final void ruleExpressionTag() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:214:2: ( ( ( rule__ExpressionTag__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:215:1: ( ( rule__ExpressionTag__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:215:1: ( ( rule__ExpressionTag__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:216:1: ( rule__ExpressionTag__Group__0 )
            {
             before(grammarAccess.getExpressionTagAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:217:1: ( rule__ExpressionTag__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:217:2: rule__ExpressionTag__Group__0
            {
            pushFollow(FOLLOW_rule__ExpressionTag__Group__0_in_ruleExpressionTag394);
            rule__ExpressionTag__Group__0();
            _fsp--;


            }

             after(grammarAccess.getExpressionTagAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end ruleExpressionTag


    // $ANTLR start entryRuleTopLevel
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:229:1: entryRuleTopLevel : ruleTopLevel EOF ;
    public final void entryRuleTopLevel() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:230:1: ( ruleTopLevel EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:231:1: ruleTopLevel EOF
            {
             before(grammarAccess.getTopLevelRule()); 
            pushFollow(FOLLOW_ruleTopLevel_in_entryRuleTopLevel421);
            ruleTopLevel();
            _fsp--;

             after(grammarAccess.getTopLevelRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTopLevel428); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:238:1: ruleTopLevel : ( ( rule__TopLevel__Group__0 ) ) ;
    public final void ruleTopLevel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:242:2: ( ( ( rule__TopLevel__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:243:1: ( ( rule__TopLevel__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:243:1: ( ( rule__TopLevel__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:244:1: ( rule__TopLevel__Group__0 )
            {
             before(grammarAccess.getTopLevelAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:245:1: ( rule__TopLevel__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:245:2: rule__TopLevel__Group__0
            {
            pushFollow(FOLLOW_rule__TopLevel__Group__0_in_ruleTopLevel454);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:257:1: entryRulePathElement : rulePathElement EOF ;
    public final void entryRulePathElement() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:258:1: ( rulePathElement EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:259:1: rulePathElement EOF
            {
             before(grammarAccess.getPathElementRule()); 
            pushFollow(FOLLOW_rulePathElement_in_entryRulePathElement481);
            rulePathElement();
            _fsp--;

             after(grammarAccess.getPathElementRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathElement488); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:266:1: rulePathElement : ( ( rule__PathElement__Alternatives ) ) ;
    public final void rulePathElement() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:270:2: ( ( ( rule__PathElement__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:271:1: ( ( rule__PathElement__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:271:1: ( ( rule__PathElement__Alternatives ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:272:1: ( rule__PathElement__Alternatives )
            {
             before(grammarAccess.getPathElementAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:273:1: ( rule__PathElement__Alternatives )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:273:2: rule__PathElement__Alternatives
            {
            pushFollow(FOLLOW_rule__PathElement__Alternatives_in_rulePathElement514);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:285:1: entryRulePathSequence : rulePathSequence EOF ;
    public final void entryRulePathSequence() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:286:1: ( rulePathSequence EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:287:1: rulePathSequence EOF
            {
             before(grammarAccess.getPathSequenceRule()); 
            pushFollow(FOLLOW_rulePathSequence_in_entryRulePathSequence541);
            rulePathSequence();
            _fsp--;

             after(grammarAccess.getPathSequenceRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePathSequence548); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:294:1: rulePathSequence : ( ( rule__PathSequence__Group__0 ) ) ;
    public final void rulePathSequence() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:298:2: ( ( ( rule__PathSequence__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:299:1: ( ( rule__PathSequence__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:299:1: ( ( rule__PathSequence__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:300:1: ( rule__PathSequence__Group__0 )
            {
             before(grammarAccess.getPathSequenceAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:301:1: ( rule__PathSequence__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:301:2: rule__PathSequence__Group__0
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__0_in_rulePathSequence574);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:313:1: entryRuleTmlExpression : ruleTmlExpression EOF ;
    public final void entryRuleTmlExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:314:1: ( ruleTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:315:1: ruleTmlExpression EOF
            {
             before(grammarAccess.getTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression601);
            ruleTmlExpression();
            _fsp--;

             after(grammarAccess.getTmlExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTmlExpression608); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:322:1: ruleTmlExpression : ( rulePathSequence ) ;
    public final void ruleTmlExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:326:2: ( ( rulePathSequence ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:327:1: ( rulePathSequence )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:327:1: ( rulePathSequence )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:328:1: rulePathSequence
            {
             before(grammarAccess.getTmlExpressionAccess().getPathSequenceParserRuleCall()); 
            pushFollow(FOLLOW_rulePathSequence_in_ruleTmlExpression634);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:341:1: entryRuleExistsTmlExpression : ruleExistsTmlExpression EOF ;
    public final void entryRuleExistsTmlExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:342:1: ( ruleExistsTmlExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:343:1: ruleExistsTmlExpression EOF
            {
             before(grammarAccess.getExistsTmlExpressionRule()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression660);
            ruleExistsTmlExpression();
            _fsp--;

             after(grammarAccess.getExistsTmlExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExistsTmlExpression667); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:350:1: ruleExistsTmlExpression : ( ( rule__ExistsTmlExpression__Group__0 ) ) ;
    public final void ruleExistsTmlExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:354:2: ( ( ( rule__ExistsTmlExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:355:1: ( ( rule__ExistsTmlExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:355:1: ( ( rule__ExistsTmlExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:356:1: ( rule__ExistsTmlExpression__Group__0 )
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:357:1: ( rule__ExistsTmlExpression__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:357:2: rule__ExistsTmlExpression__Group__0
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__0_in_ruleExistsTmlExpression693);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:369:1: entryRuleOrExpression : ruleOrExpression EOF ;
    public final void entryRuleOrExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:370:1: ( ruleOrExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:371:1: ruleOrExpression EOF
            {
             before(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression720);
            ruleOrExpression();
            _fsp--;

             after(grammarAccess.getOrExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression727); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:378:1: ruleOrExpression : ( ( rule__OrExpression__Group__0 ) ) ;
    public final void ruleOrExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:382:2: ( ( ( rule__OrExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:383:1: ( ( rule__OrExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:383:1: ( ( rule__OrExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:384:1: ( rule__OrExpression__Group__0 )
            {
             before(grammarAccess.getOrExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:385:1: ( rule__OrExpression__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:385:2: rule__OrExpression__Group__0
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__0_in_ruleOrExpression753);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:397:1: entryRuleAndExpression : ruleAndExpression EOF ;
    public final void entryRuleAndExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:398:1: ( ruleAndExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:399:1: ruleAndExpression EOF
            {
             before(grammarAccess.getAndExpressionRule()); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression780);
            ruleAndExpression();
            _fsp--;

             after(grammarAccess.getAndExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression787); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:406:1: ruleAndExpression : ( ( rule__AndExpression__Group__0 ) ) ;
    public final void ruleAndExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:410:2: ( ( ( rule__AndExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:411:1: ( ( rule__AndExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:411:1: ( ( rule__AndExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:412:1: ( rule__AndExpression__Group__0 )
            {
             before(grammarAccess.getAndExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:413:1: ( rule__AndExpression__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:413:2: rule__AndExpression__Group__0
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__0_in_ruleAndExpression813);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:425:1: entryRuleEqualityExpression : ruleEqualityExpression EOF ;
    public final void entryRuleEqualityExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:426:1: ( ruleEqualityExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:427:1: ruleEqualityExpression EOF
            {
             before(grammarAccess.getEqualityExpressionRule()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression840);
            ruleEqualityExpression();
            _fsp--;

             after(grammarAccess.getEqualityExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEqualityExpression847); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:434:1: ruleEqualityExpression : ( ( rule__EqualityExpression__Group__0 ) ) ;
    public final void ruleEqualityExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:438:2: ( ( ( rule__EqualityExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:439:1: ( ( rule__EqualityExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:439:1: ( ( rule__EqualityExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:440:1: ( rule__EqualityExpression__Group__0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:441:1: ( rule__EqualityExpression__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:441:2: rule__EqualityExpression__Group__0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__0_in_ruleEqualityExpression873);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:453:1: entryRuleAdditiveExpression : ruleAdditiveExpression EOF ;
    public final void entryRuleAdditiveExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:454:1: ( ruleAdditiveExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:455:1: ruleAdditiveExpression EOF
            {
             before(grammarAccess.getAdditiveExpressionRule()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression900);
            ruleAdditiveExpression();
            _fsp--;

             after(grammarAccess.getAdditiveExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAdditiveExpression907); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:462:1: ruleAdditiveExpression : ( ( rule__AdditiveExpression__Group__0 ) ) ;
    public final void ruleAdditiveExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:466:2: ( ( ( rule__AdditiveExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:467:1: ( ( rule__AdditiveExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:467:1: ( ( rule__AdditiveExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:468:1: ( rule__AdditiveExpression__Group__0 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:469:1: ( rule__AdditiveExpression__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:469:2: rule__AdditiveExpression__Group__0
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__0_in_ruleAdditiveExpression933);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:481:1: entryRuleMultiplicativeExpression : ruleMultiplicativeExpression EOF ;
    public final void entryRuleMultiplicativeExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:482:1: ( ruleMultiplicativeExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:483:1: ruleMultiplicativeExpression EOF
            {
             before(grammarAccess.getMultiplicativeExpressionRule()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression960);
            ruleMultiplicativeExpression();
            _fsp--;

             after(grammarAccess.getMultiplicativeExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplicativeExpression967); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:490:1: ruleMultiplicativeExpression : ( ( rule__MultiplicativeExpression__Group__0 ) ) ;
    public final void ruleMultiplicativeExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:494:2: ( ( ( rule__MultiplicativeExpression__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:495:1: ( ( rule__MultiplicativeExpression__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:495:1: ( ( rule__MultiplicativeExpression__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:496:1: ( rule__MultiplicativeExpression__Group__0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:497:1: ( rule__MultiplicativeExpression__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:497:2: rule__MultiplicativeExpression__Group__0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__0_in_ruleMultiplicativeExpression993);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:509:1: entryRuleUnaryExpression : ruleUnaryExpression EOF ;
    public final void entryRuleUnaryExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:510:1: ( ruleUnaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:511:1: ruleUnaryExpression EOF
            {
             before(grammarAccess.getUnaryExpressionRule()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression1020);
            ruleUnaryExpression();
            _fsp--;

             after(grammarAccess.getUnaryExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryExpression1027); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:518:1: ruleUnaryExpression : ( ( rule__UnaryExpression__Alternatives ) ) ;
    public final void ruleUnaryExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:522:2: ( ( ( rule__UnaryExpression__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:523:1: ( ( rule__UnaryExpression__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:523:1: ( ( rule__UnaryExpression__Alternatives ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:524:1: ( rule__UnaryExpression__Alternatives )
            {
             before(grammarAccess.getUnaryExpressionAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:525:1: ( rule__UnaryExpression__Alternatives )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:525:2: rule__UnaryExpression__Alternatives
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Alternatives_in_ruleUnaryExpression1053);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:537:1: entryRulePrimaryExpression : rulePrimaryExpression EOF ;
    public final void entryRulePrimaryExpression() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:538:1: ( rulePrimaryExpression EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:539:1: rulePrimaryExpression EOF
            {
             before(grammarAccess.getPrimaryExpressionRule()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression1080);
            rulePrimaryExpression();
            _fsp--;

             after(grammarAccess.getPrimaryExpressionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryExpression1087); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:546:1: rulePrimaryExpression : ( ( rule__PrimaryExpression__Alternatives ) ) ;
    public final void rulePrimaryExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:550:2: ( ( ( rule__PrimaryExpression__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:551:1: ( ( rule__PrimaryExpression__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:551:1: ( ( rule__PrimaryExpression__Alternatives ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:552:1: ( rule__PrimaryExpression__Alternatives )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:553:1: ( rule__PrimaryExpression__Alternatives )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:553:2: rule__PrimaryExpression__Alternatives
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Alternatives_in_rulePrimaryExpression1113);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:565:1: entryRuleFunctionCall : ruleFunctionCall EOF ;
    public final void entryRuleFunctionCall() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:566:1: ( ruleFunctionCall EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:567:1: ruleFunctionCall EOF
            {
             before(grammarAccess.getFunctionCallRule()); 
            pushFollow(FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall1140);
            ruleFunctionCall();
            _fsp--;

             after(grammarAccess.getFunctionCallRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFunctionCall1147); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:574:1: ruleFunctionCall : ( ( rule__FunctionCall__Group__0 ) ) ;
    public final void ruleFunctionCall() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:578:2: ( ( ( rule__FunctionCall__Group__0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:579:1: ( ( rule__FunctionCall__Group__0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:579:1: ( ( rule__FunctionCall__Group__0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:580:1: ( rule__FunctionCall__Group__0 )
            {
             before(grammarAccess.getFunctionCallAccess().getGroup()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:581:1: ( rule__FunctionCall__Group__0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:581:2: rule__FunctionCall__Group__0
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__0_in_ruleFunctionCall1173);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:595:1: entryRuleLiteral : ruleLiteral EOF ;
    public final void entryRuleLiteral() throws RecognitionException {
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:596:1: ( ruleLiteral EOF )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:597:1: ruleLiteral EOF
            {
             before(grammarAccess.getLiteralRule()); 
            pushFollow(FOLLOW_ruleLiteral_in_entryRuleLiteral1202);
            ruleLiteral();
            _fsp--;

             after(grammarAccess.getLiteralRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLiteral1209); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:604:1: ruleLiteral : ( ( rule__Literal__Alternatives ) ) ;
    public final void ruleLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:608:2: ( ( ( rule__Literal__Alternatives ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:609:1: ( ( rule__Literal__Alternatives ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:609:1: ( ( rule__Literal__Alternatives ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:610:1: ( rule__Literal__Alternatives )
            {
             before(grammarAccess.getLiteralAccess().getAlternatives()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:611:1: ( rule__Literal__Alternatives )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:611:2: rule__Literal__Alternatives
            {
            pushFollow(FOLLOW_rule__Literal__Alternatives_in_ruleLiteral1235);
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


    // $ANTLR start rule__Tml__Alternatives_3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:623:1: rule__Tml__Alternatives_3 : ( ( ( rule__Tml__Group_3_0__0 ) ) | ( '/>' ) );
    public final void rule__Tml__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:627:1: ( ( ( rule__Tml__Group_3_0__0 ) ) | ( '/>' ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==15) ) {
                alt1=1;
            }
            else if ( (LA1_0==11) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("623:1: rule__Tml__Alternatives_3 : ( ( ( rule__Tml__Group_3_0__0 ) ) | ( '/>' ) );", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:628:1: ( ( rule__Tml__Group_3_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:628:1: ( ( rule__Tml__Group_3_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:629:1: ( rule__Tml__Group_3_0__0 )
                    {
                     before(grammarAccess.getTmlAccess().getGroup_3_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:630:1: ( rule__Tml__Group_3_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:630:2: rule__Tml__Group_3_0__0
                    {
                    pushFollow(FOLLOW_rule__Tml__Group_3_0__0_in_rule__Tml__Alternatives_31271);
                    rule__Tml__Group_3_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getTmlAccess().getGroup_3_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:634:6: ( '/>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:634:6: ( '/>' )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:635:1: '/>'
                    {
                     before(grammarAccess.getTmlAccess().getSolidusGreaterThanSignKeyword_3_1()); 
                    match(input,11,FOLLOW_11_in_rule__Tml__Alternatives_31290); 
                     after(grammarAccess.getTmlAccess().getSolidusGreaterThanSignKeyword_3_1()); 

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
    // $ANTLR end rule__Tml__Alternatives_3


    // $ANTLR start rule__Tml__Alternatives_3_0_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:647:1: rule__Tml__Alternatives_3_0_1 : ( ( ( rule__Tml__ChildrenAssignment_3_0_1_0 ) ) | ( ( rule__Tml__ChildrenAssignment_3_0_1_1 ) ) );
    public final void rule__Tml__Alternatives_3_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:651:1: ( ( ( rule__Tml__ChildrenAssignment_3_0_1_0 ) ) | ( ( rule__Tml__ChildrenAssignment_3_0_1_1 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==20) ) {
                alt2=1;
            }
            else if ( (LA2_0==22) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("647:1: rule__Tml__Alternatives_3_0_1 : ( ( ( rule__Tml__ChildrenAssignment_3_0_1_0 ) ) | ( ( rule__Tml__ChildrenAssignment_3_0_1_1 ) ) );", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:652:1: ( ( rule__Tml__ChildrenAssignment_3_0_1_0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:652:1: ( ( rule__Tml__ChildrenAssignment_3_0_1_0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:653:1: ( rule__Tml__ChildrenAssignment_3_0_1_0 )
                    {
                     before(grammarAccess.getTmlAccess().getChildrenAssignment_3_0_1_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:654:1: ( rule__Tml__ChildrenAssignment_3_0_1_0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:654:2: rule__Tml__ChildrenAssignment_3_0_1_0
                    {
                    pushFollow(FOLLOW_rule__Tml__ChildrenAssignment_3_0_1_0_in_rule__Tml__Alternatives_3_0_11324);
                    rule__Tml__ChildrenAssignment_3_0_1_0();
                    _fsp--;


                    }

                     after(grammarAccess.getTmlAccess().getChildrenAssignment_3_0_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:658:6: ( ( rule__Tml__ChildrenAssignment_3_0_1_1 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:658:6: ( ( rule__Tml__ChildrenAssignment_3_0_1_1 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:659:1: ( rule__Tml__ChildrenAssignment_3_0_1_1 )
                    {
                     before(grammarAccess.getTmlAccess().getChildrenAssignment_3_0_1_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:660:1: ( rule__Tml__ChildrenAssignment_3_0_1_1 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:660:2: rule__Tml__ChildrenAssignment_3_0_1_1
                    {
                    pushFollow(FOLLOW_rule__Tml__ChildrenAssignment_3_0_1_1_in_rule__Tml__Alternatives_3_0_11342);
                    rule__Tml__ChildrenAssignment_3_0_1_1();
                    _fsp--;


                    }

                     after(grammarAccess.getTmlAccess().getChildrenAssignment_3_0_1_1()); 

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
    // $ANTLR end rule__Tml__Alternatives_3_0_1


    // $ANTLR start rule__PossibleExpression__Alternatives_2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:669:1: rule__PossibleExpression__Alternatives_2 : ( ( ( rule__PossibleExpression__Group_2_0__0 ) ) | ( ( rule__PossibleExpression__ValueAssignment_2_1 ) ) );
    public final void rule__PossibleExpression__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:673:1: ( ( ( rule__PossibleExpression__Group_2_0__0 ) ) | ( ( rule__PossibleExpression__ValueAssignment_2_1 ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==18) ) {
                alt3=1;
            }
            else if ( (LA3_0==RULE_ATTRIBUTESTRING) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("669:1: rule__PossibleExpression__Alternatives_2 : ( ( ( rule__PossibleExpression__Group_2_0__0 ) ) | ( ( rule__PossibleExpression__ValueAssignment_2_1 ) ) );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:674:1: ( ( rule__PossibleExpression__Group_2_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:674:1: ( ( rule__PossibleExpression__Group_2_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:675:1: ( rule__PossibleExpression__Group_2_0__0 )
                    {
                     before(grammarAccess.getPossibleExpressionAccess().getGroup_2_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:676:1: ( rule__PossibleExpression__Group_2_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:676:2: rule__PossibleExpression__Group_2_0__0
                    {
                    pushFollow(FOLLOW_rule__PossibleExpression__Group_2_0__0_in_rule__PossibleExpression__Alternatives_21375);
                    rule__PossibleExpression__Group_2_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getPossibleExpressionAccess().getGroup_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:680:6: ( ( rule__PossibleExpression__ValueAssignment_2_1 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:680:6: ( ( rule__PossibleExpression__ValueAssignment_2_1 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:681:1: ( rule__PossibleExpression__ValueAssignment_2_1 )
                    {
                     before(grammarAccess.getPossibleExpressionAccess().getValueAssignment_2_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:682:1: ( rule__PossibleExpression__ValueAssignment_2_1 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:682:2: rule__PossibleExpression__ValueAssignment_2_1
                    {
                    pushFollow(FOLLOW_rule__PossibleExpression__ValueAssignment_2_1_in_rule__PossibleExpression__Alternatives_21393);
                    rule__PossibleExpression__ValueAssignment_2_1();
                    _fsp--;


                    }

                     after(grammarAccess.getPossibleExpressionAccess().getValueAssignment_2_1()); 

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
    // $ANTLR end rule__PossibleExpression__Alternatives_2


    // $ANTLR start rule__Message__Alternatives_3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:691:1: rule__Message__Alternatives_3 : ( ( ( rule__Message__Group_3_0__0 ) ) | ( '/>' ) );
    public final void rule__Message__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:695:1: ( ( ( rule__Message__Group_3_0__0 ) ) | ( '/>' ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==15) ) {
                alt4=1;
            }
            else if ( (LA4_0==11) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("691:1: rule__Message__Alternatives_3 : ( ( ( rule__Message__Group_3_0__0 ) ) | ( '/>' ) );", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:696:1: ( ( rule__Message__Group_3_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:696:1: ( ( rule__Message__Group_3_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:697:1: ( rule__Message__Group_3_0__0 )
                    {
                     before(grammarAccess.getMessageAccess().getGroup_3_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:698:1: ( rule__Message__Group_3_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:698:2: rule__Message__Group_3_0__0
                    {
                    pushFollow(FOLLOW_rule__Message__Group_3_0__0_in_rule__Message__Alternatives_31426);
                    rule__Message__Group_3_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getMessageAccess().getGroup_3_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:702:6: ( '/>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:702:6: ( '/>' )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:703:1: '/>'
                    {
                     before(grammarAccess.getMessageAccess().getSolidusGreaterThanSignKeyword_3_1()); 
                    match(input,11,FOLLOW_11_in_rule__Message__Alternatives_31445); 
                     after(grammarAccess.getMessageAccess().getSolidusGreaterThanSignKeyword_3_1()); 

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
    // $ANTLR end rule__Message__Alternatives_3


    // $ANTLR start rule__Message__Alternatives_3_0_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:715:1: rule__Message__Alternatives_3_0_1 : ( ( ( rule__Message__ChildrenAssignment_3_0_1_0 ) ) | ( ( rule__Message__ChildrenAssignment_3_0_1_1 ) ) | ( ( rule__Message__ChildrenAssignment_3_0_1_2 ) ) );
    public final void rule__Message__Alternatives_3_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:719:1: ( ( ( rule__Message__ChildrenAssignment_3_0_1_0 ) ) | ( ( rule__Message__ChildrenAssignment_3_0_1_1 ) ) | ( ( rule__Message__ChildrenAssignment_3_0_1_2 ) ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt5=1;
                }
                break;
            case 24:
                {
                alt5=2;
                }
                break;
            case 22:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("715:1: rule__Message__Alternatives_3_0_1 : ( ( ( rule__Message__ChildrenAssignment_3_0_1_0 ) ) | ( ( rule__Message__ChildrenAssignment_3_0_1_1 ) ) | ( ( rule__Message__ChildrenAssignment_3_0_1_2 ) ) );", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:720:1: ( ( rule__Message__ChildrenAssignment_3_0_1_0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:720:1: ( ( rule__Message__ChildrenAssignment_3_0_1_0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:721:1: ( rule__Message__ChildrenAssignment_3_0_1_0 )
                    {
                     before(grammarAccess.getMessageAccess().getChildrenAssignment_3_0_1_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:722:1: ( rule__Message__ChildrenAssignment_3_0_1_0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:722:2: rule__Message__ChildrenAssignment_3_0_1_0
                    {
                    pushFollow(FOLLOW_rule__Message__ChildrenAssignment_3_0_1_0_in_rule__Message__Alternatives_3_0_11479);
                    rule__Message__ChildrenAssignment_3_0_1_0();
                    _fsp--;


                    }

                     after(grammarAccess.getMessageAccess().getChildrenAssignment_3_0_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:726:6: ( ( rule__Message__ChildrenAssignment_3_0_1_1 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:726:6: ( ( rule__Message__ChildrenAssignment_3_0_1_1 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:727:1: ( rule__Message__ChildrenAssignment_3_0_1_1 )
                    {
                     before(grammarAccess.getMessageAccess().getChildrenAssignment_3_0_1_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:728:1: ( rule__Message__ChildrenAssignment_3_0_1_1 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:728:2: rule__Message__ChildrenAssignment_3_0_1_1
                    {
                    pushFollow(FOLLOW_rule__Message__ChildrenAssignment_3_0_1_1_in_rule__Message__Alternatives_3_0_11497);
                    rule__Message__ChildrenAssignment_3_0_1_1();
                    _fsp--;


                    }

                     after(grammarAccess.getMessageAccess().getChildrenAssignment_3_0_1_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:732:6: ( ( rule__Message__ChildrenAssignment_3_0_1_2 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:732:6: ( ( rule__Message__ChildrenAssignment_3_0_1_2 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:733:1: ( rule__Message__ChildrenAssignment_3_0_1_2 )
                    {
                     before(grammarAccess.getMessageAccess().getChildrenAssignment_3_0_1_2()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:734:1: ( rule__Message__ChildrenAssignment_3_0_1_2 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:734:2: rule__Message__ChildrenAssignment_3_0_1_2
                    {
                    pushFollow(FOLLOW_rule__Message__ChildrenAssignment_3_0_1_2_in_rule__Message__Alternatives_3_0_11515);
                    rule__Message__ChildrenAssignment_3_0_1_2();
                    _fsp--;


                    }

                     after(grammarAccess.getMessageAccess().getChildrenAssignment_3_0_1_2()); 

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
    // $ANTLR end rule__Message__Alternatives_3_0_1


    // $ANTLR start rule__Map__Alternatives_4
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:743:1: rule__Map__Alternatives_4 : ( ( '/>' ) | ( ( rule__Map__Group_4_1__0 ) ) );
    public final void rule__Map__Alternatives_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:747:1: ( ( '/>' ) | ( ( rule__Map__Group_4_1__0 ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==11) ) {
                alt6=1;
            }
            else if ( (LA6_0==15) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("743:1: rule__Map__Alternatives_4 : ( ( '/>' ) | ( ( rule__Map__Group_4_1__0 ) ) );", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:748:1: ( '/>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:748:1: ( '/>' )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:749:1: '/>'
                    {
                     before(grammarAccess.getMapAccess().getSolidusGreaterThanSignKeyword_4_0()); 
                    match(input,11,FOLLOW_11_in_rule__Map__Alternatives_41549); 
                     after(grammarAccess.getMapAccess().getSolidusGreaterThanSignKeyword_4_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:756:6: ( ( rule__Map__Group_4_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:756:6: ( ( rule__Map__Group_4_1__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:757:1: ( rule__Map__Group_4_1__0 )
                    {
                     before(grammarAccess.getMapAccess().getGroup_4_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:758:1: ( rule__Map__Group_4_1__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:758:2: rule__Map__Group_4_1__0
                    {
                    pushFollow(FOLLOW_rule__Map__Group_4_1__0_in_rule__Map__Alternatives_41568);
                    rule__Map__Group_4_1__0();
                    _fsp--;


                    }

                     after(grammarAccess.getMapAccess().getGroup_4_1()); 

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
    // $ANTLR end rule__Map__Alternatives_4


    // $ANTLR start rule__Map__Alternatives_4_1_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:767:1: rule__Map__Alternatives_4_1_1 : ( ( ( rule__Map__ChildrenAssignment_4_1_1_0 ) ) | ( ( rule__Map__ChildrenAssignment_4_1_1_1 ) ) | ( ( rule__Map__ChildrenAssignment_4_1_1_2 ) ) );
    public final void rule__Map__Alternatives_4_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:771:1: ( ( ( rule__Map__ChildrenAssignment_4_1_1_0 ) ) | ( ( rule__Map__ChildrenAssignment_4_1_1_1 ) ) | ( ( rule__Map__ChildrenAssignment_4_1_1_2 ) ) )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt7=1;
                }
                break;
            case 24:
                {
                alt7=2;
                }
                break;
            case 22:
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("767:1: rule__Map__Alternatives_4_1_1 : ( ( ( rule__Map__ChildrenAssignment_4_1_1_0 ) ) | ( ( rule__Map__ChildrenAssignment_4_1_1_1 ) ) | ( ( rule__Map__ChildrenAssignment_4_1_1_2 ) ) );", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:772:1: ( ( rule__Map__ChildrenAssignment_4_1_1_0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:772:1: ( ( rule__Map__ChildrenAssignment_4_1_1_0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:773:1: ( rule__Map__ChildrenAssignment_4_1_1_0 )
                    {
                     before(grammarAccess.getMapAccess().getChildrenAssignment_4_1_1_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:774:1: ( rule__Map__ChildrenAssignment_4_1_1_0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:774:2: rule__Map__ChildrenAssignment_4_1_1_0
                    {
                    pushFollow(FOLLOW_rule__Map__ChildrenAssignment_4_1_1_0_in_rule__Map__Alternatives_4_1_11601);
                    rule__Map__ChildrenAssignment_4_1_1_0();
                    _fsp--;


                    }

                     after(grammarAccess.getMapAccess().getChildrenAssignment_4_1_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:778:6: ( ( rule__Map__ChildrenAssignment_4_1_1_1 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:778:6: ( ( rule__Map__ChildrenAssignment_4_1_1_1 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:779:1: ( rule__Map__ChildrenAssignment_4_1_1_1 )
                    {
                     before(grammarAccess.getMapAccess().getChildrenAssignment_4_1_1_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:780:1: ( rule__Map__ChildrenAssignment_4_1_1_1 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:780:2: rule__Map__ChildrenAssignment_4_1_1_1
                    {
                    pushFollow(FOLLOW_rule__Map__ChildrenAssignment_4_1_1_1_in_rule__Map__Alternatives_4_1_11619);
                    rule__Map__ChildrenAssignment_4_1_1_1();
                    _fsp--;


                    }

                     after(grammarAccess.getMapAccess().getChildrenAssignment_4_1_1_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:784:6: ( ( rule__Map__ChildrenAssignment_4_1_1_2 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:784:6: ( ( rule__Map__ChildrenAssignment_4_1_1_2 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:785:1: ( rule__Map__ChildrenAssignment_4_1_1_2 )
                    {
                     before(grammarAccess.getMapAccess().getChildrenAssignment_4_1_1_2()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:786:1: ( rule__Map__ChildrenAssignment_4_1_1_2 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:786:2: rule__Map__ChildrenAssignment_4_1_1_2
                    {
                    pushFollow(FOLLOW_rule__Map__ChildrenAssignment_4_1_1_2_in_rule__Map__Alternatives_4_1_11637);
                    rule__Map__ChildrenAssignment_4_1_1_2();
                    _fsp--;


                    }

                     after(grammarAccess.getMapAccess().getChildrenAssignment_4_1_1_2()); 

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
    // $ANTLR end rule__Map__Alternatives_4_1_1


    // $ANTLR start rule__Property__Alternatives_3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:795:1: rule__Property__Alternatives_3 : ( ( '/>' ) | ( ( rule__Property__Group_3_1__0 ) ) );
    public final void rule__Property__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:799:1: ( ( '/>' ) | ( ( rule__Property__Group_3_1__0 ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==11) ) {
                alt8=1;
            }
            else if ( (LA8_0==15) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("795:1: rule__Property__Alternatives_3 : ( ( '/>' ) | ( ( rule__Property__Group_3_1__0 ) ) );", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:800:1: ( '/>' )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:800:1: ( '/>' )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:801:1: '/>'
                    {
                     before(grammarAccess.getPropertyAccess().getSolidusGreaterThanSignKeyword_3_0()); 
                    match(input,11,FOLLOW_11_in_rule__Property__Alternatives_31671); 
                     after(grammarAccess.getPropertyAccess().getSolidusGreaterThanSignKeyword_3_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:808:6: ( ( rule__Property__Group_3_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:808:6: ( ( rule__Property__Group_3_1__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:809:1: ( rule__Property__Group_3_1__0 )
                    {
                     before(grammarAccess.getPropertyAccess().getGroup_3_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:810:1: ( rule__Property__Group_3_1__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:810:2: rule__Property__Group_3_1__0
                    {
                    pushFollow(FOLLOW_rule__Property__Group_3_1__0_in_rule__Property__Alternatives_31690);
                    rule__Property__Group_3_1__0();
                    _fsp--;


                    }

                     after(grammarAccess.getPropertyAccess().getGroup_3_1()); 

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
    // $ANTLR end rule__Property__Alternatives_3


    // $ANTLR start rule__PathElement__Alternatives
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:819:1: rule__PathElement__Alternatives : ( ( RULE_ID ) | ( '.' ) | ( '..' ) );
    public final void rule__PathElement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:823:1: ( ( RULE_ID ) | ( '.' ) | ( '..' ) )
            int alt9=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt9=1;
                }
                break;
            case 12:
                {
                alt9=2;
                }
                break;
            case 13:
                {
                alt9=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("819:1: rule__PathElement__Alternatives : ( ( RULE_ID ) | ( '.' ) | ( '..' ) );", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:824:1: ( RULE_ID )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:824:1: ( RULE_ID )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:825:1: RULE_ID
                    {
                     before(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PathElement__Alternatives1723); 
                     after(grammarAccess.getPathElementAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:830:6: ( '.' )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:830:6: ( '.' )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:831:1: '.'
                    {
                     before(grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 
                    match(input,12,FOLLOW_12_in_rule__PathElement__Alternatives1741); 
                     after(grammarAccess.getPathElementAccess().getFullStopKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:838:6: ( '..' )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:838:6: ( '..' )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:839:1: '..'
                    {
                     before(grammarAccess.getPathElementAccess().getFullStopFullStopKeyword_2()); 
                    match(input,13,FOLLOW_13_in_rule__PathElement__Alternatives1761); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:851:1: rule__EqualityExpression__Alternatives_2 : ( ( ( rule__EqualityExpression__Group_2_0__0 ) ) | ( ( rule__EqualityExpression__Group_2_1__0 ) ) );
    public final void rule__EqualityExpression__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:855:1: ( ( ( rule__EqualityExpression__Group_2_0__0 ) ) | ( ( rule__EqualityExpression__Group_2_1__0 ) ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==40) ) {
                alt10=1;
            }
            else if ( (LA10_0==41) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("851:1: rule__EqualityExpression__Alternatives_2 : ( ( ( rule__EqualityExpression__Group_2_0__0 ) ) | ( ( rule__EqualityExpression__Group_2_1__0 ) ) );", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:856:1: ( ( rule__EqualityExpression__Group_2_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:856:1: ( ( rule__EqualityExpression__Group_2_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:857:1: ( rule__EqualityExpression__Group_2_0__0 )
                    {
                     before(grammarAccess.getEqualityExpressionAccess().getGroup_2_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:858:1: ( rule__EqualityExpression__Group_2_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:858:2: rule__EqualityExpression__Group_2_0__0
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Group_2_0__0_in_rule__EqualityExpression__Alternatives_21795);
                    rule__EqualityExpression__Group_2_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getEqualityExpressionAccess().getGroup_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:862:6: ( ( rule__EqualityExpression__Group_2_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:862:6: ( ( rule__EqualityExpression__Group_2_1__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:863:1: ( rule__EqualityExpression__Group_2_1__0 )
                    {
                     before(grammarAccess.getEqualityExpressionAccess().getGroup_2_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:864:1: ( rule__EqualityExpression__Group_2_1__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:864:2: rule__EqualityExpression__Group_2_1__0
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Group_2_1__0_in_rule__EqualityExpression__Alternatives_21813);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:873:1: rule__AdditiveExpression__Alternatives_2 : ( ( ( rule__AdditiveExpression__Group_2_0__0 ) ) | ( ( rule__AdditiveExpression__Group_2_1__0 ) ) );
    public final void rule__AdditiveExpression__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:877:1: ( ( ( rule__AdditiveExpression__Group_2_0__0 ) ) | ( ( rule__AdditiveExpression__Group_2_1__0 ) ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==32) ) {
                alt11=1;
            }
            else if ( (LA11_0==33) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("873:1: rule__AdditiveExpression__Alternatives_2 : ( ( ( rule__AdditiveExpression__Group_2_0__0 ) ) | ( ( rule__AdditiveExpression__Group_2_1__0 ) ) );", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:878:1: ( ( rule__AdditiveExpression__Group_2_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:878:1: ( ( rule__AdditiveExpression__Group_2_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:879:1: ( rule__AdditiveExpression__Group_2_0__0 )
                    {
                     before(grammarAccess.getAdditiveExpressionAccess().getGroup_2_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:880:1: ( rule__AdditiveExpression__Group_2_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:880:2: rule__AdditiveExpression__Group_2_0__0
                    {
                    pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_0__0_in_rule__AdditiveExpression__Alternatives_21846);
                    rule__AdditiveExpression__Group_2_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getAdditiveExpressionAccess().getGroup_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:884:6: ( ( rule__AdditiveExpression__Group_2_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:884:6: ( ( rule__AdditiveExpression__Group_2_1__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:885:1: ( rule__AdditiveExpression__Group_2_1__0 )
                    {
                     before(grammarAccess.getAdditiveExpressionAccess().getGroup_2_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:886:1: ( rule__AdditiveExpression__Group_2_1__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:886:2: rule__AdditiveExpression__Group_2_1__0
                    {
                    pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_1__0_in_rule__AdditiveExpression__Alternatives_21864);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:895:1: rule__MultiplicativeExpression__Alternatives_2 : ( ( ( rule__MultiplicativeExpression__Group_2_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_2_1__0 ) ) );
    public final void rule__MultiplicativeExpression__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:899:1: ( ( ( rule__MultiplicativeExpression__Group_2_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_2_1__0 ) ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==42) ) {
                alt12=1;
            }
            else if ( (LA12_0==29) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("895:1: rule__MultiplicativeExpression__Alternatives_2 : ( ( ( rule__MultiplicativeExpression__Group_2_0__0 ) ) | ( ( rule__MultiplicativeExpression__Group_2_1__0 ) ) );", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:900:1: ( ( rule__MultiplicativeExpression__Group_2_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:900:1: ( ( rule__MultiplicativeExpression__Group_2_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:901:1: ( rule__MultiplicativeExpression__Group_2_0__0 )
                    {
                     before(grammarAccess.getMultiplicativeExpressionAccess().getGroup_2_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:902:1: ( rule__MultiplicativeExpression__Group_2_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:902:2: rule__MultiplicativeExpression__Group_2_0__0
                    {
                    pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_0__0_in_rule__MultiplicativeExpression__Alternatives_21897);
                    rule__MultiplicativeExpression__Group_2_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getMultiplicativeExpressionAccess().getGroup_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:906:6: ( ( rule__MultiplicativeExpression__Group_2_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:906:6: ( ( rule__MultiplicativeExpression__Group_2_1__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:907:1: ( rule__MultiplicativeExpression__Group_2_1__0 )
                    {
                     before(grammarAccess.getMultiplicativeExpressionAccess().getGroup_2_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:908:1: ( rule__MultiplicativeExpression__Group_2_1__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:908:2: rule__MultiplicativeExpression__Group_2_1__0
                    {
                    pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_1__0_in_rule__MultiplicativeExpression__Alternatives_21915);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:917:1: rule__UnaryExpression__Alternatives : ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) );
    public final void rule__UnaryExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:921:1: ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==43) ) {
                alt13=1;
            }
            else if ( (LA13_0==RULE_ID||(LA13_0>=RULE_INT && LA13_0<=RULE_LITERALSTRING)||LA13_0==28||LA13_0==31||LA13_0==34||(LA13_0>=44 && LA13_0<=49)) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("917:1: rule__UnaryExpression__Alternatives : ( ( ( rule__UnaryExpression__Group_0__0 ) ) | ( rulePrimaryExpression ) );", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:922:1: ( ( rule__UnaryExpression__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:922:1: ( ( rule__UnaryExpression__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:923:1: ( rule__UnaryExpression__Group_0__0 )
                    {
                     before(grammarAccess.getUnaryExpressionAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:924:1: ( rule__UnaryExpression__Group_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:924:2: rule__UnaryExpression__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__UnaryExpression__Group_0__0_in_rule__UnaryExpression__Alternatives1948);
                    rule__UnaryExpression__Group_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getUnaryExpressionAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:928:6: ( rulePrimaryExpression )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:928:6: ( rulePrimaryExpression )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:929:1: rulePrimaryExpression
                    {
                     before(grammarAccess.getUnaryExpressionAccess().getPrimaryExpressionParserRuleCall_1()); 
                    pushFollow(FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__Alternatives1966);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:939:1: rule__PrimaryExpression__Alternatives : ( ( ( rule__PrimaryExpression__Group_0__0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) );
    public final void rule__PrimaryExpression__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:943:1: ( ( ( rule__PrimaryExpression__Group_0__0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_ID||(LA14_0>=RULE_INT && LA14_0<=RULE_LITERALSTRING)||LA14_0==28||LA14_0==31||(LA14_0>=44 && LA14_0<=49)) ) {
                alt14=1;
            }
            else if ( (LA14_0==34) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("939:1: rule__PrimaryExpression__Alternatives : ( ( ( rule__PrimaryExpression__Group_0__0 ) ) | ( ( rule__PrimaryExpression__Group_1__0 ) ) );", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:944:1: ( ( rule__PrimaryExpression__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:944:1: ( ( rule__PrimaryExpression__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:945:1: ( rule__PrimaryExpression__Group_0__0 )
                    {
                     before(grammarAccess.getPrimaryExpressionAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:946:1: ( rule__PrimaryExpression__Group_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:946:2: rule__PrimaryExpression__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__PrimaryExpression__Group_0__0_in_rule__PrimaryExpression__Alternatives1998);
                    rule__PrimaryExpression__Group_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getPrimaryExpressionAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:950:6: ( ( rule__PrimaryExpression__Group_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:950:6: ( ( rule__PrimaryExpression__Group_1__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:951:1: ( rule__PrimaryExpression__Group_1__0 )
                    {
                     before(grammarAccess.getPrimaryExpressionAccess().getGroup_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:952:1: ( rule__PrimaryExpression__Group_1__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:952:2: rule__PrimaryExpression__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__0_in_rule__PrimaryExpression__Alternatives2016);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:961:1: rule__Literal__Alternatives : ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__Group_5__0 ) ) | ( ( rule__Literal__Group_6__0 ) ) | ( ( rule__Literal__Group_7__0 ) ) | ( ( rule__Literal__Group_8__0 ) ) | ( ( rule__Literal__Group_9__0 ) ) | ( ( rule__Literal__Group_10__0 ) ) );
    public final void rule__Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:965:1: ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__Group_5__0 ) ) | ( ( rule__Literal__Group_6__0 ) ) | ( ( rule__Literal__Group_7__0 ) ) | ( ( rule__Literal__Group_8__0 ) ) | ( ( rule__Literal__Group_9__0 ) ) | ( ( rule__Literal__Group_10__0 ) ) )
            int alt15=11;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt15=1;
                }
                break;
            case RULE_LITERALSTRING:
                {
                alt15=2;
                }
                break;
            case 44:
                {
                alt15=3;
                }
                break;
            case RULE_ID:
                {
                alt15=4;
                }
                break;
            case 31:
                {
                alt15=5;
                }
                break;
            case 28:
                {
                alt15=6;
                }
                break;
            case 45:
                {
                alt15=7;
                }
                break;
            case 46:
                {
                alt15=8;
                }
                break;
            case 47:
                {
                alt15=9;
                }
                break;
            case 48:
                {
                alt15=10;
                }
                break;
            case 49:
                {
                alt15=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("961:1: rule__Literal__Alternatives : ( ( ( rule__Literal__Group_0__0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__Group_2__0 ) ) | ( ruleFunctionCall ) | ( ( rule__Literal__Group_4__0 ) ) | ( ( rule__Literal__Group_5__0 ) ) | ( ( rule__Literal__Group_6__0 ) ) | ( ( rule__Literal__Group_7__0 ) ) | ( ( rule__Literal__Group_8__0 ) ) | ( ( rule__Literal__Group_9__0 ) ) | ( ( rule__Literal__Group_10__0 ) ) );", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:966:1: ( ( rule__Literal__Group_0__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:966:1: ( ( rule__Literal__Group_0__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:967:1: ( rule__Literal__Group_0__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_0()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:968:1: ( rule__Literal__Group_0__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:968:2: rule__Literal__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_0__0_in_rule__Literal__Alternatives2049);
                    rule__Literal__Group_0__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:972:6: ( ( rule__Literal__Group_1__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:972:6: ( ( rule__Literal__Group_1__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:973:1: ( rule__Literal__Group_1__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_1()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:974:1: ( rule__Literal__Group_1__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:974:2: rule__Literal__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_1__0_in_rule__Literal__Alternatives2067);
                    rule__Literal__Group_1__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:978:6: ( ( rule__Literal__Group_2__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:978:6: ( ( rule__Literal__Group_2__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:979:1: ( rule__Literal__Group_2__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_2()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:980:1: ( rule__Literal__Group_2__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:980:2: rule__Literal__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_2__0_in_rule__Literal__Alternatives2085);
                    rule__Literal__Group_2__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:984:6: ( ruleFunctionCall )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:984:6: ( ruleFunctionCall )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:985:1: ruleFunctionCall
                    {
                     before(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3()); 
                    pushFollow(FOLLOW_ruleFunctionCall_in_rule__Literal__Alternatives2103);
                    ruleFunctionCall();
                    _fsp--;

                     after(grammarAccess.getLiteralAccess().getFunctionCallParserRuleCall_3()); 

                    }


                    }
                    break;
                case 5 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:990:6: ( ( rule__Literal__Group_4__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:990:6: ( ( rule__Literal__Group_4__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:991:1: ( rule__Literal__Group_4__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_4()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:992:1: ( rule__Literal__Group_4__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:992:2: rule__Literal__Group_4__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_4__0_in_rule__Literal__Alternatives2120);
                    rule__Literal__Group_4__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_4()); 

                    }


                    }
                    break;
                case 6 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:996:6: ( ( rule__Literal__Group_5__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:996:6: ( ( rule__Literal__Group_5__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:997:1: ( rule__Literal__Group_5__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_5()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:998:1: ( rule__Literal__Group_5__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:998:2: rule__Literal__Group_5__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_5__0_in_rule__Literal__Alternatives2138);
                    rule__Literal__Group_5__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_5()); 

                    }


                    }
                    break;
                case 7 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1002:6: ( ( rule__Literal__Group_6__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1002:6: ( ( rule__Literal__Group_6__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1003:1: ( rule__Literal__Group_6__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_6()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1004:1: ( rule__Literal__Group_6__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1004:2: rule__Literal__Group_6__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_6__0_in_rule__Literal__Alternatives2156);
                    rule__Literal__Group_6__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_6()); 

                    }


                    }
                    break;
                case 8 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1008:6: ( ( rule__Literal__Group_7__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1008:6: ( ( rule__Literal__Group_7__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1009:1: ( rule__Literal__Group_7__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_7()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1010:1: ( rule__Literal__Group_7__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1010:2: rule__Literal__Group_7__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_7__0_in_rule__Literal__Alternatives2174);
                    rule__Literal__Group_7__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_7()); 

                    }


                    }
                    break;
                case 9 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1014:6: ( ( rule__Literal__Group_8__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1014:6: ( ( rule__Literal__Group_8__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1015:1: ( rule__Literal__Group_8__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_8()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1016:1: ( rule__Literal__Group_8__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1016:2: rule__Literal__Group_8__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_8__0_in_rule__Literal__Alternatives2192);
                    rule__Literal__Group_8__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_8()); 

                    }


                    }
                    break;
                case 10 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1020:6: ( ( rule__Literal__Group_9__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1020:6: ( ( rule__Literal__Group_9__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1021:1: ( rule__Literal__Group_9__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_9()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1022:1: ( rule__Literal__Group_9__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1022:2: rule__Literal__Group_9__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_9__0_in_rule__Literal__Alternatives2210);
                    rule__Literal__Group_9__0();
                    _fsp--;


                    }

                     after(grammarAccess.getLiteralAccess().getGroup_9()); 

                    }


                    }
                    break;
                case 11 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1026:6: ( ( rule__Literal__Group_10__0 ) )
                    {
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1026:6: ( ( rule__Literal__Group_10__0 ) )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1027:1: ( rule__Literal__Group_10__0 )
                    {
                     before(grammarAccess.getLiteralAccess().getGroup_10()); 
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1028:1: ( rule__Literal__Group_10__0 )
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1028:2: rule__Literal__Group_10__0
                    {
                    pushFollow(FOLLOW_rule__Literal__Group_10__0_in_rule__Literal__Alternatives2228);
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


    // $ANTLR start rule__Tml__Group__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1039:1: rule__Tml__Group__0 : rule__Tml__Group__0__Impl rule__Tml__Group__1 ;
    public final void rule__Tml__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1043:1: ( rule__Tml__Group__0__Impl rule__Tml__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1044:2: rule__Tml__Group__0__Impl rule__Tml__Group__1
            {
            pushFollow(FOLLOW_rule__Tml__Group__0__Impl_in_rule__Tml__Group__02259);
            rule__Tml__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Tml__Group__1_in_rule__Tml__Group__02262);
            rule__Tml__Group__1();
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
    // $ANTLR end rule__Tml__Group__0


    // $ANTLR start rule__Tml__Group__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1051:1: rule__Tml__Group__0__Impl : ( '<navascript' ) ;
    public final void rule__Tml__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1055:1: ( ( '<navascript' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1056:1: ( '<navascript' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1056:1: ( '<navascript' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1057:1: '<navascript'
            {
             before(grammarAccess.getTmlAccess().getNavascriptKeyword_0()); 
            match(input,14,FOLLOW_14_in_rule__Tml__Group__0__Impl2290); 
             after(grammarAccess.getTmlAccess().getNavascriptKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__Group__0__Impl


    // $ANTLR start rule__Tml__Group__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1070:1: rule__Tml__Group__1 : rule__Tml__Group__1__Impl rule__Tml__Group__2 ;
    public final void rule__Tml__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1074:1: ( rule__Tml__Group__1__Impl rule__Tml__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1075:2: rule__Tml__Group__1__Impl rule__Tml__Group__2
            {
            pushFollow(FOLLOW_rule__Tml__Group__1__Impl_in_rule__Tml__Group__12321);
            rule__Tml__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Tml__Group__2_in_rule__Tml__Group__12324);
            rule__Tml__Group__2();
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
    // $ANTLR end rule__Tml__Group__1


    // $ANTLR start rule__Tml__Group__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1082:1: rule__Tml__Group__1__Impl : ( () ) ;
    public final void rule__Tml__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1086:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1087:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1087:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1088:1: ()
            {
             before(grammarAccess.getTmlAccess().getTmlAction_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1089:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1091:1: 
            {
            }

             after(grammarAccess.getTmlAccess().getTmlAction_1()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__Group__1__Impl


    // $ANTLR start rule__Tml__Group__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1101:1: rule__Tml__Group__2 : rule__Tml__Group__2__Impl rule__Tml__Group__3 ;
    public final void rule__Tml__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1105:1: ( rule__Tml__Group__2__Impl rule__Tml__Group__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1106:2: rule__Tml__Group__2__Impl rule__Tml__Group__3
            {
            pushFollow(FOLLOW_rule__Tml__Group__2__Impl_in_rule__Tml__Group__22382);
            rule__Tml__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Tml__Group__3_in_rule__Tml__Group__22385);
            rule__Tml__Group__3();
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
    // $ANTLR end rule__Tml__Group__2


    // $ANTLR start rule__Tml__Group__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1113:1: rule__Tml__Group__2__Impl : ( ( rule__Tml__AttributesAssignment_2 )* ) ;
    public final void rule__Tml__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1117:1: ( ( ( rule__Tml__AttributesAssignment_2 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1118:1: ( ( rule__Tml__AttributesAssignment_2 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1118:1: ( ( rule__Tml__AttributesAssignment_2 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1119:1: ( rule__Tml__AttributesAssignment_2 )*
            {
             before(grammarAccess.getTmlAccess().getAttributesAssignment_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1120:1: ( rule__Tml__AttributesAssignment_2 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_ID) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1120:2: rule__Tml__AttributesAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__Tml__AttributesAssignment_2_in_rule__Tml__Group__2__Impl2412);
            	    rule__Tml__AttributesAssignment_2();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

             after(grammarAccess.getTmlAccess().getAttributesAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__Group__2__Impl


    // $ANTLR start rule__Tml__Group__3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1130:1: rule__Tml__Group__3 : rule__Tml__Group__3__Impl ;
    public final void rule__Tml__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1134:1: ( rule__Tml__Group__3__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1135:2: rule__Tml__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Tml__Group__3__Impl_in_rule__Tml__Group__32443);
            rule__Tml__Group__3__Impl();
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
    // $ANTLR end rule__Tml__Group__3


    // $ANTLR start rule__Tml__Group__3__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1141:1: rule__Tml__Group__3__Impl : ( ( rule__Tml__Alternatives_3 ) ) ;
    public final void rule__Tml__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1145:1: ( ( ( rule__Tml__Alternatives_3 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1146:1: ( ( rule__Tml__Alternatives_3 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1146:1: ( ( rule__Tml__Alternatives_3 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1147:1: ( rule__Tml__Alternatives_3 )
            {
             before(grammarAccess.getTmlAccess().getAlternatives_3()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1148:1: ( rule__Tml__Alternatives_3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1148:2: rule__Tml__Alternatives_3
            {
            pushFollow(FOLLOW_rule__Tml__Alternatives_3_in_rule__Tml__Group__3__Impl2470);
            rule__Tml__Alternatives_3();
            _fsp--;


            }

             after(grammarAccess.getTmlAccess().getAlternatives_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__Group__3__Impl


    // $ANTLR start rule__Tml__Group_3_0__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1166:1: rule__Tml__Group_3_0__0 : rule__Tml__Group_3_0__0__Impl rule__Tml__Group_3_0__1 ;
    public final void rule__Tml__Group_3_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1170:1: ( rule__Tml__Group_3_0__0__Impl rule__Tml__Group_3_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1171:2: rule__Tml__Group_3_0__0__Impl rule__Tml__Group_3_0__1
            {
            pushFollow(FOLLOW_rule__Tml__Group_3_0__0__Impl_in_rule__Tml__Group_3_0__02508);
            rule__Tml__Group_3_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Tml__Group_3_0__1_in_rule__Tml__Group_3_0__02511);
            rule__Tml__Group_3_0__1();
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
    // $ANTLR end rule__Tml__Group_3_0__0


    // $ANTLR start rule__Tml__Group_3_0__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1178:1: rule__Tml__Group_3_0__0__Impl : ( '>' ) ;
    public final void rule__Tml__Group_3_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1182:1: ( ( '>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1183:1: ( '>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1183:1: ( '>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1184:1: '>'
            {
             before(grammarAccess.getTmlAccess().getGreaterThanSignKeyword_3_0_0()); 
            match(input,15,FOLLOW_15_in_rule__Tml__Group_3_0__0__Impl2539); 
             after(grammarAccess.getTmlAccess().getGreaterThanSignKeyword_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__Group_3_0__0__Impl


    // $ANTLR start rule__Tml__Group_3_0__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1197:1: rule__Tml__Group_3_0__1 : rule__Tml__Group_3_0__1__Impl rule__Tml__Group_3_0__2 ;
    public final void rule__Tml__Group_3_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1201:1: ( rule__Tml__Group_3_0__1__Impl rule__Tml__Group_3_0__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1202:2: rule__Tml__Group_3_0__1__Impl rule__Tml__Group_3_0__2
            {
            pushFollow(FOLLOW_rule__Tml__Group_3_0__1__Impl_in_rule__Tml__Group_3_0__12570);
            rule__Tml__Group_3_0__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Tml__Group_3_0__2_in_rule__Tml__Group_3_0__12573);
            rule__Tml__Group_3_0__2();
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
    // $ANTLR end rule__Tml__Group_3_0__1


    // $ANTLR start rule__Tml__Group_3_0__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1209:1: rule__Tml__Group_3_0__1__Impl : ( ( rule__Tml__Alternatives_3_0_1 )* ) ;
    public final void rule__Tml__Group_3_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1213:1: ( ( ( rule__Tml__Alternatives_3_0_1 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1214:1: ( ( rule__Tml__Alternatives_3_0_1 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1214:1: ( ( rule__Tml__Alternatives_3_0_1 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1215:1: ( rule__Tml__Alternatives_3_0_1 )*
            {
             before(grammarAccess.getTmlAccess().getAlternatives_3_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1216:1: ( rule__Tml__Alternatives_3_0_1 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==20||LA17_0==22) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1216:2: rule__Tml__Alternatives_3_0_1
            	    {
            	    pushFollow(FOLLOW_rule__Tml__Alternatives_3_0_1_in_rule__Tml__Group_3_0__1__Impl2600);
            	    rule__Tml__Alternatives_3_0_1();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

             after(grammarAccess.getTmlAccess().getAlternatives_3_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__Group_3_0__1__Impl


    // $ANTLR start rule__Tml__Group_3_0__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1226:1: rule__Tml__Group_3_0__2 : rule__Tml__Group_3_0__2__Impl ;
    public final void rule__Tml__Group_3_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1230:1: ( rule__Tml__Group_3_0__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1231:2: rule__Tml__Group_3_0__2__Impl
            {
            pushFollow(FOLLOW_rule__Tml__Group_3_0__2__Impl_in_rule__Tml__Group_3_0__22631);
            rule__Tml__Group_3_0__2__Impl();
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
    // $ANTLR end rule__Tml__Group_3_0__2


    // $ANTLR start rule__Tml__Group_3_0__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1237:1: rule__Tml__Group_3_0__2__Impl : ( '</navascript>' ) ;
    public final void rule__Tml__Group_3_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1241:1: ( ( '</navascript>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1242:1: ( '</navascript>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1242:1: ( '</navascript>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1243:1: '</navascript>'
            {
             before(grammarAccess.getTmlAccess().getNavascriptKeyword_3_0_2()); 
            match(input,16,FOLLOW_16_in_rule__Tml__Group_3_0__2__Impl2659); 
             after(grammarAccess.getTmlAccess().getNavascriptKeyword_3_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__Group_3_0__2__Impl


    // $ANTLR start rule__PossibleExpression__Group__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1262:1: rule__PossibleExpression__Group__0 : rule__PossibleExpression__Group__0__Impl rule__PossibleExpression__Group__1 ;
    public final void rule__PossibleExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1266:1: ( rule__PossibleExpression__Group__0__Impl rule__PossibleExpression__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1267:2: rule__PossibleExpression__Group__0__Impl rule__PossibleExpression__Group__1
            {
            pushFollow(FOLLOW_rule__PossibleExpression__Group__0__Impl_in_rule__PossibleExpression__Group__02696);
            rule__PossibleExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PossibleExpression__Group__1_in_rule__PossibleExpression__Group__02699);
            rule__PossibleExpression__Group__1();
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
    // $ANTLR end rule__PossibleExpression__Group__0


    // $ANTLR start rule__PossibleExpression__Group__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1274:1: rule__PossibleExpression__Group__0__Impl : ( ( rule__PossibleExpression__KeyAssignment_0 ) ) ;
    public final void rule__PossibleExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1278:1: ( ( ( rule__PossibleExpression__KeyAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1279:1: ( ( rule__PossibleExpression__KeyAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1279:1: ( ( rule__PossibleExpression__KeyAssignment_0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1280:1: ( rule__PossibleExpression__KeyAssignment_0 )
            {
             before(grammarAccess.getPossibleExpressionAccess().getKeyAssignment_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1281:1: ( rule__PossibleExpression__KeyAssignment_0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1281:2: rule__PossibleExpression__KeyAssignment_0
            {
            pushFollow(FOLLOW_rule__PossibleExpression__KeyAssignment_0_in_rule__PossibleExpression__Group__0__Impl2726);
            rule__PossibleExpression__KeyAssignment_0();
            _fsp--;


            }

             after(grammarAccess.getPossibleExpressionAccess().getKeyAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__Group__0__Impl


    // $ANTLR start rule__PossibleExpression__Group__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1291:1: rule__PossibleExpression__Group__1 : rule__PossibleExpression__Group__1__Impl rule__PossibleExpression__Group__2 ;
    public final void rule__PossibleExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1295:1: ( rule__PossibleExpression__Group__1__Impl rule__PossibleExpression__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1296:2: rule__PossibleExpression__Group__1__Impl rule__PossibleExpression__Group__2
            {
            pushFollow(FOLLOW_rule__PossibleExpression__Group__1__Impl_in_rule__PossibleExpression__Group__12756);
            rule__PossibleExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PossibleExpression__Group__2_in_rule__PossibleExpression__Group__12759);
            rule__PossibleExpression__Group__2();
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
    // $ANTLR end rule__PossibleExpression__Group__1


    // $ANTLR start rule__PossibleExpression__Group__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1303:1: rule__PossibleExpression__Group__1__Impl : ( '=' ) ;
    public final void rule__PossibleExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1307:1: ( ( '=' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1308:1: ( '=' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1308:1: ( '=' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1309:1: '='
            {
             before(grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_1()); 
            match(input,17,FOLLOW_17_in_rule__PossibleExpression__Group__1__Impl2787); 
             after(grammarAccess.getPossibleExpressionAccess().getEqualsSignKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__Group__1__Impl


    // $ANTLR start rule__PossibleExpression__Group__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1322:1: rule__PossibleExpression__Group__2 : rule__PossibleExpression__Group__2__Impl ;
    public final void rule__PossibleExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1326:1: ( rule__PossibleExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1327:2: rule__PossibleExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__PossibleExpression__Group__2__Impl_in_rule__PossibleExpression__Group__22818);
            rule__PossibleExpression__Group__2__Impl();
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
    // $ANTLR end rule__PossibleExpression__Group__2


    // $ANTLR start rule__PossibleExpression__Group__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1333:1: rule__PossibleExpression__Group__2__Impl : ( ( rule__PossibleExpression__Alternatives_2 ) ) ;
    public final void rule__PossibleExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1337:1: ( ( ( rule__PossibleExpression__Alternatives_2 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1338:1: ( ( rule__PossibleExpression__Alternatives_2 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1338:1: ( ( rule__PossibleExpression__Alternatives_2 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1339:1: ( rule__PossibleExpression__Alternatives_2 )
            {
             before(grammarAccess.getPossibleExpressionAccess().getAlternatives_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1340:1: ( rule__PossibleExpression__Alternatives_2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1340:2: rule__PossibleExpression__Alternatives_2
            {
            pushFollow(FOLLOW_rule__PossibleExpression__Alternatives_2_in_rule__PossibleExpression__Group__2__Impl2845);
            rule__PossibleExpression__Alternatives_2();
            _fsp--;


            }

             after(grammarAccess.getPossibleExpressionAccess().getAlternatives_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__Group__2__Impl


    // $ANTLR start rule__PossibleExpression__Group_2_0__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1356:1: rule__PossibleExpression__Group_2_0__0 : rule__PossibleExpression__Group_2_0__0__Impl rule__PossibleExpression__Group_2_0__1 ;
    public final void rule__PossibleExpression__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1360:1: ( rule__PossibleExpression__Group_2_0__0__Impl rule__PossibleExpression__Group_2_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1361:2: rule__PossibleExpression__Group_2_0__0__Impl rule__PossibleExpression__Group_2_0__1
            {
            pushFollow(FOLLOW_rule__PossibleExpression__Group_2_0__0__Impl_in_rule__PossibleExpression__Group_2_0__02881);
            rule__PossibleExpression__Group_2_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PossibleExpression__Group_2_0__1_in_rule__PossibleExpression__Group_2_0__02884);
            rule__PossibleExpression__Group_2_0__1();
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
    // $ANTLR end rule__PossibleExpression__Group_2_0__0


    // $ANTLR start rule__PossibleExpression__Group_2_0__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1368:1: rule__PossibleExpression__Group_2_0__0__Impl : ( '\"=' ) ;
    public final void rule__PossibleExpression__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1372:1: ( ( '\"=' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1373:1: ( '\"=' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1373:1: ( '\"=' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1374:1: '\"='
            {
             before(grammarAccess.getPossibleExpressionAccess().getQuotationMarkEqualsSignKeyword_2_0_0()); 
            match(input,18,FOLLOW_18_in_rule__PossibleExpression__Group_2_0__0__Impl2912); 
             after(grammarAccess.getPossibleExpressionAccess().getQuotationMarkEqualsSignKeyword_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__Group_2_0__0__Impl


    // $ANTLR start rule__PossibleExpression__Group_2_0__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1387:1: rule__PossibleExpression__Group_2_0__1 : rule__PossibleExpression__Group_2_0__1__Impl rule__PossibleExpression__Group_2_0__2 ;
    public final void rule__PossibleExpression__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1391:1: ( rule__PossibleExpression__Group_2_0__1__Impl rule__PossibleExpression__Group_2_0__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1392:2: rule__PossibleExpression__Group_2_0__1__Impl rule__PossibleExpression__Group_2_0__2
            {
            pushFollow(FOLLOW_rule__PossibleExpression__Group_2_0__1__Impl_in_rule__PossibleExpression__Group_2_0__12943);
            rule__PossibleExpression__Group_2_0__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PossibleExpression__Group_2_0__2_in_rule__PossibleExpression__Group_2_0__12946);
            rule__PossibleExpression__Group_2_0__2();
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
    // $ANTLR end rule__PossibleExpression__Group_2_0__1


    // $ANTLR start rule__PossibleExpression__Group_2_0__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1399:1: rule__PossibleExpression__Group_2_0__1__Impl : ( ( rule__PossibleExpression__ExpressionValueAssignment_2_0_1 ) ) ;
    public final void rule__PossibleExpression__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1403:1: ( ( ( rule__PossibleExpression__ExpressionValueAssignment_2_0_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1404:1: ( ( rule__PossibleExpression__ExpressionValueAssignment_2_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1404:1: ( ( rule__PossibleExpression__ExpressionValueAssignment_2_0_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1405:1: ( rule__PossibleExpression__ExpressionValueAssignment_2_0_1 )
            {
             before(grammarAccess.getPossibleExpressionAccess().getExpressionValueAssignment_2_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1406:1: ( rule__PossibleExpression__ExpressionValueAssignment_2_0_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1406:2: rule__PossibleExpression__ExpressionValueAssignment_2_0_1
            {
            pushFollow(FOLLOW_rule__PossibleExpression__ExpressionValueAssignment_2_0_1_in_rule__PossibleExpression__Group_2_0__1__Impl2973);
            rule__PossibleExpression__ExpressionValueAssignment_2_0_1();
            _fsp--;


            }

             after(grammarAccess.getPossibleExpressionAccess().getExpressionValueAssignment_2_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__Group_2_0__1__Impl


    // $ANTLR start rule__PossibleExpression__Group_2_0__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1416:1: rule__PossibleExpression__Group_2_0__2 : rule__PossibleExpression__Group_2_0__2__Impl ;
    public final void rule__PossibleExpression__Group_2_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1420:1: ( rule__PossibleExpression__Group_2_0__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1421:2: rule__PossibleExpression__Group_2_0__2__Impl
            {
            pushFollow(FOLLOW_rule__PossibleExpression__Group_2_0__2__Impl_in_rule__PossibleExpression__Group_2_0__23003);
            rule__PossibleExpression__Group_2_0__2__Impl();
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
    // $ANTLR end rule__PossibleExpression__Group_2_0__2


    // $ANTLR start rule__PossibleExpression__Group_2_0__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1427:1: rule__PossibleExpression__Group_2_0__2__Impl : ( ';\"' ) ;
    public final void rule__PossibleExpression__Group_2_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1431:1: ( ( ';\"' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1432:1: ( ';\"' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1432:1: ( ';\"' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1433:1: ';\"'
            {
             before(grammarAccess.getPossibleExpressionAccess().getSemicolonQuotationMarkKeyword_2_0_2()); 
            match(input,19,FOLLOW_19_in_rule__PossibleExpression__Group_2_0__2__Impl3031); 
             after(grammarAccess.getPossibleExpressionAccess().getSemicolonQuotationMarkKeyword_2_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__Group_2_0__2__Impl


    // $ANTLR start rule__Message__Group__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1452:1: rule__Message__Group__0 : rule__Message__Group__0__Impl rule__Message__Group__1 ;
    public final void rule__Message__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1456:1: ( rule__Message__Group__0__Impl rule__Message__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1457:2: rule__Message__Group__0__Impl rule__Message__Group__1
            {
            pushFollow(FOLLOW_rule__Message__Group__0__Impl_in_rule__Message__Group__03068);
            rule__Message__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Message__Group__1_in_rule__Message__Group__03071);
            rule__Message__Group__1();
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
    // $ANTLR end rule__Message__Group__0


    // $ANTLR start rule__Message__Group__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1464:1: rule__Message__Group__0__Impl : ( '<message' ) ;
    public final void rule__Message__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1468:1: ( ( '<message' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1469:1: ( '<message' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1469:1: ( '<message' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1470:1: '<message'
            {
             before(grammarAccess.getMessageAccess().getMessageKeyword_0()); 
            match(input,20,FOLLOW_20_in_rule__Message__Group__0__Impl3099); 
             after(grammarAccess.getMessageAccess().getMessageKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__Group__0__Impl


    // $ANTLR start rule__Message__Group__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1483:1: rule__Message__Group__1 : rule__Message__Group__1__Impl rule__Message__Group__2 ;
    public final void rule__Message__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1487:1: ( rule__Message__Group__1__Impl rule__Message__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1488:2: rule__Message__Group__1__Impl rule__Message__Group__2
            {
            pushFollow(FOLLOW_rule__Message__Group__1__Impl_in_rule__Message__Group__13130);
            rule__Message__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Message__Group__2_in_rule__Message__Group__13133);
            rule__Message__Group__2();
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
    // $ANTLR end rule__Message__Group__1


    // $ANTLR start rule__Message__Group__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1495:1: rule__Message__Group__1__Impl : ( () ) ;
    public final void rule__Message__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1499:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1500:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1500:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1501:1: ()
            {
             before(grammarAccess.getMessageAccess().getMessageAction_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1502:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1504:1: 
            {
            }

             after(grammarAccess.getMessageAccess().getMessageAction_1()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__Group__1__Impl


    // $ANTLR start rule__Message__Group__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1514:1: rule__Message__Group__2 : rule__Message__Group__2__Impl rule__Message__Group__3 ;
    public final void rule__Message__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1518:1: ( rule__Message__Group__2__Impl rule__Message__Group__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1519:2: rule__Message__Group__2__Impl rule__Message__Group__3
            {
            pushFollow(FOLLOW_rule__Message__Group__2__Impl_in_rule__Message__Group__23191);
            rule__Message__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Message__Group__3_in_rule__Message__Group__23194);
            rule__Message__Group__3();
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
    // $ANTLR end rule__Message__Group__2


    // $ANTLR start rule__Message__Group__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1526:1: rule__Message__Group__2__Impl : ( ( rule__Message__AttributesAssignment_2 )* ) ;
    public final void rule__Message__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1530:1: ( ( ( rule__Message__AttributesAssignment_2 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1531:1: ( ( rule__Message__AttributesAssignment_2 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1531:1: ( ( rule__Message__AttributesAssignment_2 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1532:1: ( rule__Message__AttributesAssignment_2 )*
            {
             before(grammarAccess.getMessageAccess().getAttributesAssignment_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1533:1: ( rule__Message__AttributesAssignment_2 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==RULE_ID) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1533:2: rule__Message__AttributesAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__Message__AttributesAssignment_2_in_rule__Message__Group__2__Impl3221);
            	    rule__Message__AttributesAssignment_2();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

             after(grammarAccess.getMessageAccess().getAttributesAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__Group__2__Impl


    // $ANTLR start rule__Message__Group__3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1543:1: rule__Message__Group__3 : rule__Message__Group__3__Impl ;
    public final void rule__Message__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1547:1: ( rule__Message__Group__3__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1548:2: rule__Message__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Message__Group__3__Impl_in_rule__Message__Group__33252);
            rule__Message__Group__3__Impl();
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
    // $ANTLR end rule__Message__Group__3


    // $ANTLR start rule__Message__Group__3__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1554:1: rule__Message__Group__3__Impl : ( ( rule__Message__Alternatives_3 ) ) ;
    public final void rule__Message__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1558:1: ( ( ( rule__Message__Alternatives_3 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1559:1: ( ( rule__Message__Alternatives_3 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1559:1: ( ( rule__Message__Alternatives_3 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1560:1: ( rule__Message__Alternatives_3 )
            {
             before(grammarAccess.getMessageAccess().getAlternatives_3()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1561:1: ( rule__Message__Alternatives_3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1561:2: rule__Message__Alternatives_3
            {
            pushFollow(FOLLOW_rule__Message__Alternatives_3_in_rule__Message__Group__3__Impl3279);
            rule__Message__Alternatives_3();
            _fsp--;


            }

             after(grammarAccess.getMessageAccess().getAlternatives_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__Group__3__Impl


    // $ANTLR start rule__Message__Group_3_0__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1579:1: rule__Message__Group_3_0__0 : rule__Message__Group_3_0__0__Impl rule__Message__Group_3_0__1 ;
    public final void rule__Message__Group_3_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1583:1: ( rule__Message__Group_3_0__0__Impl rule__Message__Group_3_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1584:2: rule__Message__Group_3_0__0__Impl rule__Message__Group_3_0__1
            {
            pushFollow(FOLLOW_rule__Message__Group_3_0__0__Impl_in_rule__Message__Group_3_0__03317);
            rule__Message__Group_3_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Message__Group_3_0__1_in_rule__Message__Group_3_0__03320);
            rule__Message__Group_3_0__1();
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
    // $ANTLR end rule__Message__Group_3_0__0


    // $ANTLR start rule__Message__Group_3_0__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1591:1: rule__Message__Group_3_0__0__Impl : ( '>' ) ;
    public final void rule__Message__Group_3_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1595:1: ( ( '>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1596:1: ( '>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1596:1: ( '>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1597:1: '>'
            {
             before(grammarAccess.getMessageAccess().getGreaterThanSignKeyword_3_0_0()); 
            match(input,15,FOLLOW_15_in_rule__Message__Group_3_0__0__Impl3348); 
             after(grammarAccess.getMessageAccess().getGreaterThanSignKeyword_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__Group_3_0__0__Impl


    // $ANTLR start rule__Message__Group_3_0__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1610:1: rule__Message__Group_3_0__1 : rule__Message__Group_3_0__1__Impl rule__Message__Group_3_0__2 ;
    public final void rule__Message__Group_3_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1614:1: ( rule__Message__Group_3_0__1__Impl rule__Message__Group_3_0__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1615:2: rule__Message__Group_3_0__1__Impl rule__Message__Group_3_0__2
            {
            pushFollow(FOLLOW_rule__Message__Group_3_0__1__Impl_in_rule__Message__Group_3_0__13379);
            rule__Message__Group_3_0__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Message__Group_3_0__2_in_rule__Message__Group_3_0__13382);
            rule__Message__Group_3_0__2();
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
    // $ANTLR end rule__Message__Group_3_0__1


    // $ANTLR start rule__Message__Group_3_0__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1622:1: rule__Message__Group_3_0__1__Impl : ( ( rule__Message__Alternatives_3_0_1 )* ) ;
    public final void rule__Message__Group_3_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1626:1: ( ( ( rule__Message__Alternatives_3_0_1 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1627:1: ( ( rule__Message__Alternatives_3_0_1 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1627:1: ( ( rule__Message__Alternatives_3_0_1 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1628:1: ( rule__Message__Alternatives_3_0_1 )*
            {
             before(grammarAccess.getMessageAccess().getAlternatives_3_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1629:1: ( rule__Message__Alternatives_3_0_1 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==20||LA19_0==22||LA19_0==24) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1629:2: rule__Message__Alternatives_3_0_1
            	    {
            	    pushFollow(FOLLOW_rule__Message__Alternatives_3_0_1_in_rule__Message__Group_3_0__1__Impl3409);
            	    rule__Message__Alternatives_3_0_1();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             after(grammarAccess.getMessageAccess().getAlternatives_3_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__Group_3_0__1__Impl


    // $ANTLR start rule__Message__Group_3_0__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1639:1: rule__Message__Group_3_0__2 : rule__Message__Group_3_0__2__Impl ;
    public final void rule__Message__Group_3_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1643:1: ( rule__Message__Group_3_0__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1644:2: rule__Message__Group_3_0__2__Impl
            {
            pushFollow(FOLLOW_rule__Message__Group_3_0__2__Impl_in_rule__Message__Group_3_0__23440);
            rule__Message__Group_3_0__2__Impl();
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
    // $ANTLR end rule__Message__Group_3_0__2


    // $ANTLR start rule__Message__Group_3_0__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1650:1: rule__Message__Group_3_0__2__Impl : ( '</message>' ) ;
    public final void rule__Message__Group_3_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1654:1: ( ( '</message>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1655:1: ( '</message>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1655:1: ( '</message>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1656:1: '</message>'
            {
             before(grammarAccess.getMessageAccess().getMessageKeyword_3_0_2()); 
            match(input,21,FOLLOW_21_in_rule__Message__Group_3_0__2__Impl3468); 
             after(grammarAccess.getMessageAccess().getMessageKeyword_3_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__Group_3_0__2__Impl


    // $ANTLR start rule__Map__Group__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1675:1: rule__Map__Group__0 : rule__Map__Group__0__Impl rule__Map__Group__1 ;
    public final void rule__Map__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1679:1: ( rule__Map__Group__0__Impl rule__Map__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1680:2: rule__Map__Group__0__Impl rule__Map__Group__1
            {
            pushFollow(FOLLOW_rule__Map__Group__0__Impl_in_rule__Map__Group__03505);
            rule__Map__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Map__Group__1_in_rule__Map__Group__03508);
            rule__Map__Group__1();
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
    // $ANTLR end rule__Map__Group__0


    // $ANTLR start rule__Map__Group__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1687:1: rule__Map__Group__0__Impl : ( () ) ;
    public final void rule__Map__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1691:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1692:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1692:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1693:1: ()
            {
             before(grammarAccess.getMapAccess().getMapAction_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1694:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1696:1: 
            {
            }

             after(grammarAccess.getMapAccess().getMapAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group__0__Impl


    // $ANTLR start rule__Map__Group__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1706:1: rule__Map__Group__1 : rule__Map__Group__1__Impl rule__Map__Group__2 ;
    public final void rule__Map__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1710:1: ( rule__Map__Group__1__Impl rule__Map__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1711:2: rule__Map__Group__1__Impl rule__Map__Group__2
            {
            pushFollow(FOLLOW_rule__Map__Group__1__Impl_in_rule__Map__Group__13566);
            rule__Map__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Map__Group__2_in_rule__Map__Group__13569);
            rule__Map__Group__2();
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
    // $ANTLR end rule__Map__Group__1


    // $ANTLR start rule__Map__Group__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1718:1: rule__Map__Group__1__Impl : ( '<map.' ) ;
    public final void rule__Map__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1722:1: ( ( '<map.' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1723:1: ( '<map.' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1723:1: ( '<map.' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1724:1: '<map.'
            {
             before(grammarAccess.getMapAccess().getMapKeyword_1()); 
            match(input,22,FOLLOW_22_in_rule__Map__Group__1__Impl3597); 
             after(grammarAccess.getMapAccess().getMapKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group__1__Impl


    // $ANTLR start rule__Map__Group__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1737:1: rule__Map__Group__2 : rule__Map__Group__2__Impl rule__Map__Group__3 ;
    public final void rule__Map__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1741:1: ( rule__Map__Group__2__Impl rule__Map__Group__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1742:2: rule__Map__Group__2__Impl rule__Map__Group__3
            {
            pushFollow(FOLLOW_rule__Map__Group__2__Impl_in_rule__Map__Group__23628);
            rule__Map__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Map__Group__3_in_rule__Map__Group__23631);
            rule__Map__Group__3();
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
    // $ANTLR end rule__Map__Group__2


    // $ANTLR start rule__Map__Group__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1749:1: rule__Map__Group__2__Impl : ( ( rule__Map__MapNameAssignment_2 ) ) ;
    public final void rule__Map__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1753:1: ( ( ( rule__Map__MapNameAssignment_2 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1754:1: ( ( rule__Map__MapNameAssignment_2 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1754:1: ( ( rule__Map__MapNameAssignment_2 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1755:1: ( rule__Map__MapNameAssignment_2 )
            {
             before(grammarAccess.getMapAccess().getMapNameAssignment_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1756:1: ( rule__Map__MapNameAssignment_2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1756:2: rule__Map__MapNameAssignment_2
            {
            pushFollow(FOLLOW_rule__Map__MapNameAssignment_2_in_rule__Map__Group__2__Impl3658);
            rule__Map__MapNameAssignment_2();
            _fsp--;


            }

             after(grammarAccess.getMapAccess().getMapNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group__2__Impl


    // $ANTLR start rule__Map__Group__3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1766:1: rule__Map__Group__3 : rule__Map__Group__3__Impl rule__Map__Group__4 ;
    public final void rule__Map__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1770:1: ( rule__Map__Group__3__Impl rule__Map__Group__4 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1771:2: rule__Map__Group__3__Impl rule__Map__Group__4
            {
            pushFollow(FOLLOW_rule__Map__Group__3__Impl_in_rule__Map__Group__33688);
            rule__Map__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Map__Group__4_in_rule__Map__Group__33691);
            rule__Map__Group__4();
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
    // $ANTLR end rule__Map__Group__3


    // $ANTLR start rule__Map__Group__3__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1778:1: rule__Map__Group__3__Impl : ( ( rule__Map__AttributesAssignment_3 ) ) ;
    public final void rule__Map__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1782:1: ( ( ( rule__Map__AttributesAssignment_3 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1783:1: ( ( rule__Map__AttributesAssignment_3 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1783:1: ( ( rule__Map__AttributesAssignment_3 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1784:1: ( rule__Map__AttributesAssignment_3 )
            {
             before(grammarAccess.getMapAccess().getAttributesAssignment_3()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1785:1: ( rule__Map__AttributesAssignment_3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1785:2: rule__Map__AttributesAssignment_3
            {
            pushFollow(FOLLOW_rule__Map__AttributesAssignment_3_in_rule__Map__Group__3__Impl3718);
            rule__Map__AttributesAssignment_3();
            _fsp--;


            }

             after(grammarAccess.getMapAccess().getAttributesAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group__3__Impl


    // $ANTLR start rule__Map__Group__4
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1795:1: rule__Map__Group__4 : rule__Map__Group__4__Impl ;
    public final void rule__Map__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1799:1: ( rule__Map__Group__4__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1800:2: rule__Map__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__Map__Group__4__Impl_in_rule__Map__Group__43748);
            rule__Map__Group__4__Impl();
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
    // $ANTLR end rule__Map__Group__4


    // $ANTLR start rule__Map__Group__4__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1806:1: rule__Map__Group__4__Impl : ( ( rule__Map__Alternatives_4 ) ) ;
    public final void rule__Map__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1810:1: ( ( ( rule__Map__Alternatives_4 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1811:1: ( ( rule__Map__Alternatives_4 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1811:1: ( ( rule__Map__Alternatives_4 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1812:1: ( rule__Map__Alternatives_4 )
            {
             before(grammarAccess.getMapAccess().getAlternatives_4()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1813:1: ( rule__Map__Alternatives_4 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1813:2: rule__Map__Alternatives_4
            {
            pushFollow(FOLLOW_rule__Map__Alternatives_4_in_rule__Map__Group__4__Impl3775);
            rule__Map__Alternatives_4();
            _fsp--;


            }

             after(grammarAccess.getMapAccess().getAlternatives_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group__4__Impl


    // $ANTLR start rule__Map__Group_4_1__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1833:1: rule__Map__Group_4_1__0 : rule__Map__Group_4_1__0__Impl rule__Map__Group_4_1__1 ;
    public final void rule__Map__Group_4_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1837:1: ( rule__Map__Group_4_1__0__Impl rule__Map__Group_4_1__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1838:2: rule__Map__Group_4_1__0__Impl rule__Map__Group_4_1__1
            {
            pushFollow(FOLLOW_rule__Map__Group_4_1__0__Impl_in_rule__Map__Group_4_1__03815);
            rule__Map__Group_4_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Map__Group_4_1__1_in_rule__Map__Group_4_1__03818);
            rule__Map__Group_4_1__1();
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
    // $ANTLR end rule__Map__Group_4_1__0


    // $ANTLR start rule__Map__Group_4_1__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1845:1: rule__Map__Group_4_1__0__Impl : ( '>' ) ;
    public final void rule__Map__Group_4_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1849:1: ( ( '>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1850:1: ( '>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1850:1: ( '>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1851:1: '>'
            {
             before(grammarAccess.getMapAccess().getGreaterThanSignKeyword_4_1_0()); 
            match(input,15,FOLLOW_15_in_rule__Map__Group_4_1__0__Impl3846); 
             after(grammarAccess.getMapAccess().getGreaterThanSignKeyword_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group_4_1__0__Impl


    // $ANTLR start rule__Map__Group_4_1__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1864:1: rule__Map__Group_4_1__1 : rule__Map__Group_4_1__1__Impl rule__Map__Group_4_1__2 ;
    public final void rule__Map__Group_4_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1868:1: ( rule__Map__Group_4_1__1__Impl rule__Map__Group_4_1__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1869:2: rule__Map__Group_4_1__1__Impl rule__Map__Group_4_1__2
            {
            pushFollow(FOLLOW_rule__Map__Group_4_1__1__Impl_in_rule__Map__Group_4_1__13877);
            rule__Map__Group_4_1__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Map__Group_4_1__2_in_rule__Map__Group_4_1__13880);
            rule__Map__Group_4_1__2();
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
    // $ANTLR end rule__Map__Group_4_1__1


    // $ANTLR start rule__Map__Group_4_1__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1876:1: rule__Map__Group_4_1__1__Impl : ( ( rule__Map__Alternatives_4_1_1 )* ) ;
    public final void rule__Map__Group_4_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1880:1: ( ( ( rule__Map__Alternatives_4_1_1 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1881:1: ( ( rule__Map__Alternatives_4_1_1 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1881:1: ( ( rule__Map__Alternatives_4_1_1 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1882:1: ( rule__Map__Alternatives_4_1_1 )*
            {
             before(grammarAccess.getMapAccess().getAlternatives_4_1_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1883:1: ( rule__Map__Alternatives_4_1_1 )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==20||LA20_0==22||LA20_0==24) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1883:2: rule__Map__Alternatives_4_1_1
            	    {
            	    pushFollow(FOLLOW_rule__Map__Alternatives_4_1_1_in_rule__Map__Group_4_1__1__Impl3907);
            	    rule__Map__Alternatives_4_1_1();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

             after(grammarAccess.getMapAccess().getAlternatives_4_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group_4_1__1__Impl


    // $ANTLR start rule__Map__Group_4_1__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1893:1: rule__Map__Group_4_1__2 : rule__Map__Group_4_1__2__Impl rule__Map__Group_4_1__3 ;
    public final void rule__Map__Group_4_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1897:1: ( rule__Map__Group_4_1__2__Impl rule__Map__Group_4_1__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1898:2: rule__Map__Group_4_1__2__Impl rule__Map__Group_4_1__3
            {
            pushFollow(FOLLOW_rule__Map__Group_4_1__2__Impl_in_rule__Map__Group_4_1__23938);
            rule__Map__Group_4_1__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Map__Group_4_1__3_in_rule__Map__Group_4_1__23941);
            rule__Map__Group_4_1__3();
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
    // $ANTLR end rule__Map__Group_4_1__2


    // $ANTLR start rule__Map__Group_4_1__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1905:1: rule__Map__Group_4_1__2__Impl : ( '</map.' ) ;
    public final void rule__Map__Group_4_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1909:1: ( ( '</map.' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1910:1: ( '</map.' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1910:1: ( '</map.' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1911:1: '</map.'
            {
             before(grammarAccess.getMapAccess().getMapKeyword_4_1_2()); 
            match(input,23,FOLLOW_23_in_rule__Map__Group_4_1__2__Impl3969); 
             after(grammarAccess.getMapAccess().getMapKeyword_4_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group_4_1__2__Impl


    // $ANTLR start rule__Map__Group_4_1__3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1924:1: rule__Map__Group_4_1__3 : rule__Map__Group_4_1__3__Impl rule__Map__Group_4_1__4 ;
    public final void rule__Map__Group_4_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1928:1: ( rule__Map__Group_4_1__3__Impl rule__Map__Group_4_1__4 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1929:2: rule__Map__Group_4_1__3__Impl rule__Map__Group_4_1__4
            {
            pushFollow(FOLLOW_rule__Map__Group_4_1__3__Impl_in_rule__Map__Group_4_1__34000);
            rule__Map__Group_4_1__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Map__Group_4_1__4_in_rule__Map__Group_4_1__34003);
            rule__Map__Group_4_1__4();
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
    // $ANTLR end rule__Map__Group_4_1__3


    // $ANTLR start rule__Map__Group_4_1__3__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1936:1: rule__Map__Group_4_1__3__Impl : ( ( rule__Map__MapClosingNameAssignment_4_1_3 ) ) ;
    public final void rule__Map__Group_4_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1940:1: ( ( ( rule__Map__MapClosingNameAssignment_4_1_3 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1941:1: ( ( rule__Map__MapClosingNameAssignment_4_1_3 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1941:1: ( ( rule__Map__MapClosingNameAssignment_4_1_3 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1942:1: ( rule__Map__MapClosingNameAssignment_4_1_3 )
            {
             before(grammarAccess.getMapAccess().getMapClosingNameAssignment_4_1_3()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1943:1: ( rule__Map__MapClosingNameAssignment_4_1_3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1943:2: rule__Map__MapClosingNameAssignment_4_1_3
            {
            pushFollow(FOLLOW_rule__Map__MapClosingNameAssignment_4_1_3_in_rule__Map__Group_4_1__3__Impl4030);
            rule__Map__MapClosingNameAssignment_4_1_3();
            _fsp--;


            }

             after(grammarAccess.getMapAccess().getMapClosingNameAssignment_4_1_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group_4_1__3__Impl


    // $ANTLR start rule__Map__Group_4_1__4
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1953:1: rule__Map__Group_4_1__4 : rule__Map__Group_4_1__4__Impl ;
    public final void rule__Map__Group_4_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1957:1: ( rule__Map__Group_4_1__4__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1958:2: rule__Map__Group_4_1__4__Impl
            {
            pushFollow(FOLLOW_rule__Map__Group_4_1__4__Impl_in_rule__Map__Group_4_1__44060);
            rule__Map__Group_4_1__4__Impl();
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
    // $ANTLR end rule__Map__Group_4_1__4


    // $ANTLR start rule__Map__Group_4_1__4__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1964:1: rule__Map__Group_4_1__4__Impl : ( '>' ) ;
    public final void rule__Map__Group_4_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1968:1: ( ( '>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1969:1: ( '>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1969:1: ( '>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1970:1: '>'
            {
             before(grammarAccess.getMapAccess().getGreaterThanSignKeyword_4_1_4()); 
            match(input,15,FOLLOW_15_in_rule__Map__Group_4_1__4__Impl4088); 
             after(grammarAccess.getMapAccess().getGreaterThanSignKeyword_4_1_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__Group_4_1__4__Impl


    // $ANTLR start rule__Property__Group__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1993:1: rule__Property__Group__0 : rule__Property__Group__0__Impl rule__Property__Group__1 ;
    public final void rule__Property__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1997:1: ( rule__Property__Group__0__Impl rule__Property__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:1998:2: rule__Property__Group__0__Impl rule__Property__Group__1
            {
            pushFollow(FOLLOW_rule__Property__Group__0__Impl_in_rule__Property__Group__04129);
            rule__Property__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Property__Group__1_in_rule__Property__Group__04132);
            rule__Property__Group__1();
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
    // $ANTLR end rule__Property__Group__0


    // $ANTLR start rule__Property__Group__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2005:1: rule__Property__Group__0__Impl : ( '<property' ) ;
    public final void rule__Property__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2009:1: ( ( '<property' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2010:1: ( '<property' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2010:1: ( '<property' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2011:1: '<property'
            {
             before(grammarAccess.getPropertyAccess().getPropertyKeyword_0()); 
            match(input,24,FOLLOW_24_in_rule__Property__Group__0__Impl4160); 
             after(grammarAccess.getPropertyAccess().getPropertyKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__Group__0__Impl


    // $ANTLR start rule__Property__Group__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2024:1: rule__Property__Group__1 : rule__Property__Group__1__Impl rule__Property__Group__2 ;
    public final void rule__Property__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2028:1: ( rule__Property__Group__1__Impl rule__Property__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2029:2: rule__Property__Group__1__Impl rule__Property__Group__2
            {
            pushFollow(FOLLOW_rule__Property__Group__1__Impl_in_rule__Property__Group__14191);
            rule__Property__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Property__Group__2_in_rule__Property__Group__14194);
            rule__Property__Group__2();
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
    // $ANTLR end rule__Property__Group__1


    // $ANTLR start rule__Property__Group__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2036:1: rule__Property__Group__1__Impl : ( () ) ;
    public final void rule__Property__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2040:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2041:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2041:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2042:1: ()
            {
             before(grammarAccess.getPropertyAccess().getPropertyAction_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2043:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2045:1: 
            {
            }

             after(grammarAccess.getPropertyAccess().getPropertyAction_1()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__Group__1__Impl


    // $ANTLR start rule__Property__Group__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2055:1: rule__Property__Group__2 : rule__Property__Group__2__Impl rule__Property__Group__3 ;
    public final void rule__Property__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2059:1: ( rule__Property__Group__2__Impl rule__Property__Group__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2060:2: rule__Property__Group__2__Impl rule__Property__Group__3
            {
            pushFollow(FOLLOW_rule__Property__Group__2__Impl_in_rule__Property__Group__24252);
            rule__Property__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Property__Group__3_in_rule__Property__Group__24255);
            rule__Property__Group__3();
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
    // $ANTLR end rule__Property__Group__2


    // $ANTLR start rule__Property__Group__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2067:1: rule__Property__Group__2__Impl : ( ( rule__Property__AttributesAssignment_2 )* ) ;
    public final void rule__Property__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2071:1: ( ( ( rule__Property__AttributesAssignment_2 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2072:1: ( ( rule__Property__AttributesAssignment_2 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2072:1: ( ( rule__Property__AttributesAssignment_2 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2073:1: ( rule__Property__AttributesAssignment_2 )*
            {
             before(grammarAccess.getPropertyAccess().getAttributesAssignment_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2074:1: ( rule__Property__AttributesAssignment_2 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE_ID) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2074:2: rule__Property__AttributesAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__Property__AttributesAssignment_2_in_rule__Property__Group__2__Impl4282);
            	    rule__Property__AttributesAssignment_2();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

             after(grammarAccess.getPropertyAccess().getAttributesAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__Group__2__Impl


    // $ANTLR start rule__Property__Group__3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2084:1: rule__Property__Group__3 : rule__Property__Group__3__Impl ;
    public final void rule__Property__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2088:1: ( rule__Property__Group__3__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2089:2: rule__Property__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Property__Group__3__Impl_in_rule__Property__Group__34313);
            rule__Property__Group__3__Impl();
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
    // $ANTLR end rule__Property__Group__3


    // $ANTLR start rule__Property__Group__3__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2095:1: rule__Property__Group__3__Impl : ( ( rule__Property__Alternatives_3 ) ) ;
    public final void rule__Property__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2099:1: ( ( ( rule__Property__Alternatives_3 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2100:1: ( ( rule__Property__Alternatives_3 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2100:1: ( ( rule__Property__Alternatives_3 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2101:1: ( rule__Property__Alternatives_3 )
            {
             before(grammarAccess.getPropertyAccess().getAlternatives_3()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2102:1: ( rule__Property__Alternatives_3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2102:2: rule__Property__Alternatives_3
            {
            pushFollow(FOLLOW_rule__Property__Alternatives_3_in_rule__Property__Group__3__Impl4340);
            rule__Property__Alternatives_3();
            _fsp--;


            }

             after(grammarAccess.getPropertyAccess().getAlternatives_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__Group__3__Impl


    // $ANTLR start rule__Property__Group_3_1__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2120:1: rule__Property__Group_3_1__0 : rule__Property__Group_3_1__0__Impl rule__Property__Group_3_1__1 ;
    public final void rule__Property__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2124:1: ( rule__Property__Group_3_1__0__Impl rule__Property__Group_3_1__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2125:2: rule__Property__Group_3_1__0__Impl rule__Property__Group_3_1__1
            {
            pushFollow(FOLLOW_rule__Property__Group_3_1__0__Impl_in_rule__Property__Group_3_1__04378);
            rule__Property__Group_3_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Property__Group_3_1__1_in_rule__Property__Group_3_1__04381);
            rule__Property__Group_3_1__1();
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
    // $ANTLR end rule__Property__Group_3_1__0


    // $ANTLR start rule__Property__Group_3_1__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2132:1: rule__Property__Group_3_1__0__Impl : ( '>' ) ;
    public final void rule__Property__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2136:1: ( ( '>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2137:1: ( '>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2137:1: ( '>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2138:1: '>'
            {
             before(grammarAccess.getPropertyAccess().getGreaterThanSignKeyword_3_1_0()); 
            match(input,15,FOLLOW_15_in_rule__Property__Group_3_1__0__Impl4409); 
             after(grammarAccess.getPropertyAccess().getGreaterThanSignKeyword_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__Group_3_1__0__Impl


    // $ANTLR start rule__Property__Group_3_1__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2151:1: rule__Property__Group_3_1__1 : rule__Property__Group_3_1__1__Impl rule__Property__Group_3_1__2 ;
    public final void rule__Property__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2155:1: ( rule__Property__Group_3_1__1__Impl rule__Property__Group_3_1__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2156:2: rule__Property__Group_3_1__1__Impl rule__Property__Group_3_1__2
            {
            pushFollow(FOLLOW_rule__Property__Group_3_1__1__Impl_in_rule__Property__Group_3_1__14440);
            rule__Property__Group_3_1__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Property__Group_3_1__2_in_rule__Property__Group_3_1__14443);
            rule__Property__Group_3_1__2();
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
    // $ANTLR end rule__Property__Group_3_1__1


    // $ANTLR start rule__Property__Group_3_1__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2163:1: rule__Property__Group_3_1__1__Impl : ( ( rule__Property__ExpressionValueAssignment_3_1_1 )? ) ;
    public final void rule__Property__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2167:1: ( ( ( rule__Property__ExpressionValueAssignment_3_1_1 )? ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2168:1: ( ( rule__Property__ExpressionValueAssignment_3_1_1 )? )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2168:1: ( ( rule__Property__ExpressionValueAssignment_3_1_1 )? )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2169:1: ( rule__Property__ExpressionValueAssignment_3_1_1 )?
            {
             before(grammarAccess.getPropertyAccess().getExpressionValueAssignment_3_1_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2170:1: ( rule__Property__ExpressionValueAssignment_3_1_1 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==26) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2170:2: rule__Property__ExpressionValueAssignment_3_1_1
                    {
                    pushFollow(FOLLOW_rule__Property__ExpressionValueAssignment_3_1_1_in_rule__Property__Group_3_1__1__Impl4470);
                    rule__Property__ExpressionValueAssignment_3_1_1();
                    _fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPropertyAccess().getExpressionValueAssignment_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__Group_3_1__1__Impl


    // $ANTLR start rule__Property__Group_3_1__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2180:1: rule__Property__Group_3_1__2 : rule__Property__Group_3_1__2__Impl ;
    public final void rule__Property__Group_3_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2184:1: ( rule__Property__Group_3_1__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2185:2: rule__Property__Group_3_1__2__Impl
            {
            pushFollow(FOLLOW_rule__Property__Group_3_1__2__Impl_in_rule__Property__Group_3_1__24501);
            rule__Property__Group_3_1__2__Impl();
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
    // $ANTLR end rule__Property__Group_3_1__2


    // $ANTLR start rule__Property__Group_3_1__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2191:1: rule__Property__Group_3_1__2__Impl : ( '</property>' ) ;
    public final void rule__Property__Group_3_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2195:1: ( ( '</property>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2196:1: ( '</property>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2196:1: ( '</property>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2197:1: '</property>'
            {
             before(grammarAccess.getPropertyAccess().getPropertyKeyword_3_1_2()); 
            match(input,25,FOLLOW_25_in_rule__Property__Group_3_1__2__Impl4529); 
             after(grammarAccess.getPropertyAccess().getPropertyKeyword_3_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__Group_3_1__2__Impl


    // $ANTLR start rule__ExpressionTag__Group__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2216:1: rule__ExpressionTag__Group__0 : rule__ExpressionTag__Group__0__Impl rule__ExpressionTag__Group__1 ;
    public final void rule__ExpressionTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2220:1: ( rule__ExpressionTag__Group__0__Impl rule__ExpressionTag__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2221:2: rule__ExpressionTag__Group__0__Impl rule__ExpressionTag__Group__1
            {
            pushFollow(FOLLOW_rule__ExpressionTag__Group__0__Impl_in_rule__ExpressionTag__Group__04566);
            rule__ExpressionTag__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExpressionTag__Group__1_in_rule__ExpressionTag__Group__04569);
            rule__ExpressionTag__Group__1();
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
    // $ANTLR end rule__ExpressionTag__Group__0


    // $ANTLR start rule__ExpressionTag__Group__0__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2228:1: rule__ExpressionTag__Group__0__Impl : ( '<expression>' ) ;
    public final void rule__ExpressionTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2232:1: ( ( '<expression>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2233:1: ( '<expression>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2233:1: ( '<expression>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2234:1: '<expression>'
            {
             before(grammarAccess.getExpressionTagAccess().getExpressionKeyword_0()); 
            match(input,26,FOLLOW_26_in_rule__ExpressionTag__Group__0__Impl4597); 
             after(grammarAccess.getExpressionTagAccess().getExpressionKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExpressionTag__Group__0__Impl


    // $ANTLR start rule__ExpressionTag__Group__1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2247:1: rule__ExpressionTag__Group__1 : rule__ExpressionTag__Group__1__Impl rule__ExpressionTag__Group__2 ;
    public final void rule__ExpressionTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2251:1: ( rule__ExpressionTag__Group__1__Impl rule__ExpressionTag__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2252:2: rule__ExpressionTag__Group__1__Impl rule__ExpressionTag__Group__2
            {
            pushFollow(FOLLOW_rule__ExpressionTag__Group__1__Impl_in_rule__ExpressionTag__Group__14628);
            rule__ExpressionTag__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExpressionTag__Group__2_in_rule__ExpressionTag__Group__14631);
            rule__ExpressionTag__Group__2();
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
    // $ANTLR end rule__ExpressionTag__Group__1


    // $ANTLR start rule__ExpressionTag__Group__1__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2259:1: rule__ExpressionTag__Group__1__Impl : ( ( rule__ExpressionTag__ValueAssignment_1 ) ) ;
    public final void rule__ExpressionTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2263:1: ( ( ( rule__ExpressionTag__ValueAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2264:1: ( ( rule__ExpressionTag__ValueAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2264:1: ( ( rule__ExpressionTag__ValueAssignment_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2265:1: ( rule__ExpressionTag__ValueAssignment_1 )
            {
             before(grammarAccess.getExpressionTagAccess().getValueAssignment_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2266:1: ( rule__ExpressionTag__ValueAssignment_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2266:2: rule__ExpressionTag__ValueAssignment_1
            {
            pushFollow(FOLLOW_rule__ExpressionTag__ValueAssignment_1_in_rule__ExpressionTag__Group__1__Impl4658);
            rule__ExpressionTag__ValueAssignment_1();
            _fsp--;


            }

             after(grammarAccess.getExpressionTagAccess().getValueAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExpressionTag__Group__1__Impl


    // $ANTLR start rule__ExpressionTag__Group__2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2276:1: rule__ExpressionTag__Group__2 : rule__ExpressionTag__Group__2__Impl ;
    public final void rule__ExpressionTag__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2280:1: ( rule__ExpressionTag__Group__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2281:2: rule__ExpressionTag__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__ExpressionTag__Group__2__Impl_in_rule__ExpressionTag__Group__24688);
            rule__ExpressionTag__Group__2__Impl();
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
    // $ANTLR end rule__ExpressionTag__Group__2


    // $ANTLR start rule__ExpressionTag__Group__2__Impl
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2287:1: rule__ExpressionTag__Group__2__Impl : ( '</expression>' ) ;
    public final void rule__ExpressionTag__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2291:1: ( ( '</expression>' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2292:1: ( '</expression>' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2292:1: ( '</expression>' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2293:1: '</expression>'
            {
             before(grammarAccess.getExpressionTagAccess().getExpressionKeyword_2()); 
            match(input,27,FOLLOW_27_in_rule__ExpressionTag__Group__2__Impl4716); 
             after(grammarAccess.getExpressionTagAccess().getExpressionKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExpressionTag__Group__2__Impl


    // $ANTLR start rule__TopLevel__Group__0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2312:1: rule__TopLevel__Group__0 : rule__TopLevel__Group__0__Impl rule__TopLevel__Group__1 ;
    public final void rule__TopLevel__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2316:1: ( rule__TopLevel__Group__0__Impl rule__TopLevel__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2317:2: rule__TopLevel__Group__0__Impl rule__TopLevel__Group__1
            {
            pushFollow(FOLLOW_rule__TopLevel__Group__0__Impl_in_rule__TopLevel__Group__04753);
            rule__TopLevel__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__TopLevel__Group__1_in_rule__TopLevel__Group__04756);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2324:1: rule__TopLevel__Group__0__Impl : ( () ) ;
    public final void rule__TopLevel__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2328:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2329:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2329:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2330:1: ()
            {
             before(grammarAccess.getTopLevelAccess().getTopLevelAction_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2331:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2333:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2343:1: rule__TopLevel__Group__1 : rule__TopLevel__Group__1__Impl ;
    public final void rule__TopLevel__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2347:1: ( rule__TopLevel__Group__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2348:2: rule__TopLevel__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__TopLevel__Group__1__Impl_in_rule__TopLevel__Group__14814);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2354:1: rule__TopLevel__Group__1__Impl : ( ( rule__TopLevel__ToplevelExpressionAssignment_1 ) ) ;
    public final void rule__TopLevel__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2358:1: ( ( ( rule__TopLevel__ToplevelExpressionAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2359:1: ( ( rule__TopLevel__ToplevelExpressionAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2359:1: ( ( rule__TopLevel__ToplevelExpressionAssignment_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2360:1: ( rule__TopLevel__ToplevelExpressionAssignment_1 )
            {
             before(grammarAccess.getTopLevelAccess().getToplevelExpressionAssignment_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2361:1: ( rule__TopLevel__ToplevelExpressionAssignment_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2361:2: rule__TopLevel__ToplevelExpressionAssignment_1
            {
            pushFollow(FOLLOW_rule__TopLevel__ToplevelExpressionAssignment_1_in_rule__TopLevel__Group__1__Impl4841);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2375:1: rule__PathSequence__Group__0 : rule__PathSequence__Group__0__Impl rule__PathSequence__Group__1 ;
    public final void rule__PathSequence__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2379:1: ( rule__PathSequence__Group__0__Impl rule__PathSequence__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2380:2: rule__PathSequence__Group__0__Impl rule__PathSequence__Group__1
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__0__Impl_in_rule__PathSequence__Group__04875);
            rule__PathSequence__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group__1_in_rule__PathSequence__Group__04878);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2387:1: rule__PathSequence__Group__0__Impl : ( '[' ) ;
    public final void rule__PathSequence__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2391:1: ( ( '[' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2392:1: ( '[' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2392:1: ( '[' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2393:1: '['
            {
             before(grammarAccess.getPathSequenceAccess().getLeftSquareBracketKeyword_0()); 
            match(input,28,FOLLOW_28_in_rule__PathSequence__Group__0__Impl4906); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2406:1: rule__PathSequence__Group__1 : rule__PathSequence__Group__1__Impl rule__PathSequence__Group__2 ;
    public final void rule__PathSequence__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2410:1: ( rule__PathSequence__Group__1__Impl rule__PathSequence__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2411:2: rule__PathSequence__Group__1__Impl rule__PathSequence__Group__2
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__1__Impl_in_rule__PathSequence__Group__14937);
            rule__PathSequence__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group__2_in_rule__PathSequence__Group__14940);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2418:1: rule__PathSequence__Group__1__Impl : ( ( '/' )? ) ;
    public final void rule__PathSequence__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2422:1: ( ( ( '/' )? ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2423:1: ( ( '/' )? )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2423:1: ( ( '/' )? )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2424:1: ( '/' )?
            {
             before(grammarAccess.getPathSequenceAccess().getSolidusKeyword_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2425:1: ( '/' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==29) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2426:2: '/'
                    {
                    match(input,29,FOLLOW_29_in_rule__PathSequence__Group__1__Impl4969); 

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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2437:1: rule__PathSequence__Group__2 : rule__PathSequence__Group__2__Impl rule__PathSequence__Group__3 ;
    public final void rule__PathSequence__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2441:1: ( rule__PathSequence__Group__2__Impl rule__PathSequence__Group__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2442:2: rule__PathSequence__Group__2__Impl rule__PathSequence__Group__3
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__2__Impl_in_rule__PathSequence__Group__25002);
            rule__PathSequence__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group__3_in_rule__PathSequence__Group__25005);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2449:1: rule__PathSequence__Group__2__Impl : ( rulePathElement ) ;
    public final void rule__PathSequence__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2453:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2454:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2454:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2455:1: rulePathElement
            {
             before(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_2()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__PathSequence__Group__2__Impl5032);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2466:1: rule__PathSequence__Group__3 : rule__PathSequence__Group__3__Impl rule__PathSequence__Group__4 ;
    public final void rule__PathSequence__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2470:1: ( rule__PathSequence__Group__3__Impl rule__PathSequence__Group__4 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2471:2: rule__PathSequence__Group__3__Impl rule__PathSequence__Group__4
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__3__Impl_in_rule__PathSequence__Group__35061);
            rule__PathSequence__Group__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group__4_in_rule__PathSequence__Group__35064);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2478:1: rule__PathSequence__Group__3__Impl : ( ( rule__PathSequence__Group_3__0 )* ) ;
    public final void rule__PathSequence__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2482:1: ( ( ( rule__PathSequence__Group_3__0 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2483:1: ( ( rule__PathSequence__Group_3__0 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2483:1: ( ( rule__PathSequence__Group_3__0 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2484:1: ( rule__PathSequence__Group_3__0 )*
            {
             before(grammarAccess.getPathSequenceAccess().getGroup_3()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2485:1: ( rule__PathSequence__Group_3__0 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==29) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2485:2: rule__PathSequence__Group_3__0
            	    {
            	    pushFollow(FOLLOW_rule__PathSequence__Group_3__0_in_rule__PathSequence__Group__3__Impl5091);
            	    rule__PathSequence__Group_3__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop24;
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2495:1: rule__PathSequence__Group__4 : rule__PathSequence__Group__4__Impl ;
    public final void rule__PathSequence__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2499:1: ( rule__PathSequence__Group__4__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2500:2: rule__PathSequence__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__PathSequence__Group__4__Impl_in_rule__PathSequence__Group__45122);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2506:1: rule__PathSequence__Group__4__Impl : ( ']' ) ;
    public final void rule__PathSequence__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2510:1: ( ( ']' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2511:1: ( ']' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2511:1: ( ']' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2512:1: ']'
            {
             before(grammarAccess.getPathSequenceAccess().getRightSquareBracketKeyword_4()); 
            match(input,30,FOLLOW_30_in_rule__PathSequence__Group__4__Impl5150); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2535:1: rule__PathSequence__Group_3__0 : rule__PathSequence__Group_3__0__Impl rule__PathSequence__Group_3__1 ;
    public final void rule__PathSequence__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2539:1: ( rule__PathSequence__Group_3__0__Impl rule__PathSequence__Group_3__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2540:2: rule__PathSequence__Group_3__0__Impl rule__PathSequence__Group_3__1
            {
            pushFollow(FOLLOW_rule__PathSequence__Group_3__0__Impl_in_rule__PathSequence__Group_3__05191);
            rule__PathSequence__Group_3__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PathSequence__Group_3__1_in_rule__PathSequence__Group_3__05194);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2547:1: rule__PathSequence__Group_3__0__Impl : ( '/' ) ;
    public final void rule__PathSequence__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2551:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2552:1: ( '/' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2552:1: ( '/' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2553:1: '/'
            {
             before(grammarAccess.getPathSequenceAccess().getSolidusKeyword_3_0()); 
            match(input,29,FOLLOW_29_in_rule__PathSequence__Group_3__0__Impl5222); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2566:1: rule__PathSequence__Group_3__1 : rule__PathSequence__Group_3__1__Impl ;
    public final void rule__PathSequence__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2570:1: ( rule__PathSequence__Group_3__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2571:2: rule__PathSequence__Group_3__1__Impl
            {
            pushFollow(FOLLOW_rule__PathSequence__Group_3__1__Impl_in_rule__PathSequence__Group_3__15253);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2577:1: rule__PathSequence__Group_3__1__Impl : ( rulePathElement ) ;
    public final void rule__PathSequence__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2581:1: ( ( rulePathElement ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2582:1: ( rulePathElement )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2582:1: ( rulePathElement )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2583:1: rulePathElement
            {
             before(grammarAccess.getPathSequenceAccess().getPathElementParserRuleCall_3_1()); 
            pushFollow(FOLLOW_rulePathElement_in_rule__PathSequence__Group_3__1__Impl5280);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2598:1: rule__ExistsTmlExpression__Group__0 : rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1 ;
    public final void rule__ExistsTmlExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2602:1: ( rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2603:2: rule__ExistsTmlExpression__Group__0__Impl rule__ExistsTmlExpression__Group__1
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__0__Impl_in_rule__ExistsTmlExpression__Group__05313);
            rule__ExistsTmlExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__1_in_rule__ExistsTmlExpression__Group__05316);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2610:1: rule__ExistsTmlExpression__Group__0__Impl : ( '?' ) ;
    public final void rule__ExistsTmlExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2614:1: ( ( '?' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2615:1: ( '?' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2615:1: ( '?' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2616:1: '?'
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getQuestionMarkKeyword_0()); 
            match(input,31,FOLLOW_31_in_rule__ExistsTmlExpression__Group__0__Impl5344); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2629:1: rule__ExistsTmlExpression__Group__1 : rule__ExistsTmlExpression__Group__1__Impl ;
    public final void rule__ExistsTmlExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2633:1: ( rule__ExistsTmlExpression__Group__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2634:2: rule__ExistsTmlExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ExistsTmlExpression__Group__1__Impl_in_rule__ExistsTmlExpression__Group__15375);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2640:1: rule__ExistsTmlExpression__Group__1__Impl : ( ruleTmlExpression ) ;
    public final void rule__ExistsTmlExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2644:1: ( ( ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2645:1: ( ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2645:1: ( ruleTmlExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2646:1: ruleTmlExpression
            {
             before(grammarAccess.getExistsTmlExpressionAccess().getTmlExpressionParserRuleCall_1()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_rule__ExistsTmlExpression__Group__1__Impl5402);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2661:1: rule__OrExpression__Group__0 : rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1 ;
    public final void rule__OrExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2665:1: ( rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2666:2: rule__OrExpression__Group__0__Impl rule__OrExpression__Group__1
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__0__Impl_in_rule__OrExpression__Group__05435);
            rule__OrExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group__1_in_rule__OrExpression__Group__05438);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2673:1: rule__OrExpression__Group__0__Impl : ( () ) ;
    public final void rule__OrExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2677:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2678:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2678:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2679:1: ()
            {
             before(grammarAccess.getOrExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2680:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2682:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2692:1: rule__OrExpression__Group__1 : rule__OrExpression__Group__1__Impl rule__OrExpression__Group__2 ;
    public final void rule__OrExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2696:1: ( rule__OrExpression__Group__1__Impl rule__OrExpression__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2697:2: rule__OrExpression__Group__1__Impl rule__OrExpression__Group__2
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__1__Impl_in_rule__OrExpression__Group__15496);
            rule__OrExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group__2_in_rule__OrExpression__Group__15499);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2704:1: rule__OrExpression__Group__1__Impl : ( ( rule__OrExpression__ParametersAssignment_1 ) ) ;
    public final void rule__OrExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2708:1: ( ( ( rule__OrExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2709:1: ( ( rule__OrExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2709:1: ( ( rule__OrExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2710:1: ( rule__OrExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2711:1: ( rule__OrExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2711:2: rule__OrExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__OrExpression__ParametersAssignment_1_in_rule__OrExpression__Group__1__Impl5526);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2721:1: rule__OrExpression__Group__2 : rule__OrExpression__Group__2__Impl ;
    public final void rule__OrExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2725:1: ( rule__OrExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2726:2: rule__OrExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__OrExpression__Group__2__Impl_in_rule__OrExpression__Group__25556);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2732:1: rule__OrExpression__Group__2__Impl : ( ( rule__OrExpression__Group_2__0 )* ) ;
    public final void rule__OrExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2736:1: ( ( ( rule__OrExpression__Group_2__0 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2737:1: ( ( rule__OrExpression__Group_2__0 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2737:1: ( ( rule__OrExpression__Group_2__0 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2738:1: ( rule__OrExpression__Group_2__0 )*
            {
             before(grammarAccess.getOrExpressionAccess().getGroup_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2739:1: ( rule__OrExpression__Group_2__0 )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==38) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2739:2: rule__OrExpression__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__OrExpression__Group_2__0_in_rule__OrExpression__Group__2__Impl5583);
            	    rule__OrExpression__Group_2__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop25;
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2755:1: rule__OrExpression__Group_2__0 : rule__OrExpression__Group_2__0__Impl rule__OrExpression__Group_2__1 ;
    public final void rule__OrExpression__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2759:1: ( rule__OrExpression__Group_2__0__Impl rule__OrExpression__Group_2__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2760:2: rule__OrExpression__Group_2__0__Impl rule__OrExpression__Group_2__1
            {
            pushFollow(FOLLOW_rule__OrExpression__Group_2__0__Impl_in_rule__OrExpression__Group_2__05620);
            rule__OrExpression__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__OrExpression__Group_2__1_in_rule__OrExpression__Group_2__05623);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2767:1: rule__OrExpression__Group_2__0__Impl : ( ( rule__OrExpression__OperationsAssignment_2_0 ) ) ;
    public final void rule__OrExpression__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2771:1: ( ( ( rule__OrExpression__OperationsAssignment_2_0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2772:1: ( ( rule__OrExpression__OperationsAssignment_2_0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2772:1: ( ( rule__OrExpression__OperationsAssignment_2_0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2773:1: ( rule__OrExpression__OperationsAssignment_2_0 )
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsAssignment_2_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2774:1: ( rule__OrExpression__OperationsAssignment_2_0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2774:2: rule__OrExpression__OperationsAssignment_2_0
            {
            pushFollow(FOLLOW_rule__OrExpression__OperationsAssignment_2_0_in_rule__OrExpression__Group_2__0__Impl5650);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2784:1: rule__OrExpression__Group_2__1 : rule__OrExpression__Group_2__1__Impl ;
    public final void rule__OrExpression__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2788:1: ( rule__OrExpression__Group_2__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2789:2: rule__OrExpression__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__OrExpression__Group_2__1__Impl_in_rule__OrExpression__Group_2__15680);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2795:1: rule__OrExpression__Group_2__1__Impl : ( ( rule__OrExpression__ParametersAssignment_2_1 ) ) ;
    public final void rule__OrExpression__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2799:1: ( ( ( rule__OrExpression__ParametersAssignment_2_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2800:1: ( ( rule__OrExpression__ParametersAssignment_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2800:1: ( ( rule__OrExpression__ParametersAssignment_2_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2801:1: ( rule__OrExpression__ParametersAssignment_2_1 )
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAssignment_2_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2802:1: ( rule__OrExpression__ParametersAssignment_2_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2802:2: rule__OrExpression__ParametersAssignment_2_1
            {
            pushFollow(FOLLOW_rule__OrExpression__ParametersAssignment_2_1_in_rule__OrExpression__Group_2__1__Impl5707);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2816:1: rule__AndExpression__Group__0 : rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1 ;
    public final void rule__AndExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2820:1: ( rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2821:2: rule__AndExpression__Group__0__Impl rule__AndExpression__Group__1
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__0__Impl_in_rule__AndExpression__Group__05741);
            rule__AndExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group__1_in_rule__AndExpression__Group__05744);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2828:1: rule__AndExpression__Group__0__Impl : ( () ) ;
    public final void rule__AndExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2832:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2833:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2833:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2834:1: ()
            {
             before(grammarAccess.getAndExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2835:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2837:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2847:1: rule__AndExpression__Group__1 : rule__AndExpression__Group__1__Impl rule__AndExpression__Group__2 ;
    public final void rule__AndExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2851:1: ( rule__AndExpression__Group__1__Impl rule__AndExpression__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2852:2: rule__AndExpression__Group__1__Impl rule__AndExpression__Group__2
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__1__Impl_in_rule__AndExpression__Group__15802);
            rule__AndExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group__2_in_rule__AndExpression__Group__15805);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2859:1: rule__AndExpression__Group__1__Impl : ( ( rule__AndExpression__ParametersAssignment_1 ) ) ;
    public final void rule__AndExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2863:1: ( ( ( rule__AndExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2864:1: ( ( rule__AndExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2864:1: ( ( rule__AndExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2865:1: ( rule__AndExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getAndExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2866:1: ( rule__AndExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2866:2: rule__AndExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__AndExpression__ParametersAssignment_1_in_rule__AndExpression__Group__1__Impl5832);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2876:1: rule__AndExpression__Group__2 : rule__AndExpression__Group__2__Impl ;
    public final void rule__AndExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2880:1: ( rule__AndExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2881:2: rule__AndExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__AndExpression__Group__2__Impl_in_rule__AndExpression__Group__25862);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2887:1: rule__AndExpression__Group__2__Impl : ( ( rule__AndExpression__Group_2__0 )* ) ;
    public final void rule__AndExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2891:1: ( ( ( rule__AndExpression__Group_2__0 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2892:1: ( ( rule__AndExpression__Group_2__0 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2892:1: ( ( rule__AndExpression__Group_2__0 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2893:1: ( rule__AndExpression__Group_2__0 )*
            {
             before(grammarAccess.getAndExpressionAccess().getGroup_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2894:1: ( rule__AndExpression__Group_2__0 )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==39) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2894:2: rule__AndExpression__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__AndExpression__Group_2__0_in_rule__AndExpression__Group__2__Impl5889);
            	    rule__AndExpression__Group_2__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop26;
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2910:1: rule__AndExpression__Group_2__0 : rule__AndExpression__Group_2__0__Impl rule__AndExpression__Group_2__1 ;
    public final void rule__AndExpression__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2914:1: ( rule__AndExpression__Group_2__0__Impl rule__AndExpression__Group_2__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2915:2: rule__AndExpression__Group_2__0__Impl rule__AndExpression__Group_2__1
            {
            pushFollow(FOLLOW_rule__AndExpression__Group_2__0__Impl_in_rule__AndExpression__Group_2__05926);
            rule__AndExpression__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AndExpression__Group_2__1_in_rule__AndExpression__Group_2__05929);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2922:1: rule__AndExpression__Group_2__0__Impl : ( ( rule__AndExpression__OperationsAssignment_2_0 ) ) ;
    public final void rule__AndExpression__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2926:1: ( ( ( rule__AndExpression__OperationsAssignment_2_0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2927:1: ( ( rule__AndExpression__OperationsAssignment_2_0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2927:1: ( ( rule__AndExpression__OperationsAssignment_2_0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2928:1: ( rule__AndExpression__OperationsAssignment_2_0 )
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsAssignment_2_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2929:1: ( rule__AndExpression__OperationsAssignment_2_0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2929:2: rule__AndExpression__OperationsAssignment_2_0
            {
            pushFollow(FOLLOW_rule__AndExpression__OperationsAssignment_2_0_in_rule__AndExpression__Group_2__0__Impl5956);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2939:1: rule__AndExpression__Group_2__1 : rule__AndExpression__Group_2__1__Impl ;
    public final void rule__AndExpression__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2943:1: ( rule__AndExpression__Group_2__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2944:2: rule__AndExpression__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__AndExpression__Group_2__1__Impl_in_rule__AndExpression__Group_2__15986);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2950:1: rule__AndExpression__Group_2__1__Impl : ( ( rule__AndExpression__ParametersAssignment_2_1 ) ) ;
    public final void rule__AndExpression__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2954:1: ( ( ( rule__AndExpression__ParametersAssignment_2_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2955:1: ( ( rule__AndExpression__ParametersAssignment_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2955:1: ( ( rule__AndExpression__ParametersAssignment_2_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2956:1: ( rule__AndExpression__ParametersAssignment_2_1 )
            {
             before(grammarAccess.getAndExpressionAccess().getParametersAssignment_2_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2957:1: ( rule__AndExpression__ParametersAssignment_2_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2957:2: rule__AndExpression__ParametersAssignment_2_1
            {
            pushFollow(FOLLOW_rule__AndExpression__ParametersAssignment_2_1_in_rule__AndExpression__Group_2__1__Impl6013);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2971:1: rule__EqualityExpression__Group__0 : rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1 ;
    public final void rule__EqualityExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2975:1: ( rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2976:2: rule__EqualityExpression__Group__0__Impl rule__EqualityExpression__Group__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__0__Impl_in_rule__EqualityExpression__Group__06047);
            rule__EqualityExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group__1_in_rule__EqualityExpression__Group__06050);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2983:1: rule__EqualityExpression__Group__0__Impl : ( () ) ;
    public final void rule__EqualityExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2987:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2988:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2988:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2989:1: ()
            {
             before(grammarAccess.getEqualityExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2990:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:2992:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3002:1: rule__EqualityExpression__Group__1 : rule__EqualityExpression__Group__1__Impl rule__EqualityExpression__Group__2 ;
    public final void rule__EqualityExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3006:1: ( rule__EqualityExpression__Group__1__Impl rule__EqualityExpression__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3007:2: rule__EqualityExpression__Group__1__Impl rule__EqualityExpression__Group__2
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__1__Impl_in_rule__EqualityExpression__Group__16108);
            rule__EqualityExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group__2_in_rule__EqualityExpression__Group__16111);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3014:1: rule__EqualityExpression__Group__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_1 ) ) ;
    public final void rule__EqualityExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3018:1: ( ( ( rule__EqualityExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3019:1: ( ( rule__EqualityExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3019:1: ( ( rule__EqualityExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3020:1: ( rule__EqualityExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3021:1: ( rule__EqualityExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3021:2: rule__EqualityExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_1_in_rule__EqualityExpression__Group__1__Impl6138);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3031:1: rule__EqualityExpression__Group__2 : rule__EqualityExpression__Group__2__Impl ;
    public final void rule__EqualityExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3035:1: ( rule__EqualityExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3036:2: rule__EqualityExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group__2__Impl_in_rule__EqualityExpression__Group__26168);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3042:1: rule__EqualityExpression__Group__2__Impl : ( ( rule__EqualityExpression__Alternatives_2 )? ) ;
    public final void rule__EqualityExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3046:1: ( ( ( rule__EqualityExpression__Alternatives_2 )? ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3047:1: ( ( rule__EqualityExpression__Alternatives_2 )? )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3047:1: ( ( rule__EqualityExpression__Alternatives_2 )? )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3048:1: ( rule__EqualityExpression__Alternatives_2 )?
            {
             before(grammarAccess.getEqualityExpressionAccess().getAlternatives_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3049:1: ( rule__EqualityExpression__Alternatives_2 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=40 && LA27_0<=41)) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3049:2: rule__EqualityExpression__Alternatives_2
                    {
                    pushFollow(FOLLOW_rule__EqualityExpression__Alternatives_2_in_rule__EqualityExpression__Group__2__Impl6195);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3065:1: rule__EqualityExpression__Group_2_0__0 : rule__EqualityExpression__Group_2_0__0__Impl rule__EqualityExpression__Group_2_0__1 ;
    public final void rule__EqualityExpression__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3069:1: ( rule__EqualityExpression__Group_2_0__0__Impl rule__EqualityExpression__Group_2_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3070:2: rule__EqualityExpression__Group_2_0__0__Impl rule__EqualityExpression__Group_2_0__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_0__0__Impl_in_rule__EqualityExpression__Group_2_0__06232);
            rule__EqualityExpression__Group_2_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_0__1_in_rule__EqualityExpression__Group_2_0__06235);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3077:1: rule__EqualityExpression__Group_2_0__0__Impl : ( ( rule__EqualityExpression__OperationsAssignment_2_0_0 ) ) ;
    public final void rule__EqualityExpression__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3081:1: ( ( ( rule__EqualityExpression__OperationsAssignment_2_0_0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3082:1: ( ( rule__EqualityExpression__OperationsAssignment_2_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3082:1: ( ( rule__EqualityExpression__OperationsAssignment_2_0_0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3083:1: ( rule__EqualityExpression__OperationsAssignment_2_0_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_2_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3084:1: ( rule__EqualityExpression__OperationsAssignment_2_0_0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3084:2: rule__EqualityExpression__OperationsAssignment_2_0_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__OperationsAssignment_2_0_0_in_rule__EqualityExpression__Group_2_0__0__Impl6262);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3094:1: rule__EqualityExpression__Group_2_0__1 : rule__EqualityExpression__Group_2_0__1__Impl ;
    public final void rule__EqualityExpression__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3098:1: ( rule__EqualityExpression__Group_2_0__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3099:2: rule__EqualityExpression__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_0__1__Impl_in_rule__EqualityExpression__Group_2_0__16292);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3105:1: rule__EqualityExpression__Group_2_0__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_2_0_1 ) ) ;
    public final void rule__EqualityExpression__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3109:1: ( ( ( rule__EqualityExpression__ParametersAssignment_2_0_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3110:1: ( ( rule__EqualityExpression__ParametersAssignment_2_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3110:1: ( ( rule__EqualityExpression__ParametersAssignment_2_0_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3111:1: ( rule__EqualityExpression__ParametersAssignment_2_0_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_2_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3112:1: ( rule__EqualityExpression__ParametersAssignment_2_0_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3112:2: rule__EqualityExpression__ParametersAssignment_2_0_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_2_0_1_in_rule__EqualityExpression__Group_2_0__1__Impl6319);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3126:1: rule__EqualityExpression__Group_2_1__0 : rule__EqualityExpression__Group_2_1__0__Impl rule__EqualityExpression__Group_2_1__1 ;
    public final void rule__EqualityExpression__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3130:1: ( rule__EqualityExpression__Group_2_1__0__Impl rule__EqualityExpression__Group_2_1__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3131:2: rule__EqualityExpression__Group_2_1__0__Impl rule__EqualityExpression__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_1__0__Impl_in_rule__EqualityExpression__Group_2_1__06353);
            rule__EqualityExpression__Group_2_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_1__1_in_rule__EqualityExpression__Group_2_1__06356);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3138:1: rule__EqualityExpression__Group_2_1__0__Impl : ( ( rule__EqualityExpression__OperationsAssignment_2_1_0 ) ) ;
    public final void rule__EqualityExpression__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3142:1: ( ( ( rule__EqualityExpression__OperationsAssignment_2_1_0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3143:1: ( ( rule__EqualityExpression__OperationsAssignment_2_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3143:1: ( ( rule__EqualityExpression__OperationsAssignment_2_1_0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3144:1: ( rule__EqualityExpression__OperationsAssignment_2_1_0 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsAssignment_2_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3145:1: ( rule__EqualityExpression__OperationsAssignment_2_1_0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3145:2: rule__EqualityExpression__OperationsAssignment_2_1_0
            {
            pushFollow(FOLLOW_rule__EqualityExpression__OperationsAssignment_2_1_0_in_rule__EqualityExpression__Group_2_1__0__Impl6383);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3155:1: rule__EqualityExpression__Group_2_1__1 : rule__EqualityExpression__Group_2_1__1__Impl ;
    public final void rule__EqualityExpression__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3159:1: ( rule__EqualityExpression__Group_2_1__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3160:2: rule__EqualityExpression__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__EqualityExpression__Group_2_1__1__Impl_in_rule__EqualityExpression__Group_2_1__16413);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3166:1: rule__EqualityExpression__Group_2_1__1__Impl : ( ( rule__EqualityExpression__ParametersAssignment_2_1_1 ) ) ;
    public final void rule__EqualityExpression__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3170:1: ( ( ( rule__EqualityExpression__ParametersAssignment_2_1_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3171:1: ( ( rule__EqualityExpression__ParametersAssignment_2_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3171:1: ( ( rule__EqualityExpression__ParametersAssignment_2_1_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3172:1: ( rule__EqualityExpression__ParametersAssignment_2_1_1 )
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAssignment_2_1_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3173:1: ( rule__EqualityExpression__ParametersAssignment_2_1_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3173:2: rule__EqualityExpression__ParametersAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__EqualityExpression__ParametersAssignment_2_1_1_in_rule__EqualityExpression__Group_2_1__1__Impl6440);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3187:1: rule__AdditiveExpression__Group__0 : rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1 ;
    public final void rule__AdditiveExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3191:1: ( rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3192:2: rule__AdditiveExpression__Group__0__Impl rule__AdditiveExpression__Group__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__0__Impl_in_rule__AdditiveExpression__Group__06474);
            rule__AdditiveExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group__1_in_rule__AdditiveExpression__Group__06477);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3199:1: rule__AdditiveExpression__Group__0__Impl : ( () ) ;
    public final void rule__AdditiveExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3203:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3204:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3204:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3205:1: ()
            {
             before(grammarAccess.getAdditiveExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3206:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3208:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3218:1: rule__AdditiveExpression__Group__1 : rule__AdditiveExpression__Group__1__Impl rule__AdditiveExpression__Group__2 ;
    public final void rule__AdditiveExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3222:1: ( rule__AdditiveExpression__Group__1__Impl rule__AdditiveExpression__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3223:2: rule__AdditiveExpression__Group__1__Impl rule__AdditiveExpression__Group__2
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__1__Impl_in_rule__AdditiveExpression__Group__16535);
            rule__AdditiveExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group__2_in_rule__AdditiveExpression__Group__16538);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3230:1: rule__AdditiveExpression__Group__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_1 ) ) ;
    public final void rule__AdditiveExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3234:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3235:1: ( ( rule__AdditiveExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3235:1: ( ( rule__AdditiveExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3236:1: ( rule__AdditiveExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3237:1: ( rule__AdditiveExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3237:2: rule__AdditiveExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_in_rule__AdditiveExpression__Group__1__Impl6565);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3247:1: rule__AdditiveExpression__Group__2 : rule__AdditiveExpression__Group__2__Impl ;
    public final void rule__AdditiveExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3251:1: ( rule__AdditiveExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3252:2: rule__AdditiveExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group__2__Impl_in_rule__AdditiveExpression__Group__26595);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3258:1: rule__AdditiveExpression__Group__2__Impl : ( ( rule__AdditiveExpression__Alternatives_2 )* ) ;
    public final void rule__AdditiveExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3262:1: ( ( ( rule__AdditiveExpression__Alternatives_2 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3263:1: ( ( rule__AdditiveExpression__Alternatives_2 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3263:1: ( ( rule__AdditiveExpression__Alternatives_2 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3264:1: ( rule__AdditiveExpression__Alternatives_2 )*
            {
             before(grammarAccess.getAdditiveExpressionAccess().getAlternatives_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3265:1: ( rule__AdditiveExpression__Alternatives_2 )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>=32 && LA28_0<=33)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3265:2: rule__AdditiveExpression__Alternatives_2
            	    {
            	    pushFollow(FOLLOW_rule__AdditiveExpression__Alternatives_2_in_rule__AdditiveExpression__Group__2__Impl6622);
            	    rule__AdditiveExpression__Alternatives_2();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop28;
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3281:1: rule__AdditiveExpression__Group_2_0__0 : rule__AdditiveExpression__Group_2_0__0__Impl rule__AdditiveExpression__Group_2_0__1 ;
    public final void rule__AdditiveExpression__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3285:1: ( rule__AdditiveExpression__Group_2_0__0__Impl rule__AdditiveExpression__Group_2_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3286:2: rule__AdditiveExpression__Group_2_0__0__Impl rule__AdditiveExpression__Group_2_0__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_0__0__Impl_in_rule__AdditiveExpression__Group_2_0__06659);
            rule__AdditiveExpression__Group_2_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_0__1_in_rule__AdditiveExpression__Group_2_0__06662);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3293:1: rule__AdditiveExpression__Group_2_0__0__Impl : ( '+' ) ;
    public final void rule__AdditiveExpression__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3297:1: ( ( '+' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3298:1: ( '+' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3298:1: ( '+' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3299:1: '+'
            {
             before(grammarAccess.getAdditiveExpressionAccess().getPlusSignKeyword_2_0_0()); 
            match(input,32,FOLLOW_32_in_rule__AdditiveExpression__Group_2_0__0__Impl6690); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3312:1: rule__AdditiveExpression__Group_2_0__1 : rule__AdditiveExpression__Group_2_0__1__Impl ;
    public final void rule__AdditiveExpression__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3316:1: ( rule__AdditiveExpression__Group_2_0__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3317:2: rule__AdditiveExpression__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_0__1__Impl_in_rule__AdditiveExpression__Group_2_0__16721);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3323:1: rule__AdditiveExpression__Group_2_0__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_2_0_1 ) ) ;
    public final void rule__AdditiveExpression__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3327:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_2_0_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3328:1: ( ( rule__AdditiveExpression__ParametersAssignment_2_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3328:1: ( ( rule__AdditiveExpression__ParametersAssignment_2_0_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3329:1: ( rule__AdditiveExpression__ParametersAssignment_2_0_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_2_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3330:1: ( rule__AdditiveExpression__ParametersAssignment_2_0_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3330:2: rule__AdditiveExpression__ParametersAssignment_2_0_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_2_0_1_in_rule__AdditiveExpression__Group_2_0__1__Impl6748);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3344:1: rule__AdditiveExpression__Group_2_1__0 : rule__AdditiveExpression__Group_2_1__0__Impl rule__AdditiveExpression__Group_2_1__1 ;
    public final void rule__AdditiveExpression__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3348:1: ( rule__AdditiveExpression__Group_2_1__0__Impl rule__AdditiveExpression__Group_2_1__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3349:2: rule__AdditiveExpression__Group_2_1__0__Impl rule__AdditiveExpression__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_1__0__Impl_in_rule__AdditiveExpression__Group_2_1__06782);
            rule__AdditiveExpression__Group_2_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_1__1_in_rule__AdditiveExpression__Group_2_1__06785);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3356:1: rule__AdditiveExpression__Group_2_1__0__Impl : ( '-' ) ;
    public final void rule__AdditiveExpression__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3360:1: ( ( '-' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3361:1: ( '-' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3361:1: ( '-' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3362:1: '-'
            {
             before(grammarAccess.getAdditiveExpressionAccess().getHyphenMinusKeyword_2_1_0()); 
            match(input,33,FOLLOW_33_in_rule__AdditiveExpression__Group_2_1__0__Impl6813); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3375:1: rule__AdditiveExpression__Group_2_1__1 : rule__AdditiveExpression__Group_2_1__1__Impl ;
    public final void rule__AdditiveExpression__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3379:1: ( rule__AdditiveExpression__Group_2_1__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3380:2: rule__AdditiveExpression__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__Group_2_1__1__Impl_in_rule__AdditiveExpression__Group_2_1__16844);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3386:1: rule__AdditiveExpression__Group_2_1__1__Impl : ( ( rule__AdditiveExpression__ParametersAssignment_2_1_1 ) ) ;
    public final void rule__AdditiveExpression__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3390:1: ( ( ( rule__AdditiveExpression__ParametersAssignment_2_1_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3391:1: ( ( rule__AdditiveExpression__ParametersAssignment_2_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3391:1: ( ( rule__AdditiveExpression__ParametersAssignment_2_1_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3392:1: ( rule__AdditiveExpression__ParametersAssignment_2_1_1 )
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersAssignment_2_1_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3393:1: ( rule__AdditiveExpression__ParametersAssignment_2_1_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3393:2: rule__AdditiveExpression__ParametersAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__AdditiveExpression__ParametersAssignment_2_1_1_in_rule__AdditiveExpression__Group_2_1__1__Impl6871);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3407:1: rule__MultiplicativeExpression__Group__0 : rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1 ;
    public final void rule__MultiplicativeExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3411:1: ( rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3412:2: rule__MultiplicativeExpression__Group__0__Impl rule__MultiplicativeExpression__Group__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__0__Impl_in_rule__MultiplicativeExpression__Group__06905);
            rule__MultiplicativeExpression__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__1_in_rule__MultiplicativeExpression__Group__06908);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3419:1: rule__MultiplicativeExpression__Group__0__Impl : ( () ) ;
    public final void rule__MultiplicativeExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3423:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3424:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3424:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3425:1: ()
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getExpressionAction_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3426:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3428:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3438:1: rule__MultiplicativeExpression__Group__1 : rule__MultiplicativeExpression__Group__1__Impl rule__MultiplicativeExpression__Group__2 ;
    public final void rule__MultiplicativeExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3442:1: ( rule__MultiplicativeExpression__Group__1__Impl rule__MultiplicativeExpression__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3443:2: rule__MultiplicativeExpression__Group__1__Impl rule__MultiplicativeExpression__Group__2
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__1__Impl_in_rule__MultiplicativeExpression__Group__16966);
            rule__MultiplicativeExpression__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__2_in_rule__MultiplicativeExpression__Group__16969);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3450:1: rule__MultiplicativeExpression__Group__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3454:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3455:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3455:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3456:1: ( rule__MultiplicativeExpression__ParametersAssignment_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3457:1: ( rule__MultiplicativeExpression__ParametersAssignment_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3457:2: rule__MultiplicativeExpression__ParametersAssignment_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_in_rule__MultiplicativeExpression__Group__1__Impl6996);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3467:1: rule__MultiplicativeExpression__Group__2 : rule__MultiplicativeExpression__Group__2__Impl ;
    public final void rule__MultiplicativeExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3471:1: ( rule__MultiplicativeExpression__Group__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3472:2: rule__MultiplicativeExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group__2__Impl_in_rule__MultiplicativeExpression__Group__27026);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3478:1: rule__MultiplicativeExpression__Group__2__Impl : ( ( rule__MultiplicativeExpression__Alternatives_2 )* ) ;
    public final void rule__MultiplicativeExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3482:1: ( ( ( rule__MultiplicativeExpression__Alternatives_2 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3483:1: ( ( rule__MultiplicativeExpression__Alternatives_2 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3483:1: ( ( rule__MultiplicativeExpression__Alternatives_2 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3484:1: ( rule__MultiplicativeExpression__Alternatives_2 )*
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getAlternatives_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3485:1: ( rule__MultiplicativeExpression__Alternatives_2 )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==29||LA29_0==42) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3485:2: rule__MultiplicativeExpression__Alternatives_2
            	    {
            	    pushFollow(FOLLOW_rule__MultiplicativeExpression__Alternatives_2_in_rule__MultiplicativeExpression__Group__2__Impl7053);
            	    rule__MultiplicativeExpression__Alternatives_2();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop29;
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3501:1: rule__MultiplicativeExpression__Group_2_0__0 : rule__MultiplicativeExpression__Group_2_0__0__Impl rule__MultiplicativeExpression__Group_2_0__1 ;
    public final void rule__MultiplicativeExpression__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3505:1: ( rule__MultiplicativeExpression__Group_2_0__0__Impl rule__MultiplicativeExpression__Group_2_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3506:2: rule__MultiplicativeExpression__Group_2_0__0__Impl rule__MultiplicativeExpression__Group_2_0__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_0__0__Impl_in_rule__MultiplicativeExpression__Group_2_0__07090);
            rule__MultiplicativeExpression__Group_2_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_0__1_in_rule__MultiplicativeExpression__Group_2_0__07093);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3513:1: rule__MultiplicativeExpression__Group_2_0__0__Impl : ( ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3517:1: ( ( ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3518:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3518:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3519:1: ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_2_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3520:1: ( rule__MultiplicativeExpression__OperationsAssignment_2_0_0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3520:2: rule__MultiplicativeExpression__OperationsAssignment_2_0_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_2_0_0_in_rule__MultiplicativeExpression__Group_2_0__0__Impl7120);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3530:1: rule__MultiplicativeExpression__Group_2_0__1 : rule__MultiplicativeExpression__Group_2_0__1__Impl ;
    public final void rule__MultiplicativeExpression__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3534:1: ( rule__MultiplicativeExpression__Group_2_0__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3535:2: rule__MultiplicativeExpression__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_0__1__Impl_in_rule__MultiplicativeExpression__Group_2_0__17150);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3541:1: rule__MultiplicativeExpression__Group_2_0__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3545:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3546:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3546:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3547:1: ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_2_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3548:1: ( rule__MultiplicativeExpression__ParametersAssignment_2_0_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3548:2: rule__MultiplicativeExpression__ParametersAssignment_2_0_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_2_0_1_in_rule__MultiplicativeExpression__Group_2_0__1__Impl7177);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3562:1: rule__MultiplicativeExpression__Group_2_1__0 : rule__MultiplicativeExpression__Group_2_1__0__Impl rule__MultiplicativeExpression__Group_2_1__1 ;
    public final void rule__MultiplicativeExpression__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3566:1: ( rule__MultiplicativeExpression__Group_2_1__0__Impl rule__MultiplicativeExpression__Group_2_1__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3567:2: rule__MultiplicativeExpression__Group_2_1__0__Impl rule__MultiplicativeExpression__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_1__0__Impl_in_rule__MultiplicativeExpression__Group_2_1__07211);
            rule__MultiplicativeExpression__Group_2_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_1__1_in_rule__MultiplicativeExpression__Group_2_1__07214);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3574:1: rule__MultiplicativeExpression__Group_2_1__0__Impl : ( ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 ) ) ;
    public final void rule__MultiplicativeExpression__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3578:1: ( ( ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3579:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3579:1: ( ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3580:1: ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAssignment_2_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3581:1: ( rule__MultiplicativeExpression__OperationsAssignment_2_1_0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3581:2: rule__MultiplicativeExpression__OperationsAssignment_2_1_0
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_2_1_0_in_rule__MultiplicativeExpression__Group_2_1__0__Impl7241);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3591:1: rule__MultiplicativeExpression__Group_2_1__1 : rule__MultiplicativeExpression__Group_2_1__1__Impl ;
    public final void rule__MultiplicativeExpression__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3595:1: ( rule__MultiplicativeExpression__Group_2_1__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3596:2: rule__MultiplicativeExpression__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__Group_2_1__1__Impl_in_rule__MultiplicativeExpression__Group_2_1__17271);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3602:1: rule__MultiplicativeExpression__Group_2_1__1__Impl : ( ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 ) ) ;
    public final void rule__MultiplicativeExpression__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3606:1: ( ( ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3607:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3607:1: ( ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3608:1: ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersAssignment_2_1_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3609:1: ( rule__MultiplicativeExpression__ParametersAssignment_2_1_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3609:2: rule__MultiplicativeExpression__ParametersAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_2_1_1_in_rule__MultiplicativeExpression__Group_2_1__1__Impl7298);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3623:1: rule__UnaryExpression__Group_0__0 : rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1 ;
    public final void rule__UnaryExpression__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3627:1: ( rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3628:2: rule__UnaryExpression__Group_0__0__Impl rule__UnaryExpression__Group_0__1
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__0__Impl_in_rule__UnaryExpression__Group_0__07332);
            rule__UnaryExpression__Group_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__1_in_rule__UnaryExpression__Group_0__07335);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3635:1: rule__UnaryExpression__Group_0__0__Impl : ( () ) ;
    public final void rule__UnaryExpression__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3639:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3640:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3640:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3641:1: ()
            {
             before(grammarAccess.getUnaryExpressionAccess().getExpressionAction_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3642:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3644:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3654:1: rule__UnaryExpression__Group_0__1 : rule__UnaryExpression__Group_0__1__Impl rule__UnaryExpression__Group_0__2 ;
    public final void rule__UnaryExpression__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3658:1: ( rule__UnaryExpression__Group_0__1__Impl rule__UnaryExpression__Group_0__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3659:2: rule__UnaryExpression__Group_0__1__Impl rule__UnaryExpression__Group_0__2
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__1__Impl_in_rule__UnaryExpression__Group_0__17393);
            rule__UnaryExpression__Group_0__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__2_in_rule__UnaryExpression__Group_0__17396);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3666:1: rule__UnaryExpression__Group_0__1__Impl : ( ( rule__UnaryExpression__OperationsAssignment_0_1 ) ) ;
    public final void rule__UnaryExpression__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3670:1: ( ( ( rule__UnaryExpression__OperationsAssignment_0_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3671:1: ( ( rule__UnaryExpression__OperationsAssignment_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3671:1: ( ( rule__UnaryExpression__OperationsAssignment_0_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3672:1: ( rule__UnaryExpression__OperationsAssignment_0_1 )
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsAssignment_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3673:1: ( rule__UnaryExpression__OperationsAssignment_0_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3673:2: rule__UnaryExpression__OperationsAssignment_0_1
            {
            pushFollow(FOLLOW_rule__UnaryExpression__OperationsAssignment_0_1_in_rule__UnaryExpression__Group_0__1__Impl7423);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3683:1: rule__UnaryExpression__Group_0__2 : rule__UnaryExpression__Group_0__2__Impl ;
    public final void rule__UnaryExpression__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3687:1: ( rule__UnaryExpression__Group_0__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3688:2: rule__UnaryExpression__Group_0__2__Impl
            {
            pushFollow(FOLLOW_rule__UnaryExpression__Group_0__2__Impl_in_rule__UnaryExpression__Group_0__27453);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3694:1: rule__UnaryExpression__Group_0__2__Impl : ( ( rule__UnaryExpression__ParametersAssignment_0_2 ) ) ;
    public final void rule__UnaryExpression__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3698:1: ( ( ( rule__UnaryExpression__ParametersAssignment_0_2 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3699:1: ( ( rule__UnaryExpression__ParametersAssignment_0_2 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3699:1: ( ( rule__UnaryExpression__ParametersAssignment_0_2 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3700:1: ( rule__UnaryExpression__ParametersAssignment_0_2 )
            {
             before(grammarAccess.getUnaryExpressionAccess().getParametersAssignment_0_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3701:1: ( rule__UnaryExpression__ParametersAssignment_0_2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3701:2: rule__UnaryExpression__ParametersAssignment_0_2
            {
            pushFollow(FOLLOW_rule__UnaryExpression__ParametersAssignment_0_2_in_rule__UnaryExpression__Group_0__2__Impl7480);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3717:1: rule__PrimaryExpression__Group_0__0 : rule__PrimaryExpression__Group_0__0__Impl rule__PrimaryExpression__Group_0__1 ;
    public final void rule__PrimaryExpression__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3721:1: ( rule__PrimaryExpression__Group_0__0__Impl rule__PrimaryExpression__Group_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3722:2: rule__PrimaryExpression__Group_0__0__Impl rule__PrimaryExpression__Group_0__1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_0__0__Impl_in_rule__PrimaryExpression__Group_0__07516);
            rule__PrimaryExpression__Group_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_0__1_in_rule__PrimaryExpression__Group_0__07519);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3729:1: rule__PrimaryExpression__Group_0__0__Impl : ( () ) ;
    public final void rule__PrimaryExpression__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3733:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3734:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3734:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3735:1: ()
            {
             before(grammarAccess.getPrimaryExpressionAccess().getExpressionAction_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3736:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3738:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3748:1: rule__PrimaryExpression__Group_0__1 : rule__PrimaryExpression__Group_0__1__Impl ;
    public final void rule__PrimaryExpression__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3752:1: ( rule__PrimaryExpression__Group_0__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3753:2: rule__PrimaryExpression__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_0__1__Impl_in_rule__PrimaryExpression__Group_0__17577);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3759:1: rule__PrimaryExpression__Group_0__1__Impl : ( ( rule__PrimaryExpression__ParametersAssignment_0_1 ) ) ;
    public final void rule__PrimaryExpression__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3763:1: ( ( ( rule__PrimaryExpression__ParametersAssignment_0_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3764:1: ( ( rule__PrimaryExpression__ParametersAssignment_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3764:1: ( ( rule__PrimaryExpression__ParametersAssignment_0_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3765:1: ( rule__PrimaryExpression__ParametersAssignment_0_1 )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3766:1: ( rule__PrimaryExpression__ParametersAssignment_0_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3766:2: rule__PrimaryExpression__ParametersAssignment_0_1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__ParametersAssignment_0_1_in_rule__PrimaryExpression__Group_0__1__Impl7604);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3780:1: rule__PrimaryExpression__Group_1__0 : rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1 ;
    public final void rule__PrimaryExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3784:1: ( rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3785:2: rule__PrimaryExpression__Group_1__0__Impl rule__PrimaryExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__0__Impl_in_rule__PrimaryExpression__Group_1__07638);
            rule__PrimaryExpression__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__1_in_rule__PrimaryExpression__Group_1__07641);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3792:1: rule__PrimaryExpression__Group_1__0__Impl : ( '(' ) ;
    public final void rule__PrimaryExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3796:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3797:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3797:1: ( '(' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3798:1: '('
            {
             before(grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_1_0()); 
            match(input,34,FOLLOW_34_in_rule__PrimaryExpression__Group_1__0__Impl7669); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3811:1: rule__PrimaryExpression__Group_1__1 : rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2 ;
    public final void rule__PrimaryExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3815:1: ( rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3816:2: rule__PrimaryExpression__Group_1__1__Impl rule__PrimaryExpression__Group_1__2
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__1__Impl_in_rule__PrimaryExpression__Group_1__17700);
            rule__PrimaryExpression__Group_1__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__2_in_rule__PrimaryExpression__Group_1__17703);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3823:1: rule__PrimaryExpression__Group_1__1__Impl : ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) ) ;
    public final void rule__PrimaryExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3827:1: ( ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3828:1: ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3828:1: ( ( rule__PrimaryExpression__ParametersAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3829:1: ( rule__PrimaryExpression__ParametersAssignment_1_1 )
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3830:1: ( rule__PrimaryExpression__ParametersAssignment_1_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3830:2: rule__PrimaryExpression__ParametersAssignment_1_1
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__ParametersAssignment_1_1_in_rule__PrimaryExpression__Group_1__1__Impl7730);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3840:1: rule__PrimaryExpression__Group_1__2 : rule__PrimaryExpression__Group_1__2__Impl ;
    public final void rule__PrimaryExpression__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3844:1: ( rule__PrimaryExpression__Group_1__2__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3845:2: rule__PrimaryExpression__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__PrimaryExpression__Group_1__2__Impl_in_rule__PrimaryExpression__Group_1__27760);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3851:1: rule__PrimaryExpression__Group_1__2__Impl : ( ')' ) ;
    public final void rule__PrimaryExpression__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3855:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3856:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3856:1: ( ')' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3857:1: ')'
            {
             before(grammarAccess.getPrimaryExpressionAccess().getRightParenthesisKeyword_1_2()); 
            match(input,35,FOLLOW_35_in_rule__PrimaryExpression__Group_1__2__Impl7788); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3876:1: rule__FunctionCall__Group__0 : rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1 ;
    public final void rule__FunctionCall__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3880:1: ( rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3881:2: rule__FunctionCall__Group__0__Impl rule__FunctionCall__Group__1
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__0__Impl_in_rule__FunctionCall__Group__07825);
            rule__FunctionCall__Group__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__1_in_rule__FunctionCall__Group__07828);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3888:1: rule__FunctionCall__Group__0__Impl : ( ( rule__FunctionCall__NameAssignment_0 ) ) ;
    public final void rule__FunctionCall__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3892:1: ( ( ( rule__FunctionCall__NameAssignment_0 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3893:1: ( ( rule__FunctionCall__NameAssignment_0 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3893:1: ( ( rule__FunctionCall__NameAssignment_0 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3894:1: ( rule__FunctionCall__NameAssignment_0 )
            {
             before(grammarAccess.getFunctionCallAccess().getNameAssignment_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3895:1: ( rule__FunctionCall__NameAssignment_0 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3895:2: rule__FunctionCall__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__FunctionCall__NameAssignment_0_in_rule__FunctionCall__Group__0__Impl7855);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3905:1: rule__FunctionCall__Group__1 : rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2 ;
    public final void rule__FunctionCall__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3909:1: ( rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3910:2: rule__FunctionCall__Group__1__Impl rule__FunctionCall__Group__2
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__1__Impl_in_rule__FunctionCall__Group__17885);
            rule__FunctionCall__Group__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__2_in_rule__FunctionCall__Group__17888);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3917:1: rule__FunctionCall__Group__1__Impl : ( '(' ) ;
    public final void rule__FunctionCall__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3921:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3922:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3922:1: ( '(' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3923:1: '('
            {
             before(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1()); 
            match(input,34,FOLLOW_34_in_rule__FunctionCall__Group__1__Impl7916); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3936:1: rule__FunctionCall__Group__2 : rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3 ;
    public final void rule__FunctionCall__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3940:1: ( rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3941:2: rule__FunctionCall__Group__2__Impl rule__FunctionCall__Group__3
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__2__Impl_in_rule__FunctionCall__Group__27947);
            rule__FunctionCall__Group__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__FunctionCall__Group__3_in_rule__FunctionCall__Group__27950);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3948:1: rule__FunctionCall__Group__2__Impl : ( ( rule__FunctionCall__OperandsAssignment_2 ) ) ;
    public final void rule__FunctionCall__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3952:1: ( ( ( rule__FunctionCall__OperandsAssignment_2 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3953:1: ( ( rule__FunctionCall__OperandsAssignment_2 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3953:1: ( ( rule__FunctionCall__OperandsAssignment_2 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3954:1: ( rule__FunctionCall__OperandsAssignment_2 )
            {
             before(grammarAccess.getFunctionCallAccess().getOperandsAssignment_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3955:1: ( rule__FunctionCall__OperandsAssignment_2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3955:2: rule__FunctionCall__OperandsAssignment_2
            {
            pushFollow(FOLLOW_rule__FunctionCall__OperandsAssignment_2_in_rule__FunctionCall__Group__2__Impl7977);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3965:1: rule__FunctionCall__Group__3 : rule__FunctionCall__Group__3__Impl ;
    public final void rule__FunctionCall__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3969:1: ( rule__FunctionCall__Group__3__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3970:2: rule__FunctionCall__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__FunctionCall__Group__3__Impl_in_rule__FunctionCall__Group__38007);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3976:1: rule__FunctionCall__Group__3__Impl : ( ')' ) ;
    public final void rule__FunctionCall__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3980:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3981:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3981:1: ( ')' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:3982:1: ')'
            {
             before(grammarAccess.getFunctionCallAccess().getRightParenthesisKeyword_3()); 
            match(input,35,FOLLOW_35_in_rule__FunctionCall__Group__3__Impl8035); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4005:1: rule__Literal__Group_0__0 : rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1 ;
    public final void rule__Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4009:1: ( rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4010:2: rule__Literal__Group_0__0__Impl rule__Literal__Group_0__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_0__0__Impl_in_rule__Literal__Group_0__08076);
            rule__Literal__Group_0__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_0__1_in_rule__Literal__Group_0__08079);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4017:1: rule__Literal__Group_0__0__Impl : ( () ) ;
    public final void rule__Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4021:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4022:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4022:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4023:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4024:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4026:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4036:1: rule__Literal__Group_0__1 : rule__Literal__Group_0__1__Impl ;
    public final void rule__Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4040:1: ( rule__Literal__Group_0__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4041:2: rule__Literal__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_0__1__Impl_in_rule__Literal__Group_0__18137);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4047:1: rule__Literal__Group_0__1__Impl : ( ( rule__Literal__ValueStringAssignment_0_1 ) ) ;
    public final void rule__Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4051:1: ( ( ( rule__Literal__ValueStringAssignment_0_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4052:1: ( ( rule__Literal__ValueStringAssignment_0_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4052:1: ( ( rule__Literal__ValueStringAssignment_0_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4053:1: ( rule__Literal__ValueStringAssignment_0_1 )
            {
             before(grammarAccess.getLiteralAccess().getValueStringAssignment_0_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4054:1: ( rule__Literal__ValueStringAssignment_0_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4054:2: rule__Literal__ValueStringAssignment_0_1
            {
            pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_0_1_in_rule__Literal__Group_0__1__Impl8164);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4068:1: rule__Literal__Group_1__0 : rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1 ;
    public final void rule__Literal__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4072:1: ( rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4073:2: rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_1__0__Impl_in_rule__Literal__Group_1__08198);
            rule__Literal__Group_1__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_1__1_in_rule__Literal__Group_1__08201);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4080:1: rule__Literal__Group_1__0__Impl : ( () ) ;
    public final void rule__Literal__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4084:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4085:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4085:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4086:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4087:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4089:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4099:1: rule__Literal__Group_1__1 : rule__Literal__Group_1__1__Impl ;
    public final void rule__Literal__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4103:1: ( rule__Literal__Group_1__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4104:2: rule__Literal__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_1__1__Impl_in_rule__Literal__Group_1__18259);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4110:1: rule__Literal__Group_1__1__Impl : ( ( rule__Literal__ValueStringAssignment_1_1 ) ) ;
    public final void rule__Literal__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4114:1: ( ( ( rule__Literal__ValueStringAssignment_1_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4115:1: ( ( rule__Literal__ValueStringAssignment_1_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4115:1: ( ( rule__Literal__ValueStringAssignment_1_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4116:1: ( rule__Literal__ValueStringAssignment_1_1 )
            {
             before(grammarAccess.getLiteralAccess().getValueStringAssignment_1_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4117:1: ( rule__Literal__ValueStringAssignment_1_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4117:2: rule__Literal__ValueStringAssignment_1_1
            {
            pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_1_1_in_rule__Literal__Group_1__1__Impl8286);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4131:1: rule__Literal__Group_2__0 : rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1 ;
    public final void rule__Literal__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4135:1: ( rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4136:2: rule__Literal__Group_2__0__Impl rule__Literal__Group_2__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__0__Impl_in_rule__Literal__Group_2__08320);
            rule__Literal__Group_2__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__1_in_rule__Literal__Group_2__08323);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4143:1: rule__Literal__Group_2__0__Impl : ( () ) ;
    public final void rule__Literal__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4147:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4148:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4148:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4149:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_2_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4150:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4152:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4162:1: rule__Literal__Group_2__1 : rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2 ;
    public final void rule__Literal__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4166:1: ( rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4167:2: rule__Literal__Group_2__1__Impl rule__Literal__Group_2__2
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__1__Impl_in_rule__Literal__Group_2__18381);
            rule__Literal__Group_2__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__2_in_rule__Literal__Group_2__18384);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4174:1: rule__Literal__Group_2__1__Impl : ( ( rule__Literal__OperationsAssignment_2_1 ) ) ;
    public final void rule__Literal__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4178:1: ( ( ( rule__Literal__OperationsAssignment_2_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4179:1: ( ( rule__Literal__OperationsAssignment_2_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4179:1: ( ( rule__Literal__OperationsAssignment_2_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4180:1: ( rule__Literal__OperationsAssignment_2_1 )
            {
             before(grammarAccess.getLiteralAccess().getOperationsAssignment_2_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4181:1: ( rule__Literal__OperationsAssignment_2_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4181:2: rule__Literal__OperationsAssignment_2_1
            {
            pushFollow(FOLLOW_rule__Literal__OperationsAssignment_2_1_in_rule__Literal__Group_2__1__Impl8411);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4191:1: rule__Literal__Group_2__2 : rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3 ;
    public final void rule__Literal__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4195:1: ( rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4196:2: rule__Literal__Group_2__2__Impl rule__Literal__Group_2__3
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__2__Impl_in_rule__Literal__Group_2__28441);
            rule__Literal__Group_2__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__3_in_rule__Literal__Group_2__28444);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4203:1: rule__Literal__Group_2__2__Impl : ( '(' ) ;
    public final void rule__Literal__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4207:1: ( ( '(' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4208:1: ( '(' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4208:1: ( '(' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4209:1: '('
            {
             before(grammarAccess.getLiteralAccess().getLeftParenthesisKeyword_2_2()); 
            match(input,34,FOLLOW_34_in_rule__Literal__Group_2__2__Impl8472); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4222:1: rule__Literal__Group_2__3 : rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4 ;
    public final void rule__Literal__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4226:1: ( rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4227:2: rule__Literal__Group_2__3__Impl rule__Literal__Group_2__4
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__3__Impl_in_rule__Literal__Group_2__38503);
            rule__Literal__Group_2__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__4_in_rule__Literal__Group_2__38506);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4234:1: rule__Literal__Group_2__3__Impl : ( ( rule__Literal__ValueStringAssignment_2_3 ) ) ;
    public final void rule__Literal__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4238:1: ( ( ( rule__Literal__ValueStringAssignment_2_3 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4239:1: ( ( rule__Literal__ValueStringAssignment_2_3 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4239:1: ( ( rule__Literal__ValueStringAssignment_2_3 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4240:1: ( rule__Literal__ValueStringAssignment_2_3 )
            {
             before(grammarAccess.getLiteralAccess().getValueStringAssignment_2_3()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4241:1: ( rule__Literal__ValueStringAssignment_2_3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4241:2: rule__Literal__ValueStringAssignment_2_3
            {
            pushFollow(FOLLOW_rule__Literal__ValueStringAssignment_2_3_in_rule__Literal__Group_2__3__Impl8533);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4251:1: rule__Literal__Group_2__4 : rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5 ;
    public final void rule__Literal__Group_2__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4255:1: ( rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4256:2: rule__Literal__Group_2__4__Impl rule__Literal__Group_2__5
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__4__Impl_in_rule__Literal__Group_2__48563);
            rule__Literal__Group_2__4__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__5_in_rule__Literal__Group_2__48566);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4263:1: rule__Literal__Group_2__4__Impl : ( ',' ) ;
    public final void rule__Literal__Group_2__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4267:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4268:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4268:1: ( ',' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4269:1: ','
            {
             before(grammarAccess.getLiteralAccess().getCommaKeyword_2_4()); 
            match(input,36,FOLLOW_36_in_rule__Literal__Group_2__4__Impl8594); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4282:1: rule__Literal__Group_2__5 : rule__Literal__Group_2__5__Impl rule__Literal__Group_2__6 ;
    public final void rule__Literal__Group_2__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4286:1: ( rule__Literal__Group_2__5__Impl rule__Literal__Group_2__6 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4287:2: rule__Literal__Group_2__5__Impl rule__Literal__Group_2__6
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__5__Impl_in_rule__Literal__Group_2__58625);
            rule__Literal__Group_2__5__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_2__6_in_rule__Literal__Group_2__58628);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4294:1: rule__Literal__Group_2__5__Impl : ( ( rule__Literal__ParametersAssignment_2_5 ) ) ;
    public final void rule__Literal__Group_2__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4298:1: ( ( ( rule__Literal__ParametersAssignment_2_5 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4299:1: ( ( rule__Literal__ParametersAssignment_2_5 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4299:1: ( ( rule__Literal__ParametersAssignment_2_5 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4300:1: ( rule__Literal__ParametersAssignment_2_5 )
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_2_5()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4301:1: ( rule__Literal__ParametersAssignment_2_5 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4301:2: rule__Literal__ParametersAssignment_2_5
            {
            pushFollow(FOLLOW_rule__Literal__ParametersAssignment_2_5_in_rule__Literal__Group_2__5__Impl8655);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4311:1: rule__Literal__Group_2__6 : rule__Literal__Group_2__6__Impl ;
    public final void rule__Literal__Group_2__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4315:1: ( rule__Literal__Group_2__6__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4316:2: rule__Literal__Group_2__6__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_2__6__Impl_in_rule__Literal__Group_2__68685);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4322:1: rule__Literal__Group_2__6__Impl : ( ')' ) ;
    public final void rule__Literal__Group_2__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4326:1: ( ( ')' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4327:1: ( ')' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4327:1: ( ')' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4328:1: ')'
            {
             before(grammarAccess.getLiteralAccess().getRightParenthesisKeyword_2_6()); 
            match(input,35,FOLLOW_35_in_rule__Literal__Group_2__6__Impl8713); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4355:1: rule__Literal__Group_4__0 : rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1 ;
    public final void rule__Literal__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4359:1: ( rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4360:2: rule__Literal__Group_4__0__Impl rule__Literal__Group_4__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__0__Impl_in_rule__Literal__Group_4__08758);
            rule__Literal__Group_4__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_4__1_in_rule__Literal__Group_4__08761);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4367:1: rule__Literal__Group_4__0__Impl : ( () ) ;
    public final void rule__Literal__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4371:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4372:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4372:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4373:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_4_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4374:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4376:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4386:1: rule__Literal__Group_4__1 : rule__Literal__Group_4__1__Impl ;
    public final void rule__Literal__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4390:1: ( rule__Literal__Group_4__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4391:2: rule__Literal__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_4__1__Impl_in_rule__Literal__Group_4__18819);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4397:1: rule__Literal__Group_4__1__Impl : ( ( rule__Literal__ElementsAssignment_4_1 ) ) ;
    public final void rule__Literal__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4401:1: ( ( ( rule__Literal__ElementsAssignment_4_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4402:1: ( ( rule__Literal__ElementsAssignment_4_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4402:1: ( ( rule__Literal__ElementsAssignment_4_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4403:1: ( rule__Literal__ElementsAssignment_4_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_4_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4404:1: ( rule__Literal__ElementsAssignment_4_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4404:2: rule__Literal__ElementsAssignment_4_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_4_1_in_rule__Literal__Group_4__1__Impl8846);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4418:1: rule__Literal__Group_5__0 : rule__Literal__Group_5__0__Impl rule__Literal__Group_5__1 ;
    public final void rule__Literal__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4422:1: ( rule__Literal__Group_5__0__Impl rule__Literal__Group_5__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4423:2: rule__Literal__Group_5__0__Impl rule__Literal__Group_5__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_5__0__Impl_in_rule__Literal__Group_5__08880);
            rule__Literal__Group_5__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_5__1_in_rule__Literal__Group_5__08883);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4430:1: rule__Literal__Group_5__0__Impl : ( () ) ;
    public final void rule__Literal__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4434:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4435:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4435:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4436:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_5_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4437:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4439:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4449:1: rule__Literal__Group_5__1 : rule__Literal__Group_5__1__Impl ;
    public final void rule__Literal__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4453:1: ( rule__Literal__Group_5__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4454:2: rule__Literal__Group_5__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_5__1__Impl_in_rule__Literal__Group_5__18941);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4460:1: rule__Literal__Group_5__1__Impl : ( ( rule__Literal__ElementsAssignment_5_1 ) ) ;
    public final void rule__Literal__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4464:1: ( ( ( rule__Literal__ElementsAssignment_5_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4465:1: ( ( rule__Literal__ElementsAssignment_5_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4465:1: ( ( rule__Literal__ElementsAssignment_5_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4466:1: ( rule__Literal__ElementsAssignment_5_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_5_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4467:1: ( rule__Literal__ElementsAssignment_5_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4467:2: rule__Literal__ElementsAssignment_5_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_5_1_in_rule__Literal__Group_5__1__Impl8968);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4481:1: rule__Literal__Group_6__0 : rule__Literal__Group_6__0__Impl rule__Literal__Group_6__1 ;
    public final void rule__Literal__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4485:1: ( rule__Literal__Group_6__0__Impl rule__Literal__Group_6__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4486:2: rule__Literal__Group_6__0__Impl rule__Literal__Group_6__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__0__Impl_in_rule__Literal__Group_6__09002);
            rule__Literal__Group_6__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6__1_in_rule__Literal__Group_6__09005);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4493:1: rule__Literal__Group_6__0__Impl : ( () ) ;
    public final void rule__Literal__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4497:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4498:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4498:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4499:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_6_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4500:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4502:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4512:1: rule__Literal__Group_6__1 : rule__Literal__Group_6__1__Impl rule__Literal__Group_6__2 ;
    public final void rule__Literal__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4516:1: ( rule__Literal__Group_6__1__Impl rule__Literal__Group_6__2 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4517:2: rule__Literal__Group_6__1__Impl rule__Literal__Group_6__2
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__1__Impl_in_rule__Literal__Group_6__19063);
            rule__Literal__Group_6__1__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6__2_in_rule__Literal__Group_6__19066);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4524:1: rule__Literal__Group_6__1__Impl : ( ( rule__Literal__ExpressionTypeAssignment_6_1 ) ) ;
    public final void rule__Literal__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4528:1: ( ( ( rule__Literal__ExpressionTypeAssignment_6_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4529:1: ( ( rule__Literal__ExpressionTypeAssignment_6_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4529:1: ( ( rule__Literal__ExpressionTypeAssignment_6_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4530:1: ( rule__Literal__ExpressionTypeAssignment_6_1 )
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeAssignment_6_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4531:1: ( rule__Literal__ExpressionTypeAssignment_6_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4531:2: rule__Literal__ExpressionTypeAssignment_6_1
            {
            pushFollow(FOLLOW_rule__Literal__ExpressionTypeAssignment_6_1_in_rule__Literal__Group_6__1__Impl9093);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4541:1: rule__Literal__Group_6__2 : rule__Literal__Group_6__2__Impl rule__Literal__Group_6__3 ;
    public final void rule__Literal__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4545:1: ( rule__Literal__Group_6__2__Impl rule__Literal__Group_6__3 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4546:2: rule__Literal__Group_6__2__Impl rule__Literal__Group_6__3
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__2__Impl_in_rule__Literal__Group_6__29123);
            rule__Literal__Group_6__2__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6__3_in_rule__Literal__Group_6__29126);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4553:1: rule__Literal__Group_6__2__Impl : ( ( rule__Literal__ParametersAssignment_6_2 )? ) ;
    public final void rule__Literal__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4557:1: ( ( ( rule__Literal__ParametersAssignment_6_2 )? ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4558:1: ( ( rule__Literal__ParametersAssignment_6_2 )? )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4558:1: ( ( rule__Literal__ParametersAssignment_6_2 )? )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4559:1: ( rule__Literal__ParametersAssignment_6_2 )?
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_6_2()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4560:1: ( rule__Literal__ParametersAssignment_6_2 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_ID||(LA30_0>=RULE_INT && LA30_0<=RULE_LITERALSTRING)||LA30_0==28||LA30_0==31||LA30_0==34||(LA30_0>=43 && LA30_0<=49)) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4560:2: rule__Literal__ParametersAssignment_6_2
                    {
                    pushFollow(FOLLOW_rule__Literal__ParametersAssignment_6_2_in_rule__Literal__Group_6__2__Impl9153);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4570:1: rule__Literal__Group_6__3 : rule__Literal__Group_6__3__Impl rule__Literal__Group_6__4 ;
    public final void rule__Literal__Group_6__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4574:1: ( rule__Literal__Group_6__3__Impl rule__Literal__Group_6__4 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4575:2: rule__Literal__Group_6__3__Impl rule__Literal__Group_6__4
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__3__Impl_in_rule__Literal__Group_6__39184);
            rule__Literal__Group_6__3__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6__4_in_rule__Literal__Group_6__39187);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4582:1: rule__Literal__Group_6__3__Impl : ( ( rule__Literal__Group_6_3__0 )* ) ;
    public final void rule__Literal__Group_6__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4586:1: ( ( ( rule__Literal__Group_6_3__0 )* ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4587:1: ( ( rule__Literal__Group_6_3__0 )* )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4587:1: ( ( rule__Literal__Group_6_3__0 )* )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4588:1: ( rule__Literal__Group_6_3__0 )*
            {
             before(grammarAccess.getLiteralAccess().getGroup_6_3()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4589:1: ( rule__Literal__Group_6_3__0 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==36) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4589:2: rule__Literal__Group_6_3__0
            	    {
            	    pushFollow(FOLLOW_rule__Literal__Group_6_3__0_in_rule__Literal__Group_6__3__Impl9214);
            	    rule__Literal__Group_6_3__0();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop31;
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4599:1: rule__Literal__Group_6__4 : rule__Literal__Group_6__4__Impl ;
    public final void rule__Literal__Group_6__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4603:1: ( rule__Literal__Group_6__4__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4604:2: rule__Literal__Group_6__4__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_6__4__Impl_in_rule__Literal__Group_6__49245);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4610:1: rule__Literal__Group_6__4__Impl : ( '}' ) ;
    public final void rule__Literal__Group_6__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4614:1: ( ( '}' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4615:1: ( '}' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4615:1: ( '}' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4616:1: '}'
            {
             before(grammarAccess.getLiteralAccess().getRightCurlyBracketKeyword_6_4()); 
            match(input,37,FOLLOW_37_in_rule__Literal__Group_6__4__Impl9273); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4639:1: rule__Literal__Group_6_3__0 : rule__Literal__Group_6_3__0__Impl rule__Literal__Group_6_3__1 ;
    public final void rule__Literal__Group_6_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4643:1: ( rule__Literal__Group_6_3__0__Impl rule__Literal__Group_6_3__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4644:2: rule__Literal__Group_6_3__0__Impl rule__Literal__Group_6_3__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_6_3__0__Impl_in_rule__Literal__Group_6_3__09314);
            rule__Literal__Group_6_3__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_6_3__1_in_rule__Literal__Group_6_3__09317);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4651:1: rule__Literal__Group_6_3__0__Impl : ( ',' ) ;
    public final void rule__Literal__Group_6_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4655:1: ( ( ',' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4656:1: ( ',' )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4656:1: ( ',' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4657:1: ','
            {
             before(grammarAccess.getLiteralAccess().getCommaKeyword_6_3_0()); 
            match(input,36,FOLLOW_36_in_rule__Literal__Group_6_3__0__Impl9345); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4670:1: rule__Literal__Group_6_3__1 : rule__Literal__Group_6_3__1__Impl ;
    public final void rule__Literal__Group_6_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4674:1: ( rule__Literal__Group_6_3__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4675:2: rule__Literal__Group_6_3__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_6_3__1__Impl_in_rule__Literal__Group_6_3__19376);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4681:1: rule__Literal__Group_6_3__1__Impl : ( ( rule__Literal__ParametersAssignment_6_3_1 ) ) ;
    public final void rule__Literal__Group_6_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4685:1: ( ( ( rule__Literal__ParametersAssignment_6_3_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4686:1: ( ( rule__Literal__ParametersAssignment_6_3_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4686:1: ( ( rule__Literal__ParametersAssignment_6_3_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4687:1: ( rule__Literal__ParametersAssignment_6_3_1 )
            {
             before(grammarAccess.getLiteralAccess().getParametersAssignment_6_3_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4688:1: ( rule__Literal__ParametersAssignment_6_3_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4688:2: rule__Literal__ParametersAssignment_6_3_1
            {
            pushFollow(FOLLOW_rule__Literal__ParametersAssignment_6_3_1_in_rule__Literal__Group_6_3__1__Impl9403);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4702:1: rule__Literal__Group_7__0 : rule__Literal__Group_7__0__Impl rule__Literal__Group_7__1 ;
    public final void rule__Literal__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4706:1: ( rule__Literal__Group_7__0__Impl rule__Literal__Group_7__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4707:2: rule__Literal__Group_7__0__Impl rule__Literal__Group_7__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_7__0__Impl_in_rule__Literal__Group_7__09437);
            rule__Literal__Group_7__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_7__1_in_rule__Literal__Group_7__09440);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4714:1: rule__Literal__Group_7__0__Impl : ( () ) ;
    public final void rule__Literal__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4718:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4719:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4719:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4720:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_7_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4721:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4723:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4733:1: rule__Literal__Group_7__1 : rule__Literal__Group_7__1__Impl ;
    public final void rule__Literal__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4737:1: ( rule__Literal__Group_7__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4738:2: rule__Literal__Group_7__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_7__1__Impl_in_rule__Literal__Group_7__19498);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4744:1: rule__Literal__Group_7__1__Impl : ( ( rule__Literal__ElementsAssignment_7_1 ) ) ;
    public final void rule__Literal__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4748:1: ( ( ( rule__Literal__ElementsAssignment_7_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4749:1: ( ( rule__Literal__ElementsAssignment_7_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4749:1: ( ( rule__Literal__ElementsAssignment_7_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4750:1: ( rule__Literal__ElementsAssignment_7_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_7_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4751:1: ( rule__Literal__ElementsAssignment_7_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4751:2: rule__Literal__ElementsAssignment_7_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_7_1_in_rule__Literal__Group_7__1__Impl9525);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4765:1: rule__Literal__Group_8__0 : rule__Literal__Group_8__0__Impl rule__Literal__Group_8__1 ;
    public final void rule__Literal__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4769:1: ( rule__Literal__Group_8__0__Impl rule__Literal__Group_8__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4770:2: rule__Literal__Group_8__0__Impl rule__Literal__Group_8__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_8__0__Impl_in_rule__Literal__Group_8__09559);
            rule__Literal__Group_8__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_8__1_in_rule__Literal__Group_8__09562);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4777:1: rule__Literal__Group_8__0__Impl : ( () ) ;
    public final void rule__Literal__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4781:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4782:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4782:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4783:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_8_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4784:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4786:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4796:1: rule__Literal__Group_8__1 : rule__Literal__Group_8__1__Impl ;
    public final void rule__Literal__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4800:1: ( rule__Literal__Group_8__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4801:2: rule__Literal__Group_8__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_8__1__Impl_in_rule__Literal__Group_8__19620);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4807:1: rule__Literal__Group_8__1__Impl : ( ( rule__Literal__ElementsAssignment_8_1 ) ) ;
    public final void rule__Literal__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4811:1: ( ( ( rule__Literal__ElementsAssignment_8_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4812:1: ( ( rule__Literal__ElementsAssignment_8_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4812:1: ( ( rule__Literal__ElementsAssignment_8_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4813:1: ( rule__Literal__ElementsAssignment_8_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_8_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4814:1: ( rule__Literal__ElementsAssignment_8_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4814:2: rule__Literal__ElementsAssignment_8_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_8_1_in_rule__Literal__Group_8__1__Impl9647);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4828:1: rule__Literal__Group_9__0 : rule__Literal__Group_9__0__Impl rule__Literal__Group_9__1 ;
    public final void rule__Literal__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4832:1: ( rule__Literal__Group_9__0__Impl rule__Literal__Group_9__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4833:2: rule__Literal__Group_9__0__Impl rule__Literal__Group_9__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_9__0__Impl_in_rule__Literal__Group_9__09681);
            rule__Literal__Group_9__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_9__1_in_rule__Literal__Group_9__09684);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4840:1: rule__Literal__Group_9__0__Impl : ( () ) ;
    public final void rule__Literal__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4844:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4845:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4845:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4846:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_9_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4847:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4849:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4859:1: rule__Literal__Group_9__1 : rule__Literal__Group_9__1__Impl ;
    public final void rule__Literal__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4863:1: ( rule__Literal__Group_9__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4864:2: rule__Literal__Group_9__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_9__1__Impl_in_rule__Literal__Group_9__19742);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4870:1: rule__Literal__Group_9__1__Impl : ( ( rule__Literal__ElementsAssignment_9_1 ) ) ;
    public final void rule__Literal__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4874:1: ( ( ( rule__Literal__ElementsAssignment_9_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4875:1: ( ( rule__Literal__ElementsAssignment_9_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4875:1: ( ( rule__Literal__ElementsAssignment_9_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4876:1: ( rule__Literal__ElementsAssignment_9_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_9_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4877:1: ( rule__Literal__ElementsAssignment_9_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4877:2: rule__Literal__ElementsAssignment_9_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_9_1_in_rule__Literal__Group_9__1__Impl9769);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4891:1: rule__Literal__Group_10__0 : rule__Literal__Group_10__0__Impl rule__Literal__Group_10__1 ;
    public final void rule__Literal__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4895:1: ( rule__Literal__Group_10__0__Impl rule__Literal__Group_10__1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4896:2: rule__Literal__Group_10__0__Impl rule__Literal__Group_10__1
            {
            pushFollow(FOLLOW_rule__Literal__Group_10__0__Impl_in_rule__Literal__Group_10__09803);
            rule__Literal__Group_10__0__Impl();
            _fsp--;

            pushFollow(FOLLOW_rule__Literal__Group_10__1_in_rule__Literal__Group_10__09806);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4903:1: rule__Literal__Group_10__0__Impl : ( () ) ;
    public final void rule__Literal__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4907:1: ( ( () ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4908:1: ( () )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4908:1: ( () )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4909:1: ()
            {
             before(grammarAccess.getLiteralAccess().getExpressionAction_10_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4910:1: ()
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4912:1: 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4922:1: rule__Literal__Group_10__1 : rule__Literal__Group_10__1__Impl ;
    public final void rule__Literal__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4926:1: ( rule__Literal__Group_10__1__Impl )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4927:2: rule__Literal__Group_10__1__Impl
            {
            pushFollow(FOLLOW_rule__Literal__Group_10__1__Impl_in_rule__Literal__Group_10__19864);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4933:1: rule__Literal__Group_10__1__Impl : ( ( rule__Literal__ElementsAssignment_10_1 ) ) ;
    public final void rule__Literal__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4937:1: ( ( ( rule__Literal__ElementsAssignment_10_1 ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4938:1: ( ( rule__Literal__ElementsAssignment_10_1 ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4938:1: ( ( rule__Literal__ElementsAssignment_10_1 ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4939:1: ( rule__Literal__ElementsAssignment_10_1 )
            {
             before(grammarAccess.getLiteralAccess().getElementsAssignment_10_1()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4940:1: ( rule__Literal__ElementsAssignment_10_1 )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4940:2: rule__Literal__ElementsAssignment_10_1
            {
            pushFollow(FOLLOW_rule__Literal__ElementsAssignment_10_1_in_rule__Literal__Group_10__1__Impl9891);
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


    // $ANTLR start rule__Tml__AttributesAssignment_2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4955:1: rule__Tml__AttributesAssignment_2 : ( rulePossibleExpression ) ;
    public final void rule__Tml__AttributesAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4959:1: ( ( rulePossibleExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4960:1: ( rulePossibleExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4960:1: ( rulePossibleExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4961:1: rulePossibleExpression
            {
             before(grammarAccess.getTmlAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_rulePossibleExpression_in_rule__Tml__AttributesAssignment_29930);
            rulePossibleExpression();
            _fsp--;

             after(grammarAccess.getTmlAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__AttributesAssignment_2


    // $ANTLR start rule__Tml__ChildrenAssignment_3_0_1_0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4970:1: rule__Tml__ChildrenAssignment_3_0_1_0 : ( ruleMessage ) ;
    public final void rule__Tml__ChildrenAssignment_3_0_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4974:1: ( ( ruleMessage ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4975:1: ( ruleMessage )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4975:1: ( ruleMessage )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4976:1: ruleMessage
            {
             before(grammarAccess.getTmlAccess().getChildrenMessageParserRuleCall_3_0_1_0_0()); 
            pushFollow(FOLLOW_ruleMessage_in_rule__Tml__ChildrenAssignment_3_0_1_09961);
            ruleMessage();
            _fsp--;

             after(grammarAccess.getTmlAccess().getChildrenMessageParserRuleCall_3_0_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__ChildrenAssignment_3_0_1_0


    // $ANTLR start rule__Tml__ChildrenAssignment_3_0_1_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4985:1: rule__Tml__ChildrenAssignment_3_0_1_1 : ( ruleMap ) ;
    public final void rule__Tml__ChildrenAssignment_3_0_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4989:1: ( ( ruleMap ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4990:1: ( ruleMap )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4990:1: ( ruleMap )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:4991:1: ruleMap
            {
             before(grammarAccess.getTmlAccess().getChildrenMapParserRuleCall_3_0_1_1_0()); 
            pushFollow(FOLLOW_ruleMap_in_rule__Tml__ChildrenAssignment_3_0_1_19992);
            ruleMap();
            _fsp--;

             after(grammarAccess.getTmlAccess().getChildrenMapParserRuleCall_3_0_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Tml__ChildrenAssignment_3_0_1_1


    // $ANTLR start rule__PossibleExpression__KeyAssignment_0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5000:1: rule__PossibleExpression__KeyAssignment_0 : ( RULE_ID ) ;
    public final void rule__PossibleExpression__KeyAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5004:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5005:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5005:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5006:1: RULE_ID
            {
             before(grammarAccess.getPossibleExpressionAccess().getKeyIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PossibleExpression__KeyAssignment_010023); 
             after(grammarAccess.getPossibleExpressionAccess().getKeyIDTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__KeyAssignment_0


    // $ANTLR start rule__PossibleExpression__ExpressionValueAssignment_2_0_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5015:1: rule__PossibleExpression__ExpressionValueAssignment_2_0_1 : ( ruleTopLevel ) ;
    public final void rule__PossibleExpression__ExpressionValueAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5019:1: ( ( ruleTopLevel ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5020:1: ( ruleTopLevel )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5020:1: ( ruleTopLevel )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5021:1: ruleTopLevel
            {
             before(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_2_0_1_0()); 
            pushFollow(FOLLOW_ruleTopLevel_in_rule__PossibleExpression__ExpressionValueAssignment_2_0_110054);
            ruleTopLevel();
            _fsp--;

             after(grammarAccess.getPossibleExpressionAccess().getExpressionValueTopLevelParserRuleCall_2_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__ExpressionValueAssignment_2_0_1


    // $ANTLR start rule__PossibleExpression__ValueAssignment_2_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5030:1: rule__PossibleExpression__ValueAssignment_2_1 : ( RULE_ATTRIBUTESTRING ) ;
    public final void rule__PossibleExpression__ValueAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5034:1: ( ( RULE_ATTRIBUTESTRING ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5035:1: ( RULE_ATTRIBUTESTRING )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5035:1: ( RULE_ATTRIBUTESTRING )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5036:1: RULE_ATTRIBUTESTRING
            {
             before(grammarAccess.getPossibleExpressionAccess().getValueATTRIBUTESTRINGTerminalRuleCall_2_1_0()); 
            match(input,RULE_ATTRIBUTESTRING,FOLLOW_RULE_ATTRIBUTESTRING_in_rule__PossibleExpression__ValueAssignment_2_110085); 
             after(grammarAccess.getPossibleExpressionAccess().getValueATTRIBUTESTRINGTerminalRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__PossibleExpression__ValueAssignment_2_1


    // $ANTLR start rule__Message__AttributesAssignment_2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5045:1: rule__Message__AttributesAssignment_2 : ( rulePossibleExpression ) ;
    public final void rule__Message__AttributesAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5049:1: ( ( rulePossibleExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5050:1: ( rulePossibleExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5050:1: ( rulePossibleExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5051:1: rulePossibleExpression
            {
             before(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_rulePossibleExpression_in_rule__Message__AttributesAssignment_210116);
            rulePossibleExpression();
            _fsp--;

             after(grammarAccess.getMessageAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__AttributesAssignment_2


    // $ANTLR start rule__Message__ChildrenAssignment_3_0_1_0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5060:1: rule__Message__ChildrenAssignment_3_0_1_0 : ( ruleMessage ) ;
    public final void rule__Message__ChildrenAssignment_3_0_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5064:1: ( ( ruleMessage ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5065:1: ( ruleMessage )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5065:1: ( ruleMessage )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5066:1: ruleMessage
            {
             before(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0()); 
            pushFollow(FOLLOW_ruleMessage_in_rule__Message__ChildrenAssignment_3_0_1_010147);
            ruleMessage();
            _fsp--;

             after(grammarAccess.getMessageAccess().getChildrenMessageParserRuleCall_3_0_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__ChildrenAssignment_3_0_1_0


    // $ANTLR start rule__Message__ChildrenAssignment_3_0_1_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5075:1: rule__Message__ChildrenAssignment_3_0_1_1 : ( ruleProperty ) ;
    public final void rule__Message__ChildrenAssignment_3_0_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5079:1: ( ( ruleProperty ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5080:1: ( ruleProperty )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5080:1: ( ruleProperty )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5081:1: ruleProperty
            {
             before(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0()); 
            pushFollow(FOLLOW_ruleProperty_in_rule__Message__ChildrenAssignment_3_0_1_110178);
            ruleProperty();
            _fsp--;

             after(grammarAccess.getMessageAccess().getChildrenPropertyParserRuleCall_3_0_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__ChildrenAssignment_3_0_1_1


    // $ANTLR start rule__Message__ChildrenAssignment_3_0_1_2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5090:1: rule__Message__ChildrenAssignment_3_0_1_2 : ( ruleMap ) ;
    public final void rule__Message__ChildrenAssignment_3_0_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5094:1: ( ( ruleMap ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5095:1: ( ruleMap )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5095:1: ( ruleMap )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5096:1: ruleMap
            {
             before(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_2_0()); 
            pushFollow(FOLLOW_ruleMap_in_rule__Message__ChildrenAssignment_3_0_1_210209);
            ruleMap();
            _fsp--;

             after(grammarAccess.getMessageAccess().getChildrenMapParserRuleCall_3_0_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Message__ChildrenAssignment_3_0_1_2


    // $ANTLR start rule__Map__MapNameAssignment_2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5105:1: rule__Map__MapNameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Map__MapNameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5109:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5110:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5110:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5111:1: RULE_ID
            {
             before(grammarAccess.getMapAccess().getMapNameIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Map__MapNameAssignment_210240); 
             after(grammarAccess.getMapAccess().getMapNameIDTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__MapNameAssignment_2


    // $ANTLR start rule__Map__AttributesAssignment_3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5120:1: rule__Map__AttributesAssignment_3 : ( rulePossibleExpression ) ;
    public final void rule__Map__AttributesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5124:1: ( ( rulePossibleExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5125:1: ( rulePossibleExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5125:1: ( rulePossibleExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5126:1: rulePossibleExpression
            {
             before(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_3_0()); 
            pushFollow(FOLLOW_rulePossibleExpression_in_rule__Map__AttributesAssignment_310271);
            rulePossibleExpression();
            _fsp--;

             after(grammarAccess.getMapAccess().getAttributesPossibleExpressionParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__AttributesAssignment_3


    // $ANTLR start rule__Map__ChildrenAssignment_4_1_1_0
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5135:1: rule__Map__ChildrenAssignment_4_1_1_0 : ( ruleMessage ) ;
    public final void rule__Map__ChildrenAssignment_4_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5139:1: ( ( ruleMessage ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5140:1: ( ruleMessage )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5140:1: ( ruleMessage )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5141:1: ruleMessage
            {
             before(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_4_1_1_0_0()); 
            pushFollow(FOLLOW_ruleMessage_in_rule__Map__ChildrenAssignment_4_1_1_010302);
            ruleMessage();
            _fsp--;

             after(grammarAccess.getMapAccess().getChildrenMessageParserRuleCall_4_1_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__ChildrenAssignment_4_1_1_0


    // $ANTLR start rule__Map__ChildrenAssignment_4_1_1_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5150:1: rule__Map__ChildrenAssignment_4_1_1_1 : ( ruleProperty ) ;
    public final void rule__Map__ChildrenAssignment_4_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5154:1: ( ( ruleProperty ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5155:1: ( ruleProperty )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5155:1: ( ruleProperty )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5156:1: ruleProperty
            {
             before(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_4_1_1_1_0()); 
            pushFollow(FOLLOW_ruleProperty_in_rule__Map__ChildrenAssignment_4_1_1_110333);
            ruleProperty();
            _fsp--;

             after(grammarAccess.getMapAccess().getChildrenPropertyParserRuleCall_4_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__ChildrenAssignment_4_1_1_1


    // $ANTLR start rule__Map__ChildrenAssignment_4_1_1_2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5165:1: rule__Map__ChildrenAssignment_4_1_1_2 : ( ruleMap ) ;
    public final void rule__Map__ChildrenAssignment_4_1_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5169:1: ( ( ruleMap ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5170:1: ( ruleMap )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5170:1: ( ruleMap )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5171:1: ruleMap
            {
             before(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_4_1_1_2_0()); 
            pushFollow(FOLLOW_ruleMap_in_rule__Map__ChildrenAssignment_4_1_1_210364);
            ruleMap();
            _fsp--;

             after(grammarAccess.getMapAccess().getChildrenMapParserRuleCall_4_1_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__ChildrenAssignment_4_1_1_2


    // $ANTLR start rule__Map__MapClosingNameAssignment_4_1_3
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5180:1: rule__Map__MapClosingNameAssignment_4_1_3 : ( RULE_ID ) ;
    public final void rule__Map__MapClosingNameAssignment_4_1_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5184:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5185:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5185:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5186:1: RULE_ID
            {
             before(grammarAccess.getMapAccess().getMapClosingNameIDTerminalRuleCall_4_1_3_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Map__MapClosingNameAssignment_4_1_310395); 
             after(grammarAccess.getMapAccess().getMapClosingNameIDTerminalRuleCall_4_1_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Map__MapClosingNameAssignment_4_1_3


    // $ANTLR start rule__Property__AttributesAssignment_2
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5195:1: rule__Property__AttributesAssignment_2 : ( rulePossibleExpression ) ;
    public final void rule__Property__AttributesAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5199:1: ( ( rulePossibleExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5200:1: ( rulePossibleExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5200:1: ( rulePossibleExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5201:1: rulePossibleExpression
            {
             before(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_rulePossibleExpression_in_rule__Property__AttributesAssignment_210426);
            rulePossibleExpression();
            _fsp--;

             after(grammarAccess.getPropertyAccess().getAttributesPossibleExpressionParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__AttributesAssignment_2


    // $ANTLR start rule__Property__ExpressionValueAssignment_3_1_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5210:1: rule__Property__ExpressionValueAssignment_3_1_1 : ( ruleExpressionTag ) ;
    public final void rule__Property__ExpressionValueAssignment_3_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5214:1: ( ( ruleExpressionTag ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5215:1: ( ruleExpressionTag )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5215:1: ( ruleExpressionTag )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5216:1: ruleExpressionTag
            {
             before(grammarAccess.getPropertyAccess().getExpressionValueExpressionTagParserRuleCall_3_1_1_0()); 
            pushFollow(FOLLOW_ruleExpressionTag_in_rule__Property__ExpressionValueAssignment_3_1_110457);
            ruleExpressionTag();
            _fsp--;

             after(grammarAccess.getPropertyAccess().getExpressionValueExpressionTagParserRuleCall_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__Property__ExpressionValueAssignment_3_1_1


    // $ANTLR start rule__ExpressionTag__ValueAssignment_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5225:1: rule__ExpressionTag__ValueAssignment_1 : ( ruleTopLevel ) ;
    public final void rule__ExpressionTag__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5229:1: ( ( ruleTopLevel ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5230:1: ( ruleTopLevel )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5230:1: ( ruleTopLevel )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5231:1: ruleTopLevel
            {
             before(grammarAccess.getExpressionTagAccess().getValueTopLevelParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleTopLevel_in_rule__ExpressionTag__ValueAssignment_110488);
            ruleTopLevel();
            _fsp--;

             after(grammarAccess.getExpressionTagAccess().getValueTopLevelParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end rule__ExpressionTag__ValueAssignment_1


    // $ANTLR start rule__TopLevel__ToplevelExpressionAssignment_1
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5240:1: rule__TopLevel__ToplevelExpressionAssignment_1 : ( ruleOrExpression ) ;
    public final void rule__TopLevel__ToplevelExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5244:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5245:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5245:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5246:1: ruleOrExpression
            {
             before(grammarAccess.getTopLevelAccess().getToplevelExpressionOrExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__TopLevel__ToplevelExpressionAssignment_110519);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5255:1: rule__OrExpression__ParametersAssignment_1 : ( ruleAndExpression ) ;
    public final void rule__OrExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5259:1: ( ( ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5260:1: ( ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5260:1: ( ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5261:1: ruleAndExpression
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_110550);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5270:1: rule__OrExpression__OperationsAssignment_2_0 : ( ( 'OR' ) ) ;
    public final void rule__OrExpression__OperationsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5274:1: ( ( ( 'OR' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5275:1: ( ( 'OR' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5275:1: ( ( 'OR' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5276:1: ( 'OR' )
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_2_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5277:1: ( 'OR' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5278:1: 'OR'
            {
             before(grammarAccess.getOrExpressionAccess().getOperationsORKeyword_2_0_0()); 
            match(input,38,FOLLOW_38_in_rule__OrExpression__OperationsAssignment_2_010586); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5293:1: rule__OrExpression__ParametersAssignment_2_1 : ( ruleAndExpression ) ;
    public final void rule__OrExpression__ParametersAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5297:1: ( ( ruleAndExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5298:1: ( ruleAndExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5298:1: ( ruleAndExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5299:1: ruleAndExpression
            {
             before(grammarAccess.getOrExpressionAccess().getParametersAndExpressionParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_2_110625);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5308:1: rule__AndExpression__ParametersAssignment_1 : ( ruleEqualityExpression ) ;
    public final void rule__AndExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5312:1: ( ( ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5313:1: ( ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5313:1: ( ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5314:1: ruleEqualityExpression
            {
             before(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_110656);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5323:1: rule__AndExpression__OperationsAssignment_2_0 : ( ( 'AND' ) ) ;
    public final void rule__AndExpression__OperationsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5327:1: ( ( ( 'AND' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5328:1: ( ( 'AND' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5328:1: ( ( 'AND' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5329:1: ( 'AND' )
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_2_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5330:1: ( 'AND' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5331:1: 'AND'
            {
             before(grammarAccess.getAndExpressionAccess().getOperationsANDKeyword_2_0_0()); 
            match(input,39,FOLLOW_39_in_rule__AndExpression__OperationsAssignment_2_010692); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5346:1: rule__AndExpression__ParametersAssignment_2_1 : ( ruleEqualityExpression ) ;
    public final void rule__AndExpression__ParametersAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5350:1: ( ( ruleEqualityExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5351:1: ( ruleEqualityExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5351:1: ( ruleEqualityExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5352:1: ruleEqualityExpression
            {
             before(grammarAccess.getAndExpressionAccess().getParametersEqualityExpressionParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_2_110731);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5361:1: rule__EqualityExpression__ParametersAssignment_1 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5365:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5366:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5366:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5367:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_110762);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5376:1: rule__EqualityExpression__OperationsAssignment_2_0_0 : ( ( '==' ) ) ;
    public final void rule__EqualityExpression__OperationsAssignment_2_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5380:1: ( ( ( '==' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5381:1: ( ( '==' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5381:1: ( ( '==' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5382:1: ( '==' )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_2_0_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5383:1: ( '==' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5384:1: '=='
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsEqualsSignEqualsSignKeyword_2_0_0_0()); 
            match(input,40,FOLLOW_40_in_rule__EqualityExpression__OperationsAssignment_2_0_010798); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5399:1: rule__EqualityExpression__ParametersAssignment_2_0_1 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5403:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5404:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5404:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5405:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_0_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_2_0_110837);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5414:1: rule__EqualityExpression__OperationsAssignment_2_1_0 : ( ( '!=' ) ) ;
    public final void rule__EqualityExpression__OperationsAssignment_2_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5418:1: ( ( ( '!=' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5419:1: ( ( '!=' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5419:1: ( ( '!=' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5420:1: ( '!=' )
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_2_1_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5421:1: ( '!=' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5422:1: '!='
            {
             before(grammarAccess.getEqualityExpressionAccess().getOperationsExclamationMarkEqualsSignKeyword_2_1_0_0()); 
            match(input,41,FOLLOW_41_in_rule__EqualityExpression__OperationsAssignment_2_1_010873); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5437:1: rule__EqualityExpression__ParametersAssignment_2_1_1 : ( ruleAdditiveExpression ) ;
    public final void rule__EqualityExpression__ParametersAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5441:1: ( ( ruleAdditiveExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5442:1: ( ruleAdditiveExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5442:1: ( ruleAdditiveExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5443:1: ruleAdditiveExpression
            {
             before(grammarAccess.getEqualityExpressionAccess().getParametersAdditiveExpressionParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_2_1_110912);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5452:1: rule__AdditiveExpression__ParametersAssignment_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5456:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5457:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5457:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5458:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_110943);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5467:1: rule__AdditiveExpression__ParametersAssignment_2_0_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5471:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5472:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5472:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5473:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_0_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_2_0_110974);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5482:1: rule__AdditiveExpression__ParametersAssignment_2_1_1 : ( ruleMultiplicativeExpression ) ;
    public final void rule__AdditiveExpression__ParametersAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5486:1: ( ( ruleMultiplicativeExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5487:1: ( ruleMultiplicativeExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5487:1: ( ruleMultiplicativeExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5488:1: ruleMultiplicativeExpression
            {
             before(grammarAccess.getAdditiveExpressionAccess().getParametersMultiplicativeExpressionParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_2_1_111005);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5497:1: rule__MultiplicativeExpression__ParametersAssignment_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5501:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5502:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5502:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5503:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_111036);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5512:1: rule__MultiplicativeExpression__OperationsAssignment_2_0_0 : ( ( '*' ) ) ;
    public final void rule__MultiplicativeExpression__OperationsAssignment_2_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5516:1: ( ( ( '*' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5517:1: ( ( '*' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5517:1: ( ( '*' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5518:1: ( '*' )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_2_0_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5519:1: ( '*' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5520:1: '*'
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsAsteriskKeyword_2_0_0_0()); 
            match(input,42,FOLLOW_42_in_rule__MultiplicativeExpression__OperationsAssignment_2_0_011072); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5535:1: rule__MultiplicativeExpression__ParametersAssignment_2_0_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5539:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5540:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5540:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5541:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_0_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_2_0_111111);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5550:1: rule__MultiplicativeExpression__OperationsAssignment_2_1_0 : ( ( '/' ) ) ;
    public final void rule__MultiplicativeExpression__OperationsAssignment_2_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5554:1: ( ( ( '/' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5555:1: ( ( '/' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5555:1: ( ( '/' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5556:1: ( '/' )
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_2_1_0_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5557:1: ( '/' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5558:1: '/'
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getOperationsSolidusKeyword_2_1_0_0()); 
            match(input,29,FOLLOW_29_in_rule__MultiplicativeExpression__OperationsAssignment_2_1_011147); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5573:1: rule__MultiplicativeExpression__ParametersAssignment_2_1_1 : ( ruleUnaryExpression ) ;
    public final void rule__MultiplicativeExpression__ParametersAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5577:1: ( ( ruleUnaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5578:1: ( ruleUnaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5578:1: ( ruleUnaryExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5579:1: ruleUnaryExpression
            {
             before(grammarAccess.getMultiplicativeExpressionAccess().getParametersUnaryExpressionParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_2_1_111186);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5588:1: rule__UnaryExpression__OperationsAssignment_0_1 : ( ( '!' ) ) ;
    public final void rule__UnaryExpression__OperationsAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5592:1: ( ( ( '!' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5593:1: ( ( '!' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5593:1: ( ( '!' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5594:1: ( '!' )
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5595:1: ( '!' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5596:1: '!'
            {
             before(grammarAccess.getUnaryExpressionAccess().getOperationsExclamationMarkKeyword_0_1_0()); 
            match(input,43,FOLLOW_43_in_rule__UnaryExpression__OperationsAssignment_0_111222); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5611:1: rule__UnaryExpression__ParametersAssignment_0_2 : ( rulePrimaryExpression ) ;
    public final void rule__UnaryExpression__ParametersAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5615:1: ( ( rulePrimaryExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5616:1: ( rulePrimaryExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5616:1: ( rulePrimaryExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5617:1: rulePrimaryExpression
            {
             before(grammarAccess.getUnaryExpressionAccess().getParametersPrimaryExpressionParserRuleCall_0_2_0()); 
            pushFollow(FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__ParametersAssignment_0_211261);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5626:1: rule__PrimaryExpression__ParametersAssignment_0_1 : ( ruleLiteral ) ;
    public final void rule__PrimaryExpression__ParametersAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5630:1: ( ( ruleLiteral ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5631:1: ( ruleLiteral )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5631:1: ( ruleLiteral )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5632:1: ruleLiteral
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersLiteralParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_ruleLiteral_in_rule__PrimaryExpression__ParametersAssignment_0_111292);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5641:1: rule__PrimaryExpression__ParametersAssignment_1_1 : ( ruleOrExpression ) ;
    public final void rule__PrimaryExpression__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5645:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5646:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5646:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5647:1: ruleOrExpression
            {
             before(grammarAccess.getPrimaryExpressionAccess().getParametersOrExpressionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__PrimaryExpression__ParametersAssignment_1_111323);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5656:1: rule__FunctionCall__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__FunctionCall__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5660:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5661:1: ( RULE_ID )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5661:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5662:1: RULE_ID
            {
             before(grammarAccess.getFunctionCallAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__FunctionCall__NameAssignment_011354); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5671:1: rule__FunctionCall__OperandsAssignment_2 : ( ( RULE_ID ) ) ;
    public final void rule__FunctionCall__OperandsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5675:1: ( ( ( RULE_ID ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5676:1: ( ( RULE_ID ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5676:1: ( ( RULE_ID ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5677:1: ( RULE_ID )
            {
             before(grammarAccess.getFunctionCallAccess().getOperandsFunctionOperandsCrossReference_2_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5678:1: ( RULE_ID )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5679:1: RULE_ID
            {
             before(grammarAccess.getFunctionCallAccess().getOperandsFunctionOperandsIDTerminalRuleCall_2_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__FunctionCall__OperandsAssignment_211389); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5692:1: rule__Literal__ValueStringAssignment_0_1 : ( RULE_INT ) ;
    public final void rule__Literal__ValueStringAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5696:1: ( ( RULE_INT ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5697:1: ( RULE_INT )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5697:1: ( RULE_INT )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5698:1: RULE_INT
            {
             before(grammarAccess.getLiteralAccess().getValueStringINTTerminalRuleCall_0_1_0()); 
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__Literal__ValueStringAssignment_0_111426); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5707:1: rule__Literal__ValueStringAssignment_1_1 : ( RULE_LITERALSTRING ) ;
    public final void rule__Literal__ValueStringAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5711:1: ( ( RULE_LITERALSTRING ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5712:1: ( RULE_LITERALSTRING )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5712:1: ( RULE_LITERALSTRING )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5713:1: RULE_LITERALSTRING
            {
             before(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_1_111457); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5722:1: rule__Literal__OperationsAssignment_2_1 : ( ( 'FORALL' ) ) ;
    public final void rule__Literal__OperationsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5726:1: ( ( ( 'FORALL' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5727:1: ( ( 'FORALL' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5727:1: ( ( 'FORALL' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5728:1: ( 'FORALL' )
            {
             before(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5729:1: ( 'FORALL' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5730:1: 'FORALL'
            {
             before(grammarAccess.getLiteralAccess().getOperationsFORALLKeyword_2_1_0()); 
            match(input,44,FOLLOW_44_in_rule__Literal__OperationsAssignment_2_111493); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5745:1: rule__Literal__ValueStringAssignment_2_3 : ( RULE_LITERALSTRING ) ;
    public final void rule__Literal__ValueStringAssignment_2_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5749:1: ( ( RULE_LITERALSTRING ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5750:1: ( RULE_LITERALSTRING )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5750:1: ( RULE_LITERALSTRING )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5751:1: RULE_LITERALSTRING
            {
             before(grammarAccess.getLiteralAccess().getValueStringLITERALSTRINGTerminalRuleCall_2_3_0()); 
            match(input,RULE_LITERALSTRING,FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_2_311532); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5760:1: rule__Literal__ParametersAssignment_2_5 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_2_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5764:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5765:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5765:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5766:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_2_5_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_2_511563);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5775:1: rule__Literal__ElementsAssignment_4_1 : ( ruleExistsTmlExpression ) ;
    public final void rule__Literal__ElementsAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5779:1: ( ( ruleExistsTmlExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5780:1: ( ruleExistsTmlExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5780:1: ( ruleExistsTmlExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5781:1: ruleExistsTmlExpression
            {
             before(grammarAccess.getLiteralAccess().getElementsExistsTmlExpressionParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_ruleExistsTmlExpression_in_rule__Literal__ElementsAssignment_4_111594);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5790:1: rule__Literal__ElementsAssignment_5_1 : ( ruleTmlExpression ) ;
    public final void rule__Literal__ElementsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5794:1: ( ( ruleTmlExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5795:1: ( ruleTmlExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5795:1: ( ruleTmlExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5796:1: ruleTmlExpression
            {
             before(grammarAccess.getLiteralAccess().getElementsTmlExpressionParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_ruleTmlExpression_in_rule__Literal__ElementsAssignment_5_111625);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5805:1: rule__Literal__ExpressionTypeAssignment_6_1 : ( ( '{' ) ) ;
    public final void rule__Literal__ExpressionTypeAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5809:1: ( ( ( '{' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5810:1: ( ( '{' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5810:1: ( ( '{' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5811:1: ( '{' )
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_6_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5812:1: ( '{' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5813:1: '{'
            {
             before(grammarAccess.getLiteralAccess().getExpressionTypeLeftCurlyBracketKeyword_6_1_0()); 
            match(input,45,FOLLOW_45_in_rule__Literal__ExpressionTypeAssignment_6_111661); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5828:1: rule__Literal__ParametersAssignment_6_2 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_6_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5832:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5833:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5833:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5834:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_2_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_6_211700);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5843:1: rule__Literal__ParametersAssignment_6_3_1 : ( ruleOrExpression ) ;
    public final void rule__Literal__ParametersAssignment_6_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5847:1: ( ( ruleOrExpression ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5848:1: ( ruleOrExpression )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5848:1: ( ruleOrExpression )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5849:1: ruleOrExpression
            {
             before(grammarAccess.getLiteralAccess().getParametersOrExpressionParserRuleCall_6_3_1_0()); 
            pushFollow(FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_6_3_111731);
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5858:1: rule__Literal__ElementsAssignment_7_1 : ( ( 'NULL' ) ) ;
    public final void rule__Literal__ElementsAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5862:1: ( ( ( 'NULL' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5863:1: ( ( 'NULL' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5863:1: ( ( 'NULL' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5864:1: ( 'NULL' )
            {
             before(grammarAccess.getLiteralAccess().getElementsNULLKeyword_7_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5865:1: ( 'NULL' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5866:1: 'NULL'
            {
             before(grammarAccess.getLiteralAccess().getElementsNULLKeyword_7_1_0()); 
            match(input,46,FOLLOW_46_in_rule__Literal__ElementsAssignment_7_111767); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5881:1: rule__Literal__ElementsAssignment_8_1 : ( ( 'TODAY' ) ) ;
    public final void rule__Literal__ElementsAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5885:1: ( ( ( 'TODAY' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5886:1: ( ( 'TODAY' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5886:1: ( ( 'TODAY' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5887:1: ( 'TODAY' )
            {
             before(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_8_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5888:1: ( 'TODAY' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5889:1: 'TODAY'
            {
             before(grammarAccess.getLiteralAccess().getElementsTODAYKeyword_8_1_0()); 
            match(input,47,FOLLOW_47_in_rule__Literal__ElementsAssignment_8_111811); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5904:1: rule__Literal__ElementsAssignment_9_1 : ( ( 'TRUE' ) ) ;
    public final void rule__Literal__ElementsAssignment_9_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5908:1: ( ( ( 'TRUE' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5909:1: ( ( 'TRUE' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5909:1: ( ( 'TRUE' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5910:1: ( 'TRUE' )
            {
             before(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_9_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5911:1: ( 'TRUE' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5912:1: 'TRUE'
            {
             before(grammarAccess.getLiteralAccess().getElementsTRUEKeyword_9_1_0()); 
            match(input,48,FOLLOW_48_in_rule__Literal__ElementsAssignment_9_111855); 
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
    // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5927:1: rule__Literal__ElementsAssignment_10_1 : ( ( 'FALSE' ) ) ;
    public final void rule__Literal__ElementsAssignment_10_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5931:1: ( ( ( 'FALSE' ) ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5932:1: ( ( 'FALSE' ) )
            {
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5932:1: ( ( 'FALSE' ) )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5933:1: ( 'FALSE' )
            {
             before(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_10_1_0()); 
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5934:1: ( 'FALSE' )
            // ../com.dexels.navajo.dsl.tsl.ui/src-gen/com/dexels/navajo/dsl/tsl/ui/contentassist/antlr/internal/InternalTsl.g:5935:1: 'FALSE'
            {
             before(grammarAccess.getLiteralAccess().getElementsFALSEKeyword_10_1_0()); 
            match(input,49,FOLLOW_49_in_rule__Literal__ElementsAssignment_10_111899); 
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


 

    public static final BitSet FOLLOW_ruleTml_in_entryRuleTml61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTml68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Group__0_in_ruleTml94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_entryRulePossibleExpression121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePossibleExpression128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group__0_in_rulePossibleExpression154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Group__0_in_ruleMessage214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_entryRuleMap241 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMap248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group__0_in_ruleMap274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty301 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group__0_in_ruleProperty334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_entryRuleExpressionTag361 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpressionTag368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExpressionTag__Group__0_in_ruleExpressionTag394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_entryRuleTopLevel421 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTopLevel428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__Group__0_in_ruleTopLevel454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_entryRulePathElement481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathElement488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathElement__Alternatives_in_rulePathElement514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_entryRulePathSequence541 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePathSequence548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__0_in_rulePathSequence574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_entryRuleTmlExpression601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTmlExpression608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathSequence_in_ruleTmlExpression634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_entryRuleExistsTmlExpression660 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExistsTmlExpression667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__0_in_ruleExistsTmlExpression693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression720 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__0_in_ruleOrExpression753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression780 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__0_in_ruleAndExpression813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_entryRuleEqualityExpression840 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEqualityExpression847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__0_in_ruleEqualityExpression873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_entryRuleAdditiveExpression900 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAdditiveExpression907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__0_in_ruleAdditiveExpression933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_entryRuleMultiplicativeExpression960 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplicativeExpression967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__0_in_ruleMultiplicativeExpression993 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_entryRuleUnaryExpression1020 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryExpression1027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Alternatives_in_ruleUnaryExpression1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_entryRulePrimaryExpression1080 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryExpression1087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Alternatives_in_rulePrimaryExpression1113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_entryRuleFunctionCall1140 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFunctionCall1147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__0_in_ruleFunctionCall1173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_entryRuleLiteral1202 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLiteral1209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Alternatives_in_ruleLiteral1235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Group_3_0__0_in_rule__Tml__Alternatives_31271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Tml__Alternatives_31290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__ChildrenAssignment_3_0_1_0_in_rule__Tml__Alternatives_3_0_11324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__ChildrenAssignment_3_0_1_1_in_rule__Tml__Alternatives_3_0_11342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group_2_0__0_in_rule__PossibleExpression__Alternatives_21375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__ValueAssignment_2_1_in_rule__PossibleExpression__Alternatives_21393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Group_3_0__0_in_rule__Message__Alternatives_31426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Message__Alternatives_31445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__ChildrenAssignment_3_0_1_0_in_rule__Message__Alternatives_3_0_11479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__ChildrenAssignment_3_0_1_1_in_rule__Message__Alternatives_3_0_11497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__ChildrenAssignment_3_0_1_2_in_rule__Message__Alternatives_3_0_11515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Map__Alternatives_41549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__0_in_rule__Map__Alternatives_41568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__ChildrenAssignment_4_1_1_0_in_rule__Map__Alternatives_4_1_11601 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__ChildrenAssignment_4_1_1_1_in_rule__Map__Alternatives_4_1_11619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__ChildrenAssignment_4_1_1_2_in_rule__Map__Alternatives_4_1_11637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Property__Alternatives_31671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group_3_1__0_in_rule__Property__Alternatives_31690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PathElement__Alternatives1723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__PathElement__Alternatives1741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__PathElement__Alternatives1761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_0__0_in_rule__EqualityExpression__Alternatives_21795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_1__0_in_rule__EqualityExpression__Alternatives_21813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_0__0_in_rule__AdditiveExpression__Alternatives_21846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_1__0_in_rule__AdditiveExpression__Alternatives_21864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_0__0_in_rule__MultiplicativeExpression__Alternatives_21897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_1__0_in_rule__MultiplicativeExpression__Alternatives_21915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__0_in_rule__UnaryExpression__Alternatives1948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__Alternatives1966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_0__0_in_rule__PrimaryExpression__Alternatives1998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__0_in_rule__PrimaryExpression__Alternatives2016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__0_in_rule__Literal__Alternatives2049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_1__0_in_rule__Literal__Alternatives2067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__0_in_rule__Literal__Alternatives2085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFunctionCall_in_rule__Literal__Alternatives2103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__0_in_rule__Literal__Alternatives2120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_5__0_in_rule__Literal__Alternatives2138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__0_in_rule__Literal__Alternatives2156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_7__0_in_rule__Literal__Alternatives2174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_8__0_in_rule__Literal__Alternatives2192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_9__0_in_rule__Literal__Alternatives2210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_10__0_in_rule__Literal__Alternatives2228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Group__0__Impl_in_rule__Tml__Group__02259 = new BitSet(new long[]{0x0000000000008810L});
    public static final BitSet FOLLOW_rule__Tml__Group__1_in_rule__Tml__Group__02262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__Tml__Group__0__Impl2290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Group__1__Impl_in_rule__Tml__Group__12321 = new BitSet(new long[]{0x0000000000008810L});
    public static final BitSet FOLLOW_rule__Tml__Group__2_in_rule__Tml__Group__12324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Group__2__Impl_in_rule__Tml__Group__22382 = new BitSet(new long[]{0x0000000000008800L});
    public static final BitSet FOLLOW_rule__Tml__Group__3_in_rule__Tml__Group__22385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__AttributesAssignment_2_in_rule__Tml__Group__2__Impl2412 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Tml__Group__3__Impl_in_rule__Tml__Group__32443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Alternatives_3_in_rule__Tml__Group__3__Impl2470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Group_3_0__0__Impl_in_rule__Tml__Group_3_0__02508 = new BitSet(new long[]{0x0000000000510000L});
    public static final BitSet FOLLOW_rule__Tml__Group_3_0__1_in_rule__Tml__Group_3_0__02511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Tml__Group_3_0__0__Impl2539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Group_3_0__1__Impl_in_rule__Tml__Group_3_0__12570 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_rule__Tml__Group_3_0__2_in_rule__Tml__Group_3_0__12573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tml__Alternatives_3_0_1_in_rule__Tml__Group_3_0__1__Impl2600 = new BitSet(new long[]{0x0000000000500002L});
    public static final BitSet FOLLOW_rule__Tml__Group_3_0__2__Impl_in_rule__Tml__Group_3_0__22631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__Tml__Group_3_0__2__Impl2659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group__0__Impl_in_rule__PossibleExpression__Group__02696 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group__1_in_rule__PossibleExpression__Group__02699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__KeyAssignment_0_in_rule__PossibleExpression__Group__0__Impl2726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group__1__Impl_in_rule__PossibleExpression__Group__12756 = new BitSet(new long[]{0x0000000000040020L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group__2_in_rule__PossibleExpression__Group__12759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__PossibleExpression__Group__1__Impl2787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group__2__Impl_in_rule__PossibleExpression__Group__22818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Alternatives_2_in_rule__PossibleExpression__Group__2__Impl2845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group_2_0__0__Impl_in_rule__PossibleExpression__Group_2_0__02881 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group_2_0__1_in_rule__PossibleExpression__Group_2_0__02884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__PossibleExpression__Group_2_0__0__Impl2912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group_2_0__1__Impl_in_rule__PossibleExpression__Group_2_0__12943 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group_2_0__2_in_rule__PossibleExpression__Group_2_0__12946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__ExpressionValueAssignment_2_0_1_in_rule__PossibleExpression__Group_2_0__1__Impl2973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PossibleExpression__Group_2_0__2__Impl_in_rule__PossibleExpression__Group_2_0__23003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__PossibleExpression__Group_2_0__2__Impl3031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Group__0__Impl_in_rule__Message__Group__03068 = new BitSet(new long[]{0x0000000000008810L});
    public static final BitSet FOLLOW_rule__Message__Group__1_in_rule__Message__Group__03071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Message__Group__0__Impl3099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Group__1__Impl_in_rule__Message__Group__13130 = new BitSet(new long[]{0x0000000000008810L});
    public static final BitSet FOLLOW_rule__Message__Group__2_in_rule__Message__Group__13133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Group__2__Impl_in_rule__Message__Group__23191 = new BitSet(new long[]{0x0000000000008800L});
    public static final BitSet FOLLOW_rule__Message__Group__3_in_rule__Message__Group__23194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__AttributesAssignment_2_in_rule__Message__Group__2__Impl3221 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Message__Group__3__Impl_in_rule__Message__Group__33252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Alternatives_3_in_rule__Message__Group__3__Impl3279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Group_3_0__0__Impl_in_rule__Message__Group_3_0__03317 = new BitSet(new long[]{0x0000000001700000L});
    public static final BitSet FOLLOW_rule__Message__Group_3_0__1_in_rule__Message__Group_3_0__03320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Message__Group_3_0__0__Impl3348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Group_3_0__1__Impl_in_rule__Message__Group_3_0__13379 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__Message__Group_3_0__2_in_rule__Message__Group_3_0__13382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Alternatives_3_0_1_in_rule__Message__Group_3_0__1__Impl3409 = new BitSet(new long[]{0x0000000001500002L});
    public static final BitSet FOLLOW_rule__Message__Group_3_0__2__Impl_in_rule__Message__Group_3_0__23440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Message__Group_3_0__2__Impl3468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group__0__Impl_in_rule__Map__Group__03505 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__Map__Group__1_in_rule__Map__Group__03508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group__1__Impl_in_rule__Map__Group__13566 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Map__Group__2_in_rule__Map__Group__13569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__Map__Group__1__Impl3597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group__2__Impl_in_rule__Map__Group__23628 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Map__Group__3_in_rule__Map__Group__23631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__MapNameAssignment_2_in_rule__Map__Group__2__Impl3658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group__3__Impl_in_rule__Map__Group__33688 = new BitSet(new long[]{0x0000000000008800L});
    public static final BitSet FOLLOW_rule__Map__Group__4_in_rule__Map__Group__33691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__AttributesAssignment_3_in_rule__Map__Group__3__Impl3718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group__4__Impl_in_rule__Map__Group__43748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Alternatives_4_in_rule__Map__Group__4__Impl3775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__0__Impl_in_rule__Map__Group_4_1__03815 = new BitSet(new long[]{0x0000000001D00000L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__1_in_rule__Map__Group_4_1__03818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Map__Group_4_1__0__Impl3846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__1__Impl_in_rule__Map__Group_4_1__13877 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__2_in_rule__Map__Group_4_1__13880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Alternatives_4_1_1_in_rule__Map__Group_4_1__1__Impl3907 = new BitSet(new long[]{0x0000000001500002L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__2__Impl_in_rule__Map__Group_4_1__23938 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__3_in_rule__Map__Group_4_1__23941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__Map__Group_4_1__2__Impl3969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__3__Impl_in_rule__Map__Group_4_1__34000 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__4_in_rule__Map__Group_4_1__34003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__MapClosingNameAssignment_4_1_3_in_rule__Map__Group_4_1__3__Impl4030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Map__Group_4_1__4__Impl_in_rule__Map__Group_4_1__44060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Map__Group_4_1__4__Impl4088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group__0__Impl_in_rule__Property__Group__04129 = new BitSet(new long[]{0x0000000000008810L});
    public static final BitSet FOLLOW_rule__Property__Group__1_in_rule__Property__Group__04132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__Property__Group__0__Impl4160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group__1__Impl_in_rule__Property__Group__14191 = new BitSet(new long[]{0x0000000000008810L});
    public static final BitSet FOLLOW_rule__Property__Group__2_in_rule__Property__Group__14194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group__2__Impl_in_rule__Property__Group__24252 = new BitSet(new long[]{0x0000000000008800L});
    public static final BitSet FOLLOW_rule__Property__Group__3_in_rule__Property__Group__24255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__AttributesAssignment_2_in_rule__Property__Group__2__Impl4282 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Property__Group__3__Impl_in_rule__Property__Group__34313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Alternatives_3_in_rule__Property__Group__3__Impl4340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group_3_1__0__Impl_in_rule__Property__Group_3_1__04378 = new BitSet(new long[]{0x0000000006000000L});
    public static final BitSet FOLLOW_rule__Property__Group_3_1__1_in_rule__Property__Group_3_1__04381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Property__Group_3_1__0__Impl4409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group_3_1__1__Impl_in_rule__Property__Group_3_1__14440 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__Property__Group_3_1__2_in_rule__Property__Group_3_1__14443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__ExpressionValueAssignment_3_1_1_in_rule__Property__Group_3_1__1__Impl4470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Property__Group_3_1__2__Impl_in_rule__Property__Group_3_1__24501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Property__Group_3_1__2__Impl4529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExpressionTag__Group__0__Impl_in_rule__ExpressionTag__Group__04566 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__ExpressionTag__Group__1_in_rule__ExpressionTag__Group__04569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__ExpressionTag__Group__0__Impl4597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExpressionTag__Group__1__Impl_in_rule__ExpressionTag__Group__14628 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__ExpressionTag__Group__2_in_rule__ExpressionTag__Group__14631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExpressionTag__ValueAssignment_1_in_rule__ExpressionTag__Group__1__Impl4658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExpressionTag__Group__2__Impl_in_rule__ExpressionTag__Group__24688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__ExpressionTag__Group__2__Impl4716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__Group__0__Impl_in_rule__TopLevel__Group__04753 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__TopLevel__Group__1_in_rule__TopLevel__Group__04756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__Group__1__Impl_in_rule__TopLevel__Group__14814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TopLevel__ToplevelExpressionAssignment_1_in_rule__TopLevel__Group__1__Impl4841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__0__Impl_in_rule__PathSequence__Group__04875 = new BitSet(new long[]{0x0000000020003010L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__1_in_rule__PathSequence__Group__04878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__PathSequence__Group__0__Impl4906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__1__Impl_in_rule__PathSequence__Group__14937 = new BitSet(new long[]{0x0000000000003010L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__2_in_rule__PathSequence__Group__14940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__PathSequence__Group__1__Impl4969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__2__Impl_in_rule__PathSequence__Group__25002 = new BitSet(new long[]{0x0000000060000000L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__3_in_rule__PathSequence__Group__25005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__PathSequence__Group__2__Impl5032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__3__Impl_in_rule__PathSequence__Group__35061 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__4_in_rule__PathSequence__Group__35064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group_3__0_in_rule__PathSequence__Group__3__Impl5091 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group__4__Impl_in_rule__PathSequence__Group__45122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__PathSequence__Group__4__Impl5150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group_3__0__Impl_in_rule__PathSequence__Group_3__05191 = new BitSet(new long[]{0x0000000000003010L});
    public static final BitSet FOLLOW_rule__PathSequence__Group_3__1_in_rule__PathSequence__Group_3__05194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__PathSequence__Group_3__0__Impl5222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PathSequence__Group_3__1__Impl_in_rule__PathSequence__Group_3__15253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePathElement_in_rule__PathSequence__Group_3__1__Impl5280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__0__Impl_in_rule__ExistsTmlExpression__Group__05313 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__1_in_rule__ExistsTmlExpression__Group__05316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__ExistsTmlExpression__Group__0__Impl5344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ExistsTmlExpression__Group__1__Impl_in_rule__ExistsTmlExpression__Group__15375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_rule__ExistsTmlExpression__Group__1__Impl5402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__0__Impl_in_rule__OrExpression__Group__05435 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__1_in_rule__OrExpression__Group__05438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__1__Impl_in_rule__OrExpression__Group__15496 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__2_in_rule__OrExpression__Group__15499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__ParametersAssignment_1_in_rule__OrExpression__Group__1__Impl5526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group__2__Impl_in_rule__OrExpression__Group__25556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_2__0_in_rule__OrExpression__Group__2__Impl5583 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_2__0__Impl_in_rule__OrExpression__Group_2__05620 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_2__1_in_rule__OrExpression__Group_2__05623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__OperationsAssignment_2_0_in_rule__OrExpression__Group_2__0__Impl5650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__Group_2__1__Impl_in_rule__OrExpression__Group_2__15680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__OrExpression__ParametersAssignment_2_1_in_rule__OrExpression__Group_2__1__Impl5707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__0__Impl_in_rule__AndExpression__Group__05741 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__1_in_rule__AndExpression__Group__05744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__1__Impl_in_rule__AndExpression__Group__15802 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__2_in_rule__AndExpression__Group__15805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__ParametersAssignment_1_in_rule__AndExpression__Group__1__Impl5832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group__2__Impl_in_rule__AndExpression__Group__25862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_2__0_in_rule__AndExpression__Group__2__Impl5889 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_2__0__Impl_in_rule__AndExpression__Group_2__05926 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_2__1_in_rule__AndExpression__Group_2__05929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__OperationsAssignment_2_0_in_rule__AndExpression__Group_2__0__Impl5956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__Group_2__1__Impl_in_rule__AndExpression__Group_2__15986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AndExpression__ParametersAssignment_2_1_in_rule__AndExpression__Group_2__1__Impl6013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__0__Impl_in_rule__EqualityExpression__Group__06047 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__1_in_rule__EqualityExpression__Group__06050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__1__Impl_in_rule__EqualityExpression__Group__16108 = new BitSet(new long[]{0x0000030000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__2_in_rule__EqualityExpression__Group__16111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_1_in_rule__EqualityExpression__Group__1__Impl6138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group__2__Impl_in_rule__EqualityExpression__Group__26168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Alternatives_2_in_rule__EqualityExpression__Group__2__Impl6195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_0__0__Impl_in_rule__EqualityExpression__Group_2_0__06232 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_0__1_in_rule__EqualityExpression__Group_2_0__06235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__OperationsAssignment_2_0_0_in_rule__EqualityExpression__Group_2_0__0__Impl6262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_0__1__Impl_in_rule__EqualityExpression__Group_2_0__16292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_2_0_1_in_rule__EqualityExpression__Group_2_0__1__Impl6319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_1__0__Impl_in_rule__EqualityExpression__Group_2_1__06353 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_1__1_in_rule__EqualityExpression__Group_2_1__06356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__OperationsAssignment_2_1_0_in_rule__EqualityExpression__Group_2_1__0__Impl6383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__Group_2_1__1__Impl_in_rule__EqualityExpression__Group_2_1__16413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__EqualityExpression__ParametersAssignment_2_1_1_in_rule__EqualityExpression__Group_2_1__1__Impl6440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__0__Impl_in_rule__AdditiveExpression__Group__06474 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__1_in_rule__AdditiveExpression__Group__06477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__1__Impl_in_rule__AdditiveExpression__Group__16535 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__2_in_rule__AdditiveExpression__Group__16538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_1_in_rule__AdditiveExpression__Group__1__Impl6565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group__2__Impl_in_rule__AdditiveExpression__Group__26595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Alternatives_2_in_rule__AdditiveExpression__Group__2__Impl6622 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_0__0__Impl_in_rule__AdditiveExpression__Group_2_0__06659 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_0__1_in_rule__AdditiveExpression__Group_2_0__06662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__AdditiveExpression__Group_2_0__0__Impl6690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_0__1__Impl_in_rule__AdditiveExpression__Group_2_0__16721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_2_0_1_in_rule__AdditiveExpression__Group_2_0__1__Impl6748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_1__0__Impl_in_rule__AdditiveExpression__Group_2_1__06782 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_1__1_in_rule__AdditiveExpression__Group_2_1__06785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__AdditiveExpression__Group_2_1__0__Impl6813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__Group_2_1__1__Impl_in_rule__AdditiveExpression__Group_2_1__16844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__AdditiveExpression__ParametersAssignment_2_1_1_in_rule__AdditiveExpression__Group_2_1__1__Impl6871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__0__Impl_in_rule__MultiplicativeExpression__Group__06905 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__1_in_rule__MultiplicativeExpression__Group__06908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__1__Impl_in_rule__MultiplicativeExpression__Group__16966 = new BitSet(new long[]{0x0000040020000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__2_in_rule__MultiplicativeExpression__Group__16969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_1_in_rule__MultiplicativeExpression__Group__1__Impl6996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group__2__Impl_in_rule__MultiplicativeExpression__Group__27026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Alternatives_2_in_rule__MultiplicativeExpression__Group__2__Impl7053 = new BitSet(new long[]{0x0000040020000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_0__0__Impl_in_rule__MultiplicativeExpression__Group_2_0__07090 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_0__1_in_rule__MultiplicativeExpression__Group_2_0__07093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_2_0_0_in_rule__MultiplicativeExpression__Group_2_0__0__Impl7120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_0__1__Impl_in_rule__MultiplicativeExpression__Group_2_0__17150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_2_0_1_in_rule__MultiplicativeExpression__Group_2_0__1__Impl7177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_1__0__Impl_in_rule__MultiplicativeExpression__Group_2_1__07211 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_1__1_in_rule__MultiplicativeExpression__Group_2_1__07214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__OperationsAssignment_2_1_0_in_rule__MultiplicativeExpression__Group_2_1__0__Impl7241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__Group_2_1__1__Impl_in_rule__MultiplicativeExpression__Group_2_1__17271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MultiplicativeExpression__ParametersAssignment_2_1_1_in_rule__MultiplicativeExpression__Group_2_1__1__Impl7298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__0__Impl_in_rule__UnaryExpression__Group_0__07332 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__1_in_rule__UnaryExpression__Group_0__07335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__1__Impl_in_rule__UnaryExpression__Group_0__17393 = new BitSet(new long[]{0x0003F004900000D0L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__2_in_rule__UnaryExpression__Group_0__17396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__OperationsAssignment_0_1_in_rule__UnaryExpression__Group_0__1__Impl7423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__Group_0__2__Impl_in_rule__UnaryExpression__Group_0__27453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryExpression__ParametersAssignment_0_2_in_rule__UnaryExpression__Group_0__2__Impl7480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_0__0__Impl_in_rule__PrimaryExpression__Group_0__07516 = new BitSet(new long[]{0x0003F000900000D0L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_0__1_in_rule__PrimaryExpression__Group_0__07519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_0__1__Impl_in_rule__PrimaryExpression__Group_0__17577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__ParametersAssignment_0_1_in_rule__PrimaryExpression__Group_0__1__Impl7604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__0__Impl_in_rule__PrimaryExpression__Group_1__07638 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__1_in_rule__PrimaryExpression__Group_1__07641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__PrimaryExpression__Group_1__0__Impl7669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__1__Impl_in_rule__PrimaryExpression__Group_1__17700 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__2_in_rule__PrimaryExpression__Group_1__17703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__ParametersAssignment_1_1_in_rule__PrimaryExpression__Group_1__1__Impl7730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryExpression__Group_1__2__Impl_in_rule__PrimaryExpression__Group_1__27760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rule__PrimaryExpression__Group_1__2__Impl7788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__0__Impl_in_rule__FunctionCall__Group__07825 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__1_in_rule__FunctionCall__Group__07828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__NameAssignment_0_in_rule__FunctionCall__Group__0__Impl7855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__1__Impl_in_rule__FunctionCall__Group__17885 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__2_in_rule__FunctionCall__Group__17888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__FunctionCall__Group__1__Impl7916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__2__Impl_in_rule__FunctionCall__Group__27947 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__3_in_rule__FunctionCall__Group__27950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__OperandsAssignment_2_in_rule__FunctionCall__Group__2__Impl7977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__FunctionCall__Group__3__Impl_in_rule__FunctionCall__Group__38007 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rule__FunctionCall__Group__3__Impl8035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__0__Impl_in_rule__Literal__Group_0__08076 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__1_in_rule__Literal__Group_0__08079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_0__1__Impl_in_rule__Literal__Group_0__18137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_0_1_in_rule__Literal__Group_0__1__Impl8164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_1__0__Impl_in_rule__Literal__Group_1__08198 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_rule__Literal__Group_1__1_in_rule__Literal__Group_1__08201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_1__1__Impl_in_rule__Literal__Group_1__18259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_1_1_in_rule__Literal__Group_1__1__Impl8286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__0__Impl_in_rule__Literal__Group_2__08320 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__1_in_rule__Literal__Group_2__08323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__1__Impl_in_rule__Literal__Group_2__18381 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__2_in_rule__Literal__Group_2__18384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__OperationsAssignment_2_1_in_rule__Literal__Group_2__1__Impl8411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__2__Impl_in_rule__Literal__Group_2__28441 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__3_in_rule__Literal__Group_2__28444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__Literal__Group_2__2__Impl8472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__3__Impl_in_rule__Literal__Group_2__38503 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__4_in_rule__Literal__Group_2__38506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ValueStringAssignment_2_3_in_rule__Literal__Group_2__3__Impl8533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__4__Impl_in_rule__Literal__Group_2__48563 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__5_in_rule__Literal__Group_2__48566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_rule__Literal__Group_2__4__Impl8594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__5__Impl_in_rule__Literal__Group_2__58625 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__6_in_rule__Literal__Group_2__58628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_2_5_in_rule__Literal__Group_2__5__Impl8655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_2__6__Impl_in_rule__Literal__Group_2__68685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rule__Literal__Group_2__6__Impl8713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__0__Impl_in_rule__Literal__Group_4__08758 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__1_in_rule__Literal__Group_4__08761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_4__1__Impl_in_rule__Literal__Group_4__18819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_4_1_in_rule__Literal__Group_4__1__Impl8846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_5__0__Impl_in_rule__Literal__Group_5__08880 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_5__1_in_rule__Literal__Group_5__08883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_5__1__Impl_in_rule__Literal__Group_5__18941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_5_1_in_rule__Literal__Group_5__1__Impl8968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__0__Impl_in_rule__Literal__Group_6__09002 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__1_in_rule__Literal__Group_6__09005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__1__Impl_in_rule__Literal__Group_6__19063 = new BitSet(new long[]{0x0003F834900000D0L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__2_in_rule__Literal__Group_6__19066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ExpressionTypeAssignment_6_1_in_rule__Literal__Group_6__1__Impl9093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__2__Impl_in_rule__Literal__Group_6__29123 = new BitSet(new long[]{0x0000003000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__3_in_rule__Literal__Group_6__29126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_6_2_in_rule__Literal__Group_6__2__Impl9153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__3__Impl_in_rule__Literal__Group_6__39184 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__4_in_rule__Literal__Group_6__39187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6_3__0_in_rule__Literal__Group_6__3__Impl9214 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6__4__Impl_in_rule__Literal__Group_6__49245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__Literal__Group_6__4__Impl9273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6_3__0__Impl_in_rule__Literal__Group_6_3__09314 = new BitSet(new long[]{0x0003F804900000D0L});
    public static final BitSet FOLLOW_rule__Literal__Group_6_3__1_in_rule__Literal__Group_6_3__09317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_rule__Literal__Group_6_3__0__Impl9345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_6_3__1__Impl_in_rule__Literal__Group_6_3__19376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ParametersAssignment_6_3_1_in_rule__Literal__Group_6_3__1__Impl9403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_7__0__Impl_in_rule__Literal__Group_7__09437 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_7__1_in_rule__Literal__Group_7__09440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_7__1__Impl_in_rule__Literal__Group_7__19498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_7_1_in_rule__Literal__Group_7__1__Impl9525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_8__0__Impl_in_rule__Literal__Group_8__09559 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_8__1_in_rule__Literal__Group_8__09562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_8__1__Impl_in_rule__Literal__Group_8__19620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_8_1_in_rule__Literal__Group_8__1__Impl9647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_9__0__Impl_in_rule__Literal__Group_9__09681 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_9__1_in_rule__Literal__Group_9__09684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_9__1__Impl_in_rule__Literal__Group_9__19742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_9_1_in_rule__Literal__Group_9__1__Impl9769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_10__0__Impl_in_rule__Literal__Group_10__09803 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_rule__Literal__Group_10__1_in_rule__Literal__Group_10__09806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__Group_10__1__Impl_in_rule__Literal__Group_10__19864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Literal__ElementsAssignment_10_1_in_rule__Literal__Group_10__1__Impl9891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_rule__Tml__AttributesAssignment_29930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_rule__Tml__ChildrenAssignment_3_0_1_09961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_rule__Tml__ChildrenAssignment_3_0_1_19992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PossibleExpression__KeyAssignment_010023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_rule__PossibleExpression__ExpressionValueAssignment_2_0_110054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ATTRIBUTESTRING_in_rule__PossibleExpression__ValueAssignment_2_110085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_rule__Message__AttributesAssignment_210116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_rule__Message__ChildrenAssignment_3_0_1_010147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_rule__Message__ChildrenAssignment_3_0_1_110178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_rule__Message__ChildrenAssignment_3_0_1_210209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Map__MapNameAssignment_210240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_rule__Map__AttributesAssignment_310271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_rule__Map__ChildrenAssignment_4_1_1_010302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_rule__Map__ChildrenAssignment_4_1_1_110333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMap_in_rule__Map__ChildrenAssignment_4_1_1_210364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Map__MapClosingNameAssignment_4_1_310395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePossibleExpression_in_rule__Property__AttributesAssignment_210426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpressionTag_in_rule__Property__ExpressionValueAssignment_3_1_110457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTopLevel_in_rule__ExpressionTag__ValueAssignment_110488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__TopLevel__ToplevelExpressionAssignment_110519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_110550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__OrExpression__OperationsAssignment_2_010586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_rule__OrExpression__ParametersAssignment_2_110625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_110656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_rule__AndExpression__OperationsAssignment_2_010692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEqualityExpression_in_rule__AndExpression__ParametersAssignment_2_110731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_110762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_rule__EqualityExpression__OperationsAssignment_2_0_010798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_2_0_110837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_rule__EqualityExpression__OperationsAssignment_2_1_010873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAdditiveExpression_in_rule__EqualityExpression__ParametersAssignment_2_1_110912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_110943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_2_0_110974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplicativeExpression_in_rule__AdditiveExpression__ParametersAssignment_2_1_111005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_111036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_rule__MultiplicativeExpression__OperationsAssignment_2_0_011072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_2_0_111111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__MultiplicativeExpression__OperationsAssignment_2_1_011147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryExpression_in_rule__MultiplicativeExpression__ParametersAssignment_2_1_111186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_rule__UnaryExpression__OperationsAssignment_0_111222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryExpression_in_rule__UnaryExpression__ParametersAssignment_0_211261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLiteral_in_rule__PrimaryExpression__ParametersAssignment_0_111292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__PrimaryExpression__ParametersAssignment_1_111323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__FunctionCall__NameAssignment_011354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__FunctionCall__OperandsAssignment_211389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__Literal__ValueStringAssignment_0_111426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_1_111457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_rule__Literal__OperationsAssignment_2_111493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LITERALSTRING_in_rule__Literal__ValueStringAssignment_2_311532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_2_511563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExistsTmlExpression_in_rule__Literal__ElementsAssignment_4_111594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTmlExpression_in_rule__Literal__ElementsAssignment_5_111625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_rule__Literal__ExpressionTypeAssignment_6_111661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_6_211700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_rule__Literal__ParametersAssignment_6_3_111731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_rule__Literal__ElementsAssignment_7_111767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_rule__Literal__ElementsAssignment_8_111811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_rule__Literal__ElementsAssignment_9_111855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_rule__Literal__ElementsAssignment_10_111899 = new BitSet(new long[]{0x0000000000000002L});

}