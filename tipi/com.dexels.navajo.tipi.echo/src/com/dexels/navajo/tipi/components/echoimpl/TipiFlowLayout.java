package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Row;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

public class TipiFlowLayout extends TipiLayoutImpl {

	private static final long serialVersionUID = 5911721063631611697L;

	public void createLayout() throws TipiException {
        // myComponent.setContainerLayout(new Row());
        setLayout(new Row());
    }

    protected void setValue(String name, TipiValue tv) {
        // TODO Auto-generated method stub

    }

    public Object parseConstraint(String text, int index) {
        return null;
    }

}
