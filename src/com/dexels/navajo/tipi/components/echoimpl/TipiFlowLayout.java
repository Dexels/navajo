package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Row;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

public class TipiFlowLayout extends TipiLayoutImpl {

	public void createLayout() throws TipiException {
		// myComponent.setContainerLayout(new Row());
		setLayout(new Row());
	}

	protected void setValue(String name, TipiValue tv) {
		// TODO Auto-generated method stub

	}

}
