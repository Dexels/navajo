/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Style;

import com.dexels.navajo.echoclient.components.Styles;

public class GradientContentPane extends ContentPane {

	private static final long serialVersionUID = 4320779262967456985L;

	public GradientContentPane() {
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(getClass(), "Default");
		setStyle(ss);
	}
}
