package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

import echopointng.able.Sizeable;

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
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoBorderLayout extends TipiLayoutImpl {
	public TipiEchoBorderLayout() {
	}

	protected void setValue(String name, TipiValue tv) {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.internal.TipiLayout
		 *       abstract method
		 */
	}

	public void createLayout() throws com.dexels.navajo.tipi.TipiException {
		// myLayout = new Column();
		// if (parent instanceof Sizeable) {
		// Sizeable s = (Sizeable)parent;
		// }
		// if (parent!=null) {
		// // ((Column)myLayout).
		// }
		// EchoBorderLayout p = (EchoBorderLayout) myLayout;
		// p.setVerticalAlignment(EchoConstants.TOP);
		// p.setFullWidth(true);
		// setLayout(p);
	}

	public void childAdded(Object c) {
		Component parent = (Component) myComponent.getContainer();
		if (parent instanceof Sizeable) {
			Sizeable s = (Sizeable) parent;
		}
	}

	protected Object parseConstraint(String text) {
		// if ("north".equals(text)) {
		// return (new CellConstraints(0, 0, 2, 0));
		// }
		// if ("south".equals(text)) {
		// return (new CellConstraints(0, 2, 2, 2));
		// }
		// if ("west".equals(text)) {
		// return (new CellConstraints(0, 1, 0, 1));
		// }
		// if ("east".equals(text)) {
		// return (new CellConstraints(2, 1, 2, 1));
		// }
		// if ("center".equals(text)) {
		// CellConstraints cc = new CellConstraints(1, 1, 1, 1);
		// cc.setHeightUnits(CellConstraints.PERCENT_UNITS);
		// return (cc);
		// }
		// return new CellConstraints(0, 0, 0, 0);
		return null;
	}

}
