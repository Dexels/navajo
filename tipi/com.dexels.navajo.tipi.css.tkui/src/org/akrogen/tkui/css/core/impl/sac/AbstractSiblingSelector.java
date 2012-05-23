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

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SiblingSelector;
import org.w3c.css.sac.SimpleSelector;

/**
 * This class provides an abstract implementation of the {@link
 * org.w3c.css.sac.SiblingSelector} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractSiblingSelector implements SiblingSelector,
		ExtendedSelector {

	/**
	 * The node type.
	 */
	protected short nodeType;

	/**
	 * The selector.
	 */
	protected Selector selector;

	/**
	 * The simple selector.
	 */
	protected SimpleSelector simpleSelector;

	/**
	 * Creates a new SiblingSelector object.
	 */
	protected AbstractSiblingSelector(short type, Selector sel,
			SimpleSelector simple) {
		nodeType = type;
		selector = sel;
		simpleSelector = simple;
	}

	/**
	 * Returns the node type.
	 */
	public short getNodeType() {
		return nodeType;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj
	 *            the reference object with which to compare.
	 */
	public boolean equals(Object obj) {
		if (obj == null || (obj.getClass() != getClass())) {
			return false;
		}
		AbstractSiblingSelector s = (AbstractSiblingSelector) obj;
		return s.simpleSelector.equals(simpleSelector);
	}

	/**
	 * Returns the specificity of this selector.
	 */
	public int getSpecificity() {
		return ((ExtendedSelector) selector).getSpecificity()
				+ ((ExtendedSelector) simpleSelector).getSpecificity();
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SiblingSelector#getSelector()}.
	 */
	public Selector getSelector() {
		return selector;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.SiblingSelector#getSiblingSelector()}.
	 */
	public SimpleSelector getSiblingSelector() {
		return simpleSelector;
	}
}
