package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Grid;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

public class TipiGridLayout extends TipiLayoutImpl {

	private static final long serialVersionUID = -4685169727853606933L;

	public void createLayout() throws TipiException {
        // myComponent.setContainerLayout(new Row());
        setLayout(new Grid(2));
    }

    protected void setValue(String name, TipiValue tv) {
        // TODO Auto-generated method stub

    }

    public Object parseConstraint(String text, int index) {
        return null;
    }

}
