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

import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;

/**
 * This class provides an abstract implementation of the {@link
 * org.w3c.css.sac.CombinatorCondition} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCombinatorCondition implements
		CombinatorCondition, ExtendedCondition {

	/**
	 * The first condition.
	 */
	protected Condition firstCondition;

	/**
	 * The second condition.
	 */
	protected Condition secondCondition;

	/**
	 * Creates a new CombinatorCondition object.
	 */
	protected AbstractCombinatorCondition(Condition c1, Condition c2) {
		firstCondition = c1;
		secondCondition = c2;
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
		AbstractCombinatorCondition c = (AbstractCombinatorCondition) obj;
		return (c.firstCondition.equals(firstCondition) && c.secondCondition
				.equals(secondCondition));
	}

	/**
	 * Returns the specificity of this condition.
	 */
	public int getSpecificity() {
		return ((ExtendedCondition) getFirstCondition()).getSpecificity()
				+ ((ExtendedCondition) getSecondCondition()).getSpecificity();
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.CombinatorCondition#getFirstCondition()}.
	 */
	public Condition getFirstCondition() {
		return firstCondition;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.CombinatorCondition#getSecondCondition()}.
	 */
	public Condition getSecondCondition() {
		return secondCondition;
	}
}
