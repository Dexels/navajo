/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public interface TipiReference {
	/**
	 * Sets the value of this reference.
	 * 
	 * @param expression
	 *            The unevaluated expression. Only used by AttributeRef, because
	 *            the TipiComponentImpl likes to know the unevaluated
	 *            expression.
	 * @param value
	 *            The evaluated object. This one is ignored by AttributeRef, as
	 *            it will evaluate the expression itself. todo: Change that.
	 * @param tc
	 *            The owner of this reference.
	 */
	public void setValue(Object expression);

	public Object getValue();

}
