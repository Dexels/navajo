/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
