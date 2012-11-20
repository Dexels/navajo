/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.core.impl.dom;

import java.io.Serializable;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSUnknownRule;

/**
 * w3c {@link CSSUnknownRule} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSUnknownRuleImpl extends AbstractCSSNode implements
		CSSUnknownRule, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2992456242269976324L;
	private CSSStyleSheet parentStyleSheet = null;
	private CSSRule parentRule = null;
	private String text = null;

	public CSSUnknownRuleImpl(CSSStyleSheet parentStyleSheet,
			CSSRule parentRule, String text) {
		this.parentStyleSheet = parentStyleSheet;
		this.parentRule = parentRule;
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getType()
	 */
	public short getType() {
		return UNKNOWN_RULE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getCssText()
	 */
	public String getCssText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#setCssText(java.lang.String)
	 */
	public void setCssText(String cssText) throws DOMException {
		/*
		 * if( _parentStyleSheet != null && _parentStyleSheet.isReadOnly() )
		 * throw new DOMExceptionImpl( DOMException.NO_MODIFICATION_ALLOWED_ERR,
		 * DOMExceptionImpl.READ_ONLY_STYLE_SHEET );
		 * 
		 * try { // // Parse the rule string and retrieve the rule //
		 * StringReader sr = new StringReader( cssText ); CSS2Parser parser =
		 * new CSS2Parser( sr ); ASTStyleSheetRuleSingle ssrs =
		 * parser.styleSheetRuleSingle(); CSSRule r = (CSSRule)
		 * ssrs.jjtGetChild( 0 ); // // The rule must be an unknown rule // if(
		 * r.getType() == CSSRule.UNKNOWN_RULE ) { _text =
		 * ((ASTUnknownRule)r)._text; setChildren( ((SimpleNode)r).getChildren() ); }
		 * else { throw new DOMExceptionImpl(
		 * DOMException.INVALID_MODIFICATION_ERR,
		 * DOMExceptionImpl.EXPECTING_UNKNOWN_RULE ); } } catch( ParseException
		 * e ) { throw new DOMExceptionImpl( DOMException.SYNTAX_ERR,
		 * DOMExceptionImpl.SYNTAX_ERROR, e.getMessage() ); }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getParentStyleSheet()
	 */
	public CSSStyleSheet getParentStyleSheet() {
		return parentStyleSheet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getParentRule()
	 */
	public CSSRule getParentRule() {
		return parentRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getCssText();
	}
}
