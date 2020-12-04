/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.akrogen.tkui.css.core.dom.ExtendedCSSRule;
import org.akrogen.tkui.css.core.dom.ExtendedDocumentCSS;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

/**
 * w3c {@link DocumentCSS} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class DocumentCSSImpl implements ExtendedDocumentCSS {

	private StyleSheetListImpl styleSheetList = new StyleSheetListImpl();

	/**
	 * key=selector type, value = CSSStyleDeclaration
	 */
	private Map styleDeclarationMap = null;

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.stylesheets.DocumentStyle#getStyleSheets()
	 */
	public StyleSheetList getStyleSheets() {
		return styleSheetList;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.DocumentCSS#getOverrideStyle(org.w3c.dom.Element, java.lang.String)
	 */
	public CSSStyleDeclaration getOverrideStyle(Element element, String s) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.akrogen.tkui.css.core.dom.ExtendedDocumentCSS#addStyleSheet(org.w3c.dom.stylesheets.StyleSheet)
	 */
	public void addStyleSheet(StyleSheet styleSheet) {
		styleSheetList.addStyleSheet(styleSheet);
	}

	/*
	 * (non-Javadoc)
	 * @see org.akrogen.tkui.css.core.dom.ExtendedDocumentCSS#removeAllStyleSheets()
	 */
	public void removeAllStyleSheets() {
		styleSheetList.removeAllStyleSheets();
		this.styleDeclarationMap = null;
	}

	public List queryConditionSelector(int conditionType) {
		return querySelector(Selector.SAC_CONDITIONAL_SELECTOR, conditionType);
	}

	public List querySelector(int selectorType, int conditionType) {
		List list = getCSSStyleDeclarationList(selectorType, conditionType);
		if (list != null)
			return list;
		int l = styleSheetList.getLength();
		for (int i = 0; i < l; i++) {
			CSSStyleSheet styleSheet = (CSSStyleSheet) styleSheetList.item(i);
			CSSRuleList ruleList = styleSheet.getCssRules();
			list = querySelector(ruleList, selectorType, conditionType);
			setCSSStyleDeclarationList(list, selectorType, conditionType);
		}
		return list;
	}

	protected List querySelector(CSSRuleList ruleList, int selectorType,
			int selectorConditionType) {
		List list = new ArrayList();
		int length = ruleList.getLength();
		for (int i = 0; i < length; i++) {
			CSSRule rule = ruleList.item(i);
			switch (rule.getType()) {
			case CSSRule.STYLE_RULE: {
				if (rule instanceof ExtendedCSSRule) {
					ExtendedCSSRule r = (ExtendedCSSRule) rule;
					SelectorList selectorList = r.getSelectorList();
					// Loop for SelectorList
					int l = selectorList.getLength();
					for (int j = 0; j < l; j++) {
						Selector selector = (Selector) selectorList.item(j);
						if (selector.getSelectorType() == selectorType) {
							switch (selectorType) {
							case Selector.SAC_CONDITIONAL_SELECTOR:
								// It's conditional selector
								ConditionalSelector conditionalSelector = (ConditionalSelector) selector;
								short conditionType = conditionalSelector
										.getCondition().getConditionType();
								if (selectorConditionType == conditionType) {
									// current selector match the current CSS
									// Rule
									// CSSStyleRule styleRule = (CSSStyleRule)
									// rule;
									list.add(selector);
								}
							}
						}

					}
				}
			}
			}
		}
		return list;
	}

	protected List getCSSStyleDeclarationList(int selectorType,
			int conditionType) {
		Integer key = getKey(selectorType, conditionType);
		return (List) getStyleDeclarationMap().get(key);
	}

	protected void setCSSStyleDeclarationList(List list, int selectorType,
			int conditionType) {
		Integer key = getKey(selectorType, conditionType);
		getStyleDeclarationMap().put(key, list);
	}

	protected Integer getKey(int selectorType, int conditionType) {
		if (selectorType == Selector.SAC_CONDITIONAL_SELECTOR) {
			if (conditionType == SAC_CLASS_CONDITION.intValue())
				return SAC_CLASS_CONDITION;
			if (conditionType == SAC_ID_CONDITION.intValue())
				return SAC_ID_CONDITION;
			if (conditionType == SAC_PSEUDO_CLASS_CONDITION.intValue())
				return SAC_PSEUDO_CLASS_CONDITION;
			return OTHER_SAC_CONDITIONAL_SELECTOR;
		}

		return OTHER_SAC_SELECTOR;
	}

	protected Map getStyleDeclarationMap() {
		if (styleDeclarationMap == null)
			styleDeclarationMap = new HashMap();
		return styleDeclarationMap;
	}
}
