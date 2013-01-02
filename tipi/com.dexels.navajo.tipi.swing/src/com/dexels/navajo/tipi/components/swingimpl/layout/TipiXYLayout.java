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

	public void createLayout() {
		setLayout(new NullLayout(this,myComponent));
	}

	public Object createDefaultConstraint(int index) {
		return new Rectangle(10, 50 * index, 40, 80);
	}

	protected void setValue(String name, TipiValue tv) {
		throw new UnsupportedOperationException("Not implemented.");
	}

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
	
	public void addToLayout(Object component, Object constraints) {
		((NullLayout)myLayout).addLayoutComponent((Component) component, constraints);
	}
}