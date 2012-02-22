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
 
import java.util.Set;

import org.w3c.css.sac.Selector;
import org.w3c.dom.Element;

/**
 * This interface extends the {@link org.w3c.css.sac.Selector}.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface ExtendedSelector extends Selector {

	/**
	 * Tests whether this selector matches the given element.
	 */
	boolean match(Element e, String pseudoE);

	/**
	 * Returns the specificity of this selector.
	 */
	int getSpecificity();

	/**
	 * Fills the given set with the attribute names found in this selector.
	 */
	void fillAttributeSet(Set attrSet);
}
