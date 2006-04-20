package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Grid;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

public class TipiGridLayout extends TipiLayoutImpl {

    public void createLayout() throws TipiException {
        // myComponent.setContainerLayout(new Row());
        setLayout(new Grid(2));
    }

    protected void setValue(String name, TipiValue tv) {
        // TODO Auto-generated method stub

    }

    protected Object parseConstraint(String text, int index) {
        return null;
    }

}
