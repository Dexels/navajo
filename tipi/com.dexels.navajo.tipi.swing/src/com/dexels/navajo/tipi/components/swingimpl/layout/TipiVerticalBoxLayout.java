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

public class TipiVerticalBoxLayout extends TipiLayoutImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8199951786650135856L;

	public TipiVerticalBoxLayout() {
	}

	@Override
	protected void setValue(String name, TipiValue tv) {
		/**
		 * Not necessary, no parameters for this layout.
		 */
	}

	@Override
	public Object parseConstraint(String text, int index) {
		return createDefaultConstraint(index);
	}

	@Override
	public void createLayout() throws com.dexels.navajo.tipi.TipiException {

		setLayout(new GridBagLayout());

	}

	@Override
	public Object createDefaultConstraint(int index) {
		return new TipiSwingGridBagConstraints(0, index, 1, 1, 1, 1,
				GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0);
	}

}
