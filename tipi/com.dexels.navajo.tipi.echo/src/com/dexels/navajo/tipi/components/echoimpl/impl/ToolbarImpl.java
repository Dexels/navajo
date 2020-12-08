/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.Row;
import nextapp.echo2.app.Style;

import com.dexels.navajo.echoclient.components.Styles;

public class ToolbarImpl extends Row {

	private static final long serialVersionUID = -1211947246421681203L;

	public ToolbarImpl() {
		super();
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(getClass(), "Default");
        setStyle(ss);
//        setInsets(new Insets(3));
	}

	
}
