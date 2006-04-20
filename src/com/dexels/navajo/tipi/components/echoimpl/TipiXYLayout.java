package com.dexels.navajo.tipi.components.echoimpl;

import java.util.StringTokenizer;

import nextapp.echo2.app.Component;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.dexels.navajo.tipi.components.echoimpl.impl.XYLayout;
import com.dexels.navajo.tipi.components.echoimpl.impl.XYLayoutConstraint;

public class TipiXYLayout extends TipiLayoutImpl {

	public void createLayout() throws TipiException {
		// myComponent.setContainerLayout(new Row());
		setLayout(new XYLayout());
	}

	public Object getDefaultConstraint(TipiComponent tc, int index) {
		// TODO Auto-generated method stub
		return super.getDefaultConstraint(tc, index);
	}

	protected Object parseConstraint(String text, int index) {
		StringTokenizer st = new StringTokenizer(text, ",");
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		if (!st.hasMoreTokens()) {
			return new XYLayoutConstraint(x, y, -1, -1);
		}
		int w = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		return new XYLayoutConstraint(x, y, w, h);
	}

	protected void setValue(String name, TipiValue tv) {
		// not important
	}

	public void childAdded(Object c) {
		((XYLayout) getLayout()).layoutContainer((Component) myComponent
				.getContainer());
		// myComponent
	}

}
