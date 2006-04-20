package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import echopointng.ContainerEx;

public class TipiColumn extends TipiEchoDataComponentImpl {
    private Column myColumn;

    public Object createContainer() {
        myColumn = new Column();
        return myColumn;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.add(comp);
    }

}
