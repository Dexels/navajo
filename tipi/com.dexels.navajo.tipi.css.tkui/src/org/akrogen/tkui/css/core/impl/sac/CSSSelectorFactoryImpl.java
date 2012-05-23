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
package org.akrogen.tkui.css.core.impl.sac;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CharacterDataSelector;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.DescendantSelector;
import org.w3c.css.sac.ElementSelector;
import org.w3c.css.sac.NegativeSelector;
import org.w3c.css.sac.ProcessingInstructionSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorFactory;
import org.w3c.css.sac.SiblingSelector;
import org.w3c.css.sac.SimpleSelector;

/**
 * This class implements the {@link org.w3c.css.sac.SelectorFactory} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSSelectorFactoryImpl implements SelectorFactory {

	/**
	 * The instance of this class.
	 */
	public static final SelectorFactory INSTANCE = new CSSSelectorFactoryImpl();

	/**
	 * This class does not need to be instantiated.
	 */
	protected CSSSelectorFactoryImpl() {
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * SelectorFactory#createConditionalSelector(SimpleSelector,Condition)}.
	 */
	public ConditionalSelector createConditionalSelector(
			SimpleSelector selector, Condition condition) throws CSSException {
		return new CSSConditionalSelectorImpl(selector, condition);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SelectorFactory#createAnyNodeSelector()}.
	 */
	public SimpleSelector createAnyNodeSelector() throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SelectorFactory#createRootNodeSelector()}.
	 */
	public SimpleSelector createRootNodeSelector() throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SelectorFactory#createNegativeSelector(SimpleSelector)}.
	 */
	public NegativeSelector createNegativeSelector(SimpleSelector selector)
			throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SelectorFactory#createElementSelector(String,String)}.
	 */
	public ElementSelector createElementSelector(String namespaceURI,
			String tagName) throws CSSException {
		return new CSSElementSelectorImpl(namespaceURI, tagName);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SelectorFactory#createTextNodeSelector(String)}.
	 */
	public CharacterDataSelector createTextNodeSelector(String data)
			throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SelectorFactory#createCDataSectionSelector(String)}.
	 */
	public CharacterDataSelector createCDataSectionSelector(String data)
			throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * SelectorFactory#createProcessingInstructionSelector(String,String)}.
	 */
	public ProcessingInstructionSelector createProcessingInstructionSelector(
			String target, String data) throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SelectorFactory#createCommentSelector(String)}.
	 */
	public CharacterDataSelector createCommentSelector(String data)
			throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * SelectorFactory#createPseudoElementSelector(String,String)}.
	 */
	public ElementSelector createPseudoElementSelector(String namespaceURI,
			String pseudoName) throws CSSException {
		return new CSSPseudoElementSelectorImpl(namespaceURI, pseudoName);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * SelectorFactory#createDescendantSelector(Selector,SimpleSelector)}.
	 */
	public DescendantSelector createDescendantSelector(Selector parent,
			SimpleSelector descendant) throws CSSException {
		return new CSSDescendantSelectorImpl(parent, descendant);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * SelectorFactory#createChildSelector(Selector,SimpleSelector)}.
	 */
	public DescendantSelector createChildSelector(Selector parent,
			SimpleSelector child) throws CSSException {
		return new CSSChildSelectorImpl(parent, child);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * SelectorFactory#createDirectAdjacentSelector(short,Selector,SimpleSelector)}.
	 */
	public SiblingSelector createDirectAdjacentSelector(short nodeType,
			Selector child, SimpleSelector directAdjacent) throws CSSException {
		return new CSSDirectAdjacentSelectorImpl(nodeType, child,
				directAdjacent);
	}
}
