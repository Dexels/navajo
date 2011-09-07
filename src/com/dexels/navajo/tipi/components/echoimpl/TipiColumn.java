package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;

public class TipiColumn extends TipiEchoDataComponentImpl {

	private static final long serialVersionUID = -7956286824574976318L;
	private Column myColumn;

    public Object createContainer() {
        myColumn = new Column();
//        myColumn.setStyleName("Default");
                
        return myColumn;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.add(comp);
    }

}
