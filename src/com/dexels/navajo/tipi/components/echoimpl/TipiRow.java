package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;
import echopointng.ContainerEx;

public class TipiRow extends TipiEchoDataComponentImpl {
    private Row myRow;

    public Object createContainer() {
        myRow = new Row();
        // myColumn.setLayoutData(new ColumnLayoutData());
        // myContainer.add(myRow);
        return myRow;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myRow.add(comp);
    }

    // public Object getActualComponent() {
    // return myColumn;
    // }

}
