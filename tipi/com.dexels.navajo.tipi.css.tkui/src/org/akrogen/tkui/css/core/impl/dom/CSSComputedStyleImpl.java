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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.akrogen.tkui.css.core.dom.CSSProperty;
import org.akrogen.tkui.css.core.dom.CSSPropertyList;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.ElementSelector;
import org.w3c.css.sac.Selector;
import org.w3c.dom.css.CSSStyleDeclaration;

/**
 * CSS computed style which concatenate list of CSSComputedStyleImpl to manage
 * styles coming from Condition Selector (ex : Label#MyId) and other selectors
 * (ex : Label).
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSComputedStyleImpl extends CSSStyleDeclarationImpl implements
		CSSStyleDeclaration {

	private static final Integer OTHER_SAC_CONDITIONAL_SELECTOR = new Integer(
			Selector.SAC_CONDITIONAL_SELECTOR);
	private static final Integer SAC_DESCENDANT_SELECTOR = new Integer(
			Selector.SAC_DESCENDANT_SELECTOR);
	private static final Integer SAC_ID_CONDITION = new Integer(
			Condition.SAC_ID_CONDITION);
	private static final Integer SAC_CLASS_CONDITION = new Integer(
			Condition.SAC_CLASS_CONDITION);
	private static final Integer SAC_PSEUDO_CLASS_CONDITION = new Integer(
			Condition.SAC_PSEUDO_CLASS_CONDITION);
	
	

	private static final Integer OTHER_SAC_SELECTOR = new Integer(999);

	public CSSComputedStyleImpl(List styleDeclarations) {
		super(null);

		Map stylesMap = new HashMap();
		Map stylesMapWithNS = null;
		for (Iterator iterator = styleDeclarations.iterator(); iterator
				.hasNext();) {
			CSSStyleDeclarationImpl styleDeclaration = (CSSStyleDeclarationImpl) iterator
					.next();
			Selector selector = styleDeclaration.getSelector();
			short selectorType = selector.getSelectorType();
			switch (selectorType) {
			case Selector.SAC_CONDITIONAL_SELECTOR:
				ConditionalSelector conditionalSelector = (ConditionalSelector) selector;
				short conditionType = conditionalSelector.getCondition()
						.getConditionType();
				switch (conditionType) {
				case Condition.SAC_ID_CONDITION:
					addStyleDeclarationToMap(styleDeclaration,
							SAC_ID_CONDITION, stylesMap);
					break;
				case Condition.SAC_CLASS_CONDITION:
					addStyleDeclarationToMap(styleDeclaration,
							SAC_CLASS_CONDITION, stylesMap);
					break;
				case Condition.SAC_PSEUDO_CLASS_CONDITION:
					addStyleDeclarationToMap(styleDeclaration,
							SAC_PSEUDO_CLASS_CONDITION, stylesMap);
					break;
				default:
					addStyleDeclarationToMap(styleDeclaration,
							OTHER_SAC_CONDITIONAL_SELECTOR, stylesMap);
					break;

				}
				break;
			case Selector.SAC_DESCENDANT_SELECTOR:
				addStyleDeclarationToMap(styleDeclaration,
						SAC_DESCENDANT_SELECTOR, stylesMap);
				break;
			default:
				boolean hasNamespace = false;
				if (selector instanceof ElementSelector) {
					ElementSelector elementSelector = (ElementSelector) selector;
					if (elementSelector.getNamespaceURI() != null) {
						hasNamespace = true;
						if (stylesMapWithNS == null)
							stylesMapWithNS = new HashMap();
						addStyleDeclarationToMap(styleDeclaration,
								OTHER_SAC_SELECTOR, stylesMapWithNS);
					}
				}
				if (hasNamespace == false)
					addStyleDeclarationToMap(styleDeclaration,
							OTHER_SAC_SELECTOR, stylesMap);
				break;
			}
		}

		addStyleDeclarations(SAC_ID_CONDITION, stylesMap);
		addStyleDeclarations(SAC_CLASS_CONDITION, stylesMap);
		addStyleDeclarations(SAC_PSEUDO_CLASS_CONDITION, stylesMap);
		addStyleDeclarations(OTHER_SAC_CONDITIONAL_SELECTOR, stylesMap);
		if (stylesMapWithNS != null) {
			addStyleDeclarations(OTHER_SAC_SELECTOR, stylesMapWithNS);
		}
		addStyleDeclarations(SAC_DESCENDANT_SELECTOR, stylesMap);
		addStyleDeclarations(OTHER_SAC_SELECTOR, stylesMap);
	}

	protected void addStyleDeclarationToMap(
			CSSStyleDeclaration styleDeclaration, Integer selectorType,
			Map stylesMap) {
		List styles = (List) stylesMap.get(selectorType);
		if (styles == null) {
			styles = new ArrayList();
			stylesMap.put(selectorType, styles);
		}
		styles.add(styleDeclaration);
	}

	private void addStyleDeclarations(Integer selectorType, Map stylesMap) {
		List styleDeclarations = (List) stylesMap.get(selectorType);
		if (styleDeclarations != null) {
			for (Iterator iterator = styleDeclarations.iterator(); iterator
					.hasNext();) {
				CSSStyleDeclarationImpl declaration = (CSSStyleDeclarationImpl) iterator
						.next();
				addCSSPropertyList(declaration.getCSSPropertyList());
			}
		}
	}

	protected void addCSSPropertyList(CSSPropertyList properties) {
		int length = properties.getLength();
		for (int i = 0; i < length; i++) {
			CSSProperty property = properties.item(i);
			super.addProperty(property);
		}
	}

}
