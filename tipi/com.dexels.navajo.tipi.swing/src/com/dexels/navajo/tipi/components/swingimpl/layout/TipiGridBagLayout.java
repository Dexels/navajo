/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingGridBagConstraints;

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
public class TipiGridBagLayout extends TipiLayoutImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3670255465500438693L;

	// GridBagLayout layout = null;
	@Override
	protected void setValue(String name, TipiValue tv) {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.TipiLayout abstract method
		 */
	}

	@Override
	public Object parseConstraint(String text, int index) {
		if (text == null) {
			return createDefaultConstraint(index);
		}
		TipiSwingGridBagConstraints gt = new TipiSwingGridBagConstraints(text);
		return gt;
	}

	@Override
	public void createLayout() {
		setLayout(new GridBagLayout());
	}

	@Override
	public Object createDefaultConstraint(int index) {
		return new TipiSwingGridBagConstraints(0, index, 1, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 10, 0, 0), 1, 0);
	}
}
