/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.parsers;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Rectangle;

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

public class TipiGradientPaint extends GradientPaint {
	private String direction;
	private Color colorOne, colorTwo;
	private Rectangle bounds = new Rectangle(0, 0, 0, 0);

	public TipiGradientPaint(String direction, Color colorOne, Color colorTwo) {
		super(0, 0, colorOne, 0, 0, colorTwo);
		this.colorOne = colorOne;
		this.colorTwo = colorTwo;
		this.direction = direction;
	}

	public void setBounds(Rectangle r) {
		bounds = r;
	}

	public GradientPaint getPaint() {
		GradientPaint gp = null;
		if (this.direction.equalsIgnoreCase("horizontal")) {
		    gp = new GradientPaint(0, 0, colorOne, bounds.width, 0, colorTwo);
		    return gp;
		} else {
		    gp = new GradientPaint(0, 0, colorTwo, 0, bounds.height, colorOne);
          return gp;
		}
	}

	public String getDirection() {
		return direction;
	}

}
