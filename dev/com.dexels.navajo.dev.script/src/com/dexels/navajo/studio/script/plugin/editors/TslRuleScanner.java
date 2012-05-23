/*
 * Created on Jun 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TslRuleScanner extends RuleBasedScanner {
    public TslRuleScanner() {
        Token keyword = new Token(new TextAttribute(new Color(null,220,240,240), null, SWT.BOLD));
        setRules(new IRule[]{
                new SingleLineRule("aap","noot",keyword)
        });
//        SingleLineRule slr;
//        IRule r;
    }
 }
