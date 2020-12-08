/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.LayoutData;

public class XYLayoutConstraint implements LayoutData {

	private static final long serialVersionUID = 7384374383237445877L;
	private final int x, y, w, h;

    public XYLayoutConstraint(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public XYLayoutConstraint(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = -1;
        this.h = -1;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return x + "," + y + "," + w + "," + h;
    }
}
