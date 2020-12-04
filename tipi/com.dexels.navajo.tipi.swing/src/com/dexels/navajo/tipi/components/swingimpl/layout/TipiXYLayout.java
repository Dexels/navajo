/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.StringTokenizer;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

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
public class TipiXYLayout extends TipiLayoutImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9050648341678817519L;

	public TipiXYLayout() {
	}

	@Override
	public void createLayout() {
		setLayout(new NullLayout(this,myComponent));
	}

	@Override
	public Object createDefaultConstraint(int index) {
		return new Rectangle(10, 50 * index, 40, 80);
	}

	@Override
	protected void setValue(String name, TipiValue tv) {
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public Object parseConstraint(String text, int index) {
		StringTokenizer st = new StringTokenizer(text, ",");
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		return new Rectangle(x, y, w, h);
	}
	
	@Override
	public void doUpdate() {
		((NullLayout)myLayout).doUpdate();
	}
	
	@Override
	public void addToLayout(Object component, Object constraints) {
		((NullLayout)myLayout).addLayoutComponent((Component) component, constraints);
	}
}