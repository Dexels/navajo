package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.Property;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class PropertyHiddenField extends PropertyPasswordField {

	private static final long serialVersionUID = -8722096684301164869L;

	@Override
	public void setProperty(Property p) {
		if (p == null) {
			// logger.info("Setting to null property. Ignoring");
			return;
		}

		initProperty = p;

		// Trim the value

		setText("xxxxx");
		setEnabled(false);
		setEditable(false);

	}

}
