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
package org.akrogen.tkui.css.core.dom;

import java.util.List;

import org.w3c.css.sac.Condition;
import org.w3c.css.sac.Selector;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.stylesheets.StyleSheet;

/**
 * Extend {@link DocumentCSS} to add methods like add/remove style sheet.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface ExtendedDocumentCSS extends DocumentCSS {

	public static final Integer SAC_ID_CONDITION = new Integer(
			Condition.SAC_ID_CONDITION);
	public static final Integer SAC_CLASS_CONDITION = new Integer(
			Condition.SAC_CLASS_CONDITION);
	public static final Integer SAC_PSEUDO_CLASS_CONDITION = new Integer(
			Condition.SAC_PSEUDO_CLASS_CONDITION);
	public static final Integer OTHER_SAC_CONDITIONAL_SELECTOR = new Integer(
			Selector.SAC_CONDITIONAL_SELECTOR);

	public static final Integer OTHER_SAC_SELECTOR = new Integer(999);

	public void addStyleSheet(StyleSheet styleSheet);

	public void removeAllStyleSheets();

	public List queryConditionSelector(int conditionType);

	public List querySelector(int selectorType, int conditionType);

}
