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
	protected void setValue(String name, TipiValue tv) {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.TipiLayout abstract method
		 */
	}

	public Object parseConstraint(String text, int index) {
		if (text == null) {
			return createDefaultConstraint(index);
		}
		TipiSwingGridBagConstraints gt = new TipiSwingGridBagConstraints(text);
		return gt;
	}

	public void createLayout() {
		setLayout(new GridBagLayout());
	}

	public Object createDefaultConstraint(int index) {
		return new TipiSwingGridBagConstraints(0, index, 1, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 10, 0, 0), 1, 0);
	}
}
