/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.formatters;

import java.awt.Color;

import com.dexels.navajo.tipi.components.core.TipiFormatter;

@Deprecated
public class ColorFormatter extends TipiFormatter {

	@Override
	public String format(Object o) {
		Color tc = (Color) o;
		String red = Integer.toHexString(tc.getRed());
		if (red.length() == 1) {
			red = "0" + red;
		}
		String green = Integer.toHexString(tc.getGreen());
		if (green.length() == 1) {
			green = "0" + green;
		}
		String blue = Integer.toHexString(tc.getBlue());
		if (blue.length() == 1) {
			blue = "0" + blue;
		}
		String hex = "#" + red + green + blue;
		return "{color:/" + hex + "}";
	}

	@Override
	public Class<?> getType() {
		return Color.class;
	}

	public static void main(String[] args) {
		new ColorFormatter();
	}

}
