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
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiHorizontalBoxLayout extends TipiLayoutImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5623592074935290163L;

	public TipiHorizontalBoxLayout() {
	}

	@Override
	protected void setValue(String name, TipiValue tv) {
		/**
		 * Not necessaru
		 */
	}

	@Override
	public Object parseConstraint(String text, int index) {
		return createDefaultConstraint(index);
	}

	@Override
	public void createLayout() throws com.dexels.navajo.tipi.TipiException {
		// Container c = (Container)super.myComponent.getContainer();
		// BoxLayout layout = new BoxLayout(c,BoxLayout.X_AXIS);
		// setLayout(layout);
		setLayout(new GridBagLayout());

		/**
		 * @todo Implement this com.dexels.navajo.tipi.internal.TipiLayout
		 *       abstract method
		 */
	}

	@Override
	public Object createDefaultConstraint(int index) {
		return new TipiSwingGridBagConstraints(index, 0, 1, 1, 1, 1,
				GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0);
	}

}
