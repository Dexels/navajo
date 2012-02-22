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

import org.w3c.css.sac.AttributeCondition;

/**
 * This class provides an abstract implementation of the {@link
 * org.w3c.css.sac.AttributeCondition} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractAttributeCondition implements AttributeCondition,
		ExtendedCondition {

	/**
	 * The attribute value.
	 */
	protected String value;

	/**
	 * Creates a new AbstractAttributeCondition object.
	 */
	protected AbstractAttributeCondition(String value) {
		this.value = value;
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
		AbstractAttributeCondition c = (AbstractAttributeCondition) obj;
		return c.value.equals(value);
	}

	/**
	 * equal objects should have equal hashCodes.
	 * 
	 * @return hashCode of this AbstractAttributeCondition
	 */
	public int hashCode() {
		return value == null ? -1 : value.hashCode();
	}

	/**
	 * Returns the specificity of this condition.
	 */
	public int getSpecificity() {
		return 1 << 8;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.AttributeCondition#getValue()}.
	 */
	public String getValue() {
		return value;
	}
}
