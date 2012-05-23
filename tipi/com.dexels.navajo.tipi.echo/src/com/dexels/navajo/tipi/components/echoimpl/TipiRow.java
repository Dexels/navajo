package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Row;

public class TipiRow extends TipiEchoDataComponentImpl {
	private static final long serialVersionUID = -408069195341249869L;
	private Row myRow;

    public Object createContainer() {
        myRow = new Row();
        // myColumn.setLayoutData(new ColumnLayoutData());
        // myContainer.add(myRow);
//        myRow.setStyleName("Default");
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
