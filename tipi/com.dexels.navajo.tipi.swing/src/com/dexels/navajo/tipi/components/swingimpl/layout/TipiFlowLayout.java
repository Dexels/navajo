/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.FlowLayout;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

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
public class TipiFlowLayout extends TipiLayoutImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4090756250087128602L;

	public TipiFlowLayout() {
	}

	@Override
	public void createLayout() {
		FlowLayout layout = new FlowLayout();
		String align = myDefinition.getStringAttribute("alignment");
		if (align == null) {
			align = "left";
		}
		if (align.equals("left")) {
			layout.setAlignment(FlowLayout.LEFT);
		}
		if (align.equals("right")) {
			layout.setAlignment(FlowLayout.RIGHT);
		}
		if (align.equals("center")) {
			layout.setAlignment(FlowLayout.CENTER);
		}

		String gap = myDefinition.getStringAttribute("gap");
		if (gap != null) {
			int g = Integer.parseInt(gap);
			layout.setHgap(g);
		}
		setLayout(layout);
	}

	@Override
	public Object parseConstraint(String text, int index) {
		return null;
	}

	@Override
	protected void setValue(String name, TipiValue tv) {
		throw new UnsupportedOperationException(
				"Not implemented yet. But I should.");
	}
}
