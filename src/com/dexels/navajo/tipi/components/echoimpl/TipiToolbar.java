package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Row;
import echopointng.ContainerEx;

public class TipiToolbar extends TipiEchoDataComponentImpl {
    private Row myRow;

    public Object createContainer() {
        // ContainerEx myContainer = new ContainerEx();
        myRow = new Row();
        // myColumn.setLayoutData(new ColumnLayoutData());
        // myContainer.add(myRow);
        return myRow;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myRow.add(comp);
        // System.err.println(">>>>>>>>>|||||||||||||||||| Added to
        // container...");
    }

    // public Object getActualComponent() {
    // return myColumn;
    // }

}
