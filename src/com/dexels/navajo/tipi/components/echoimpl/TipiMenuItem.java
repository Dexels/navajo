package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.ResourceImageReference;
import echopointng.MenuItem;

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

public class TipiMenuItem extends TipiEchoComponentImpl {
	public TipiMenuItem() {
	}

	public Object createContainer() {
		MenuItem b = new MenuItem();
		System.err.println("Creating menu item");
		return b;
	}

	protected void setComponentValue(String name, Object object) {
		MenuItem b = (MenuItem) getContainer();
		if ("text".equals(name)) {
			b.setText("" + object);
		}
		if ("icon".equals(name)) {
			System.err.println("URL: " + object.toString());
			b.setIcon(new ResourceImageReference(object.toString()));
		}
		super.setComponentValue(name, object);
	}

}
